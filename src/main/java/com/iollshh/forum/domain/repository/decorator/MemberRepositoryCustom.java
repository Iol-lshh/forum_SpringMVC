package com.iollshh.forum.domain.repository.decorator;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;

public interface MemberRepositoryCustom {

    //search Member by accountId
    Member getReferenceByAccountId(String AccountId);

    Member saveNewByDto(MemberDto memberDto);
}
