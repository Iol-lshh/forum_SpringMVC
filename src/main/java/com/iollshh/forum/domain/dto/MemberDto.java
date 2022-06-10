package com.iollshh.forum.domain.dto;

import lombok.*;

@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class MemberDto implements Dto{
    String accountId;
    String nickname;
    String accountType;
    String quit;    //다른 상태를 대비하여, 문자열로 지정
}
