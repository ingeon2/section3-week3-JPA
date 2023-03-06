package com.study.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class OrderCoffeeResponseDto { //오더랑 커피는 다대일 일대다로 매핑되어있으니 API를 위한 dto 따로 필요
    //OrderCoffeeResponseDto 역시 여러분의 실습 과제에서 사용
    //OrderCoffee를 매핑하기 위한 OrderCoffeeResponseDto
    private long coffeeId;
    private String korName;
    private String engName;
    private int price;
    private int quantity;
}
