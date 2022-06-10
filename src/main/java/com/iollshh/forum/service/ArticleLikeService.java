package com.iollshh.forum.service;


import com.iollshh.forum.domain.dao.ArticleLikeDao;
import com.iollshh.forum.domain.dto.ArticleLikeDto;
import com.iollshh.forum.domain.dto.ListDto;
import com.iollshh.forum.domain.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ArticleLikeService implements BusinessLogic{

    private final ArticleLikeDao articleLikeDao;

    //좋아요
    public Result createLike(String memberAccountId, Long articleId) {
        Result result = new Result();
        try{
            ArticleLikeDto res = articleLikeDao.createLike(memberAccountId, articleId);

//            result.setResultData(res);
            result.setProcessResult("success");
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }


    public Result deleteLike(String memberAccountId, Long articleId) {
        Result result = new Result();
        try{
            Long res = articleLikeDao.deleteLike(memberAccountId, articleId);

            if(res == 1){
                result.setProcessResult("success");
            }else{
                result.setProcessResult("fail");
                result.setResultDetail("중복된 요청");
            }
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }


    public Result getListByArticleId(Long articleId) {
        Result result = new Result();
        try {
            ListDto listDto = articleLikeDao.getListByArticleId(articleId);

            result.setResultData(listDto);
            result.setProcessResult("success");
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }

    //삭제된 글의 좋아요 데이터는 삭제되지 않는다.
    //멤버로 좋아요 조회 기능 등을 구현시 주의
}
