package com.zfznjj.smarthome.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.ElectricOrderDao;
import com.zfznjj.smarthome.entity.OrderInfo;
import com.zfznjj.smarthome.model.ElectricOrder;
import com.zfznjj.smarthome.util.SmartHomeUtil;

public class ElectricOrderDaoImpl implements ElectricOrderDao {

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/*
	 * 不推荐使用HibernateTemplate 及 HibernateDaoSupport
	 * 因为这样会导致Dao 和 Spring 的API 进行耦合，可移植性变差，
	 */
	//private HibernateTemplate hibernateTemplate;
	/**
	 * 获取和当前线程绑定的Session
	 * @return
	 */
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public int insert(ElectricOrder electricOrder) {
		// TODO Auto-generated method stub
		try {
			getSession().saveOrUpdate(electricOrder);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> ugetElectricOrders(String masterCode) {
		// TODO Auto-generated method stub
		String hql="FROM ElectricOrder e WHERE e.masterCode = :masterCode AND e.isReaded = :isReaded";
//		List<ElectricOrder> electricOrders = (List<ElectricOrder>)getSession().createQuery(hql).setString(0, masterCode).
//				setCharacter("isReaded", 'N').setMaxResults(3).list();
		List<ElectricOrder> electricOrders = (List<ElectricOrder>)getSession().createQuery(hql).setString("masterCode", masterCode).
				setCharacter("isReaded", 'N').list();
		return SmartHomeUtil.ElectricOrdersToOrderInfos(electricOrders);
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIsReaded(int orderID) {
		// TODO Auto-generated method stub
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String hql2 = "UPDATE ElectricOrder e SET e.isReaded = :isReaded , e.readTime = :timestamp WHERE e.id = :orderID";
		getSession().createQuery(hql2).setCharacter("isReaded", 'Y').setTimestamp("timestamp", timestamp)
		.setInteger("orderID", orderID).executeUpdate();
	}

}
