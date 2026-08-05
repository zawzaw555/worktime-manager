package com.zawzaw.worktime.controller.worker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/worker")
public class HomeController {
	
	@GetMapping("/home")
	public String getHome() {
		return "worker/worker-home";
	}
}
