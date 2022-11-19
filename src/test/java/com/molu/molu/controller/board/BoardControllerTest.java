package com.molu.molu.controller.board;

import com.molu.molu.controller.config.ControllerTest;
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
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
public class BoardControllerTest extends ControllerTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("get board의 sort 값을 잘못 입력하면 실패한다.")
    void getBoardFail1() throws Exception {
        //given
        PostBoardRequest postBoardRequest = new PostBoardRequest("제목", "내용");
        boardRepository.save(Board.createBoard(postBoardRequest));
        //when
        ResultActions perform = mockMvc.perform(
                get("/v1/board")
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
}
