package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.BoardRequest;
import com.molu.molu.domain.vo.board.BoardResponse;

public interface BoardService {
    BoardResponse postBoard(BoardRequest request);
}
