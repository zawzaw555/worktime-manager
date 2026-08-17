package com.zawzaw.worktime.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EWorkTime {
	
	private Long id;
	
	private Long workerId;
	
	private LocalDate checkDate;
	
	private LocalDateTime checkIn;
	
	private LocalDateTime checkOut;
}
