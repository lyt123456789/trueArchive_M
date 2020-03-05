<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerbase3.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${systitle}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/dtree/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/font/dtreefont.css">
    
    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
    <style type="text/css">
    	#toolbarDiv{
	    	width: 261px;
		    height: 100%;
		    overflow: auto;
		    border: solid 1px;
    	}
    	#treediv{
	    	width: 261px;
	    	float: left;
		    height: 100%;
    	}
    	#editDiv{
    	    width: 1100px;
		    height: 654px;
		    float: left;
		    padding: 10px;
    	}
    	.wf-fixtable thead th{
		        background: #008cee;
		    font-size: 16px;
		    color: #fff;
		    padding: 8px 0;
		    font-weight: normal;
		    vertical-align: top;
		    white-space: nowrap;
		    line-height: 1.5;
		    cursor: default;
		    border: 0;
    	}
    	.wf-fixtable tbody td {
		    font-size: 16px;
		    color: #333333;
		    overflow: hidden;
		    font-family: "Microsoft Yahei";
		    padding: 8px 0;
		    border-bottom: solid 1px #e4eef5;
		    vertical-align: middle;
		    line-height: 1.5;
		    white-space: nowrap;
		    border: 0;
		}
    </style>
</head> 
<style type="text/css">


</style>

</head>
<body>
<div style="position:relative;">
	<div  id="treediv" >
		<div  id="toolbarDiv">
		    <ul id="demoTree" class="dtree" data-id="-1"></ul>
		</div>
	</div>
	<div id="editDiv">
		<div class="wf-layout" style="height: 60%;">
		<div class="wf-list-top">
			<div class="wf-search-bar">
				<form name="list"  id="list" action="" method="post" style="display:inline-block;width: 100%;">
					<div class="search" style="text-align: left;">
				    	文件类型：&nbsp;&nbsp;&nbsp;<input type="radio" name="fileType" checked="checked" style="width: 15px;"/>&nbsp;&nbsp;&nbsp;Excel (*.xls/*.xlsx)
				    		  &nbsp;&nbsp;&nbsp;<input type="radio" name="fileType" style="width: 15px;"/>&nbsp;&nbsp;&nbsp;DBF (*.dbf)
		            </div>
				</form>
			</div>
		</div>
		<div class="wf-list-wrap">
		<br><br><br><br><br><br>
				选择文件<br>
				案卷级：<input type="file"/><br>
				卷内级：<input type="file"/><br>
				文件级：<input type="file"/><br>
		
		</div>
		<!-- <div class="wf-list-ft" id="pagingWrap"></div> -->
	</div>
</div>	
	
<script src="${ctx}/lib/jquery-3.2.1.js"></script>
<script src="${ctx}/lib/popper.js"></script>
<script src="${ctx}/lib/layui/layui.js"></script>
<script src="${ctx}/lib/bootstrap-material/js/bootstrap-material-design.js"></script>
<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/met_toNameSpacePage.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lendLingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	</script>
<script>
var ctx = "${ctx}";
	layui.extend({
		dtree: '${ctx}/dtree/layui_ext/dtree/dtree'
	}).use(['element','layer', 'dtree'], function(){
		var element = layui.element, layer = layui.layer, dtree = layui.dtree, $ = layui.$;
		var dataJson=[{"id":"1","title":"收藏夹","parentId":"-1","checkArr":"0","basicData":-1},
		              {"id":"2","title":"公共收藏夹","parentId":"1","checkArr":"0","basicData":3833},
		              {"id":"3","title":"食品安全","parentId":"1","checkArr":"0","basicData":3171},
		              ];
		var commonTree = dtree.render({
		    elem: "#demoTree",
		    url: "${ctx}/dataTemp_getSonTreeJson.do",
		    data:dataJson,
		    defaultRequest:{nodeId:"nodeId",parentId:"parentId"},
		    dataFormat: "list",  //配置data的风格为list
		    ficon: ["1","-1"],
		    line:true,
		    type:"load",//分级加载
		    cache:true,
		    initLevel: "2",
		});	
		
	});
	
	function refreshTreeNode(treeId){
		$("div[dtree-click='itemNodeClick'][dtree-id='demoTree'][data-id='"+treeId+"']").click();
		$("div[dtree-click='itemNodeClick'][dtree-id='demoTree'][data-id='"+treeId+"']").click()
	}
	
	</script>
</body>
</html>
