package com.iollshh.forum.domain.repository.decorator;

import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.ArticleLike;
import com.iollshh.forum.domain.entity.Member;

import java.util.List;

public interface ArticleLikeRepositoryCustom {

    ArticleLike saveByLikeInform(Member member, Article article);

    List<ArticleLike> getListByArticleId(Long parsedArticleId);

    boolean existsByInform(String memberAccountId, Long articleId);

    ArticleLike getByInform(String memberAccountId, Long articleId);
}
