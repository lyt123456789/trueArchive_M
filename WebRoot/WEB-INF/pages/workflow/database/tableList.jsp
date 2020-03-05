<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top" style="height:62px;">
		<div class="wf-search-bar">
			<div class="wf-top-tool">
	            <a class="wf-btn" onclick="" href="${ctx}/table_toAddJsp.do?workflowId=${workflowId }&vc_parent=${vc_parent}">
	                <i class="wf-icon-plus-circle"></i> 新建
	            </a>
	            <a class="wf-btn-green" onclick="javascript:refTable();" href="javascript:;">
	                <i class="wf-icon-check-circle"></i> 选择
	            </a>
	            <a class="wf-btn-primary" onclick="modify_row();">
	                <i class="wf-icon-pencil"></i> 修改
	            </a>
	            <a class="wf-btn-danger" onclick="javascript:del();" href="javascript:;"  title="确定要删除吗？" warn="请选择一个对象">
	                <i class="wf-icon-minus-circle"></i> 删除
	            </a>
	        </div>
		</div>
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" >
	<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
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
					    	<td title="${d.vc_name}">
					    		${d.vc_name}
					    	</td>
					    	<td title="${d.vc_tablename}">
					    		${d.vc_tablename}
					    	</td>
					    	<td align="center" title="<fmt:formatDate value="${d.vc_creatdate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					    		<fmt:formatDate value="${d.vc_creatdate}" pattern="yyyy-MM-dd"/>
					    	</td>
				    	</tr>
				    </c:forEach>
				</tbody>
			</table>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
	</div>
	</form>
</div>

</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/table_getTableList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};


	function refTable(){
		var url ="${ctx}/table_toRefJsp.do?workflowId=${workflowId }&num="+Math.random();
		//用layer的模式打开
		layer.open({
			title:'选择其他流程业务表',
			type:2,
			area:['350px', '200px'],
			btn:["确定","取消"],
			content: url,
			yes:function(index,layero){
			 	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
	            iframeWin.ref();
			 	layer.close(index);
			 	window.location.href = "${ctx}/table_getTableList.do?workflowId=${workflowId }";
			}
		}); 
		/* var ret=window.showModalDialog("${ctx}/table_toRefJsp.do?num="+Math.random(),null,"dialogWidth:350px;dialogHeight:150px;help:no;status:no");
		if(true==ret){
			window.location.href="${ctx}/table_getTableList.do";
		}else{
		} */
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
			var id=$(".wf-actived td:eq(1)").attr("tableid");
			if(id!=null && id.length>0){
				ids = id;
			}else{
				alert('请选择一个删除对象');
				return;
			}
			
		}
		if(!confirm('确定删除所选吗？'))return;
		location.href = "${ctx}/table_del.do?workflowId=${workflowId }&ids="+ids;
	}

	function modify_row(){
		 var id=$(".wf-actived td:eq(1)").attr("tableid");
		 if(id){
		 	location.href = "${ctx}/table_toEditJsp.do?workflowId=${workflowId}&id=" + id;
		 }else{
			alert('请选择要修改的表！');
	     }
	}
	</script>
</html>
