package com.zfznjj.smarthome.model;

import java.security.KeyStore.PrivateKeyEntry;

public class ElectricShared {
	private int sElecId;
	private int electricId;
	private int userId;
	private String sElecName;
	private int sElecSequ;
	private int isShared;
	public ElectricShared() {
		super();
	}
	public int getsElecId() {
		return sElecId;
	}
	public void setsElecId(int sElecId) {
		this.sElecId = sElecId;
	}
	public int getElectricId() {
		return electricId;
	}
	public void setElectricId(int electricId) {
		this.electricId = electricId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getsElecName() {
		return sElecName;
	}
	public void setsElecName(String sElecName) {
		this.sElecName = sElecName;
	}
	public int getsElecSequ() {
		return sElecSequ;
	}
	public void setsElecSequ(int sElecSequ) {
		this.sElecSequ = sElecSequ;
	}
	public int getIsShared() {
		return isShared;
	}
	public void setIsShared(int isShared) {
		this.isShared = isShared;
	}
	@Override
	public String toString() {
		return "ElectricShared [sElecId=" + sElecId + ", electricId=" + electricId + ", userId=" + userId
				+ ", sElecName=" + sElecName + ", sElecSequ=" + sElecSequ + ", isShared=" + isShared + "]";
	}
	

}
