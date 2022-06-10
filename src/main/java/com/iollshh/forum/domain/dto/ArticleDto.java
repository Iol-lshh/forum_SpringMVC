package com.iollshh.forum.domain.dto;

import com.iollshh.forum.domain.vo.ArticleVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@AllArgsConstructor
@Data
public class ArticleDto implements Dto{
    Long articleId;
    String writerId; //accountId
    String title;
    String content;
    Date regdate;
    Date lastUpdate;
    Date deldate;
    int likeCount;

    boolean isLike;

    public ArticleDto(ArticleVo vo){
        this.articleId = vo.getId();
        this.writerId = vo.getWriterAccountId();
        this.title = vo.getTitle();
        this.content = vo.getContent();
        this.regdate = vo.getRegdate();
        this.lastUpdate = vo.getLastUpdate();
        this.deldate = vo.getDeldate();
        this.likeCount = vo.getLikeCount();
        this.isLike = vo.getIsLike();
    }

}
