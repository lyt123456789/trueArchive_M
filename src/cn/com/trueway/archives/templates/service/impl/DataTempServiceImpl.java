package cn.com.trueway.archives.templates.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.trueway.archives.templates.dao.DataTempDao;
import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.pojo.MrzPojo;
import cn.com.trueway.archives.templates.service.DataTempService;


@Service("dataTempService")
public class DataTempServiceImpl implements DataTempService {
	@Autowired
	private DataTempDao dataTempDao;

	public DataTempDao getDataTempDao() {
		return dataTempDao;
	}

	public void setDataTempDao(DataTempDao dataTempDao) {
		this.dataTempDao = dataTempDao;
	}

	@Override
	public int TreeCountByMap(Map<String, String> map) {
		return dataTempDao.TreeCountByMap(map);
	}
	
	@Override
	public List<EssTree> getTreeByMap(Map<String, String> map,
			Integer pageSize, Integer pageIndex) {
		return dataTempDao.getTreeByMap(map,pageSize,pageIndex);
	}

	@Override
	public void updateEssTree(EssTree et) {
		dataTempDao.updateEssTree(et);
	}

	@Override
	public void updateNativeSql(String sql) {
		dataTempDao.updateNativeSql(sql);
	}

	@Override
	public List<EssStructure> getStructureList(Map<String, String> map,
			Integer pageSize, Integer pageIndex) {
		return dataTempDao.getStructureList(map,pageSize,pageIndex);
	}

	@Override
	public void updateStructure(EssStructure es) {
		dataTempDao.updateStructure(es);
	}

	@Override
	public void updateTag(EssTag etag) {
		dataTempDao.updateTag(etag);
	}

	@Override
	public List<Object[]> excuteNativeSql(String sql) {
		return dataTempDao.excuteNativeSql(sql);
	}

	@Override
	public int getEssTagCount(Map<String, String> map) {
		return dataTempDao.getEssTagCount(map);
	}

	@Override
	public List<EssTag> getEssTagList(Map<String, String> map, Integer pageSize,
			Integer pageIndex) {
		return dataTempDao.getEssTagList(map,pageSize,pageIndex);
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
				this.dataTempDao.saveTagsMetaData(map);
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
		return this.dataTempDao.checkIsJustOne(map);
	}

	@Override
	public List<EssZDXZCommon> getZDXZDataList(Map<String, String> map) {
		return this.dataTempDao.getZDXZDataList(map);
	}

	@Override
	public boolean saveZDXZDataOfTable(List<Map<String, String>> list, String tableFlag,String idstructure) {
		try {
			if(idstructure == null || "".equals(idstructure)) {
				return false;
			}
			if(tableFlag == null || "".equals(tableFlag)) {
				return false;
			}
			this.dataTempDao.deleteZDXZDataOfTable(tableFlag,idstructure);
			if(list != null && !list.isEmpty()) {
				for(int i = 0; i < list.size(); i++) {
					Map<String,String> map = list.get(i);
					this.dataTempDao.saveZDXZDataOfTable(map,tableFlag);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<EssZDXZCommon> getPXDataList(Map<String, String> map) {
		return this.dataTempDao.getPXDataList(map);
	}

	@Override
	public boolean savePxData(String sortString, String idstructure) {
		try {
			if(idstructure == null || "".equals(idstructure)) {
				return false;
			}
			this.dataTempDao.deletePxData(idstructure);
			if(sortString != null && !"".equals(sortString)) {
				this.dataTempDao.savePxData(sortString,idstructure);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<EssZDXZCommon> getZDZDataList(Map<String, String> map) {
		return this.dataTempDao.getZDZDataList(map);
	} 

	@Override
	public boolean saveOfZDZData(List<Map<String, String>> list, String structureId) {
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("structureId", structureId);
			List<EssZDXZCommon> originalList = this.dataTempDao.getZDZDataList(map);
			if(originalList != null && !originalList.isEmpty()) {
				//1. get all data in database And set data for list
				if(list != null && !list.isEmpty()) {
					for(int i = 0; i < list.size(); i++) {
						Map<String,String> mapZDZ = list.get(i);
						int idStructure = Integer.valueOf(mapZDZ.get("idStructure"));
						int idTag = Integer.valueOf(mapZDZ.get("idTag"));
						String deleteFlag = mapZDZ.get("deleteFlag");
						for(int t = 0; t < originalList.size(); t++) {
							EssZDXZCommon ez = originalList.get(t);
							int ezIdStructure = ez.getIdstructure();
							int ezIdTag = ez.getIdtag();
							if(ezIdStructure == idStructure && idTag == ezIdTag) {
								if("noChange".equals(deleteFlag)) {
									mapZDZ.put("tagIds", ez.getTagIds());
									break;
								}
							}
						}
					}
				}
				//2. delete all
				this.dataTempDao.deleteAllZDZData(structureId);
				//3. insert all
				if(list != null && !list.isEmpty()) {
					for(int i = 0; i < list.size(); i++) {
						Map<String,String> mapZDZ = list.get(i);
						this.dataTempDao.insertAllZDZDataList(mapZDZ);
					}
				}
			} else {
				//all insert
				if(list != null && !list.isEmpty()) {
					for(int i = 0; i < list.size(); i++) {
						Map<String,String> mapZDZ = list.get(i);
						this.dataTempDao.insertAllZDZDataList(mapZDZ);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveofDMZData(List<Map<String, String>> list, String tableFlag, String structureId) {
		try {
			if(structureId == null || "".equals(structureId)) {
				return false;
			}
			if(tableFlag == null || "".equals(tableFlag)) {
				return false;
			}
			//case 1: all delete, list is empty
			//case 2: delete part, get data in database, check list and data, delete part
			//case 3: all insert
			Map<String,String> mapdb = new HashMap<String,String>();
			mapdb.put("structureId", structureId);
			mapdb.put("tableFlag",tableFlag);
			List<EssZDXZCommon> dbList = this.dataTempDao.getZDXZDataList(mapdb);
			if(dbList == null || dbList.isEmpty()) {//字段代码规则表中不存在数据
				if(list != null && !list.isEmpty()) {
					for(int i = 0; i < list.size(); i++) {
						Map<String,String> map = list.get(i);
						this.dataTempDao.saveZDXZDataOfTable(map,tableFlag);
					}
				}
			} else {//字段代码规则表中存在数据
				if(list != null && !list.isEmpty()) {//前台传递数据过来
					//删除字段代码规则表
					this.dataTempDao.deleteZDXZDataOfTable(tableFlag,structureId);
					for(int i = 0; i < list.size(); i++) {
						Map<String,String> map = list.get(i);
						this.dataTempDao.saveZDXZDataOfTable(map,tableFlag);
						Iterator<EssZDXZCommon> it = dbList.iterator();
						int idTag = Integer.valueOf(map.get("idtag"));
						int idStructure = Integer.valueOf(map.get("idstructure"));
						while(it.hasNext()) {
							EssZDXZCommon ez = it.next();
							int idstructure = ez.getIdstructure();
							int idtag = ez.getIdtag();
							if(idtag == idTag && idstructure==idStructure) {
								it.remove();
							}
						}
					}
					//删除字段代码-字段值规则表
					for(int i = 0; i < dbList.size(); i++) {
						EssZDXZCommon ez = dbList.get(i);
						int idstructure = ez.getIdstructure();
						int idTag = ez.getIdtag();
						this.dataTempDao.deleteFileCodeProp(idstructure, idTag);
					}
				} else {//前台为传递数据过来
					//删除字段代码规则表
					this.dataTempDao.deleteZDXZDataOfTable(tableFlag,structureId);
					//删除字段代码-字段值规则表
					for(int i = 0; i < dbList.size(); i++) {
						EssZDXZCommon ez = dbList.get(i);
						int idstructure = ez.getIdstructure();
						int idTag = ez.getIdtag();
						this.dataTempDao.deleteFileCodeProp(idstructure, idTag);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean getDataOfDMZList(String structureId, String idTag) {
		List<EssZDXZCommon> list = this.dataTempDao.getDataOfDMZList(structureId, idTag);
		if(list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Map<String, String>> getFieldCodeProp(Map<String, String> map) {
		return this.dataTempDao.getFieldCodeProp(map);
	}

	@Override
	public boolean checkDMZPropIsHave(Map<String, String> map) {
		List<Map<String, String>> list = this.dataTempDao.checkDMZPropIsHave(map);
		if(list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean saveDMZPropData(Map<String, String> map) {
		try {
			this.dataTempDao.saveDMZPropData(map);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteDMZPropData(String idStr) {
		try {
			String[] ids = idStr.split(",");
			for(int i = 0; i < ids.length; i++) {
				String id = ids[i];
				this.dataTempDao.deleteDMZPropData(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<EssZDXZCommon> getBLDataList(Map<String, String> map) {
		return this.dataTempDao.getBLDataList(map);
	}

	@Override
	public boolean saveDataOfBL(List<Map<String, String>> list, String structureId) {
		try {
			this.dataTempDao.deleteDataOfBL(structureId);
			if(list != null && !list.isEmpty()) {
				for(int i = 0; i < list.size(); i++) {
					Map<String,String> map = list.get(i);
					this.dataTempDao.saveDataOfBL(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<EssZDXZCommon> getZHZDDataList(Map<String, String> map) {
		return this.dataTempDao.getZHZDDataList(map);
	}

	@Override
	public boolean saveDataOfZHZD(List<Map<String, String>> list, String structureId) {
		try {
			//获取原有数据
			Map<String,String> pMap = new HashMap<String, String>();
			pMap.put("structureId", structureId);
			pMap.put("isNotSystem", "1");
			List<EssZDXZCommon> commonLst = this.dataTempDao.getZHZDDataList(pMap);//原有的数据 存入内存中，供判断处理
			
			if(list==null||list.isEmpty()){
				//删除原有数据
				this.dataTempDao.deleteDataOfZHZD(structureId);
				this.dataTempDao.deleteZHZDPropData(structureId,null,null);
			}else{
				//删除原有数据
				this.dataTempDao.deleteDataOfZHZD(structureId);
				if(list != null && !list.isEmpty()) {
					//1.先判断是否有删除的数据
					for(int t = 0; commonLst!=null&&t < commonLst.size(); t++) {
						EssZDXZCommon ez = commonLst.get(t);
						int idTagNum = ez.getIdtag();
						boolean deleteFlag = true;
						for(int i = 0; i < list.size(); i++) {
							Map<String,String> map = list.get(i);
							String idTag = map.get("idTag");
							if(idTag.equals(idTagNum+"")){
								deleteFlag = false;//不需要删除
								break;
							}
						}
						if(deleteFlag){			
							this.dataTempDao.deleteZHZDPropData(structureId,idTagNum+"",null);
						}
					}
					//2.再处理新增和修改的数据
					for(int i = 0; i < list.size(); i++) {
						Map<String,String> map = list.get(i);
						String saveFlag = map.get("saveFlag");
						String idTag = map.get("idTag");
						int idTagInt = Integer.valueOf(idTag);
						if("nochange".equals(saveFlag)) {
							for(int t = 0; commonLst!=null&&t < commonLst.size(); t++) {
								EssZDXZCommon ez = commonLst.get(t);
								int idTagNum = ez.getIdtag();
								if(idTagNum == idTagInt) {
									String tagIds = ez.getTagIds();
									map.put("tagIds", tagIds);
									continue;
								}
							}
						}else if("update".equals(saveFlag)){
							//判断是否有删除的	
							for(int t = 0; commonLst!=null&&t < commonLst.size(); t++) {
								EssZDXZCommon ez = commonLst.get(t);
								int idTagNum = ez.getIdtag();
								if(idTagNum == idTagInt) {
									String tagIds_old = ez.getTagIds();
									String tagIds_new = map.get("tagIds");
									String[] oldarr = tagIds_old.split(",");
									for(int p=0;p<oldarr.length;p++){
										if(tagIds_new.indexOf(oldarr[p])<0){
											this.dataTempDao.deleteZHZDPropData(structureId,map.get("idTag"),oldarr[p].split("\\|")[0]);
										}
									}
								}
							}
						}
						this.dataTempDao.saveDataOfZHZD(map);
					}	
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, String>> getZHZDPropDataList(Map<String, String> map) {
		return this.dataTempDao.getZHZDPropDataList(map);
	}

	@Override
	public boolean checkZHZDPropIsHave(Map<String, String> map) {
		List<Object[]> list = this.dataTempDao.checkZHZDPropIsHave(map);
		if(list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteZHZDPropData(String idStr) {
		try {
			String[] ids = idStr.split(",");
			for(int i = 0; i < ids.length; i++) {
				String id = ids[i];
				this.dataTempDao.deleteZHZDPropData(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveZHZDPropData(Map<String, String> map) {
		try {
			this.dataTempDao.saveZHZDPropData(map);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public Map<String, String> getDataOfJD(String structureId) {
		return this.dataTempDao.getDataOfJD(structureId);
	}

	@Override
	public List<Map<String, String>> getCheckUpPropData(Map<String, String> map) {
		return this.dataTempDao.getCheckUpPropData(map);
	}

	@Override
	public boolean saveDataOfJD(Map<String, String> map) {
		try {
			String structureId = map.get("structureId");
			Map<String, String> flagMap = this.dataTempDao.getDataOfJD(structureId);
			if(flagMap != null && !flagMap.isEmpty()) {
				//update
				this.dataTempDao.updateDataOfJD(map);
			} else {
				//insert
				this.dataTempDao.insertDataOfJD(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean checkDataRepeat(Map<String, String> map) {
		List<Object[]> list = this.dataTempDao.checkDataRepeat(map);
		if(list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean insertDataOfJDKeyValue(Map<String, String> map) {
		try {
			this.dataTempDao.insertDataOfJDKeyValue(map);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateDataOfJDKeyValue(Map<String, String> map) {
		try {
			this.dataTempDao.updateDataOfJDKeyValue(map);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteTableDataOfJD(String ids) {
		try {
			String[] idArr = ids.split(",");
			for(int i = 0; i < idArr.length; i++) {
				String id = idArr[i];
				this.dataTempDao.deleteTableDataOfJD(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public List<EssTree> deleteTreeNodeList(Map<String, String> map, Integer pageSize, Integer pageIndex) {
		// 获取当前选中树节点下所有子节点id并删除
		return dataTempDao.deleteTreeNodeList(map,pageSize,pageIndex);
	}

	@Override
	public List<MrzPojo> searchMRZ(Map<String, String> map,Integer pageSize, Integer pageIndex) {
		return dataTempDao.searchMRZ(map,pageSize,pageIndex);
	}

	@Override
	public boolean updateMrz(String updateSql) {
		return dataTempDao.updateMrz(updateSql);
	}

	@Override
	public boolean deleteMrz(String deleteSql) {
		return dataTempDao.deleteMrz(deleteSql);
	}

	@Override
	public int findByIdCount(String sql) {
		// TODO 自动生成的方法存根
		return dataTempDao.findByIdCount(sql);
	}

}
