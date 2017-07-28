package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.model.Account;

public interface AccountDao {

	int saveOrUpdate(Account account);
	int delete(String accountCode);
	int updateUserTime(String accountCode);
	int updateUserTimeByMasterCode(String masterCode);
	int updateAccountTime(String accountCode, String accountTime);
	int signLeCheng(String accountCode);
	Account select(String accountCode);
	List<Account> selectAccountByMasterCode(String masterCode);
}
