package com.toyopilgrim.pubsubexample.messaging;

import jp.toyopilgrim.proto.SampleMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class Publishers {
    @Bean
    public Sinks.Many<SampleMessage> sampleSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<SampleMessage>> sendSampleMessage(Sinks.Many<SampleMessage> sampleSink) {
        return sampleSink::asFlux;
    }
}
