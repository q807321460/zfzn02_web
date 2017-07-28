package com.zfznjj.smarthome.model;

import java.sql.Timestamp;

public class ElectricOrder {

	private Integer id;
	private String masterCode;
	private String electricCode;
	private String orderData;
	private String orderInfo;
	private String writeTime;
	private String readTime;
	private Character isReaded;
	public ElectricOrder() {
		super();
	}
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
		return "ElectricOrder [id=" + id + ", masterCode=" + masterCode + ", electricCode=" + electricCode + ", orderData="
				+ orderData + ", orderInfo=" + orderInfo + ", writeTime=" + writeTime + ", readTime=" + readTime
				+ ", isReaded=" + isReaded + "]";
	}
	
	
}
