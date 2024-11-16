package com.raillylinker.module_common.util_components;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

// Naver SMS 발송 유틸 객체
public interface NaverSmsSenderComponent {
    // (SMS 보내기)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean sendSms(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendSmsInputVo inputVo
    );

    // (알림톡 보내기)
    @Nullable
    @org.jetbrains.annotations.Nullable
    SendAlimTalkOutputVo sendAlimTalk(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendAlimTalkInputVo inputVo
    );


    // ---------------------------------------------------------------------------------------------
    // <중첩 클래스 공간>
    // SendSmsInputVo 클래스
    record SendSmsInputVo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String messageType,       // 메세지 타입 (SMS, LMS, MMS)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String countryCode,       // 국가 코드 (ex : 82)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,       // 전화번호 (ex : 01000000000)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String content            // 문자 본문
    ) {
    }

    // SendAlimTalkInputVo 클래스
    record SendAlimTalkInputVo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String plusFriendId,                  // 카카오톡 채널명 ((구)플러스친구 아이디)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String templateCode,                  // 템플릿 코드
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull MessageVo> messages              // 메시지(최대 100 개)
    ) {
        // MessageVo 클래스
        record MessageVo(
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String countryCode,                // 국가 코드 (ex : 82)
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String phoneNumber,                // 전화번호 (ex : 01000000000)
                @Nullable @org.jetbrains.annotations.Nullable
                String title,                      // 알림톡 강조표시 내용, 강조 표기 유형의 템플릿에서만 사용 가능
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String content,                    // 문자 본문 (템플릿에 등록한 문장과 동일해야합니다.)
                @Nullable @org.jetbrains.annotations.Nullable
                String headerContent,              // 알림톡 헤더 내용, 아이템 리스트 유형의 템플릿에서만 사용 가능
                @Nullable @org.jetbrains.annotations.Nullable
                ItemHighlightVo itemHighlight,     // 아이템 하이라이트, 아이템 리스트 유형의 템플릿에서만 사용 가능
                @Nullable @org.jetbrains.annotations.Nullable
                ItemVo item,                       // 아이템 리스트, 아이템리스트 유형의 템플릿에서만 사용 가능
                @Nullable @org.jetbrains.annotations.Nullable
                List<@Valid @NotNull ButtonVo> buttons,            // 알림톡 메시지 버튼
                @Nullable @org.jetbrains.annotations.Nullable
                Boolean useSmsFailover,            // SMS Failover 사용 여부
                @Nullable @org.jetbrains.annotations.Nullable
                FailOverConfigVo failoverConfig    // Failover 설정
        ) {
        }

        // ItemHighlightVo 클래스
        record ItemHighlightVo(
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String title,       // 아이템 하이라이트 제목, 아이템 리스트 유형의 템플릿에서만 사용 가능
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String description  // 아이템 하이라이트 설명, 아이템 리스트 유형의 템플릿에서만 사용 가능
        ) {
        }

        // ItemVo 클래스
        record ItemVo(
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                List<@Valid @NotNull ListItemVo> list,       // 아이템 리스트, 아이템리스트 유형의 템플릿에서만 사용 가능
                @Nullable @org.jetbrains.annotations.Nullable
                SummaryVo summary            // 아이템 요약 정보, 아이템리스트 유형의 템플릿에서만 사용 가능
        ) {
            // ListItemVo 클래스
            record ListItemVo(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String title,              // 아이템 리스트 제목, 아이템리스트 유형의 템플릿에서만 사용 가능
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String description         // 아이템 리스트 설명, 아이템리스트 유형의 템플릿에서만 사용 가능
            ) {
            }

            // SummaryVo 클래스
            record SummaryVo(
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String title,              // 아이템 요약 제목, 아이템리스트 유형의 템플릿에서만 사용 가능
                    @Valid @NotNull @org.jetbrains.annotations.NotNull
                    String description         // 아이템 요약 설명, 아이템리스트 유형의 템플릿에서만 사용 가능
            ) {
            }
        }

        // ButtonVo 클래스
        record ButtonVo(
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String type,              // 버튼 Type
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String name,              // 버튼명
                @Nullable @org.jetbrains.annotations.Nullable
                String linkMobile,        // 모바일 링크
                @Nullable @org.jetbrains.annotations.Nullable
                String linkPc,            // PC 링크
                @Nullable @org.jetbrains.annotations.Nullable
                String schemeIos,         // iOS Scheme 링크
                @Nullable @org.jetbrains.annotations.Nullable
                String schemeAndroid      // Android Scheme 링크
        ) {
        }

        // FailOverConfigVo 클래스
        record FailOverConfigVo(
                @Nullable @org.jetbrains.annotations.Nullable
                String type,              // Failover SMS 메시지 Type
                @Nullable @org.jetbrains.annotations.Nullable
                String from,              // Failover SMS 발신번호
                @Nullable @org.jetbrains.annotations.Nullable
                String subject,           // Failover SMS 제목
                @Nullable @org.jetbrains.annotations.Nullable
                String content            // Failover SMS 내용
        ) {
        }
    }

    // SendAlimTalkOutputVo 클래스
    record SendAlimTalkOutputVo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull MessageResultVo> messageResults // 메시지 전송 결과
    ) {
        // MessageResultVo 클래스
        record MessageResultVo(
                @Nullable @org.jetbrains.annotations.Nullable
                String countryCode,           // 수신자 국가번호, default: 82
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String to,                    // 수신자 번호
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestStatusCode,     // 발송요청 상태 코드
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String requestStatusDesc      // 발송 요청 상태 내용
        ) {
        }
    }
}
