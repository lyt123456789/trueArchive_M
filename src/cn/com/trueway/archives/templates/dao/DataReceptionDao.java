package cn.com.trueway.archives.templates.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;



public interface DataReceptionDao {

	int getStructureDataCount(Map<String, Object> map);

	List<Map<String, Object>> getStructureDataList(Map<String, Object> map,
			Integer pageSize, Integer pageIndex);

	List<Map<String, Object>> getStructureData(Map<String, Object> map);

	
}
