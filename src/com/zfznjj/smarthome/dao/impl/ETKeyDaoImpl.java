package com.zfznjj.smarthome.dao.impl;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.zfznjj.smarthome.dao.ETKeyDao;
import com.zfznjj.smarthome.model.ETKey;

public class ETKeyDaoImpl implements ETKeyDao {
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
	public int save(ETKey etKey) {

		try {
			getSession().saveOrUpdate(etKey);
			return 1;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}
	
	@Override
	public int save(List<ETKey> list) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			int i = 0;
			// TODO Auto-generated method stub
			for (ETKey etKey : list) {
	            System.out.println("start!!");
	            if (session.contains(etKey)) {
	                System.out.println("yes,it's here");
	                continue;
	            } else {
	            	i++;
	                session.save(etKey);
	                System.out.println("start--");
	            }

	            if (i % 2 == 0 || i == list.size()) {
	            	System.out.println((i %2 == 0)?("第"+i/2+"次提交"):("第"+(int)Math.ceil(i/2.0)+"次提交"));
	                session.getTransaction().commit();
	                session.flush();
	                session.clear();
	                session.beginTransaction();
	            }

	        }

	        System.out.println(i);
	        session.getTransaction().commit();
	        session.close();
			return 1;
		} catch (Exception e) {
			return -2;
		}
		
	}
	
	@Override
	public int updateIRKeyValue(String masterCode, int electricIndex, int keyKey, String keyValue) {
		String sql = "UPDATE etkey SET key_value = :keyValue WHERE master_code = :masterCode AND electric_index = :electricIndex AND key_key = :keyKey";
		return getSession().createSQLQuery(sql).setString("keyValue", keyValue).setString("masterCode", masterCode)
				.setInteger("electricIndex", electricIndex).setInteger("keyKey", keyKey).executeUpdate();
	}
	
	@Override
	public ETKey query(String masterCode, int electricIndex, int keyKey) {
		String hql = "FROM ETKey e WHERE e.masterCode = :masterCode AND e.electricIndex = :electricIndex AND e.keyKey = :keyKey";
		return (ETKey)getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).setInteger("keyKey", keyKey).uniqueResult();
	}
	
	@Override
	public List<ETKey> loadKeyByElectric(String masterCode, int electricIndex) {
		String hql = "FROM ETKey e WHERE e.masterCode = :masterCode AND e.electricIndex = :electricIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).list();
	}
	
	@Override
	public int delete(String masterCode, int electricIndex) {
		String hql = "DELETE ETKey e WHERE e.masterCode = :masterCode and e.electricIndex = :electricIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).setInteger("electricIndex", electricIndex).executeUpdate();
	}
}
