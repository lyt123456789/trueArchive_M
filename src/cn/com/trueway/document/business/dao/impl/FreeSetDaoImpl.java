package cn.com.trueway.document.business.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.document.business.dao.FreeSetDao;
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

public class FreeSetDaoImpl extends BaseDao implements FreeSetDao{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	//通用查询方法
	public List<ItemSelect> getResult(Map col) {
		String hql = "from ItemSelect t where 1=1";
		Iterator<String> iterator = col.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			hql = hql + " and "+ key + "='" + (String)col.get(key) + "'";
		}
		List<ItemSelect>  select = getEm().createQuery(hql).getResultList();
		return select;

	}

	@Override
	public void updateItemSelect(ItemSelect select) {
		getEm().merge(select);	
	}

	@Override
	public void addItemSelect(ItemSelect select) {	
		getEm().persist(select);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getResultBySql(String sql) {
		return getEm().createNativeQuery(sql).getResultList();
	}

	

}
