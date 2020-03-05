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
	function sub(){
		var wf_name_list=${wf_name_list};
		var str=$("#wf_name").val();
		var staus="true";
		
		for(var i=0;i<wf_name_list.length;i++){
			if(str==wf_name_list[i]){
				staus="false";
			}
		}
		if(str!='' && staus=="true"){
			$.ajax({
				url:"${ctx}/mobileTerminalInterface_saveWorkFlow.do",
				type:"post",
				data:{
					"wf_name":str
				},
				async : false,
				error: function(){
					parent.layer.alert('保存流程出错！');
				},
				success: function(msg){
					if (msg!= "1") {
						parent.layer.alert("系统license未验证通过");
						parent.layer.closeAll();
					}else{
						parent.layer.closeAll();
						parent.window.location.href="${ctx}/mobileTerminalInterface_listWF.do?t="+new Date();
					}
				}
			});
		}else if(""==str){
			parent.layer.alert("请输入流程名称");
		}else if("false"==staus){
			parent.layer.alert("流程名已经存在，请重新输入!");
		}
	}
	
	function cancel(){
		parent.window.location.href="${ctx}/mobileTerminalInterface_listWF.do";
		parent.layer.closeAll();
	}
</script>
</html>