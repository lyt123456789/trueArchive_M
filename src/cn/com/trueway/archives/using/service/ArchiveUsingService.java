package cn.com.trueway.archives.using.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
















import cn.com.trueway.archives.using.pojo.ArchiveMsg;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.pojo.Basicdata;
import cn.com.trueway.archives.using.pojo.Transferform;
import cn.com.trueway.archives.using.pojo.Transferstore;

public interface ArchiveUsingService {

	List<Basicdata> queryDataList(Map<String,String> map);
	
	List<ArchiveNode> queryNodeList(Map<String,String> map);
	
	List<Basicdata> queryTypeList(Map<String,String> map);
	
	void addBasicData(Basicdata basicdata);
	
	void deiBasicData(String ids);
	
	int getDataCount(Map<String,String> map);
	
	void addData(ArchiveNode archiveNode);
	
	int getCountByName(String colName,String tableName);
	
	void delData(String ids);
	
	void addfield(ArchiveNode archiveNode);
	
	ArchiveNode getArchiveNodeById(String id);
	
	void upfield(String fielName,ArchiveNode archiveNode);
	
	public void upfieldLength(String fielLength, ArchiveNode archiveNode);
	
	void delfield(ArchiveNode archiveNode);
	
	ArrayList<Basicdata> queryData(String vc_system);
	
	void insertData(Map<String,String> map);
	
	List<String[]> getValList(List<ArchiveNode> nodeList,Map<String,String> map);
	
	int getValCount(List<ArchiveNode> nodeList,Map<String,String> map);
	
	void delByIds(Map<String,String> map);
	
	String getVals(Map<String,String> map);
	
	void updateData(Map<String,String> map);
	
	String getIdByCondition(Map<String,String> map);
	
	List<String[]> getStoreVal(Map<String,String> map);
	
	List<String[]> getStoreVal(List<ArchiveNode> nodeList,Map<String,String> map);


	void addToJYK(String string);

	void updateStore(String sql);

	List<Object[]> getFjList(Map<String, String> map);

	List<Map<String, Object>> queryFormlist(Map<String, String> map,
			List<ArchiveNode> nodeList);

	List<Object[]> getFJById(String id);

	void updateArchiveMsg(ArchiveMsg msg);

	List<ArchiveMsg> getArchiveMsgList(Map<String, String> map ,Integer pageIndex,Integer pageSize);

	int getTransferformCount(Map<String, String> map);

	List<Transferform> getTransferformList(Map<String, String> map,
			Integer pageIndex, Integer pageSize);

	void updateTransferForm(Transferform tf);

	void addToDJK(Transferstore ts);

	List<Transferstore> getTransferstoreList(Map<String, String> map,
			Integer pageIndex, Integer pageSize);

	List<Object[]> getChecDJKList(Map<String, String> map);
	
	public String getFormStutsById(String sql);

}
