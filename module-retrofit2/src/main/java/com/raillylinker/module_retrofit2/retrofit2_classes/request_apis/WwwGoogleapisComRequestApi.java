package com.raillylinker.module_retrofit2.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

// (한 주소에 대한 API 요청명세)
// 사용법은 아래 기본 사용 샘플을 참고하여 추상함수를 작성하여 사용
public interface WwwGoogleapisComRequestApi {
    // [Google Oauth2 AccessToken 요청]
    @GET("/oauth2/v1/userinfo")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<@org.jetbrains.annotations.Nullable GetOauth2V1UserInfoOutputVO> getOauth2V1UserInfo(
            @Header("Authorization")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );

    record GetOauth2V1UserInfoOutputVO(
            @SerializedName("id")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            String id,
            @SerializedName("email")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            String email,
            @SerializedName("verified_email")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            Boolean verifiedEmail,
            @SerializedName("name")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            String name,
            @SerializedName("given_name")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            String givenName,
            @SerializedName("family_name")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            String familyName,
            @SerializedName("picture")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            String picture,
            @SerializedName("locale")
            @Expose
            @Nullable @org.jetbrains.annotations.Nullable
            String locale
    ) {
    }
}
