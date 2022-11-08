package com.ead.notification.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class NotificationCommandDto {
	
	private String title;
	private String message;
	private UUID userId;

}
