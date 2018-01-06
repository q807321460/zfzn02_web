package com.zfznjj.smarthome.model;

import java.util.function.Predicate;

public class DoorRecord {
	private int doorRecordId;
	private String masterCode;
	private String electricCode;
	private String openTime;
	private String byPerson; // ��˭��������
	private int recordSequ;
	
	public int getDoorRecordId() {
		return doorRecordId;
	}
	public void setDoorRecordId(int doorRecordId) {
		this.doorRecordId = doorRecordId;
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
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getByPerson() {
		return byPerson;
	}
	public void setByPerson(String byPerson) {
		this.byPerson = byPerson;
	}
	public int getRecordSequ() {
		return recordSequ;
	}
	public void setRecordSequ(int recordSequ) {
		this.recordSequ = recordSequ;
	}
//	@Override
//	public String toString() {
//		return "DoorRecord [electricId=" + doorRecordId + ", masterCode=" + masterCode + ", electricCode=" + electricCode
//				+ ", openTime=" + openTime + ", recordSequ=" + recordSequ + "]";
//	}
}
