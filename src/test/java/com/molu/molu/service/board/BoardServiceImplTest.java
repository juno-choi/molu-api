package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PatchBoardRequest;
import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.vo.board.AddHeartResponse;
import com.molu.molu.domain.vo.board.GetBoardResponse;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import com.molu.molu.repository.board.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
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
        PostBoardRequest postBoardRequest = new PostBoardRequest(title, content);
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
            PostBoardRequest postBoardRequest = new PostBoardRequest("제목" + i, "내용" + i);
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

    @Test
    @DisplayName("게시글 id가 유효하지 않으면 좋아요 추가에 실패한다.")
    void addHeartFail1() throws Exception {
        //given
        PatchBoardRequest request = new PatchBoardRequest(112312300L);
        //when
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> boardService.addHeader(request));
        //then
        assertEquals("유효 하지 않은 게시판 번호입니다.", ex.getMessage());
    }

    @Test
    @DisplayName("게시글 좋아요 추가에 성공한다.")
    void addHeartSuccess() throws Exception {
        //given
        PostBoardRequest postBoardRequest = new PostBoardRequest("좋아요 제목", "좋아요 내용");
        Board saveBoard = boardRepository.save(Board.createBoard(postBoardRequest));
        Long boardId = saveBoard.getId();

        PatchBoardRequest request = new PatchBoardRequest(boardId);
        //when
        AddHeartResponse addHeartResponse1 = boardService.addHeader(request);
        AddHeartResponse addHeartResponse2 = boardService.addHeader(request);
        //then
        assertEquals(2L, addHeartResponse2.getHeart());
    }
}