 <!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>文号管理</title>
        <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div class="pageContent">	
			<form action="${ctx}/docNumberManager_docNumAdd.do" method="post" name="form" onSubmit="return check();">
				<input type="hidden" name="define" value="${define}">
				<div id="w_list_print">
					<table  class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
						<tr >
							<td ><font color="red" >*</font>&nbsp;<span style="font-size: 24; font-family: 黑体;">请输入文号：</span></td>
							<td >
								<trueway:dn name="docNum" tagId="zyj" defineId="${define}" value="${docnum}" webId="${webId}" displayVlaue="请输入文号"/>
							</td>
						</tr>
						<tr>
							<td ><font color="red" >*</font>&nbsp;<span style="font-size: 24; font-family: 黑体;">请输入标题：</span></td>
							<td >
							<textarea id="title" name="title" rows="3" cols="60" ></textarea>
							</td>
						</tr>
						<tr>
	                       	<td ><span style="font-size: 24; font-family: 黑体;">单位：</span></td>
	                       	<td >
	                               <select mice-select="select" name="deparementId" id="sender" onChange="getfwjg(options[selectedIndex].text)">
	                                  <c:forEach var="dep" items="${departments}">
	                                  	<option value="${dep.departmentGuid}" <c:if test="${dep.departmentGuid eq deparementId}">selected="selected"</c:if>>${dep.departmentName}</option>
	                                  </c:forEach>
	                               </select>
	                          </td>         
		                 </tr>
		                 <tr>
							<td ><span style="font-size: 24; font-family: 黑体;">请输入拟稿人：</span></td>
							<td >
							<input type="text" name="ngr" mice-input="input" id="ngr" value="${ngr}" size="50"/>
							</td>
						</tr>
						 <tr>
							<td ><span style="font-size: 24; font-family: 黑体;">请输入拟稿科室：</span></td>
							<td >
							<input type="text" name="ngrbm" mice-input="input" id="ngrbm" value="${ngrbm}" size="50"/>
							</td>
						</tr>
						<tr>
							<td ><span style="font-size: 24; font-family: 黑体;">请选择密级：</span></td>
							<td >
							      <select mice-select="select" name="security" id="security" >
							               <option value="3" >&nbsp;</option>
	                                       <option value="2" <c:if test="${security == '2'}">selected</c:if>>秘密</option>
	                                       <option value="1" <c:if test="${security == '1'}">selected</c:if>>机密</option>
	                                       <option value="0" <c:if test="${security == '0'}">selected</c:if>>绝密</option>
	                                   </select>
							</td>
						</tr>
		                 <tr>
		                 	<td ><span style="font-size: 24; font-family: 黑体;"><font color="red" >*</font>时间:</span></td>
		                 	<td >
								<input type="text" id="submittm" mice-input="input" name="time" class="Wdate" value="${time}" onClick="WdatePicker()">
							</td>
		                 </tr>
		                 <tr><td align="right" colspan="2">     
		                 <input type="hidden" name="doc.fwjg" value="" id="fwjg"/>     
							<input type="submit" mice-btn="icon-apply" value="提交"  class="btn"/>
						&nbsp;&nbsp;&nbsp;
						</td></tr>
					</table>
				</div>
			</form>
		</div>
		
		<script type="text/javascript" src="${ctx}/widgets/component/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		window.onload=function(){
			if("${msg}"!==""){
				alert("${msg}");
			}
		};
		if('${error}'==null||'${error}'==""){}else{alert('${error}');}
		//删除左右两端的空格
		function trim(text){ 
			return (text || "").replace( /^\s+|\s+$/g, "" );
		}
		function check(){
			var fwxh = document.getElementsByName("docNum").item(0).value;
			if(fwxh==""||fwxh==null||trim(fwxh).length==0){
				alert("文号不能为空");
				return false;
			}
			var title = document.getElementById("title").value;
			if(title==""||title==null||trim(title).length==0){
				alert("标题不能为空");
				return false;
			}
			var sender = document.getElementById("sender").value;
			if(sender==""||sender==null||trim(sender).length==0){
				alert("发文单位不能为空");
				return false;
			}
			var submittm = document.getElementById("submittm").value;
			if(submittm==""||submittm==null||trim(submittm).length==0){
				alert("发文时间不能为空");
				return false;
			}
		}
		function getfwjg(fwjg){
			document.getElementById("fwjg").value=fwjg;
		}
    </script>
    </body>
</html>
