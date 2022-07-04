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
        given(articleRepository.saveByArticleDto(requestDto)).willReturn("success");
        given(articleRepository.getReferenceById(requestDto.getArticleId())).willReturn(newArticle);

        //test
        ArticleDto resArticleDto = articleService.uploadNewArticle(requestDto);
        assertNotNull(resArticleDto);
        verify(articleRepository).saveByArticleDto(requestDto);
    }

    @Test
    void getArticleSuccess() throws Exception {
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

        //test
        ArticleDto resArticleDto = articleService.getArticle(articleId, memberAccountId);
        assertNotNull(resArticleDto);
        verify(articleRepository).getReferenceById(articleId);
    }

    @Test
    void getArticleListSuccess() throws Exception {
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
        given(articleRepository.findListByPagination(startIdx, count)).willReturn(list);

        //test
        ListDto resListDto = articleService.getArticleList(startIdx, count, memberAccountId);
        assertNotNull(resListDto);
        verify(articleRepository).findListByPagination(startIdx, count);
    }

    @Test
    void deleteArticleSuccess() throws Exception {
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
        //test
        String res = articleService.deleteArticle(articleId, memberAccountId);
        assertNotNull(res);
        verify(articleRepository).deleteById(articleId);
    }

    @Test
    void updateArticleSuccess() throws Exception {
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
        Article mockArticle = Article.builder()
                .id(1l)
                .member(mockMember)
                .title("test Article")
                .content("this is mock Article")
                .regdate(null)
                .lastUpdate(null)
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
                .lastUpdate(null)
                .likeCount(0)
                .build();
        given(articleRepository.saveByArticleDto(articleDto)).willReturn("success");
        //test
        String res = articleService.updateArticle(articleDto,memberAccountId);
        assertNotNull(res);
        verify(articleRepository).saveByArticleDto(articleDto);
    }
}