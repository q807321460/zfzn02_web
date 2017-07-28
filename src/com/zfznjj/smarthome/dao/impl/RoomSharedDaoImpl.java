package com.zfznjj.smarthome.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.RoomSharedDao;
import com.zfznjj.smarthome.model.ElectricShared;
import com.zfznjj.smarthome.model.RoomShared;

public class RoomSharedDaoImpl implements RoomSharedDao{
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
	public int saveOrUpdate(RoomShared roomShared) {
		// TODO Auto-generated method stub
				try {
					getSession().saveOrUpdate(roomShared);
					return 1;
				} catch (HibernateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return -2;
				}
	}
	@Override
	public int delete(int roomId, int userId) {
		String hql = "DELETE RoomShared rs WHERE rs.roomId = :roomId AND es.userId = :userId";
		return getSession().createQuery(hql).setInteger("roomId", roomId).setInteger("userId", userId).executeUpdate();
	}
	@Override
	public RoomShared select(int roomId, int userId) {
		String hql = "FROM RoomShared rs WHERE rs.roomId = :roomId AND rs.userId = :userId";
		return (RoomShared)getSession().createQuery(hql).setInteger("roomId", roomId).setInteger("userId", userId).uniqueResult();
	}
}
