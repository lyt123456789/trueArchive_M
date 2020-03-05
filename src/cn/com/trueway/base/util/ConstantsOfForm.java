package cn.com.trueway.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Sw;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;

/**
 * 
 * @ClassName: ConstantsOfForm 
 * @Description: 获取表单默认值
 * @author zj
 * @date 2014-3-15 上午10:34:12
 */
public class ConstantsOfForm extends BaseAction{
	
	private static final long serialVersionUID = -2089254747423732436L;
	
	public ConstantsOfForm() {
	}

	/**
	 * 
	 * @Title: getLoginName 
	 * @Description: 获取表单默认当前登录人
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public String getLoginName(String userId,EmployeeService employeeService){
		Employee employee=null;
		try {
			employee = employeeService.findEmployeeById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(employee != null){
			return employee.getEmployeeName();
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getLoginDepName 
	 * @Description: 获取当前登录者部门
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public String getLoginDepName(String userId,EmployeeService employeeService,DepartmentService departmentService){
		Employee employee=null;
		try {
			employee = employeeService.findEmployeeById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Department department=null;
		try {
			//人员当前机构
			department = departmentService.queryDepartmentById(employee.getDepartmentGuid());
			/*String zfbDepId  = SystemParamConfigUtil.getParamValueByParam("zfbDepId");
			//当前人员处于政府办一层子机构下,获取政府办的dep
			if(department!=null && department.getSuperiorGuid().equals(zfbDepId)){
				department = departmentService.queryDepartmentById(zfbDepId);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(department != null){
			return department.getDepartmentName();
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: getNowTime 
	 * @Description: 获取当前时间
	 * @param @param formateType 时间格式化格式："yyyy-MM-dd HH:mm:ss" "yyyy年MM月dd日 HH时mm分ss秒"
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public String getNowTime(int formateType){
		Date date = new Date();
		String type = "";
		switch(formateType){
		case 0: 
			type = "yyyy-MM-dd";
			break;
		case 1: 
			type = "yyyy-MM-dd HH:mm:ss";
			break;
		case 2: 
			type = "yyyy年MM月dd日";
			break;
		case 3: 
			type = "yyyy年MM月dd日 HH时mm分ss秒";
			break;
		case 4: 
			type = "yyyy/MM/dd";
			break;
		case 5: 
			type = "yyyy-MM-dd HH:mm";
			break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		return sdf.format(date);
	}
	
	public String getSwsj(String instanceId,TableInfoService tableInfoService){
		
		Sw sw =  tableInfoService.getSwByInstanceId(instanceId);
		if(sw != null){
			Date cwsj = sw.getSwsj();
			if(cwsj != null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(cwsj);
			}else{
				return "";
			}
			
		} 
		return "";
	}

	public String getLoginIdAndName(String userId,
			EmployeeService employeeService) {
		Employee employee=null;
		try {
			employee = employeeService.findEmployeeById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(employee != null){
			return employee.getEmployeeGuid()+"*"+employee.getEmployeeName();
		}else{
			return null;
		}
	}

	public String getLoginDepId(String userId, EmployeeService employeeService,
			DepartmentService departmentService) {
		Employee employee=null;
		try {
			employee = employeeService.findEmployeeById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Department department=null;
		try {
			//人员当前机构
			department = departmentService.queryDepartmentById(employee.getDepartmentGuid());
			String zfbDepId  = "";
			//当前人员处于政府办一层子机构下,获取政府办的dep
			if(department!=null && department.getSuperiorGuid().equals(zfbDepId)){
				department = departmentService.queryDepartmentById(zfbDepId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(department != null){
			return department.getDepartmentGuid();
		}else{
			return null;
		}
	}
	
	public String getUnitName(String userId, EmployeeService employeeService, DepartmentService departmentService){
	    Employee emp=null;
		try{
		    emp = employeeService.findEmployeeById(userId);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    String unitName = "";
	    if(emp != null){
		String deptId = emp.getDepartmentGuid();
		String department_rootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		String superDeptId = "";
		if(CommonUtil.stringNotNULL(deptId) && CommonUtil.stringNotNULL(department_rootId)){
		    while(!department_rootId.equals(superDeptId)){
			Department dept = departmentService.findDepartmentById(deptId);
			if(dept != null){
			    superDeptId = dept.getSuperiorGuid();
			    deptId = superDeptId;
			    unitName = dept.getDepartmentName();
			}
		    }
		}
	    }
	   return unitName; 
	}
	
}
