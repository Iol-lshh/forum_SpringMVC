package com.iollshh.forum.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "article_like", uniqueConstraints = {@UniqueConstraint(
        name="LIKE_UNIQUE",
        columnNames = {"member_id","article_id"}
)})
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    protected Long id;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Member member;

    @ManyToOne(targetEntity = Article.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Article article;

    @Builder
    ArticleLike(Member member, Article article ){
        this.member = member;
        this.article = article;
    }

}
