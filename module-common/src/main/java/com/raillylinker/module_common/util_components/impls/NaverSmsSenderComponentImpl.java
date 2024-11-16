package com.raillylinker.module_common.util_components.impls;

import com.raillylinker.module_common.util_components.NaverSmsSenderComponent;
import com.raillylinker.module_retrofit2.retrofit2_classes.RepositoryNetworkRetrofit2;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// [Naver SMS 발송 유틸 객체]
@Component
public class NaverSmsSenderComponentImpl implements NaverSmsSenderComponent {
    public NaverSmsSenderComponentImpl(
            @Value("${custom-config.naverSms.access-key}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accessKey,
            @Value("${custom-config.naverSms.secret-key}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String secretKey,
            @Value("${custom-config.naverSms.service-id}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String serviceId,
            @Value("${custom-config.naverSms.phone-number}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Value("${custom-config.naverSms.alim-talk-service-id}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String alimTalkServiceId
    ) throws InterruptedException {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.serviceId = serviceId;
        this.phoneNumber = phoneNumber;
        this.alimTalkServiceId = alimTalkServiceId;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String accessKey;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String secretKey;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String serviceId;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String phoneNumber;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String alimTalkServiceId;

    // Retrofit2 요청 객체
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final RepositoryNetworkRetrofit2 networkRetrofit2 = RepositoryNetworkRetrofit2.getInstance();


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Boolean sendSms(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendSmsInputVo inputVo
    ) {
        // todo
        return null;
    }


    // (알림톡 보내기)
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SendAlimTalkOutputVo sendAlimTalk(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendAlimTalkInputVo inputVo
    ) {
        // todo
        return null;
    }
}
