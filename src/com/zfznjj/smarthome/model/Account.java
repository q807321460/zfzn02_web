package com.zfznjj.smarthome.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

public class Account {
	private int accountId;
	private String accountCode;
	private String accountName;
	private String accountPhone;
	private String accountAddress;
	private String accountEmail;
	private String password;
	private byte[] photo;
	private String signTime;
	private String accountTime;
	private String userTime;
	
	private String lePhone;
	private int leSign;
	public Account() {
		super();
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPhone() {
		return accountPhone;
	}
	public void setAccountPhone(String accountPhone) {
		this.accountPhone = accountPhone;
	}
	public String getAccountAddress() {
		return accountAddress;
	}
	public void setAccountAddress(String accountAddress) {
		this.accountAddress = accountAddress;
	}
	public String getAccountEmail() {
		return accountEmail;
	}
	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getAccountTime() {
		return accountTime;
	}
	public void setAccountTime(String accountTime) {
		this.accountTime = accountTime;
	}
	public String getUserTime() {
		return userTime;
	}
	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}
	public String getLePhone() {
		return lePhone;
	}
	public void setLePhone(String lePhone) {
		this.lePhone = lePhone;
	}
	public int getLeSign() {
		return leSign;
	}
	public void setLeSign(int leSign) {
		this.leSign = leSign;
	}
	
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountCode=" + accountCode + ", accountName=" + accountName
				+ ", accountPhone=" + accountPhone + ", accountAddress=" + accountAddress + ", accountEmail="
				+ accountEmail + ", password=" + password + ", photo=" + Arrays.toString(photo) + ", signTime="
				+ signTime + ", accountTime=" + accountTime + ", userTime=" + userTime + ", lePhone=" + lePhone
				+ ", leSign=" + leSign + "]";
	}
	

}
