package com.iollshh.forum.controller;

import com.iollshh.forum.domain.dto.*;
import com.iollshh.forum.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    //신규 글 등록
    @PostMapping("/new")
    public ResponseEntity<Result> uploadNewArticle(@RequestBody ArticleDto article) {

        Result result = articleService.uploadNewArticle(article);

        //todo AOP
        if (result.getProcessResult().equals("success")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(200));
        } else if (result.getProcessResult().equals("fail")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(203));
        } else {
            return new ResponseEntity<Result>(HttpStatus.valueOf(204));
        }
    }

    //단 건 가져오기
    @GetMapping("/view/{articleId}/{memberAccountId}")
    public ResponseEntity<Result> getArticle(@PathVariable String articleId, @PathVariable String memberAccountId) {

        Result result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);
            result = articleService.getArticle(parsedArticleId, memberAccountId);
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
            return new ResponseEntity<Result>(HttpStatus.valueOf(420));
        }
    }

    //리스트 가져오기(페이징)
    @GetMapping("/list/{startIdx}/{count}/{memberAccountId}")
    public ResponseEntity<Result> getArticleList(@PathVariable("startIdx") String startIdx, @PathVariable("count") String count, @PathVariable String memberAccountId) {

        Result result;
        int parsedStartIdx;
        int parsedCount;

        if (!(startIdx.isEmpty())
                && startIdx.matches("[0-9]*")
                && !(count.isEmpty())
                && count.matches("[0-9]*")
        ) {
            parsedStartIdx = Integer.parseInt(startIdx);
            parsedCount = Integer.parseInt(count);

            result = articleService.getArticleList(parsedStartIdx, parsedCount, memberAccountId);
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
            return new ResponseEntity<Result>(HttpStatus.valueOf(420));
        }
    }

    //삭제
    @DeleteMapping("/{articleId}/{memberAccountId}")
    public ResponseEntity<Result> deleteArticle(@PathVariable("articleId") String articleId, @PathVariable("memberAccountId") String memberAccountId) {

        Result result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);
            result = articleService.deleteArticle(parsedArticleId, memberAccountId);
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
            return new ResponseEntity<Result>(HttpStatus.valueOf(420));
        }
    }

    //수정
    @PutMapping("/{memberAccountId}")
    public ResponseEntity<Result> updateArticle(@RequestBody ArticleDto article, @PathVariable String memberAccountId) {

        Result result = articleService.updateArticle(article, memberAccountId);

        //todo AOP
        if (result.getProcessResult().equals("success")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(200));
        } else if (result.getProcessResult().equals("fail")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(203));
        } else {
            return new ResponseEntity<Result>(HttpStatus.valueOf(204));
        }
    }
}
