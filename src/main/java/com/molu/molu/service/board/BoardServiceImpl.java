package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import com.molu.molu.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public PostBoardResponse postBoard(PostBoardRequest request) {
        Board board = new Board();
        board.createBoard(request);
        board.makeWriter();
        boardRepository.save(board);

        return PostBoardResponse.builder()
                .boardId(board.getId())
                .writer(board.getWriter())
                .build();
    }
}
