package com.molu.molu.domain.dto.board;

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
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long boardId;
    private String title;
    private String content;
    private String writer;
    private List<Comment> comments = new LinkedList<>();
    private long heart;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
}
