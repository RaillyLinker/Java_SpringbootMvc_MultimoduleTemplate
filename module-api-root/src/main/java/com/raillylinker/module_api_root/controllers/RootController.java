package com.raillylinker.module_api_root.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_root.services.RootService;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Tag(name = "root APIs", description = "Root 경로에 대한 API 컨트롤러")
@Controller
public class RootController {
    public RootController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RootService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final RootService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "루트 홈페이지",
            description = "루트 홈페이지를 반환합니다.\n\n"
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
            path = {"", "/"},
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ModelAndView getRootHomePage(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.getRootHomePage(httpServletResponse);
    }


    ////
    @Operation(
            summary = "Project Runtime Config Redis Key-Value 모두 조회 테스트",
            description = "Project 의 런타임 설정 저장용 Redis Table 에 저장된 모든 Key-Value 를 조회합니다.\n\n"
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
            path = "/project-runtime-configs",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo selectAllProjectRuntimeConfigsRedisKeyValue(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectAllProjectRuntimeConfigsRedisKeyValue(httpServletResponse);
    }

    public record SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo(
            @Schema(description = "Key-Value 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("keyValueList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<KeyValueVo> keyValueList
    ) {
        @Schema(description = "Key-Value 객체")
        public record KeyValueVo(
                @Schema(description = "Key", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("key")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String key,
                @Schema(description = "설정 IP 정보 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("ipInfoList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull IpDescVo> ipInfoList
        ) {
            @Schema(description = "ip 설명 객체")
            public record IpDescVo(
                    @Schema(description = "설정 ip", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                    @JsonProperty("ip")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String ip,
                    @Schema(description = "ip 설명", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                    @JsonProperty("desc")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String desc
            ) {
            }
        }
    }


    ////
    @Operation(
            summary = "Redis Project Runtime Config actuatorAllowIpList 입력 테스트",
            description = "Redis 의 Project Runtime Config actuatorAllowIpList 를 입력합니다.\n\n" +
                    "이 정보는 본 프로젝트 actuator 접근 허용 IP 를 뜻합니다.\n\n"
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
            path = "/project-runtime-config-actuator-allow-ip-list",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void insertProjectRuntimeConfigActuatorAllowIpList(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertProjectRuntimeConfigActuatorAllowIpListInputVo inputVo
    ) {
        service.insertProjectRuntimeConfigActuatorAllowIpList(httpServletResponse, inputVo);
    }

    public record InsertProjectRuntimeConfigActuatorAllowIpListInputVo(
            @Schema(
                    description = "설정 IP 정보 리스트",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "[{\"ip\":\"127.0.0.1\",\"desc\":\"localHost\"}]"
            )
            @JsonProperty("ipInfoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull IpDescVo> ipInfoList
    ) {
        @Schema(description = "ip 설명 객체")
        public record IpDescVo(
                @Schema(description = "설정 ip", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("ip")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String ip,
                @Schema(description = "ip 설명", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("desc")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String desc
        ) {
        }
    }


    ////
    @Operation(
            summary = "Redis Project Runtime Config loggingDenyIpList 입력 테스트",
            description = "Redis 의 Project Runtime Config loggingDenyIpList 를 입력합니다.\n\n" +
                    "이 정보는 본 프로젝트에서 로깅하지 않을 IP 를 뜻합니다.\n\n"
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
            path = "/project-runtime-config-logging-deny-ip-list",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void insertProjectRuntimeConfigLoggingDenyIpList(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertProjectRuntimeConfigLoggingDenyIpListInputVo inputVo
    ) {
        service.insertProjectRuntimeConfigLoggingDenyIpList(httpServletResponse, inputVo);
    }

    public record InsertProjectRuntimeConfigLoggingDenyIpListInputVo(
            @Schema(
                    description = "설정 IP 정보 리스트",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "[{\"ip\":\"127.0.0.1\",\"desc\":\"localHost\"}]"
            )
            @JsonProperty("ipInfoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull IpDescVo> ipInfoList
    ) {
        @Schema(description = "ip 설명 객체")
        public record IpDescVo(
                @Schema(description = "설정 ip", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("ip")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String ip,
                @Schema(description = "ip 설명", example = "testing", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("desc")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String desc
        ) {
        }
    }
}