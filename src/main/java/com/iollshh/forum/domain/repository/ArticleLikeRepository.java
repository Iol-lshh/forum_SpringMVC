package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long>, CustomArticleLikeRepository {
}