package com.euleops.notificationsvc.service;

import com.euleops.notificationsvc.dto.SendNotificationRequest;
import com.euleops.notificationsvc.dto.SendNotificationResponse;

public interface NotificationService {
    SendNotificationResponse sendDirect(SendNotificationRequest request);
    void sendActivationEmail(String email, String username);
}
