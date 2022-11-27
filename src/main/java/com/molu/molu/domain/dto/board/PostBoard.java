package com.molu.molu.domain.dto.board;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostBoard {

    @NotEmpty(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 100, message = "제목은 1~100 글자 입니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(min = 1, max = 3000, message = "내용은 3000자까지 입력이 가능합니다!")
    private String content;
}
