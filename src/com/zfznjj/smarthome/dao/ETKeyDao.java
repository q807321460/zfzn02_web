package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.model.ETKey;

public interface ETKeyDao {
	
	int save(ETKey etKey);
	int save(List<ETKey> list);
	int updateIRKeyValue(String masterCode, int electricIndex, int keyKey, String keyValue);
	ETKey query(String masterCode, int electricIndex, int keyKey);
	List<ETKey> loadKeyByElectric(String masterCode, int electricIndex);
	int delete(String masterCode, int electricIndex);
}
