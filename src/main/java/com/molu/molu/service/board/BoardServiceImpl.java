package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.BoardDto;
import com.molu.molu.domain.dto.board.PatchBoard;
import com.molu.molu.domain.dto.board.PostBoard;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.vo.board.AddHeartResponse;
import com.molu.molu.domain.vo.board.GetBoardResponse;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import com.molu.molu.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public PostBoardResponse postBoard(PostBoard request) {
        Board board = Board.of(request);
        boardRepository.save(board);

        return PostBoardResponse.builder()
                .boardId(board.getBoardId())
                .writer(board.getWriter())
                .build();
    }

    @Override
    public GetBoardResponse getBoard(Pageable page) {
        Page<BoardDto> findAll = getBoardPage(page);
        return GetBoardResponse.builder()
                .last(findAll.isLast())
                .empty(findAll.isEmpty())
                .totalPage(findAll.getTotalPages())
                .totalElements(findAll.getTotalElements())
                .boardList(findAll.getContent())
                .numberOfElements(findAll.getNumberOfElements())
                .build();
    }

    @Override
    @Transactional
    public AddHeartResponse addHeader(PatchBoard request) {
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> {
            throw new IllegalArgumentException("유효 하지 않은 게시판 번호입니다.");
        });
        board.addHeart();

        return AddHeartResponse.builder()
                .boardId(board.getBoardId())
                .heart(board.getHeart())
                .build();
    }

    private Page<BoardDto> getBoardPage(Pageable page) {
        return boardRepository.findBoardPage(page);
    }
}
