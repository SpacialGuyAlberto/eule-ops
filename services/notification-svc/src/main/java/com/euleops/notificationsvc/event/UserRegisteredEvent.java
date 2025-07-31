package com.euleops.notificationsvc.event;

public record UserRegisteredEvent(
        String email,
        String username,
        String activationLink
) {}
