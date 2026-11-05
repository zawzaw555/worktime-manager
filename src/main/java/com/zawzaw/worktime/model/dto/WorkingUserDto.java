package com.zawzaw.worktime.model.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class WorkingUserDto {
	
	private Long workerId;
	
	private String workerNo;
	
	private String name;
	
	private LocalTime checkIn;
	
	private boolean onBreak;
}
