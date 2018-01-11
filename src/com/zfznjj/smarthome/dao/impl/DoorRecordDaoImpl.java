package com.zfznjj.smarthome.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

//import com.sun.xml.internal.ws.developer.StreamingAttachment;
import com.zfznjj.smarthome.dao.DoorRecordDao;
import com.zfznjj.smarthome.model.DoorRecord;
import com.zfznjj.smarthome.util.JsonPluginsUtil;

import net.sf.json.JSON;

public class DoorRecordDaoImpl implements DoorRecordDao {
	
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
	public int saveOrUpdate(DoorRecord doorRecord) {
		try {
			getSession().saveOrUpdate(doorRecord);
			return 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			return -2;
		}
	}
	
	@Override
	public List<DoorRecord> select(String masterCode, String electricCode) {
		String hql = "FROM DoorRecord d WHERE d.masterCode = :masterCode AND d.electricCode = :electricCode";
//		return getSession().createQuery(hql).setString("masterCode", masterCode).setString("electricCode", electricCode).list();
		Query q = getSession().createQuery(hql).setString("masterCode", masterCode).setString("electricCode", electricCode);
        //限制条数
        q.setFirstResult(0);
        q.setMaxResults(300);
        return q.list();
	}

	@Override
	public List<Object[]> select(String electricCode) {
		String sql = "SELECT record_sequ, by_person, open_time from doorrecords WHERE electric_code = :electricCode";
		Query query = getSession().createSQLQuery(sql).setString("electricCode", electricCode);
		List<Object[]> lists = query.list();
		return lists;
	}
	
	@Override
	public DoorRecord selectTop(String electricCode) {
		String sql = "SELECT * from doorrecords WHERE electric_code = :electricCode";
		List<DoorRecord> lists = getSession().createSQLQuery(sql).setString("electricCode", electricCode).list();
		return lists.get(lists.size()-1); // 返回最后一个，也就是最新的数据
	}
	
	@Override
	public int getMaxRecordSequ(String electricCode) {
		String sql = "SELECT MAX(record_sequ) FROM doorrecords WHERE electric_code = :electricCode";
		Object object = getSession().createSQLQuery(sql).setString("electricCode", electricCode).uniqueResult();
		if (object==null) {
			return -1;
		}else {
			return (int)object;
		}
	}
	
	@Override
	public int delete(String electricCode, int recordSequ) {
		String hql = "DELETE DoorRecord d WHERE d.electricCode = :electricCode AND d.recordSequ = :recordSequ";
		return getSession().createQuery(hql).setString("electricCode", electricCode).setInteger("recordSequ", recordSequ).executeUpdate();
	}
	
	@Override
	public int updateDoorRecordSequ(String electricCode) {
		String sql = "UPDATE doorrecords SET record_sequ = record_sequ - 1 WHERE electric_code = :electricCode";
		return getSession().createSQLQuery(sql).setString("electricCode", electricCode).executeUpdate();
	}
}
