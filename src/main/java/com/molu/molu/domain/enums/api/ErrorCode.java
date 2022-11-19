package com.molu.molu.domain.enums.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    FAIL("9000", "요청 실패"),
    BAD_REQUEST("9400", "잘못된 요청"),
    ;
    public String CODE;
    public String MESSAGE;
}
