package com.iollshh.forum.controller;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.dto.Result;
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
    public ResponseEntity<Result> setNewMember(@RequestBody MemberDto member){
        Result result = memberService.setNewMember(member);

        //todo AOP
        if (result.getProcessResult().equals("success")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(200));
        } else if (result.getProcessResult().equals("fail")) {
            return new ResponseEntity<Result>(result, HttpStatus.valueOf(203));
        } else {
            return new ResponseEntity<Result>(HttpStatus.valueOf(204));
        }
    }

    //회원 탈퇴 처리

    //nickname 변경

    //회원 정보 확인 <= 필터에 구현
}
