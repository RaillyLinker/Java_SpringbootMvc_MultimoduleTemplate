package com.raillylinker.module_security.configurations;

import com.raillylinker.module_redis.redis_map_components.redis1_main.Redis1_Map_TotalAuthForceExpireAuthorizationSet;
import com.raillylinker.module_security.util_components.JwtTokenUtil;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// [서비스 보안 시큐리티 설정]
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    // <멤버 변수 공간>


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    // (비밀번호 인코딩, 매칭시 사용할 객체)
    @Bean
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public CorsConfigurationSource corsConfigurationSource() {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setExposedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.setAllowCredentials(true);

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**", // 설정을 적용할 컨트롤러 패턴
                corsConfiguration
        );
        return source;
    }

    @Bean
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @Order
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public SecurityFilterChain securityFilterChainMainSc(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpSecurity http
    ) throws Exception {
        // cors 적용(서로 다른 origin 의 웹화면에서 리퀘스트 금지)
        http.cors(corsCustomizer -> {
        });

        // (사이즈간 위조 요청(Cross site Request forgery) 방지 설정)
        // csrf 설정시 POST, PUT, DELETE 요청으로부터 보호하며 csrf 토큰이 포함되어야 요청을 받아들이게 됨
        // Rest API 에선 Token 이 요청의 위조 방지 역할을 하기에 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        http.httpBasic(AbstractHttpConfigurer::disable);

        // Token 인증을 위한 세션 비활성화
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 스프링 시큐리티 기본 로그인 화면 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        // 스프링 시큐리티 기본 로그아웃 비활성화
        http.logout(AbstractHttpConfigurer::disable);

        // (API 요청 제한)
        // 기본적으로 모두 Open
        http.authorizeHttpRequests(
                authorizeHttpRequests ->
                        // 모든 요청 허용
                        authorizeHttpRequests.anyRequest().permitAll()
        );

        return http.build();
    }


    ////
    // [JWT 토큰 인증 체계 적용 필터 체인]
    @Bean
    @Order(1)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public SecurityFilterChain securityFilterChainToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpSecurity http,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Redis1_Map_TotalAuthForceExpireAuthorizationSet expireTokenRedis,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JwtTokenUtil jwtTokenUtil
    ) throws Exception {
        // !!!시큐리티 필터 추가시 수정!!!
        // 본 시큐리티 필터가 관리할 주소 체계
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        List<String> securityUrlList = List.of(
                "/my-service/tk/**"
        );

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        HttpSecurity securityMatcher = http.securityMatcher(securityUrlList.toArray(new String[0]));

        securityMatcher.headers(headers ->
                // iframe 허용 설정
                // 기본은 허용하지 않음, sameOrigin 은 같은 origin 일 때에만 허용하고, disable 은 모두 허용
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        );

        // cors 적용(서로 다른 origin 의 웹화면에서 리퀘스트 금지)
        http.cors(corsCustomizer -> {
        });

        // (사이즈간 위조 요청(Cross site Request forgery) 방지 설정)
        // csrf 설정시 POST, PUT, DELETE 요청으로부터 보호하며 csrf 토큰이 포함되어야 요청을 받아들이게 됨
        // Rest API 에선 Token 이 요청의 위조 방지 역할을 하기에 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        http.httpBasic(AbstractHttpConfigurer::disable);

        // Token 인증을 위한 세션 비활성화
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // (Token 인증 검증 필터 연결)
        // API 요청마다 헤더로 들어오는 인증 토큰 유효성을 검증
        http.addFilterBefore(
                // !!!시큐리티 필터 추가시 수정!!!
                new AuthTokenFilterTotalAuth(
                        securityUrlList,
                        expireTokenRedis,
                        jwtTokenUtil
                ),
                UsernamePasswordAuthenticationFilter.class
        );

        // 스프링 시큐리티 기본 로그인 화면 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        // 스프링 시큐리티 기본 로그아웃 비활성화
        http.logout(AbstractHttpConfigurer::disable);

        // 예외처리
        http.exceptionHandling(exceptionHandling -> {
            // 비인증(Security Context 에 멤버 정보가 없음) 처리
            exceptionHandling.authenticationEntryPoint((request, response, authException) -> // Http Status 401
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: UnAuthorized")
            );
            // 비인가(멤버 권한이 충족되지 않음) 처리
            exceptionHandling.accessDeniedHandler((request, response, accessDeniedException) -> // Http Status 403
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Error: Forbidden")
            );
        });

        // (API 요청 제한)
        // 기본적으로 모두 Open
        http.authorizeHttpRequests(authorizeHttpRequests ->
                /*
                    본 서버 접근 보안은 블랙 리스트 방식을 사용합니다.
                    일반적으로 모든 요청을 허용하며, 인증/인가가 필요한 부분에는,
                    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN'))")
                    위와 같은 어노테이션을 접근 통제하고자 하는 API 위에 달아주면 인증 필터가 동작하게 됩니다.
                 */
                authorizeHttpRequests.anyRequest().permitAll()
        );

        return http.build();
    }

    // 인증 토큰 검증 필터 - API 요청마다 검증 실행
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static class AuthTokenFilterTotalAuth extends OncePerRequestFilter {
        public AuthTokenFilterTotalAuth(
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull String> filterPatternList,
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Redis1_Map_TotalAuthForceExpireAuthorizationSet expireTokenRedis,
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                JwtTokenUtil jwtTokenUtil
        ) {
            this.filterPatternList = filterPatternList;
            this.expireTokenRedis = expireTokenRedis;
            this.jwtTokenUtil = jwtTokenUtil;
        }

        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        private final List<@Valid @NotNull String> filterPatternList;
        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        private final Redis1_Map_TotalAuthForceExpireAuthorizationSet expireTokenRedis;
        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        private final JwtTokenUtil jwtTokenUtil;

        // !!!아래 인증 관련 설정 정보 변수들의 값을 수정하기!!!
        // 계정 설정 - JWT 비밀키
        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        public static final String AUTH_JWT_SECRET_KEY_STRING = "123456789abcdefghijklmnopqrstuvw";

        // 계정 설정 - JWT AccessToken 유효기간(초)
        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        public static final Long AUTH_JWT_ACCESS_TOKEN_EXPIRATION_TIME_SEC = 60L * 30L; // 30분

        // 계정 설정 - JWT RefreshToken 유효기간(초)
        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        public static final Long AUTH_JWT_REFRESH_TOKEN_EXPIRATION_TIME_SEC = 60L * 60L * 24L * 7L; // 7일

        // 계정 설정 - JWT 본문 암호화 AES256 IV 16자
        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        public static final String AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR = "odkejduc726dj48d";

        // 계정 설정 - JWT 본문 암호화 AES256 암호키 32자
        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        public static final String AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY = "8fu3jd0ciiu3384hfucy36dye9sjv7b3";

        // 계정 설정 - JWT 발행자
        @Valid
        @NotNull
        @org.jetbrains.annotations.NotNull
        public static final String AUTH_JWT_ISSUER = "com.raillylinker.my-service";


        // ---------------------------------------------------------------------------------------------
        // <공개 메소드 공간>
        @Override
        protected void doFilterInternal(
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                HttpServletRequest request,
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                HttpServletResponse response,
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                FilterChain filterChain
        ) throws ServletException, IOException {
            // 패턴에 매치되는지 확인
            boolean patternMatch = false;

            for (String filterPattern : filterPatternList) {
                if (new AntPathRequestMatcher(filterPattern).matches(request)) {
                    patternMatch = true;
                    break;
                }
            }

            if (!patternMatch) {
                // 이 필터를 실행해야 할 패턴이 아님.

                // 다음 필터 실행
                filterChain.doFilter(request, response);
                return;
            }

            // (리퀘스트에서 가져온 AccessToken 검증)
            // 헤더의 Authorization 의 값 가져오기
            // 정상적인 토큰값은 "Bearer {Token String}" 형식으로 온다고 가정.
            String authorization = request.getHeader("Authorization"); // ex : "Bearer aqwer1234"
            if (authorization == null) {
                // Authorization 에 토큰을 넣지 않은 경우 = 인증 / 인가를 받을 의도가 없음
                // 다음 필터 실행
                filterChain.doFilter(request, response);
                return;
            }

            // 타입과 토큰을 분리
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String[] authorizationSplit = authorization.split(" "); // ex : ["Bearer", "qwer1234"]
            if (authorizationSplit.length < 2) {
                // 다음 필터 실행
                filterChain.doFilter(request, response);
                return;
            }

            // 타입으로 추정되는 문장이 존재할 때
            // 타입 분리
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String tokenType = authorizationSplit[0].trim(); // 첫번째 단어는 토큰 타입
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accessToken = authorizationSplit[1].trim(); // 앞의 타입을 자르고 남은 토큰

            // 강제 토큰 만료 검증
            boolean forceExpired = false;
            try {
                forceExpired = expireTokenRedis.findKeyValue(tokenType + "_" + accessToken) != null;
            } catch (Exception ignored) {
            }

            if (forceExpired) {
                // 다음 필터 실행
                filterChain.doFilter(request, response);
                return;
            }

            // 토큰 검증
            if (accessToken.isEmpty()) {
                // 다음 필터 실행
                filterChain.doFilter(request, response);
                return;
            }

            if ("bearer".equalsIgnoreCase(tokenType)) { // 타입 검증
                // 토큰 문자열 해석 가능여부 확인
                @Nullable @org.jetbrains.annotations.Nullable
                String accessTokenType = null;
                try {
                    accessTokenType = jwtTokenUtil.getTokenType(accessToken);
                } catch (Exception ignored) {
                }

                if (accessTokenType == null || // 해석 불가능한 JWT 토큰
                        !accessTokenType.equalsIgnoreCase("jwt") || // 토큰 타입이 JWT 가 아님
                        !jwtTokenUtil.getTokenUsage(
                                accessToken,
                                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
                        ).equalsIgnoreCase("access") || // 토큰 용도가 다름
                        // 남은 시간이 최대 만료시간을 초과 (서버 기준이 변경되었을 때, 남은 시간이 더 많은 토큰을 견제하기 위한 처리)
                        jwtTokenUtil.getRemainSeconds(accessToken) > AUTH_JWT_ACCESS_TOKEN_EXPIRATION_TIME_SEC ||
                        !jwtTokenUtil.getIssuer(accessToken).equals(AUTH_JWT_ISSUER) || // 발행인 불일치
                        !jwtTokenUtil.validateSignature(
                                accessToken,
                                AUTH_JWT_SECRET_KEY_STRING
                        ) // 시크릿 검증이 무효 = 위변조 된 토큰
                ) {
                    // 다음 필터 실행
                    filterChain.doFilter(request, response);
                    return;
                }

                // 토큰 만료 검증
                long jwtRemainSeconds = jwtTokenUtil.getRemainSeconds(accessToken);
                if (jwtRemainSeconds <= 0L) {
                    // 다음 필터 실행
                    filterChain.doFilter(request, response);
                    return;
                }

                // 회원 권한
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (@Valid @NotNull String memberRole : jwtTokenUtil.getRoleList(
                        accessToken,
                        AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR, AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
                )) {
                    authorities.add(
                            new SimpleGrantedAuthority(memberRole)
                    );
                }

                // (검증된 멤버 정보와 권한 정보를 Security Context 에 입력)
                // authentication 정보가 context 에 존재하는지 여부로 로그인 여부를 확인
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                null, // 세션을 유지하지 않으니 굳이 입력할 필요가 없음
                                null, // 세션을 유지하지 않으니 굳이 입력할 필요가 없음
                                authorities // 멤버 권한 리스트만 입력해주어 권한 확인에 사용
                        );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                filterChain.doFilter(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
