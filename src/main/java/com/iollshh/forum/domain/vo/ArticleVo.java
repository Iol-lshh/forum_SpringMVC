package com.iollshh.forum.domain.vo;

import com.iollshh.forum.domain.entity.Article;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class ArticleVo implements ReadOnlyVo {  //어댑터

    private Article entity;

    @Override
    public Article getEntity() {
        return this.entity;
    }

    public Long getId() {
        Long id = this.entity.getId();
        return id;
    }

    public Long getWriterId() {
        Long writerId = this.entity.getMember().getId();
        return writerId;
    }

    public String getWriterAccountId() {
        String writerId = this.entity.getMember().getAccountId();
        return writerId;
    }

    public String getWriterNickName() {
        String writerNickName = this.entity.getMember().getNickname();
        return writerNickName;
    }

    public String getTitle() {
        String title = this.entity.getTitle();
        return title;
    }

    public String getContent() {
        String content = this.entity.getContent();
        return content;
    }

    public Date getRegdate() {
        Date regdate = this.entity.getRegdate();
        return regdate;
    }

    public Date getLastUpdate() {
        Date lastUpdate = this.entity.getLastUpdate();
        return lastUpdate;
    }

    public Date getDeldate() {
        Date deldate = this.entity.getDeldate();
        return deldate;
    }

    public int getLikeCount() {
        int likeCount = this.entity.getLikeCount();
        return likeCount;
    }

    public boolean getIsLike() {
        Long count = this.entity.getArticleLikeList().stream().filter(
                e -> (e.getMember().getId()).equals(getWriterId())
        ).count();
        boolean isLike = count > 0;
        return isLike;
    }


}


