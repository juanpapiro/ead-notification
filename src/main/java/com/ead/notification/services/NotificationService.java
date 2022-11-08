package com.ead.notification.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ead.notification.models.NotificationModel;

public interface NotificationService {

	void saveNotification(NotificationModel notificationModel);

	Page<NotificationModel> findAllNotificationsByUser(UUID userId, Pageable pageable);

	Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId);

}
