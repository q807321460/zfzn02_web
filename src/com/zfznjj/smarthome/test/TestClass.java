package com.zfznjj.smarthome.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.el.ArrayELResolver;
import javax.swing.border.EtchedBorder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.sun.org.apache.bcel.internal.generic.NEW;
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
import com.zfznjj.smarthome.dao.RoomSharedDao;
import com.zfznjj.smarthome.dao.SceneDao;
import com.zfznjj.smarthome.dao.UserDao;
import com.zfznjj.smarthome.dao.UserRoomDao;
import com.zfznjj.smarthome.entity.AdminElectricBean;
import com.zfznjj.smarthome.entity.ElectricForVoice;
import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.model.AlarmRecord;
import com.zfznjj.smarthome.model.CrashLog;
import com.zfznjj.smarthome.model.DoorRecord;
import com.zfznjj.smarthome.model.ETKey;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.Scene;
import com.zfznjj.smarthome.model.User;
import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.service.impl.SmarthomeServiceImpl;
import com.zfznjj.smarthome.util.SmartHomeUtil;
import com.zfznjj.smarthome.util.SmsUtil;
import com.zfznjj.smarthome.util.AppWebSocket;
import com.zfznjj.smarthome.util.JsonPluginsUtil;
import com.zfznjj.smarthome.util.WriteLog;
import com.zfznjj.smarthome.ws.SmarthomeWs;

public class TestClass {

	private ApplicationContext ctx = null;
	private MasterNodeDao masterNodeDao;
	private AccountDao accountDao;
	private UserDao userDao = null;

	private ElectricDao electricDao;
	private AlarmRecordDao alarmRecordDao;
	private DoorRecordDao doorRecordDao;
	private UserRoomDao userRoomDao;
	private ChildNodeDao childNodeDao;
	private ElectricOrderDao electricOrderDao;
	private ElectricSharedDao electricSharedDao;
	private RoomSharedDao roomSharedDao;
	private ETKeyDao eTKeyDao;
	private ETAirDeviceDao eTAirDeviceDao;
	private CrashDao crashDao;
	private SceneDao sceneDao;
	private SmarthomeService smarthomeService;
	private SmarthomeWs smarthomeWs;

	{
		ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		alarmRecordDao = ctx.getBean(AlarmRecordDao.class);
		doorRecordDao = ctx.getBean(DoorRecordDao.class);
		masterNodeDao = ctx.getBean(MasterNodeDao.class);
		accountDao = ctx.getBean(AccountDao.class);
		userDao = ctx.getBean(UserDao.class);
		electricDao = ctx.getBean(ElectricDao.class);
		userRoomDao = ctx.getBean(UserRoomDao.class);
		childNodeDao = ctx.getBean(ChildNodeDao.class);
		electricOrderDao = ctx.getBean(ElectricOrderDao.class);
		electricSharedDao = ctx.getBean(ElectricSharedDao.class);
		roomSharedDao = ctx.getBean(RoomSharedDao.class);
		eTKeyDao = ctx.getBean(ETKeyDao.class);
		eTAirDeviceDao = ctx.getBean(ETAirDeviceDao.class);
		crashDao = ctx.getBean(CrashDao.class);
		sceneDao = ctx.getBean(SceneDao.class);
		smarthomeService = ctx.getBean(SmarthomeService.class);
		smarthomeWs = ctx.getBean(SmarthomeWs.class);
	}
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 获取和当前线程绑定的Session
	 * @return
	 */
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	//用于本地接口测试
	@Test
	public void test() throws Exception {
		//String re = smarthomeWs.loadDoorRecord("AA00FFD9", "1000AAF28715");//("AA00FFD9", "1000AAF28715", "ZF", "28001*******");
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String time = SmartHomeUtil.TimestampToString(timestamp);
		String re = smarthomeWs.updateSceneDetailTiming("AA00FFD9", 0, time);
		System.out.println(re);
	}

	class MyLogHander extends Formatter { 
	    @Override 
	    public String format(LogRecord record) { 
	    	return record.getLevel() + ":" + record.getMessage()+"\n"; 
	    } 
	}
	
	private byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

}

