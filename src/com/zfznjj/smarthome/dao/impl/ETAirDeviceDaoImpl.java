package com.zfznjj.smarthome.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.ETAirDeviceDao;
import com.zfznjj.smarthome.model.ETAirDevice;

public class ETAirDeviceDaoImpl implements ETAirDeviceDao {
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
	public int save(ETAirDevice etAirDevice) {
		try {
			getSession().saveOrUpdate(etAirDevice);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}
	
	@Override
	public ETAirDevice query(String masterCode, int electricIndex) {
		String hql = "FROM ETAirDevice e WHERE e.masterCode = :masterCode AND e.electricIndex = :electricIndex";
		return (ETAirDevice)getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).uniqueResult();
	}
	
	@Override
	public int delete(String masterCode, int electricIndex) {
		String hql = "DELETE ETAirDevice e WHERE e.masterCode = :masterCode and e.electricIndex = :electricIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).executeUpdate();
	}
	
	
}
