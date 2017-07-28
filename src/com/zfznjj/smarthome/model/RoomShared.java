package com.zfznjj.smarthome.model;

public class RoomShared {
	private int sRoomId;
	private int roomId;
	private int userId;
	private String sRoomName;
	private int sRoomSequ;
	public RoomShared() {
		// TODO Auto-generated constructor stub
	}
	public int getsRoomId() {
		return sRoomId;
	}
	public void setsRoomId(int sRoomId) {
		this.sRoomId = sRoomId;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getsRoomName() {
		return sRoomName;
	}
	public void setsRoomName(String sRoomName) {
		this.sRoomName = sRoomName;
	}
	public int getsRoomSequ() {
		return sRoomSequ;
	}
	public void setsRoomSequ(int sRoomSequ) {
		this.sRoomSequ = sRoomSequ;
	}
	@Override
	public String toString() {
		return "RoomShared [sRoomId=" + sRoomId + ", roomId=" + roomId + ", userId=" + userId + ", sRoomName="
				+ sRoomName + ", sRoomSequ=" + sRoomSequ + "]";
	}
	
}
