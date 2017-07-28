package com.zfznjj.smarthome.entity;

import java.sql.Timestamp;

public class AccountLocal {
	private int accountId;
	private String accountCode;
	private String accountName;
	private String accountPhone;
	private String accountAddress;
	private String accountEmail;
	private String signTime;
	private String accountTime;
	private String userTime;
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
	@Override
	public String toString() {
		return "AccountLocal [accountId=" + accountId + ", accountCode=" + accountCode + ", accountName=" + accountName
				+ ", accountPhone=" + accountPhone + ", accountAddress=" + accountAddress + ", accountEmail="
				+ accountEmail + ", signTime=" + signTime + ", accountTime=" + accountTime + ", userTime=" + userTime
				+ "]";
	}
	
}
