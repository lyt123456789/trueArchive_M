package cn.com.trueway.archives.templates.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.trueway.archives.templates.dao.DataManageDao;
import cn.com.trueway.archives.templates.dao.DataTempDao;
import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.service.DataManageService;
import cn.com.trueway.archives.templates.service.DataTempService;


@Service("dataTempService")
public class DataManageServiceImpl implements DataManageService {
	@Autowired
	private DataManageDao dataManageDao;

	public DataManageDao getDataManageDao() {
		return dataManageDao;
	}

	public void setDataManageDao(DataManageDao dataManageDao) {
		this.dataManageDao = dataManageDao;
	}

	@Override
	public int getStructureDataCount(Map<String, Object> map) {
		return dataManageDao.getStructureDataCount(map);
	}

	@Override
	public List<Map<String, Object>> getStructureDataList(
			Map<String, Object> map, Integer pageSize, Integer pageIndex) {
		return dataManageDao.getStructureDataList(map,pageSize,pageIndex);
	}

	@Override
	public List<Map<String, Object>> getStructureData(Map<String, Object> map) {
		return dataManageDao.getStructureData(map);
	}

	

	
}
