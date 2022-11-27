package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PostBoard;
import com.molu.molu.domain.dto.board.PostComment;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.entity.board.Comment;
import com.molu.molu.domain.vo.board.PostCommentResponse;
import com.molu.molu.repository.board.BoardRepository;
import com.molu.molu.repository.board.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시판 댓글 등록에 성공한다.")
    void postCommentSuccess1() throws Exception {
        //given
        PostBoard postBoard = new PostBoard("댓글 테스트", "댓글 달릴 게시판입니다.");
        Board saveBoard = boardRepository.save(Board.of(postBoard));
        PostComment postComment = new PostComment(saveBoard.getBoardId(), "등록될 댓글 내용입니다.");
        //when
        PostCommentResponse postCommentResponse = commentService.postComment(postComment);
        //then
        Comment comment = commentRepository.findById(postCommentResponse.getCommentId()).get();
        assertNotNull(comment);
    }
}