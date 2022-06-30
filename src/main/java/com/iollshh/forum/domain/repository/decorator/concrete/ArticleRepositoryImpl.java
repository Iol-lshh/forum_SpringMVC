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
    public String saveByArticleDto(ArticleDto articleDto) {

        Article resultArticle;

        //switch insert/update
        if(articleDto.getArticleId()==null) {//insert
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
            return resultArticle.getId().toString();
        }else{ //update
            resultArticle = Article.builder()
                    .id(articleDto.getArticleId())
                    .title(articleDto.getTitle())
                    .content(articleDto.getContent())
                    .lastUpdate(articleDto.getLastUpdate())
                    .build();
            em.merge(resultArticle);
            return "success";
        }
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
    public String deleteByArticleId(Long articleId) {
        //시간
        Date regdate = new Date();
        QArticle article1 = new QArticle("article1");

        queryFactory.update(article1)
                .set(article1.deldate, regdate)
                .where(article1.id.eq(articleId))
                .execute();

        return "success";
    }

    @Override
    public Long updateLikeCnt(String option, Long articleId) throws Exception {
        long res;
        QArticle article1 = new QArticle("article1");
        Integer _before = queryFactory.select(article1.likeCount)
                .from(article1)
                .where(article1.id.eq(articleId))
                .fetchOne();
        int before = _before==null?0:_before;
        switch (option) {
            case "+":
                res = queryFactory.update(article1)
                        .set(article1.likeCount, before + 1)
                        .where(article1.id.eq(articleId))
                        .execute();
                break;
            case "-":
                res = queryFactory.update(article1)
                        .set(article1.likeCount, before -1)
                        .where(article1.id.eq(articleId))
                        .execute();
                break;
            default:
                throw new Exception("option error");
        }
        return res;
    }
}
