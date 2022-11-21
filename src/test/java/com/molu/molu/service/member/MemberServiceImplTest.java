package com.molu.molu.service.member;

import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.domain.vo.member.PostStickerResponse;
import com.molu.molu.repository.member.MemberRepository;
import com.molu.molu.repository.member.StickerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
@Transactional(readOnly = true)
class MemberServiceImplTest{
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StickerRepository stickerRepository;

    @Test
    @DisplayName("스티커 발급에 성공한다.")
    void postStickerSuccess() throws Exception {
        //given
        Member luna = Member.createMember("루나");
        Member juno = Member.createMember("주노");
        Member saveLuna = memberRepository.save(luna);
        Member saveJuno = memberRepository.save(juno);
        //when
        PostStickerResponse sticker = memberService.postSticker(saveLuna.getMemberId(), saveJuno.getMemberId(), "칭찬해", 10);
        //then
        Member findLuna = memberRepository.findById(saveLuna.getMemberId()).get();
    }
}