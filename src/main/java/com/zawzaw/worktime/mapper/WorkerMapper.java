package com.zawzaw.worktime.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.model.entity.EWorker;

@Mapper
public interface WorkerMapper {
	
	/* ホームページ */
	List<EWorker> findAll();
	
	List<AdminAttendanceDto> findTodayAttendance();
	
	/* 社員管理ページ */
	EWorker findWorkerById(Long id);
	
	int insertWorkerSignup(EWorker worker);
	
	int countByWorkerNo(String workerNo);
	
	int countByEmail(String email);
	
	String findWorkerByWorkerNo(String workerNo);
	
	String findWorkerByWorkerName(String workerName);
	
	List<EWorker> findByNameOrWorkerNo(
			@Param("keyword") String keyword);
	
	int updateWorker(EWorker worker);
	
	int deleteWorker(Long id);
	
	/* 勤怠管理ページ */
	List<AdminAttendanceDto> findAdminAttendance();
	
	List<AdminAttendanceDto> searchWorkerAttendance(
			@Param("checkDate") LocalDate checkDate,
			@Param("workerNo") String workerNo);
}
