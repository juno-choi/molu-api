package com.molu.molu.service.board;

import com.molu.molu.domain.dto.board.PostComment;
import com.molu.molu.domain.vo.board.PostCommentResponse;

public interface CommentService {
    PostCommentResponse postComment(PostComment postComment);
}
