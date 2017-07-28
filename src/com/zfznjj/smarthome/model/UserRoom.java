package com.zfznjj.smarthome.model;

public class UserRoom {
	private int roomId;
	private String masterCode;
	private int roomIndex;
	private String roomName;
	private int roomSequ;
	private int roomImg;
	private String extraTime;
	public UserRoom() {
		super();
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getMasterCode() {
		return masterCode;
	}
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	public int getRoomIndex() {
		return roomIndex;
	}
	public void setRoomIndex(int roomIndex) {
		this.roomIndex = roomIndex;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getRoomSequ() {
		return roomSequ;
	}
	public void setRoomSequ(int roomSequ) {
		this.roomSequ = roomSequ;
	}
	public int getRoomImg() {
		return roomImg;
	}
	public void setRoomImg(int roomImg) {
		this.roomImg = roomImg;
	}
	
	public String getExtraTime() {
		return extraTime;
	}
	public void setExtraTime(String extraTime) {
		this.extraTime = extraTime;
	}
	@Override
	public String toString() {
		return "UserRoom [roomId=" + roomId + ", masterCode=" + masterCode + ", roomIndex=" + roomIndex + ", roomName="
				+ roomName + ", roomSequ=" + roomSequ + ", roomImg=" + roomImg + "]";
	}
	
}
