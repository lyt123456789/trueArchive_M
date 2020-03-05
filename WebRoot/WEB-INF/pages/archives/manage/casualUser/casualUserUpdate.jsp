 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
 <%@ include file="/common/headerbase.jsp"%>
<html>
    <head>
        <title>添加角色</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
    	<style type="text/css">
    		.add_verify{
    			height:320px;
    			top:150px;;
    		}
    		.txtRight {
    			text-align:right;
    		}
    		.add_verify .dw {
    			width:320px;
    		}
    		.add_verify .dw input {
    			width:293px;
    		}
    		.wf-input-datepick input{
   				height:22px!important;
    		}
    	</style>
    </head>
    <body>
   		<form id="verifyForm" method="post" name="verifyForm" >
   			<input type="hidden" id="id" name="id" value="${id }"/>
   			<div class="add_verify">
	   			<table>
	   				<tr>
	   					<td class="txtRight">临时用户账号：</td>
	   					<td>
	   						<div class="dw">
				        		<input id="name"  type="text" name="name" value="${name }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">临时用户密码：</td>
	   					<td>
	   						<div class="dw">
				        		<input id="password"  type="text" name="password" value="${password }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">权限生效时间：</td>
	   					<td>
	   						<div class="dw">
				        		<input class="wf-form-text wf-form-date" id="startTime"  type="text" name="startTime" value="" readonly="readonly"/>
			        			~
			        			<input class="wf-form-text wf-form-date" id="endTime"  type="text" name="endTime" value="" readonly="readonly"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">临时用户描述：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="describe"  type="text" name="describe" value="${describe }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">临时用户序号：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="index"  type="text" name="index" value="${index }"/>
				    		</div>
				    	</td>	
	   				</tr>
	  			</table>
			    <div style="text-align:center;position:absolute;bottom:0;width:100%;left:0;">
			        <button id="reset" type="reset" class="btn_qx">取消</button>
			        <button id="yes" type="button" class="btn_ok">确定</button>
			    </div>
			    <input type="hidden" name="menuFlag" value="no"/>
			</div>
   		</form>
    </body>
    <script type="text/javascript">
	    Date.prototype.Format = function(fmt) { //author: meizz   
		    var o = {   
		      "M+" : this.getMonth()+1,                 //月份   
		      "d+" : this.getDate(),                    //日   
		      "h+" : this.getHours(),                   //小时   
		      "m+" : this.getMinutes(),                 //分   
		      "s+" : this.getSeconds(),                 //秒   
		      "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		      "S"  : this.getMilliseconds()             //毫秒   
		    };   
		    if(/(y+)/.test(fmt))   
		      fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		    for(var k in o)   
		      if(new RegExp("("+ k +")").test(fmt))   
		    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		    return fmt;   
	    }  		
    
   		$(function() {
   			var now = new Date().Format("yyyy-MM-dd"); 
   			$("#startTime").val(now);
   		});
   		
   		//分配权限
   		function paiMenu() {
   			var ids = $("#id").val();
   		}
   		
		$("#reset").bind("click",function(){
			var ids = $("#id").val();
			if(confirm('确定放弃本次修改')){
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}
		});
		
		$("#yes").bind("click",function(){
			var name = $("#name").val();
			if(isEmpty(name)) {
				alert("请填写临时用户账户");
				return;
			}
			var password = $("#password").val();
			if(isEmpty(password)) {
				alert("请填写临时用户密码");
				return;
			}
			var endTime = $("#endTime").val();
			if(isEmpty(endTime)) {
				alert("请选择权限失效时间");
				return;
			} else {
				var startTime = $("#startTime").val();
				if(!compareDate(startTime,endTime,"dayTime")) {
					alert("权限失效时间不能早于今天");
					return;
				}
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
				url:"${ctx}/role_saveCasualUser.do",
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
