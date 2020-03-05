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
.infotan td select{width:120px;}
/* .infotan .label{
				font-size: 24px;
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
  <table class="infotan" width="819px"  cellspacing="0"  align="center" style="border:2px ; ">
    <tr style=" border:0x solid red">
      <td style="width:130px;border:0px solid red;height:1px;"></td>
	  <td style="width:130px;border:0px solid red;height:1px;"></td>
      <td style="width:130px;border:0px solid red;height:1px;"></td>
	  <td style="width:65px;border:0px solid red;height:1px;"></td>
      <td style="width:65px;border:0px solid red;height:1px;"></td>
      <td style="width:65px;border:0px solid red;height:1px;"></td>
      <td style="width:75px;border:0px solid red;height:1px;"></td>
      <td style="border:0px solid red;height:1px;"></td>
    </tr>
	<tr>
	 <td colspan="8" align="center" style="font-size:40px;height:20px; border:0px solid red;color:black;"></td>	  
	</tr>
	<tr>
	 <td align="center" style="font-size:40px;height:50px; border:0px solid red;color:black;"></td>
	 <td colspan="5" align="center" style="font-size:40px;height:50px; border:0px solid red;color:red;border-bottom-width:4px;border-bottom-style:double;"><strong>拟&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;稿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;纸</strong></td>
	  <td colspan="2" align="center" style="font-size:40px;height:50px; border:0px solid red;color:red;"></td>
	</tr>
	<tr>
	 <td colspan="8" align="center" style="font-size:40px;height:20px; border:0px solid red;color:red;"></td>	  
	</tr>
	<tr>
	 <td colspan="4" align="left" style="font-size:32;height:60px; border:1px solid red;color:red;border-right-width:0px;"><strong>&nbsp;&nbsp;盐城市交通运输局文件</strong></td>
	 <td colspan="4" align="left" style="font-size:18px;height:60px; border:1px solid red;color:black;border-left-width:0px;"><input type="text" id="wh" name="wh" style="height:30px;width:250px;" /></td>  
	</tr>
	<tr>
      <td align="center" valign="middle" style="font-size:24px;height:130px; border:1px solid red;color:red;font-family:'楷体_GB2312';">主&nbsp;&nbsp;要<br/>领&nbsp;&nbsp;导<br/>审&nbsp;&nbsp;签</td>
      <td colspan="7" style="text-align:left;border:1px solid red;color:red;height:100px;"  valign="middle"></td>
    </tr>
	<tr>
      <td align="center" valign="middle" style="font-size:24px;height:130px; border:1px solid red;color:red;font-family:'楷体_GB2312';">分&nbsp;&nbsp;管<br/>领&nbsp;&nbsp;导<br/>审&nbsp;&nbsp;签</td>
      <td colspan="7" style="text-align:left;border:1px solid red;color:red;height:100px;"  valign="middle"></td>
    </tr>
	<tr>
      <td align="center" valign="middle" style="font-size:24px;height:130px; border:1px solid red;color:red;font-family:'楷体_GB2312';">办公室<br/>核&nbsp;&nbsp;稿</td>
      <td colspan="3" style="text-align:left;border:1px solid red;color:red;height:130px;"  valign="middle"></td>
	  <td colspan="2" align="center" valign="middle" style="font-size:24px;height:130px; border:1px solid red;color:red;font-family:'楷体_GB2312';">有关科室<br/>或&nbsp;单&nbsp;位<br/>会&nbsp;&nbsp;&nbsp;&nbsp;签</td>
	   <td colspan="2" style="text-align:left;border:1px solid red;color:red;height:130px;"  valign="middle"></td>
    </tr>
	<tr>
      <td align="center" valign="middle" style="font-size:24px;height:130px; border:1px solid red;color:red;font-family:'楷体_GB2312';">科室或单位<br/>负责人<br/>签&nbsp;&nbsp;字</td>
      <td colspan="3" style="text-align:left;border:1px solid red;color:red;height:130px;"  valign="middle"></td>
	  <td colspan="2" align="center" valign="middle" style="font-size:24px;height:130px; border:1px solid red;color:red;font-family:'楷体_GB2312';">拟稿人<br/>签&nbsp;&nbsp;字</td>
	   <td colspan="2" style="text-align:left;border:1px solid red;height:130px;color:black;"  valign="middle">
			<textarea id="nigaoren" name="nigaoren" style="width:90%;height:110px;" ></textarea>
		</td>
    </tr>
	<tr>
      <td align="center" valign="middle" style="font-size:24px;height:50px; border:1px solid red;color:red;font-family:'楷体_GB2312';">密&nbsp;&nbsp;级</td>
      <td style="text-align:left;border:1px solid red;color:black;height:50px;"  valign="middle"><select id="miji" name="miji" style="width:50px; height:65px;" ></select></td>
	  <td align="center" valign="middle" style="font-size:24px;height:50px; border:1px solid red;color:red;font-family:'楷体_GB2312';">可否公开</td>
	   <td colspan="2" style="text-align:left;border:1px solid red;color:black;height:50px;"  valign="middle"><select id="kefougongkai" name="kefougongkai" style="width:50px; height:65px;" ></select></td>
	   <td colspan="2" align="center" valign="middle" style="font-size:24px;height:50px; border:1px solid red;color:red;font-family:'楷体_GB2312';">印发份数</td>
	   <td style="text-align:left;border:1px solid red;color:black;height:50px;"  valign="middle"><input type="text" id="yinfafenshu" name="yinfafenshu" style=" height:30px;width:50px;" /></td>
    </tr>
	<tr>
      <td align="center" valign="middle" style="font-size:24px;height:100px; border:1px solid red;color:red;font-family:'楷体_GB2312';">事&nbsp;&nbsp;由</td>
      <td colspan="7" style="text-align:left;border:1px solid red;height:100px;color:black;"  valign="middle">
			<textarea id="syshiyou" name="syshiyou" style="width:90%;height:80px;" ></textarea>
		</td>
    </tr>
	<tr>
      <td align="center" valign="middle" style="font-size:24px;height:250px; border:1px solid red;color:red;font-family:'楷体_GB2312';">主&nbsp;&nbsp;送</td>
      <td colspan="7" style="text-align:left;border:1px solid red;height:250px;color:black;"  valign="middle">
			<textarea id="zs" name="zs" style="width:90%;height:235px;" ></textarea>
		</td>
    </tr>
	<tr>
      <td align="center" valign="middle" style="font-size:24px;height:250px; border:1px solid red;color:red;font-family:'楷体_GB2312';border-top-width:0px;border-right-width:0px;">抄&nbsp;&nbsp;送</td>
      <td colspan="7" style="text-align:left;border:1px solid red;height:250px;color:black;border-top-width:0px;"  valign="middle">
			<textarea id="cs" name="cs" style="width:90%;height:235px;" ></textarea>
		</td>
    </tr>
	
	
    
	
  </table>
  <div style="display:none">
				
         <span id="fjshow"></span>
			<trueway:att  onlineEditAble="true" id="${instanceId}fj" docguid="${instanceId}fj" showId="fjshow" ismain="true" 
			downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add"             otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
</div>
</div>
	<INPUT type="hidden" id="instanceId" name="instanceId" value="">
	<INPUT type="hidden" id="formId" name="formId" value="">
	<INPUT type="hidden" id="workFlowId" name="workFlowId" value="">
	<INPUT type="hidden" id="processId" name="processId" value="">
	<input type="hidden" id="keyword" name="keyword" value="空" />
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
