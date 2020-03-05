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
	<script src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
<script type="text/javascript">
	function showEmpTree(){
		var type = "${type}";
		//var selectBtn = document.getElementById('selectBtnDiv');
		var assisttitle = document.getElementById('assistTitle');
		var assistframe = document.getElementById('assistIFrame');
		//document.getElementById('selectBtnDiv').style.display='none';
		var empDiv = document.getElementById('empTreeDiv');
		empDiv.style.display='block';
		var ahtml = '<iframe id="treeframe"  frameborder="no" marginheight="0" marginwidth="0" style="width:560;height:380;" border="0" src="${ctx}/table_showAssistTree.do?type='+type+'"></iframe>';
		if(type=='xs'){
			document.getElementById('assistType').value='xs';
			//selectBtn.style.display='none';
			assisttitle.innerHTML ='<font style="font-size: 14;font-weight: bold;color: purple">消息 ：</font> <textarea rows="2" name="assistMsg" id="assistMsg" style="width:550px"></textarea>&nbsp;<font color="red">*</font> <br/><br/><font style="font-size: 14;font-weight: bold;color: purple">选择协商人员:</font>';
			assistframe.innerHTML = ahtml;
		}

		if(type=='cs'){
			document.getElementById('assistType').value='cs';
			//selectBtn.style.display='none';
			assisttitle.innerHTML = '<font style="font-size: 14;font-weight: bold;color: purple">选择抄送人员:</font>';
			assistframe.innerHTML = ahtml;
		}

		if(type=='wt'){
			document.getElementById('assistType').value='wt';
			//selectBtn.style.display='none';
			assisttitle.innerHTML = '<font style="font-size: 14;font-weight: bold;color: purple">选择委托人员:</font>';
			assistframe.innerHTML = ahtml;
			
		}
	}
</script>
</head>
    <body onload="showEmpTree();">
    <input type="hidden" name="assistType" id="assistType">
<!--    <div id="selectBtnDiv" style="height: 20px;display:none">-->
<!--	    <div class="panelBar">-->
<!--			<ul class="toolBar">-->
<!--				<li><a href="javascript:showEmpTree('xs');" class="edit"><span>协 商</span> </a></li> -->
<!--				<li class="line">line</li>-->
<!--				<li><a href="javascript:showEmpTree('cs');" class="edit"><span>抄 送</span> </a></li>  -->
<!--				<li class="line">line</li>-->
<!--				<li><a href="javascript:showEmpTree('wt');" class="edit"><span>委 托</span> </a></li>  -->
<!--				<li class="line">line</li>-->
<!--			</ul>-->
<!--		</div>-->
<!--		<div align="center" style="margin-top: 200px" ><font style="font-size: 14;font-weight: bold;color: purple" >请点击按钮</font></div>-->
<!--	</div>				-->
	
    <div id="empTreeDiv" style="display:none;" >
    	<div id="assistTitle" style="height: 20px;"></div>
    	<div id="assistIFrame"> </div>
    	
    </div>
    </body>
</html>
