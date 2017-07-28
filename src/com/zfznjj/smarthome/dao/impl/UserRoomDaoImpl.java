package com.zfznjj.smarthome.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.UserRoomDao;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.model.UserRoom;

public class UserRoomDaoImpl implements UserRoomDao{
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
	public int saveOrUpdate(UserRoom userRoom) {
		// TODO Auto-generated method stub
		try {
			getSession().saveOrUpdate(userRoom);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}

	@Override
	public int delete(String masterCode, int roomIndex) {
		String hql = "DELETE UserRoom ur WHERE ur.masterCode = :masterCode and ur.roomIndex = :roomIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("roomIndex", roomIndex).executeUpdate();
	}

	@Override
	public UserRoom select(String masterCode, int roomIndex) {
		// TODO Auto-generated method stub
		String hql = "FROM UserRoom ur WHERE ur.masterCode = :masterCode and ur.roomIndex = :roomIndex";
		return (UserRoom)getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("roomIndex", roomIndex).uniqueResult();
	}
	@Override
	public List<UserRoom> select(String masterCode) {
		// TODO Auto-generated method stub
		String hql = "FROM UserRoom ur WHERE ur.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).list();
	}
	
	@Override
	public int updateRoomSequ(String masterCode, int roomSequ) {
		String sql = "UPDATE userrooms SET userrooms.room_sequ = userrooms.room_sequ - 1 WHERE "
				+ "userrooms.master_code = :masterCode AND userrooms.room_sequ > :roomSequ";
		return getSession().createSQLQuery(sql).setString("masterCode", masterCode).setInteger("roomSequ", roomSequ).executeUpdate();
	}

}
