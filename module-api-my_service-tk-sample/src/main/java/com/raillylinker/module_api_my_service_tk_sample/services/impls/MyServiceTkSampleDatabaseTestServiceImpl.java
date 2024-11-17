package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleDatabaseTestController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleDatabaseTestService;
import com.raillylinker.module_jpa.annotations.CustomTransactional;
import com.raillylinker.module_jpa.configurations.jpa_configs.Db1MainConfig;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.*;
import com.raillylinker.module_jpa.jpa_beans.db1_main.repositories.*;
import com.raillylinker.module_jpa.jpa_beans.db1_main.repositories_dsl.Db1_Template_RepositoryDsl;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MyServiceTkSampleDatabaseTestServiceImpl implements MyServiceTkSampleDatabaseTestService {
    public MyServiceTkSampleDatabaseTestServiceImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Native_Repository db1NativeRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Template_Tests_Repository db1TemplateTestsRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Template_FkTestParent_Repository db1TemplateFkTestParentRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Template_FkTestManyToOneChild_Repository db1TemplateFkTestManyToOneChildRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Template_LogicalDeleteUniqueData_Repository db1TemplateLogicalDeleteUniqueDataRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Template_JustBooleanTest_Repository db1TemplateJustBooleanTestRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Template_RepositoryDsl db1TemplateRepositoryDsl) {
        this.db1NativeRepository = db1NativeRepository;
        this.db1TemplateTestsRepository = db1TemplateTestsRepository;
        this.db1TemplateFkTestParentRepository = db1TemplateFkTestParentRepository;
        this.db1TemplateFkTestManyToOneChildRepository = db1TemplateFkTestManyToOneChildRepository;
        this.db1TemplateLogicalDeleteUniqueDataRepository = db1TemplateLogicalDeleteUniqueDataRepository;
        this.db1TemplateJustBooleanTestRepository = db1TemplateJustBooleanTestRepository;
        this.db1TemplateRepositoryDsl = db1TemplateRepositoryDsl;
    }

    // (Database Repository)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Native_Repository db1NativeRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Template_Tests_Repository db1TemplateTestsRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Template_FkTestParent_Repository db1TemplateFkTestParentRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Template_FkTestManyToOneChild_Repository db1TemplateFkTestManyToOneChildRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Template_LogicalDeleteUniqueData_Repository db1TemplateLogicalDeleteUniqueDataRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Template_JustBooleanTest_Repository db1TemplateJustBooleanTestRepository;

    // (Database Repository DSL)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Template_RepositoryDsl db1TemplateRepositoryDsl;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.InsertDataSampleOutputVo insertDataSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.InsertDataSampleInputVo inputVo
    ) {
        Db1_Template_TestData result = db1TemplateTestsRepository.save(
                new Db1_Template_TestData(
                        inputVo.content(),
                        (int) (Math.random() * 99999999), // Random number between 0 and 99999999
                        ZonedDateTime.parse(inputVo.dateString(), DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                                .toLocalDateTime()
                )
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleDatabaseTestController.InsertDataSampleOutputVo(
                result.uid,
                result.content,
                result.randomNum,
                result.testDatetime.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowCreateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowUpdateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowDeleteDateStr
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean deleteLogically
    ) {
        if (deleteLogically) {
            List<Db1_Template_TestData> entityList = db1TemplateTestsRepository.findAllByRowDeleteDateStrOrderByRowCreateDate("/");
            for (Db1_Template_TestData entity : entityList) {
                entity.rowDeleteDateStr = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
                db1TemplateTestsRepository.save(entity);
            }
        } else {
            db1TemplateTestsRepository.deleteAll();
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean deleteLogically
    ) {
        Optional<Db1_Template_TestData> entityOpt = db1TemplateTestsRepository.findByUidAndRowDeleteDateStr(index, "/");

        if (entityOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_Template_TestData entity = entityOpt.get();

        if (deleteLogically) {
            entity.rowDeleteDateStr = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
            db1TemplateTestsRepository.save(entity);
        } else {
            db1TemplateTestsRepository.deleteById(index);
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowsSampleOutputVo selectRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        List<Db1_Template_TestData> resultEntityList = db1TemplateTestsRepository.findAllByRowDeleteDateStrOrderByRowCreateDate("/");
        List<MyServiceTkSampleDatabaseTestController.SelectRowsSampleOutputVo.TestEntityVo> entityVoList = new ArrayList<>();
        for (Db1_Template_TestData resultEntity : resultEntityList) {
            entityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectRowsSampleOutputVo.TestEntityVo(
                    resultEntity.uid,
                    resultEntity.content,
                    resultEntity.randomNum,
                    resultEntity.testDatetime.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowCreateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowDeleteDateStr
            ));
        }

        List<Db1_Template_TestData> logicalDeleteEntityVoList = db1TemplateTestsRepository.findAllByRowDeleteDateStrNotOrderByRowCreateDate("/");
        List<MyServiceTkSampleDatabaseTestController.SelectRowsSampleOutputVo.TestEntityVo> logicalDeleteVoList = new ArrayList<>();
        for (Db1_Template_TestData resultEntity : logicalDeleteEntityVoList) {
            logicalDeleteVoList.add(new MyServiceTkSampleDatabaseTestController.SelectRowsSampleOutputVo.TestEntityVo(
                    resultEntity.uid,
                    resultEntity.content,
                    resultEntity.randomNum,
                    resultEntity.testDatetime.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowCreateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowDeleteDateStr
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowsSampleOutputVo(entityVoList, logicalDeleteVoList);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRandomNumSampleOutputVo selectRowsOrderByRandomNumSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer num
    ) {
        List<Db1_Native_Repository.ForC7N5OutputVo> foundEntityList = db1NativeRepository.forC7N5(num);

        List<MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRandomNumSampleOutputVo.TestEntityVo> testEntityVoList = new ArrayList<>();
        for (Db1_Native_Repository.ForC7N5OutputVo entity : foundEntityList) {
            testEntityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRandomNumSampleOutputVo.TestEntityVo(
                    entity.getUid(),
                    entity.getContent(),
                    entity.getRandomNum(),
                    entity.getTestDatetime().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    entity.getRowCreateDate().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    entity.getRowUpdateDate().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    entity.getDistance()
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRandomNumSampleOutputVo(testEntityVoList);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRowCreateDateSampleOutputVo selectRowsOrderByRowCreateDateSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String dateString
    ) {
        List<Db1_Native_Repository.ForC7N6OutputVo> foundEntityList = db1NativeRepository.forC7N6(
                ZonedDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                        .toLocalDateTime()
        );

        List<MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRowCreateDateSampleOutputVo.TestEntityVo> testEntityVoList = new ArrayList<>();
        for (Db1_Native_Repository.ForC7N6OutputVo entity : foundEntityList) {
            testEntityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRowCreateDateSampleOutputVo.TestEntityVo(
                    entity.getUid(),
                    entity.getContent(),
                    entity.getRandomNum(),
                    entity.getTestDatetime().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    entity.getRowCreateDate().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    entity.getRowUpdateDate().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    entity.getTimeDiffMicroSec()
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowsOrderByRowCreateDateSampleOutputVo(testEntityVoList);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowsPageSampleOutputVo selectRowsPageSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount
    ) {
        Pageable pageable = PageRequest.of(page - 1, pageElementsCount);
        Page<Db1_Template_TestData> entityList = db1TemplateTestsRepository.findAllByRowDeleteDateStrOrderByRowCreateDate("/", pageable);

        List<MyServiceTkSampleDatabaseTestController.SelectRowsPageSampleOutputVo.TestEntityVo> testEntityVoList = new ArrayList<>();
        for (Db1_Template_TestData entity : entityList) {
            testEntityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectRowsPageSampleOutputVo.TestEntityVo(
                    entity.uid,
                    entity.content,
                    entity.randomNum,
                    entity.testDatetime.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    entity.rowCreateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    entity.rowUpdateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowsPageSampleOutputVo(
                entityList.getTotalElements(), testEntityVoList
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowsNativeQueryPageSampleOutputVo selectRowsNativeQueryPageSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer num
    ) {
        Pageable pageable = PageRequest.of(page - 1, pageElementsCount);
        Page<Db1_Native_Repository.ForC7N8OutputVo> voList = db1NativeRepository.forC7N8(num, pageable);

        List<MyServiceTkSampleDatabaseTestController.SelectRowsNativeQueryPageSampleOutputVo.TestEntityVo> testEntityVoList = new ArrayList<>();
        for (Db1_Native_Repository.ForC7N8OutputVo vo : voList) {
            testEntityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectRowsNativeQueryPageSampleOutputVo.TestEntityVo(
                    vo.getUid(),
                    vo.getContent(),
                    vo.getRandomNum(),
                    vo.getTestDatetime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    vo.getRowCreateDate().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    vo.getRowUpdateDate().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    vo.getDistance()
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowsNativeQueryPageSampleOutputVo(
                voList.getTotalElements(), testEntityVoList
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.UpdateRowSampleOutputVo updateRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.UpdateRowSampleInputVo inputVo
    ) {
        Optional<Db1_Template_TestData> oldEntityOpt = db1TemplateTestsRepository.findByUidAndRowDeleteDateStr(testTableUid, "/");

        if (oldEntityOpt.isEmpty() || !"/".equals(oldEntityOpt.get().rowDeleteDateStr)) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        Db1_Template_TestData oldEntity = oldEntityOpt.get();

        oldEntity.content = inputVo.content();
        oldEntity.testDatetime = ZonedDateTime.parse(inputVo.dateString(), DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")).toLocalDateTime();

        Db1_Template_TestData result = db1TemplateTestsRepository.save(oldEntity);

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.UpdateRowSampleOutputVo(
                result.uid,
                result.content,
                result.randomNum,
                result.testDatetime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowCreateDate.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowUpdateDate.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void updateRowNativeQuerySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.UpdateRowNativeQuerySampleInputVo inputVo
    ) {
        Optional<Db1_Template_TestData> testEntityOpt = db1TemplateTestsRepository.findByUidAndRowDeleteDateStr(testTableUid, "/");

        if (testEntityOpt.isEmpty() || !"/".equals(testEntityOpt.get().rowDeleteDateStr)) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_Template_TestData testEntity = testEntityOpt.get();

        db1NativeRepository.forC7N10(
                testTableUid,
                inputVo.content(),
                ZonedDateTime.parse(inputVo.dateString(), DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")).toLocalDateTime()
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowWhereSearchingKeywordSampleOutputVo selectRowWhereSearchingKeywordSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer page,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String searchKeyword
    ) {
        Pageable pageable = PageRequest.of(page - 1, pageElementsCount);
        Page<Db1_Native_Repository.ForC7N11OutputVo> voList = db1NativeRepository.forC7N11(searchKeyword, pageable);

        List<MyServiceTkSampleDatabaseTestController.SelectRowWhereSearchingKeywordSampleOutputVo.TestEntityVo> testEntityVoList = new ArrayList<>();
        for (Db1_Native_Repository.ForC7N11OutputVo vo : voList) {
            testEntityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectRowWhereSearchingKeywordSampleOutputVo.TestEntityVo(
                    vo.getUid(),
                    vo.getContent(),
                    vo.getRandomNum(),
                    vo.getTestDatetime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    vo.getRowCreateDate().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    vo.getRowUpdateDate().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowWhereSearchingKeywordSampleOutputVo(
                voList.getTotalElements(), testEntityVoList
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void transactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        db1TemplateTestsRepository.save(new Db1_Template_TestData(
                "error test",
                new Random().nextInt(100000000),
                LocalDateTime.now()
        ));

        throw new RuntimeException("Transaction Rollback Test!");
    }


    ////
    @Override
    public void nonTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        db1TemplateTestsRepository.save(new Db1_Template_TestData(
                "error test",
                new Random().nextInt(100000000),
                LocalDateTime.now()
        ));

        throw new RuntimeException("No Transaction Exception Test!");
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void tryCatchNonTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        try {
            db1TemplateTestsRepository.save(new Db1_Template_TestData(
                    "error test",
                    new Random().nextInt(100000000),
                    LocalDateTime.now()
            ));

            throw new Exception("Transaction Rollback Test!");
        } catch (Exception e) {
            classLogger.error("error ", e);
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowsNoDuplicatePagingSampleOutputVo selectRowsNoDuplicatePagingSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Nullable @org.jetbrains.annotations.Nullable
            Long lastItemUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer pageElementsCount
    ) {
        List<Db1_Native_Repository.ForC7N14OutputVo> voList = db1NativeRepository.forC7N14(lastItemUid, pageElementsCount);
        Long count = db1NativeRepository.forC7N14I1();

        List<MyServiceTkSampleDatabaseTestController.SelectRowsNoDuplicatePagingSampleOutputVo.TestEntityVo> testEntityVoList = new ArrayList<>();
        for (Db1_Native_Repository.ForC7N14OutputVo vo : voList) {
            testEntityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectRowsNoDuplicatePagingSampleOutputVo.TestEntityVo(
                    vo.getUid(),
                    vo.getContent(),
                    vo.getRandomNum(),
                    vo.getTestDatetime().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    vo.getRowCreateDate().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    vo.getRowUpdateDate().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowsNoDuplicatePagingSampleOutputVo(count, testEntityVoList);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowsCountSampleOutputVo selectRowsCountSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        Long count = db1TemplateTestsRepository.countByRowDeleteDateStr("/");

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowsCountSampleOutputVo(count);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowsCountByNativeQuerySampleOutputVo selectRowsCountByNativeQuerySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        Long count = db1NativeRepository.forC7N16();

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowsCountByNativeQuerySampleOutputVo(count);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectRowByNativeQuerySampleOutputVo selectRowByNativeQuerySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid
    ) {
        Optional<Db1_Native_Repository.ForC7N17OutputVo> entityOpt = db1NativeRepository.forC7N17(testTableUid);

        if (entityOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        Db1_Native_Repository.ForC7N17OutputVo entity = entityOpt.get();

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectRowByNativeQuerySampleOutputVo(
                entity.getUid(),
                entity.getContent(),
                entity.getRandomNum(),
                entity.getTestDatetime().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                entity.getRowCreateDate().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                entity.getRowUpdateDate().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.InsertUniqueTestTableRowSampleOutputVo insertUniqueTestTableRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.InsertUniqueTestTableRowSampleInputVo inputVo
    ) {
        Db1_Template_LogicalDeleteUniqueData result = db1TemplateLogicalDeleteUniqueDataRepository.save(new Db1_Template_LogicalDeleteUniqueData(
                inputVo.uniqueValue()
        ));

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.InsertUniqueTestTableRowSampleOutputVo(
                result.uid,
                result.uniqueValue,
                result.rowCreateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowUpdateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowDeleteDateStr
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectUniqueTestTableRowsSampleOutputVo selectUniqueTestTableRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        List<Db1_Template_LogicalDeleteUniqueData> resultEntityList = db1TemplateLogicalDeleteUniqueDataRepository.findAllByRowDeleteDateStrOrderByRowCreateDate("/");
        List<MyServiceTkSampleDatabaseTestController.SelectUniqueTestTableRowsSampleOutputVo.TestEntityVo> entityVoList = new ArrayList<>();
        for (Db1_Template_LogicalDeleteUniqueData resultEntity : resultEntityList) {
            entityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectUniqueTestTableRowsSampleOutputVo.TestEntityVo(
                    resultEntity.uid,
                    resultEntity.uniqueValue,
                    resultEntity.rowCreateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowDeleteDateStr
            ));
        }

        List<Db1_Template_LogicalDeleteUniqueData> logicalDeleteEntityVoList = db1TemplateLogicalDeleteUniqueDataRepository.findAllByRowDeleteDateStrNotOrderByRowCreateDate("/");
        List<MyServiceTkSampleDatabaseTestController.SelectUniqueTestTableRowsSampleOutputVo.TestEntityVo> logicalDeleteVoList = new ArrayList<>();
        for (Db1_Template_LogicalDeleteUniqueData resultEntity : logicalDeleteEntityVoList) {
            logicalDeleteVoList.add(new MyServiceTkSampleDatabaseTestController.SelectUniqueTestTableRowsSampleOutputVo.TestEntityVo(
                    resultEntity.uid,
                    resultEntity.uniqueValue,
                    resultEntity.rowCreateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowDeleteDateStr
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectUniqueTestTableRowsSampleOutputVo(entityVoList, logicalDeleteVoList);
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.UpdateUniqueTestTableRowSampleOutputVo updateUniqueTestTableRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long testTableUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.UpdateUniqueTestTableRowSampleInputVo inputVo
    ) {
        Optional<Db1_Template_LogicalDeleteUniqueData> oldEntityOpt = db1TemplateLogicalDeleteUniqueDataRepository.findByUidAndRowDeleteDateStr(testTableUid, "/");

        if (oldEntityOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        Db1_Template_LogicalDeleteUniqueData oldEntity = oldEntityOpt.get();

        Optional<Db1_Template_LogicalDeleteUniqueData> uniqueValueEntityOpt = db1TemplateLogicalDeleteUniqueDataRepository
                .findByUniqueValueAndRowDeleteDateStr(inputVo.uniqueValue(), "/");

        if (uniqueValueEntityOpt.isPresent()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return null;
        }

        oldEntity.uniqueValue = inputVo.uniqueValue();
        Db1_Template_LogicalDeleteUniqueData result = db1TemplateLogicalDeleteUniqueDataRepository.save(oldEntity);

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.UpdateUniqueTestTableRowSampleOutputVo(
                result.uid,
                result.uniqueValue,
                result.rowCreateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowUpdateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteUniqueTestTableRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    ) {
        Optional<Db1_Template_LogicalDeleteUniqueData> entityOpt = db1TemplateLogicalDeleteUniqueDataRepository
                .findByUidAndRowDeleteDateStr(index, "/");

        if (entityOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_Template_LogicalDeleteUniqueData entity = entityOpt.get();

        entity.rowDeleteDateStr = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
        db1TemplateLogicalDeleteUniqueDataRepository.save(entity);

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.InsertFkParentRowSampleOutputVo insertFkParentRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.InsertFkParentRowSampleInputVo inputVo
    ) {
        Db1_Template_FkTestParent result = db1TemplateFkTestParentRepository.save(
                new Db1_Template_FkTestParent(inputVo.fkParentName())
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.InsertFkParentRowSampleOutputVo(
                result.uid,
                result.parentName,
                result.rowCreateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowUpdateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.InsertFkChildRowSampleOutputVo insertFkChildRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long parentUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleDatabaseTestController.InsertFkChildRowSampleInputVo inputVo
    ) {
        Optional<Db1_Template_FkTestParent> parentEntityOpt = db1TemplateFkTestParentRepository.findById(parentUid);

        if (parentEntityOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        Db1_Template_FkTestParent parentEntity = parentEntityOpt.get();

        Db1_Template_FkTestManyToOneChild result = db1TemplateFkTestManyToOneChildRepository.save(
                new Db1_Template_FkTestManyToOneChild(inputVo.fkChildName(), parentEntity)
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.InsertFkChildRowSampleOutputVo(
                result.uid,
                result.childName,
                result.fkTestParent.parentName,
                result.rowCreateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                result.rowUpdateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsSampleOutputVo selectFkTestTableRowsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        List<Db1_Template_FkTestParent> resultEntityList = db1TemplateFkTestParentRepository
                .findAllByRowDeleteDateStrOrderByRowCreateDate("/");

        List<MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsSampleOutputVo.ParentEntityVo> entityVoList = new ArrayList<>();
        for (Db1_Template_FkTestParent resultEntity : resultEntityList) {
            List<MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsSampleOutputVo.ParentEntityVo.ChildEntityVo> childEntityVoList = new ArrayList<>();

            List<Db1_Template_FkTestManyToOneChild> childList = db1TemplateFkTestManyToOneChildRepository
                    .findAllByFkTestParentAndRowDeleteDateStrOrderByRowCreateDate(resultEntity, "/");

            for (Db1_Template_FkTestManyToOneChild childEntity : childList) {
                childEntityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsSampleOutputVo.ParentEntityVo.ChildEntityVo(
                        childEntity.uid,
                        childEntity.childName,
                        childEntity.rowCreateDate.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        childEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                ));
            }

            entityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsSampleOutputVo.ParentEntityVo(
                    resultEntity.uid,
                    resultEntity.parentName,
                    resultEntity.rowCreateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    childEntityVoList
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsSampleOutputVo(entityVoList);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsByNativeQuerySampleDot1OutputVo selectFkTestTableRowsByNativeQuerySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        List<Db1_Native_Repository.ForC7N24Dot1OutputVo> resultEntityList = db1NativeRepository.forC7N24Dot1();

        List<MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsByNativeQuerySampleDot1OutputVo.ChildEntityVo> entityVoList = new ArrayList<>();
        for (Db1_Native_Repository.ForC7N24Dot1OutputVo resultEntity : resultEntityList) {
            entityVoList.add(new MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsByNativeQuerySampleDot1OutputVo.ChildEntityVo(
                    resultEntity.getChildUid(),
                    resultEntity.getChildName(),
                    resultEntity.getChildCreateDate().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.getChildUpdateDate().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    resultEntity.getParentUid(),
                    resultEntity.getParentName()
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectFkTestTableRowsByNativeQuerySampleDot1OutputVo(entityVoList);

    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.GetNativeQueryReturnValueTestOutputVo getNativeQueryReturnValueTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean inputVal
    ) {
        // boolean 값을 갖고오기 위한 테스트 테이블이 존재하지 않는다면 하나 생성하기
        var justBooleanEntity = db1TemplateJustBooleanTestRepository.findAll();
        if (justBooleanEntity.isEmpty()) {
            db1TemplateJustBooleanTestRepository.save(
                    new Db1_Template_JustBooleanTest(true)
            );
        }

        var resultEntity = db1NativeRepository.forC7N25(inputVal);

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.GetNativeQueryReturnValueTestOutputVo(
                resultEntity.getNormalBoolValue() == 1L,
                resultEntity.getFuncBoolValue() == 1L,
                resultEntity.getIfBoolValue() == 1L,
                resultEntity.getCaseBoolValue() == 1L,
                resultEntity.getTableColumnBoolValue()
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo sqlInjectionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String searchKeyword
    ) {
        var jpaRepositoryResultEntityList = db1TemplateTestsRepository.findAllByContentOrderByRowCreateDate(searchKeyword);
        var jpaRepositoryResultList = new ArrayList<MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo.TestEntityVo>();

        for (var jpaRepositoryResultEntity : jpaRepositoryResultEntityList) {
            jpaRepositoryResultList.add(
                    new MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo.TestEntityVo(
                            jpaRepositoryResultEntity.uid,
                            jpaRepositoryResultEntity.content,
                            jpaRepositoryResultEntity.randomNum,
                            jpaRepositoryResultEntity.testDatetime.atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                            jpaRepositoryResultEntity.rowCreateDate.atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                            jpaRepositoryResultEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                    )
            );
        }

        var jpqlResultEntityList = db1TemplateTestsRepository.findAllByContentOrderByRowCreateDateJpql(searchKeyword);
        var jpqlResultList = new ArrayList<MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo.TestEntityVo>();

        for (var jpqlEntity : jpqlResultEntityList) {
            jpqlResultList.add(
                    new MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo.TestEntityVo(
                            jpqlEntity.uid,
                            jpqlEntity.content,
                            jpqlEntity.randomNum,
                            jpqlEntity.testDatetime.atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                            jpqlEntity.rowCreateDate.atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                            jpqlEntity.rowUpdateDate.atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                    )
            );
        }

        var nativeQueryResultEntityList = db1NativeRepository.forC7N26(searchKeyword);
        var nativeQueryResultList = new ArrayList<MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo.TestEntityVo>();

        for (var nativeQueryEntity : nativeQueryResultEntityList) {
            nativeQueryResultList.add(
                    new MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo.TestEntityVo(
                            nativeQueryEntity.getUid(),
                            nativeQueryEntity.getContent(),
                            nativeQueryEntity.getRandomNum(),
                            nativeQueryEntity.getTestDatetime().atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                            nativeQueryEntity.getRowCreateDate().atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                            nativeQueryEntity.getRowUpdateDate().atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                    )
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SqlInjectionTestOutputVo(
                jpaRepositoryResultList,
                jpqlResultList,
                nativeQueryResultList
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleDatabaseTestController.SelectFkTableRowsWithLatestChildSampleOutputVo selectFkTableRowsWithLatestChildSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        var resultEntityList = db1NativeRepository.forC7N27();

        var entityVoList = new ArrayList<MyServiceTkSampleDatabaseTestController.SelectFkTableRowsWithLatestChildSampleOutputVo.ParentEntityVo>();
        for (var resultEntity : resultEntityList) {
            entityVoList.add(
                    new MyServiceTkSampleDatabaseTestController.SelectFkTableRowsWithLatestChildSampleOutputVo.ParentEntityVo(
                            resultEntity.getParentUid(),
                            resultEntity.getParentName(),
                            resultEntity.getParentCreateDate().atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                            resultEntity.getParentUpdateDate().atZone(ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                            resultEntity.getChildUid() == null ? null : new MyServiceTkSampleDatabaseTestController.SelectFkTableRowsWithLatestChildSampleOutputVo.ParentEntityVo.ChildEntityVo(
                                    resultEntity.getChildUid(),
                                    Objects.requireNonNull(resultEntity.getChildName()),
                                    Objects.requireNonNull(resultEntity.getChildCreateDate()).atZone(ZoneId.systemDefault())
                                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                                    Objects.requireNonNull(resultEntity.getChildUpdateDate()).atZone(ZoneId.systemDefault())
                                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                            )
                    )
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleDatabaseTestController.SelectFkTableRowsWithLatestChildSampleOutputVo(entityVoList);

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteFkChildRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    ) {
        var entityOpt = db1TemplateFkTestManyToOneChildRepository.findById(index);

        if (entityOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        db1TemplateFkTestManyToOneChildRepository.deleteById(index);

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteFkParentRowSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long index
    ) {
        var entityOpt = db1TemplateFkTestParentRepository.findById(index);

        if (entityOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        db1TemplateFkTestParentRepository.deleteById(index);

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void fkTableTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        var parentEntity = db1TemplateFkTestParentRepository.save(
                new Db1_Template_FkTestParent("transaction test")
        );

        db1TemplateFkTestManyToOneChildRepository.save(
                new Db1_Template_FkTestManyToOneChild("transaction test1", parentEntity)
        );

        db1TemplateFkTestManyToOneChildRepository.save(
                new Db1_Template_FkTestManyToOneChild("transaction test2", parentEntity)
        );

        throw new RuntimeException("Transaction Rollback Test!");
    }


    ////
    @Override
    public void fkTableNonTransactionTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        var parentEntity = db1TemplateFkTestParentRepository.save(
                new Db1_Template_FkTestParent("transaction test")
        );

        db1TemplateFkTestManyToOneChildRepository.save(
                new Db1_Template_FkTestManyToOneChild("transaction test1", parentEntity)
        );

        db1TemplateFkTestManyToOneChildRepository.save(
                new Db1_Template_FkTestManyToOneChild("transaction test2", parentEntity)
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }
}