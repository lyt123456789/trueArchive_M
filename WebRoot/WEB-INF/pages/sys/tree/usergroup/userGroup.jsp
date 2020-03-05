<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body onload="showF('2')">
	<div class="panelBar"> 
		<ul class="toolBar">
			<li><a class="edit" href="javascript:sub();" ><span>保存</span></a></li>
			<li><a class="auxiliary" href="javascript:back();" ><span>返回</span></a></li>
		</ul>
	</div>
<div class="pageContent" style="vertical-align: middle;">
	<div class="tabs" currentIndex="1" eventType="click">
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio selected" onclick=""><a ><span onclick="showF('2')">本部门人员</span></a></li>  
					<li class="lio" onclick=""><a ><span onclick="showF('1')">所有用户</span></a></li>  
					<li class="lio" onclick=""><a ><span onclick="showF('3')">地址簿</span></a></li>
				</ul>
			</div>
		</div>
    	<div class="tabsContent" >
    				<div id="div2" >
						<iframe id="frame100" name="frame100" class="frame10" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/departmentTree_showDepartmentTree.do?isowner=1"></iframe>
					</div>
    				<div id="div1" style="display: none;">
    					<iframe id="frame101" name="frame101" class="frame101"frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/departmentTree_showDepartmentTree.do"></iframe>
					</div>
					<div id="div3" style="display: none;">
						<iframe id="frame10" name="frame10" class="frame10" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src=""></iframe>
					</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
<script type="text/javascript">
function showF(type){
	if(type == '1'){
		document.getElementById('div2').style.display = "none";
		document.getElementById('div3').style.display = "none";
		document.getElementById('div1').style.display = '';
	}else if(type == '2'){
		document.getElementById('div1').style.display = "none";
		document.getElementById('div3').style.display = "none";
		document.getElementById('div2').style.display = '';
	}else if(type == '3'){
		document.getElementById('div1').style.display = "none";
		document.getElementById('div2').style.display = "none";
		document.getElementById('div3').style.display = '';
	}
}

	$('.lio').click(function(){
		$('.lio').removeClass("selected");
		$(this).addClass("selected");
	});
</script>
<script type="text/javascript">
	//保存表单值
	function sub(){
		var div1 = document.getElementById("div1");
		var div2 = document.getElementById("div2");
		var div3 = document.getElementById("div3");
		var oldSelect = null;
		if(div1.style.display != 'none'){
			oldSelect = frames['frame101'].document.getElementById('oldSelect');
		}else if(div2.style.display != 'none'){
			oldSelect = frames['frame100'].document.getElementById('oldSelect');
		}else if(div3.style.display != 'none'){
			oldSelect = frames['frame10'].document.getElementById('oldSelect');
		}
		var xtoId = "";
		var xtoName = "";
		for(var i = 0 ; i < oldSelect.options.length; i++){
			xtoId += oldSelect.options[i].value + ";";
			xtoName += oldSelect.options[i].text + ";";
		}
		var idType = "${idType}";
		var nameType = "${nameType}";
		parent.$("#"+nameType).val(xtoName);
		parent.$("#"+idType).val(xtoId);
		window.returnValue= xtoName+"^|^"+xtoId;
		window.close();
	}
</script>
</html>
