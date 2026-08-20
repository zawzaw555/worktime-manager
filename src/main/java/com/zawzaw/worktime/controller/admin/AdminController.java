package com.zawzaw.worktime.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("mode","home");
		return "admin/dashboard";
	}
	@GetMapping("/worker")
	public String getWorker(Model model) {
		model.addAttribute("mode","worker");
		return "admin/dashboard";
	}
	@GetMapping("/attendance")
	public String getAttendance(Model model) {
		model.addAttribute("mode","attendance");
		return "admin/dashboard";
	}
}
