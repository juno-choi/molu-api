package com.molu.molu.domain.dto.board;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostComment {
    @NotEmpty(message = "게시판 번호는 필수값 입니다.")
    private Long boardId;
    @NotEmpty(message = "댓글 내용은 비어있을 수 없습니다.")
    @Size(min = 1, max = 500, message = "댓글은 500자까지 입력이 가능합니다!")
    private String comment;
}
