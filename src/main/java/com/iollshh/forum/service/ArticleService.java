package com.iollshh.forum.service;

import com.iollshh.forum.domain.dao.ArticleDao;
import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.dto.ListDto;
import com.iollshh.forum.domain.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class ArticleService{

    private final ArticleDao articleDao;

    //신규 생성
    public Result uploadNewArticle(ArticleDto articleDto){
        Result result = new Result();

        //시간
        Date regdate = new Date();
        articleDto.setRegdate(regdate);
        articleDto.setLastUpdate(regdate);
        try{
            articleDto = articleDao.uploadNewArticle(articleDto);

            result.setResultData(articleDto);
            result.setProcessResult("success");
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }

    //단 건
    //회원의 좋아요 포함
    public Result getArticle(Long articleId, String memberAccountId) {
        Result result = new Result();
        try {
            ArticleDto articleDto = articleDao.getArticle(articleId, memberAccountId);

            result.setResultData(articleDto);
            result.setProcessResult("success");
        }catch (NullPointerException e) {
            result.setProcessResult("null");
            result.setResultDetail("데이터가 없습니다."+e);
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }

    //복수
    //회원의 좋아요 포함
    public Result getArticleList(int startIdx, int count, String memberAccountId) {
        Result result = new Result();
        try {
            ListDto listDto = articleDao.getArticleList(startIdx, count, memberAccountId);

            result.setResultData(listDto);
            result.setProcessResult("success");
        }catch (NullPointerException e) {
            result.setProcessResult("null");
            result.setResultDetail("데이터가 없습니다."+e);
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }

    //삭제
    //좋아요 확인하여 같이 삭제
    public Result deleteArticle(Long articleId, String memberAccountId) {
        Result result = new Result();
        try {
            ArticleDto articleDto = articleDao.deleteArticle(articleId, memberAccountId);

            result.setResultData(articleDto);
            result.setProcessResult("success");
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }

    //수정
    public Result updateArticle(ArticleDto articleDto, String memeberAccountId) {
        Result result = new Result();
        //시간
        Date regdate = new Date();
        articleDto.setLastUpdate(regdate);
        try {
            ArticleDto res = articleDao.updateArticle(articleDto, memeberAccountId);

            result.setResultData(res);
            result.setProcessResult("success");
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }
}
