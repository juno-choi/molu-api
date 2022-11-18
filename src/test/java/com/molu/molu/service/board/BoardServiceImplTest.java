package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.vo.board.PostBoardResponse;
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
        PostBoardRequest postBoardRequest = new PostBoardRequest(title, content, null);
        //when
        PostBoardResponse postBoardResponse = boardService.postBoard(postBoardRequest);
        //then
        assertNotNull(postBoardResponse.getWriter());
        Board board = boardRepository.findById(postBoardResponse.getBoardId()).get();
        assertEquals(title, board.getTitle());
        assertEquals(content, board.getContent());
    }

}