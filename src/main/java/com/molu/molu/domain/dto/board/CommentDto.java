package com.molu.molu.domain.dto.board;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommentDto {
    private Long commentId;
    private Long memberId;
    private Long boardId;
    private String comment;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
}
