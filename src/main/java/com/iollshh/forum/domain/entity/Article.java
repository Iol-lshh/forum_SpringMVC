package com.iollshh.forum.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    protected Long id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false)
    protected Member member;

    @Column(length=100, nullable = false)
    protected String title;

    @Column(columnDefinition = "TEXT")
    protected String content;

    @Column(nullable = false)
    protected Date regdate;

    @Column(name = "last_update", nullable = false)
    protected Date lastUpdate;

    protected Date deldate;

    @Column(name = "like_count")
    protected int likeCount;

    @OneToMany(mappedBy = "article")
    protected List<ArticleLike> articleLikeList= new ArrayList<ArticleLike>();

    @Builder
    public Article(Long id, Member member, String title, String content, Date regdate, Date lastUpdate, Date deldate, int likeCount) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.lastUpdate = lastUpdate;
        this.deldate = deldate;
        this.likeCount = likeCount;
    }
}
