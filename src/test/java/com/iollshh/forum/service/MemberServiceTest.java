package com.iollshh.forum.service;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.MemberFactory;
import com.iollshh.forum.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @Spy
    private MemberFactory memberFactory;
    @InjectMocks
    private MemberService memberService;


    @Test
    void setNewMemberSuccess() throws Exception {
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
    void findMemberSuccess() throws Exception {
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