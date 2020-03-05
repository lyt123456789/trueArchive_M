package cn.com.trueway.mobileInterface.spacial;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.mobileInterface.spacial.bean.SpecialInfo;
import cn.com.trueway.workflow.core.pojo.Employee;

public class SpecialDao extends BaseDao {

	/**
	 * 添加专项办数据
	 * @param info
	 * @param instanceId
	 * @param formId
	 * @param workFlowId
	 * @param processId
	 * @param zxbdw
	 */
	@Transactional
	public void addSpecialInfo(SpecialInfo info, String instanceId,
			String formId, String workFlowId, String processId, String zxbdw) {
//		String tableName = "T_WF_OFFICE_NWGJ_TZCG";  //交办单流程
		StringBuffer sql = new StringBuffer();
		sql.append("insert into "+getTableName(zxbdw)+" ");
		sql.append(" (INSTANCEID,FORMID,WORKFLOWID,PROCESSID,");
		sql.append(" jbdw,didian,flmc ,fltiao,flkuan,jbsxms ,zgyq ,zgyqsj,lxr ,lxdh ,sj,ly");
		sql.append(")");
		sql.append(" values(");
		sql.append("'").append(instanceId).append("',");
		sql.append("'").append(formId).append("',");
		sql.append("'").append(workFlowId).append("',");
		sql.append("'").append(processId).append("',");
		sql.append("'").append(info.getJbdw()).append("',");
		sql.append("'").append(info.getDd()).append("',");
		sql.append("'").append(info.getFlfg()).append("',");
		sql.append("'").append("").append("',");
		sql.append("'").append("").append("',");
		sql.append("'").append(info.getJbsxms()).append("',");
		sql.append("'").append(info.getZgyq()).append("',");
		sql.append("'").append(info.getZgsj()).append("',");
		sql.append("'").append(info.getLxr()).append("',");
		sql.append("'").append(info.getLxdh()).append("',");
		sql.append("'").append(info.getSj()).append("',");
		sql.append("'").append(info.getLy()).append("'");
		sql.append(")");
		
		this.getEm().createNativeQuery(sql.toString()).executeUpdate();
		
	}

	public String getTableName(String zxbdw){
		String tableName = "";
		if("0".equals(zxbdw)){
			tableName = "T_WF_OFFICE_HZBJB_TZCG";
		}
		if("1".equals(zxbdw)){
			tableName = "T_WF_OFFICE_ZTBJB_TZCG";
		}
		if("2".equals(zxbdw)){
			tableName = "T_WF_OFFICE_FZBJBD";
		}
		if("3".equals(zxbdw)){
			tableName = "T_WF_OFFICE_CGBJBD";
		}
		if("4".equals(zxbdw)){
			tableName = "T_WF_OFFICE_GGJB_TZCG";
		}
		if("5".equals(zxbdw)){
			tableName = "T_WF_OFFICE_TZCG_GWBJB";
		}
		if("6".equals(zxbdw)){
			tableName = "T_WF_OFFICE_NHBJBD";
		}
		return tableName;
	}

	/**
	 * 添加流程（）
	 * @param workFlowId
	 * @param instanceId
	 * @param itemId
	 * @param nodeId
	 * @param formId
	 * @param processId
	 * @param title
	 * @param empId
	 */
	@Transactional
	public void addProcess(String workFlowId, String instanceId, String itemId,
			String nodeId, String formId, String processId, String title,
			String empId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = sdf.format(new Date());
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into t_wf_process ");
		sql.append("(WF_PROCESS_UID,WF_INSTANCE_UID,WF_NODE_UID,USER_UID,APPLY_TIME,FINSH_TIME,FROMUSERID,OWNER,IS_OVER," +
				"PROCESS_TITLE,IS_END,IS_MASTER,WF_UID,IS_SHOW,STEP_INDEX,WF_ITEM_UID,WF_FORM_ID,IS_DUPLICATE,FROMNODEID,TONODEID,STATUS)");
		sql.append(" values(");
		sql.append("'").append(processId).append("',");
		sql.append("'").append(instanceId).append("',");
		sql.append("'").append(nodeId).append("',");
		sql.append("'").append(empId).append("',");
		sql.append("to_date('").append(nowDate).append("','yyyy-mm-dd hh24:mi:ss'),");
		sql.append("to_date('").append(nowDate).append("','yyyy-mm-dd hh24:mi:ss'),");
		sql.append("'").append(empId).append("',");
		sql.append("'").append(empId).append("',");
		sql.append("'").append("NOT_OVER").append("',");
		sql.append("'").append(title).append("',");
		sql.append("0,");
		sql.append("1,");
		sql.append("'").append(workFlowId).append("',");
		sql.append("1,");
		sql.append("1,");
		sql.append("'").append(itemId).append("',");
		sql.append("'").append("").append("',");
		sql.append("'").append("").append("',");
		sql.append("'").append("").append("',");
		sql.append("'").append("").append("',");
		sql.append("'").append("0").append("'");
		sql.append(")");
		
		this.getEm().createNativeQuery(sql.toString()).executeUpdate();
		
	}

	public String findUserName(String userId) {
		String hql = "from Employee where employeeGuid='"+userId+"'";
		List<Employee> emps = this.getEm().createQuery(hql).getResultList();
		if(emps.size()>0){
			return emps.get(0).getEmployeeName();
		}
		return "";
	}

}
