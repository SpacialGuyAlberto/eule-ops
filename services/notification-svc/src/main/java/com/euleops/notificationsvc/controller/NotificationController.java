package com.euleops.notificationsvc.controller;


import com.euleops.notificationsvc.dto.SendNotificationRequest;
import com.euleops.notificationsvc.dto.SendNotificationResponse;
import com.euleops.notificationsvc.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService svc;

    @PostMapping("/email")
    public ResponseEntity<SendNotificationResponse> send(@RequestBody @Valid SendNotificationRequest req){
        return ResponseEntity.ok(svc.sendDirect(req));
    }
}

