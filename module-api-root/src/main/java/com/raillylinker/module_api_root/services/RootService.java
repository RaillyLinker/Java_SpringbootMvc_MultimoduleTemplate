package com.raillylinker.module_api_root.services;

import com.raillylinker.module_api_root.controllers.RootController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.servlet.ModelAndView;

public interface RootService {
    // (루트 홈페이지 반환 함수)
    @Nullable
    @org.jetbrains.annotations.Nullable
    ModelAndView getRootHomePage(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (Project Runtime Config Redis Key-Value 모두 조회)
    @Nullable
    @org.jetbrains.annotations.Nullable
    RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo selectAllProjectRuntimeConfigsRedisKeyValue(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (Redis Project Runtime Config actuatorAllowIpList 입력)
    void insertProjectRuntimeConfigActuatorAllowIpList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RootController.InsertProjectRuntimeConfigActuatorAllowIpListInputVo inputVo)
    ;


    ////
    // (Redis Project Runtime Config loggingDenyIpList 입력)
    void insertProjectRuntimeConfigLoggingDenyIpList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RootController.InsertProjectRuntimeConfigLoggingDenyIpListInputVo inputVo
    );
}
