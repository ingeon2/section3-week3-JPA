package com.study.order.dto;

import com.study.coffee.dto.CoffeeResponseDto;
import com.study.member.entity.Member;
import com.study.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private long orderId;
    private long memberId;
    private Order.OrderStatus orderStatus;
    private List<CoffeeResponseDto> orderCoffees;
    private LocalDateTime createdAt;

    // 리파지토리에서 사용할것. 다대다때문에 새로운 클래스 생겼으니 새로운 dto도생기고, 서비스 로직 손봐줘야지..ㅠ
    // Order를 OrderResponseDto로 바꿀 때 member를 memberId로 매핑해야하기 때문에 만든 메서드임
    public void setMember(Member member) {
        this.memberId = member.getMemberId();
    }
}
