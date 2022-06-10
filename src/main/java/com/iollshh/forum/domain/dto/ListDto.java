package com.iollshh.forum.domain.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class ListDto implements Dto{
    List<Dto> dtoList;
}

