package com.raillylinker.module_api_project.services.impls;

import com.raillylinker.module_api_project.controllers.RootController;
import com.raillylinker.module_api_project.services.RootService;
import com.raillylinker.module_redis.abstract_classes.BasicRedisMap;
import com.raillylinker.module_redis.redis_map_components.redis1_main.Redis1_Map_RuntimeConfigIpList;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Service
public class RootServiceImpl implements RootService {
    public RootServiceImpl(
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile,
            @Value("${springdoc.swagger-ui.enabled}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean swaggerEnabled,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList
    ) {
        this.activeProfile = activeProfile;
        this.swaggerEnabled = swaggerEnabled;
        this.redis1RuntimeConfigIpList = redis1RuntimeConfigIpList;
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

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Redis1_Map_RuntimeConfigIpList redis1RuntimeConfigIpList;


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


    ////
    @Override
    public RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo selectAllProjectRuntimeConfigsRedisKeyValue(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo> testEntityListVoList = new ArrayList<>();

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo.IpDescVo> actuatorIpDescVoList = new ArrayList<>();

        // actuator 저장 정보 가져오기
        BasicRedisMap.RedisMapDataVo<Redis1_Map_RuntimeConfigIpList.ValueVo> keyValue =
                redis1RuntimeConfigIpList.findKeyValue(Redis1_Map_RuntimeConfigIpList.KeyEnum.ACTUATOR_ALLOW_IP_LIST.name());

        if (keyValue != null) {
            for (Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo vl : keyValue.value().ipInfoList) {
                actuatorIpDescVoList.add(new RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo.IpDescVo(
                        vl.ip(),
                        vl.desc()
                ));
            }
        }

        testEntityListVoList.add(new RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo(
                Redis1_Map_RuntimeConfigIpList.KeyEnum.ACTUATOR_ALLOW_IP_LIST.name(),
                actuatorIpDescVoList
        ));

        // 전체 조회 테스트
        BasicRedisMap.RedisMapDataVo<Redis1_Map_RuntimeConfigIpList.ValueVo> loggingDenyInfo =
                redis1RuntimeConfigIpList.findKeyValue(Redis1_Map_RuntimeConfigIpList.KeyEnum.LOGGING_DENY_IP_LIST.name());

        if (loggingDenyInfo != null) {
            List<RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo.IpDescVo> ipDescVoList =
                    new ArrayList<>();
            for (Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo ipInfo : loggingDenyInfo.value().ipInfoList) {
                ipDescVoList.add(new RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo.IpDescVo(
                        ipInfo.ip(),
                        ipInfo.desc()
                ));
            }

            testEntityListVoList.add(new RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo.KeyValueVo(
                    Redis1_Map_RuntimeConfigIpList.KeyEnum.LOGGING_DENY_IP_LIST.name(),
                    ipDescVoList
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new RootController.SelectAllProjectRuntimeConfigsRedisKeyValueOutputVo(testEntityListVoList);
    }


    ////
    @Override
    public void insertProjectRuntimeConfigActuatorAllowIpList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RootController.InsertProjectRuntimeConfigActuatorAllowIpListInputVo inputVo
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo> ipDescVoList = new ArrayList<>();

        for (RootController.InsertProjectRuntimeConfigActuatorAllowIpListInputVo.IpDescVo ipDescInfo : inputVo.ipInfoList()) {
            ipDescVoList.add(new Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo(ipDescInfo.ip(), ipDescInfo.desc()));
        }

        redis1RuntimeConfigIpList.saveKeyValue(
                Redis1_Map_RuntimeConfigIpList.KeyEnum.ACTUATOR_ALLOW_IP_LIST.name(),
                new Redis1_Map_RuntimeConfigIpList.ValueVo(ipDescVoList),
                null
        );
        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    public void insertProjectRuntimeConfigLoggingDenyIpList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RootController.InsertProjectRuntimeConfigLoggingDenyIpListInputVo inputVo
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo> ipDescVoList = new ArrayList<>();

        for (RootController.InsertProjectRuntimeConfigLoggingDenyIpListInputVo.IpDescVo ipDescInfo : inputVo.ipInfoList()) {
            ipDescVoList.add(new Redis1_Map_RuntimeConfigIpList.ValueVo.IpDescVo(
                    ipDescInfo.ip(),
                    ipDescInfo.desc()
            ));
        }

        redis1RuntimeConfigIpList.saveKeyValue(
                Redis1_Map_RuntimeConfigIpList.KeyEnum.LOGGING_DENY_IP_LIST.name(),
                new Redis1_Map_RuntimeConfigIpList.ValueVo(ipDescVoList),
                null
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }
}
