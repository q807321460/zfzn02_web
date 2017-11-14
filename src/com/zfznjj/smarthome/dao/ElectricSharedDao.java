package com.zfznjj.smarthome.dao;

import java.util.List;

import org.omg.CORBA.INTERNAL;

import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.ElectricShared;

public interface ElectricSharedDao {
	void addSharedElectric(String accountCode,String masterCode);
	void addSharedElectric(String accountCode,String masterCode,int electricIndex);
	int saveOrUpdate(ElectricShared electricShared);
	int delete(int electricId, int userId);
	int deleteByUserId(int userId);
	int deleteByElectricId(int electricId);
	int deleteByElectric(String masterCode,int electricIndex);
	int deleteByUser(String accountCode, String masterCode);
	ElectricShared select(int electricId, int userId);
	List<Electric> select(String accountCode, String masterCode);
	List<ElectricSharedLoacl> select(String masterCode);
	List<ElectricSharedLoacl> select2(String masterCode, String accountCode);
	int adminElectricShared(String accountCode, String masterCode, int electricIndex,int admin);
}
