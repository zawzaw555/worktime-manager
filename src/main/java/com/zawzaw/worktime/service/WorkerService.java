package com.zawzaw.worktime.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zawzaw.worktime.mapper.WorkerMapper;
import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.model.dto.TodayAttendanceDto;
import com.zawzaw.worktime.model.entity.EWorker;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerService {
	
	private final WorkerMapper workerMapper;
	
	public List<EWorker> findAll() {
		return workerMapper.findAll();
	}
	
	public List<TodayAttendanceDto> findTodayAttendance() {
		return workerMapper.findTodayAttendance();
	}
	
	public int countTodayAttendance() {
		return findTodayAttendance().size();
	}
	
	public long countTodayBreak() {
		return findTodayAttendance().stream()
				.filter(a -> a.getBreakStart() != null
						&& a.getBreakEnd() == null)
				.count();
	}
	
	public long countTodayCheckedOut() {
		return findTodayAttendance().stream()
				.filter(a -> a.getCheckOut() != null)
				.count();
	}
	
	public List<AdminAttendanceDto> findAdminAttendance() {
		return workerMapper.findAdminAttendance();
	}
}
