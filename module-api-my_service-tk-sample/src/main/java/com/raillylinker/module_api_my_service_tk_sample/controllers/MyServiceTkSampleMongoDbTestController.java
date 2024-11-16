package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleMongoDbTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "/my-service/tk/sample/mongodb-test APIs", description = "MongoDB 에 대한 테스트 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/mongodb-test")
public class MyServiceTkSampleMongoDbTestController {
    public MyServiceTkSampleMongoDbTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMongoDbTestService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleMongoDbTestService service;


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Operation(
            summary = "DB document 입력 테스트 API",
            description = "테스트 테이블에 document 를 입력합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @PostMapping(
            path = "/test-document",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public InsertDocumentTestOutputVo insertDocumentTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertDocumentTestInputVo inputVo
    ) {
        return service.insertDocumentTest(httpServletResponse, inputVo);
    }

    public record InsertDocumentTestInputVo(
            @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,

            @Schema(description = "Nullable 값", example = "Not Null")
            @JsonProperty("nullableValue")
            @Nullable @org.jetbrains.annotations.Nullable
            String nullableValue
    ) {
    }

    public record InsertDocumentTestOutputVo(
            @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
            @JsonProperty("uid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String uid,

            @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,

            @Schema(description = "Nullable 값", example = "Not Null")
            @JsonProperty("nullableValue")
            @Nullable @org.jetbrains.annotations.Nullable
            String nullableValue,

            @Schema(description = "자동 생성 숫자", requiredMode = Schema.RequiredMode.REQUIRED, example = "21345")
            @JsonProperty("randomNum")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer randomNum,

            @Schema(description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
            @JsonProperty("createDate")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String createDate,

            @Schema(description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
            @JsonProperty("updateDate")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String updateDate
    ) {
    }


    ////
    @Operation(
            summary = "DB Rows 삭제 테스트 API",
            description = "테스트 테이블의 모든 Row 를 모두 삭제합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @DeleteMapping(
            path = "/test-document",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteAllDocumentTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.deleteAllDocumentTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "DB Row 삭제 테스트",
            description = "테스트 테이블의 Row 하나를 삭제합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작"),
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(),
                    description = "Response Body 가 없습니다.\n\n" +
                            "Response Headers 를 확인하세요.",
                    headers = @Header(
                            name = "api-result-code",
                            description = "(Response Code 반환 원인) - Required\n\n" +
                                    "1 : id 에 해당하는 데이터가 데이터베이스에 존재하지 않습니다.\n\n",
                            schema = @Schema(type = "string")
                    )
            )
    })
    @DeleteMapping(
            path = "/test-document/{id}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteDocumentTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "id", description = "글 Id", example = "1")
            @PathVariable("id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    ) {
        service.deleteDocumentTest(httpServletResponse, id);
    }


    ////
    @Operation(
            summary = "DB Rows 조회 테스트",
            description = "테스트 테이블의 모든 Rows 를 반환합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @GetMapping(
            path = "/test-document",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectAllDocumentsTestOutputVo selectAllDocumentsTest(
            @Parameter(hidden = true) HttpServletResponse httpServletResponse
    ) {
        return service.selectAllDocumentsTest(httpServletResponse);
    }

    public record SelectAllDocumentsTestOutputVo(
            @Schema(description = "아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("testEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> testEntityVoList
    ) {

        public record TestEntityVo(
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String uid,

                @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
                @JsonProperty("content")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String content,

                @Schema(description = "Nullable 값", example = "Not Null")
                @JsonProperty("nullableValue")
                @Nullable @org.jetbrains.annotations.Nullable
                String nullableValue,

                @Schema(description = "자동 생성 숫자", requiredMode = Schema.RequiredMode.REQUIRED, example = "21345")
                @JsonProperty("randomNum")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer randomNum,

                @Schema(description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("createDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String createDate,

                @Schema(description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("updateDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String updateDate
        ) {
        }
    }


    ////
    @Operation(
            summary = "트랜젝션 동작 테스트",
            description = "정보 입력 후 Exception 이 발생했을 때 롤백되어 데이터가 저장되지 않는지를 테스트하는 API\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @PostMapping(
            path = "/transaction-rollback-sample",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void transactionRollbackTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.transactionRollbackTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "트랜젝션 비동작 테스트",
            description = "트랜젝션 처리를 하지 않았을 때, DB 정보 입력 후 Exception 이 발생 했을 때 의 테스트 API\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @PostMapping(
            path = "/no-transaction-exception-sample",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void noTransactionRollbackTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.noTransactionRollbackTest(httpServletResponse);
    }
}
