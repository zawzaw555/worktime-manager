package com.zawzaw.worktime.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.zawzaw.worktime.adminvalidation.AttendanceSearchForm;
import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.service.WorkerService;
import com.zawzaw.worktime.workervalidation.AttendanceEditForm;

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
		model.addAttribute(
				"monthlyAttendanceLists",
				workerService.findMonthlyDailyAttendance());
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
	
	@GetMapping("/attendance/edit/{id}")
	public String getEditAttendance(@PathVariable Long id,Model model) {
		setPageInfo(model,"Attendance-Edit","attendance-edit");
		List<AdminAttendanceDto> attendanceLists= workerService.findEditAttendance(id);
		if(attendanceLists.isEmpty()) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND,
					"勤怠データが見つかりません");
		}
		model.addAttribute("attendance", attendanceLists.get(0));
		model.addAttribute("attendanceLists",attendanceLists);
		model.addAttribute("workTimeId",id);
		return "admin/dashboard";
	}
	
	@PostMapping("/attendance/delete/{id}")
	public String postDeleteAttendance(@PathVariable Long id,Model model) {
		workerService.deleteAttendance(id);
		workerService.deleteWorker(id);
		return "redirect:/admin/attendance";
	}
	
	@PostMapping("/attendance/update/{id}")
	public String postUpdateAttendance(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute AttendanceEditForm form) {
		workerService.updateAttendance(id,form);
		return "redirect:/admin/attendance";
	}
}
