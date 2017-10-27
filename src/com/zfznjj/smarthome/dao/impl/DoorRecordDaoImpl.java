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
	public String select2(String electricCode) {
		String sql = "SELECT record_sequ, open_time from doorrecords WHERE electric_code = :electricCode";
		Query query = getSession().createSQLQuery(sql).setString("electricCode", electricCode);
//		query.setFirstResult(0);
//		int maxSequ = 300;//当前需求为300条
//		query.setMaxResults(maxSequ);
		List<Object[]> lists = query.list();
		//这里需要做进一步处理
		//[{"masterCode":"AA00FFD9","extras":"","recordSequ":0,"electricCode":"1000AAF28715","doorRecordId":1,"openTime":"2017-10-17 15:05:47"}]
		String sReturn = "[";
		int size = lists.size();
		for (int i=0;i<size;i++) {
			sReturn = sReturn + "{" + "\"recordSequ\"" + ":" + lists.get(i)[0].toString() + "," + "\"openTime\"" + ":" + "\"" + lists.get(i)[1].toString() + "\"" + "}";
//			sReturn = sReturn+ "{" + lists.get(i)[0].toString() + ":" + "\"" + lists.get(i)[1].toString() + "\"" + "}";
			if(i!=size-1) {
				sReturn += ",";
			}
		}
		sReturn+="]";
		return sReturn;
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
