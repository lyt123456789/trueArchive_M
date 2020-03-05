<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	
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
	</head>
   
	<body scroll="no" id="bodyid">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#">标志</a>
				<ul class="nav">
					<!-- <li><a href="设置" target="_blank">设置</a></li> -->
					<li><a href="javascript:loginout();"  external="true" target="navTab" rel="loginout">退出</a></li>
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
						<h2 id="workflowname" workflowid=""><span>Folder</span>请创建或选择工作流！</h2>
					</div> 
					<div id="tmconfj" class="accordionContent" style="position:relative;overflow:hidden;"> 
					<div id="tmcon" onclick="alert('请先双击选择右边工作流名称！');" style="z-index:1000;position:absolute;filter:alpha(opacity=30);opacity:0.3; height:1000px; width:1000px; background:#ccc;top:0px;left:0px;"></div>
						<ul class="tree treeFolder">
							<li><a id="workflowimg" href="#" external="true" target="navTab" rel="page1">流程图</a> 
							</li>
                            <li><a>任务属性</a>
							</li>
							<li><a>数据库表</a> 
								<ul> 
									<li><a href="${ctx}/table_getTableList.do" external="true" target="navTab" rel="mainForms">主表</a></li>
									<li><a href="表单页面.html" target="navTab" external="true" target="navTab" rel="detailForms">细表</a></li>
									<li><a href="${ctx}/dictionary_getDictionaryList.do" external="true" target="navTab" rel="dictionaryForms">字典表</a></li>
									<li><a href="${ctx}/field_getFieldList.do" external="true" target="navTab" rel="publicfield">基础字段</a></li>
								</ul>
							</li>
							<li><a>用户界面</a>
								<ul>
									<li><a href="${ctx}/form_toAddFormJsp.do" external="true" target="navTab" rel="CreatingForms">创建表单</a></li>
									<li><a href="${ctx}/form_getFormList.do" external="true" target="navTab" rel="FormsList">表单列表</a></li>
								</ul>
							</li>
							<li><a>角色设置</a> 
								<ul>
									<li><a href="${ctx }/group_getInnerUserList.do" external="true" target="navTab" rel="BuiltInUser ">内置用户</a></li>
									<li><a href="${ctx }/departmentTree_showDepartmentTree.do" external="true" target="navTab" rel="11">部门与用户</a></li>
									<li><a href="main.html" target="navTab" rel="main">全局用户组</a></li>
									<li><a href="main.html" target="navTab" rel="main">平台用户组</a></li>
									<li><a href="main.html" target="navTab" rel="main">流程用户组</a></li>
									<li><a href="main.html" target="navTab" rel="main">动态角色</a></li>
								</ul>
							</li>
							<li><a>许可设置</a>
								<ul>
									<li><a href="main.html" target="navTab" rel="main">工作流许可</a></li>
									<li><a href="main.html" target="navTab" rel="main">任务许可</a></li>
									<li><a href="main.html" target="navTab" rel="main">数据库许可</a></li>
									<li><a href="main.html" target="navTab" rel="main">审批意见许可</a></li>
								</ul>
							</li>
							<li><a>其它设置</a>
								<ul>
									<li><a href="${ctx}/template_getTemplateList.do" external="true" target="navTab" rel="template">正文模板</a></li>
									<li><a href="${ctx}/calendar_getCalender.do" external="true" target="navTab" rel="workingcalendar">工作日历</a></li>
								</ul> 
							</li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>事项管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree">
							<li><a id="" href="${ctx}/item_getItemList.do" external="true" target="navTab" rel="page1">事项</a></li>
							<li><a id="" href="${ctx}/table_getPendingList.do" external="true" target="navTab" rel="page2">待办流程</a></li>
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
				<iframe id="mainframe" frameborder="no" marginheight="0" marginwidth="0" border="0" style="width:100%;" src="${ctx}/mobileTerminalInterface_listWF.do"></iframe>
				</div> 
				</div> 
				</div>
			</div> 
		</div>
	</div>
	<div id="footer">Copyright &copy; 2013 江苏中威科技系统软件有限公司 Tel：0513-81550896</div>
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
	
};
</script>
</html>
