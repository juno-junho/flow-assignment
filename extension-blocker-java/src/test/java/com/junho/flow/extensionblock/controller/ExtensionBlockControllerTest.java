package com.junho.flow.extensionblock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junho.flow.extensionblock.service.ExtensionBlockService;
import com.junho.flow.extensionblock.service.request.FixedExtensionInfo;
import com.junho.flow.extensionblock.service.response.ExtensionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.formParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExtensionBlockController.class)
@AutoConfigureRestDocs
class ExtensionBlockControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ExtensionBlockService extensionBlockService;

    @Test
    @DisplayName("유저의 고정 확장자 목록을 조회한다")
    void getFixedExtensionInfo() throws Exception {
        // given
        Long userId = 1L;
        List<ExtensionResult> fixedExtensionResults = List.of(
                new ExtensionResult("bat", true),
                new ExtensionResult("cmd", true),
                new ExtensionResult("com", true),
                new ExtensionResult("cpl", true),
                new ExtensionResult("exe", false),
                new ExtensionResult("scr", false),
                new ExtensionResult("js", false)
        );
        given(extensionBlockService.getFixedExtensions(userId)).willReturn(fixedExtensionResults);

        // when, then
        mvc.perform(get("/api/v1/extension-blocks/fixed-extensions/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(7))
                .andExpect(jsonPath("$[0].extension").value("bat"))
                .andExpect(jsonPath("$[0].isChecked").value(true))
                .andExpect(jsonPath("$[1].extension").value("cmd"))
                .andExpect(jsonPath("$[1].isChecked").value(true))
                .andExpect(jsonPath("$[2].extension").value("com"))
                .andExpect(jsonPath("$[2].isChecked").value(true))
                .andExpect(jsonPath("$[3].extension").value("cpl"))
                .andExpect(jsonPath("$[3].isChecked").value(true))
                .andExpect(jsonPath("$[4].extension").value("exe"))
                .andExpect(jsonPath("$[4].isChecked").value(false))
                .andExpect(jsonPath("$[5].extension").value("scr"))
                .andExpect(jsonPath("$[5].isChecked").value(false))
                .andExpect(jsonPath("$[6].extension").value("js"))
                .andExpect(jsonPath("$[6].isChecked").value(false))
                .andDo(document("extension-blocks/getFixedExtensions",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("userId").description("유저 ID")
                                ),
                                responseFields(
                                        fieldWithPath("[]").description("고정 확장자 목록"),
                                        fieldWithPath("[].extension").description("고정 확장자"),
                                        fieldWithPath("[].isChecked").description("확장자 체크 여부")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저의 커스텀 확장자 목록을 조회한다")
    void getCustomExtensionInfo() throws Exception {
        // given
        Long userId = 1L;
        List<ExtensionResult> customExtensionResults = List.of(
                new ExtensionResult("png", true),
                new ExtensionResult("md", true),
                new ExtensionResult("mp4", true)
        );
        given(extensionBlockService.getCustomExtensions(userId)).willReturn(customExtensionResults);

        // when, then
        mvc.perform(get("/api/v1/extension-blocks/custom-extensions/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].extension").value("png"))
                .andExpect(jsonPath("$[1].extension").value("md"))
                .andExpect(jsonPath("$[2].extension").value("mp4"))
                .andDo(document("extension-blocks/getCustomExtensions",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("userId").description("유저 ID")
                                ),
                                responseFields(
                                        fieldWithPath("[]").description("커스텀 확장자 목록"),
                                        fieldWithPath("[].extension").description("커스텀 확장자")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저가 커스텀 확장자를 추가한다")
    void addCustomExtension() throws Exception {
        // given
        Long userId = 1L;
        String extensionInput = "PNG";
        String extensionOutput = extensionInput.toLowerCase();

        given(extensionBlockService.addCustomExtension(userId, extensionInput))
                .willReturn(new ExtensionResult(extensionOutput, true));

        // when, then
        mvc.perform(post("/api/v1/extension-blocks/custom-extensions/{userId}", userId)
                        .param("extension", extensionInput))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("extension-blocks/addCustomExtension",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("userId").description("유저 ID")
                                ),
                                formParameters(
                                        parameterWithName("extension").description("추가할 커스텀 확장자 (요청 시 대소문자 구문 x)")
                                ),
                                responseFields(
                                        fieldWithPath("extension").description("추가된 커스텀 확장자 (소문자)")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저가 파일을 업로드한다")
    void uploadFile() throws Exception {
        // given
        Long userId = 1L;
        MockMultipartFile testFile = new MockMultipartFile("file", "testFile.png", MediaType.IMAGE_PNG_VALUE, "testFile".getBytes(StandardCharsets.UTF_8));
        doNothing().when(extensionBlockService).uploadFile(userId, testFile);

        // when, then
        mvc.perform(multipart("/api/v1/extension-blocks/upload/{userId}", userId)
                        .file(testFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("extension-blocks/uploadFile",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("userId").description("유저 ID")
                                ),
                                requestParts(
                                        partWithName("file").description("업로드할 파일")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저의 고정 확장자에 대한 체크 여부를 변경한다")
    void updateFixedFileExtensionStatus() throws Exception {
        // given
        Long userId = 1L;
        boolean isChecked = true;
        FixedExtensionInfo fixedExtensionInfo = new FixedExtensionInfo("exe", isChecked);
        doNothing().when(extensionBlockService).checkFixedExtension(userId, fixedExtensionInfo);

        // when, then
        mvc.perform(patch("/api/v1/extension-blocks/custom-extensions/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(fixedExtensionInfo)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("extension-blocks/changeFixedExtensionStatus",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("userId").description("유저 ID")
                                ),
                                requestFields(
                                        fieldWithPath("extension").description("고정 확장자"),
                                        fieldWithPath("isChecked").description("확장자 제한 체크 여부")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저의 커스텀 확장자를 제거한다")
    void deleteCustomExtension() throws Exception {
        // given
        Long userId = 1L;
        String extension1 = "png";
        String extension2 = "jpeg";
        List<String> extensions = List.of(extension1, extension2);
        doNothing().when(extensionBlockService).deleteCustomExtension(userId, extensions);

        // when, then
        mvc.perform(delete("/api/v1/extension-blocks/custom-extensions/{userId}", userId)
                        .param("extension", extension1, extension2)
                )
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("extension-blocks/deleteCustomExtension",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("userId").description("유저 ID")
                                ),
                                formParameters(
                                        parameterWithName("extension").description("삭제할 확장자 목록")
                                )
                        )
                );
    }

}
