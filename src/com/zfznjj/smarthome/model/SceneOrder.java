package com.zfznjj.smarthome.model;

public class SceneOrder {
	private Integer id;
	private String masterCode;
	private String electricCode;
	private String orderData;
	private String orderInfo;
	private int sceneIndex;
//	private String extras;
	private String writeTime;
	private String readTime;
	private Character isReaded;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getOrderData() {
		return orderData;
	}
	public void setOrderData(String orderData) {
		this.orderData = orderData;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public int getSceneIndex() {
		return sceneIndex;
	}
	public void setSceneIndex(int sceneIndex) {
		this.sceneIndex = sceneIndex;
	}
//	public String getExtras() {
//		return extras;
//	}
//	public void setExtras(String extras) {
//		this.extras = extras;
//	}
	public String getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	public Character getIsReaded() {
		return isReaded;
	}
	public void setIsReaded(Character isReaded) {
		this.isReaded = isReaded;
	}
	@Override
	public String toString() {
		return "SceneOrder [id=" + id + ", masterCode=" + masterCode + ", electricCode=" + electricCode + ", orderData="
				+ orderData + ", orderInfo=" + orderInfo + ", sceneIndex=" + sceneIndex + ", writeTime=" + writeTime
				+ ", readTime=" + readTime + ", isReaded=" + isReaded + "]";
	}
	
}
