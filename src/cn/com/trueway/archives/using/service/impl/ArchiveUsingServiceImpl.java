package cn.com.trueway.archives.using.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.using.dao.ArchiveUsingDao;
import cn.com.trueway.archives.using.pojo.ArchiveMsg;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.pojo.Basicdata;
import cn.com.trueway.archives.using.pojo.Transferform;
import cn.com.trueway.archives.using.pojo.Transferstore;
import cn.com.trueway.archives.using.service.ArchiveUsingService;

@SuppressWarnings("unused")
public class ArchiveUsingServiceImpl implements  ArchiveUsingService{

	private ArchiveUsingDao archiveUsingDao;

	
	public ArchiveUsingDao getArchiveUsingDao() {
		return archiveUsingDao;
	}


	public void setArchiveUsingDao(ArchiveUsingDao archiveUsingDao) {
		this.archiveUsingDao = archiveUsingDao;
	}

	public List<Basicdata> queryDataList(Map<String,String> map){
		
		return archiveUsingDao.queryDataList(map);
	}

	@Override
	public List<ArchiveNode> queryNodeList(Map<String, String> map) {
		
		return archiveUsingDao.queryNodeList(map);
	}
	
	public List<Basicdata> queryTypeList(Map<String,String> map){
		
		return archiveUsingDao.queryTypeList(map);
	}
	
	public void addBasicData(Basicdata basicdata){
		
		archiveUsingDao.addBasicData(basicdata);
	}
	
	public void deiBasicData(String ids){
		
		archiveUsingDao.deiBasicData(ids);
	}
	
	public int getDataCount(Map<String,String> map){
		
		return archiveUsingDao.getDataCount(map);
	}
	
	public void addData(ArchiveNode archiveNode){
		
		archiveUsingDao.addData(archiveNode);
	}
	
	public int getCountByName(String colName,String tableName){
		
		return archiveUsingDao.getCountByName(colName,tableName);
	}
	
	public void delData(String ids){
		
		archiveUsingDao.delData(ids);
	}
	
	public void addfield(ArchiveNode archiveNode){
		
		archiveUsingDao.addfield(archiveNode);
	}
	
	public ArchiveNode getArchiveNodeById(String id){
		
		return archiveUsingDao.getArchiveNodeById(id);
	}
	
	public void upfield(String fielName,ArchiveNode archiveNode){
		
		 archiveUsingDao.upfield(fielName, archiveNode);
	}
	
	@Override
	public void upfieldLength(String fielLength, ArchiveNode archiveNode) {
		this.archiveUsingDao.upfieldLength(fielLength, archiveNode);
	}
	
	public void delfield(ArchiveNode archiveNode){
		
		archiveUsingDao.delfield(archiveNode);
	}
	
	public ArrayList<Basicdata> queryData(String vc_system){
		
		return archiveUsingDao.queryData(vc_system);
	}
	
	public void insertData(Map<String,String> map){
		
		archiveUsingDao.insertData(map);
	}
	
	public List<String[]> getValList(List<ArchiveNode> nodeList,Map<String,String> map){
		
		return archiveUsingDao.getValList(nodeList,map);
	}
	
	public int getValCount(List<ArchiveNode> nodeList,Map<String,String> map){
		
		return archiveUsingDao.getValCount(nodeList,map);
	}
	
	public void delByIds(Map<String,String> map){
		
		 archiveUsingDao.delByIds(map);
	}
	
	public String getVals(Map<String,String> map){
		
		return archiveUsingDao.getVals(map);
	}
	
	public void updateData(Map<String,String> map){
		
		archiveUsingDao.updateData(map);
	}
	
	public String getIdByCondition(Map<String,String> map){
		
		return archiveUsingDao.getIdByCondition(map);
	}
	
	public List<String[]> getStoreVal(Map<String,String> map){
		
		return archiveUsingDao.getStoreVal(map);
	}
	
	public List<String[]> getStoreVal(List<ArchiveNode> nodeList,Map<String,String> map){
		
		return archiveUsingDao.getStoreVal(nodeList,map);
	}


	@Override
	public void addToJYK(String sql) {
		archiveUsingDao.addToJYK(sql);
	}


	@Override
	public void updateStore(String sql) {
		archiveUsingDao.updateStore(sql);
	}


	@Override
	public List<Object[]> getFjList(Map<String, String> map) {
		return archiveUsingDao.getFjList(map);
	}


	@Override
	public List<Map<String, Object>> queryFormlist(Map<String, String> map,
			List<ArchiveNode> nodeList) {
		return archiveUsingDao.queryFormlist(map,nodeList);
	}


	@Override
	public List<Object[]> getFJById(String id) {
		return archiveUsingDao.getFJById(id);
	}


	@Override
	public void updateArchiveMsg(ArchiveMsg msg) {
		archiveUsingDao.updateArchiveMsg(msg);
	}


	@Override
	public List<ArchiveMsg> getArchiveMsgList(Map<String, String> map,Integer pageIndex,Integer pageSize) {
		return archiveUsingDao.getArchiveMsgList(map,pageIndex,pageSize);
	}


	@Override
	public int getTransferformCount(Map<String, String> map) {
		return archiveUsingDao.getTransferformCount(map);
	}


	@Override
	public List<Transferform> getTransferformList(Map<String, String> map,
			Integer pageIndex, Integer pageSize) {
		return archiveUsingDao.getTransferformList(map,pageIndex,pageSize);
	}


	@Override
	public void updateTransferForm(Transferform tf) {
		archiveUsingDao.updateTransferForm(tf);
	}


	@Override
	public void addToDJK(Transferstore ts) {
		archiveUsingDao.addToDJK(ts);
	}


	@Override
	public List<Transferstore> getTransferstoreList(Map<String, String> map,
			Integer pageIndex, Integer pageSize) {
		return archiveUsingDao.getTransferstoreList(map,pageIndex,pageSize);
	}


	@Override
	public List<Object[]> getChecDJKList(Map<String, String> map) {
		return archiveUsingDao.getChecDJKList(map);
	}


	@Override
	public String getFormStutsById(String sql) {
		return archiveUsingDao.getFormStutsById(sql);
	}

}
