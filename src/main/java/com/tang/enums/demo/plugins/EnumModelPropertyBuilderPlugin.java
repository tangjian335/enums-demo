package com.tang.enums.demo.plugins;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.tang.enums.demo.interfaces.BaseEnum;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import springfox.documentation.builders.ModelSpecificationBuilder;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.schema.ModelSpecification;
import springfox.documentation.schema.ScalarTypes;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EnumModelPropertyBuilderPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<BeanPropertyDefinition> optional = context.getBeanPropertyDefinition();
        if (!optional.isPresent()) {
            return;
        }

        try {

            AnnotatedField field = optional.get().getField();

            if (field == null) {
                return;
            }
            final Class<?> fieldType = field.getRawType();
            addDescForEnum(context, fieldType);
        } catch (Exception ignored) {
        }

    }

    @Override
    public boolean supports(@NonNull DocumentationType delimiter) {
        return true;
    }

    private void addDescForEnum(ModelPropertyContext context, Class<?> fieldType) {
        if (Enum.class.isAssignableFrom(fieldType) && BaseEnum.class.isAssignableFrom(fieldType)) {


            Object[] enumConstants = fieldType.getEnumConstants();

            Class<?> valueClass = ((BaseEnum<?>)enumConstants[0]).getValue().getClass();

            List<String> displayValues =
                    Arrays.stream(enumConstants)
                            .filter(Objects::nonNull)
                            .map(item -> {
                                BaseEnum<?> baseEnum = (BaseEnum<?>) item;
                                String value = String.valueOf(baseEnum.getValue());
                                String name = baseEnum.getDesc();
                                return value + ":" + name;

                            }).collect(Collectors.toList());


            PropertySpecificationBuilder builder = context.getSpecificationBuilder();
            Object description = getFieldValue(builder, "description");
            String joinText = description
                    + " (" + String.join("; ", displayValues) + ")";

            ModelSpecification type = (ModelSpecification)getFieldValue(builder,  "type");

            ModelSpecificationBuilder modelSpecificationBuilder = new ModelSpecificationBuilder();
            modelSpecificationBuilder.copyOf(type);
            modelSpecificationBuilder.scalarModel(ScalarTypes.builtInScalarType(valueClass).orElse(null));

            builder.description(joinText).type(modelSpecificationBuilder.build());
        }


    }

    private Object getFieldValue(Object object, String fieldName) {
        Field descField = ReflectionUtils.findField(object.getClass(), fieldName);
        //noinspection ConstantConditions
        ReflectionUtils.makeAccessible(descField);
        return ReflectionUtils.getField(descField, object);
    }
}