<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle")%></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top" style="height:62px;">
		<div class="wf-search-bar">
			<div class="wf-top-tool">
	            <a class="wf-btn" href="${ctx}/field_toAddJsp.do" target="_self">
	                <i class="wf-icon-plus-circle"></i> 新建
	            </a>
	            <a class="wf-btn-primary" onclick="modify_row();">
	                <i class="wf-icon-pencil"></i> 修改
	            </a>
	            <a class="wf-btn-danger" href="javascript:del()" target="_self" title="确定要删除吗？" warn="请选择一个对象">
	                <i class="wf-icon-minus-circle"></i> 删除
	            </a>
	        </div>
		</div>
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/field_getFieldList.do" >
	<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
				    	<th width="4%">
						    <input type="checkbox" name="selAll" id="selAll" onclick="sel();">
						</th>
			    		<th width="5%" align="center">
				    		序号
				    	</th>
				    	<th width="14%" align="center">
				    		名称
				    	</th>
				    	<th width="15%" align="center">
				    		字段名
				    	</th>
				    	<th width="8%" align="center">
				    		字段类型
				    	</th>
				    	<th width="8%" align="center">
				    		字段长度
				    	</th>
				    	<th width="8%" align="center">
				    		是否为空
				    	</th>
				    	<th width="14%" align="center">
				    		默认值
				    	</th>
				    	<th width="14%" align="center">
				    		描述
				    	</th>
			    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="d" varStatus="n">
			    	<tr>
			    		<td align="center">
				    		<input type="checkbox" name="selid" value="${d.id}" >
				    	</td>
				    	<td align="center" fieldid="${d.id}">
				    		${(selectIndex-1)*10+n.count}
				    	</td>
				    	<td>
				    		${d.vc_name}
				    	</td>
				    	<td >
				    		${d.vc_fieldname}
				    	</td>
				    	<td align="center">
				    		<c:if test="${d.i_fieldtype=='0'}">字符</c:if>
				    		<c:if test="${d.i_fieldtype=='1'}">日期</c:if>
				    		<c:if test="${d.i_fieldtype=='2'}">数字</c:if>
				    		<c:if test="${d.i_fieldtype=='3'}">大文本</c:if>
				    	</td>
				    	<td align="center">
				    		${d.i_length}
				    	</td>
				    	<td align="center">
				    		<c:if test="${d.b_value=='1'}">是</c:if>
				    		<c:if test="${d.b_value=='0'}">否</c:if>
				    	</td>
				    	<td align="center">
				    		${d.vc_value}&nbsp;
				    	</td>
				    	<td>
				    		${d.vc_comment}&nbsp;
				    	</td>
			    	</tr>
			    </c:forEach>
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
			skipUrl="<%=request.getContextPath()%>"+"/field_getFieldList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};	


	function sel() {
		var selAll = document.getElementById('selAll');
		var selid = document.getElementsByName('selid');
		for ( var i = 0; i < selid.length; i++) {
			if (selAll.checked) {
				selid[i].checked = true;
			} else {
				selid[i].checked = false;
			}
		}
	}

	function modify_row() {
		var id = $(".wf-actived td:eq(1)").attr("fieldid");
		if (id) {
			location.href = "${ctx}/field_toEditJsp.do?id=" + id;
		} else {
			alert('请选择要修改的字段！');
		}
	}

	function del() {
		var selid = document.getElementsByName('selid');
		var ids = "";
		for ( var i = 0; i < selid.length; i++) {
			if (selid[i].checked) {
				ids += selid[i].value + ",";
			}
		}
		if (ids.length > 0) {
			ids = ids.substring(0, ids.length - 1);
		} else {
			var id=$(".wf-actived td:eq(1)").attr("fieldid");
			if(id!=null && id.length>0){
				ids = id;
			}else{
				alert('请选择一个删除对象');
				return;
			}
			
		}
		if (!confirm('确定删除所选吗？'))
			return;
		location.href = "${ctx}/field_del.do?ids=" + ids;
	}
</script>
</html>