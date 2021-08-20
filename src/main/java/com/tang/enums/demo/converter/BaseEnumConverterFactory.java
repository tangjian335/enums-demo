package com.tang.enums.demo.converter;

import com.tang.enums.demo.interfaces.BaseEnum;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Objects;

/**
 * 用于spring框架将String封装为BaseEnum
 * 解决query和path的封装
 *
 * @author : tangjian
 * @date : 2020/11/17 5:20 下午
 */
public class BaseEnumConverterFactory implements ConverterFactory<String, BaseEnum<?>>, ConditionalConverter {


    @Override
    public <T extends BaseEnum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new BaseEnumConverter<>(targetType);
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getType() == String.class &&
                Enum.class.isAssignableFrom(targetType.getType()) &&
                BaseEnum.class.isAssignableFrom(targetType.getType());
    }


    private static class BaseEnumConverter<T extends BaseEnum<?>> implements Converter<String, T> {

        private final T[] values;

        public BaseEnumConverter(Class<T> targetType) {
            values = targetType.getEnumConstants();
        }

        @Override
        public T convert(String source) {
            for (T t : values) {
                if (Objects.equals(String.valueOf(t.getValue()), source)) {
                    return t;
                }
            }
            return null;
        }
    }
}
