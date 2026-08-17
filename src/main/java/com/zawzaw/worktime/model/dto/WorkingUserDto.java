package com.zawzaw.worktime.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WorkingUserDto {
	
	private Long workerId;
	
	private String workerNo;
	
	private String name;
	
	private LocalDateTime checkIn;
	
	private boolean onBreak;
}
