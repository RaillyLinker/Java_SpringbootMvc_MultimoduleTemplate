package com.raillylinker.module_common.util_components.impls;

import com.raillylinker.module_common.util_components.NaverSmsSenderComponent;
import com.raillylinker.module_retrofit2.retrofit2_classes.RepositoryNetworkRetrofit2;
import com.raillylinker.module_retrofit2.retrofit2_classes.request_apis.SensApigwNtrussComRequestApi;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        long time = System.currentTimeMillis();

        // HMAC-SHA256 계산
        String base64EncodedHmac;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(("POST" +
                " " +
                "/sms/v2/services/" + serviceId + "/messages" +
                "\n" +
                time +
                "\n" +
                accessKey).getBytes(StandardCharsets.UTF_8));

        base64EncodedHmac = Base64.getEncoder().encodeToString(hmacBytes);

        // API 호출
        SensApigwNtrussComRequestApi.PostSmsV2ServicesNaverSmsServiceIdMessagesInputVO messageInputVo =
                new SensApigwNtrussComRequestApi.PostSmsV2ServicesNaverSmsServiceIdMessagesInputVO(
                        inputVo.messageType(),
                        null,
                        inputVo.countryCode(),
                        phoneNumber,
                        null,
                        inputVo.content(),
                        List.of(new SensApigwNtrussComRequestApi.PostSmsV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo(
                                inputVo.phoneNumber(),
                                null,
                                null
                        )),
                        null,
                        null,
                        null
                );

        Response<SensApigwNtrussComRequestApi.PostSmsV2ServicesNaverSmsServiceIdMessagesOutputVO> responseObj =
                networkRetrofit2.sensApigwNtrussComRequestApi.postSmsV2ServicesNaverSmsServiceIdMessages(
                                serviceId,
                                String.valueOf(time),
                                accessKey,
                                base64EncodedHmac,
                                messageInputVo
                        )
                        .execute();

        return responseObj.code() == 202;
    }


    // (알림톡 보내기)
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SendAlimTalkOutputVo sendAlimTalk(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendAlimTalkInputVo inputVo
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        if (inputVo.messages().size() > 100) {
            return null;
        }

        long time = System.currentTimeMillis();
        List<SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo> messageVoList = new ArrayList<>();

        for (SendAlimTalkInputVo.MessageVo message : inputVo.messages()) {
            SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo messageVo =
                    new SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo(
                            message.countryCode(),
                            message.phoneNumber(),
                            message.title(),
                            message.content(),
                            message.headerContent(),
                            message.itemHighlight() == null ? null :
                                    new SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo.ItemHighlightVo(
                                            message.itemHighlight().title(),
                                            message.itemHighlight().description()
                                    ),
                            message.item() == null ? null :
                                    new SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo.ItemVo(
                                            message.item().list().stream().map(item ->
                                                    new SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo.ItemVo.ListItemVo(
                                                            item.title(),
                                                            item.description()
                                                    )
                                            ).collect(Collectors.toList()),
                                            message.item().summary() == null ? null :
                                                    new SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo.ItemVo.SummaryVo(
                                                            message.item().summary().title(),
                                                            message.item().summary().description()
                                                    )
                                    ),
                            message.buttons() == null ? null :
                                    message.buttons().stream().map(button ->
                                            new SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo.ButtonVo(
                                                    button.type(),
                                                    button.name(),
                                                    button.linkMobile(),
                                                    button.linkPc(),
                                                    button.schemeIos(),
                                                    button.schemeAndroid()
                                            )
                                    ).collect(Collectors.toList()),
                            message.useSmsFailover(),
                            message.failoverConfig() == null ? null :
                                    new SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO.MessageVo.FailOverConfigVo(
                                            message.failoverConfig().type(),
                                            message.failoverConfig().from(),
                                            message.failoverConfig().subject(),
                                            message.failoverConfig().content()
                                    )
                    );
            messageVoList.add(messageVo);
        }

        String base64EncodedHmac;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(("POST" +
                " " +
                "/alimtalk/v2/services/" + alimTalkServiceId + "/messages" +
                "\n" +
                time +
                "\n" +
                accessKey).getBytes(StandardCharsets.UTF_8));

        base64EncodedHmac = Base64.getEncoder().encodeToString(hmacBytes);

        Response<SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesOutputVO> responseObj = networkRetrofit2.sensApigwNtrussComRequestApi
                .postAlimtalkV2ServicesNaverSmsServiceIdMessages(alimTalkServiceId,
                        String.valueOf(time),
                        accessKey,
                        base64EncodedHmac,
                        new SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesInputVO(
                                inputVo.plusFriendId(),
                                inputVo.templateCode(),
                                messageVoList,
                                null,
                                null
                        )
                ).execute();

        if (responseObj.code() == 202) {
            SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesOutputVO responseBody = responseObj.body();
            List<NaverSmsSenderComponent.SendAlimTalkOutputVo.MessageResultVo> messageResults = new ArrayList<>();

            for (SensApigwNtrussComRequestApi.PostAlimtalkV2ServicesNaverSmsServiceIdMessagesOutputVO.MessageVo message : Objects.requireNonNull(responseBody).messages()) {
                messageResults.add(
                        new NaverSmsSenderComponent.SendAlimTalkOutputVo.MessageResultVo(
                                message.countryCode(),
                                message.to(),
                                message.requestStatusCode(),
                                message.requestStatusDesc()
                        )
                );
            }
            return new NaverSmsSenderComponent.SendAlimTalkOutputVo(messageResults);
        } else {
            return null;
        }
    }
}
