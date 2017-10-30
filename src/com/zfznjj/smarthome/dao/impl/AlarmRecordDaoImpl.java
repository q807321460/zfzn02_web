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
	
	@Override
	public int getMaxRecordSequ(String electricCode) {
		String sql = "SELECT MAX(record_sequ) FROM alarmrecords WHERE electric_code = :electricCode";
		Object object = getSession().createSQLQuery(sql).setString("electricCode", electricCode).uniqueResult();
		if (object==null) {
			return -1;
		}else {
			return (int)object;
		}
	}
	
	@Override
	public int delete(String electricCode, int recordSequ) {
		String hql = "DELETE AlarmRecord a WHERE a.electricCode = :electricCode AND a.recordSequ = :recordSequ";
		return getSession().createQuery(hql).setString("electricCode", electricCode).setInteger("recordSequ", recordSequ).executeUpdate();
	}
	
	@Override
	public int updateAlarmRecordSequ(String electricCode) {
		String sql = "UPDATE alarmrecords SET record_sequ = record_sequ - 1 WHERE electric_code = :electricCode";
		return getSession().createSQLQuery(sql).setString("electricCode", electricCode).executeUpdate();
	}
	
}


