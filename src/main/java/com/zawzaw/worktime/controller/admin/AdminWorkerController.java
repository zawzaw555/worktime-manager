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
public class AdminWorkerController {
	
	/* Worker Page */
	
	private final WorkerService workerService;
	
	@GetMapping("/worker")
	public String getWorker(Model model) {
		
		model.addAttribute("pageTitle","Worker");
		
		List<EWorker> workerList = workerService.findAll();
		model.addAttribute("mode","worker");
		model.addAttribute("workerList",workerList);
		
		return "admin/dashboard";
	}
}
