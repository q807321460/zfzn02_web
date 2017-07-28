package com.zfznjj.smarthome.dao;

import java.util.List;

import com.zfznjj.smarthome.model.SceneElectric;

public interface SceneElectricDao {
	int saveOrUpdate(SceneElectric sceneElectric);
	//ɾ������ʱɾ�������龰ģʽ���������ĸõ������õ���δ�ظ����
	int delete(String masterCode, String electricCode);
	//ɾ������ʱɾ�������龰ģʽ���������ĸõ������õ����ظ����
	int delete(String masterCode, int electricIndex);
	//�����ɾ��ĳһ�龰�еĵ���
	int delete(String masterCode, int electricIndex, int sceneIndex);
	int deleteBySceneIndex(String masterCode, int sceneIndex);
	int updateSceneElectricOrder(String masterCode, int electricIndex, int sceneIndex, String electricOrder);
	SceneElectric select(String masterCode, String electricCode, String orderInfo,int sceneIndex);
	List<SceneElectric> select(String masterCode);	
	List<SceneElectric> select(String masterCode, int electricIndex);
	List<SceneElectric> select(String masterCode, int electricIndex, int sceneIndex);
}
