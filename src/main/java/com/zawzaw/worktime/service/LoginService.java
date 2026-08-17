package com.zawzaw.worktime.service;

import org.springframework.stereotype.Service;

import com.zawzaw.worktime.mapper.LoginMapper;
import com.zawzaw.worktime.model.entity.ELogin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	private final LoginMapper loginMapper;
	
	public ELogin login(String userId, String password) {
		
		ELogin login = loginMapper.findByLoginId(userId);
		
		if (login == null) {
			return null;
		}
		if (!login.getPassword().equals(password)) {
			return null;
		}
		
		return login;
	}
}
