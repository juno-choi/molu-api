package com.molu.molu.service.member;

import com.molu.molu.domain.dto.member.PostMember;
import com.molu.molu.domain.dto.member.PostMemberSticker;
import com.molu.molu.domain.vo.member.GetMemberStickerResponse;
import com.molu.molu.domain.vo.member.PostMemberResponse;
import com.molu.molu.domain.vo.member.PostStickerResponse;

public interface MemberService {
    GetMemberStickerResponse getMemberSticker(Long id);
    PostMemberResponse postMember(PostMember postMember);
    PostStickerResponse simpleSticker(Long toMemberId, Long fromMemberId, String reason, int ea);
    PostStickerResponse postSticker(PostMemberSticker postMemberSticker);
}
