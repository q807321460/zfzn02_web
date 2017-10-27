package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.entity.ElectricForVoice;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.User;

public interface ElectricDao {
	int saveOrUpdate(Electric electric);
	int delete(String masterCode, int electricIndex);
	int updateElectricSequ(String masterCode, int electricSequ,int roomIndex);
	int getMaxElectricSequ(String masterCode, int roomIndex);
	int saveDoorRecord(String masterCode, String electricCode, String stateInfo);
	Electric select(String masterCode, int electricIndex);
	Electric select(String masterCode, int electricIndex, String extras);
	List<Electric> select(String masterCode);
	List<Electric> select(String masterCode, String electricCode);
	List<ElectricForVoice> selectForVoice(String masterCode);
}
