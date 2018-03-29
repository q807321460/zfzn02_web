package com.zfznjj.smarthome.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.awt.print.Printable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.cxf.tools.corba.common.idltypes.IdlString;
import org.apache.http.conn.util.PublicSuffixList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Check;
import org.hibernate.loader.custom.EntityFetchReturn;
import org.omg.CORBA.DomainManagerOperations;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.scheduling.SchedulingException;

import com.mysql.fabric.xmlrpc.base.Data;
//import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;
//import com.sun.org.apache.bcel.internal.generic.NEW;
//import com.sun.org.apache.bcel.internal.generic.RETURN;
//import com.sun.org.apache.regexp.internal.recompile;
//import com.sun.org.apache.xpath.internal.operations.And;
//import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zfznjj.smarthome.dao.AccountDao;
import com.zfznjj.smarthome.dao.AlarmRecordDao;
import com.zfznjj.smarthome.dao.ChildNodeDao;
import com.zfznjj.smarthome.dao.CrashDao;
import com.zfznjj.smarthome.dao.DoorRecordDao;
import com.zfznjj.smarthome.dao.ETAirDeviceDao;
import com.zfznjj.smarthome.dao.ETKeyDao;
import com.zfznjj.smarthome.dao.ElectricDao;
import com.zfznjj.smarthome.dao.ElectricOrderDao;
import com.zfznjj.smarthome.dao.ElectricSharedDao;
import com.zfznjj.smarthome.dao.MasterNodeDao;
import com.zfznjj.smarthome.dao.OtherDao;
import com.zfznjj.smarthome.dao.RoomSharedDao;
import com.zfznjj.smarthome.dao.SceneDao;
import com.zfznjj.smarthome.dao.SceneElectricDao;
import com.zfznjj.smarthome.dao.SceneOrderDao;
import com.zfznjj.smarthome.dao.UserDao;
import com.zfznjj.smarthome.dao.UserRoomDao;
import com.zfznjj.smarthome.entity.AdminElectricBean;
import com.zfznjj.smarthome.entity.ElectricForVoice;
import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.entity.OrderInfo;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.model.AlarmRecord;
import com.zfznjj.smarthome.model.ChildNode;
import com.zfznjj.smarthome.model.CrashLog;
import com.zfznjj.smarthome.model.DoorRecord;
import com.zfznjj.smarthome.model.ETAirDevice;
import com.zfznjj.smarthome.model.ETKey;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.ElectricOrder;
import com.zfznjj.smarthome.model.MasterNode;
import com.zfznjj.smarthome.model.Scene;
import com.zfznjj.smarthome.model.SceneElectric;
import com.zfznjj.smarthome.model.SceneOrder;
import com.zfznjj.smarthome.model.User;
import com.zfznjj.smarthome.model.UserRoom;
import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.util.JsonPluginsUtil;
import com.zfznjj.smarthome.util.SmartHomeUtil;
import com.zfznjj.smarthome.util.SmsUtil;
import com.zfznjj.smarthome.util.WriteLog;
import com.zfznjj.smarthome.websocket.AppWebSocket;
import com.zfznjj.smarthome.websocket.MasterWebSocket;

import net.sf.json.JSONArray;

//import javafx.scene.chart.ScatterChart;
//import javafx.scene.control.cell.CheckBoxListCell;

public class SmarthomeServiceImpl implements SmarthomeService {

	// 以下是该业务逻辑组件所依赖的DAO组件
	private MasterNodeDao masterNodeDao;
	private AccountDao accountDao;
	private UserDao userDao;
	private UserRoomDao userRoomDao;
	private ElectricDao electricDao;
	private ElectricOrderDao electricOrderDao;
	private ChildNodeDao childNodeDao;
	private ElectricSharedDao electricSharedDao;
	private AlarmRecordDao alarmRecordDao;
	private DoorRecordDao doorRecordDao;
	private RoomSharedDao roomSharedDao;
	private SceneDao sceneDao;
	private SceneElectricDao sceneElectricDao;
	private SceneOrderDao sceneOrderDao;
	private OtherDao otherDao;
	private ETKeyDao eTKeyDao;
	private ETAirDeviceDao eTAirDeviceDao;
	private CrashDao crashDao;

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取和当前线程绑定的Session
	 * 
	 * @return
	 */
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setCrashDao(CrashDao crashDao) {
		this.crashDao = crashDao;
	}

	public void setRoomSharedDao(RoomSharedDao roomSharedDao) {
		this.roomSharedDao = roomSharedDao;
	}

	public void setAlarmRecordDao(AlarmRecordDao alarmRecordDao) {
		this.alarmRecordDao = alarmRecordDao;
	}

	public void setDoorRecordDao(DoorRecordDao doorRecordDao) {
		this.doorRecordDao = doorRecordDao;
	}

	public void setMasterNodeDao(MasterNodeDao masterNodeDao) {
		this.masterNodeDao = masterNodeDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserRoomDao(UserRoomDao userRoomDao) {
		this.userRoomDao = userRoomDao;
	}

	public void setElectricDao(ElectricDao electricDao) {
		this.electricDao = electricDao;
	}

	public void setChildNodeDao(ChildNodeDao childNodeDao) {
		this.childNodeDao = childNodeDao;
	}

	public void setElectricSharedDao(ElectricSharedDao electricSharedDao) {
		this.electricSharedDao = electricSharedDao;
	}

	public void setSceneDao(SceneDao sceneDao) {
		this.sceneDao = sceneDao;
	}

	public void setSceneElectricDao(SceneElectricDao sceneElectricDao) {
		this.sceneElectricDao = sceneElectricDao;
	}

	public void setElectricOrderDao(ElectricOrderDao electricOrderDao) {
		this.electricOrderDao = electricOrderDao;
	}

	public void setSceneOrderDao(SceneOrderDao sceneOrderDao) {
		this.sceneOrderDao = sceneOrderDao;
	}

	public void setOtherDao(OtherDao otherDao) {
		this.otherDao = otherDao;
	}

	public void seteTKeyDao(ETKeyDao eTKeyDao) {
		this.eTKeyDao = eTKeyDao;
	}

	public void seteTAirDeviceDao(ETAirDeviceDao eTAirDeviceDao) {
		this.eTAirDeviceDao = eTAirDeviceDao;
	}

	/**
	 * return: 0:密码不正确， 1：登录成功， 2：不存在该用户
	 * 
	 * @throws Exception
	 */
	@Override
	public String validLogin(String accountCode, String password) throws Exception {
		System.out.println("accountCode:" + accountCode + "  password:" + password);
		// WriteLog writeLog = new WriteLog();
		WriteLog.writeLog("login.log", "accountCode:" + accountCode + "  password:" + password);
		Account account = accountDao.select(accountCode);
		if (account != null) {
			if (password.equals(account.getPassword())) {
				return 1 + "";
			}
			return 0 + "";
		}
		return 2 + "";
	}

	@Override
	public int isExistAccount(String accountCode) {
		Account account = accountDao.select(accountCode);
		if (account == null) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public String getAdminAccountCode(String masterCode) {
		String str = masterNodeDao.getAdminCode(masterCode);
		return str;
	}

	@Override
	public String updateAccountPassword(String accountCode, String oldPassword, String newPassword) {
		System.out.println("userName: " + accountCode + " oldPassWord: " + oldPassword + "newPassword: " + newPassword);
		Account account = accountDao.select(accountCode);
		if (account != null) {
			if (account.getPassword().equals(oldPassword)) {
				account.setPassword(newPassword);
				accountDao.saveOrUpdate(account);
				return "1";
			}
			return "0";
		}
		return "0";
	}

	@Override
	public String resetAccountPassword(String accountCode, String newPassword) {
		System.out.println("accountCode: " + accountCode + " newPassword: " + newPassword);
		Account account = accountDao.select(accountCode);
		if (account != null) {
			account.setPassword(newPassword);
			accountDao.saveOrUpdate(account);
			return "1";
		}
		return "0";
	}

	@Override
	public int updateUserIP(String masterCode, String userIP) {
		accountDao.updateUserTimeByMasterCode(masterCode);
		return userDao.updateUserIP(masterCode, userIP);
	}

	@Override
	public List<String> getAccountCodesByMaster(String masterCode) {
		// TODO Auto-generated method stub
		return userDao.seleteAccountCodesByMaster(masterCode);
	}

	/**
	 * return -2:插入失败，1：插入成功，2：已添加过该房间
	 */
	@Override
	public int addUserRoom(String masterCode, int roomIndex, String roomName, int roomSequ, int roomImg) {
		UserRoom userRoom = userRoomDao.select(masterCode, roomIndex);
		if (userRoom == null) {
			userRoom = new UserRoom();
			userRoom.setMasterCode(masterCode);
			userRoom.setRoomIndex(roomIndex);
			userRoom.setRoomName(roomName);
			userRoom.setRoomSequ(roomSequ);
			userRoom.setRoomImg(roomImg);

			userDao.updateUserAreaTime(masterCode);
			return userRoomDao.saveOrUpdate(userRoom);
		}

		return 2;
	}

	@Override
	public int addScene(String accountCode, String masterCode, String sceneName, int sceneIndex, int sceneSequ,
			int sceneImg) {
		Scene scene = sceneDao.select(masterCode, sceneIndex);
		if (scene == null) {
			Timestamp buildTime = new Timestamp(new Date().getTime());
			scene = new Scene();
			scene.setMasterCode(masterCode);
			scene.setSceneIndex(sceneIndex);
			scene.setSceneName(sceneName);
			scene.setSceneImg(sceneImg);
			
			int newSceneSequ = sceneDao.getMaxSceneSequ(masterCode) + 1;
			if (newSceneSequ != sceneSequ) {
				try {
					WriteLog.writeLog("error.log",
							"【addScene错误】" + "masterCode:" + masterCode + "  sceneIndex:" + sceneIndex + "  sceneSequ:"
									+ sceneSequ + "  newSceneSequ:" + newSceneSequ);
				} catch (IOException e) {
					System.out.println("WriteLog addScene error");
				}
			}
			scene.setSceneSequ(newSceneSequ);
//			scene.setSceneSequ(sceneSequ); // TODO：问题可能出在了这里，sequ值不正确导致了苹果情景不显示的问题
			scene.setBuildTime(SmartHomeUtil.TimestampToString(buildTime));
			userDao.updateUserSceneTime(masterCode);
			return sceneDao.saveOrUpdate(scene);
		}
		return 0;
	}

	@Override
	public int addSceneElectric(String masterCode, String electricCode, String electricOrder, String accountCode,
			int sceneIndex, String orderInfo, int electricIndex, String electricName, int roomIndex, int electricType) {
		SceneElectric sceneElectric = sceneElectricDao.select(masterCode, electricCode, orderInfo, sceneIndex);
		if (sceneElectric == null) {
			sceneElectric = new SceneElectric();
			sceneElectric.setMasterCode(masterCode);
			sceneElectric.setElectricCode(electricCode);
			sceneElectric.setElectricOrder(electricOrder);
			sceneElectric.setSceneIndex(sceneIndex);
			sceneElectric.setOrderInfo(orderInfo);
			sceneElectric.setElectricIndex(electricIndex);
			sceneElectric.setElectricName(electricName);
			sceneElectric.setRoomIndex(roomIndex);
			sceneElectric.setElectricType(electricType);

			SceneOrder sceneOrder = new SceneOrder();
			sceneOrder.setMasterCode(masterCode);
			sceneOrder.setElectricCode(electricCode);
			sceneOrder.setOrderData(electricOrder);
			sceneOrder.setOrderInfo(orderInfo);
			sceneOrder.setSceneIndex(sceneIndex);
			sceneOrder.setIsReaded('N');
			Timestamp timestamp = new Timestamp(new Date().getTime());
			sceneOrder.setWriteTime(SmartHomeUtil.TimestampToString(timestamp));

			MasterWebSocket.sendSceneOrder(masterCode, sceneOrder);
			sceneOrderDao.insert(sceneOrder);

			userDao.updateUserSceneELectricTime(masterCode);
			return sceneElectricDao.saveOrUpdate(sceneElectric);
		}
		return 0;
	}

	@Override
	public int addCrashLog(String logName, byte[] logDetail, String appName) {
		CrashLog crashLog = new CrashLog();
		crashLog.setAppName(appName);
		crashLog.setLogDetail(logDetail);
		crashLog.setLogName(logName);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		crashLog.setLogTime(SmartHomeUtil.TimestampToString(timestamp));
		return crashDao.save(crashLog);
	}

	@Override
	public int addETKeys(String keyJsonString) {
		// ETKey etKey = JsonPluginsUtil.jsonToBean(keyJsonString, ETKey.class);
		List<ETKey> list = JsonPluginsUtil.jsonToBeanList(keyJsonString, ETKey.class);
		return eTKeyDao.save(list);
	}

	@Override
	public int addETAirDevice(String eTAirJsonString) {
		// TODO Auto-generated method stub
		ETAirDevice etAirDevice = JsonPluginsUtil.jsonToBean(eTAirJsonString, ETAirDevice.class);
		return eTAirDeviceDao.save(etAirDevice);
	}

	private void shareNewAddElectric(String masterCode, int electricIndex) {
		List<User> users = userDao.selectAllByMasterCode(masterCode);
		for (User user : users) {
			electricSharedDao.addSharedElectric(user.getAccountCode(), masterCode, electricIndex);
		}
	}

	@Override
	public int addSceneOrder(String masterCode, String electricCode, String electricOrder, String orderInfo,
			int sceneIndex) {
		SceneOrder sceneOrder = new SceneOrder();
		sceneOrder.setMasterCode(masterCode);
		sceneOrder.setElectricCode(electricCode);
		sceneOrder.setOrderData(electricOrder);
		sceneOrder.setOrderInfo(orderInfo);
		sceneOrder.setSceneIndex(sceneIndex);
		sceneOrder.setIsReaded('N');
		Timestamp timestamp = new Timestamp(new Date().getTime());
		sceneOrder.setWriteTime(SmartHomeUtil.TimestampToString(timestamp));

		MasterWebSocket.sendSceneOrder(masterCode, sceneOrder);
		return sceneOrderDao.insert(sceneOrder);
	}

	@Override
	public int updateSceneElectrics(String masterCode, int electricIndex, String electricName) {
		List<SceneElectric> sceneElectrics = sceneElectricDao.select(masterCode, electricIndex);
		for (int i = 0; i < sceneElectrics.size(); i++) {
			SceneElectric sceneElectric = sceneElectrics.get(i);
			sceneElectric.setElectricName(electricName);
			int flag = sceneElectricDao.saveOrUpdate(sceneElectric);
			if (flag != 1) {
				return -2;
			}
		}
		return 1;
	}

	@Override
	public int updateElectric(String masterCode, String electricCode, int electricIndex, String electricName,
			int sceneIndex) {
		// 修改electrics表中的数据
		Electric electric = electricDao.select(masterCode, electricIndex);
		electric.setElectricName(electricName);
		electric.setSceneIndex(sceneIndex);

		// 同时修改sceneElectrics表中的电器名
		updateSceneElectrics(masterCode, electricIndex, electricName);

		// 在情景指令表中，添加一条新的数据，不管之前是否发出过
		addSceneOrder(masterCode, electricCode, "SH", electric.getOrderInfo(), sceneIndex);

		accountDao.updateUserTimeByMasterCode(masterCode);
		userDao.updateUserElectricTime(masterCode);
		return electricDao.saveOrUpdate(electric);
	}

	@Override
	public int updateElectric1(String masterCode, String electricCode, int electricIndex, String electricName,
			int sceneIndex, String electricOrder) {
		// 修改electrics表中的数据
		Electric electric = electricDao.select(masterCode, electricIndex);// 感觉这里需要修改一下，添加一个字段extras，防止重复覆盖
		electric.setElectricName(electricName);
		electric.setSceneIndex(sceneIndex);
		// 将开触发或是关触发保存在这里，使用json字符串的格式存储
		String sJson = electric.getExtras();
		String extras;
		Map map;
		if (sJson.equals("") || sJson == null) {
			map = new HashMap();
		} else {
			map = JsonPluginsUtil.jsonToMap(sJson);
		}
		map.put(electricOrder, String.valueOf(sceneIndex));
		extras = JsonPluginsUtil.mapToJson(map);
		electric.setExtras(extras);
		// 同时修改sceneElectrics表中的电器名
		updateSceneElectrics(masterCode, electricIndex, electricName);
		// electricOrder为SH，说明门锁开时触发情景模式，为SG说明门锁关时触发情景模式
		addSceneOrder(masterCode, electricCode, electricOrder, electric.getOrderInfo(), sceneIndex);
		accountDao.updateUserTimeByMasterCode(masterCode);
		userDao.updateUserElectricTime(masterCode);
		return electricDao.saveOrUpdate(electric);
	}

	@Override
	public int updateUserRoom(String masterCode, int roomIndex, String roomName, int roomImg) {
		UserRoom userRoom = userRoomDao.select(masterCode, roomIndex);
		if (userRoom != null) {
			userRoom.setRoomName(roomName);
			userRoom.setRoomImg(roomImg);

			userDao.updateUserAreaTime(masterCode);
			return userRoomDao.saveOrUpdate(userRoom);
		}
		return -1;
	}

	@Override
	public int updateUserName(String accountCode, String masterCode, String userName) {
		accountDao.updateUserTimeByMasterCode(masterCode);
		return userDao.updateUserName(accountCode, masterCode, userName);
	}

	/**
	 * return -2:更新失败，0：改用户不存在 1：更新成功。
	 */
	@Override
	public int updateAccount(String accountCode, String accountName, String accountPhone, String accountAddress,
			String accountEmail) {
		Account account1 = accountDao.select(accountCode);
		if (account1 == null) {
			return 0;
		} else {
			account1.setAccountName(accountName);
			account1.setAccountPhone(accountPhone);
			account1.setAccountAddress(accountAddress);
			account1.setAccountEmail(accountEmail);
			Timestamp timestamp = new Timestamp(new Date().getTime());
			account1.setAccountTime(SmartHomeUtil.TimestampToString(timestamp));
			return accountDao.saveOrUpdate(account1);
		}
	}

	@Override
	public int updateAccountPhoto(String accountCode, byte[] photo) {
		Account account = accountDao.select(accountCode);
		if (account == null) {
			return 0;
		} else {
			account.setPhoto(photo);
			Timestamp timestamp = new Timestamp(new Date().getTime());
			account.setAccountTime(SmartHomeUtil.TimestampToString(timestamp));
			return accountDao.saveOrUpdate(account);
		}
	}

	@Override
	public int deleteRoom(String masterCode, int roomIndex) {
		userDao.updateUserAreaTime(masterCode);
		return userRoomDao.delete(masterCode, roomIndex);
	}

	@Override
	public int updateRoomSequ(String masterCode, int roomSequ) {
		userDao.updateUserAreaTime(masterCode);
		return userRoomDao.updateRoomSequ(masterCode, roomSequ);
	}

	// 【已弃用】
	@Override
	public int deleteElectric(String masterCode, int electricIndex) {
		// 更新电器时间
		userDao.updateUserElectricTime(masterCode);
		List<SceneElectric> list = sceneElectricDao.select(masterCode, electricIndex);
		if (list != null && list.size() > 0) {
			// 更新情景电器时间
			userDao.updateUserSceneELectricTime(masterCode);
			int count = list.size();
			// 在SceneOrder表中添加删除电器的指令
			for (int i = 0; i < count; i++) {
				SceneOrder sceneOrder = new SceneOrder();
				sceneOrder.setMasterCode(masterCode);
				sceneOrder.setElectricCode(list.get(i).getElectricCode());
				sceneOrder.setOrderData("SR");
				sceneOrder.setOrderInfo(list.get(i).getOrderInfo());
				sceneOrder.setSceneIndex(list.get(i).getSceneIndex());
				sceneOrder.setIsReaded('N');
				Timestamp timestamp = new Timestamp(new Date().getTime());
				sceneOrder.setWriteTime(SmartHomeUtil.TimestampToString(timestamp));

				MasterWebSocket.sendSceneOrder(masterCode, sceneOrder);
				sceneOrderDao.insert(sceneOrder);
			}
		}
		// 删除情景电器
		sceneElectricDao.delete(masterCode, electricIndex);
		// 删除分享电器表中的电器
		electricSharedDao.deleteByElectric(masterCode, electricIndex);

		// 如果是红外类电器，还需要在指令表中删除电器
		Electric electric = electricDao.select(masterCode, electricIndex);
		if (electric != null && electric.getElectricCode().startsWith("09")) {
			if (electric.getElectricType() == 9) { // 对于空调，删除空调参数表中的数据
				eTAirDeviceDao.delete(masterCode, electricIndex);
			}
			// 删除红外电器中指令表中的数据
			eTKeyDao.delete(masterCode, electricIndex);
		}
		return electricDao.delete(masterCode, electricIndex);
	}

	// 删除电器时： 1、更新user的electric_time 2、删除情景模式电器表中的电器 3、删除分享电器表中的电器
	public int deleteElectric(String masterCode, String electricCode, int electricIndex) {
		// 更新电器时间
		userDao.updateUserElectricTime(masterCode);
		List<SceneElectric> list = sceneElectricDao.select(masterCode, electricIndex);
		if (list != null && list.size() > 0) {
			// 更新情景电器时间
			userDao.updateUserSceneELectricTime(masterCode);
			int count = list.size();
			// 在SceneOrder表中添加删除电器的指令
			for (int i = 0; i < count; i++) {
				SceneOrder sceneOrder = new SceneOrder();
				sceneOrder.setMasterCode(masterCode);
				sceneOrder.setElectricCode(list.get(i).getElectricCode());
				sceneOrder.setOrderData("SR");
				sceneOrder.setOrderInfo(list.get(i).getOrderInfo());
				sceneOrder.setSceneIndex(list.get(i).getSceneIndex());
				sceneOrder.setIsReaded('N');
				Timestamp timestamp = new Timestamp(new Date().getTime());
				sceneOrder.setWriteTime(SmartHomeUtil.TimestampToString(timestamp));

				MasterWebSocket.sendSceneOrder(masterCode, sceneOrder);
				sceneOrderDao.insert(sceneOrder);
			}
		}
		// 删除情景电器
		sceneElectricDao.delete(masterCode, electricIndex);
		// 删除分享电器表中的电器
		electricSharedDao.deleteByElectric(masterCode, electricIndex);

		// 判断是否需要删除childnode表内容
		List<Electric> electrics = electricDao.select(masterCode, electricCode);
		if (!electrics.isEmpty() && electrics.size() == 1) {
			childNodeDao.delete(masterCode, electricCode);
		}

		// 如果是红外类电器，还需要在指令表中删除电器
		Electric electric = electricDao.select(masterCode, electricIndex);
		if (electric != null && electric.getElectricCode().startsWith("09")) {
			if (electric.getElectricType() == 9) { // 对于空调，删除空调参数表中的数据
				eTAirDeviceDao.delete(masterCode, electricIndex);
			}
			// 删除红外电器中指令表中的数据
			eTKeyDao.delete(masterCode, electricIndex);
		}
		// 如果是传感器，则在报警列表中删除对应的电器，从后向前循环，让sequ值都保证是正确的
		List<AlarmRecord> alarmRecords = alarmRecordDao.select(masterCode, electricCode);
		int size = alarmRecords.size();
		for (int i = size - 1; i >= 0; i--) {
			int recordSequ = alarmRecords.get(i).getRecordSequ();
			int a = alarmRecordDao.delete(masterCode, recordSequ);
			int b = alarmRecordDao.updateAlarmRecordSequ(masterCode, recordSequ);
			System.out.println(a + " " + b);
		}
		return electricDao.delete(masterCode, electricIndex);
	}

	@Override
	public int deleteScene(String masterCode, int sceneIndex) {
		// 向sceneOrder表中写数据
		SceneOrder sceneOrder = new SceneOrder();
		sceneOrder.setMasterCode(masterCode);
		sceneOrder.setElectricCode("********");
		sceneOrder.setOrderData("TR");
		sceneOrder.setOrderInfo("**");
		sceneOrder.setSceneIndex(sceneIndex);
		sceneOrder.setIsReaded('N');

		MasterWebSocket.sendSceneOrder(masterCode, sceneOrder);
		sceneOrderDao.insert(sceneOrder);

		// 删除该情景模式中的所有电器
		sceneElectricDao.deleteBySceneIndex(masterCode, sceneIndex);

		// 更新与情景有关的时间：情景时间、情景电器时间
		userDao.updateUserSceneTime(masterCode);
		userDao.updateUserSceneELectricTime(masterCode);

		return sceneDao.delete(masterCode, sceneIndex);
	}

	@Override
	public int deleteSceneElectric(String masterCode, int electricIndex, int sceneIndex) {
		userDao.updateUserSceneELectricTime(masterCode);
		List<SceneElectric> list = sceneElectricDao.select(masterCode, electricIndex, sceneIndex);
		if (list != null && list.size() > 0) {
			// 更新情景电器时间
			userDao.updateUserSceneELectricTime(masterCode);
			int count = list.size();
			// 在SceneOrder表中添加删除电器的指令
			for (int i = 0; i < count; i++) {
				SceneOrder sceneOrder = new SceneOrder();
				sceneOrder.setMasterCode(masterCode);
				sceneOrder.setElectricCode(list.get(i).getElectricCode());
				sceneOrder.setOrderData("SR");
				sceneOrder.setOrderInfo(list.get(i).getOrderInfo());
				sceneOrder.setSceneIndex(list.get(i).getSceneIndex());
				sceneOrder.setIsReaded('N');
				Timestamp timestamp = new Timestamp(new Date().getTime());
				sceneOrder.setWriteTime(SmartHomeUtil.TimestampToString(timestamp));

				MasterWebSocket.sendSceneOrder(masterCode, sceneOrder);
				sceneOrderDao.insert(sceneOrder);
			}
		}
		return sceneElectricDao.delete(masterCode, electricIndex, sceneIndex);
	}

	@Override
	public int updateSceneSequ(String masterCode, int sceneSequ) {
		userDao.updateUserSceneTime(masterCode);
		return sceneDao.updateSceneSequ(masterCode, sceneSequ);
	}

	/**
	 * 有待优化
	 */
	@Override
	public List<ElectricState> getElectricStateByUser(String accountCode, String masterCode) {
		return childNodeDao.getStateByMasterCode(masterCode);
	}

	@Override
	public List<User> loadUserFromWs(String accountCode, String userTime) {
		Account account = accountDao.select(accountCode);
		if (account == null) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (userTime != null && userTime.equals(account.getUserTime())) {
				return null;
			}
			List<User> users = userDao.selectByAccountCode(accountCode);
			User user = new User();
			user.setExtraTime(account.getUserTime());
			users.add(user);
			return users;
		}
	}

	@Override
	public List<Account> loadSharedAccount(String masterCode) {
		return accountDao.selectAccountByMasterCode(masterCode);
	}

	@Override
	public Account loadAccountFromWs(String accountCode, String accountTime) {
		Account account = accountDao.select(accountCode);
		if (account == null) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (accountTime != null && accountTime.equals(account.getAccountTime())) {
				return null;
			}
			return account;
		}
	}

	/**
	 * 1、若不存在该主节点，则可以直接添加，并默认该主节点的拥有者是该账户 2、若存在该主节点： a 该主节点没有拥有者，则可以直接添加 b
	 * 该主节点有拥有者，则不能添加，需通过该主节点的拥有者分享 return 1： 添加成功 -2：添加失败 0：2-b情况，不允许添加
	 * 
	 */
	@Override
	public int addUser(String accountCode, String masterCode, String userName, String userIp) {
		MasterNode masterNode = masterNodeDao.select(masterCode);
		int flag = 0;
		if (masterNode == null) {
			masterNode = new MasterNode();
			masterNode.setMasterCode(masterCode);
			masterNode.setMasterName(userName);
			masterNode.setOwner(accountCode);
			addNewUserScene(masterCode); // 有一个主节点是，直接添加四中情景模式
			flag = masterNodeDao.saveOrUpdate(masterNode);
		} else if (masterNode.getOwner() == null || masterNode.getOwner().equals("")) {
			masterNode.setOwner(accountCode);
			flag = masterNodeDao.saveOrUpdate(masterNode);
		} else if (masterNode.getOwner() != null && !masterNode.getOwner().equals("")
				&& !masterNode.getOwner().equals(accountCode)) {
			return 0;
		}
		if (flag != -2) {
			User user = userDao.select(accountCode, masterCode);
			if (user != null) {
				return -3;
			}
			Timestamp timestamp = new Timestamp(new Date().getTime());
			user = new User();
			user.setMasterCode(masterCode);
			user.setAccountCode(accountCode);
			user.setUserName(userName);
			user.setUserIp(userIp);
			user.setIsAdmin(1);
			user.setAreaTime(SmartHomeUtil.TimestampToString(timestamp));
			user.setSceneTime(SmartHomeUtil.TimestampToString(timestamp));
			user.setElectricTime(SmartHomeUtil.TimestampToString(timestamp));
			user.setSceneELectricTime(SmartHomeUtil.TimestampToString(timestamp));

			accountDao.updateUserTime(accountCode);
			return userDao.saveOrUpdate(user);
		} else {
			return -2;
		}

	}

	/**
	 * 添加分享账户 return -1：不存在被分享的账户 0：已分享 1：分享成功 -2：分享失败
	 */
	@Override
	public int addSharedUser(String accountCode, String masterCode, String userName, String userIp) {
		Account account = accountDao.select(accountCode);
		if (account == null) {
			return -1;
		}
		User user = userDao.select(accountCode, masterCode);
		if (user == null) {
			user = new User();
			Timestamp timestamp = new Timestamp(new Date().getTime());
			user.setAccountCode(accountCode);
			user.setMasterCode(masterCode);
			user.setAccountCode(accountCode);
			user.setUserName(userName);
			user.setUserIp(userIp);
			user.setIsAdmin(0);
			user.setAreaTime(SmartHomeUtil.TimestampToString(timestamp));
			user.setSceneTime(SmartHomeUtil.TimestampToString(timestamp));
			user.setElectricTime(SmartHomeUtil.TimestampToString(timestamp));
			user.setSceneELectricTime(SmartHomeUtil.TimestampToString(timestamp));
			System.out.println(user);
			int result = userDao.saveOrUpdate(user);
			electricSharedDao.addSharedElectric(accountCode, masterCode);
			accountDao.updateUserTime(accountCode);
			return result;
		}
		return 0;
	}

	@Override
	public List<UserRoom> loadUserRoomFromWs(String accountCode, String masterCode, String areaTime) {
		User user = userDao.select(accountCode, masterCode);
		/* 若用户读取用户失败或者用户区域时间没发生变化， 则不读取 */
		if (user == null || (areaTime != null && user.getAreaTime().equals(areaTime))) {
			return null;
		}
		/*
		 * 区域不设置权限，都从主区域表读取
		 */
		List<UserRoom> userRooms = userRoomDao.select(masterCode);
		UserRoom userRoom = new UserRoom();
		userRoom.setExtraTime(user.getAreaTime());
		userRooms.add(userRoom);
		return userRooms;
	}

	@Override
	public List<Electric> loadElectricFromWs(String accountCode, String masterCode, String electricTime) {
		User user = userDao.select(accountCode, masterCode);
		/* 若用户读取用户失败或者用户区域时间没发生变化， 则不读取 */
		if (user == null || (electricTime != null && user.getElectricTime().equals(electricTime))) {
			return null;
		}
		// 若用户区域时间发生改变： 是否主账户：
		// 1、是：则从主电器表读取
		// 2、否：主节点是否有主账户：
		// 有：从分享区域表读取
		// 无：从主电器表读取
		MasterNode masterNode = masterNodeDao.select(masterCode);
		if (masterNode == null) {
			return null;
		}
		List<Electric> electrics;
		if (user.getIsAdmin() == 1 || masterNode.getOwner() == null || masterNode.getOwner().equals("")) {
			electrics = electricDao.select(masterCode);
		} else {
			electrics = electricSharedDao.select(accountCode, masterCode);
			electrics = FixSharedElectricSequ(electrics);
		}
		Electric electric = new Electric();
		electric.setExtraTime(user.getElectricTime());
		electrics.add(electric);
		return electrics;
	}

	@Override
	public String loadKeyByElectric(String masterCode, int electricIndex) {
		return JsonPluginsUtil.beanListToJson(eTKeyDao.loadKeyByElectric(masterCode, electricIndex));
	}

	@Override
	public String loadETAirByElectric(String masterCode, int electricIndex) {
		return JsonPluginsUtil.beanToJson(eTAirDeviceDao.query(masterCode, electricIndex));
	}

	@Override
	public List<ElectricSharedLoacl> loadAllSharedElectric(String masterCode) {
		// List<ElectricSharedLoacl> list =
		// electricSharedDao.select(masterCode);
		return electricSharedDao.select(masterCode);
	}

	// 对情景指令进行检查，防止出现null值
	private List<SceneOrder> checkSceneOrderList(List<SceneOrder> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getElectricCode() == null) {
				list.remove(i);
				i--;
			}
		}
		return list;
	}

	// 对电器指令进行检查，超过2条指令的判断第二条以后的指令是否是红外指令，是则去除，否则不去除。保证红外指令一次只能发送一条
	private List<OrderInfo> checkElectricOrderList(List<OrderInfo> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getElectricCode() == null) {
				list.remove(i);
				i--;
			}
		}
		int count = list.size();
		if (count >= 1) {
			for (int i = 1; i < list.size(); i++) {
				if (i >= 3) {
					list.remove(i);
					i--;
					continue;
				}
				if (list.get(i).getElectricCode().startsWith("09")) {
					list.remove(i);
					i--;
				}
			}
		}
		return list;
	}

	@Override
	public String ugetElectricOrderByMasterNode(String masterCode) {
		StringBuffer result = new StringBuffer();
		List<OrderInfo> list1 = electricOrderDao.ugetElectricOrders(masterCode);
		List<OrderInfo> list = checkElectricOrderList(list1);
		updateELectricOrderTime(list);
		if (list.isEmpty() || list.size() == 0) {
			result = result.append("none");
		} else {
			for (int i = 0; i < list.size(); i++) {
				OrderInfo orderInfo = list.get(i);
				String electricCode = orderInfo.getElectricCode();
				result.append('<').append(orderInfo.getElectricCode()).append(orderInfo.getOrder());
				if (list.get(i).getElectricCode().startsWith("09")) {
					String irCode = toIrCode(list.get(i).getOrderInfo());
					String length = Integer.toHexString(irCode.length());
					String irCount = (length.length() < 2) ? "0" + length : length;
					result.append(irCount);
				}
				result.append(orderInfo.getOrderInfo()).append("00").append('>');

				if (i < list.size() - 1) {
					result = result.append('|');
				}
			}
		}
		return result.toString();
	}

	@Override
	public String ugetSceneOrderByMasterNode(String masterCode) {
		StringBuffer result = new StringBuffer();
		List<SceneOrder> list1 = sceneOrderDao.ugetSceneOrders(masterCode);
		List<SceneOrder> list = checkSceneOrderList(list1);
		updateSceneOrderTime(list);
		if (list.isEmpty() || list.size() == 0) {
			result = result.append("none");
		} else {
			for (int i = 0; i < list.size(); i++) {
				SceneOrder sceneOrder = list.get(i);
				result.append('<').append(sceneOrder.getElectricCode());
				if (sceneOrder.getOrderData().equals("XH")) {
					result.append("SH");
				} else if (sceneOrder.getOrderData().equals("XG")) {
					result.append("SG");
				} else {
					result.append(sceneOrder.getOrderData());
				}
				result.append(sceneOrder.getOrderInfo()).append(sceneOrder.getSceneIndex()).append("*******")
						.append("FF").append('>');
				if (i < list.size() - 1) {
					result = result.append('|');
				}
			}
		}
		return result.toString();
	}

	private void updateELectricOrderTime(List<OrderInfo> list) {
		for (OrderInfo orderInfo : list) {
			electricOrderDao.updateIsReaded(orderInfo.getOrderID());
		}

	}

	private void updateSceneOrderTime(List<SceneOrder> list) {
		for (SceneOrder sceneOrder : list) {
			sceneOrderDao.updateIsReaded(sceneOrder.getId());
		}
	}

	@Override
	public void sendSms(String phoneNum, String msg) {
		System.out.println("向" + phoneNum + "发送消息：" + msg);
		return;
	}

	@Override
	public String sendSmsCode(String phoneNum) {
		String str = "";
		try {
			str = SmsUtil.sendSmsCode(phoneNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String checkSmsCode(String phoneNum, String code) {
		String str = "";
		try {
			str = SmsUtil.checkSmsCode(phoneNum, code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public int updateIRKeyValue(String masterCode, int electricIndex, int keyKey, String keyValue) {
		// 更新该主控下用户的电器时间标识
		userDao.updateUserElectricTime(masterCode);
		return eTKeyDao.updateIRKeyValue(masterCode, electricIndex, keyKey, keyValue);
	}

	/**
	 * 更新电器指令
	 * 
	 * @throws IOException
	 */
	@Override
	public int addELectricOrder(String masterCode, String electricCode, String orderData, String orderInfo) {
		// TODO Auto-generated method stub
		ElectricOrder electricOrder = new ElectricOrder();
		Timestamp timestamp = new Timestamp(new Date().getTime());
		electricOrder.setMasterCode(masterCode);
		electricOrder.setElectricCode(electricCode);
		electricOrder.setOrderData(orderData);
		electricOrder.setOrderInfo(orderInfo);
		electricOrder.setWriteTime(SmartHomeUtil.TimestampToString(timestamp));
		electricOrder.setIsReaded('N');

		MasterWebSocket.sendElectricOrder(masterCode, electricOrder);
		return electricOrderDao.insert(electricOrder);
	}

	@Override
	public List<Scene> loadSceneFromWs(String accountCode, String masterCode, String sceneTime) {
		User user = userDao.select(accountCode, masterCode);
		/* 若用户读取用户失败或者用户情景时间没发生变化， 则不读取 */
		if (user == null || (sceneTime != null && user.getSceneTime().equals(sceneTime))) {
			return null;
		}
		/*
		 * 区域不设置权限，都从主区域表读取
		 */
		List<Scene> scenes = sceneDao.select(masterCode);
		Scene scene = new Scene();
		scene.setExtraTime(user.getSceneTime());
		scenes.add(scene);
		return scenes;
	}

	@Override
	public List<Scene> masterReadScene(String masterCode) {
		return sceneDao.select(masterCode);
	}

	@Override
	public List<SceneElectric> loadSceneElectricFromWs(String accountCode, String masterCode,
			String sceneElectricTime) {
		User user = userDao.select(accountCode, masterCode);
		/* 若用户读取用户失败或者用户区域时间没发生变化， 则不读取 */
		if (user == null || (sceneElectricTime != null && user.getSceneELectricTime().equals(sceneElectricTime))) {
			return null;
		}
		/*
		 * 若用户区域时间发生改变： 是否主账户：
		 * 
		 */
		List<SceneElectric> sceneElectrics;
		sceneElectrics = sceneElectricDao.select(masterCode);
		SceneElectric sceneElectric = new SceneElectric();
		sceneElectric.setExtraTime(user.getSceneELectricTime());
		sceneElectrics.add(sceneElectric);
		return sceneElectrics;
	}

	@Override
	public List<ElectricForVoice> selectElectricForVoice(String masterCode) {
		// TODO Auto-generated method stub
		return electricDao.selectForVoice(masterCode);
	}

	/**
	 * 放弃管理员权限 return -2:操作失败 1：操作成功， 0：不能执行该操作
	 */
	@Override
	public int giveUpAdmin(String masterCode, String owner) {
		MasterNode masterNode = masterNodeDao.select(masterCode);
		if (masterNode != null) {
			if (masterNode.getOwner() != null && owner.equals(masterNode.getOwner())) {
				int flag1 = 0;
				int flag2 = 0;
				masterNode.setOwner(null);
				flag1 = masterNodeDao.saveOrUpdate(masterNode);
				User user = userDao.select(owner, masterCode);
				if (user != null) {
					user.setIsAdmin(0);
					flag2 = userDao.saveOrUpdate(user);
				}
				if (flag1 == 1 && flag2 == 1) {
					return 1;
				} else {
					return -2;
				}
			}
		}
		return 0;
	}

	/**
	 * 获取管理员权限 return -2:操作失败 1：操作成功 0：不能执行该操作
	 */
	@Override
	public int accessAdmin(String masterCode, String owner) {
		MasterNode masterNode = masterNodeDao.select(masterCode);
		if (masterNode != null && masterNode.getOwner() == null) {
			int flag1 = 0;
			int flag2 = 0;
			masterNode.setOwner(owner);
			flag1 = masterNodeDao.saveOrUpdate(masterNode);
			User user = userDao.select(owner, masterCode);
			if (user != null) {
				user.setIsAdmin(1);
				flag2 = userDao.saveOrUpdate(user);
			}
			if (flag1 == 1 && flag2 == 1) {
				return 1;
			} else {
				return -2;
			}
		}
		return 0;
	}

	/**
	 * 判断要删除的用户是否是该主机的管理员 是：将主控的管理员置为null 否：删除用户user
	 */
	@Override
	public int deleteUser(String accountCode, String masterCode) {
		MasterNode masterNode = masterNodeDao.select(masterCode);
		if (masterNode.getOwner() != null && masterNode.getOwner().equals(accountCode)) {
			masterNode.setOwner(null);
			masterNodeDao.saveOrUpdate(masterNode);
		}
		electricSharedDao.deleteByUser(accountCode, masterCode);
		accountDao.updateUserTime(accountCode);
		return userDao.delete(accountCode, masterCode);
	}

	@Override
	public int deleteSharedUser(String masterCode, String accountCode) {
		User user = userDao.select(accountCode, masterCode);
		electricSharedDao.deleteByUserId(user.getUserId());
		return userDao.delete(accountCode, masterCode);
	}

	@Override
	public String getAppVersion() {
		// TODO Auto-generated method stub
		return otherDao.getAppVersion();

	}

	@Override
	public String getAppVersionOs() {
		// TODO Auto-generated method stub
		return otherDao.getAppVersionOs();
	}

	@Override
	public String getAppVersionVoice(String appName) {
		// TODO Auto-generated method stub
		return otherDao.getAppVersionVoice(appName);
		// return "1.0.002";
	}

	@Override
	public int adminSharedElectric(List<AdminElectricBean> adminElectricBeans) {
		for (AdminElectricBean bean : adminElectricBeans) {
			int result = electricSharedDao.adminElectricShared(bean.getAccountCode(), bean.getMasterCode(),
					bean.getElectricIndex(), bean.getAdmin());
			if (result == -1) {
				try {
					throw new Exception();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					return -1;
				}
			}

		}
		if (adminElectricBeans.size() > 0) {
			userDao.updateUserELectricTime(adminElectricBeans.get(0).getAccountCode(),
					adminElectricBeans.get(0).getMasterCode());
		}
		return 1;
	}

	@Override
	public int signLeCheng(String accountCode) {
		// TODO Auto-generated method stub
		String accountTime = SmartHomeUtil.TimestampToString(new Timestamp(new Date().getTime()));
		accountDao.updateAccountTime(accountCode, accountTime);
		return accountDao.signLeCheng(accountCode);
	}

	private void addNewUserScene(String masterCode) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		Scene scene0 = new Scene();
		Scene scene1 = new Scene();
		Scene scene2 = new Scene();
		Scene scene3 = new Scene();
		scene0.setMasterCode(masterCode);
		scene0.setSceneName("回家");
		scene0.setSceneIndex(0);
		scene0.setSceneSequ(0);
		scene0.setSceneImg(0);
		scene0.setBuildTime(SmartHomeUtil.TimestampToString(timestamp));
		sceneDao.saveOrUpdate(scene0);

		scene1.setMasterCode(masterCode);
		scene1.setSceneName("离家");
		scene1.setSceneIndex(1);
		scene1.setSceneSequ(1);
		scene1.setSceneImg(1);
		scene1.setBuildTime(SmartHomeUtil.TimestampToString(timestamp));
		sceneDao.saveOrUpdate(scene1);

		scene2.setMasterCode(masterCode);
		scene2.setSceneName("起床");
		scene2.setSceneIndex(2);
		scene2.setSceneSequ(2);
		scene2.setSceneImg(2);
		scene2.setBuildTime(SmartHomeUtil.TimestampToString(timestamp));
		sceneDao.saveOrUpdate(scene2);

		scene3.setMasterCode(masterCode);
		scene3.setSceneName("睡觉");
		scene3.setSceneIndex(3);
		scene3.setSceneSequ(3);
		scene3.setSceneImg(3);
		scene3.setBuildTime(SmartHomeUtil.TimestampToString(timestamp));
		sceneDao.saveOrUpdate(scene3);
	}

	private String toIrCode(String code) {
		String irCode = "";
		if (code.length() == 4) {
			return SmartHomeUtil.hexString2String(code);
		} else {
			return code;
		}
	}

	private String bytes2Order(byte[] bytes) {
		String irCode = SmartHomeUtil.byte2hex(bytes);
		System.out.println("红外码：" + irCode);
		return irCode;
	}

	// 以下为konnn添加或修复的接口

	// 更新电器状态，当主机返回当前电器状态时进入这个函数，完成各种核心功能
	@Override
	public int updateElectricState(String masterCode, String electricCode, String electricState, String stateInfo)
			throws IOException {
		if (electricCode.startsWith("1000")) {
			if (electricState.equals("Z#")) // 说明是新门锁的控制码，不应该保存和推送
				return 1;
			// 说明是新门锁，需要确定是否保存开锁记录
			saveDoorRecord(masterCode, electricCode, stateInfo);
		} else if (electricCode.startsWith("0D")) {
			// 说明是传感器，需要确定是否发送短信并保存报警记录
			sensorAlarm(masterCode, electricCode, electricState, stateInfo);
		}
		// 调用websocket发送当前电器状态字符串到指定的socket客户端，将电器状态发送给客户端
		String message = "<" + electricCode + electricState + stateInfo + "00>";
		AppWebSocket.sendMessage(masterCode, message);
		return childNodeDao.updateChildNodeState(masterCode, electricCode, electricState, stateInfo);
	}

	@Override
	public int saveAlarmRecord(String masterCode, String electricCode, String electricState, String stateInfo) {
		AlarmRecord alarmRecord = new AlarmRecord();
		alarmRecord.setMasterCode(masterCode);
		alarmRecord.setElectricCode(electricCode);
		alarmRecord.setElectricState(electricState);
		alarmRecord.setStateInfo(stateInfo);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		alarmRecord.setAlarmTime(SmartHomeUtil.TimestampToString(timestamp));
		int newSequ = alarmRecordDao.getMaxRecordSequ(masterCode) + 1;
		int maxSequ = 30;// 每个传感器的记录设置为30条，应该够用了吧？
		if (newSequ == maxSequ) {
			alarmRecordDao.delete(masterCode, 0);
			alarmRecordDao.updateAlarmRecordSequ(masterCode);
			alarmRecord.setRecordSequ(newSequ - 1);
		} else {
			alarmRecord.setRecordSequ(newSequ);
		}
		return alarmRecordDao.saveOrUpdate(alarmRecord);// 保存记录
	}

	// 保存门锁记录
	@Override
	public int saveDoorRecord(String masterCode, String electricCode, String stateInfo) {
		String pre = electricCode.substring(0, 4);
		if (pre.equals("1000")) {
			String sFlag = stateInfo.substring(0, 1);
			if (sFlag.equals("2") || sFlag.equals("4")) {// 说明是开锁
				DoorRecord doorRecord = new DoorRecord();
				doorRecord.setMasterCode(masterCode);
				doorRecord.setElectricCode(electricCode);
				Timestamp timestamp = new Timestamp(new Date().getTime());
				doorRecord.setOpenTime(SmartHomeUtil.TimestampToString(timestamp));
				
				//如果不用app开锁的话，则记录门锁上的开锁者
				doorRecord.setByPerson(stateInfo.substring(1, 5));
				
				int newSequ = doorRecordDao.getMaxRecordSequ(electricCode) + 1;
				int maxSequ = 300; // 当前需求为300
				if (newSequ == maxSequ) { //
					doorRecordDao.delete(electricCode, 0);
					doorRecordDao.updateDoorRecordSequ(electricCode);
					doorRecord.setRecordSequ(newSequ - 1);
				} else {
					doorRecord.setRecordSequ(newSequ);
				}
				return doorRecordDao.saveOrUpdate(doorRecord);// 保存记录
			}
			return -2;
		}
		return -2;
	}

	// 传感器报警，需要在这里判断是否报警和保存报警记录
	private void sensorAlarm(String masterCode, String electricCode, String electricState, String stateInfo) {
		
		// 传感器的报警消息解析格式——<0D31001280FEAB0000******> A=0，B=0~7
		// 00 没事 01 报警 02 防拆 03 报警+防拆 04 电量低 05 报警+电量低 06 防拆+电量低 07 报警+防拆+电量低
		
		if (electricCode.startsWith("0D")) {
			// 历史遗留问题，这里理应只select到一个电器，但是之前可能在同一主机下，存在多个相同编号的传感器
			List<Electric> electrics = electricDao.select(masterCode, electricCode);
			Electric electric = electrics.get(0);
			if (electric.getExtras().equals("0")) { // 说明该传感器处于撤防状态，直接退出
				return;
			}
			if (electric.getElectricCode().startsWith("0D31")) { // 对于门磁传感器，要对extras进行json字符串处理
				String sJson = electric.getExtras();
				if (sJson.equals("") == false) {
					Map map = JsonPluginsUtil.jsonToMap(sJson);
					if (map.containsKey("BuFang") && map.get("BuFang").equals("0")) {
						return;
					}
				}
			}
			
			boolean isOldAlarm; // 旧状态是否处于警报状态
			boolean isAlarm; // 当前新状态是否处于警报状态
			ChildNode childNode = childNodeDao.select(masterCode, electricCode);
			String oldStateInfo = childNode.getStateInfo();
			String flag = oldStateInfo.substring(0, 2);
			if (flag.equals("00") || flag.equals("02") || flag.equals("04") || flag.equals("06")) {
				isOldAlarm = false;
			} else {
				isOldAlarm = true;
			}
			flag = stateInfo.substring(0, 2);
			if (flag.equals("00") || flag.equals("02") || flag.equals("04") || flag.equals("06")) {
				isAlarm = false;
			} else {
				isAlarm = true;
			}
			if (isOldAlarm == isAlarm) { // 报警的状态没有改变的话，是不需要发送短信的
				return;
			}
			// 保存到报警列表中去
			if (isAlarm == true) {
				saveAlarmRecord(masterCode, electricCode, electricState, stateInfo);
			}
			Timestamp timestamp = new Timestamp(new Date().getTime());
			String time = SmartHomeUtil.TimestampToString(timestamp);
			String electricName = electric.getElectricName();
			String msg = time + " 传感器：" + electricName + " ： " + electricCode + " 状态切换为：" + stateInfo;
			System.out.println(msg);
			
			// 发送短信给所有被分享的手机号上去
			List<User> list = userDao.selectByMasterCodeAll(masterCode);
			List<String> phones = new ArrayList<String>();
			for (User user : list) {
				phones.add(user.getAccountCode());
			}
			try {
				if (isAlarm == true) {
					SmsUtil.sendAlarm(phones, electricName, time, "alarm"); // 根据是报警还是解除，确定发送短信的格式
				} else {
					SmsUtil.sendAlarm(phones, electricName, time, "alarm_reset"); // 根据是报警还是解除，确定发送短信的格式
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int updateSceneElectricOrder(String masterCode, int electricIndex, String electricCode, int sceneIndex,
			String electricOrder, String orderInfo) {
		// 修改sceneeletrics表
		int flag = sceneElectricDao.updateSceneElectricOrder(masterCode, electricIndex, sceneIndex, electricOrder);
		if (flag != 1) {
			return -2;
		}
		// 修改时间
		userDao.updateUserSceneELectricTime(masterCode);
		// 修改控制指令表
		SceneOrder sceneOrder = new SceneOrder();
		sceneOrder.setMasterCode(masterCode);
		sceneOrder.setElectricCode(electricCode);
		sceneOrder.setOrderData(electricOrder);
		sceneOrder.setOrderInfo(orderInfo);
		sceneOrder.setSceneIndex(sceneIndex);
		sceneOrder.setIsReaded('N');
		Timestamp timestamp = new Timestamp(new Date().getTime());
		sceneOrder.setWriteTime(SmartHomeUtil.TimestampToString(timestamp));

		MasterWebSocket.sendSceneOrder(masterCode, sceneOrder);
		return sceneOrderDao.insert(sceneOrder);
	}

	// 更新传感器的布防状态，针对门磁电器类型，需要更新json字符串
	@Override
	public int updateSensorExtras(String masterCode, String electricCode, int electricIndex, String extras) {
		// 更新电器时间
		userDao.updateUserElectricTime(masterCode);

		Electric electric = electricDao.select(masterCode, electricIndex);
		if (electric != null) {
			if (electric.getElectricType() != 15) {
				electric.setExtras(extras);
			} else {// 读取json字符串
				String sJson = electric.getExtras();
				Map map;
				if (sJson.equals("")) {
					map = new HashMap();
				} else {
					map = JsonPluginsUtil.jsonToMap(sJson);
				}
				map.put("BuFang", extras);
				String sExtras = JsonPluginsUtil.mapToJson(map);
				electric.setExtras(sExtras);
			}
			// 添加传感器失效的控制指令
			ElectricOrder electricOrder = new ElectricOrder();
			electricOrder.setMasterCode(masterCode);
			electricOrder.setElectricCode(electricCode);
			electricOrder.setOrderInfo("**********");
			electricOrder.setIsReaded('N');
			Timestamp timestamp = new Timestamp(new Date().getTime());
			electricOrder.setWriteTime(SmartHomeUtil.TimestampToString(timestamp));
			if (extras.equals("0")) {
				electricOrder.setOrderData("TG");
			} else if (extras.equals("1")) {
				electricOrder.setOrderData("TS");
			}
			MasterWebSocket.sendElectricOrder(masterCode, electricOrder);
			electricOrderDao.insert(electricOrder);
			// 保存电器
			return electricDao.saveOrUpdate(electric);
		}
		return 0;
	}

	@Override
	public String loadAlarmRecord(String masterCode) {
		List<AlarmRecord> alarmRecords = alarmRecordDao.select(masterCode);
		int size = alarmRecords.size();
		String sReturn = "[";
		for (int i = 0; i < size; i++) {
			List<Electric> electrics = electricDao.select(masterCode, alarmRecords.get(i).getElectricCode());
			String electricName = "", roomName = "";
			int roomIndex = -1, electricType = -1;
			if (electrics.size() > 0) {
				int foot = electrics.size() - 1;
				electricName = electrics.get(foot).getElectricName();
				electricType = electrics.get(foot).getElectricType();
				roomIndex = electrics.get(foot).getRoomIndex();
				UserRoom userRoom = userRoomDao.select(masterCode, roomIndex);
				roomName = userRoom.getRoomName();
			} else {
				continue;
			}
			sReturn = sReturn + "{" + "\"electricCode\"" + ":" + "\"" + alarmRecords.get(i).getElectricCode() + "\""
					+ "," + "\"electricState\"" + ":" + "\"" + alarmRecords.get(i).getElectricState() + "\"" + ","
					+ "\"electricName\"" + ":" + "\"" + electricName + "\"" + "," + "\"electricType\"" + ":"
					+ electricType + "," + "\"roomName\"" + ":" + "\"" + roomName + "\"" + "," + "\"stateInfo\"" + ":"
					+ "\"" + alarmRecords.get(i).getStateInfo() + "\"" + "," + "\"alarmTime\"" + ":" + "\""
					+ alarmRecords.get(i).getAlarmTime() + "\"" + "," + "\"recordSequ\"" + ":"
					+ alarmRecords.get(i).getRecordSequ() + "}";
			if (i != size - 1) {
				sReturn += ",";
			}
		}
		sReturn += "]";
		// System.out.println(sReturn);
		return sReturn;
	}

	@Override
	public String loadDoorRecord(String masterCode, String electricCode) {
		List<Object[]> lists = (List<Object[]>) doorRecordDao.select(electricCode);
		String sReturn = "[";
		int size = lists.size();
		for (int i=0;i<size;i++) {
			sReturn = sReturn +
						"{" +
						"\"recordSequ\"" + ":" + lists.get(i)[0].toString() + "," + 
						"\"byPerson\"" + ":" + "\"" + lists.get(i)[1].toString() + "\"" + ","  + // 该参数有可能是****或者是空
						"\"openTime\"" + ":" + "\"" + lists.get(i)[2].toString() + "\"" + 
						"}";
			if(i!=size-1) {
				sReturn += ",";
			}
		}
		sReturn+="]";
		System.out.println(sReturn);
		return sReturn;
	}

	@Override
	public int updateElectricSequ(String masterCode, int electricIndex, int electricSequ, int roomIndex)
			throws IOException {
		userDao.updateUserElectricTime(masterCode);
		return electricDao.updateElectricSequ(masterCode, electricSequ, roomIndex);
	}

	/**
	 * return -2:插入失败，1：插入成功，2：已添加过该电器
	 */
	@Override
	public int addElectric(String masterCode, int electricIndex, String electricCode, int roomIndex,
			String electricName, int electricSequ, int electricType, String extras, String orderInfo) throws Exception {

		ChildNode childNode = childNodeDao.select(masterCode, electricCode);
		if (childNode == null) {
			childNode = new ChildNode();
			childNode.setMasterCode(masterCode);
			childNode.setElectricCode(electricCode);
			childNode.setElectricState("Z0");
			childNode.setStateInfo("0000000000");
			Timestamp timestamp = new Timestamp(new Date().getTime());
			childNode.setChangeTime(SmartHomeUtil.TimestampToString(timestamp));
			childNodeDao.saveOrUpdate(childNode);
		}
		Electric electric1 = electricDao.select(masterCode, electricIndex);
		if (electric1 == null) {
			Electric electric = new Electric();
			electric.setMasterCode(masterCode);
			electric.setElectricIndex(electricIndex);
			electric.setElectricCode(electricCode);
			electric.setRoomIndex(roomIndex);
			electric.setElectricName(electricName);
			// sequ出现问题，这里需要对sequ做新的处理，保证sequ的值，是当前master_code，room_index唯一确定的electric_sequ中的最大值+1
			int newElectricSequ = electricDao.getMaxElectricSequ(masterCode, roomIndex) + 1;
			if (newElectricSequ != electricSequ) {
				// 保存log文件
				WriteLog.writeLog("error.log",
						"【addElectric错误】" + "masterCode:" + masterCode + "  electricIndex:" + electricIndex
								+ "  electricCode:" + electricCode + "  roomIndex:" + roomIndex + "  electricSequ:"
								+ electricSequ + "  newElectircSequ:" + newElectricSequ);
			}
			electric.setElectricSequ(newElectricSequ);
			electric.setElectricType(electricType);
			electric.setSceneIndex(-1);
			electric.setBelong(0); // 暂时设置为0
			electric.setExtras(extras);
			if (orderInfo == null) {
				electric.setOrderInfo("00");
			} else {
				electric.setOrderInfo(orderInfo);
			}
			// 更新电器时间标识
			userDao.updateUserElectricTime(masterCode);
			int result = electricDao.saveOrUpdate(electric);
			// 分享给该主控下的全部已分享用户
			shareNewAddElectric(masterCode, electricIndex);
			return result;
		}
		return 2;
	}

	@Override
	public int getElectricSequ(String masterCode, int electricIndex) {
		Electric electric = (Electric) electricDao.select(masterCode, electricIndex);
		if (electric != null) {
			return electric.getElectricSequ();
		} else {
			return -1;
		}
	}

	@Override
	public int updateElectricSequ(String masterCode, int electricIndex, int roomIndex, int oldElectricSequ,
			int newElectricSequ) {
		userDao.updateUserElectricTime(masterCode);
		return electricDao.updateElectricSequ(masterCode, electricIndex, roomIndex, oldElectricSequ, newElectricSequ);
	}

	@Override
	public int isExistElectric(String masterCode, String electricCode) {
		List<Electric> electrics = electricDao.select(masterCode, electricCode);
		if (electrics.size() == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public List<ElectricSharedLoacl> loadSharedElectric(String masterCode, String accountCode) {
		return electricSharedDao.select2(masterCode, accountCode);
	}

	@Override
	public int updateSceneName(String masterCode, int sceneIndex, String sceneName, int sceneImg) {
		Scene scene = sceneDao.select(masterCode, sceneIndex);
		if (scene != null) {
			scene.setSceneName(sceneName);
			userDao.updateUserSceneTime(masterCode);
			return sceneDao.saveOrUpdate(scene);
		}
		return -1;
	}

	@Override
	public List<Electric> FixSharedElectricSequ(List<Electric> electrics) {
		Map<Integer, List<Electric>> map = new HashMap<Integer, List<Electric>>();
		for (Electric electric : electrics) {
			Integer roomIndex = electric.getRoomIndex();
			List<Electric> list = map.get(roomIndex);
			if (list == null) {
				list = new ArrayList<Electric>();
			}
			list.add(electric);
			map.put(roomIndex, list);
		}
		List<Electric> returnElectircs = new ArrayList<Electric>();
		for (Integer key : map.keySet()) {
			List<Electric> list = map.get(key);
			Collections.sort(list, new Comparator<Electric>() {
				public int compare(Electric o1, Electric o2) {
					return o1.getElectricSequ().compareTo(o2.getElectricSequ());
				}
			});
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setElectricSequ(i);
				returnElectircs.add(list.get(i));
			}
		}
		return returnElectircs;
	}
	
	@Override
	public int moveElectricToAnotherRoom(String masterCode, int electricIndex, int roomIndex) {
		userDao.updateUserElectricTime(masterCode);
		Electric electric = electricDao.select(masterCode, electricIndex);
		//调整之前房间中的sequ值
		electricDao.updateElectricSequ(masterCode, electric.getElectricSequ(), electric.getRoomIndex());
		//调整目标房间中该电器的sequ值
		int newSequ = electricDao.getMaxElectricSequ(masterCode, roomIndex);
		electric.setRoomIndex(roomIndex);
		electric.setElectricSequ(newSequ+1);
		//调整sceneElectrics表中的roomIndex值
		List<SceneElectric> sceneElectrics = sceneElectricDao.select(masterCode, electricIndex);
		for (SceneElectric sceneElectric : sceneElectrics) {
			sceneElectric.setRoomIndex(roomIndex);
			sceneElectricDao.saveOrUpdate(sceneElectric);
		}
		//更新该电器属性
		return electricDao.saveOrUpdate(electric);
	}
	
	@Override
	public String updateDoorOpenPerson(String electricCode, String accountCode) {
		Timestamp curTime = new Timestamp(new Date().getTime());
		DoorRecord doorRecord = (DoorRecord) doorRecordDao.selectTop(electricCode);
		Timestamp openTime = SmartHomeUtil.StringToTimestamp(doorRecord.getOpenTime());
		System.out.println(curTime);
		System.out.println(openTime);
		long deltaTime = curTime.getTime() - openTime.getTime();
		System.out.println("门锁开锁时间间隔毫秒数：" + deltaTime);
		if (deltaTime < 3000) { // 如果间隔不超过3000毫秒，则认为是当前账号开启的，其实这里可以不进行这个判断
			doorRecord.setByPerson(accountCode);
			doorRecordDao.saveOrUpdate(doorRecord);
		}
		return "1"; // 这里返回值没有太大意义
	}

	@Override
	public String getMasterVersion() {
		return otherDao.getMasterVersion();
	}
	
	@Override
	public String getMasterVersion(String masterCode) {
		MasterNode masterNode = masterNodeDao.select(masterCode);
		return masterNode.getMasterVersion();
	}
	
	@Override
	public int updateMasterVersion(String masterCode, String masterVersion) {
		return masterNodeDao.updateMasterVersion(masterCode, masterVersion);
	}
	
	@Override
	public void lechageAlarm(String electricCode, String type, String time) {
		if (type.equals("1")) {
			Electric electric = electricDao.getLechangeCamera(electricCode);
			String electricName = electric.getElectricName();
			String sTime = SmartHomeUtil.string2Date(time, "yyyy-MM-dd HH:mm:ss");	
			String msg = sTime + " 摄像头：" + electricName + " ： " + electricCode + " 触发动检报警";
			System.out.println(msg);
//			// 发送短信给所有被分享的手机号上去
			String masterCode = electric.getMasterCode();
			List<User> list = userDao.selectByMasterCodeAll(masterCode);
			List<String> phones = new ArrayList<String>();
			for (User user : list) {
				phones.add(user.getAccountCode());
			}
			try {
				System.out.println("需要发送给以下手机：" + phones);
				SmsUtil.sendAlarm(phones, electricName, sTime, "lechange"); // 根据是报警还是解除，确定发送短信的格式
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int updateSceneDetailTiming(String masterCode, int sceneIndex, String detailTiming) {
		Scene scene = sceneDao.select(masterCode, sceneIndex);
		String sHead;
		if (scene.getDetailTiming() != null && scene.getDetailTiming().equals("")) {
			sHead = "<********PH";
		} else {
			sHead = "<********PG";
		}
		String sTime = detailTiming.substring(0, 4) + detailTiming.substring(5, 7) + detailTiming.substring(8, 10) + 
				detailTiming.substring(11, 13) + detailTiming.substring(14, 16) + detailTiming.substring(17, 19);
		String message = sHead + String.valueOf(sceneIndex) + "*F" + sTime + ">";
		if (MasterWebSocket.sendMessage(masterCode, message) == false) {
			System.out.println("scenetiming - error：" + masterCode + " " + message + " 主机没有连入websocket");
			return 0;
		}
		System.out.println("scenetiming：" + masterCode + " " + message);
		scene.setDetailTiming(detailTiming);
		scene.setWeeklyDays("");
		scene.setDaliyTiming("");
		userDao.updateUserSceneTime(masterCode);
		return sceneDao.saveOrUpdate(scene);
	}
	
	@Override
	public int updateSceneDaliyTiming(String masterCode, int sceneIndex, String weeklyDays, String daliyTiming) {
		Scene scene = sceneDao.select(masterCode, sceneIndex);
		String sHead;
		if (scene.getDaliyTiming() != null && scene.getDaliyTiming().equals("")) {
			sHead = "<********PH";
		} else {
			sHead = "<********PG";
		}
//		// 需要在这里将时间信息合成为主机能够识别的指令
		String sTime = daliyTiming.substring(0, 2) + daliyTiming.substring(3, 5) + daliyTiming.substring(6, 8);
//		String[] weekDays = JsonPluginsUtil.jsonToStringArray(weeklyDays);
		String message = sHead + String.valueOf(sceneIndex) + "*W" + weeklyDays + "-" + sTime + ">";
		if (MasterWebSocket.sendMessage(masterCode, message) == false) {
			System.out.println("scenetiming - error：" + masterCode + " " + message + " 主机没有连入websocket");
			return 0;
		}
		System.out.println("scenetiming：" + masterCode + " " + message);
		scene.setDetailTiming("");
		scene.setWeeklyDays(weeklyDays);
		scene.setDaliyTiming(daliyTiming);
		userDao.updateUserSceneTime(masterCode);
		return sceneDao.saveOrUpdate(scene);
	}
	
	@Override
	public int deleteSceneTiming(String masterCode, int sceneIndex) {
		Scene scene = sceneDao.select(masterCode, sceneIndex);
		String message = "<********PR" + String.valueOf(sceneIndex) + "**********>";
		if (MasterWebSocket.sendMessage(masterCode, message) == false) {
			System.out.println("scenetiming - error：" + masterCode + " " + message + " 主机没有连入websocket");
			return 0;
		}
		System.out.println("scenetiming：" + masterCode + " " + message);
		scene.setDetailTiming("");
		scene.setWeeklyDays("");
		scene.setDaliyTiming("");
		userDao.updateUserSceneTime(masterCode);
		return sceneDao.saveOrUpdate(scene);
	}
	@Override
	public int addCentralAir(String masterCode, int electricIndex, String airCode,String airName) {
		Electric electric = electricDao.select(masterCode, electricIndex);
		String sJson = electric.getExtras();
		if (sJson == null || sJson.equals("")) {
			sJson = "[]";
		}
		Map map;
		map = JsonPluginsUtil.jsonToMap(sJson);
		map = new LinkedHashMap();
		map.put(airName,airCode);
		String sExtras = JsonPluginsUtil.mapToJson(map);
		electric.setExtras(sExtras);
		accountDao.updateUserTimeByMasterCode(masterCode);
		userDao.updateUserElectricTime(masterCode);
		return electricDao.saveOrUpdate(electric);
	}
	
	@Override
	public int deleteCentralAir(String masterCode, int electricIndex, String airCode) {
		Electric electric = electricDao.select(masterCode, electricIndex);
		String sJson = electric.getExtras();
		if (sJson == null || sJson.equals("")) {
			return -2;
		}
		String ss[] = JsonPluginsUtil.jsonToStringArray(sJson);
		Set<String> staffsSet = new HashSet<>(Arrays.asList(ss));
		staffsSet.remove(airCode);
		List<String> list = new ArrayList<>(staffsSet);
		Collections.sort(list,new Comparator<String>(){
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });
		String extras = JsonPluginsUtil.listToJson(list);
		electric.setExtras(extras);
		accountDao.updateUserTimeByMasterCode(masterCode);
		userDao.updateUserElectricTime(masterCode);
		return electricDao.saveOrUpdate(electric);
	}

	/**
	 * return -2:插入失败，1：插入成功，2：该用户已注册
	 */
	@Override
	public int addAccount(String accountCode, String password, String accountName) {
		return _addAccount(accountCode, password, accountName, 0);
	}
	
	/**
	 * return -2:插入失败，1：插入成功，2：该用户已注册
	 */
	@Override
	public int addAccountDaFuGui(String accountCode, String password, String accountName) {
		return _addAccount(accountCode, password, accountName, 1);
	}
	
	@Override
	public int _addAccount(String accountCode, String password, String accountName, int flag) {
		Account account1 = accountDao.select(accountCode);
		if (account1 == null) {
			Account account = new Account();
			InputStream inputStream = null;
			try {
				switch (flag) {
				case 0:
					inputStream = new FileInputStream("C:/logo.jpg");
					break;
				case 1:
					inputStream = new FileInputStream("C:/logo_dafugui.jpg");
					break;
				default:
					inputStream = new FileInputStream("C:/logo.jpg");
					break;
				}
				byte[] photo = new byte[inputStream.available()];
				inputStream.read(photo);
				account.setPhoto(photo);
				inputStream.close();
			} catch (FileNotFoundException e) {
				System.out.println("addAccountDaFuGui 图片文件读取失败");
			} catch (Exception e) {
				System.out.println("addAccountDaFuGui 其他的错误");
			}
			account.setAccountCode(accountCode);
			account.setPassword(password);
			account.setAccountName(accountName);
			account.setLePhone(accountCode);
			account.setLeSign(0);
			Timestamp timestamp = new Timestamp(new Date().getTime());
			account.setSignTime(SmartHomeUtil.TimestampToString(timestamp));
			account.setAccountTime(SmartHomeUtil.TimestampToString(timestamp));
			account.setUserTime(SmartHomeUtil.TimestampToString(timestamp));
			return accountDao.saveOrUpdate(account);
		}
		return 2;
	}
	
}
