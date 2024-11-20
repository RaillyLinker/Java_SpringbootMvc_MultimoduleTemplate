package com.raillylinker.module_batch.configurations;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.raillylinker.module_jpa.configurations.jpa_configs.Db1MainConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

// [SpringBatch Chunk 테스트]
@Configuration
@EnableBatchProcessing(
        dataSourceRef = Db1MainConfig.DATABASE_DIRECTORY_NAME + "_DataSource",
        transactionManagerRef = Db1MainConfig.TRANSACTION_NAME,
        tablePrefix = "batch_metadata.BATCH_"
)
public class ChunkBatchTestConfig {
    public ChunkBatchTestConfig(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JobRepository jobRepository,
            @Qualifier(Db1MainConfig.TRANSACTION_NAME)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PlatformTransactionManager transactionManager
    ) throws IOException {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;

        this.saveDirectoryPath = Paths.get("./by_product_files/test/batch_test").toAbsolutePath().normalize();

        initializeTestFiles();
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final JobRepository jobRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final PlatformTransactionManager transactionManager;

    // 배치 Job 이름
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String BATCH_JOB_NAME = "ChunkBatchTest";

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(ChunkBatchTestConfig.class);
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Gson gson = new Gson();

    // JSON 파일 저장 위치
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Path saveDirectoryPath;

    // (테스트용 데이터 파일이 없으면 생성)
    private void initializeTestFiles() throws IOException {
        Files.createDirectories(saveDirectoryPath);
        try (Stream<Path> files = Files.list(saveDirectoryPath)) {
            if (files.findAny().isEmpty()) {
                for (int index = 1; index <= 10; index++) {
                    String fileName = "batch_test" + index + ".json";
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", randomString(6));
                    jsonObject.addProperty("num", new Random().nextInt(1000));
                    Files.writeString(saveDirectoryPath.resolve(fileName), gson.toJson(jsonObject));
                }

                classLogger.info("ChunkBatchTest : 파일 생성 완료");
            }
        }
    }

    private String randomString(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer length
    ) {
        return new Random().ints('A', 'Z' + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    // ---------------------------------------------------------------------------------------------
    // [Batch Job 및 하위 작업 작성]
    // BatchConfig 하나당 하나의 Job 을 가져야 합니다.

    // (Batch Job)
    @Bean(BATCH_JOB_NAME)
    public Job batchJob() {
        return new JobBuilder(BATCH_JOB_NAME + "_batchJob", jobRepository)
                .start(chunkTestStep())
                .build();
    }

    @Bean
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Step chunkTestStep() {
        return new StepBuilder(BATCH_JOB_NAME + "_chunkTestStep", jobRepository)
                .<String, JsonObject>chunk(10, transactionManager)
                .reader(jsonFileReader())
                .processor(jsonProcessor())
                .writer(jsonWriter())
                .build();
    }

    @Bean
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public ItemReader<@Valid @NotNull String> jsonFileReader() {
        try {
            List<String> files = Files.list(saveDirectoryPath)
                    .map(Path::toString)
                    .toList();
            Iterator<String> iterator = files.iterator();
            return iterator::next;
        } catch (IOException e) {
            throw new RuntimeException("Error reading files", e);
        }
    }

    @Bean
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public ItemProcessor<@Valid @NotNull String, @Valid @NotNull JsonObject> jsonProcessor() {
        return filePath -> {
            try {
                String jsonString = Files.readString(Paths.get(filePath));
                JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
                jsonObject.addProperty("name", randomString(6));
                jsonObject.addProperty("num", jsonObject.get("num").getAsInt() + 1);
                jsonObject.addProperty("fileName", Paths.get(filePath).getFileName().toString());
                return jsonObject;
            } catch (IOException e) {
                throw new RuntimeException("Error processing file: " + filePath, e);
            }
        };
    }

    @Bean
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public ItemWriter<@Valid @NotNull JsonObject> jsonWriter() {
        return items -> items.forEach(jsonObject -> {
            try {
                String fileName = jsonObject.remove("fileName").getAsString();
                Path filePath = saveDirectoryPath.resolve(fileName);
                Files.writeString(filePath, gson.toJson(jsonObject));
                classLogger.info("ChunkBatchTest : Updated {}", filePath);
            } catch (IOException e) {
                throw new RuntimeException("Error writing file", e);
            }
        });
    }
}