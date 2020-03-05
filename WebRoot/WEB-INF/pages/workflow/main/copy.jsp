<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
</head>
<body>
    <div id="superSearch" class="wf-super-search">
        <form method="get" action="#" class="wf-form wf-form-horizontal">
        	<input type="hidden" name="old_id" id="old_id" value="${wfMain.wfm_id}">
			<input type="hidden" name="old_name" id="old_name" value="${wfMain.wfm_name}">
            <div class="wf-form-item">
                <label class="wf-form-label" for="">工作流名称：</label>
                <div class="wf-form-field">
                    <input type="text" class="wf-form-text" id="wf_name" name="wf_name">
                </div>
            </div>
            <div class="wf-form-action wf-action-left">
                <button id="btnSuperSearch" class="wf-btn-primary" type="button" onclick="sub();">
                    <i class="wf-icon-search"></i> 保存
                </button>
                <button class="wf-btn" type="button" onclick="cancel();">
                    <i class="wf-icon-minus-circle"></i> 关闭
                </button>
            </div>
        </form>
    </div>
</body>
<script type="text/javascript">
function cancel(){
	window.returnValue=false;
	window.close();
}

function sub(){
	var wf_name = $("#wf_name").val();
	wf_name = $.trim(wf_name);
	var old_name = $("#old_name").val();
	var old_workflowId = $("#old_id").val();
	if(wf_name!=null && wf_name!=''){
		
	}else{
		alert("请填写流程名称！");	
		return;
	}
	if(confirm("确定复制流程["+old_name+"]且重新命名生成流程为["+wf_name+"]？")){
		//ajax
		$.ajax({
			url : '${ctx}/mobileTerminalInterface_copyWorkFlow.do',
			type : 'POST',  
			cache : false,
			async : true,
			data : {
				'old_workflowId':old_workflowId, 'wf_name':wf_name
			},
			error : function() {
				alert('AJAX调用错误(table_getAllnodes.do)');
			},
			success : function(result) {
				var res = eval("("+result+")");
				if(res.success){
					parent.layer.alert("复制流程成功");
					parent.layer.closeAll();
					parent.window.location.href="${ctx}/mobileTerminalInterface_listWF.do?t="+new Date();
				}else{
					parent.layer.alert("复制流程失败");
				}
			}
		});
	}
}
</script>
<%@ include file="/common/function.jsp"%>
</html>
