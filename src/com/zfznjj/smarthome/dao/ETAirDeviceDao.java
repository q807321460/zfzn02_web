package com.zfznjj.smarthome.dao;

import com.zfznjj.smarthome.model.ETAirDevice;

public interface ETAirDeviceDao {
	int save(ETAirDevice etAirDevice);
	ETAirDevice query(String masterCode, int electricIndex);
	int delete(String masterCode, int electricIndex);
}
