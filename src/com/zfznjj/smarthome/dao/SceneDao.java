package com.zfznjj.smarthome.dao;


import java.util.List;

import com.zfznjj.smarthome.model.Scene;

public interface SceneDao {
	int saveOrUpdate(Scene scene);
	int delete(String masterCode, int sceneIndex);
	int updateSceneSequ(String masterCode, int sceneSequ);
	Scene select(String masterCode, int sceneIndex);
	List<Scene> select(String masterCode);
	int getMaxSceneSequ(String masterCode);
}
