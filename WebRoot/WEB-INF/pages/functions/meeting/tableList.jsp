<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>中威科技工作流表单系统</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="panelBar"> 
		<ul class="toolBar">
			<li><a class="add" onclick="" href="${ctx}/table_toAddJsp.do?vc_parent=${vc_parent}" ><span>新建</span></a></li>
			<li><a class="select" onclick="javascript:refTable();" href="javascript:;" ><span>选择</span></a></li>
			<li><a class="edit" onclick="modify_row();"><span>修改</span></a></li>
			<li><a onclick="javascript:del();" href="javascript:;" class="delete"><span>删除</span></a></li>			
		</ul>
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" >
    	<div id="w_list_print">
			<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
				<thead>
			    	<tr height="30px">
			    		<th width="5%">
				    		<input type="checkbox" name="selAll" id="selAll" onclick="sel();">
				    	</th>
			    		<th width="5%" align="center">
				    		序号
				    	</th>
				    	<th width="30%" align="center" >
				    		名称
				    	</th>
				    	<th width="30%" align="center">
				    		表名
				    	</th>
				    	<th width="15%" align="center">
				    		创建时间
				    	</th>
			    	</tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${list}" var="d" varStatus="n">
				    	<tr height="30px">
				    		<td align="center">
				    			<input type="checkbox" name="selid" value="${d.id}">
				    		</td>
					    	<td align="center" tableid="${d.id}">
					    		${(selectIndex-1)*10+n.count}
					    	</td>
					    	<td >
					    		${d.vc_name}
					    	</td>
					    	<td>
					    		${d.vc_tablename}
					    	</td>
					    	<td align="center">
					    		<fmt:formatDate value="${d.vc_creatdate}" pattern="yyyy-MM-dd"/>
					    	</td>
				    	</tr>
				    </c:forEach>
				</tbody>
		    </table>
    	</div>
    	<div class="formBar pa" style="bottom:0px;width:100%;">  
			<div id="div_god_paging" class="cbo pl5 pr5"></div> 
		</div>
		</form>
    </body>
   
   	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/table_getTableList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		}
	$("table.list", document).cssTable();

	function refTable(){
		var ret=window.showModalDialog("${ctx}/table_toRefJsp.do?num="+Math.random(),null,"dialogWidth:350px;dialogHeight:150px;help:no;status:no");
		if(true==ret){
			window.location.href="${ctx}/table_getTableList.do";
		}else{
			//
		}
	}

	function sel(){
		var selAll = document.getElementById('selAll');
		var selid = document.getElementsByName('selid');
		for(var i = 0 ; i < selid.length; i++){
			if(selAll.checked){
				selid[i].checked = true;
			}else{
				selid[i].checked = false;
			}
		}
	}

	function del(){
		var selid = document.getElementsByName('selid');
		var ids = "";
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].checked){
				ids += selid[i].value + ",";
			}
		}
		if(ids.length > 0){
			ids = ids.substring(0, ids.length - 1);
		}else {
			alert('请选择一个删除对象');
			return;
		}
		if(!confirm('确定删除所选吗？'))return;
		location.href = "${ctx}/table_del.do?ids="+ids;
	}

	function modify_row(){
		 var id=$(".selected td:eq(1)").attr("tableid");
		 if(id){
		 	location.href = "${ctx}/table_toEditJsp.do?id=" + id;
		 }else{
			alert('请选择要修改的表！');
	     }
	}
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
