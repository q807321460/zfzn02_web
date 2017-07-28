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
	private String extraTime;
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
	
	public String getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(String extraTime) {
		this.extraTime = extraTime;
	}
	@Override
	public String toString() {
		return "Scene [sceneId=" + sceneId + ", masterCode=" + masterCode + ", sceneName=" + sceneName + ", sceneIndex="
				+ sceneIndex + ", sceneSequ=" + sceneSequ + ", sceneImg=" + sceneImg + ", buildTime=" + buildTime
				+ ", extraTime=" + extraTime + "]";
	}
	
}
