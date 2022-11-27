package com.molu.molu.controller.board;

import com.molu.molu.controller.config.ControllerTest;
import com.molu.molu.domain.dto.board.PatchBoard;
import com.molu.molu.domain.dto.board.PostBoard;
import com.molu.molu.repository.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
public class BoardControllerTest extends ControllerTest {
    @Autowired
    private BoardRepository boardRepository;

    private final String PREFIX = "/v1/board";

    @Test
    @DisplayName("title 존재하지 않은 게시물 등록은 실패한다.")
    void postBoardFail1() throws Exception {
        //given
        PostBoard postBoard = new PostBoard(null, "내용만 입력");
        //when
        ResultActions perform = mockMvc.perform(post(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(postBoard)))
                .andDo(print());
        //then
        perform.andExpect(status().is4xxClientError());
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청. error field 값은 모두 snake 전략으로 변경하여 요청해주세요.\",\"errors\":[{\"field\":\"title\",\"message\":\"제목을 입력해주세요.\"}]}"));
    }

    @Test
    @DisplayName("내용 없는 게시물 등록은 실패한다.")
    void postBoardFail2() throws Exception {
        //given
        PostBoard postBoard = new PostBoard("제목만 입력", "");
        //when
        ResultActions perform = mockMvc.perform(post(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(postBoard)))
                .andDo(print());
        //then
        perform.andExpect(status().is4xxClientError());
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청. error field 값은 모두 snake 전략으로 변경하여 요청해주세요.\",\"errors\":[{\"field\":\"content\",\"message\":\"내용을 입력해주세요.\"},{\"field\":\"content\",\"message\":\"내용은 3000자까지 입력이 가능합니다!\"}]}"));
    }
    @Test
    @DisplayName("아무 입력도 없는 게시물 등록은 실패한다.")
    void postBoardFail3() throws Exception {
        //given
        PostBoard postBoard = new PostBoard(null, null);
        //when
        ResultActions perform = mockMvc.perform(post(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(postBoard)))
                .andDo(print());
        //then
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청. error field 값은 모두 snake 전략으로 변경하여 요청해주세요.\",\"errors\":[{\"field\":\"content\",\"message\":\"내용을 입력해주세요.\"},{\"field\":\"title\",\"message\":\"제목을 입력해주세요.\"}]}"));
    }

    @Test
    @DisplayName("유효하지 않은 게시물 id는 좋아요에 실패한다.")
    void addHeartFail1() throws Exception {
        //given
        PatchBoard patchBoard = new PatchBoard(1231234L);
        //when
        ResultActions perform = mockMvc.perform(patch(PREFIX + "/heart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(patchBoard)))
                .andDo(print());
        //then
        perform.andExpect(status().is4xxClientError());
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청\",\"errors\":[{\"message\":\"유효 하지 않은 게시판 번호입니다.\"}]}"));

    }

    @Test
    @DisplayName("비어있는 게시물 id 요청은 좋아요에 실패한다.")
    void addHeartFail2() throws Exception {
        //given
        PatchBoard patchBoard = new PatchBoard(null);
        //when
        ResultActions perform = mockMvc.perform(patch(PREFIX + "/heart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(patchBoard)))
                .andDo(print());
        //then
        perform.andExpect(status().is4xxClientError());
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청. error field 값은 모두 snake 전략으로 변경하여 요청해주세요.\",\"errors\":[{\"field\":\"boardId\",\"message\":\"게시판 번호는 필수값입니다.\"}]}"));

    }
}
