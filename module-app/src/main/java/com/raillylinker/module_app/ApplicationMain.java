package com.raillylinker.module_app;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;


@EnableMongoAuditing // MongoDB 에서 @CreatedDate, @LastModifiedDate 사용 설정
@EnableScheduling // 스케쥴러 사용 설정
@EnableAsync // 스케쥴러의 Async 사용 설정
@ComponentScan(
        basePackages = {
                // !!!Bean 스캔할 모듈들의 패키지 리스트(group) 추가하기!!!
                "com.raillylinker"
        }
)
//@EntityScan("com.raillylinker.module_idp_jpa.jpa_beans.entities")
//@EnableJpaRepositories("com.raillylinker.module_idp_jpa.jpa_beans.repositories")
@SpringBootApplication
public class ApplicationMain {
    @Bean
    public CommandLineRunner init() {
        return args -> {
            // 서버 타임존 명시적 설정 (UTC, Asia/Seoul, ...)
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//            System.out.println("Current TimeZone: " + TimeZone.getDefault().getID());
        };
    }

    public static void main(@Valid @NotNull @org.jetbrains.annotations.NotNull String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}