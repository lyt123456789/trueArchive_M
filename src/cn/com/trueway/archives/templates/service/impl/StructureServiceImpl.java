package cn.com.trueway.archives.templates.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.trueway.archives.templates.dao.StructureDao;
import cn.com.trueway.archives.templates.pojo.EssModelTags;
import cn.com.trueway.archives.templates.pojo.EssStructureModel;
import cn.com.trueway.archives.templates.service.StructureService;

public class StructureServiceImpl implements StructureService {
	
	private StructureDao structureDao;

	public StructureDao getStructureDao() {
		return structureDao;
	}

	public void setStructureDao(StructureDao structureDao) {
		this.structureDao = structureDao;
	}

	@Override
	public int getStructureTempCount(Map<String, Object> map) {
		return this.structureDao.getStructureTempCount(map);
	}

	@Override
	public List<EssStructureModel> getStructureTempList(Map<String, Object> map) {
		return this.structureDao.getStructureTempList(map);
	}

	@Override
	public boolean deleteStructureByIds(String ids) {
		try {
			String[] idz = ids.split(",");
			for(int i = 0; i < idz.length; i++) {
				String id = idz[i];
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("id",Integer.valueOf(id));
				this.structureDao.deleteStructureTempRecord(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveStructureTemp(EssStructureModel estm) {
		try {
			this.structureDao.saveStructureTemp(estm);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public int getEssModelTagsCount(Map<String, Object> map) {
		return this.structureDao.getModelTagsCount(map);
	}

	@Override
	public List<EssModelTags> getEssModelTagsList(Map<String, Object> map) {
		return this.structureDao.getModelTagsList(map);
	}

	@Override
	public void updateModelTags(EssModelTags emt) {
		this.structureDao.updateModelTags(emt);
	}

	@Override
	public boolean saveTagsMetaData(List<Map<String, String>> list) {
		try {
			for(int i = 0; i < list.size(); i++) {
				Map<String,String> map = list.get(i);
				String id = map.get("id");
				if(id == null || "".equals(id)) {
					continue;
				}
				this.structureDao.saveTagsMetaData(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public int checkIsJustOne(Map<String, Object> map) {
		return this.structureDao.checkIsJustOne(map);
	}
	
	
}
