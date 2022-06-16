package com.iollshh.forum.domain.repository.decorator;

import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.ArticleLike;
import com.iollshh.forum.domain.entity.Member;

import java.util.List;

public interface ArticleLikeRepositoryCustom {

    ArticleLike saveByLikeInform(Member member, Article article);

    Long deleteByLikeInform(String memberAccountId, Long articleId);

    List<ArticleLike> findListByArticleId(Long parsedArticleId);

    boolean existsByInform(String memberAccountId, Long articleId);

    ArticleLike findOneByInform(String memberAccountId, Long articleId);

    Long deleteByMemberAccountId(String memberAccountId);

    Long deleteByArticleId(Long articleId);
}
