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
	
	/* 社員登録 */
	int insertWorkerSignup(EWorker worker);
	
	int countByWorkerNo(String workerNo);
	
	int countByEmail(String email);
	
	String findWorkerByWorkerNo(String workerNo);
	
	String findWorkerByWorkerName(String workerName);
	
	EWorker findWorkerById(Long id);
	
	int updateWorker(EWorker worker);
	
	int deleteWorker(Long id);
}
