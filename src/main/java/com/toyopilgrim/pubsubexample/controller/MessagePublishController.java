package com.toyopilgrim.pubsubexample.controller;

import jp.toyopilgrim.proto.SampleMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessagePublishController {

    private final Sinks.Many<SampleMessage> sampleSink;

    @PostMapping(value = "/message/send")
    public SampleRequest sendMessage(@RequestBody SampleRequest request) {
        final var payload = SampleMessage.newBuilder()
            .setUserId(request.getId())
            .setContent("text").build();
        sampleSink.tryEmitNext(payload);
        return request;
    }

    @Getter
    @Setter
    public static class SampleRequest {
        private String id;
    }
}
