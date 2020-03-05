 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
 <%@ include file="/common/headerbase.jsp"%>
<html>
    <head>
        <title>身份验证</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
    </head>
    <body>
   		<form id="verifyForm" method="post" name="verifyForm" >
   			<input type="hidden" id="id" name="id"  value="${roleid }"/>
   			<div class="add_verify">
				<div class="dw" style="margin-top:38px;">
			        <span>角色名称：</span>
			        <input id="name"  type="text" name="name" value="${name }"/>
			    </div>
			    <div class="dw">
			        <span>角色描述：</span>
			        <input id="describe"  type="text" name="describe" value="${roledescibe }"/>
			    </div>
			    <div class="dw">
			        <span>序号：</span>
			        <input id="index"  type="text" name="index" value="${roleindex }"/>
			    </div>
			    <div style="text-align:center;">
			        <button id="reset" type="reset" class="btn_qx">取消</button>
			        <button id="yea" type="button" class="btn_ok">确定</button>
			    </div>
			</div>
   		</form>
    </body>
    <script type="text/javascript">
			$("#reset").bind("click",function(){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			});
			
			$("#yea").bind("click",function(){
				var name = $("#name").val();
				if(isEmpty(name)) {
					alert("请填写角色名称");
					return;
				}
				var index = $("#index").val();
				if(isEmpty(index)) {
					alert("请填写角色序号");
					return;
				} else {
					if(isNotNumber(index)) {
						alert("角色序号只能为大于等于0的整数");
						return;
					}
				}
				$.ajax({
					url:"${ctx}/role_updateRole.do",
					type:"post",
					async:false,
					cache: false,
					data:$("#verifyForm").serialize(),
					success:function(msg){
						if(msg=="success"){
							parent.location.reload();
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						}else{
							alert("修改失败");
						}
					},
					error:function(){
						alert("系统错误请重试");
					}	
				});
			});
		</script>
</html>
