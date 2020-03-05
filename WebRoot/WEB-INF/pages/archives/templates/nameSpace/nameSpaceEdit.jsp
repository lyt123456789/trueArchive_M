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
	   		<div class="add_verify">
	   			<table>
	   				<tr>
	   					<td class="txtRight">编号：</td>
	   					<td>
	   						<div class="dw">
				        		<input id="esIdentifier" placeholder="必填" type="text" name="esIdentifier" value="${esIdentifier }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">作者：</td>
	   					<td>
	   						<div class="dw">
				        		<input id="esCreator" placeholder="必填" type="text" name="esCreator" value="${esCreator }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">名称：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esTitle" placeholder="必填" type="text" name="esTitle" value="${esTitle }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">描述文件位置：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esUrl"  type="text" name="esUrl" value="${esUrl }" placeholder="请填写文件地址信息"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">描述：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esDescription"  type="text" name="esDescription" value="${esDescription }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">版本：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esVersion"  type="text" name="esVersion" value="${esVersion }" placeholder="必填"/>
				    		</div>
				    	</td>	
	   				</tr>
   				</table>
   				<div style="text-align:center;position:absolute;bottom:0;width:100%;left:0;">
			        <button id="reset" type="button" class="btn_qx">取消</button>
			        <button id="yes" type="button" class="btn_ok">确定</button>
			    </div>
	   		</div>
   			<input type="hidden" id="id" name="id" value="${id }"/>
   			</form>
   		</div>
    </body>
    
    <script type="text/javascript">
		$("#reset").bind("click",function(){
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
		
		$("#yes").bind("click",function(){
			var esIdentifier = $("#esIdentifier").val();
			if(isEmpty(esIdentifier)) {
				alert("请填写编号");
				return;
			}
			var esCreator = $("#esCreator").val();
			if(isEmpty(esCreator)) {
				alert("请填写作者");
				return;
			}
			var esTitle = $("#esTitle").val();
			if(isEmpty(esTitle)) {
				alert("请填写名称");
				return;
			}
			var esVersion = $("#esVersion").val();
			if(isEmpty(esVersion)) {
				alert("请填写版本号");
				return;
			}
			$.ajax({
				url:"${ctx}/met_NameSpaceSave.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",
				data:$("#verifyForm").serialize(),
				success:function(data){
					var sfv = data.flag;
					if(sfv=="yes"){
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
		});
	</script>
</html>
