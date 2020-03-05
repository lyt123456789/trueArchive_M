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
办理时限：<input type="text" id="blsx" valign="right" name="blsx" style="width:150px;line-height:17px;text-align:center;" />
<table width="819" class="infotan" align="center">
	<tr>
        <td  width="154px"   colspan="2" valign="middle" style="font-size:18px;text-align:center;"  >来文单位</td>
	  	<td width="250px"  colspan="2" style="font-size:18px;" valign="middle" align="center">
	  		<input type="text" id="lwdw" valign="middle" name="lwdw" style="width:150px;line-height:17px;text-align:center;" />
	  </td>
	  <td   valign="middle" colspan="2" width="195px" style="font-size:18px;text-align:center;" > 来文号</td>
	  <td width="195px" colspan="2" style="font-size:18px;" valign="middle" align="center">
	  	<input type="text" id="lwh" name="lwh" style="width:150px;line-height:17px;"  /></td>
	
	  	
 	</tr>
	
	<tr>
       
	
		<td   width="154px" colspan="2" valign="middle" style="font-size:18px;text-align:center;"> 收文号</td>
	  	<td  width="250px" colspan="2" style="font-size:18px;" valign="middle" align="center">
		<input type="text" id="swh" name="swh" style="width:150px;line-height:17px;" />
		</td>
		<td  width="195px" colspan="2"  valign="middle" style="font-size:18px;text-align:center;" >登记号</td>
		<td  width="195px" colspan="2" valign="middle" align="center" style="border:1px solid ;color:black;">
		<input type="text" id="djh" name="djh" style="width:150px;line-height:17px;" />
		
		</td>
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
 			<td colspan="4" valign="top" style="font-size:18px;border-bottom:none;">拟办意见：</td>
 			</tr>
 	<tr>
		<td  width="80px" height="72" valign="middle" style="font-size:18px;text-align:center;">朱进华 </td>
		  <td width="74px" style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="zjh" name="zjh" style="line-height:17px;width:74px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="zjhdate" name="zjhdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		   <td  colspan="4" rowspan="2" style="font-size:18px;border-top:none" valign="middle" align="center">
		  
		  		
		  </td>
		 
	</tr>

	
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">王进</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="wj" name="wj" style="line-height:17px;width:74px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="wjdate" name="wjdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">严新民</td>
		  <td style="font-size:18px;" valign="middle" align="center"> 
		  		<input type="text" id="yxm" name="yxm" style="line-height:17px;width:74px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center"style="font-size:18px;text-align:center;">
		  <input  type="text" id="yxmdate" name="yxmdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		   <td colspan="4" valign="top" style="font-size:18px;border-bottom:none;">阅批意见：</td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">范袁斌</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="fyb" name="fyb" style="line-height:17px;width:74px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="fybdate" name="fybdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		   <td  colspan="4" rowspan="7" style="font-size:18px;border-top:none" valign="middle" align="center">
		
		  		
		  </td>
	</tr>
	<tr>
		<td height="72" valign="middle" style="font-size:18px;text-align:center;">王融荣</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="wrr" name="wrr" style="line-height:17px;width:74px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="wrrdate" name="wrrdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td  height="72" valign="middle" align="center" style="font-size:18px;text-align:center;">孙建坤</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="sjk" name="sjk" style="width:74px;line-height:17px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="sjkdate" name="sjkdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">冯亚军</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="fyj" name="fyj" style="line-height:17px;width:74px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="fyjdate" name="fyjdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">严汉平</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="yhp" name="yhp" style="line-height:17px;width:74px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="yhpdate" name="yhpdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">张庆国</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="zqg" name="zqg" style="line-height:17px;width:74px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="zqgdate" name="zqgdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">张进</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="zj" name="zj" style="line-height:17px;width:35px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="zjdate" name="zjdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">崔恒友</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="chy" name="chy" style="line-height:17px;width:35px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="chydate" name="chydate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		  <td colspan="4"  valign="top" style="font-size:18px;border-bottom:none;">办理结果：</td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">严士平 </td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="ysp" name="ysp" style="line-height:17px;width:35px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="yspdate" name="yspdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
		  <td  colspan="4" rowspan="4" style="font-size:18px;border-top:none" valign="middle" align="center">
		
		  		
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">邹美华</td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="zmh" name="zmh" style="line-height:17px;width:35px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="zmhdate" name="zmhdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">顾彬 </td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="gb" name="gb" style="line-height:17px;width:35px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="gbdate" name="gbdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		  </td>
	</tr>
	<tr>
		<td   height="72" valign="middle" style="font-size:18px;text-align:center;">刘红娟 </td>
		  <td style="font-size:18px;" valign="middle" align="center">
		  		<input type="text" id="lhj" name="lhj" style="line-height:17px;width:35px;" />
		  </td>
		   <td  colspan="2" valign="middle" align="center" style="font-size:18px;text-align:center;">
		  <input  type="text" id="lhjdate" name="lhjdate" style="line-height:18px;width:95px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
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