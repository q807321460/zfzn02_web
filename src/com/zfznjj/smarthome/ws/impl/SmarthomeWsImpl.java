package com.zfznjj.smarthome.ws.impl;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import org.hibernate.loader.custom.Return;

import com.zfznjj.smarthome.entity.AdminElectricBean;
import com.zfznjj.smarthome.entity.ElectricForVoice;
import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.model.AlarmRecord;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.Scene;
import com.zfznjj.smarthome.model.SceneElectric;
import com.zfznjj.smarthome.model.User;
import com.zfznjj.smarthome.model.UserRoom;
import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.util.SmartHomeUtil;
import com.zfznjj.smarthome.util.SmsUtil;
import com.zfznjj.smarthome.util.WriteLog;
import com.zfznjj.smarthome.websocket.AppWebSocket;
import com.zfznjj.smarthome.ws.SmarthomeWs;

public class SmarthomeWsImpl implements SmarthomeWs {

	private SmarthomeService smarthomeService;
	public void setSmarthomeService(SmarthomeService smarthomeService) {
		this.smarthomeService = smarthomeService;
	}
	
	@Override
	public String validLogin(String accountCode, String password) throws Exception {
		return smarthomeService.validLogin(accountCode, password);
	}
	
	@Override
	public String isExistAccount(String accountCode) {
		return String.valueOf(smarthomeService.isExistAccount(accountCode));
	}
	
	@Override
	public String resetAccountPassword(String accountCode, String newPassword) {
		return smarthomeService.resetAccountPassword(accountCode, newPassword);
	}
	
	@Override
	public String updateAccountPassword(String accountCode, String oldPassword, String newPassword) {
		return smarthomeService.updateAccountPassword(accountCode, oldPassword, newPassword);
	}

	@Override
	public String addAccount(String accountCode, String password,String accountName) {
		return ""+smarthomeService.addAccount(accountCode, password,accountName);
	}
	
	@Override
	public String getAdminAccountCode(String masterCode) {
		return smarthomeService.getAdminAccountCode(masterCode);
	}
	
	@Override
	public List<String> getAccountCodesByMaster(String masterCode) {
		// TODO Auto-generated method stub
		return smarthomeService.getAccountCodesByMaster(masterCode);
	}
	
	@Override
	public String addUserRoom(String masterCode, int roomIndex, String roomName, int roomSequ, int roomImg) {
		int re = smarthomeService.addUserRoom(masterCode, roomIndex, roomName, roomSequ, roomImg);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String addETKeys(String keyJsonString) {
		// TODO Auto-generated method stub
		return ""+smarthomeService.addETKeys(keyJsonString);
	}
	
	@Override
	public String updateSensorExtras(String masterCode, String electricCode, int electricIndex, String extras) {
		int re = smarthomeService.updateSensorExtras(masterCode, electricCode, electricIndex, extras);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public void sendSms(String phoneNum, String msg) {
		return;
		//return SmsUtil.sendSms(phoneNum, msg);
	}
	
	@Override
	public String sendSmsCode(String phoneNum) {
		return ""+smarthomeService.sendSmsCode(phoneNum);
	}
	
	@Override
	public String checkSmsCode(String phoneNum, String code) {
		return ""+smarthomeService.checkSmsCode(phoneNum, code);
	}
	
	@Override
	public String addETAirDevice(String eTAirJsonString) {
		return ""+smarthomeService.addETAirDevice(eTAirJsonString);
	}
	
	@Override
	public String addElectric(String masterCode, int electricIndex, String electricCode, int roomIndex,
			String electricName, int electricSequ, int electricType, String extra, String orderInfo) throws Exception {
		WriteLog.writeLog("electrics.log", "【addElectric】" + "masterCode:" + masterCode + "  electricIndex:" + electricIndex + "  electricCode:" + electricCode +
				"  roomIndex:" + roomIndex + "  electricName:" + electricName + "  electricSequ:" + electricSequ + "  electricType:" + electricType + "  extra:" + extra + "  orderInfo:" + orderInfo);
		int re = smarthomeService.addElectric(masterCode, electricIndex, electricCode, roomIndex,
				electricName, electricSequ, electricType, extra, orderInfo);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String addCrashLog(String logName, byte[] logDetail, String appName) {
		return String.valueOf(smarthomeService.addCrashLog(logName, logDetail, appName));
	}
	
	@Override
	public String updateElectric(String masterCode, String electricCode, int electricIndex, String electricName, int sceneIndex) {
		int re = smarthomeService.updateElectric(masterCode, electricCode, electricIndex, electricName, sceneIndex);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	//用于选择门磁的开关状态
	@Override
	public String updateElectric1(String masterCode, String electricCode, int electricIndex, String electricName, int sceneIndex, String electricOrder) {
		int re = smarthomeService.updateElectric1(masterCode, electricCode, electricIndex, electricName, sceneIndex, electricOrder);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String updateUserRoom(String masterCode, int roomIndex, String roomName, int roomImg) {
		int re = smarthomeService.updateUserRoom(masterCode, roomIndex, roomName, roomImg);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String updateUserIP(String masterCode, String userIP) {
		return String.valueOf(smarthomeService.updateUserIP(masterCode, userIP));
	}
	
	@Override
	public String updateAccount(String accountCode, String accountName, String accountPhone, String accountAddress,
			String accountEmail) {
		int re = smarthomeService.updateAccount(accountCode, accountName, accountPhone, accountAddress, accountEmail);
		return String.valueOf(re);
	}
	
	@Override
	public String updateAccountPhoto(String accountCode, byte[] photo) {
		return String.valueOf(smarthomeService.updateAccountPhoto(accountCode, photo));
	}
	
	@Override
	public String updateIRKeyValue(String masterCode, int electricIndex, int keyKey, String keyValue) {
		int re = smarthomeService.updateIRKeyValue(masterCode, electricIndex, keyKey, SmartHomeUtil.string2HexString(keyValue).toString());
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public List<ElectricSharedLoacl> loadAllSharedElectric(String masterCode) {
		return smarthomeService.loadAllSharedElectric(masterCode);
	}

	@Override
	public List<ElectricState> getElectricStateByUser(String accountCode, String masterCode) {
		return smarthomeService.getElectricStateByUser(accountCode, masterCode);
	}
	
	@Override
	public List<User> loadUserFromWs(String accountCode,String userTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return smarthomeService.loadUserFromWs(accountCode, userTime);
	}
	
	@Override
	public String loadKeyByElectric(String masterCode, int electricIndex) {
		return smarthomeService.loadKeyByElectric(masterCode, electricIndex);
	}
	
	@Override
	public String loadETAirByElectric(String masterCode, int electricIndex) {
		// TODO Auto-generated method stub
		return smarthomeService.loadETAirByElectric(masterCode, electricIndex);
	}

	@Override
	public String addUser(String accountCode,String masterCode, String userName, String userIp) {
		int re = smarthomeService.addUser(accountCode, masterCode, userName, userIp);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}

	@Override
	public Account loadAccountFromWs(String accountCode, String accountTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return smarthomeService.loadAccountFromWs(accountCode,accountTime);
		
	}
	
	@Override
	public String deleteUser(String accountCode, String masterCode) {
		int re = smarthomeService.deleteUser(accountCode, masterCode);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String deleteScene(String masterCode, int sceneIndex, int sceneSequ) {
		int result = smarthomeService.deleteScene(masterCode, sceneIndex);
		if(result > 0){
			smarthomeService.updateSceneSequ(masterCode, sceneSequ);
		}
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(result);
	}
	
	@Override
	public String adminSharedElectric(byte[] bytes) {
		List<AdminElectricBean> adminElectricBeans = new ArrayList<>();
		String string1 = new String(bytes);
		String[] strings = string1.split(";");
		String masterCode = "";
		for (String string : strings) {
			String[] params = string.split("\\|");
			AdminElectricBean bean = new AdminElectricBean();
			bean.setAccountCode(params[0]);
			bean.setMasterCode(params[1]);
			masterCode = params[1];
			bean.setElectricIndex(Integer.parseInt(params[2]));
			bean.setAdmin(Integer.parseInt(params[3]));
			adminElectricBeans.add(bean);
		}
		int result = smarthomeService.adminSharedElectric(adminElectricBeans);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(result);
	}
	
	@Override
	public String deleteRoom(String masterCode, int roomIndex, int roomSequ) {
		int result = smarthomeService.deleteRoom(masterCode, roomIndex);
		if(result > 0){
			smarthomeService.updateRoomSequ(masterCode, roomSequ);
		}
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(result);
	}
	
	@Override
	public String deleteElectric(String masterCode, int electricIndex, int electricSequ,int roomIndex) throws IOException {
		//将添加电器的记录保存到log中
		WriteLog.writeLog("electrics.log", "【deleteElectric1】" + "masterCode:" + masterCode + "  electricIndex:" + electricIndex + "  roomIndex:" + roomIndex + "  electricSequ:" + electricSequ);
		//这里的electricSequ是客户端上传的参数，这个参数可能是不正确的，现查看之
		int newElectricSequ = smarthomeService.getElectricSequ(masterCode, electricIndex);
		if (newElectricSequ != electricSequ) {
			WriteLog.writeLog("error.log",
					"【updateElectricSequ错误】" + "masterCode:" + masterCode + "  electricIndex:" + electricIndex
							+ "  roomIndex:" + roomIndex + "  electricSequ:" + electricSequ + "  newElectircSequ:"
							+ newElectricSequ);
		}
		int result = smarthomeService.deleteElectric(masterCode, electricIndex);
		if(result > 0){
			smarthomeService.updateElectricSequ(masterCode, electricIndex, newElectricSequ, roomIndex);
		}
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(result);
	}
	
	@Override
	public String deleteElectric1(String masterCode, String electricCode, int electricIndex, int electricSequ,int roomIndex) throws IOException {
		//将添加电器的记录保存到log中
		WriteLog.writeLog("electrics.log",
				"【deleteElectric1】" + "masterCode:" + masterCode + "  electricIndex:" + electricIndex + "  roomIndex:" + roomIndex + "  electricSequ:" + electricSequ
						+ "  electricCode:" + electricCode);
		//这里的electricSequ是客户端上传的参数，这个参数可能是不正确的，现查看之
		int newElectricSequ = smarthomeService.getElectricSequ(masterCode, electricIndex);
		if (newElectricSequ != electricSequ) {
			WriteLog.writeLog("error.log",
					"【updateElectricSequ错误】" + "masterCode:" + masterCode + "  electricIndex:" + electricIndex + "  roomIndex:" + roomIndex + "  electricSequ:" + electricSequ
							+ "  newElectircSequ:" + newElectricSequ);
		}
		int result = smarthomeService.deleteElectric(masterCode, electricCode, electricIndex);
		if(result > 0){
			smarthomeService.updateElectricSequ(masterCode, electricIndex, newElectricSequ, roomIndex);
		}
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(result);
	}
	
	@Override
	public String deleteSceneElectric(String masterCode, int electricIndex, int sceneIndex) {
		int re = smarthomeService.deleteSceneElectric(masterCode, electricIndex, sceneIndex);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}

	@Override
	public List<UserRoom> loadUserRoomFromWs(String accountCode,String masterCode, String areaTime) {
		return smarthomeService.loadUserRoomFromWs(accountCode,masterCode, areaTime);
	}

	@Override
	public List<Electric> loadElectricFromWs(String accountCode,String masterCode, String electricTime) {
		return smarthomeService.loadElectricFromWs(accountCode,masterCode, electricTime);
	}

	@Override
	public String addScene(String accountCode, String masterCode, String sceneName, int sceneIndex,int sceneSequ, int sceneImg) {
		int re = smarthomeService.addScene(accountCode, masterCode, sceneName, sceneIndex,sceneSequ,sceneImg);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}

	@Override
	public String addSceneElectric(String masterCode, String electricCode, String electricOrder, String accountCode,
			int sceneIndex, String orderInfo,int electricIndex,String electricName, int roomIndex, int electricType) {
		int re = smarthomeService.addSceneElectric(masterCode, electricCode, electricOrder, accountCode, sceneIndex, orderInfo,
				electricIndex, electricName, roomIndex, electricType);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	//远程控制电器
	@Override
	public void updateElectricOrder(String masterCode,String electricCode, String order,String orderInfo) {
		smarthomeService.addELectricOrder(masterCode, electricCode, order, orderInfo);
	}

	@Override
	public String giveUpAdmin(String masterCode, String owner) {
		int re = smarthomeService.giveUpAdmin(masterCode, owner);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}

	@Override
	public String accessAdmin(String masterCode, String owner) {
		int re = smarthomeService.accessAdmin(masterCode, owner);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}

	@Override
	public List<Account> loadSharedAccount(String masterCode) {
		return smarthomeService.loadSharedAccount(masterCode);
	}

	@Override
	public String deleteSharedUser(String masterCode, String accountCode) {
		return ""+smarthomeService.deleteSharedUser(masterCode, accountCode);
	}

	@Override
	public String addSharedUser(String accountCode, String masterCode, String userName, String userIp) {
		return ""+smarthomeService.addSharedUser(accountCode, masterCode, userName, userIp);
	}

	@Override
	public List<Scene> loadSceneFromWs(String accountCode, String masterCode, String sceneTime) {
		return smarthomeService.loadSceneFromWs(accountCode, masterCode, sceneTime);
	}
	
	@Override
	public List<Scene> masterReadScene(String masterCode) {
		return smarthomeService.masterReadScene(masterCode);
	}

	@Override
	public List<SceneElectric> loadSceneElectricFromWs(String accountCode, String masterCode,
			String sceneElectricTime) {
		return smarthomeService.loadSceneElectricFromWs(accountCode, masterCode, sceneElectricTime);
	}
	
	@Override
	public List<ElectricForVoice> selectElectricForVoice(String masterCode) {
		return smarthomeService.selectElectricForVoice(masterCode);
	}
	
	@Override
	public String getAppVersion() {
		return smarthomeService.getAppVersion();
	}
	
	@Override
	public String getAppVersionOs() {
		return smarthomeService.getAppVersionOs();
	}
	
	@Override
	public String getAppVersionVoice(String appName) {
		return smarthomeService.getAppVersionVoice(appName);
	}
	
	@Override
	public String addSceneOrder(String masterCode, String electricCode, String electricOrder, String orderInfo,
			int sceneIndex) {
		int re = smarthomeService.addSceneOrder(masterCode, electricCode, electricOrder, orderInfo, sceneIndex);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String signLeCheng(String accountCode) {
		return String.valueOf(smarthomeService.signLeCheng(accountCode));
	}

	@Override
	public void updateElectricState(String masterCode, String electricCode, String electricState, String stateInfo) throws IOException {
		smarthomeService.updateElectricState(masterCode, electricCode, electricState, stateInfo);
	}

	@Override
	public String updateSceneElectricOrder(String masterCode, int electricIndex, String electricCode, int sceneIndex, String electricOrder, String orderInfo) {
		int re = smarthomeService.updateSceneElectricOrder(masterCode, electricIndex, electricCode, sceneIndex, electricOrder, orderInfo);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String masterReadElecticOrder(String masterCode) {
		String result = smarthomeService.ugetSceneOrderByMasterNode(masterCode);
		if("none".equals(result)){
			result = smarthomeService.ugetElectricOrderByMasterNode(masterCode);
		}
		return result;
	}

	//专门用于测试
	@Override
	public String sayHello() throws IOException {
		System.out.println("hello");
		AppWebSocket.sendMessage("AA00FFD9", "<030036C9E0BZZ603********00>");
		return "hello";
	}

	@Override
	public String updateUserName(String accountCode, String masterCode, String userName) {
		int re = smarthomeService.updateUserName(accountCode, masterCode, userName);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String loadAlarmRecord(String masterCode) {
		return smarthomeService.loadAlarmRecord(masterCode);		
	}
	
	@Override
	public String loadDoorRecord(String masterCode, String electricCode) {
		return String.valueOf(smarthomeService.loadDoorRecord(masterCode, electricCode));
	}
	
	@Override
	public String updateElectricSequ(String masterCode, int electricIndex, int roomIndex, int oldElectricSequ, int newElectricSequ) {
		int re = smarthomeService.updateElectricSequ(masterCode, electricIndex, roomIndex, oldElectricSequ, newElectricSequ);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String isExistElectric(String masterCode, String electricCode) {
		return String.valueOf(smarthomeService.isExistElectric(masterCode, electricCode));
	}
	
	@Override
	public List<ElectricSharedLoacl> loadSharedElectric(String masterCode, String accountCode) {
		return smarthomeService.loadSharedElectric(masterCode, accountCode);
	}

	@Override
	public String updateSceneName(String masterCode, int sceneIndex, String sceneName, int sceneImg) {
		int re = smarthomeService.updateSceneName(masterCode, sceneIndex, sceneName, sceneImg);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String moveElectricToAnotherRoom(String masterCode, int electricIndex, int roomIndex) {
		int re = smarthomeService.moveElectricToAnotherRoom(masterCode, electricIndex, roomIndex);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String updateDoorOpenPerson(String electricCode, String accountCode) {
		return smarthomeService.updateDoorOpenPerson(electricCode, accountCode);
	}
	
	@Override
	public String getMasterVersion() {
		return smarthomeService.getMasterVersion();
	}
	
	@Override
	public String getMasterVersionBy(String masterCode) {
		return smarthomeService.getMasterVersion(masterCode);
	}
	
	@Override
	public String deleteSceneTiming(String masterCode, int sceneIndex) {
		int re = smarthomeService.deleteSceneTiming(masterCode, sceneIndex);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String updateSceneDetailTiming(String masterCode, int sceneIndex, String detailTiming) {
		int re = smarthomeService.updateSceneDetailTiming(masterCode, sceneIndex, detailTiming);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
	@Override
	public String updateSceneDaliyTiming(String masterCode, int sceneIndex, String weeklyDays, String daliyTiming) {
		int re = smarthomeService.updateSceneDaliyTiming(masterCode, sceneIndex, weeklyDays, daliyTiming);
		AppWebSocket.sendSync(masterCode);
		return String.valueOf(re);
	}
	
}
