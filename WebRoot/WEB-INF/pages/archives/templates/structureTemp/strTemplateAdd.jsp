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
    			height:310px;
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
    		.add_verify .dw select {
    			width:295px;
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
	   					<td class="txtRight">所属业务：</td>
	   					<td>
	   						<div class="dw">
				        		<select id="businessSe" name="business" class="sel">
			        				<option value="" selected="selected"></option>
			        				<c:forEach items="${list}" var="data" varStatus="status1">
			        					<option value="${data.esIdentifier }">${data.esTitle }</option>
			        				</c:forEach>
			    				</select>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">模板类型：</td>
	   					<td>
	   						<div class="dw">
				        		<select id="templateSe" name="esType" class="sel">
			        				<option value="" selected="selected"></option>
			        				<option value="innerFile">卷内-文件</option>
			        				<option value="file">案卷-卷内-文件</option>
			        				<option value="project">项目-案卷-卷内-文件</option>
			    				</select>
			    			</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">模板名称：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="identifier"  type="text" name="esIdentifier" value=""/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">描述描述：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="describe"  type="text" name="esDescription" value=""/>
				    		</div>
				    	</td>	
	   				</tr>
   				</table>
   				<div style="text-align:center;position:absolute;bottom:0;width:100%;left:-10px;">
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
			var bussiness = $("#businessSe option:selected").val();
			if(isEmpty(bussiness)) {
				alert("请选择所属业务");
				return;
			}
			var templateSe = $("#templateSe option:selected").val();
			if(isEmpty(templateSe)) {
				alert("请选择模板类型");
				return;
			}
			var identifier = $("#identifier").val();
			if(isEmpty(identifier)) {
				alert("请填写模板名称");
				return;
			}
			$.ajax({
				url:"${ctx}/str_saveStructureTemp.do",
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
		})
	</script>
</html>
