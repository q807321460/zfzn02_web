package com.zfznjj.smarthome.dao;

import com.zfznjj.smarthome.model.MasterNode;

public interface MasterNodeDao {
	
	int saveOrUpdate(MasterNode masterNode);
	int delete(String masterCode);
	MasterNode select(String masterCode);
	int giveUpAdmin(String masterCode, String owner);
	int getAdmin(String masterCode, String owner);
	int updateSharedElectricTime(String masterCode);
	int updateSharedSceneTime(String masterCode);
	String getAdminCode(String masterCode);
	int updateMasterVersion(String masterCode, String masterVersion);
}
