package com.raillylinker.module_redis.abstract_classes;

import com.google.gson.Gson;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

// [RedisMap 의 Abstract 클래스]
// 본 추상 클래스를 상속받은 클래스를 key, value, expireTime 및 Redis 저장, 삭제, 조회 기능 메소드를 가진 클래스로 만들어줍니다.
// Redis Storage 를 Map 타입처럼 사용 가능하도록 래핑해주는 역할을 합니다.
public abstract class BasicRedisMap<ValueVo> {
    public BasicRedisMap(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RedisTemplate<@Valid @NotNull String, @Valid @NotNull String> redisTemplateObj,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String mapName,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Class<ValueVo> clazz
    ) {
        this.redisTemplateObj = redisTemplateObj;
        this.mapName = mapName;
        this.clazz = clazz;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final RedisTemplate<@Valid @NotNull String, @Valid @NotNull String> redisTemplateObj;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String mapName;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Class<ValueVo> clazz;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Gson gson = new Gson();

    // <공개 메소드 공간>
    // (RedisMap 에 Key-Value 저장)
    public void saveKeyValue(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ValueVo value,
            @Nullable @org.jetbrains.annotations.Nullable
            Long expireTimeMs
    ) {
        // 입력 키 검증
        validateKey(key);

        // Redis Storage 에 실제로 저장 되는 키 (map 이름과 키를 합친 String)
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        String innerKey = mapName + ":" + key;

        // Redis Storage 에 실제로 저장 되는 Value (Json String 형식)
        redisTemplateObj.opsForValue().set(innerKey, gson.toJson(value));

        if (expireTimeMs != null) {
            // Redis Key 에 대한 만료시간 설정
            redisTemplateObj.expire(innerKey, expireTimeMs, TimeUnit.MILLISECONDS);
        }
    }

    // (RedisMap 의 모든 Key-Value 리스트 반환)
    public @Valid @NotNull @org.jetbrains.annotations.NotNull List<@Valid @NotNull RedisMapDataVo<ValueVo>> findAllKeyValues() {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<@Valid @NotNull RedisMapDataVo<ValueVo>> resultList = new ArrayList<>();

        Set<@Valid @NotNull String> keySet = redisTemplateObj.keys(mapName + ":*");

        if (keySet == null) {
            return resultList;
        }

        for (@Valid @NotNull @org.jetbrains.annotations.NotNull String innerKey : keySet) {
            // innerKey : Redis Storage 에 실제로 저장 되는 키 (map 이름과 키를 합친 String)

            // 외부적으로 사용되는 Key (innerKey 에서 map 이름을 제거한 String)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key = innerKey.substring(mapName.length() + 1);

            // Redis Storage 에 실제로 저장 되는 Value (Json String 형식)
            String innerValue = redisTemplateObj.opsForValue().get(innerKey);
            if (innerValue == null) continue;

            // 외부적으로 사용되는 Value (Json String 을 테이블 객체로 변환)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ValueVo valueObject = gson.fromJson(innerValue, clazz);

            Long expireTimeMs = redisTemplateObj.getExpire(innerKey, TimeUnit.MILLISECONDS);

            resultList.add(new RedisMapDataVo<>(key, valueObject, expireTimeMs == null ? -1 : expireTimeMs));
        }

        return resultList;
    }

    // (RedisMap 의 key-Value 를 반환)
    @Nullable @org.jetbrains.annotations.Nullable
    public RedisMapDataVo<ValueVo> findKeyValue(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key
    ) {
        // 입력 키 검증
        validateKey(key);

        // Redis Storage 에 실제로 저장 되는 키 (map 이름과 키를 합친 String)
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        String innerKey = mapName + ":" + key;

        // Redis Storage 에 실제로 저장 되는 Value (Json String 형식)
        String innerValue = redisTemplateObj.opsForValue().get(innerKey);

        if (innerValue == null) {
            return null;
        } else {
            // 외부적으로 사용되는 Value (Json String 을 테이블 객체로 변환)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ValueVo valueObject = gson.fromJson(innerValue, clazz);

            Long expireTimeMs = redisTemplateObj.getExpire(innerKey, TimeUnit.MILLISECONDS);

            return new RedisMapDataVo<>(key, valueObject, expireTimeMs == null ? -1 : expireTimeMs);
        }
    }

    // (RedisMap 의 모든 Key-Value 리스트 삭제)
    public void deleteAllKeyValues() {
        Set<String> keySet = redisTemplateObj.keys(mapName + ":*");

        if (keySet == null) {
            keySet = new HashSet<>();
        }

        redisTemplateObj.delete(keySet);
    }

    // (RedisMap 의 Key-Value 를 삭제)
    public void deleteKeyValue(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key
    ) {
        // 입력 키 검증
        validateKey(key);

        // Redis Storage 에 실제로 저장 되는 키 (map 이름과 키를 합친 String)
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        String innerKey = mapName + ":" + key;

        redisTemplateObj.delete(innerKey);
    }

    // ---------------------------------------------------------------------------------------------
    // <비공개 메소드 공간>
    // (입력 키 검증 함수)
    private void validateKey(@Valid @NotNull @org.jetbrains.annotations.NotNull String key) {
        if (key.trim().isEmpty()) {
            throw new RuntimeException("key 는 비어있을 수 없습니다.");
        }
        if (key.contains(":")) {
            throw new RuntimeException("key 는 :를 포함할 수 없습니다.");
        }
    }


    // ---------------------------------------------------------------------------------------------
    // <중첩 클래스 공간>
    // [RedisMap 의 출력값 데이터 클래스]
    public record RedisMapDataVo<ValueVo>(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String key,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ValueVo value,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long expireTimeMs
    ) {
    }
}