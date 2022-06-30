package com.iollshh.forum.controller;

import com.iollshh.forum.domain.dto.ResultDto;
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
    @PutMapping("/{articleId}/{memberAccountId}")
    public ResponseEntity<ResultDto> createLike(@PathVariable("memberAccountId") String memberAccountId, @PathVariable("articleId") String articleId) {

        ResultDto result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);

            try {
                likeService.createLike(memberAccountId, parsedArticleId);
                result = ResultDto.builder()
                            .processResult("success")
                            .build();
            } catch (Exception e) {
                result = ResultDto.builder().processResult("fail").resultDetail(e.getMessage()).build();
            }
        }else {
            result = ResultDto.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }

    //좋아요 취소
    @DeleteMapping("/{articleId}/{memberAccountId}")
    public ResponseEntity<ResultDto> deleteLike(@PathVariable("memberAccountId") String memberAccountId, @PathVariable("articleId") String articleId) {

        ResultDto result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);

            try {
                likeService.deleteLike(memberAccountId, parsedArticleId);
                result = ResultDto.builder()
                            .processResult("success")
                            .build();
            } catch (Exception e) {
                result = ResultDto.builder().processResult("fail").resultDetail(e.getMessage()).build();
            }
        } else {
            result = ResultDto.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }

    //글의 좋아요 리스트
    @GetMapping("/list/{articleId}")
    public ResponseEntity<ResultDto> getArticleList(@PathVariable("articleId") String articleId) {

        ResultDto result;
        Long parsedArticleId;

        if (!(articleId.isEmpty())
                && articleId.matches("[0-9]*")) {
            parsedArticleId = Long.parseLong(articleId);
            try {
                result = ResultDto.builder()
                            .processResult("success")
                            .resultData(likeService.getListByArticleId(parsedArticleId))
                            .build();
            } catch (Exception e) {
                result = ResultDto.builder().processResult("fail").resultDetail(e.getMessage()).build();
            }
        } else {
            result = ResultDto.builder().processResult("fail").resultDetail("잘못된 데이터 형식").build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }
}
