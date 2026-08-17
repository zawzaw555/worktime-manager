package com.zawzaw.worktime.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zawzaw.worktime.model.entity.ELogin;

@Mapper
public interface LoginMapper {
	ELogin findByLoginId (
				@Param("userId") String userId
			);
}
