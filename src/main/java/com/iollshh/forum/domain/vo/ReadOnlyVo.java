package com.iollshh.forum.domain.vo;

import com.iollshh.forum.domain.dto.Dto;

public interface ReadOnlyVo extends Dto {
    <S>S getEntity();
}
