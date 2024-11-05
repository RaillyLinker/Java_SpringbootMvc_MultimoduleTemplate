package com.raillylinker.module_api_project.services.impls;

import com.raillylinker.module_api_project.services.RootService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


@Service
public class RootServiceImpl implements RootService {
    public RootServiceImpl(
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile,
            @Value("${springdoc.swagger-ui.enabled}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean swaggerEnabled
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            ActuatorWhiteList actuatorWhiteList
    ) {
        this.activeProfile = activeProfile;
        this.swaggerEnabled = swaggerEnabled;
//        this.redis1RuntimeConfigIpList = redis1RuntimeConfigIpList;
//        this.actuatorWhiteList = actuatorWhiteList;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String activeProfile;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Boolean swaggerEnabled;
//    @Valid
//    @NotNull
//    @org.jetbrains.annotations.NotNull
//    private final Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList;
//    @Valid
//    @NotNull
//    @org.jetbrains.annotations.NotNull
//    private final ActuatorWhiteList actuatorWhiteList;

    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    public ModelAndView getRootHomePage(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        ModelAndView mv = new ModelAndView();
        mv.setViewName("root_home_page/home_page");

        mv.addObject(
                "viewModel",
                new Api1ViewModel(
                        activeProfile,
                        swaggerEnabled
                )
        );

        return mv;
    }

    public record Api1ViewModel(String env, boolean showApiDocumentBtn) {
    }

//    ////
//    @Override
//    public C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo api2SelectAllProjectRuntimeConfigsRedisKeyValue(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse
//    ) {
//        @Valid @NotNull @org.jetbrains.annotations.NotNull
//        List<C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo> testEntityListVoList = new ArrayList<>();
//
//        // actuator 저장 정보 가져오기
//        @Valid @NotNull @org.jetbrains.annotations.NotNull
//        List<ActuatorWhiteList.ActuatorAllowIpVo> actuatorWhiteList = this.actuatorWhiteList.getActuatorWhiteList();
//
//        @Valid @NotNull @org.jetbrains.annotations.NotNull
//        List<C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo.IpDescVo> actuatorIpDescVoList = new ArrayList<>();
//        for (ActuatorWhiteList.ActuatorAllowIpVo actuatorWhite : actuatorWhiteList) {
//            actuatorIpDescVoList.add(new C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo.IpDescVo(
//                    actuatorWhite.ip(),
//                    actuatorWhite.desc()
//            ));
//        }
//
//        testEntityListVoList.add(new C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo(
//                Redis1_Map_RuntimeConfigIpList.KeyEnum.ACTUATOR_ALLOW_IP_LIST.name(),
//                actuatorIpDescVoList
//        ));
//
//        // 전체 조회 테스트
//        BasicRedisMap.RedisMapDataVo<Redis1_Map_RuntimeConfigIpList.ValueVo> loggingDenyInfo =
//                redis1RuntimeConfigIpList.findKeyValue(Redis1_Map_RuntimeConfigIpList.KeyEnum.LOGGING_DENY_IP_LIST.name());
//
//        if (loggingDenyInfo != null) {
//            List<C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo.IpDescVo> ipDescVoList =
//                    new ArrayList<>();
//            for (Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo ipInfo : loggingDenyInfo.value().ipInfoList) {
//                ipDescVoList.add(new C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo.IpDescVo(
//                        ipInfo.ip(),
//                        ipInfo.desc()
//                ));
//            }
//
//            testEntityListVoList.add(new C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo(
//                    Redis1_Map_RuntimeConfigIpList.KeyEnum.LOGGING_DENY_IP_LIST.name(),
//                    ipDescVoList
//            ));
//        }
//
//        httpServletResponse.setStatus(HttpStatus.OK.value());
//        return new C1Controller.Api2SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo(testEntityListVoList);
//    }
//
//    ////
//    @Override
//    public void api3InsertProjectRuntimeConfigActuatorAllowIpList(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            C1Controller.Api3InsertProjectRuntimeConfigActuatorAllowIpListInputVo inputVo
//    ) {
//        @Valid @NotNull @org.jetbrains.annotations.NotNull
//        List<ActuatorWhiteList.ActuatorAllowIpVo> actuatorAllowIpVoList = new ArrayList<>();
//
//        for (C1Controller.Api3InsertProjectRuntimeConfigActuatorAllowIpListInputVo.IpDescVo ipDescInfo : inputVo.ipInfoList()) {
//            actuatorAllowIpVoList.add(new ActuatorWhiteList.ActuatorAllowIpVo(
//                    ipDescInfo.ip(),
//                    ipDescInfo.desc()
//            ));
//        }
//
//        actuatorWhiteList.setActuatorWhiteList(actuatorAllowIpVoList);
//        httpServletResponse.setStatus(HttpStatus.OK.value());
//    }
//
//    ////
//    @Override
//    public void api4InsertProjectRuntimeConfigLoggingDenyIpList(
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            HttpServletResponse httpServletResponse,
//            @Valid @NotNull @org.jetbrains.annotations.NotNull
//            C1Controller.Api4InsertProjectRuntimeConfigLoggingDenyIpListInputVo inputVo
//    ) {
//        @Valid @NotNull @org.jetbrains.annotations.NotNull
//        List<Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo> ipDescVoList = new ArrayList<>();
//
//        for (C1Controller.Api4InsertProjectRuntimeConfigLoggingDenyIpListInputVo.IpDescVo ipDescInfo : inputVo.ipInfoList()) {
//            ipDescVoList.add(new Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo(
//                    ipDescInfo.ip(),
//                    ipDescInfo.desc()
//            ));
//        }
//
//        redis1RuntimeConfigIpList.saveKeyValue(
//                Redis1_Map_RuntimeConfigIpList.KeyEnum.LOGGING_DENY_IP_LIST.name(),
//                new Redis1_Map_RuntimeConfigIpList.ValueVo(ipDescVoList),
//                null
//        );
//
//        httpServletResponse.setStatus(HttpStatus.OK.value());
//    }
}