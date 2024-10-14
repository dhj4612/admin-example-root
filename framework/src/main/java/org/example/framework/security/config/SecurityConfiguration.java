package org.example.framework.security.config;

import org.example.framework.security.core.utils.JwtTokenUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties({XssProperties.class, JwtProperties.class})
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(JwtProperties properties) {
        return new JwtTokenUtil(properties);
    }
}
