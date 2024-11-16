package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.google.gson.Gson;
import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleRequestFromServerTestController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleRequestFromServerTestService;
import com.raillylinker.module_common.classes.SseClient;
import com.raillylinker.module_retrofit2.retrofit2_classes.RepositoryNetworkRetrofit2;
import com.raillylinker.module_retrofit2.retrofit2_classes.request_apis.LocalHostRequestApi;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import okhttp3.*;
import okhttp3.MediaType.Companion;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MyServiceTkSampleRequestFromServerTestServiceImpl implements MyServiceTkSampleRequestFromServerTestService {
    public MyServiceTkSampleRequestFromServerTestServiceImpl(
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile
    ) throws InterruptedException {
        this.activeProfile = activeProfile;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final Logger classLogger = LoggerFactory.getLogger(MyServiceTkSampleRequestTestServiceImpl.class);

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String activeProfile;

    // Retrofit2 요청 객체
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final RepositoryNetworkRetrofit2 networkRetrofit2 = RepositoryNetworkRetrofit2.getInstance();


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String basicRequestTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<String> responseObj = networkRetrofit2.localHostRequestApi.getMyServiceTkSampleRequestTest().execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                return responseObj.body();
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String redirectTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<String> responseObj = networkRetrofit2.localHostRequestApi.getMyServiceTkSampleRequestTestRedirectToBlank().execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                return responseObj.body();
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String forwardTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<String> responseObj = networkRetrofit2.localHostRequestApi.getMyServiceTkSampleRequestTestForwardToBlank().execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                return responseObj.body();
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestFromServerTestController.GetRequestTestOutputVo getRequestTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<LocalHostRequestApi.GetMyServiceTkSampleRequestTestGetRequestOutputVO> responseObj =
                    networkRetrofit2.localHostRequestApi
                            .getMyServiceTkSampleRequestTestGetRequest(
                                    "paramFromServer",
                                    null,
                                    1,
                                    null,
                                    1.1,
                                    null,
                                    true,
                                    null,
                                    List.of("paramFromServer"),
                                    null
                            ).execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                LocalHostRequestApi.GetMyServiceTkSampleRequestTestGetRequestOutputVO responseBody = Objects.requireNonNull(responseObj.body());
                return new MyServiceTkSampleRequestFromServerTestController.GetRequestTestOutputVo(
                        responseBody.queryParamString(),
                        responseBody.queryParamStringNullable(),
                        responseBody.queryParamInt(),
                        responseBody.queryParamIntNullable(),
                        responseBody.queryParamDouble(),
                        responseBody.queryParamDoubleNullable(),
                        responseBody.queryParamBoolean(),
                        responseBody.queryParamBooleanNullable(),
                        responseBody.queryParamStringList(),
                        responseBody.queryParamStringListNullable()
                );
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestFromServerTestController.GetRequestTestWithPathParamOutputVo getRequestTestWithPathParam(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<LocalHostRequestApi.GetMyServiceTkSampleRequestTestGetRequestPathParamIntOutputVO> responseObj =
                    networkRetrofit2.localHostRequestApi
                            .getMyServiceTkSampleRequestTestGetRequestPathParamInt(1234)
                            .execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                LocalHostRequestApi.GetMyServiceTkSampleRequestTestGetRequestPathParamIntOutputVO responseBody = Objects.requireNonNull(responseObj.body());
                return new MyServiceTkSampleRequestFromServerTestController.GetRequestTestWithPathParamOutputVo(
                        responseBody.pathParamInt()
                );
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo postRequestTestWithApplicationJsonTypeRequestBody(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestApplicationJsonOutputVO> responseObj =
                    networkRetrofit2.localHostRequestApi
                            .postMyServiceTkSampleRequestTestPostRequestApplicationJson(
                                    new LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestApplicationJsonInputVO(
                                            "paramFromServer",
                                            null,
                                            1,
                                            null,
                                            1.1,
                                            null,
                                            true,
                                            null,
                                            List.of("paramFromServer"),
                                            null
                                    )
                            )
                            .execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestApplicationJsonOutputVO responseBody = Objects.requireNonNull(responseObj.body());
                return new MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo(
                        responseBody.requestBodyString(),
                        responseBody.requestBodyStringNullable(),
                        responseBody.requestBodyInt(),
                        responseBody.requestBodyIntNullable(),
                        responseBody.requestBodyDouble(),
                        responseBody.requestBodyDoubleNullable(),
                        responseBody.requestBodyBoolean(),
                        responseBody.requestBodyBooleanNullable(),
                        responseBody.requestBodyStringList(),
                        responseBody.requestBodyStringListNullable()
                );
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithFormTypeRequestBodyOutputVo postRequestTestWithFormTypeRequestBody(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestXWwwFormUrlencodedOutputVO> responseObj =
                    networkRetrofit2.localHostRequestApi
                            .postMyServiceTkSampleRequestTestPostRequestXWwwFormUrlencoded(
                                    "paramFromServer",
                                    null,
                                    1,
                                    null,
                                    1.1,
                                    null,
                                    true,
                                    null,
                                    List.of("paramFromServer"),
                                    null
                            )
                            .execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestXWwwFormUrlencodedOutputVO responseBody = Objects.requireNonNull(responseObj.body());
                return new MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithFormTypeRequestBodyOutputVo(
                        responseBody.requestFormString(),
                        responseBody.requestFormStringNullable(),
                        responseBody.requestFormInt(),
                        responseBody.requestFormIntNullable(),
                        responseBody.requestFormDouble(),
                        responseBody.requestFormDoubleNullable(),
                        responseBody.requestFormBoolean(),
                        responseBody.requestFormBooleanNullable(),
                        responseBody.requestFormStringList(),
                        responseBody.requestFormStringListNullable()
                );
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBodyOutputVo postRequestTestWithMultipartFormTypeRequestBody(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            MultipartBody.Part requestFormString = MultipartBody.Part.createFormData("requestFormString", "paramFromServer");
            MultipartBody.Part requestFormInt = MultipartBody.Part.createFormData("requestFormInt", Integer.toString(1));
            MultipartBody.Part requestFormDouble = MultipartBody.Part.createFormData("requestFormDouble", Double.toString(1.1));
            MultipartBody.Part requestFormBoolean = MultipartBody.Part.createFormData("requestFormBoolean", Boolean.toString(true));

            List<MultipartBody.Part> requestFormStringList = new ArrayList<>();
            for (String requestFormString1 : List.of("paramFromServer")) {
                requestFormStringList.add(MultipartBody.Part.createFormData("requestFormStringList", requestFormString1));
            }

            // 전송 하려는 File
            File serverFile = Paths.get(new File("").getAbsolutePath() + "/module-api-my_service-tk-sample/src/main/resources/static/post_request_test_with_multipart_form_type_request_body/test.txt").toFile();
            MultipartBody.Part multipartFileFormData = MultipartBody.Part.createFormData(
                    "multipartFile",
                    serverFile.getName(),
                    RequestBody.create(serverFile, MediaType.parse(serverFile.toURI().toURL().openConnection().getContentType()))
            );

            // 네트워크 요청 전송
            Response<LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestMultipartFormDataOutputVO> responseObj =
                    networkRetrofit2.localHostRequestApi
                            .postMyServiceTkSampleRequestTestPostRequestMultipartFormData(
                                    requestFormString,
                                    null,
                                    requestFormInt,
                                    null,
                                    requestFormDouble,
                                    null,
                                    requestFormBoolean,
                                    null,
                                    requestFormStringList,
                                    null,
                                    multipartFileFormData,
                                    null
                            )
                            .execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestMultipartFormDataOutputVO responseBody = Objects.requireNonNull(responseObj.body());
                return new MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBodyOutputVo(
                        responseBody.requestFormString(),
                        responseBody.requestFormStringNullable(),
                        responseBody.requestFormInt(),
                        responseBody.requestFormIntNullable(),
                        responseBody.requestFormDouble(),
                        responseBody.requestFormDoubleNullable(),
                        responseBody.requestFormBoolean(),
                        responseBody.requestFormBooleanNullable(),
                        responseBody.requestFormStringList(),
                        responseBody.requestFormStringListNullable()
                );
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBody2OutputVo postRequestTestWithMultipartFormTypeRequestBody2(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            MultipartBody.Part requestFormString = MultipartBody.Part.createFormData("requestFormString", "paramFromServer");
            MultipartBody.Part requestFormInt = MultipartBody.Part.createFormData("requestFormInt", Integer.toString(1));
            MultipartBody.Part requestFormDouble = MultipartBody.Part.createFormData("requestFormDouble", Double.toString(1.1));
            MultipartBody.Part requestFormBoolean = MultipartBody.Part.createFormData("requestFormBoolean", Boolean.toString(true));

            List<MultipartBody.Part> requestFormStringList = new ArrayList<>();
            for (String requestFormString1 : List.of("paramFromServer")) {
                requestFormStringList.add(MultipartBody.Part.createFormData("requestFormStringList", requestFormString1));
            }

            // 전송 하려는 File
            File serverFile1 = Paths.get(new File("").getAbsolutePath() + "/module-api-my_service-tk-sample/src/main/resources/static/post_request_test_with_multipart_form_type_request_body2/test1.txt").toFile();
            File serverFile2 = Paths.get(new File("").getAbsolutePath() + "/module-api-my_service-tk-sample/src/main/resources/static/post_request_test_with_multipart_form_type_request_body2/test2.txt").toFile();

            List<MultipartBody.Part> multipartFileListFormData = Arrays.asList(
                    MultipartBody.Part.createFormData(
                            "multipartFileList",
                            serverFile1.getName(),
                            RequestBody.create(serverFile1, MediaType.parse(serverFile1.toURI().toURL().openConnection().getContentType()))
                    ),
                    MultipartBody.Part.createFormData(
                            "multipartFileList",
                            serverFile2.getName(),
                            RequestBody.create(serverFile2, MediaType.parse(serverFile2.toURI().toURL().openConnection().getContentType()))
                    )
            );

            // 네트워크 요청
            Response<LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestMultipartFormData2VO> responseObj = networkRetrofit2.localHostRequestApi
                    .postMyServiceTkSampleRequestTestPostRequestMultipartFormData2(
                            requestFormString,
                            null,
                            requestFormInt,
                            null,
                            requestFormDouble,
                            null,
                            requestFormBoolean,
                            null,
                            requestFormStringList,
                            null,
                            multipartFileListFormData,
                            null
                    ).execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestMultipartFormData2VO responseBody = Objects.requireNonNull(responseObj.body());
                return new MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBody2OutputVo(
                        responseBody.requestFormString(),
                        responseBody.requestFormStringNullable(),
                        responseBody.requestFormInt(),
                        responseBody.requestFormIntNullable(),
                        responseBody.requestFormDouble(),
                        responseBody.requestFormDoubleNullable(),
                        responseBody.requestFormBoolean(),
                        responseBody.requestFormBooleanNullable(),
                        responseBody.requestFormStringList(),
                        responseBody.requestFormStringListNullable()
                );
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBody3OutputVo postRequestTestWithMultipartFormTypeRequestBody3(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            MultipartBody.Part jsonStringFormData = MultipartBody.Part.createFormData(
                    "jsonString", new Gson().toJson(
                            new LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestMultipartFormDataJsonJsonStringVo(
                                    "paramFromServer", null, 1, null, 1.1, null, true, null, Arrays.asList("paramFromServer"), null
                            )
                    )
            );

            // 전송 하려는 File
            File serverFile = Paths.get(new File("").getAbsolutePath() + "/module-api-my_service-tk-sample/src/main/resources/static/post_request_test_with_multipart_form_type_request_body3/test.txt").toFile();
            MultipartBody.Part multipartFileFormData = MultipartBody.Part.createFormData(
                    "multipartFile",
                    serverFile.getName(),
                    RequestBody.create(serverFile, MediaType.parse(serverFile.toURI().toURL().openConnection().getContentType()))
            );

            // 네트워크 요청
            Response<LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestMultipartFormDataJsonOutputVO> responseObj = networkRetrofit2.localHostRequestApi
                    .postMyServiceTkSampleRequestTestPostRequestMultipartFormDataJson(
                            jsonStringFormData,
                            multipartFileFormData,
                            null
                    ).execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                LocalHostRequestApi.PostMyServiceTkSampleRequestTestPostRequestMultipartFormDataJsonOutputVO responseBody = Objects.requireNonNull(responseObj.body());
                return new MyServiceTkSampleRequestFromServerTestController.PostRequestTestWithMultipartFormTypeRequestBody3OutputVo(
                        responseBody.requestFormString(),
                        responseBody.requestFormStringNullable(),
                        responseBody.requestFormInt(),
                        responseBody.requestFormIntNullable(),
                        responseBody.requestFormDouble(),
                        responseBody.requestFormDoubleNullable(),
                        responseBody.requestFormBoolean(),
                        responseBody.requestFormBooleanNullable(),
                        responseBody.requestFormStringList(),
                        responseBody.requestFormStringListNullable()
                );
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (SocketTimeoutException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } catch (IOException e) {
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    public void generateErrorTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<Void> responseObj = networkRetrofit2.localHostRequestApi
                    .postMyServiceTkSampleRequestTestGenerateError().execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
            }
        } catch (IOException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
        }
    }


    ////
    @Override
    public void returnResultCodeThroughHeaders(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<Void> responseObj = networkRetrofit2.localHostRequestApi
                    .postMyServiceTkSampleRequestTestApiResultCodeTest(
                            LocalHostRequestApi.PostMyServiceTkSampleRequestTestApiResultCodeTestErrorTypeEnum.A
                    ).execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
            } else if (responseObj.code() == 204) {
                // api-result-code 확인 필요
                Headers responseHeaders = responseObj.headers();
                String apiResultCode = responseHeaders.get("api-result-code");

                // api-result-code 분기
                switch (Objects.requireNonNull(apiResultCode)) {
                    case "1":
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "3");
                        break;

                    case "2":
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "4");
                        break;

                    case "3":
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "5");
                        break;

                    default:
                        // 알수없는 api-result-code
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "2");
                        break;
                }
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
            }
        } catch (IOException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
        }
    }


    ////
    @Override
    public void responseDelayTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse, @Valid @NotNull @org.jetbrains.annotations.NotNull Long delayTimeSec) {
        try {
            // 네트워크 요청
            Response<Void> responseObj = networkRetrofit2.localHostRequestApi
                    .postMyServiceTkSampleRequestTestGenerateTimeOutError(delayTimeSec)
                    .execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
            }
        } catch (IOException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String returnTextStringTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<String> responseObj = networkRetrofit2.localHostRequestApi
                    .getMyServiceTkSampleRequestTestReturnTextString().execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                return responseObj.body();
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (IOException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String returnTextHtmlTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<String> responseObj = networkRetrofit2.localHostRequestApi
                    .getMyServiceTkSampleRequestTestReturnTextHtml().execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                return responseObj.body();
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (IOException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestFromServerTestController.AsynchronousResponseTestOutputVo asynchronousResponseTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        try {
            // 네트워크 요청
            Response<LocalHostRequestApi.GetMyServiceTkSampleRequestTestAsyncResultOutputVO> responseObj = networkRetrofit2.localHostRequestApi
                    .getMyServiceTkSampleRequestTestAsyncResult().execute();

            if (responseObj.code() == 200) {
                // 정상 동작
                httpServletResponse.setStatus(HttpStatus.OK.value());
                LocalHostRequestApi.GetMyServiceTkSampleRequestTestAsyncResultOutputVO responseBody = Objects.requireNonNull(responseObj.body());
                return new MyServiceTkSampleRequestFromServerTestController.AsynchronousResponseTestOutputVo(responseBody.resultMessage());
            } else {
                // 반환될 일 없는 상태 = 서버측 에러
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return null;
            }
        } catch (IOException e) {
            // 타임아웃 에러 처리 = 런타임에서 처리해야하는 유일한 클라이언트 측 에러
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }
    }


    ////
    @Override
    public void sseSubscribeTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        // SSE Subscribe Url 연결 객체 생성
        SseClient sseClient = new SseClient("http://127.0.0.1:8080/my-service/tk/sample/request-test/sse-test/subscribe");

        final int maxCount = 5;
        final AtomicInteger count = new AtomicInteger(0);

        // SSE 구독 연결
        sseClient.connect(5000L, new SseClient.ListenerCallback() {
            @Override
            public void onConnectRequestFirstTime(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    SseClient sse,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Request originalRequest
            ) {
                classLogger.info("++++ api17 : onConnectRequestFirstTime");
            }

            @Override
            public void onConnect(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    SseClient sse,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    okhttp3.Response response
            ) {
                classLogger.info("++++ api17 : onOpen");
            }

            @Override
            public void onMessageReceive(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    SseClient sse,
                    @Nullable @org.jetbrains.annotations.Nullable
                    String eventId,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String event,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String message
            ) {
                classLogger.info("++++ api17 : onMessage : " + event + " " + message);

                // maxCount 만큼 반복했다면 연결 끊기
                if (maxCount == count.incrementAndGet()) {
                    sseClient.disconnect();
                }
            }

            @Override
            public void onCommentReceive(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    SseClient sse,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String comment
            ) {
                classLogger.info("++++ api17 : onComment : " + comment);
            }

            @Override
            public boolean onPreRetry(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    SseClient sse,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Request originalRequest,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Throwable throwable,
                    @Nullable @org.jetbrains.annotations.Nullable
                    okhttp3.Response response
            ) {

                classLogger.info("++++ api17 : onPreRetry");
                return true;
            }

            @Override
            public void onDisconnected(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    SseClient sse
            ) {
                classLogger.info("++++ api17 : onClosed");
            }
        });

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }


    ////
    @Override
    public void websocketConnectTest(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        OkHttpClient client = new OkHttpClient();

        final int maxCount = 5;
        final AtomicInteger count = new AtomicInteger(0);

        Request request = new Request.Builder()
                .url("ws://localhost:8080/ws/test/websocket")
                .build();

        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    WebSocket webSocket,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    okhttp3.Response response
            ) {
                classLogger.info("++++ api18 : onOpen");
                webSocket.send(
                        new Gson().toJson(new MessagePayloadVo("++++ api18 Client", "i am open!"))
                );
            }

            @Override
            public void onMessage(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    WebSocket webSocket,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String text
            ) {
                // maxCount 만큼 반복했다면 연결 끊기
                if (maxCount == count.incrementAndGet()) {
                    webSocket.close(1000, null);
                }

                classLogger.info("++++ api18 : onMessage : " + text);

                // 메세지를 받으면 바로 메세지를 보내기
                webSocket.send(
                        new Gson().toJson(new MessagePayloadVo("++++ api18 Client", "reply!"))
                );
            }

            @Override
            public void onMessage(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    WebSocket webSocket,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    ByteString bytes
            ) {
                classLogger.info("++++ api18 : onMessage : " + bytes);
            }

            @Override
            public void onClosing(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    WebSocket webSocket,
                    @Valid @NotNull
                    int code,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String reason) {
                classLogger.info("++++ api18 : onClosing");
            }

            @Override
            public void onFailure(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    WebSocket webSocket,
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    Throwable t,
                    @Valid @NotNull
                    okhttp3.Response response
            ) {
                classLogger.info("++++ api18 : onFailure");
                t.printStackTrace();
            }
        });

        client.dispatcher().executorService().shutdown();

        classLogger.info(webSocket.toString());

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }

    public record MessagePayloadVo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String sender,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message
    ) {
    }
}