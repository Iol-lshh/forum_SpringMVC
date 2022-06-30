package com.iollshh.forum.service;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MemberServiceTest {
    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    void setNewMember() throws Exception {
        //test case
        MemberDto memberDto = MemberDto.builder()
                .accountId("tester2")
                .accountType("tester")
                .nickname("테스터")
                .build();
        //mock
        Member mockMember = Member.builder()
                .id(1l)
                .accountId("tester2")
                .accountType("tester")
                .nickname("테스터")
                .quit("0")
                .build();
        given(memberRepository.saveNewByDto(memberDto)).willReturn(mockMember);

        //test
        MemberDto resMemberDto = memberService.setNewMember(memberDto);
        assertNotNull(resMemberDto);
        verify(memberRepository).saveNewByDto(memberDto);
    }

    @Test
    void findMember() throws Exception {
        //test case
        String accountId = "tester";

        //mock
        Member mockMember = Member.builder()
                .id(1l)
                .accountId("tester")
                .accountType("tester")
                .nickname("테스터")
                .quit("0")
                .build();
        given(memberRepository.getReferenceByAccountId(accountId)).willReturn(mockMember);

        //test
        MemberDto resMemberDto = memberService.findMember(accountId);
        assertEquals(resMemberDto.getAccountId(), accountId);
    }
}