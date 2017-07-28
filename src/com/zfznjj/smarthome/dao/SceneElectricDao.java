package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.model.SceneElectric;

public interface SceneElectricDao {
	int saveOrUpdate(SceneElectric sceneElectric);
	//删除电器时删除所有情景模式中所包含的该电器：该电器未重复添加
	int delete(String masterCode, String electricCode);
	//删除电器时删除所有情景模式中所包含的该电器：该电器重复添加
	int delete(String masterCode, int electricIndex);
	//具体地删除某一情景中的电器
	int delete(String masterCode, int electricIndex, int sceneIndex);
	int deleteBySceneIndex(String masterCode, int sceneIndex);
	int updateSceneElectricOrder(String masterCode, int electricIndex, int sceneIndex, String electricOrder);
	SceneElectric select(String masterCode, String electricCode, String orderInfo,int sceneIndex);
	List<SceneElectric> select(String masterCode);	
	List<SceneElectric> select(String masterCode, int electricIndex);
	List<SceneElectric> select(String masterCode, int electricIndex, int sceneIndex);
}
