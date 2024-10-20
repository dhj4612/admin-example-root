package org.example.framework.utils.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.framework.utils.AesUtil;
import org.example.framework.utils.RedisUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({AesProperties.class})
public class UtilsConfiguration {

    private final AesProperties aesProperties;

    private final StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init() {
        AesUtil.init(aesProperties.getKey());
        RedisUtil.init(stringRedisTemplate);
        log.info("UtilsConfiguration init complete......");
    }
}
