package com.zfznjj.smarthome.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.ChildNodeDao;
import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.model.ChildNode;
import com.zfznjj.smarthome.model.UserRoom;
import com.zfznjj.smarthome.util.SmartHomeUtil;

public class ChildNodeDaoImpl implements ChildNodeDao{
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
	public int saveOrUpdate(ChildNode childNode) {
		// TODO Auto-generated method stub
		try {
			getSession().saveOrUpdate(childNode);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}

	@Override
	public int delete(String masterCode, String electricCode) {
		String hql = "DELETE ChildNode c WHERE c.masterCode = :masterCode and c.electricCode = :electricCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setString("electricCode", electricCode).executeUpdate();
	}

	@Override
	public ChildNode select(String masterCode, String electricCode) {
		String hql = "FROM ChildNode c WHERE c.masterCode = :masterCode and c.electricCode = :electricCode";
		return (ChildNode)getSession().createQuery(hql).setString("masterCode", masterCode).setString("electricCode", electricCode).uniqueResult();
	}
	
	//UPDATE Electric e SET e.electricState = ? WHERE e.electricCode = ?
		@Override
		public int updateChildNodeState(String masterCode, String electricCode, String electricState, String stateInfo) {
			Timestamp timestamp = new Timestamp(new Date().getTime());
			String changeTime = SmartHomeUtil.TimestampToString(timestamp);
			String hql = "UPDATE ChildNode c SET c.electricState = :electricState ,c.stateInfo = :stateInfo, c.changeTime = :changeTime "
					+ "WHERE c.masterCode = :masterCode AND c.electricCode = :electricCode";
			return getSession().createQuery(hql).setString("electricState", electricState).
					setString("stateInfo", stateInfo).setString("changeTime", changeTime).
				setString("masterCode", masterCode).setString("electricCode", electricCode).executeUpdate();
		}

		

		@Override
		public ElectricState getState(String masterCode, String electricCode) {
			// TODO Auto-generated method stub
			String hql = "FROM ChildNode c WHERE c.masterCode = :masterCode AND c.electricCode = :electricCode";
			ChildNode childNode = (ChildNode) getSession().createQuery(hql).setString("masterCode", masterCode).setString("electricCode", electricCode).uniqueResult();
			return SmartHomeUtil.childNodeToElectricState(childNode);
		}

		@Override
		public List<ElectricState> getStateByMasterCode(String masterCode) {
			// TODO Auto-generated method stub
			String hql = "FROM ChildNode c WHERE c.masterCode = :masterCode";
			List<ChildNode> childNodes = getSession().createQuery(hql).setString("masterCode", masterCode).list();	
			return SmartHomeUtil.childNodesToElectricStates(childNodes);
		}

}
