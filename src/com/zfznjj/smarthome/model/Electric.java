package com.zfznjj.smarthome.model;

public class Electric {
	private int electricId;
	private String masterCode;
	private int roomIndex;
	private int electricIndex;
	private String electricName;
	private Integer electricSequ;
	private String electricCode;
	private int electricType;
	private String orderInfo;
	private Integer sceneIndex;
	private int belong;
	private String extras;
	private String extraTime;
	
	public Electric() {
		super();
	}
	public int getElectricId() {
		return electricId;
	}
	public void setElectricId(int electricId) {
		this.electricId = electricId;
	}
	
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public int getRoomIndex() {
		return roomIndex;
	}
	public void setRoomIndex(int roomIndex) {
		this.roomIndex = roomIndex;
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
	public Integer getElectricSequ() {
		return electricSequ;
	}
	public void setElectricSequ(Integer electricSequ) {
		this.electricSequ = electricSequ;
	}
	public String getElectricCode() {
		return electricCode;
	}
	public void setElectricCode(String electricCode) {
		this.electricCode = electricCode;
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
	
	public Integer getSceneIndex() {
		return sceneIndex;
	}
	public void setSceneIndex(Integer sceneIndex) {
		this.sceneIndex = sceneIndex;
	}
	public int getBelong() {
		return belong;
	}
	public void setBelong(int belong) {
		this.belong = belong;
	}
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}
	
	public String getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(String extraTime) {
		this.extraTime = extraTime;
	}
	@Override
	public String toString() {
		return "Electric [electricId=" + electricId + ", masterCode=" + masterCode + ", roomIndex=" + roomIndex
				+ ", electricIndex=" + electricIndex + ", electricName=" + electricName + ", electricSequ="
				+ electricSequ + ", electricCode=" + electricCode + ", electricType=" + electricType + ", orderInfo="
				+ orderInfo + ", sceneIndex=" + sceneIndex + ", belong=" + belong + ", extras=" + extras
				+ ", extraTime=" + extraTime + "]";
	}
	
}
