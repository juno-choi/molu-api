package com.molu.molu.controller.member;

import com.molu.molu.domain.api.CommonApi;
import com.molu.molu.domain.dto.member.PostMember;
import com.molu.molu.domain.enums.api.ResultCode;
import com.molu.molu.domain.enums.api.ResultType;
import com.molu.molu.domain.vo.member.PostMemberResponse;
import com.molu.molu.domain.vo.member.PostStickerResponse;
import com.molu.molu.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<CommonApi<PostMemberResponse>> postMember(@Valid @RequestBody PostMember postMember, BindingResult bindingResult){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonApi.<PostMemberResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .resultType(ResultType.NONE)
                        .data(memberService.postMember(postMember))
                        .build());
    }

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
