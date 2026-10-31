package com.zawzaw.worktime.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.zawzaw.worktime.mapper.WorkerMapper;
import com.zawzaw.worktime.model.dto.AdminAttendanceDto;
import com.zawzaw.worktime.model.entity.EWorker;
import com.zawzaw.worktime.workervalidation.AttendanceEditForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerService {
	
	private final WorkerMapper workerMapper;
	private final PasswordEncoder passwordEncoder;
	
	/*　ホームページ */
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
	
	/* 社員管理ページ */
	/* insert worker signup */
	public void workerSignup(EWorker worker) {
		String encodedPassword = passwordEncoder.encode(worker.getPassword());
		worker.setPassword(encodedPassword);
		workerMapper.insertWorkerSignup(worker);
	}
	
	/* workerNoの有無確認 */
	public boolean workerNoExist(String workerNo) {
		int countWorkerNo = workerMapper.countByWorkerNo(workerNo);
		return countWorkerNo > 0;
	}
	public boolean emailExist(String email) {
		int countEmail = workerMapper.countByEmail(email);
		return countEmail > 0;
	}
	
	/* worker情報詳細 */
	public EWorker findWorkerDetail(Long id) {
		return workerMapper.findWorkerById(id);
	}
	
	/* worker検索 */
	public List<EWorker> WorkerSearch(String keyword) {
		return workerMapper.findByNameOrWorkerNo(keyword);
	}
	
	/* worker情報編集 */
	public EWorker findWorkerEdit(Long id) {
		return workerMapper.findWorkerById(id);
	}
	
	/* worker情報更新 */
	public boolean workerUpdate(EWorker worker) {
		int updateWorker = workerMapper.updateWorker(worker);
		return updateWorker > 0;
	}
	
	/* worker情報削除 */
	public boolean deleteWorker(Long id) {
		return workerMapper.deleteWorker(id) > 0;
	}
	
	/* 勤怠管理ページ */
	/* 勤怠検査 */
	public List<AdminAttendanceDto> searchWorkerAttendance(
			LocalDate checkDate,
			String workerNo) {
		List<AdminAttendanceDto> attendanceLists = workerMapper.searchWorkerAttendance(checkDate, workerNo);
		for(AdminAttendanceDto attendance : attendanceLists) {
			attendance.setBreakTime(calcBreakTime(attendance));
			attendance.setWorkingTime(calcWorkingTime(attendance));
		}
		return attendanceLists;
	}
	/* 本日の勤怠歴 */
	public List<AdminAttendanceDto> findAdminAttendance() {
		List<AdminAttendanceDto> attendanceLists = workerMapper.findAdminAttendance();
		for(AdminAttendanceDto attendance : attendanceLists) {
			attendance.setBreakTime(calcBreakTime(attendance));
			attendance.setWorkingTime(calcWorkingTime(attendance));
		}
		return attendanceLists;
	}
	/* 月ごとのまと勤怠歴 */
	public List<AdminAttendanceDto> findMonthlyDailyAttendance(){
		
		// ① - DBから勤務と休憩の明細を取得
		List<AdminAttendanceDto> rows =
				workerMapper.findAdminAttendance();
		
		// ② - 同じ勤務の休憩をまとめる
		List<AdminAttendanceDto> workList = 
				summarizeByWork(rows);
		
		// ③ - 同じ社員 - 同じ日付の勤務をまとめる
		List<AdminAttendanceDto> dailyList = 
				summarizeByDay(workList);
		
		return dailyList;
	}
	
	private List<AdminAttendanceDto> summarizeByWork(
			List<AdminAttendanceDto> rows) {
		Map<Long,AdminAttendanceDto> workMap =
				new LinkedHashMap<>();
		for(AdminAttendanceDto row : rows) {
			Long workTimeId = row.getWorkTimeId();
			if(workTimeId == null) {
				throw new IllegalStateException(
						"workTimeIdを取得してください");
			}
			// 退勤済みの勤務を対象
			if(row.getCheckIn() == null ||
			   row.getCheckOut() == null) {
				continue;
			}
			AdminAttendanceDto work = workMap.get(workTimeId);
			// まだ登録していない勤務なら、新しく作る
			if(work == null) {
				work = new AdminAttendanceDto();
				work.setWorkTimeId(workTimeId);
				work.setWorkerNo(row.getWorkerNo());
				work.setWorkerName(row.getWorkerName());
				work.setCheckDate(row.getCheckDate());
				work.setCheckIn(row.getCheckIn());
				work.setCheckOut(row.getCheckOut());
				
				workMap.put(workTimeId, work);
			}
			// 同じ勤務の休憩を足す
			long totalBreakMinutes = work.getBreakMinutes() +
					calcBreakMinutes(row);
			work.setBreakMinutes(totalBreakMinutes);
		}
		for(AdminAttendanceDto work : workMap.values()) {
			// 合計休憩を引いて、勤務１回分実働を計算
			long workingMinutes = 
					calcWorkingMinutes(work,work.getBreakMinutes());
			work.setWorkingMinutes(workingMinutes);
			
			//　明細画面に表示する文字列
			work.setBreakTime(
				work.getBreakMinutes() == 0
					? "-"
					:
				formatMinutes(work.getBreakMinutes())
					);
			work.setWorkingTime(formatMinutes(workingMinutes));
		}
		return new ArrayList<>(workMap.values());
	}
	
	private List<AdminAttendanceDto> summarizeByDay(
			List<AdminAttendanceDto> workList){
		// 日付と社員番号の組み合わせ
		record DailyKey(LocalDate date, String workerNo) {}

	    Map<DailyKey, AdminAttendanceDto> dailyMap =
	            new LinkedHashMap<>();

	    for (AdminAttendanceDto work : workList) {

	        DailyKey key = new DailyKey(
	                work.getCheckDate(),
	                work.getWorkerNo()
	        );

	        AdminAttendanceDto daily = dailyMap.get(key);

	        // まだ登録していない社員・日付なら、新しく作る
	        if (daily == null) {
	            daily = new AdminAttendanceDto();

	            daily.setWorkerNo(work.getWorkerNo());
	            daily.setWorkerName(work.getWorkerName());
	            daily.setCheckDate(work.getCheckDate());
	            daily.setCheckIn(work.getCheckIn());
	            daily.setCheckOut(work.getCheckOut());

	            dailyMap.put(key, daily);
	        }

	        // 最初の出勤時間
	        if (work.getCheckIn().isBefore(daily.getCheckIn())) {
	            daily.setCheckIn(work.getCheckIn());
	        }

	        // 最後の退勤時間
	        if (work.getCheckOut().isAfter(daily.getCheckOut())) {
	            daily.setCheckOut(work.getCheckOut());
	        }

	        // 休憩を合計
	        daily.setBreakMinutes(
	                daily.getBreakMinutes() + work.getBreakMinutes()
	        );

	        // 各勤務の実働を合計
	        daily.setWorkingMinutes(
	                daily.getWorkingMinutes() + work.getWorkingMinutes()
	        );
	    }

	    for (AdminAttendanceDto daily : dailyMap.values()) {

	        daily.setBreakTime(
	                daily.getBreakMinutes() == 0
	                        ? "-"
	                        : formatMinutes(daily.getBreakMinutes())
	        );

	        daily.setWorkingTime(
	                formatMinutes(daily.getWorkingMinutes())
	        );
	    }

	    return new ArrayList<>(dailyMap.values());
	}
	/* 勤怠編集 */
	public List<AdminAttendanceDto> findEditAttendance(Long id){
		return workerMapper.findEditAttendance(id);
	}
	/* 勤怠の時間削除 */
	@Transactional
	public void deleteAttendance(Long id) {
		
		// 子データの休憩を削除
		workerMapper.deleteBreaksByWorkTimeId(id);
		// 親データの勤務を削除
		workerMapper.deleteWorkTime(id);
	}
	
	public void updateAttendance(Long id, AttendanceEditForm form) {

	    // 同日内の勤務として、時刻の前後を確認
	    if (!form.getCheckOut().isAfter(form.getCheckIn())) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "退勤時刻は出勤時刻より後にしてください"
	        );
	    }

	    int count = workerMapper.updateAttendance(
	            id,
	            form.getCheckIn(),
	            form.getCheckOut()
	    );

	    if (count == 0) {
	        throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND,
	                "勤怠データが見つかりません"
	        );
	    }
	}
	// 時間計算
	// for break-time minutes to String
	private String calcBreakTime(AdminAttendanceDto attendance) {
		long breakMinutes = calcBreakMinutes(attendance);
				if (breakMinutes == 0) {
					return "-";
				}
		return formatMinutes(breakMinutes);
	}
	
	// for working minutes to String
	private String calcWorkingTime(AdminAttendanceDto attendance) {
		long workMinutes = calcWorkingMinutes(attendance);
			if (workMinutes == 0) {
				return "-";
			}
		return formatMinutes(workMinutes);
	}
	
	// 既存勤務の処理 : 休憩1回分
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
	
	// 同じ勤務の休憩を全て合計後
	private long calcWorkingMinutes(
			AdminAttendanceDto attendance,
			long totalBreakMinutes) {		
		if(attendance.getCheckIn() == null ||
			attendance.getCheckOut() == null) {
			return 0;
		}
		return Duration.between(
				attendance.getCheckIn(),
				attendance.getCheckOut()
			).toMinutes() - totalBreakMinutes;
	}
	
	// for break-time minutes
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
	
	// for change minutes to hour & minutes
	private String formatMinutes(long minutes) {
		return String.format(
				"%d時間%d分",
				minutes / 60,
				minutes % 60
				);
	}
}
