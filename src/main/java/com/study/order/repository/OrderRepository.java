package com.study.order.repository;

import com.study.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> { //crud에서 jpa로 (부가기능 추가)
}

