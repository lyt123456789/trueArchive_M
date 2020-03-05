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

import cn.com.trueway.archives.templates.dao.DataReceptionDao;
import cn.com.trueway.archives.templates.dao.DataTempDao;
import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.service.DataReceptionService;
import cn.com.trueway.archives.templates.service.DataTempService;


@Service("dataTempService")
public class DataReceptionServiceImpl implements DataReceptionService {
	@Autowired
	private DataReceptionDao dataReceptionDao;

	public DataReceptionDao getDataReceptionDao() {
		return dataReceptionDao;
	}

	public void setDataReceptionDao(DataReceptionDao dataReceptionDao) {
		this.dataReceptionDao = dataReceptionDao;
	}

	@Override
	public int getStructureDataCount(Map<String, Object> map) {
		return dataReceptionDao.getStructureDataCount(map);
	}

	@Override
	public List<Map<String, Object>> getStructureDataList(
			Map<String, Object> map, Integer pageSize, Integer pageIndex) {
		return dataReceptionDao.getStructureDataList(map,pageSize,pageIndex);
	}

	@Override
	public List<Map<String, Object>> getStructureData(Map<String, Object> map) {
		return dataReceptionDao.getStructureData(map);
	}

	

	
}
