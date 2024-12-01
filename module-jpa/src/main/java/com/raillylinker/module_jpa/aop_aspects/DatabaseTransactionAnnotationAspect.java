package com.raillylinker.module_jpa.aop_aspects;

import com.raillylinker.module_jpa.annotations.CustomTransactional;
import com.raillylinker.module_jpa.const_objects.ModuleConst;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

// [JPA Database @CustomTransactional 어노테이션 함수 처리 AOP]
@Component
@Aspect
public class DatabaseTransactionAnnotationAspect {
    public DatabaseTransactionAnnotationAspect(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ApplicationContext applicationContext
    ) {
        this.applicationContext = applicationContext;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ApplicationContext applicationContext;

    // DB 트랜젝션용 어노테이션인 CustomTransactional 파일의 프로젝트 경로
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final String TRANSACTION_ANNOTATION_PATH =
            "@annotation(" + ModuleConst.PACKAGE_NAME + ".annotations.CustomTransactional)";


    // ---------------------------------------------------------------------------------------------
    // <AOP 작성 공간>
    // (@CustomTransactional 를 입력한 함수 실행 전후에 JPA 트랜젝션 적용)
    @Around(TRANSACTION_ANNOTATION_PATH)
    @Nullable
    @org.jetbrains.annotations.Nullable
    public Object aroundTransactionAnnotationFunction(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ProceedingJoinPoint joinPoint
    ) throws Throwable {
        @Nullable @org.jetbrains.annotations.Nullable
        Object proceed;

        // transactionManager and transactionStatus 리스트
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<Pair<PlatformTransactionManager, TransactionStatus>> transactionManagerAndTransactionStatusList = new ArrayList<>();

        try {
            // annotation 에 설정된 transaction 순차 실행 및 저장
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CustomTransactional customTransactional = signature.getMethod().getAnnotation(CustomTransactional.class);
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull String transactionManagerBeanName : customTransactional.transactionManagerBeanNameList()) {
                // annotation 에 저장된 transactionManager Bean 이름으로 Bean 객체 가져오기
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager) applicationContext.getBean(transactionManagerBeanName);

                // CustomTransactional 에서 읽기 전용 속성 확인
                @Valid @NotNull
                boolean readOnly = customTransactional.readOnly();

                // readOnly 설정을 transactionDefinition 에 적용
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
                defaultTransactionDefinition.setReadOnly(readOnly);

                // transaction 시작 및 정보 저장
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);
                transactionManagerAndTransactionStatusList.add(Pair.of(platformTransactionManager, transactionStatus));
            }

            // 함수 실행
            proceed = joinPoint.proceed();

            // annotation 에 설정된 transaction commit 역순 실행 및 저장
            for (int transactionManagerIdx = transactionManagerAndTransactionStatusList.size() - 1; transactionManagerIdx >= 0; transactionManagerIdx--) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull Pair<PlatformTransactionManager, TransactionStatus> transactionManager = transactionManagerAndTransactionStatusList.get(transactionManagerIdx);
                transactionManager.getFirst().commit(transactionManager.getSecond());
            }
        } catch (@Valid @NotNull @org.jetbrains.annotations.NotNull Exception e) {
            // annotation 에 설정된 transaction rollback 역순 실행 및 저장
            for (@Valid @NotNull @org.jetbrains.annotations.NotNull Integer transactionManagerIdx = transactionManagerAndTransactionStatusList.size() - 1; transactionManagerIdx >= 0; transactionManagerIdx--) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Pair<PlatformTransactionManager, TransactionStatus> transactionManager = transactionManagerAndTransactionStatusList.get(transactionManagerIdx);
                transactionManager.getFirst().rollback(transactionManager.getSecond());
            }
            throw e;
        }

        return proceed; // 결과 리턴
    }
}