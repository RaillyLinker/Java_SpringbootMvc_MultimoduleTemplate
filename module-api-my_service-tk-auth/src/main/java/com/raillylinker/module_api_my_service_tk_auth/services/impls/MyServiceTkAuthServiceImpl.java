package com.raillylinker.module_api_my_service_tk_auth.services.impls;

import com.raillylinker.module_api_my_service_tk_auth.controllers.MyServiceTkAuthController;
import com.raillylinker.module_api_my_service_tk_auth.services.MyServiceTkAuthService;
import com.raillylinker.module_common.util_components.EmailSender;
import com.raillylinker.module_common.util_components.NaverSmsSenderComponent;
import com.raillylinker.module_jpa.annotations.CustomTransactional;
import com.raillylinker.module_jpa.configurations.jpa_configs.Db1MainConfig;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.*;
import com.raillylinker.module_jpa.jpa_beans.db1_main.repositories.*;
import com.raillylinker.module_redis.redis_map_components.redis1_main.Redis1_Map_TotalAuthForceExpireAuthorizationSet;
import com.raillylinker.module_retrofit2.retrofit2_classes.RepositoryNetworkRetrofit2;
import com.raillylinker.module_retrofit2.retrofit2_classes.request_apis.*;
import com.raillylinker.module_security.util_components.AppleOAuthHelperUtil;
import com.raillylinker.module_security.util_components.JwtTokenUtil;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import retrofit2.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.raillylinker.module_security.configurations.SecurityConfig.AuthTokenFilterTotalAuth.*;

@Service
public class MyServiceTkAuthServiceImpl implements MyServiceTkAuthService {
    public MyServiceTkAuthServiceImpl(
            // (프로젝트 실행시 사용 설정한 프로필명 (ex : dev8080, prod80, local8080, 설정 안하면 default 반환))
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile,

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PasswordEncoder passwordEncoder,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            EmailSender emailSender,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            NaverSmsSenderComponent naverSmsSenderComponent,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JwtTokenUtil jwtTokenUtil,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AppleOAuthHelperUtil appleOAuthHelperUtil,

            // (Redis Map)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Redis1_Map_TotalAuthForceExpireAuthorizationSet redis1MapTotalAuthForceExpireAuthorizationSet,

            // (Database Repository)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_Native_Repository db1NativeRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember_Repository db1RaillyLinkerCompanyTotalAuthMemberRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMemberRole_Repository db1RaillyLinkerCompanyTotalAuthMemberRoleRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMemberEmail_Repository db1RaillyLinkerCompanyTotalAuthMemberEmailRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMemberPhone_Repository db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login_Repository db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithPhoneVerification_Repository db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithPhoneVerificationRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithEmailVerification_Repository db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithEmailVerificationRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification_Repository db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthFindPwWithPhoneVerification_Repository db1RaillyLinkerCompanyTotalAuthFindPwWithPhoneVerificationRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthFindPwWithEmailVerification_Repository db1RaillyLinkerCompanyTotalAuthFindPwWithEmailVerificationRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthAddEmailVerification_Repository db1RaillyLinkerCompanyTotalAuthAddEmailVerificationRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthAddPhoneVerification_Repository db1RaillyLinkerCompanyTotalAuthAddPhoneVerificationRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMemberProfile_Repository db1RaillyLinkerCompanyTotalAuthMemberProfileRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory_Repository db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMemberLockHistory_Repository db1RaillyLinkerCompanyTotalAuthMemberLockHistoryRepository
    ) throws InterruptedException {
        this.activeProfile = activeProfile;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
        this.naverSmsSenderComponent = naverSmsSenderComponent;
        this.jwtTokenUtil = jwtTokenUtil;
        this.appleOAuthHelperUtil = appleOAuthHelperUtil;
        this.redis1MapTotalAuthForceExpireAuthorizationSet = redis1MapTotalAuthForceExpireAuthorizationSet;
        this.db1NativeRepository = db1NativeRepository;
        this.db1RaillyLinkerCompanyTotalAuthMemberRepository = db1RaillyLinkerCompanyTotalAuthMemberRepository;
        this.db1RaillyLinkerCompanyTotalAuthMemberRoleRepository = db1RaillyLinkerCompanyTotalAuthMemberRoleRepository;
        this.db1RaillyLinkerCompanyTotalAuthMemberEmailRepository = db1RaillyLinkerCompanyTotalAuthMemberEmailRepository;
        this.db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository = db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository;
        this.db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository;
        this.db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithPhoneVerificationRepository = db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithPhoneVerificationRepository;
        this.db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithEmailVerificationRepository = db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithEmailVerificationRepository;
        this.db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository = db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository;
        this.db1RaillyLinkerCompanyTotalAuthFindPwWithPhoneVerificationRepository = db1RaillyLinkerCompanyTotalAuthFindPwWithPhoneVerificationRepository;
        this.db1RaillyLinkerCompanyTotalAuthFindPwWithEmailVerificationRepository = db1RaillyLinkerCompanyTotalAuthFindPwWithEmailVerificationRepository;
        this.db1RaillyLinkerCompanyTotalAuthAddEmailVerificationRepository = db1RaillyLinkerCompanyTotalAuthAddEmailVerificationRepository;
        this.db1RaillyLinkerCompanyTotalAuthAddPhoneVerificationRepository = db1RaillyLinkerCompanyTotalAuthAddPhoneVerificationRepository;
        this.db1RaillyLinkerCompanyTotalAuthMemberProfileRepository = db1RaillyLinkerCompanyTotalAuthMemberProfileRepository;
        this.db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository = db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository;
        this.db1RaillyLinkerCompanyTotalAuthMemberLockHistoryRepository = db1RaillyLinkerCompanyTotalAuthMemberLockHistoryRepository;

        this.externalAccessAddress = switch (activeProfile) {
            case "prod80" -> "http://127.0.0.1";
            case "dev8080" -> "http://127.0.0.1:8080";
            default -> "http://127.0.0.1:8080";
        };
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String activeProfile;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final PasswordEncoder passwordEncoder;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final EmailSender emailSender;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final NaverSmsSenderComponent naverSmsSenderComponent;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final JwtTokenUtil jwtTokenUtil;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final AppleOAuthHelperUtil appleOAuthHelperUtil;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Redis1_Map_TotalAuthForceExpireAuthorizationSet redis1MapTotalAuthForceExpireAuthorizationSet;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_Native_Repository db1NativeRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthMember_Repository db1RaillyLinkerCompanyTotalAuthMemberRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthMemberRole_Repository db1RaillyLinkerCompanyTotalAuthMemberRoleRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthMemberEmail_Repository db1RaillyLinkerCompanyTotalAuthMemberEmailRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthMemberPhone_Repository db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login_Repository db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithPhoneVerification_Repository db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithPhoneVerificationRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithEmailVerification_Repository db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithEmailVerificationRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification_Repository db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthFindPwWithPhoneVerification_Repository db1RaillyLinkerCompanyTotalAuthFindPwWithPhoneVerificationRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthFindPwWithEmailVerification_Repository db1RaillyLinkerCompanyTotalAuthFindPwWithEmailVerificationRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthAddEmailVerification_Repository db1RaillyLinkerCompanyTotalAuthAddEmailVerificationRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthAddPhoneVerification_Repository db1RaillyLinkerCompanyTotalAuthAddPhoneVerificationRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthMemberProfile_Repository db1RaillyLinkerCompanyTotalAuthMemberProfileRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory_Repository db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Db1_RaillyLinkerCompany_TotalAuthMemberLockHistory_Repository db1RaillyLinkerCompanyTotalAuthMemberLockHistoryRepository;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final RepositoryNetworkRetrofit2 networkRetrofit2 = RepositoryNetworkRetrofit2.getInstance();

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    String externalAccessAddress;


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String noLoggedInAccessTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        return externalAccessAddress;
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String loggedInAccessTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return "Member No." + memberUid + " : Test Success";
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String adminAccessTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return "Member No." + memberUid + " : Test Success";
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String developerAccessTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return "Member No." + memberUid + " : Test Success";
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void doExpireAccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long memberUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.DoExpireAccessTokenInputVo inputVo
    ) {
        if (!inputVo.apiSecret().equals("aadke234!@")) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Optional<Db1_RaillyLinkerCompany_TotalAuthMember> memberEntityOpt = db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(memberUid, "/");

        if (memberEntityOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        } else {
            Db1_RaillyLinkerCompany_TotalAuthMember memberEntity = memberEntityOpt.get();

            List<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> tokenEntityList = db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository
                    .findAllByTotalAuthMemberAndAccessTokenExpireWhenAfterAndRowDeleteDateStr(
                            memberEntity, LocalDateTime.now(), "/");

            for (Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory tokenEntity : tokenEntityList) {
                tokenEntity.loginDate = LocalDateTime.now();
                db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(tokenEntity);

                String tokenType = tokenEntity.tokenType;
                String accessToken = tokenEntity.accessToken;

                Long accessTokenExpireRemainSeconds = null;
                if ("Bearer".equals(tokenType)) {
                    accessTokenExpireRemainSeconds = jwtTokenUtil.getRemainSeconds(accessToken);
                }

                if (accessTokenExpireRemainSeconds != null) {
                    try {
                        redis1MapTotalAuthForceExpireAuthorizationSet.saveKeyValue(
                                tokenType + "_" + accessToken,
                                new Redis1_Map_TotalAuthForceExpireAuthorizationSet.ValueVo(),
                                accessTokenExpireRemainSeconds * 1000);
                    } catch (Exception e) {
                        classLogger.error("Error while saving forced expiration in Redis", e);
                    }
                }
            }
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.LoginOutputVo loginWithPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.LoginWithPasswordInputVo inputVo
    ) {
        Db1_RaillyLinkerCompany_TotalAuthMember memberData;

        switch (inputVo.loginTypeCode()) {
            case 0: // 아이디
                // (정보 검증 로직 수행)
                Optional<Db1_RaillyLinkerCompany_TotalAuthMember> member = db1RaillyLinkerCompanyTotalAuthMemberRepository
                        .findByAccountIdAndRowDeleteDateStr(inputVo.id(), "/");

                if (member.isEmpty()) { // 가입된 회원이 없음
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }
                memberData = member.get();
                break;
            case 1: // 이메일
                // (정보 검증 로직 수행)
                Optional<Db1_RaillyLinkerCompany_TotalAuthMemberEmail> memberEmail =
                        db1RaillyLinkerCompanyTotalAuthMemberEmailRepository
                                .findByEmailAddressAndRowDeleteDateStr(inputVo.id(), "/");

                if (memberEmail.isEmpty()) { // 가입된 회원이 없음
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }
                memberData = memberEmail.get().totalAuthMember;
                break;
            case 2: // 전화번호
                // (정보 검증 로직 수행)
                Optional<Db1_RaillyLinkerCompany_TotalAuthMemberPhone> memberPhone =
                        db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository
                                .findByPhoneNumberAndRowDeleteDateStr(inputVo.id(), "/");

                if (memberPhone.isEmpty()) { // 가입된 회원이 없음
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }
                memberData = memberPhone.get().totalAuthMember;
                break;
            default:
                classLogger.info("loginTypeCode " + inputVo.loginTypeCode() + " Not Supported");
                httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                return null;
        }

        if (memberData.accountPassword == null || // 페스워드는 아직 만들지 않음
                !passwordEncoder.matches(inputVo.password(), memberData.accountPassword) // 패스워드 불일치
        ) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return null;
        }

        // 계정 정지 검증
        List<Db1_Native_Repository.FindAllNowActivateMemberLockInfoOutputVo> lockList = db1NativeRepository.findAllNowActivateMemberLockInfo(
                memberData.uid, LocalDateTime.now());
        if (!lockList.isEmpty()) {
            // 계정 정지 당한 상황
            List<MyServiceTkAuthController.LoginOutputVo.LockedOutput> lockedOutputList = new ArrayList<>();
            for (Db1_Native_Repository.FindAllNowActivateMemberLockInfoOutputVo lockInfo : lockList) {
                lockedOutputList.add(new MyServiceTkAuthController.LoginOutputVo.LockedOutput(
                        memberData.uid,
                        lockInfo.getLockStart().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        lockInfo.getLockBefore() == null ? null :
                                lockInfo.getLockBefore().atZone(ZoneId.systemDefault())
                                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        lockInfo.getLockReasonCode().intValue(),
                        lockInfo.getLockReason()
                ));
            }

            httpServletResponse.setStatus(HttpStatus.OK.value());
            return new MyServiceTkAuthController.LoginOutputVo(null, lockedOutputList);
        }

        // 멤버의 권한 리스트를 조회 후 반환
        List<Db1_RaillyLinkerCompany_TotalAuthMemberRole> memberRoleList =
                db1RaillyLinkerCompanyTotalAuthMemberRoleRepository
                        .findAllByTotalAuthMemberAndRowDeleteDateStr(memberData, "/");
        List<String> roleList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberRole userRole : memberRoleList) {
            roleList.add(userRole.role);
        }

        // (토큰 생성 로직 수행)
        // 멤버 고유번호로 엑세스 토큰 생성
        String jwtAccessToken = jwtTokenUtil.generateAccessToken(
                memberData.uid,
                AUTH_JWT_ACCESS_TOKEN_EXPIRATION_TIME_SEC,
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY,
                AUTH_JWT_ISSUER,
                AUTH_JWT_SECRET_KEY_STRING,
                roleList
        );

        LocalDateTime accessTokenExpireWhen = jwtTokenUtil.getExpirationDateTime(jwtAccessToken);

        // 액세스 토큰의 리프레시 토큰 생성 및 DB 저장 = 액세스 토큰에 대한 리프레시 토큰은 1개 혹은 0개
        String jwtRefreshToken = jwtTokenUtil.generateRefreshToken(
                memberData.uid,
                AUTH_JWT_REFRESH_TOKEN_EXPIRATION_TIME_SEC,
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY,
                AUTH_JWT_ISSUER,
                AUTH_JWT_SECRET_KEY_STRING
        );

        LocalDateTime refreshTokenExpireWhen = jwtTokenUtil.getExpirationDateTime(jwtRefreshToken);

        // 로그인 정보 저장
        db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(
                new Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory(
                        memberData,
                        "Bearer",
                        LocalDateTime.now(),
                        jwtAccessToken,
                        accessTokenExpireWhen,
                        jwtRefreshToken,
                        refreshTokenExpireWhen,
                        null
                )
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.LoginOutputVo(
                new MyServiceTkAuthController.LoginOutputVo.LoggedInOutput(
                        memberData.uid,
                        "Bearer",
                        jwtAccessToken,
                        jwtRefreshToken,
                        accessTokenExpireWhen.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        refreshTokenExpireWhen.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                ),
                null
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.GetOAuth2AccessTokenOutputVo getOAuth2AccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2Code
    ) {
        String snsAccessTokenType;
        String snsAccessToken;

        // !!!OAuth2 ClientId!!
        String clientId = "TODO";

        // !!!OAuth2 clientSecret!!
        String clientSecret = "TODO";

        // !!!OAuth2 로그인할때 사용한 Redirect Uri!!
        String redirectUri = "TODO";

        try {
            switch (oauth2TypeCode) {
                case 1: { // GOOGLE
                    // Access Token 가져오기
                    Response<AccountsGoogleComRequestApi.PostOOauth2TokenOutputVO> atResponse = networkRetrofit2.accountsGoogleComRequestApi
                            .postOOauth2Token(
                                    oauth2Code,
                                    clientId,
                                    clientSecret,
                                    "authorization_code",
                                    redirectUri
                            ).execute();

                    // code 사용 결과 검증
                    if (atResponse.code() != 200 ||
                            atResponse.body() == null ||
                            atResponse.body().accessToken() == null) {
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "1");
                        return null;
                    }

                    snsAccessTokenType = atResponse.body().tokenType();
                    snsAccessToken = atResponse.body().accessToken();
                    break;
                }

                case 2: { // NAVER
                    // !!!OAuth2 로그인시 사용한 State!!
                    String state = "TODO";

                    // Access Token 가져오기
                    Response<NidNaverComRequestApi.GetOAuth2Dot0TokenRequestOutputVO> atResponse = networkRetrofit2.nidNaverComRequestApi
                            .getOAuth2Dot0Token(
                                    "authorization_code",
                                    clientId,
                                    clientSecret,
                                    redirectUri,
                                    oauth2Code,
                                    state
                            ).execute();

                    // code 사용 결과 검증
                    if (atResponse.code() != 200 ||
                            atResponse.body() == null ||
                            atResponse.body().accessToken() == null) {
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "1");
                        return null;
                    }

                    snsAccessTokenType = atResponse.body().tokenType();
                    snsAccessToken = atResponse.body().accessToken();
                    break;
                }

                case 3: { // KAKAO
                    // Access Token 가져오기
                    Response<KauthKakaoComRequestApi.PostOOauthTokenOutputVO> atResponse = networkRetrofit2.kauthKakaoComRequestApi
                            .postOOauthToken(
                                    "authorization_code",
                                    clientId,
                                    clientSecret,
                                    redirectUri,
                                    oauth2Code
                            ).execute();

                    // code 사용 결과 검증
                    if (atResponse.code() != 200 ||
                            atResponse.body() == null ||
                            atResponse.body().accessToken() == null) {
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "1");
                        return null;
                    }

                    snsAccessTokenType = atResponse.body().tokenType();
                    snsAccessToken = atResponse.body().accessToken();
                    break;
                }

                default: {
                    classLogger.info("SNS Login Type " + oauth2TypeCode + " Not Supported");
                    httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                    return null;
                }
            }
        } catch (IOException e) {
            classLogger.error("Error ", e);
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.GetOAuth2AccessTokenOutputVo(snsAccessTokenType, snsAccessToken);
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.LoginOutputVo loginWithOAuth2AccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.LoginWithOAuth2AccessTokenInputVo inputVo
    ) throws IOException {
        Optional<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login> snsOauth2Opt;

        // (정보 검증 로직 수행)
        switch (inputVo.oauth2TypeCode()) {
            case 1: { // GOOGLE
                // 클라이언트에서 받은 access 토큰으로 멤버 정보 요청
                Response<WwwGoogleapisComRequestApi.GetOauth2V1UserInfoOutputVO> response = networkRetrofit2.wwwGoogleapisComRequestApi
                        .getOauth2V1UserInfo(inputVo.oauth2AccessToken())
                        .execute();

                // 액세트 토큰 정상 동작 확인
                if (response.code() != 200 || response.body() == null) {
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                snsOauth2Opt = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository
                        .findByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr((byte) 1, response.body().id(), "/");
                break;
            }

            case 2: { // NAVER
                // 클라이언트에서 받은 access 토큰으로 멤버 정보 요청
                Response<OpenapiNaverComRequestApi.GetV1NidMeOutputVO> response = networkRetrofit2.openapiNaverComRequestApi
                        .getV1NidMe(inputVo.oauth2AccessToken())
                        .execute();

                // 액세트 토큰 정상 동작 확인
                if (response.code() != 200 || response.body() == null) {
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                snsOauth2Opt = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository
                        .findByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr((byte) 2, response.body().response().id(), "/");
                break;
            }

            case 3: { // KAKAO
                // 클라이언트에서 받은 access 토큰으로 멤버 정보 요청
                Response<KapiKakaoComRequestApi.GetV2UserMeOutputVO> response = networkRetrofit2.kapiKakaoComRequestApi
                        .getV2UserMe(inputVo.oauth2AccessToken())
                        .execute();

                // 액세트 토큰 정상 동작 확인
                if (response.code() != 200 || response.body() == null) {
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                snsOauth2Opt = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository
                        .findByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr((byte) 3, response.body().id().toString(), "/");
                break;
            }

            default: {
                classLogger.info("SNS Login Type " + inputVo.oauth2TypeCode() + " Not Supported");
                httpServletResponse.setStatus(400);
                return null;
            }
        }

        if (snsOauth2Opt.isEmpty()) { // 가입된 회원이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return null;
        }

        Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login snsOauth2 = snsOauth2Opt.get();

        // 계정 정지 검증
        List<Db1_Native_Repository.FindAllNowActivateMemberLockInfoOutputVo> lockList = db1NativeRepository
                .findAllNowActivateMemberLockInfo(snsOauth2.totalAuthMember.uid, LocalDateTime.now());

        if (!lockList.isEmpty()) {
            // 계정 정지 당한 상황
            List<MyServiceTkAuthController.LoginOutputVo.LockedOutput> lockedOutputList = new ArrayList<>();
            for (Db1_Native_Repository.FindAllNowActivateMemberLockInfoOutputVo lockInfo : lockList) {
                lockedOutputList.add(new MyServiceTkAuthController.LoginOutputVo.LockedOutput(
                        snsOauth2.totalAuthMember.uid,
                        lockInfo.getLockStart().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        lockInfo.getLockBefore() == null ? null :
                                lockInfo.getLockBefore().atZone(ZoneId.systemDefault())
                                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        lockInfo.getLockReasonCode().intValue(),
                        lockInfo.getLockReason()
                ));
            }

            httpServletResponse.setStatus(HttpStatus.OK.value());
            return new MyServiceTkAuthController.LoginOutputVo(null, lockedOutputList);
        }

        // 멤버의 권한 리스트를 조회 후 반환
        List<Db1_RaillyLinkerCompany_TotalAuthMemberRole> memberRoleList =
                db1RaillyLinkerCompanyTotalAuthMemberRoleRepository
                        .findAllByTotalAuthMemberAndRowDeleteDateStr(snsOauth2.totalAuthMember, "/");
        List<String> roleList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberRole memberRole : memberRoleList) {
            roleList.add(memberRole.role);
        }

        // (토큰 생성 로직 수행)
        // 멤버 고유번호로 엑세스 토큰 생성
        String jwtAccessToken = jwtTokenUtil.generateAccessToken(
                snsOauth2.totalAuthMember.uid,
                AUTH_JWT_ACCESS_TOKEN_EXPIRATION_TIME_SEC,
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY,
                AUTH_JWT_ISSUER,
                AUTH_JWT_SECRET_KEY_STRING,
                roleList
        );

        LocalDateTime accessTokenExpireWhen = jwtTokenUtil.getExpirationDateTime(jwtAccessToken);

        // 액세스 토큰의 리프레시 토큰 생성 및 DB 저장 = 액세스 토큰에 대한 리프레시 토큰은 1개 혹은 0개
        String jwtRefreshToken = jwtTokenUtil.generateRefreshToken(
                snsOauth2.totalAuthMember.uid,
                AUTH_JWT_REFRESH_TOKEN_EXPIRATION_TIME_SEC,
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY,
                AUTH_JWT_ISSUER,
                AUTH_JWT_SECRET_KEY_STRING
        );

        LocalDateTime refreshTokenExpireWhen = jwtTokenUtil.getExpirationDateTime(jwtRefreshToken);

        // 로그인 정보 저장
        db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(
                new Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory(
                        snsOauth2.totalAuthMember,
                        "Bearer",
                        LocalDateTime.now(),
                        jwtAccessToken,
                        accessTokenExpireWhen,
                        jwtRefreshToken,
                        refreshTokenExpireWhen,
                        null
                )
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.LoginOutputVo(
                new MyServiceTkAuthController.LoginOutputVo.LoggedInOutput(
                        snsOauth2.totalAuthMember.uid,
                        "Bearer",
                        jwtAccessToken,
                        jwtRefreshToken,
                        accessTokenExpireWhen.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        refreshTokenExpireWhen.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                ),
                null
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.LoginOutputVo loginWithOAuth2IdToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.LoginWithOAuth2IdTokenInputVo inputVo
    ) {
        Optional<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login> snsOauth2Opt;

        // (정보 검증 로직 수행)
        switch (inputVo.oauth2TypeCode()) {
            case 4: // APPLE
                AppleOAuthHelperUtil.AppleProfileData appleInfo = appleOAuthHelperUtil.getAppleMemberData(inputVo.oauth2IdToken());

                String loginId;
                if (appleInfo != null) {
                    loginId = appleInfo.snsId();
                } else {
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                snsOauth2Opt = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository
                        .findByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr((byte) 4, loginId, "/");
                break;

            default:
                classLogger.info("SNS Login Type " + inputVo.oauth2IdToken() + " Not Supported");
                httpServletResponse.setStatus(400);
                return null;
        }

        if (snsOauth2Opt.isEmpty()) { // 가입된 회원이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return null;
        }

        Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login snsOauth2 = snsOauth2Opt.get();

        // 계정 정지 검증
        List<Db1_Native_Repository.FindAllNowActivateMemberLockInfoOutputVo> lockList = db1NativeRepository.findAllNowActivateMemberLockInfo(
                snsOauth2.totalAuthMember.uid, LocalDateTime.now());
        if (!lockList.isEmpty()) { // 계정 정지 상황
            List<MyServiceTkAuthController.LoginOutputVo.LockedOutput> lockedOutputList = new ArrayList<>();
            for (Db1_Native_Repository.FindAllNowActivateMemberLockInfoOutputVo lockInfo : lockList) {
                lockedOutputList.add(new MyServiceTkAuthController.LoginOutputVo.LockedOutput(
                        snsOauth2.totalAuthMember.uid,
                        lockInfo.getLockStart().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        lockInfo.getLockBefore() == null ? null :
                                lockInfo.getLockBefore().atZone(ZoneId.systemDefault())
                                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        lockInfo.getLockReasonCode().intValue(),
                        lockInfo.getLockReason()
                ));
            }

            httpServletResponse.setStatus(HttpStatus.OK.value());
            return new MyServiceTkAuthController.LoginOutputVo(null, lockedOutputList);
        }

        // 멤버의 권한 리스트를 조회 후 반환
        List<Db1_RaillyLinkerCompany_TotalAuthMemberRole> memberRoleList = db1RaillyLinkerCompanyTotalAuthMemberRoleRepository
                .findAllByTotalAuthMemberAndRowDeleteDateStr(snsOauth2.totalAuthMember, "/");
        List<String> roleList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberRole userRole : memberRoleList) {
            roleList.add(userRole.role);
        }

        // (토큰 생성 로직 수행)
        // 멤버 고유번호로 엑세스 토큰 생성
        String jwtAccessToken = jwtTokenUtil.generateAccessToken(
                snsOauth2.totalAuthMember.uid,
                AUTH_JWT_ACCESS_TOKEN_EXPIRATION_TIME_SEC,
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY,
                AUTH_JWT_ISSUER,
                AUTH_JWT_SECRET_KEY_STRING,
                roleList
        );

        LocalDateTime accessTokenExpireWhen = jwtTokenUtil.getExpirationDateTime(jwtAccessToken);

        // 액세스 토큰의 리프레시 토큰 생성 및 DB 저장
        String jwtRefreshToken = jwtTokenUtil.generateRefreshToken(
                snsOauth2.totalAuthMember.uid,
                AUTH_JWT_REFRESH_TOKEN_EXPIRATION_TIME_SEC,
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY,
                AUTH_JWT_ISSUER,
                AUTH_JWT_SECRET_KEY_STRING
        );

        LocalDateTime refreshTokenExpireWhen = jwtTokenUtil.getExpirationDateTime(jwtRefreshToken);

        // 로그인 정보 저장
        db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(
                new Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory(
                        snsOauth2.totalAuthMember,
                        "Bearer",
                        LocalDateTime.now(),
                        jwtAccessToken,
                        accessTokenExpireWhen,
                        jwtRefreshToken,
                        refreshTokenExpireWhen,
                        null
                )
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.LoginOutputVo(
                new MyServiceTkAuthController.LoginOutputVo.LoggedInOutput(
                        snsOauth2.totalAuthMember.uid,
                        "Bearer",
                        jwtAccessToken,
                        jwtRefreshToken,
                        accessTokenExpireWhen.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        refreshTokenExpireWhen.atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                ),
                null
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void logout(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        List<String> authorizationSplit = Arrays.asList(authorization.split(" ")); // ex : ["Bearer", "qwer1234"]
        String token = authorizationSplit.get(1).trim(); // (ex : "abcd1234")

        // 해당 멤버의 토큰 발행 정보 삭제
        String tokenType = authorizationSplit.get(0).trim().toLowerCase(); // (ex : "bearer")

        Optional<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> tokenInfoOpt =
                db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository
                        .findByTokenTypeAndAccessTokenAndLogoutDateAndRowDeleteDateStr(
                                tokenType, token, null, "/"
                        );

        if (tokenInfoOpt.isPresent()) {
            Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory tokenInfo = tokenInfoOpt.get();

            tokenInfo.loginDate = LocalDateTime.now();
            db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(tokenInfo);

            // 토큰 만료처리
            String tokenType1 = tokenInfo.tokenType;
            String accessToken = tokenInfo.accessToken;

            Long accessTokenExpireRemainSeconds;
            if ("Bearer".equals(tokenType1)) {
                accessTokenExpireRemainSeconds = jwtTokenUtil.getRemainSeconds(accessToken);
            } else {
                accessTokenExpireRemainSeconds = null;
            }

            try {
                redis1MapTotalAuthForceExpireAuthorizationSet.saveKeyValue(
                        tokenType1 + "_" + accessToken,
                        new Redis1_Map_TotalAuthForceExpireAuthorizationSet.ValueVo(),
                        accessTokenExpireRemainSeconds != null ? accessTokenExpireRemainSeconds * 1000 : null
                );
            } catch (Exception e) {
                classLogger.error("error ", e);
            }
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.LoginOutputVo reissueJwt(
            @Nullable @org.jetbrains.annotations.Nullable
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.ReissueJwtInputVo inputVo,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        if (authorization == null) {
            // 올바르지 않은 Authorization Token
            httpServletResponse.setHeader("api-result-code", "3");
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        }

        String[] authorizationSplit = authorization.split(" "); // ex : ["Bearer", "qwer1234"]
        if (authorizationSplit.length < 2) {
            // 올바르지 않은 Authorization Token
            httpServletResponse.setHeader("api-result-code", "3");
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        }

        String accessTokenType = authorizationSplit[0].trim(); // (ex : "bearer")
        String accessToken = authorizationSplit[1].trim(); // (ex : "abcd1234")

        // 토큰 검증
        if (accessToken.isEmpty()) {
            // 액세스 토큰이 비어있음 (올바르지 않은 Authorization Token)
            httpServletResponse.setHeader("api-result-code", "3");
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        }

        switch (accessTokenType.toLowerCase()) { // 타입 검증
            case "bearer": { // Bearer JWT 토큰 검증
                // 토큰 문자열 해석 가능여부 확인
                String accessTokenType1;
                try {
                    accessTokenType1 = jwtTokenUtil.getTokenType(accessToken);
                } catch (Exception e) {
                    accessTokenType1 = null;
                }

                if (accessTokenType1 == null || // 해석 불가능한 JWT 토큰
                        !accessTokenType1.equalsIgnoreCase("jwt") || // 토큰 타입이 JWT 가 아님
                        !jwtTokenUtil.getTokenUsage(
                                accessToken,
                                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
                        ).equalsIgnoreCase("access") || // 토큰 용도가 다름
                        // 남은 시간이 최대 만료시간을 초과 (서버 기준이 변경되었을 때, 남은 시간이 더 많은 토큰을 견제하기 위한 처리)
                        jwtTokenUtil.getRemainSeconds(accessToken) > AUTH_JWT_ACCESS_TOKEN_EXPIRATION_TIME_SEC ||
                        !jwtTokenUtil.getIssuer(accessToken).equals(AUTH_JWT_ISSUER) || // 발행인 불일치
                        !jwtTokenUtil.validateSignature(accessToken, AUTH_JWT_SECRET_KEY_STRING) // 시크릿 검증이 무효 = 위변조 된 토큰
                ) {
                    // 올바르지 않은 Authorization Token
                    httpServletResponse.setHeader("api-result-code", "3");
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    return null;
                }

                // 토큰 검증 정상 -> 데이터베이스 현 상태 확인

                // 유저 탈퇴 여부 확인
                Long accessTokenMemberUid = jwtTokenUtil.getMemberUid(accessToken, AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR, AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY);
                var memberDataOpt = db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(accessTokenMemberUid, "/");
                if (memberDataOpt.isEmpty()) {
                    // 멤버 탈퇴
                    httpServletResponse.setHeader("api-result-code", "4");
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    return null;
                }

                Db1_RaillyLinkerCompany_TotalAuthMember memberData = memberDataOpt.get();

                // 정지 여부 파악
                List<Db1_Native_Repository.FindAllNowActivateMemberLockInfoOutputVo> lockList = db1NativeRepository.findAllNowActivateMemberLockInfo(memberData.uid, LocalDateTime.now());
                if (!lockList.isEmpty()) {
                    // 계정 정지 당한 상황
                    List<MyServiceTkAuthController.LoginOutputVo.LockedOutput> lockedOutputList = new ArrayList<>();
                    for (Db1_Native_Repository.FindAllNowActivateMemberLockInfoOutputVo lockInfo : lockList) {
                        lockedOutputList.add(new MyServiceTkAuthController.LoginOutputVo.LockedOutput(
                                memberData.uid,
                                lockInfo.getLockStart().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                                lockInfo.getLockBefore() == null ? null : lockInfo.getLockBefore().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                                lockInfo.getLockReasonCode().intValue(),
                                lockInfo.getLockReason()
                        ));
                    }
                    httpServletResponse.setStatus(HttpStatus.OK.value());
                    return new MyServiceTkAuthController.LoginOutputVo(null, lockedOutputList);
                }

                // 로그아웃 여부 파악
                var tokenInfoOpt = db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.findByTokenTypeAndAccessTokenAndLogoutDateAndRowDeleteDateStr(accessTokenType, accessToken, null, "/");
                if (tokenInfoOpt.isEmpty()) {
                    httpServletResponse.setHeader("api-result-code", "5");
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    return null;
                }

                Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory tokenInfo = tokenInfoOpt.get();

                // 액세스 토큰 만료 외의 인증/인가 검증 완료

                // 리플레시 토큰 검증 시작
                // 타입과 토큰을 분리
                String[] refreshTokenInputSplit = inputVo.refreshToken().split(" ");
                if (refreshTokenInputSplit.length < 2) {
                    // 올바르지 않은 Token
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                // 타입 분리
                String tokenType = refreshTokenInputSplit[0].trim(); // 첫번째 단어는 토큰 타입
                String jwtRefreshToken = refreshTokenInputSplit[1].trim(); // 앞의 타입을 자르고 남은 토큰

                if (jwtRefreshToken.isEmpty()) {
                    // 토큰이 비어있음 (올바르지 않은 Authorization Token)
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                switch (tokenType.toLowerCase()) { // 타입 검증
                    case "bearer": { // Bearer JWT 토큰 검증
                        // 토큰 문자열 해석 가능여부 확인
                        String refreshTokenType;
                        try {
                            refreshTokenType = jwtTokenUtil.getTokenType(jwtRefreshToken);
                        } catch (Exception e) {
                            refreshTokenType = null;
                        }

                        // 리프레시 토큰 검증
                        if (refreshTokenType == null ||  // 해석 불가능한 리프레시 토큰
                                !refreshTokenType.equalsIgnoreCase("jwt") || // 토큰 타입이 JWT 가 아닐 때
                                !jwtTokenUtil.getTokenUsage(
                                        jwtRefreshToken,
                                        AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR, AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
                                ).equalsIgnoreCase("refresh") || // 토큰 타입이 Refresh 토큰이 아닐 때
                                jwtTokenUtil.getRemainSeconds(jwtRefreshToken) > AUTH_JWT_REFRESH_TOKEN_EXPIRATION_TIME_SEC ||
                                !jwtTokenUtil.getIssuer(jwtRefreshToken).equals(AUTH_JWT_ISSUER) || // 발행인이 다를 때
                                !jwtTokenUtil.validateSignature(jwtRefreshToken, AUTH_JWT_SECRET_KEY_STRING) || // 시크릿 검증이 유효하지 않을 때 = 위변조된 토큰
                                !jwtTokenUtil.getMemberUid(
                                        jwtRefreshToken,
                                        AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                                        AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
                                ).equals(accessTokenMemberUid) // 리프레시 토큰의 멤버 고유번호와 액세스 토큰 멤버 고유번호가 다를시
                        ) {
                            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                            httpServletResponse.setHeader("api-result-code", "1");
                            return null;
                        }

                        if (jwtTokenUtil.getRemainSeconds(jwtRefreshToken) <= 0L) {
                            // 리플레시 토큰 만료
                            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                            httpServletResponse.setHeader("api-result-code", "2");
                            return null;
                        }

                        if (!jwtRefreshToken.equals(tokenInfo.refreshToken)) {
                            // 건내받은 토큰이 해당 액세스 토큰의 가용 토큰과 맞지 않음
                            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                            httpServletResponse.setHeader("api-result-code", "1");
                            return null;
                        }

                        // 먼저 로그아웃 처리
                        tokenInfo.logoutDate = LocalDateTime.now();
                        db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(tokenInfo);

                        // 토큰 만료처리
                        String tokenType1 = tokenInfo.tokenType;
                        String accessToken1 = tokenInfo.accessToken;

                        Long accessTokenExpireRemainSeconds = null;
                        if ("Bearer".equals(tokenType1)) {
                            accessTokenExpireRemainSeconds = jwtTokenUtil.getRemainSeconds(accessToken1);
                        }

                        try {
                            if (accessTokenExpireRemainSeconds != null) {
                                redis1MapTotalAuthForceExpireAuthorizationSet.saveKeyValue(
                                        tokenType1 + "_" + accessToken1,
                                        new Redis1_Map_TotalAuthForceExpireAuthorizationSet.ValueVo(),
                                        accessTokenExpireRemainSeconds * 1000
                                );
                            }
                        } catch (Exception e) {
                            classLogger.error("error ", e);
                        }

                        // 멤버의 권한 리스트를 조회 후 반환
                        List<String> roleList = new ArrayList<>();
                        for (Db1_RaillyLinkerCompany_TotalAuthMemberRole userRole : db1RaillyLinkerCompanyTotalAuthMemberRoleRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(tokenInfo.totalAuthMember, "/")) {
                            roleList.add(userRole.role);
                        }

                        // 새 토큰 생성 및 로그인 처리
                        String newJwtAccessToken = jwtTokenUtil.generateAccessToken(
                                accessTokenMemberUid, AUTH_JWT_ACCESS_TOKEN_EXPIRATION_TIME_SEC, AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR, AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY, AUTH_JWT_ISSUER, AUTH_JWT_SECRET_KEY_STRING, roleList);
                        LocalDateTime accessTokenExpireWhen = jwtTokenUtil.getExpirationDateTime(newJwtAccessToken);

                        String newRefreshToken = jwtTokenUtil.generateRefreshToken(
                                accessTokenMemberUid, AUTH_JWT_REFRESH_TOKEN_EXPIRATION_TIME_SEC, AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR, AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY, AUTH_JWT_ISSUER, AUTH_JWT_SECRET_KEY_STRING);
                        LocalDateTime refreshTokenExpireWhen = jwtTokenUtil.getExpirationDateTime(newRefreshToken);

                        // 로그인 정보 저장
                        db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(new Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory(
                                tokenInfo.totalAuthMember, "Bearer", LocalDateTime.now(), newJwtAccessToken, accessTokenExpireWhen, newRefreshToken, refreshTokenExpireWhen, null));

                        httpServletResponse.setStatus(HttpStatus.OK.value());
                        return new MyServiceTkAuthController.LoginOutputVo(
                                new MyServiceTkAuthController.LoginOutputVo.LoggedInOutput(
                                        memberData.uid,
                                        "Bearer",
                                        newJwtAccessToken,
                                        newRefreshToken,
                                        accessTokenExpireWhen.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                                        refreshTokenExpireWhen.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))),
                                null);
                    }
                    default:
                        // 지원하지 않는 토큰 타입 (올바르지 않은 Authorization Token)
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "1");
                        return null;
                }
            }
            default:
                // 올바르지 않은 Authorization Token
                httpServletResponse.setHeader("api-result-code", "3");
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                return null;
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteAllJwtOfAMember(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        Db1_RaillyLinkerCompany_TotalAuthMember memberData =
                db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(memberUid, "/").get();

        // loginAccessToken 의 Iterable 가져오기
        List<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> tokenInfoList =
                db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.findAllByTotalAuthMemberAndLogoutDateAndRowDeleteDateStr(
                        memberData,
                        null,
                        "/"
                );

        // 발행되었던 모든 액세스 토큰 무효화 (다른 디바이스에선 사용중 로그아웃된 것과 동일한 효과)
        for (Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory tokenInfo : tokenInfoList) {
            tokenInfo.logoutDate = LocalDateTime.now();
            db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(tokenInfo);

            // 토큰 만료처리
            String tokenType = tokenInfo.tokenType;
            String accessToken = tokenInfo.accessToken;

            Long accessTokenExpireRemainSeconds = null;
            if ("Bearer".equals(tokenType)) {
                accessTokenExpireRemainSeconds = jwtTokenUtil.getRemainSeconds(accessToken);
            }

            try {
                if (accessTokenExpireRemainSeconds != null) {
                    redis1MapTotalAuthForceExpireAuthorizationSet.saveKeyValue(
                            tokenType + "_" + accessToken,
                            new Redis1_Map_TotalAuthForceExpireAuthorizationSet.ValueVo(),
                            accessTokenExpireRemainSeconds * 1000
                    );
                }
            } catch (Exception e) {
                classLogger.error("error : ", e);
            }
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.GetMemberInfoOutputVo getMemberInfo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        Db1_RaillyLinkerCompany_TotalAuthMember memberData =
                db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(memberUid, "/").get();

        // 멤버의 권한 리스트를 조회 후 반환
        List<Db1_RaillyLinkerCompany_TotalAuthMemberRole> memberRoleList =
                db1RaillyLinkerCompanyTotalAuthMemberRoleRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(memberData, "/");

        List<String> roleList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberRole userRole : memberRoleList) {
            roleList.add(userRole.role);
        }

        List<Db1_RaillyLinkerCompany_TotalAuthMemberProfile> profileData =
                db1RaillyLinkerCompanyTotalAuthMemberProfileRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(memberData, "/");
        List<MyServiceTkAuthController.GetMemberInfoOutputVo.ProfileInfo> myProfileList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberProfile profile : profileData) {
            myProfileList.add(new MyServiceTkAuthController.GetMemberInfoOutputVo.ProfileInfo(
                    profile.uid,
                    profile.imageFullUrl,
                    profile.uid.equals(memberData.frontTotalAuthMemberProfile.uid)
            ));
        }

        List<Db1_RaillyLinkerCompany_TotalAuthMemberEmail> emailEntityList =
                db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(memberData, "/");
        List<MyServiceTkAuthController.GetMemberInfoOutputVo.EmailInfo> myEmailList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberEmail emailEntity : emailEntityList) {
            myEmailList.add(new MyServiceTkAuthController.GetMemberInfoOutputVo.EmailInfo(
                    emailEntity.uid,
                    emailEntity.emailAddress,
                    emailEntity.uid.equals(memberData.frontTotalAuthMemberEmail.uid)
            ));
        }

        List<Db1_RaillyLinkerCompany_TotalAuthMemberPhone> phoneEntityList =
                db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(memberData, "/");
        List<MyServiceTkAuthController.GetMemberInfoOutputVo.PhoneNumberInfo> myPhoneNumberList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberPhone phoneEntity : phoneEntityList) {
            myPhoneNumberList.add(new MyServiceTkAuthController.GetMemberInfoOutputVo.PhoneNumberInfo(
                    phoneEntity.uid,
                    phoneEntity.phoneNumber,
                    phoneEntity.uid.equals(memberData.frontTotalAuthMemberPhone.uid)
            ));
        }

        List<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login> oAuth2EntityList =
                db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(memberData, "/");
        List<MyServiceTkAuthController.GetMemberInfoOutputVo.OAuth2Info> myOAuth2List = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login oAuth2Entity : oAuth2EntityList) {
            myOAuth2List.add(new MyServiceTkAuthController.GetMemberInfoOutputVo.OAuth2Info(
                    oAuth2Entity.uid,
                    (int) oAuth2Entity.oauth2TypeCode,
                    oAuth2Entity.oauth2Id
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.GetMemberInfoOutputVo(
                memberData.accountId,
                roleList,
                myOAuth2List,
                myProfileList,
                myEmailList,
                myPhoneNumberList,
                memberData.accountPassword == null
        );
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.CheckIdDuplicateOutputVo checkIdDuplicate(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        boolean isDuplicate = db1RaillyLinkerCompanyTotalAuthMemberRepository.existsByAccountIdAndRowDeleteDateStr(id.trim(), "/");
        return new MyServiceTkAuthController.CheckIdDuplicateOutputVo(isDuplicate);
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void updateId(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        Db1_RaillyLinkerCompany_TotalAuthMember memberData =
                db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(memberUid, "/").get();

        if (db1RaillyLinkerCompanyTotalAuthMemberRepository.existsByAccountIdAndRowDeleteDateStr(id, "/")) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        memberData.accountId = id;
        db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberData);

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void joinTheMembershipForTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.JoinTheMembershipForTestInputVo inputVo
    ) throws IOException {
        if (!"aadke234!@".equals(inputVo.apiSecret())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (db1RaillyLinkerCompanyTotalAuthMemberRepository.existsByAccountIdAndRowDeleteDateStr(inputVo.id().trim(), "/")) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        if (inputVo.email() != null) {
            boolean isUserExists = db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.existsByEmailAddressAndRowDeleteDateStr(inputVo.email(), "/");
            if (isUserExists) {
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "3");
                return;
            }
        }

        if (inputVo.phoneNumber() != null) {
            boolean isUserExists = db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository.existsByPhoneNumberAndRowDeleteDateStr(inputVo.phoneNumber(), "/");
            if (isUserExists) {
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "4");
                return;
            }
        }

        String password = passwordEncoder.encode(inputVo.password()); // 비밀번호 암호화

        // 회원가입
        Db1_RaillyLinkerCompany_TotalAuthMember memberEntity = new Db1_RaillyLinkerCompany_TotalAuthMember(
                inputVo.id(),
                password,
                null,
                null,
                null
        );
        memberEntity = db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberEntity);

        if (inputVo.profileImageFile() != null) {
            // 저장된 프로필 이미지 파일을 다운로드 할 수 있는 URL

            //----------------------------------------------------------------------------------------------------------
            // 프로필 이미지를 서버 스토리지에 저장할 때 사용하는 방식
            Path saveDirectoryPath = Paths.get("./by_product_files/member/profile").toAbsolutePath().normalize();
            Files.createDirectories(saveDirectoryPath);

            String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.profileImageFile().getOriginalFilename()));
            int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

            String fileNameWithOutExtension;
            String fileExtension;

            if (fileExtensionSplitIdx == -1) {
                fileNameWithOutExtension = multiPartFileNameString;
                fileExtension = "";
            } else {
                fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
                fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
            }

            String savedFileName = String.format("%s(%s).%s",
                    fileNameWithOutExtension,
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                    fileExtension);

            inputVo.profileImageFile().transferTo(saveDirectoryPath.resolve(savedFileName).normalize());

            String savedProfileImageUrl = externalAccessAddress + "/my-service/tk/auth/member-profile/" + savedFileName;
            //----------------------------------------------------------------------------------------------------------

            Db1_RaillyLinkerCompany_TotalAuthMemberProfile memberProfileData = new Db1_RaillyLinkerCompany_TotalAuthMemberProfile(memberEntity, savedProfileImageUrl);
            memberProfileData = db1RaillyLinkerCompanyTotalAuthMemberProfileRepository.save(memberProfileData);
            memberEntity.frontTotalAuthMemberProfile = memberProfileData;
        }

        if (inputVo.email() != null) {
            // 이메일 저장
            Db1_RaillyLinkerCompany_TotalAuthMemberEmail memberEmailData = new Db1_RaillyLinkerCompany_TotalAuthMemberEmail(memberEntity, inputVo.email());
            memberEmailData = db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.save(memberEmailData);
            memberEntity.frontTotalAuthMemberEmail = memberEmailData;
        }

        if (inputVo.phoneNumber() != null) {
            // 전화번호 저장
            Db1_RaillyLinkerCompany_TotalAuthMemberPhone memberPhoneData = new Db1_RaillyLinkerCompany_TotalAuthMemberPhone(memberEntity, inputVo.phoneNumber());
            memberPhoneData = db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository.save(memberPhoneData);
            memberEntity.frontTotalAuthMemberPhone = memberPhoneData;
        }

        db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberEntity);

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.SendEmailVerificationForJoinOutputVo sendEmailVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendEmailVerificationForJoinInputVo inputVo
    ) throws Exception {
        // 입력 데이터 검증
        boolean memberExists = db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.existsByEmailAddressAndRowDeleteDateStr(
                inputVo.email(),
                "/"
        );

        if (memberExists) { // 기존 회원 존재
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        // 정보 저장 후 이메일 발송
        long verificationTimeSec = 60 * 10;
        String verificationCode = String.format("%06d", new Random().nextInt(999999)); // 랜덤 6자리 숫자
        Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithEmailVerification memberRegisterEmailVerificationData =
                db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithEmailVerificationRepository.save(
                        new Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithEmailVerification(
                                inputVo.email(),
                                verificationCode,
                                LocalDateTime.now().plusSeconds(verificationTimeSec)
                        )
                );

        emailSender.sendThymeLeafHtmlMail(
                "Springboot Mvc Project Template",
                new String[]{inputVo.email()},
                null,
                "Springboot Mvc Project Template 회원가입 - 본인 계정 확인용 이메일입니다.",
                "send_email_verification_for_join/email_verification_email",
                Map.of("verificationCode", verificationCode),
                null,
                null,
                null,
                null
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkAuthController.SendEmailVerificationForJoinOutputVo(
                memberRegisterEmailVerificationData.uid,
                memberRegisterEmailVerificationData.verificationExpireWhen
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @Override
    public void checkEmailVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        Optional<Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithEmailVerification> emailVerificationOpt =
                db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithEmailVerificationRepository.findByUidAndRowDeleteDateStr(
                        verificationUid,
                        "/"
                );

        if (emailVerificationOpt.isEmpty()) { // 해당 이메일 검증을 요청한적이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithEmailVerification emailVerification = emailVerificationOpt.get();

        if (!emailVerification.emailAddress.equals(email)) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(emailVerification.verificationExpireWhen)) {
            // 만료됨
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        // 입력 코드와 발급된 코드와의 매칭
        if (emailVerification.verificationSecret.equals(verificationCode)) {
            // 코드 일치
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else {
            // 코드 불일치
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void joinTheMembershipWithEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.JoinTheMembershipWithEmailInputVo inputVo
    ) throws IOException {
        Optional<Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithEmailVerification> emailVerificationOpt =
                db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithEmailVerificationRepository.findByUidAndRowDeleteDateStr(
                        inputVo.verificationUid(),
                        "/"
                );

        if (emailVerificationOpt.isEmpty()) { // 해당 이메일 검증을 요청한적이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithEmailVerification emailVerification = emailVerificationOpt.get();

        if (!emailVerification.emailAddress.equals(inputVo.email())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(emailVerification.verificationExpireWhen)) {
            // 만료됨
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        // 입력 코드와 발급된 코드와의 매칭
        if (emailVerification.verificationSecret.equals(inputVo.verificationCode())) { // 코드 일치
            boolean isUserExists =
                    db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.existsByEmailAddressAndRowDeleteDateStr(
                            inputVo.email(),
                            "/"
                    );
            if (isUserExists) { // 기존 회원이 있을 때
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "4");
                return;
            }

            if (db1RaillyLinkerCompanyTotalAuthMemberRepository.existsByAccountIdAndRowDeleteDateStr(
                    inputVo.id().trim(),
                    "/"
            )
            ) {
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "5");
                return;
            }

            String password = passwordEncoder.encode(inputVo.password()); // 비밀번호 암호화

            // 회원가입
            Db1_RaillyLinkerCompany_TotalAuthMember memberData = db1RaillyLinkerCompanyTotalAuthMemberRepository.save(
                    new Db1_RaillyLinkerCompany_TotalAuthMember(
                            inputVo.id(),
                            password,
                            null,
                            null,
                            null
                    )
            );

            // 이메일 저장
            Db1_RaillyLinkerCompany_TotalAuthMemberEmail memberEmailData = db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.save(
                    new Db1_RaillyLinkerCompany_TotalAuthMemberEmail(
                            memberData,
                            inputVo.email()
                    )
            );

            memberData.frontTotalAuthMemberEmail = memberEmailData;

            if (inputVo.profileImageFile() != null) {
                // 저장된 프로필 이미지 파일을 다운로드 할 수 있는 URL
                String savedProfileImageUrl;

                // 프로필 이미지 파일 저장

                //----------------------------------------------------------------------------------------------------------
                // 프로필 이미지를 서버 스토리지에 저장할 때 사용하는 방식
                // 파일 저장 기본 디렉토리 경로
                Path saveDirectoryPath = Paths.get("./by_product_files/member/profile").toAbsolutePath().normalize();

                // 파일 저장 기본 디렉토리 생성
                Files.createDirectories(saveDirectoryPath);

                // 원본 파일명(with suffix)
                String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.profileImageFile().getOriginalFilename()));

                // 파일 확장자 구분 위치
                int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

                // 확장자가 없는 파일명
                String fileNameWithOutExtension;
                // 확장자
                String fileExtension;

                if (fileExtensionSplitIdx == -1) {
                    fileNameWithOutExtension = multiPartFileNameString;
                    fileExtension = "";
                } else {
                    fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
                    fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
                }

                String savedFileName = String.format("%s(%s).%s", fileNameWithOutExtension,
                        LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                        fileExtension);

                // multipartFile 을 targetPath 에 저장
                inputVo.profileImageFile().transferTo(saveDirectoryPath.resolve(savedFileName).normalize());

                savedProfileImageUrl = externalAccessAddress + "/my-service/tk/auth/member-profile/" + savedFileName;
                //----------------------------------------------------------------------------------------------------------

                Db1_RaillyLinkerCompany_TotalAuthMemberProfile memberProfileData = db1RaillyLinkerCompanyTotalAuthMemberProfileRepository.save(
                        new Db1_RaillyLinkerCompany_TotalAuthMemberProfile(
                                memberData,
                                savedProfileImageUrl
                        )
                );

                memberData.frontTotalAuthMemberProfile = memberProfileData;
            }

            db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberData);

            // 확인 완료된 검증 요청 정보 삭제
            emailVerification.rowDeleteDateStr =
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
            db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithEmailVerificationRepository.save(emailVerification);

            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else { // 코드 불일치
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.SendPhoneVerificationForJoinOutputVo sendPhoneVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendPhoneVerificationForJoinInputVo inputVo
    ) {
        // 입력 데이터 검증
        boolean memberExists = db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository.existsByPhoneNumberAndRowDeleteDateStr(
                inputVo.phoneNumber(), "/"
        );

        if (memberExists) { // 기존 회원 존재
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        // 정보 저장 후 발송
        long verificationTimeSec = 60 * 10;
        String verificationCode = String.format("%06d", new Random().nextInt(999999)); // 랜덤 6자리 숫자
        Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithPhoneVerification memberRegisterPhoneNumberVerificationData =
                db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithPhoneVerificationRepository.save(
                        new Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithPhoneVerification(
                                inputVo.phoneNumber(),
                                verificationCode,
                                LocalDateTime.now().plusSeconds(verificationTimeSec)
                        )
                );

        String[] phoneNumberSplit = inputVo.phoneNumber().split("\\)"); // ["82", "010-0000-0000"]

        // 국가 코드 (ex : 82)
        String countryCode = phoneNumberSplit[0];

        // 전화번호 (ex : "01000000000")
        String phoneNumber = phoneNumberSplit[1].replace("-", "").replace(" ", "");

        boolean sendSmsResult = naverSmsSenderComponent.sendSms(
                new NaverSmsSenderComponent.SendSmsInputVo(
                        "SMS",
                        countryCode,
                        phoneNumber,
                        "[Springboot Mvc Project Template - 회원가입] 인증번호 [" + verificationCode + "]"
                )
        );

        if (!sendSmsResult) {
            throw new RuntimeException();
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.SendPhoneVerificationForJoinOutputVo(
                memberRegisterPhoneNumberVerificationData.uid,
                memberRegisterPhoneNumberVerificationData.verificationExpireWhen
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @Override
    public void checkPhoneVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        Optional<Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithPhoneVerification> phoneNumberVerificationOpt =
                db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithPhoneVerificationRepository.findByUidAndRowDeleteDateStr(
                        verificationUid, "/"
                );

        if (phoneNumberVerificationOpt.isEmpty()) { // 해당 이메일 검증을 요청한적이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithPhoneVerification phoneNumberVerification = phoneNumberVerificationOpt.get();

        if (!phoneNumberVerification.phoneNumber.equals(phoneNumber)) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(phoneNumberVerification.verificationExpireWhen)) {
            // 만료됨
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        // 입력 코드와 발급된 코드와의 매칭
        if (phoneNumberVerification.verificationSecret.equals(verificationCode)) {
            // 코드 일치
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else {
            // 코드 불일치
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void joinTheMembershipWithPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.JoinTheMembershipWithPhoneNumberInputVo inputVo
    ) throws IOException {
        Optional<Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithPhoneVerification> phoneNumberVerificationOpt =
                db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithPhoneVerificationRepository.findByUidAndRowDeleteDateStr(inputVo.verificationUid(), "/");

        if (phoneNumberVerificationOpt.isEmpty()) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithPhoneVerification phoneNumberVerification = phoneNumberVerificationOpt.get();

        if (!phoneNumberVerification.phoneNumber.equals(inputVo.phoneNumber())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(phoneNumberVerification.verificationExpireWhen)) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        if (phoneNumberVerification.verificationSecret.equals(inputVo.verificationCode())) {

            boolean isUserExists = db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository.existsByPhoneNumberAndRowDeleteDateStr(inputVo.phoneNumber(), "/");
            if (isUserExists) {
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "4");
                return;
            }

            if (db1RaillyLinkerCompanyTotalAuthMemberRepository.existsByAccountIdAndRowDeleteDateStr(inputVo.id().trim(), "/")) {
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "5");
                return;
            }

            // Encrypt password
            String password = passwordEncoder.encode(inputVo.password());

            // Save member data
            Db1_RaillyLinkerCompany_TotalAuthMember memberUser = db1RaillyLinkerCompanyTotalAuthMemberRepository.save(
                    new Db1_RaillyLinkerCompany_TotalAuthMember(inputVo.id(), password, null, null, null));

            // Save phone number
            Db1_RaillyLinkerCompany_TotalAuthMemberPhone memberPhoneData = db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository.save(
                    new Db1_RaillyLinkerCompany_TotalAuthMemberPhone(memberUser, inputVo.phoneNumber()));

            memberUser.frontTotalAuthMemberPhone = memberPhoneData;

            // Save profile image if available
            if (inputVo.profileImageFile() != null) {
                // File storage directory
                Path saveDirectoryPath = Paths.get("./by_product_files/member/profile").toAbsolutePath().normalize();
                Files.createDirectories(saveDirectoryPath);

                // 원본 파일명(with suffix)
                String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.profileImageFile().getOriginalFilename()));

                // 파일 확장자 구분 위치
                int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

                // 확장자가 없는 파일명
                String fileNameWithOutExtension;
                // 확장자
                String fileExtension;

                if (fileExtensionSplitIdx == -1) {
                    fileNameWithOutExtension = multiPartFileNameString;
                    fileExtension = "";
                } else {
                    fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
                    fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
                }

                String savedFileName = fileNameWithOutExtension + "(" + LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")) + ")." + fileExtension;

                // Save the profile image file
                inputVo.profileImageFile().transferTo(saveDirectoryPath.resolve(savedFileName).normalize());

                String savedProfileImageUrl = externalAccessAddress + "/my-service/tk/auth/member-profile/" + savedFileName;

                Db1_RaillyLinkerCompany_TotalAuthMemberProfile memberProfileData = db1RaillyLinkerCompanyTotalAuthMemberProfileRepository.save(
                        new Db1_RaillyLinkerCompany_TotalAuthMemberProfile(memberUser, savedProfileImageUrl));

                memberUser.frontTotalAuthMemberProfile = memberProfileData;
            }

            db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberUser);

            // Delete phone verification data after successful membership
            phoneNumberVerification.rowDeleteDateStr = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
            db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithPhoneVerificationRepository.save(phoneNumberVerification);

            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.CheckOauth2AccessTokenVerificationForJoinOutputVo checkOauth2AccessTokenVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.CheckOauth2AccessTokenVerificationForJoinInputVo inputVo
    ) throws IOException {
        Long verificationUid;
        String verificationCode;
        String expireWhen;
        String loginId;

        long verificationTimeSec = 60 * 10;

        switch (inputVo.oauth2TypeCode()) {
            case 1: { // GOOGLE
                // 클라이언트에서 받은 access 토큰으로 멤버 정보 요청
                Response<WwwGoogleapisComRequestApi.GetOauth2V1UserInfoOutputVO> response =
                        networkRetrofit2.wwwGoogleapisComRequestApi.getOauth2V1UserInfo(inputVo.oauth2AccessToken()).execute();

                // 액세스 토큰 정상 동작 확인
                if (response.code() != 200 || response.body() == null) {
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                loginId = response.body().id();

                boolean memberExists = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository.existsByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr(
                        (byte) 1, Objects.requireNonNull(loginId), "/"
                );

                if (memberExists) { // 기존 회원 존재
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "2");
                    return null;
                }

                verificationCode = String.format("%06d", new Random().nextInt(999999)); // 랜덤 6자리 숫자
                Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification memberRegisterOauth2VerificationData =
                        db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository.save(
                                new Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification(
                                        (byte) 1, loginId, verificationCode, LocalDateTime.now().plusSeconds(verificationTimeSec)
                                )
                        );

                verificationUid = memberRegisterOauth2VerificationData.uid;

                expireWhen = memberRegisterOauth2VerificationData.verificationExpireWhen.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
                break;
            }
            case 2: { // NAVER
                // 클라이언트에서 받은 access 토큰으로 멤버 정보 요청
                Response<OpenapiNaverComRequestApi.GetV1NidMeOutputVO> response =
                        networkRetrofit2.openapiNaverComRequestApi.getV1NidMe(inputVo.oauth2AccessToken()).execute();

                // 액세스 토큰 정상 동작 확인
                if (response.code() != 200 || response.body() == null) {
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                loginId = response.body().response().id();

                boolean memberExists = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository.existsByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr(
                        (byte) 2, loginId, "/"
                );

                if (memberExists) { // 기존 회원 존재
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "2");
                    return null;
                }

                verificationCode = String.format("%06d", new Random().nextInt(999999)); // 랜덤 6자리 숫자
                Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification memberRegisterOauth2VerificationData =
                        db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository.save(
                                new Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification(
                                        (byte) 2, loginId, verificationCode, LocalDateTime.now().plusSeconds(verificationTimeSec)
                                )
                        );

                verificationUid = memberRegisterOauth2VerificationData.uid;

                expireWhen = memberRegisterOauth2VerificationData.verificationExpireWhen.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
                break;
            }
            case 3: { // KAKAO TALK
                // 클라이언트에서 받은 access 토큰으로 멤버 정보 요청
                Response<KapiKakaoComRequestApi.GetV2UserMeOutputVO> response =
                        networkRetrofit2.kapiKakaoComRequestApi.getV2UserMe(inputVo.oauth2AccessToken()).execute();

                // 액세스 토큰 정상 동작 확인
                if (response.code() != 200 || response.body() == null) {
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                loginId = String.valueOf(response.body().id());

                boolean memberExists = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository.existsByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr(
                        (byte) 3, loginId, "/"
                );

                if (memberExists) { // 기존 회원 존재
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "2");
                    return null;
                }

                verificationCode = String.format("%06d", new Random().nextInt(999999)); // 랜덤 6자리 숫자
                Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification memberRegisterOauth2VerificationData =
                        db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository.save(
                                new Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification(
                                        (byte) 3, loginId, verificationCode, LocalDateTime.now().plusSeconds(verificationTimeSec)
                                )
                        );

                verificationUid = memberRegisterOauth2VerificationData.uid;

                expireWhen = memberRegisterOauth2VerificationData.verificationExpireWhen.atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
                break;
            }
            default:
                classLogger.info("SNS Login Type {} Not Supported", inputVo.oauth2TypeCode());
                httpServletResponse.setStatus(400);
                return null;
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.CheckOauth2AccessTokenVerificationForJoinOutputVo(
                verificationUid, verificationCode, loginId, expireWhen
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.CheckOauth2IdTokenVerificationForJoinOutputVo checkOauth2IdTokenVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.CheckOauth2IdTokenVerificationForJoinInputVo inputVo
    ) {
        long verificationUid;
        String verificationCode;
        String expireWhen;
        String loginId;

        long verificationTimeSec = 60 * 10;

        switch (inputVo.oauth2TypeCode()) {
            case 4: { // Apple
                AppleOAuthHelperUtil.AppleProfileData appleInfo = appleOAuthHelperUtil.getAppleMemberData(inputVo.oauth2IdToken());

                if (appleInfo != null) {
                    loginId = appleInfo.snsId();
                } else {
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "1");
                    return null;
                }

                boolean memberExists = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository.existsByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr(
                        (byte) 4,
                        loginId,
                        "/"
                );

                if (memberExists) { // Existing member
                    httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                    httpServletResponse.setHeader("api-result-code", "2");
                    return null;
                }

                verificationCode = String.format("%06d", new Random().nextInt(999999)); // Random 6-digit number
                Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification memberRegisterOauth2VerificationData =
                        db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository.save(
                                new Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification(
                                        (byte) 4,
                                        loginId,
                                        verificationCode,
                                        LocalDateTime.now().plusSeconds(verificationTimeSec)
                                )
                        );

                verificationUid = memberRegisterOauth2VerificationData.uid;

                expireWhen = memberRegisterOauth2VerificationData.verificationExpireWhen
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
                break;
            }

            default:
                classLogger.info("SNS Login Type " + inputVo.oauth2TypeCode() + " Not Supported");
                httpServletResponse.setStatus(400);
                return null;
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.CheckOauth2IdTokenVerificationForJoinOutputVo(
                verificationUid,
                verificationCode,
                loginId,
                expireWhen
        );
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void joinTheMembershipWithOauth2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.JoinTheMembershipWithOauth2InputVo inputVo
    ) throws IOException {
        // oauth2 종류 (1 : GOOGLE, 2 : NAVER, 3 : KAKAO)
        int oauth2TypeCode;

        switch (inputVo.oauth2TypeCode()) {
            case 1:
                oauth2TypeCode = 1;
                break;
            case 2:
                oauth2TypeCode = 2;
                break;
            case 3:
                oauth2TypeCode = 3;
                break;
            case 4:
                oauth2TypeCode = 4;
                break;
            default:
                httpServletResponse.setStatus(400);
                return;
        }

        Optional<Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification> oauth2VerificationOpt =
                db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository
                        .findByUidAndRowDeleteDateStr(inputVo.verificationUid(), "/");

        if (oauth2VerificationOpt.isEmpty()) { // 해당 검증을 요청한적이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthJoinTheMembershipWithOauth2Verification oauth2Verification = oauth2VerificationOpt.get();

        if (oauth2Verification.oauth2TypeCode != (byte) oauth2TypeCode ||
                !oauth2Verification.oauth2Id.equals(inputVo.oauth2Id())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(oauth2Verification.verificationExpireWhen)) {
            // 만료됨
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        // 입력 코드와 발급된 코드와의 매칭
        if (oauth2Verification.verificationSecret.equals(inputVo.verificationCode())) { // 코드 일치
            boolean isUserExists = db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository
                    .existsByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr(
                            inputVo.oauth2TypeCode().byteValue(),
                            inputVo.oauth2Id(),
                            "/"
                    );

            if (isUserExists) { // 기존 회원이 있을 때
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "4");
                return;
            }

            if (db1RaillyLinkerCompanyTotalAuthMemberRepository
                    .existsByAccountIdAndRowDeleteDateStr(inputVo.id().trim(), "/")) {
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "5");
                return;
            }

            // 회원가입
            Db1_RaillyLinkerCompany_TotalAuthMember memberEntity = db1RaillyLinkerCompanyTotalAuthMemberRepository.save(
                    new Db1_RaillyLinkerCompany_TotalAuthMember(
                            inputVo.id(),
                            null, null, null, null
                    )
            );

            // SNS OAuth2 저장
            db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository.save(
                    new Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login(
                            memberEntity,
                            inputVo.oauth2TypeCode().byteValue(),
                            inputVo.oauth2Id()
                    )
            );

            if (inputVo.profileImageFile() != null) {
                // 저장된 프로필 이미지 파일을 다운로드 할 수 있는 URL
                String savedProfileImageUrl;

                // 프로필 이미지 파일 저장
                Path saveDirectoryPath = Paths.get("./by_product_files/member/profile").toAbsolutePath().normalize();

                // 파일 저장 디렉토리 생성
                Files.createDirectories(saveDirectoryPath);

                String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.profileImageFile().getOriginalFilename()));
                int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');
                String fileNameWithOutExtension;
                String fileExtension;

                if (fileExtensionSplitIdx == -1) {
                    fileNameWithOutExtension = multiPartFileNameString;
                    fileExtension = "";
                } else {
                    fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
                    fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
                }

                String savedFileName = fileNameWithOutExtension + "(" +
                        LocalDateTime.now().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                        + ")." + fileExtension;

                inputVo.profileImageFile().transferTo(
                        saveDirectoryPath.resolve(savedFileName).normalize()
                );

                savedProfileImageUrl = externalAccessAddress + "/my-service/tk/auth/member-profile/" + savedFileName;

                Db1_RaillyLinkerCompany_TotalAuthMemberProfile memberProfileData =
                        db1RaillyLinkerCompanyTotalAuthMemberProfileRepository.save(
                                new Db1_RaillyLinkerCompany_TotalAuthMemberProfile(
                                        memberEntity,
                                        savedProfileImageUrl
                                )
                        );

                memberEntity.frontTotalAuthMemberProfile = memberProfileData;
            }

            db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberEntity);

            // 확인 완료된 검증 요청 정보 삭제
            oauth2Verification.rowDeleteDateStr =
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
            db1RaillyLinkerCompanyTotalAuthJoinTheMembershipWithOauth2VerificationRepository.save(oauth2Verification);

            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else { // 코드 불일치
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void updateAccountPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.UpdateAccountPasswordInputVo inputVo
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        Db1_RaillyLinkerCompany_TotalAuthMember memberData =
                db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(memberUid, "/").get();

        if (memberData.accountPassword == null) { // 기존 비밀번호가 존재하지 않음
            if (inputVo.oldPassword() != null) { // 비밀번호 불일치
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "1");
                return;
            }
        } else { // 기존 비밀번호 존재
            if (inputVo.oldPassword() == null || !passwordEncoder.matches(inputVo.oldPassword(), memberData.accountPassword)) { // 비밀번호 불일치
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "1");
                return;
            }
        }

        if (inputVo.newPassword() == null) {
            List<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login> oAuth2EntityList =
                    db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(memberData, "/");

            if (oAuth2EntityList.isEmpty()) {
                // null로 만들려 할 때 account 외의 OAuth2 인증이 없다면 제거 불가
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "2");
                return;
            }

            memberData.accountPassword = null;
        } else {
            memberData.accountPassword = passwordEncoder.encode(inputVo.newPassword()); // 비밀번호는 암호화
        }

        db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberData);

        // 모든 토큰 비활성화 처리
        List<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> tokenInfoList =
                db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.findAllByTotalAuthMemberAndLogoutDateAndRowDeleteDateStr(
                        memberData, null, "/"
                );

        // 발행되었던 모든 액세스 토큰 무효화 (다른 디바이스에선 사용 중 로그아웃된 것과 동일한 효과)
        for (Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory tokenInfo : tokenInfoList) {
            tokenInfo.logoutDate = LocalDateTime.now();
            db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(tokenInfo);

            // 토큰 만료 처리
            String tokenType = tokenInfo.tokenType;
            String accessToken = tokenInfo.accessToken;

            Long accessTokenExpireRemainSeconds = null;
            if ("Bearer".equals(tokenType)) {
                accessTokenExpireRemainSeconds = jwtTokenUtil.getRemainSeconds(accessToken);
            }

            try {
                if (accessTokenExpireRemainSeconds != null) {
                    redis1MapTotalAuthForceExpireAuthorizationSet.saveKeyValue(
                            tokenType + "_" + accessToken,
                            new Redis1_Map_TotalAuthForceExpireAuthorizationSet.ValueVo(),
                            accessTokenExpireRemainSeconds * 1000
                    );
                }
            } catch (Exception e) {
                classLogger.error("error ", e);
            }
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.SendEmailVerificationForFindPasswordOutputVo sendEmailVerificationForFindPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendEmailVerificationForFindPasswordInputVo inputVo
    ) throws Exception {
        // 입력 데이터 검증
        boolean memberExists = db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.existsByEmailAddressAndRowDeleteDateStr(
                inputVo.email(),
                "/"
        );

        if (!memberExists) { // 회원 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        // 정보 저장 후 이메일 발송
        long verificationTimeSec = 60 * 10;
        String verificationCode = String.format("%06d", new Random().nextInt(999999)); // 랜덤 6자리 숫자

        Db1_RaillyLinkerCompany_TotalAuthFindPwWithEmailVerification memberFindPasswordEmailVerificationData =
                db1RaillyLinkerCompanyTotalAuthFindPwWithEmailVerificationRepository.save(
                        new Db1_RaillyLinkerCompany_TotalAuthFindPwWithEmailVerification(
                                inputVo.email(),
                                verificationCode,
                                LocalDateTime.now().plusSeconds(verificationTimeSec)
                        )
                );

        emailSender.sendThymeLeafHtmlMail(
                "Springboot Mvc Project Template",
                new String[]{inputVo.email()},
                null,
                "Springboot Mvc Project Template 비밀번호 찾기 - 본인 계정 확인용 이메일입니다.",
                "send_email_verification_for_find_password/find_password_email_verification_email",
                new HashMap<String, Object>() {{
                    put("verificationCode", verificationCode);
                }},
                null,
                null,
                null,
                null
        );

        return new MyServiceTkAuthController.SendEmailVerificationForFindPasswordOutputVo(
                memberFindPasswordEmailVerificationData.uid,
                memberFindPasswordEmailVerificationData.verificationExpireWhen
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @Override
    public void checkEmailVerificationForFindPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        Optional<Db1_RaillyLinkerCompany_TotalAuthFindPwWithEmailVerification> emailVerificationOpt =
                db1RaillyLinkerCompanyTotalAuthFindPwWithEmailVerificationRepository.findByUidAndRowDeleteDateStr(
                        verificationUid,
                        "/"
                );

        if (emailVerificationOpt.isEmpty()) { // 해당 이메일 검증을 요청한 적이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthFindPwWithEmailVerification emailVerification = emailVerificationOpt.get();

        if (!emailVerification.emailAddress.equals(email)) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(emailVerification.verificationExpireWhen)) {
            // 만료됨
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        // 입력 코드와 발급된 코드와의 매칭
        boolean codeMatched = emailVerification.verificationSecret.equals(verificationCode);

        if (codeMatched) {
            // 코드 일치
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else {
            // 코드 불일치
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void findPasswordWithEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.FindPasswordWithEmailInputVo inputVo
    ) throws Exception {
        Optional<Db1_RaillyLinkerCompany_TotalAuthFindPwWithEmailVerification> emailVerificationOpt =
                db1RaillyLinkerCompanyTotalAuthFindPwWithEmailVerificationRepository.findByUidAndRowDeleteDateStr(
                        inputVo.verificationUid(),
                        "/"
                );

        if (emailVerificationOpt.isEmpty()) { // 해당 이메일 검증을 요청한 적이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthFindPwWithEmailVerification emailVerification = emailVerificationOpt.get();

        if (!emailVerification.emailAddress.equals(inputVo.email())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(emailVerification.verificationExpireWhen)) {
            // 만료됨
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        // 입력 코드와 발급된 코드와의 매칭
        if (emailVerification.verificationSecret.equals(inputVo.verificationCode())) { // 코드 일치
            // 입력 데이터 검증
            Optional<Db1_RaillyLinkerCompany_TotalAuthMemberEmail> memberEmailOpt =
                    db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.findByEmailAddressAndRowDeleteDateStr(
                            inputVo.email(),
                            "/"
                    );

            if (memberEmailOpt.isEmpty()) {
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "4");
                return;
            }

            Db1_RaillyLinkerCompany_TotalAuthMemberEmail memberEmail = memberEmailOpt.get();

            // 랜덤 비번 생성 후 세팅
            String newPassword = String.format("%09d", new Random().nextInt(999999999)); // 랜덤 9자리 숫자
            memberEmail.totalAuthMember.accountPassword = passwordEncoder.encode(newPassword); // 비밀번호는 암호화
            db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberEmail.totalAuthMember);

            // 생성된 비번 이메일 전송
            emailSender.sendThymeLeafHtmlMail(
                    "Springboot Mvc Project Template",
                    new String[]{inputVo.email()},
                    null,
                    "Springboot Mvc Project Template 새 비밀번호 발급",
                    "find_password_with_email/find_password_new_password_email",
                    new HashMap<String, Object>() {{
                        put("newPassword", newPassword);
                    }},
                    null,
                    null,
                    null,
                    null
            );

            // 확인 완료된 검증 요청 정보 삭제
            emailVerification.rowDeleteDateStr =
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
            db1RaillyLinkerCompanyTotalAuthFindPwWithEmailVerificationRepository.save(emailVerification);

            // 모든 토큰 비활성화 처리
            // loginAccessToken 의 Iterable 가져오기
            Iterable<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> tokenInfoList =
                    db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.findAllByTotalAuthMemberAndLogoutDateAndRowDeleteDateStr(
                            memberEmail.totalAuthMember,
                            null,
                            "/"
                    );

            // 발행되었던 모든 액세스 토큰 무효화 (다른 디바이스에선 사용중 로그아웃된 것과 동일한 효과)
            for (Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory tokenInfo : tokenInfoList) {
                tokenInfo.logoutDate = LocalDateTime.now();
                db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(tokenInfo);

                // 토큰 만료처리
                String tokenType = tokenInfo.tokenType;
                String accessToken = tokenInfo.accessToken;

                Long accessTokenExpireRemainSeconds = null;
                if ("Bearer".equals(tokenType)) {
                    accessTokenExpireRemainSeconds = jwtTokenUtil.getRemainSeconds(accessToken);
                }

                try {
                    redis1MapTotalAuthForceExpireAuthorizationSet.saveKeyValue(
                            tokenType + "_" + accessToken,
                            new Redis1_Map_TotalAuthForceExpireAuthorizationSet.ValueVo(),
                            accessTokenExpireRemainSeconds != null ? accessTokenExpireRemainSeconds * 1000 : 0
                    );
                } catch (Exception e) {
                    classLogger.error("error ", e);
                    e.printStackTrace();
                }
            }

            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else { // 코드 불일치
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.SendPhoneVerificationForFindPasswordOutputVo sendPhoneVerificationForFindPassword(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendPhoneVerificationForFindPasswordInputVo inputVo
    ) {
        // 입력 데이터 검증
        boolean memberExists = db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository
                .existsByPhoneNumberAndRowDeleteDateStr(inputVo.phoneNumber(), "/");
        if (!memberExists) { // 회원 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        // 정보 저장 후 발송
        long verificationTimeSec = 60 * 10;
        String verificationCode = String.format("%06d", new Random().nextInt(999999)); // 랜덤 6자리 숫자
        Db1_RaillyLinkerCompany_TotalAuthFindPwWithPhoneVerification memberFindPasswordPhoneNumberVerificationData =
                db1RaillyLinkerCompanyTotalAuthFindPwWithPhoneVerificationRepository.save(
                        new Db1_RaillyLinkerCompany_TotalAuthFindPwWithPhoneVerification(
                                inputVo.phoneNumber(),
                                verificationCode,
                                LocalDateTime.now().plusSeconds(verificationTimeSec)
                        )
                );

        String[] phoneNumberSplit = inputVo.phoneNumber().split("\\)"); // ["82", "010-0000-0000"]

        // 국가 코드 (ex : 82)
        String countryCode = phoneNumberSplit[0];

        // 전화번호 (ex : "01000000000")
        String phoneNumber = phoneNumberSplit[1].replace("-", "").replace(" ", "");

        boolean sendSmsResult = naverSmsSenderComponent.sendSms(
                new NaverSmsSenderComponent.SendSmsInputVo(
                        "SMS",
                        countryCode,
                        phoneNumber,
                        "[Springboot Mvc Project Template - 비밀번호 찾기] 인증번호 [" + verificationCode + "]"
                )
        );

        if (!sendSmsResult) {
            throw new RuntimeException();
        }

        return new MyServiceTkAuthController.SendPhoneVerificationForFindPasswordOutputVo(
                memberFindPasswordPhoneNumberVerificationData.uid,
                memberFindPasswordPhoneNumberVerificationData.verificationExpireWhen
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
        );
    }


    ////
    @Override
    public void checkPhoneVerificationForFindPassword(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        Optional<Db1_RaillyLinkerCompany_TotalAuthFindPwWithPhoneVerification> phoneNumberVerificationOpt =
                db1RaillyLinkerCompanyTotalAuthFindPwWithPhoneVerificationRepository
                        .findByUidAndRowDeleteDateStr(verificationUid, "/");

        if (phoneNumberVerificationOpt.isEmpty()) { // 해당 이메일 검증을 요청한적이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthFindPwWithPhoneVerification phoneNumberVerification = phoneNumberVerificationOpt.get();

        if (!phoneNumberVerification.phoneNumber.equals(phoneNumber)) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(phoneNumberVerification.verificationExpireWhen)) {
            // 만료됨
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        // 입력 코드와 발급된 코드와의 매칭
        boolean codeMatched = phoneNumberVerification.verificationSecret.equals(verificationCode);

        if (codeMatched) {
            // 코드 일치
            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else {
            // 코드 불일치
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void findPasswordWithPhoneNumber(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.FindPasswordWithPhoneNumberInputVo inputVo
    ) {
        Optional<Db1_RaillyLinkerCompany_TotalAuthFindPwWithPhoneVerification> phoneNumberVerificationOpt =
                db1RaillyLinkerCompanyTotalAuthFindPwWithPhoneVerificationRepository
                        .findByUidAndRowDeleteDateStr(inputVo.verificationUid(), "/");

        if (phoneNumberVerificationOpt.isEmpty()) { // 해당 이메일 검증을 요청한적이 없음
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        Db1_RaillyLinkerCompany_TotalAuthFindPwWithPhoneVerification phoneNumberVerification = phoneNumberVerificationOpt.get();

        if (!phoneNumberVerification.phoneNumber.equals(inputVo.phoneNumber())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return;
        }

        if (LocalDateTime.now().isAfter(phoneNumberVerification.verificationExpireWhen)) {
            // 만료됨
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "2");
            return;
        }

        // 입력 코드와 발급된 코드와의 매칭
        if (phoneNumberVerification.verificationSecret.equals(inputVo.verificationCode())) { // 코드 일치
            Optional<Db1_RaillyLinkerCompany_TotalAuthMemberPhone> memberPhoneOpt =
                    db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository
                            .findByPhoneNumberAndRowDeleteDateStr(inputVo.phoneNumber(), "/");

            if (memberPhoneOpt.isEmpty()) {
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "4");
                return;
            }

            Db1_RaillyLinkerCompany_TotalAuthMemberPhone memberPhone = memberPhoneOpt.get();

            // 랜덤 비번 생성 후 세팅
            String newPassword = String.format("%09d", new Random().nextInt(999999999)); // 랜덤 9자리 숫자
            memberPhone.totalAuthMember.accountPassword = passwordEncoder.encode(newPassword); // 비밀번호 암호화
            db1RaillyLinkerCompanyTotalAuthMemberRepository.save(memberPhone.totalAuthMember);

            String[] phoneNumberSplit = inputVo.phoneNumber().split("\\)"); // ["82", "010-0000-0000"]

            // 국가 코드 (ex : 82)
            String countryCode = phoneNumberSplit[0];

            // 전화번호 (ex : "01000000000")
            String phoneNumber = phoneNumberSplit[1].replace("-", "").replace(" ", "");

            boolean sendSmsResult = naverSmsSenderComponent.sendSms(
                    new NaverSmsSenderComponent.SendSmsInputVo(
                            "SMS",
                            countryCode,
                            phoneNumber,
                            "[Springboot Mvc Project Template - 새 비밀번호] " + newPassword
                    )
            );

            if (!sendSmsResult) {
                throw new RuntimeException();
            }

            // 확인 완료된 검증 요청 정보 삭제
            phoneNumberVerification.rowDeleteDateStr =
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));
            db1RaillyLinkerCompanyTotalAuthFindPwWithPhoneVerificationRepository.save(phoneNumberVerification);

            // 모든 토큰 비활성화 처리
            Iterable<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> tokenInfoList =
                    db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository
                            .findAllByTotalAuthMemberAndLogoutDateAndRowDeleteDateStr(
                                    memberPhone.totalAuthMember,
                                    null,
                                    "/"
                            );

            for (Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory tokenInfo : tokenInfoList) {
                tokenInfo.logoutDate = LocalDateTime.now();
                db1RaillyLinkerCompanyTotalAuthLogInTokenHistoryRepository.save(tokenInfo);

                // 토큰 만료처리
                String tokenType = tokenInfo.tokenType;
                String accessToken = tokenInfo.accessToken;

                Long accessTokenExpireRemainSeconds = null;
                if ("Bearer".equals(tokenType)) {
                    accessTokenExpireRemainSeconds = jwtTokenUtil.getRemainSeconds(accessToken);
                }

                try {
                    redis1MapTotalAuthForceExpireAuthorizationSet.saveKeyValue(
                            tokenType + "_" + accessToken,
                            new Redis1_Map_TotalAuthForceExpireAuthorizationSet.ValueVo(),
                            accessTokenExpireRemainSeconds != null ? accessTokenExpireRemainSeconds * 1000 : null
                    );
                } catch (Exception e) {
                    classLogger.error("error ", e);
                }
            }

            httpServletResponse.setStatus(HttpStatus.OK.value());
        } else { // 코드 불일치
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "3");
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.GetMyEmailListOutputVo getMyEmailList(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        Db1_RaillyLinkerCompany_TotalAuthMember memberData =
                db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(memberUid, "/").get();

        List<Db1_RaillyLinkerCompany_TotalAuthMemberEmail> emailEntityList =
                db1RaillyLinkerCompanyTotalAuthMemberEmailRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(
                        memberData,
                        "/"
                );
        List<MyServiceTkAuthController.GetMyEmailListOutputVo.EmailInfo> emailList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberEmail emailEntity : emailEntityList) {
            emailList.add(new MyServiceTkAuthController.GetMyEmailListOutputVo.EmailInfo(
                    emailEntity.uid,
                    emailEntity.emailAddress,
                    emailEntity.uid.equals(
                            memberData.frontTotalAuthMemberEmail != null
                                    ? memberData.frontTotalAuthMemberEmail.uid
                                    : null
                    )
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.GetMyEmailListOutputVo(emailList);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.GetMyPhoneNumberListOutputVo getMyPhoneNumberList(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        Db1_RaillyLinkerCompany_TotalAuthMember memberData =
                db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(memberUid, "/").get();

        List<Db1_RaillyLinkerCompany_TotalAuthMemberPhone> phoneEntityList =
                db1RaillyLinkerCompanyTotalAuthMemberPhoneRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(
                        memberData,
                        "/"
                );
        List<MyServiceTkAuthController.GetMyPhoneNumberListOutputVo.PhoneInfo> phoneNumberList = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberPhone phoneEntity : phoneEntityList) {
            phoneNumberList.add(new MyServiceTkAuthController.GetMyPhoneNumberListOutputVo.PhoneInfo(
                    phoneEntity.uid,
                    phoneEntity.phoneNumber,
                    phoneEntity.uid.equals(
                            memberData.frontTotalAuthMemberPhone != null
                                    ? memberData.frontTotalAuthMemberPhone.uid
                                    : null
                    )
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.GetMyPhoneNumberListOutputVo(phoneNumberList);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.GetMyOauth2ListOutputVo getMyOauth2List(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        Long memberUid = jwtTokenUtil.getMemberUid(
                authorization.split(" ")[1].trim(),
                AUTH_JWT_CLAIMS_AES256_INITIALIZATION_VECTOR,
                AUTH_JWT_CLAIMS_AES256_ENCRYPTION_KEY
        );

        Db1_RaillyLinkerCompany_TotalAuthMember memberData =
                db1RaillyLinkerCompanyTotalAuthMemberRepository.findByUidAndRowDeleteDateStr(memberUid, "/").get();

        List<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login> oAuth2EntityList =
                db1RaillyLinkerCompanyTotalAuthMemberOauth2LoginRepository.findAllByTotalAuthMemberAndRowDeleteDateStr(
                        memberData,
                        "/"
                );
        List<MyServiceTkAuthController.GetMyOauth2ListOutputVo.OAuth2Info> myOAuth2List = new ArrayList<>();
        for (Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login oAuth2Entity : oAuth2EntityList) {
            myOAuth2List.add(new MyServiceTkAuthController.GetMyOauth2ListOutputVo.OAuth2Info(
                    oAuth2Entity.uid,
                    oAuth2Entity.oauth2TypeCode.intValue(),
                    oAuth2Entity.oauth2Id
            ));
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkAuthController.GetMyOauth2ListOutputVo(myOAuth2List);
    }


    // todo
    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.SendEmailVerificationForAddNewEmailOutputVo sendEmailVerificationForAddNewEmail(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendEmailVerificationForAddNewEmailInputVo inputVo,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        return null;
    }


    ////
    @Override
    public void checkEmailVerificationForAddNewEmail(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @org.jetbrains.annotations.NotNull
            String email,
            @org.jetbrains.annotations.NotNull
            String verificationCode,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.AddNewEmailOutputVo addNewEmail(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewEmailInputVo inputVo,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        return null;
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteMyEmail(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            Long emailUid,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.SendPhoneVerificationForAddNewPhoneNumberOutputVo sendPhoneVerificationForAddNewPhoneNumber(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendPhoneVerificationForAddNewPhoneNumberInputVo inputVo,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        return null;
    }


    ////
    @Override
    public void checkPhoneVerificationForAddNewPhoneNumber(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @org.jetbrains.annotations.NotNull
            String verificationCode,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.AddNewPhoneNumberOutputVo addNewPhoneNumber(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewPhoneNumberInputVo inputVo,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        return null;
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteMyPhoneNumber(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            Long phoneUid,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void addNewOauth2WithAccessToken(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewOauth2WithAccessTokenInputVo inputVo,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void addNewOauth2WithIdToken(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewOauth2WithIdTokenInputVo inputVo,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteMyOauth2(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            Long oAuth2Uid,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void withdrawalMembership(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {

    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.GetMyProfileListOutputVo getMyProfileList(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        return null;
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.GetMyFrontProfileOutputVo getMyFrontProfile(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        return null;
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void setMyFrontProfile(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization,
            @Nullable @org.jetbrains.annotations.Nullable
            Long profileUid
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void deleteMyProfile(
            @org.jetbrains.annotations.NotNull
            String authorization,
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            Long profileUid
    ) {

    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.AddNewProfileOutputVo addNewProfile(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization,
            @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewProfileInputVo inputVo
    ) {
        return null;
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> downloadProfileFile(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String fileName
    ) {
        return null;
    }


    ////
    @Override
    public MyServiceTkAuthController.GetMyFrontEmailOutputVo getMyFrontEmail(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        return null;
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void setMyFrontEmail(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization,
            @Nullable @org.jetbrains.annotations.Nullable
            Long emailUid
    ) {

    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.GetMyFrontPhoneNumberOutputVo getMyFrontPhoneNumber(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization
    ) {
        return null;
    }


    ////
    @CustomTransactional(transactionManagerBeanNameList = {Db1MainConfig.TRANSACTION_NAME})
    @Override
    public void setMyFrontPhoneNumber(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @org.jetbrains.annotations.NotNull
            String authorization,
            @Nullable @org.jetbrains.annotations.Nullable
            Long phoneNumberUid
    ) {

    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkAuthController.SelectAllRedisKeyValueSampleOutputVo selectAllRedisKeyValueSample(
            @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return null;
    }
}
