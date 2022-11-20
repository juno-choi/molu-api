package com.molu.molu.controller.board;

import com.molu.molu.controller.config.ControllerTest;
import com.molu.molu.domain.dto.board.PatchBoardRequest;
import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.entity.board.Board;
import com.molu.molu.domain.enums.api.ErrorCode;
import com.molu.molu.repository.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
        PostBoardRequest postBoardRequest = new PostBoardRequest(null, "내용만 입력");
        //when
        ResultActions perform = mockMvc.perform(post(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(postBoardRequest)))
                .andDo(print());
        //then
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청\",\"error\":{\"message\":\"제목을 입력해주세요.\"}}"));
    }

    @Test
    @DisplayName("내용 없는 게시물 등록은 실패한다.")
    void postBoardFail2() throws Exception {
        //given
        PostBoardRequest postBoardRequest = new PostBoardRequest("제목만 입력", "");
        //when
        ResultActions perform = mockMvc.perform(post(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(postBoardRequest)))
                .andDo(print());
        //then
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청\",\"error\":{\"message\":\"내용을 입력해주세요.\"}}"));
    }

    @Test
    @DisplayName("get board의 sort 값을 잘못 입력하면 실패한다.")
    void getBoardFail1() throws Exception {
        //given
        PostBoardRequest postBoardRequest = new PostBoardRequest("제목", "내용");
        boardRepository.save(Board.createBoard(postBoardRequest));
        //when
        ResultActions perform = mockMvc.perform(
                get(PREFIX)
                .param("size", "3")
                .param("page", "0")
                .param("sort","id,as")
        ).andDo(print());
        //then
        assertTrue(perform.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8)
                .contains(ErrorCode.BAD_REQUEST.CODE));
    }

    @Test
    @DisplayName("유효하지 않은 게시물 id는 좋아요에 실패한다.")
    void addHeartFail1() throws Exception {
        //given
        PatchBoardRequest patchBoardRequest = new PatchBoardRequest(1231234L);
        //when
        ResultActions perform = mockMvc.perform(patch(PREFIX + "/heart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(patchBoardRequest)))
                .andDo(print());
        //then
        perform.andExpect(status().is4xxClientError());
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청\",\"error\":{\"message\":\"유효 하지 않은 게시판 번호입니다.\"}}"));

    }

    @Test
    @DisplayName("비어있는 게시물 id 요청은 좋아요에 실패한다.")
    void addHeartFail2() throws Exception {
        //given
        PatchBoardRequest patchBoardRequest = new PatchBoardRequest(null);
        //when
        ResultActions perform = mockMvc.perform(patch(PREFIX + "/heart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(patchBoardRequest)))
                .andDo(print());
        //then
        perform.andExpect(status().is4xxClientError());
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청\",\"error\":{\"message\":\"게시판 번호는 필수값입니다.\"}}"));

    }
}
