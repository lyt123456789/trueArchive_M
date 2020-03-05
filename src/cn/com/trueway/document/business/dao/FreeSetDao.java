package cn.com.trueway.document.business.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.document.business.model.ItemSelect;

/**
 * 
 * 描述：自定义查询<br>
 * 作者：张灵<br>
 * 创建时间：2016-1-27 下午05:12:18<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */

public interface FreeSetDao {

	@SuppressWarnings("rawtypes")
	public List<ItemSelect> getResult(Map col);

	public void updateItemSelect(ItemSelect select);

	public void addItemSelect(ItemSelect select);

	public List<Object[]> getResultBySql(String sql);
	


}
