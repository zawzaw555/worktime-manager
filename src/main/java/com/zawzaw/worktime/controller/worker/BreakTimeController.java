package com.zawzaw.worktime.controller.worker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zawzaw.worktime.service.BreakTimeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/worker")
@RequiredArgsConstructor
public class BreakTimeController {
	
	private final BreakTimeService breakTimeService;
	
	@PostMapping("/break-start")
	public String postBreakStart(
			@RequestParam Long workerId) {
		
		breakTimeService.breakStart(workerId);
		
		return "redirect:/worker/home";
	}
	
	@PostMapping("/break-end")
	public String postBreakEnd(
			@RequestParam Long workerId) {
		
		breakTimeService.breakEnd(workerId);
		
		return "redirect:/worker/home";
	}
}
