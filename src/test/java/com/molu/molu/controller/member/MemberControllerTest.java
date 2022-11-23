package com.molu.molu.controller.member;

import com.molu.molu.controller.config.ControllerTest;
import com.molu.molu.domain.dto.member.PostMember;
import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
@Transactional(readOnly = true)
class MemberControllerTest extends ControllerTest {
    @Autowired
    private MemberRepository memberRepository;

    private final String PREFIX = "/v1/member";

    @Test
    @DisplayName("이름을 입력하지 않으면 회원가입 실패한다.")
    void postMemberFail1() throws Exception {
        //given
        PostMember postMember = new PostMember();
        //when
        ResultActions perform = mockMvc.perform(post(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(postMember)))
                .andDo(print());
        //then
        perform.andExpect(content().json("{\"error_code\":\"9400\",\"message\":\"잘못된 요청. error field 값은 모두 snake 전략으로 변경하여 요청해주세요.\",\"errors\":[{\"field\":\"name\",\"message\":\"이름은 필수입니다!\"}]}"));
    }

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
                get(PREFIX+"/sticker/simple")
                .param("to", String.valueOf(saveLuna.getMemberId()))
                .param("from", String.valueOf(saveJuno.getMemberId()))
                .param("reason", "칭찬해")
                .param("ea", "10")
        ).andDo(print());
        //then
        Member findLuna = memberRepository.findById(saveLuna.getMemberId()).get();
    }
}