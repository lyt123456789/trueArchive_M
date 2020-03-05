 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
 <%@ include file="/common/headerbase.jsp"%>
<html>
    <head>
        <title>修改按钮</title>
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
	   					<td class="txtRight">按钮名称：</td>
	   					<td>
	   						<div class="dw">
				        		<input id="btn_name" placeholder="必填" type="text" name="btn_name" value="${btn_name }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">按钮描述：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="btn_description" placeholder="必填" type="text" name="btn_description" value="${btn_description }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">排序号：</td>
	   					<td>
	   						<div class="dw">
				        		<input id="btn_sort" placeholder="必填" type="text" name="btn_sort" value="${btn_sort }"/>
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
			var btn_sort = $("#btn_sort").val();
			if(isEmpty(btn_sort)) {
				alert("请填写编号");
				return;
			}
			var btn_name = $("#btn_name").val();
			if(isEmpty(btn_name)) {
				alert("请填写按钮名称");
				return;
			}
			var btn_description = $("#btn_description").val();
			if(isEmpty(btn_description)) {
				alert("请填写按钮描述");
				return;
			}
			$.ajax({
				url:"${ctx}/btnmanage_BtnManageSave.do",
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
