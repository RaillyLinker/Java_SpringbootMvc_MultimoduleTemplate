package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleMapCoordinateCalculationController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface MyServiceTkSampleMapCoordinateCalculationService {
    // (테스트용 기본 좌표 리스트를 DB에 저장)
    void insertDefaultCoordinateDataToDatabase(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (두 좌표 사이의 거리를 반환(하버사인 공식))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMapCoordinateCalculationController.GetDistanceMeterBetweenTwoCoordinateOutputVo getDistanceMeterBetweenTwoCoordinate(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude1,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude1,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude2,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude2
    );


    ////
    // (두 좌표 사이의 거리를 반환(Vincenty 공식))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMapCoordinateCalculationController.GetDistanceMeterBetweenTwoCoordinateVincentyOutputVo getDistanceMeterBetweenTwoCoordinateVincenty(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude1,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude1,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude2,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude2
    );


    ////
    // (좌표들 사이의 중심 좌표를 반환)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMapCoordinateCalculationController.ReturnCenterCoordinateOutputVo returnCenterCoordinate(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMapCoordinateCalculationController.ReturnCenterCoordinateInputVo inputVo
    );


    ////
    // (DB 의 좌표 테이블에 좌표 정보를 저장)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMapCoordinateCalculationController.InsertCoordinateDataToDatabaseOutputVo insertCoordinateDataToDatabase(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMapCoordinateCalculationController.InsertCoordinateDataToDatabaseInputVo inputVo
    );


    ////
    // (DB 의 좌표 테이블의 모든 데이터 삭제)
    void deleteAllCoordinateDataFromDatabase(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (DB 에 저장된 좌표들을 SQL 을 사용하여, 기준 좌표의 N Km 내의 결과만 필터)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInRadiusKiloMeterSampleOutputVo selectCoordinateDataRowsInRadiusKiloMeterSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double anchorLatitude,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double anchorLongitude,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double radiusKiloMeter
    );


    ////
    // (DB 에 저장된 좌표들을 SQL 을 사용하여, 북서 좌표에서 남동 좌표까지의 사각 영역 안에 들어오는 좌표들만 필터링하여 반환)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInCoordinateBoxSampleOutputVo selectCoordinateDataRowsInCoordinateBoxSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double northLatitude, // 북위도 (ex : 37.771848)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double eastLongitude, // 동경도 (ex : 127.433549)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double southLatitude, // 남위도 (ex : 37.245683)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double westLongitude // 서경도 (ex : 126.587602)
    );
}