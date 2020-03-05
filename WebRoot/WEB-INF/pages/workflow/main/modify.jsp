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
<%-- 	<table class="infotan" width="100%">
	<tr>
	 <td class="bgs tc fb">修改工作流</td>
	</tr>
	</table>
	<p class="cbo m10">工作流名称：<input style="line-height:25px;width:200px;height: 25px;" id="wf_name" wf_id="${id }" type="text" value="${workflowname}"/></p>
	<div class="formBar pa" style="bottom:0px;width:100%;">  
	<ul class="mr5"> 
		<li><a id="ok" onclick="sub()" name="CmdView" class="buttonActive" href="javascript:;"><span>保存</span></a></li>
		<li><a id="cancel" onclick="cancel()" name="CmdView" class="buttonActive" href="javascript:;"><span>返回</span></a></li>
	</ul>
	</div> --%>
	
    <div id="superSearch" class="wf-super-search">
        <form method="get" action="#" class="wf-form wf-form-horizontal">
            <div class="wf-form-item">
                <label class="wf-form-label" for="">工作流名称：</label>
                <div class="wf-form-field">
                    <input type="text" class="wf-form-text" id="wf_name" name="wf_name" wf_id="${id }" value="${workflowname}">
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
		var wf_name_list= ${wf_name_list};
		var str=$("#wf_name").val().trim();
		var id=$("#wf_name").attr("wf_id");

		var staus="true";
		
		for(var i=0;i<wf_name_list.length;i++){
			if(str==wf_name_list[i].trim()){
				staus="false";
			}
		}
		
		if(""!=str&&"true"==staus){
			$.ajax({
				url:"${ctx}/mobileTerminalInterface_modifyWorkFlow.do",
				type:"post",
				data:{"wf_name":str,"id":id},
				async : false
			});
			parent.layer.closeAll();
			parent.window.location.href="${ctx}/mobileTerminalInterface_listWF.do?t="+new Date();
		}else if(""==str){
			alert("请输入流程名称");
		}else if("false"==staus){
			alert("流程名已经存在，请重新输入!");
		}
	}
	
	function cancel(){
		parent.window.location.href="${ctx}/mobileTerminalInterface_listWF.do";
		parent.layer.closeAll();
	}
	</script>
</html>