package com.tang.enums.demo.plugins;

import com.fasterxml.classmate.ResolvedType;
import com.tang.enums.demo.interfaces.BaseEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarTypes;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 解决swagger展示枚举的时候，显示的name的问题
 */
@Component
public class EnumParameterBuilderPlugin implements ParameterBuilderPlugin, OperationBuilderPlugin {


    @Override
    public void apply(ParameterContext context) {
        Class<?> type = context.resolvedMethodParameter().getParameterType().getErasedType();
        if (Enum.class.isAssignableFrom(type) && BaseEnum.class.isAssignableFrom(type)) {

            Object[] enumConstants = type.getEnumConstants();

            Class<?> valueClass = ((BaseEnum<?>)enumConstants[0]).getCode().getClass();
            List<String> displayValues = Arrays.stream(enumConstants).filter(Objects::nonNull).map(item -> {
                BaseEnum<?> baseEnum = (BaseEnum<?>) item;
                return String.valueOf(baseEnum.getCode());
            }).collect(Collectors.toList());

            RequestParameterBuilder parameterBuilder = context.requestParameterBuilder();
            parameterBuilder.query(m -> {
                m.enumerationFacet(e -> e.allowedValues(new AllowableListValues(displayValues, "LIST")));
                m.model(modelBuilder -> modelBuilder.scalarModel(ScalarTypes.builtInScalarType(valueClass).orElse(null)));
            });
        }
    }


    @Override
    public boolean supports(@NonNull DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(OperationContext context) {
        List<ResolvedMethodParameter> parameters = context.getParameters();
        parameters.forEach(parameter -> {
            ResolvedType parameterType = parameter.getParameterType();
            Class<?> clazz = parameterType.getErasedType();
            if (Enum.class.isAssignableFrom(clazz) && BaseEnum.class.isAssignableFrom(clazz)) {
                Object[] enumConstants = clazz.getEnumConstants();

                List<String> displayValues = Arrays.stream(enumConstants).filter(Objects::nonNull).map(item -> {
                    BaseEnum<?> namedEnum = (BaseEnum<?>) item;

                    String value = String.valueOf(namedEnum.getCode());

                    String name = namedEnum.getDesc();
                    return value + ":" + name;

                }).collect(Collectors.toList());

                String parameterName = parameter.defaultName().orElse("");

                OperationBuilder operationBuilder = context.operationBuilder();
                Field parametersField = ReflectionUtils.findField(operationBuilder.getClass(), "requestParameters");
                //noinspection ConstantConditions
                ReflectionUtils.makeAccessible(parametersField);
                //noinspection unchecked
                Set<RequestParameter> set = (Set<RequestParameter>) ReflectionUtils.getField(parametersField, operationBuilder);


                if (CollectionUtils.isNotEmpty(set)) {
                    for (RequestParameter currentParameter : set) {
                        if (parameterName.equals(currentParameter.getName())) {

                            Field description = ReflectionUtils.findField(currentParameter.getClass(), "description");
                            //noinspection ConstantConditions
                            ReflectionUtils.makeAccessible(description);
                            Object field = ReflectionUtils.getField(description, currentParameter);
                            ReflectionUtils.setField(description, currentParameter, field + " , " + String.join(",", displayValues));
                            break;
                        }
                    }
                }
            }

        });
    }

}