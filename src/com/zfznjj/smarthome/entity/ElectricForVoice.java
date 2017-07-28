package com.zfznjj.smarthome.entity;

public class ElectricForVoice {
	private String electricCode;
	private String electricName;
	private int electricIndex;
	private int electricType;
	private String roomName;
	private String orderInfo;
	public ElectricForVoice(){}
	public String getElectricCode() {
		return electricCode;
	}
	public void setElectricCode(String electricCode) {
		this.electricCode = electricCode;
	}
	public String getElectricName() {
		return electricName;
	}
	public void setElectricName(String electricName) {
		this.electricName = electricName;
	}
	
	
	public int getElectricType() {
		return electricType;
	}
	public void setElectricType(int electricType) {
		this.electricType = electricType;
	}
	public int getElectricIndex() {
		return electricIndex;
	}
	public void setElectricIndex(int electricIndex) {
		this.electricIndex = electricIndex;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	@Override
	public String toString() {
		return "ElectricForVoice [electricCode=" + electricCode + ", electricName=" + electricName + ", electricIndex="
				+ electricIndex + ", electricType=" + electricType + ", roomName=" + roomName + ", orderInfo="
				+ orderInfo + "]";
	};
	
	
}
