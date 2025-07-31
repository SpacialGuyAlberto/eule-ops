package com.euleops.notificationsvc.dto;

import com.euleops.notificationsvc.model.Status;

public record SendNotificationResponse(Long id, Status status) {}