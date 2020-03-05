package cn.com.trueway.workflow.set.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.com.trueway.workflow.set.dao.PushDao;
import cn.com.trueway.workflow.set.pojo.PushEntity;
import cn.com.trueway.workflow.set.service.PushService;

/**
 * ClassName: PushServiceImpl <br/>
 * date: 2016年4月19日 上午10:38:12 <br/>
 *
 * @author adolph_jiang
 * @version 
 * @since JDK 1.6
 */
public class PushServiceImpl implements PushService {
	
	private PushDao pushDao;
	
	public PushDao getPushDao() {
		return pushDao;
	}

	public void setPushDao(PushDao pushDao) {
		this.pushDao = pushDao;
	}

	@Override
	public void add(PushEntity entity) {
		pushDao.add(entity);
	}

	@Override
	public void delete(PushEntity entity) {
		pushDao.delete(entity);
	}

	@Override
	public List<PushEntity> getPushDate(String loginName, String token) {
		return pushDao.select(token, loginName);
	}

	@Override
	public void update(PushEntity entity) {
		pushDao.update(entity);
	}

	@Override
	public List<PushEntity> getPushDateByUserId(String userId) {
		List<PushEntity> list = new ArrayList<PushEntity>();
		String [] userIds = userId.split(",");
		for (String str : userIds) {
			List<PushEntity> _list = new ArrayList<PushEntity>();
			_list = pushDao.selectByUserId(str);
			list.addAll(_list);
		}
		return list;
	}

	@Override
	public List<PushEntity> getPushDateNew(String token) {
		return pushDao.selectNew(token);
	}

	@Override
	public void deleteByToken(String token) {
		pushDao.deleteByToken(token);
	}

}
