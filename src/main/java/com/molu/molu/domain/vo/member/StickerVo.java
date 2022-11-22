package com.molu.molu.domain.vo.member;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StickerVo {
    private Long stickerId;
    private Long fromMemberId;
    private String fromMemberName;
    private String reason;
    private int ea;
    private LocalDateTime createdAt;
}
