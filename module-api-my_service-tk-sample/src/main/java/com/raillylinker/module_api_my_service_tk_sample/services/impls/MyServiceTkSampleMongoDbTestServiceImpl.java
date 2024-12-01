package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleMongoDbTestController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleMongoDbTestService;
import com.raillylinker.module_mongodb.annotations.CustomMongoDbTransactional;
import com.raillylinker.module_mongodb.configurations.mongodb_configs.Mdb1MainConfig;
import com.raillylinker.module_mongodb.mongodb_beans.mdb1_main.documents.Mdb1_Test;
import com.raillylinker.module_mongodb.mongodb_beans.mdb1_main.repositories.Mdb1_Test_Repository;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MyServiceTkSampleMongoDbTestServiceImpl implements MyServiceTkSampleMongoDbTestService {
    public MyServiceTkSampleMongoDbTestServiceImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Mdb1_Test_Repository mdb1TestRepository
    ) {
        this.mdb1TestRepository = mdb1TestRepository;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Mdb1_Test_Repository mdb1TestRepository;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final Logger classLogger = LoggerFactory.getLogger(MyServiceTkSampleMongoDbTestServiceImpl.class);


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    // ReplicaSet 환경이 아니면 에러가 납니다.
    @CustomMongoDbTransactional(transactionManagerBeanNameList = {Mdb1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMongoDbTestController.InsertDocumentTestOutputVo insertDocumentTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMongoDbTestController.InsertDocumentTestInputVo inputVo
    ) {
        Mdb1_Test resultCollection = mdb1TestRepository.save(new Mdb1_Test(
                inputVo.content(),
                new Random().nextInt(100000000),
                inputVo.nullableValue(),
                true
        ));

        httpServletResponse.setHeader("api-result-code", "");
        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleMongoDbTestController.InsertDocumentTestOutputVo(
                resultCollection.uid,
                resultCollection.content,
                resultCollection.nullableValue,
                resultCollection.randomNum,
                resultCollection.rowCreateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                resultCollection.rowUpdateDate.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    // ReplicaSet 환경이 아니면 에러가 납니다.
    @CustomMongoDbTransactional(transactionManagerBeanNameList = {Mdb1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteAllDocumentTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        mdb1TestRepository.deleteAll();

        httpServletResponse.setHeader("api-result-code", "");
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    // ReplicaSet 환경이 아니면 에러가 납니다.
    @CustomMongoDbTransactional(transactionManagerBeanNameList = {Mdb1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteDocumentTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    ) {
        Optional<Mdb1_Test> testDocument = mdb1TestRepository.findById(id);

        if (testDocument.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        mdb1TestRepository.deleteById(id);

        httpServletResponse.setHeader("api-result-code", "");
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    // ReplicaSet 환경이 아니면 에러가 납니다.
    @CustomMongoDbTransactional(transactionManagerBeanNameList = {Mdb1MainConfig.TRANSACTION_NAME}, readOnly = true)
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleMongoDbTestController.SelectAllDocumentsTestOutputVo selectAllDocumentsTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {

        List<Mdb1_Test> testCollectionList = mdb1TestRepository.findAll();

        List<MyServiceTkSampleMongoDbTestController.SelectAllDocumentsTestOutputVo.TestEntityVo> resultVoList = new ArrayList<>();

        for (Mdb1_Test testCollection : testCollectionList) {
            resultVoList.add(new MyServiceTkSampleMongoDbTestController.SelectAllDocumentsTestOutputVo.TestEntityVo(
                    testCollection.uid,
                    testCollection.content,
                    testCollection.nullableValue,
                    testCollection.randomNum,
                    testCollection.rowCreateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    testCollection.rowUpdateDate.atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
            ));
        }

        httpServletResponse.setHeader("api-result-code", "");
        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleMongoDbTestController.SelectAllDocumentsTestOutputVo(resultVoList);
    }


    ////
    // ReplicaSet 환경이 아니면 에러가 납니다.
    @CustomMongoDbTransactional(transactionManagerBeanNameList = {Mdb1MainConfig.TRANSACTION_NAME})
    @Override
    public void transactionRollbackTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        mdb1TestRepository.save(new Mdb1_Test(
                "test",
                new Random().nextInt(100000000),
                null,
                true
        ));

        throw new RuntimeException("Transaction Rollback Test!");
    }


    ////
    @Override
    public void noTransactionRollbackTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        mdb1TestRepository.save(new Mdb1_Test(
                "test",
                new Random().nextInt(100000000),
                null,
                true
        ));

        throw new RuntimeException("No Transaction Exception Test!");
    }
}
