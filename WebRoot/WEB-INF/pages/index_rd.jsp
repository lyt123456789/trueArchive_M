<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
 		<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/headerindex.jsp"%>
		 <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
		<script type="text/javascript" src="${ctx }/flow/js/classMessage.js" defer="defer"></script>
		<script type="text/javascript" src="${ctx }/flow/js/classMessage_consult.js" defer="defer"></script>
		<script src="${ctx}/tabInLayer/plugins/layer/layer.js" type="text/javascript"></script>
		<script src="${ctx}/tabInLayer/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${ctx}/tabInLayer/js/bootstrap-addtabs.js" type="text/javascript"></script>
		<script type="text/javascript">
$(function(){
	DWZ.init("${ctx}/dwz.frag.xml", {
		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:true,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${ctx}/dwz/themes"}); // themeBase 相对于index页面的主题base路径 
		}
	});  
});
</script>
    <style>
    .layui-layer {
	    background-color:#eee;
    }   
    .layui-layer-content {
        overflow-y:hidden!important;
    } 
    </style>
	</head>
    <form action="${ctx}/form_loginout.do" id="form1" method="post" target="_self"></form>
	<body scroll="no" id="bodyid">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#">标志</a> 
				<ul class="nav">
					<li ><a href="#">${loginEmployee.employeeName }</a></li>
					<!-- <li><a href="设置" target="_blank">设置</a></li> -->
					<li><a href="javascript:loginout();" >退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
					
				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
							<h2><span>Folder</span>档案利用系统</h2>
						</div>
						<div class="accordionContent">
							<ul class="tree treeFolder">
								<li><a id="" href="${ctx }/using_showUsingForm.do?vc_table=1" external="true" target="navTab" rel="showNode">借阅单</a></li>
								<li><a id="" href="${ctx }/using_showLendingList.do" external="true" target="navTab" rel="showData">借阅管理列表</a></li>
								<li><a id="" href="${ctx }/using_toshowNode.do?vc_table=1" external="true" target="navTab" rel="showNode">借阅单管理</a></li>
								<li><a id="" href="${ctx }/using_toshowNode.do?vc_table=2" external="true" target="navTab" rel="showNode">借阅库管理</a></li>
								<li><a id="" href="${ctx }/using_toShowData.do" external="true" target="navTab" rel="showData">数据字典维护</a></li>
							</ul>
						</div>
					<div class="accordionHeader">
						<h2 id="workflowname" workflowid=""><span>Folder</span>请创建或选择工作流！</h2>
					</div> 
					<div id="tmconfj" class="accordionContent" style="position:relative;overflow:hidden;"> 
					<div id="tmcon" onclick="alert('请先双击选择右边工作流名称！');" style="z-index:1000;position:absolute;filter:alpha(opacity=30);opacity:0.3; height:1000px; width:1000px; background:#ccc;top:0px;left:0px;"></div>
						<ul class="tree treeFolder">
							<li><a id="workflowimg" herf="#" onclick="showDialog(this)">流程图</a> 
							</li>
							<li><a>数据库表</a> 
								<ul> 
									<li><a href="${ctx}/table_getTableList.do" external="true" target="navTab" rel="mainForms">数据表</a></li>
									<li><a href="${ctx}/dictionary_getDictionaryList.do" external="true" target="navTab" rel="dictionaryForms">字典表</a></li>
									<li><a href="${ctx}/field_getFieldList.do" external="true" target="navTab" rel="publicfield">基础字段</a></li>
									<li><a href="${ctx}/procedure_getList.do" external="true" target="navTab" rel="FormsList">存储过程</a></li>
								</ul>
							</li>
							<li><a>用户界面</a>
								<ul>
									<li><a href="${ctx}/form_getFormList.do" external="true" target="navTab" rel="FormsList">流程表单</a></li>
								</ul>
							</li>
							<li><a>角色设置</a> 
								<ul>
									<li><a href="${ctx }/group_getInnerUserList.do" external="true" target="navTab" rel="type44">流程用户组</a></li>
									<li><a href="${ctx }/group_getInnerUserList.do?isSystemGroup=1" external="true" target="navTab" rel="type44">平台用户组</a></li>
								</ul>
							</li>
							<li><a>许可设置</a>
								<ul>
									<li><a href="${ctx}/permition_getFormList.do?type=1"  external="true" target="navTab" rel="permit">表单许可</a></li>
								</ul>
							</li>
							<li><a>其它设置</a>
								<ul>
									<li><a href="${ctx}/template_getTemplateList.do" external="true" target="navTab" rel="template">正文模板</a></li>
									<li><a href="${ctx}/template_getTemplateList.do?isRedTape=1" external="true" target="navTab" rel="template">红头模板</a></li>
									<li><a href="${ctx}/workday_getList.do" external="true" target="navTab" rel="workingcalendar">工作日历</a></li>
									<li><a href="${ctx}/attachmentType_getAttachmentTypeList.do" external="true" target="navTab" rel="attachmentType">附件类型</a></li>
									<li><a href="${ctx}/serverPlugin_getList.do" external="true" target="navTab" rel="classTool">接口插件</a></li>
									<li><a href="${ctx}/table_backOneNode.do" external="true" target="navTab" rel="classTool">自动追溯</a></li>
								</ul> 
							</li>
							<li><a>文号管理</a>
								<ul>
									<li><a href='${ctx}/docNumberManager_docNumberBigClass.do' external="true" target="navTab" rel="type52">文号大类管理</a></li>
									<li><a href='${ctx}/docNumberManager_docNumberSmallClass.do' external="true" target="navTab" rel="type53">文号小类管理</a></li>
									<li><a href='${ctx}/docNumberManager_docNumberModelManage.do' external="true" target="navTab" rel="type51">文号实例管理</a></li>
									<li><a href='${ctx}/docNumberManager_docNumberBindDefine.do' external="true" target="navTab" rel="type54">文号流程绑定</a></li>
								</ul> 
							</li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>事项管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx}/item_getItemList.do" external="true" target="navTab" rel="page1">事项管理</a></li>
							<li><a id="" href="${ctx}/item_setItemBinding.do" external="true" target="navTab" rel="page1">待收事项绑定</a></li>
							<li><a id="" href="${ctx}/table_getDoFileList.do?redirect=1" external="true" target="navTab" rel="page1">办件列表(重定向)</a></li>
							<li><a href='${ctx}/comment_getCommentList.do' external="true" target="navTab" rel="type52">意见常用语</a></li>
							<li><a href='${ctx}/install.jsp' external="true" target="navTab" rel="type52">安装控件</a></li>
							<li><a href='${ctx}/replay_getReplayList.do' external="true" target="navTab" rel="type52">待处理列表</a></li>
							<li><a href='${ctx}/noticeInfo_getNoticeInfoList.do' external="true" target="navTab" rel="type52">通知公告</a></li>
							<li><a href='${ctx}/employeeLeader_getDepartmentLeaderList.do' external="true" target="navTab" rel="type52">部门领导人</a></li>
							<li><a href='${ctx}/push_toCheckPushServer.do' external="true" target="navTab" rel="type52">推送服务器检测</a></li>
							<li><a href="${ctx}/form_toAddFormStyle.do?a="+Math.random() external="true" target="navTab" rel="page4">表单相关参数设置</a></li>
							<li><a href="${ctx}/ztree_toSetUserGroup.do?isRole=1&siteId=BFA811EA-0000-0000-4557-2FC000000689" external="true" target="navTab" rel="page99">角色设置（默认信息中心）</a></li>
							<li><a href="${ctx}/ztree_toSetUserGroup.do?siteId=BFA811EA-0000-0000-4557-2FC000000689" external="true" target="navTab" rel="page199">常用组设置（默认信息中心）</a></li>
							<li><a href="${ctx}/ztree_toSetLeader.do" external="true" target="navTab" rel="page199">领导设置（用于督办件发短信过滤人员）</a></li>
							<li><a href="${ctx}/noticeInfo_getXMList.do" external="true" target="navTab" rel="page199">项目信息列表</a></li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>业务处理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="pendingLink" href="${ctx}/table_getPendingList.do" external="true" target="navTab" rel="page2">待办事项</a></li>
							<li><a id="pendingLink" href="${ctx}/table_getPendingList.do?isShowWH=1" external="true" target="navTab" rel="page2">待办事项(文号)</a></li>
							<li><a id="pendingLink" href="${ctx}/tableExtend_getPendingListOfReadFile.do" external="true" target="navTab" rel="page2">待阅事项</a></li>
							<li><a id="pendingLink" href="${ctx}/table_getPendingList.do?state=1" external="true" target="navTab" rel="page2">待办事项(带发起)</a></li>
							<li><a id="" href="${ctx}/table_getOverList.do" external="true" target="navTab" >已办事项</a></li>
							<li><a id="" href="${ctx}/table_getOverList.do?isShowWH=1" external="true" target="navTab" >已办事项(文号)</a></li>
							<li><a id="" href="${ctx}/table_getDuBanList.do?" external="true" target="navTab" >督办办件列表</a></li>
							<li><a id="" href="${ctx}/tableExtend_getOverListOfReadFile.do" external="true" target="navTab" rel="page100">已阅事项</a></li>
							<li><a id="" href="${ctx}/table_getOverList.do?status=4" external="true" target="navTab" >已办未办结</a></li>
							<li><a id="" href="${ctx}/table_getOverList.do?status=2" external="true" target="navTab" >已办结</a></li>
							<li><a id="" href="${ctx}/table_getOverList.do?status=2&isShowWH=1" external="true" target="navTab" >已办结(文号)</a></li>
							<li><a id="" href="${ctx}/tableExtend_getFollowList.do?status=5" external="true" target="navTab" >已关注办件</a></li>
							<li><a id="" href="${ctx}/tableExtend_getOutOfDateList.do" external="true" target="navTab" >所有超期办件列表</a></li>
							<li><a id="" href="${ctx}/table_getAllOverList.do" external="true" target="navTab" >全部已办</a></li>
							<li><a id="" href="${ctx}/table_getOverList.do?owner=1" external="true" target="navTab" >发起人事项</a></li>
							<li><a id="" href="${ctx}/table_getWaitPendingList.do" external="true" target="navTab" rel="page3">等办事项</a></li>
							<li><a id="" href="${ctx}/table_getDoFileReceiveList.do?status=0" external="true" target="navTab" rel="page3">待收事项</a></li>
							<li><a id="" href="${ctx}/table_getDoFileReceiveList.do?status=1" external="true" target="navTab" rel="page3">已收事项</a></li>
							<li><a id="" href="${ctx}/table_getDoFileReceiveList.do?status=1&type=1" external="true" target="navTab" rel="page3">已收事项(仅查看)</a></li>
							<li><a id="" href="${ctx}/table_getLhfwList.do" external="true" target="navTab" rel="page3">待盖章列表</a></li>
							<li><a id="" href="${ctx}/table_getLhfwList.do?status=1" external="true" target="navTab" rel="page3">已盖章列表</a></li>
							<li><a id="" href="${ctx}/freeSet_freeSet.do" external="true" target="navTab" rel="page3">自定义设置</a></li>
							<li><a id="" href="${ctx}/freeSet_preview.do" external="true" target="navTab" rel="page3">自定义查询</a></li>
							<li><a id="" href="${ctx}/table_getDofileReceiveList.do" external="true" target="navTab"  rel="page4">已发送列表</a></li>
							<li><a id="" href="${ctx}/table_getDoFileList.do?favourite=1" external="true" target="navTab"  rel="page4">收藏夹</a></li>
							
							<li><a id="" href="${ctx}/business_getSendLibrary.do" external="true" target="navTab"  rel="page4">发文库</a></li>
						    <li><a  href="${ctx}/business_getReceiveLibrary.do" external="true" target="navTab" rel="page4">收文库</a></li>
							<li><a href="${ctx }/ztree_getCommonUseGroupList.do" external="true" target="navTab" rel="type44">公共常用用户组</a></li>
							<li><a>公文交换xml</a>
								<ul>
									<li><a id="" href="${ctx}/table_getReceiveAllList.do?status=0" external="true" target="navTab" rel="page3">待收事项(全部)</a></li>
									<li><a id="" href="${ctx}/table_getReceiveAllList.do?status=2" external="true" target="navTab" rel="page3">已收事项(全部)</a></li>
								</ul>
							</li>
							<li><a>催办</a>
								<ul>
									<li><a id="pressList" href="${ctx}/business_getPressList.do" external="true" target="navTab"  rel="page4">催办列表（管理）</a></li>
									<li><a id="" href="${ctx}/business_getPressMsgList.do" external="true" target="navTab"  rel="page4">已催办信息表</a></li>
									<li><a id="" href="${ctx}/business_getStatisticalList.do" external="true" target="navTab"  rel="page4">超期汇总列表</a></li>
								</ul>
							</li>
							<li><a>来文</a>
								<ul>
									<li><a id="" href="${ctx}/toRecDoc_getToRecDocList.do" external="true" target="navTab"  rel="page4">上级待收</a></li>
									<li><a id="" href="${ctx}/toRecDoc_getToRecedDocList.do" external="true" target="navTab"  rel="page4">已收列表</a></li>
									<li><a id="" href="${ctx}/fieldMatching_getAllItemList.do" external="true" target="navTab"  rel="page4">公文交换字段匹配</a></li>
								</ul>
							</li>
							<li><a  href="${ctx}/doArticlePost_getFreeDofileList.do?bjlx=0&status=1&a="+Math.random() external="true" target="navTab" rel="page4">办文查询</a></li>
							<li><a  href="${ctx}/doArticlePost_getFreeDofileList.do?bjlx=1&status=1&a="+Math.random() external="true" target="navTab" rel="page4">发文查询</a></li>
							<li><a>公文交换</a>
								<ul>
									<li><a id="" href="${ctx}/rec_tobeRecList.do" external="true" target="navTab"  rel="page4">公文待收</a></li>
									<li><a id="" href="${ctx}/rec_receivedDocList.do" external="true" target="navTab"  rel="page4">已收列表</a></li>
									<li><a id="" href="${ctx}/selectTree_setDepExchangeRelation.do" external="true" target="navTab"  rel="page4">部门匹配关系</a></li>
									<li><a id="" href="${ctx}/fieldMatching_getAllItemList.do" external="true" target="navTab"  rel="page4">公文交换字段匹配</a></li>
								</ul>
							</li>
						</ul>
						
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>南京新加业务</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx}/ztree_getCommonUseGroupList.do" external="true" target="navTab" rel="page70">公共常用用户组</a></li>
							<li><a id="" href="${ctx}/depConfig_getDep_All.do" external="true" target="navTab" rel="page70">主抄送部门配置</a></li>
							<li><a id="" href="${ctx}/business_getCyDoFileList.do?status=0" external="true" target="navTab" rel="page70">传阅列表</a></li>
							<li><a id="" href="${ctx}/business_getCyDoFileList.do?status=1" external="true" target="navTab" rel="page70">已阅列表</a></li>
							<li><a id="" href="${ctx}/table_getDoFileReceiveList.do?status=3" external="true" target="navTab" rel="page3">公文收取</a></li>
							<li><a id="" href="${ctx}/employeeLeader_getEmployeeList.do?" external="true" target="navTab" rel="page3">水印码查询</a></li>
							<li><a id="" href="${ctx}/table_getDoFileLogList.do?isAdmin=1" external="true" target="navTab" >办件日志列表</a></li>
							<li>
								<a>运营监管</a>
								<ul>
									<li><a id="" href="${ctx}/monitor_getNoOwnerDofileList.do" external="true" target="navTab" rel="page3">无归属办件列表</a></li>
									<li><a id="" href="${ctx}/monitor_getExceedPendingList2.do" external="true" target="navTab" rel="page3">长期未办件列表</a></li>
									<li><a id="" href="${ctx}/monitor_getExceedPendingList2ForDept.do" external="true" target="navTab" rel="page3">长期未办件列表(部门)</a></li>
									<li><a id="" href="${ctx}/monitor_getExceedUnresolvedList2.do" external="true" target="navTab" rel="page3">长期未结件列表</a></li>
									<li><a id="" href="${ctx}/monitor_getLostCmtDfList.do" external="true" target="navTab" rel="page3">意见丢失办件列表</a></li>
									<li><a id="" href="${ctx}/monitor_getLostAttDfList.do" external="true" target="navTab" rel="page3">附件丢失办件列表</a></li>
									<li><a id="" href="${ctx}/monitor_getMonitorIndex.do" external="true" target="navTab" rel="page3">运营监管主页</a></li>
									<li><a id="" href="${ctx}/monitor_getInterfaceCheckPage.do" external="true" target="navTab" rel="page3">接口检测</a></li>
									<li><a id="" href="${ctx}/monitor_getMonitorChart.do" external="true" target="navTab" rel="page3">监管情况统计</a></li>
								</ul>
							</li>
							<li>
								<a>统一人员管理</a>
								<ul>
									<li><a id="" href="${ctx}/siteManage_showAllSite.do" external="true" target="navTab" rel="page3">站点列表</a></li>
									<li><a id="" href="${ctx}/siteManage_showAllDep.do" external="true" target="navTab" rel="page3">部门列表</a></li>
									<li><a id="" href="${ctx}/siteManage_showAllEmp.do" external="true" target="navTab" rel="page3">人员列表</a></li>
									<li><a id="" href="${ctx}/siteManage_showAllEmpForAuth.do" external="true" target="navTab" rel="page3">人员角色控制列表</a></li>
									<li><a id="" href="${ctx}/siteManage_getRoleList.do" external="true" target="navTab" rel="page3">角色列表</a></li>
									<li><a id="" href="${ctx}/mobileTerminalInterface_showAllWfList.do" external="true" target="navTab" rel="page3">流程人员管理</a></li>
								</ul>
							</li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>数据中心</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx}/dataCenter_getbusModuleList.do" external="true" target="navTab" rel="page70">数据库列表</a></li>
							<li><a id="" href="${ctx}/dataCenter_getDataDicList.do" external="true" target="navTab" rel="page71">数据字典</a></li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>办件删除</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx}/table_getHszDoFileList.do" external="true" target="navTab" rel="page70">回收站</a></li>
							<li><a id="" href="${ctx}/table_getDoFileList.do?type=del" external="true" target="navTab" rel="page71">办件列表(带删除)</a></li>
							<li><a id="" href="${ctx}/table_getDoFileList.do?type=del&isShowWH=1" external="true" target="navTab" rel="page71">办件列表(带删除带文号)</a></li>
							<li><a id="" href="${ctx}/table_getDoFileList.do?isAdmin=1" external="true" target="navTab" >办件列表</a></li>
							<li><a id="" href="${ctx}/table_getDoFileList.do?isAdmin=1&isShowExp=1" external="true" target="navTab" rel="page711">办件列表</a></li>
							<li><a id="" href="${ctx}/table_getDoFileList.do?isAdmin=1&isShowWH=1" external="true" target="navTab">办件列表(文号)</a></li>
						</ul>
					</div>
					<%-- <div class="accordionHeader">
						<h2><span>Folder</span>版本管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx}/vm_getAllVMList.do" external="true" target="navTab" rel="page1">版本管理</a></li>
						</ul>
					</div> --%>
					<div class="accordionHeader">
						<h2><span>Folder</span>工具类</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx}/supportTools_toTrueJsp.do" external="true" target="navTab" rel="page1">文件阅读转换</a></li>
							<li><a id="" href="${ctx}/log_queryLogList.do" external="true" target="navTab" rel="page1">异常日志列表</a></li>
							<li><a href='${ctx}/uNodePermission_showPermissionToEverBody.do' external="true" target="navTab" rel="type52">流程权限管理</a></li>
							<li><a id="" href="${ctx}/trueJson_getTrueJsonLogList.do" external="true" target="navTab" rel="page1">意见操作日志</a></li>
							<li><a id="" href="${ctx}/tableExtend_getAccessLogList.do" external="true" target="navTab" rel="page60">移动端接口访问日志</a></li>
							<li><a id="" href="${ctx}/trueJson_getDelFileLogList.do" external="true" target="navTab" rel="page999">办件删除操作日志</a></li>
							<li><a id="" href="${ctx}/tableExtend_getDownloadLogList.do" external="true" target="navTab" rel="page665">办件下载日志</a></li>
						</ul>
					</div>
					
					<div class="accordionHeader">
						<h2><span>Folder</span>个人中心</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx}/table_personMessage.do" external="true" target="navTab" rel="page3">个人中心</a></li>
							<li><a id="" href="${ctx}/employeeRole_getRoleList.do" external="true" target="navTab" rel="page3">角色管理</a></li>
							<li>
								<a>数据库管理</a>
								<ul>
									<li><a href="${ctx}/table_getCoreTableList.do"  external="true" target="navTab" rel="page5">基础核心表</a></li>
									<li><a href="${ctx }/table_getOfficeTableList.do" external="true" target="navTab" rel="page5">业务表</a></li>
								</ul>
							</li>
							<li><a id="" href="${ctx}/licenseInfo.jsp" external="true" target="navTab" rel="page3">license信息</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">工作流主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
				<div class="page">
				<div id="divCurPage" layoutH="0">
				<iframe id="mainframe" name="mainframe" frameborder="no" marginheight="0" marginwidth="0" border="0" style="width:100%;" src="${ctx}/mobileTerminalInterface_listWF.do"></iframe>
				</div> 
				</div> 
				</div>
			</div>
		</div> 
	</div> 
	<div id="footer">Copyright &copy; 2013 江苏中威科技软件系统有限公司             Tel：0513-81550895</div>
    <div id="tabs" style="display:none;">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">                  
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">                 
        </div>
    </div>
</body>
<script>
$(document).ready(function(){
	var bH=$(window).height();
	$('iframe').height(bH-118);  
	$('#tmcon').width(198); 
	$('#tmcon').height(bH-60-108); 
});
$(window).bind('resize',function(){
	var bH=$(window).height();
	$('iframe').height($('.navTab-panel').height());
});
function loginout(){
	if(!confirm('是否退出系统?'))return; 
	//document.getElementById('form1').submit();
	//如果系统为单点登录则执行单点登出方法，否则执行普通退出方法
	if('${ssologin}'=='1'){
		var sso_out = '${ssoUrl}/logout';
		//alert(sso_out+'?service=${curl}/form_loginout.do');
		location.href = 'http://192.168.5.103:10086/sso/logout?service=${curl}';
	}else{
		document.getElementById('form1').submit();
		window.close();
	};
};
function showDialog(obj){
	 var url = $(obj).attr("href")+"&date="+new Date();
	//用layer的模式打开
	layer.open({
		title:'流程设计编辑',
		type:2,
		area:[window.screen.width+"px",(window.screen.height-150)+"px"],
		content: url
	}); 
};

//弹出待办提示
function showTip(){
	$.ajax({   
		url : '${ctx}/table_getPendingCount.do',
		type : 'POST',   
		cache : false,
		global:false,
		dataType:'html',
		error : function() {  
			alert('AJAX调用错误(table_getPendingCount.do)');
		},
		success : function(result) {  
			
			var obj = result.split(';');
			var pendingcount = obj[0];
			var warncount = obj[1];	
			var passedcount = obj[2];
			if(pendingcount != '0')
			{
				var msg = "您有 <font color='red'>"+pendingcount+"</font> 条待办事项</br>";
				if(warncount != '0' || passedcount != '0'){
					msg += "其中 ";
				}
				if(warncount != '0'){
					msg += "<font color='red'>"+warncount+"</font> 条即将到期&nbsp;&nbsp;";
				}
				if(passedcount != '0'){
					msg += "<font color='red'>"+passedcount+"</font> 条已过期";
				}
				var MSG1 = new CLASS_MSN_MESSAGE("tipId",240,150,"${loginEmployee.employeeName}：","消息提醒",msg,"navTab","${ctx}/table_getPendingList.do"); 
			    MSG1.rect(null,null,null,screen.height-50); 
			    MSG1.speed    = 10; 
			    MSG1.step    = 5;
			  
				 /**
				 * 显示提示框
				 */ 
				MSG1.show();
				setTimeout(function(){MSG1.close=true;MSG1.hide();},1000*30);
			}else{
				showConsultMsg();
			}
			
		}
	});
	
}

var interval;
function ConsultMsg(){
	interval = setInterval(showConsultMsg,1000*61);
	// showConsultMsg();
}
//弹出待办提示
function showConsultMsg(){
	$.ajax({   
		url : '${ctx}/table_getNotReadConsultList.do',
		type : 'POST',   
		cache : false,
		global:false,
		dataType:'html',
		error : function() {  
			clearInterval(interval);
			//alert('错误，连接已断开');
		},
		success : function(result) {  
			
			if(result=='[null]'){
				return;
			}
			var obj = eval('('+result+')');
			for(var i=0;i<obj.length;i++){
				var id = obj[i].id;
				var userId = obj[i].fromUserId;
				var userName = obj[i].fromUserName;
				var msg = obj[i].message;

				var args = new Array();
				args.push(id);
				args.push(userId);
				args.push(userName);
				var MSG1 = new CLASS_MSN_MESSAGE_CONSULT("consultId_"+i,240,150,userName+" 说：","协商消息",msg,args,null); 
			    MSG1.rect(null,null,null,screen.height-50); 
			    MSG1.speed    = 10; 
			    MSG1.step    = 5;
			  
				 /**
				 * 显示提示框
				 */ 
				MSG1.show();
				
				setTimeout(function(){MSG1.close=true;MSG1.hide();},1000*30);
				break;//显示第一条，多条显示会引起提示框不停闪烁
			}
			
		}
	});
	
}
</script>
    <script type="text/javascript">
	        var layid,
                taskid,
	            isFull = 0;
            function topOpenLayerTabs(processId,width,height,title,url){
            	if('' != processId){
					$.ajax({
						url : '${ctx}/tableExtend_updateIsOpen.do',
						type : 'POST',
						cache : false,
						async : false,
						data : {
							'processId':processId,
							'isOpen':'1'
						},
						success : function(ret) {
						}
					});
				}
            	
			    if ($('.layui-layer').length == 1) {
					if(document.all && document.addEventListener && window.atob){
				    
					}else {
                        addTabsInLayer(processId,title,url);
                        layer.restore(taskid);
					}
				}else{
                    layer.open({
                        type: 1,
                        title: false,
                        shade: false,
						move: '.nav-tabs',
						moveType: 1,
                        area: [$(window).width()-100+'px','90%'],
                        content: $('#tabs'),
					    success: function(){
			                $('#tabs').addtabs({
				                iframeHeight: ($(window).height()*0.9)+'px',
				                callback: function () { //关闭tabs后回调函数
				                    if($('.layui-layer li[role = "presentation"].active').length == 0){
					                    layer.close(taskid);
						                $('#tabs .nav-tabs').html('');
						                $('#tabs .tab-content').html('');
					                }
				                    if($('.page iframe:visible')[0].contentWindow.autoCheck){
				                    	$('.page iframe:visible')[0].contentWindow.autoCheck()
									} else {
										var selectIndex = $('.page iframe:visible')[0].contentWindow.selectIndex;
									   	var pageSize = $('.page iframe:visible')[0].contentWindow.pageSize;
									   
									   	if(null == selectIndex || '' == selectIndex){
											selectIndex = '1';
									   	}
									   	if(null == pageSize || '' == pageSize){
										   	pageSize = '10';
									   	}
								   
									   	var pageIndex = (parseInt(selectIndex)-1)*parseInt(pageSize);
									   	var visibleUrl = $('.page iframe:visible').attr('src');
									   	if(visibleUrl.indexOf("?") > 0){
										   	visibleUrl = visibleUrl + "&pageSize="+pageSize + "&selectIndex=" + selectIndex + "&pageIndex=" + pageIndex;
									   	}else{
										   	visibleUrl = visibleUrl + "?pageSize="+pageSize + "&selectIndex=" + selectIndex + "&pageIndex=" + pageIndex;
									   	}
									   	$('.page iframe:visible').attr('src',visibleUrl);
										//$('.page iframe:visible').attr('src',$('.page iframe:visible').attr('src'));
									}
                               }
				            });						
                            layid = $('.layui-layer').attr("id");
                            taskid = parseInt(layid.replace("layui-layer",""));						
                            addTabsInLayer(processId,title,url);
                           
					    },
						min: function(){
							$('.layui-layer').height('38px');
							$('.layui-layer').width($(window).width()-100+'px');
							$('.iframeClass').height('0px');
						},					    
						cancel: function(){
							debugger;
						   $('#tabs .nav-tabs').html('');
						   $('#tabs .tab-content').html('');
						   
						   if($('.page iframe:visible')[0].contentWindow.autoCheck){
							   $('.page iframe:visible')[0].contentWindow.autoCheck()
						   } else {
							   var selectIndex = $('.page iframe:visible')[0].contentWindow.selectIndex;
							   var pageSize = $('.page iframe:visible')[0].contentWindow.pageSize;
							   
							   if(null == selectIndex || '' == selectIndex){
								   selectIndex = '1';
							   }
							   if(null == pageSize || '' == pageSize){
								   pageSize = '10';
							   }
						   
						   
							   var pageIndex = (parseInt(selectIndex)-1)*parseInt(pageSize);
							   var visibleUrl = $('.page iframe:visible').attr('src');
							   if(visibleUrl.indexOf("?") > 0){
								   visibleUrl = visibleUrl + "&pageSize="+pageSize + "&selectIndex=" + selectIndex + "&pageIndex=" + pageIndex;
							   }else{
								   visibleUrl = visibleUrl + "?pageSize="+pageSize + "&selectIndex=" + selectIndex + "&pageIndex=" + pageIndex;
							   }
							   $('.page iframe:visible').attr('src',visibleUrl);
						   }
						},
						full: function(){
						    $('.layui-layer').height($(window).height());
						    $('.layui-layer-content').height($(window).height());
						    $('.iframeClass').height($(window).height());
						    isFull = 1;
						},
						restore: function(){
						    $('.layui-layer').height($(window).height()*0.9);
						    $('.layui-layer-content').css('height',$(window).height()*0.9);
						    $('.iframeClass').height($(window).height()*0.9);
						    isFull = 0;
						}
                    });				
				}
			}
			
			function addTabsInLayer(processId,title,url){
				if(url.indexOf('?') >= 0){
					url += '&title='+title
				} else {
					url += '?title='+title
				}
                Addtabs.add({
                    id: processId,
                    title: title,
                    url: url
                });					
			}

			function closeTabsInLayer(processId) {
				if('' != processId){
					$.ajax({
						url : '${ctx}/tableExtend_updateIsOpen.do',
						type : 'POST',
						cache : false,
						async : false,
						data : {
							'processId':processId,
							'isOpen':'0'
						},
						success : function(ret) {
						}
					});
				}
			    var index = $('.layui-layer li[role = "presentation"].active').find('a').attr('aria-controls');
				Addtabs.close(index);   
			}
			
			function maxOrNotToMax(){
			    if(isFull==0){
				    layer.full(taskid);	
					isFull = 0;
				}
				var bH = $(window).height();
				$('.layui-layer,.layui-layer-content,.iframeClass').height(bH+80);

			}

			function maxOrNotToMin(){
			    if(isFull==0){				
				    layer.restore(taskid);	
					var bH = $(window).height();
					$('.layui-layer').height(bH-80);
					$('.layui-layer-content').height(bH-87);
		            $('.iframeClass').height(bH);				    
			    }else{
					var bH = $(window).height();
					$('.layui-layer').height(bH);
					$('.layui-layer-content').height(bH-2);
		            $('.iframeClass').height(bH);
			    }
			}			
        </script>
</html>
