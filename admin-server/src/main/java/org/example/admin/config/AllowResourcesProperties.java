package org.example.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "allow")
@Data
public class AllowResourcesProperties {
    private Set<String> resources;
}
