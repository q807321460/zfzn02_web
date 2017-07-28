package com.zfznjj.smarthome.entity;

public class ElectricState {

	private String masterCode;
	private String electricCode;
	private String electricState;
	private String stateInfo;
	public ElectricState() {
		// TODO Auto-generated constructor stub
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
	@Override
	public String toString() {
		return "ElectricState [masterCode=" + masterCode + ", electricCode=" + electricCode + ", electricState="
				+ electricState + ", stateInfo=" + stateInfo + "]";
	}
	
	
}
