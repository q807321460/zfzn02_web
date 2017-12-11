package com.zfznjj.smarthome.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.AccountDao;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.util.SmartHomeUtil;

public class AccountDaoImpl implements AccountDao {
	
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
	public int saveOrUpdate(Account account) {
		try {
			getSession().saveOrUpdate(account);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}
	
	@Override
	public int updateUserTimeByMasterCode(String masterCode) {
		String userTime = SmartHomeUtil.TimestampToString(new Timestamp(new Date().getTime()));
		String sql = "UPDATE accounts SET user_time = :userTime WHERE account_code IN (SELECT account_code FROM users WHERE users.master_code = :masterCode);";
		return getSession().createSQLQuery(sql).setString("userTime", userTime).setString("masterCode", masterCode).executeUpdate();
	}
	
	
	
	@Override
	public int updateUserTime(String accountCode) {
	
		String userTime = SmartHomeUtil.TimestampToString(new Timestamp(new Date().getTime()));
		String sql = "update accounts SET accounts.user_time = :userTime WHERE accounts.account_code = :accountCode";
		return getSession().createSQLQuery(sql).setString("userTime", userTime).setString("accountCode", accountCode).executeUpdate();
	}
	
	@Override
	public int updateAccountTime(String accountCode, String accountTime) {
		String sql = "update accounts SET accounts.accountTime = :accountTime WHERE accountCode = :accountCode";
		return getSession().createSQLQuery(sql).setString("accountTime", accountTime).setString("accountCode", accountCode).executeUpdate();
	}

	@Override
	public int delete(String accountCode) {
		// TODO Auto-generated method stub
		String hql = "DELETE Account a WHERE a.accountCode = :accountCode";
		return getSession().createQuery(hql).setString("accountCode", accountCode).executeUpdate();
	}

	@Override
	public Account select(String accountCode) {
		String hql = "FROM Account a WHERE a.accountCode = :accountCode";
		return (Account)getSession().createQuery(hql).setString("accountCode", accountCode).uniqueResult();
	}
	
	@Override
	public List<Account> selectAccountByMasterCode(String masterCode) {
		String hql = "FROM Account a WHERE a.accountCode IN (SELECT u.accountCode FROM User u WHERE u.masterCode = :masterCode and u.isAdmin = 0)";
		return getSession().createQuery(hql).setString("masterCode", masterCode).list();
	}
	
	@Override
	public int signLeCheng(String accountCode) {
		String hql = "UPDATE Account a SET a.leSign = 1 WHERE a.masterCode = :accountCode";
		return getSession().createQuery(hql).setString("accountCode", accountCode).executeUpdate();
	}

}
