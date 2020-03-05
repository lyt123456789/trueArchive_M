package cn.com.trueway.archives.templates.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.trueway.archives.templates.dao.MetaDataDao;
import cn.com.trueway.archives.templates.pojo.EssMetaData;
import cn.com.trueway.archives.templates.pojo.EssProp;
import cn.com.trueway.archives.templates.pojo.EssPropValue;
import cn.com.trueway.archives.templates.service.MetaDataService;

public class MetaDataServiceImpl implements MetaDataService{
	
	private MetaDataDao metaDataDao;

	public MetaDataDao getMetaDataDao() {
		return metaDataDao;
	}

	public void setMetaDataDao(MetaDataDao metaDataDao) {
		this.metaDataDao = metaDataDao;
	}

	@Override
	public int getNameSpaceListCount(Map<String, Object> map) {
		return this.metaDataDao.getNameSpaceListCount(map);
	}

	@Override
	public List<Map<String, String>> getNameSpaceList(Map<String, Object> map) {
		return this.metaDataDao.getNameSpaceList(map);
	}

	@Override
	public boolean deleteOneNameSpaceById(String ids) {
		try {
			String[] idz = ids.split(",");
			for(int i = 0; i < idz.length; i++) {
				String id = idz[i];
				this.metaDataDao.deleteOneNameSpaceById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveNameSpaceRecord(Map<String, Object> map) {
		try {
			this.metaDataDao.saveNameSpaceRecord(map);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public int getMetaDataListCount(Map<String, Object> map) {
		return this.metaDataDao.getMetaDataListCount(map);
	}

	@Override
	public List<EssMetaData> getMetaDataList(Map<String, Object> map) {
		return this.metaDataDao.getMetaDataList(map);
	}

	@Override
	public boolean deleteMetaDataById(String ids) {
		try {
			String[] idz = ids.split(",");
			for(int i = 0; i < idz.length; i++) {
				String id = idz[i];
				this.metaDataDao.deleteMetaDataById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean checkMetaDataRecord(Map<String, Object> map) {
		List<EssMetaData> list = this.metaDataDao.checkMetaDataRecord(map);
		if(list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean saveMetaDataRecord(EssMetaData emda) {
		try {
			this.metaDataDao.saveMetaDataRecord(emda);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<EssPropValue> getMetaDataPropValue(Map<String, Object> map) {
		EssProp ep = this.metaDataDao.getMetaDataPorp(map);
		if(ep == null) {
			return null;
		} else {
			String idProp = ep.getId();
			if(idProp == null || "".equals(idProp)) {
				return null;
			}
			map.put("idProp", idProp);
			List<EssPropValue> list = this.metaDataDao.getMetaDataPropValue(map);
			return list;
		}
	}

	@Override
	public boolean deleteMeDaPropertyById(String ids) {
		try {
			String[] idz = ids.split(",");
			for(int i = 0; i < idz.length; i++) {
				String id = idz[i];
				this.metaDataDao.deleteMeDaPropertyById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveMeDaPropertyRecord(Map<String, Object> map) {
		try {
			EssPropValue epve = (EssPropValue)map.get("essPropValue");
			String id = epve.getId();
			if("newAdd".equals(id)) {
				EssProp ep = this.metaDataDao.getMetaDataPorp(map);
				String metaDataId = (String)map.get("metaDataId");
				String propId = null;
				if(ep == null || ep.getId() == null || "".equals(ep.getId())) {
					EssProp epn = new EssProp(null,"fieldCode","metadata","元数据维护","元数据维护", metaDataId);
					propId = this.metaDataDao.saveMeDaPropRela(epn);
				} else {
					propId = ep.getId();
				}
				epve.setIdProp(propId);
				this.metaDataDao.saveMeDaPropertyRecord(epve);
			} else {
				this.metaDataDao.saveMeDaPropertyRecord(epve);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<EssPropValue> getMetaDataPropValueById(Map<String, Object> map) {
		return this.metaDataDao.getMetaDataPropValue(map);
	}

}
