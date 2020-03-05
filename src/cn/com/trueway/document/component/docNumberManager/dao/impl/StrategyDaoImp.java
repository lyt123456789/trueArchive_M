package cn.com.trueway.document.component.docNumberManager.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;

public class StrategyDaoImp extends BaseDao implements cn.com.trueway.document.component.docNumberManager.dao.StrategyDao{
	/* (non-Javadoc)
	 * @see cn.com.trueway.wh.dao.impl.StrategyDao#getStrategyList()
	 */
	@SuppressWarnings("unchecked")
	public List<DocNumberStrategy> getStrategyList(){
		String sql = "select * from DCONUMBER_STRATEGY";
		List<DocNumberStrategy> list = (List<DocNumberStrategy>)getEm().createNativeQuery(sql).getResultList();
		return list;
	}
	
	/* (non-Javadoc)
	 * @see cn.com.trueway.wh.dao.impl.StrategyDao#getSingnStrategy(java.lang.String)
	 */
	public DocNumberStrategy getSingnStrategy(String id){
		String sql = "select * from DOCNUMBER_STRATEGY t where t.strategyid=:id";
		Query q = getEm().createNativeQuery(sql);
		q.setParameter("id", id);
		Object[] obj = (Object[])q.getSingleResult();
		DocNumberStrategy dn = new DocNumberStrategy();
		dn.setStrategyId(obj[0].toString());
		dn.setContent(obj[1]==null?"":obj[1].toString());
		dn.setType(obj[2]==null?"":obj[2].toString());
		dn.setDescription(obj[3]==null?"":obj[3].toString());
		return dn;
	}
	
	public void updateStrategy(DocNumberStrategy doc){
	 	getEm().merge(doc);
	}
}
