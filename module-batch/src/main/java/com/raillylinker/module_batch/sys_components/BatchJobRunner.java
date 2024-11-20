package com.raillylinker.module_batch.sys_components;

import com.raillylinker.module_batch.configurations.ChunkBatchTestConfig;
import com.raillylinker.module_batch.configurations.TaskletBatchTestConfig;
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

@Component
public class BatchJobRunner implements CommandLineRunner {
    public BatchJobRunner(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JobLauncher jobLauncher,
            @Qualifier(TaskletBatchTestConfig.BATCH_JOB_NAME)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Job taskletBatchTestJob,
            @Qualifier(ChunkBatchTestConfig.BATCH_JOB_NAME)
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
    private final Logger classLogger = LoggerFactory.getLogger(ChunkBatchTestConfig.class);

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