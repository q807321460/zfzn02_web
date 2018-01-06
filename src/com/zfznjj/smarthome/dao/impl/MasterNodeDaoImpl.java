package com.zfznjj.smarthome.dao.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.MasterNodeDao;
import com.zfznjj.smarthome.model.MasterNode;
import com.zfznjj.smarthome.util.SmartHomeUtil;

public class MasterNodeDaoImpl implements MasterNodeDao {

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
	public int saveOrUpdate(MasterNode masterNode) {
		// TODO Auto-generated method stub
		try {
			getSession().saveOrUpdate(masterNode);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}	
	}
	
	@Override
	public MasterNode select(String masterCode) {

		String hql = "From MasterNode m WHERE m.masterCode = :masterCode";
		return (MasterNode)getSession().createQuery(hql).setString("masterCode", masterCode).uniqueResult();
	}
	@Override
	public int delete(String masterCode) {
		
		String hql = "DELETE MasterNode m WHERE m.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).executeUpdate();
	}
	@Override
	public int giveUpAdmin(String masterCode, String owner) {
		String hql = "UPDATE MasterNode m SET m.owner = null WHERE m.masterCode = :masterCode AND m.owner = :owner";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setString("owner", owner).executeUpdate();
	}
	@Override
	public int getAdmin(String masterCode, String owner) {
		String hql = "UPDATE MasterNode m SET m.owner = :owner WHERE m.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("owner", owner).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int updateSharedElectricTime(String masterCode) {
		String sharedElectricTime = SmartHomeUtil.TimestampToString(new Timestamp(new Date().getTime()));
		String sql = "update masternodes SET masternodes.shared_electric_time = :sharedElectricTime WHERE masternodes.master_code = :masterCode ;";
		return getSession().createSQLQuery(sql).setString("sharedElectricTime", sharedElectricTime).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int updateSharedSceneTime(String masterCode) {
		String sharedSceneTime = SmartHomeUtil.TimestampToString(new Timestamp(new Date().getTime()));
		String sql = "update masternodes SET masternodes.shared_scene_time = :sharedSceneTime WHERE masternodes.master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("sharedSceneTime", sharedSceneTime).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public String getAdminCode(String masterCode) {
		String hql = "Select m.owner From MasterNode m WHERE m.masterCode = :masterCode";
		return (String)getSession().createQuery(hql).setString("masterCode", masterCode).uniqueResult();
	}
	
	@Override
	public int updateMasterVersion(String masterCode, String masterVersion) {
		String hql = "UPDATE MasterNode SET masterVersion = :masterVersion WHERE masterCode = :masterCode";
		return getSession().createQuery(hql).setString("masterVersion", masterVersion).setString("masterCode", masterCode).executeUpdate();
	}

}
