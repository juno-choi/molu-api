package com.molu.molu.controller.member;

import com.molu.molu.controller.config.ControllerTest;
import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional(readOnly = true)
class MemberControllerTest extends ControllerTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 스티커 발급에 성공한다.")
    void stickerSuccess1() throws Exception {
        //given
        Member luna = Member.createMember("루나");
        Member juno = Member.createMember("주노");
        Member saveLuna = memberRepository.save(luna);
        Member saveJuno = memberRepository.save(juno);

        //when
        mockMvc.perform(
                get("/v1/member/sticker/simple")
                .param("to", String.valueOf(saveLuna.getMemberId()))
                .param("from", String.valueOf(saveJuno.getMemberId()))
                .param("reason", "칭찬해")
                .param("ea", "10")
        ).andDo(print());
        //then
        Member findLuna = memberRepository.findById(saveLuna.getMemberId()).get();
        assertTrue(findLuna.getStickers().size() == 1);
    }
}