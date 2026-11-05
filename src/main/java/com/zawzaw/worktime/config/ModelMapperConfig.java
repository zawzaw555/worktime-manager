package com.zawzaw.worktime.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	/* 使用予定　*/
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
