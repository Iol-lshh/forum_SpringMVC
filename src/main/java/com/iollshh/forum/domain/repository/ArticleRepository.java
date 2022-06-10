package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, CustomArticleRepository {
}