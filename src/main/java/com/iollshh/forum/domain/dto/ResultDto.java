package com.iollshh.forum.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
//@NoArgsConstructor //테뉴언트 영역으로 들어가지 않도록, 미리 생성하지 않는다.
@AllArgsConstructor
@Data
public class ResultDto implements Dto{
    String processResult;
    String resultDetail;
    Dto resultData;
}
