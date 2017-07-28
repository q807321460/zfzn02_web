package com.zfznjj.smarthome.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.SceneOrderDao;
import com.zfznjj.smarthome.entity.OrderInfo;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.SceneOrder;

public class SceneOrderDaoImpl implements SceneOrderDao {
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
	public SceneOrder select(String masterCode, String electricCode, String orderInfo, int sceneIndex){
		String hql = "FROM SceneOrder e WHERE e.masterCode = :masterCode and e.electricCode = :electricCode and e.orderInfo = :orderInfo and e.sceneIndex = :sceneIndex";
		return (SceneOrder)getSession().createQuery(hql).setString("masterCode", masterCode).setString("electricCode", electricCode).setString("orderInfo", orderInfo).setInteger("sceneIndex", sceneIndex).uniqueResult();
	}
	
	@Override
	public int insert(SceneOrder sceneOrder) {
		try {
			getSession().saveOrUpdate(sceneOrder);
			return 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public List<SceneOrder> ugetSceneOrders(String masterCode) {
		// TODO Auto-generated method stub
		String hql="FROM SceneOrder s WHERE s.masterCode = :masterCode AND s.isReaded = :isReaded";
		//return getSession().createQuery(hql).setString(0, masterCode).setCharacter(1, 'N').setMaxResults(3).list();
		return getSession().createQuery(hql).setString("masterCode", masterCode).setCharacter("isReaded", 'N').list();
	}

	@Override
	public void updateIsReaded(int orderID) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String hql2 = "UPDATE SceneOrder s SET s.isReaded = :isReaded , s.readTime = :timestamp WHERE s.id = :orderID";
		getSession().createQuery(hql2).setCharacter("isReaded", 'Y').setTimestamp("timestamp", timestamp).setInteger("orderID", orderID).executeUpdate();
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

}
