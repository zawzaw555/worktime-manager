package com.zawzaw.worktime.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zawzaw.worktime.mapper.WorkTimeMapper;
import com.zawzaw.worktime.model.dto.WorkingUserDto;
import com.zawzaw.worktime.model.entity.EWorkTime;
import com.zawzaw.worktime.model.entity.EWorker;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkTimeService {
	
	private final WorkTimeMapper workTimeMapper;
	
	public List<WorkingUserDto> findTodayWorkingUsers() {
		return workTimeMapper.findTodayWorkingUsers();
	}
	
	public WorkingUserDto findTodayWorkingUserById(Long id) {
		return workTimeMapper.findTodayWorkingUserById(id);
	}
	
	public boolean insertCheckIn(String workerNo,String password) {
		EWorker worker = workTimeMapper.findWorkerByWorkerNo(workerNo);
		
		if (worker == null) {
			return false;
		}
		if (!worker.getPassword().equals(password)) {
			return false;
		}
		
		EWorkTime working = workTimeMapper.selectWorking(worker.getId());
		
		if (working != null) {
			return false;
		}
		workTimeMapper.insertCheckIn(worker.getId());
		
		return true;
	}
	
	public int updateCheckOut(Long workerId) {
		return workTimeMapper.updateCheckOut(workerId);
	}
}
