package org.example.framework.utils.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.framework.utils.AesUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({AesProperties.class})
public class UtilsConfiguration {

    private final AesProperties aesProperties;

    @PostConstruct
    public void init() {
        AesUtil.init(aesProperties.getKey());
        log.info("UtilsConfiguration init complete......");
    }
}
