package com.zawzaw.worktime.controller.worker;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zawzaw.worktime.model.dto.WorkingUserDto;
import com.zawzaw.worktime.service.WorkTimeService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/worker")
@RequiredArgsConstructor
public class WorkerController {
	
	private final WorkTimeService workTimeService;
	
	private void addWorkingUsers(Model model) {
		List<WorkingUserDto> workingUsers = 
				workTimeService.findTodayWorkingUsers();
		
		model.addAttribute("workingUsers",workingUsers);
		model.addAttribute("workingCount",workingUsers.size());
	}
	
	@GetMapping("/home")
	public String getHome(Model model) {
		addWorkingUsers(model);
		model.addAttribute("mode","attendance");
		
		return "worker/home";
	}
	
	@GetMapping("/home/{id}")
	public String getAttendancePage(
			@PathVariable Long id,
			Model model) {
		
		addWorkingUsers(model);
		
		WorkingUserDto selectedWorker =
				workTimeService.findTodayWorkingUserById(id);
		model.addAttribute("selectedWorker",selectedWorker);
		model.addAttribute("mode","leave");
		
		return "worker/home";
			
		}
}
