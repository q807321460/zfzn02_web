package com.zfznjj.smarthome.model;

public class SceneElectric {
	private int seId;
	private String masterCode;
	private int electricIndex;
	private String electricName;
	private String electricCode;
	private String electricOrder;
	private String orderInfo;
	private int roomIndex;
	private int electricType;
	private int sceneIndex;
	private String extraTime;
	public SceneElectric(){}
	public int getSeId() {
		return seId;
	}
	public void setSeId(int seId) {
		this.seId = seId;
	}
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public String getElectricCode() {
		return electricCode;
	}
	public void setElectricCode(String electricCode) {
		this.electricCode = electricCode;
	}
	public String getElectricOrder() {
		return electricOrder;
	}
	public void setElectricOrder(String electricOrder) {
		this.electricOrder = electricOrder;
	}	
	
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(String extraTime) {
		this.extraTime = extraTime;
	}
	
	public int getElectricIndex() {
		return electricIndex;
	}
	public void setElectricIndex(int electricIndex) {
		this.electricIndex = electricIndex;
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
	public int getElectricType() {
		return electricType;
	}
	public void setElectricType(int electricType) {
		this.electricType = electricType;
	}
	public int getSceneIndex() {
		return sceneIndex;
	}
	public void setSceneIndex(int sceneIndex) {
		this.sceneIndex = sceneIndex;
	}
	@Override
	public String toString() {
		return "SceneElectric [seId=" + seId + ", masterCode=" + masterCode + ", electricIndex=" + electricIndex
				+ ", electricName=" + electricName + ", electricCode=" + electricCode + ", electricOrder="
				+ electricOrder + ", orderInfo=" + orderInfo + ", roomIndex=" + roomIndex + ", electricType="
				+ electricType + ", sceneIndex=" + sceneIndex + ", extraTime=" + extraTime + "]";
	}
	
}
