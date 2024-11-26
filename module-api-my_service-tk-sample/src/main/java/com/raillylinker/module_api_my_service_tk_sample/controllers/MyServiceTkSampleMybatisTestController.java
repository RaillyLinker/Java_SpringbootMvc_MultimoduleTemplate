package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleMybatisTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "/my-service/tk/sample/mybatis-test APIs", description = "Mybatis 에 대한 테스트 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/mybatis-test")
public class MyServiceTkSampleMybatisTestController {
    public MyServiceTkSampleMybatisTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMybatisTestService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleMybatisTestService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "DB Row 입력 테스트 API",
            description = "테스트 테이블에 Row 를 입력합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/row",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public InsertDataSampleOutputVo insertDataSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertDataSampleInputVo inputVo
    ) {
        return service.insertDataSample(httpServletResponse, inputVo);
    }

    public record InsertDataSampleInputVo(
            @Schema(
                    description = "글 본문",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "테스트 텍스트입니다."
            )
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,

            @Schema(
                    description = "원하는 날짜(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @JsonProperty("dateString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String dateString
    ) {
    }

    public record InsertDataSampleOutputVo(
            @Schema(
                    description = "글 고유번호",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1234"
            )
            @JsonProperty("uid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long uid,

            @Schema(
                    description = "글 본문",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "테스트 텍스트입니다."
            )
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,

            @Schema(
                    description = "자동 생성 숫자",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "21345"
            )
            @JsonProperty("randomNum")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer randomNum,

            @Schema(
                    description = "테스트용 일시 데이터(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @JsonProperty("testDatetime")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String testDatetime,

            @Schema(
                    description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @JsonProperty("createDate")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String createDate,

            @Schema(
                    description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @JsonProperty("updateDate")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String updateDate,

            @Schema(
                    description = "글 삭제일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z, Null 이면 /)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "/"
            )
            @JsonProperty("deleteDate")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String deleteDate
    ) {
    }


    /// /
    @Operation(
            summary = "DB Rows 조회 테스트",
            description = "테스트 테이블의 모든 Rows 를 반환합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @GetMapping(
            path = "/rows",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowsSampleOutputVo selectRowsSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectRowsSample(httpServletResponse);
    }

    public record SelectRowsSampleOutputVo(
            @Schema(description = "아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("testEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> testEntityVoList,

            @Schema(description = "논리적으로 제거된 아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("logicalDeleteEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> logicalDeleteEntityVoList
    ) {
        public record TestEntityVo(
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
                @JsonProperty("content")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String content,

                @Schema(description = "자동 생성 숫자", requiredMode = Schema.RequiredMode.REQUIRED, example = "21345")
                @JsonProperty("randomNum")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer randomNum,

                @Schema(description = "테스트용 일시 데이터(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("testDatetime")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String testDatetime,

                @Schema(description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("createDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String createDate,

                @Schema(description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("updateDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String updateDate,

                @Schema(description = "글 삭제일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z, Null 이면 /)", requiredMode = Schema.RequiredMode.REQUIRED, example = "/")
                @JsonProperty("deleteDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String deleteDate
        ) {
        }
    }
}