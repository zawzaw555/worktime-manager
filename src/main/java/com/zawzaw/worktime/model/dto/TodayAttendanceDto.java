package com.zawzaw.worktime.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TodayAttendanceDto {
	
	private String workerNo;
	private String workerName;
	
	private LocalDateTime checkIn;
	private LocalDateTime checkOut;
	
	private LocalDateTime breakStart;
	private LocalDateTime breakEnd;
}
