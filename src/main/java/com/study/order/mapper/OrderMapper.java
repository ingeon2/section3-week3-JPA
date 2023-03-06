package com.study.order.mapper;

import com.study.coffee.entity.Coffee;
import com.study.member.entity.Member;
import com.study.order.dto.*;
import com.study.order.entity.Order;
import com.study.order.entity.OrderCoffee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    default Order orderPostDtoToOrder(OrderPostDto orderPostDto) { 
        //오더포스트 dto가 가지고있는 멤버변수는 memberId, orderCoffees 단 두개, 이걸 고려해서 매서드 작성
        if(orderPostDto == null) {
            return null;
        }
        else {
            Order order = new Order(); //리턴할 오더 생성 후 아래는 오더에 장착할 멤버와 오더커피 만드는 일

            Member member = new Member();
            member.setMemberId(orderPostDto.getMemberId());

            List<OrderCoffee> orderCoffees = orderPostDto.getOrderCoffees().stream().map(orderCoffeeDto -> {
                OrderCoffee orderCoffee = new OrderCoffee(); //리턴할놈, 아래는 장착할놈들

                Coffee coffee = new Coffee();
                coffee.setCoffeeId(orderCoffeeDto.getCoffeeId());
                //커피 id만 set 해줬는데 어떻게 order에서 coffee의 나머지 멤버변수들에게 접근할 수 있지?
                //주문(ORDERS) 테이블에 주문 정보가 저장될 때, memberId가 외래키로 추가된다는 의미와 같음. 이게 memberId만 있어도 되는 이유

                orderCoffee.setCoffee(coffee);
                orderCoffee.setOrder(order);
                orderCoffee.setQuantity(orderCoffeeDto.getQuantity());

                return orderCoffee;
            }).collect(Collectors.toList());


            //위의 member와 orderCoffees는 단지 order을 위해 세팅해준것 (결국은 Order 클래스의 변수를 뿌려야 하니까)
            order.setMember(member);
            order.setOrderCoffees(orderCoffees);
            return order;
        }
    }




    Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto); //얘는 컨트롤러에서 쓰려고 만들어줬고
    OrderResponseDto orderToOrderResponseDto(Order order); //얘는 매개변수 오더만 사용하도록 바꿔주었다
    //하지만 현재 상태의 OrderMapper로는 주문 정보 중에서 주문한 커피(List)에 대한 정보는 매핑이 되지 않음.
    //이 부분은 여러분이 실습 과제를 통해 직접 추가 매핑
    List<OrderResponseDto> ordersToOrderResponseDtos(List<Order> orders);


    default OrderCoffeeResponseDto orderCoffeeToOrderCoffeeResponseDto(OrderCoffee orderCoffee) {
        //오더커피리스폰스dto (커피아이디, 코네임, 잉네임, 프라이스, 퀀티티) 왜 적어놓을까? 생각해보기
       if(orderCoffee == null) {
           return null;
       }
       else {
           //OrderCoffeeResponseDto orderCoffeeResponseDto = new OrderCoffeeResponseDto(); 노 아규먼트 없어서 얘는 에러남

           //Long coffeeId = orderCoffee.getCoffee().getCoffeeId(); 나는 이렇게했는데 아래가 더 가독성 좋겠다.
           Coffee coffee = orderCoffee.getCoffee();
           Long coffeeId = coffee.getCoffeeId();
           String korName = coffee.getKorName();
           String engName = coffee.getEngName();
           int price = coffee.getPrice();
           int quantity = orderCoffee.getQuantity();

           return new OrderCoffeeResponseDto(coffeeId, korName, engName, price, quantity);
       }
    }



}
