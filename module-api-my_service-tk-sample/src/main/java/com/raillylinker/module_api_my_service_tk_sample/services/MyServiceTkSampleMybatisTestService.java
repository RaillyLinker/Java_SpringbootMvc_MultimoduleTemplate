package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleMybatisTestController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface MyServiceTkSampleMybatisTestService {
    // (DB Row 입력 테스트 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMybatisTestController.InsertDataSampleOutputVo insertDataSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMybatisTestController.InsertDataSampleInputVo inputVo
    );


    /// /
    // (DB Rows 조회 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMybatisTestController.SelectRowsSampleOutputVo selectRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );
}
