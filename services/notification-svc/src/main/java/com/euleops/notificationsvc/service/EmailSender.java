package com.euleops.notificationsvc.service;

public interface EmailSender {
    void send(String to, String subject, String htmlBody) throws Exception;
}
