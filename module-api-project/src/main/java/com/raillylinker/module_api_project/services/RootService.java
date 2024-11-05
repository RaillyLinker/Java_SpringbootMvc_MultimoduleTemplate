package com.raillylinker.module_api_project.services;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.servlet.ModelAndView;


public interface RootService {
    // (루트 홈페이지 반환 함수)
    ModelAndView getRootHomePage(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );

//    ////
//    // (Project Runtime Config Redis Key-Value 모두 조회)
//    C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo api2SelectAllProjectRuntimeConfigsRedisKeyValue(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    );
//
//    ////
//    // (Redis Project Runtime Config actuatorAllowIpList 입력)
//    void api3InsertProjectRuntimeConfigActuatorAllowIpList(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            C1Controller.Api3InsertProjectRuntimeConfigActuatorAllowIpListInputVo inputVo
//    );
//
//    ////
//    // (Redis Project Runtime Config loggingDenyIpList 입력)
//    void api4InsertProjectRuntimeConfigLoggingDenyIpList(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull C1Controller.Api4InsertProjectRuntimeConfigLoggingDenyIpListInputVo inputVo
//    );
}