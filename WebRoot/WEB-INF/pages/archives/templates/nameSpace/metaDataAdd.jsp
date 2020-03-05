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
    			height:430px;
    			top:150px;
    			left:52%; 
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
    		.add_verify .dw select {
    			width:295px
    		}
    		.wf-input-datepick{
    			width:320px;
    		}
    		#metaDefaultTime {
    			width:286px!important;
    		}
    		#timeChange {
    			display:none;
    		}
    		#selectChange {
    			display:none;
    		}
    	</style>
    </head>
    <body>
    <div style="height:450px;position:relative;">
   		<form id="verifyForm" method="post" name="verifyForm" >
   			<input type="hidden" id="nameSpaceId" name="nameSpaceId" value="${nameSpaceId }" />
	   		<div class="add_verify">
	   			<table>
	   				<tr>
	   					<td class="txtRight">名称：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esTitle" placeholder="必填" type="text" name="esTitle" value=""/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">类型：</td>
	   					<td>
	   						<div class="dw">
	   							<select id="esType" name="esType">
	   								<option value="TEXT">文本类型</option>
	   								<option value="NUMBER">整数类型</option>
	   								<option value="DATE">日期类型</option>
	   								<option value="DOUBLE">浮点类型</option>
	   								<option value="BOOLEAN">布尔类型</option>
	   							</select>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">唯一标识：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esIdentifier"  type="text" name="esIdentifier" value="" placeholder="必填"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">描述：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esDescription"  type="text" name="esDescription" value="" placeholder="必填" />
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">是否参与检索：</td>
	   					<td>
	   						<div class="dw">
	   							<select id="esIsMetaDataSearch" name="esIsMetaDataSearch">
	   								<option value="1">否</option>
	   								<option value="0">是</option>
	   							</select>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">是否参与全文检索：</td>
	   					<td>
	   						<div class="dw">
	   							<select id="esIsFullTextSearch" name="esIsFullTextSearch">
	   								<option value="1">否</option>
	   								<option value="0">是</option>
	   							</select>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr id="timeChange">
	   					<td class="txtRight">固定（默认）值：</td>
	   					<td>
	   						<div class="dw">
	   							<input id='metaDefaultTime' class="wf-form-text wf-form-date" type='text' name='metaDefaultValueTime' value='' readonly='readonly'/>
							</div>
				    	</td>	
	   				</tr>
	   				<tr id="selectChange">
	   					<td class="txtRight">固定（默认）值：</td>
	   					<td>
	   						<div class="dw">
	   							<select id="metaDefaultSelect" name="metaDefaultSelect">
	   								<option value="否">否</option>
	   								<option value="是">是</option>
	   							</select>
							</div>
				    	</td>	
	   				</tr>
	   				<tr id="inputChange">
	   					<td class="txtRight">固定（默认）值：</td>
	   					<td>
	   						<div class="dw">
								<input id="metaDefaultInput"  type="text" name="metaDefaultValueInput" value="" placeholder="填写后，即使设置元数据属性，仍将优先采用本项内容" />
							</div>
				    	</td>	
	   				</tr>
   				</table>
   				<div style="text-align:center;position:absolute;bottom:0;width:100%;left:0;">
			        <button id="reset" type="button" class="btn_qx">取消</button>
			        <button id="yes" type="button" class="btn_ok">确定</button>
			    </div>
	   		</div>
   			</form>
   		</div>
    </body>
    <script type="text/javascript">
    	$(function() {
    		$("#esType").change(function() {
    			var val = $(this).children('option:selected').val();
    			if(val == "DATE") {
    				$("#selectChange").css("display","none");
    				$("#inputChange").css("display","none");
    				$("#timeChange").css("display","table-row");
    			} else if(val == "BOOLEAN") {
    				$("#inputChange").css("display","none");
    				$("#timeChange").css("display","none");
    				$("#selectChange").css("display","table-row");
    			} else {
    				$("#timeChange").css("display","none");
    				$("#selectChange").css("display","none");
    				$("#inputChange").css("display","table-row");
    			}
    		}); 
    	})
    
		$("#reset").bind("click",function(){
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
		
		$("#yes").bind("click",function(){
			var esTitle = $("#esTitle").val();
			if(isEmpty(esTitle)) {
				alert("请填写名称");
				return;
			}
			var esIdentifier = $("#esIdentifier").val();
			if(isEmpty(esIdentifier)) {
				alert("请填写唯一标识");
				return;
			}
			var reg= /^[A-Za-z]+$/;
			if(!reg.test(esIdentifier)) {
				alert("请保证唯一标识为全字母");
				return;
			}
			var esDescription = $("#esDescription").val();
			if(isEmpty(esDescription)) {
				alert("请填写描述");
				return;
			}
			var type = $("#esType option:selected").val();
			var defaultValue;
			if(type == "NUMBER") {
				defaultValue = $("#metaDefaultInput").val();
				if(!isEmpty(defaultValue) && defaultValue%1 != 0) {
					alert("固定（默认）值请填写为整数");
				}
			} else if(type == "DOUBLE") {
				defaultValue = $("#metaDefaultInput").val();
				var regu = /(^[\-0-9][0-9]*(.[0-9]+)?)$/;
				if(!isEmpty(defaultValue) && !regu.test(defaultValue)) {
					alert("固定（默认）值请填写为整数或小数");
				}
			} else if(type == "DATE") {
				defaultValue = $("#metaDefaultTime").val();
			} else if(type == "BOOLEAN") {
				defaultValue = $("metaDefaultSelect option:selected").val();
			} else {
				defaultValue = $("#metaDefaultInput").val();
			}
			if(isEmpty(defaultValue)) {
				defaultValue = "";
			}
			var obj = $("#verifyForm").serialize();
			obj += "&metaDefaultValue="+defaultValue;
			if(isEmpty(defaultValue)) {
				obj += "&isFixedValue="+1;
			} else {
				obj += "&isFixedValue="+0;
			}
			console.log(obj);
			var nameSpaceId = $("#nameSpaceId").val();
			var data = {
				"nameSpaceId":nameSpaceId,
				"esIdentifier":esIdentifier
			}
			$.ajax({
				url:"${ctx}/met_checkMetaDataRecord.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",
				data:obj,
				success:function(data){
					var sfv = data.flag;
					if(sfv=="no"){
						$.ajax({
							url:"${ctx}/met_metaDataSave.do",
							type:"post",
							async:false,
							cache: false,
							dataType:"json",
							data:obj,
							success:function(data){
								var sfv = data.flag;
								if(sfv=="have"){
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
					}else{
						alert("存在相同唯一标识符");
					}
				},
				error:function(){
					alert("系统错误请重试");
				}	
			});
		});
	</script>
</html>
