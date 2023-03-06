package com.study.order.dto;

import com.study.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class OrderPostDto {
    @Positive
    private long memberId;

    @Valid
    @NotNull(message = "주문할 커피 정보는 필수입니다.")
    private List<OrderCoffeeDto> orderCoffees;

    private Member getMember() { //리파지토리에서 사용할것. 다대다때문에 새로운 클래스 생겼으니 새로운 dto도생기고, 서비스 로직 손봐줘야지..ㅠ
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}
