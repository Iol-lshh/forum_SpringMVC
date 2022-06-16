package com.iollshh.forum.domain.dao;

import com.iollshh.forum.domain.dto.ArticleLikeDto;
import com.iollshh.forum.domain.dto.Dto;
import com.iollshh.forum.domain.dto.ListDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.ArticleLike;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.ArticleLikeFactory;
import com.iollshh.forum.domain.repository.ArticleLikeRepository;
import com.iollshh.forum.domain.repository.ArticleRepository;
import com.iollshh.forum.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ArticleLikeDao {

    @PersistenceContext
    private EntityManager em;
    private final ArticleLikeRepository articleLikeRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final ArticleLikeFactory articleLikeFactory;

    //좋아요
    @Transactional
    public ArticleLikeDto createLike(String memberAccountId, Long articleId) throws Exception {

        EntityTransaction tr = em.getTransaction();

        ArticleLike newArticleLike;
        try {
            tr.begin();
            if(articleLikeRepository.existsByInform(memberAccountId, articleId)){
                throw new Exception("이미 좋아합니다.");
            }

            Member member = memberRepository.findOneByAccountId(memberAccountId);
            if (member == null || member.getAccountId() == null) {
                throw new Exception("no member");
            }

            Article article = articleRepository.findOneByArticleId(articleId);
            if (article == null || article.getId() == null) {
                throw new Exception("no article");
            }

            newArticleLike = articleLikeRepository.saveByLikeInform(member, article);
            em.persist(newArticleLike);

            tr.commit();
            articleRepository.updateLikeCnt("+", articleId);

            em.close();
        }catch (Exception e){
            tr.rollback();
            em.close();
            throw e;
        }

        return articleLikeFactory.makeDtoByEntity(newArticleLike);
    }

    @Transactional
    public Long deleteLike(String memberAccountId, Long articleId) throws Exception{

        if(!articleLikeRepository.existsByInform(memberAccountId, articleId)){
            throw new Exception("이미 좋아하지 않습니다.");
        }
        Long res = articleLikeRepository.deleteByLikeInform(memberAccountId, articleId);
        articleRepository.updateLikeCnt("-", articleId);

        return res;
    }

    @Transactional
    public ListDto getListByArticleId(Long articleId) throws Exception {
        ListDto listDto = null;

        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            List<ArticleLike> articleLikeList = articleLikeRepository.findListByArticleId(articleId);
            List<Dto> list = articleLikeList.stream().map(e->articleLikeFactory.makeDtoByEntity(e)).collect(Collectors.toList());
            listDto = new ListDto(list);
        } catch (Exception e) {
            tr.rollback();
            em.close();
            throw new Exception("리스트 생성 불가 "+e);
        }
        return listDto;
    }



}
