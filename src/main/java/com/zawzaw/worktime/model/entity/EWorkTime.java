package com.zawzaw.worktime.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class EWorkTime {
	
	private Long id;
	
	private Long workerId;
	
	private LocalDate checkDate;
	
	private LocalTime checkIn;
	
	private LocalTime checkOut;
}
