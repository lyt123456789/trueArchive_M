<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="keywords" content="">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle")%></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
	<link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="tw-layout">
        <div class="tw-container">
            <table class="tw-table tw-table-form">
				<colgroup>
                    <col  width="20%" />
                    <col />
                </colgroup>
				<tr>
					<th><em>*</em>IP：</th>
					<td>
						<input type="text" class="tw-form-text" id="ip" name="ip" value=""/>
						<span style="color: red;">推送服务器的IP</span>
						<span class="tw-warning tw-warning-ip"><i></i>请输入IP！</span>
					</td>
				</tr>
				<tr>
					<th><em>*</em>PORT：</th>
					<td>
						<input type="text" class="tw-form-text" id="port" name="port"/>
						<span style="color: red;">推送服务的端口</span>
						<span class="tw-warning tw-warning-port"><i></i>请输入端口号！</span>
					</td>
				</tr>
				<tr>
					<th><em>*</em>TOKEN：</th>
					<td>
						<input type="text" class="tw-form-text" id="token" name="token"/>
						<span style="color: red;">终端的唯一标识，在工作流的T_PUSH里可以查到</span>	
						<span class="tw-warning tw-warning-token"><i></i>请输入终端的唯一标识！</span>
					</td>
				</tr>
				<tr>
					<th>设置连接超时时间(单位:秒)：</th>
					<td>
						<input type="text" class="tw-form-text" id="timeout" name="timeout"/>
						<span style="color: red;">如果不设置，默认5秒</span>	
					</td>
				</tr>
				<tr>
                    <th></th>
                    <td>
                    	<button id="btnSuperSearch" class="tw-btn-primary tw-btn-lg" type="submit">
                       		 <i class="tw-icon-send"></i> 提交
                   		 </button>
                    </td>
                </tr>
			</table>
			<br/>
			<br/>
			<table class="tw-table tw-table-form">
				<tr>
					<td width="20%" class="bgs ls">结果：</td>
					<td width="80%" id="result">
                    	
					</td>
				</tr>
			</table>
		</div>
	</div>

    <script type="text/javascript">
	    $(document).ready(function(){
			$('#btnSuperSearch').click(function(){
	    		$.ajax({
					url : '${ctx}/push_checkPushServer.do',
					type : 'POST',  
					cache : false,
					async : false, 
					data:{
	    		    	"ip":$('#ip').val(),
	    		    	"port":$('#port').val(),
	    		    	"token":$('#token').val(),
	    				"timeout":$('#timeout').val()
	    		    },
					error : function() {
						alert('AJAX调用错误(push_checkPushServer.do)');
					},
					success : function(msg) { 
						var data = eval("("+msg+")");
						var result = data.result;
						var code = data.code;
						if("false" == result){
							if('10001' == code){
								alert("IP不能为空");
								$('#ip').focus();
								document.getElementById("result").innerHTML = "<span>IP不能为空</span>";
							}else if('10002' == code){
								alert("端口不能为空");
								$('#port').focus();
								document.getElementById("result").innerHTML = "<span>端口不能为空</span>";
							}else if('10003' == code){
								alert("token不能为空");
								$('#token').focus();
								document.getElementById("result").innerHTML = "<span>token不能为空</span>";
							}else if('10004' == code){
								alert("IP不符合规则");
								$('#ip').focus();
								document.getElementById("result").innerHTML = "<span>IP不符合规则</span>";
							}else if('10005' == code){
								alert("token不符合规则");
								$('#token').focus();
								document.getElementById("result").innerHTML = "<span>token不符合规则</span>";
							}else if('10006' == code){
								alert("请求失败，请重试");
								document.getElementById("result").innerHTML = "<span>请求失败，请重试</span>";
							}else if('10007' == code){
								document.getElementById("result").innerHTML = "<span>端口不通</span>";
							}else if('10008' == code){
								document.getElementById("result").innerHTML = "<span>IP连接超时</span>";
							}
						}else{
							document.getElementById("result").innerHTML = "<span>"+code+"</span>";
						}
					}
				});
			});
			
			$("#ip").blur(function(){
				var ip = $('#ip').val();
				if(null != ip && '' != ip){
					$(".tw-warning-ip").css("display","none");
				}else{
					$(".tw-warning-ip").css("display","inline");
					$('#ip').focus();
				}
			});
			
			$("#port").blur(function(){
				var port = $('#port').val();
				if(null != port && '' != port){
					$(".tw-warning-port").css("display","none");
				}else{
					$(".tw-warning-port").css("display","inline");
					$('#port').focus();
				}
			});
			
			$("#token").blur(function(){
				var token = $('#token').val();
				if(null != token && '' != token){
					$(".tw-warning-token").css("display","none");
				}else{
					$(".tw-warning-token").css("display","inline");
					$('#token').focus();
				}
			});
			
	    });

    </script>
    
</body>
</html>
