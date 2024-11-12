package com.raillylinker.module_api_my_service_sc.services.impls;

import com.raillylinker.module_api_my_service_sc.services.MyServiceScService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/*
    (세션 멤버 정보 가져오기)
    val authentication = SecurityContextHolder.getContext().authentication
    // 현 세션 멤버 이름 (비로그인 : "anonymousUser")
    val username: String = authentication.name
    // 현 세션 권한 리스트 (비로그인 : [ROLE_ANONYMOUS], 권한없음 : [])
    val roles: List<String> = authentication.authorities.map(GrantedAuthority::getAuthority)
    println("username : $username")
    println("roles : $roles")

    (세션 만료시간 설정)
    session.maxInactiveInterval = 60
    위와 같이 세션 객체에 만료시간(초) 를 설정하면 됩니다.
*/
@Service
public class MyServiceScServiceImpl implements MyServiceScService {
    public MyServiceScServiceImpl(
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile,
            @Value("${springdoc.swagger-ui.enabled}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean swaggerEnabled
    ) {
        this.activeProfile = activeProfile;
        this.swaggerEnabled = swaggerEnabled;
    }

    // (프로젝트 실행시 사용 설정한 프로필명 (ex : dev8080, prod80, local8080, 설정 안하면 default 반환))
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String activeProfile;

    // (스웨거 문서 공개 여부 설정)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Boolean swaggerEnabled;

    // <멤버 변수 공간>
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final Logger classLogger = LoggerFactory.getLogger(MyServiceScServiceImpl.class);


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    public ModelAndView homePage(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletRequest httpServletRequest,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpSession session
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home_page/home_page");

        mv.addObject(
                "viewModel",
                new HomePageViewModel(activeProfile, swaggerEnabled)
        );

        return mv;
    }

    // Java record for HomePageViewModel
    public record HomePageViewModel(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String env,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean showApiDocumentBtn
    ) {
    }
}