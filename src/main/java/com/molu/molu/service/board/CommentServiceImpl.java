package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PostComment;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.entity.board.Comment;
import com.molu.molu.domain.vo.board.PostCommentResponse;
import com.molu.molu.repository.board.BoardRepository;
import com.molu.molu.repository.board.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    public PostCommentResponse postComment(PostComment postComment) {
        Board board = boardRepository.findById(postComment.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 게시판 번호입니다."));

        Comment comment = Comment.of(0L, postComment.getComment(), board);
        Comment saveComment = commentRepository.save(comment);

        return PostCommentResponse.builder()
                .boardId(board.getBoardId())
                .commentId(saveComment.getCommentId())
                .memberId(saveComment.getMemberId())
                .comment(saveComment.getComment())
                .modifiedAt(saveComment.getModifiedAt())
                .createdAt(saveComment.getCreatedAt())
                .build();
    }
}
