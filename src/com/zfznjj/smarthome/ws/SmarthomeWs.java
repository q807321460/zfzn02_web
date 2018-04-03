package com.zfznjj.smarthome.ws;

import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.zfznjj.smarthome.entity.AccountLocal;
import com.zfznjj.smarthome.entity.AdminElectricBean;
import com.zfznjj.smarthome.entity.ElectricForVoice;
import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.entity.UserLocal;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.model.AlarmRecord;
import com.zfznjj.smarthome.model.ETKey;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.Scene;
import com.zfznjj.smarthome.model.SceneElectric;
import com.zfznjj.smarthome.model.User;
import com.zfznjj.smarthome.model.UserRoom;

@WebService(targetNamespace="http://ws.smarthome.zfznjj.com/",endpointInterface = "com.zfznjj.smarthome.ws.SmarthomeWs",
serviceName = "smarthomeWsImpl")
public interface SmarthomeWs {
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
    @WebMethod
	String validLogin(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode , 
			@WebParam(name="password",targetNamespace="http://ws.smarthome.zfznjj.com/")String password) throws Exception;
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String isExistAccount(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String resetAccountPassword(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="newPassword",targetNamespace="http://ws.smarthome.zfznjj.com/")String newPassword);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateAccountPassword(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="oldPassword",targetNamespace="http://ws.smarthome.zfznjj.com/")String oldPassword,
			@WebParam(name="newPassword",targetNamespace="http://ws.smarthome.zfznjj.com/")String newPassword);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addAccount(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode,
			@WebParam(name="password",targetNamespace="http://ws.smarthome.zfznjj.com/")String password,
			@WebParam(name="accountName",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountName);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addAccountDaFuGui(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode,
			@WebParam(name="password",targetNamespace="http://ws.smarthome.zfznjj.com/")String password,
			@WebParam(name="accountName",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountName);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String getAdminAccountCode(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<String> getAccountCodesByMaster(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateSensorExtras(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex, 
			@WebParam(name="extras",targetNamespace="http://ws.smarthome.zfznjj.com/")String extras);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	void sendSms(
			@WebParam(name="phoneNum",targetNamespace="http://ws.smarthome.zfznjj.com/")String phoneNum, 
			@WebParam(name="msg",targetNamespace="http://ws.smarthome.zfznjj.com/")String msg);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String sendSmsCode(
			@WebParam(name="phoneNum",targetNamespace="http://ws.smarthome.zfznjj.com/")String phoneNum);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String checkSmsCode(
			@WebParam(name="phoneNum",targetNamespace="http://ws.smarthome.zfznjj.com/")String phoneNum, 
			@WebParam(name="code",targetNamespace="http://ws.smarthome.zfznjj.com/")String code);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateAccountPhoto(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="photo",targetNamespace="http://ws.smarthome.zfznjj.com/")byte[] photo);
	
	
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addUserRoom(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode,
			@WebParam(name="roomIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomIndex, 
			@WebParam(name="roomName",targetNamespace="http://ws.smarthome.zfznjj.com/")String roomName, 
			@WebParam(name="roomSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomSequ, 
			@WebParam(name="roomImg",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomImg);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addETKeys(
			@WebParam(name="keyJsonString",targetNamespace="http://ws.smarthome.zfznjj.com/")String keyJsonString);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addETAirDevice(
			@WebParam(name="eTAirJsonString",targetNamespace="http://ws.smarthome.zfznjj.com/")String eTAirJsonString);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode,
			@WebParam(name="roomIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomIndex, 
			@WebParam(name="electricName",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricName,
			@WebParam(name="electricSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricSequ, 
			@WebParam(name="electricType",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricType, 
			@WebParam(name="extra",targetNamespace="http://ws.smarthome.zfznjj.com/")String extra,
			@WebParam(name="orderInfo",targetNamespace="http://ws.smarthome.zfznjj.com/")String orderInfo) throws Exception;
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addCrashLog(
			@WebParam(name="logName",targetNamespace="http://ws.smarthome.zfznjj.com/")String logName, 
			@WebParam(name="logDetail",targetNamespace="http://ws.smarthome.zfznjj.com/")byte[] logDetail, 
			@WebParam(name="appName",targetNamespace="http://ws.smarthome.zfznjj.com/")String appName);
	
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex,
			@WebParam(name="electricName",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricName,
			@WebParam(name="sceneIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneIndex);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateElectric1(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex,
			@WebParam(name="electricName",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricName,
			@WebParam(name="sceneIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneIndex,
			@WebParam(name="electricOrder",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricOrder);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateUserRoom(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="roomIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomIndex, 
			@WebParam(name="roomName",targetNamespace="http://ws.smarthome.zfznjj.com/")String roomName, 
			@WebParam(name="roomImg",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomImg);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateUserIP(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="userIP",targetNamespace="http://ws.smarthome.zfznjj.com/")String userIP);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateUserName(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="userName",targetNamespace="http://ws.smarthome.zfznjj.com/")String userName);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addScene(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="sceneName",targetNamespace="http://ws.smarthome.zfznjj.com/")String sceneName, 
			@WebParam(name="sceneIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneIndex,
			@WebParam(name="sceneSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneSequ, 
			@WebParam(name="sceneImg",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneImg);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addSceneElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode, 
			@WebParam(name="electricOrder",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricOrder, 
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode,
			@WebParam(name="sceneIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneIndex,
			@WebParam(name="orderInfo",targetNamespace="http://ws.smarthome.zfznjj.com/")String orderInfo,
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex,
			@WebParam(name="electricName",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricName, 
			@WebParam(name="roomIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomIndex, 
			@WebParam(name="electricType",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricType);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addSceneOrder(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode, 
			@WebParam(name="electricOrder",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricOrder, 
			@WebParam(name="orderInfo",targetNamespace="http://ws.smarthome.zfznjj.com/")String orderInfo, 
			@WebParam(name="sceneIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneIndex);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateAccount(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="accountName",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountName, 
			@WebParam(name="accountPhone",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountPhone,
			@WebParam(name="accountAddress",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountAddress,
			@WebParam(name="accountEmail",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountEmail);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateIRKeyValue(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex, 
			@WebParam(name="keyKey",targetNamespace="http://ws.smarthome.zfznjj.com/")int keyKey, 
			@WebParam(name="keyValue",targetNamespace="http://ws.smarthome.zfznjj.com/")String keyValue);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateSceneElectricOrder(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode, 
			@WebParam(name="sceneIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneIndex, 
			@WebParam(name="electricOrder",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricOrder,
			@WebParam(name="orderInfo",targetNamespace="http://ws.smarthome.zfznjj.com/")String orderInfo);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String deleteUser(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String adminSharedElectric(
			@WebParam(name="bytes",targetNamespace="http://ws.smarthome.zfznjj.com/")byte[] bytes);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String deleteRoom(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="roomIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomIndex, 
			@WebParam(name="roomSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomSequ);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String deleteElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex, 
			@WebParam(name="electricSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricSequ,
			@WebParam(name="roomIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomIndex) throws IOException;
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String deleteElectric1(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode,
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex, 
			@WebParam(name="electricSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricSequ,
			@WebParam(name="roomIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomIndex) throws IOException;
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String deleteScene(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="sceneIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneIndex,
			@WebParam(name="sceneSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneSequ);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String deleteSceneElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex, 
			@WebParam(name="sceneIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int sceneIndex);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<ElectricState> getElectricStateByUser(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode,
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<User> loadUserFromWs(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode,
			@WebParam(name="userTime",targetNamespace="http://ws.smarthome.zfznjj.com/")String userTime);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<Account> loadSharedAccount(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String loadKeyByElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String loadETAirByElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<UserRoom> loadUserRoomFromWs(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode,
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode,
			@WebParam(name="areaTime",targetNamespace="http://ws.smarthome.zfznjj.com/")String areaTime);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<Electric> loadElectricFromWs(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode,
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode,
			@WebParam(name="electricTime",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricTime);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<ElectricSharedLoacl> loadAllSharedElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	Account loadAccountFromWs(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode,
			@WebParam(name="accountTime",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountTime);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<Scene> loadSceneFromWs(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="sceneTime",targetNamespace="http://ws.smarthome.zfznjj.com/")String sceneTime);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<SceneElectric> loadSceneElectricFromWs(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="sceneElectricTime",targetNamespace="http://ws.smarthome.zfznjj.com/")String sceneElectricTime);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<ElectricForVoice> selectElectricForVoice(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addUser(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="userName",targetNamespace="http://ws.smarthome.zfznjj.com/")String userName,
			@WebParam(name="userIp",targetNamespace="http://ws.smarthome.zfznjj.com/")String userIp);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addSharedUser(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode, 
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="userName",targetNamespace="http://ws.smarthome.zfznjj.com/")String userName,
			@WebParam(name="userIp",targetNamespace="http://ws.smarthome.zfznjj.com/")String userIp);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
    void updateElectricOrder(
    		@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode,
    		@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode, 
    		@WebParam(name="order",targetNamespace="http://ws.smarthome.zfznjj.com/")String order,
    		@WebParam(name="orderInfo",targetNamespace="http://ws.smarthome.zfznjj.com/")String orderInfo);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String giveUpAdmin(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="owner",targetNamespace="http://ws.smarthome.zfznjj.com/")String owner);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String accessAdmin(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode,
			@WebParam(name="owner",targetNamespace="http://ws.smarthome.zfznjj.com/")String owner);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String deleteSharedUser(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String getAppVersion();
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String getAppVersionOs();
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String getAppVersionVoice(
			@WebParam(name="appName",targetNamespace="http://ws.smarthome.zfznjj.com/")String appName);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	void updateElectricState(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode,
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode,
			@WebParam(name="electricState",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricState,
			@WebParam(name="stateInfo",targetNamespace="http://ws.smarthome.zfznjj.com/")String stateInfo) throws IOException;
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String masterReadElecticOrder(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	List<Scene> masterReadScene(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String signLeCheng(
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String sayHello() throws IOException;
	
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String loadAlarmRecord(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String loadDoorRecord(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateElectricSequ(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int electricIndex,
			@WebParam(name="roomIndex",targetNamespace="http://ws.smarthome.zfznjj.com/")int roomIndex,
			@WebParam(name="oldElectricSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int oldElectricSequ,
			@WebParam(name="newElectricSequ",targetNamespace="http://ws.smarthome.zfznjj.com/")int newElectricSequ);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String isExistElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="electricCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String electricCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	List<ElectricSharedLoacl> loadSharedElectric(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name="accountCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String accountCode);

	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateSceneName(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name = "sceneIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int sceneIndex, 
			@WebParam(name = "sceneName", targetNamespace = "http://ws.smarthome.zfznjj.com/") String sceneName, 
			@WebParam(name = "sceneImg", targetNamespace = "http://ws.smarthome.zfznjj.com/") int sceneImg);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String moveElectricToAnotherRoom(
			@WebParam(name="masterCode",targetNamespace="http://ws.smarthome.zfznjj.com/")String masterCode, 
			@WebParam(name = "electricIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int electricIndex, 
			@WebParam(name = "roomIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int roomIndex);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateDoorOpenPerson(
			@WebParam(name = "electricCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String electricCode, 
			@WebParam(name = "accountCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String accountCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")
	@WebMethod
	String getMasterVersion();
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String getMasterVersionBy(
			@WebParam(name = "masterCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String masterCode);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String deleteSceneTiming(
			@WebParam(name = "masterCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String masterCode, 
			@WebParam(name = "sceneIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int sceneIndex);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateSceneDetailTiming(
			@WebParam(name = "masterCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String masterCode, 
			@WebParam(name = "sceneIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int sceneIndex, 
			@WebParam(name = "detailTiming", targetNamespace = "http://ws.smarthome.zfznjj.com/") String detailTiming);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateSceneDaliyTiming(
			@WebParam(name = "masterCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String masterCode, 
			@WebParam(name = "sceneIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int sceneIndex, 
			@WebParam(name = "weeklyDays", targetNamespace = "http://ws.smarthome.zfznjj.com/") String weeklyDays, 
			@WebParam(name = "daliyTiming", targetNamespace = "http://ws.smarthome.zfznjj.com/") String daliyTiming);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String addCentralAir(
			@WebParam(name = "masterCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String masterCode, 
			@WebParam(name = "electricIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int electricIndex,
			@WebParam(name = "airCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String airCode,
			@WebParam(name = "airName", targetNamespace = "http://ws.smarthome.zfznjj.com/") String airName);
	
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String deleteCentralAir(
			@WebParam(name = "masterCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String masterCode, 
			@WebParam(name = "electricIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int electricIndex,
			@WebParam(name = "airCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String airCode,
			@WebParam(name = "airName", targetNamespace = "http://ws.smarthome.zfznjj.com/") String airName);
	@WebResult(name="result",targetNamespace="http://ws.smarthome.zfznjj.com/")  
	@WebMethod
	String updateCentralAirName(
			@WebParam(name = "masterCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String masterCode, 
			@WebParam(name = "electricIndex", targetNamespace = "http://ws.smarthome.zfznjj.com/") int electricIndex,
			@WebParam(name = "airCode", targetNamespace = "http://ws.smarthome.zfznjj.com/") String airCode,
			@WebParam(name = "newAirName", targetNamespace = "http://ws.smarthome.zfznjj.com/") String newAirName);
	
}
