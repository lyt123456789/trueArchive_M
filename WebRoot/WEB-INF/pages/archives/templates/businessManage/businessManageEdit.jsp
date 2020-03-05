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
    			height:410px;
    			top:150px;
    			left:55%; 
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
    
    <div style="height:450px;position:relative;">
   		<form id="verifyForm" method="post" name="verifyForm" >
   			<input type="hidden" id="esId" name="id" value="${EssBus.id }"/>
	   		<div class="add_verify">
	   			<table>
	   				<tr>
	   					<td class="txtRight">业务标题：</td>
	   					<td>
	   						<div class="dw">
				        		<input id="title"  type="text" name="title" value="${EssBus.esTitle }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">标识符：</td>
	   					<td>
	   						<div class="dw">
				        		<input id="identifier" placeholder=" 请确保标识符的唯一性，并填写字母" type="text" name="identifier" value="${EssBus.esIdentifier }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">描述描述：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="describe"  type="text" name="describe" value="${EssBus.esDescription }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">业务类型：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="type"  type="text" name="type" value="${EssBus.esType }" readonly="readonly"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">菜单名称：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="menuName"  type="text" name="menuName" value=""/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">上级菜单：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="lastMenu"  type="text" name="lastMenu" value=""/>
				    		</div>
				    	</td>	
	   				</tr>
   				</table>
   				<div style="text-align:center;position:absolute;bottom:0;width:100%;left:0;">
			        <button id="reset" type="reset" class="btn_qx">取消</button>
			        <button id="yes" type="button" class="btn_ok">确定</button>
			    </div>
	   		</div>
   			<input type="hidden" id="id" name="id" value="${casualId }"/>
   			</form>
   		</div>
   		
    </body>
    <script type="text/javascript">
		$("#reset").bind("click",function(){
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
		
		$("#yes").bind("click",function(){
			var title = $("#title").val();
			if(isEmpty(title)) {
				alert("请填写业务标题");
				return;
			}
			var identifier = $("#identifier").val();
			if(isEmpty(identifier)) {
				alert("请填写标识符");
				return;
			}
			var reg= /^[A-Za-z]+$/;
			if(!reg.test(identifier)) {
				alert("请保证标识符为全字母");
				return;
			}
			var id = $("#esId").val();
			$.ajax({
				url:"${ctx}/bus_checkIdentifierById.do",
				type:"post",
				async:false,
				cache: false,
				data:{"identifier":identifier,"id":id},
				dataType:"json",//返回值类型
				success:function(msg){
					var flag = msg.flag;
					if(flag == "have") {
						alert("请确保标识符的唯一性");
					} else {
						$.ajax({
							url:"${ctx}/bus_saveBusinessManage.do",
							type:"post",
							async:false,
							cache: false,
							dataType:"json",
							data:$("#verifyForm").serialize(),
							success:function(data){
								var sfv = data.flag;
								if(sfv=="success"){
									parent.location.reload();
									var index = parent.layer.getFrameIndex(window.name);
									parent.layer.close(index);
								}else{
									alert("新增失败");
								}
							},
							error:function(){
								alert("系统错误请重试");
							}	
						});
					}
				},
				error:function(){
					alert("系统错误请重试");
				}	
			});
		});
	</script>
</html>
