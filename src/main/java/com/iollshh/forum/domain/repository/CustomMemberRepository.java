package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;

public interface CustomMemberRepository {

    //search Member by accountId
    Member findOneByAccountId(String AccountId);

    Member saveNewByDto(MemberDto memberDto);
}
