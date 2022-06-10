package com.iollshh.forum.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result implements Dto{
    String processResult="";
    String resultDetail="";
    Dto resultData;

    public Result(String processResult, String resultDetail) {
        this.processResult=processResult;
        this.resultDetail=resultDetail;
    }
}
