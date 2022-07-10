package com.iollshh.forum.service;

import com.iollshh.forum.domain.dto.ListDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.ArticleLike;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.ArticleLikeFactory;
import com.iollshh.forum.domain.repository.ArticleLikeRepository;
import com.iollshh.forum.domain.repository.ArticleRepository;
import com.iollshh.forum.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleLikeServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ArticleLikeRepository articleLikeRepository;
    @Spy
    private ArticleLikeFactory articleLikeFactory;
    @InjectMocks
    private ArticleLikeService articleLikeService;

    @Test
    void createLikeSuccess() throws Exception {
        //#given
        //test case
        String memberAccountId = "tester";
        Long articleId = 1l;
        //mock
        Member mockMember = Member.builder()
                .id(1l)
                .accountId("tester")
                .accountType("tester")
                .nickname("테스터")
                .quit("0")
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
        ArticleLike resArticleLike = ArticleLike.builder()
                .member(mockMember)
                .article(mockArticle)
                .build();
        Article mockArticle2 = Article.builder()
                .id(1l)
                .member(mockMember)
                .title("test Article")
                .content("this is mock Article")
                .regdate(null)
                .lastUpdate(null)
                .likeCount(mockArticle.getLikeCount()+1)
                .build();
        given(articleLikeRepository.existsByInform(memberAccountId,articleId)).willReturn(false);
        given(memberRepository.getReferenceByAccountId(memberAccountId)).willReturn(mockMember);
        given(articleRepository.getReferenceById(articleId)).willReturn(mockArticle);
        given(articleLikeRepository.saveByLikeInform(mockMember,mockArticle)).willReturn(resArticleLike);
        given(articleRepository.save(mockArticle2)).willReturn(mockArticle2);

        //#test
        //when
        String res = articleLikeService.createLike(memberAccountId, articleId);
        //then
        assertNotNull(res);
        assertEquals(res,"success");
        verify(articleLikeRepository).saveByLikeInform(mockMember, mockArticle);
    }

    @Test
    void deleteLikeSuccess() throws Exception {
        //#given
        //test case
        String memberAccountId = "tester";
        Long articleId = 1l;
        //mock
        Member mockMember = Member.builder()
                .id(1l)
                .accountId("tester")
                .accountType("tester")
                .nickname("테스터")
                .quit("0")
                .build();
        Article mockArticle = Article.builder()
                .id(1l)
                .member(mockMember)
                .title("test Article")
                .content("this is mock Article")
                .regdate(null)
                .lastUpdate(null)
                .likeCount(1)
                .build();
        ArticleLike resArticleLike = ArticleLike.builder()
                .member(mockMember)
                .article(mockArticle)
                .build();
        Article mockArticle2 = Article.builder()
                .id(1l)
                .member(mockMember)
                .title("test Article")
                .content("this is mock Article")
                .regdate(null)
                .lastUpdate(null)
                .likeCount(mockArticle.getLikeCount()-1)
                .build();
        //mock
        given(articleLikeRepository.existsByInform(memberAccountId, articleId)).willReturn(true);
        given(memberRepository.getReferenceByAccountId(memberAccountId)).willReturn(mockMember);
        given(articleRepository.getReferenceById(articleId)).willReturn(mockArticle);
        given(articleLikeRepository.getByInform(memberAccountId,articleId)).willReturn(resArticleLike);
        given(articleRepository.save(mockArticle2)).willReturn(mockArticle2);
        //#test
        //when
        String res = articleLikeService.deleteLike(memberAccountId, articleId);
        //then
        assertNotNull(res);
        assertEquals(res,"success");
        verify(articleLikeRepository).delete(resArticleLike);
    }

    @Test
    void getListByArticleIdSuccess() throws Exception {
        //#given
        //test case
        Long articleId = 1l;
        //mock
        Member mockMember = Member.builder()
                .id(1l)
                .accountId("tester")
                .accountType("tester")
                .nickname("테스터")
                .quit("0")
                .build();
        Article mockArticle = Article.builder()
                .id(1l)
                .member(mockMember)
                .title("test Article")
                .content("this is mock Article")
                .regdate(null)
                .lastUpdate(null)
                .likeCount(1)
                .build();
        ArticleLike resArticleLike = ArticleLike.builder()
                .member(mockMember)
                .article(mockArticle)
                .build();
        List<ArticleLike> list = new ArrayList<>();
        list.add(resArticleLike);
        given(articleLikeRepository.getListByArticleId(articleId)).willReturn(list);

        //#test
        //when
        ListDto resListDto = articleLikeService.getListByArticleId(articleId);
        //then
        assertNotNull(resListDto);
        verify(articleLikeRepository).getListByArticleId(articleId);
    }
}