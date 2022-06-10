package com.iollshh.forum.domain.dto;

import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.vo.ArticleVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class ArticleLikeDto implements Dto{
    Long likeId;
    Long articleId;
    String memberAccountId;
}
