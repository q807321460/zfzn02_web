package com.zfznjj.smarthome.dao.impl;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.zfznjj.smarthome.dao.ElectricSharedDao;
import com.zfznjj.smarthome.entity.ElectricSharedLoacl;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.ElectricShared;

public class ElectricSharedDaoImpl implements ElectricSharedDao{
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
	public int saveOrUpdate(ElectricShared electricShared) {
		// TODO Auto-generated method stub
		try {
			getSession().saveOrUpdate(electricShared);;
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}
	

	@Override
	public int delete(int electricId, int userId) {
		// TODO Auto-generated method stub
		String hql = "DELETE ElectricShared es WHERE es.electricId = :electricId AND es.userId = :userId";
		return getSession().createQuery(hql).setInteger("electricId", electricId).setInteger("userId", userId).executeUpdate();
	}
	
	

	@Override
	public ElectricShared select(int electricId, int userId) {
		String hql = "FROM ElectricShared es WHERE es.electricId = :electricId AND es.userId = :userId";
		return (ElectricShared)getSession().createQuery(hql).setInteger("electricId", electricId).setInteger("userId", userId).uniqueResult();
	}
	@Override
	public int deleteByUserId(int userId) {
		String hql = "DELETE ElectricShared es WHERE es.userId = :userId";
		return getSession().createQuery(hql).setInteger("userId", userId).executeUpdate();
	}
	
	@Override
	public int deleteByUser(String accountCode, String masterCode) {
		String sql = "DELETE FROM electricshared WHERE "
				+ "user_id = "
				+ "(SELECT user_id FROM users AS u WHERE u.ACCOUNT_CODE = :accountCode AND u.MASTER_CODE = :masterCode )";
		return getSession().createSQLQuery(sql).setString("accountCode", accountCode).setString("masterCode", masterCode).executeUpdate();
	}
	
	@Override
	public int deleteByElectricId(int electricId) {
		String hql = "DELETE ElectricShared es WHERE es.electricId = :electricId";
		return getSession().createQuery(hql).setInteger("electricId", electricId).executeUpdate();
	}
	
	@Override
	public int deleteByElectric(String masterCode, int electricIndex) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM electricshared WHERE "
				+ "electric_id = "
				+ "(SELECT electric_id FROM electrics AS e WHERE e.MASTER_CODE = :masterCode AND e.ELECTRIC_INDEX = :electricIndex )";
		return getSession().createSQLQuery(sql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).executeUpdate();
	}
	
	@Override
	public void addSharedElectric(String accountCode, String masterCode) {
		// TODO Auto-generated method stub
		String sql2 = "INSERT INTO electricshared(user_id,electric_id,s_elec_name,s_elec_sequ) "
				+ "SELECT u.USER_ID,e.ELECTRIC_ID,e.ELECTRIC_NAME,e.ELECTRIC_SEQU FROM users u LEFT JOIN electrics e ON 1=1 "
				+ "WHERE (u.account_code = :accountCode AND u.master_code = :umasterCode AND e.master_code = :emasterCode);";
		getSession().createSQLQuery(sql2).setString("accountCode", accountCode).setString("umasterCode", masterCode).setString("emasterCode", masterCode).executeUpdate();
		return;
	}
	
	@Override
	public void addSharedElectric(String accountCode,String masterCode, int electricIndex) {
		// TODO Auto-generated method stub
				String sql2 = "INSERT INTO electricshared(user_id,electric_id,s_elec_name,s_elec_sequ) "
						+ "SELECT u.USER_ID,e.ELECTRIC_ID,e.ELECTRIC_NAME,e.ELECTRIC_SEQU FROM users u LEFT JOIN electrics e ON 1=1 "
						+ "WHERE (u.account_code = :accountCode AND u.master_code = :umasterCode AND e.master_code = :emasterCode AND e.electric_index = :electricIndex );";
				getSession().createSQLQuery(sql2).setString("accountCode", accountCode).setString("umasterCode", masterCode).setString("emasterCode", masterCode)
				.setInteger("electricIndex", electricIndex).executeUpdate();
				return;
		
	}
	
	
	@Override
	public List<Electric> select(String accountCode, String masterCode) {
		String sql = "SELECT ELECTRIC_ID as electricId,MASTER_CODE as masterCode,ROOM_INDEX as roomIndex,"
				+ "ELECTRIC_INDEX as electricIndex, ELECTRIC_NAME as electricName, ELECTRIC_SEQU as electricSequ,"
				+ "ELECTRIC_CODE as electricCode, ORDER_INFO AS orderInfo,"
				+ "ELECTRIC_TYPE as electricType,BELONG as belong, EXTRAS as extras"
				+ " FROM electrics WHERE electrics.ELECTRIC_ID IN "
				+ "(SELECT electric_id FROM electricshared LEFT JOIN users u "
				+ "ON electricshared.USER_ID = u.USER_ID "
				+ "WHERE u.ACCOUNT_CODE = :accountCode AND u.MASTER_CODE = :masterCode "
				+ "AND electricshared.IS_SHARED = 1);";
		SQLQuery query = getSession().createSQLQuery(sql).addScalar("electricId", StandardBasicTypes.INTEGER)
				.addScalar("masterCode",StandardBasicTypes.STRING)
				.addScalar("roomIndex",StandardBasicTypes.INTEGER)
				.addScalar("electricIndex",StandardBasicTypes.INTEGER)
				.addScalar("electricName",StandardBasicTypes.STRING)
				.addScalar("electricSequ",StandardBasicTypes.INTEGER)
				.addScalar("orderInfo",StandardBasicTypes.STRING)
				.addScalar("electricCode",StandardBasicTypes.STRING)
				.addScalar("electricType",StandardBasicTypes.INTEGER)
				.addScalar("belong",StandardBasicTypes.INTEGER)
				.addScalar("extras",StandardBasicTypes.STRING);
		return query.setString("accountCode", accountCode).setString("masterCode", masterCode)
				.setResultTransformer(Transformers.aliasToBean(Electric.class)).list();
	}
	
	@Override
	public List<ElectricSharedLoacl> select(String masterCode) {
		String sql = "SELECT e.master_code as masterCode,account_code as accountCode,"
				+ "electric_code as electricCode,electric_index as electricIndex,"
				+ "electric_type as electricType,order_info as orderInfo,"
				+ "electric_name as electricName,room_index as roomIndex,is_shared as isShared "
				+ "FROM (electrics e RIGHT JOIN users u ON e.master_code = u.master_code) "
				+ "RIGHT JOIN electricshared es "
				+ "ON es.electric_id = e.electric_id AND es.user_id = u.user_id  "
				+ "WHERE e.master_code = :masterCode";
		SQLQuery query  = getSession().createSQLQuery(sql)
				.addScalar("masterCode", StandardBasicTypes.STRING)
				.addScalar("accountCode", StandardBasicTypes.STRING)
				.addScalar("electricCode", StandardBasicTypes.STRING)
				.addScalar("electricIndex", StandardBasicTypes.INTEGER)
				.addScalar("electricType", StandardBasicTypes.INTEGER)
				.addScalar("orderInfo", StandardBasicTypes.STRING)
				.addScalar("electricName", StandardBasicTypes.STRING)
				.addScalar("roomIndex", StandardBasicTypes.INTEGER)
				.addScalar("isShared", StandardBasicTypes.INTEGER);
		return query.setString("masterCode", masterCode).setResultTransformer(Transformers.aliasToBean(ElectricSharedLoacl.class)).list();
	}
	
	@Override
	public int adminElectricShared(String accountCode, String masterCode, int electricIndex, int admin) {
		String sql = "UPDATE electricshared SET is_shared = :admin "
				+ "WHERE user_id = "
				+ "(SELECT user_id FROM users WHERE account_code = :accountCode AND master_code = :masterCode1) "
				+ "AND electric_id = "
				+ "(SELECT electric_id FROM electrics WHERE master_code = :masterCode2 AND electric_index = :electricIndex);";
		return getSession().createSQLQuery(sql).setInteger("admin", admin).setString("accountCode", accountCode).setString("masterCode1", masterCode)
		.setString("masterCode2", masterCode).setInteger("electricIndex", electricIndex).executeUpdate();
	}
	
	
	

}
