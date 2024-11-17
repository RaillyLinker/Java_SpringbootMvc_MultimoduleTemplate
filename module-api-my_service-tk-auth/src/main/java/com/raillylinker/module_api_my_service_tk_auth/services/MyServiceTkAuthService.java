package com.raillylinker.module_api_my_service_tk_auth.services;


import com.raillylinker.module_api_my_service_tk_auth.controllers.MyServiceTkAuthController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface MyServiceTkAuthService {
    // (비 로그인 접속 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String noLoggedInAccessTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (로그인 진입 테스트 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String loggedInAccessTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (ADMIN 권한 진입 테스트 <'ADMIN'>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String adminAccessTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (Developer 권한 진입 테스트 <'ADMIN' or 'Developer'>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String developerAccessTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (특정 회원의 발행된 Access 토큰 만료 처리)
    void doExpireAccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long memberUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.DoExpireAccessTokenInputVo inputVo
    );


    ////
    // (계정 비밀번호 로그인)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.LoginOutputVo loginWithPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.LoginWithPasswordInputVo inputVo
    );


    ////
    // (OAuth2 Code 로 OAuth2 AccessToken 발급)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetOAuth2AccessTokenOutputVo getOAuth2AccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse var1,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer var2,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String var3
    );


    ////
    // (OAuth2 로그인 (Access Token))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.LoginOutputVo loginWithOAuth2AccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.LoginWithOAuth2AccessTokenInputVo inputVo
    ) throws IOException;


    ////
    // (OAuth2 로그인 (ID Token))
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.LoginOutputVo loginWithOAuth2IdToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.LoginWithOAuth2IdTokenInputVo inputVo
    );


    ////
    // (로그아웃 처리 <>)
    void logout(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (토큰 재발급 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.LoginOutputVo reissueJwt(
            @Nullable @org.jetbrains.annotations.Nullable
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.ReissueJwtInputVo inputVo,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (멤버의 현재 발행된 모든 토큰 비활성화 (= 모든 기기에서 로그아웃) <>)
    void deleteAllJwtOfAMember(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );


    ////
    // (회원 정보 가져오기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetMemberInfoOutputVo getMemberInfo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (아이디 중복 검사)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.CheckIdDuplicateOutputVo checkIdDuplicate(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    );


    ////
    // (아이디 수정하기 <>)
    void updateId(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    );


    ////
    // (테스트 회원 회원가입)
    void joinTheMembershipForTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.JoinTheMembershipForTestInputVo inputVo
    ) throws IOException;


    ////
    // (이메일 회원가입 본인 인증 이메일 발송)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.SendEmailVerificationForJoinOutputVo sendEmailVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendEmailVerificationForJoinInputVo inputVo
    ) throws MessagingException, Exception, MessagingException;


    ////
    // (이메일 회원가입 본인 확인 이메일에서 받은 코드 검증하기)
    void checkEmailVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    );


    ////
    // (이메일 회원가입)
    void joinTheMembershipWithEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse HttpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.JoinTheMembershipWithEmailInputVo inputVo
    ) throws IOException;


    ////
    // (전화번호 회원가입 본인 인증 문자 발송)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.SendPhoneVerificationForJoinOutputVo sendPhoneVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendPhoneVerificationForJoinInputVo inputVo
    );


    ////
    // (전화번호 회원가입 본인 확인 문자에서 받은 코드 검증하기)
    void checkPhoneVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    );


    ////
    // (전화번호 회원가입)
    void joinTheMembershipWithPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.JoinTheMembershipWithPhoneNumberInputVo inputVo
    );


    ////
    // (OAuth2 AccessToken 으로 회원가입 검증)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.CheckOauth2AccessTokenVerificationForJoinOutputVo checkOauth2AccessTokenVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.CheckOauth2AccessTokenVerificationForJoinInputVo inputVo
    );


    ////
    // (OAuth2 IdToken 으로 회원가입 검증)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.CheckOauth2IdTokenVerificationForJoinOutputVo checkOauth2IdTokenVerificationForJoin(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.CheckOauth2IdTokenVerificationForJoinInputVo inputVo
    );


    ////
    // (OAuth2 회원가입)
    void joinTheMembershipWithOauth2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.JoinTheMembershipWithOauth2InputVo inputVo);


    ////
    // (계정 비밀번호 변경 <>)
    void updateAccountPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.UpdateAccountPasswordInputVo inputVo
    );


    ////
    // (이메일 비밀번호 찾기 본인 인증 이메일 발송)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.SendEmailVerificationForFindPasswordOutputVo sendEmailVerificationForFindPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendEmailVerificationForFindPasswordInputVo inputVo
    );


    ////
    // (이메일 비밀번호 찾기 본인 확인 이메일에서 받은 코드 검증하기)
    void checkEmailVerificationForFindPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    );


    ////
    // (이메일 비밀번호 찾기 완료)
    void findPasswordWithEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.FindPasswordWithEmailInputVo inputVo
    );


    ////
    // (전화번호 비밀번호 찾기 본인 인증 문자 발송)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.SendPhoneVerificationForFindPasswordOutputVo sendPhoneVerificationForFindPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendPhoneVerificationForFindPasswordInputVo inputVo
    );


    ////
    // (전화번호 비밀번호 찾기 본인 확인 문자에서 받은 코드 검증하기)
    void checkPhoneVerificationForFindPassword(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    );


    ////
    // (전화번호 비밀번호 찾기 완료)
    void findPasswordWithPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.FindPasswordWithPhoneNumberInputVo inputVo
    );


    ////
    // (내 이메일 리스트 가져오기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetMyEmailListOutputVo getMyEmailList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 전화번호 리스트 가져오기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetMyPhoneNumberListOutputVo getMyPhoneNumberList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 OAuth2 로그인 리스트 가져오기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetMyOauth2ListOutputVo getMyOauth2List(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse HttpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (이메일 추가하기 본인 인증 이메일 발송 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.SendEmailVerificationForAddNewEmailOutputVo sendEmailVerificationForAddNewEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendEmailVerificationForAddNewEmailInputVo inputVo,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (이메일 추가하기 본인 확인 이메일에서 받은 코드 검증하기 <>)
    void checkEmailVerificationForAddNewEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (이메일 추가하기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.AddNewEmailOutputVo addNewEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewEmailInputVo inputVo,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 이메일 제거하기 <>)
    void deleteMyEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long emailUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (전화번호 추가하기 본인 인증 문자 발송 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.SendPhoneVerificationForAddNewPhoneNumberOutputVo sendPhoneVerificationForAddNewPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.SendPhoneVerificationForAddNewPhoneNumberInputVo inputVo,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (전화번호 추가하기 본인 확인 문자에서 받은 코드 검증하기 <>)
    void checkPhoneVerificationForAddNewPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (전화번호 추가하기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.AddNewPhoneNumberOutputVo addNewPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewPhoneNumberInputVo inputVo,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 전화번호 제거하기 <>)
    void deleteMyPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long phoneUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (OAuth2 추가하기 (Access Token) <>)
    void addNewOauth2WithAccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewOauth2WithAccessTokenInputVo inputVo,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (OAuth2 추가하기 (Id Token) <>)
    void addNewOauth2WithIdToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewOauth2WithIdTokenInputVo inputVo,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 OAuth2 제거하기 <>)
    void deleteMyOauth2(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long oAuth2Uid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (회원탈퇴 <>)
    void withdrawalMembership(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 Profile 이미지 정보 리스트 가져오기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetMyProfileListOutputVo getMyProfileList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 대표 Profile 이미지 정보 가져오기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetMyFrontProfileOutputVo getMyFrontProfile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 대표 프로필 설정하기 <>)
    void setMyFrontProfile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Nullable @org.jetbrains.annotations.Nullable
            Long profileUid
    );


    ////
    // (내 프로필 삭제 <>)
    void deleteMyProfile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long profileUid
    );


    ////
    // (내 프로필 이미지 추가 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.AddNewProfileOutputVo addNewProfile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthController.AddNewProfileInputVo inputVo
    );


    ////
    // (by_product_files/member/profile 폴더에서 파일 다운받기)
    ResponseEntity<Resource> downloadProfileFile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    );


    ////
    // (내 대표 이메일 정보 가져오기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetMyFrontEmailOutputVo getMyFrontEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 대표 이메일 설정하기 <>)
    void setMyFrontEmail(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Nullable @org.jetbrains.annotations.Nullable
            Long emailUid
    );


    ////
    // (내 대표 전화번호 정보 가져오기 <>)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.GetMyFrontPhoneNumberOutputVo getMyFrontPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );


    ////
    // (내 대표 전화번호 설정하기 <>)
    void setMyFrontPhoneNumber(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization,
            @Nullable @org.jetbrains.annotations.Nullable
            Long phoneNumberUid
    );


    ////
    // (Redis Key-Value 모두 조회 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkAuthController.SelectAllRedisKeyValueSampleOutputVo selectAllRedisKeyValueSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    );
}
