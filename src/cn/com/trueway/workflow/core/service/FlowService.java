package cn.com.trueway.workflow.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;

/**
 * 
 * 描述：处理 文件 转成流的 Service
 * 作者：Yuxl
 * 创建时间：2014-6-30 下午4:49:48
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public interface FlowService {

	/**
	 * 
	 * 描述：根据参数生成 sql 语句
	 * @param tableName update 的 表名
	 * @param keyValueSet  更新的字段集合
	 * @param whereKey  where 查询字段
	 * @param whereValue where 字段 值
	 * 作者:Yuxl
	 * 创建时间:2014-6-30 下午4:54:42
	 */
	public void geneSql(String tableName , HashMap<String, String> keyValueSet, String whereKey , String whereValue);

	public BLOB getBlob(String tablename, String fieldname,String key,String value);
	
}
