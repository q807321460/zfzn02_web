package com.zfznjj.smarthome.entity;

public class ElectricSharedLoacl {
	private String masterCode;
	private String accountCode;
	private String electricCode;
	private int electricIndex;
	private int electricType;
	private String orderInfo;
	private String electricName;
	private int roomIndex;
	private int isShared;
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
	public String getElectricCode() {
		return electricCode;
	}
	public void setElectricCode(String electricCode) {
		this.electricCode = electricCode;
	}
	public int getElectricIndex() {
		return electricIndex;
	}
	public void setElectricIndex(int electricIndex) {
		this.electricIndex = electricIndex;
	}
	public int getElectricType() {
		return electricType;
	}
	public void setElectricType(int electricType) {
		this.electricType = electricType;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getElectricName() {
		return electricName;
	}
	public void setElectricName(String electricName) {
		this.electricName = electricName;
	}
	
	public int getRoomIndex() {
		return roomIndex;
	}
	public void setRoomIndex(int roomIndex) {
		this.roomIndex = roomIndex;
	}
	public int getIsShared() {
		return isShared;
	}
	public void setIsShared(int isShared) {
		this.isShared = isShared;
	}
	@Override
	public String toString() {
		return "ElectricSharedLoacl [masterCode=" + masterCode + ", accountCode=" + accountCode + ", electricCode="
				+ electricCode + ", electricIndex=" + electricIndex + ", electricType=" + electricType + ", orderInfo="
				+ orderInfo + ", electricName=" + electricName + ", roomIndex=" + roomIndex + ", isShared=" + isShared
				+ "]";
	}
	
	
	
}
