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
		.search input {
            width: 20px;
            text-indent: 5px;
        }
        input[type="checkbox"]:checked + label {
             width: 56px;
             height: 15px;
             border: 0;
             background: none;
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
					<div class="search">
					  <label style="float:left" class="wf-form-label" for="">查询范围：</label>
                        <select style="float:left" class="wf-form-select" id="docnumbig" name="parentid">
                		<option value="">全部范围</option>
     					<option value="DF866F12">项目</option>
     					<option value="B632D4D3">案卷（盒）</option>
                        </select>
                        <select style="float:left" class="wf-form-select" id="docnumbig" name="parentid">
                		<option value=""></option>
     					<option value="DF866F12">项目</option>
     					<option value="B632D4D3">案卷（盒）</option>
                        </select>
                        <button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-search"></i>检索
				    	</button>
				    	<button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-search"></i>二次检索
				    	</button>
				    	<button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-search"></i>二次检索回退
				    	</button>
	    				<!-- <button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
	    					<i class="fa fa-trash-o fa-lg"></i>删除
	    				</button> -->
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-file-text fa-lg"></i>摘要
	    				</button>
	    				
	    				<input type="checkbox" autocomplete="off" class="btn btn-write" style="float:left"  tabindex="0" checked="">
	    				<label for="collkeyword_showRelevance" class="x-form-cb-label" style="float:left" id="ext-gen1098">显示关联</label>
	    				
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-file-photo-o fa-lg"></i>图片相关
	    				</button>
	    				<%-- <div class="fr">
	        				<input type="text" id="estitle" name="esTitle" value="${esTitle }" placeholder="">
	        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
	        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
	        				</button>
	    				</div> --%>
		            </div>
				</form>
			</div>
		</div>
		<div class="wf-list-wrap">
			<!-- <div class="loading"></div> --><!--2017-11-10-->
			<%-- <table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
				<thead>
					<tr>
		    	  		<th width="3%"></th>
		    	  		<th align="center" width="14%">订阅范围</th>
		    	  		<th align="center" width="14%">订阅条件</th>
		    	  		<th align="center" width="24%">订阅日期</th>
		    	  		<th align="center" width="14%">退订</th>
	                   </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="data" varStatus="status">
		    		<tr>
		    			<td align="center" >
							<input type="checkbox"  name="nameSpaceId"  value="${data.id}" class="check_view_state" id="${data.id}" style="display: none;">
							<label for="${data.id}"></label>
						</td>
						<td align="center"  title ="${data.esCreator}" >${data.esCreator }</td>
						<td align="center"  title ="${data.esTitle}" >${data.esTitle}</td>
						<td align="center"  title ="${data.esUrl}" >${data.esUrl}</td>
						<td align="center"  title ="${data.esDescription}" >${data.esDescription}</td>
						<td align="center"  title ="${data.esIdentifier}" >${data.esIdentifier}</td>
						<td align="center"  title ="${data.esDate}" >${data.esDate}</td>
						<td align="center"  title ="${data.esVersion}" >${data.esVersion}</td>
		    		</tr>
		    	</c:forEach>
		    	
		    	<tr>
	    			<td align="center" >
						<input type="checkbox"  name="checkbox"  value="11" class="check_view_state" id="11" style="display: none;">
						<label for="11"></label>
					</td>
					<td align="center"  title ="" >档案管理业务下的所有数据</td>
					<td align="center"  title ="" >([任意字段]包含"食品安全") </td>
					<td align="center"  title ="" >2015-02-28</td>
					<td align="center"  title ="" ></td>
	    		</tr>
			</table> --%>
		</div>
		<!-- <div class="wf-list-ft" id="pagingWrap"></div> -->
	</div>
	<div class="wf-layout">
		<div class="wf-list-top">
			<div class="wf-search-bar">
				<form name="list"  id="list" action="" method="post" style="display:inline-block;width: 100%;">
					<div class="search">
				    	<div style="float: left;font-size: 16px;line-height: 30px;height: 30px;margin-right: 15px;">临时申请栏</div>
				    	<div style="float: left;font-size: 16px;line-height: 30px;height: 30px;margin-right: 15px;">临时收藏栏</div>
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
	    					<i class="fa fa-trash-o fa-lg"></i>清空
	    				</button>
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-plus"></i>借阅利用
	    				</button>
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-file-text fa-lg"></i>发起档案利用流程
	    				</button>
	    				<%-- <div class="fr">
	        				<input type="text" id="estitle" name="esTitle" value="${esTitle }" placeholder="">
	        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
	        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
	        				</button>
	    				</div> --%>
		            </div>
				</form>
			</div>
		</div>
		<div class="wf-list-wrap">
			<!-- <div class="loading"></div> --><!--2017-11-10-->
			<%-- <table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
				<thead>
					<tr>
		    	  		<th width="3%"></th>
		    	  		<th align="center" width="14%">订阅范围</th>
		    	  		<th align="center" width="14%">订阅条件</th>
		    	  		<th align="center" width="24%">订阅日期</th>
		    	  		<th align="center" width="14%">退订</th>
	                   </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="data" varStatus="status">
		    		<tr>
		    			<td align="center" >
							<input type="checkbox"  name="nameSpaceId"  value="${data.id}" class="check_view_state" id="${data.id}" style="display: none;">
							<label for="${data.id}"></label>
						</td>
						<td align="center"  title ="${data.esCreator}" >${data.esCreator }</td>
						<td align="center"  title ="${data.esTitle}" >${data.esTitle}</td>
						<td align="center"  title ="${data.esUrl}" >${data.esUrl}</td>
						<td align="center"  title ="${data.esDescription}" >${data.esDescription}</td>
						<td align="center"  title ="${data.esIdentifier}" >${data.esIdentifier}</td>
						<td align="center"  title ="${data.esDate}" >${data.esDate}</td>
						<td align="center"  title ="${data.esVersion}" >${data.esVersion}</td>
		    		</tr>
		    	</c:forEach>
		    	
		    	<tr>
	    			<td align="center" >
						<input type="checkbox"  name="checkbox"  value="11" class="check_view_state" id="11" style="display: none;">
						<label for="11"></label>
					</td>
					<td align="center"  title ="" >档案管理业务下的所有数据</td>
					<td align="center"  title ="" >([任意字段]包含"食品安全") </td>
					<td align="center"  title ="" >2015-02-28</td>
					<td align="center"  title ="" ></td>
	    		</tr>
			</table> --%>
		</div>
		<!-- <div class="wf-list-ft" id="pagingWrap"></div> -->
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
		var dataJson=[{"id":"1","title":"档案管理","parentId":"-1","checkArr":"0","basicData":-1},
		              {"id":"2","title":"如东县档案局","parentId":"1","checkArr":"0","basicData":3833},
		              {"id":"3","title":"文书档案","parentId":"2","checkArr":"0","basicData":3171},
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
