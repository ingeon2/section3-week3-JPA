package com.study.member.service;

import com.study.exception.BusinessLogicException;
import com.study.exception.ExceptionCode;
import com.study.member.entity.Member;
import com.study.member.repository.MemberRepository;
import com.study.stamp.entity.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberService {

    //DI from 리파지토리
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }


    //여기는 서비스로직
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail()); //여기 겟셋은 롬복 애너테이션으로, 등록된 이메일인지 확인
        initStamp(member); //저장할 매개변수 멤버의 스탬프 초기화
        return memberRepository.save(member); //여기 save는 구현한게 아니라 자동으로 jpa리파지토리 인터페이스에서 가져오는것
    }
    private static void initStamp(Member member) { //스탬프 초기화 매서드
        Stamp stamp = new Stamp();
        stamp.setMember(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getName()).ifPresent(name -> findMember.setName(name)); //이름 바꾸기
        Optional.ofNullable(member.getPhone()).ifPresent(phone -> findMember.setPhone(phone)); //폰번호 바꾸기

        //추가된 부분 (기능추가)
        Optional.ofNullable(member.getMemberStatus()).ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));
        findMember.setModifiedAt(LocalDateTime.now());

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }


    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }

    //여기 아래부터는 서비스 안에서 사용할 매서드, 비즈니스로직익셉션이랑 익셉션코드도 내가 만든 클래스와 이눔이다.
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}
