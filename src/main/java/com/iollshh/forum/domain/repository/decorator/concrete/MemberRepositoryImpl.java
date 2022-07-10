package com.iollshh.forum.domain.repository.decorator.concrete;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.entity.QMember;
import com.iollshh.forum.domain.repository.decorator.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    //
    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager em;

    @Override
    public Member getReferenceByAccountId(String accountId) {
        QMember qMember = new QMember("M");

        Member member = queryFactory.selectFrom(qMember)
                .where(qMember.accountId.eq(accountId)
                        .and(qMember.quit.eq("0")))
                .fetchOne();

        return member;
    }

    @Override
    public Member saveNewByDto(MemberDto memberDto) {

        Member newMember = Member.builder()
                .nickname(memberDto.getNickname())
                .accountType(memberDto.getAccountType())
                .accountId(memberDto.getAccountId())
                .quit("0")
                .build();

        em.persist(newMember);
        em.flush();

        return newMember;
    }
}
