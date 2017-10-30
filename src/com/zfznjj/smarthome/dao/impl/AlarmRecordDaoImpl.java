package com.zfznjj.smarthome.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.AlarmRecordDao;
import com.zfznjj.smarthome.model.AlarmRecord;

public class AlarmRecordDaoImpl implements AlarmRecordDao {
	
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
	public int saveOrUpdate(AlarmRecord alarmRecord) {
		try {
			getSession().saveOrUpdate(alarmRecord);
			return 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			return -2;
		}
	}
	
	@Override
	public List<AlarmRecord> select(String masterCode) {
		String hql = "FROM AlarmRecord a WHERE a.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("electricCode", masterCode).list();
	}
	
	//获取报警记录表中，当前主机下的最大sequ值
	@Override
	public int getMaxRecordSequ(String masterCode) {
		String sql = "SELECT MAX(record_sequ) FROM alarmrecords WHERE master_code = :masterCode";
		Object object = getSession().createSQLQuery(sql).setString("masterCode", masterCode).uniqueResult();
		if (object==null) {
			return -1;
		}else {
			return (int)object;
		}
	}
	
	@Override
	public int delete(String masterCode, int recordSequ) {
		String hql = "DELETE AlarmRecord a WHERE a.masterCode = :masterCode AND a.recordSequ = :recordSequ";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("recordSequ", recordSequ).executeUpdate();
	}
	
	@Override
	public int updateAlarmRecordSequ(String masterCode) {
		String sql = "UPDATE alarmrecords SET record_sequ = record_sequ - 1 WHERE master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("masterCode", masterCode).executeUpdate();
	}
	
}


