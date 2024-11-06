package com.raillylinker.module_kafka.kafka_components.producers;

import com.raillylinker.module_kafka.configurations.kafka_configs.Kafka1MainConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


// Kafka 메시지를 발송하는 프로듀서 컴포넌트
@Component
public class Kafka1MainProducer {
    public Kafka1MainProducer(@Qualifier(Kafka1MainConfig.PRODUCER_BEAN_NAME) KafkaTemplate<String, Object> kafka1MainProducerTemplate) {
        this.kafka1MainProducerTemplate = kafka1MainProducerTemplate;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final KafkaTemplate<String, Object> kafka1MainProducerTemplate;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(Kafka1MainProducer.class);


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    // (testTopic1 에 메시지 발송)
    public void sendMessageToTestTopic1(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendMessageToTestTopic1InputVo message
    ) {
        // kafkaProducer1 에 토픽 메세지 발행
        kafka1MainProducerTemplate.send("testTopic1", message);
    }

    // 내포된 클래스 (TestTopic1Group0ListenerInputVo)
    public record SendMessageToTestTopic1InputVo(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String test,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer test1
    ) {
    }
}