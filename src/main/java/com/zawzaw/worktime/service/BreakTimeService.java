package com.zawzaw.worktime.service;

import org.springframework.stereotype.Service;

import com.zawzaw.worktime.mapper.BreakTimeMapper;
import com.zawzaw.worktime.mapper.WorkTimeMapper;
import com.zawzaw.worktime.model.entity.EBreakTime;
import com.zawzaw.worktime.model.entity.EWorkTime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BreakTimeService {
	
	private final WorkTimeMapper workTimeMapper;
	private final BreakTimeMapper breakTimeMapper;
	
	public boolean breakStart(Long workerId) {
		
		System.out.println("workerId = " + workerId);
		
		EWorkTime working = workTimeMapper.selectWorking(workerId);
		
		System.out.println("working = " + working);
		
		if (working == null) {
			System.out.println("出勤データなし");
			return false;
		}
		
		EBreakTime activeBreak = breakTimeMapper.selectBreakTime(working.getId());
		
		System.out.println("activeBreak = " + activeBreak);
		
		if (activeBreak != null) {
			System.out.println("すでに休憩中");
			return false;
		}
		
		int result = breakTimeMapper.insertBreakStart(working.getId());
		
		System.out.println("insert result = " + result);
		
		return true;
	}
	
	public boolean breakEnd(Long workerId) {
		
		EWorkTime working = workTimeMapper.selectWorking(workerId);
		
		if (working == null) {
			return false;
		}
		
		EBreakTime activeBreak = breakTimeMapper.selectBreakTime(working.getId());
		
		if (activeBreak == null) {
			return false;
		}
		
		breakTimeMapper.updateBreakEnd(working.getId());
		
		return true;
	}
}
