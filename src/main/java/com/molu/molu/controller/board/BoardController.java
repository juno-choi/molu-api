package com.molu.molu.controller.board;

import com.molu.molu.domain.api.CommonApi;
import com.molu.molu.domain.dto.board.PatchBoardRequest;
import com.molu.molu.domain.dto.board.PostBoardRequest;
import com.molu.molu.domain.enums.api.ResultCode;
import com.molu.molu.domain.enums.api.ResultType;
import com.molu.molu.domain.vo.board.AddHeartResponse;
import com.molu.molu.domain.vo.board.GetBoardResponse;
import com.molu.molu.domain.vo.board.PostBoardResponse;
import com.molu.molu.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<CommonApi<PostBoardResponse>> postBoard(@Valid @RequestBody PostBoardRequest postBoardRequest, BindingResult bindingResult){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonApi.<PostBoardResponse>builder()
                                .resultCode(ResultCode.SUCCESS)
                                .resultType(ResultType.NONE)
                                .data(boardService.postBoard(postBoardRequest))
                                .build()
                );
    }

    @GetMapping("")
    public ResponseEntity<CommonApi<GetBoardResponse>> postBoard(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        return ResponseEntity.ok(
                        CommonApi.<GetBoardResponse>builder()
                                .resultCode(ResultCode.SUCCESS)
                                .resultType(ResultType.NONE)
                                .data(boardService.getBoard(pageable))
                                .build()
                );
    }

    @PatchMapping("/heart")
    public ResponseEntity<CommonApi<AddHeartResponse>> addHeart(@Valid @RequestBody PatchBoardRequest patchBoardRequest, BindingResult bindingResult){
        return ResponseEntity.ok(
                CommonApi.<AddHeartResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .resultType(ResultType.NONE)
                        .data(boardService.addHeader(patchBoardRequest))
                        .build()
        );
    }
}
