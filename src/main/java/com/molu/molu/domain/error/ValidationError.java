package com.molu.molu.domain.error;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class ValidationError extends Error{
    private String field;
    private String message;
}
