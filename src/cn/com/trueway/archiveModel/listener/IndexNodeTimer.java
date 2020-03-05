package cn.com.trueway.archiveModel.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archives.manage.service.FullTextManageService;
import cn.com.trueway.archives.manage.service.RoleManageService;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.ldbook.util.RemoteLogin;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.action.MsgSendAction;
import cn.com.trueway.workflow.core.service.MsgSendService;
/**
 * 
 * 描述：短消息发送task实现类
 * 作者：蔡亚军
 * 创建时间：2015-3-16 上午11:19:17
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class IndexNodeTimer extends TimerTask{
	
	public static ApplicationContext ctx = null;
	public static FullTextManageService fullTextManageService;
	public static RoleManageService roleManageService;
	public static ArchiveUsingService archiveUsingService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void run() {
		try {
			if(IndexNodeTimer.ctx==null){
				IndexNodeTimer.ctx = new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/common/*.xml","classpath*:spring/archive/manage/*.xml","classpath*:spring/archive/using/*.xml"});
			}
			if(fullTextManageService == null){
				fullTextManageService = (FullTextManageService) IndexNodeTimer.ctx.getBean("fullTextManageService");
			}
			if(roleManageService == null){
				roleManageService = (RoleManageService) IndexNodeTimer.ctx.getBean("roleManageService");
			}
			if(archiveUsingService == null){
				archiveUsingService = (ArchiveUsingService) IndexNodeTimer.ctx.getBean("archiveUsingService");
			}
			
			//获取所有底层树节点
			Map<String,String> map = new HashMap<String, String>();
			map.put("conditionSql", " and (t.id_structure <>0 and t.id_structure <>-1) ");
			List<EssTree> data = roleManageService.getModelTreeByMap(map);
			for(int i=0;i<data.size();i++){
				String treeId = data.get(i).getId()+"";
				map.clear();
				map.put("treeNodes1", "'"+treeId+"'");
				List<Object[]> indexList = fullTextManageService.getFullTextIndexList(map,null,null);
				if(indexList==null||indexList.size()==0){//未找到
					map.clear();
					map.put("type", "father");//father 往上级递归   son 往下级递归
					map.put("treeId", treeId);
					List<EssTree> fathertreelist = fullTextManageService.getTreeNodesList(map);
					Collections.reverse(fathertreelist); // 倒序排列 
					String indexPathName="";
					for(int j=0;j<fathertreelist.size();j++){
						indexPathName+="/"+fathertreelist.get(j).getTitle();
					}
					String sql = " insert into t_archive_fulltextindex (id,treenode,index_path_name,index_path) "
							+ "values('"+UuidGenerator.generate36UUID()+"','"+treeId+"','"+indexPathName+"',null)";
					archiveUsingService.updateStore(sql);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
}