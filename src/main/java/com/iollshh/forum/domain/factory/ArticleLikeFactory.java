package com.iollshh.forum.domain.factory;

import com.iollshh.forum.domain.dto.ArticleLikeDto;
import com.iollshh.forum.domain.entity.ArticleLike;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ArticleLikeFactory {

    //LAZY시 트랜잭션 필요
    @Transactional
    public ArticleLikeDto makeDtoByEntity(ArticleLike article){
        ArticleLikeDto articleLikeDto = ArticleLikeDto.builder()
                .likeId(article.getId())
                .articleId(article.getArticle().getId())
                .memberAccountId(article.getMember().getAccountId())
                .build();
        return articleLikeDto;
    }
}
