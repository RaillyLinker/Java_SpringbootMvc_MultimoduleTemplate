package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleRequestFromServerTestService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Tag(name = "/my-service/tk/sample/request-from-server-test APIs", description = "서버에서 요청을 보내는 테스트 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/request-from-server-test")
public class MyServiceTkSampleRequestFromServerTestController {
    public MyServiceTkSampleRequestFromServerTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestFromServerTestService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleRequestFromServerTestService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "기본 요청 테스트",
            description = "기본적인 Get 메소드 요청 테스트입니다.\n\n"
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
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/request-test",
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
            summary = "Redirect 테스트",
            description = "Redirect 되었을 때의 응답 테스트입니다.\n\n"
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
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/redirect-to-blank",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String redirectTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.redirectTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "Forward 테스트",
            description = "Forward 되었을 때의 응답 테스트입니다.\n\n"
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
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/forward-to-blank",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String forwardTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.forwardTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "Get 요청 테스트 (Query Parameter)",
            description = "Query 파라미터를 받는 Get 요청 테스트\n\n"
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
                            headers = @Header(
                                    name = "api-result-code",
                                    description = "(Response Code 반환 원인) - Required\n\n" +
                                            "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                            "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                    schema = @Schema(type = "string")
                            )
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
            HttpServletResponse httpServletResponse
    ) {
        return service.getRequestTest(httpServletResponse);
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

    @Operation(
            summary = "Get 요청 테스트 (Path Parameter)",
            description = "Path 파라미터를 받는 Get 요청 테스트\n\n"
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
                            headers = @Header(
                                    name = "api-result-code",
                                    description = "(Response Code 반환 원인) - Required\n\n" +
                                            "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                            "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                    schema = @Schema(type = "string")
                            )
                    )
            }
    )
    @GetMapping(
            path = "/get-request-path-param",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public GetRequestTestWithPathParamOutputVo getRequestTestWithPathParam(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.getRequestTestWithPathParam(httpServletResponse);
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
            summary = "Post 요청 테스트 (Request Body, application/json)",
            description = "application/json 형식의 Request Body 를 받는 Post 요청 테스트\n\n"
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
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/post-request-application-json",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo postRequestTestWithApplicationJsonTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.postRequestTestWithApplicationJsonTypeRequestBody(httpServletResponse);
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
            summary = "Post 요청 테스트 (Request Body, x-www-form-urlencoded)",
            description = "x-www-form-urlencoded 형식의 Request Body 를 받는 Post 요청 테스트\n\n"
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
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/post-request-x-www-form-urlencoded",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithFormTypeRequestBodyOutputVo postRequestTestWithFormTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.postRequestTestWithFormTypeRequestBody(httpServletResponse);
    }

    public record PostRequestTestWithFormTypeRequestBodyOutputVo(
            @Schema(description = "입력한 String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "입력한 String Nullable Body 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @Schema(description = "입력한 Int Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "입력한 Int Nullable Body 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @Schema(description = "입력한 Double Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "입력한 Double Nullable Body 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @Schema(description = "입력한 Boolean Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "입력한 Boolean Nullable Body 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @Schema(description = "입력한 StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(description = "입력한 StringList Nullable Body 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (Request Body, multipart/form-data)",
            description = "multipart/form-data 형식의 Request Body 를 받는 Post 요청 테스트\n\n"
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
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/post-request-multipart-form-data",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithMultipartFormTypeRequestBodyOutputVo postRequestTestWithMultipartFormTypeRequestBody(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.postRequestTestWithMultipartFormTypeRequestBody(httpServletResponse);
    }

    public record PostRequestTestWithMultipartFormTypeRequestBodyOutputVo(
            @Schema(description = "입력한 String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,
            @Schema(description = "입력한 String Nullable Body 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,
            @Schema(description = "입력한 Int Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,
            @Schema(description = "입력한 Int Nullable Body 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,
            @Schema(description = "입력한 Double Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,
            @Schema(description = "입력한 Double Nullable Body 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,
            @Schema(description = "입력한 Boolean Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,
            @Schema(description = "입력한 Boolean Nullable Body 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,
            @Schema(description = "입력한 StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,
            @Schema(description = "입력한 StringList Nullable Body 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (Request Body, multipart/form-data, MultipartFile List)",
            description = "multipart/form-data 형식의 Request Body 를 받는 Post 요청 테스트\n\n" +
                    "MultipartFile 파라미터를 List 로 받습니다.\n\n"
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
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/post-request-multipart-form-data2",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithMultipartFormTypeRequestBody2OutputVo postRequestTestWithMultipartFormTypeRequestBody2(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.postRequestTestWithMultipartFormTypeRequestBody2(httpServletResponse);
    }

    public record PostRequestTestWithMultipartFormTypeRequestBody2OutputVo(
            @Schema(description = "입력한 String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,
            @Schema(description = "입력한 String Nullable Body 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,
            @Schema(description = "입력한 Int Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,
            @Schema(description = "입력한 Int Nullable Body 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,
            @Schema(description = "입력한 Double Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,
            @Schema(description = "입력한 Double Nullable Body 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,
            @Schema(description = "입력한 Boolean Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,
            @Schema(description = "입력한 Boolean Nullable Body 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,
            @Schema(description = "입력한 StringList Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,
            @Schema(description = "입력한 StringList Nullable Body 파라미터", example = "[\"testString1\", \"testString2\"]")
            @JsonProperty("requestFormStringListNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "Post 요청 테스트 (Request Body, multipart/form-data, with jsonString)",
            description = "multipart/form-data 형식의 Request Body 를 받는 Post 요청 테스트\n\n" +
                    "파일 외의 파라미터를 JsonString 형식으로 받습니다.\n\n"
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
                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                            schema = @Schema(type = "string")
                    )
            )
    })
    @GetMapping(
            path = "/post-request-multipart-form-data-json",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public PostRequestTestWithMultipartFormTypeRequestBody3OutputVo postRequestTestWithMultipartFormTypeRequestBody3(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.postRequestTestWithMultipartFormTypeRequestBody3(httpServletResponse);
    }

    public record PostRequestTestWithMultipartFormTypeRequestBody3OutputVo(
            @Schema(description = "입력한 String Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("requestFormString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String requestFormString,

            @Schema(description = "입력한 String Nullable Body 파라미터", example = "testString")
            @JsonProperty("requestFormStringNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            String requestFormStringNullable,

            @Schema(description = "입력한 Int Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("requestFormInt")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer requestFormInt,

            @Schema(description = "입력한 Int Nullable Body 파라미터", example = "1")
            @JsonProperty("requestFormIntNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer requestFormIntNullable,

            @Schema(description = "입력한 Double Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.1")
            @JsonProperty("requestFormDouble")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double requestFormDouble,

            @Schema(description = "입력한 Double Nullable Body 파라미터", example = "1.1")
            @JsonProperty("requestFormDoubleNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Double requestFormDoubleNullable,

            @Schema(description = "입력한 Boolean Body 파라미터", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("requestFormBoolean")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean requestFormBoolean,

            @Schema(description = "입력한 Boolean Nullable Body 파라미터", example = "true")
            @JsonProperty("requestFormBooleanNullable")
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean requestFormBooleanNullable,

            @Schema(
                    description = "입력한 StringList Body 파라미터",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "[\"testString1\", \"testString2\"]"
            )
            @JsonProperty("requestFormStringList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> requestFormStringList,

            @Schema(
                    description = "입력한 StringList Nullable Body 파라미터",
                    example = "[\"testString1\", \"testString2\"]"
            )
            @JsonProperty("requestFormStringListNullable")
            List<@Valid @NotNull String> requestFormStringListNullable
    ) {
    }


    ////
    @Operation(
            summary = "에러 발생 테스트",
            description = "요청시 에러가 발생했을 때의 테스트입니다."
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
                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                            schema = @Schema(type = "string")
                    )
            )
    })
    @GetMapping(
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
            summary = "api-result-code 반환 테스트",
            description = "api-result-code 가 Response Header 로 반환되는 테스트입니다."
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
                                    "1 : 네트워크 에러\n\n" +
                                    "2 : 서버 에러\n\n" +
                                    "3 : errorType 을 A 로 보냈습니다.\n\n" +
                                    "4 : errorType 을 B 로 보냈습니다.\n\n" +
                                    "5 : errorType 을 C 로 보냈습니다.\n\n",
                            schema = @Schema(type = "string")
                    )
            )
    })
    @GetMapping(
            path = "/api-result-code-test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void returnResultCodeThroughHeaders(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.returnResultCodeThroughHeaders(httpServletResponse);
    }


    ////
    @Operation(
            summary = "응답 지연 발생 테스트",
            description = "요청을 보내어 인위적으로 응답이 지연 되었을 때를 테스트합니다."
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
                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                            schema = @Schema(type = "string")
                    )
            )
    })
    @GetMapping(
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
    @Operation(
            summary = "text/string 형식 Response 받아오기",
            description = "text/string 형식 Response 를 받아옵니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작"),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(),
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/text-string-response",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String returnTextStringTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.returnTextStringTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "text/html 형식 Response 받아오기",
            description = "text/html 형식 Response 를 받아옵니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작"),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(),
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/text-html-response",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.TEXT_HTML_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String returnTextHtmlTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.returnTextHtmlTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "DeferredResult Get 요청 테스트",
            description = "결과 반환 지연 Get 메소드 요청 테스트\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작"),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(),
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/delayed-result-test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public AsynchronousResponseTestOutputVo asynchronousResponseTest(
            @Parameter(hidden = true)
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
            summary = "SSE 구독 테스트",
            description = "SSE 구독 요청 테스트\n\n" +
                    "SSE 를 구독하여 백그라운드에서 실행합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작"),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(),
                            description = "Response Body 가 없습니다.\n\nResponse Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/sse-subscribe",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void sseSubscribeTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.sseSubscribeTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "WebSocket 연결 테스트",
            description = "WebSocket 연결 요청 테스트\n\n" +
                    "WebSocket 을 연결 하여 백그라운드에서 실행합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정상 동작"),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(),
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 요청시 네트워크 에러가 발생하였습니다.\n\n" +
                                                    "2 : API 요청시 서버 에러가 발생하였습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = "/websocket-connect",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void websocketConnectTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.websocketConnectTest(httpServletResponse);
    }
}