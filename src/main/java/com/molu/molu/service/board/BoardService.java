package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PatchBoardRequest;
import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.vo.board.AddHeartResponse;
import com.molu.molu.domain.vo.board.GetBoardResponse;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    PostBoardResponse postBoard(PostBoardRequest request);
    GetBoardResponse getBoard(Pageable page);
    AddHeartResponse addHeader(PatchBoardRequest request);
}
