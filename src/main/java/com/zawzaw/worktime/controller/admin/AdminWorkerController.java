package com.zawzaw.worktime.controller.admin;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zawzaw.worktime.model.dto.WorkerSignupDto;
import com.zawzaw.worktime.model.entity.EWorker;
import com.zawzaw.worktime.service.WorkerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminWorkerController {
	
	/* Worker Page */
	private final WorkerService workerService;
	private final ModelMapper modelMapper;
	
	// 重複のmodelのaddAttribute メソッド
	private void setPageInfo(Model model,String pageTitle,String mode) {
		model.addAttribute("pageTitle",pageTitle);
		model.addAttribute("mode",mode);
	}
	
	@GetMapping("/worker")
	public String getWorker(Model model) {
		setPageInfo(model,"Worker","worker");
		List<EWorker> workerList = workerService.findAll();
		model.addAttribute("workerList",workerList);
		
		return "admin/dashboard";
	}
	
	/* Admin ー>　Worker Signup */
	@GetMapping("/signup")
	public String getSignup(Model model) {
		setPageInfo(model,"Signup","signup");
		model.addAttribute("signupForm", new EWorker());
		return "admin/dashboard";
	}
	@PostMapping("/signup")
	public String postSignup(
			Model model,
			@Valid @ModelAttribute("signupForm") WorkerSignupDto form,
			BindingResult bindingResult) {
		// workerNoの重複確認
		if (workerService.workerNoExist(form.getWorkerNo())) {
			bindingResult.rejectValue(
				"workerNo",
				"重複",
				"この社員番号は既に登録されてます"
			);
		}
		// emailの重複確認
		if (workerService.emailExist(form.getEmail())) {
			bindingResult.rejectValue(
					"email",
					"重複",
					"このメールアドレスは既に登録されてます"
			);
		}
		// bindingResult のエラー確認
		if (bindingResult.hasErrors()) {
				setPageInfo(model,"Signup","signup");
				return "admin/dashboard";
		}
		// DTO --> Entity
		EWorker worker = modelMapper.map(form, EWorker.class);
		// DBへ登録
		workerService.workerSignup(worker);
		return "redirect:/admin/worker";
	}
	
	/* Admin -> worker edit */
	@GetMapping("/worker/edit/{id}")
	public String editWorker(@PathVariable Long id,Model model) {
		EWorker worker = workerService.findWorkerEdit(id);
		model.addAttribute("worker",worker);
		setPageInfo(model,"Edit","edit");
		return "admin/dashboard";
	}
	
	/* Admin -> Worker */
	@PostMapping("/worker/update")
	public String updateWorker(
			Model model,
			@ModelAttribute EWorker worker) {
		workerService.workerUpdate(worker);
		return "redirect:/admin/worker";
	}
	
	/* Admin -> Worker Detail */
	@GetMapping("/worker/detail/{id}")
	public String detailWorker(
			Model model,
			@PathVariable Long id) {
		EWorker worker = workerService.findWorkerDetail(id);
		model.addAttribute("worker",worker);
		setPageInfo(model,"Detail","detail");
		return "admin/dashboard";
	}
	
	/* Admin -> Worker Delete */
	@GetMapping("/worker/delete/{id}")
	public String deleteWorker(
			Model model,
			@PathVariable Long id) {
		workerService.deleteWorker(id);
		return "redirect:/admin/worker";
	}
}
