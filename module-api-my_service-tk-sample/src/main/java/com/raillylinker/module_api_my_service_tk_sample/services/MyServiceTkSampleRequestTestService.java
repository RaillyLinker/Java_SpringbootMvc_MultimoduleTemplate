package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleRequestTestController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

public interface MyServiceTkSampleRequestTestService {
    // (기본 요청 테스트 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String basicRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (요청 Redirect 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    ModelAndView redirectTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (요청 Forward 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    ModelAndView forwardTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (Get 요청 테스트 (Query Parameter))
    MyServiceTkSampleRequestTestController.GetRequestTestOutputVo getRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String queryParamString,
            @Nullable @org.jetbrains.annotations.Nullable
            String queryParamStringNullable,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer queryParamInt,
            @Nullable @org.jetbrains.annotations.Nullable
            Integer queryParamIntNullable,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double queryParamDouble,
            @Nullable @org.jetbrains.annotations.Nullable
            Double queryParamDoubleNullable,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean queryParamBoolean,
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean queryParamBooleanNullable,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> queryParamStringList,
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> queryParamStringListNullable
    );


    ////
    // (Get 요청 테스트 (Path Parameter))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestTestController.GetRequestTestWithPathParamOutputVo getRequestTestWithPathParam(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    );


    ////
    // (Post 요청 테스트 (application-json))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo postRequestTestWithApplicationJsonTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBodyInputVo inputVo
    );


    ////
    // (Post 요청 테스트 (application-json, 객체 파라미터 포함))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo postRequestTestWithApplicationJsonTypeRequestBody2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2InputVo inputVo
    );


    ////
    // (Post 요청 테스트 (입출력값 없음))
    void postRequestTestWithNoInputAndOutput(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (Post 요청 테스트 (x-www-form-urlencoded))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestTestController.PostRequestTestWithFormTypeRequestBodyOutputVo postRequestTestWithFormTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithFormTypeRequestBodyInputVo inputVo
    );


    ////
    // (Post 요청 테스트 (multipart/form-data))
    MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBodyOutputVo postRequestTestWithMultipartFormTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBodyInputVo inputVo
    ) throws IOException;


    ////
    // (Post 요청 테스트2 (multipart/form-data))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody2OutputVo postRequestTestWithMultipartFormTypeRequestBody2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody2InputVo inputVo
    ) throws IOException;


    ////
    // (Post 요청 테스트 (multipart/form-data - JsonString))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody3OutputVo postRequestTestWithMultipartFormTypeRequestBody3(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody3InputVo inputVo
    ) throws IOException;


    ////
    // (인위적 에러 발생 테스트)
    void generateErrorTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (결과 코드 발생 테스트)
    void returnResultCodeThroughHeaders(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Nullable @org.jetbrains.annotations.Nullable
            MyServiceTkSampleRequestTestController.ReturnResultCodeThroughHeadersErrorTypeEnum errorType
    );


    ////
    // (인위적 응답 지연 테스트)
    void responseDelayTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long delayTimeSec
    );


    ////
    // (text/string 반환 샘플)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String returnTextStringTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (text/html 반환 샘플)
    @Nullable
    @org.jetbrains.annotations.Nullable
    ModelAndView returnTextHtmlTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (byte 반환 샘플)
    @Nullable
    @org.jetbrains.annotations.Nullable
    Resource returnByteDataTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (비디오 스트리밍 샘플)
    @Nullable
    @org.jetbrains.annotations.Nullable
    Resource videoStreamingTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.VideoStreamingTestVideoHeight videoHeight,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException;


    ////
    // (오디오 스트리밍 샘플)
    @Nullable
    @org.jetbrains.annotations.Nullable
    Resource audioStreamingTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException;


    ////
    // (비동기 처리 결과 반환 샘플)
    @Nullable
    @org.jetbrains.annotations.Nullable
    DeferredResult<MyServiceTkSampleRequestTestController.AsynchronousResponseTestOutputVo> asynchronousResponseTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (클라이언트가 특정 SSE 이벤트를 구독)
    @Nullable
    @org.jetbrains.annotations.Nullable
    SseEmitter sseTestSubscribe(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Nullable @org.jetbrains.annotations.Nullable
            String lastSseEventId
    );


    ////
    // (SSE 이벤트 전송 트리거 테스트)
    void sseTestEventTrigger(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (빈 리스트 받기 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleRequestTestController.EmptyListRequestTestOutputVo emptyListRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> stringList,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.EmptyListRequestTestInputVo inputVo
    );
}
