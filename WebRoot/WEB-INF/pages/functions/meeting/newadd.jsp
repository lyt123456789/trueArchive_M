<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>会议通知消息</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
</head>
	<body>
	<script>
	function remove1(obj){
		$.ajax({
			  url: "${ctx}/meeting_removeAttsext.do?attId="+$(obj).attr("att"),
			  type: 'POST'
			});
	}
	function del(obj){
		$(obj).remove();
	}
	function upload(){
		var res=window.showModalDialog("${ctx}/meeting_upload.do?docguid=${docguid }",window,'dialogWidth:502px; dialogHeight:310px; center:yes; help:no; status:no; resizable:yes;');
		if(res){
			for(var i=0;i<res.length;i++){
				$('#custom-queue').append('&nbsp;&nbsp;&nbsp;&nbsp;<p ondblclick="remove1(this);del(this);" att='+res[i].split(":")[0]+'><font style="color :blue;">'+res[i].split(":")[2]+'</font></p>');
			}
		}
	}
	function doSave(){
		var meetingname = document.getElementById("meetingname").value.replace(/(^\s*)|(\s*$)/g, "");
		if(meetingname==""){
			alert('请填写会议名称！');
			return;
		}
		var newtime = document.getElementById("newtime").value.replace(/(^\s*)|(\s*$)/g, "");
		if(newtime==""){
			alert('请选择会议时间！');
			return;
		}
		var sbmd = '';
		var obj = document.getElementsByName("sbmd");
		for (var t=0; t<obj.length; t++){
			if(obj[t].checked){
				sbmd = sbmd + obj[t].value;
			}
		}
		var myForm = document.getElementById('myForm');
		myForm.action='${ctx}/meeting_saveNew.do?sbmd='+sbmd;
		myForm.submit();
	}
	function seljsy(){
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
			$("#jsy").val(userNames);
			$("#jsyid").val(userIds);
		}
	}
	function selperson(){
		var winoption ="dialogHeight:800px;dialogWidth:700px;status:no;scroll:no;resizable:no;center:yes";
		var ids = $("#emp_ids").val();
		var obj = '';
	    if(ids) obj = getIdsObj(ids); 
		var ret=window.showModalDialog("http://61.155.85.78:9488/functions/meeting_getSettingTree.do?t="+new Date().getTime(),obj,winoption);	
		if(ret){
			var userNames = '';
	        var userIds = '';
	        $.each(ret.data,function(k,v){
	        	userNames += v.name+",";
	        	userIds += v.value+",";
			});
	        userNames=userNames.substring(0,userNames.length-1);
	        userIds=userIds.substring(0,userIds.length-1);
			$("#personname").val(userNames);
			$("#personid").val(userIds);
		}
	}
	</script>
	<div style="height:450px; overflow:scroll;" id="div">
		<form name="myForm" id="myForm" method="post" action="${ctx}/meeting_saveNew.do" enctype="multipart/form-data">
			<h1 style="font-size: 20px;line-height:25px;text-align:center;font-weight:bold;">基本信息</h1>
			<table class="infotan" width="100%">
			<tbody>
			   	 <tr>
					<td width="25%" class="bgs ls">会议名称：</td>
					<td width="75%" colspan="3"><input type="text" style="width: 600px" id="meetingname" name="meetnew.meetingname" maxlength="100" /><span style="color: red;">*</span></td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">通知标题：</td>
					<td width="75%" colspan="3"><input type="text" style="width: 600px" id="newtitle" name="meetnew.newtitle" maxlength="100"/></td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">抬头：</td>
					<td width="75%" colspan="3"><input type="text" style="width: 600px" id="newtt" name="meetnew.newtt" maxlength="100" />
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">通知内容：</td>
					<td width="75%" colspan="3"><input type="text" style="width: 600px" id="newrl" name="meetnew.newrl" maxlength="1000" />
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">落款：</td>
					<td width="75%" colspan="3"><input type="text" style="width: 600px" id="newlk" name="meetnew.newlk" maxlength="100" />
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">时间：</td>
					<td width="75%" colspan="3"><input type="text" style="width: 600px" id="newtime" name="meetnew.newtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/><font color="red">*</font></td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">地点：</td>
					<td width="25%" ><input type="text" style="width: 250px" id="arr" name="meetnew.arr" /></td>
					<td width="20%" class="bgs ls">驾驶员：</td>
					<td width="30%" ><input type="text" style="width: 100px;float: left !important;" id="jsy" name="meetnew.jsy" readonly="readonly"/>
					<input type="hidden" name="meetnew.jsyid" id="jsyid">
					<span ><a class="buttonActive" style="display: inline;" href="javascript:seljsy();"><span>选择人员</span></a></span>
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">发送短信：</td>
					<td width="25%" >
					<select name="meetnew.dx" id="dx" >
					<option value="0">不发送</option>
					<option value="1">发送</option>
					</select>
					</td>
					<td width="20%" class="bgs ls">是否上报参会名单：</td>
					<td width="30%" >
					<input type="radio" name="sbmd" value="0" checked="checked">否
					<input type="radio" name="sbmd" value="1" >是
					</td>
				</tr>
				<tr>
					<td width="25%" class="bgs ls">参与人员：</td>
					<td width="75%" colspan="3"><textarea rows="3" style="width: 600px;float: left !important;" id="personname" name="meetnew.personname" readonly="readonly"></textarea>
					<s:hidden theme="simple" id="personid" name="meetnew.personid"/>
					<span ><a class="buttonActive" style="display: inline;" href="javascript:selperson();"><span>选择人员</span></a></span>
					</td>
				</tr>
				</tbody>
			</table>
			<br>
			<h1 style="font-size: 20px;line-height:25px;text-align:center;font-weight:bold;">会议资料</h1>
			<table class="infotan" width="100%">
			<tr>
			<td width="25%" class="bgs ls">上传附件：<br/><font color="red">(双击删除)</font></td> 
			<td width="75%" colspan="3"> 
				<input type="hidden" name="meetnew.docid" id="docguid" value="${docguid }">
				<span ><a class="buttonActive" style="display: inline;" href="javascript:upload();"><span>上传</span></a></span><br/>
				<div id="custom-queue" style="margin:0 auto;"></div>
				<br/>
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
	<%@ include file="/common/function.jsp"%>
</html>
