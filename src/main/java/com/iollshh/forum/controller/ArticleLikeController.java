package com.iollshh.forum.controller;

import com.iollshh.forum.domain.dto.Result;
import com.iollshh.forum.service.ArticleLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/like")
@RestController
public class ArticleLikeController {
    private final ArticleLikeService likeService;

    //좋아요 
    // => 토글보단 컨트롤러단 분리가 나은 듯 하다.. 
    // => 분리시, 이미 존재 여부에 대해, 유효성 검증이 필요 없다!
    // => articleid와 memberAccountId 에 대해, unique이기 때문
    @GetMapping("/{articleId}/{memberAccountId}")
    public ResponseEntity<Result> createLike(@PathVariable("memberAccountId") String memberAccountId, @PathVariable("articleId") String articleId) {

        Result result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);
            result = likeService.createLike(memberAccountId, parsedArticleId);
        } else {
            result = Result.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        //todo AOP
        if (result.getProcessResult().equals("success")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(200));
        } else if (result.getProcessResult().equals("null")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(204));
        } else if (result.getProcessResult().equals("fail")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(203));
        } else {
            return new ResponseEntity<Result>(HttpStatus.valueOf(203));
        }
    }

    //좋아요 취소
    @DeleteMapping("/{articleId}/{memberAccountId}")
    public ResponseEntity<Result> deleteLike(@PathVariable("memberAccountId") String memberAccountId, @PathVariable("articleId") String articleId) {

        Result result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);
            result = likeService.deleteLike(memberAccountId, parsedArticleId);
        } else {
            result = Result.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        //todo AOP
        if (result.getProcessResult().equals("success")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(200));
        } else if (result.getProcessResult().equals("null")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(204));
        } else if (result.getProcessResult().equals("fail")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(203));
        } else {
            return new ResponseEntity<Result>(HttpStatus.valueOf(203));
        }
    }

    //글의 좋아요 리스트
    @GetMapping("/list/{articleId}")
    public ResponseEntity<Result> getArticleList(@PathVariable("articleId") String articleId) {

        Result result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);
            result = likeService.getListByArticleId(parsedArticleId);
        } else {
            result = Result.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        //todo AOP
        if (result.getProcessResult().equals("success")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(200));
        } else if (result.getProcessResult().equals("null")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(204));
        } else if (result.getProcessResult().equals("fail")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(203));
        } else {
            return new ResponseEntity<Result>(HttpStatus.valueOf(203));
        }
    }
}
