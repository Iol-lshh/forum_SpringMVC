package com.iollshh.forum.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    protected Long id;

    @Column(length = 20, nullable = false)
    protected String nickname;

    @Column(name = "account_type", length = 8, nullable = false)
    protected String accountType;
    //LESSOR : 임대인, REALTOR: 공인중개사, LESSEE: 임차인

    @Column(name = "account_id", length = 50, nullable = false, unique = true)
    protected String accountId;

    @Column(length = 1)
    protected String quit;

    @OneToMany(mappedBy = "member")
    private List<Article> article = new ArrayList<Article>();

    @OneToMany(mappedBy = "member")
    private List<ArticleLike> articleLikeList = new ArrayList<ArticleLike>();

    @Builder
    public Member(Long id, String nickname, String accountType, String accountId, String quit) {
        this.id = id;
        this.nickname = nickname;
        this.accountType = accountType;
        this.accountId = accountId;
        this.quit = quit;
    }
}
