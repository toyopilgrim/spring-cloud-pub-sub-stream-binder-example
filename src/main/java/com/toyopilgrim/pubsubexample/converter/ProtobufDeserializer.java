package com.toyopilgrim.pubsubexample.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProtobufDeserializer extends StdDeserializer<GeneratedMessageV3> {

    private final JsonFormat.Parser parser;

    private final Method builderFactory;

    /**
     * ProtobufDeserializer。
     *
     * @param clazz GeneratedMessageV3
     */
    public ProtobufDeserializer(Class<? extends GeneratedMessageV3> clazz) {
        super(clazz);
        parser = JsonFormat.parser().ignoringUnknownFields();
        try {
            builderFactory = clazz.getMethod("newBuilder");
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                "Missing newBuilder method expected in protobuf class: " + clazz.getName(), e);
        }
    }

    @Override
    public Class<?> handledType() {
        return GeneratedMessageV3.class;
    }

    @Override
    public GeneratedMessageV3 deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        GeneratedMessageV3.Builder builder = newBuilder();
        TreeNode node = treeNode(parser);
        this.parser.merge(node.toString(), builder);
        return (GeneratedMessageV3) builder.build();
    }

    private TreeNode treeNode(JsonParser parser) throws IOException {
        ObjectCodec codec = parser.getCodec();
        return codec.readTree(parser);
    }

    private GeneratedMessageV3.Builder newBuilder() {
        GeneratedMessageV3.Builder newBuilder;
        try {
            newBuilder = (GeneratedMessageV3.Builder) builderFactory.invoke(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
        return newBuilder;
    }
}
