package com.study.coffee.entity;

import com.study.order.entity.OrderCoffee;
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
@Entity
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coffeeId;

    @Column(length = 100, nullable = false)
    private String korName;

    @Column(length = 100, nullable = false)
    private String engName;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 3, nullable = false, unique = true)
    private String coffeeCode;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    // 추가된 부분, 커피 엔티티와 마찬가지
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CoffeeStatus coffeeStatus = CoffeeStatus.COFFEE_FOR_SALE;

    public enum CoffeeStatus {
        COFFEE_FOR_SALE("판매중"),
        COFFEE_SOLD_OUT("판매중지");

        @Getter
        private String status;

        CoffeeStatus(String status) {
            this.status = status;
        }
    }


    //오더커피와의 관계
    @OneToMany(mappedBy = "coffee", cascade = CascadeType.PERSIST) //맵드바이 보고 여기랑 연관된 클래스에 커피 클래스의 pk인 커피아이디가 잡혀있겠구나! 생각
    private List<OrderCoffee> orderCoffees = new ArrayList<>();
    public void setOrderCoffee(OrderCoffee orderCoffee) {
        orderCoffees.add(orderCoffee);
        if(orderCoffee.getCoffee() != this) {
            orderCoffee.setCoffee(this);
        }
    }
    
}
