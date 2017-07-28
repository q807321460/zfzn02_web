package com.zfznjj.smarthome.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.UserDao;
import com.zfznjj.smarthome.model.User;
import com.zfznjj.smarthome.util.SmartHomeUtil;

public class UserDaoImpl implements UserDao {

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
	public int saveOrUpdate(User user) {
		// TODO Auto-generated method stub
		try {
			getSession().saveOrUpdate(user);
			return 1;
		} catch (HibernateException e) {
			// TODO: handle exception
			return -2;
		}
	}

	@Override
	public int delete(String accountCode, String masterCode) {
		// TODO Auto-generated method stub
		String hql = "DELETE User u WHERE u.accountCode = :accountCode AND u.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("accountCode", accountCode).setString("masterCode", masterCode).executeUpdate();
	}

	@Override
	public User select(String accountCode, String masterCode) {
		// TODO Auto-generated method stub
		String hql = "FROM User u WHERE u.accountCode = :accountCode AND u.masterCode = :masterCode";
		return (User)getSession().createQuery(hql).setString("accountCode", accountCode).setString("masterCode", masterCode).uniqueResult();
	}
	
	@Override
	public int updateUserAreaTime(String masterCode) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String areaTime = SmartHomeUtil.TimestampToString(timestamp);
		String sql = "UPDATE USERS SET area_time = :areaTime WHERE master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("areaTime", areaTime).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int updateUserIP(String masterCode, String userIP) {
		String sql = "UPDATE USERS SET user_ip = :userIP WHERE master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("userIP", userIP).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int updateUserName(String accountCode, String masterCode, String userName) {
		String sql = "UPDATE USERS SET user_name = :userName WHERE account_code = :accountCode and master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("userName", userName).setString("accountCode", accountCode).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int updateUserELectricTime(String masterCode) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String electricTime = SmartHomeUtil.TimestampToString(timestamp);
		String sql = "UPDATE USERS SET electric_time = :electricTime WHERE master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("electricTime", electricTime).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int updateUserELectricTime(String accountCode, String masterCode) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String electricTime = SmartHomeUtil.TimestampToString(timestamp);
		String sql = "UPDATE USERS SET electric_time = :electricTime WHERE account_code = :accountCode and master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("electricTime", electricTime).setString("accountCode", accountCode).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int updateUserSceneTime(String masterCode) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String sceneTime = SmartHomeUtil.TimestampToString(timestamp);
		String sql = "UPDATE USERS SET scene_time = :sceneTime WHERE master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("sceneTime", sceneTime).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int updateUserSceneELectricTime(String masterCode) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		String sceneElectricTime = SmartHomeUtil.TimestampToString(timestamp);
		String sql = "UPDATE USERS SET scene_electric_time = :sceneElectricTime WHERE master_code = :masterCode";
		return getSession().createSQLQuery(sql).setString("sceneElectricTime", sceneElectricTime).setString("masterCode", masterCode).executeUpdate();
	}
	@Override
	public List<User> selectByAccountCode(String accountCode) {
		String hql = "FROM User u WHERE u.accountCode = :accountCode";
		return getSession().createQuery(hql).setString("accountCode", accountCode).list();
	}
	
	@Override
	public List<User> selectByMasterCode(String masterCode) {
		String hql = "FROM User u WHERE u.masterCode = :masterCode and u.isAdmin = 0";
		return getSession().createQuery(hql).setString("masterCode", masterCode).list();
	}
	
	@Override
	public List<User> selectByMasterCodeAll(String masterCode) {
		String hql = "FROM User u WHERE u.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).list();
	}
	
	@Override
	public List<User> selectAllByMasterCode(String masterCode) {
		String hql = "FROM User u WHERE u.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).list();
	}
	
	public List<String> seleteAccountCodesByMaster(String masterCode){
		String sql = "Select accountCode FROM User u WHERE u.masterCode = :masterCode";
		return getSession().createQuery(sql).setString("masterCode", masterCode).list();
	}

}
