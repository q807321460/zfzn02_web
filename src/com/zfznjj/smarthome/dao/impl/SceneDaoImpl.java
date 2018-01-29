package com.zfznjj.smarthome.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.zfznjj.smarthome.dao.SceneDao;
import com.zfznjj.smarthome.model.Account;
import com.zfznjj.smarthome.model.Scene;

public class SceneDaoImpl implements SceneDao {
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * ��ȡ�͵�ǰ�̰߳󶨵�Session
	 * @return
	 */
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public int saveOrUpdate(Scene scene) {
		try {
			getSession().saveOrUpdate(scene);
			return 1;
		} catch (HibernateException e) {
			//e.printStackTrace();
			return -2;
		}
	}

	@Override
	public int delete( String masterCode, int sceneIndex) {
		String hql = "DELETE Scene s WHERE  s.masterCode = :masterCode AND s.sceneIndex = :sceneIndex";
		return getSession().createQuery(hql).setString("masterCode", masterCode).
				setInteger("sceneIndex", sceneIndex).executeUpdate();
	}

	@Override
	public Scene select(String masterCode, int sceneIndex) {
		String hql = "FROM Scene s WHERE s.masterCode = :masterCode AND s.sceneIndex = :sceneIndex";
		return (Scene)getSession().createQuery(hql).setString("masterCode", masterCode).
				setInteger("sceneIndex", sceneIndex).uniqueResult();
	}
	
	@Override
	public int updateSceneSequ(String masterCode, int sceneSequ) {
		String sql = "UPDATE scenes SET scenes.scene_sequ = scenes.scene_sequ - 1 WHERE "
				+ "scenes.master_code = :masterCode AND scenes.scene_sequ > :sceneSequ";
		return getSession().createSQLQuery(sql).setString("masterCode", masterCode).setInteger("sceneSequ", sceneSequ).executeUpdate();
	}
	@Override
	public List<Scene> select(String masterCode) {
		String hql = "FROM Scene s WHERE s.masterCode = :masterCode";
		return getSession().createQuery(hql).setString("masterCode", masterCode).list();
	}
	
	@Override
	public int getMaxSceneSequ(String masterCode) {
		String sql = "SELECT MAX(scene_sequ) FROM scenes WHERE master_code = :masterCode";
		Object object = getSession().createSQLQuery(sql).setString("masterCode", masterCode).uniqueResult();
		if (object==null) {
			return -1;
		}else {
			return (int)object;
		}
	}

}
