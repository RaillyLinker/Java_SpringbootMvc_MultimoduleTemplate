package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleMybatisTestController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleMybatisTestService;
import com.raillylinker.module_mybatis.annotations.CustomMybatisTransactional;
import com.raillylinker.module_mybatis.configurations.mybatis_configs.Mybatis1MainConfig;
import com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.entities.Mybatis1_Template_TestData;
import com.raillylinker.module_mybatis.mybatis_beans.mybatis1_main.mappers.Mybatis1_Template_TestData_Mapper;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MyServiceTkSampleMybatisTestServiceImpl implements MyServiceTkSampleMybatisTestService {
    public MyServiceTkSampleMybatisTestServiceImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Mybatis1_Template_TestData_Mapper mybatis1TemplateTestDataMapper
    ) {
        this.mybatis1TemplateTestDataMapper = mybatis1TemplateTestDataMapper;
    }

    // (Database Repository)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Mybatis1_Template_TestData_Mapper mybatis1TemplateTestDataMapper;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    @CustomMybatisTransactional(transactionManagerBeanNameList = {Mybatis1MainConfig.TRANSACTION_NAME})
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMybatisTestController.InsertDataSampleOutputVo insertDataSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMybatisTestController.InsertDataSampleInputVo inputVo
    ) {
        LocalDateTime now = LocalDateTime.now();

        mybatis1TemplateTestDataMapper.save(
                new Mybatis1_Template_TestData(
                        now,
                        now,
                        "/",
                        inputVo.content(),
                        (int) (Math.random() * 99999999), // Random number between 0 and 99999999
                        ZonedDateTime.parse(inputVo.dateString(), DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                                .toLocalDateTime()
                )
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());

//        return new MyServiceTkSampleMybatisTestController.InsertDataSampleOutputVo(
//                result.uid,
//                result.content,
//                result.randomNum,
//                result.testDatetime.atZone(ZoneId.systemDefault())
//                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                result.rowCreateDate.atZone(ZoneId.systemDefault())
//                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                result.rowUpdateDate.atZone(ZoneId.systemDefault())
//                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                result.rowDeleteDateStr
//        );
        return null;
    }


    /// /
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMybatisTestController.SelectRowsSampleOutputVo selectRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
//        List<Db1_Template_TestData> resultEntityList = db1TemplateTestsRepository.findAllByRowDeleteDateStrOrderByRowCreateDate("/");
//        List<MyServiceTkSampleMybatisTestController.SelectRowsSampleOutputVo.TestEntityVo> entityVoList = new ArrayList<>();
//        for (Db1_Template_TestData resultEntity : resultEntityList) {
//            entityVoList.add(new MyServiceTkSampleMybatisTestController.SelectRowsSampleOutputVo.TestEntityVo(
//                    resultEntity.uid,
//                    resultEntity.content,
//                    resultEntity.randomNum,
//                    resultEntity.testDatetime.atZone(ZoneId.systemDefault())
//                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                    resultEntity.rowCreateDate.atZone(ZoneId.systemDefault())
//                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                    resultEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
//                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                    resultEntity.rowDeleteDateStr
//            ));
//        }
//
//        List<Db1_Template_TestData> logicalDeleteEntityVoList = db1TemplateTestsRepository.findAllByRowDeleteDateStrNotOrderByRowCreateDate("/");
//        List<MyServiceTkSampleMybatisTestController.SelectRowsSampleOutputVo.TestEntityVo> logicalDeleteVoList = new ArrayList<>();
//        for (Db1_Template_TestData resultEntity : logicalDeleteEntityVoList) {
//            logicalDeleteVoList.add(new MyServiceTkSampleMybatisTestController.SelectRowsSampleOutputVo.TestEntityVo(
//                    resultEntity.uid,
//                    resultEntity.content,
//                    resultEntity.randomNum,
//                    resultEntity.testDatetime.atZone(ZoneId.systemDefault())
//                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                    resultEntity.rowCreateDate.atZone(ZoneId.systemDefault())
//                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                    resultEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
//                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
//                    resultEntity.rowDeleteDateStr
//            ));
//        }
//
//        httpServletResponse.setStatus(HttpStatus.OK.value());
//        return new MyServiceTkSampleMybatisTestController.SelectRowsSampleOutputVo(entityVoList, logicalDeleteVoList);
        return null;
    }
}