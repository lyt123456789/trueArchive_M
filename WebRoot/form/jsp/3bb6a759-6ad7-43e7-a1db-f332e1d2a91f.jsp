<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/formJs.jsp"%>
<html>
    
     <head>
        <title>
            表单元素说明
        </title>
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
table{border-collapse:collapse;border-spacing:0; border-color:#FF0000;}
.infotan{width: 820px;margin:0 auto; border-color:#FF0000;} 
.infotan td{align="center";height:30px;font-size:18px;border:1px #333 solid;padding:5px;border-color:#FF0000;}
.infotan td input{border:0px;border-bottom:1px #cccccc dotted;line-height:22px;}
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
function choosezs() {
        	var subSender = "";
        	var subNum = 10;
        	var obj ={};
    		obj.dep=subSender;
    		obj.num=subNum;
var zsid= document.getElementById("zsid").value;
var zsdw = document.getElementById("zsdw").value;
var value = zsid+"*"+ zsdw ;
value = encodeURI(value);
            var winoption = "dialogHeight:500px;dialogWidth:1200px;status:no;scroll:no;resizable:no;center:yes";
            var ret = window.showModalDialog("${ctx}/selectTree_showDepartment.do?value="+value+"&t=" + new Date().getTime(), obj, winoption);
            if (typeof ret !='undefined') {
                  var userNames = "";
                  var userIds = "";
                  if(ret!=''){
                         userNames  = ret.split(";")[1];
                         userIds = ret.split(";")[0];
                    }
                document.getElementById("zsdw").value = userNames;
                document.getElementById("zsid").value = userIds;
            }
        }

       function choosecs() {
    	  var subSender = "";
       	  var subNum = 10;
       	  var obj ={};
   	  obj.dep=subSender;
   	  obj.num=subNum;
var csid= document.getElementById("csid").value;
var csdw = document.getElementById("csdw").value;
var value = csid+"*"+ csdw ;
value = encodeURI(value);
          var winoption = "dialogHeight:500px;dialogWidth:1200px;status:no;scroll:no;resizable:no;center:yes";
          var ret = window.showModalDialog("${ctx}/selectTree_showDepartment.do?value="+value+"&t=" + new Date().getTime(), obj, winoption);
          if (typeof ret !='undefined') {
                 var userNames = "";
                  var userIds = "";
                  if(ret!=''){
                         userNames  = ret.split(";")[1];
                         userIds = ret.split(";")[0];
                 }
                document.getElementById("csdw").value = userNames;
                document.getElementById("csid").value = userIds;
            }
        }


</script>
<!--以上必须-->

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></HEAD>

<body>
	<div class="warp">
	<table class="infotan" width="819px"  cellspacing="0"  align="center" style="border:0px ; ">
	 <tr style=" border:0px solid black">
      <td style="width:35%;border:0px solid black;height:0px;"></td>
	  <td style="border:0px solid black;height:0px;"></td>
    </tr>
	
	<tr>
	 <td colspan="2" align="center" style="font-size:40px;height:20px; border:0px solid black;"><input type="hidden" style="height:20px;width:90%;"/></td>	  
	</tr>
	<tr>
	 <td colspan="2" align="center" style="font-size:32px;height:50px; border:0px solid black;"> <strong >信息公开</strong></td>
	</tr>
	<tr>
	 <td colspan="2" align="center" style="font-size:40px;height:10px; border:0px solid black;"></td>	  
	</tr>
	<tr>
		<td align="center" valign="middle"  style="font-size:20px; height:150px; border:1px solid black; ">申请信息内容</td>
		<td align="center" valign="middle"  style="font-size:20px; height:150px; border:1px solid black; "><textarea id="sqxxnr" name="sqxxnr" style="height:145px;width:95%;" ></textarea></td>
	</tr>
	<tr>
		<td align="center" valign="middle"  style="font-size:20px; height:150px; border:1px solid black;  ">办公室拟办意见</td>
		<td align="center" valign="middle"  style="font-size:20px; height:200px; border:1px solid black; "></td>
	</tr>
	<tr>
		<td align="center" valign="middle"  style="font-size:20px; height:150px; border:1px solid black;  ">局领导批示意见</td>
		<td align="center" valign="middle"  style="font-size:20px; height:200px; border:1px solid black; "></td>
	</tr>
	<tr>
		<td align="center" valign="middle"  style="font-size:20px; height:150px; border:1px solid black;  ">承办部门意见<br/>（包括提供材料）</td>
		<td align="center" valign="middle"  style="font-size:20px; height:200px; border:1px solid black; "></td>
	</tr>
	<tr>
		<td align="center" valign="middle"  style="font-size:20px; height:150px; border:1px solid black;  ">分管领导审核</td>
		<td align="center" valign="middle"  style="font-size:20px; height:200px; border:1px solid black; "></td>
	</tr>
	<tr>
		<td align="center" valign="middle"  style="font-size:20px; height:150px; border:1px solid black; ">办公室办理结果</td>
		<td align="center" valign="middle"  style="font-size:20px; height:200px; border:1px solid black; "><textarea id="bljg" name="bljg" style="height:190px;width:95%;" ></textarea></td>
	</tr>
	
	
					
</table>
<br/>

<div style="display:none">
				
         <span id="fjshow"></span>
			<trueway:att  onlineEditAble="true" id="${instanceId}fj" docguid="${instanceId}fj" showId="fjshow" ismain="true" 
			downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add"             otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
</div>
	</div
	><INPUT type="hidden" id="instanceId" name="instanceId" value="">
	<INPUT type="hidden" id="formId" name="formId" value="">
	<INPUT type="hidden" id="workFlowId" name="workFlowId" value="">
	<INPUT type="hidden" id="processId" name="processId" value="">
	<input type="hidden" id="keyword" name="keyword" value="空" />
	<INPUT type="hidden" id="csid" name="csid" value="">
	<INPUT type="hidden" id="zsid" name="zsid" value="">


</body>
<script src="/trueWorkFlow/dwz/style/js/jquery-1.7.1.min.js"></script>
<script src="/trueWorkFlow/dwz/style/js/jquery.tab.js"></script>
<script type="text/javascript">
	//以下必须有
	function loadCss() {
		seajs.use('lib/form', function() {
			$('input[mice-btn]').cssBtn();
			$('input[mice-input]').cssInput();
			$('select[mice-select]').cssSelect();
		});
	}
	//以上必须有
</script>

</html>
