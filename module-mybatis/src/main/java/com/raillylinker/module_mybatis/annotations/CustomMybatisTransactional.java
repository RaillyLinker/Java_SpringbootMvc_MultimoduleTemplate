package com.raillylinker.module_mybatis.annotations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// [JPA 의 Transactional 을 여러 TransactionManager 으로 사용 가능하도록 개조한 annotation]
// 사용 예시 : @CustomMybatisTransactional([Mybatis1MainConfig.TRANSACTION_NAME])
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomMybatisTransactional {
    @Valid @NotNull @org.jetbrains.annotations.NotNull String[] transactionManagerBeanNameList();
}