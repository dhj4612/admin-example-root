package org.example.framework.security.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.framework.security.core.utils.JwtUtil;
import org.example.framework.security.core.utils.SecurityExpressions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableConfigurationProperties({XssProperties.class, JwtProperties.class})
public class SecurityConfiguration {
    private final JwtProperties jwtProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean("se")
    public SecurityExpressions securityExpressions() {
        return new SecurityExpressions();
    }

    @PostConstruct
    public void init() {
        JwtUtil.init(jwtProperties);
    }
}
