package org.example.framework.utils.config;

import lombok.RequiredArgsConstructor;
import org.example.framework.utils.AesUtil;
import org.example.framework.utils.ApiResourcesUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@EnableConfigurationProperties({AesProperties.class})
public class UtilsConfiguration {

    private final ApplicationContext applicationContext;

    @Bean
    public ApiResourcesUtil apiResourcesUtil() {
        return new ApiResourcesUtil(applicationContext);
    }

    @Bean
    public AesUtil aesUtil(AesProperties properties) {
        return new AesUtil(properties.getKey());
    }
}
