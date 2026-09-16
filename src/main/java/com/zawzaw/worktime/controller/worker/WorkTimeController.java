package com.zawzaw.worktime.controller.worker;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zawzaw.worktime.model.dto.WorkingUserDto;
import com.zawzaw.worktime.service.WorkTimeService;
import com.zawzaw.worktime.workervalidation.AttendanceForm;
import com.zawzaw.worktime.workervalidation.CheckOutForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkTimeController {
	
	private final WorkTimeService workTimeService;
	private void addWorkingUsers(Model model) {
		List<WorkingUserDto> workingUsers = 
				workTimeService.findTodayWorkingUsers();
		
		model.addAttribute("workingUsers",workingUsers);
		model.addAttribute("workingCount",workingUsers.size());
	}
	
	@PostMapping("/checkin")
	public String postCheckIn(
						Model model,
						@Valid @ModelAttribute("attendanceForm") AttendanceForm attendanceForm,
						BindingResult result) {
	    if (result.hasErrors()) {
	    	addWorkingUsers(model);
			model.addAttribute("mode","attendance");
			model.addAttribute("modeout","tocheckout");
	    	return "worker/home";
	    }
		workTimeService.insertCheckIn(attendanceForm.getWorkerNo(),attendanceForm.getPassword());
		return "redirect:/worker/home";
	}
	
	@GetMapping("/checkout")
	public String getCheckOut(
					Model model,
					@RequestParam("workerId") Long id) {
		WorkingUserDto selectedWorker =
		        workTimeService.findTodayWorkingUserById(id);

		model.addAttribute("selectedWorker", selectedWorker);
		model.addAttribute("checkOutForm", new CheckOutForm());
		return "worker/home";
	}
	@PostMapping("/checkout")
	public String postCheckOut(
					Model model,
					@Valid @ModelAttribute("checkOutForm") CheckOutForm checkOutForm,
					BindingResult result,
					@RequestParam("workerId") Long id) {
		if (result.hasErrors()) {
			WorkingUserDto selectedWorker = workTimeService.findTodayWorkingUserById(id);
			model.addAttribute("selectedWorker",selectedWorker);
			addWorkingUsers(model);
			model.addAttribute("mode","leave");
			model.addAttribute("modein","tocheckin");
	    	return "worker/home";
		}
		boolean success = workTimeService.updateCheckOut(
											id,
											checkOutForm.getPassword()
							);
		if (!success) {
			WorkingUserDto selectedWorker = 
								workTimeService.findTodayWorkingUserById(id);
			model.addAttribute("selectedWorker",selectedWorker);
			model.addAttribute(
					"passwordError",
					"パスワードが正しくありません"
			);
			addWorkingUsers(model);
			model.addAttribute("mode","leave");
			model.addAttribute("modein","tocheckin");
			return "worker/home";
		}
		return "redirect:/worker/home";
	}
}
