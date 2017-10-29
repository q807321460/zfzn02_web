package com.zfznjj.smarthome.model;

public class AlarmRecord {
	private int alarmRecordId;
	private String masterCode;
	private String electricCode;
	private String electricState;
	private String stateInfo;
	private int recordSequ;
	private String alarmTime;
	private String extras;
	
	public int getAlarmRecordId() {
		return alarmRecordId;
	}
	public void setAlarmRecordId(int alarmRecordId) {
		this.alarmRecordId = alarmRecordId;
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
	public int getRecordSequ() {
		return recordSequ;
	}
	public void setRecordSequ(int recordSequ) {
		this.recordSequ = recordSequ;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}
	
	@Override
	public String toString() {
		return "AlarmRecord [alarmRecordId=" + alarmRecordId + ", masterCode=" + masterCode + ", electricCode=" + electricCode
				+ ", electricState=" + electricState + ", stateInfo=" + stateInfo + ", recordSequ=" + recordSequ + ", alarmTime=" + alarmTime + ", extras=" + extras + "]";
	}
}
