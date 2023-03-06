package com.study.order.entity;

import com.study.coffee.entity.Coffee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderCoffee { //주문과 커피는 다대다의 관계. 그러한 관계를 @ManyToMany 대신에
    //테이블 설계 시, 다대다의 관계는 중간에 테이블을 하나 추가해서 두 개의 일대다 관계를 만들어주는 것이 일반적인 방법.
    //지금 오더커피 클래스는 그러한 일반적 방법을 나타낸 클래스.
    //즉, 커피와 오더의 다대다를 위한 매개체

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderCoffeeId;

    @Column(nullable = false)
    private int quantity;





    @JoinColumn(name = "ORDER_ID")
    @ManyToOne //주문과 커피가 다대다면, 주문도 다 이고 커피도 다 이다. 그럼 One의 역할은 자연스레오더커피 클래스.
    //다대일 에서의 다 = 오더 안에 있는 커피들, 일 = 오더커피 안에 있는 오더
    private Order order;
    public void setOrder(Order order) {
        this.order = order; //멤버 변수 설정 (여기 오더커피가 가지고있는 오더를, 매개변수 오더로 고쳐줘잉!)
        if(!order.getOrderCoffees().contains(this)) { //매개변수로 받은 오더가 이 오더커피 안가지고 있으면 갖게 해줘잉!
            order.getOrderCoffees().add(this);
        }
    }


    @JoinColumn(name = "COFFEE_ID")
    @ManyToOne
    private Coffee coffee;
    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
        if(!coffee.getOrderCoffees().contains(this)) {
            coffee.getOrderCoffees().add(this);
        }
    }


}
