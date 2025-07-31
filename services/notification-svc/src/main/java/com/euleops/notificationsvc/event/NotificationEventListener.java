package com.euleops.notificationsvc.event;

import com.euleops.notificationsvc.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService service;

    @KafkaListener(topics = "user-registered", groupId = "notification-svc")
    public void onUserRegistered(UserRegisteredEvent evt){
        service.sendActivationEmail(evt.email(), evt.username());
    }
}
