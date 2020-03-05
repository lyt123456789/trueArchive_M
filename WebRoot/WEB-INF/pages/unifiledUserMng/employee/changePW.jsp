<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
   <link href="${ctx}/assets-common/css/common.css" type="text/css" rel="stylesheet" />
	<style>
		.new-htable {
			margin-top:20px;
			width:96%;
			margin-left:3%;
		}
		.new-htable tr th{
			text-align:right;
			color:#333333;
			font-size:16px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;

		}
		.new-htable tr td{
		text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;
		}
		.new-htable .wf-form-text{
			width:354px;
			text-indent:6px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.new-htable select{
			width:163px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.wf-form-date{
			width:133px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
	</style>
</head>
<body>
	<div class="tw-layout">
		<div class="tw-container">
		    <form method="get" id="passWForm" name="empForm" action="#" class="tw-form">
				<table class="tw-table-form tm-table-form">
			        <colgroup>
			            <col width="20%" />
			            <col />
			           
			        </colgroup>
			        <tr>
				   		
				   		<th>新密码</th>
			    	 	<td>
			    	 		<input id="newPW"  name="newPW" style="width: 260px;"></input>
			    	 		<input id="passWords"  name="passWords" value="${passWords }" type="hidden"></input>
			    	 		<input id="personIds"  name="personIds" value="${personIds }" type="hidden"></input>
			    	 	</td>
				    </tr>
			        <tr>
				   		
				   		<th>注</th>
			    	 	<td>
			    	 		密码必须同时包含字母、数字、特殊字符！且长度在8-16位之间！
			    	 	</td>
				    </tr>
				    
			    	 <tr>
			    	 	<td align="center" colspan="4">
			    	 		<button class="wf-btn-primary" type="button"  style="size: 16px;margin-top: 10px;" onclick="save();">
			                    <i class="wf-icon-plus-circle" ></i> 确认
			                </button>
			    	 	</td>
			    	 </tr>
					
			    </table>
		    </form>
		</div>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	function save(){
		
		var newPW = $('#newPW').val();
		var passWords = $('#passWords').val();
		var personIds = $('#personIds').val();
		
		if(newPW == null || newPW == ''){
			alert("修改的密码不能为空");
			return;
		}
		
		newPW = newPW.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
		var bo1=/^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{8,16}$/;
		if(!bo1.test(newPW)){
			alert("密码必须同时包含字母、数字、特殊字符！且长度在8-16位之间！");
			return false;
		}
		
		$.ajax({
			url : 'siteManage_changePW.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :$('#passWForm').serialize(),
			error : function(e) {  
				alert('AJAX调用错误(siteManage_changePW.do)');
			},
			success : function(msg) {
				if(msg == 'success'){
					alert('批量设置密码成功！');
				}else{
					alert('批量设置密码失败！');
				}
				window.parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}
		});
	}
</script>
</html>