package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleDatabaseTestController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface MyServiceTkSampleDatabaseTestService {
    // (DB Row 입력 테스트 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.InsertDataSampleOutputVo insertDataSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.InsertDataSampleInputVo inputVo
    );


    ////
    // (DB Rows 삭제 테스트 API)
    void deleteRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean deleteLogically
    );


    ////
    // (DB Row 삭제 테스트)
    void deleteRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean deleteLogically
    );


    ////
    // (DB Rows 조회 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowsSampleOutputVo selectRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (DB 테이블의 random_num 컬럼 근사치 기준으로 정렬한 리스트 조회 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRandomNumSampleOutputVo selectRowsOrderByRandomNumSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer num
    );


    ////
    // (DB 테이블의 row_create_date 컬럼 근사치 기준으로 정렬한 리스트 조회 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRowCreateDateSampleOutputVo selectRowsOrderByRowCreateDateSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String dateString
    );


    ////
    // (DB Rows 조회 테스트 (페이징))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowsPageSampleOutputVo selectRowsPageSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount
    );


    ////
    // (DB Rows 조회 테스트 (네이티브 쿼리 페이징))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowsNativeQueryPageSampleOutputVo selectRowsNativeQueryPageSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer num
    );


    ////
    // (DB Row 수정 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.UpdateRowSampleOutputVo updateRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.UpdateRowSampleInputVo inputVo
    );


    ////
    // (DB Row 수정 테스트 (네이티브 쿼리))
    void updateRowNativeQuerySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.UpdateRowNativeQuerySampleInputVo inputVo
    );


    ////
    // (DB 정보 검색 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowWhereSearchingKeywordSampleOutputVo selectRowWhereSearchingKeywordSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String searchKeyword
    );


    ////
    // (트랜젝션 동작 테스트)
    void transactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (트랜젝션 비동작 테스트)
    void nonTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (트랜젝션 비동작 테스트(try-catch))
    void tryCatchNonTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (DB Rows 조회 테스트 (중복 없는 네이티브 쿼리 페이징))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowsNoDuplicatePagingSampleOutputVo selectRowsNoDuplicatePagingSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Nullable @org.jetbrains.annotations.Nullable
            Long lastItemUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount
    );


    ////
    // (DB Rows 조회 테스트 (카운팅))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowsCountSampleOutputVo selectRowsCountSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (DB Rows 조회 테스트 (네이티브 카운팅))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowsCountByNativeQuerySampleOutputVo selectRowsCountByNativeQuerySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (DB Row 조회 테스트 (네이티브))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectRowByNativeQuerySampleOutputVo selectRowByNativeQuerySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid
    );


    ////
    // (유니크 테스트 테이블 Row 입력 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.InsertUniqueTestTableRowSampleOutputVo insertUniqueTestTableRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.InsertUniqueTestTableRowSampleInputVo inputVo
    );


    ////
    // (유니크 테스트 테이블 Rows 조회 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectUniqueTestTableRowsSampleOutputVo selectUniqueTestTableRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (유니크 테스트 테이블 Row 수정 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.UpdateUniqueTestTableRowSampleOutputVo updateUniqueTestTableRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.UpdateUniqueTestTableRowSampleInputVo inputVo
    );


    ////
    // (유니크 테스트 테이블 Row 삭제 테스트)
    void deleteUniqueTestTableRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    );


    ////
    // (외래키 부모 테이블 Row 입력 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.InsertFkParentRowSampleOutputVo insertFkParentRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.InsertFkParentRowSampleInputVo inputVo
    );


    ////
    // (외래키 부모 테이블 아래에 자식 테이블의 Row 입력 API)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.InsertFkChildRowSampleOutputVo insertFkChildRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long parentUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.InsertFkChildRowSampleInputVo inputVo
    );


    ////
    // (외래키 관련 테이블 Rows 조회 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsSampleOutputVo selectFkTestTableRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (외래키 관련 테이블 Rows 조회 테스트(Native Join))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsByNativeQuerySampleDot1OutputVo selectFkTestTableRowsByNativeQuerySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (Native Query 반환값 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.GetNativeQueryReturnValueTestOutputVo getNativeQueryReturnValueTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean inputVal
    );


    ////
    // (SQL Injection 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo sqlInjectionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String searchKeyword
    );


    ////
    // (외래키 관련 테이블 Rows 조회 (네이티브 쿼리, 부모 테이블을 자식 테이블의 가장 최근 데이터만 Join))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleDatabaseTestController.SelectFkTableRowsWithLatestChildSampleOutputVo selectFkTableRowsWithLatestChildSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (외래키 자식 테이블 Row 삭제 테스트)
    void deleteFkChildRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    );


    ////
    // (외래키 부모 테이블 Row 삭제 테스트 (Cascade 기능 확인))
    void deleteFkParentRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    );


    ////
    // (외래키 테이블 트랜젝션 동작 테스트)
    void fkTableTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (외래키 테이블 트랜젝션 비동작 테스트)
    void fkTableNonTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );
}
