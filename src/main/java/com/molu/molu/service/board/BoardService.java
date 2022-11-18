package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.vo.board.PostBoardResponse;

public interface BoardService {
    PostBoardResponse postBoard(PostBoardRequest request);
}
