package com.molu.molu.domain.enums.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultCode {
    SUCCESS("0000", "정상 처리"),
    ;
    public String CODE;
    public String MESSAGE;
}
