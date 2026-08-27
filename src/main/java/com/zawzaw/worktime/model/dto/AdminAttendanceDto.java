package com.zawzaw.worktime.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class AdminAttendanceDto {
	
	private String workerNo;
	private String workerName;
	
	private LocalDate checkDate;
	private LocalTime checkIn;
	private LocalTime checkOut;
	
	private LocalTime breakStart;
	private LocalTime breakEnd;
}
