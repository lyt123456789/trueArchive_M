<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 transitional//EN">
<html>
	<head>
		<title>字段映射</title>
		<META http-equiv=Content-Type content="text/html; charset=gb2312">
		<script src="${pageContext.request.contextPath}/assets-common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			function setVal(e){
				e.length=0;
				e.add(new Option(document.all.ssdwmc.value,document.all.ssdw.value));
			}
			//function checkVal(){
				//var v = document.all.EXCELSSDW.value;
				//if(v=='+-1'){
					//alert("请选择excel所属单位");
					//return false;
				//}
			//}
			function dictTreeOnClick(clickFieldId,clickFieldMcId,dName,dType,condition){
				var records = new Array();
				records["clickField"]=clickFieldId;
				records["mcField"]=clickFieldMcId;
				var url="/syhpt/jsp/common/intoDictTree.action?treetype=001&dictName="+dName+"&dictType="+dType+"&condition="+condition;
				if (url.indexOf(" ")>=0){
					url=encodeURI(url);
					url=encodeURI(url);
				}
				openDictTreeModalDialog(url,records,430,700);
			} 
			
			function openDictTreeModalDialog(URL,arg,width, height) {
				  var returnValue=window.showModalDialog(URL,arg,"dialogWidth="+width+"px;dialogHeight="+height+"px;help:no;scroll=yes;status=0;");
				  if(returnValue==null){
						 return false;
				  }
				  var clickField=returnValue["clickField"];
				  var clickvalue=returnValue["value"];
				  var mcvalue=returnValue["mcvalue"];
				  var mcField=returnValue["mcField"];
				  document.all(mcField).value=mcvalue;
				  document.all(clickField).value=clickvalue;
			}	
		</script>
	</head>
	<body>
		<table style="width: 80%" align="center" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30">
					<!-- 第二个表格开始，圆角表格头开始-->
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="TableRound">
						<tr>
							<td width="12" height="30" class="TableHeadLeft"></td>
							<td class="TableHeader">
								<!-- 第三个表格开始 -->
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="46%" valign="middle">
											<!-- 第四个表格开始 -->
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="small">
												<tr>
													<td width="5%" align="center">
													</td>
													<td width="35%">
														<b>字段映射</b>
													</td>
													<td width="55%" align="right">
														&nbsp;
													</td>
												</tr>
											</table>
											<!-- 第四个表格结束 -->
										</td>
									</tr>
								</table>
								<!-- 第三个表格结束 -->
							</td>
							<td width="16" class="TableHeadRight"></td>
						</tr>
					</table>
					<!-- 第二个表格结束 -->
				</td>
			</tr>
			<tr>
				<td height="18">
					<!-- 第五个表格开始，圆角表格头开始 -->
					<form action="dataReception_importData.do" id="queryForm" method="post">
						<input type="hidden" name="structureId" id="structureId" value="${structureId }" />
						<input type="hidden" name="treeId" value="${treeId }" />
						<input type="hidden" name="parentId" value="${parentId }" />
						<input type="hidden" name="excelPath" value="${excelPath }" />
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="TableRound">
							<tr>
								<td width="8" class="TableLeft"></td>
								<td>
									<!-- 第六个表格开始 -->

									<fieldset>
										<legend>
											<b>映射表 </b>
										</legend>
										<table width="100%" align="center" class="TableBlock1">
											<c:forEach items="${tHeadMap}" var="item">
											 	<tr>
													<td align="right" class="TableContent" width="30%">
														${item.value }
														<input type="hidden" name="zdval" value="${item.key }" />
													</td>
													<td align="left" class="TableContent" width="80%">
														<select name="mapping">
															<option>请选择...</option>
															<c:forEach items="${headMap}" var="headItem">
																<option value="${headItem.key }" <c:if test="${headItem.value eq item.value}">selected="selected"</c:if>>${headItem.value }</option>
															</c:forEach>
														</select>
													</td>
												</tr>
											</c:forEach>
											<tr>
												<td class="TableContent" colspan="2" align="center">
													<input type="button" class="SmallButton02" value="下一步" onclick="checkForm()" />
												</td>
											</tr>
										</table>
									</fieldset>
								</td>
								<td width="8" class="TableLeft"></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td height="35">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="TableRound">
					<tr>
						<td width="12" height="35" class="TableFooterLeft"></td>
						<td class="TableFooter">

							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								class="small">
								<tr>
									<td>
										&nbsp;&nbsp;
									</td>
									<td>
										<table border="0" align="right" cellpadding="0"
											cellspacing="0" class="small">
											<tr>
												<td width="40">
													&nbsp;&nbsp;
												</td>
												<td width="45">
													&nbsp;&nbsp;
												</td>
												<td width="45">
													&nbsp;&nbsp;
												</td>
												<td width="40">
													&nbsp;&nbsp;
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
						<td width="16" class="TableFooterRight"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			function checkForm(){
				$.ajax({
					async : true,//是否异步
					type : "post",//请求类型post\get
					cache : false,//是否使用缓存
					dataType : "text",//返回值类型
					data : $("#queryForm").serialize(),
					url : "${pageContext.request.contextPath}/dataReception_importData.do",
					success : function(text) {
						if(text == 'success'){
							alert("导入成功！");
						}else{
							alert("导入失败！");
						}
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					}
				});
			}
		</script>
	</body>
	
</html>