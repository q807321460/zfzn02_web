package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.model.AlarmRecord;

public interface AlarmRecordDao {
	int saveOrUpdate(AlarmRecord alarmRecord);
	List<AlarmRecord> select(String electricCode);
	int getMaxRecordSequ(String masterCode);
	int delete(String electricCode, int recordSequ);
	int updateAlarmRecordSequ(String electricCode);
}
