package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleDatabaseTestService;
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

@Tag(name = "/my-service/tk/sample/database-test APIs", description = "Database 에 대한 테스트 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/database-test")
public class MyServiceTkSampleDatabaseTestController {
    public MyServiceTkSampleDatabaseTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleDatabaseTestService service;


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


    ////
    @Operation(
            summary = "DB Rows 삭제 테스트 API",
            description = "테스트 테이블의 모든 Row 를 모두 삭제합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @DeleteMapping(
            path = "/rows",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteRowsSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "deleteLogically", description = "논리적 삭제 여부", example = "true")
            @RequestParam("deleteLogically")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean deleteLogically
    ) {
        service.deleteRowsSample(httpServletResponse, deleteLogically);
    }


    ////
    @Operation(
            summary = "DB Row 삭제 테스트",
            description = "테스트 테이블의 Row 하나를 삭제합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작"),
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(),
                    description = "Response Body 가 없습니다.\n\n" +
                            "Response Headers 를 확인하세요.",
                    headers = @Header(
                            name = "api-result-code",
                            description = "(Response Code 반환 원인) - Required\n\n" +
                                    "1 : index 에 해당하는 데이터가 데이터베이스에 존재하지 않습니다.\n\n",
                            schema = @Schema(type = "string")
                    )
            )
    })
    @DeleteMapping(
            path = "/row/{index}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteRowSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "index", description = "글 인덱스", example = "1") @PathVariable("index")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index,
            @Parameter(name = "deleteLogically", description = "논리적 삭제 여부", example = "true") @RequestParam("deleteLogically")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean deleteLogically
    ) {
        service.deleteRowSample(httpServletResponse, index, deleteLogically);
    }


    ////
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


    ////
    @Operation(
            summary = "DB 테이블의 random_num 컬럼 근사치 기준으로 정렬한 리스트 조회 API",
            description = "테이블의 row 중 random_num 컬럼과 num 파라미터의 값의 근사치로 정렬한 리스트 반환\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @GetMapping(path = "/rows/order-by-random-num-nearest", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowsOrderByRandomNumSampleOutputVo selectRowsOrderByRandomNumSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "num", description = "근사값 정렬의 기준", example = "1")
            @RequestParam("num")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer num
    ) {
        return service.selectRowsOrderByRandomNumSample(httpServletResponse, num);
    }

    public record SelectRowsOrderByRandomNumSampleOutputVo(
            @Schema(description = "아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("testEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> testEntityVoList
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

                @Schema(description = "기준과의 절대거리", requiredMode = Schema.RequiredMode.REQUIRED, example = "34")
                @JsonProperty("distance")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer distance
        ) {
        }
    }


    ////
    @Operation(
            summary = "DB 테이블의 row_create_date 컬럼 근사치 기준으로 정렬한 리스트 조회 API",
            description = "테이블의 row 중 row_create_date 컬럼과 dateString 파라미터의 값의 근사치로 정렬한 리스트 반환\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/rows/order-by-create-date-nearest",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowsOrderByRowCreateDateSampleOutputVo selectRowsOrderByRowCreateDateSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(
                    name = "dateString",
                    description = "원하는 날짜(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @RequestParam("dateString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String dateString
    ) {
        return service.selectRowsOrderByRowCreateDateSample(httpServletResponse, dateString);
    }

    public record SelectRowsOrderByRowCreateDateSampleOutputVo(
            @JsonProperty("testEntityVoList")
            @Schema(description = "아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> testEntityVoList
    ) {
        public record TestEntityVo(
                @JsonProperty("uid")
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @JsonProperty("content")
                @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String content,

                @JsonProperty("randomNum")
                @Schema(description = "자동 생성 숫자", requiredMode = Schema.RequiredMode.REQUIRED, example = "21345")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer randomNum,

                @JsonProperty("testDatetime")
                @Schema(description = "테스트용 일시 데이터(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String testDatetime,

                @JsonProperty("createDate")
                @Schema(description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String createDate,

                @JsonProperty("updateDate")
                @Schema(description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String updateDate,

                @JsonProperty("timeDiffMicroSec")
                @Schema(description = "기준과의 절대차이(마이크로 초)", requiredMode = Schema.RequiredMode.REQUIRED, example = "34")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long timeDiffMicroSec
        ) {
        }
    }


    ////
    @Operation(
            summary = "DB Rows 조회 테스트 (페이징)",
            description = "테스트 테이블의 Rows 를 페이징하여 반환합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/rows/paging",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowsPageSampleOutputVo selectRowsPageSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "page", description = "원하는 페이지(1 부터 시작)", example = "1")
            @RequestParam("page")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Parameter(name = "pageElementsCount", description = "페이지 아이템 개수", example = "10")
            @RequestParam("pageElementsCount")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount
    ) {
        return service.selectRowsPageSample(httpServletResponse, page, pageElementsCount);
    }

    public record SelectRowsPageSampleOutputVo(
            @JsonProperty("totalElements")
            @Schema(description = "아이템 전체 개수", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long totalElements,

            @JsonProperty("testEntityVoList")
            @Schema(description = "아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<TestEntityVo> testEntityVoList
    ) {
        public record TestEntityVo(
                @JsonProperty("uid")
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @JsonProperty("content")
                @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String content,

                @JsonProperty("randomNum")
                @Schema(description = "자동 생성 숫자", requiredMode = Schema.RequiredMode.REQUIRED, example = "23456")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer randomNum,

                @JsonProperty("testDatetime")
                @Schema(description = "테스트용 일시 데이터(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String testDatetime,

                @JsonProperty("createDate")
                @Schema(description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String createDate,

                @JsonProperty("updateDate")
                @Schema(description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String updateDate
        ) {
        }
    }


    ////
    @Operation(
            summary = "DB Rows 조회 테스트 (네이티브 쿼리 페이징)",
            description = "테스트 테이블의 Rows 를 네이티브 쿼리로 페이징하여 반환합니다.\n\n" +
                    "num 을 기준으로 근사치 정렬도 수행합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/rows/native-paging",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowsNativeQueryPageSampleOutputVo selectRowsNativeQueryPageSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "page", description = "원하는 페이지(1 부터 시작)", example = "1")
            @RequestParam("page")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Parameter(name = "pageElementsCount", description = "페이지 아이템 개수", example = "10")
            @RequestParam("pageElementsCount")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount,
            @Parameter(name = "num", description = "근사값의 기준", example = "1")
            @RequestParam("num")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer num
    ) {
        return service.selectRowsNativeQueryPageSample(httpServletResponse, page, pageElementsCount, num);
    }

    public record SelectRowsNativeQueryPageSampleOutputVo(
            @Schema(description = "아이템 전체 개수", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
            @JsonProperty("totalElements")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long totalElements,
            @Schema(description = "아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("testEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> testEntityVoList
    ) {
        public record TestEntityVo(
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
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
                @Schema(description = "기준과의 절대거리", requiredMode = Schema.RequiredMode.REQUIRED, example = "34")
                @JsonProperty("distance")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer distance
        ) {
        }
    }


    ////
    @Operation(
            summary = "DB Row 수정 테스트",
            description = "테스트 테이블의 Row 하나를 수정합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(),
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : testTableUid 에 해당하는 정보가 데이터베이스에 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PatchMapping(
            path = "/row/{testTableUid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public UpdateRowSampleOutputVo updateRowSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "testTableUid", description = "test 테이블의 uid", example = "1")
            @PathVariable("testTableUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            UpdateRowSampleInputVo inputVo
    ) {
        return service.updateRowSample(httpServletResponse, testTableUid, inputVo);
    }

    public record UpdateRowSampleInputVo(
            @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트 수정글입니다.")
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,
            @Schema(description = "원하는 날짜(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
            @JsonProperty("dateString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String dateString
    ) {
    }

    public record UpdateRowSampleOutputVo(
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
            String updateDate
    ) {
    }


    ////
    @Operation(
            summary = "DB Row 수정 테스트 (네이티브 쿼리)",
            description = "테스트 테이블의 Row 하나를 네이티브 쿼리로 수정합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(),
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : testTableUid 에 해당하는 정보가 데이터베이스에 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PatchMapping(
            path = "/row/{testTableUid}/native-query",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public void updateRowNativeQuerySample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "testTableUid", description = "test 테이블의 uid", example = "1")
            @PathVariable("testTableUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            UpdateRowNativeQuerySampleInputVo inputVo
    ) {
        service.updateRowNativeQuerySample(httpServletResponse, testTableUid, inputVo);
    }

    public record UpdateRowNativeQuerySampleInputVo(
            @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트 수정글입니다.")
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,
            @Schema(description = "원하는 날짜(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
            @JsonProperty("dateString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String dateString
    ) {
    }


    ////
    @Operation(
            summary = "DB 정보 검색 테스트",
            description = "글 본문 내용중 searchKeyword 가 포함된 rows 를 검색하여 반환합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작")
            }
    )
    @GetMapping(
            path = "/search-content",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowWhereSearchingKeywordSampleOutputVo selectRowWhereSearchingKeywordSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "page", description = "원하는 페이지(1 부터 시작)", example = "1")
            @RequestParam("page")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Parameter(name = "pageElementsCount", description = "페이지 아이템 개수", example = "10")
            @RequestParam("pageElementsCount")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount,
            @Parameter(name = "searchKeyword", description = "검색어", example = "테스트")
            @RequestParam("searchKeyword")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String searchKeyword
    ) {
        return service.selectRowWhereSearchingKeywordSample(httpServletResponse, page, pageElementsCount, searchKeyword);
    }

    public record SelectRowWhereSearchingKeywordSampleOutputVo(
            @Schema(description = "아이템 전체 개수", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
            @JsonProperty("totalElements")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long totalElements,

            @Schema(description = "아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("testEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> testEntityVoList
    ) {
        public record TestEntityVo(
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
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
                String updateDate
        ) {
        }
    }


    ////
    @Operation(
            summary = "트랜젝션 동작 테스트",
            description = "정보 입력 후 Exception 이 발생했을 때 롤백되어 데이터가 저장되지 않는지를 테스트하는 API\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작")
            }
    )
    @PostMapping(
            path = "/transaction-rollback-sample",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void transactionTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.transactionTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "트랜젝션 비동작 테스트",
            description = "트랜젝션 처리를 하지 않았을 때, DB 정보 입력 후 Exception 이 발생 했을 때 의 테스트 API\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작")
            }
    )
    @PostMapping(
            path = "/no-transaction-exception-sample",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void nonTransactionTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.nonTransactionTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "트랜젝션 비동작 테스트(try-catch)",
            description = "에러 발생문이 try-catch 문 안에 있을 때, DB 정보 입력 후 Exception 이 발생 해도 트랜젝션이 동작하지 않는지에 대한 테스트 API\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작")
            }
    )
    @PostMapping(
            path = "/try-catch-no-transaction-exception-sample",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void tryCatchNonTransactionTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.tryCatchNonTransactionTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "DB Rows 조회 테스트 (중복 없는 네이티브 쿼리 페이징)",
            description = "테스트 테이블의 Rows 를 네이티브 쿼리로 중복없이 페이징하여 반환합니다.\n\n" +
                    "num 을 기준으로 근사치 정렬도 수행합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작")
            }
    )
    @GetMapping(
            path = "/rows/native-paging-no-duplication",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowsNoDuplicatePagingSampleOutputVo selectRowsNoDuplicatePagingSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "lastItemUid", description = "이전 페이지에서 받은 마지막 아이템의 Uid (첫 요청이면 null)", example = "1")
            @RequestParam("lastItemUid")
            @Nullable @org.jetbrains.annotations.Nullable
            Long lastItemUid,
            @Parameter(name = "pageElementsCount", description = "페이지 아이템 개수", example = "10")
            @RequestParam("pageElementsCount")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount
    ) {
        return service.selectRowsNoDuplicatePagingSample(httpServletResponse, lastItemUid, pageElementsCount);
    }

    public record SelectRowsNoDuplicatePagingSampleOutputVo(
            @Schema(description = "아이템 전체 개수", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
            @JsonProperty("totalElements")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long totalElements,

            @Schema(description = "아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("testEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> testEntityVoList
    ) {
        public record TestEntityVo(
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
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
                String updateDate
        ) {
        }
    }


    ////
    @Operation(
            summary = "DB Rows 조회 테스트 (카운팅)",
            description = "테스트 테이블의 Rows 를 카운팅하여 반환합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @GetMapping(
            path = "/rows/counting",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowsCountSampleOutputVo selectRowsCountSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectRowsCountSample(httpServletResponse);
    }

    public record SelectRowsCountSampleOutputVo(
            @Schema(description = "아이템 전체 개수", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
            @JsonProperty("totalElements")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long totalElements
    ) {
    }


    ////
    @Operation(
            summary = "DB Rows 조회 테스트 (네이티브 카운팅)",
            description = "테스트 테이블의 Rows 를 네이티브 쿼리로 카운팅하여 반환합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @GetMapping(
            path = "/rows/native-counting",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowsCountByNativeQuerySampleOutputVo selectRowsCountByNativeQuerySample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectRowsCountByNativeQuerySample(httpServletResponse);
    }

    public record SelectRowsCountByNativeQuerySampleOutputVo(
            @Schema(description = "아이템 전체 개수", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
            @JsonProperty("totalElements")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long totalElements
    ) {
    }


    ////
    @Operation(
            summary = "DB Row 조회 테스트 (네이티브)",
            description = "테스트 테이블의 Row 하나를 네이티브 쿼리로 반환합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작"),
            @ApiResponse(responseCode = "204", content = @Content(), description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.")
    })
    @GetMapping(
            path = "/row/native/{testTableUid}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRowByNativeQuerySampleOutputVo selectRowByNativeQuerySample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "testTableUid", description = "test 테이블의 uid", example = "1")
            @PathVariable("testTableUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid
    ) {
        return service.selectRowByNativeQuerySample(httpServletResponse, testTableUid);
    }

    public record SelectRowByNativeQuerySampleOutputVo(
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
            String updateDate
    ) {
    }


    ////
    @Operation(
            summary = "유니크 테스트 테이블 Row 입력 API",
            description = "유니크 테스트 테이블에 Row 를 입력합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @PostMapping(
            path = "/unique-test-table",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public InsertUniqueTestTableRowSampleOutputVo insertUniqueTestTableRowSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertUniqueTestTableRowSampleInputVo inputVo
    ) {
        return service.insertUniqueTestTableRowSample(httpServletResponse, inputVo);
    }

    public record InsertUniqueTestTableRowSampleInputVo(
            @Schema(description = "유니크 값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("uniqueValue")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer uniqueValue
    ) {
    }

    public record InsertUniqueTestTableRowSampleOutputVo(
            @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
            @JsonProperty("uid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long uid,

            @Schema(description = "유니크 값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("uniqueValue")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer uniqueValue,

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


    ////
    @Operation(
            summary = "유니크 테스트 테이블 Rows 조회 테스트",
            description = "유니크 테스트 테이블의 모든 Rows 를 반환합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @GetMapping(
            path = "/unique-test-table/all",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectUniqueTestTableRowsSampleOutputVo selectUniqueTestTableRowsSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectUniqueTestTableRowsSample(httpServletResponse);
    }

    public record SelectUniqueTestTableRowsSampleOutputVo(
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

                @Schema(description = "유니크 값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uniqueValue")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer uniqueValue,

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


    ////
    @Operation(
            summary = "유니크 테스트 테이블 Row 수정 테스트",
            description = "유니크 테스트 테이블의 Row 하나를 수정합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작"),
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(),
                    description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                    headers = {
                            @Header(
                                    name = "api-result-code",
                                    description = "(Response Code 반환 원인) - Required\n\n1 : uniqueTestTableUid 에 해당하는 정보가 데이터베이스에 존재하지 않습니다.\n\n2 : uniqueValue 와 일치하는 정보가 이미 데이터베이스에 존재합니다.\n\n",
                                    schema = @Schema(type = "string")
                            )
                    }
            )
    })
    @PatchMapping(
            path = "/unique-test-table/{uniqueTestTableUid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public UpdateUniqueTestTableRowSampleOutputVo updateUniqueTestTableRowSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "uniqueTestTableUid", description = "unique test 테이블의 uid", example = "1")
            @PathVariable("uniqueTestTableUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long uniqueTestTableUid,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            UpdateUniqueTestTableRowSampleInputVo inputVo
    ) {
        return service.updateUniqueTestTableRowSample(httpServletResponse, uniqueTestTableUid, inputVo);
    }

    public record UpdateUniqueTestTableRowSampleInputVo(
            @Schema(description = "유니크 값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("uniqueValue")
            Integer uniqueValue
    ) {
    }

    public record UpdateUniqueTestTableRowSampleOutputVo(
            @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
            @JsonProperty("uid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long uid,

            @Schema(description = "유니크 값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("uniqueValue")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer uniqueValue,

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
            summary = "유니크 테스트 테이블 Row 삭제 테스트",
            description = "유니크 테스트 테이블의 Row 하나를 삭제합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작"),
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(),
                    description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                    headers = {
                            @Header(
                                    name = "api-result-code",
                                    description = "(Response Code 반환 원인) - Required\n\n1 : index 에 해당하는 데이터가 데이터베이스에 존재하지 않습니다.\n\n",
                                    schema = @Schema(type = "string")
                            )
                    }
            )
    })
    @DeleteMapping(
            path = "/unique-test-table/{index}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteUniqueTestTableRowSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "index", description = "글 인덱스", example = "1")
            @PathVariable("index")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    ) {
        service.deleteUniqueTestTableRowSample(httpServletResponse, index);
    }


    ////
    @Operation(
            summary = "외래키 부모 테이블 Row 입력 API",
            description = "외래키 부모 테이블에 Row 를 입력합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @PostMapping(
            path = "/fk-parent",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public InsertFkParentRowSampleOutputVo insertFkParentRowSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertFkParentRowSampleInputVo inputVo
    ) {
        return service.insertFkParentRowSample(httpServletResponse, inputVo);
    }

    public record InsertFkParentRowSampleInputVo(
            @Schema(description = "외래키 테이블 부모 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "홍길동")
            @JsonProperty("fkParentName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fkParentName
    ) {
    }

    public record InsertFkParentRowSampleOutputVo(
            @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
            @JsonProperty("uid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long uid,

            @Schema(description = "외래키 테이블 부모 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "홍길동")
            @JsonProperty("fkParentName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fkParentName,

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
            summary = "외래키 부모 테이블 아래에 자식 테이블의 Row 입력 API",
            description = "외래키 부모 테이블의 아래에 자식 테이블의 Row 를 입력합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작"),
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(),
                    description = "Response Body 가 없습니다.\n\n" +
                            "Response Headers 를 확인하세요.",
                    headers = @io.swagger.v3.oas.annotations.headers.Header(
                            name = "api-result-code",
                            description = "(Response Code 반환 원인) - Required\n\n" +
                                    "1 : parentUid 에 해당하는 데이터가 존재하지 않습니다.\n\n",
                            schema = @Schema(type = "string")
                    )
            )
    })
    @PostMapping(
            path = "/fk-parent/{parentUid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public InsertFkChildRowSampleOutputVo insertFkChildRowSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "parentUid", description = "외래키 부모 테이블 고유번호", example = "1")
            @PathVariable("parentUid") Long parentUid,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertFkChildRowSampleInputVo inputVo) {
        return service.insertFkChildRowSample(httpServletResponse, parentUid, inputVo);
    }

    public record InsertFkChildRowSampleInputVo(
            @Schema(description = "외래키 테이블 자식 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "홍길동")
            @JsonProperty("fkChildName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fkChildName
    ) {
    }

    public record InsertFkChildRowSampleOutputVo(
            @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
            @JsonProperty("uid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long uid,
            @Schema(description = "외래키 테이블 부모 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "홍길동")
            @JsonProperty("fkParentName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fkParentName,
            @Schema(description = "외래키 테이블 자식 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "홍길동")
            @JsonProperty("fkChildName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fkChildName,
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
            summary = "외래키 관련 테이블 Rows 조회 테스트",
            description = "외래키 관련 테이블의 모든 Rows 를 반환합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @GetMapping(
            path = "/fk-table/all",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectFkTestTableRowsSampleOutputVo selectFkTestTableRowsSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectFkTestTableRowsSample(httpServletResponse);
    }

    public record SelectFkTestTableRowsSampleOutputVo(
            @Schema(description = "부모 아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("parentEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull ParentEntityVo> parentEntityVoList
    ) {
        public record ParentEntityVo(
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,
                @Schema(description = "부모 테이블 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("parentName")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String parentName,
                @Schema(description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("createDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String createDate,
                @Schema(description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("updateDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String updateDate,
                @Schema(description = "부모 테이블에 속하는 자식 테이블 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("childEntityList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull ChildEntityVo> childEntityList
        ) {
            public record ChildEntityVo(
                    @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                    @JsonProperty("uid")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Long uid,
                    @Schema(description = "자식 테이블 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                    @JsonProperty("childName")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String childName,
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
    }


    ////
    @Operation(
            summary = "외래키 관련 테이블 Rows 조회 테스트(Native Join)",
            description = "외래키 관련 테이블의 모든 Rows 를 Native Query 로 Join 하여 반환합니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @GetMapping(
            path = "/fk-table-native-join",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectFkTestTableRowsByNativeQuerySampleDot1OutputVo selectFkTestTableRowsByNativeQuerySample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectFkTestTableRowsByNativeQuerySample(httpServletResponse);
    }

    public record SelectFkTestTableRowsByNativeQuerySampleDot1OutputVo(
            @Schema(description = "자식 아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("childEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull ChildEntityVo> childEntityVoList
    ) {
        public record ChildEntityVo(
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,
                @Schema(description = "자식 테이블 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("childName")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String childName,
                @Schema(description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("createDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String createDate,
                @Schema(description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("updateDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String updateDate,
                @Schema(description = "부모 테이블 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("parentUid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long parentUid,
                @Schema(description = "부모 테이블 이름", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("parentName")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String parentName
        ) {
        }
    }


    ////
    @Operation(
            summary = "Native Query 반환값 테스트",
            description = "Native Query Select 문에서 IF, CASE 등의 문구에서 반환되는 값들을 받는 예시\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/native-query-return",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public GetNativeQueryReturnValueTestOutputVo getNativeQueryReturnValueTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "inputVal", description = "Native Query 비교문에 사용되는 파라미터", example = "true")
            @RequestParam("inputVal")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean inputVal
    ) {
        return service.getNativeQueryReturnValueTest(httpServletResponse, inputVal);
    }

    public record GetNativeQueryReturnValueTestOutputVo(
            @Schema(description = "Select 문에서 직접적으로 true 를 반환한 예시", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("normalBoolValue")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean normalBoolValue,

            @Schema(description = "Select 문에서 (1=1) 과 같이 비교한 결과를 반환한 예시", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("funcBoolValue")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean funcBoolValue,

            @Schema(description = "Select 문에서 if 문의 결과를 반환한 예시", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("ifBoolValue")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean ifBoolValue,

            @Schema(description = "Select 문에서 case 문의 결과를 반환한 예시", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("caseBoolValue")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean caseBoolValue,

            @Schema(description = "Select 문에서 테이블의 Boolean 컬럼의 결과를 반환한 예시", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("tableColumnBoolValue")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean tableColumnBoolValue
    ) {
    }


    ////
    @Operation(
            summary = "SQL Injection 테스트",
            description = "각 상황에서 SQL Injection 공격이 유효한지 확인하기 위한 테스트\n\n" +
                    "SELECT 문에서, WHERE 에, content = :searchKeyword 를 하여,\n\n" +
                    " 인젝션이 일어나는 키워드를 입력시 인젝션이 먹히는지를 확인할 것입니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/sql-injection-test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SqlInjectionTestOutputVo sqlInjectionTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "searchKeyword", description = "Select 문 검색에 사용되는 키워드", example = "test OR 1 = 1")
            @RequestParam("searchKeyword")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String searchKeyword
    ) {
        return service.sqlInjectionTest(httpServletResponse, searchKeyword);
    }

    public record SqlInjectionTestOutputVo(
            @Schema(description = "JpaRepository 로 조회했을 때의 아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("jpaRepositoryResultList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> jpaRepositoryResultList,

            @Schema(description = "JPQL 로 조회했을 때의 아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("jpqlResultList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> jpqlResultList,

            @Schema(description = "Native Query 로 조회했을 때의 아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("nativeQueryResultList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull TestEntityVo> nativeQueryResultList
    ) {

        @Schema(description = "아이템")
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
                String updateDate
        ) {
        }
    }


    ////
    @Operation(
            summary = "외래키 관련 테이블 Rows 조회 (네이티브 쿼리, 부모 테이블을 자식 테이블의 가장 최근 데이터만 Join)",
            description = "외래키 관련 테이블의 모든 Rows 를 반환합니다.\n\n" +
                    "부모 테이블을 Native Query 로 조회할 때, 부모 테이블을 가리키는 자식 테이블들 중 가장 최신 데이터만 Join 하는 예시입니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/fk-table-latest-join",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectFkTableRowsWithLatestChildSampleOutputVo selectFkTableRowsWithLatestChildSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectFkTableRowsWithLatestChildSample(httpServletResponse);
    }

    public record SelectFkTableRowsWithLatestChildSampleOutputVo(
            @Schema(description = "부모 아이템 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("parentEntityVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull ParentEntityVo> parentEntityVoList
    ) {

        @Schema(description = "부모 아이템")
        public record ParentEntityVo(
                @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "부모 테이블 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("parentName")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String parentName,

                @Schema(description = "글 작성일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("createDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String createDate,

                @Schema(description = "글 수정일(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
                @JsonProperty("updateDate")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String updateDate,

                @Schema(description = "부모 테이블에 속하는 자식 테이블들 중 가장 최신 데이터")
                @JsonProperty("latestChildEntity")
                @Nullable @org.jetbrains.annotations.Nullable
                ChildEntityVo latestChildEntity
        ) {

            @Schema(description = "자식 아이템")
            public record ChildEntityVo(
                    @Schema(description = "글 고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
                    @JsonProperty("uid")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Long uid,

                    @Schema(description = "자식 테이블 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                    @JsonProperty("childName")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String childName,

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
    }


    ////
    @Operation(
            summary = "외래키 자식 테이블 Row 삭제 테스트",
            description = "외래키 자식 테이블의 Row 하나를 삭제합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content,
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n1 : index 에 해당하는 데이터가 데이터베이스에 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @DeleteMapping(
            path = "/fk-child/{index}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteFkChildRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @PathVariable("index")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    ) {
        service.deleteFkChildRowSample(httpServletResponse, index);
    }


    ////
    @Operation(
            summary = "외래키 부모 테이블 Row 삭제 테스트 (Cascade 기능 확인)",
            description = "외래키 부모 테이블의 Row 하나를 삭제합니다.\n\nCascade 설정을 했으므로 부모 테이블이 삭제되면 해당 부모 테이블을 참조중인 다른 모든 자식 테이블들이 삭제되어야 합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content,
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n1 : index 에 해당하는 데이터가 데이터베이스에 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @DeleteMapping(
            path = "/fk-parent/{index}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteFkParentRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @PathVariable("index")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    ) {
        service.deleteFkParentRowSample(httpServletResponse, index);
    }


    ////
    @Operation(
            summary = "외래키 테이블 트랜젝션 동작 테스트",
            description = "외래키 테이블에 정보 입력 후 Exception 이 발생했을 때 롤백되어 데이터가 저장되지 않는지를 테스트하는 API\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @PostMapping(
            path = "/fk-transaction-rollback-sample",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void fkTableTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.fkTableTransactionTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "외래키 테이블 트랜젝션 비동작 테스트",
            description = "외래키 테이블의 트랜젝션 처리를 하지 않았을 때, DB 정보 입력 후 Exception 이 발생 했을 때 의 테스트 API\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @PostMapping(
            path = "/fk-no-transaction-exception-sample",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void fkTableNonTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.fkTableNonTransactionTest(httpServletResponse);
    }
}