package com.zawzaw.worktime.workervalidation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttendanceForm {
	
	@NotBlank(message = "社員番号を入力してください")
	private String workerNo;
	
	@NotBlank(message = "パスワードを入力してください")
	private String password;

}
