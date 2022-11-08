package com.ead.notification.dtos;

import javax.validation.constraints.NotNull;

import com.ead.notification.enums.NotificationStatus;

import lombok.Data;

@Data
public class NotificationDto {
	
	@NotNull
	private NotificationStatus notificationStatus;

}
