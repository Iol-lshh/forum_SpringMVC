package com.iollshh.forum.domain.factory;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberFactory {

    public MemberDto makeDtoByEntity(Member member){
        MemberDto memberDto = MemberDto.builder()
                .accountId(member.getAccountId())
                .nickname(member.getNickname())
                .accountType(member.getAccountType())
                .quit(member.getQuit())
                .build();
        return memberDto;
    }
}
