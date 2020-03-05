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
					文件传阅、拟办单		   	</strong>
				</p>
				
					<p>&nbsp;</p><p>&nbsp;</p>
			</caption>
			<tr>
			<td style="text-align:center;height:80px;width:80px;border:2px solid black;border-right:none;font-size:18px;color:black;">发文单位</td>
			<td colspan="3" style="height:80px;width:100px;border:2px solid black;border-right:none;font-size:18px;color:black;border-top:none;">
			<textarea id="fwdw" name="fwdw"  style="width:100%;height:100%;" value="">
			
			</textarea></td>
			<td style="text-align:center;height:80px;width:60px;border:2px solid black;font-size:18px;color:black;">收文号</td>
			<td colspan="2" style="height:80px;width:50px;border:2px solid black;font-size:18px;color:black;border-top:none;">
			<textarea id="swh" name="swh"  style="height:100%;width:100%" value="">
			
			</textarea></td>
			<td style="text-align:center;height:80px;width:60px;border:2px solid black;font-size:18px;color:black;">发文号</td>
			<td colspan="2" style="height:80px;width:50px;border:2px solid black;font-size:18px;color:black;border-top:none;">
			<textarea id="fwh" name="fwh"  style="height:100%;width:100%" value="">
			
			</textarea></td>
			<td style="text-align:center;height:80px;width:40px;border:2px solid black;font-size:18px;color:black;">份数</td>
			<td style="height:80px;width:80px;border:2px solid black;font-size:18px;color:black;border-top:none;">
			<textarea id="fs" name="fs"  style="height:100%;width:100%" value="">
			
			</textarea></td>
			</tr>
			<tr>
			<td colspan="12" style="height:40px;border:2px solid black;border-bottom:none;font-size:22px;color:black;">拟办意见：</td>
			</tr>
			<tr>
			<td colspan="12" style="height:200px;width:500px;border:2px solid black;border-top:none;font-size:22px;color:black;">
			<textarea style="width:100%;height:100%" id="nbyj" name="nbyj"  value=""></textarea></td>
			</tr>
			<tr>
			<td colspan="6" style="height:40px;border:2px solid black;border-bottom:none;font-size:22px;color:black;">领导批示：</td>
			<td colspan="6" style="height:40px;border:2px solid black;text-align:center;line-height:9px;font-size:22px;color:black;">阅后签名</td>
			</tr>
			<tr>
			<td rowspan="16" colspan="6" style="height:800px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<textarea id="ldsp" name="ldsp"  style="width:100%;height:100%;" value="">
			
			</textarea>
			</td>
			</tr>
			<tr>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qma" name="qma"  value=""/>
			</td>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmb" name="qmb"  value=""/>
			</td>
			</tr>
			<tr>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmc" name="qmc"  value=""/>
			</td>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmd" name="qmd"  value=""/>
			</td>
			</tr>
			<tr>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qme" name="qme"  value=""/>
			</td>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmf" name="qmf"  value=""/>
			</td>
			</tr>
			<tr>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmg" name="qmg"  value=""/>
			</td>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmh" name="qmh"  value=""/>
			</td>
			</tr>
				<tr>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmi" name="qmi"  value=""/>
			</td>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmj" name="qmj"  value=""/>
			</td>
			</tr>
			<tr>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmk" name="qmk"  value=""/>
			</td>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qml" name="qml"  value=""/>
			</td>
			</tr>
			<tr>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmm" name="qmm"  value=""/>
			</td>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmn" name="qmn"  value=""/>
			</td>
			</tr>
			<tr>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmo" name="qmo"  value=""/>
			</td>
			<td colspan="3" style="height:100px;border:2px solid black;border-top:none;font-size:18px;color:black;">
			<input type="text" id="qmp" name="qmp"  value=""/>
			</td>
			</tr>
</table>

		
		

<div style="display:none">
				
         <span id="fjshow"></span>
			<trueway:att  onlineEditAble="true" id="${instanceId}fj" docguid="${instanceId}fj" showId="fjshow" ismain="true" 
			downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add"             otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
</div>
	</div><INPUT type="hidden" id="instanceId" name="instanceId" value="">
	<input   type="hidden" id="formId" name="formId" value="">
	<input   type="hidden" id="workFlowId" name="workFlowId" value="">
	<input  type="hidden" id="processId" name="processId" value="">



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
