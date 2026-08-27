package com.zawzaw.worktime.model.entity;

import java.time.LocalTime;

import lombok.Data;

@Data
public class EBreakTime {
	
	private Long id;
	
	private Long workTimeId;
	
	private LocalTime breakStart;
	
	private LocalTime breakEnd;
}
