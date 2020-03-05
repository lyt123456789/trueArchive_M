<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/formJs.jsp"%>
<html>
<head>
<title>表单元素说明</title>		
<!--以下必须-->
<style>
body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,legend,button,input,textarea,th,td
	{
	margin: 0;
	padding: 0px;
}

body,button,input,select,textarea {
	font: 12px Arial, sans-serif, "Lucida Grande", Helvetica, arial,
		tahoma, \5b8b\4f53;
}

h1,h2,h3,h4,h5,h6 {
	font-size: 100%;
}

address,cite,dfn,em,var {
	font-style: normal;
}

code,kbd,pre,samp {
	font-family: courier new, courier, monospace;
}

small {
	font-size: 12px;
}

ul,ol {
	list-style: none;
}

a {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

/* .warp{
				width: 1024px;
				max-width:1024px;
				height: 1448px;
				min-height: 1448px;
				overflow: hidden;
	}*/
table{border-collapse:collapse;border-spacing:0;}
.infotan{width: 820px;margin:0 auto;} 
.infotan td{height:30px;line-height:30px;font-size:18px;border:1px #333 solid;padding:5px;}
.infotan td input{border:0px;border-bottom:1px #cccccc dotted;line-height:17px;}
.infotan td textarea{width:100%;height:180px;}
.infotan td select{width:120px;}
/* .infotan .label{
				font-size: 18px;
				text-align: center;
				vertical-align:middle;
			}
				td.vam{
				vertical-align:middle;
			} */
</style>		
<!--以下必须-->
<script type="text/javascript" defer="defer">
window.onload=function(){ 
	var listValues='<%=request.getSession().getAttribute("listValues")==null?"":request.getSession().getAttribute("listValues")%>';
	var selects='<%=request.getParameter("selects")==null?"":request.getParameter("selects")%>';
	var limitValue='<%=request.getParameter("limitValue")==null?"":request.getParameter("limitValue")%>';
	var valuestr='<%=request.getParameter("values")==null?"":request.getParameter("values")%>';	
	var instanceId = '${instanceId}';  
	if(instanceId.length!=13)tagvalues(listValues,selects,limitValue,valuestr,instanceId);//标签赋值，权限控制
	if(instanceId.length==13){registerTag();}//注册标签选中效果
};
</script>
<!--以上必须-->
<style>
</style>
<script type="text/javascript">
function chooseroom(){
	if(document.getElementById("meeting_Begin_Time").value.replace(/(^\s*)|(\s*$)/g, "")==""){ 
		alert("请先选择会议开始时间！");
		document.getElementById("meeting_Begin_Time").focus();
		return;
	}else if(document.getElementById("meeting_End_Time").value.replace(/(^\s*)|(\s*$)/g, "")==""){
		alert("请先选择会议结束时间！");
		document.getElementById("meeting_End_Time").focus();
		return;
	}else if(document.getElementById("meeting_Begin_Time").value.replace(/(^\s*)|(\s*$)/g, "")>
		document.getElementById("meeting_End_Time").value.replace(/(^\s*)|(\s*$)/g, "")){
		alert("会议结束时间应大于会议开始时间！");
		return;
	}
	var beginTime = document.getElementById("meeting_Begin_Time").value.replace(/(^\s*)|(\s*$)/g, "");
	var winoption ="dialogHeight:500px;dialogWidth:1200px;status:no;scroll:no;resizable:no;center:yes";
	var ret = window.showModalDialog('http://61.155.85.78:9493/functions/meeting_selroom.do?beginTime='+beginTime, null, winoption);
        //var ret = window.showModalDialog('http://192.168.7.75:8082/trueWorkFlowV1.1_TZCG/meeting_selroom.do?beginTime='+beginTime, null, winoption);

	if(ret != null){
		document.getElementById("roomName").value = ret.split("@#@")[1];
		document.getElementById("roomId").value = ret.split("@#@")[0];
	}
}	
</script>
</HEAD>

<body>
<div class="warp">
<p style="font-size:25px;line-height:35px; text-align:center;padding-top:30px;">
<strong>卫生和计划生育委员会会议申请</strong>
</p>
</br>
<table width="819px;" class="infotan" align="center">
<tr>
<td width="165" valign="middle"><div align="center">申&nbsp;&nbsp;请&nbsp;&nbsp;人&nbsp;&nbsp;员</div></td>
<td width="245"><input id="userName" name="userName"  type="text" style="width:200px;height:34px;" value="" /></td>
<td width="165" valign="middle" ><div align="center">申&nbsp;&nbsp;请&nbsp;&nbsp;部&nbsp;&nbsp;门</div></td>
<td width="245"><input id="departmentName" name="departmentName"  type="text"  style="width:200px;height:23px;"  /></td>
</tr>
<tr>
<td valign="middle" ><div align="center">会议开始时间</div></td>
<td><input id="meeting_Begin_Time" name="meeting_Begin_Time"  type="text"  style="height:23px;width:200px;"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/></td>
<td valign="middle" ><div align="center">会议结束时间</div></td>
<td><input id="meeting_End_Time" name="meeting_End_Time"  type="text"  style="height:23px;width:200px;"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/></td>
</tr>
<tr>
<td valign="middle" ><div align="center">会&nbsp;&nbsp;议&nbsp;&nbsp;人&nbsp;&nbsp;数</div></td>
<td ><input id="meeting_Rscount" name="meeting_Rscount"  type="text"  style="height:23px;width:200px;"   /></td>
<td valign="middle" ><div align="center">会&nbsp;&nbsp;&nbsp;&nbsp;议&nbsp;&nbsp;&nbsp;&nbsp;室</div></td>
<td >
<input id="roomName" name="roomName"  type="text" readonly="readoly" style="height:23px;width:200px;" ></input>
</td>
</tr>

<tr height="365px">
<td valign="middle"  style="height:365px"><div align="center">本系统参加人员</div></td>
<td style="height:365px;padding:5px" colspan="3" valign="center" ><textarea id="meeting_Bcyry" name="meeting_Bcyry"  type="text" readonly="readoly" style="width:600px;height:350px;"></textarea></td>

</tr>
<tr height="195px">
<td valign="middle" style="height:195px"><div align="center">外单位参加人员</div></td>
<td colspan="3" style="height:195px;padding:5px" ><textarea id="meeting_Wcyry" name="meeting_Wcyry"  type="text" style="width:600px;height:180px;"></textarea></td>
</tr>
<tr>
<td valign="middle" height="115px" ><div align="center">会议主持人</div></td>
<td style="height:115px;" >
<textarea id="meeting_Zcr" name="meeting_Zcr"  type="text" readonly="readoly" style="width:200px;height:100px;"></textarea>
</td>
<td valign="middle" ><div align="center">会场准备需求</div></td>
<td style="height:115px;" >
<textarea id="hczbxq" name="hczbxq"  type="text" readonly="readoly" style="width:200px;height:100px;"></textarea>
</td>
</tr>
<tr>
<td valign="middle" style="width:20%"><div align="center">会&nbsp;&nbsp;议&nbsp;&nbsp;主&nbsp;&nbsp;题</div></td>
<td colspan="3" style="height:100px;font-size:18px;" ><textarea id="meeting_Subject" name="meeting_Subject"  style="width:600px;height:85px;"></textarea></td>
</tr>
<tr>
<td valign="middle" style="width:20%;height:250px;"><div align="center">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</div></td>
<td colspan="3" style="height:170px;font-size:18px;" ><textarea id="meeting_bz" name="meeting_bz"  style="width:600px;"></textarea></td>
</tr>
</table>
<div style="display:none">
				
         <span id="fjshow"></span>
			<trueway:att  onlineEditAble="true" id="${instanceId}fj" docguid="${instanceId}fj" showId="fjshow" ismain="true" 
			downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add"             otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
</div>
</div>
<INPUT type="hidden" id="userId" name="userId"  value="${loginEmployee.employeeGuid}"> 
<INPUT type="hidden" id="departmentId" name="departmentId"  value="${loginEmployee.departmentGuid}"> 
<INPUT type="hidden" id="roomId" name="roomId"  value=""> 
<INPUT type="hidden" id="meeting_BcyryId" name="meeting_BcyryId"  value=""> 
<INPUT type="hidden" id="state" name="state"  value="">  
<INPUT type="hidden" id="instanceId" name="instanceId" value=""> 
<INPUT type="hidden" id="formId" name="formId" value=""> 
<INPUT type="hidden" id="workFlowId" name="workFlowId" value="">   
<INPUT type="hidden" id="processId" name="processId" value="">  
<INPUT type="hidden" id="webId" name="webId" value="${webID}"> 
</body>
<script src="${ctx}/dwz/style/js/jquery-1.7.1.min.js"></script>
<script src="${ctx}/dwz/style/js/jquery.tab.js"></script>
<script type="text/javascript"> 
	//以下必须有
	function loadCss(){  
   		seajs.use('lib/form',function(){  
			$('input[mice-btn]').cssBtn();
			$('input[mice-input]').cssInput();
			$('select[mice-select]').cssSelect();
	    });
	}
	//以上必须有
function choosezcr(){
	var winoption ="dialogHeight:800px;dialogWidth:700px;status:no;scroll:no;resizable:no;center:yes";
	var ret=window.showModalDialog("http://61.155.85.78:3994/functions/meeting_getSettingTree.do?isSingle=1&t="+new Date().getTime(),null,winoption);	
	if(ret){
		var userNames = '';
		$.each(ret.data,function(k,v){
		        userNames += v.name+",";
		});
		userNames=userNames.substring(0,userNames.length-1);
                document.getElementById("meeting_Zcr").value = userNames;
	}
}
function choosebcyry(){
	var winoption ="dialogHeight:800px;dialogWidth:700px;status:no;scroll:no;resizable:no;center:yes";
	var ret=window.showModalDialog("http://61.155.85.78:3994/functions/meeting_getSettingTree.do?t="+new Date().getTime(),null,winoption);	
	if(ret){
		var userNames = '';
		var userIds = '';
		$.each(ret.data,function(k,v){
		        userNames += v.name+",";
		        userIds += v.value+",";
		});
		userNames=userNames.substring(0,userNames.length-1);
		userIds=userIds.substring(0,userIds.length-1);
                document.getElementById("meeting_Bcyry").value = userNames;
                document.getElementById("meeting_BcyryId").value = userIds;
	}
}
</script>
</html>
