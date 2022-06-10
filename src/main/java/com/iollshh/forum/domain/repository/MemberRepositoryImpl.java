package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements CustomMemberRepository{

    private final JPAQueryFactory queryFactory;

    //
    @Override
    public Member findOneByAccountId(String accountId) {
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
        return newMember;
    }
}
