package com.study.order.mapper;

import com.study.coffee.entity.Coffee;
import com.study.order.dto.OrderPatchDto;
import com.study.order.dto.OrderPostDto;
import com.study.order.dto.OrderResponseDto;
import com.study.order.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderPostDtoToOrder(OrderPostDto orderPostDto);
    Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto); //얘는 컨트롤러에서 쓰려고 만들어줬고
    OrderResponseDto orderToOrderResponseDto(Order order); //얘는 매개변수 오더만 사용하도록 바꿔주었다
    //하지만 현재 상태의 OrderMapper로는 주문 정보 중에서 주문한 커피(List)에 대한 정보는 매핑이 되지 않음.
    //이 부분은 여러분이 실습 과제를 통해 직접 추가 매핑
    List<OrderResponseDto> ordersToOrderResponseDtos(List<Order> orders);
}
