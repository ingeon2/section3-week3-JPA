package com.study.order.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderCoffeeResponseDto { //오더랑 커피는 다대일 일대다로 매핑되어있으니 API를 위한 dto 따로 필요
    private long coffeeId;
    private String korName;
    private String engName;
    private int price;
    private int quantity;
}
