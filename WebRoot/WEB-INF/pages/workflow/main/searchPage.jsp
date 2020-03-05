<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" media="screen"/>
</head>
<body>

    <div class="tw-layout">
        <div class="tw-container">
            <form method="get" id="form" name="form" action="#" class="tw-form">
            <table class="tw-table tw-table-form">
                <colgroup>
                    <col  width="30%" />
                    <col />
                </colgroup>
                <tr>
                    <th>标题</th>
                    <td><input type="text" class="tw-form-text" id="wfTitle" name="wfTitle" value="${wfTitle}"></td>
                </tr>
                <tr>
                    <th>事项类别</th>
                    <td>
                    	<select class="tw-form-select" id="itemType" name="itemType">
		                	<option value=""></option>
			 				<option value="0" <c:if test="${itemType eq '0'}">selected="selected"</c:if>>发文</option>
			 				<option value="1" <c:if test="${itemType eq '1'}">selected="selected"</c:if>>收文</option>
		                </select>
                    </td>
                </tr>
                <tr>
                    <th>发文日期(开始)</th>
                    <td><input type="text" class="tw-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}"></td>
                </tr>
                <tr>
                    <th>发文日期(结束)</th>
                    <td><input type="text" class="tw-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo" value="${commitTimeTo}"></td>
					<input type="hidden" id="itemid" name="itemid" value="${itemid}" />
                </tr>
                <tr>
                    <th>文号</th>
                    <td><input type="text" class="tw-form-text" id="wh" name="wh" value="${wh}"></td>
                </tr>
                <tr>
                    <th>来文单位</th>
                    <td><input type="text" class="tw-form-text" id="lwdw" name="lwdw" value="${lwdw}"></td>
                </tr>
            </table>
            </form>
        </div>
    </div>
    <script type="text/javascript">
    	function sub(){
    		var wfTitle = document.getElementById("wfTitle").value;
    		var commitTimeFrom = document.getElementById("commitTimeFrom").value;
    		var commitTimeTo = document.getElementById("commitTimeTo").value;
    		var itemType = $('#itemType option:selected').val();
    		var wh = document.getElementById("wh").value;
    		var lwdw = document.getElementById("lwdw").value;
    		return '{"wfTitle":"'+wfTitle+'","itemType":"'+itemType+'","commitTimeFrom":"'+commitTimeFrom+'","commitTimeTo":"'+commitTimeTo+'","wh":"'+wh+'","lwdw":"'+lwdw+'"}';
    	}
    </script>
</body>
</html>
