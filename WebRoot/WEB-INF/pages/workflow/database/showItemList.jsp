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
			<td class="bgs tc fb">选择收取的事项类别</td>
		</tr>
	</table>
	<form action="${ctx}/itemRelation_addItemRelation.do" name="itemrelationform" id="itemrelationform" method="post">
	<input type="hidden" name="wfItemRelation.item_id" id="item_id" value="${itemid}">
	<table width="100%">
		<tr>
			<td width="30%" class="bgs ls">事项类别：</td>
			<td width="80%">
				<select id="itemid" name="itemid"  style="width: 300px;">
					<option value="">请选择</option>
					<c:forEach items="${itemList}" var="list" >
						<option value="${list.lcid},${list.id},${list.vc_sxlx}">${list.vc_sxmc}</option>wfItem.vc_sxmc}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<div class="formBar pa" style="bottom: 0px; width: 100%;">
		<ul class="mr5">
			<li><a id="ok" onclick="sub();" name="CmdView" class="buttonActive" href="#"><span>确定</span></a></li>
			<li><a id="cancel" onclick="window.close();" name="CmdView" class="buttonActive" href="javascript:;"><span>返回</span></a></li>
		</ul>
	</div>
	<iframe style="display: none;" id="hid" name="hid"></iframe>
	</form>
</body>
<script type="text/javascript">
//确定保存数据
function sub(){
	var itemid = $('#itemid option:selected').val();//选中的文本
	var itemname = $('#itemid option:selected').text();//选中的值
	//alert(itemname);
	if(confirm("确定收取待办到"+itemname+"吗？")){
		window.returnValue = itemid;
	}
	window.close();
}
</script>
<%@ include file="/common/function.jsp"%>
</html>