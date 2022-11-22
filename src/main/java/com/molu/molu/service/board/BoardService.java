package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PatchBoard;
import com.molu.molu.domain.dto.board.PostBoard;
import com.molu.molu.domain.vo.board.AddHeartResponse;
import com.molu.molu.domain.vo.board.GetBoardResponse;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    PostBoardResponse postBoard(PostBoard request);
    GetBoardResponse getBoard(Pageable page);
    AddHeartResponse addHeader(PatchBoard request);
}
