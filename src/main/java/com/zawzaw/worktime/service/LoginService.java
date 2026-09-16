package com.zawzaw.worktime.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zawzaw.worktime.mapper.LoginMapper;
import com.zawzaw.worktime.model.entity.ELogin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	private final LoginMapper loginMapper;
	private final PasswordEncoder passwordEncoder;
	
	public ELogin login(String userId, String password) {
		
		ELogin login = loginMapper.findByLoginId(userId);
		
		if (login == null) {
			return null;
		}
		if (!passwordEncoder.matches(password, login.getPassword())) {
			return null;
		}
		
		return login;
	}
}
