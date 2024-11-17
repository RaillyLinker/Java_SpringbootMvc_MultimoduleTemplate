package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleFileTestController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface MyServiceTkSampleFileTestService {
    // (by_product_files/test 폴더로 파일 업로드)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleFileTestController.UploadToServerTestOutputVo uploadToServerTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleFileTestController.UploadToServerTestInputVo inputVo
    ) throws IOException;


    ////
    // (by_product_files/test 폴더에서 파일 다운받기)
    @Nullable
    @org.jetbrains.annotations.Nullable
    ResponseEntity<Resource> fileDownloadTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    );


    ////
    // (파일 리스트 zip 압축 테스트)
    void filesToZipTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (폴더 zip 압축 테스트)
    void folderToZipTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (zip 압축 파일 해제 테스트)
    void unzipTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException;


    ////
    // (클라이언트 이미지 표시 테스트용 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    ResponseEntity<Resource> forClientSideImageTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer delayTimeSecond
    ) throws InterruptedException;


    ////
    // (AWS S3 로 파일 업로드)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleFileTestController.AwsS3UploadTestOutputVo awsS3UploadTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleFileTestController.AwsS3UploadTestInputVo inputVo
    ) throws IOException;


    ////
    // (AWS S3 파일의 내용을 String 으로 가져오기)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleFileTestController.GetFileContentToStringTestOutputVo getFileContentToStringTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String uploadFileName
    ) throws IOException;


    ////
    // (AWS S3 파일을 삭제하기)
    void deleteAwsS3FileTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String deleteFileName
    );
}
