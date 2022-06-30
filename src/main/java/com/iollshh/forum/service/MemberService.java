package com.iollshh.forum.service;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.MemberFactory;
import com.iollshh.forum.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberFactory memberFactory;
    private final MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    //회원정보 동기화를 위한 REST API 인터페이스
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public MemberDto setNewMember(MemberDto memberDto) throws Exception {

        Member newMember;

        newMember = memberRepository.saveNewByDto(memberDto);

        return memberFactory.makeDtoByEntity(newMember);
    }

    //회원 탈퇴 처리
    //게시글 전부 비활성화
    //좋아요 삭제

    //nickname 변경


    //회원 정보 확인 <= 필터에 구현
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public MemberDto findMember(String accountId) throws Exception{

        Member member = memberRepository.getReferenceByAccountId(accountId);

        if(member.getQuit().equals("1"))
            throw new Exception("탈퇴회원");

        return memberFactory.makeDtoByEntity(member);
    }
}
