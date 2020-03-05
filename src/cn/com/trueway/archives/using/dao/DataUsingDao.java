package cn.com.trueway.archives.using.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.archives.templates.pojo.EssTree;

public interface DataUsingDao {

	List<EssTree> getTreeByMap(Map<String, String> map, Integer pageSize, Integer pageIndex);

}
