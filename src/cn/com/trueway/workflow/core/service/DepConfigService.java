package cn.com.trueway.workflow.core.service;

import java.util.List;

import net.sf.json.JSONObject;
import cn.com.trueway.document.business.model.DocXgDepartment;

public interface DepConfigService {
	
	/**
	 * 根据上级部门id获取部门list
	 * 描述：TODO 对此方法进行描述
	 * @param superior_guid
	 * @return List<DocXgDepartment>
	 * 作者:季振华
	 * 创建时间:2017-8-16 上午10:41:42
	 */
	List<DocXgDepartment> getDepsBySuperId(String superior_guid);
	
	/**
	 * 新加父部门
	 * 描述：TODO 对此方法进行描述
	 * @param docXgDepartment void
	 * 作者:季振华
	 * 创建时间:2017-8-16 上午11:47:51
	 */
	JSONObject addDep_F(DocXgDepartment docXgDepartment);
	
	/**
	 * 根据id获取部门
	 * 描述：TODO 对此方法进行描述
	 * @param id
	 * @return DocXgDepartment
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午2:23:05
	 */
	DocXgDepartment getDepById(String id);
	
	/**
	 * 删除父部门及其下的子部门
	 * 描述：TODO 对此方法进行描述
	 * @param ids_arr
	 * @return JSONObject
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午2:51:06
	 */
	JSONObject delDep_F(String[] ids_arr);
	
	/**
	 * 删除子部门
	 * 描述：TODO 对此方法进行描述
	 * @param ids_arr
	 * @return JSONObject
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午2:51:06
	 */
	JSONObject delDep_C(String[] ids_arr);
	
	/**
	 * 根据部门id查询
	 * 描述：TODO 对此方法进行描述
	 * @param depId
	 * @return DocXgDepartment
	 * 作者:季振华
	 * 创建时间:2017-8-16 下午5:02:29
	 */
	DocXgDepartment getDepByDepId(String depId);
	
}
