package com.intellijo.proup.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellijo.proup.member.controller.MemberController;
import com.intellijo.proup.member.dto.MemberDTO;
import com.intellijo.proup.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles(profiles = "test")
public class MemberControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;

    MemberDTO dummy_member(){
        return MemberDTO.builder()
                .id(1L)
                .name("nameTest")
                .pw("pwTest")
                .adr("adrTest")
                .nickname("nickNameTest")
                .build();
    }

    MemberDTO.MemberResponseDTO memberResponseDTO(){
        return MemberDTO.MemberResponseDTO.builder()
                .name("testName")
                .adr("testAdt")
                .nickname("testNick").build();
    }


    Map<String, String> inputMap(){
        Map<String, String> input = new HashMap<>();
        input.put("adr","adrTest");
        input.put("nickname","nickNameTest");
        return input;
    }
    @Test
    void ??????_??????() throws Exception{
        given(memberService.memberJoin(any())).willReturn(memberResponseDTO());

        mockMvc.perform(post("/api/v1/proup/member")   // perform get????????? ????????? ???
                .contentType(MediaType.APPLICATION_JSON)        // ????????? ????????? ?????????
                .content(objectMapper.writeValueAsString(dummy_member()))   // ????????? ??????
                ).andExpect(status().isOk())           // andExpect ~?????? ???????????? ???
                .andDo(document("memberJoin",    // andDo ~??? ??????
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedRequestFields(
                                fieldWithPath("name").description("????????? ?????????").type(JsonFieldType.STRING),
                                fieldWithPath("adr").description("????????? ??????").type(JsonFieldType.STRING),
                                fieldWithPath("nickname").description("????????? ?????????").type(JsonFieldType.STRING)
                        ),
                        relaxedResponseFields( //relaxed : ????????? ???????????? ???????????? ????????? ??????????????? prefix
                                fieldWithPath("name").description("????????? ?????????").type(JsonFieldType.STRING),
                                fieldWithPath("adr").description("????????? ??????").type(JsonFieldType.STRING),
                                fieldWithPath("nickname").description("????????? ?????????").type(JsonFieldType.STRING)
                        )
                ));
    }

    @Test
    void ??????_??????_????????????_?????????() throws Exception {
        // ????????? ?????? ??????
        given(memberService.memberDelete(any())).willReturn(true);

        mockMvc.perform(delete("/api/v1/proup/member/{id}", dummy_member().getId())
                ).andExpect(status().isOk())
                .andDo(document("memberDelete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("????????? index")
                        )

                ));
    }

    @Test
    void ??????_????????????_????????????_?????????() throws Exception {
        given(memberService.memberFind(any())).willReturn(memberResponseDTO());

        mockMvc.perform(get("/api/v1/proup/member/{id}", dummy_member().getId())
                ).andExpect(status().isOk())
                .andDo(document("memberFind",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("????????? index")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("name").description("????????? ?????????").type(JsonFieldType.STRING),
                                fieldWithPath("adr").description("????????? ??????").type(JsonFieldType.STRING),
                                fieldWithPath("nickname").description("????????? ?????????").type(JsonFieldType.STRING)
                        )
                ));
    }

    @Test
    void ????????????_??????_????????????_?????????() throws Exception {
        given(memberService.memberUpdate(any(), any())).willReturn(memberResponseDTO());

        mockMvc.perform(patch("/api/v1/proup/member/{id}", dummy_member().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputMap()))
                ).andExpect(status().isOk())
                .andDo(document("memberUpdate",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("????????? index")
                        ),
                        relaxedRequestFields(
                                fieldWithPath("adr").description("????????? ??????").type(JsonFieldType.STRING),
                                fieldWithPath("nickname").description("????????? ?????????").type(JsonFieldType.STRING)
                        ),
                        relaxedResponseFields(
                                fieldWithPath("name").description("????????? ?????????").type(JsonFieldType.STRING),
                                fieldWithPath("adr").description("????????? ??????").type(JsonFieldType.STRING),
                                fieldWithPath("nickname").description("????????? ?????????").type(JsonFieldType.STRING)
                        )
                ));

    }


}
