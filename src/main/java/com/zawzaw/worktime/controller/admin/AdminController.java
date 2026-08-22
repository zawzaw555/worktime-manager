package com.zawzaw.worktime.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zawzaw.worktime.model.entity.EWorker;
import com.zawzaw.worktime.service.WorkerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final WorkerService workerService;
	
	@GetMapping("/home")
	public String getHome(Model model) {
		
		model.addAttribute("mode","home");
		
		model.addAttribute(
				"attendanceList",
				workerService.findTodayAttendance()
		);
		
		model.addAttribute(
				"totalCount",
				workerService.countTodayAttendance()
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
	@GetMapping("/worker")
	public String getWorker(Model model) {
		
		List<EWorker> workerList = workerService.findAll();
		model.addAttribute("mode","worker");
		model.addAttribute("workerList",workerList);
		
		return "admin/dashboard";
	}
	@GetMapping("/attendance")
	public String getAttendance(Model model) {
		model.addAttribute("mode","attendance");
		return "admin/dashboard";
	}
}
