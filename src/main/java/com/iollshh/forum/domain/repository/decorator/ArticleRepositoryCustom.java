package com.iollshh.forum.domain.repository.decorator;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;

import java.util.List;

public interface ArticleRepositoryCustom {
    //save new Article
    Article saveByMemberAndDto(Member member, ArticleDto articleDto);

    Article findOneByArticleId(Long articleId);

    List<Article> findListByPagination(int startIdx, int count);

    Article deleteByArticleId(Long articleId);

    Article updateByArticle(Member member, ArticleDto articleDto);

    Long updateLikeCnt(String option, Long articleId) throws Exception;

}
