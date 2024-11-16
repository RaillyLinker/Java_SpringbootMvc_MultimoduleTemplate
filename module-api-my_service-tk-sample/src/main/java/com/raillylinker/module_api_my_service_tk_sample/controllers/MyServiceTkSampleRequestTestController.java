package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleRequestTestService;
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
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Tag(name = "/my-service/tk/sample/request-test APIs", description = "요청 / 응답에 대한 테스트 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/request-test")
public class MyServiceTkSampleRequestTestController {
    public MyServiceTkSampleRequestTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestService service
    ) {
        this.service = service;
    }

    @Valid @NotNull @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleRequestTestService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "기본 요청 테스트 API",
            description = "이 API 를 요청하면 현재 실행중인 프로필 이름을 반환합니다.\n\n"
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
            path = "",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String basicRequestTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.basicRequestTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "요청 Redirect 테스트 API",
            description = "이 API 를 요청하면 /my-service/tk/sample/request-test 로 Redirect 됩니다.\n\n"
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
            path = "/redirect-to-blank",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ModelAndView redirectTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.redirectTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "요청 Forward 테스트 API",
            description = "이 API 를 요청하면 /my-service/tk/sample/request-test 로 Forward 됩니다.\n\n"
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
            path = "/forward-to-blank",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ModelAndView forwardTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.forwardTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "Get 요청 테스트 (Query Parameter)",
            description = "Query Parameter 를 받는 Get 메소드 요청 테스트\n\n"
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
            path = "/get-request",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public GetRequestTestOutputVo getRequestTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "queryParamString", description = "String Query 파라미터", example = "testString")
            @RequestParam("queryParamString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryParamString,
            @Parameter(name = "queryParamStringNullable", description = "String Query 파라미터 Nullable", example = "testString")
            @RequestParam("queryParamStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String queryParamStringNullable,
            @Parameter(name = "queryParamInt", description = "Int Query 파라미터", example = "1")
            @RequestParam("queryParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer queryParamInt,
            @Parameter(name = "queryParamIntNullable", description = "Int Query 파라미터 Nullable", example = "1")
            @RequestParam("queryParamIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer queryParamIntNullable,
            @Parameter(name = "queryParamDouble", description = "Double Query 파라미터", example = "1.1")
            @RequestParam("queryParamDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double queryParamDouble,
            @Parameter(name = "queryParamDoubleNullable", description = "Double Query 파라미터 Nullable", example = "1.1")
            @RequestParam("queryParamDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double queryParamDoubleNullable,
            @Parameter(name = "queryParamBoolean", description = "Boolean Query 파라미터", example = "true")
            @RequestParam("queryParamBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean queryParamBoolean,
            @Parameter(name = "queryParamBooleanNullable", description = "Boolean Query 파라미터 Nullable", example = "true")
            @RequestParam("queryParamBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean queryParamBooleanNullable,
            @Parameter(name = "queryParamStringList", description = "StringList Query 파라미터", example = "[\"testString1\", \"testString2\"]")
            @RequestParam("queryParamStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> queryParamStringList,
            @Parameter(name = "queryParamStringListNullable", description = "StringList Query 파라미터 Nullable", example = "[\"testString1\", \"testString2\"]")
            @RequestParam("queryParamStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> queryParamStringListNullable
    ) {
        return service.getRequestTest(
                httpServletResponse,
                queryParamString,
                queryParamStringNullable,
                queryParamInt,
                queryParamIntNullable,
                queryParamDouble,
                queryParamDoubleNullable,
                queryParamBoolean,
                queryParamBooleanNullable,
                queryParamStringList,
                queryParamStringListNullable
        );
    }

    public record GetRequestTestOutputVo(
            @Schema(description = "입력한 String Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("queryParamString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryParamString,

            @Schema(description = "입력한 String Nullable Query 파라미터", example = "testString")
            @JsonProperty("queryParamStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String queryParamStringNullable,

            @Schema(description = "입력한 Int Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("queryParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer queryParamInt,

            @Schema(description = "입력한 Int Nullable Query 파라미터", example = "1")
            @JsonProperty("queryParamIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer queryParamIntNullable,

            @Schema(description = "입력한 Double Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("queryParamDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double queryParamDouble,

            @Schema(description = "입력한 Double Nullable Query 파라미터", example = "1.1")
            @JsonProperty("queryParamDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double queryParamDoubleNullable,

            @Schema(description = "입력한 Boolean Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("queryParamBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean queryParamBoolean,

            @Schema(description = "입력한 Boolean Nullable Query 파라미터", example = "true")
            @JsonProperty("queryParamBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean queryParamBooleanNullable,

            @Schema(description = "입력한 StringList Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("queryParamStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> queryParamStringList,

            @Schema(description = "입력한 StringList Nullable Query 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("queryParamStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> queryParamStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "Get 요청 테스트 (Path Parameter)",
            description = "Path Parameter 를 받는 Get 메소드 요청 테스트\n\n"
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
            path = "/get-request/{pathParamInt}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public GetRequestTestWithPathParamOutputVo getRequestTestWithPathParam(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "pathParamInt", description = "Int Path 파라미터", example = "1")
            @PathVariable("pathParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    ) {
        return service.getRequestTestWithPathParam(httpServletResponse, pathParamInt);
    }

    public record GetRequestTestWithPathParamOutputVo(
            @Schema(description = "입력한 Int Path 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("pathParamInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    ) {
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (application-json)",
            description = "application-json 형태의 Request Body 를 받는 Post 메소드 요청 테스트\n\n"
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
            path = "/post-request-application-json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo postRequestTestWithApplicationJsonTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostRequestTestWithApplicationJsonTypeRequestBodyInputVo inputVo
    ) {
        return service.postRequestTestWithApplicationJsonTypeRequestBody(httpServletResponse, inputVo);
    }

    public record PostRequestTestWithApplicationJsonTypeRequestBodyInputVo(
            @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestBodyString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestBodyString,
            @Schema(description = "String Nullable Body 파라미터", example = "testString")
            @JsonProperty("requestBodyStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestBodyStringNullable,
            @Schema(description = "Int Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestBodyInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestBodyInt,
            @Schema(description = "Int Nullable Body 파라미터", example = "1")
            @JsonProperty("requestBodyIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestBodyIntNullable,
            @Schema(description = "Double Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestBodyDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestBodyDouble,
            @Schema(description = "Double Nullable Body 파라미터", example = "1.1")
            @JsonProperty("requestBodyDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestBodyDoubleNullable,
            @Schema(description = "Boolean Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestBodyBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestBodyBoolean,
            @Schema(description = "Boolean Nullable Body 파라미터", example = "true")
            @JsonProperty("requestBodyBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestBodyBooleanNullable,
            @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestBodyStringList,
            @Schema(description = "StringList Nullable Body 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestBodyStringListNullable
    ) {
    }

    public record PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo(
            @Schema(description = "입력한 String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestBodyString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestBodyString,
            @Schema(description = "입력한 String Nullable Body 파라미터", example = "testString")
            @JsonProperty("requestBodyStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestBodyStringNullable,
            @Schema(description = "입력한 Int Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestBodyInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestBodyInt,
            @Schema(description = "입력한 Int Nullable Body 파라미터", example = "1")
            @JsonProperty("requestBodyIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestBodyIntNullable,
            @Schema(description = "입력한 Double Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestBodyDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestBodyDouble,
            @Schema(description = "입력한 Double Nullable Body 파라미터", example = "1.1")
            @JsonProperty("requestBodyDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestBodyDoubleNullable,
            @Schema(description = "입력한 Boolean Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestBodyBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestBodyBoolean,
            @Schema(description = "입력한 Boolean Nullable Body 파라미터", example = "true")
            @JsonProperty("requestBodyBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestBodyBooleanNullable,
            @Schema(description = "입력한 StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestBodyStringList,
            @Schema(description = "입력한 StringList Nullable Body 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestBodyStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (application-json, 객체 파라미터 포함)",
            description = "application-json 형태의 Request Body(객체 파라미터 포함) 를 받는 Post 메소드 요청 테스트\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/post-request-application-json-with-object-param",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo postRequestTestWithApplicationJsonTypeRequestBody2(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostRequestTestWithApplicationJsonTypeRequestBody2InputVo inputVo
    ) {
        return service.postRequestTestWithApplicationJsonTypeRequestBody2(httpServletResponse, inputVo);
    }

    public record PostRequestTestWithApplicationJsonTypeRequestBody2InputVo(
            @Schema(description = "객체 타입 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("objectVo")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ObjectVo objectVo,

            @Schema(description = "객체 타입 리스트 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("objectVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull ObjectVo> objectVoList
    ) {
        @Schema(description = "객체 타입")
        public record ObjectVo(
                @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                @JsonProperty("requestBodyString")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestBodyString,

                @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                @JsonProperty("requestBodyStringList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull String> requestBodyStringList,

                @Schema(description = "서브 객체 타입 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("subObjectVo")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                SubObjectVo subObjectVo,

                @Schema(description = "서브 객체 타입 리스트 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("subObjectVoList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull SubObjectVo> subObjectVoList
        ) {
            @Schema(description = "서브 객체  타입")
            public record SubObjectVo(
                    @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                    @JsonProperty("requestBodyString")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String requestBodyString,

                    @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                    @JsonProperty("requestBodyStringList")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    List<@Valid @NotNull String> requestBodyStringList
            ) {
            }
        }
    }

    public record PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo(
            @Schema(description = "객체 타입 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("objectVo")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ObjectVo objectVo,

            @Schema(description = "객체 타입 리스트 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("objectVoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull ObjectVo> objectVoList
    ) {
        @Schema(description = "객체 타입 파라미터 VO")
        public record ObjectVo(
                @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                @JsonProperty("requestBodyString")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestBodyString,

                @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                @JsonProperty("requestBodyStringList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull String> requestBodyStringList,

                @Schema(description = "서브 객체 타입 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("subObjectVo")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                SubObjectVo subObjectVo,

                @Schema(description = "서브 객체 타입 리스트 파라미터", requiredMode = Schema.RequiredMode.REQUIRED)
                @JsonProperty("subObjectVoList")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull SubObjectVo> subObjectVoList
        ) {
            @Schema(description = "서브 객체 타입 파라미터 VO")
            public record SubObjectVo(
                    @Schema(description = "String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                    @JsonProperty("requestBodyString")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String requestBodyString,

                    @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                    @JsonProperty("requestBodyStringList")
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    List<@Valid @NotNull String> requestBodyStringList
            ) {
            }
        }
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (입출력값 없음)",
            description = "입출력값이 없는 Post 메소드 요청 테스트\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/post-request-application-json-with-no-param",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void postRequestTestWithNoInputAndOutput(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.postRequestTestWithNoInputAndOutput(httpServletResponse);
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (x-www-form-urlencoded)",
            description = "x-www-form-urlencoded 형태의 Request Body 를 받는 Post 메소드 요청 테스트\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/post-request-x-www-form-urlencoded",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithFormTypeRequestBodyOutputVo postRequestTestWithFormTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostRequestTestWithFormTypeRequestBodyInputVo inputVo
    ) {
        return service.postRequestTestWithFormTypeRequestBody(httpServletResponse, inputVo);
    }

    // Input Vo
    public record PostRequestTestWithFormTypeRequestBodyInputVo(
            @Schema(description = "String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @Schema(description = "Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @Schema(description = "Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @Schema(description = "Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @Schema(description = "StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }

    // Output Vo
    public record PostRequestTestWithFormTypeRequestBodyOutputVo(
            @Schema(description = "입력한 String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "입력한 String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @Schema(description = "입력한 Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "입력한 Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @Schema(description = "입력한 Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "입력한 Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @Schema(description = "입력한 Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "입력한 Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @Schema(description = "입력한 StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "입력한 StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (multipart/form-data)",
            description = "multipart/form-data 형태의 Request Body 를 받는 Post 메소드 요청 테스트\n\n" +
                    "MultipartFile 파라미터가 null 이 아니라면 저장\n\n"
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
            path = "/post-request-multipart-form-data",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithMultipartFormTypeRequestBodyOutputVo postRequestTestWithMultipartFormTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostRequestTestWithMultipartFormTypeRequestBodyInputVo inputVo
    ) throws IOException {
        return service.postRequestTestWithMultipartFormTypeRequestBody(httpServletResponse, inputVo);
    }

    public record PostRequestTestWithMultipartFormTypeRequestBodyInputVo(
            @Schema(description = "String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @Schema(description = "Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @Schema(description = "Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @Schema(description = "Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @Schema(description = "StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable,

            @Schema(description = "멀티 파트 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("multipartFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile multipartFile,

            @Schema(description = "멀티 파트 파일 Nullable")
            @JsonProperty("multipartFileNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            MultipartFile multipartFileNullable
    ) {
    }

    public record PostRequestTestWithMultipartFormTypeRequestBodyOutputVo(
            @Schema(description = "입력한 String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "입력한 String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @Schema(description = "입력한 Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "입력한 Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @Schema(description = "입력한 Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "입력한 Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @Schema(description = "입력한 Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "입력한 Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @Schema(description = "입력한 StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "입력한 StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "Post 요청 테스트2 (multipart/form-data)",
            description = "multipart/form-data 형태의 Request Body 를 받는 Post 메소드 요청 테스트(Multipart File List)\n\n" +
                    "파일 리스트가 null 이 아니라면 저장\n\n"
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
            path = "/post-request-multipart-form-data2",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithMultipartFormTypeRequestBody2OutputVo postRequestTestWithMultipartFormTypeRequestBody2(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostRequestTestWithMultipartFormTypeRequestBody2InputVo inputVo
    ) throws IOException {
        return service.postRequestTestWithMultipartFormTypeRequestBody2(httpServletResponse, inputVo);
    }

    public record PostRequestTestWithMultipartFormTypeRequestBody2InputVo(
            @Schema(description = "String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @Schema(description = "Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @Schema(description = "Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @Schema(description = "Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @Schema(description = "StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable,

            @Schema(description = "멀티 파트 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("multipartFileList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull MultipartFile> multipartFileList,

            @Schema(description = "멀티 파트 파일 Nullable")
            @JsonProperty("multipartFileNullableList")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull MultipartFile> multipartFileNullableList
    ) {
    }

    public record PostRequestTestWithMultipartFormTypeRequestBody2OutputVo(
            @Schema(description = "입력한 String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "입력한 String Nullable Form 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @Schema(description = "입력한 Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "입력한 Int Nullable Form 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @Schema(description = "입력한 Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "입력한 Double Nullable Form 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @Schema(description = "입력한 Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "입력한 Boolean Nullable Form 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @Schema(description = "입력한 StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "입력한 StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (multipart/form-data - JsonString)",
            description = "multipart/form-data 형태의 Request Body 를 받는 Post 메소드 요청 테스트\n\n" +
                    "Form Data 의 Input Body 에는 Object 리스트 타입은 사용 불가능합니다.\n\n" +
                    "Object 리스트 타입을 사용한다면, Json String 타입으로 객체를 받아서 파싱하여 사용하는 방식을 사용합니다.\n\n" +
                    "아래 예시에서는 모두 JsonString 형식으로 만들었지만, ObjectList 타입만 이런식으로 처리하세요.\n\n"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/post-request-multipart-form-data-json",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public PostRequestTestWithMultipartFormTypeRequestBody3OutputVo postRequestTestWithMultipartFormTypeRequestBody3(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PostRequestTestWithMultipartFormTypeRequestBody3InputVo inputVo
    ) throws IOException {
        return service.postRequestTestWithMultipartFormTypeRequestBody3(httpServletResponse, inputVo);
    }

    public record PostRequestTestWithMultipartFormTypeRequestBody3InputVo(
            @Schema(
                    description = "json 형식의 문자열\n\n" +
                            "data class InputJsonObject(\n" +
                            " @Schema(description = \"String Form 파라미터\", requiredMode = Schema.RequiredMode.REQUIRED, example = \"testString\")\n" +
                            " @JsonProperty(\"requestFormString\")\n" +
                            " val requestFormString: String,\n" +
                            " @Schema(description = \"String Nullable Form 파라미터\", required = false, example = \"testString\")\n" +
                            " @JsonProperty(\"requestFormStringNullable\")\n" +
                            " val requestFormStringNullable: String?,\n" +
                            " @Schema(description = \"Int Form 파라미터\", requiredMode = Schema.RequiredMode.REQUIRED, example = \"1\")\n" +
                            " @JsonProperty(\"requestFormInt\")\n" +
                            " val requestFormInt: Int,\n" +
                            " @Schema(description = \"Int Nullable Form 파라미터\", required = false, example = \"1\")\n" +
                            " @JsonProperty(\"requestFormIntNullable\")\n" +
                            " val requestFormIntNullable: Int?,\n" +
                            " @Schema(description = \"Double Form 파라미터\", requiredMode = Schema.RequiredMode.REQUIRED, example = \"1.1\")\n" +
                            " @JsonProperty(\"requestFormDouble\")\n" +
                            " val requestFormDouble: Double,\n" +
                            " @Schema(description = \"Double Nullable Form 파라미터\", required = false, example = \"1.1\")\n" +
                            " @JsonProperty(\"requestFormDoubleNullable\")\n" +
                            " val requestFormDoubleNullable: Double?,\n" +
                            " @Schema(description = \"Boolean Form 파라미터\", requiredMode = Schema.RequiredMode.REQUIRED, example = \"true\")\n" +
                            " @JsonProperty(\"requestFormBoolean\")\n" +
                            " val requestFormBoolean: Boolean,\n" +
                            " @Schema(description = \"Boolean Nullable Form 파라미터\", required = false, example = \"true\")\n" +
                            " @JsonProperty(\"requestFormBooleanNullable\")\n" +
                            " val requestFormBooleanNullable: Boolean?,\n" +
                            " @Schema(description = \"StringList Form 파라미터\", requiredMode = Schema.RequiredMode.REQUIRED, example = \"[\\\"testString1\\\", \\\"testString2\\\"]\")\n" +
                            " @JsonProperty(\"requestFormStringList\")\n" +
                            " val requestFormStringList: List<String>,\n" +
                            " @Schema(description = \"StringList Nullable Form 파라미터\", required = false, example = \"[\\\"testString1\\\", \\\"testString2\\\"]\")\n" +
                            " @JsonProperty(\"requestFormStringListNullable\")\n" +
                            " val requestFormStringListNullable: List<String>?\n" +
                            ")",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "{\n" +
                            "  \"requestFormString\": \"testString\",\n" +
                            "  \"requestFormStringNullable\": null,\n" +
                            "  \"requestFormInt\": 1,\n" +
                            "  \"requestFormIntNullable\": null,\n" +
                            "  \"requestFormDouble\": 1.1,\n" +
                            "  \"requestFormDoubleNullable\": null,\n" +
                            "  \"requestFormBoolean\": true,\n" +
                            "  \"requestFormBooleanNullable\": null,\n" +
                            "  \"requestFormStringList\": [\n" +
                            "    \"testString1\",\n" +
                            "    \"testString2\"\n" +
                            "  ],\n" +
                            "  \"requestFormStringListNullable\": null\n" +
                            "}")
            @JsonProperty("jsonString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jsonString,

            @Schema(description = "멀티 파트 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("multipartFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile multipartFile,

            @Schema(description = "멀티 파트 파일 Nullable")
            @JsonProperty("multipartFileNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            MultipartFile multipartFileNullable
    ) {

        public record InputJsonObject(
                @JsonProperty("requestFormString")
                @Schema(description = "String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestFormString,

                @JsonProperty("requestFormStringNullable")
                @Schema(description = "String Nullable Form 파라미터", example = "testString")
                @Nullable @org.jetbrains.annotations.Nullable
                String requestFormStringNullable,

                @JsonProperty("requestFormInt")
                @Schema(description = "Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer requestFormInt,

                @JsonProperty("requestFormIntNullable")
                @Schema(description = "Int Nullable Form 파라미터", example = "1")
                @Nullable @org.jetbrains.annotations.Nullable
                Integer requestFormIntNullable,

                @JsonProperty("requestFormDouble")
                @Schema(description = "Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double requestFormDouble,

                @JsonProperty("requestFormDoubleNullable")
                @Schema(description = "Double Nullable Form 파라미터", example = "1.1")
                @Nullable @org.jetbrains.annotations.Nullable
                Double requestFormDoubleNullable,

                @JsonProperty("requestFormBoolean")
                @Schema(description = "Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Boolean requestFormBoolean,

                @JsonProperty("requestFormBooleanNullable")
                @Schema(description = "Boolean Nullable Form 파라미터", example = "true")
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean requestFormBooleanNullable,

                @JsonProperty("requestFormStringList")
                @Schema(description = "StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull String> requestFormStringList,

                @JsonProperty("requestFormStringListNullable")
                @Schema(description = "StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
                @Nullable @org.jetbrains.annotations.Nullable
                List<@Valid @NotNull String> requestFormStringListNullable
        ) {
        }
    }

    public record PostRequestTestWithMultipartFormTypeRequestBody3OutputVo(
            @JsonProperty("requestFormString")
            @Schema(description = "입력한 String Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @JsonProperty("requestFormStringNullable")
            @Schema(description = "입력한 String Nullable Form 파라미터", example = "testString")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @JsonProperty("requestFormInt")
            @Schema(description = "입력한 Int Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @JsonProperty("requestFormIntNullable")
            @Schema(description = "입력한 Int Nullable Form 파라미터", example = "1")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @JsonProperty("requestFormDouble")
            @Schema(description = "입력한 Double Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @JsonProperty("requestFormDoubleNullable")
            @Schema(description = "입력한 Double Nullable Form 파라미터", example = "1.1")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @JsonProperty("requestFormBoolean")
            @Schema(description = "입력한 Boolean Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @JsonProperty("requestFormBooleanNullable")
            @Schema(description = "입력한 Boolean Nullable Form 파라미터", example = "true")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @JsonProperty("requestFormStringList")
            @Schema(description = "입력한 StringList Form 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @JsonProperty("requestFormStringListNullable")
            @Schema(description = "입력한 StringList Nullable Form 파라미터", example = "[\"testString1\", \"testString2\"]")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "인위적 에러 발생 테스트",
            description = "요청 받으면 인위적인 서버 에러를 발생시킵니다.(Http Response Status 500)\n\n"
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
            path = "/generate-error",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void generateErrorTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.generateErrorTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "결과 코드 발생 테스트",
            description = "Response Header 에 api-result-code 를 반환하는 테스트 API\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : errorType 을 A 로 보냈습니다.\n\n" +
                                                    "2 : errorType 을 B 로 보냈습니다.\n\n" +
                                                    "3 : errorType 을 C 로 보냈습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            },
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = "/api-result-code-test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void returnResultCodeThroughHeaders(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "errorType", description = "정상적이지 않은 상황을 만들도록 가정된 변수입니다.", example = "A")
            @RequestParam("errorType")
            @Nullable @org.jetbrains.annotations.Nullable
            ReturnResultCodeThroughHeadersErrorTypeEnum errorType
    ) {
        service.returnResultCodeThroughHeaders(httpServletResponse, errorType);
    }

    public enum ReturnResultCodeThroughHeadersErrorTypeEnum {
        A, B, C
    }


    ////
    @Operation(
            summary = "인위적 응답 지연 테스트",
            description = "임의로 응답 시간을 지연시킵니다.\n\n"
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
            path = "/time-delay-test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void responseDelayTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "delayTimeSec", description = "지연 시간(초)", example = "1")
            @RequestParam("delayTimeSec")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long delayTimeSec
    ) {
        service.responseDelayTest(httpServletResponse, delayTimeSec);
    }


    ////
    // UTF-8 설정을 적용하려면,
    // produces = ["text/plain;charset=utf-8"]
    // produces = ["text/html;charset=utf-8"]
    @Operation(
            summary = "text/string 반환 샘플",
            description = "text/string 형식의 Response Body 를 반환합니다.\n\n"
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
            path = "/return-text-string",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public String returnTextStringTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.returnTextStringTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "text/html 반환 샘플",
            description = "text/html 형식의 Response Body 를 반환합니다.\n\n"
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
            path = "/return-text-html",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public ModelAndView returnTextHtmlTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.returnTextHtmlTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "byte 반환 샘플",
            description = " byte array('a', .. , 'f') 에서 아래와 같은 요청으로 원하는 바이트를 요청 가능\n\n" +
                    "    >> curl http://localhost:8080/my-service/tk/sample/request-test/byte -i -H \"Range: bytes=2-4\"\n\n"
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
            path = "/byte",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public Resource returnByteDataTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(
                    name = "Range",
                    description = "byte array('a', 'b', 'c', 'd', 'e', 'f') 중 가져올 범위(0 부터 시작되는 인덱스)",
                    example = "Bytes=2-4"
            )
            @RequestHeader("Range")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String range
    ) {
        return service.returnByteDataTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "비디오 스트리밍 샘플",
            description = "비디오 스트리밍 샘플\n\n" +
                    "테스트는 프로젝트 파일 경로의 external_files/files_for_api_test/html_file_sample 안의 video-streaming.html 파일을 사용하세요.\n\n"
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
            path = "/video-streaming",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public Resource videoStreamingTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestParam(value = "videoHeight")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            VideoStreamingTestVideoHeight videoHeight
    ) throws IOException {
        return service.videoStreamingTest(videoHeight, httpServletResponse);
    }

    public enum VideoStreamingTestVideoHeight {
        H240,
        H360,
        H480,
        H720
    }


    ////
    @Operation(
            summary = "오디오 스트리밍 샘플",
            description = "오디오 스트리밍 샘플\n\n" +
                    "테스트는 프로젝트 파일 경로의 external_files/files_for_api_test/html_file_sample 안의 audio-streaming.html 파일을 사용하세요.\n\n"
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
            path = "/audio-streaming",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public Resource audioStreamingTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        return service.audioStreamingTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "비동기 처리 결과 반환 샘플",
            description = "API 호출시 함수 내에서 별도 스레드로 작업을 수행하고,\n\n" +
                    "비동기 작업 완료 후 그 처리 결과가 반환됨\n\n"
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
            path = "/async-result",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public DeferredResult<AsynchronousResponseTestOutputVo> asynchronousResponseTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.asynchronousResponseTest(httpServletResponse);
    }

    public record AsynchronousResponseTestOutputVo(
            @Schema(description = "결과 메세지", requiredMode = Schema.RequiredMode.REQUIRED, example = "n 초 경과 후 반환했습니다.")
            @JsonProperty("resultMessage")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String resultMessage
    ) {
    }


    ////
    @Operation(
            summary = "클라이언트가 특정 SSE 이벤트를 구독",
            description = "구독 수신 중 연결이 끊어질 경우, 클라이언트가 헤더에 Last-Event-ID 라는 값을 넣어서 다시 요청함\n\n" +
                    "!주의점! : 로깅 필터와 충돌되므로, 꼭 요청 헤더에는 Accept:text/event-stream 를 넣어서 요청을 해야함 (이것으로 SSE 요청임을 필터가 확인함)\n\n" +
                    "테스트는, CMD 를 열고, \n\n" +
                    "    >>> curl -N --http2 -H \"Accept:text/event-stream\" http://127.0.0.1:8080/my-service/tk/sample/request-test/sse-test/subscribe\n\n" +
                    "혹은, 프로젝트 파일 경로의 external_files/files_for_api_test/html_file_sample 안의 sse-test.html 파일을 사용하세요. (cors 설정 필요)\n\n"
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
            path = "/sse-test/subscribe",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public SseEmitter sseTestSubscribe(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "Last-Event-ID", description = "멤버가 수신한 마지막 event id")
            @RequestHeader(value = "Last-Event-ID")
            @Nullable @org.jetbrains.annotations.Nullable
            String lastSseEventId
    ) {
        return service.sseTestSubscribe(httpServletResponse, lastSseEventId);
    }


    ////
    @Operation(
            summary = "SSE 이벤트 전송 트리거 테스트",
            description = "어떠한 사건이 일어나면 알림을 위하여 SSE 이벤트 전송을 한다고 가정\n\n"
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
            path = "/sse-test/event-trigger",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void sseTestEventTrigger(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.sseTestEventTrigger(httpServletResponse);
    }


    ////
    /*
         결론적으로 아래 파라미터는, Query Param 의 경우는 리스트 입력이 ?stringList=string&stringList=string 이런식이므로,
         리스트 파라미터가 Not NULL 이라면 빈 리스트를 보낼 수 없으며,
         Body Param 의 경우는 JSON 으로 "requestBodyStringList" : [] 이렇게 보내면 빈 리스트를 보낼 수 있습니다.
     */
    @Operation(
            summary = "빈 리스트 받기 테스트",
            description = "Query 파라미터에 NotNull List 와 Body 파라미터의 NotNull List 에 빈 리스트를 넣었을 때의 현상을 관측하기 위한 테스트\n\n"
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
            path = "/empty-list-param-test",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable @org.jetbrains.annotations.Nullable
    public EmptyListRequestTestOutputVo emptyListRequestTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(
                    name = "stringList",
                    description = "String List Query 파라미터",
                    example = "[\"testString1\", \"testString2\"]"
            )
            @RequestParam("stringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> stringList,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            EmptyListRequestTestInputVo inputVo
    ) {
        return service.emptyListRequestTest(httpServletResponse, stringList, inputVo);
    }

    public record EmptyListRequestTestInputVo(
            @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestBodyStringList
    ) {
    }

    public record EmptyListRequestTestOutputVo(
            @Schema(description = "StringList Query 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestQueryStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestQueryStringList,
            @Schema(description = "StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestBodyStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestBodyStringList
    ) {
    }
}