package com.euleops.notificationsvc.config;

import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SendGridConfig {
    private final MailProperties props;

    @Bean
    public SendGrid sendGridClient() {
        return new SendGrid(props.getApiKey());
    }
}
