package com.zawzaw.worktime.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class AdminAttendanceDto {
	
	private Long workTimeId;
	private String workerNo;
	private String workerName;
	
	private LocalDate checkDate;
	private LocalTime checkIn;
	private LocalTime checkOut;
	
	private LocalTime breakStart;
	private LocalTime breakEnd;
	
	// 計算用
	private long breakMinutes;
	private long workingMinutes;
	
	// 表示用
	private String breakTime;
	private String workingTime;
}
