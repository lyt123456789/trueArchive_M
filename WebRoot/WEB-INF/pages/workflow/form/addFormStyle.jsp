<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
	<link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
	<style>
	.laydate_body .laydate_bottom {height:32px;}
	.laydate_body .laydate_bottom .laydate_btn a {height:22px;line-height:22px;}
	</style>
</head>
<body>
    <div class="tw-layout">
        <div class="tw-container">
			<form id="formStyleForm" method="post" action="" class="tw-form">
			<input type="hidden" name="id" id="id" value="${id }"></input>
			<table class="tw-table tw-table-form">
				<colgroup>
                    <col  width="20%" />
                    <col />
                </colgroup>
                <tr>
					<td colspan="2" align="left">
						<font size="4" >设置页面风格</font>
					</td>
				</tr>
				<tr>
					<th>字体：</th>
					<td>
						<input type="text" name="font" id="font" class="tw-form-text" value="${font}" style="width: 300px"></input>
					</td>
				</tr>
				<tr>
					<th>字体大小：</th>
					<td>
						<input type="text" name="fontSize" id="fontSize" class="tw-form-text" value="${fontSize }" style="width: 300px"></input>
					</td>
				</tr>
				<tr>
					<th>行间距：</th>
					<td>
						<input type="text" name="verticalSpacing" id="verticalSpacing" class="tw-form-text" value="${verticalSpacing }" onblur="checkRecNo();" style="width: 300px"></input>
					</td>
				</tr>
				<tr>
					<th>日期时间格式：</th>
					<td>
						<select id="dateFormat" name="dateFormat" style="width: 300px">
							<option value="0" <c:if test="${dateFormat == '0' }">selected</c:if>>yyyy-MM-dd</option>
							<option value="1" <c:if test="${dateFormat == '1' }">selected</c:if>>yyyy-MM-dd HH:mm</option>
							<option value="2" <c:if test="${dateFormat == '2' }">selected</c:if>>yyyy-MM-dd HH:mm:ss</option>
							<%-- <option value="3" <c:if test="${dateFormat == '3' }">selected</c:if>>yyyy/MM/dd</option>
							<option value="4" <c:if test="${dateFormat == '4' }">selected</c:if>>yyyy/MM/dd HH:mm</option>
							<option value="5" <c:if test="${dateFormat == '5' }">selected</c:if>>yyyy/MM/dd HH:mm:ss</option> --%>
						</select>
					</td>
				</tr>
				<tr>
                    <th></th>
                    <td>
                    	<button id="btnSuperSearch" class="tw-btn-primary tw-btn-lg" type="button" onclick="addFormStyle();">
                       		 <i class="tw-icon-send"></i> 完成
                   		 </button>
                    </td>
                </tr>
			</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	function addFormStyle(){
		var fontSize = document.getElementById("fontSize").value;
		var verticalSpacing = document.getElementById("verticalSpacing").value;
		if(fontSize != null && fontSize != ''){
			if(isNaN(fontSize)){
				alert('请输入数字！');
				return;
			}
		}
		if(verticalSpacing != null && verticalSpacing != ''){
			if(isNaN(verticalSpacing)){
				alert('请输入数字！');
				return;
			}
		}
		$.ajax({
			url : '${ctx}/form_addFormStyle.do',
			type : 'POST',
			cache : false,
			async : false,
			data :$('#formStyleForm').serialize(),
			error : function() {
				alert('添加失败！');
			},
			success : function(result) {
				alert("添加成功！");
			}
		});
	}
</script>
<%@ include file="/common/function.jsp"%>
</html>
