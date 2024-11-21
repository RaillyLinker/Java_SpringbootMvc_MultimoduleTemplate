package com.raillylinker.module_batch.batch_components;

import com.raillylinker.module_jpa.configurations.jpa_configs.Db1MainConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Component
public class TaskletBatchTest {
    public TaskletBatchTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JobRepository jobRepository,
            @Qualifier(Db1MainConfig.TRANSACTION_NAME)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PlatformTransactionManager transactionManager
    ) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final JobRepository jobRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final PlatformTransactionManager transactionManager;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String BATCH_JOB_NAME = "TaskletBatchTest";

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(TaskletBatchTest.class);


    // ---------------------------------------------------------------------------------------------
    // [Batch Job 및 하위 작업 작성]
    // BatchConfig 하나당 하나의 Job 을 가져야 합니다.

    // (Batch Job)
    @Bean(BATCH_JOB_NAME)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Job batchJob() {
        return new JobBuilder(BATCH_JOB_NAME + "_batchJob", jobRepository)
                .start(taskletTestStep())
                .build();
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Step taskletTestStep() {
        return new StepBuilder(BATCH_JOB_NAME + "_taskletTestStep", jobRepository)
                .tasklet(justLoggingTasklet(), transactionManager)
                .build();
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Tasklet justLoggingTasklet() {
        return (contribution, chunkContext) -> {
            classLogger.info("TaskletBatchTest : Tasklet Batch Test Complete!");
            return RepeatStatus.FINISHED;
        };
    }
}