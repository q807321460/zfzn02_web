package com.zfznjj.smarthome.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.zfznjj.smarthome.dao.ElectricDao;
import com.zfznjj.smarthome.entity.ElectricForVoice;
import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.model.DoorRecord;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.UserRoom;

public class ElectricDaoImpl implements ElectricDao{
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
	public int saveOrUpdate(Electric electric) {
		try {
			getSession().saveOrUpdate(electric);
			return 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			return -2;
		}
	}

	@Override
	public int delete(String masterCode, int electricIndex) {
		String hql = "DELETE Electric e WHERE e.masterCode = :masterCode and e.electricIndex = :electricIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).executeUpdate();
	}
	
	@Override
	public int updateElectricSequ(String masterCode, int electricSequ, int roomIndex) {
		String sql = "UPDATE electrics SET electrics.electric_sequ = electrics.electric_sequ - 1 WHERE "
				+ "electrics.master_code = :masterCode AND electrics.electric_sequ > :electricSequ AND electrics.room_index = :roomIndex";
		return getSession().createSQLQuery(sql).setString("masterCode", masterCode).setInteger("electricSequ", electricSequ).setInteger("roomIndex", roomIndex).executeUpdate();
	}
	
	@Override
	public int updateElectricSequ(String masterCode, int electricIndex, int roomIndex, int oldElectricSequ, int newElectricSequ) {	
		try {
			//更新中间位置电器的sequ值
			if (oldElectricSequ < newElectricSequ) {
				String sql = "UPDATE electrics SET electric_sequ = electric_sequ - 1 WHERE master_code = :masterCode AND room_index = :roomIndex AND "
						+ "electric_sequ > :oldElectricSequ AND electric_sequ <= :newElectricSequ";
				getSession().createSQLQuery(sql).setString("masterCode", masterCode).setInteger("roomIndex", roomIndex)
					.setInteger("oldElectricSequ", oldElectricSequ).setInteger("newElectricSequ", newElectricSequ).executeUpdate();
			}else {
				String sql = "UPDATE electrics SET electric_sequ = electric_sequ + 1 WHERE master_code = :masterCode AND room_index = :roomIndex AND "
						+ "electric_sequ >= :newElectricSequ AND electric_sequ < :oldElectricSequ";
				getSession().createSQLQuery(sql).setString("masterCode", masterCode).setInteger("roomIndex", roomIndex)
					.setInteger("oldElectricSequ", oldElectricSequ).setInteger("newElectricSequ", newElectricSequ).executeUpdate();
			}
			
			//更新指定电器的新sequ值
			String sql2 = "UPDATE electrics SET electric_sequ = :newElectricSequ WHERE master_code = :masterCode AND electric_index = :electricIndex";
			getSession().createSQLQuery(sql2).setInteger("newElectricSequ", newElectricSequ).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
	}
	
	@Override
	public int getMaxElectricSequ(String masterCode, int roomIndex) {
		String sql = "SELECT MAX(electric_sequ) FROM electrics WHERE master_code = :masterCode AND room_index = :roomIndex";
		Object object = getSession().createSQLQuery(sql).setString("masterCode", masterCode).setInteger("roomIndex", roomIndex).uniqueResult();
		if (object==null) {
			return -1;
		}else {
			return (int)object;
		}
	}
	
	@Override
	public int saveDoorRecord(String masterCode, String electricCode, String stateInfo) {
		return 1;
	}
	
	@Override
	public Electric select(String masterCode, int electricIndex) {
		String hql = "FROM Electric e WHERE e.masterCode = :masterCode and e.electricIndex = :electricIndex";
		return (Electric)getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).uniqueResult();
	}
	
	@Override
	public Electric select(String masterCode, int electricIndex, String extras) {
		String hql = "FROM Electric e WHERE e.masterCode = :masterCode and e.electricIndex = :electricIndex and e.extras = :extras";
		return (Electric)getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).setString("extras", extras).uniqueResult();
	}
	
	@Override
	public List<Electric> select(String masterCode) {
		String hql = "FROM Electric e WHERE e.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).list();
	}
	
	@Override
	public List<Electric> select(String masterCode, String electricCode) {
		String hql = "FROM Electric e WHERE e.masterCode = :masterCode AND e.electricCode = :electricCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setString("electricCode", electricCode).list();
	}
	
	@Override
	public List<ElectricForVoice> selectForVoice(String masterCode) {
		String sql = "SELECT electric_code as electricCode,electric_name as electricName"
				+ ", electric_index as electricIndex, electric_type as electricType"
				+ ",room_name as roomName,order_info as orderInfo "
				+ "FROM electrics e RIGHT JOIN userrooms r "
				+ "ON e.master_code = r.master_code AND e.room_index = r.room_index "
				+ "WHERE e.master_code = :masterCode";
		SQLQuery query  = getSession().createSQLQuery(sql)
				.addScalar("electricCode", StandardBasicTypes.STRING)
				.addScalar("electricName", StandardBasicTypes.STRING)
				.addScalar("electricIndex", StandardBasicTypes.INTEGER)
				.addScalar("electricType", StandardBasicTypes.INTEGER)
				.addScalar("roomName", StandardBasicTypes.STRING)
				.addScalar("orderInfo", StandardBasicTypes.STRING);
		return query.setString("masterCode", masterCode)
				.setResultTransformer(Transformers.aliasToBean(ElectricForVoice.class))
				.list();
	}
	
	@Override
	public Electric getLechangeCamera(String electricCode) {
		String hql = "FROM Electric WHERE electricCode = :electricCode";
		Query query = getSession().createQuery(hql).setString("electricCode", electricCode);
		// query.setMaxResults(1); // sqlserver中不能这么写，会报错
		List<Electric> electrics = query.list();
		return electrics.get(0); // 正常应该只有一个结果
	}

}
