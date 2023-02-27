package com.study.coffee.repository;

import com.study.coffee.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> { //여기 바뀌고
    Optional<Coffee> findByCoffeeCode(String coffeeCode);



//    @Query(value = "SELECT * FROM COFFEE WHERE coffee_Id = :coffeeId", nativeQuery = true) 진짜 SQL문
//    @Query(value = "FROM Coffee c WHERE c.coffeeId = :coffeeId") 얘는 지금 실제 사용하는 쿼리와 같은내용인데 ‘SELECT c’를 생략한 형태로 사용 가능.
    @Query(value = "SELECT c FROM Coffee c WHERE c.coffeeId = :coffeeId") //Coffee는 클래스명이고, coffeeId는 Coffee 클래스의 필드명
    Optional<Coffee> findByCoffee(long coffeeId);
}

//JPA에서는 JPQL이라는 객체 지향 쿼리를 통해 데이터베이스 내의 테이블을 조회할 수 있음.
//JPQL은 데이터베이스의 테이블을 대상으로 조회 작업을 진행하는 것이 아니라 엔티티 클래스의 객체를 대상으로 객체를 조회하는 방법.
//JPQL의 문법을 사용해서 객체를 조회하면 JPA가 내부적으로 JPQL을 분석해서
//적절한 SQL을 만든 후에 데이터베이스를 조회하고, 조회한 결과를 엔티티 객체로 매핑한 뒤에 반환.
//현재의 쿼리문은 JPQL을 사용해서 coffeeId에 해당하는 커피 정보를 조회.
//JPQL의 쿼리문을 보면 SQL과 유사하지만 차이점이 있습음. (SQL 쿼리문 같지만 SQL 쿼리문이 아니니 유심히 잘 보기)

//하지만 중요한것. 위의 쿼리문 셋 다 호환 가능 in JPA