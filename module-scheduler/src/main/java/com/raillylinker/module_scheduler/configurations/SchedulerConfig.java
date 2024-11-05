package com.raillylinker.module_scheduler.configurations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ScheduledTaskRegistrar taskRegistrar
    ) {
        // 스케쥴러 스레드 설정
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        // 스레드 풀 크기 설정
        threadPoolTaskScheduler.setPoolSize(10);
        // 스레드 풀 접두사 설정
        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool");
        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}