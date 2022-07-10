package com.iollshh.forum.service;

import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.dto.Dto;
import com.iollshh.forum.domain.dto.ListDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.ArticleFactory;
import com.iollshh.forum.domain.repository.ArticleRepository;
import com.iollshh.forum.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleService{
    private final ArticleFactory articleFactory;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    //신규 생성
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public ArticleDto uploadNewArticle(ArticleDto requestDto) throws Exception{
        //시간
        Date regdate = new Date();
        requestDto.setRegdate(regdate);
        requestDto.setLastUpdate(regdate);

        Member member = memberRepository.getReferenceByAccountId(requestDto.getWriterId());
        if (member == null) {
            throw new Exception("member is null");
        }

        return articleFactory.makeDtoByEntity(articleRepository.saveNewByArticleDto(requestDto));
    }

    //단 건
    //회원의 좋아요 포함
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ArticleDto getArticle(Long articleId, String memberAccountId) throws Exception {

        Article article = articleRepository.getReferenceById(articleId);
        ArticleDto resultDto = articleFactory.makeDtoByEntity(article, memberAccountId);

        return resultDto;
    }

    //복수
    //회원의 좋아요 포함
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ListDto getArticleList(int startIdx, int count, String memberAccountId) throws Exception {

        ListDto listDto;

        List<Article> articleList = articleRepository.getListByPagination(startIdx, count);
        List<Dto> list = articleList.stream()
                .map(e -> articleFactory.makeDtoByEntity(e, memberAccountId))
                .collect(Collectors.toList());
        listDto = new ListDto(list);

        return listDto;
    }

    //삭제
    //좋아요 확인하여 같이 삭제
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public String deleteArticle(Long articleId, String memberAccountId) throws Exception{

        Article article = articleRepository.getReferenceById(articleId);

        if(
                article.getId() == null
                || !article.getMember().getAccountId().equals(memberAccountId)
        ){
            return "fail";
        }
        articleRepository.deleteSoft(article);

        return "success";
    }

    //수정
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public String updateArticle(ArticleDto articleDto, String memberAccountId) throws Exception{
        //유효성 검사
        Member member = memberRepository.getReferenceByAccountId(memberAccountId);
        Article article = articleRepository.getReferenceById(articleDto.getArticleId());
        if( 
                member.getAccountId().isEmpty() //회원 검증
                && !member.getAccountId().equals(article.getMember().getAccountId()) //글 작성자 검증
        ){
            return "fail";
        }

        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setLastUpdate(new Date());
        articleRepository.save(article);

        return "success";
    }
}
