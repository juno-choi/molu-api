package com.molu.molu.controller.member;

import com.molu.molu.controller.config.RestdocsTest;
import com.molu.molu.domain.dto.member.PostMember;
import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.domain.entity.member.Sticker;
import com.molu.molu.repository.member.MemberRepository;
import com.molu.molu.repository.member.StickerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
@Transactional(readOnly = true)
class MemberControllerDocs extends RestdocsTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StickerRepository stickerRepository;

    private final String PREFIX = "/v1/member";

    @Test
    @DisplayName(PREFIX+"/sticker (get)")
    void getMemberSticker() throws Exception {
        //given
        Member luna = Member.createMember("루나");
        Member juno = Member.createMember("주노");
        Member saveLuna = memberRepository.save(luna);
        Member saveJuno = memberRepository.save(juno);
        Sticker sticker1 = Sticker.createSticker(saveLuna, saveJuno, "테스트로 줌1", 5);
        Sticker sticker2 = Sticker.createSticker(saveLuna, saveJuno, "테스트로 줌2", 10);
        Sticker saveSticker1 = stickerRepository.save(sticker1);
        Sticker saveSticker2 =stickerRepository.save(sticker2);
        saveLuna.changeSticker(saveSticker1);
        saveLuna.changeSticker(saveSticker2);
        //when
        ResultActions perform = mockMvc.perform(RestDocumentationRequestBuilders.get(PREFIX + "/sticker/{memberId}", saveLuna.getMemberId()).contentType(MediaType.APPLICATION_JSON_UTF8));
        //then
        perform.andDo(docs.document(
                pathParameters(
                        parameterWithName("memberId").description("회원 번호")
                ),
                responseFields(
                        fieldWithPath("result_code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("result_type").type(JsonFieldType.STRING).description("결과 타입"),
                        fieldWithPath("result_message").type(JsonFieldType.STRING).description("결과 메세지"),
                        fieldWithPath("data.member_id").type(JsonFieldType.NUMBER).description("회원 번호"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                        fieldWithPath("data.total_sticker_ea").type(JsonFieldType.NUMBER).description("sticker 총 개수"),
                        fieldWithPath("data.sticker[].sticker_id").type(JsonFieldType.NUMBER).description("sticker 번호"),
                        fieldWithPath("data.sticker[].from_member_id").type(JsonFieldType.NUMBER).description("sticker 받은 회원 번호"),
                        fieldWithPath("data.sticker[].from_member_name").type(JsonFieldType.STRING).description("sticker 받은 회원 이름"),
                        fieldWithPath("data.sticker[].reason").type(JsonFieldType.STRING).description("sticker 받은 이유"),
                        fieldWithPath("data.sticker[].ea").type(JsonFieldType.NUMBER).description("sticker 개수"),
                        fieldWithPath("data.sticker[].created_at").type(JsonFieldType.STRING).description("sticker 생성일")
                )
        ));
    }

    @Test
    @DisplayName(PREFIX+" (post)")
    void postMember() throws Exception {
        //given
        PostMember postMember = new PostMember("tester");
        //when
        ResultActions perform = mockMvc.perform(post(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToString(postMember)));
        //then
        perform.andDo(docs.document(
            requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름")
            ),
            responseFields(
                    fieldWithPath("result_code").type(JsonFieldType.STRING).description("결과 코드"),
                    fieldWithPath("result_type").type(JsonFieldType.STRING).description("결과 타입"),
                    fieldWithPath("result_message").type(JsonFieldType.STRING).description("결과 메세지"),
                    fieldWithPath("data.member_id").type(JsonFieldType.NUMBER).description("회원 번호"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름")
            )
        ));
    }

    @Test
    @DisplayName(PREFIX+"/sticker/simple (get)")
    void stickerSimple() throws Exception {
        //given
        Member luna = Member.createMember("루나");
        Member juno = Member.createMember("주노");
        Member saveLuna = memberRepository.save(luna);
        Member saveJuno = memberRepository.save(juno);

        //when
        ResultActions perform = mockMvc.perform(
                get(PREFIX+"/sticker/simple")
                        .param("to", String.valueOf(saveLuna.getMemberId()))
                        .param("from", String.valueOf(saveJuno.getMemberId()))
                        .param("reason", "칭찬해")
                        .param("ea", "10")
        );
        //then
        perform.andDo(docs.document(
                 requestParameters(
                         parameterWithName("to").description("스티카를 받을 회원"),
                         parameterWithName("from").description("스티카를 주는 회원"),
                         parameterWithName("reason").description("스티카를 주는 이유"),
                         parameterWithName("ea").description("스티카 개수")
                 ),
                responseFields(
                        fieldWithPath("result_code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("result_type").type(JsonFieldType.STRING).description("결과 타입"),
                        fieldWithPath("result_message").type(JsonFieldType.STRING).description("결과 메세지"),
                        fieldWithPath("data.to_member_name").type(JsonFieldType.STRING).description("스티카 받은 회원 이름"),
                        fieldWithPath("data.ea").type(JsonFieldType.NUMBER).description("스티카 받은 개수"),
                        fieldWithPath("data.reason").type(JsonFieldType.STRING).description("스티카 받은 이유")
                )
        ));
    }
}