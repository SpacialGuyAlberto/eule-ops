package com.euleops.notificationsvc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Template {
    ACTIVATION("Activate your account", "activation.html");

    private final String subject;
    private final String file;
}
