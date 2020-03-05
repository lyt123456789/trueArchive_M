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
				height: 16px;
				min-height: 1600px;
				overflow: hidden;
	}*/
table{border-collapse:collapse;border-spacing:0;}
.infotan{width: 820px;margin:0 auto;} 
.infotan td{height:30px;line-height:30px;font-size:18px;border:1px #333 solid;padding:6px;}
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
</script>
<!--以上必须-->
</HEAD>
<body>
<div style="height:950px;">
<br/>
<p style="font-size:40px;line-height:35px; text-align:center;padding-top:30px;">
<strong>南通市规划局文件传阅批办单</strong>
</p>
<br/>
<br/>
<table width="819" class="infotan" align="center">
		<tr>
  <td  width="154px"   colspan="2" valign="middle" style="font-size:18px;text-align:center;"  >来文单位</td>
	  	<td width="250px" colspan="2" style="font-size:18px;height:35px" valign="middle" align="center">
	  		<textarea type="text" id="lwdw" valign="middle" name="lwdw" style="width:150px;height:32px;text-align:center;" ></textarea>
	  </td>
	  <td   width="125px"  valign="middle" colspan="2" style="font-size:18px;text-align:center;" > 来文号</td>
	  <td width="265px" colspan="2" style="font-size:18px;height:35px" valign="middle" align="center">
	  	<textarea type="text" id="lwh" name="lwh" style="width:150px;height:32px;text-align:center;" ></textarea>
</td>
	
	  	
 	</tr>
	<tr>
		<td   width="154px" colspan="2" valign="middle" style="font-size:18px;text-align:center;"> 收文号</td>
		<td width="250px" colspan="2" style="font-size:18px;height:35px" valign="middle" align="center">
	  	<textarea type="text" id="swh" name="swh" style="width:150px;height:32px;text-align:center;" ></textarea>
</td>

		
	
		<td  width="125px" colspan="2"  valign="middle" style="font-size:18px;text-align:center;" >密级</td>
		<td  width="265px" colspan="2" valign="middle" align="center" style="border:1px solid ;color:black;"><select id="mj" name="mj" style="width:50px;"  ></select></td>
 	</tr>
	<tr>
        <td  width="154px"   colspan="2" valign="middle" style="font-size:18px;text-align:center;"  >标题</td>
	  	<td width="650px"  colspan="6" style="font-size:18px;height:65px" valign="middle" align="center">
	  		<textarea type="text" id="bt" valign="middle" name="bt" style="width:600px;height:50px;text-align:center;" ></textarea>
	  </td>
	
	  	
 	</tr>
 	
 		<tr>
 			<td  width="154px" colspan="2" valign="middle" style="font-size:18px;text-align:center;">传阅名单</td>
 			<td   width="250px"  colspan="2" valign="middle" style="font-size:18px;text-align:center;">阅文日期</td>
 			<td colspan="4" valign="top" style="font-size:18px;border-bottom:none;">拟办：</td>
 			</tr>
 	<tr>
		<td  width="80px" colspan="2" height="72" align="center" valign="middle" style="font-size:18px;text-align:center;">马啸平 </td>
		
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywone" name="ywone" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		   <td  colspan="4" rowspan="3" style="font-size:18px;border-top:none" valign="middle" align="center">
		  
		  		
		  </td>
		 
	</tr>

	
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">徐相东</td>
		
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywthree" name="ywthree" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">张&nbsp;&nbsp;&nbsp;彤</td>
		  
		   <td  colspan="2" valign="middle" align="center"style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywfour" name="ywfour" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td  height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">郝民立</td>
		 
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywfive" name="ywfive" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		  <td colspan="4" valign="top" style="font-size:18px;border-bottom:none;">阅批意见：</td>
	</tr>
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">吴&nbsp;&nbsp;&nbsp;刚</td>
		  
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywsix" name="ywsix" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		   <td  colspan="4" rowspan="8" style="font-size:18px;border-top:none" valign="middle" align="center">
		
		  </td>
	</tr>
	<tr>
		<td height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">成媛媛</td>
		 
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywseven" name="ywseven" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">&nbsp;</td>
		
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywtwo" name="ywtwo" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td  height="72"  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">柳&nbsp;&nbsp;&nbsp;毅</td>
		  
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="yweight" name="yweight" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">孙&nbsp;&nbsp;&nbsp;寰</td>
		  
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywnigh" name="ywnigh" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">&nbsp;</td>
		  
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywten" name="ywten" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">詹思慧</td>
		 
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="yweleven" name="yweleven" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">钱圣豹</td>
		 
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywtwelvee" name="ywtwelvee" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2"  valign="middle" style="font-size:18px;text-align:center;">&nbsp; </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywthirteen" name="ywthirteen" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		  <td colspan="4"  valign="top" style="font-size:18px;border-bottom:none;">反馈意见：</td>
	</tr>
	<tr>
		<td   height="72" colspan="2" valign="middle" style="font-size:18px;text-align:center;">&nbsp; </td>
		 
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywfourteen" name="ywfourteen" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		  <td  colspan="4" rowspan="4" style="font-size:18px;border-top:none" valign="middle" align="center">
		
		  		
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2"  valign="middle" style="font-size:18px;text-align:center;">&nbsp; </td>
		  
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywfivteen" name="ywfivteen" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" colspan="2"  valign="middle" style="font-size:18px;text-align:center;">&nbsp; </td>
		 
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="ywsixteen" name="ywsixteen" style="line-height:18px;width:150px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	
	
   
</table>
</div>
<INPUT type="hidden" id="instanceId" name="instanceId" value=""> 
<INPUT type="hidden" id="formId" name="formId" value=""> 
<INPUT type="hidden" id="workFlowId" name="workFlowId" value="">   
<INPUT type="hidden" id="processId" name="processId" value="">  

			<trueway:att  onlineEditAble="true" id="${instanceId}fj" docguid="${instanceId}fj" showId="fjshow" ismain="true" 
					downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" 
					uploadCallback="loadCss" deleteCallback="loadCss"/>

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
