package com.zawzaw.worktime.controller.worker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zawzaw.worktime.service.WorkTimeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkTimeController {
	
	private final WorkTimeService workTimeService;
	
	@PostMapping("/checkin")
	public String postCheckIn(
			@RequestParam String workerNo,
			@RequestParam String password,
			RedirectAttributes redirectAttributes) {
	    
		boolean success = workTimeService.insertCheckIn(workerNo,password);
		
		if (!success) {
			redirectAttributes.addFlashAttribute(
					"errorMessage",
					"この社員は既に出勤中です。"
				);
		}
		
		return "redirect:/worker/home";
	}
	
	@PostMapping("/checkout")
	public String postCheckOut(
			@RequestParam Long workerId,
			@RequestParam String password) {
		
		workTimeService.updateCheckOut(workerId);
		
		return "redirect:/worker/home";
	}
}
