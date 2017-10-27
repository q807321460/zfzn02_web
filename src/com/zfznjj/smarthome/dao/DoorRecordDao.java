package com.zfznjj.smarthome.dao;

import java.util.List;

import org.hibernate.sql.Delete;

import com.zfznjj.smarthome.model.DoorRecord;

public interface DoorRecordDao {
	int saveOrUpdate(DoorRecord doorRecord);
	List<DoorRecord> select(String masterCode, String electricCode);
	String select2(String electricCode);
	int getMaxRecordSequ(String electricCode);
	int delete(String electricCode, int recordSequ);
	int updateDoorRecordSequ(String electricCode);
}
