package com.zfznjj.smarthome.model;

import java.sql.Timestamp;

public class ChildNode {
	private int childNodeId;
	private String masterCode;
	private String electricCode;
	private String electricState;
	private String stateInfo;
	private String changeTime;
	public ChildNode() {
		super();
	}
	public int getChildNodeId() {
		return childNodeId;
	}
	public void setChildNodeId(int childNodeId) {
		this.childNodeId = childNodeId;
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
	public String getElectricState() {
		return electricState;
	}
	public void setElectricState(String electricState) {
		this.electricState = electricState;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	@Override
	public String toString() {
		return "ChildNode [childNodeId=" + childNodeId + ", masterCode=" + masterCode + ", electricCode=" + electricCode
				+ ", electricState=" + electricState + ", stateInfo=" + stateInfo + ", changeTime=" + changeTime + "]";
	}
	
}
