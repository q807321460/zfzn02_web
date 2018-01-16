package com.zfznjj.smarthome.model;

import java.sql.Timestamp;

public class Scene {
	private int sceneId;
	private String masterCode;
	private String sceneName;
	private int sceneIndex;
	private int sceneSequ;
	private int sceneImg;
	private String buildTime;
	private String detailTiming; // 具体的某个时间点的定时
	private String weeklyDays; // 每周有哪几天定时
	private String daliyTiming; // 每天的定时时间

	public Scene(){}
	
	public int getSceneId() {
		return sceneId;
	}
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}
	public int getSceneSequ() {
		return sceneSequ;
	}
	public void setSceneSequ(int sceneSequ) {
		this.sceneSequ = sceneSequ;
	}
	public int getSceneImg() {
		return sceneImg;
	}
	public void setSceneImg(int sceneImg) {
		this.sceneImg = sceneImg;
	}
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public String getSceneName() {
		return sceneName;
	}
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public int getSceneIndex() {
		return sceneIndex;
	}
	public void setSceneIndex(int sceneIndex) {
		this.sceneIndex = sceneIndex;
	}
	public String getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public String getDetailTiming() {
		return detailTiming;
	}

	public void setDetailTiming(String detailTiming) {
		this.detailTiming = detailTiming;
	}

	public String getWeeklyDays() {
		return weeklyDays;
	}

	public void setWeeklyDays(String weeklyDays) {
		this.weeklyDays = weeklyDays;
	}

	public String getDaliyTiming() {
		return daliyTiming;
	}

	public void setDaliyTiming(String daliyTiming) {
		this.daliyTiming = daliyTiming;
	}
	
//	@Override
//	public String toString() {
//		return "Scene [sceneId=" + sceneId + ", masterCode=" + masterCode + ", sceneName=" + sceneName + ", sceneIndex="
//				+ sceneIndex + ", sceneSequ=" + sceneSequ + ", sceneImg=" + sceneImg + ", buildTime=" + buildTime
//				+ ", extraTime=" + extraTime + "]";
//	}
//	
}
