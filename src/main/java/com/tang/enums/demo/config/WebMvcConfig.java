package com.tang.enums.demo.config;

import com.tang.enums.demo.converter.BaseEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : tangjian
 * @date : 2021-08-19 10:52
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 枚举类的转换器工厂 addConverterFactory
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new BaseEnumConverterFactory());
    }
}
