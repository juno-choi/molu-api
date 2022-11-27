package com.molu.molu.domain.vo.board;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostCommentResponse {
    private Long commentId;
    private Long memberId;
    private Long boardId;
    private String comment;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
}
