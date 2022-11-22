package com.molu.molu.service.member;

import com.molu.molu.domain.dto.member.PostMember;
import com.molu.molu.domain.vo.member.PostMemberResponse;
import com.molu.molu.domain.vo.member.PostStickerResponse;

public interface MemberService {
    PostMemberResponse postMember(PostMember postMember);
    PostStickerResponse postSticker(Long toMemberId, Long fromMemberId, String reason, int ea);
}
