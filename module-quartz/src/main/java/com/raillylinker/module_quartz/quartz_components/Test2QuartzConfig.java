package com.raillylinker.module_quartz.quartz_components;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Test2QuartzConfig {
    private static final String QUARTZ_NAME = "Test2Quartz";

//    @Bean(name = QUARTZ_NAME + "_Trigger")
//    public Trigger jobTrigger() {
//        return TriggerBuilder
//                .newTrigger()
//                // 트리거 ID 설정 (같은 ID는 주기당 한 번씩만 동작)
//                .withIdentity(QUARTZ_NAME + "_Trigger")
//                // Job Detail 설정
//                .forJob(testJobDetail())
//                // 실행 시간 설정 (애플리케이션 실행 시점에 실행)
//                .startNow()
//                // 반복 스케줄 설정 (4초마다 실행)
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/4 * * * * ?"))
//                .build();
//    }
//
//    @Bean(name = QUARTZ_NAME + "_Job")
//    public JobDetail testJobDetail() {
//        return JobBuilder.newJob(TestQuartzJob.class)
//                .withIdentity(QUARTZ_NAME + "_Job")
//                .storeDurably()
//                .build();
//    }
//
//    public static class TestQuartzJob implements Job {
//        @Override
//        public void execute(JobExecutionContext context) {
//            System.out.println("Run " + QUARTZ_NAME);
//        }
//    }
}