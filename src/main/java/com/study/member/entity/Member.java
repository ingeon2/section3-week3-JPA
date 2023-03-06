package com.study.member.entity;

import com.study.order.entity.Order;
import com.study.stamp.entity.Stamp;
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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false, unique = true)
    private String phone;

    //여기가 jdbc랑 달라진부분 (회원의 상태를 저장하기 위해 추가된 enum 필드)
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;
    
    //추가헤준 멤버변수를 위해 만들어준 이눔
    public enum MemberStatus{
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status){
            this.status = status;
        }
    }

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt = LocalDateTime.now();




    @OneToMany(mappedBy = "member") //일대 다 매핑, 멤버 엔티티와의 소통창구
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) //캐스캐이드는 같이 살고 같이 죽는다 이런느낌
    private Stamp stamp;
    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
        if(stamp.getMember()!= this){
            stamp.setMember(this);
        }
    }
}
