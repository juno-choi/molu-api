package com.molu.molu.repository.board;

import com.molu.molu.domain.dto.board.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<BoardDto> findBoardPage(Pageable pageable);
}
