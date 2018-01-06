package com.zfznjj.smarthome.dao;

import java.util.List;

import org.hibernate.sql.Delete;

import com.zfznjj.smarthome.model.DoorRecord;

public interface DoorRecordDao {
	int saveOrUpdate(DoorRecord doorRecord);
	List<DoorRecord> select(String masterCode, String electricCode);
	List<Object[]> select(String electricCode);
	DoorRecord selectTop(String electricCode); // 获取最新的开锁记录
	int getMaxRecordSequ(String electricCode);
	int delete(String electricCode, int recordSequ);
	int updateDoorRecordSequ(String electricCode);
}
