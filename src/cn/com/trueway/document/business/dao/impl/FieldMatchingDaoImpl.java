package cn.com.trueway.document.business.dao.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.list.TreeList;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.document.business.dao.FieldMatchingDao;
import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.DocxgFieldMap;

public class FieldMatchingDaoImpl extends BaseDao implements FieldMatchingDao{

	@Override
	public void addDocxgFieldMap(DocxgFieldMap docxgField) {
		this.getEm().persist(docxgField);
	}

	@Override
	public List<DocxgFieldMap> getDocxgFieldMapList(String itemId) {
		String hql = " from DocxgFieldMap t where t.itemId='"+itemId+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public void deleteDocxgFieldMapByItemId(String itemId) {
		String sql = "delete from DOCEXCHANGE_DOCXGFIELDMAP t where t.itemId = '"+itemId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void saveForm(String tableName, Map<String, Object> map) throws Exception{
		//遍历list
		String columnName = "";
		String value = "";
		List<Object> tagValueList = new TreeList();
		for(String key : map.keySet()){
			columnName += key+","; 
			value += "'"+map.get(key)+"',"; 
		}
		columnName = columnName.substring(0,columnName.length()-1);
		value = value.substring(0,value.length()-1);
		String sql = "insert into "+tableName+"("+columnName+")"+" values ("+value+")";
		super.getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public List<DepRelationShip> getDepRelationShipListByDepId(String depId) {
		String hql = "from DepRelationShip t where t.gtj_depId in (select tt.id from DocXgDepartment tt where tt.deptGuid in ("+depId+") )";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public void deleteDepShipById(String id) {
		String sql = " delete from DOCEXCHANGE_RELATIONSHIP t where t.id='"+id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void deleteTableInfo(String instanceId, String tableName) {
		String sql = "delete from "+tableName+" t where t.instanceId = '"+instanceId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
		
	}

}
