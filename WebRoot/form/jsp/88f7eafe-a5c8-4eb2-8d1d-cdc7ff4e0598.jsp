<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/formJs.jsp"%>
<html>
    
    <head>
        <title>
            表单元素说明
        </title>
<style>
body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,legend,button,input,input,th,td
	{
	margin: 0;
	padding: 0px;
}

body,button,input,select,input {
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
.infotan{margin:0 auto; border-color:#FF0000;} 
.infotan td{align="center";height:30px;font-size:18px;border:1px #333 solid;padding:5px;color:black;border-color:#FF0000;}
.infotan td input{border:0px;border-bottom:1px #cccccc dotted;line-height:22px;}
.infotan td select{width:120px;}
.bottominfotan td{line-height:20px;padding:3px;}
.bottominfotan td select{width:120px;}
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
	<table class="infotan" width="819px"  cellspacing="0"  align="center" style="border:2px ; ">
	<caption>
	<center style="font-size:28px;line-height:35px; text-align:center;padding-top:30px;">
	<strong>盐城交通局会议使用申请单</strong>
	<br/>
	<br/>
	</center>
	</caption>
	<tr>
		<td width="20%" align="center" valign="middle" style="font-size:18px;height:60px; border:1px solid black;color:black;">部门</td>
		<td  width="30%" style="text-align:left;border:1px solid black;color:black;height:60px;"  valign="middle">
			<input id="bm" name="bm" style="width:200px; height:35px;" />				
		</td>
		<td width="20%" align="center" valign="middle" style="font-size:18px;height:60px; border:1px solid black;color:black;">申请人</td>
		<td width="30%" style="text-align:left;border:1px solid black;color:black;height:60px;"  valign="middle">
			<input id="sqr" name="sqr" style="width:200px; height:35px;" />				
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">会议主题</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:100px;color:black;"  valign="middle">
			<input id="hyzt" name="hyzt" style="width:450px;height:85px;" />
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">会议人数</td>
		<td style="text-align:left;border:1px solid black;height:60px;color:black;"  valign="middle">
			<input id="hyrs" name="hyrs" style="width:200px;height:35px;" />
		</td>
		<td align="center" style="border:1px solid black;color:black;">申请时间</td>
		<td style="text-align:left;border:1px solid black;height:60px;color:black;"  valign="middle">
			<input id="sqsj" name="sqsj" style="width:200px;height:35px;" />
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">会议类别</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:60px;color:black;"  valign="middle">
			<select id="hylb" name="hylb"  ></select>
				
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">与会人员</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:100px;color:black;"  valign="middle">
			<textarea id="yhry" name="yhry" style="width:450px;height:85px;" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">会议开始时间</td>
		<td style="text-align:left;border:1px solid black;height:60px;color:black;"  valign="middle">
			<input id="hykssj" name="hykssj" style="200px;height:45px;" />
		</td>
		<td align="center" style="border:1px solid black;color:black;">会议结束时间</td>
		<td style="text-align:left;border:1px solid black;height:60px;color:black;"  valign="middle">
			<input id="hyjssj" name="hyjssj" style="width:200px;height:45px;" />
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">预约会议室</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:60px;color:black;"  valign="middle">
			<textarea id="yyhys" name="yyhys" style="height:40px;" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">会议服务要求</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:100px;color:black;padding:10px;line-height:40px;">
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="投影">投影</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="电脑">电脑</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="Apple TV">Apple TV</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="电话会议系统">电话会议系统</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="视频会议系统">视频会议系统</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="拍照">拍照</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="摄像">摄像</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="茶水">茶水</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="矿泉水">矿泉水</input>
			<input type="checkbox" id="hyfwyq" name="hyfwyq" style="height:5px"  value="桌游">桌游</input>
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;height:60px" valign="middle">其他需求</td>
		<td colspan="3" style="text-align:left;border:1px solid black;color:black;" >
		<input style="width:400px;height:40px;" name="hyfwyqa"/></td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">席卡</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:60px;color:black;padding:10px;line-height:40px;"  valign="middle">
			<select id="xk" name="xk" style="height:40px"  ></select>
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">欢迎标语</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:60px;color:black;padding:10px;line-height:40px;"  valign="middle">
			<input type="checkbox" id="hyby" name="hyby" style="height:40px" ></input>
			<input type="checkbox" id="hyby" name="hyby" style="height:40px" ></input>
			<input type="checkbox" id="hyby" name="hyby" style="height:40px" ></input>
		</td>
	</tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">标语内容</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:100px;color:black;padding:10px;line-height:40px;"  valign="middle">
			<input id="bynr" name="bynr" style="width:450px;height:65px;" />
		</td>
	</tr>
	<tr>
	<tr>
		<td align="center" style="border:1px solid black;color:black;">领导批示</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:200px;color:black;"  valign="middle">
			&nbsp;
		</td>
	</tr>
		<td align="center" style="border:1px solid black;color:black;">备注</td>
		<td colspan="3" style="text-align:left;border:1px solid black;height:100px;color:black;padding:15px;line-height:50px;"  valign="middle">
			<input id="bz" name="bz" style="width:450px;height:65px;"  />
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