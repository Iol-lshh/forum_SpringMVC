package com.iollshh.forum.domain.repository.decorator;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;

import java.util.List;

public interface ArticleRepositoryCustom {
    //save new Article
    String saveByArticleDto(ArticleDto articleDto);

    List<Article> findListByPagination(int startIdx, int count);

    String deleteByArticleId(Long articleId);

    Long updateLikeCnt(String option, Long articleId) throws Exception;

}
