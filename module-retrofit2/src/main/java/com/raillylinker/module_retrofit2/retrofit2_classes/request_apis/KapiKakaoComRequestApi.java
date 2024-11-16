package com.raillylinker.module_retrofit2.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface KapiKakaoComRequestApi {
    // [KakaoTalk Oauth2 AccessToken 요청]
    @GET("v2/user/me")
    @Headers("Connection: close")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<@Valid @NotNull GetV2UserMeOutputVO> getV2UserMe(
            @Header("Authorization")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );

    // (kakao profile 정보 요청 API 응답 객체)
    // https://kapi.kakao.com/v2/user/me
    record GetV2UserMeOutputVO(
            @SerializedName("id")
            @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long id, // 회원번호
            @SerializedName("connected_at")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            String connectedAt, // 연결 완료된 시각
            @SerializedName("kakao_account")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            KakaoAccountVo kakaoAccount // 카카오계정 정보
    ) {
        public record KakaoAccountVo(
                @SerializedName("profile_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean profileNeedsAgreement,
                @SerializedName("profile_nickname_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean profileNicknameNeedsAgreement,
                @SerializedName("profile_image_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean profileImageNeedsAgreement,
                @SerializedName("profile")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                ProfileVo profile,
                @SerializedName("name_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean nameNeedsAgreement,
                @SerializedName("name")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String name,
                @SerializedName("email_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean emailNeedsAgreement,
                @SerializedName("is_email_valid")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean isEmailValid,
                @SerializedName("is_email_verified")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean isEmailVerified,
                @SerializedName("email")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String email,
                @SerializedName("age_range_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean ageRangeNeedsAgreement,
                @SerializedName("age_range")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String ageRange,
                @SerializedName("birthyear_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean birthyearNeedsAgreement,
                @SerializedName("birthyear")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String birthyear,
                @SerializedName("birthday_type")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String birthdayType,
                @SerializedName("gender_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean genderNeedsAgreement,
                @SerializedName("gender")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String gender,
                @SerializedName("phone_number_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean phoneNumberNeedsAgreement,
                @SerializedName("phone_number")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String phoneNumber,
                @SerializedName("ci_needs_agreement")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean ciNeedsAgreement,
                @SerializedName("ci")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String ci,
                @SerializedName("ci_authenticated_at")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String ciAuthenticatedAt
        ) {
            public record ProfileVo(
                    @SerializedName("nickname")
                    @Expose
                    @Nullable @org.jetbrains.annotations.Nullable
                    String nickname,
                    @SerializedName("thumbnail_image_url")
                    @Expose
                    @Nullable @org.jetbrains.annotations.Nullable
                    String thumbnailImageUrl,
                    @SerializedName("profile_image_url")
                    @Expose
                    @Nullable @org.jetbrains.annotations.Nullable
                    String profileImageUrl,
                    @SerializedName("is_default_image")
                    @Expose
                    @Nullable @org.jetbrains.annotations.Nullable
                    String isDefaultImage
            ) {
            }
        }
    }
}
