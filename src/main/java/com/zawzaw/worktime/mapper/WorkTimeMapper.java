package com.zawzaw.worktime.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zawzaw.worktime.model.dto.WorkingUserDto;
import com.zawzaw.worktime.model.entity.EWorkTime;
import com.zawzaw.worktime.model.entity.EWorker;

@Mapper
public interface WorkTimeMapper {
	
	List<WorkingUserDto> findTodayWorkingUsers();
	
	WorkingUserDto findTodayWorkingUserById(Long id);
	
	int insertCheckIn(@Param("workerId") Long workerId);
	
	Long findWorkerIdByWorkerNo(String workerNo);
	
	int updateCheckOut(@Param("workerId") Long workerId);
	
	EWorkTime selectWorking(Long workerId);
	
	EWorker findWorkerByWorkerNo(String workerNo);
}
