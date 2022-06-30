package com.iollshh.forum.controller;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.dto.ResultDto;
import com.iollshh.forum.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    //회원정보 동기화를 위한 REST API 인터페이스
    @PostMapping("/new")
    public ResponseEntity<ResultDto> setNewMember(@RequestBody MemberDto member){
        ResultDto result;

        try {
            result = ResultDto.builder()
                                .processResult("success")
                                .resultData(memberService.setNewMember(member))
                                .build();
        } catch (Exception e) {
            result = ResultDto.builder()
                    .processResult("fail")
                    .resultDetail(e.getMessage())
                    .build();
        }

        return new ResponseEntity<ResultDto>(result, HttpStatus.OK);
    }

    //회원 탈퇴 처리

    //nickname 변경

    //회원 정보 확인 <= 필터에 구현
}
