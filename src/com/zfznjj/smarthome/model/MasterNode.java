package com.zfznjj.smarthome.model;

public class MasterNode {
	private int masterId;
	private String masterCode;
	private String masterName;
	private String owner;
	private String sharedElectricTime;
	private String sharedSceneTime;
	public MasterNode() {
		super();
	}
	public int getMasterId() {
		return masterId;
	}
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public String getMasterName() {
		return masterName;
	}
	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSharedElectricTime() {
		return sharedElectricTime;
	}
	public void setSharedElectricTime(String sharedElectricTime) {
		this.sharedElectricTime = sharedElectricTime;
	}
	public String getSharedSceneTime() {
		return sharedSceneTime;
	}
	public void setSharedSceneTime(String sharedSceneTime) {
		this.sharedSceneTime = sharedSceneTime;
	}
	
	@Override
	public String toString() {
		return "MasterNode [masterId=" + masterId + ", masterCode=" + masterCode + ", masterName=" + masterName
				+ ", owner=" + owner + ", sharedElectricTime=" + sharedElectricTime + ", sharedSceneTime="
				+ sharedSceneTime + "]";
	}
	
	
}
