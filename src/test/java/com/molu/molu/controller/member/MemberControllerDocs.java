package com.molu.molu.controller.member;

import com.molu.molu.controller.config.RestdocsTest;
import com.molu.molu.domain.entity.member.Member;
import com.molu.molu.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
@Transactional(readOnly = true)
class MemberControllerDocs extends RestdocsTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("/v1/member/sticker/simple")
    void stickerSimple() throws Exception {
        //given
        Member luna = Member.createMember("루나");
        Member juno = Member.createMember("주노");
        Member saveLuna = memberRepository.save(luna);
        Member saveJuno = memberRepository.save(juno);

        //when
        ResultActions perform = mockMvc.perform(
                get("/v1/member/sticker/simple")
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