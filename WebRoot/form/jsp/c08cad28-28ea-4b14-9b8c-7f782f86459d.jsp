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
.infotan td{align="center";height:30px;font-size:18px;border:1px #333 solid;padding:5px;color:black;border-color:#FF0000;}
.infotan td input{border:0px;border-bottom:1px #cccccc dotted;line-height:22px;}
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
			<caption>
				<br/>
				 <p style="font-size:40px;line-height:35px; color:#FF0000; text-align:center;padding-top:50px;">
				   <strong >
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   	</strong>
				</p>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
			</caption>
		
		
			<tr>
				<td width="13%"  align="center" valign="middle"  style="font-size:18px; height:65px; border:1px solid red; color:red; ">文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</td>
			  <td width="40%"  style="border:1px solid red;font-size:26px;font-family:STFangsong;color:black;">
						<input style="height:33px;width:240px;padding:0 2 0 0;" type="text" id="wh" name="wh"  value="" /></td>
			  <td width="12%" align="center" style="font-size:18px; height:18px; border:1px solid red; color:red;">紧急程度</td>
				<td width="10%" align="center" style="font-size:26px;font-family:STFangsong; border:1px solid red;color:black; "><select id="jjcd" name="jjcd" style="width:50px;" ></select></td>
				<td width="10%" align="center" style="font-size:18px; border:1px solid red; color:red;">份数</td>
				<td width="15%" align="center" style="font-size:26px;font-family:STFangsong;border:1px solid red; color:black;">
				<input id="syfs" name="syfs"  style="width:50px;"   /></td>
			</tr>
			 <tr>
				<td align="center" valign="middle" style="font-size:18px; height:65px; border:1px solid red; color:red;">发文单位</td>
				<td colspan="5" style="font-size:26px;font-family:STFangsong;border:1px solid red; height:65px;color:black;">
                <textarea id="fwdw" name="fwdw" style="width:600px; height:50px;" ></textarea>
         </td>
			</tr>
     <tr>
		<td height="104" align="center" valign="middle" style="font-size:18px;  height:150px; border:1px solid red; color:red;">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题</td>
		<td colspan="5" style="font-size:26px;font-family:STFangsong;border:1px solid red;height:100px;color:black;">
			<textarea id="bt" name="bt" style="width:600px; height:135px;" ></textarea>				
		</td>
		  </tr>
			<tr>
				<td align="center" valign="middle" style="font-size:18px;height:900px; border:1px solid red; color:red; border-bottom:2px solid red;">主送单位</td>
				<td  style="font-size:26px;font-family:STFangsong;text-align:left;border:1px solid red;;border-bottom:2px solid red;color:black;height:900px;"  valign="top">
					<textarea id="zsdw" name="zsdw" style="width:280px;height:850px;" ></textarea>				
					</td>
			    <td align="center" style="border:1px solid red;border-bottom:2px solid red;color:red;">抄送单位</td>
			    <td colspan="3" style="font-size:26px;font-family:STFangsong;text-align:left;border:1px solid red;border-bottom:2px solid red;height:900px;color:black;"  valign="top">
			        <textarea id="csdw" name="csdw" style="width:280px;height:850px;" ></textarea>
             </td>
		    </tr>
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
