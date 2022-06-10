package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@RequiredArgsConstructor
public class ArticleLikeRepositoryImpl implements CustomArticleLikeRepository{

    private final JPAQueryFactory queryFactory;
    private final EntityManagerFactory emf;

    @Override
    public ArticleLike saveByLikeInform(Member member, Article article){
        ArticleLike newArticleLike = ArticleLike.builder()
                .member(member)
                .article(article)
                .build();

        return newArticleLike;
    }

    @Override
    public Long deleteByLikeInform(String memberAccountId, Long articleId) {
        QArticleLike articleLike1 = new QArticleLike("articleLike1");

        Long targetId = queryFactory.select(articleLike1.id)
                .from(articleLike1)
                .where(articleLike1.member.accountId.eq(memberAccountId))
                .where(articleLike1.article.id.eq(articleId))
                .fetchOne();

        Long result = queryFactory.delete(articleLike1)
                .where(articleLike1.id.eq(targetId))
                .execute();

        return result;
    }

    @Override
    public List<ArticleLike> findListByArticleId(Long parsedArticleId) {
        QArticleLike articleLike1 =  new QArticleLike("articleLike1");

        List<ArticleLike> articleLikeList = queryFactory.selectFrom(articleLike1)
                .where(articleLike1.article.id.eq(parsedArticleId))
                .where(articleLike1.article.deldate.isNull())    //삭제된 글 제외
                .fetch();

        return articleLikeList;
    }

    @Override
    public Long deleteByMemberAccountId(String memberAccountId) {
        QArticleLike articleLike1 = new QArticleLike("articleLike1");

        Long result = queryFactory.delete(articleLike1)
                .where(articleLike1.member.accountId.eq(memberAccountId))
                .execute();

        return result;
    }

    @Override
    public Long deleteByArticleId(Long articleId) {
        QArticleLike articleLike1 = new QArticleLike("articleLike1");

        Long result = queryFactory.delete(articleLike1)
                .where(articleLike1.article.id.eq(articleId))
                .execute();

        return result;
    }

    @Override
    public ArticleLike findOneByInform(String memberAccountId, Long articleId){
        QArticleLike articleLike1 = new QArticleLike("articleLike1");

        ArticleLike articleLike = queryFactory.selectFrom(articleLike1)
                .where(articleLike1.member.accountId.eq(memberAccountId))
                .where(articleLike1.article.id.eq(articleId))
                .fetchOne();
        return articleLike;
    }

    @Override
    public boolean existsByInform(String memberAccountId, Long articleId){
        QArticleLike articleLike1 = new QArticleLike("articleLike1");

        boolean res = queryFactory.select(articleLike1.id)
                .from(articleLike1)
                .where(articleLike1.member.accountId.eq(memberAccountId))
                .where(articleLike1.article.id.eq(articleId))
                .fetchOne() != null;

        return res;
    }
}
