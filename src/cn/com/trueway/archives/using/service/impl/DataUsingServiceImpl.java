package cn.com.trueway.archives.using.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.using.dao.DataUsingDao;
import cn.com.trueway.archives.using.service.DataUsingService;

@SuppressWarnings("unused")
public class DataUsingServiceImpl implements DataUsingService{
    private DataUsingDao dataUsingDao;

	public DataUsingDao getDataUsingDao() {
		return dataUsingDao;
	}
	
	public void setDataUsingDao(DataUsingDao dataUsingDao) {
		this.dataUsingDao = dataUsingDao;
	}
   
	@Override
	public List<EssTree> getTreeByMap(Map<String, String> map,
			Integer pageSize, Integer pageIndex) {
		return dataUsingDao.getTreeByMap(map,pageSize,pageIndex);
	}
	
}
