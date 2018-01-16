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
	int updateSceneName(String masterCode,int sceneIndex,String sceneName,int sceneImg);
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
	int updateUserName(String accountCode, String masterCode, String userName); //更新主机名
	int updateSceneElectricOrder(String masterCode, int electricIndex, String electricCode, int sceneIndex, String electricOrder, String orderInfo); //更新情景模式中的电器信息
	void sendSms(String phoneNum, String msg); //向指定手机号发送指定信息的短信
	String sendSmsCode(String phoneNum); //向指定手机号发送短信
	String checkSmsCode(String phoneNum, String code); //根据传感器状态判断是否发送短信
	String resetAccountPassword(String accountCode, String newPassword); //重置密码时用
	int isExistAccount(String accountCode); //是否存在该账号，注册账号或者是忘记密码时用
	int updateElectric1(String masterCode, String electricCode, int electricIndex, String electricName, int sceneIndex, String electricOrder); //更新电器一些相关信息
	int updateElectricState(String masterCode,String electricCode, String electricState, String stateInfo) throws IOException; //更新电器状态，这里需要实现很多的额外功能
	String loadAlarmRecord(String masterCode); //加载报警历史记录
	String loadDoorRecord(String masterCode, String electricCode); //加载门锁开锁记录
	int getElectricSequ(String masterCode, int electricIndex); //获取电器排序值
	int saveDoorRecord(String masterCode, String electricCode, String stateInfo); //保存门锁开锁记录
	int saveAlarmRecord(String masterCode, String electricCode, String electricState, String stateInfo); //保存报警记录
	int updateElectricSequ(String masterCode, int electricIndex, int electricSequ, int roomIndex) throws IOException; //删除电器时用，将比删除的电器的sequ值大的sequ全部减一
	int updateElectricSequ(String masterCode, int electricIndex, int roomIndex, int oldElectricSequ, int newElectricSequ); //调整电器顺序时用，将新旧sequ值中间的sequ全部减一
	int isExistElectric(String masterCode, String electricCode); //添加电器时，判断该电器是否被重复添加过
	List<ElectricSharedLoacl> loadSharedElectric(String masterCode, String accountCode); //加载被分享的电器列表
	List<Electric> FixSharedElectricSequ(List<Electric>electrics); //加载被分享的电器列表时，sequ有可能不正确，修复之
	int moveElectricToAnotherRoom(String masterCode, int electricIndex, int roomIndex); //将指定的电器移动到另一个房间
	String updateDoorOpenPerson(String electricCode, String accountCode); //将开锁记录中的开锁人更新为手机号
	String getMasterVersion(); // 获取最新的主机版本号
	String getMasterVersion(String masterCode); // 根据主机编号获取版本号
	int updateMasterVersion(String masterCode, String masterVersion); // 更新主机版本号
	void lechageAlarm(String electricCode, String type, String time); // 乐橙摄像头触发报警，乐橙云将信息推送给服务器的后续处理
	int updateSceneDetailTiming(String masterCode, int sceneIndex, String detailTiming); // 设置具体某个时间点的情景定时
	int updateSceneDaliyTiming(String masterCode, int sceneIndex, String weeklyDays, String daliyTiming); // 设置每周的循环定时
}
