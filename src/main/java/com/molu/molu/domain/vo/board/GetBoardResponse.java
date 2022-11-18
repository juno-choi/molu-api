package com.molu.molu.domain.vo.board;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.molu.molu.domain.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetBoardResponse {
    private int totalPage;
    private long totalElements;
    private int numberOfElements;
    private Boolean last;
    private Boolean empty;
    private List<Board> boardList;
}
