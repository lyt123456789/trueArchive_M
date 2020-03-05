package cn.com.trueway.workflow.set.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.set.dao.PushDao;
import cn.com.trueway.workflow.set.pojo.PushEntity;

@SuppressWarnings("unchecked")
public class PushDaoImpl extends BaseDao implements PushDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushDaoImpl.class);
	
	@Override
	public void add(PushEntity entity) {
		getEm().persist(entity);
	}

	@Override
	public void delete(PushEntity entity) {
		try {
			String hql = "delete from PushEntity t where t.token = :token and t.loginName = :loginName";
			Query query = super.getEm().createQuery(hql);
			query.setParameter("token", entity.getToken());
			query.setParameter("loginName", entity.getLoginName());
			query.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.error("dao层内部异常"+e);
		}
	}

	@Override
	public void update(PushEntity entity) {
		try {
			getEm().merge(entity);
		} catch (Exception e) {
			LOGGER.error("dao层内部异常"+e);
		}
		
	}

	@Override
	public List<PushEntity> select(String token, String loginName) {
		try {
			String hql = "from PushEntity t where t.token = :token and t.loginName = :loginName";
			Query query = super.getEm().createQuery(hql);
			query.setParameter("token", token);
			query.setParameter("loginName", loginName);
			List<PushEntity> list = query.getResultList();
			return list;
		} catch (Exception e) {
			LOGGER.error("dao层内部异常"+e);
		}
		return null;
	}

	@Override
	public List<PushEntity> selectByUserId(String userId) {
		try {
			String hql = "from PushEntity t where t.userId = :userId";
			Query query = super.getEm().createQuery(hql);
			query.setParameter("userId", userId);
			List<PushEntity> list = query.getResultList();
			return list;
		} catch (Exception e) {
			LOGGER.error("dao层内部异常"+e);
		}
		return null;
	}

	@Override
	public List<PushEntity> selectNew(String token) {
		try {
			String hql = "from PushEntity t where t.token = :token";
			Query query = super.getEm().createQuery(hql);
			query.setParameter("token", token);
			List<PushEntity> list = query.getResultList();
			return list;
		} catch (Exception e) {
			LOGGER.error("dao层内部异常"+e);
		}
		return null;
	}

	@Override
	public void deleteByToken(String token) {
		try {
			String hql = "delete from PushEntity t where t.token = :token";
			Query query = super.getEm().createQuery(hql);
			query.setParameter("token", token);
			query.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.error("dao层内部异常"+e);
		}
		
	}
	
	
}
