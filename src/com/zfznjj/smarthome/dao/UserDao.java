package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.model.User;

public interface UserDao {

	int saveOrUpdate(User user);
	int delete(String accountCode,String masterCode);
	User select(String accountCode, String masterCode);
	int updateUserIP(String masterCode, String userIP);
	int updateUserName(String accountCode, String masterCode, String userName);
	int updateUserELectricTime(String masterCode);
	int updateUserELectricTime(String accountCode, String masterCode);
	int updateUserAreaTime(String masterCode);
	int updateUserSceneTime(String masterCode);
	int updateUserSceneELectricTime(String masterCode);
	List<User> selectByAccountCode(String accountCode);
	List<User> selectByMasterCode(String masterCode);
	List<User> selectByMasterCodeAll(String masterCode);
	List<User> selectAllByMasterCode(String masterCode);
	List<String> seleteAccountCodesByMaster(String masterCode);
	
}
