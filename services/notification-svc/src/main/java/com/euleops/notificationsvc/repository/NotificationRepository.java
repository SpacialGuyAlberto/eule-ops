package com.euleops.notificationsvc.repository;

import com.euleops.notificationsvc.model.Notification;
import com.euleops.notificationsvc.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatus(Status status);
}
