package com.ead.notification.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.notification.configs.security.UserDetailsImpl;
import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@PreAuthorize("hasAnyRole('STUDENT')")
	@GetMapping("/users/{userId}/notifications")
	public ResponseEntity<Page<NotificationModel>> getAllNotificationByUser(
			@PathVariable(value = "userId") UUID userId,
			@PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Direction.DESC) Pageable pageable,
			Authentication authentication) {
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		log.info("Usuário autenticado {}", userDetail.getUsername());
		return ResponseEntity.ok(notificationService.findAllNotificationsByUser(userId, pageable));
	}
	
	@PreAuthorize("hasAnyRole('STUDENT')")
	@PutMapping("/users/{userId}/notifications/{notificationId}")
	public ResponseEntity<Object> updateNotification(
			@PathVariable(value = "userId") UUID userId,
			@PathVariable(value = "notificationId") UUID notificationId,
			@RequestBody @Valid NotificationDto notificationDto
			) {
		Optional<NotificationModel> notificationModelOptional = notificationService
				.findByNotificationIdAndUserId(notificationId, userId);
		if(notificationModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification Not Found!");
		}
		notificationModelOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
		notificationService.saveNotification(notificationModelOptional.get());
		return ResponseEntity.ok(notificationModelOptional.get());
	}
	
}
