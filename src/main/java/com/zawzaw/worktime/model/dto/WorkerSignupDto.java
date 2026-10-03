package com.zawzaw.worktime.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkerSignupDto {
	
	@NotBlank(message = "社員番号を入力してください")
	private String workerNo;
	
	@NotBlank(message = "社員名を入力してください")
	private String name;
	
	@NotBlank(message = "メールを入力してください")
	private String email;
	
	@NotBlank(message = "パスワードを入力してください")
	@Size(min = 4,max = 20, message = "パスワード4~20文字で入力してください")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "パスワードは半角英数のみで入力してください")
	private String password;
}
