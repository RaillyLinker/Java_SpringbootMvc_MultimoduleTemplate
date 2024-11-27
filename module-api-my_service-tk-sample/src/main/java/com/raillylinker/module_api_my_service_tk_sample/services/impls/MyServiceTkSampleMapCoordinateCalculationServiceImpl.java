package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleMapCoordinateCalculationController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleMapCoordinateCalculationService;
import com.raillylinker.module_common.classes.Pair;
import com.raillylinker.module_common.util_components.MapCoordinateUtil;
import com.raillylinker.module_jpa.annotations.CustomTransactional;
import com.raillylinker.module_jpa.configurations.jpa_configs.Db1MainConfig;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_Template_TestMap;
import com.raillylinker.module_jpa.jpa_beans.db1_main.repositories.Db1_Native_Repository;
import com.raillylinker.module_jpa.jpa_beans.db1_main.repositories.Db1_Template_TestMap_Repository;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyServiceTkSampleMapCoordinateCalculationServiceImpl implements MyServiceTkSampleMapCoordinateCalculationService {
    public MyServiceTkSampleMapCoordinateCalculationServiceImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MapCoordinateUtil mapCoordinateUtil,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Template_TestMap_Repository db1TemplateTestMapRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Native_Repository db1NativeRepository
    ) {
        this.mapCoordinateUtil = mapCoordinateUtil;
        this.db1TemplateTestMapRepository = db1TemplateTestMapRepository;
        this.db1NativeRepository = db1NativeRepository;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MapCoordinateUtil mapCoordinateUtil;

    // (Database Repository)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Template_TestMap_Repository db1TemplateTestMapRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Native_Repository db1NativeRepository;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final Logger classLogger = LoggerFactory.getLogger(MyServiceTkSampleMapCoordinateCalculationServiceImpl.class);


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void insertDefaultCoordinateDataToDatabase(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        db1TemplateTestMapRepository.deleteAll();

        List<Pair<Double, Double>> latLngList = Arrays.asList(
                new Pair<>(37.5845885, 127.0001891),
                new Pair<>(37.6060504, 126.9607987),
                new Pair<>(37.5844214, 126.9699813),
                new Pair<>(37.5757558, 126.9710255),
                new Pair<>(37.5764907, 126.968655),
                new Pair<>(37.5786667, 127.0156223),
                new Pair<>(37.561697, 126.9968491),
                new Pair<>(37.5880051, 127.0181872),
                new Pair<>(37.5713246, 126.9635654),
                new Pair<>(37.5922066, 127.0135319),
                new Pair<>(37.5690038, 126.9632755),
                new Pair<>(37.584865, 126.948639),
                new Pair<>(37.5690454, 127.0232121),
                new Pair<>(37.5634635, 127.015948),
                new Pair<>(37.5748642, 127.0155003),
                new Pair<>(37.5708604, 126.9612919),
                new Pair<>(37.5570078, 126.9533333),
                new Pair<>(37.5726188, 127.0576283),
                new Pair<>(37.5914225, 127.0129648),
                new Pair<>(37.5659102, 127.0217363)
        );

        for (Pair<Double, Double> latLng : latLngList) {
            db1TemplateTestMapRepository.save(
                    new Db1_Template_TestMap(
                            latLng.first(),
                            latLng.second()
                    )
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMapCoordinateCalculationController.GetDistanceMeterBetweenTwoCoordinateOutputVo getDistanceMeterBetweenTwoCoordinate(
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
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleMapCoordinateCalculationController.GetDistanceMeterBetweenTwoCoordinateOutputVo(
                mapCoordinateUtil.getDistanceMeterBetweenTwoLatLngCoordinateHarversine(
                        new Pair<>(latitude1, longitude1),
                        new Pair<>(latitude2, longitude2)
                )
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMapCoordinateCalculationController.GetDistanceMeterBetweenTwoCoordinateVincentyOutputVo getDistanceMeterBetweenTwoCoordinateVincenty(
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
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleMapCoordinateCalculationController.GetDistanceMeterBetweenTwoCoordinateVincentyOutputVo(
                mapCoordinateUtil.getDistanceMeterBetweenTwoLatLngCoordinateVincenty(
                        new Pair<>(latitude1, longitude1),
                        new Pair<>(latitude2, longitude2)
                )
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMapCoordinateCalculationController.ReturnCenterCoordinateOutputVo returnCenterCoordinate(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMapCoordinateCalculationController.ReturnCenterCoordinateInputVo inputVo
    ) {
        List<Pair<Double, Double>> latLngCoordinate = new ArrayList<>();

        for (MyServiceTkSampleMapCoordinateCalculationController.ReturnCenterCoordinateInputVo.Coordinate coordinate : inputVo.coordinateList()) {
            latLngCoordinate.add(
                    new Pair<>(coordinate.centerLatitude(), coordinate.centerLongitude())
            );
        }

        Pair<Double, Double> centerCoordinate = mapCoordinateUtil.getCenterLatLngCoordinate(latLngCoordinate);

        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleMapCoordinateCalculationController.ReturnCenterCoordinateOutputVo(
                centerCoordinate.first(),
                centerCoordinate.second()
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMapCoordinateCalculationController.InsertCoordinateDataToDatabaseOutputVo insertCoordinateDataToDatabase(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMapCoordinateCalculationController.InsertCoordinateDataToDatabaseInputVo inputVo
    ) {
        db1TemplateTestMapRepository.save(
                new Db1_Template_TestMap(inputVo.latitude(), inputVo.longitude())
        );

        List<MyServiceTkSampleMapCoordinateCalculationController.InsertCoordinateDataToDatabaseOutputVo.Coordinate> coordinateList = new ArrayList<>();
        List<Pair<Double, Double>> latLngCoordinate = new ArrayList<>();

        for (Db1_Template_TestMap testMap : db1TemplateTestMapRepository.findAll()) {
            coordinateList.add(
                    new MyServiceTkSampleMapCoordinateCalculationController.InsertCoordinateDataToDatabaseOutputVo.Coordinate(
                            testMap.latitude,
                            testMap.longitude
                    )
            );

            latLngCoordinate.add(new Pair<>(testMap.latitude, testMap.longitude));
        }

        Pair<Double, Double> centerCoordinate = mapCoordinateUtil.getCenterLatLngCoordinate(latLngCoordinate);

        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleMapCoordinateCalculationController.InsertCoordinateDataToDatabaseOutputVo(
                coordinateList,
                new MyServiceTkSampleMapCoordinateCalculationController.InsertCoordinateDataToDatabaseOutputVo.Coordinate(
                        centerCoordinate.first(),
                        centerCoordinate.second()
                )
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteAllCoordinateDataFromDatabase(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        db1TemplateTestMapRepository.deleteAll();
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInRadiusKiloMeterSampleOutputVo selectCoordinateDataRowsInRadiusKiloMeterSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double anchorLatitude,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double anchorLongitude,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double radiusKiloMeter
    ) {
        List<Db1_Native_Repository.FindAllFromTemplateTestMapInnerHaversineCoordDistanceAreaOutputVo> entityList = db1NativeRepository.findAllFromTemplateTestMapInnerHaversineCoordDistanceArea(anchorLatitude, anchorLongitude, radiusKiloMeter);

        List<MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInRadiusKiloMeterSampleOutputVo.CoordinateCalcResult> coordinateCalcResultList = new ArrayList<>();
        for (Db1_Native_Repository.FindAllFromTemplateTestMapInnerHaversineCoordDistanceAreaOutputVo entity : entityList) {
            coordinateCalcResultList.add(
                    new MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInRadiusKiloMeterSampleOutputVo.CoordinateCalcResult(
                            entity.getUid(),
                            entity.getLatitude(),
                            entity.getLongitude(),
                            entity.getDistanceKiloMeter()
                    )
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInRadiusKiloMeterSampleOutputVo(coordinateCalcResultList);

    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInCoordinateBoxSampleOutputVo selectCoordinateDataRowsInCoordinateBoxSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double northLatitude,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double eastLongitude,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double southLatitude,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double westLongitude
    ) {
        List<Db1_Native_Repository.FindAllFromTemplateTestMapInnerCoordSquareAreaOutputVo> entityList = db1NativeRepository.findAllFromTemplateTestMapInnerCoordSquareArea(northLatitude, eastLongitude, southLatitude, westLongitude);

        List<MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInCoordinateBoxSampleOutputVo.CoordinateCalcResult> coordinateCalcResultList = new ArrayList<>();
        for (Db1_Native_Repository.FindAllFromTemplateTestMapInnerCoordSquareAreaOutputVo entity : entityList) {
            coordinateCalcResultList.add(
                    new MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInCoordinateBoxSampleOutputVo.CoordinateCalcResult(
                            entity.getUid(),
                            entity.getLatitude(),
                            entity.getLongitude()
                    )
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleMapCoordinateCalculationController.SelectCoordinateDataRowsInCoordinateBoxSampleOutputVo(coordinateCalcResultList);

    }
}