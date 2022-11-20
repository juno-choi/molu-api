package com.molu.molu.advice;

import com.molu.molu.domain.api.CommonError;
import com.molu.molu.domain.enums.api.ErrorCode;
import com.molu.molu.domain.error.Error;
import com.molu.molu.domain.error.MessageError;
import com.molu.molu.domain.error.ValidationError;
import com.molu.molu.exception.ListException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(basePackages = {"com.molu.molu"})
@Slf4j
public class CommonAdvice {

    @ExceptionHandler
    public ResponseEntity<CommonError> illegalArgumentException(IllegalArgumentException e){
        List<Error> errors = new ArrayList<>();
        errors.add(MessageError.builder()
                        .message(e.getMessage())
                        .build());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CommonError.builder()
                        .errorCode(ErrorCode.BAD_REQUEST.CODE)
                        .message(ErrorCode.BAD_REQUEST.MESSAGE)
                        .errors(errors)
                        .build()
        );
    }

    @ExceptionHandler
    public ResponseEntity<CommonError> listException(ListException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CommonError.builder()
                        .errorCode(ErrorCode.BAD_REQUEST.CODE)
                        .message("잘못된 요청. error field 값은 모두 snake 전략으로 변경하여 요청해주세요.")
                        .errors(e.getErrors())
                        .build()
        );
    }
}
