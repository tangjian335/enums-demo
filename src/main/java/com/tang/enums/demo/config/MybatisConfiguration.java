package com.tang.enums.demo.config;

import com.tang.enums.demo.handler.type.BaseEnumTypeHandler;
import com.tang.enums.demo.interfaces.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * 注册BaseEnum子类的TypeHandler
 * BaseEnum的子类需要放在com.tang.enums.demo.enums下，否则需要手动注册转换器
 *
 * @author : tangjian
 * @date : 2020/11/17 5:38 下午
 */
@Slf4j
@Configuration
public class MybatisConfiguration {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            try {
                Set<Class<?>> classes = scanClasses("com.tang.enums.demo.enums", BaseEnum.class);
                for (Class<?> aClass : classes) {
                    typeHandlerRegistry.register(aClass, BaseEnumTypeHandler.class);
                }
            } catch (IOException e) {
                log.error("配置mybatis转换器失败", e);
                throw new RuntimeException("配置mybatis转换器失败");
            }
        };
    }


    @SuppressWarnings("SameParameterValue")
    private Set<Class<?>> scanClasses(String packagePatterns, Class<?> assignableType) throws IOException {
        Set<Class<?>> classes = new HashSet<>();
        String[] packagePatternArray = tokenizeToStringArray(packagePatterns,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        for (String packagePattern : packagePatternArray) {
            Resource[] resources = resourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    + ClassUtils.convertClassNameToResourcePath(packagePattern) + "/**/*.class");
            for (Resource resource : resources) {
                try {
                    ClassMetadata classMetadata = metadataReaderFactory.getMetadataReader(resource).getClassMetadata();
                    Class<?> clazz = Resources.classForName(classMetadata.getClassName());
                    if (assignableType == null || assignableType.isAssignableFrom(clazz)) {
                        classes.add(clazz);
                    }
                } catch (Throwable e) {
                    log.warn("Cannot load the '" + resource + "'. Cause by " + e.toString());
                }
            }
        }
        return classes;
    }

}
