package com.raillylinker.module_redis.redis_map_components.redis1_main;

import com.raillylinker.module_redis.abstract_classes.BasicRedisMap;
import com.raillylinker.module_redis.configurations.redis_configs.Redis1MainConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

// [RedisMap 컴포넌트]
// 프로젝트 내부에서 사용할 IP 관련 설정 저장 타입입니다.
@Component
public class Redis1_Map_TotalAuthForceExpireAuthorizationSet extends BasicRedisMap<Redis1_Map_TotalAuthForceExpireAuthorizationSet.ValueVo> {
    public Redis1_Map_TotalAuthForceExpireAuthorizationSet(
            // !!!RedisConfig 종류 변경!!!
            @Qualifier(Redis1MainConfig.REDIS_TEMPLATE_NAME)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RedisTemplate<@Valid @NotNull String, @Valid @NotNull String> redisTemplate
    ) {
        super(redisTemplate, MAP_NAME, ValueVo.class);
    }

    // <멤버 변수 공간>
    // !!!중복되지 않도록, 본 클래스명을 MAP_NAME 으로 설정하기!!!
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String MAP_NAME = "Redis1_Map_TotalAuthForceExpireAuthorizationSet";

    // !!!본 RedisMAP 의 Value 클래스 설정!!!
    public record ValueVo() {
    }
}