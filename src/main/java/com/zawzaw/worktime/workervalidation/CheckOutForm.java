package com.zawzaw.worktime.workervalidation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckOutForm {
	
	@NotBlank(message ="個人パスワードを入力してください")
	private String password;
}
