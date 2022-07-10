package com.iollshh.forum.domain.repository.decorator;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;

import java.util.List;

public interface ArticleRepositoryCustom {
    //save new Article
    Article saveNewByArticleDto(ArticleDto articleDto);

    List<Article> getListByPagination(int startIdx, int count);

    String deleteSoft(Article article);
}
