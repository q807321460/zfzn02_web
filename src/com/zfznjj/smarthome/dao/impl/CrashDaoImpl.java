package com.zfznjj.smarthome.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.CrashDao;
import com.zfznjj.smarthome.model.CrashLog;

/** 
* @author  jia 
* @date 创建时间：2017年4月12日 下午3:32:17
* @Description 
* @version 1.0  
*/
public class CrashDaoImpl implements CrashDao{

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
	public int save(CrashLog crashLog) {
		try {
			getSession().saveOrUpdate(crashLog);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}
}
