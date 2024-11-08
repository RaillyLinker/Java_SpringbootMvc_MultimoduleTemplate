package com.raillylinker.module_socket.services;

import com.raillylinker.module_socket.controllers.WebSocketStompController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface WebSocketStompService {
    // (/test 로 받아서 /topic 토픽을 구독중인 모든 클라이언트에 메시지 전달)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    WebSocketStompController.TopicVo api1SendToTopicTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            WebSocketStompController.Api1SendToTopicTestInputVo inputVo
    );
}
