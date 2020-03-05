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
    			top:140px;
    			left:26%; 
    			width:445px;
    			z-index:1;
    			border-right:solid 1px #d2d2d2;
    			border-radius:0px;
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
    		.rightDiv {
    			width: 50%;
			    height: 450px;
			    position: absolute;
			    left: 50%;
			    top: 0px;
			    z-index:1
    		}
    		.blueBut {
    			width: 93px;
			    height: 40px;
			    color: #fff;
			    background: #1c9dd4;
			    border-radius: 5px;
			    margin-top: 20px;
			    margin-left: 5px;
    		}
    		.grayBut {
    			width: 93px;
			    height: 40px;
			    color: #fff;
			    background: #ccc;
			    border-radius: 5px;
			    margin-top: 20px;
			    margin-left: 5px;
    		}
    		button {
    			font-size: 100%;
    			font: 12px/18px Arial,sans-serif,"Lucida Grande",Helvetica,arial,tahoma,\5b8b\4f53;
    		}
    		.footDiv {
				z-index:999;
				text-align:center;
				position:fixed;
				bottom:8px;
				width:100%;
				left:0;
    		}
    		iframe {
    			width:450px;
    			height:450px;
    		}
    	</style>
    </head>
    <body>
    <div class="leftDiv">
   		<form id="verifyForm" method="post" name="verifyForm" >
   			<input type="hidden" id="id" name="id" value="${esmd.id }" />
   			<input type="hidden" id="nameSpaceId" name="nameSpaceId" value="${esmd.idNameSpace }" />
	   		<div class="add_verify">
	   			<table>
	   				<tr>
	   					<td class="txtRight">名称：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esTitle" placeholder="必填" type="text" name="esTitle" value="${esmd.esTitle }"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">类型：</td>
	   					<td>
	   						<div class="dw">
	   							<select id="esType" name="esType">
	   								<option value="TEXT" <c:if test="${esmd.esType eq 'TEXT'}">selected="selected"</c:if>>文本类型</option>
	   								<option value="NUMBER" <c:if test="${esmd.esType eq 'NUMBER'}">selected="selected"</c:if>>整数类型</option>
	   								<option value="DATE" <c:if test="${esmd.esType eq 'DATE'}">selected="selected"</c:if>>日期类型</option>
	   								<option value="DOUBLE" <c:if test="${esmd.esType eq 'DOUBLE'}">selected="selected"</c:if>>浮点类型</option>
	   								<option value="BOOLEAN" <c:if test="${esmd.esType eq 'BOOLEAN'}">selected="selected"</c:if>>布尔类型</option>
	   							</select>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">唯一标识：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esIdentifier"  type="text" name="esIdentifier" value="${esmd.esIdentifier }" placeholder="必填"/>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">描述：</td>
	   					<td>
	   						<div class="dw">
	   							<input id="esDescription"  type="text" name="esDescription" value="${esmd.esDescription }" placeholder="必填" />
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">是否参与检索：</td>
	   					<td>
	   						<div class="dw">
	   							<select id="esIsMetaDataSearch" name="esIsMetaDataSearch">
	   								<option value="1" <c:if test="${esmd.esIsMetaDataSearch eq '1'}">selected="selected"</c:if>>否</option>
	   								<option value="0" <c:if test="${esmd.esIsMetaDataSearch eq '0'}">selected="selected"</c:if>>是</option>
	   							</select>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr>
	   					<td class="txtRight">是否参与全文检索：</td>
	   					<td>
	   						<div class="dw">
	   							<select id="esIsFullTextSearch" name="esIsFullTextSearch">
	   								<option value="1" <c:if test="${esmd.esIsFullTextSearch eq '1'}">selected="selected"</c:if>>否</option>
	   								<option value="0" <c:if test="${esmd.esIsFullTextSearch eq '0'}">selected="selected"</c:if>>是</option>
	   							</select>
				    		</div>
				    	</td>	
	   				</tr>
	   				<tr id="timeChange" <c:if test="${esmd.esType ne 'DATE' }">style="display:none"</c:if>>
	   					<td class="txtRight">固定（默认）值：</td>
	   					<td>
	   						<div class="dw">
	   							<input id='metaDefaultTime' class="wf-form-text wf-form-date" type='text' name='metaDefaultValueTime' value='${esmd.metaDefaultValue }' readonly='readonly'/>
							</div>
				    	</td>	
	   				</tr>
	   				<tr id="selectChange" <c:if test="${esmd.esType ne 'BOOLEAN' }">style="display:none"</c:if>>
	   					<td class="txtRight">固定（默认）值：</td>
	   					<td>
	   						<div class="dw">
	   							<select id="metaDefaultSelect" name="metaDefaultSelect">
	   								<option value="否" <c:if test="${esmd.metaDefaultValue eq '否'}"></c:if>>否</option>
	   								<option value="是" <c:if test="${esmd.metaDefaultValue eq '是'}"></c:if>>是</option>
	   							</select>
							</div>
				    	</td>	
	   				</tr>
	   				<tr id="inputChange" <c:if test="${esmd.esType eq 'BOOLEAN' || esmd.esType eq 'DATE'}">style="display:none"</c:if>>
	   					<td class="txtRight">固定（默认）值：</td>
	   					<td>
	   						<div class="dw">
								<input id="metaDefaultInput"  type="text" name="metaDefaultValueInput" value="${esmd.metaDefaultValue }" placeholder="填写后，即使设置元数据属性，仍将优先采用本项内容" />
							</div>
				    	</td>	
	   				</tr>
   				</table>
	   		</div>
   			</form>
   		</div>
   		<div class="rightDiv">
   			<iframe id="metaDataframe" name="metaDataframe" src="${ctx}/met_toMeDaPropertyPage.do?metaDataId=${esmd.id}" frameborder="0"></iframe>
   		</div>
   		<div class="footDiv">
	        <button id="reset" type="button" class="grayBut">取消</button>
	        <button id="yes" type="button" class="blueBut">确定</button>
   		</div>
    </body>
    <script type="text/javascript">
    	var esTypeIndex = "${esmd.esType}";
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
