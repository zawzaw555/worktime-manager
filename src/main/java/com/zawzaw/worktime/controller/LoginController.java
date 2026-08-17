package com.zawzaw.worktime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zawzaw.worktime.model.entity.ELogin;
import com.zawzaw.worktime.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;
	
	@GetMapping("/")
	public String reLogin() {
		return "redirect:/login";
	}
	@GetMapping("/login")
	public String getLogin() {
		return "login/login";
	}
	
	@PostMapping("/login")
	public String postLogin(
			@RequestParam String userId,
			@RequestParam String password,
			HttpSession session,
			Model model) {
		ELogin loginUser = loginService.login(userId, password);
		
		if (loginUser == null) {
			model.addAttribute(
					"errorMessage",
					"ログインIDまたはパスワードが正しくありません"
				);
		return "login/login";
		}
		session.setAttribute("loginUser", loginUser);
		if ("ADMIN".equals(loginUser.getRole())) {
			return "redirect:/admin/signup";
		}
		if ("WORKER".equals(loginUser.getRole())) {
			return "redirect:/worker/home";
		}
		
		model.addAttribute(
				"errorMessage",
				"ユーザー権限が設定されていません"
			);
		return "login/login";
	}
}
