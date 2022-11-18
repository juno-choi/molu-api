package com.molu.molu.controller.board;

import com.molu.molu.common.api.CommonApi;
import com.molu.molu.domain.dto.board.BoardRequest;
import com.molu.molu.domain.enums.api.ResultCode;
import com.molu.molu.domain.enums.api.ResultType;
import com.molu.molu.domain.vo.board.BoardResponse;
import com.molu.molu.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<CommonApi<BoardResponse>> postBoard(@RequestBody BoardRequest boardRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonApi.<BoardResponse>builder()
                                .resultCode(ResultCode.SUCCESS)
                                .resultType(ResultType.NONE)
                                .data(boardService.postBoard(boardRequest))
                                .build()
                );
    }
}
