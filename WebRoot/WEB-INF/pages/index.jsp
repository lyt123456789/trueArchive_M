<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
 		<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/headerindex.jsp"%>
		<link rel="stylesheet" href="css/index.css?t=123">
		 <link rel="stylesheet" href="css/style.css?t=111">
		 <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
		<script type="text/javascript" src="${ctx }/flow/js/classMessage.js" defer="defer"></script>
		<script type="text/javascript" src="${ctx }/flow/js/classMessage_consult.js" defer="defer"></script>
		<script src="${ctx}/tabInLayer/plugins/layer/layer.js" type="text/javascript"></script>
		<script src="${ctx}/tabInLayer/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${ctx}/tabInLayer/js/bootstrap-addtabs.js" type="text/javascript"></script>
		    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link href="${ctx}/css/font-awesome.4.6.0.css" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="${ctx}/css/sidebar-menu.css">
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
	
	<div class="top">
	    <div class="top_l">
	        <a href="javascript:void(0)" style="float: left;"><img src="img/menu_ico.png" height="16" width="19"></a>
	        <span>数字档案管理系统</span>
	        <form action="${ctx}/form_loginout.do" id="form1" method="post" target="_self"></form>
	    </div>
	    <div class="top_m">
	        <ul>
	        	
	        </ul>
	    </div>
	    <div class="top_r">
	        <a href="javascript:void(0)" class="sqzx" onclick="applyCenter()">
	            <img src="img/sqzx.png">申请中心
	            <label class="jiaobiao"></label>
	        </a>
	        <a href="" class="sqzx">
	            <img src="img/jrdaxt.png">进入档案系统
	        </a>
	
	        <a href="javascript:loginout();" class="exit"><img src="img/exit.png"></a>
	
	   </div>
</div>
<div class="mainContainer">

	
	<div id="layout">
		<div id="leftside" style="top:0px">
	    <div id="sidebar_s">
	    	<div class="toggleCollapse"><img src="img/menu/show_ico.png" class="toggleico none"></div>
	    </div>
    <div id="sidebar">
      <!--   <div class="toggleCollapse"><h2 >主菜单</h2>
        <img src="img/menu/hide_ico.png" class="toggleico shows">
        </div> -->
        <div style="clear: both"></div>
		
<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu">
            <!--<li class="header">主导航</li>-->
            <li class="treeview">
                <a href="javascript:void(0)">
                    <img src="img/menu/menu1.png" class="shows"><img src="img/menu/menu1_act.png" class="none"> <span>我的个人空间</span>
                     <i class="fa fa-angle-right pull-right"></i>
                </a>
                <ul class="treeview-menu">
                     <li><a id="" href="" external="true"  rel=""><i class="fa fa-circle-o"></i>编辑用户信息</a></li>
                    <li><a id="" href="${ctx }/demos_toWdscJsp.do" external="true" target="navTab" rel=Wdsc><i class="fa fa-circle-o"></i>我的收藏</a></li>       
                    <li><a id="" href="${ctx}/demos_toDaxxdyJsp.do" external="true" target="navTab" rel="Daxxdy"><i class="fa fa-circle-o"></i>档案信息订阅</a></li>
                    <li><a id="" href="${ctx }/demos_toDyxxckJsp.do" external="true" target="navTab" rel="Dyxxck"><i class="fa fa-circle-o"></i>订阅信息查看</a></li>
                    <li><a id="" href="${ctx }/str_toStructureTempPage.do" external="true" target="navTab" rel="showStructure"><i class="fa fa-circle-o"></i>我的下载</a></li>
                    <li><a id="" href="${ctx }/dataManage_toDataManageMainPage.do" external="true" target="navTab" rel="dataManage"><i class="fa fa-circle-o"></i>即时通讯</a></li>
                    <li><a id="" href="${ctx }/demos_toHujblpzJsp.do" external="true" target="navTab" rel="toHujblpzJsp"><i class="fa fa-circle-o"></i>环境变量配置</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="javascript:void(0)">
                     <img src="img/menu/menu1.png" class="shows"><img src="img/menu/menu1_act.png" class="none">
                    <span>馆务（协同）工作平台</span>
                    <i class="fa fa-angle-right pull-right"></i>
                </a>
                <ul class="treeview-menu" style="display: none;">
                    <li><a href="${ctx}/mobileTerminalInterface_listWF.do" external="true" target="navTab" rel="WFmain"><i class="fa fa-circle-o"></i>工作流主页</a> 
					<li><a id="workflowimg" href="#" onclick="showDialog(this)"><i class="fa fa-circle-o"></i>流程图</a> 
					</li>
					<li><a><i class="fa fa-angle-right"></i>数据库表</a> 
						<ul class="treeview-menu" style="display: none;"> 
							<li><a href="${ctx}/table_getTableList.do" external="true" target="navTab" rel="mainForms"><i class="fa fa-circle-o"></i>数据表</a></li>
							<li><a href="${ctx}/dictionary_getDictionaryList.do" external="true" target="navTab" rel="dictionaryForms"><i class="fa fa-circle-o"></i>字典表</a></li>
							<li><a href="${ctx}/field_getFieldList.do" external="true" target="navTab" rel="publicfield"><i class="fa fa-circle-o"></i>基础字段</a></li>
							<li><a href="${ctx}/procedure_getList.do" external="true" target="navTab" rel="FormsList"><i class="fa fa-circle-o"></i>存储过程</a></li>
						</ul>
					</li>
					<li><a><i class="fa fa-angle-right"></i>用户界面</a>
						<ul class="treeview-menu" style="display: none;">
							<li><a href="${ctx}/form_getFormList.do" external="true" target="navTab" rel="FormsList"><i class="fa fa-circle-o"></i>流程表单</a></li>
						</ul>
					</li>
					<li><a><i class="fa fa-angle-right"></i>角色设置</a> 
						<ul class="treeview-menu" style="display: none;">
							<li><a href="${ctx }/group_getInnerUserList.do" external="true" target="navTab" rel="type44"><i class="fa fa-circle-o"></i>流程用户组</a></li>
							<li><a href="${ctx }/group_getInnerUserList.do?isSystemGroup=1" external="true" target="navTab" rel="type44"><i class="fa fa-circle-o"></i>平台用户组</a></li>
						</ul>
					</li>
					<li><a><i class="fa fa-angle-right"></i>许可设置</a>
						<ul class="treeview-menu" style="display: none;">
							<li><a href="${ctx}/permition_getFormList.do?type=1"  external="true" target="navTab" rel="permit"><i class="fa fa-circle-o"></i>表单许可</a></li>
						</ul>
					</li>
					<li><a><i class="fa fa-angle-right"></i>其它设置</a>
						<ul class="treeview-menu" style="display: none;">
							<li><a href="${ctx}/template_getTemplateList.do" external="true" target="navTab" rel="template"><i class="fa fa-circle-o"></i>正文模板</a></li>
							<li><a href="${ctx}/template_getTemplateList.do?isRedTape=1" external="true" target="navTab" rel="template"><i class="fa fa-circle-o"></i>红头模板</a></li>
							<li><a href="${ctx}/workday_getList.do" external="true" target="navTab" rel="workingcalendar"><i class="fa fa-circle-o"></i>工作日历</a></li>
							<li><a href="${ctx}/attachmentType_getAttachmentTypeList.do" external="true" target="navTab" rel="attachmentType"><i class="fa fa-circle-o"></i>附件类型</a></li>
							<li><a href="${ctx}/serverPlugin_getList.do" external="true" target="navTab" rel="classTool"><i class="fa fa-circle-o"></i>接口插件</a></li>
							<li><a href="${ctx}/table_backOneNode.do" external="true" target="navTab" rel="classTool"><i class="fa fa-circle-o"></i>自动追溯</a></li>
						</ul> 
					</li>
					<li><a><i class="fa fa-angle-right"></i>文号管理</a>
						<ul class="treeview-menu" style="display: none;">
							<li><a href='${ctx}/docNumberManager_docNumberBigClass.do' external="true" target="navTab" rel="type52"><i class="fa fa-circle-o"></i>文号大类管理</a></li>
							<li><a href='${ctx}/docNumberManager_docNumberSmallClass.do' external="true" target="navTab" rel="type53"><i class="fa fa-circle-o"></i>文号小类管理</a></li>
							<li><a href='${ctx}/docNumberManager_docNumberModelManage.do' external="true" target="navTab" rel="type51"><i class="fa fa-circle-o"></i>文号实例管理</a></li>
							<li><a href='${ctx}/docNumberManager_docNumberBindDefine.do' external="true" target="navTab" rel="type54"><i class="fa fa-circle-o"></i>文号流程绑定</a></li>
						</ul> 
					</li>
                </ul>
            </li>
            <!-- <li>
                <a href="http://www.jq22.com/demo/jqueryNavbl20160816/#">
                    <i class="fa fa-th"></i> <span>窗口小部件</span>
                    <small class="label pull-right label-info">新的</small>
                </a>
            </li> -->
            <li class="treeview">
                <a href="http://www.jq22.com/demo/jqueryNavbl20160816/#">
                    <i class="fa fa-pie-chart"></i>
                    <span>接收中心</span>
                    <i class="fa fa-angle-right pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <%-- <li><a id="" href="${ctx}/demos_toDajsJsp.do" external="true" target="navTab" rel="toDajsJsp"><i class="fa fa-circle-o"></i>档案接收</a></li> --%>
                    <li><a id="" href="${ctx}/dataReception_toDataReceptionMainPage.do" external="true" target="navTab" rel="dataReception"><i class="fa fa-circle-o"></i>档案接收</a></li>
                    <%-- <li><a id="" href="${ctx }/met_toNameSpacePage.do" external="true" target="navTab" rel="showNameSpace"><i class="fa fa-circle-o"></i>文件归档</a></li>   --%>     
                    <li><a id="" href="" external="true"  rel="toDataTempJsp"><i class="fa fa-circle-o"></i>批量上传文件</a></li>
                    <li><a id="" href="${ctx }/demos_toTysjwjzhJsp.do" external="true" target="navTab" rel="Tysjwjzh"><i class="fa fa-circle-o"></i>通用数据文件转换</a></li>
                    <li><a id="" href="${ctx }/demos_toLxsjzlgjzzJsp.do" external="true" target="navTab" rel="Lxsjzlgjzz"><i class="fa fa-circle-o"></i>离线数据著录工具制作</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="http://www.jq22.com/demo/jqueryNavbl20160816/#">
                    <i class="fa fa-pie-chart"></i>
                    <span>管理中心</span>
                    <i class="fa fa-angle-right pull-right"></i>
                </a>
                <ul class="treeview-menu" style="display: none;">
                    <%-- <li><a href="${ctx}/mobileTerminalInterface_listWF.do" external="true" target="navTab" rel="WFmain"><i class="fa fa-circle-o"></i>数据管理</a>  --%>
                    <li><a id="" href="${ctx }/dataManage_toDataManageMainPage.do" external="true" target="navTab" rel="dataManage"><i class="fa fa-circle-o"></i>数据管理</a></li>
					<!-- <li><a id="workflowimg" href="#" onclick="showDialog(this)"><i class="fa fa-circle-o"></i>流程图</a> 
					</li> -->
					<li><a><i class="fa fa-angle-right"></i>接收移出管理</a> 
						<ul class="treeview-menu" style="display: none;"> 
							<li><a id="" href="${ctx}/demos_toYcglJsp.do?status=0" external="true" target="navTab" rel="toYcglJsp"><i class="fa fa-circle-o"></i>移出管理</a></li>
							<li><a id="" href="${ctx}/demos_toJsglJsp.do?status=2" external="true" target="navTab" rel="toJsglJsp"><i class="fa fa-circle-o"></i>接收管理</a></li>
							<li><a id="" href="${ctx}/demos_toYcjstjJsp.do?status=2" external="true" target="navTab" rel="toYcjstjJsp"><i class="fa fa-circle-o"></i>移出接收统计</a></li>
							<li><a id="" href="${ctx}/demos_toJsyctzJsp.do?status=2" external="true" target="navTab" rel="toJsyctzJsp"><i class="fa fa-circle-o"></i>接收移出台账</a></li>
						</ul>
					</li>
					<li><a id="" href="${ctx }/demos_toBgglJsp.do" external="true" target="navTab" rel="toBgglJsp"><i class="fa fa-circle-o"></i>保管管理</a> 
					<li><a><i class="fa fa-angle-right"></i>库房管理</a>
						<ul class="treeview-menu" style="display: none;">
							<li><a id="" href="${ctx}/demos_toKufstdaglJsp.do?status=0" external="true" target="navTab" rel="toKufstdaglJsp"><i class="fa fa-circle-o"></i>库房实体档案管理</a></li>
							<li><a id="" href="${ctx}/table_getReceiveAllList.do?status=2" external="true" target="navTab" rel="page3"><i class="fa fa-circle-o"></i>库房实体3D显示</a></li>
							<li><a id="" href="${ctx}/demos_toKufgltzJsp.do?status=2" external="true" target="navTab" rel="toKufgltzJsp"><i class="fa fa-circle-o"></i>库房管理台账</a></li>
						</ul>
					</li>
					<li><a><i class="fa fa-angle-right"></i>鉴定销毁管理</a> 
						<ul class="treeview-menu" style="display: none;">
							<li><a id="" href="${ctx}/demos_toXiaohglJsp.do?status=0" external="true" target="navTab" rel="toXiaohglJsp"><i class="fa fa-circle-o"></i>销毁管理</a></li>
							<li><a id="" href="${ctx}/demos_toDaxhtzJsp.do?status=2" external="true" target="navTab" rel="toDaxhtzJsp"><i class="fa fa-circle-o"></i>档案销毁台账</a></li>
						</ul>
					</li>
					<li><a id="" href="${ctx }/demos_toHuakjdglJsp.do" external="true" target="navTab" rel="toHuakjdglJsp"><i class="fa fa-circle-o"></i>划控鉴定管理</a> 
					<li><a id="" href="${ctx }/demos_toXiubglJsp.do" external="true" target="navTab" rel="toXiubglJsp"><i class="fa fa-circle-o"></i>俢裱管理</a> 
					<li><a><i class="fa fa-angle-right"></i>档案统计</a>
						<ul class="treeview-menu" style="display: none;">
							<li><a id="" href="${ctx}/demos_toGuanctjJsp.do?status=0" external="true" target="navTab" rel="toGuanctjJsp"><i class="fa fa-circle-o"></i>馆藏统计</a></li>
							<li><a id="" href="${ctx}/demos_toNianbglJsp.do?status=2" external="true" target="navTab" rel="toNianbglJsp"><i class="fa fa-circle-o"></i>年报管理</a></li>
						</ul>
					</li>
					<li><a id="" href="${ctx }/demos_toDangaszhJsp.do" external="true" target="navTab" rel="toDangaszhJsp"><i class="fa fa-circle-o"></i>档案数字化</a></li>
					<li><a id="" href="${ctx }/demos_toQuzglJsp.do" external="true" target="navTab" rel="toQuzglJsp"><i class="fa fa-circle-o"></i>全宗管理</a></li>
					<li><a><i class="fa fa-angle-right"></i>元数据管理</a>
						<ul class="treeview-menu" style="display: none;">
							<%-- <li><a id="" href="${ctx }/demos_toMingmkjglJsp.do" external="true" target="navTab" rel="toMingmkjglJsp">命名空间管理</a></li> 
							<li><a id="" href="${ctx }/demos_toYewglJsp.do" external="true" target="navTab" rel="toYewglJsp">业务管理</a></li> --%>
							<li><a id="" href="${ctx }/met_toNameSpacePage.do" external="true" target="navTab" rel="showNameSpace"><i class="fa fa-circle-o"></i>命名空间管理</a></li> 
							<li><a id="" href="${ctx }/bus_toBusinessManageJsp.do" external="true" target="navTab" rel="showBusiness"><i class="fa fa-circle-o"></i>业务管理</a></li>      
		                    <li><a id="" href="${ctx}/dataTemp_toDataTempJsp.do?business=35547CE2-A572-4CDF-83D0-48B404164C59" external="true" target="navTab" rel="toDataTempJsp"><i class="fa fa-circle-o"></i>模板定义</a></li>     
		                    <li><a id="" href="${ctx }/str_toStructureTempPage.do" external="true" target="navTab" rel="showStructure"><i class="fa fa-circle-o"></i>结构模板定义</a></li>
		                    <li><a id="" href="${ctx }/demos_toBaobwhJsp.do" external="true" target="navTab" rel="toBaobwhJsp"><i class="fa fa-circle-o"></i>报表维护</a></li>
		                    <li><a id="" href="${ctx }/demos_toQuanzqglJsp.do" external="true" target="navTab" rel="toQuanzqglJsp"><i class="fa fa-circle-o"></i>全宗群管理</a></li>
						</ul> 
					</li>
                </ul>
            </li>
            <li class="treeview">
                <a href="http://www.jq22.com/demo/jqueryNavbl20160816/#">
                    <i class="fa fa-laptop"></i>
                    <span>利用中心</span>
                    <i class="fa fa-angle-right pull-right"></i>
                </a>
                <ul class="treeview-menu" style="display: none;">
                    <li><a id="" href="${ctx}/demos_toZhywcxJsp.do" external="true" target="navTab" rel="toZhywcxJsp"><i class="fa fa-circle-o"></i>综合业务查询</a></li>
                    <li><a id="" href="${ctx}/dataUsing_toSearchJsp.do" external="true" target="navTab" rel="toGuanjccxJsp"><i class="fa fa-circle-o"></i>关键词检索</a></li>
                    <li><a id="" href="${ctx }/bus_toBusinessManageJsp.do" external="true" target="navTab" rel="showBusiness"><i class="fa fa-circle-o"></i>全文检索</a></li>
					<li><a><i class="fa fa-angle-right"></i>档案编研</a> 
						<ul class="treeview-menu" style="display: none;"> 
							<li><a id="" href="${ctx}/demos_toDangabyglJsp.do?status=0" external="true" target="navTab" rel="toDangabyglJsp"><i class="fa fa-circle-o"></i>档案编研管理计</a></li>
							<li><a id="" href="${ctx}/demos_toChakbywdJsp.do?status=2" external="true" target="navTab" rel="page3"><i class="fa fa-circle-o"></i>查看编研文档</a></li>
						</ul>
					</li>
					<li><a><i class="fa fa-angle-right"></i>利用管理</a>
						<ul class="treeview-menu" style="display: none;">
							<li><a id="" href="${ctx}/demos_toChayglJsp.do?status=0" external="true" target="navTab" rel="toChayglJsp"><i class="fa fa-circle-o"></i>查阅管理</a></li>
							<li><a id="" href="${ctx}/demos_toJieyglJsp.do?status=2" external="true" target="navTab" rel="toJieyglJsp"><i class="fa fa-circle-o"></i>借阅管理</a></li>
							<li><a id="" href="${ctx}/demos_toJieymbszJsp.do?status=2" external="true" target="navTab" rel="toJieymbszJsp"><i class="fa fa-circle-o"></i>借阅模板设置</a></li>
							<li><a id="" href="${ctx}/demos_toSuoykglJsp.do?status=2" external="true" target="navTab" rel="toSuoykglJsp"><i class="fa fa-circle-o"></i>索引库管理</a></li>
							<li><a id="" href="${ctx}/table_getReceiveAllList.do?status=2" external="true" target="navTab" rel="page3"><i class="fa fa-circle-o"></i>利用统计</a></li>
						</ul>
					</li>
                </ul>
            </li>
            <li class="treeview">
                <a href="http://www.jq22.com/demo/jqueryNavbl20160816/#">
                    <i class="fa fa-edit"></i> <span>系统管理</span>
                    <i class="fa fa-angle-right pull-right"></i>
                </a>
                <ul class="treeview-menu" style="display: none;">
                    <li><a id="" href="${ctx}/demos_toJighyhglJsp.do" external="true" target="navTab" rel="toJighyhglJsp"><i class="fa fa-circle-o"></i>机构和用户管理</a></li>
					<li><a><i class="fa fa-angle-right"></i>权限管理</a> 
						<ul class="treeview-menu" style="display: none;"> 
							<li><a id="" href="${ctx}/menu_getMenuList.do" external="true" target="navTab" rel="toJuesglJsp"><i class="fa fa-circle-o"></i>菜单管理</a></li>
							<li><a id="" href="${ctx}/role_getRoleList.do" external="true" target="navTab" rel="toJuesglJsp"><i class="fa fa-circle-o"></i>角色管理</a></li>
							<li><a id="" href="${ctx}/menurole_getRoleList.do" external="true" target="navTab" rel="toJuesglJsp"><i class="fa fa-circle-o"></i>菜单授权</a></li>
							<li><a id="" href="${ctx}/rolemanage_toRoleDataAuthorizeJsp.do" external="true" target="navTab" rel="toGonnsqJsp"><i class="fa fa-circle-o"></i>数据授权</a></li>
							<li><a id="" href="${ctx}/btnmanage_toBtnManagePage.do" external="true" target="navTab" rel="toGonnsqJsp"><i class="fa fa-circle-o"></i>按钮授权</a></li>
							<%-- <li><a id="" href="${ctx}/demos_toShujsqJsp.do?status=2" external="true" target="navTab" rel="toShujsqJsp"><i class="fa fa-circle-o"></i>数据授权</a></li>
							<li><a id="" href="${ctx}/demos_toChakyhqxJsp.do?status=2" external="true" target="navTab" rel="toChakyhqxJsp"><i class="fa fa-circle-o"></i>查看用户权限</a></li> --%>
						</ul>
					</li>
					<li><a id="" href="${ctx}/demos_toWenjfwqszJsp.do?status=2" external="true" target="navTab" rel="toWenjfwqszJsp"><i class="fa fa-circle-o"></i>文件服务器设置</a></li>
                    <li><a id="" href="${ctx}/demos_toRizglJsp.do?status=2" external="true" target="navTab" rel="toRizglJsp"><i class="fa fa-circle-o"></i>日志管理</a></li>
                </ul>
            </li>
        </ul>
    </section>
</aside>

    </div>
</div>
		
		<div id="container" style="top:0px">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab" style="left: 0px;">
							<li tabid="main" class="main"><a href="javascript:;"><span>主页</span></a></li>
						</ul>
					</div>
					<!-- <div class="tabsLeft tabsLeftDisabled">left</div> --><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<!-- <div class="tabsRight tabsRightDisabled">right</div> --><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<!-- <div class="tabsMore tabsMoreDisabled">more</div> -->
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">欢迎页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page">
						<div id="divCurPage" layoutH="0">
							<iframe id="mainframe" name="mainframe" frameborder="no" marginheight="0" marginwidth="0" border="0" style="width:100%;" src="${ctx}/departmentTree_toWelcomeJsp.do"></iframe>
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
</div>


<script src="${ctx}/js/sidebar-menu.js"></script>
<script>
    $.sidebarMenu($('.sidebar-menu'));

</script>
</body>
<script>
$(document).ready(function(){
	var bH=$(".tabsPageContent").height();
	var lH=$(".mainContainer").height()-106;
	$(".sidebar-menu > li > .treeview-menu").css("max-height",lH-$(".main-sidebar").height()+"px");
	$('#mainframe').height(bH);  
	//$(".main").height(bH-118);
	$('#tmcon').width(198); 
	$('#tmcon').height(bH-60-108); 
	$(".toggleico.shows").click(function(){
		$("#sidebar_s").show();
		$("#sidebar").hide();
		$("#container").css("left","24px");
		$("#container").width($("#container").width()+186);
	})
	$(".toggleico.none").click(function(){
		$("#sidebar_s").hide();
		$("#sidebar").show();
		$("#container").css("left","210px");
		$("#container").width($("#container").width()-186);
	})
	//setTimeout('$("#start").trigger("click");',1000);
});
$(window).bind('resize',function(){
	var bH=$(window).height();
	$('iframe').height($('.navTab-panel').height());
});
</script>
<script type="text/javascript">
function showDialog(obj){
	 var url = $(obj).attr("href")+"&date="+new Date().getTime();
	//用layer的模式打开
	layer.open({
		title:'流程设计编辑',
		type:2,
		area:[window.screen.width+"px",(window.screen.height-150)+"px"],
		content: url
	}); 
};
</script>
</html>
