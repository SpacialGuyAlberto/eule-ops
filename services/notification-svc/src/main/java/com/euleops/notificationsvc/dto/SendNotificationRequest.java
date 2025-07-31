package com.euleops.notificationsvc.dto;

import com.euleops.notificationsvc.model.Channel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendNotificationRequest(
        @Email     String to,
        @NotBlank  String subject,
        @NotBlank  String content,
        Channel    channel
) {}
