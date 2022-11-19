package com.molu.molu.domain.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MessageError extends Error{
    private String message;
}
