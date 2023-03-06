package com.study.order.entity;

import com.study.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST;

    public enum OrderStatus {
        ORDER_REQUEST(1, "주문 요청"),
        ORDER_CONFIRM(2, "주문 확정"),
        ORDER_COMPLETE(3, "주문 완료"),
        ORDER_CANCEL(4, "주문 취소");

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne //다대일, 누구랑?
    //일반적으로 부모 테이블에서 기본키로 설정된 컬럼명과 동일하게 외래키 컬럼을 만드는데, 
    //여기서는 MEMBER 테이블의 기본키 컬럼명이 “MEMBER_ID” 이기 때문에 동일하게 적음.
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    public void setMember(Member member) { //매핑에 필요한 매서드. (오더의 멤버변수 멤버를 세팅해주는 매서드)
        this.member = member;
    }



    
    //오더커피 클래스와의 관계
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST) //오더커피가 외래키로 선택한 memberId를 소유한 클래스는 member (맵드바이 멤버인 이유)
    private List<OrderCoffee> orderCoffees = new ArrayList<>(); //얘는 일대다의 다 이니까 리스트(다)
    public void setOrderCoffee(OrderCoffee orderCoffee){
        orderCoffees.add(orderCoffee);
        if(orderCoffee.getOrder() != this) {
            orderCoffee.setOrder(this);
        }
    }

}
