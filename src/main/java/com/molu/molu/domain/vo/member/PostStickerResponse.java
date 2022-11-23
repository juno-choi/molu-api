package com.molu.molu.domain.vo.member;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostStickerResponse {
    private Long stickerId;
    private String toMemberName;
    private int ea;
    private String reason;
}
