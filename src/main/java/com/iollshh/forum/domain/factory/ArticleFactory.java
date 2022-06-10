package com.iollshh.forum.domain.factory;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleFactory {

    //LAZY시, 트랜잭션 필요
    public ArticleDto makeDtoByEntity(Article article){
        ArticleDto articleDto = ArticleDto.builder()
                .articleId(article.getId())
                .writerId(article.getMember().getAccountId())
                .title(article.getTitle())
                .content(article.getContent())
                .regdate(article.getRegdate())
                .lastUpdate(article.getLastUpdate())
                .deldate(article.getDeldate())
                .likeCount(article.getLikeCount())
                .isLike(article.getArticleLikeList().stream().filter(
                        e -> (e.getMember().getId()).equals(article.getMember().getId())
                ).count()>0)
                .build();
        return articleDto;
    }

    public ArticleDto makeDtoByEntity(Article article, String memberAccountId){
        ArticleDto articleDto = ArticleDto.builder()
                .articleId(article.getId())
                .writerId(article.getMember().getAccountId())
                .title(article.getTitle())
                .content(article.getContent())
                .regdate(article.getRegdate())
                .lastUpdate(article.getLastUpdate())
                .deldate(article.getDeldate())
                .likeCount(article.getLikeCount())
                .build();
        articleDto.setLike(
                article.getArticleLikeList()
                        .stream()
                        .filter(e->e.getMember().getAccountId().equals(memberAccountId))
                        .count() > 0);
        return articleDto;
    }
}
