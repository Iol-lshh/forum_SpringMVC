package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.repository.decorator.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
}