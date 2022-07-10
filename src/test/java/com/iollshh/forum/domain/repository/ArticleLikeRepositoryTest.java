package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.ArticleLike;
import com.iollshh.forum.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class ArticleLikeRepositoryTest {

    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    void saveByLikeInformSuccess(){
        //#given
        MemberDto memberDto = MemberDto.builder()
                .accountId("tester")
                .nickname("테스터")
                .accountType("tester")
                .quit("0")
                .build();
        Member testMember = memberRepository.saveNewByDto(memberDto);
        Date regdate = new Date();
        ArticleDto articleDto = ArticleDto.builder()
                .writerId("tester")
                .title("test")
                .content("this is test")
                .regdate(regdate)
                .lastUpdate(regdate)
                .build();
        Article testArticle = articleRepository.saveNewByArticleDto(articleDto);
        //#test
        //when
        ArticleLike res = articleLikeRepository.saveByLikeInform(testMember, testArticle);
        //then
        assertNotNull(res);
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    void getListByArticleIdSuccess(){
        //#given
        //test case
        MemberDto memberDto = MemberDto.builder()
                .accountId("tester")
                .nickname("테스터")
                .accountType("tester")
                .quit("0")
                .build();
        Member testMember = memberRepository.saveNewByDto(memberDto);
        Date regdate = new Date();
        ArticleDto articleDto = ArticleDto.builder()
                .writerId("tester")
                .title("test")
                .content("this is test")
                .regdate(regdate)
                .lastUpdate(regdate)
                .build();
        Article testArticle = articleRepository.saveNewByArticleDto(articleDto);
        Long testArticleId = testArticle.getId();
        //#test
        //when
        List<ArticleLike> res = articleLikeRepository.getListByArticleId(testArticleId);
        //then
        assertNotNull(res);
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    void existsByInformSuccess(){
        //#given
        //test case
        MemberDto memberDto = MemberDto.builder()
                .accountId("tester")
                .nickname("테스터")
                .accountType("tester")
                .quit("0")
                .build();
        Member testMember = memberRepository.saveNewByDto(memberDto);
        Date regdate = new Date();
        ArticleDto articleDto = ArticleDto.builder()
                .writerId("tester")
                .title("test")
                .content("this is test")
                .regdate(regdate)
                .lastUpdate(regdate)
                .build();
        Article testArticle = articleRepository.saveNewByArticleDto(articleDto);
        Long testArticleId = testArticle.getId();
        String testMemberAccountId = memberDto.getAccountId();
        //#test
        //when
        boolean res = articleLikeRepository.existsByInform(testMemberAccountId, testArticleId);
        //then
        assertNotNull(res);
        assertTrue(res);
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    void getByInformSuccess(){
        //#given
        //test case
        MemberDto memberDto = MemberDto.builder()
                .accountId("tester")
                .nickname("테스터")
                .accountType("tester")
                .quit("0")
                .build();
        Member testMember = memberRepository.saveNewByDto(memberDto);
        Date regdate = new Date();
        ArticleDto articleDto = ArticleDto.builder()
                .writerId("tester")
                .title("test")
                .content("this is test")
                .regdate(regdate)
                .lastUpdate(regdate)
                .build();
        Article testArticle = articleRepository.saveNewByArticleDto(articleDto);
        Long testArticleId = testArticle.getId();
        String testMemberAccountId = memberDto.getAccountId();
        //#test
        //when
        ArticleLike res = articleLikeRepository.getByInform(testMemberAccountId, testArticleId);
        //then
        assertNotNull(res);
    }
}