package com.zawzaw.worktime.workervalidation;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceEditForm {
	
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime checkIn;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalTime checkOut;
}
