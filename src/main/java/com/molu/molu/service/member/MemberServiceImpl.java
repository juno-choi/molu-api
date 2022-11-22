package com.molu.molu.service.member;

import com.molu.molu.domain.dto.member.PostMember;
import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.domain.entity.member.Sticker;
import com.molu.molu.domain.vo.member.GetMemberStickerResponse;
import com.molu.molu.domain.vo.member.PostMemberResponse;
import com.molu.molu.domain.vo.member.PostStickerResponse;
import com.molu.molu.domain.vo.member.StickerVo;
import com.molu.molu.repository.member.MemberRepository;
import com.molu.molu.repository.member.StickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final StickerRepository stickerRepository;

    @Override
    public GetMemberStickerResponse getMemberSticker(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 회원입니다."));

        List<Sticker> stickers = findMember.getStickers();
        List<StickerVo> stickerList = new LinkedList<>();
        int totalStickers = 0;
        for (Sticker sticker : stickers) {
            int ea = sticker.getEa();
            totalStickers += ea;
            StickerVo stickerVo = StickerVo.builder()
                    .stickerId(sticker.getId())
                    .fromMemberId(sticker.getFromMemberId())
                    .fromMemberName(sticker.getFromMemberName())
                    .reason(sticker.getReason())
                    .ea(ea)
                    .createdAt(sticker.getCreatedAt())
                    .build();
            stickerList.add(stickerVo);
        }

        return GetMemberStickerResponse.builder()
                .memberId(findMember.getMemberId())
                .name(findMember.getName())
                .sticker(stickerList)
                .totalStickerEa(totalStickers)
                .build();
    }

    @Transactional
    @Override
    public PostMemberResponse postMember(PostMember postMember) {
        Member member = Member.createMember(postMember.getName());
        Member saveMember = memberRepository.save(member);
        return PostMemberResponse.builder()
                .memberId(saveMember.getMemberId())
                .name(saveMember.getName())
                .build();
    }

    @Transactional
    @Override
    public PostStickerResponse postSticker(Long toMemberId, Long fromMemberId, String reason, int ea) {

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new IllegalArgumentException("받는 회원이 유효하지 않습니다."));
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new IllegalArgumentException("스티커 발급하려는 회원이 유효하지 않습니다."));

        Sticker sticker = Sticker.createSticker(toMember, fromMember, reason, ea);
        toMember.changeSticker(stickerRepository.save(sticker));
        memberRepository.save(toMember);
        return PostStickerResponse.builder()
                .toMemberName(toMember.getName())
                .ea(ea)
                .reason(reason)
                .build();
    }
}
