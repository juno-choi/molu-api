package com.molu.molu.service.member;

import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.domain.entity.member.Sticker;
import com.molu.molu.domain.vo.member.PostStickerResponse;
import com.molu.molu.repository.member.MemberRepository;
import com.molu.molu.repository.member.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final StickerRepository stickerRepository;

    @Transactional
    @Override
    public PostStickerResponse postSticker(Long toMemberId, Long fromMemberId, String reason, int ea) {

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new IllegalArgumentException("받는 회원이 유효하지 않습니다."));
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new IllegalArgumentException("스티커 발급하려는 회원이 유효하지 않습니다."));

        Sticker sticker = Sticker.createSticker(toMember, fromMember, reason, ea);
        toMember.changeSticker(stickerRepository.save(sticker));

        return PostStickerResponse.builder()
                .toMemberName(toMember.getName())
                .ea(ea)
                .reason(reason)
                .build();
    }
}
