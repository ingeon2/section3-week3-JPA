package com.study.order.service;

import com.study.coffee.service.CoffeeService;
import com.study.exception.BusinessLogicException;
import com.study.exception.ExceptionCode;
import com.study.member.entity.Member;
import com.study.member.service.MemberService;
import com.study.order.entity.Order;
import com.study.order.entity.OrderCoffee;
import com.study.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final MemberService memberService;
    private final OrderRepository orderRepository;
    private final CoffeeService coffeeService; //다대다때문에 추가

    public OrderService(MemberService memberService, OrderRepository orderRepository, CoffeeService coffeeService) {
        this.memberService = memberService;
        this.orderRepository = orderRepository;
        this.coffeeService = coffeeService; //다대다때문에 추가
    }

    public Order createOrder(Order order) {
        // 회원이 존재하는지 확인
        Member verifiedMember = memberService.findVerifiedMember(order.getMember().getMemberId()); 
        //체크하면서 동시에 변수화 (아래 업데이트에 쓸거거든ㅋ)

        //메서드에서 주문한 커피가 테이블에 존재하는 커피인지 체크하는 기능이 포함되도록 수정!, 다대다
        List<OrderCoffee> orderCoffees = order.getOrderCoffees(); //주문한 커피가
        orderCoffees.stream()
                .forEach(orderCoffee -> coffeeService.findVerifiedCoffee(orderCoffee.getCoffee().getCoffeeId())); 
        //존재하는지, 여기가 커피서비스 di받은 이유

        updateStamp(order, verifiedMember);
        return orderRepository.save(order);
    }
    private static void updateStamp(Order order, Member verifiedMember) { //주문한 커피의 수량만큼 회원의 스탬프 숫자를 증가
        int coffeeQuantity = order.getOrderCoffees().stream().mapToInt(orderCoffee -> orderCoffee.getQuantity()).sum(); //주문한커피의 수량
        //stream 이해 잘하기. OrderCoffees는 List이고 그안에있는 orderCoffee 객체를 stream으로 원하는 방식으로 이용.
        int stampCount = verifiedMember.getStamp().getStampCount(); //원래 스탬프 수

        verifiedMember.getStamp().setStampCount(coffeeQuantity + stampCount); //스탬프 숫자 증가 (커피 수량만큼)
    }



    // 주문 상태 처리를 위한 updateOrder() 메서드 추가됨. (기능추가)
    public Order updateOrder(Order order) {
        Order findOrder = findVerifiedOrder(order.getOrderId());

        Optional.ofNullable(order.getOrderStatus()).ifPresent(orderStatus -> findOrder.setOrderStatus(orderStatus));
        findOrder.setModifiedAt(LocalDateTime.now());
        return orderRepository.save(findOrder);
    }

    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

    public Page<Order> findOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size, Sort.by("orderId").descending()));
    }

    public void cancelOrder(long orderId) {
        Order findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();

        //// OrderStatus의 step이 2 이상일 경우(ORDER_CONFIRM)에는 주문 취소가 되지 않도록함.
        if (step >= 2) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        findOrder.setModifiedAt(LocalDateTime.now());
        orderRepository.save(findOrder);
    }

    private Order findVerifiedOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder =
                optionalOrder.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }
}