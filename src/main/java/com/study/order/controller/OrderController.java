package com.study.order.controller;

import com.study.coffee.service.CoffeeService;
import com.study.order.dto.OrderPatchDto;
import com.study.order.dto.OrderResponseDto;
import com.study.response.MultiResponseDto;
import com.study.response.SingleResponseDto;
import com.study.order.dto.OrderPostDto;
import com.study.order.entity.Order;
import com.study.order.mapper.OrderMapper;
import com.study.order.service.OrderService;
import com.study.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v11/orders")
@Validated
public class OrderController {
    private final static String ORDER_DEFAULT_URL = "/v11/orders";
    private final OrderService orderService;
    private final OrderMapper mapper;
    private final CoffeeService coffeeService;

    public OrderController(OrderService orderService,
                           OrderMapper mapper,
                           CoffeeService coffeeService) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.coffeeService = coffeeService;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {
        Order order = orderService.createOrder(mapper.orderPostDtoToOrder(orderPostDto));
        URI location = UriCreator.createUri(ORDER_DEFAULT_URL, order.getOrderId());

        return ResponseEntity.created(location).build();
    }
    
    //패치오더 핸들러 매서드 추가 (만들면서 dto만들고 매퍼 인터페이스도 손봐줬음)
    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(@PathVariable("order-id") @Positive long orderId,
                                     @Valid @RequestBody OrderPatchDto orderPatchDto) {
        orderPatchDto.setOrderId(orderId); //id저장해주고
        //이제 패치디티오를 오더로 바꿔준 후 엔티티로 리턴해주기
        Order order = orderService.updateOrder(mapper.orderPatchDtoToOrder(orderPatchDto));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)), HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order order = orderService.findOrder(orderId);
        

        // TODO JPA에 맞춰서 변경 필요
        //주문한 커피 정보가 ResponseEntity에 포함되도록 변경

        OrderResponseDto orderResponseDto = mapper.orderToOrderResponseDto(order);
        //List<CoffeeResponseDto> 객체가 orderResponseDto에 포함되어있어서 커피정보 들어가있음. (템플릿에 구성.)
        
        // List<Coffee> coffees = coffeeService.findOrderedCoffees(order); 이전에 사용했던것(다대다 매핑 전)
        return new ResponseEntity<>(
                new SingleResponseDto<>(orderResponseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(@Positive @RequestParam int page, 
                                    @Positive @RequestParam int size) {
        Page<Order> pageOrders = orderService.findOrders(page - 1, size);
        List<Order> orders = pageOrders.getContent();

        // TODO JPA에 맞춰서 주문 커피 정보 추가 필요
        // 주문한 커피 정보 목록이 ResponseEntity에 포함되도록 변경
        // (아래의 orderResponseDtos 객체에 커피 정보 List<CoffeeResponseDto> 포함.)
        List<OrderResponseDto> orderResponseDtos = mapper.ordersToOrderResponseDtos(orders);

        return new ResponseEntity<>(
                new MultiResponseDto<>(orderResponseDtos, pageOrders),
                HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@PathVariable("order-id") @Positive long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
