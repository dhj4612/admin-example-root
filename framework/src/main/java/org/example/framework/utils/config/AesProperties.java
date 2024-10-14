package org.example.framework.utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aes")
@Data
public class AesProperties {
    private String key;
}
