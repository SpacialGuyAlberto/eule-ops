package com.euleops.notificationsvc.service;

import com.euleops.notificationsvc.dto.SendNotificationRequest;
import com.euleops.notificationsvc.dto.SendNotificationResponse;
import com.euleops.notificationsvc.model.Channel;
import com.euleops.notificationsvc.model.Notification;
import com.euleops.notificationsvc.model.Status;
import com.euleops.notificationsvc.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repo;
    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;   // define en MailConfig si usas Thymeleaf

    @Override
    public SendNotificationResponse sendDirect(SendNotificationRequest r) {
        Notification n = repo.save(Notification.builder()
                .recipient(r.to())
                .subject(r.subject())
                .body(r.content())
                .channel(r.channel() == null ? Channel.EMAIL : r.channel())
                .status(Status.PENDING)
                .createdAt(Instant.now())
                .build());

        try {
            emailSender.send(r.to(), r.subject(), r.content());
            n.setStatus(Status.SENT);
            n.setSentAt(Instant.now());
        } catch (Exception e) {
            n.setStatus(Status.FAILED);
            n.setRetries(n.getRetries()+1);
        }
        repo.save(n);
        return new SendNotificationResponse(n.getId(), n.getStatus());
    }

    @Override
    public void sendActivationEmail(String email, String username) {
        Context ctx = new Context();
        ctx.setVariable("username", username);
        String html = templateEngine.process("activation.html", ctx);
        sendDirect(new SendNotificationRequest(email, "Activate your account", html, Channel.EMAIL));
    }
}
