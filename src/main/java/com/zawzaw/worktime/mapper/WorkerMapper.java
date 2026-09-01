package com.zawzaw.worktime.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.model.entity.EWorker;

@Mapper
public interface WorkerMapper {
	List<EWorker> findAll();
	
	List<AdminAttendanceDto> findTodayAttendance();
	List<AdminAttendanceDto> findAdminAttendance();
}
