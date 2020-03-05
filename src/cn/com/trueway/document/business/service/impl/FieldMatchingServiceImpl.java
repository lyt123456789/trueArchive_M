package cn.com.trueway.document.business.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.trueway.document.business.dao.FieldMatchingDao;
import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.DocxgFieldMap;
import cn.com.trueway.document.business.service.FieldMatchingService;

public class FieldMatchingServiceImpl implements FieldMatchingService{
	
	private FieldMatchingDao fieldMatchingDao;

	public FieldMatchingDao getFieldMatchingDao() {
		return fieldMatchingDao;
	}

	public void setFieldMatchingDao(FieldMatchingDao fieldMatchingDao) {
		this.fieldMatchingDao = fieldMatchingDao;
	}

	@Override
	public void addDocxgFieldMap(DocxgFieldMap docxgField) {
		fieldMatchingDao.addDocxgFieldMap(docxgField);
	}

	@Override
	public List<DocxgFieldMap> getDocxgFieldMapList(String itemId) {
		return fieldMatchingDao.getDocxgFieldMapList(itemId);
	}

	@Override
	public void deleteDocxgFieldMapByItemId(String itemId) {
		fieldMatchingDao.deleteDocxgFieldMapByItemId(itemId);
	}

	@Override
	public void saveForm(String tableName, Map<String, Object> map, String instanceId) {
		try {
			fieldMatchingDao.saveForm(tableName, map);
		} catch (Exception e) {
			fieldMatchingDao.deleteTableInfo(instanceId, tableName);
			try {
				fieldMatchingDao.saveForm(tableName, map);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public List<DepRelationShip> getDepRelationShipListByDepId(String depId) {
		return fieldMatchingDao.getDepRelationShipListByDepId(depId);
	}

	@Override
	public void deleteDepShipById(String id) {
		fieldMatchingDao.deleteDepShipById(id);
	}

	@Override
	public void deleteTableInfo(String instanceId, String tableName) {
		fieldMatchingDao.deleteTableInfo(instanceId, tableName);
	}

}
