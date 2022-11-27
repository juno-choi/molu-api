package com.molu.molu.controller.board;

import com.molu.molu.controller.config.RestdocsTest;
import com.molu.molu.domain.dto.board.PostBoard;
import com.molu.molu.domain.dto.board.PostComment;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.repository.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
class CommentControllerDocs extends RestdocsTest {
    @Autowired
    private BoardRepository boardRepository;


    private final String PREFIX = "/v1/comment";

    @Test
    @DisplayName(PREFIX+" (post)")
    void postComment() throws Exception {
        //given
        PostBoard postBoard = new PostBoard("댓글이 달릴 게시판", "댓글이 달릴 게시판입니다.");
        Board saveBoard = boardRepository.save(Board.of(postBoard));
        PostComment postComment = new PostComment(saveBoard.getBoardId(), "댓글 입니다.");
        //when
        ResultActions perform = mockMvc.perform(
                post(PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToString(postComment)));
        //then
        perform.andExpect(status().is2xxSuccessful());
        //docs
        perform.andDo(docs.document(
            requestFields(
                    fieldWithPath("board_id").type(JsonFieldType.NUMBER).description("게시판 id"),
                    fieldWithPath("comment").type(JsonFieldType.STRING).description("댓글 내용")
            ),
            responseFields(
                    fieldWithPath("result_code").type(JsonFieldType.STRING).description("결과 코드"),
                    fieldWithPath("result_type").type(JsonFieldType.STRING).description("결과 타입"),
                    fieldWithPath("result_message").type(JsonFieldType.STRING).description("결과 메세지"),
                    fieldWithPath("data.comment_id").type(JsonFieldType.NUMBER).description("댓글 id"),
                    fieldWithPath("data.member_id").type(JsonFieldType.NUMBER).description("댓글 회원 id"),
                    fieldWithPath("data.board_id").type(JsonFieldType.NUMBER).description("댓글이 추가된 게시판 id"),
                    fieldWithPath("data.comment").type(JsonFieldType.STRING).description("추가된 댓글 내용"),
                    fieldWithPath("data.writer").type(JsonFieldType.STRING).description("익명 작성자"),
                    fieldWithPath("data.modified_at").type(JsonFieldType.STRING).description("수정일"),
                    fieldWithPath("data.created_at").type(JsonFieldType.STRING).description("생성일")
            )
        ));
    }

}