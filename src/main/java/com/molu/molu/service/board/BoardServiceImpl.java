package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.vo.board.GetBoardResponse;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import com.molu.molu.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
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
    public PostBoardResponse postBoard(PostBoardRequest request) {
        Board board = Board.createBoard(request);
        boardRepository.save(board);

        return PostBoardResponse.builder()
                .boardId(board.getId())
                .writer(board.getWriter())
                .build();
    }

    @Override
    public GetBoardResponse getBoard(Pageable page) {
        Page<Board> findAll = getBoardPage(page);
        return GetBoardResponse.builder()
                .last(findAll.isLast())
                .empty(findAll.isEmpty())
                .totalPage(findAll.getTotalPages())
                .totalElements(findAll.getTotalElements())
                .boardList(findAll.getContent())
                .numberOfElements(findAll.getNumberOfElements())
                .build();
    }

    private Page<Board> getBoardPage(Pageable page) {
        Page<Board> findAll;
        try{
            findAll = boardRepository.findAll(page);
        }catch (PropertyReferenceException e){
            throw new IllegalArgumentException(String.format("잘못된 요청 값 = %s", e.getPropertyName()));
        }
        return findAll;
    }
}
