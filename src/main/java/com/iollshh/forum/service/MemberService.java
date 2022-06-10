package com.iollshh.forum.service;

import com.iollshh.forum.domain.dao.MemberDao;
import com.iollshh.forum.domain.dto.ArticleDto;
import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberDao memberDao;

    //회원정보 동기화를 위한 REST API 인터페이스
    public Result setNewMember(MemberDto memberDto) {
        Result result = new Result();

        try{
            memberDto = memberDao.uploadNewMember(memberDto);

            result.setResultData(memberDto);
            result.setProcessResult("success");
        }catch(Exception e){
            result.setProcessResult("fail");
            result.setResultDetail(result.getResultDetail()+e);
        }
        return result;
    }

    //회원 탈퇴 처리
    //게시글 전부 비활성화
    //좋아요 삭제

    //nickname 변경


    //회원 정보 확인 <= 필터에 구현
    public Result findMember(String accountId){
        Result result = new Result();
        try {
            MemberDto memberDto = memberDao.getMember(accountId);
            if(memberDto.getQuit().equals("0")) throw new Exception("탈퇴회원");
            result.setResultData(memberDto);
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
}
