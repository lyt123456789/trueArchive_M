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
					<div class="search">
						<button type="button" id="endTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-chevron-circle-right"></i>结束
				    	</button>
				    	<button type="button" onclick="javascript:update();" target="_self" class="btn btn-write" style="float:left">
			    		<i class="fa fa-superpowers fa-lg"></i>续借
			    	    </button>
				    	<button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
    					    <i class="fa fa-arrow-left fa-lg"></i>归还
    				    </button>
    				    <button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-search"></i>查看预约
				    	</button>
    				    <!-- <button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-object-ungroup fa-lg"></i>借阅利用
	    				</button>
				    	<button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-print"></i>报表打印
				    	</button> -->
				    	<!-- <button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-sign-in"></i>移入档案库
				    	</button>
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-file-text fa-lg"></i>批量操作
	    				</button>
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-file-text fa-lg"></i>其他操作
	    				</button> -->
	    				<div class="fr">
	        				<input type="text" id="estitle" name="esTitle" value="${esTitle }" placeholder="" style="width: auto;">
	        				<button type="submit" class="btn_seargjc" style="width: 65px;margin-right: 20px;">
	        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
	        				</button>
	    				</div>
		            </div>
				</form>
			</div>
		</div>
		<div class="wf-list-wrap">
			<!-- <div class="loading"></div> --><!--2017-11-10-->
			<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
				<thead>
					<tr>
		    	  		<th width="3%"></th>
		    	  		<th align="center" width="14%">利用单编号</th>
		    	  		<th align="center" width="8%">登记人</th>
		    	  		<th align="center" width="8%">登记日期</th>
		    	  		<th align="center" width="8%">利用类型</th>
		    	  		<th align="center" width="10%">查档目的</th>
		    	  		<th align="center" width="8%">利用人</th>
		    	  		<th align="center" width="10%">单位</th>
		    	  		<th align="center" width="18%">身份证号</th>
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
					<td align="center"  title ="" >20190605220108876</td>
					<td align="center"  title ="" >administratoradmin</td>
					<td align="center"  title ="" >2019-06-05</td>
					<td align="center"  title ="" >实体</td>
					<td align="center"  title ="" >编史修志</td>
					<td align="center"  title ="" >丁伟华</td>
					<td align="center"  title ="" >副局长室</td>
					<td align="center"  title ="" >...</td>
	    		</tr>
	    		<!-- <tr>
	    			<td align="center" >
						<input type="checkbox"  name="checkbox"  value="11" class="check_view_state" id="11" style="display: none;">
						<label for="11"></label>
					</td>
					<td align="center"  title ="" ></td>
					<td align="center"  title ="" >3</td>
					<td align="center"  title ="" >1004</td>
					<td align="center"  title ="" >1999</td>
					<td align="center"  title ="" >1004-1999-6</td>
					<td align="center"  title ="" >A3</td>
					<td align="center"  title ="" >0025</td>
					<td align="center"  title ="" >县纪委常委会议，通案纪律</td>
	    		</tr> -->
			</table>
		</div>
		<div class="wf-list-ft" id="pagingWrap"></div>
	</div>
	<div class="wf-layout">
		<div class="wf-list-top">
			<div class="wf-search-bar">
				<form name="list"  id="list" action="" method="post" style="display:inline-block;width: 100%;">
					<div class="search">
				    	<button type="button" onclick="javascript:update();" target="_self" class="btn btn-write" style="float:left">
			    		<i class="fa fa-superpowers fa-lg"></i>续借
			    	    </button>
				    	<button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
    					    <i class="fa fa-arrow-left fa-lg"></i>归还
    				    </button>
    				    <!-- <button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-tasks fa-lg"></i>自定义分类
	    				</button>
	    				<button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-search"></i>综合查询
				    	</button>
				    	<button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-print"></i>打印
				    	</button>
				    	<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-file-text fa-lg"></i>附加文件
	    				</button>
				    	<button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-sign-in"></i>移入档案库
				    	</button>
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-file-text fa-lg"></i>批量操作
	    				</button>
	    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-write" style="float:left">
	    					<i class="fa fa-file-text fa-lg"></i>其他操作
	    				</button> -->
	    				<div class="fr">
	        				<input type="text" id="estitle" name="esTitle" value="${esTitle }" placeholder="" style="width: auto;">
	        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
	        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
	        				</button>
	    				</div>
		            </div>
				</form>
			</div>
		</div>
		<div class="wf-list-wrap">
			<!-- <div class="loading"></div> --><!--2017-11-10-->
			<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
				<thead>
					<tr>
		    	  		<th width="3%"></th>
		    	  		<th align="center" width="14%">利用单编号</th>
		    	  		<th align="center" width="8%">利用方式</th>
		    	  		<th align="center" width="8%">办理日期</th>
		    	  		<th align="center" width="8%">续借日期</th>
		    	  		<th align="center" width="8%">应归还日期</th>
		    	  		<th align="center" width="8%">归还日期</th>
		    	  		<th align="center" width="8%">续借次数</th>
		    	  		<th align="center" width="14%">档号</th>
		    	  		<!-- <th align="center" width="10%">文号</th>
		    	  		<th align="center" width="18%">责任者</th>
		    	  		<th align="center" width="10%">密级</th>
		    	  		<th align="center" width="10%">保管期限</th>
		    	  		<th align="center" width="10%">成文日期</th>
		    	  		<th align="center" width="10%">载体...</th>
		    	  		<th align="center" width="10%">页号</th>
		    	  		<th align="center" width="24%">文本</th> -->
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
					<td align="center"  title ="" >20190516174847635</td>
					<td align="center"  title ="" >摘抄</td>
					<td align="center"  title ="" ></td>
					<td align="center"  title ="" ></td>
					<td align="center"  title ="" ></td>
					<td align="center"  title ="" ></td>
					<td align="center"  title ="" ></td>
					<td align="center"  title ="" >A001-1930-001-0077-0001</td>
					<!-- <td align="center"  title ="" ></td>
					<td align="center"  title ="" >3</td>
					<td align="center"  title ="" >1004</td>
					<td align="center"  title ="" >1999</td>
					<td align="center"  title ="" >1004-1999-6</td>
					<td align="center"  title ="" >A3</td>
					<td align="center"  title ="" >0025</td>
					<td align="center"  title ="" >县纪委常委会议，通案纪律</td> -->
	    		</tr>
			</table>
		</div>
		<div class="wf-list-ft" id="pagingWrap1"></div>
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
		innerObj1=document.getElementById('pagingWrap1');  //todo
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj1);}   //todo
	};
	</script>
<script>
var ctx = "${ctx}";
	layui.extend({
		dtree: '${ctx}/dtree/layui_ext/dtree/dtree'
	}).use(['element','layer', 'dtree'], function(){
		var element = layui.element, layer = layui.layer, dtree = layui.dtree, $ = layui.$;
		var dataJson=[{"id":"1","title":"借阅管理","parentId":"-1","checkArr":"0","basicData":-1},
		              {"id":"2","title":"2019","parentId":"1","checkArr":"0","basicData":3833},
		              {"id":"3","title":"2018","parentId":"1","checkArr":"0","basicData":3171},
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
