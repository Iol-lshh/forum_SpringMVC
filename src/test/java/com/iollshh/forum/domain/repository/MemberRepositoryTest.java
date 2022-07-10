package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
//@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void getReferenceByAccountIdSuccess() throws Exception{
        //#given
        //test case
        String accountId = "tester";
        //mock
        MemberDto memberDto = MemberDto.builder()
                .accountId("tester")
                .nickname("테스터")
                .accountType("tester")
                .quit("0")
                .build();
        memberRepository.saveNewByDto(memberDto);

        //#test
        //when
        Member member = memberRepository.getReferenceByAccountId(accountId);
        //then
        assertEquals(accountId, member.getAccountId());
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void saveNewByDtoSuccess() throws Exception{
        //#given
        //test case
        String accountId = "tester";
        MemberDto memberDto = MemberDto.builder()
                .accountId("tester")
                .nickname("테스터")
                .accountType("tester")
                .quit("0")
                .build();
        //#test
        //when
        Member resMember = memberRepository.saveNewByDto(memberDto);
        //then
        assertEquals(memberDto.getAccountId(), resMember.getAccountId());
    }
}