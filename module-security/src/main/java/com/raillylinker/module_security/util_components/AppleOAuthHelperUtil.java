package com.raillylinker.module_security.util_components;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

// [Apple OAuth2 검증 관련 유틸]
public interface AppleOAuthHelperUtil {
    // 애플 Id Token 검증 함수 - 검증이 완료되었다면 프로필 정보가 반환되고, 검증되지 않는다면 null 반환
    @Nullable
    @org.jetbrains.annotations.Nullable
    AppleProfileData getAppleMemberData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String idToken
    );

    record AppleProfileData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String snsId,
            @Nullable @org.jetbrains.annotations.Nullable
            String email
    ) {
    }
}