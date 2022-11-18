package com.molu.molu.controller.board;

import com.molu.molu.controller.config.RestdocsTest;
import com.molu.molu.domain.dto.board.PostBoardRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class BoardControllerDocs extends RestdocsTest {

    @Test
    @DisplayName("/v1/board (post)")
    void postBoard() throws Exception {
        //given
        String title = "제목";
        String content = "내용";
        PostBoardRequest postBoardRequest = new PostBoardRequest(title, content, null);
        //when
        ResultActions perform = mockMvc.perform(
                post("/v1/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToString(postBoardRequest)));
        //then
        perform.andDo(docs.document(
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                        fieldWithPath("writer").type(JsonFieldType.NULL).description("요청하지 않아도 되는 내용").optional()
                ),
                responseFields(
                        fieldWithPath("result_code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("result_type").type(JsonFieldType.STRING).description("결과 타입"),
                        fieldWithPath("result_message").type(JsonFieldType.STRING).description("결과 메세지"),
                        fieldWithPath("data.board_id").type(JsonFieldType.NUMBER).description("게시판 id"),
                        fieldWithPath("data.writer").type(JsonFieldType.STRING).description("익명 작성자명")
                )
        ));
    }
}