package com.zawzaw.worktime.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zawzaw.worktime.mapper.WorkerMapper;
import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.model.entity.EWorker;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerService {
	
	private final WorkerMapper workerMapper;
	
	public List<EWorker> findAll() {
		return workerMapper.findAll();
	}
	
	public List<AdminAttendanceDto> findTodayAttendance() {
		
		List<AdminAttendanceDto> homeLists = 				
									workerMapper.findTodayAttendance();
		
		Map<String,Long> totalMinutesMap = new LinkedHashMap<>();
		Map<String,AdminAttendanceDto> workerMap = new LinkedHashMap<>();
		
			for (AdminAttendanceDto attendance : homeLists) {
				
				long workingMinutes = calcWorkingMinutes(attendance);
				
				// 同じ社員番号なら勤務時間加算
				totalMinutesMap.merge(
							attendance.getWorkerNo(),
							workingMinutes,
							Long::sum
				);
				
				// 同じ社員なら後のデータ (最新状態) を残す
				workerMap.put(
						attendance.getWorkerNo(),
						attendance
				);
				
				attendance.setWorkingTime(
						formatMinutes(workingMinutes)
				);
			}
			for (Map.Entry<String, AdminAttendanceDto> entry
					: workerMap.entrySet()) {
				
				String workerNo = entry.getKey();
				AdminAttendanceDto attendance = entry.getValue();
				
				long totalMinutes = totalMinutesMap.get(workerNo);
				
				attendance.setWorkingTime(
						formatMinutes(totalMinutes)
				);
			}
			
		return new ArrayList<>(workerMap.values());
	}
	
	public long countTodayAttendance() {
		return findTodayAttendance().stream()
				.map(AdminAttendanceDto::getWorkerNo)
				.distinct()
				.count();
	}
	
	public long countTodayCheckIn() {
		return findTodayAttendance().stream()
				.filter(a -> a.getCheckIn() != null
						&& a.getCheckOut() == null
						&& (a.getBreakStart() == null
							|| a.getBreakEnd() != null))
				.map(AdminAttendanceDto::getWorkerNo)
				.distinct()
				.count();
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
				.map(AdminAttendanceDto::getWorkerNo)
				.distinct()
				.count();
	}
	
	public List<AdminAttendanceDto> findAdminAttendance() {
		
		List<AdminAttendanceDto> attendanceLists = workerMapper.findAdminAttendance();
		
		for(AdminAttendanceDto attendance : attendanceLists) {
			attendance.setBreakTime(calcBreakTime(attendance));
			attendance.setWorkingTime(calcWorkingTime(attendance));
		}
		
		return attendanceLists;
	}
	
	/* for break-time minutes to String */
	private String calcBreakTime(AdminAttendanceDto attendance) {
		
		long breakMinutes = calcBreakMinutes(attendance);
			
				if (breakMinutes == 0) {
					return "-";
				}
			
		return formatMinutes(breakMinutes);
	}
	
	/* for working minutes to String */
	private String calcWorkingTime(AdminAttendanceDto attendance) {
		
		long workMinutes = calcWorkingMinutes(attendance);
		
			if (workMinutes == 0) {
				return "-";
			}
		
		return formatMinutes(workMinutes);
	}
	
	/* for working minutes */
	private long calcWorkingMinutes(AdminAttendanceDto attendance) {
		
		if (attendance.getCheckIn() == null ||
			attendance.getCheckOut()== null) {
			return 0;
		}
		
	long breakMinutes = calcBreakMinutes(attendance);
		
	return Duration.between(
			attendance.getCheckIn(),
			attendance.getCheckOut()
			).toMinutes() - breakMinutes;
	}
	
	/* for break-time minutes */
	private long calcBreakMinutes(AdminAttendanceDto attendance) {
		
		if (attendance.getBreakStart() == null ||
			attendance.getBreakEnd() == null) {
			return 0;
		}
		return Duration.between(
				attendance.getBreakStart(),
				attendance.getBreakEnd()
		).toMinutes();
	}
	
	/* for change minutes to hour & minutes */
	private String formatMinutes(long minutes) {
		return String.format(
				"%d時間%d分",
				minutes / 60,
				minutes % 60
				);
	}
	
	/* workerNo の有無確認 */
	public boolean workerNoExist(String workerNo) {
		int countWorkerNo = workerMapper.countByWorkerNo(workerNo);
		
		return countWorkerNo > 0;
	}
	public boolean emailExist(String email) {
		int countEmail = workerMapper.countByEmail(email);
		
		return countEmail > 0;
	}
	
	/* insert worker signup */
	public void workerSignup(EWorker worker) {
		workerMapper.insertWorkerSignup(worker);
	}
}
