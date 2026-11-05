package com.zawzaw.worktime.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zawzaw.worktime.service.WorkerService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminHomeController {
	
	/* Home Page */
	
	private final WorkerService workerService;
	
	@GetMapping("/home")
	public String getHome(Model model) {
		
		model.addAttribute("pageTitle","Home");
		
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
	
}
