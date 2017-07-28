package com.zfznjj.smarthome.model;

import java.sql.Timestamp;

public class User {
	private int userId;
	private String masterCode;
	private String accountCode;
	private String userName;
	private String userIp;
	private int isAdmin;
	private String areaTime;
	private String sceneTime;
	private String electricTime;
	private String sceneELectricTime;
	private String extraTime;
	public User() {
		super();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getAreaTime() {
		return areaTime;
	}
	public void setAreaTime(String areaTime) {
		this.areaTime = areaTime;
	}
	public String getSceneTime() {
		return sceneTime;
	}
	public void setSceneTime(String sceneTime) {
		this.sceneTime = sceneTime;
	}
	
	public String getElectricTime() {
		return electricTime;
	}
	public void setElectricTime(String electricTime) {
		this.electricTime = electricTime;
	}
	public String getSceneELectricTime() {
		return sceneELectricTime;
	}
	public void setSceneELectricTime(String sceneELectricTime) {
		this.sceneELectricTime = sceneELectricTime;
	}
	
	public String getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(String extraTime) {
		this.extraTime = extraTime;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", masterCode=" + masterCode + ", accountCode=" + accountCode + ", userName="
				+ userName + ", userIp=" + userIp + ", isAdmin=" + isAdmin + ", areaTime=" + areaTime + ", sceneTime="
				+ sceneTime + ", electricTime=" + electricTime + ", sceneELectricTime=" + sceneELectricTime
				+ ", extraTime=" + extraTime + "]";
	}
	
}
