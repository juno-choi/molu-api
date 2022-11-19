package com.molu.molu.exception;

import com.molu.molu.domain.api.CommonError;
import com.molu.molu.domain.enums.api.ErrorCode;
import com.molu.molu.domain.error.MessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.molu.molu"})
@Slf4j
public class CommonAdvice {

    @ExceptionHandler
    public ResponseEntity<CommonError> illegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CommonError.builder()
                        .errorCode(ErrorCode.BAD_REQUEST.CODE)
                        .message(ErrorCode.BAD_REQUEST.MESSAGE)
                        .error(MessageError.builder()
                                .message(e.getMessage())
                                .build())
                        .build()
        );
    }
}
