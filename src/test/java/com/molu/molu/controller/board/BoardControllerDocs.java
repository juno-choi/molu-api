package com.molu.molu.controller.board;

import com.molu.molu.controller.config.RestdocsTest;
import com.molu.molu.domain.dto.board.PatchBoard;
import com.molu.molu.domain.dto.board.PostBoard;
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

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
class BoardControllerDocs extends RestdocsTest {
    @Autowired
    private BoardRepository boardRepository;

    private final String PREFIX = "/v1/board";

    @Test
    @DisplayName(PREFIX+" (post)")
    void postBoard() throws Exception {
        //given
        String title = "제목";
        String content = "내용";
        PostBoard postBoard = new PostBoard(title, content);
        //when
        ResultActions perform = mockMvc.perform(
                post(PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToString(postBoard)));
        //then
        perform.andDo(docs.document(
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                ),
                responseFields(
                        fieldWithPath("result_code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("result_type").type(JsonFieldType.STRING).description("결과 타입"),
                        fieldWithPath("result_message").type(JsonFieldType.STRING).description("결과 메세지"),
                        fieldWithPath("data.board_id").type(JsonFieldType.NUMBER).description("게시판 id"),
                        fieldWithPath("data.writer").type(JsonFieldType.STRING).description("익명 작성자")
                )
        ));
    }


    @Test
    @DisplayName(PREFIX+" (get)")
    void getBoard() throws Exception {
        //given
        for(int i=0; i<20; i++){
            PostBoard postBoard = new PostBoard("제목" + i, "내용" + i);
            boardRepository.save(Board.createBoard(postBoard));
        }
        //when
        ResultActions perform = mockMvc.perform(
                get(PREFIX)
                .param("page", "0")
                .param("size", "5")
                .param("sort", "id,desc"));
        //then
        perform.andDo(docs.document(
                requestParameters(
                        parameterWithName("page").description("페이지 번호 (default = 0)").optional(),
                        parameterWithName("size").description("페이지 당 호출할 게시글 수 (default = 10)").optional(),
                        parameterWithName("sort").description("정렬 기준 (default = id,desc)").optional()
                ),
                responseFields(
                        fieldWithPath("result_code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("result_type").type(JsonFieldType.STRING).description("결과 타입"),
                        fieldWithPath("result_message").type(JsonFieldType.STRING).description("결과 메세지"),
                        fieldWithPath("data.total_page").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                        fieldWithPath("data.total_elements").type(JsonFieldType.NUMBER).description("총 게시글 수"),
                        fieldWithPath("data.number_of_elements").type(JsonFieldType.NUMBER).description("호출한 페이지 게시글 수"),
                        fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                        fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("호출 페이지 빈 페이지 여부"),
                        fieldWithPath("data.board_list[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
                        fieldWithPath("data.board_list[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                        fieldWithPath("data.board_list[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                        fieldWithPath("data.board_list[].writer").type(JsonFieldType.STRING).description("게시글 익명 작성자"),
                        fieldWithPath("data.board_list[].comments").type(JsonFieldType.ARRAY).description("게시글 댓글"),
                        fieldWithPath("data.board_list[].heart").type(JsonFieldType.NUMBER).description("게시글 좋아요"),
                        fieldWithPath("data.board_list[].modifiedAt").type(JsonFieldType.STRING).description("게시글 수정일"),
                        fieldWithPath("data.board_list[].createdAt").type(JsonFieldType.STRING).description("게시글 생성일")
                )
        ));
    }

    @Test
    @DisplayName(PREFIX+"/heart (patch)")
    void addHeart() throws Exception {
        //given
        PostBoard postBoard = new PostBoard("좋아요 게시글", "내용");
        Board saveBoard = boardRepository.save(Board.createBoard(postBoard));
        Long boardId = saveBoard.getId();

        PatchBoard patchBoard = new PatchBoard(boardId);
        //when
        ResultActions perform = mockMvc.perform(patch(PREFIX + "/heart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(patchBoard)));
        //then
        perform.andDo(docs.document(
                requestFields(
                        fieldWithPath("board_id").type(JsonFieldType.NUMBER).description("글 id")
                ),
                responseFields(
                        fieldWithPath("result_code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("result_type").type(JsonFieldType.STRING).description("결과 타입"),
                        fieldWithPath("result_message").type(JsonFieldType.STRING).description("결과 메세지"),
                        fieldWithPath("data.board_id").type(JsonFieldType.NUMBER).description("게시판 id"),
                        fieldWithPath("data.heart").type(JsonFieldType.NUMBER).description("게시물 좋아요 개수")
                )
        ));
    }
}