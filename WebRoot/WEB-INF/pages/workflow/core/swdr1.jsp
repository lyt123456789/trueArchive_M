<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>

<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
<script src="${ctx}/widgets/plugin/js/sea.js"></script>  
</head>
  <body  >
       <table class="infotan" width="100%">
		<tr>
			<td class="bgs tc fb">来文信息</td>
		</tr>
	</table>                        
<form action="${ctx}/table_addFw.do" name="itemrelationform" id="itemrelationform" method="post">
	<input type="hidden" name="id" value="${uuid}"/>
	<table width="100%" height="80%" class="infotan">
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">来文标题：</td>
			<td width="80%" >
				<input type="text" name="lwbt"  id="lwbt" style="width: 22%" />
			</td>
		</tr>
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">来文单位：</td>
			<td width="80%" >
				<input type="text" name="lwdw" style="width: 22%"/>
			</td>
		</tr>
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">印发单位：</td>
			<td width="80%" >
				<input type="text" name="yfdw" style="width: 22%"/>
			</td>
		</tr>
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">来文号：</td>
			<td width="80%" >
				<input type="text" name="lwh" style="width: 22%"/>
			</td>
		</tr>
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">发文时间：</td>
			<td width="80%" >
				<input type="text" name="fwsj" style="width: 22%" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="wdate" onkeydown="return false;"/>
			</td>
		</tr>
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">收文时间：</td>
			<td width="80%" >
				<input type="text" name="swsj" style="width: 22%" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="wdate" onkeydown="return false;"/>
			</td>
		</tr>
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">份数：</td>
			<td width="80%" >
				<input type="text" name="fs" style="width: 22%"/></td>
		</tr>
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">事项：</td>
			<td width="80%" >
			<select name="sx"  style="width: 220px">
				<option value="CBBC87E6-45FB-44D8-B400-C237F3A0E4B3">依申请公开</option>
				<option value="1A0FC914-F29A-4DCE-8113-ECD5B5A908F7">市国土局_12345群众诉求_主工作流</option>
				<option value="AE94B59E-12CA-493A-8EE0-935676DB3078">国土局_办文事项</option>
			</select>
		</tr>
		<tr style="height: 40px;">
			<td width="20%" class="bgs ls">公文类型：</td>
			<td width="80%" >
				<select name="gwlx" style="width: 220px">
				<option value="通知">通知</option>
			</select>
		</tr>
		<tr>
		<td width="20%" class="bgs ls">正文
		<trueway:att onlineEditAble="true" id='${uuid}attzw' docguid='${uuid}attzw' showId="attzwshow" ismain="true" uploadAble="true" deleteAble="true" downloadAble="true" previewAble="true" tocebAble="true" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
			</td>
			<td ><span id="attzwshow"></span>
		       	<input id="cebid" name="doc.cebid" type="hidden" value="${doc.cebid}" />
				</td>
		</tr>
			<tr><td width="20%" class="bgs ls">
				附件
				<trueway:att onlineEditAble="true" id='${uuid}fj' docguid='${uuid}fj' showId="fjshow" ismain="true" downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="true" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
			</td>
			<td ><span id="fjshow"></span>
					</td>
			</tr>
	</table>
	
	</form>
	<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
<li><a class="buttonActive" href="javascript:baocun();"><span>保存</span></a></li>
<li><a class="buttonActive" href="javascript:fanhui();"><span>返回</span></a></li>
</ul>
</div>
</body>
</body>

<script type="text/javascript">//以下必须有
function baocun(){
	var title = $('#lwbt').val();
	if(title==''){
		alert('标题不能为空');
		return;
	}
	document.getElementById("itemrelationform").submit();
}
function fanhui(){
	window.location.href='${ctx}/table_getSwdrlbList.do';
}
function loadCss(){  
		seajs.use('lib/form',function(){  
		$('input[mice-btn]').cssBtn();
		$('input[mice-input]').cssInput();
		$('select[mice-select]').cssSelect();
    });
}
//以上必须有</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
</html>
