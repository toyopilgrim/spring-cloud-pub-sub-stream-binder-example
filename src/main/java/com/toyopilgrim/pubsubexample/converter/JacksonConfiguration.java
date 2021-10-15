package com.toyopilgrim.pubsubexample.converter;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.google.protobuf.GeneratedMessageV3;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@Configuration
public class JacksonConfiguration {

    /**
     * Configuration for de/serializing protobuf in JSON。
     *
     * @return SimpleModule
     */
    @Bean
    public SimpleModule protobufModule() {
        final var module = new SimpleModule("protobufModule");

        Map<Class<?>, JsonDeserializer<?>> deserializers =
            new Reflections("jp.toyopilgrim.proto").getSubTypesOf(GeneratedMessageV3.class).stream()
                .collect(Collectors.toMap(clazz -> clazz, ProtobufDeserializer::new));

        module.setDeserializers(new SimpleDeserializers(deserializers));
        module.setSerializers(new SimpleSerializers(singletonList(new ProtobufSerializer())));

        return module;
    }
}
