package cn.com.trueway.archives.using.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.using.dao.DataUsingDao;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;

public class DataUsingDaoImpl extends BaseDao implements DataUsingDao{

	@Override
	public List<EssTree> getTreeByMap(Map<String, String> map,Integer pageSize, Integer pageIndex) {
		String hql = " from EssTree where 1=1 ";
		String business =  map.get("business");
		if(CommonUtil.stringNotNULL(business)){
			hql += " and idBusiness = "+"'"+business+"'";
		}
		String id =  map.get("id");
		if(CommonUtil.stringNotNULL(id)){
			hql += " and id = "+id;
		}
		String parentId =  map.get("parentId");
		if(CommonUtil.stringNotNULL(parentId)){
			hql += " and idParent = "+parentId;
		}
		String conditionSql =  map.get("conditionSql");
		if(CommonUtil.stringNotNULL(parentId)){
			hql += conditionSql;
		}
		hql += "   order by esOrder ";
		Query query = this.getEm().createQuery(hql);
		if(pageSize!=null&&pageIndex!=null){
			query.setFirstResult(pageIndex);// 从哪条记录开始
			query.setMaxResults(pageSize);// 每页显示的最大记录数
		}
		return query.getResultList();
	}

}
