<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="keywords" content="">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>    
</head>
<body>
	<div class="tw-super-search">
		<form name=form1 id="textForm" method=post action="${ctx}/role_toEditPassWord.do" class="tw-form tw-form-horizontal-lg">
			<div class="tw-form-item">
              	<label class="tw-form-label"><em>*</em>姓名：</label>
              	<div class="tw-form-field">
              		<input type="text" name="menuName" id="menuName" value="${jyxm}" class="tw-form-text"  maxlength="100"  readonly="readonly"/>
              		<%-- <span id="menuNametip"><i>${jyxm}</i></span> --%>
             	</div>
       		</div>
       		<div class="tw-form-item">
              	<label class="tw-form-label"><em>*</em>新密码：</label>
              	<div class="tw-form-field">
              		<input type="password" name="password" class="SmallInput"  Style="width:280" maxlength="20" class="tw-form-text"/>&nbsp;<font color="red">*</font>
             		 <span id="menuSimpleNametip"><i></i></span>
             	</div>
       		</div>
       		<div class="tw-form-item">
              	<label class="tw-form-label"><em>*</em>确认密码：</label>
              	<div class="tw-form-field">
              		<input type="password" name="PwdConfirm" class="SmallInput"  Style="width:280" maxlength="20" class="tw-form-text"/>&nbsp;<font color="red">*</font>
             		 <span id="menuSimpleNametip"><i></i></span>
             	</div>
       		</div>
        	
        	<div class="tw-form-action wf-action-left">
                <div id="btnSuperSearch" class="tw-btn-primary" onclick="act_zbdj();" style="top: 140px;/* float: left; */position: fixed;left: 155px;">
                    <i class="tw-icon-search"></i> 提 交
                </div>
                <!-- <div class="tw-btn" onclick="javascript:parent.layer.closeAll();">
                    <i class="tw-icon-minus-circle"></i> 关闭
                </div> -->
       		</div>
   		</form>
	</div>
</body>
<script type="text/javascript">	
function act_zbdj(){
	if(check1()){
			var data = $("#textForm").serialize();
//			data = encodeURI(data);
			$.ajax({
			   type: "POST",
			   url:  "${ctx}/role_updateUserPassword.do",
			   data: data,
			   async:false,
			   success: function(msg){
				   alert(msg);
			   }
			});
	}
}


function check1() {
	if(document.form1.password.value=="") {
	  alert("请填写新密码!");
	  document.form1.password.focus();
	  return false;
	}
	else if(document.form1.PwdConfirm.value=="") {
	  alert("请填写确认密码!");
	  document.form1.PwdConfirm.focus();
	  return false;
	}	  
	else if(document.form1.PwdConfirm.value!=document.form1.password.value) {
	  alert("确认密码与新密码不一致!");
	  document.form1.password.focus();
	  return false;
	}	  
	else return true;
	}
	
</script>
<%@ include file="/common/widgets.jsp"%>