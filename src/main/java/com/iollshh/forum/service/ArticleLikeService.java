package com.iollshh.forum.service;

import com.iollshh.forum.domain.dto.ArticleLikeDto;
import com.iollshh.forum.domain.dto.Dto;
import com.iollshh.forum.domain.dto.ListDto;
import com.iollshh.forum.domain.dto.ResultDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.ArticleLike;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.ArticleLikeFactory;
import com.iollshh.forum.domain.repository.ArticleLikeRepository;
import com.iollshh.forum.domain.repository.ArticleRepository;
import com.iollshh.forum.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleLikeService implements BusinessLogic{
    private final ArticleLikeRepository articleLikeRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final ArticleLikeFactory articleLikeFactory;

    @PersistenceContext
    EntityManager em;

    //좋아요
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public String createLike(String memberAccountId, Long articleId) throws Exception{

        if(articleLikeRepository.existsByInform(memberAccountId, articleId)){
            return "이미 좋아합니다.";
        }

        Member member = memberRepository.getReferenceByAccountId(memberAccountId);
        if (member == null || member.getAccountId() == null) {
            return "no member";
        }

        Article article = articleRepository.getReferenceById(articleId);
        if (article == null || article.getId() == null) {
            return "no article";
        }

        articleLikeRepository.saveByLikeInform(member, article);
        articleRepository.updateLikeCnt("+", articleId);

        return "success";
    }


    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public String deleteLike(String memberAccountId, Long articleId) throws Exception{

        if(!articleLikeRepository.existsByInform(memberAccountId, articleId)){
            return "이미 좋아하지 않습니다.";
        }
        articleLikeRepository.deleteByLikeInform(memberAccountId, articleId);
        articleRepository.updateLikeCnt("-", articleId);

        return "success";
    }


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ListDto getListByArticleId(Long articleId) throws Exception{

        ListDto listDto;

        List<ArticleLike> articleLikeList = articleLikeRepository.findListByArticleId(articleId);
        List<Dto> list = articleLikeList.stream().map(e->articleLikeFactory.makeDtoByEntity(e)).collect(Collectors.toList());
        listDto = new ListDto(list);

        return listDto;
    }

    //삭제된 글의 좋아요 데이터는 삭제되지 않는다.
    //멤버로 좋아요 조회 기능 등을 구현시 주의
}
