package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleRequestFromServerTestController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface MyServiceTkSampleRequestFromServerTestService {
    // (기본 요청 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String basicRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Redirect 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String redirectTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Forward 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String forwardTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Get 요청 테스트 (Query Parameter))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestFromServerTestController.GetRequestTestOutputVo getRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Get 요청 테스트 (Path Parameter))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestFromServerTestController.GetRequestTestWithPathParamOutputVo getRequestTestWithPathParam(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Post 요청 테스트 (Request Body, application/json))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo postRequestTestWithApplicationJsonTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Post 요청 테스트 (Request Body, x-www-form-urlencoded))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithFormTypeRequestBodyOutputVo postRequestTestWithFormTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Post 요청 테스트 (Request Body, multipart/form-data))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBodyOutputVo postRequestTestWithMultipartFormTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Post 요청 테스트 (Request Body, multipart/form-data, MultipartFile List))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBody2OutputVo postRequestTestWithMultipartFormTypeRequestBody2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (Post 요청 테스트 (Request Body, multipart/form-data, with jsonString))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBody3OutputVo postRequestTestWithMultipartFormTypeRequestBody3(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (에러 발생 테스트)
    void generateErrorTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (api-result-code 반환 테스트)
    void returnResultCodeThroughHeaders(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (응답 지연 발생 테스트)
    void responseDelayTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long delayTimeSec
    );

    // (text/string 형식 Response 받아오기)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String returnTextStringTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (text/html 형식 Response 받아오기)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String returnTextHtmlTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (DeferredResult Get 요청 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestFromServerTestController.AsynchronousResponseTestOutputVo asynchronousResponseTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (SSE 구독 테스트)
    void sseSubscribeTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

    // (WebSocket 연결 테스트)
    void websocketConnectTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );
}