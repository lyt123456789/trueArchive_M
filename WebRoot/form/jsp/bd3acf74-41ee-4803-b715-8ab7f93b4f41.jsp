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
.infotan td{align="center";height:30px;font-size:18px;border:1px #333 solid;padding:5px;color:red;border-color:#FF0000;}
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
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"></HEAD>

<body>
	<div class="warp">
		<table class="infotan" width=819px cellspacing="0" align="center" style="border-top:2px solid black;">
			<caption>
				<br/>
				 <p style="font-size:40px;line-height:35px; color:#000; text-align:center;padding-top:30px;">
				   <strong >
					江苏省南通市民政局发文稿纸		   	</strong>
				</p>
				
					<p>&nbsp;</p><p>&nbsp;</p>
			</caption>
			<tr>
			<td colspan="3" style="border:2px solid black;border-right:none;font-size:18px;color:black;border-left:none;border-bottom:none;">主办单位</td>
			<td colspan="3" style="border:2px solid black;border-right:none;font-size:18px;color:black;border-bottom:none;">主办部门和拟稿人</td>
			</tr>
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;">
			<input id="zbdw" name="zbdw"  value=""/>
			</td>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
			<table width="100%">
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-bottom:none;border-top:none;border-right:none;"><input id="zbbm" name="zbbm"  value=""/> </td>
			</tr>
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-bottom:none;border-top:none;border-right:none;"><input id="ngr" name="ngr"  value=""/> </td>
			</tr>
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-bottom:none;border-right:none;">核稿</td>
			</tr>
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-bottom:none;border-top:none;border-right:none;"> <input id="hg" name="hg"  value=""/> </td>
			</tr>
			</table>
			
			</td>
			</tr>
			
			
			<tr>
			<td colspan="3" style="border:2px solid black;border-right:none;font-size:18px;color:black;border-left:none;border-bottom:none;">抄报或抄送单位</td>
			<td colspan="3" style="border:2px solid black;border-right:none;font-size:18px;color:black;border-bottom:none;">签发</td>
			</tr>
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;">
			<input id="cbcsdw" name="cbcsdw"  value=""/>
			</td>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
			<table width="100%">
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-bottom:none;border-top:none;border-right:none;"><input id="qf" name="qf"  value=""/> </td>
			</tr>
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-bottom:none;border-right:none;">附件</td>
			</tr>
			<tr>
			<td colspan="3" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-bottom:none;border-top:none;border-right:none;"> <input id="fj" name="fj"  value=""/> </td>
			</tr>
			</table>
			
			</td>
			</tr>
			
			<tr>
			<td  style="border:2px solid black;border-right:none;font-size:12px;color:black;border-left:none;border-top:none;">密级：<input  style="width:100px;" id="mj" name="mj"  value=""/></td>
			<td style="border:2px solid black;border-right:none;font-size:12px;color:black;border-left:none;border-top:none;">通民</td>
			<td  style="border:2px solid black;border-right:none;font-size:12px;color:black;border-left:none;border-top:none;">[<input style="width:100px;" id="zi" name="zi"  value=""/>]字</td>
			<td  style="border:2px solid black;border-right:none;font-size:12px;color:black;border-left:none;border-top:none;">第<input style="width:100px;" id="xh" name="xh"  value=""/>号</td>
            <td  style="border:2px solid black;border-right:none;font-size:12px;color:black;border-left:none;border-top:none;">印刷日期：<input style="width:100px;" class="wf-form-text wdate" name="ysrq" id="ysrq" onclick="WdatePicker()" onkeydown="return false;"></td>
            <td  style="border:2px solid black;border-right:none;font-size:12px;color:black;border-left:none;border-top:none;">爆印份数：<input style="width:100px;" id="fs" name="fs"  value=""/></td>
			</tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-left:none;border-bottom:none;border-top:none;border-right:none;">
		    <span style="float:right">第一页</span>
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-bottom:none;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
			<tr>
			<td colspan="6" style="border:2px solid black;font-size:18px;color:black;border-bottom:none;border-left:none;border-top:none;border-right:none;">
		
			</td>
			<tr>
</table>

		
		

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