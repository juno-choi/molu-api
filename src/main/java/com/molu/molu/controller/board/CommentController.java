package com.molu.molu.controller.board;

import com.molu.molu.domain.api.CommonApi;
import com.molu.molu.domain.dto.board.PostComment;
import com.molu.molu.domain.enums.api.ResultCode;
import com.molu.molu.domain.enums.api.ResultType;
import com.molu.molu.domain.vo.board.PostCommentResponse;
import com.molu.molu.service.board.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommonApi<PostCommentResponse>> postComment(@Valid @RequestBody PostComment postComment, BindingResult bindingResult){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonApi.<PostCommentResponse>builder()
                                .resultCode(ResultCode.SUCCESS)
                                .resultType(ResultType.NONE)
                                .data(commentService.postComment(postComment))
                                .build()
                );
    }
}
