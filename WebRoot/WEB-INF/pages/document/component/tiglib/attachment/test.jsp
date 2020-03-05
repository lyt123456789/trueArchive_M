<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
<script type="text/javascript" src="${ctx}/widgets/component/taglib/attachment/js/attachment.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
<script src="${cdn_js}/sea.js"></script>
<title></title>
</head>
<body>
	<table align="center">
		<tr>
			<td height="80px" width="100px">附件标签</td>
			<td width="100px">
				<trueway:att id="att111" showId="test1" ismain="true"  docguid="wangxftestonlineeditdoc002" downloadAble="true" uploadAble="true" deleteAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" deleteCallback="test" uploadCallback="test"/>
				<trueway:att id="wangxftestonlineeditdoc002attext" ismain="false" docguid="wangxftestonlineeditdoc002"  uploadAble="true" showId="attextshow" deleteAble="true" previewAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="test" deleteCallback="test" />
			</td>
			<td id="test1"  height="80px" width="100px"></td> 
		</tr>
  		<tr>
			<td height="80px" width="100px">文号标签</td>
			<td width="100px">
				<trueway:dn value="" showId="wh" name="doc.fwxh" defineId="EF5A775F-633A-40DB-8658-717C6069E816" tagId="${doc.docguid}dn" webId="sfb" displayVlaue="生成文号"/>
			</td>
			<td id="wh" height="80px" width="100px"></td>
		</tr>
		<tr>
			<td id="test2" colspan="2">
				<input type="button" value="刷新" onclick="refreshAtt('att111')">
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		$("#ok").bind("click",function (){
			alert(getAttCounts("att222"));
		});

		function loadCss(){
        	seajs.use('lib/form',function(){
    			$('input[mice-btn]').cssBtn();
    			$('input[mice-input]').cssInput();
    			$('select[mice-select]').cssSelect();
    	    });
    	}
    	loadCss();
	</script>
</body>

</html>
