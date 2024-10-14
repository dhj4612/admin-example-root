package org.example.framework.common.designpattern.config;

import lombok.RequiredArgsConstructor;
import org.example.framework.common.designpattern.chain.AbstractChainContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class DesignPatternAutoConfiguration {

    private final ApplicationContext context;

    @Bean
    public AbstractChainContext<?> abstractChainHandler() {
        return new AbstractChainContext<>(context);
    }
}
