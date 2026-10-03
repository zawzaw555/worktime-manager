package com.zawzaw.worktime.model.entity;

import lombok.Data;

@Data
public class ELogin {
	
	private Long id;
	
	private String userId;
	
	private String password;
	
	private String role;
}
