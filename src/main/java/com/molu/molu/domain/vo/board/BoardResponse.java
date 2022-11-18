package com.molu.molu.domain.vo.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardResponse {
    private Long boardId;
    private String writer;
}
