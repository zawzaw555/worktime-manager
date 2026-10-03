package com.zawzaw.worktime.model.entity;

import lombok.Data;

@Data
public class EWorker {
	
	private Long id;
	
	private String workerNo;
	
	private String name;
	
	private String email;
	
	private String password;
}
