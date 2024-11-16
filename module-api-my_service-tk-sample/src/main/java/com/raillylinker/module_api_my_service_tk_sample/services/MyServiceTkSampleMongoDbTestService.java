package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleMongoDbTestController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface MyServiceTkSampleMongoDbTestService {
    // (DB document 입력 테스트 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMongoDbTestController.InsertDocumentTestOutputVo insertDocumentTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMongoDbTestController.InsertDocumentTestInputVo inputVo
    );


    ////
    // (DB Rows 삭제 테스트 API)
    void deleteAllDocumentTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (DB Row 삭제 테스트)
    void deleteDocumentTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    );


    ////
    // (DB Rows 조회 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMongoDbTestController.SelectAllDocumentsTestOutputVo selectAllDocumentsTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (트랜젝션 동작 테스트)
    void transactionRollbackTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (트랜젝션 비동작 테스트)
    void noTransactionRollbackTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );
}