<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>填报参会名单</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
</head>
	<body>
	<script>
	function doSave(){
		var chryname = document.getElementById("chryname").value.replace(/(^\s*)|(\s*$)/g, "");
		if(chryname==""){
			alert('请选择待选人员！');
			return;
		}
		var id = document.getElementById('meetnew.id').value;
		var myForm = document.getElementById('myForm');
		myForm.action='${ctx}/meeting_saveNew.do?id='+id;
		myForm.submit();
	}
	function selperson(){
		var winoption ="dialogHeight:800px;dialogWidth:700px;status:no;scroll:no;resizable:no;center:yes";
		var ids = $("#emp_ids").val();
		var obj = '';
	    if(ids) obj = getIdsObj(ids); 
		var ret=window.showModalDialog("http://61.155.85.78:9488/functions/meeting_getSettingTree.do?isSingle=1&t="+new Date().getTime(),obj,winoption);	
		if(ret){
			var userNames = '';
	        var userIds = '';
	        $.each(ret.data,function(k,v){
	        	userNames += v.name+",";
	        	userIds += v.value+",";
			});
	        userNames=userNames.substring(0,userNames.length-1);
	        userIds=userIds.substring(0,userIds.length-1);
			$("#chryname").val(userNames);
			$("#chryid").val(userIds);
		}
	}
	</script>
	<div style="height:450px; overflow:scroll;" id="div">
		<form name="myForm" id="myForm" method="post" action="${ctx}/meeting_savechry.do" enctype="multipart/form-data">
		<input type="hidden" name="meetnew.id" value="${meetnew.id}" id="meetnew.id" /> 
			<h1 style="font-size: 20px;line-height:25px;text-align:center;font-weight:bold;">基本信息</h1>
			<table class="infotan" width="100%">
			<tbody>
			   	 <tr>
					<td width="25%" class="bgs ls">会议名称：</td>
					<td width="75%" colspan="3">${meetnew.meetingname }</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">通知标题：</td>
					<td width="75%" colspan="3">${meetnew.newtitle }</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">抬头：</td>
					<td width="75%" colspan="3">${meetnew.newtt }
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">通知内容：</td>
					<td width="75%" colspan="3">${meetnew.newrl }
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">落款：</td>
					<td width="75%" colspan="3">${meetnew.newlk }
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">时间：</td>
					<td width="75%" colspan="3">${meetnew.newtime }</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">地点：</td>
					<td width="25%" colspan="3">${meetnew.arr }</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">已选人员：</td>
					<td width="75%" colspan="3">${meetnew.personname }
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">待选人员：</td>
					<td width="75%" colspan="3"><textarea rows="3" style="width: 600px;float: left !important;" id="chryname" name="meetnew.chryname" readonly="readonly"></textarea>
					<s:hidden theme="simple" id="chryid" name="meetnew.chryid" value="${meetnew.chryid }"/>
					<span ><a class="buttonActive" style="display: inline;" href="javascript:selperson();"><span>选择人员</span></a></span>
					</td>
				</tr>
				</tbody>
			</table>
			<br>
			<h1 style="font-size: 20px;line-height:25px;text-align:center;font-weight:bold;">附件资料</h1>
			<table class="infotan" width="100%">
			<tr>
			<td width="25%" class="bgs ls">附件：</td> 
			<td width="75%" colspan="3"> 
				<c:forEach items="${doclist}" var="doc" varStatus="p">
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${p.count }、<a href="${doc.localation }">${doc.file_name }</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${doc.file_size /1000}KB</p><br>
				</c:forEach>
			</td>
			</tr>
			</table>
			<br>
			<div align="center">
				<input type="button" value="提交" title="提交" onclick="doSave();" >&nbsp;&nbsp;&nbsp;
				<input type="button" value="返回" title="返回" onclick="history.back();" >
			</div>
		</form>
	</div>
	</body>
	<script type="text/javascript">
		document.getElementById('chryname').innerHTML = '${meetnew.chryname}';
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
