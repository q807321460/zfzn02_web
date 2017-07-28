package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.entity.OrderInfo;
import com.zfznjj.smarthome.model.SceneOrder;

public interface SceneOrderDao {

	int insert(SceneOrder sceneOrder);
	SceneOrder select(String masterCode, String electricCode, String orderInfo, int sceneIndex);
	List<SceneOrder> ugetSceneOrders(String masterCode);
	void updateIsReaded(int orderID);
	void delete();
}
