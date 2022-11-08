package com.ead.notification.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRepository;
import com.ead.notification.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public void saveNotification(NotificationModel notificationModel) {
		notificationRepository.save(notificationModel);
	}

	@Override
	public Page<NotificationModel> findAllNotificationsByUser(UUID userId, Pageable pageable) {
		return notificationRepository.findByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable);
	}

	@Override
	public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
		return notificationRepository.findByNotificationIdAndUserId(notificationId, userId);
	}
	
}
