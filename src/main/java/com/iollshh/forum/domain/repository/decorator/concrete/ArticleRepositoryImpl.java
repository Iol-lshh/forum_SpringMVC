package com.iollshh.forum.domain.repository.decorator.concrete;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.entity.QArticle;
import com.iollshh.forum.domain.entity.QMember;
import com.iollshh.forum.domain.repository.ArticleRepository;
import com.iollshh.forum.domain.repository.decorator.ArticleRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public Article saveNewByArticleDto(ArticleDto articleDto) {

        Article resultArticle;

        //switch insert/update
        QMember qMember = new QMember("writer");

        Member member = queryFactory.selectFrom(qMember)
                .where(qMember.accountId.eq(articleDto.getWriterId())
                        .and(qMember.quit.ne("1")))
                .fetchOne();

        resultArticle = Article.builder()
                .member(member)
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .regdate(articleDto.getRegdate())
                .lastUpdate(articleDto.getRegdate())
                .likeCount(0)
                .build();

        em.persist(resultArticle);
        em.flush();
        return resultArticle;
    }

    @Override
    public List<Article> getListByPagination(int startIdx, int count) {
        QArticle article1 =  new QArticle("article1");

        List<Article> articleList = queryFactory.selectFrom(article1)
                .orderBy(article1.id.asc())
                .offset(startIdx)
                .limit(count)
                .fetch();

        return articleList;
    }

    @Override
    public String deleteSoft(Article article) {
        //시간
        Date regdate = new Date();
        article.setDeldate(regdate);
        em.merge(article);
        em.flush();

        return "success";
    }
}
