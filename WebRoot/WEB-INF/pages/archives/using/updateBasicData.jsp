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
</head>
<body>
    <div class="tw-layout">
        <div class="tw-container">
			<form id="itemform" method="post" action="${ctx}/using_saveBasicData.do" class="tw-form">
			<table class="tw-table tw-table-form">
				<colgroup>
                    <col  width="30%" />
                    <col />
                </colgroup>
				<tr>
					<th>页面展示值：</th>
					<td>
						<input type="text" name="basicdata.dataName" id="basicdata.dataName" class="tw-form-text" value="${basicdata.dataName }">
						<input type="hidden" name="basicdata.id" id="basicdata.id" class="tw-form-text" value="${basicdata.id }">
					</td>
				</tr>
				<tr>
					<th>数据库展示值：</th>
					<td>
						<input type="text" name="basicdata.feilName" id="basicdata.feilName" class="tw-form-text"  value="${basicdata.feilName }">
					</td>
				</tr>
                <tr>
                    <th>隶属类别名称：</th>
                   <td>
                   		<input type="text" name="basicdata.typeName" id="basicdatatypeName" class="tw-form-text"  value="${basicdata.typeName }">
						<select type="text" name="typeName" id="typeName" class="tw-form-text"  onchange="change()">
						<c:forEach items="${typeList}" var="item" varStatus="status">
							<option value="${item.type }">${item.typeName }</option>
						</c:forEach>
						</select>
					</td>
                </tr>
                <tr>
                    <th>隶属类别：</th>
                   <td>
						<input type="text" name="basicdata.type" id="type" class="tw-form-text"  value="${basicdata.type }">
					</td>
                </tr>
                  <tr>
                    <th>排序：</th>
                   <td>
						<input type="text" name="basicdata.numIndex" id="basicdata.numIndex" class="tw-form-text"  value="${basicdata.numIndex }">
					</td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                    	<button id="btnSuperSearch" class="tw-btn-primary tw-btn-lg" type="submit" onclick="checkForm();">
                       		 <i class="tw-icon-send"></i> 提交
                   		 </button>
                   		 <button class="tw-btn tw-btn-lg" onclick="window.history.go(-1);">
                        	<i class="tw-icon-minus-circle"></i> 返回
                   		 </button>
                    </td>
                </tr>
			</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	function change(){
		var type = $("#typeName").val();
		var typeName = $("#typeName option:selected").text();
		$("#type").val(type);
		$("#basicdatatypeName").val(typeName);
	}
	
</script>
<%@ include file="/common/function.jsp"%>
</html>
