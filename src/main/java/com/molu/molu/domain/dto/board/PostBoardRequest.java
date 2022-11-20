package com.molu.molu.domain.dto.board;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostBoardRequest {

    @NotEmpty(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 30, message = "제목은 1~30 글자 입니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(min=1, max = 3000, message = "내용이 너무 깁니다!")
    private String content;
}
