package com.study.member.dto;

import com.study.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

// TODO 변경: Builder 패턴 적용
@Builder
@Getter
public class MemberResponseDto {
    private long memberId;
    private String email;
    private String name;
    private String phone;

    //회원 상태 업데이트 기능 추가에 따른 memberStatus 필드 추가
    private Member.MemberStatus memberStatus;
    public String getMemberStatus() {
        return memberStatus.getStatus();
    }
}
