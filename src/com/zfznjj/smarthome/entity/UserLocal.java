package com.zfznjj.smarthome.entity;

import java.sql.Timestamp;

public class UserLocal {
	private int userId;
	private String masterCode;
	private String accountCode;
	private String userName;
	private String userIp;
	private int isAdmin;
	private String areaTime;
	private String sceneTime;
	public UserLocal() {
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
	@Override
	public String toString() {
		return "UserLocal [userId=" + userId + ", masterCode=" + masterCode + ", accountCode=" + accountCode
				+ ", userName=" + userName + ", userIp=" + userIp + ", isAdmin=" + isAdmin + ", areaTime=" + areaTime
				+ ", sceneTime=" + sceneTime + "]";
	}
	
}
