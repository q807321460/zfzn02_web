package com.zfznjj.smarthome.entity;

public class AdminElectricBean {
	private String masterCode;
	private String accountCode;
	private int electricIndex;
	private int admin;
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
	public int getElectricIndex() {
		return electricIndex;
	}
	public void setElectricIndex(int electricIndex) {
		this.electricIndex = electricIndex;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	@Override
	public String toString() {
		return "AdminElectricBean [masterCode=" + masterCode + ", accountCode=" + accountCode + ", electricIndex="
				+ electricIndex + ", admin=" + admin + "]";
	}
	
}
