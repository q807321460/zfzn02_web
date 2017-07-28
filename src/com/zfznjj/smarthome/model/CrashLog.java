package com.zfznjj.smarthome.model;

import java.sql.Blob;

/** 
* @author  jia 
* @date 创建时间：2017年4月12日 下午3:18:20
* @Description 
* @version 1.0  
*/
public class CrashLog {

	private int id;
	private String logName;
	private byte[] logDetail;
	private String logTime;
	private String appName;
	public CrashLog() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	
	public byte[] getLogDetail() {
		return logDetail;
	}
	public void setLogDetail(byte[] logDetail) {
		this.logDetail = logDetail;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
	
	
}
