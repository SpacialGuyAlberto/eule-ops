package com.euleops.notificationsvc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sendgrid")
public class MailProperties {
    private String apiKey;
    private String from;
}
