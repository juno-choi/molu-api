package com.molu.molu.exception;

import com.molu.molu.domain.error.Error;
import lombok.Getter;

import java.util.List;

@Getter
public class ListException extends RuntimeException{
    private List<Error> errors;

    public ListException(String message, List<Error> errors) {
        super(message);
        this.errors = errors;
    }
}
