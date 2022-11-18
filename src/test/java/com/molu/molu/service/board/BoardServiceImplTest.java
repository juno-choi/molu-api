package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.vo.board.GetBoardResponse;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import com.molu.molu.repository.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 등록 성공한다.")
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

    @Test
    @DisplayName("게시글 페이징 성공한다.")
    void getBoardSuccess() throws Exception {
        //given
        for(int i=0; i<20; i++){
            PostBoardRequest postBoardRequest = new PostBoardRequest("제목" + i, "내용" + i, null);
            boardRepository.save(Board.createBoard(postBoardRequest));
        }

        Pageable pageable = Pageable.ofSize(5);
        pageable = pageable.next();
        pageable = pageable.next();

        //when
        GetBoardResponse board = boardService.getBoard(pageable);

        //then
        Stream<String> title = board.getBoardList().stream().filter(b -> b.getContent().equals("내용10")).map(b -> b.getTitle());
        assertTrue(title.count() == 1);
    }
}