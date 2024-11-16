package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleRequestTestController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleRequestTestService;
import com.raillylinker.module_common.classes.SseEmitterWrapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MyServiceTkSampleRequestTestServiceImpl implements MyServiceTkSampleRequestTestService {
    public MyServiceTkSampleRequestTestServiceImpl(
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile
    ) {
        this.activeProfile = activeProfile;
    }

    private static final Logger classLogger = LoggerFactory.getLogger(MyServiceTkSampleRequestTestServiceImpl.class);

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String activeProfile;


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String basicRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return activeProfile;
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ModelAndView redirectTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/my-service/tk/sample/request-test");
        return mv;
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ModelAndView forwardTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forward:/my-service/tk/sample/request-test");
        return mv;
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.GetRequestTestOutputVo getRequestTest(
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
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRequestTestController.GetRequestTestOutputVo(
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


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.GetRequestTestWithPathParamOutputVo getRequestTestWithPathParam(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pathParamInt
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRequestTestController.GetRequestTestWithPathParamOutputVo(pathParamInt);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo postRequestTestWithApplicationJsonTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBodyInputVo inputVo
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBodyOutputVo(
                inputVo.requestBodyString(),
                inputVo.requestBodyStringNullable(),
                inputVo.requestBodyInt(),
                inputVo.requestBodyIntNullable(),
                inputVo.requestBodyDouble(),
                inputVo.requestBodyDoubleNullable(),
                inputVo.requestBodyBoolean(),
                inputVo.requestBodyBooleanNullable(),
                inputVo.requestBodyStringList(),
                inputVo.requestBodyStringListNullable()
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo postRequestTestWithApplicationJsonTypeRequestBody2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2InputVo inputVo
    ) {
        List<MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo> objectList = new ArrayList<>();

        for (MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2InputVo.ObjectVo objectVo : inputVo.objectVoList()) {
            List<MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo> subObjectVoList = new ArrayList<>();
            for (MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2InputVo.ObjectVo.SubObjectVo subObject : objectVo.subObjectVoList()) {
                subObjectVoList.add(
                        new MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo(
                                subObject.requestBodyString(),
                                subObject.requestBodyStringList()
                        )
                );
            }

            objectList.add(
                    new MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo(
                            objectVo.requestBodyString(),
                            objectVo.requestBodyStringList(),
                            new MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo(
                                    objectVo.subObjectVo().requestBodyString(),
                                    objectVo.subObjectVo().requestBodyStringList()
                            ),
                            subObjectVoList
                    )
            );
        }

        List<MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo> subObjectVoList = new ArrayList<>();
        for (MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2InputVo.ObjectVo.SubObjectVo subObject : inputVo.objectVo().subObjectVoList()) {
            subObjectVoList.add(
                    new MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo(
                            subObject.requestBodyString(),
                            subObject.requestBodyStringList()
                    )
            );
        }

        MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo outputVo =
                new MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo(
                        new MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo(
                                inputVo.objectVo().requestBodyString(),
                                inputVo.objectVo().requestBodyStringList(),
                                new MyServiceTkSampleRequestTestController.PostRequestTestWithApplicationJsonTypeRequestBody2OutputVo.ObjectVo.SubObjectVo(
                                        inputVo.objectVo().subObjectVo().requestBodyString(),
                                        inputVo.objectVo().subObjectVo().requestBodyStringList()
                                ),
                                subObjectVoList
                        ),
                        objectList
                );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return outputVo;
    }


    ////
    @Override
    public void postRequestTestWithNoInputAndOutput(@Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.PostRequestTestWithFormTypeRequestBodyOutputVo postRequestTestWithFormTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithFormTypeRequestBodyInputVo inputVo
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRequestTestController.PostRequestTestWithFormTypeRequestBodyOutputVo(
                inputVo.requestFormString(),
                inputVo.requestFormStringNullable(),
                inputVo.requestFormInt(),
                inputVo.requestFormIntNullable(),
                inputVo.requestFormDouble(),
                inputVo.requestFormDoubleNullable(),
                inputVo.requestFormBoolean(),
                inputVo.requestFormBooleanNullable(),
                inputVo.requestFormStringList(),
                inputVo.requestFormStringListNullable()
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBodyOutputVo postRequestTestWithMultipartFormTypeRequestBody(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBodyInputVo inputVo
    ) throws IOException {
        // 파일 저장 기본 디렉토리 경로
        Path saveDirectoryPath = Paths.get("./by_product_files/test").toAbsolutePath().normalize();

        // 파일 저장 기본 디렉토리 생성
        Files.createDirectories(saveDirectoryPath);

        // 원본 파일명(with suffix)
        String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.multipartFile().getOriginalFilename()));

        // 파일 확장자 구분 위치
        int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

        // 확장자가 없는 파일명
        String fileNameWithOutExtension;
        // 확장자
        String fileExtension;

        if (fileExtensionSplitIdx == -1) {
            fileNameWithOutExtension = multiPartFileNameString;
            fileExtension = "";
        } else {
            fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
            fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
        }

        // multipartFile 을 targetPath 에 저장
        inputVo.multipartFile().transferTo(
                saveDirectoryPath.resolve(
                        String.format("%s(%s).%s", fileNameWithOutExtension, LocalDateTime.now().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")), fileExtension)
                ).normalize()
        );

        if (inputVo.multipartFileNullable() != null) {
            // 원본 파일명(with suffix)
            String multiPartFileNullableNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.multipartFileNullable().getOriginalFilename()));

            // 파일 확장자 구분 위치
            int nullableFileExtensionSplitIdx = multiPartFileNullableNameString.lastIndexOf('.');

            // 확장자가 없는 파일명
            String nullableFileNameWithOutExtension;
            // 확장자
            String nullableFileExtension;

            if (nullableFileExtensionSplitIdx == -1) {
                nullableFileNameWithOutExtension = multiPartFileNullableNameString;
                nullableFileExtension = "";
            } else {
                nullableFileNameWithOutExtension = multiPartFileNullableNameString.substring(0, nullableFileExtensionSplitIdx);
                nullableFileExtension = multiPartFileNullableNameString.substring(nullableFileExtensionSplitIdx + 1);
            }

            // multipartFile 을 targetPath 에 저장
            inputVo.multipartFileNullable().transferTo(
                    saveDirectoryPath.resolve(
                            String.format("%s(%s).%s", nullableFileNameWithOutExtension, LocalDateTime.now().atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")), nullableFileExtension)
                    ).normalize()
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBodyOutputVo(
                inputVo.requestFormString(),
                inputVo.requestFormStringNullable(),
                inputVo.requestFormInt(),
                inputVo.requestFormIntNullable(),
                inputVo.requestFormDouble(),
                inputVo.requestFormDoubleNullable(),
                inputVo.requestFormBoolean(),
                inputVo.requestFormBooleanNullable(),
                inputVo.requestFormStringList(),
                inputVo.requestFormStringListNullable()
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody2OutputVo postRequestTestWithMultipartFormTypeRequestBody2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody2InputVo inputVo
    ) throws IOException {
        // 파일 저장 기본 디렉토리 경로
        Path saveDirectoryPath = Paths.get("./by_product_files/test").toAbsolutePath().normalize();

        // 파일 저장 기본 디렉토리 생성
        Files.createDirectories(saveDirectoryPath);

        for (MultipartFile multipartFile : inputVo.multipartFileList()) {
            // 원본 파일명(with suffix)
            String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            // 파일 확장자 구분 위치
            int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

            // 확장자가 없는 파일명
            String fileNameWithOutExtension;
            // 확장자
            String fileExtension;

            if (fileExtensionSplitIdx == -1) {
                fileNameWithOutExtension = multiPartFileNameString;
                fileExtension = "";
            } else {
                fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
                fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
            }

            // multipartFile 을 targetPath 에 저장
            multipartFile.transferTo(
                    saveDirectoryPath.resolve(
                            String.format("%s(%s).%s", fileNameWithOutExtension, LocalDateTime.now().atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")), fileExtension)
                    ).normalize()
            );
        }

        if (inputVo.multipartFileNullableList() != null) {
            for (MultipartFile multipartFileNullable : inputVo.multipartFileNullableList()) {
                // 원본 파일명(with suffix)
                String multiPartFileNullableNameString = StringUtils.cleanPath(Objects.requireNonNull(multipartFileNullable.getOriginalFilename()));

                // 파일 확장자 구분 위치
                int nullableFileExtensionSplitIdx = multiPartFileNullableNameString.lastIndexOf('.');

                // 확장자가 없는 파일명
                String nullableFileNameWithOutExtension;
                // 확장자
                String nullableFileExtension;

                if (nullableFileExtensionSplitIdx == -1) {
                    nullableFileNameWithOutExtension = multiPartFileNullableNameString;
                    nullableFileExtension = "";
                } else {
                    nullableFileNameWithOutExtension = multiPartFileNullableNameString.substring(0, nullableFileExtensionSplitIdx);
                    nullableFileExtension = multiPartFileNullableNameString.substring(nullableFileExtensionSplitIdx + 1);
                }

                // multipartFile 을 targetPath 에 저장
                multipartFileNullable.transferTo(
                        saveDirectoryPath.resolve(
                                String.format("%s(%s).%s", nullableFileNameWithOutExtension, LocalDateTime.now().atZone(ZoneId.systemDefault())
                                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")), nullableFileExtension)
                        ).normalize()
                );
            }
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody2OutputVo(
                inputVo.requestFormString(),
                inputVo.requestFormStringNullable(),
                inputVo.requestFormInt(),
                inputVo.requestFormIntNullable(),
                inputVo.requestFormDouble(),
                inputVo.requestFormDoubleNullable(),
                inputVo.requestFormBoolean(),
                inputVo.requestFormBooleanNullable(),
                inputVo.requestFormStringList(),
                inputVo.requestFormStringListNullable()
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody3OutputVo postRequestTestWithMultipartFormTypeRequestBody3(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody3InputVo inputVo
    ) throws IOException {
        // input Json String to Object
        MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody3InputVo.InputJsonObject inputJsonObject =
                new Gson().fromJson(inputVo.jsonString(),
                        new TypeToken<MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody3InputVo.InputJsonObject>() {
                        }.getType());

        // 파일 저장 기본 디렉토리 경로
        Path saveDirectoryPath = Paths.get("./by_product_files/test").toAbsolutePath().normalize();

        // 파일 저장 기본 디렉토리 생성
        Files.createDirectories(saveDirectoryPath);

        // 원본 파일명(with suffix)
        String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.multipartFile().getOriginalFilename()));

        // 파일 확장자 구분 위치
        int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

        // 확장자가 없는 파일명
        String fileNameWithOutExtension;
        // 확장자
        String fileExtension;

        if (fileExtensionSplitIdx == -1) {
            fileNameWithOutExtension = multiPartFileNameString;
            fileExtension = "";
        } else {
            fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
            fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
        }

        // multipartFile 을 targetPath 에 저장
        inputVo.multipartFile().transferTo(
                saveDirectoryPath.resolve(
                        fileNameWithOutExtension + "(" +
                                LocalDateTime.now().atZone(ZoneId.systemDefault())
                                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")) + ")." + fileExtension
                ).normalize()
        );

        if (inputVo.multipartFileNullable() != null) {
            String multiPartFileNullableNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.multipartFileNullable().getOriginalFilename()));
            int nullableFileExtensionSplitIdx = multiPartFileNullableNameString.lastIndexOf('.');

            String nullableFileNameWithOutExtension;
            String nullableFileExtension;

            if (nullableFileExtensionSplitIdx == -1) {
                nullableFileNameWithOutExtension = multiPartFileNullableNameString;
                nullableFileExtension = "";
            } else {
                nullableFileNameWithOutExtension = multiPartFileNullableNameString.substring(0, nullableFileExtensionSplitIdx);
                nullableFileExtension = multiPartFileNullableNameString.substring(nullableFileExtensionSplitIdx + 1);
            }

            inputVo.multipartFileNullable().transferTo(
                    saveDirectoryPath.resolve(
                            nullableFileNameWithOutExtension + "(" +
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm-ss-SSS")) + ")." + nullableFileExtension
                    ).normalize()
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRequestTestController.PostRequestTestWithMultipartFormTypeRequestBody3OutputVo(
                inputJsonObject.requestFormString(),
                inputJsonObject.requestFormStringNullable(),
                inputJsonObject.requestFormInt(),
                inputJsonObject.requestFormIntNullable(),
                inputJsonObject.requestFormDouble(),
                inputJsonObject.requestFormDoubleNullable(),
                inputJsonObject.requestFormBoolean(),
                inputJsonObject.requestFormBooleanNullable(),
                inputJsonObject.requestFormStringList(),
                inputJsonObject.requestFormStringListNullable()
        );
    }


    ////
    @Override
    public void generateErrorTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        throw new RuntimeException("Test Error");
    }


    ////
    @Override
    public void returnResultCodeThroughHeaders(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Nullable @org.jetbrains.annotations.Nullable
            MyServiceTkSampleRequestTestController.ReturnResultCodeThroughHeadersErrorTypeEnum errorType
    ) {
        if (errorType == null) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else {
            switch (errorType) {
                case A:
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    break;
                case B:
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "2");
                    break;
                case C:
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "3");
                    break;
            }
        }
    }


    ////
    @Override
    public void responseDelayTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long delayTimeSec
    ) {
        long endTime = System.currentTimeMillis() + (delayTimeSec * 1000);

        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(100);  // 100ms마다 스레드를 잠들게 하여 CPU 사용률을 줄임
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String returnTextStringTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return "test Complete!";
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ModelAndView returnTextHtmlTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("return_text_html_test/html_response_example");
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return modelAndView;
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public Resource returnByteDataTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new ByteArrayResource(new byte[]{'a', 'b', 'c', 'd', 'e', 'f'});
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public Resource videoStreamingTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.VideoStreamingTestVideoHeight videoHeight,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        String projectRootAbsolutePathString = new File("").getAbsolutePath();
        String serverFileAbsolutePathString = projectRootAbsolutePathString + "/module-api-my_service-tk-sample/src/main/resources/static/video_streaming_test";

        String serverFileNameString = switch (videoHeight) {
            case H240 -> "test_240p.mp4";
            case H360 -> "test_360p.mp4";
            case H480 -> "test_480p.mp4";
            case H720 -> "test_720p.mp4";
        };

        byte[] fileByteArray;
        try (FileInputStream fileInputStream = new FileInputStream(serverFileAbsolutePathString + "/" + serverFileNameString)) {
            fileByteArray = fileInputStream.readAllBytes();
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new ByteArrayResource(fileByteArray);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public Resource audioStreamingTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        String projectRootAbsolutePathString = new File("").getAbsolutePath();
        String serverFileAbsolutePathString = projectRootAbsolutePathString + "/module-api-my_service-tk-sample/src/main/resources/static/audio_streaming_test";
        String serverFileNameString = "test.mp3";

        byte[] fileByteArray;
        try (FileInputStream fileInputStream = new FileInputStream(serverFileAbsolutePathString + "/" + serverFileNameString)) {
            fileByteArray = fileInputStream.readAllBytes();
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new ByteArrayResource(fileByteArray);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public DeferredResult<MyServiceTkSampleRequestTestController.AsynchronousResponseTestOutputVo> asynchronousResponseTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        long deferredResultTimeoutMs = 1000L * 60;
        DeferredResult<MyServiceTkSampleRequestTestController.AsynchronousResponseTestOutputVo> deferredResult =
                new DeferredResult<>(deferredResultTimeoutMs);

        executorService.execute(() -> {
            long delayMs = 5000L;
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            deferredResult.setResult(new MyServiceTkSampleRequestTestController.AsynchronousResponseTestOutputVo("complete"));
        });

        return deferredResult;
    }


    ////
    // api20에서 발급한 Emitter 객체
    private final SseEmitterWrapper api20SseEmitterWrapperMbr = new SseEmitterWrapper();

    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SseEmitter sseTestSubscribe(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Nullable @org.jetbrains.annotations.Nullable
            String lastSseEventId
    ) {
        // 수신 객체
        SseEmitter sseEmitter = api20SseEmitterWrapperMbr.getSseEmitter(null, lastSseEventId);

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return sseEmitter;
    }


    ////
    private int api21TriggerTestCountMbr = 0;

    @Override
    public void sseTestEventTrigger(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        // emitter 이벤트 전송
        int nowTriggerTestCount = ++api21TriggerTestCountMbr;

        api20SseEmitterWrapperMbr.broadcastEvent(
                "triggerTest",
                "trigger " + nowTriggerTestCount
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRequestTestController.EmptyListRequestTestOutputVo emptyListRequestTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> stringList,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRequestTestController.EmptyListRequestTestInputVo inputVo
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRequestTestController.EmptyListRequestTestOutputVo(
                stringList,
                inputVo.requestBodyStringList()
        );
    }
}