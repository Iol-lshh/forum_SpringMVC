package com.iollshh.forum.domain.dao;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.dto.Dto;
import com.iollshh.forum.domain.dto.ListDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.ArticleFactory;
import com.iollshh.forum.domain.repository.ArticleRepository;
import com.iollshh.forum.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ArticleDao {

    private final EntityManagerFactory emf;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    private final ArticleFactory articleFactory;

    //신규 생성
    @Transactional
    public ArticleDto uploadNewArticle(ArticleDto articleDto) throws Exception {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tr = em.getTransaction();

        Article newArticle;

        try {
            tr.begin();

            Member member = memberRepository.findOneByAccountId(articleDto.getWriterId());
            if (member == null) {
                throw new Exception("member is null");
            }

            newArticle = articleRepository.saveByMemberAndDto(member, articleDto);
            em.persist(newArticle);

            tr.commit();
            em.close();
        } catch (Exception e) {
            tr.rollback();
            em.close();
            throw new Exception("생성실패" + e);
        }
        ArticleDto resDto = articleFactory.makeDtoByEntity(newArticle);
        return resDto;
    }

    //단 건
    @Transactional
    public ArticleDto getArticle(Long articleId, String memberAccountId) {
        Article article = articleRepository.findOneByArticleId(articleId);
        ArticleDto resDto = articleFactory.makeDtoByEntity(article, memberAccountId);

        return resDto;
    }

    //복수
    //회원의 좋아요 포함
    @Transactional
    public ListDto getArticleList(int startIdx, int count, String memberAccountId) throws Exception {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tr = em.getTransaction();

        ListDto listDto;
        try {
            tr.begin();
            List<Article> articleList = articleRepository.findListByPagination(startIdx, count);
            List<Dto> list = articleList.stream()
                    .map(e -> articleFactory.makeDtoByEntity(e, memberAccountId))
                    .collect(Collectors.toList());
            listDto = new ListDto(list);
            tr.commit();
            em.close();
        } catch (Exception e) {
            tr.rollback();
            em.close();
            throw new Exception("리스트 생성 불가 "+e);
        }
        return listDto;
    }

    //삭제
    //좋아요 확인하여 같이 삭제
    @Transactional
    public ArticleDto deleteArticle(Long articleId, String memberAccountId) throws Exception {

        Article article = articleRepository.findOneByArticleId(articleId);
        if(article.getId() == null || !article.getMember().getAccountId().equals(memberAccountId) ){
            throw new Exception("유효하지 않은 요청");
        }
        Article res = articleRepository.deleteByArticleId(articleId);

        return articleFactory.makeDtoByEntity(res, memberAccountId);
    }

    @Transactional
    public ArticleDto updateArticle(ArticleDto articleDto, String memberAccountId) throws Exception {

        Member member = memberRepository.findOneByAccountId(memberAccountId);
        String validMemberId = articleRepository.findOneByArticleId(articleDto.getArticleId()).getMember().getAccountId();
        if(member.getAccountId().isEmpty() || !member.getAccountId().equals(validMemberId)){
            throw new Exception("유효하지 않은 요청");
        }
        Article res = articleRepository.updateByArticle(member, articleDto);

        return articleFactory.makeDtoByEntity(res, memberAccountId);
    }
}
