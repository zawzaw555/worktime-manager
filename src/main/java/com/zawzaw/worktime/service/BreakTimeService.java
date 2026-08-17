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
		
		EWorkTime working = workTimeMapper.selectWorking(workerId);
		
		
		if (working == null) {
			return false;
		}
		
		EBreakTime activeBreak = breakTimeMapper.selectBreakTime(working.getId());
		
		if (activeBreak != null) {
			return false;
		}
		
		breakTimeMapper.insertBreakStart(working.getId());
		
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
