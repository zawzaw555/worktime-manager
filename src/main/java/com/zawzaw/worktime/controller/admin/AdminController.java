package com.zawzaw.worktime.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.model.entity.EWorker;
import com.zawzaw.worktime.service.WorkerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final WorkerService workerService;
	
	/* home */
	@GetMapping("/home")
	public String getHome(Model model) {
		
		model.addAttribute("mode","home");
		
		model.addAttribute(
				"homeList",
				workerService.findTodayAttendance()
		);
		
		model.addAttribute(
				"totalCount",
				workerService.countTodayAttendance()
		);
		
		model.addAttribute(
				"checkInCount",
				workerService.countTodayCheckIn()
		);
		
		model.addAttribute(
				"breakCount",
				workerService.countTodayBreak()
		);
		
		model.addAttribute(
				"checkedOutCount",
				workerService.countTodayCheckedOut()
		);
		
		return "admin/dashboard";
	}
	
	/* worker */
	@GetMapping("/worker")
	public String getWorker(Model model) {
		
		List<EWorker> workerList = workerService.findAll();
		model.addAttribute("mode","worker");
		model.addAttribute("workerList",workerList);
		
		return "admin/dashboard";
	}
	
	/* attendance */
	@GetMapping("/attendance")
	public String getAttendance(Model model) {
		
		List<AdminAttendanceDto> attendanceLists= workerService.findAdminAttendance();
		model.addAttribute("attendanceLists",attendanceLists);
		model.addAttribute("mode","attendance");
		return "admin/dashboard";
	}
}
