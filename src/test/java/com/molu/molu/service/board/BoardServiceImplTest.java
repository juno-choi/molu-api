package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.CommentDto;
import com.molu.molu.domain.dto.board.PatchBoard;
import com.molu.molu.domain.dto.board.PostBoard;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.entity.board.Comment;
import com.molu.molu.domain.vo.board.AddHeartResponse;
import com.molu.molu.domain.vo.board.GetBoardResponse;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import com.molu.molu.repository.board.BoardRepository;
import com.molu.molu.repository.board.CommentRepository;
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

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("게시글 등록 성공한다.")
    public void postBoardSuccess() throws Exception {
        //given
        String title = "제목";
        String content = "내용";
        PostBoard postBoard = new PostBoard(title, content);
        //when
        PostBoardResponse postBoardResponse = boardService.postBoard(postBoard);
        //then
        assertNotNull(postBoardResponse.getWriter());
        Board board = boardRepository.findById(postBoardResponse.getBoardId()).get();
        assertEquals(title, board.getTitle());
        assertEquals(content, board.getContent());
    }

    @Test
    @DisplayName("게시글 페이징 성공한다.")
    void getBoardSuccess1() throws Exception {
        //given
        for(int i=1; i<=20; i++){
            PostBoard postBoard = new PostBoard("제목" + i, "내용" + i);
            boardRepository.save(Board.of(postBoard));
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
    @DisplayName("게시글 페이징 후 댓글을 불러오는데 성공한다.")
    void getBoardSuccess2() throws Exception {
        //given
        for(int i=1; i<=20; i++){
            PostBoard postBoard = new PostBoard("제목" + i, "내용" + i);
            Board saveBoard = boardRepository.save(Board.of(postBoard));
            for(int j=1; j<=3; j++){
                commentRepository.save(Comment.of(0L, "댓글"+j, saveBoard));
            }
        }

        Pageable pageable = Pageable.ofSize(5);
        pageable = pageable.next();
        pageable = pageable.next();

        //when
        GetBoardResponse board = boardService.getBoard(pageable);

        //then
//        Stream<String> title = board.getBoardList().stream().filter(b -> b.comm().equals("내용10")).map(b -> b.getTitle());
        Stream<CommentDto> commentStream = board.getBoardList().stream().map(b -> b.getComments().get(1));
        CommentDto comment = commentStream.findFirst().get();
        assertTrue(comment.getComment().equals("댓글2"));
    }

    @Test
    @DisplayName("게시글 id가 유효하지 않으면 좋아요 추가에 실패한다.")
    void addHeartFail1() throws Exception {
        //given
        PatchBoard request = new PatchBoard(112312300L);
        //when
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> boardService.addHeader(request));
        //then
        assertEquals("유효 하지 않은 게시판 번호입니다.", ex.getMessage());
    }

    @Test
    @DisplayName("게시글 좋아요 추가에 성공한다.")
    void addHeartSuccess() throws Exception {
        //given
        PostBoard postBoard = new PostBoard("좋아요 제목", "좋아요 내용");
        Board saveBoard = boardRepository.save(Board.of(postBoard));
        Long boardId = saveBoard.getBoardId();

        PatchBoard request = new PatchBoard(boardId);
        //when
        AddHeartResponse addHeartResponse1 = boardService.addHeader(request);
        AddHeartResponse addHeartResponse2 = boardService.addHeader(request);
        //then
        assertEquals(2L, addHeartResponse2.getHeart());
    }


}