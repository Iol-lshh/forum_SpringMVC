package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.entity.QArticle;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements CustomArticleRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManagerFactory emf;

    @Override
    public Article saveByMemberAndDto(Member member, ArticleDto articleDto) {
        Article newArticle = Article.builder()
                .member(member)
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .regdate(articleDto.getRegdate())
                .lastUpdate(articleDto.getRegdate())
                .likeCount(0)
                .build();

        EntityManager em = emf.createEntityManager();
        EntityTransaction tr = em.getTransaction();

        return newArticle;
    }

    @Override
    public Article findOneByArticleId(Long articleId) {
        QArticle article1 =  new QArticle("article1");

        Article article = queryFactory.selectFrom(article1)
                .where(article1.id.eq(articleId)
                        .and(article1.deldate.isNull())
                )
                .fetchOne();
        return article;
    }

    @Override
    public List<Article> findListByPagination(int startIdx, int count) {
        QArticle article1 =  new QArticle("article1");

        List<Article> articleList = queryFactory.selectFrom(article1)
                .orderBy(article1.id.asc())
                .offset(startIdx)
                .limit(count)
                .fetch();
        return articleList;
    }

    @Override
    public Article deleteByArticleId(Long articleId) {
        //시간
        Date regdate = new Date();
        QArticle article1 = new QArticle("article1");

        queryFactory.update(article1)
                .set(article1.deldate, regdate)
                .where(article1.id.eq(articleId))
                .execute();

        Article article = queryFactory.selectFrom(article1)
                .where(article1.id.eq(articleId))
                .fetchOne();

        return article;
    }

    @Override
    @Transactional
    public Article updateByArticle(Member member, ArticleDto articleDto) {
        //시간
        Date regdate = new Date();
        QArticle article1 = new QArticle("article1");

        queryFactory.update(article1)
                .set(article1.title, articleDto.getTitle())
                .set(article1.content, articleDto.getContent())
                .set(article1.lastUpdate, articleDto.getLastUpdate())
                .where(article1.id.eq(articleDto.getArticleId())
                        .and(article1.deldate.isNull()))
                .execute();

        Article article = queryFactory.selectFrom(article1)
                .where(article1.id.eq(articleDto.getArticleId())
                        .and(article1.deldate.isNull())
                )
                .fetchOne();

        return article;
    }

    @Override
    public Long updateLikeCnt(String option, Long articleId) throws Exception {
        long res = 0;
        QArticle article1 = new QArticle("article1");
        Integer before = queryFactory.select(article1.likeCount)
                .from(article1)
                .where(article1.id.eq(articleId))
                .fetchOne();
        switch (option) {
            case "+":
                res = queryFactory.update(article1)
                        .set(article1.likeCount, before+1)
                        .where(article1.id.eq(articleId))
                        .execute();
                break;
            case "-":
                res = queryFactory.update(article1)
                        .set(article1.likeCount, before-1)
                        .where(article1.id.eq(articleId))
                        .execute();
                break;
            default:
                throw new Exception("option error");
        }
        return res;
    }
}
