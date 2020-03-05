package cn.com.trueway.sso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.SpringContextUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.dao.DepartmentDao;
import cn.com.trueway.workflow.set.dao.EmployeeDao;
import cn.com.trueway.workflow.set.service.DepartmentService;

import com.trueway.client.validation.Assertion;

import egov.appservice.sso.BaseValidationFilter;

@SuppressWarnings("unchecked")
public class FileValidationFilter extends BaseValidationFilter {


	protected void onSuccessfulValidation(final HttpServletRequest request,
			final HttpServletResponse response, final Assertion assertion) {
		
		if(!("1").equals(Constant.LICENSE_CHECK_PASSED)){
			String url = "/licenseerror.html";
			try {
				request.getRequestDispatcher(url).forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else{
			Map attributes = assertion.getPrincipal().getAttributes();
			String user_id = (String) attributes.get("riseGuid");
			
			//根据userid获取用户信息
			EmployeeDao employeeDao = (EmployeeDao) SpringContextUtil.getBeanValue1("employeeDao");
			Employee employee=null;
			try {
				employee = employeeDao.queryEmployeeById(user_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getSession().setAttribute(MyConstants.loginEmployee, employee);
			
			//根据userid获取用户所属大部门信息
			DepartmentDao departmentDao = (DepartmentDao) SpringContextUtil.getBeanValue1("departmentDao");
			Department department=null;
			try {
				department = departmentDao.queryDepartmentById(employee.getDepartmentGuid());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//根据机构逆推
			boolean flag = true ;
			String depids = "";
			String depid = department.getDepartmentGuid();
			Department depart=null;
			while(flag){
				depart = departmentDao.queryDepartmentById(depid);
				if(depart!=null){
					if(StringUtils.isNotBlank(depart.getSuperiorGuid()) && depid.equals(depart.getSuperiorGuid())){
						flag = false;
					}else{
						depids+= "'"+depid +"',";
						depid = depart.getSuperiorGuid();
						if(depid!=null && depid.equals("1")){
							flag = false;
						}
					}
				}
			}
			if(depids!=null && depids.length()>0){
				depids = depids.substring(0,depids.length()-1);
			}
			request.getSession().setAttribute(MyConstants.DEPARMENT_ID, depids);
			
			//初始化人员大部门信息
			String department_shortdn = department.getDepartmentShortdn();
			String superior_guid = department.getSuperiorGuid();
			//查找最大的部门
			Department departmentBig = departmentDao.queryDepartmentById(superior_guid);
			if("1".equals(superior_guid) || departmentBig == null ){
				List<String> depIds = new ArrayList<String>();
				depIds.add(department.getDepartmentGuid());
				request.getSession().setAttribute(MyConstants.DEPARMENT_IDS, depIds);
				request.getSession().setAttribute(MyConstants.DEPARMENT_NAME, department.getDepartmentName());
			}else{
				Department dept = departmentDao.queryDepartmentById(superior_guid);
				String deptName = dept.getDepartmentName();
				if (!(department_shortdn == null || department_shortdn.length() < 1)) {
					if (department_shortdn.split(",").length == 1) {
						superior_guid = department.getDepartmentGuid();
						Department deptbak = departmentDao.queryDepartmentById(superior_guid);
						deptName = deptbak.getDepartmentName();
					}	
				}
				List<String> depIds = new ArrayList<String>();
				Collection<Department> depts =	departmentDao.queryDepartments(department.getDepartmentGuid());
				if(depts  == null||depts.size() ==0){
					depIds.add(superior_guid);
				}else{
					depIds.add(department.getDepartmentGuid());
				}

				request.getSession().setAttribute(MyConstants.DEPARMENT_IDS, depIds);
				request.getSession().setAttribute(MyConstants.DEPARMENT_NAME, deptName);
				
			}
			request.getSession().setAttribute(MyConstants.bigDepartmentId, superior_guid);//存储大部门信息
			request.getSession().setAttribute("ssologin", "1");//用于区分普通登录和单点登录跳转
		}
	}
}
