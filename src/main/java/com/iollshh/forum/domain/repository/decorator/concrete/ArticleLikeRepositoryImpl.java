package com.iollshh.forum.domain.repository.decorator.concrete;

import com.iollshh.forum.domain.entity.*;
import com.iollshh.forum.domain.entity.QArticleLike;
import com.iollshh.forum.domain.repository.decorator.ArticleLikeRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@RequiredArgsConstructor
public class ArticleLikeRepositoryImpl implements ArticleLikeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public ArticleLike saveByLikeInform(Member member, Article article){
        ArticleLike newArticleLike = ArticleLike.builder()
                .member(member)
                .article(article)
                .build();

        em.persist(newArticleLike);
        return newArticleLike;
    }

    @Override
    public List<ArticleLike> getListByArticleId(Long articleId) {
        QArticleLike qArticle =  new QArticleLike("qArticle");

        List<ArticleLike> articleLikeList = queryFactory.selectFrom(qArticle)
                .where(qArticle.article.id.eq(articleId))
                .where(qArticle.article.deldate.isNull())    //삭제된 글 제외
                .fetch();

        return articleLikeList;
    }

    @Override
    public boolean existsByInform(String memberAccountId, Long articleId){
        QArticleLike qArticle = new QArticleLike("qArticle");

        boolean res = queryFactory.select(qArticle.id)
                .from(qArticle)
                .where(qArticle.member.accountId.eq(memberAccountId))
                .where(qArticle.article.id.eq(articleId))
                .fetchOne() != null;

        return res;
    }

    @Override
    public ArticleLike getByInform(String memberAccountId, Long articleId){
        QArticleLike qArticleLike = new QArticleLike("qArticleLike");

        ArticleLike res = queryFactory.select(qArticleLike)
                .from(qArticleLike)
                .where(qArticleLike.member.accountId.eq(memberAccountId))
                .where(qArticleLike.article.id.eq(articleId))
                .fetchOne();

        return res;
    }
}
