package com.raillylinker.module_retrofit2.retrofit2_classes.request_apis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface OpenapiNaverComRequestApi {
    // [Naver Oauth2 AccessToken 요청]
    @GET("/v1/nid/me")
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Call<@org.jetbrains.annotations.Nullable GetV1NidMeOutputVO> getV1NidMe(
            @Header("Authorization")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String authorization
    );

    // (naver profile 정보 요청 API 응답 객체)
    // https://openapi.naver.com/v1/nid/me
    record GetV1NidMeOutputVO(
            @SerializedName("resultcode")
            @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String resultCode,
            @SerializedName("message")
            @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message,
            @SerializedName("response")
            @Expose
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ResponseVo response
    ) {
        public record ResponseVo(
                @SerializedName("id")
                @Expose
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String id,
                @SerializedName("nickname")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String nickname,
                @SerializedName("name")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String name,
                @SerializedName("email")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String email,
                @SerializedName("gender")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String gender,
                @SerializedName("age")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String age,
                @SerializedName("birthday")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String birthday,
                @SerializedName("profile_image")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String profileImage,
                @SerializedName("birthyear")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String birthyear,
                @SerializedName("mobile")
                @Expose
                @Nullable @org.jetbrains.annotations.Nullable
                String mobile
        ) {
        }
    }
}
