package com.molu.molu.domain.vo.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostStickerResponse {
    private String toMember;
    private int ea;
    private String reason;
}
