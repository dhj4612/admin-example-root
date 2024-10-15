package org.example.framework.database.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "db")
public class DbProperties {
    private String aesKey;
}
