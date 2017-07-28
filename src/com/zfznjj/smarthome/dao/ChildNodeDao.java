package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.model.ChildNode;

public interface ChildNodeDao {

	int saveOrUpdate(ChildNode childNode);
	int delete(String masterCode, String electricCode);
	ChildNode select(String masterCode, String electricCode);
	
	int updateChildNodeState(String masterCode, String electricCode, String electricState, String stateInfo);
	ElectricState getState(String masterCode, String electricCode);
	List<ElectricState> getStateByMasterCode(String masterCode); 
}
