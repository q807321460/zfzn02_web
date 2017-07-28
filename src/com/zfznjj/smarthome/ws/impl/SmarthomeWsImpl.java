package com.zfznjj.smarthome.ws.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.zfznjj.smarthome.entity.AdminElectricBean;
import com.zfznjj.smarthome.entity.ElectricForVoice;
import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.Scene;
import com.zfznjj.smarthome.model.SceneElectric;
import com.zfznjj.smarthome.model.User;
import com.zfznjj.smarthome.model.UserRoom;
import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.util.SmartHomeUtil;
import com.zfznjj.smarthome.util.SmsUtil;
import com.zfznjj.smarthome.ws.SmarthomeWs;

public class SmarthomeWsImpl implements SmarthomeWs {

	private SmarthomeService smarthomeService;
	public void setSmarthomeService(SmarthomeService smarthomeService) {
		this.smarthomeService = smarthomeService;
	}
	
	@Override
	public String validLogin(String accountCode, String password) {
		return smarthomeService.validLogin(accountCode, password);
	}
	
	@Override
	public String isExistAccount(String accountCode) {
		return smarthomeService.isExistAccount(accountCode);
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
		// TODO Auto-generated method stub
		return ""+smarthomeService.addUserRoom(masterCode, roomIndex, roomName, roomSequ, roomImg);
	}
	
	@Override
	public String addETKeys(String keyJsonString) {
		// TODO Auto-generated method stub
		return ""+smarthomeService.addETKeys(keyJsonString);
	}
	
	@Override
	public String updateSensorExtras(String masterCode, String electricCode, int electricIndex, String extras) {
		return String.valueOf(smarthomeService.updateSensorExtras(masterCode, electricCode, electricIndex, extras));
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
			String electricName, int electricSequ, int electricType, String extra, String orderInfo) {
		return ""+smarthomeService.addElectric(masterCode, electricIndex, electricCode, roomIndex,
				electricName, electricSequ, electricType, extra,orderInfo);
	}
	
	@Override
	public String addCrashLog(String logName, byte[] logDetail, String appName) {
		// TODO Auto-generated method stub
		return String.valueOf(smarthomeService.addCrashLog(logName, logDetail, appName));
	}
	
	@Override
	public String updateElectric(String masterCode, String electricCode, int electricIndex, String electricName, int sceneIndex) {
		return ""+smarthomeService.updateElectric(masterCode, electricCode, electricIndex, electricName, sceneIndex);
	}
	
	@Override
	public String updateElectric1(String masterCode, String electricCode, int electricIndex, String electricName, int sceneIndex, String electricOrder) {
		return ""+smarthomeService.updateElectric1(masterCode, electricCode, electricIndex, electricName, sceneIndex, electricOrder);
	}
	
	@Override
	public String updateUserRoom(String masterCode, int roomIndex, String roomName, int roomImg) {
		// TODO Auto-generated method stub
		return ""+smarthomeService.updateUserRoom(masterCode, roomIndex, roomName, roomImg);
	}
	
	@Override
	public String updateUserIP(String masterCode, String userIP) {
		return String.valueOf(smarthomeService.updateUserIP(masterCode, userIP));
	}
	
	@Override
	public String updateAccount(String accountCode, String accountName, String accountPhone, String accountAddress,
			String accountEmail) {
		// TODO Auto-generated method stub
		return ""+smarthomeService.updateAccount(accountCode, accountName, accountPhone, accountAddress, accountEmail);
	}
	
	@Override
	public String updateAccountPhoto(String accountCode, byte[] photo) {
		// TODO Auto-generated method stub
		return String.valueOf(smarthomeService.updateAccountPhoto(accountCode, photo));
	}
	
	@Override
	public String updateIRKeyValue(String masterCode, int electricIndex, int keyKey, String keyValue) {
		
		return String.valueOf(smarthomeService.updateIRKeyValue(masterCode, electricIndex, keyKey, SmartHomeUtil.string2HexString(keyValue).toString()));
	}
	
	@Override
	public List<ElectricSharedLoacl> loadAllSharedElectric(String masterCode) {
		return smarthomeService.loadAllSharedElectric(masterCode);
	}

	@Override
	public List<ElectricState> getElectricStateByUser(String accountCode, String masterCode) {
		// TODO Auto-generated method stub
		return smarthomeService.getElectricStateByUser(accountCode, masterCode);
	}
	
	@Override
	public List<User> loadUserFromWs(String accountCode,String userTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return smarthomeService.loadUserFromWs(accountCode, userTime);
		
	}
	
	@Override
	public String loadKeyByElectric(String masterCode, int electricIndex) {
		// TODO Auto-generated method stub
		return smarthomeService.loadKeyByElectric(masterCode, electricIndex);
	}
	
	@Override
	public String loadETAirByElectric(String masterCode, int electricIndex) {
		// TODO Auto-generated method stub
		return smarthomeService.loadETAirByElectric(masterCode, electricIndex);
	}

	@Override
	public String addUser(String accountCode,String masterCode, String userName, String userIp) {
		return ""+smarthomeService.addUser(accountCode, masterCode, userName, userIp);
	}

	@Override
	public Account loadAccountFromWs(String accountCode, String accountTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return smarthomeService.loadAccountFromWs(accountCode,accountTime);
		
	}
	
	@Override
	public String deleteUser(String accountCode, String masterCode) {
		// TODO Auto-generated method stub
		return String.valueOf(smarthomeService.deleteUser(accountCode, masterCode));
	}
	
	@Override
	public String deleteScene(String masterCode, int sceneIndex, int sceneSequ) {
		int result = smarthomeService.deleteScene(masterCode, sceneIndex);
		if(result > 0){
			smarthomeService.updateSceneSequ(masterCode, sceneSequ);
		}
		return String.valueOf(result);
	}
	
	@Override
	public String adminSharedElectric(byte[] bytes) {
		List<AdminElectricBean> adminElectricBeans = new ArrayList<>();
		String string1 = new String(bytes);
		String[] strings = string1.split(";");
		for (String string : strings) {
			String[] params = string.split("\\|");
			AdminElectricBean bean = new AdminElectricBean();
			bean.setAccountCode(params[0]);
			bean.setMasterCode(params[1]);
			bean.setElectricIndex(Integer.parseInt(params[2]));
			bean.setAdmin(Integer.parseInt(params[3]));
			adminElectricBeans.add(bean);
		}
		// TODO Auto-generated method stub
		return String.valueOf(smarthomeService.adminSharedElectric(adminElectricBeans));
	}
	
	@Override
	public String deleteRoom(String masterCode, int roomIndex, int roomSequ) {
		int result = smarthomeService.deleteRoom(masterCode, roomIndex);
		if(result > 0){
			smarthomeService.updateRoomSequ(masterCode, roomSequ);
		}
		return String.valueOf(result);
	}
	
	@Override
	public String deleteElectric(String masterCode, int electricIndex, int electricSequ,int roomIndex) {
		int result = smarthomeService.deleteElectric(masterCode, electricIndex);
		if(result > 0){
			smarthomeService.updateElectricSequ(masterCode, electricSequ,roomIndex);
		}
		return String.valueOf(result);
	}
	
	@Override
	public String deleteElectric1(String masterCode, String electricCode, int electricIndex, int electricSequ,int roomIndex) {
		int result = smarthomeService.deleteElectric(masterCode, electricCode, electricIndex);
		if(result > 0){
			smarthomeService.updateElectricSequ(masterCode, electricSequ,roomIndex);
		}
		return String.valueOf(result);
	}
	
	@Override
	public String deleteSceneElectric(String masterCode, int electricIndex, int sceneIndex) {
		// TODO Auto-generated method stub
		return String.valueOf(smarthomeService.deleteSceneElectric(masterCode, electricIndex, sceneIndex));
	}

	@Override
	public List<UserRoom> loadUserRoomFromWs(String accountCode,String masterCode, String areaTime) {
		// TODO Auto-generated method stub
		return smarthomeService.loadUserRoomFromWs(accountCode,masterCode, areaTime);
	}

	@Override
	public List<Electric> loadElectricFromWs(String accountCode,String masterCode, String electricTime) {
		// TODO Auto-generated method stub
		return smarthomeService.loadElectricFromWs(accountCode,masterCode, electricTime);
	}

	@Override
	public String addScene(String accountCode, String masterCode, String sceneName, int sceneIndex,int sceneSequ, int sceneImg) {
		// TODO Auto-generated method stub
		return ""+smarthomeService.addScene(accountCode, masterCode, sceneName, sceneIndex,sceneSequ,sceneImg);
	}

	@Override
	public String addSceneElectric(String masterCode, String electricCode, String electricOrder, String accountCode,
			int sceneIndex, String orderInfo,int electricIndex,String electricName, int roomIndex, int electricType) {
		// TODO Auto-generated method stub
		return ""+smarthomeService.addSceneElectric(masterCode, electricCode, electricOrder, accountCode, sceneIndex, orderInfo,
				electricIndex, electricName, roomIndex, electricType);
	}
	
	@Override
	public void updateElectricOrder(String masterCode,String electricCode, String order,String orderInfo) {
		// TODO Auto-generated method stub
		smarthomeService.addELectricOrder(masterCode, electricCode, order, orderInfo);
	}

	@Override
	public String giveUpAdmin(String masterCode, String owner) {
		// TODO Auto-generated method stub
		return ""+smarthomeService.giveUpAdmin(masterCode, owner);
	}

	@Override
	public String accessAdmin(String masterCode, String owner) {
		// TODO Auto-generated method stub
		return ""+smarthomeService.accessAdmin(masterCode, owner);
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
		return String.valueOf(smarthomeService.addSceneOrder(masterCode, electricCode, electricOrder, orderInfo, sceneIndex));
	}
	
	@Override
	public String signLeCheng(String accountCode) {
		return String.valueOf(smarthomeService.signLeCheng(accountCode));
	}

	@Override
	public void updateElectricState(String masterCode, String electricCode, String electricState, String stateInfo) {
		smarthomeService.updateElectricState(masterCode, electricCode, electricState, stateInfo);
	}

	@Override
	public String updateSceneElectricOrder(String masterCode, int electricIndex, String electricCode, int sceneIndex, String electricOrder, String orderInfo) {
		return String.valueOf(smarthomeService.updateSceneElectricOrder(masterCode, electricIndex, electricCode, sceneIndex, electricOrder, orderInfo));
	}
	
	@Override
	public String masterReadElecticOrder(String masterCode) {
		String result = smarthomeService.ugetSceneOrderByMasterNode(masterCode);
		if("none".equals(result)){
			result = smarthomeService.ugetElectricOrderByMasterNode(masterCode);
		}
		return result;
	}

	@Override
	public String sayHello(String masterCode) {
		System.out.println("hello"+masterCode);
		return "hello"+masterCode;
	}

	@Override
	public String updateUserName(String accountCode, String masterCode, String userName) {
		return String.valueOf(smarthomeService.updateUserName(accountCode, masterCode, userName));
	}
	
}
