package com.zawzaw.worktime.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zawzaw.worktime.mapper.WorkTimeMapper;
import com.zawzaw.worktime.mapper.WorkerMapper;
import com.zawzaw.worktime.model.dto.WorkingUserDto;
import com.zawzaw.worktime.model.entity.EWorkTime;
import com.zawzaw.worktime.model.entity.EWorker;
import com.zawzaw.worktime.workervalidation.CheckInResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkTimeService {
	
	private final WorkerMapper workerMapper;
	private final WorkTimeMapper workTimeMapper;
	private final PasswordEncoder passwordEncoder;
	
	public List<WorkingUserDto> findTodayWorkingUsers() {
		return workTimeMapper.findTodayWorkingUsers();
	}
	
	public WorkingUserDto findTodayWorkingUserById(Long id) {
		return workTimeMapper.findTodayWorkingUserById(id);
	}
	
	public CheckInResult insertCheckIn(String workerNo,String password) {
		EWorker worker = workTimeMapper.findWorkerByWorkerNo(workerNo);
		
		if (worker == null) {
			return CheckInResult.WORKER_NOT_FOUND;
		}
		if (!passwordEncoder.matches(password, worker.getPassword())) {
			return CheckInResult.WRONG_PASSWORD;
		}
		
		EWorkTime working = workTimeMapper.selectWorking(worker.getId());
		
		if (working != null) {
			return CheckInResult.ALREADY_WORKING;
		}
		workTimeMapper.insertCheckIn(worker.getId());
		
		return CheckInResult.SUCCESS;
	}
	
	public boolean updateCheckOut(
						Long id,
						String password) {
		EWorker worker = workerMapper.findWorkerById(id);
		if (worker == null) {
			return false;
		}
		if (!passwordEncoder.matches(
								password, 
								worker.getPassword())) {
			return false;
		}
		
		workTimeMapper.updateCheckOut(id);
		return true;
	}
	
}
