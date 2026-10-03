package com.zawzaw.worktime.adminvalidation;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceSearchForm {
	
	@NotNull(message = "日付を入力してください")
	private LocalDate checkDate;
	
	@NotBlank(message = "社員番号を入力してください")
	private String workerNo;
}