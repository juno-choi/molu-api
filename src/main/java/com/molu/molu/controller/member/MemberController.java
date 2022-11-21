package com.molu.molu.controller.member;

import com.molu.molu.domain.api.CommonApi;
import com.molu.molu.domain.enums.api.ResultCode;
import com.molu.molu.domain.enums.api.ResultType;
import com.molu.molu.domain.vo.member.PostStickerResponse;
import com.molu.molu.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/sticker/simple")
    public ResponseEntity<CommonApi<PostStickerResponse>> postSimpleSticker
            (@RequestParam(name = "to") Long toMemberId,
             @RequestParam(name = "from") Long fromMemberId,
             @RequestParam String reason, @RequestParam int ea){

        return ResponseEntity.ok(CommonApi.<PostStickerResponse>builder()
                .resultCode(ResultCode.SUCCESS)
                .resultType(ResultType.NONE)
                .data(memberService.postSticker(toMemberId, fromMemberId, reason, ea))
                .build());
    }
}
