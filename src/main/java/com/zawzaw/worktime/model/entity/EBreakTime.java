package com.zawzaw.worktime.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EBreakTime {
	
	private Long id;
	
	private Long workTimeId;
	
	private LocalDateTime breakStart;
	
	private LocalDateTime breakEnd;
}
