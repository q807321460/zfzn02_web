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

	// �����Ǹ�ҵ���߼������������DAO���
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
	 * ��ȡ�͵�ǰ�̰߳󶨵�Session
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
	 * return: 0:���벻��ȷ�� 1����¼�ɹ��� 2�������ڸ��û�
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
	 * return -2:����ʧ�ܣ�1������ɹ���2������ӹ��÷���
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
							"��addScene����" + "masterCode:" + masterCode + "  sceneIndex:" + sceneIndex + "  sceneSequ:"
									+ sceneSequ + "  newSceneSequ:" + newSceneSequ);
				} catch (IOException e) {
					System.out.println("WriteLog addScene error");
				}
			}
			scene.setSceneSequ(newSceneSequ);
//			scene.setSceneSequ(sceneSequ); // TODO��������ܳ��������sequֵ����ȷ������ƻ���龰����ʾ������
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
		// �޸�electrics���е�����
		Electric electric = electricDao.select(masterCode, electricIndex);
		electric.setElectricName(electricName);
		electric.setSceneIndex(sceneIndex);

		// ͬʱ�޸�sceneElectrics���еĵ�����
		updateSceneElectrics(masterCode, electricIndex, electricName);

		// ���龰ָ����У����һ���µ����ݣ�����֮ǰ�Ƿ񷢳���
		addSceneOrder(masterCode, electricCode, "SH", electric.getOrderInfo(), sceneIndex);

		accountDao.updateUserTimeByMasterCode(masterCode);
		userDao.updateUserElectricTime(masterCode);
		return electricDao.saveOrUpdate(electric);
	}

	@Override
	public int updateElectric1(String masterCode, String electricCode, int electricIndex, String electricName,
			int sceneIndex, String electricOrder) {
		// �޸�electrics���е�����
		Electric electric = electricDao.select(masterCode, electricIndex);// �о�������Ҫ�޸�һ�£����һ���ֶ�extras����ֹ�ظ�����
		electric.setElectricName(electricName);
		electric.setSceneIndex(sceneIndex);
		// �����������ǹش������������ʹ��json�ַ����ĸ�ʽ�洢
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
		// ͬʱ�޸�sceneElectrics���еĵ�����
		updateSceneElectrics(masterCode, electricIndex, electricName);
		// electricOrderΪSH��˵��������ʱ�����龰ģʽ��ΪSG˵��������ʱ�����龰ģʽ
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
	 * return -2:����ʧ�ܣ�0�����û������� 1�����³ɹ���
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

	// �������á�
	@Override
	public int deleteElectric(String masterCode, int electricIndex) {
		// ���µ���ʱ��
		userDao.updateUserElectricTime(masterCode);
		List<SceneElectric> list = sceneElectricDao.select(masterCode, electricIndex);
		if (list != null && list.size() > 0) {
			// �����龰����ʱ��
			userDao.updateUserSceneELectricTime(masterCode);
			int count = list.size();
			// ��SceneOrder�������ɾ��������ָ��
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
		// ɾ���龰����
		sceneElectricDao.delete(masterCode, electricIndex);
		// ɾ������������еĵ���
		electricSharedDao.deleteByElectric(masterCode, electricIndex);

		// ����Ǻ��������������Ҫ��ָ�����ɾ������
		Electric electric = electricDao.select(masterCode, electricIndex);
		if (electric != null && electric.getElectricCode().startsWith("09")) {
			if (electric.getElectricType() == 9) { // ���ڿյ���ɾ���յ��������е�����
				eTAirDeviceDao.delete(masterCode, electricIndex);
			}
			// ɾ�����������ָ����е�����
			eTKeyDao.delete(masterCode, electricIndex);
		}
		return electricDao.delete(masterCode, electricIndex);
	}

	// ɾ������ʱ�� 1������user��electric_time 2��ɾ���龰ģʽ�������еĵ��� 3��ɾ������������еĵ���
	public int deleteElectric(String masterCode, String electricCode, int electricIndex) {
		// ���µ���ʱ��
		userDao.updateUserElectricTime(masterCode);
		List<SceneElectric> list = sceneElectricDao.select(masterCode, electricIndex);
		if (list != null && list.size() > 0) {
			// �����龰����ʱ��
			userDao.updateUserSceneELectricTime(masterCode);
			int count = list.size();
			// ��SceneOrder�������ɾ��������ָ��
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
		// ɾ���龰����
		sceneElectricDao.delete(masterCode, electricIndex);
		// ɾ������������еĵ���
		electricSharedDao.deleteByElectric(masterCode, electricIndex);

		// �ж��Ƿ���Ҫɾ��childnode������
		List<Electric> electrics = electricDao.select(masterCode, electricCode);
		if (!electrics.isEmpty() && electrics.size() == 1) {
			childNodeDao.delete(masterCode, electricCode);
		}

		// ����Ǻ��������������Ҫ��ָ�����ɾ������
		Electric electric = electricDao.select(masterCode, electricIndex);
		if (electric != null && electric.getElectricCode().startsWith("09")) {
			if (electric.getElectricType() == 9) { // ���ڿյ���ɾ���յ��������е�����
				eTAirDeviceDao.delete(masterCode, electricIndex);
			}
			// ɾ�����������ָ����е�����
			eTKeyDao.delete(masterCode, electricIndex);
		}
		// ����Ǵ����������ڱ����б���ɾ����Ӧ�ĵ������Ӻ���ǰѭ������sequֵ����֤����ȷ��
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
		// ��sceneOrder����д����
		SceneOrder sceneOrder = new SceneOrder();
		sceneOrder.setMasterCode(masterCode);
		sceneOrder.setElectricCode("********");
		sceneOrder.setOrderData("TR");
		sceneOrder.setOrderInfo("**");
		sceneOrder.setSceneIndex(sceneIndex);
		sceneOrder.setIsReaded('N');

		MasterWebSocket.sendSceneOrder(masterCode, sceneOrder);
		sceneOrderDao.insert(sceneOrder);

		// ɾ�����龰ģʽ�е����е���
		sceneElectricDao.deleteBySceneIndex(masterCode, sceneIndex);

		// �������龰�йص�ʱ�䣺�龰ʱ�䡢�龰����ʱ��
		userDao.updateUserSceneTime(masterCode);
		userDao.updateUserSceneELectricTime(masterCode);

		return sceneDao.delete(masterCode, sceneIndex);
	}

	@Override
	public int deleteSceneElectric(String masterCode, int electricIndex, int sceneIndex) {
		userDao.updateUserSceneELectricTime(masterCode);
		List<SceneElectric> list = sceneElectricDao.select(masterCode, electricIndex, sceneIndex);
		if (list != null && list.size() > 0) {
			// �����龰����ʱ��
			userDao.updateUserSceneELectricTime(masterCode);
			int count = list.size();
			// ��SceneOrder�������ɾ��������ָ��
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
	 * �д��Ż�
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
	 * 1���������ڸ����ڵ㣬�����ֱ����ӣ���Ĭ�ϸ����ڵ��ӵ�����Ǹ��˻� 2�������ڸ����ڵ㣺 a �����ڵ�û��ӵ���ߣ������ֱ����� b
	 * �����ڵ���ӵ���ߣ�������ӣ���ͨ�������ڵ��ӵ���߷��� return 1�� ��ӳɹ� -2�����ʧ�� 0��2-b��������������
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
			addNewUserScene(masterCode); // ��һ�����ڵ��ǣ�ֱ����������龰ģʽ
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
	 * ��ӷ����˻� return -1�������ڱ�������˻� 0���ѷ��� 1������ɹ� -2������ʧ��
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
		/* ���û���ȡ�û�ʧ�ܻ����û�����ʱ��û�����仯�� �򲻶�ȡ */
		if (user == null || (areaTime != null && user.getAreaTime().equals(areaTime))) {
			return null;
		}
		/*
		 * ��������Ȩ�ޣ�������������ȡ
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
		/* ���û���ȡ�û�ʧ�ܻ����û�����ʱ��û�����仯�� �򲻶�ȡ */
		if (user == null || (electricTime != null && user.getElectricTime().equals(electricTime))) {
			return null;
		}
		// ���û�����ʱ�䷢���ı䣺 �Ƿ����˻���
		// 1���ǣ�������������ȡ
		// 2�������ڵ��Ƿ������˻���
		// �У��ӷ���������ȡ
		// �ޣ������������ȡ
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

	// ���龰ָ����м�飬��ֹ����nullֵ
	private List<SceneOrder> checkSceneOrderList(List<SceneOrder> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getElectricCode() == null) {
				list.remove(i);
				i--;
			}
		}
		return list;
	}

	// �Ե���ָ����м�飬����2��ָ����жϵڶ����Ժ��ָ���Ƿ��Ǻ���ָ�����ȥ��������ȥ������֤����ָ��һ��ֻ�ܷ���һ��
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
		System.out.println("��" + phoneNum + "������Ϣ��" + msg);
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
		// ���¸��������û��ĵ���ʱ���ʶ
		userDao.updateUserElectricTime(masterCode);
		return eTKeyDao.updateIRKeyValue(masterCode, electricIndex, keyKey, keyValue);
	}

	/**
	 * ���µ���ָ��
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
		/* ���û���ȡ�û�ʧ�ܻ����û��龰ʱ��û�����仯�� �򲻶�ȡ */
		if (user == null || (sceneTime != null && user.getSceneTime().equals(sceneTime))) {
			return null;
		}
		/*
		 * ��������Ȩ�ޣ�������������ȡ
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
		/* ���û���ȡ�û�ʧ�ܻ����û�����ʱ��û�����仯�� �򲻶�ȡ */
		if (user == null || (sceneElectricTime != null && user.getSceneELectricTime().equals(sceneElectricTime))) {
			return null;
		}
		/*
		 * ���û�����ʱ�䷢���ı䣺 �Ƿ����˻���
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
	 * ��������ԱȨ�� return -2:����ʧ�� 1�������ɹ��� 0������ִ�иò���
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
	 * ��ȡ����ԱȨ�� return -2:����ʧ�� 1�������ɹ� 0������ִ�иò���
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
	 * �ж�Ҫɾ�����û��Ƿ��Ǹ������Ĺ���Ա �ǣ������صĹ���Ա��Ϊnull ��ɾ���û�user
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
		scene0.setSceneName("�ؼ�");
		scene0.setSceneIndex(0);
		scene0.setSceneSequ(0);
		scene0.setSceneImg(0);
		scene0.setBuildTime(SmartHomeUtil.TimestampToString(timestamp));
		sceneDao.saveOrUpdate(scene0);

		scene1.setMasterCode(masterCode);
		scene1.setSceneName("���");
		scene1.setSceneIndex(1);
		scene1.setSceneSequ(1);
		scene1.setSceneImg(1);
		scene1.setBuildTime(SmartHomeUtil.TimestampToString(timestamp));
		sceneDao.saveOrUpdate(scene1);

		scene2.setMasterCode(masterCode);
		scene2.setSceneName("��");
		scene2.setSceneIndex(2);
		scene2.setSceneSequ(2);
		scene2.setSceneImg(2);
		scene2.setBuildTime(SmartHomeUtil.TimestampToString(timestamp));
		sceneDao.saveOrUpdate(scene2);

		scene3.setMasterCode(masterCode);
		scene3.setSceneName("˯��");
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
		System.out.println("�����룺" + irCode);
		return irCode;
	}

	// ����Ϊkonnn��ӻ��޸��Ľӿ�

	// ���µ���״̬�����������ص�ǰ����״̬ʱ���������������ɸ��ֺ��Ĺ���
	@Override
	public int updateElectricState(String masterCode, String electricCode, String electricState, String stateInfo)
			throws IOException {
		if (electricCode.startsWith("1000")) {
			if (electricState.equals("Z#")) // ˵�����������Ŀ����룬��Ӧ�ñ��������
				return 1;
			// ˵��������������Ҫȷ���Ƿ񱣴濪����¼
			saveDoorRecord(masterCode, electricCode, stateInfo);
		} else if (electricCode.startsWith("0D")) {
			// ˵���Ǵ���������Ҫȷ���Ƿ��Ͷ��Ų����汨����¼
			sensorAlarm(masterCode, electricCode, electricState, stateInfo);
		}
		// ����websocket���͵�ǰ����״̬�ַ�����ָ����socket�ͻ��ˣ�������״̬���͸��ͻ���
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
		int maxSequ = 30;// ÿ���������ļ�¼����Ϊ30����Ӧ�ù����˰ɣ�
		if (newSequ == maxSequ) {
			alarmRecordDao.delete(masterCode, 0);
			alarmRecordDao.updateAlarmRecordSequ(masterCode);
			alarmRecord.setRecordSequ(newSequ - 1);
		} else {
			alarmRecord.setRecordSequ(newSequ);
		}
		return alarmRecordDao.saveOrUpdate(alarmRecord);// �����¼
	}

	// ����������¼
	@Override
	public int saveDoorRecord(String masterCode, String electricCode, String stateInfo) {
		String pre = electricCode.substring(0, 4);
		if (pre.equals("1000")) {
			String sFlag = stateInfo.substring(0, 1);
			if (sFlag.equals("2") || sFlag.equals("4")) {// ˵���ǿ���
				DoorRecord doorRecord = new DoorRecord();
				doorRecord.setMasterCode(masterCode);
				doorRecord.setElectricCode(electricCode);
				Timestamp timestamp = new Timestamp(new Date().getTime());
				doorRecord.setOpenTime(SmartHomeUtil.TimestampToString(timestamp));
				
				//�������app�����Ļ������¼�����ϵĿ�����
				doorRecord.setByPerson(stateInfo.substring(1, 5));
				
				int newSequ = doorRecordDao.getMaxRecordSequ(electricCode) + 1;
				int maxSequ = 300; // ��ǰ����Ϊ300
				if (newSequ == maxSequ) { //
					doorRecordDao.delete(electricCode, 0);
					doorRecordDao.updateDoorRecordSequ(electricCode);
					doorRecord.setRecordSequ(newSequ - 1);
				} else {
					doorRecord.setRecordSequ(newSequ);
				}
				return doorRecordDao.saveOrUpdate(doorRecord);// �����¼
			}
			return -2;
		}
		return -2;
	}

	// ��������������Ҫ�������ж��Ƿ񱨾��ͱ��汨����¼
	private void sensorAlarm(String masterCode, String electricCode, String electricState, String stateInfo) {
		
		// �������ı�����Ϣ������ʽ����<0D31001280FEAB0000******> A=0��B=0~7
		// 00 û�� 01 ���� 02 ���� 03 ����+���� 04 ������ 05 ����+������ 06 ����+������ 07 ����+����+������
		
		if (electricCode.startsWith("0D")) {
			// ��ʷ�������⣬������Ӧֻselect��һ������������֮ǰ������ͬһ�����£����ڶ����ͬ��ŵĴ�����
			List<Electric> electrics = electricDao.select(masterCode, electricCode);
			Electric electric = electrics.get(0);
			if (electric.getExtras().equals("0")) { // ˵���ô��������ڳ���״̬��ֱ���˳�
				return;
			}
			if (electric.getElectricCode().startsWith("0D31")) { // �����ŴŴ�������Ҫ��extras����json�ַ�������
				String sJson = electric.getExtras();
				if (sJson.equals("") == false) {
					Map map = JsonPluginsUtil.jsonToMap(sJson);
					if (map.containsKey("BuFang") && map.get("BuFang").equals("0")) {
						return;
					}
				}
			}
			
			boolean isOldAlarm; // ��״̬�Ƿ��ھ���״̬
			boolean isAlarm; // ��ǰ��״̬�Ƿ��ھ���״̬
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
			if (isOldAlarm == isAlarm) { // ������״̬û�иı�Ļ����ǲ���Ҫ���Ͷ��ŵ�
				return;
			}
			// ���浽�����б���ȥ
			if (isAlarm == true) {
				saveAlarmRecord(masterCode, electricCode, electricState, stateInfo);
			}
			Timestamp timestamp = new Timestamp(new Date().getTime());
			String time = SmartHomeUtil.TimestampToString(timestamp);
			String electricName = electric.getElectricName();
			String msg = time + " ��������" + electricName + " �� " + electricCode + " ״̬�л�Ϊ��" + stateInfo;
			System.out.println(msg);
			
			// ���Ͷ��Ÿ����б�������ֻ�����ȥ
			List<User> list = userDao.selectByMasterCodeAll(masterCode);
			List<String> phones = new ArrayList<String>();
			for (User user : list) {
				phones.add(user.getAccountCode());
			}
			try {
				if (isAlarm == true) {
					SmsUtil.sendAlarm(phones, electricName, time, "alarm"); // �����Ǳ������ǽ����ȷ�����Ͷ��ŵĸ�ʽ
				} else {
					SmsUtil.sendAlarm(phones, electricName, time, "alarm_reset"); // �����Ǳ������ǽ����ȷ�����Ͷ��ŵĸ�ʽ
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int updateSceneElectricOrder(String masterCode, int electricIndex, String electricCode, int sceneIndex,
			String electricOrder, String orderInfo) {
		// �޸�sceneeletrics��
		int flag = sceneElectricDao.updateSceneElectricOrder(masterCode, electricIndex, sceneIndex, electricOrder);
		if (flag != 1) {
			return -2;
		}
		// �޸�ʱ��
		userDao.updateUserSceneELectricTime(masterCode);
		// �޸Ŀ���ָ���
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

	// ���´������Ĳ���״̬������Ŵŵ������ͣ���Ҫ����json�ַ���
	@Override
	public int updateSensorExtras(String masterCode, String electricCode, int electricIndex, String extras) {
		// ���µ���ʱ��
		userDao.updateUserElectricTime(masterCode);

		Electric electric = electricDao.select(masterCode, electricIndex);
		if (electric != null) {
			if (electric.getElectricType() != 15) {
				electric.setExtras(extras);
			} else {// ��ȡjson�ַ���
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
			// ��Ӵ�����ʧЧ�Ŀ���ָ��
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
			// �������
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
						"\"byPerson\"" + ":" + "\"" + lists.get(i)[1].toString() + "\"" + ","  + // �ò����п�����****�����ǿ�
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
	 * return -2:����ʧ�ܣ�1������ɹ���2������ӹ��õ���
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
			// sequ�������⣬������Ҫ��sequ���µĴ�����֤sequ��ֵ���ǵ�ǰmaster_code��room_indexΨһȷ����electric_sequ�е����ֵ+1
			int newElectricSequ = electricDao.getMaxElectricSequ(masterCode, roomIndex) + 1;
			if (newElectricSequ != electricSequ) {
				// ����log�ļ�
				WriteLog.writeLog("error.log",
						"��addElectric����" + "masterCode:" + masterCode + "  electricIndex:" + electricIndex
								+ "  electricCode:" + electricCode + "  roomIndex:" + roomIndex + "  electricSequ:"
								+ electricSequ + "  newElectircSequ:" + newElectricSequ);
			}
			electric.setElectricSequ(newElectricSequ);
			electric.setElectricType(electricType);
			electric.setSceneIndex(-1);
			electric.setBelong(0); // ��ʱ����Ϊ0
			electric.setExtras(extras);
			if (orderInfo == null) {
				electric.setOrderInfo("00");
			} else {
				electric.setOrderInfo(orderInfo);
			}
			// ���µ���ʱ���ʶ
			userDao.updateUserElectricTime(masterCode);
			int result = electricDao.saveOrUpdate(electric);
			// ������������µ�ȫ���ѷ����û�
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
		//����֮ǰ�����е�sequֵ
		electricDao.updateElectricSequ(masterCode, electric.getElectricSequ(), electric.getRoomIndex());
		//����Ŀ�귿���иõ�����sequֵ
		int newSequ = electricDao.getMaxElectricSequ(masterCode, roomIndex);
		electric.setRoomIndex(roomIndex);
		electric.setElectricSequ(newSequ+1);
		//����sceneElectrics���е�roomIndexֵ
		List<SceneElectric> sceneElectrics = sceneElectricDao.select(masterCode, electricIndex);
		for (SceneElectric sceneElectric : sceneElectrics) {
			sceneElectric.setRoomIndex(roomIndex);
			sceneElectricDao.saveOrUpdate(sceneElectric);
		}
		//���¸õ�������
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
		System.out.println("��������ʱ������������" + deltaTime);
		if (deltaTime < 3000) { // ������������3000���룬����Ϊ�ǵ�ǰ�˺ſ����ģ���ʵ������Բ���������ж�
			doorRecord.setByPerson(accountCode);
			doorRecordDao.saveOrUpdate(doorRecord);
		}
		return "1"; // ���ﷵ��ֵû��̫������
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
			String msg = sTime + " ����ͷ��" + electricName + " �� " + electricCode + " �������챨��";
			System.out.println(msg);
//			// ���Ͷ��Ÿ����б�������ֻ�����ȥ
			String masterCode = electric.getMasterCode();
			List<User> list = userDao.selectByMasterCodeAll(masterCode);
			List<String> phones = new ArrayList<String>();
			for (User user : list) {
				phones.add(user.getAccountCode());
			}
			try {
				System.out.println("��Ҫ���͸������ֻ���" + phones);
				SmsUtil.sendAlarm(phones, electricName, sTime, "lechange"); // �����Ǳ������ǽ����ȷ�����Ͷ��ŵĸ�ʽ
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
			System.out.println("scenetiming - error��" + masterCode + " " + message + " ����û������websocket");
			return 0;
		}
		System.out.println("scenetiming��" + masterCode + " " + message);
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
//		// ��Ҫ�����ｫʱ����Ϣ�ϳ�Ϊ�����ܹ�ʶ���ָ��
		String sTime = daliyTiming.substring(0, 2) + daliyTiming.substring(3, 5) + daliyTiming.substring(6, 8);
//		String[] weekDays = JsonPluginsUtil.jsonToStringArray(weeklyDays);
		String message = sHead + String.valueOf(sceneIndex) + "*W" + weeklyDays + "-" + sTime + ">";
		if (MasterWebSocket.sendMessage(masterCode, message) == false) {
			System.out.println("scenetiming - error��" + masterCode + " " + message + " ����û������websocket");
			return 0;
		}
		System.out.println("scenetiming��" + masterCode + " " + message);
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
			System.out.println("scenetiming - error��" + masterCode + " " + message + " ����û������websocket");
			return 0;
		}
		System.out.println("scenetiming��" + masterCode + " " + message);
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
	 * return -2:����ʧ�ܣ�1������ɹ���2�����û���ע��
	 */
	@Override
	public int addAccount(String accountCode, String password, String accountName) {
		return _addAccount(accountCode, password, accountName, 0);
	}
	
	/**
	 * return -2:����ʧ�ܣ�1������ɹ���2�����û���ע��
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
				System.out.println("addAccountDaFuGui ͼƬ�ļ���ȡʧ��");
			} catch (Exception e) {
				System.out.println("addAccountDaFuGui �����Ĵ���");
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
