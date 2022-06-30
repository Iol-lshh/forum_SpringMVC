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
    public ResponseEntity<ResultDto> uploadNewArticle(@RequestBody ArticleDto article) {

        ResultDto result;
        try{
            result = ResultDto.builder()
                    .processResult("success")
                    .resultData(articleService.uploadNewArticle(article))
                    .build();
        }catch (Exception e){
            result = ResultDto.builder().processResult("fail").resultDetail(e.getMessage()).build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }

    //단 건 가져오기
    @GetMapping("/view/{articleId}/{memberAccountId}")
    public ResponseEntity<ResultDto> getArticle(@PathVariable String articleId, @PathVariable String memberAccountId) {

        ResultDto result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);

            try{
                result = ResultDto.builder()
                        .processResult("success")
                        .resultData(articleService.getArticle(parsedArticleId, memberAccountId))
                        .build();
            }catch(Exception e){
                result = ResultDto.builder().processResult("fail").resultDetail(e.getMessage()).build();
            }
        } else {
            result = ResultDto.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }

    //리스트 가져오기(페이징)
    @GetMapping("/list/{startIdx}/{count}/{memberAccountId}")
    public ResponseEntity<ResultDto> getArticleList(@PathVariable("startIdx") String startIdx, @PathVariable("count") String count, @PathVariable String memberAccountId) {

        ResultDto result;
        int parsedStartIdx;
        int parsedCount;

        if (!(startIdx.isEmpty())
                && startIdx.matches("[0-9]*")
                && !(count.isEmpty())
                && count.matches("[0-9]*")
        ) {
            parsedStartIdx = Integer.parseInt(startIdx);
            parsedCount = Integer.parseInt(count);

            try{
                result = ResultDto.builder()
                        .processResult("success")
                        .resultData(articleService.getArticleList(parsedStartIdx, parsedCount, memberAccountId))
                        .build();
            }catch (Exception e){
                result = ResultDto.builder().processResult("fail").resultDetail(e.getMessage()).build();
            }
        } else {
            result = ResultDto.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }

    //삭제
    @DeleteMapping("/{articleId}/{memberAccountId}")
    public ResponseEntity<ResultDto> deleteArticle(@PathVariable("articleId") String articleId, @PathVariable("memberAccountId") String memberAccountId) {
        ResultDto result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);

            try{
                String res = articleService.deleteArticle(parsedArticleId, memberAccountId);
                result = ResultDto.builder()
                        .processResult("success")
                        .resultDetail(res)
                        .build();
            }catch (Exception e){
                result = ResultDto.builder().processResult("fail").resultDetail(e.getMessage()).build();
            }
        } else {
            result = ResultDto.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }

    //수정
    @PutMapping("/{memberAccountId}")
    public ResponseEntity<ResultDto> updateArticle(@RequestBody ArticleDto article, @PathVariable String memberAccountId) {
        ResultDto result;

        try{
            String res = articleService.updateArticle(article, memberAccountId);

            result = ResultDto.builder()
                    .processResult("success")
                    .resultDetail(res)
                    .build();
        }catch (Exception e){
            result = ResultDto.builder().processResult("fail").resultDetail(e.getMessage()).build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }
}
