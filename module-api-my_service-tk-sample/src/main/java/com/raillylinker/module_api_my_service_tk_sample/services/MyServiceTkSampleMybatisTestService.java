package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleMybatisTestController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface MyServiceTkSampleMybatisTestService {
    // (DB Row 입력 테스트 API)
    void insertDataSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMybatisTestController.InsertDataSampleInputVo inputVo
    );


    /// /
    // (DB Rows 삭제 테스트 API)
    void deleteRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean deleteLogically
    );


    /// /
    // (DB Row 삭제 테스트)
    void deleteRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean deleteLogically
    );


    /// /
    // (DB Rows 조회 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMybatisTestController.SelectRowsSampleOutputVo selectRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    /// /
    // (DB 테이블의 random_num 컬럼 근사치 기준으로 정렬한 리스트 조회 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMybatisTestController.SelectRowsOrderByRandomNumSampleOutputVo selectRowsOrderByRandomNumSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer num
    );
}
