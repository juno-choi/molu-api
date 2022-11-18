package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.BoardRequest;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.vo.board.BoardResponse;
import com.molu.molu.repository.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 등록 성공")
    public void postBoardSuccess() throws Exception {
        //given
        String title = "제목";
        String content = "내용";
        BoardRequest boardRequest = new BoardRequest(title, content, null);
        //when
        BoardResponse boardResponse = boardService.postBoard(boardRequest);
        //then
        assertNotNull(boardResponse.getWriter());
        Board board = boardRepository.findById(boardResponse.getBoardId()).get();
        assertEquals(title, board.getTitle());
        assertEquals(content, board.getContent());
    }

}