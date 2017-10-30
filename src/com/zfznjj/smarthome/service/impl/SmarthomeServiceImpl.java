package com.zfznjj.smarthome.service.impl;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.tools.corba.common.idltypes.IdlString;
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
import com.zfznjj.smarthome.util.WebSocket;
import com.zfznjj.smarthome.util.WriteLog;

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
	public String isExistAccount(String accountCode) {
		Account account = accountDao.select(accountCode);
		if (account == null) {
			return "0";
		} else {
			return "1";
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

	

	/**
	 * return -2:����ʧ�ܣ�1������ɹ���2�����û���ע��
	 */
	@Override
	public int addAccount(String accountCode, String password, String accountName) {
		// TODO Auto-generated method stub
		Account account1 = accountDao.select(accountCode);
		if (account1 == null) {
			Account account = new Account();
			InputStream inputStream;
			try {
				inputStream = new FileInputStream("C:/logo.jpg");
				byte[] photo = new byte[inputStream.available()];
				inputStream.read(photo);
				account.setPhoto(photo);
				inputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("addAccount ͼƬ�ļ���ȡʧ��");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("addAccount �����Ĵ���");
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

	@Override
	public List<String> getAccountCodesByMaster(String masterCode) {
		// TODO Auto-generated method stub
		return userDao.seleteAccountCodesByMaster(masterCode);
	}

	/**
	 * return -2:����ʧ�ܣ�1������ɹ���2�������ӹ��÷���
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
			scene.setSceneSequ(sceneSequ);
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

		// ���龰ָ����У�����һ���µ����ݣ�����֮ǰ�Ƿ񷢳���
		addSceneOrder(masterCode, electricCode, "SH", electric.getOrderInfo(), sceneIndex);

		accountDao.updateUserTimeByMasterCode(masterCode);
		userDao.updateUserELectricTime(masterCode);
		return electricDao.saveOrUpdate(electric);
	}

	@Override
	public int updateElectric1(String masterCode, String electricCode, int electricIndex, String electricName,
			int sceneIndex, String electricOrder) {
		// �޸�electrics���е�����
		Electric electric = electricDao.select(masterCode, electricIndex);// �о�������Ҫ�޸�һ�£�����һ���ֶ�extras����ֹ�ظ�����
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
		userDao.updateUserELectricTime(masterCode);
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

	/**
	 * ɾ������ʱ�� 1������user��electric_time 2��ɾ���龰ģʽ�������еĵ��� 3��ɾ�������������еĵ���
	 */
	@Override
	public int deleteElectric(String masterCode, int electricIndex) {
		// ���µ���ʱ��
		userDao.updateUserELectricTime(masterCode);
		List<SceneElectric> list = sceneElectricDao.select(masterCode, electricIndex);
		if (list != null && list.size() > 0) {
			// �����龰����ʱ��
			userDao.updateUserSceneELectricTime(masterCode);
			int count = list.size();
			// ��SceneOrder��������ɾ��������ָ��
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
				sceneOrderDao.insert(sceneOrder);
			}
		}
		// ɾ���龰����
		sceneElectricDao.delete(masterCode, electricIndex);
		// ɾ�������������еĵ���
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

	public int deleteElectric(String masterCode, String electricCode, int electricIndex) {
		// ���µ���ʱ��
		userDao.updateUserELectricTime(masterCode);
		List<SceneElectric> list = sceneElectricDao.select(masterCode, electricIndex);
		if (list != null && list.size() > 0) {
			// �����龰����ʱ��
			userDao.updateUserSceneELectricTime(masterCode);
			int count = list.size();
			// ��SceneOrder��������ɾ��������ָ��
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
				sceneOrderDao.insert(sceneOrder);
			}
		}
		// ɾ���龰����
		sceneElectricDao.delete(masterCode, electricIndex);
		// ɾ�������������еĵ���
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
			// ��SceneOrder��������ɾ��������ָ��
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return accountDao.selectAccountByMasterCode(masterCode);
	}

	@Override
	public Account loadAccountFromWs(String accountCode, String accountTime) {
		// TODO Auto-generated method stub
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
	 * 1���������ڸ����ڵ㣬�����ֱ�����ӣ���Ĭ�ϸ����ڵ��ӵ�����Ǹ��˻� 2�������ڸ����ڵ㣺 a �����ڵ�û��ӵ���ߣ������ֱ������ b
	 * �����ڵ���ӵ���ߣ��������ӣ���ͨ�������ڵ��ӵ���߷��� return 1�� ���ӳɹ� -2������ʧ�� 0��2-b���������������
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
			addNewUserScene(masterCode); // ��һ�����ڵ��ǣ�ֱ�����������龰ģʽ
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
	 * ���ӷ����˻� return -1�������ڱ��������˻� 0���ѷ��� 1�������ɹ� -2������ʧ��
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
		 * ��������Ȩ�ޣ��������������ȡ
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
		/*
		 * ���û�����ʱ�䷢���ı䣺 �Ƿ����˻��� 1���ǣ��������������ȡ 2�������ڵ��Ƿ������˻��� �У��ӷ����������ȡ �ޣ�������������ȡ
		 * 
		 */
		MasterNode masterNode = masterNodeDao.select(masterCode);
		if (masterNode == null) {
			return null;
		}
		List<Electric> electrics;
		if (user.getIsAdmin() == 1 || masterNode.getOwner() == null || masterNode.getOwner().equals("")) {
			electrics = electricDao.select(masterCode);
		} else {
			electrics = electricSharedDao.select(accountCode, masterCode);
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
		// TODO Auto-generated method stub
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
		userDao.updateUserELectricTime(masterCode);
		return eTKeyDao.updateIRKeyValue(masterCode, electricIndex, keyKey, keyValue);
	}

	/**
	 * ���µ���ָ��
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
		 * ��������Ȩ�ޣ��������������ȡ
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

	// ����Ϊkonnn���ӻ��޸��Ľӿ�
	
	//���µ���״̬�����������ص�ǰ����״̬ʱ���������������Ҫ�Ը�
	@Override
	public int updateElectricState(String masterCode, String electricCode, String electricState, String stateInfo) throws IOException {
			//����websocket���͵�ǰ����״̬�ַ�����ָ����socket�ͻ���
			String message = "<" + electricCode + electricState + stateInfo + "00>"; 
			WebSocket.sendMessage(masterCode, message);
			//����������¼����������Ҳ��return����Ϊ��Ҫ�ó����������ȥ
			saveDoorRecord(masterCode, electricCode, stateInfo);
			//���汨����¼����������Ҳ��return����Ϊ��Ҫ�ó����������ȥ
			saveAlarmRecord(masterCode, electricCode, electricState, stateInfo);
			//����Ƿ���Ҫ���Ͷ���
//			checkIfSendSms(masterCode, electricCode, electricState, stateInfo);
			// ��������������͵�������Ҫ����¼���浽���ݿ���
			return childNodeDao.updateChildNodeState(masterCode, electricCode, electricState, stateInfo);
		}
	
	@Override
	public int saveAlarmRecord(String masterCode, String electricCode, String electricState, String stateInfo) {
		if (electricCode.startsWith("0D")) {
			String sFlag = stateInfo.substring(0, 2);
			if (sFlag.equals("01") || sFlag.equals("03") || sFlag.equals("05") || sFlag.equals("07")) {
				AlarmRecord alarmRecord = new AlarmRecord();
				alarmRecord.setMasterCode(masterCode);
				alarmRecord.setElectricCode(electricCode);
				alarmRecord.setElectricState(electricState);
				alarmRecord.setStateInfo(stateInfo);
				Timestamp timestamp = new Timestamp(new Date().getTime());
				alarmRecord.setAlarmTime(SmartHomeUtil.TimestampToString(timestamp));
				int newSequ = alarmRecordDao.getMaxRecordSequ(electricCode) + 1;
				int maxSequ = 30;//��ǰ����Ϊ30
				if (newSequ==maxSequ) {
					alarmRecordDao.delete(electricCode, 0);
					alarmRecordDao.updateAlarmRecordSequ(electricCode);
					alarmRecord.setRecordSequ(newSequ-1);
				}else {
					alarmRecord.setRecordSequ(newSequ);
				}
				return alarmRecordDao.saveOrUpdate(alarmRecord);// �����¼
			}
			return -2;
		}
		return -2;
	}
	
	//����������¼
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
				int newSequ = doorRecordDao.getMaxRecordSequ(electricCode) + 1;
				int maxSequ = 300;//��ǰ����Ϊ300
				if (newSequ==maxSequ) {//
					doorRecordDao.delete(electricCode, 0);
					doorRecordDao.updateDoorRecordSequ(electricCode);
					doorRecord.setRecordSequ(newSequ-1);
				}else {
					doorRecord.setRecordSequ(newSequ);
				}
				return doorRecordDao.saveOrUpdate(doorRecord);// �����¼
			}
			return -2;
		}
		return -2;
	}
	
	// ���Ͷ��ű���֪ͨ
	private void checkIfSendSms(String masterCode, String electricCode, String electricState, String stateInfo) {
//		if (electricCode.startsWith("0D") && stateInfo.startsWith("01")) {
		if (electricCode.startsWith("0D")) {
			List<Electric> electrics = electricDao.select(masterCode, electricCode);
			String electricName = "";
			if (electrics.size() > 0) {
				electricName = electrics.get(0).getElectricName();
			}
			// <0D215239ZFAB0000******>
			// A=0��B=0~7
			// 00 û��
			// 01 ����
			// 02 ����
			// 03 ����+����
			// 04 ������
			// 05 ����+������
			// 06 ����+������
			// 07 ����+����+������
			// 00~07��Ϊ�ַ���
			ChildNode childNode = childNodeDao.select(masterCode, electricCode);
			String oldStateInfo = childNode.getStateInfo();
			if (stateInfo.equals(oldStateInfo) == true) {// ״̬���䣬���˳�
				return;
			}
			boolean isOldAlarm;// ��״̬�Ƿ��ھ���״̬
			boolean isAlarm;// ��ǰ��״̬�Ƿ��ھ���״̬
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
			if (isOldAlarm == isAlarm) {// ������״̬û�иı�Ļ����ǲ���Ҫ���Ͷ��ŵ�
				return;
			}

			Timestamp timestamp = new Timestamp(new Date().getTime());
			String time = SmartHomeUtil.TimestampToString(timestamp);
			String msg = time + " ��������" + electricName + " �� " + electricCode + "״̬�л�Ϊ��" + stateInfo;
			System.out.println(msg);

			List<User> list = userDao.selectByMasterCodeAll(masterCode);
			List<String> phones = new ArrayList<String>();

			for (User user : list) {
				phones.add(user.getAccountCode());
			}
			try {
				SmsUtil.sendAlarm(phones, electricName, time, isAlarm);
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
		return sceneOrderDao.insert(sceneOrder);
	}

	//���´������Ĳ���״̬������Ŵŵ������ͣ���Ҫ����json�ַ���
	@Override
	public int updateSensorExtras(String masterCode, String electricCode, int electricIndex, String extras) {
		// ���µ���ʱ��
		userDao.updateUserELectricTime(masterCode);

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
			// ���Ӵ�����ʧЧ�Ŀ���ָ��
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
			electricOrderDao.insert(electricOrder);
			// �������
			return electricDao.saveOrUpdate(electric);
		}
		return 0;
	}
	
	@Override
	public List<AlarmRecord> loadAlarmRecord(String masterCode) {
		List<AlarmRecord> alarmRecords;
		alarmRecords = alarmRecordDao.select(masterCode);
		return alarmRecords;
	}
	
	@Override
	public String loadDoorRecord(String masterCode, String electricCode) {
		return doorRecordDao.select2(electricCode);
	}
	
	@Override
	public int updateElectricSequ(String masterCode, int electricIndex, int electricSequ, int roomIndex) throws IOException {
		userDao.updateUserELectricTime(masterCode);
		return electricDao.updateElectricSequ(masterCode, electricSequ, roomIndex);
	}
	
	/**
	 * return -2:����ʧ�ܣ�1������ɹ���2�������ӹ��õ���
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
			// sequ�������⣬������Ҫ��sequ���µĴ�������֤sequ��ֵ���ǵ�ǰmaster_code��room_indexΨһȷ����electric_sequ�е����ֵ+1
			int newElectricSequ = electricDao.getMaxElectricSequ(masterCode, roomIndex) + 1;
			if (newElectricSequ != electricSequ) {
				// ����log�ļ�
				WriteLog.writeLog("error.log",
						"��addElectric����" + "masterCode:" + masterCode + "  electricIndex:" + electricIndex + "  electricCode:"
								+ electricCode + "  roomIndex:" + roomIndex + "  electricSequ:" + electricSequ
								+ "  newElectircSequ:" + newElectricSequ);
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
			userDao.updateUserELectricTime(masterCode);
			int result = electricDao.saveOrUpdate(electric);
			// �������������µ�ȫ���ѷ����û�
			shareNewAddElectric(masterCode, electricIndex);
			return result;
		}
		return 2;
	}
	
	@Override
	public int getElectricSequ(String masterCode, int electricIndex) {
		Electric electric = (Electric)electricDao.select(masterCode, electricIndex);
		if (electric!=null) {
			return electric.getElectricSequ();
		}else {
			return -1;
		}
	}
}