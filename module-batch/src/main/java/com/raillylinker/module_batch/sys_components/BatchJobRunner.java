package com.raillylinker.module_batch.sys_components;

import com.raillylinker.module_batch.batch_components.ChunkBatchTest;
import com.raillylinker.module_batch.batch_components.TaskletBatchTest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

// (Batch Job 을 어플리케이션 실행 시점에 실행하도록 해주는 코드 = 테스트용)
/*
    SpringBatch 에는 meta data 를 저장할 데이터베이스 테이블이 필요한데,
    이러한 테이블들은,
    외부 라이브러리 - Gradle: org.springframework.batch:spring-batch-core:5.1.2 -
    spring-batch-core-5.1.2.jar - org - springframework - batch - core
    안의 schema-{데이터베이스 종류}.sql 안에 저장된 SQL 문을 참고하여 생성하면 됩니다.
 */
@Component
public class BatchJobRunner implements CommandLineRunner {
    public BatchJobRunner(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JobLauncher jobLauncher,
            @Qualifier(TaskletBatchTest.BATCH_JOB_NAME)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Job taskletBatchTestJob,
            @Qualifier(ChunkBatchTest.BATCH_JOB_NAME)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Job chunkBatchTestJob
    ) {
        this.jobLauncher = jobLauncher;
        this.taskletBatchTestJob = taskletBatchTestJob;
        this.chunkBatchTestJob = chunkBatchTestJob;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final JobLauncher jobLauncher;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Job taskletBatchTestJob;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Job chunkBatchTestJob;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(ChunkBatchTest.class);

    @Override
    public void run(String... args) {
        try {
            // (TaskletBatchTest Job 실행)
            jobLauncher.run(
                    taskletBatchTestJob,
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis())
                            .toJobParameters()
            );

            // (ChunkBatchTest Job 실행)
            jobLauncher.run(
                    chunkBatchTestJob,
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis())
                            .toJobParameters()
            );
        } catch (Exception e) {
            classLogger.error("error : ", e);
        }
    }
}