package com.tang.enums.demo.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.tang.enums.demo.interfaces.BaseEnum;

import java.io.IOException;
import java.util.Objects;

/**
 * 解决body参数中BaseEnum的封装
 * @author : tangjian
 * @date : 2021-08-18 18:37
 */
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum<?>> implements ContextualDeserializer {

    private Class<?> target;


    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        target = ctxt.getContextualType().getRawClass();
        return this;
    }

    @Override
    public BaseEnum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Object[] enumConstants = target.getEnumConstants();
        String value = p.getCodec().readValue(p, String.class);
        for (Object enumConstant : enumConstants) {
            BaseEnum<?> baseEnum = (BaseEnum<?>) enumConstant;
            if (Objects.equals(String.valueOf(baseEnum.getValue()), value)) {
                return baseEnum;
            }
        }
        return null;
    }
}
