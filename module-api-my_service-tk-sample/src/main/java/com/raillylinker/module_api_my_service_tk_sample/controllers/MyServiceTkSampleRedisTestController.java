package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleRedisTestService;
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

// Key - Value 형식으로 저장되는 Redis Wrapper 를 사용하여 Database 의 Row 를 모사할 수 있으며,
// 이를 통해 데이터베이스 결과에 대한 캐싱을 구현할 수 있습니다.
/*
    !!!
    테스트를 하고 싶다면, 도커를 설치하고,
    cmd 를 열어,
    프로젝트 폴더 내의 external_files/docker/redis_docker 로 이동 후,
    명령어.txt 에 적힌 명령어를 입력하여 Redis 를 실행시킬 수 있습니다.
    !!!
 */
@Tag(name = "/my-service/tk/sample/redis-test APIs", description = "Redis 에 대한 테스트 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/redis-test")
public class MyServiceTkSampleRedisTestController {
    public MyServiceTkSampleRedisTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRedisTestService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleRedisTestService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "Redis Key-Value 입력 테스트",
            description = "Redis 테이블에 Key-Value 를 입력합니다.\n\n"
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
            path = "/test",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void insertRedisKeyValueTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertRedisKeyValueTestInputVo inputVo
    ) {
        service.insertRedisKeyValueTest(httpServletResponse, inputVo);
    }

    public record InsertRedisKeyValueTestInputVo(
            @Schema(description = "redis 키", requiredMode = Schema.RequiredMode.REQUIRED, example = "test_key")
            @JsonProperty("key")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key,

            @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,

            @Schema(description = "데이터 만료시간(밀리 초, null 이라면 무한정)", example = "12000")
            @JsonProperty("expirationMs")
            @Nullable @org.jetbrains.annotations.Nullable
            Long expirationMs
    ) {
    }


    ////
    @Operation(
            summary = "Redis Key-Value 조회 테스트",
            description = "Redis Table 에 저장된 Key-Value 를 조회합니다.\n\n"
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
                                                    "1 : key 에 저장된 데이터가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectRedisValueSampleOutputVo selectRedisValueSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "key", description = "redis 키", example = "test_key")
            @RequestParam("key")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key
    ) {
        return service.selectRedisValueSample(httpServletResponse, key);
    }

    public record SelectRedisValueSampleOutputVo(
            @Schema(description = "Table 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "Redis1_Test")
            @JsonProperty("tableName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String tableName,

            @Schema(description = "Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "testing")
            @JsonProperty("key")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key,

            @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
            @JsonProperty("content")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content,

            @Schema(description = "데이터 만료시간(밀리 초, -1 이라면 무한정)", requiredMode = Schema.RequiredMode.REQUIRED, example = "12000")
            @JsonProperty("expirationMs")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long expirationMs
    ) {
    }


    ////
    @Operation(
            summary = "Redis Key-Value 모두 조회 테스트",
            description = "Redis Table 에 저장된 모든 Key-Value 를 조회합니다.\n\n"
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
            path = "/tests",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectAllRedisKeyValueSampleOutputVo selectAllRedisKeyValueSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectAllRedisKeyValueSample(httpServletResponse);
    }

    public record SelectAllRedisKeyValueSampleOutputVo(
            @Schema(description = "Table 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "Redis1_Test")
            @JsonProperty("tableName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String tableName,

            @Schema(description = "Key-Value 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("keyValueList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull KeyValueVo> keyValueList
    ) {
        @Schema(description = "Key-Value 객체")
        public record KeyValueVo(
                @Schema(description = "Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "testing")
                @JsonProperty("key")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String key,

                @Schema(description = "글 본문", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 텍스트입니다.")
                @JsonProperty("content")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String content,

                @Schema(description = "데이터 만료시간(밀리 초, -1 이라면 무한정)", requiredMode = Schema.RequiredMode.REQUIRED, example = "12000")
                @JsonProperty("expirationMs")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long expirationMs
        ) {
        }
    }


    ////
    @Operation(
            summary = "Redis Key-Value 삭제 테스트",
            description = "Redis Table 에 저장된 Key 를 삭제합니다.\n\n"
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
                                                    "1 : key 에 저장된 데이터가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @DeleteMapping(
            path = "/test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteRedisKeySample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "key", description = "redis 키", example = "test_key")
            @RequestParam("key")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key
    ) {
        service.deleteRedisKeySample(httpServletResponse, key);
    }


    ////
    @Operation(
            summary = "Redis Key-Value 모두 삭제 테스트",
            description = "Redis Table 에 저장된 모든 Key 를 삭제합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @DeleteMapping(
            path = "/test-all",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteAllRedisKeySample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.deleteAllRedisKeySample(httpServletResponse);
    }


    ////
    @Operation(
            summary = "Redis Lock 테스트",
            description = "Redis Lock 을 요청합니다.\n\n"
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
                                                    "1 : Redis Lock 상태\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/try-redis-lock",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public TryRedisLockSampleOutputVo tryRedisLockSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.tryRedisLockSample(httpServletResponse);
    }

    public record TryRedisLockSampleOutputVo(
            @Schema(description = "응답 코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "OK")
            @JsonProperty("resultCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String resultCode
    ) {
    }


    ////
    @Operation(
            summary = "Redis unLock 테스트",
            description = "Redis unLock 을 요청합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @DeleteMapping(
            path = "/unlock-redis-lock",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void unLockRedisLockSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "lockKey", description = "unLock 할 lockKey", example = "lockKey")
            @RequestParam("lockKey")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String lockKey
    ) {
        service.unLockRedisLockSample(httpServletResponse, lockKey);
    }
}
