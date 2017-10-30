package com.zfznjj.smarthome.service;

import java.io.IOException;
import java.util.List;

import org.apache.cxf.tools.corba.common.idltypes.IdlString;
import org.omg.CORBA.INTERNAL;

import com.zfznjj.smarthome.entity.AdminElectricBean;
import com.zfznjj.smarthome.entity.ElectricForVoice;
import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.model.AlarmRecord;
import com.zfznjj.smarthome.model.CrashLog;
import com.zfznjj.smarthome.model.ETKey;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.Scene;
import com.zfznjj.smarthome.model.SceneElectric;
import com.zfznjj.smarthome.model.User;
import com.zfznjj.smarthome.model.UserRoom;

//import sun.tools.jar.resources.jar;


public interface SmarthomeService {
	
	String validLogin(String accountCode, String password) throws Exception;
	int addAccount(String accountCode,String password, String accountName);
	int addUser(String accountCode,String masterCode, String userName, String userIp);
	List<String> getAccountCodesByMaster(String masterCode);
	String getAdminAccountCode(String masterCode);
	int addSharedUser(String accountCode,String masterCode,String userName,String userIp);
	int addUserRoom(String masterCode, int roomIndex, String roomName, int roomSequ, int roomImg);
	int addScene(String accountCode, String masterCode, String sceneName, int sceneIndex, int sceneSequ, int sceneImg);
	int addSceneElectric(String masterCode, String electricCode, String electricOrder, String accountCode,int sceneIndex, String orderInfo,
			int electricIndex,String electricName, int roomIndex, int electricType);
	int addElectric(String masterCode, int electricIndex, String electricCode,int roomIndex, String electricName,
			int electricSequ, int electricType, String extras, String orderInfo) throws Exception;
	int addELectricOrder(String masterCode,String electricCode, String order, String orderInfo);
	int addSceneOrder(String masterCode, String electricCode, String electricOrder, String orderInfo, int sceneIndex);
	
	int addETKeys(String keyJsonString);
	int addETAirDevice(String eTAirJsonString);
	int addCrashLog(String logName, byte[] logDetail, String appName);
	int updateSensorExtras(String masterCode, String electricCode, int electricIndex, String extras);
	
	/**
	 * 更改账户密码done
	 * @param userName
	 * @param password
	 * @return
	 */
	String updateAccountPassword(String accountCode, String oldPassword,String newPassword);
	int updateAccount(String accountCode, String accountName, String accountPhone,String accountAddress,String accountEmail);
	int updateAccountPhoto(String accountCode, byte[] photo);
	int updateUserIP(String masterCode, String userIP);
	int updateUserRoom(String masterCode, int roomIndex, String roomName, int roomImg);
	int updateRoomSequ(String masterCode, int roomSequ);
	int updateElectric(String masterCode, String electricCode, int electricIndex, String electricName, int sceneIndex);
	int updateSceneElectrics(String masterCode, int electricIndex, String electricName);
	
	int updateSceneSequ(String masterCode, int sceneSequ);
	
	
	int updateIRKeyValue(String masterCode, int electricIndex, int keyKey, String keyValue);
	int adminSharedElectric(List<AdminElectricBean> adminElectricBeans);
	int deleteUser(String accountCode, String masterCode);
	int deleteRoom(String masterCode, int roomIndex);
	int deleteElectric(String masterCode, int electricIndex);
	int deleteElectric(String masterCode, String electricCode, int electricIndex);
	int deleteScene(String masterCode, int sceneIndex);
	int deleteSceneElectric(String masterCode, int electricIndex, int sceneIndex);
	int signLeCheng(String accountCode);
	Account loadAccountFromWs(String accountCode, String accountTime);
	List<ElectricState> getElectricStateByUser(String accountCode,String masterCode);
	List<User> loadUserFromWs(String accountCode, String userTime);
	List<Account> loadSharedAccount(String masterCode);
	List<UserRoom> loadUserRoomFromWs(String accountCode, String masterCode, String areaTime);
	List<Electric> loadElectricFromWs(String accountCode, String masterCode, String electricTime);
	List<ElectricSharedLoacl> loadAllSharedElectric(String masterCode);
	List<Scene> loadSceneFromWs(String accountCode, String masterCode, String sceneTime);
	List<Scene> masterReadScene(String masterCode);
	List<SceneElectric> loadSceneElectricFromWs(String accountCode, String masterCode, String sceneElectricTime);
	List<ElectricForVoice> selectElectricForVoice(String masterCode);
	String loadKeyByElectric(String masterCode, int electricIndex);
	String loadETAirByElectric(String masterCode, int electricIndex);
	String ugetElectricOrderByMasterNode(String masterCode);
	String ugetSceneOrderByMasterNode(String masterCode);
	/**
	 * 更新电器指令
	 * @param masterCode
	 * @param electricCode
	 * @param electricOrder
	 * @param orderInfo
	 */
	int giveUpAdmin(String masterCode, String owner);
	int accessAdmin(String masterCode,String owner);
	int deleteSharedUser(String masterCode, String accountCode);
	
	String getAppVersion();
	String getAppVersionOs();
	String getAppVersionVoice(String appName);
	
	//konnn
	int updateUserName(String accountCode, String masterCode, String userName);
	int updateSceneElectricOrder(String masterCode, int electricIndex, String electricCode, int sceneIndex, String electricOrder, String orderInfo);
	void sendSms(String phoneNum, String msg);
	String sendSmsCode(String phoneNum);
	String checkSmsCode(String phoneNum, String code);
	String resetAccountPassword(String accountCode, String newPassword);
	String isExistAccount(String accountCode);
	int updateElectric1(String masterCode, String electricCode, int electricIndex, String electricName, int sceneIndex, String electricOrder);
	int updateElectricState(String masterCode,String electricCode, String electricState, String stateInfo) throws IOException;
	List<AlarmRecord> loadAlarmRecord(String masterCode);
	String loadDoorRecord(String masterCode, String electricCode);
	int getElectricSequ(String masterCode, int electricIndex);
	int updateElectricSequ(String masterCode, int electricIndex, int electricSequ, int roomIndex) throws IOException;
	int saveDoorRecord(String masterCode, String electricCode, String stateInfo);
	int saveAlarmRecord(String masterCode, String electricCode, String electricState, String stateInfo);
}
