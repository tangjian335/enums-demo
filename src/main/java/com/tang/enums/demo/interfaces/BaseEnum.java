package com.tang.enums.demo.interfaces;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tang.enums.demo.converter.BaseEnumDeserializer;

/**
 * 基本枚举类型
 *
 * @author : tangjian
 * @date : 2021-08-18 16:27
 */
@JsonDeserialize(using = BaseEnumDeserializer.class)
public interface BaseEnum<T> {

    @JsonValue
    T getValue();

    String getDesc();
}
