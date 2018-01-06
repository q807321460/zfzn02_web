package com.zfznjj.smarthome.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.OtherDao;

public class OtherDaoImpl implements OtherDao{

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
	
	
	@Override
	public String getAppVersion() {
		String sql = "SElECT mvalue FROM i_param WHERE mkey = :appVersion";
		String appVersion= (String)getSession().createSQLQuery(sql).setString("appVersion", "appVersion").uniqueResult();
		return appVersion;
	}
	
	@Override
	public String getAppVersionOs(){
		String sql = "SElECT mvalue FROM i_param WHERE mkey = :appVersionOs";
		String appVersion= (String)getSession().createSQLQuery(sql).setString("appVersionOs", "appVersionOs").uniqueResult();
		return appVersion;
	}
	
	@Override
	public String getMasterVersion() {
		String sql = "SElECT mvalue FROM i_param WHERE mkey = :masterVersion";
		String appVersion= (String)getSession().createSQLQuery(sql).setString("masterVersion", "masterVersion").uniqueResult();
		return appVersion;
	}
	
	@Override
		public String getAppVersionVoice(String appName) {
		String sql = "SElECT mvalue FROM i_param WHERE mkey = :appName";
		String appVersion= (String)getSession().createSQLQuery(sql).setString("appName", appName).uniqueResult();
		return appVersion;
		}

}
