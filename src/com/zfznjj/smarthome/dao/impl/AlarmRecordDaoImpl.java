package com.zfznjj.smarthome.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.AlarmRecordDao;

public class AlarmRecordDaoImpl implements AlarmRecordDao {
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * ��ȡ�͵�ǰ�̰߳󶨵�Session
	 * @return
	 */
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
}
