package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.model.AlarmRecord;

public interface AlarmRecordDao {
	int saveOrUpdate(AlarmRecord alarmRecord);
	List<AlarmRecord> select(String masterCode);
	List<AlarmRecord> select(String masterCode, String electricCode);
	int getMaxRecordSequ(String masterCode);
	int delete(String masterCode, int recordSequ);
	int updateAlarmRecordSequ(String electricCode);
	int updateAlarmRecordSequ(String masterCode, int recordSequ);
}
