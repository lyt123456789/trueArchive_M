package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.document.business.model.DocXgDepartment;

public interface DepConfigDao {
	
	List<DocXgDepartment> getDepsBySuperId(String superior_guid);
	
	void addDep_F(DocXgDepartment docXgDepartment) throws Exception;
	
	DocXgDepartment getDepById(String id);
	
	void delDep_F(String[] ids_arr) throws Exception;
	
	void delDep_C(String[] ids_arr) throws Exception;
	
	DocXgDepartment getDepByDepId(String depId);
	
}
