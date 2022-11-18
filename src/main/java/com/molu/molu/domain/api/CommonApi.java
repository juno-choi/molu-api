package com.molu.molu.domain.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.molu.molu.domain.enums.api.ResultCode;
import com.molu.molu.domain.enums.api.ResultType;
import lombok.Builder;
import lombok.Getter;

@Getter

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommonApi<T> {
    private ResultCode resultCode;
    private ResultType resultType;
    private String resultMessage;
    private T data;

    public CommonApi(ResultCode resultCode, ResultType resultType, String resultMessage, T data) {
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.resultMessage = resultMessage;
        this.data = data;
    }

    @Builder
    public CommonApi(ResultCode resultCode, ResultType resultType, T data) {
        this.resultCode = resultCode;
        this.resultType = resultType;
        this.resultMessage = resultCode.MESSAGE;
        this.data = data;
    }
}
