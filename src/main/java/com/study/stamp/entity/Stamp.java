package com.study.stamp.entity;


import com.study.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stamp { //스탬프는 회원이 주문하는 커피 한잔 당 찍히는 도장
    //Member 클래스(회원)와 Stamp 클래스(도장)의 연관 관계 매핑은 일대일

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stampId; //테이블 상에서 자동 증가하는 기본키

    @Column(nullable = false)
    private int stampCount; //주문하는 커피 한잔 당 stampCount가 1씩 증가

    @Column (nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();



    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    public void setMember(Member member) {
        this.member = member;
        if(member.getStamp() != this) {
            member.setStamp(this);
        }
    }


}
