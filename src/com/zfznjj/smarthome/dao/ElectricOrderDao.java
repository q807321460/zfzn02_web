package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.entity.OrderInfo;
import com.zfznjj.smarthome.model.ElectricOrder;

public interface ElectricOrderDao {

	int insert(ElectricOrder electricOrder);
	List<OrderInfo> ugetElectricOrders(String masterCode);
	void updateIsReaded(int orderID);
	void delete();
}
