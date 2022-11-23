package com.molu.molu.service.member;

import com.molu.molu.domain.dto.member.PostMember;
import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.domain.entity.member.Sticker;
import com.molu.molu.domain.vo.member.GetMemberStickerResponse;
import com.molu.molu.domain.vo.member.PostMemberResponse;
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
    @DisplayName("회원 스티커 조회에 성공한다.")
    void getMemberSticker() throws Exception {
        //given
        Member luna = Member.createMember("루나");
        Member juno = Member.createMember("주노");
        Member saveLuna = memberRepository.save(luna);
        Member saveJuno = memberRepository.save(juno);
        Sticker sticker1 = Sticker.createSticker(saveLuna, saveJuno, "테스트로 줌1", 5);
        Sticker sticker2 = Sticker.createSticker(saveLuna, saveJuno, "테스트로 줌2", 10);
        Sticker sticker3 = Sticker.createSticker(saveJuno, saveLuna, "테스트로 줌3", 3);
        Sticker saveSticker1 = stickerRepository.save(sticker1);
        Sticker saveSticker2 = stickerRepository.save(sticker2);
        Sticker saveSticker3 = stickerRepository.save(sticker3);
        //when
        GetMemberStickerResponse memberSticker = memberService.getMemberSticker(saveLuna.getMemberId());
        //then
        assertTrue(memberSticker.getSticker().size() == 2);
    }

    @Test
    @DisplayName("스티커 발급에 성공한다.")
    void postStickerSuccess() throws Exception {
        //given
        Member luna = Member.createMember("루나");
        Member juno = Member.createMember("주노");
        Member saveLuna = memberRepository.save(luna);
        Member saveJuno = memberRepository.save(juno);
        //when
        PostStickerResponse sticker = memberService.simpleSticker(saveLuna.getMemberId(), saveJuno.getMemberId(), "칭찬해", 10);
        //then
        Member findLuna = memberRepository.findById(saveLuna.getMemberId()).get();
    }

    @Test
    @DisplayName("멤버 회원가입에 성공한다.")
    void postMemberSuccess() throws Exception {
        //given
        PostMember postMember = new PostMember("tester");
        //when
        PostMemberResponse postMemberResponse = memberService.postMember(postMember);
        //then
        Member findMember = memberRepository.findById(postMemberResponse.getMemberId()).get();
        assertEquals(postMember.getName(), findMember.getName());
    }
}