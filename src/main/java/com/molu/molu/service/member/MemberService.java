package com.molu.molu.service.member;

import com.molu.molu.domain.vo.member.PostStickerResponse;

public interface MemberService {
    PostStickerResponse postSticker(Long toMemberId, Long fromMemberId, String reason, int ea);
}
