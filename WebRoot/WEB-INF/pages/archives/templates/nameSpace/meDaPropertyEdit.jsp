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
    			height:210px;
    			top:150px;
    			left:64%; 
    		}
    		.txtRight {
    			text-align:right;
    		}
    		.add_verify {
    			width:350px;
    		}
    		.add_verify .dw {
    			width:300px;
    		}
    		.add_verify .dw input {
    			width:293px;
    		}
    		.wf-input-datepick input{
   				height:22px!important;
    		}
    		.add_verify .dw select {
    			width:295px
    		}
    	</style>
    </head>
    <body>
    <div>
   		<form id="verifyForm" method="post" name="verifyForm" >
   			<input type="hidden" id="metaDataId" name="metaDataId" value="${metaDataId }" />
   			<input type="hidden" id="idProp" name="idProp" value="${epve.idProp }" />
   			<input type="hidden" id="id" name="id" value="${epve.id }" />
	   		<div class="add_verify">
	   			<table>
	   				<tr>
	   					<td class="txtRight">属性值：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esIdentifier" placeholder="必填" type="text" name="esIdentifier" value="${epve.esIdentifier }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">代码值：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esTitle"  type="text" name="esTitle" value="${epve.esTitle }" placeholder="必填"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">描述：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esDescription"  type="text" name="esDescription" value="${epve.esDescription }"/>
				    		</div>
				    	</td>	
	   				</tr>
   				</table>
   				<div style="text-align:center;position:absolute;bottom:0;width:100%;left:0;">
			        <button id="resetIframe" type="button" class="btn_qx">取消</button>
			        <button id="yesIframe" type="button" class="btn_ok">确定</button>
			    </div>
	   		</div>
   			</form>
   		</div>
    </body>
    <script type="text/javascript">
		$("#resetIframe").bind("click",function(){
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
		
		$("#yesIframe").bind("click",function(){
			var esIdentifier = $("#esIdentifier").val();
			if(isEmpty(esIdentifier)) {
				alert("请填写属性值");
				return;
			}
			var esTitle = $("#esTitle").val();
			if(isEmpty(esTitle)) {
				alert("请填写代码值");
				return;
			}
			$.ajax({
				url:"${ctx}/met_saveMeDaProPertyRecord.do",
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
