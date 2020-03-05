<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
 <%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
	<body >
	<table class="infotan" width="100%">
		<tr>
			<td class="bgs tc fb">绑定延期事项</td>
		</tr>
	</table>
	<form action="${ctx}/itemRelation_addItemRelation.do" name="itemrelationform" id="itemrelationform" method="post">
	<input type="hidden" name="wfItemRelation.item_id" id="item_id" value="${itemid}">
	<table width="100%">
		<tr>
			<td width="30%" class="bgs ls">延期事项：</td>
			<td width="80%">
				<select id="delay_item_id" name="wfItemRelation.delay_item_id" onchange="getTables();" style="width: 300px;">
					<option value="">请选择</option>
					<c:forEach items="${list}" var="wfItem" varStatus="vs">
						<option value="${wfItem.id}">${wfItem.vc_sxmc}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td  class="bgs ls" >关联表：</td>
			<td>
				<select id="table_id" name="wfItemRelation.table_id" onchange="getColoum();" style="width: 300px;">
					<option value=''>请选择</option>
				</select>
			</td>
		</tr>
		<tr>
			<td  class="bgs ls">关联字段：</td>
			<td>
				<select id="coloum" name="wfItemRelation.coloum" style="width: 300px;">
					<option value=''>请选择</option>
				</select>
			</td>
		</tr>
		<tr>
			<td  class="bgs ls">预定结果：</td>
			<td>
				<input id="reserve_value" name="wfItemRelation.reserve_value" type="text" style="width: 300px"/>
			</td>
		</tr>
		<tr>
			<td  class="bgs ls">延期天数：</td>
			<td>
				<input id="delay_date" name="wfItemRelation.delay_date" type="text" style="width: 100px" 
				onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
				/>天
			</td>
		</tr>
		<!-- <tr>
			<td  class="bgs ls">重复申请：</td>
			<td>
				<input type="radio" name ="wfItemRelation.is_only" value="1" checked> 是
				<input type="radio" name ="wfItemRelation.is_only" value="2"> 否
			</td>
		</tr> -->
	</table>
	<div class="formBar pa" style="bottom: 0px; width: 100%;">
		<ul class="mr5">
			<li><a id="ok" onclick="sub();" name="CmdView" class="buttonActive" href="#"><span>保存</span></a></li>
			<li><a id="cancel" onclick="window.close();" name="CmdView" class="buttonActive" href="javascript:;"><span>返回</span></a></li>
		</ul>
	</div>
	<iframe style="display: none;" id="hid" name="hid"></iframe>
	</form>
</body>
<script type="text/javascript">
	function getTables() {
		var itemid = $('#delay_item_id option:selected').val();//选中的文本
		$.post("${ctx}/itemRelation_getTableByItemId.do?itemid="+itemid,"",function(res){	
			var tablelist = eval('('+res+')');
			$("#table_id").empty();
			$("#coloum").empty();
			$("#table_id").append("<option value=''>请选择</option>");
			$("#coloum").append("<option value=''>请选择</option>");
			for(var i=0; i<tablelist.length;i++){
				$("#table_id").append("<option value='"+tablelist[i].id+"'>"+tablelist[i].vc_tablename+"["+tablelist[i].vc_name +"]"+"</option>");
			}
		});
	}
	
	function getColoum(){
		var table_id = $('#table_id option:selected').val();	//选中的文本
		$.post("${ctx}/itemRelation_getFieldByTableId.do?tableid="+table_id,"",function(res){	
			var fieldlist = eval('('+res+')');
			$("#coloum").empty();
			$("#coloum").append("<option value=''>请选择</option>");
			for(var i=0; i<fieldlist.length;i++){
				$("#coloum").append("<option value='"+fieldlist[i].vc_fieldname+"'>"+fieldlist[i].vc_name+"</option>");
			}
		});
	}
	
	function sub(){
		//必填验证
		var	itemid = $('#delay_item_id option:selected').val();
		if(itemid==""){
			alert("延期事项不能为空");
			return false;
		}
		
		var table_id = $('#table_id option:selected').val();	
		if(table_id==""){
			alert("关联表不能为空");
			return false;
		}
		
		var coloum = $('#coloum option:selected').val();	
		if(coloum==""){
			alert("关联字段不能为空");
			return false;
		}
		
		var reserve_value = $('#reserve_value').val();	
		if(reserve_value==""){
			alert("预定结果不能为空");
			return false;
		}
		
		var delay_date = $('#delay_date').val();	
		if(delay_date==""){
			alert("延期天数不能为空");
			return false;
		}
		
		document.getElementById("itemrelationform").target="hid";
		document.itemrelationform.submit();
		window.returnValue= "ok"; 
    	window.close();
	}
</script>
<%@ include file="/common/function.jsp"%>
</html>