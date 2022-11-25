package com.molu.molu.domain.dto.board;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.molu.molu.domain.entity.board.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
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
    private List<Comment> comments;

    public BoardDto(Long boardId, String title, String content, String writer, long commentCount, long heart, LocalDateTime modifiedAt, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.commentCount = commentCount;
        this.heart = heart;
        this.modifiedAt = modifiedAt;
        this.createdAt = createdAt;
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

}
