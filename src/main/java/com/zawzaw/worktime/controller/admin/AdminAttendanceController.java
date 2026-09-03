package com.zawzaw.worktime.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.service.WorkerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAttendanceController {
	
	/* Attendance Page */
	
	private final WorkerService workerService;
	
	@GetMapping("/attendance")
	public String getAttendance(Model model) {
		
		model.addAttribute("pageTitle","Attendance");
		
		List<AdminAttendanceDto> attendanceLists= workerService.findAdminAttendance();
		model.addAttribute("attendanceLists",attendanceLists);
		model.addAttribute("mode","attendance");
		return "admin/dashboard";
	}
}
