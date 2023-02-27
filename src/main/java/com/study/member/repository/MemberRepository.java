package com.study.member.repository;

import com.study.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //Spring Data JDBC에서의 MemberRepository와 비교했을때 변경된 부분은 위와 같이
    //CrudRepository를 상속하는 대신 JpaRepository 를 상속하는 것
    //JpaReposiroty가 JPA에 특화된 더 많은 기능들을 포함하고 있기 때문에 JpaReposiroty를 상속
    Optional<Member> findByEmail(String email);
}
