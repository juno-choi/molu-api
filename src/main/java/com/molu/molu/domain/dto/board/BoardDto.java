package com.molu.molu.domain.dto.board;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;
    private String writer;
    private long commentCount;
    private long heart;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
}
