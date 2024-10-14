package org.example.framework.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "xss")
@Data
public class XssProperties {
    private boolean enable;
    private List<String> excludes;
}
