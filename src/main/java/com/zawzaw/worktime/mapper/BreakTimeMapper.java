package com.zawzaw.worktime.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zawzaw.worktime.model.entity.EBreakTime;

@Mapper
public interface BreakTimeMapper {
	
	EBreakTime selectBreakTime(Long workTimeId);
	
	int insertBreakStart(Long workTimeId);
	
	int updateBreakEnd(Long workTimeId);
}
