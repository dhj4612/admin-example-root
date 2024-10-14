package org.example.framework.web.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.framework.web.core.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
public class WebAutoConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
