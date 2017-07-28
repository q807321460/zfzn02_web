package com.zfznjj.smarthome.entity;

public class OrderInfo {
	private int orderID;
	
	private String masterCode;
	private String electricCode;
	private String order;
	private String orderInfo;
	public OrderInfo() {
		// TODO Auto-generated constructor stub
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
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
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	@Override
	public String toString() {
		return "ElectricOrder [masterCode=" + masterCode + ", electricCode=" + electricCode + ", order=" + order
				+ ", orderInfo=" + orderInfo + "]";
	}
	
}
