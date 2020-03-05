<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/formJs.jsp"%>
<html>
<head>
<title></title><style>
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
.infotan td{height:28px;line-height:28px;font-size:18px;border:1px #333 solid;padding:5px;}
.infotan td input{border:0px;border-bottom:1px #cccccc dotted;line-height:22px;height:22px;}
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
<script type="text/javascript" defer>
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
.infotan td {
	text-align:center;
}
.topinfotan td {
	line-height: 30px;
	text-align:left;
        padding:5px;
}

</style>
</HEAD>
<body>
	<div class="warp">
		<center>
			<table width="820" align="center" class="topinfotan" style="height:80px;">
				<tr>
					<td colspan="3" align="center">
						<p style="font-size:30px;line-height:35px;width:820;text-align:center;padding-top:30px;font-family:华文中宋;">南京市玄武区人民政府办公室办文单</p>
					</td>
				</tr>
			</table>
		</center>
<table width="820" class="infotan" align="center" >
	<tr>
	  <td colspan="4" valign="middle" style="border:none;"></td>
		<td colspan="2" style="font-size:18px;height:50px;border:none;" valign="middle" align="center">
			<input type="text" id="rq" name="rq" style="width:150px;height:22px;"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		</td>
	</tr>
	<tr>
		<td width="100px" valign="middle"  style="text-align:center;font-size:18px;line-height:30px;border-left-style:none;">来文单位</td>
		<td width="220px" style="text-align:left;font-size:18px;line-height: 30px;height:100px;border-right-style:none;">
			<textarea  id="lwdw" valign="middle" name="lwdw" style="width:95%;height:95%;" ></textarea>
		</td>
		<td width="50px" valign="middle"  style="text-align:center;font-size:18px;line-height:30px;">原号</td>
		<td width="200px" style="text-align:left;font-size:18px;line-height:30px;height:100px;border-right-style:none;">
			<textarea  id="yh" valign="middle" name="yh" style="width:95%;height:95%;" ></textarea>
		</td>
		<td width="50px" valign="middle"  style="text-align:center;font-size:18px;line-height:30px;">密级</td>
		<td width="200px" style="text-align:left;font-size:18px;line-height:30px;height:100px;border-right-style:none;">
			<select id="mj" name="mj" style="width:50px;"  ></select>
                </td>
	</tr>
	<tr>
	  <td valign="middle"  style="text-align:center;font-size:18px;line-height:30px;border-left-style:none;">文件标题</td>
		<td colspan="5" style="text-align: left;font-size:18px;line-height: 30px;height:100px;border-right-style:none;">
			<textarea  id="wjbt" valign="middle" name="wjbt" style="width:95%;height:95%;" ></textarea>
		</td>
	</tr>
	<tr>
	  <td width="50px" valign="middle"  style="text-align:center;font-size:18px;line-height:30px;border-left-style:none;">拟办</td>
		<td colspan="5" valign="middle"  style="font-size:18px;line-height:30px;height:100px;height:300px;border-left-style:none;border-right-style:none;"></td>
	</tr>
	<tr>
	  <td width="50px" valign="middle"  style="text-align:center;font-size:18px;line-height:30px;border-left-style:none;">领导签批</td>
		<td colspan="5" valign="middle"  style="font-size:18px;line-height:30px;height:100px;height:300px;border-left-style:none;border-right-style:none;"></td>
	</tr>
	<tr>
	  <td width="50px" valign="middle"  style="text-align:center;font-size:18px;line-height:30px;border-left-style:none;">办理情况</td>
		<td colspan="5" valign="middle"  style="font-size:18px;line-height:30px;height:100px;height:300px;border-left-style:none;border-right-style:none;"></td>
	</tr>
	<tr>
	  <td valign="middle" style="text-align: center;font-size:18px;line-height: 30px;border-left-style:none;">备注</td>
		<td colspan="5"  style="text-align: left;font-size:18px;line-height: 30px;height:100px;border-right-style:none;">
			<textarea  id="bz" valign="middle" name="bz" style="width:95%;height:95%;" ></textarea>
		</td>
	</tr>
</table>
<div style="display:none">
	<span id="fjshow"></span>
	<trueway:att  onlineEditAble="true" id="${instanceId}fj" docguid="${instanceId}fj" showId="fjshow" ismain="true" downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
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
	function loadCss(){  
   		seajs.use('lib/form',function(){  
			$('input[mice-btn]').cssBtn();
			$('input[mice-input]').cssInput();
			$('select[mice-select]').cssSelect();
	    });
	}
	//以上必须有
</script>
</html>
