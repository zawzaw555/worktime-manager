package com.zawzaw.worktime.controller.admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zawzaw.worktime.adminvalidation.AttendanceSearchForm;
import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.service.WorkerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAttendanceController {
	
	/* Attendance Page */
	private final WorkerService workerService;
	
	// 重要のmodelのaddAttributeメソッド
	private void setPageInfo(Model model,String pageTitle,String mode) {
		model.addAttribute("pageTitle",pageTitle);
		model.addAttribute("mode",mode);
	}
	
	@GetMapping("/attendance")
	public String getAttendance(Model model) {
		setPageInfo(model,"Attendance","attendance");
		List<AdminAttendanceDto> attendanceLists= workerService.findAdminAttendance();
		model.addAttribute("attendanceLists",attendanceLists);
		model.addAttribute("searchForm",new AttendanceSearchForm());
		return "admin/dashboard";
	}
	
	@PostMapping("/attendance/search")
	public String searchAttendance(
			Model model,
			@Valid @ModelAttribute("searchForm") AttendanceSearchForm searchForm,
			BindingResult result) {
		setPageInfo(model,"Attendance","attendance");
		
		if (result.hasErrors()) {
			List<AdminAttendanceDto> attendanceLists = workerService.findAdminAttendance();
					model.addAttribute("attendanceLists",attendanceLists);
			return "admin/dashboard";
		}
		List<AdminAttendanceDto> attendanceLists = workerService.searchWorkerAttendance(
															searchForm.getCheckDate(),
															searchForm.getWorkerNo());
		model.addAttribute("attendanceLists",attendanceLists);
		return "admin/dashboard";
	}
}
