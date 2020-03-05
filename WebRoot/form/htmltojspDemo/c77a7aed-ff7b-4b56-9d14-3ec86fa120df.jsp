<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>表单</title>
<%@page import="javax.servlet.*"%>
<script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/widgets/component/writeByHand/js/websign.js"></script>
<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/widgets/component/taglib/comment/js/comment.js"></script>
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/widgets/plugin/js/sea.js"></script>  

<style>
.table1 td{border:1px solid #cccccc;font-size:12px;}
</style>

<script type="text/javascript" defer="defer">
window.onload=function(){
	//下拉框对应字典表自动赋值
	try{
	var selectstr='[{"diclist":[{"id":"55816FD9-010C-4C94-A6ED-623046FCC2D8","vc_name":"办理地点","vc_key":"审批中心,部门","vc_value":"0,1"}],"m":{"id":"D3AAA91C-1E59-471C-BBD8-73B4C10A74F4","formid":"CBD5122E-FF5E-4291-AC5D-E901030FF20A","formtagname":"banlididian","formtagtype":"select","tagtype":"100","tablename":"T_WF_OFFICE_RFB_RFXMTF","columnname":"BANLIDIDIAN","sortIndex":13,"selectDic":"办理地点"}}]';
	var jsobjs=eval('('+selectstr+')'); 
	if(jsobjs&&jsobjs.length>0){
	for(var i=0;i<jsobjs.length;i++){
	var tagtype=jsobjs[i].m.formtagtype;
	var tagname=jsobjs[i].m.formtagname;
	var keys=jsobjs[i].diclist[0].vc_key.split(/,/g);
	var values=jsobjs[i].diclist[0].vc_value.split(/,/g);
	var objs=document.getElementsByName(tagname);
	for(var j=0;j<objs.length;j++){
	var t=objs[j].tagName.toLowerCase();
	if(t=='select'){  
	for(var k=0;k<keys.length;k++){
	objs[j].options.add(new Option(keys[k],values[k])); 
	};
	};
	};
	};
	};
	}catch(e){
	alert(e);
	};

	
	var valuestr='VC_SQSX:4;VC_XMMC:w;VC_XMMJ:e;VC_XMDZ:f;VC_TFYY:w;VC_TFJE:e;BANLIDIDIAN:0;DECLARESN:333;VC_SQR:e;workFlowId:0C31F4EC-AF9B-4DB6-834D-6175C8964E87;formId:7DF42627-0B05-4F7D-AE88-ECD2D88AF449;instanceId:2E572A20-A99B-4D83-9F6B-DDE5E3CD6B09;processId:96C45AF5-7663-4C71-B8CA-4C514BE3D440;att_comment_id:483327AD-69A3-40C9-BD01-F0FF5FA8E248';
	//alert(valuestr);
	if(valuestr&&valuestr!='null'&&valuestr!=''){
	var values=valuestr.split(/;/);
	if(values){
	for(var i=0;i<values.length;i++){
	var v=values[i].split(/:/);
	if(v&&v.length==2){
	var os=document.getElementsByName(v[0]);
	if(os){
	for(var k=0;k<os.length;k++){
	var tagname=os[k].tagName.toLowerCase();
	//alert(tagname);
	if(tagname=='input'||tagname=='select'){
	os[k].value=v[1];
	}else if(tagname=='textarea'){
	os[k].innerHTML=v[1];
	}
	}
	}
	}
	}
	}
	}

	var limitValue = 'att_comment_id:2,text;VC_XMMC:0,text;VC_SQSX:0,text;csyj:0,comment;processId:2,text;VC_TFJE:2,text;instanceId:2,text;scyj:0,comment;VC_TITLE:1,text;workFlowId:2,text;banlididian:2,select;DeclareSN:0,text;VC_XMDZ:2,text;formId:2,text;VC_TFYY:2,text;VC_XMMJ:2,text;VC_SQR:0,text;att:0,attachment';
	if(limitValue&&limitValue!='null'&&limitValue!=''){
		var limVals = limitValue.split(/;/);
		for(var j=0;j<limVals.length;j++){
			var childVal = limVals[j].split(/:/);  
			var tagN     = childVal[0];
			var limit    = childVal[1].split(/,/)[0];    
			var typeVal  = childVal[1].split(/,/)[1];
			if(typeVal == 'text' || typeVal == 'select'){
				if(limit == 0){
					document.getElementById(tagN).style.display="none";  
				}else if(limit == 1){    
					document.getElementById(tagN).readOnly=true;
				}  
			}else if(typeVal = 'comment'){
				if(limit == 0){
					document.getElementById(tagN).style.display="none"; 
				}else if(limit == 1){    
					document.getElementById(tagN+"luru").style.display="none"; 
				}  
			}else if(typeVal = 'att'){
					alert(limit+"===");
				if(limit == 0){
					document.getElementById("attshow").style.display="none"; 
					document.getElementById('4F87605-41E4-41DD-BE21-6160024F3938'+"att_upload").style.display="none"; 
				}else if(limit == 1){  
					document.getElementById('4F87605-41E4-41DD-BE21-6160024F3938'+"att_upload").style.display="none"; 
				}  
			}				
		}
	}
};
</script>
</HEAD>

<body>
<form name=riseForm>
 <h2 align="center" style="LINE-HEIGHT: 40px">不建人防工程建设项目退费登记单</h2>
 <table style="MARGIN-BOTTOM: 5px" width="100%" border="0" cellpadding="0" cellspacing="0">
 <tr>
 <td align="left" width="50%">办理地点：
   <select name="banlididian" id="banlididian" value="" dic="办理地点"></select>
 </td>
 <td align="right" width="50%">
 <div >编号：<input id="DeclareSN"class="displayTextInput_SN" name="DeclareSN" value=""></div> </td>
 </tr>
 </table>
		
 <table width="100%"  class="table1"  border="1" cellpadding="0" align="center">
  <tr>
     <td width="15%" bgcolor=#e4e9ff>申请人：</td>
     <td width="35%" ><input name="VC_SQR" class="displayTextInput_LONG" id="VC_SQR" value=""></td>
     <td width="15%" bgcolor=#e4e9ff>申请事项：</td>
     <td width="35%" ><input name="VC_SQSX" class="displayTextInput_LONG" id="VC_SQSX" value=""></td>
   </tr>
   <tr>
     <td  bgcolor=#e4e9ff>项目名称：</td>
     <td ><input name="VC_XMMC" class="displayTextInput_LONG" id="VC_XMMC" value=""></td>
     <td  bgcolor=#e4e9ff>项目面积：</td>
     <td ><input name="VC_XMMJ" class="displayTextInput_LONG" id="VC_XMMJ" value=""></td>
   </tr>
    <tr>
     <td  bgcolor=#e4e9ff>项目地址：</td>
     <td  ><input name="VC_XMDZ" class="displayTextInput_LONG" id="VC_XMDZ" value=""></td>
     <td  bgcolor=#e4e9ff>退费原因：</td>
     <td  ><input name="VC_TFYY" class="displayTextInput_LONG" id="VC_TFYY" value=""></td>
   </tr>
     <tr>
     <td  bgcolor=#e4e9ff>退费金额：</td>
     <td colspan="3" ><input name="VC_TFJE" class="displayTextInput_LONG" id="VC_TFJE" value=""></td>
	</tr>
	<tr>
     <td bgcolor=#e4e9ff>相关附件：</td> 
     <td colspan="3">
     	<span id="attshow"></span>
	  	<trueway:att onlineEditAble="true" id="54F87605-41E4-41DD-BE21-6160024F3938att" docguid="54F87605-41E4-41DD-BE21-6160024F3938" ismain="true" showId="attshow" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
     </td> 
   </tr>
    <tr>
     <td  bgcolor=#e4e9ff aling="center">文件上传: </td>
     <td colspan="3">
     	<span id="attshow"></span>
	  	<trueway:att onlineEditAble="true" id="54F87605-41E4-41DD-BE21-6160024F3938att" docguid="54F87605-41E4-41DD-BE21-6160024F3938" ismain="true" showId="attshow" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
     </td>
   </tr>
   <tr>
     <td  bgcolor=#e4e9ff class="displayLongerTitle">审查意见<br ></td>
     <td colspan="3" width="80%">
     	 <table width="100%" >
		    <tr>
			   <td  height="100px;">
			     <div>
		<trueway:comment typeinAble="true" deleteAbled="true" id="54F87605-41E4-41DD-BE21-6160024F3938scyj" instanceId="54F87605-41E4-41DD-BE21-6160024F3938" currentStepId="54F87605-41E4-41DD-BE21-6160024F3938"/>
			     </div>
			  </td>
		    </tr> 
		</table>
     </td>
   </tr>
   <tr>
     <td  bgcolor=#e4e9ff class="displayLongerTitle">初审意见<br > </td>
     <td colspan="3" width="80%">
     	 <table width="100%" class="docformTableNoBD">
		    <tr>
			   <td class="vt bdB" height="100px;">
			     <div>
		<trueway:comment typeinAble="true" deleteAbled="true" id="54F87605-41E4-41DD-BE21-6160024F3938csyj" instanceId="54F87605-41E4-41DD-BE21-6160024F3938" currentStepId="54F87605-41E4-41DD-BE21-6160024F3938"/>
			     </div>
			  </td>
		    </tr> 
		</table>
     </td>
   </tr>
    <tr>
     <td bgcolor=#e4e9ff class="displayLongerTitle">复核意见<br > </td>
     <td colspan="3" width="80%">
     	<table width="100%" class="docformTableNoBD">
		    <tr>
			   <td class="vt bdB" height="100px;">
			     <div>
		<trueway:comment typeinAble="true" deleteAbled="true" id="54F87605-41E4-41DD-BE21-6160024F3938fhyj" instanceId="54F87605-41E4-41DD-BE21-6160024F3938" currentStepId="54F87605-41E4-41DD-BE21-6160024F3938"/>
			     </div>
			  </td>
		    </tr> 
		</table>
	</td>
   </tr>
     <tr>
     <td bgcolor=#e4e9ff class="displayLongerTitle">审核意见<br > </td>
     <td colspan="3" width="80%">
     	<table width="100%" class="docformTableNoBD">
		    <tr>
			   <td class="vt bdB" height="100px;">
			     <div>
		<trueway:comment typeinAble="true" deleteAbled="true" id="54F87605-41E4-41DD-BE21-6160024F3938shyj" instanceId="54F87605-41E4-41DD-BE21-6160024F3938" currentStepId="54F87605-41E4-41DD-BE21-6160024F3938"/>
			     </div>
			  </td>
		    </tr> 
		</table>
	</td>
   </tr>
     <tr>
     <td bgcolor=#e4e9ff class="displayLongerTitle">审批意见<br > </td>
     <td colspan="3" width="80%">
     	<table width="100%" class="docformTableNoBD">
		    <tr>
			   <td class="vt bdB" height="100px;">
			     <div>
		<trueway:comment typeinAble="true" deleteAbled="true" id="54F87605-41E4-41DD-BE21-6160024F3938spyj" instanceId="54F87605-41E4-41DD-BE21-6160024F3938" currentStepId="54F87605-41E4-41DD-BE21-6160024F3938"/>
			     </div>
			  </td>
		    </tr> 
		</table>
	</td>
   </tr>
     <tr>
     <td bgcolor=#e4e9ff class="displayLongerTitle">办理意见<br > </td>
     <td colspan="3" width="80%">
     	<table width="100%" class="docformTableNoBD">
		    <tr>
			   <td class="vt bdB" height="100px;">
			     <div>
		<trueway:comment typeinAble="true" deleteAbled="true" id="${att_comment_id}blyj" instanceId="54F87605-41E4-41DD-BE21-6160024F3938" currentStepId="54F87605-41E4-41DD-BE21-6160024F3938"/>
			     </div>
			  </td>
		    </tr> 
		</table>
	</td>
   </tr>
 </table>
<INPUT type="hidden" id="instanceId" name="instanceId" value=""> 
<INPUT type="hidden" id="formId" name="formId" value=""> 
<INPUT type="hidden" id="workFlowId" name="workFlowId" value="">   
<INPUT type="hidden" id="processId" name="processId" value="">  
<INPUT type="hidden" id="att_comment_id" name="att_comment_id" value="">  
</form>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
	<script type="text/javascript">
      	function loadCss(){  
	       	seajs.use('lib/form',function(){  
	   			$('input[mice-btn]').cssBtn();
	   			$('input[mice-input]').cssInput();
	   			$('select[mice-select]').cssSelect();
	   	    });
      	}
    </script>
</body>
</html>
