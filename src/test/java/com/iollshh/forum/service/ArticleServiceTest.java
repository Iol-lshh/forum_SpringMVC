package com.iollshh.forum.service;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.dto.ListDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.ArticleFactory;
import com.iollshh.forum.domain.repository.ArticleRepository;
import com.iollshh.forum.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private MemberRepository memberRepository;
    @Spy
    private ArticleFactory articleFactory;
    @InjectMocks
    private ArticleService articleService;

    @Test
    void uploadNewArticleSuccess() throws Exception {
        //#given
        //test case
        ArticleDto requestDto = ArticleDto.builder()
                .writerId("tester")
                .title("test Article")
                .content("this is test.")
                .build();
        //mock
        Member mockMember = Member.builder()
                .id(1l)
                .accountId("tester")
                .accountType("tester")
                .nickname("테스터")
                .quit("0")
                .build();
        given(memberRepository.getReferenceByAccountId(requestDto.getWriterId())).willReturn(mockMember);
        Article newArticle = Article.builder()
                .member(mockMember)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .regdate(null)
                .lastUpdate(null)
                .likeCount(0)
                .build();
        given(articleRepository.saveNewByArticleDto(requestDto)).willReturn(newArticle);

        //#test
        //when
        ArticleDto resArticleDto = articleService.uploadNewArticle(requestDto);
        //then
        assertNotNull(resArticleDto);
        verify(articleRepository).saveNewByArticleDto(requestDto);
    }

    @Test
    void getArticleSuccess() throws Exception {
        //#given
        //test case
        Long articleId = 1l;
        String memberAccountId = "tester";
        //mock
        Article mockArticle = Article.builder()
                .id(1l)
                .member(Member.builder().accountId(memberAccountId).build())
                .title("test Article")
                .content("this is test.")
                .regdate(null)
                .lastUpdate(null)
                .likeCount(0)
                .build();
        given(articleRepository.getReferenceById(articleId)).willReturn(mockArticle);

        //#test
        //when
        ArticleDto resArticleDto = articleService.getArticle(articleId, memberAccountId);
        //then
        assertNotNull(resArticleDto);
        verify(articleRepository).getReferenceById(articleId);
    }

    @Test
    void getArticleListSuccess() throws Exception {
        //#given
        //test case
        int startIdx=0;
        int count = 1;
        String memberAccountId = "tester";
        //mock
        Article mockArticle = Article.builder()
                .id(1l)
                .member(Member.builder().accountId(memberAccountId).build())
                .title("test Article")
                .content("this is test.")
                .regdate(null)
                .lastUpdate(null)
                .likeCount(0)
                .build();
        List<Article> list = new ArrayList<>();
        list.add(mockArticle);
        given(articleRepository.getListByPagination(startIdx, count)).willReturn(list);

        //#test
        //when
        ListDto resListDto = articleService.getArticleList(startIdx, count, memberAccountId);
        //then
        assertNotNull(resListDto);
        verify(articleRepository).getListByPagination(startIdx, count);
    }

    @Test
    void deleteArticleSuccess() throws Exception {
        //#given
        //test case
        Long articleId = 1l;
        String memberAccountId = "tester";
        //mock
        Article mockArticle = Article.builder()
                .id(articleId)
                .member(Member.builder().accountId(memberAccountId).build())
                .title("test Article")
                .content("this is test.")
                .regdate(null)
                .lastUpdate(null)
                .likeCount(0)
                .build();
        given(articleRepository.getReferenceById(articleId)).willReturn(mockArticle);
        //#test
        //when
        String res = articleService.deleteArticle(articleId, memberAccountId);
        //then
        assertNotNull(res);
        verify(articleRepository).deleteSoft(mockArticle);
    }

    @Test
    void updateArticleSuccess() throws Exception {
        //#given
        //test case
        ArticleDto articleDto = ArticleDto.builder()
                .articleId(1l)
                .writerId("tester")
                .title("update test Article")
                .content("this is test about update.")
                .build();
        String memberAccountId = "tester";
        //mock
        Member mockMember = Member.builder()
                .accountId("tester")
                .build();
        Date mockDate = new Date();
        Article mockArticle = Article.builder()
                .id(1l)
                .member(mockMember)
                .title("test Article")
                .content("this is mock Article")
                .regdate(mockDate)
                .lastUpdate(mockDate)
                .likeCount(0)
                .build();
        given(memberRepository.getReferenceByAccountId(memberAccountId)).willReturn(mockMember);
        given(articleRepository.getReferenceById(articleDto.getArticleId())).willReturn(mockArticle);
        Article resArticle = Article.builder()
                .id(1l)
                .member(mockMember)
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .regdate(mockArticle.getRegdate())
                .lastUpdate(new Date())
                .likeCount(0)
                .build();
        given(articleRepository.save(mockArticle)).willReturn(resArticle);

        //#test
        //when
        String res = articleService.updateArticle(articleDto,memberAccountId);
        //then
        assertNotNull(res);
        verify(articleRepository).save(mockArticle);
    }
}