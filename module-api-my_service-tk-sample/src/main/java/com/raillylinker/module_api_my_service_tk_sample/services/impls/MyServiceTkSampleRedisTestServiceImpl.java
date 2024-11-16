package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleRedisTestController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleRedisTestService;
import com.raillylinker.module_redis.redis_map_components.redis1_main.Redis1_Lock_Test;
import com.raillylinker.module_redis.redis_map_components.redis1_main.Redis1_Map_Test;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
    Redis 는 주로 캐싱에 사용됩니다.
    이러한 특징에 기반하여, 본 프로젝트에서는 Redis 를 쉽고 편하게 사용하기 위하여 Key-Map 형식으로 래핑하여 사용하고 있으며,
    인 메모리 데이터 구조 저장소인 Redis 의 성능을 해치지 않기 위하여, Database 와는 달리 트랜젝션 처리를 따로 하지 않습니다.
    (Redis 는 애초에 고성능, 단순성을 위해 설계되었고, 롤백 기능을 지원하지 않으므로 일반적으로는 어플리케이션 레벨에서 처리합니다.)
    고로 Redis 에 값을 입력/삭제/수정하는 로직은, API 의 별도 다른 알고리즘이 모두 실행된 이후, "코드의 끝자락에서 한꺼번에 변경"하도록 처리하세요.
    그럼에도 트랜젝션 기능이 필요하다고 한다면,
    비동기 실행을 고려하여 Semaphore 등으로 락을 건 후, 기존 데이터를 백업한 후, 에러가 일어나면 복원하는 방식을 사용하면 됩니다.
 */
@Service
public class MyServiceTkSampleRedisTestServiceImpl implements MyServiceTkSampleRedisTestService {
    public MyServiceTkSampleRedisTestServiceImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Redis1_Map_Test redis1Test,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Redis1_Lock_Test redis1LockTest
    ) {
        this.redis1Test = redis1Test;
        this.redis1LockTest = redis1LockTest;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Redis1_Map_Test redis1Test;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Redis1_Lock_Test redis1LockTest;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    public void insertRedisKeyValueTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleRedisTestController.InsertRedisKeyValueTestInputVo inputVo
    ) {
        redis1Test.saveKeyValue(
                inputVo.key(),
                new Redis1_Map_Test.ValueVo(
                        inputVo.content(),
                        new Redis1_Map_Test.ValueVo.InnerVo("testObject", true),
                        List.of(
                                new Redis1_Map_Test.ValueVo.InnerVo("testObject1", false),
                                new Redis1_Map_Test.ValueVo.InnerVo("testObject2", true)
                        )
                ),
                inputVo.expirationMs()
        );
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRedisTestController.SelectRedisValueSampleOutputVo selectRedisValueSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key
    ) {
        var keyValue = redis1Test.findKeyValue(key);

        if (keyValue == null) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRedisTestController.SelectRedisValueSampleOutputVo(
                Redis1_Map_Test.MAP_NAME,
                keyValue.key(),
                keyValue.value().content(),
                keyValue.expireTimeMs()
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRedisTestController.SelectAllRedisKeyValueSampleOutputVo selectAllRedisKeyValueSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        var keyValueList = redis1Test.findAllKeyValues();
        var testEntityListVoList = new ArrayList<MyServiceTkSampleRedisTestController.SelectAllRedisKeyValueSampleOutputVo.KeyValueVo>();

        for (var keyValue : keyValueList) {
            testEntityListVoList.add(new MyServiceTkSampleRedisTestController.SelectAllRedisKeyValueSampleOutputVo.KeyValueVo(
                    keyValue.key(),
                    keyValue.value().content(),
                    keyValue.expireTimeMs()
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRedisTestController.SelectAllRedisKeyValueSampleOutputVo(
                Redis1_Map_Test.MAP_NAME,
                testEntityListVoList
        );
    }


    ////
    @Override
    public void deleteRedisKeySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key
    ) {
        var keyValue = redis1Test.findKeyValue(key);

        if (keyValue == null) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        redis1Test.deleteKeyValue(key);
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    public void deleteAllRedisKeySample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        redis1Test.deleteAllKeyValues();
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleRedisTestController.TryRedisLockSampleOutputVo tryRedisLockSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        var lockKey = redis1LockTest.tryLock(100000L);
        if (lockKey == null) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleRedisTestController.TryRedisLockSampleOutputVo(lockKey);
    }


    ////
    @Override
    public void unLockRedisLockSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String lockKey
    ) {
        redis1LockTest.unlock(lockKey);
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }
}
