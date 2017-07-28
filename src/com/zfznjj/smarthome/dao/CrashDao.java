package com.zfznjj.smarthome.dao;

import com.zfznjj.smarthome.model.CrashLog;

/** 
* @author  jia 
* @date 创建时间：2017年4月12日 下午3:32:00
* @Description 
* @version 1.0  
*/
public interface CrashDao {
	
	int save(CrashLog crashLog);
}
