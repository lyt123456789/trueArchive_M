package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.workflow.core.dao.DepConfigDao;
import cn.com.trueway.workflow.core.service.DepConfigService;

public class DepConfigServiceImpl implements DepConfigService{
	
	private DepConfigDao  depConfigDao;
	
	public DepConfigDao getDepConfigDao() {
		return depConfigDao;
	}

	public void setDepConfigDao(DepConfigDao depConfigDao) {
		this.depConfigDao = depConfigDao;
	}

	@Override
	public List<DocXgDepartment> getDepsBySuperId(String superior_guid){
		return depConfigDao.getDepsBySuperId(superior_guid);
	}
	
	@Override
	public JSONObject addDep_F(DocXgDepartment docXgDepartment) {
		JSONObject result = new JSONObject();
		try {
			depConfigDao.addDep_F(docXgDepartment);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public DocXgDepartment getDepById(String id){
		return depConfigDao.getDepById(id);
	}

	@Override
	public JSONObject delDep_F(String[] ids_arr){
		JSONObject result = new JSONObject();
		try {
			depConfigDao.delDep_F(ids_arr);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public JSONObject delDep_C(String[] ids_arr){
		JSONObject result = new JSONObject();
		try {
			depConfigDao.delDep_C(ids_arr);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public DocXgDepartment getDepByDepId(String depId){
		return depConfigDao.getDepByDepId(depId);
	}

}
