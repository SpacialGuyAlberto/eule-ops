package com.euleops.auth.client;

import com.euleops.notification.client.model.SendNotificationRequest;
import com.euleops.notification.client.model.SendNotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
        name = "notification-svc",                       // ← mismo nombre que en compose
        url  = "${notification.host:http://notification-svc:8080}"
)
public interface NotificationFeignClient {

    @PostMapping("/notifications/email")
    SendNotificationResponse sendEmail(@RequestBody SendNotificationRequest req);
}
