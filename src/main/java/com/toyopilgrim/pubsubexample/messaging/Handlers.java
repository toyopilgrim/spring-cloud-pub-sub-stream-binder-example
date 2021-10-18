package com.toyopilgrim.pubsubexample.messaging;

import jp.toyopilgrim.proto.SampleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class Handlers {
    @Bean
    public Consumer<SampleMessage> processSample() {
        return sampleMessage -> log.info("Received message: {}", sampleMessage.getContent());
    }
}
