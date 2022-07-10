package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Article;
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
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    void saveNewByArticleDtoSuccess() throws Exception{
        //#given
        //test case
        Date regdate = new Date();
        ArticleDto articleDto = ArticleDto.builder()
                .writerId("tester")
                .title("test")
                .content("this is test")
                .regdate(regdate)
                .lastUpdate(regdate)
                .build();
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
        Article res = articleRepository.saveNewByArticleDto(articleDto);
        //then
        assertNotNull(res);
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    void getListByPaginationSuccess() throws Exception {
        //#given
        //test case
        int startIdx=0;
        int count=10;
        //mock
        saveNewByArticleDtoSuccess(); //실패를 대비하여, 하나는 넣어둔다.

        //#test
        //when
        List<Article> res = articleRepository.getListByPagination(startIdx, count);
        //then
        assertNotNull(res);
        assertTrue(res.size()>0);
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    void deleteByArticleIdSuccess() throws Exception {
        //#given
        //test case
        Long articleId = 6l;
        //mock
        saveNewByArticleDtoSuccess(); //실패를 대비하여, 하나는 넣어둔다.

        //#test
        //when
        Article article = articleRepository.getReferenceById(articleId);
        String res = articleRepository.deleteSoft(article);
        //then
        assertEquals(res,"success");
    }
}