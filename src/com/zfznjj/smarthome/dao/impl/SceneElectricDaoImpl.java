package com.zfznjj.smarthome.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.SceneElectricDao;
import com.zfznjj.smarthome.dao.UserDao;
import com.zfznjj.smarthome.model.Scene;
import com.zfznjj.smarthome.model.SceneElectric;
import com.zfznjj.smarthome.util.SmartHomeUtil;

public class SceneElectricDaoImpl implements SceneElectricDao {
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
	public int saveOrUpdate(SceneElectric sceneElectric) {
		try {
			getSession().saveOrUpdate(sceneElectric);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}

	@Override
	public int delete(String masterCode, String electricCode) {
		String hql = "DELETE SceneElectric se WHERE se.masterCode = :masterCode AND se.electricCode = :electricCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setString("electricCode", electricCode).executeUpdate();
	}
	
	@Override
	public int delete(String masterCode, int electricIndex) {
		String hql = "DELETE SceneElectric se WHERE se.masterCode = :masterCode AND se.electricIndex = :electricIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).executeUpdate();
	}
	
	@Override
	public int delete(String masterCode, int electricIndex, int sceneIndex) {
		String hql = "DELETE SceneElectric se WHERE se.masterCode = :masterCode AND se.electricIndex = :electricIndex AND se.sceneIndex = :sceneIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).
				setInteger("sceneIndex", sceneIndex).executeUpdate();
	}

	
	@Override
	public int deleteBySceneIndex(String masterCode, int sceneIndex) {
		String hql = "DELETE SceneElectric se WHERE se.masterCode = :masterCode AND se.sceneIndex = :sceneIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("sceneIndex", sceneIndex).executeUpdate();
	}
	
	@Override
	public SceneElectric select(String masterCode, String electricCode, String orderInfo, int sceneIndex) {
		String hql = "FROM SceneElectric se WHERE se.masterCode = :masterCode AND se.electricCode = :electricCode "
				+ "AND se.orderInfo = :orderInfo AND se.sceneIndex = :sceneIndex";
		return (SceneElectric)getSession().createQuery(hql).setString("masterCode", masterCode).
				setString("electricCode", electricCode).setString("orderInfo", orderInfo).setInteger("sceneIndex", sceneIndex).uniqueResult();
	}
	
	@Override
	public int updateSceneElectricOrder(String masterCode, int electricIndex, int sceneIndex, String electricOrder) {
		String sql = "UPDATE SCENEELECTRICS SET electric_order = :electricOrder WHERE master_code = :masterCode AND electric_index = :electricIndex AND scene_index = :sceneIndex";
		return getSession().createSQLQuery(sql).setString("electricOrder", electricOrder).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).setInteger("sceneIndex", sceneIndex).executeUpdate();
	}
	
	@Override
	public List<SceneElectric> select(String masterCode) {
		String hql = "FROM SceneElectric se WHERE se.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).list();
	}
	
	@Override
	public List<SceneElectric> select(String masterCode, int electricIndex) {
		String hql = "FROM SceneElectric se WHERE se.masterCode = :masterCode AND se.electricIndex = :electricIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).list();
	}
	
	@Override
	public List<SceneElectric> select(String masterCode, int electricIndex, int sceneIndex) {
		String hql = "FROM SceneElectric se WHERE se.masterCode = :masterCode AND se.electricIndex = :electricIndex AND se.sceneIndex = :sceneIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).setInteger("sceneIndex", sceneIndex).list();
	}

}
