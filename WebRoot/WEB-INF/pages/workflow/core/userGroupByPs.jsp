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
</head>
  <body >
  <div class="pageContent">
	<div class="tabs" currentIndex="1" eventType="click">
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio selected" onclick=""><a ><span >人员</span></a></li>  
					<li style="float:right !important;"><a href="#" onclick="sendNext()"><span>确定</span></a></li>  
				</ul>
			</div>
		</div>
    	<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
    				<div id="div1" >
    					<iframe id="frame101" name="frame101" class="frame101"frameborder="auto" marginheight="0" marginwidth="0"
    					 border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" 
    					 src="${ctx}/table_toDepartmentJsp2.do?ids=${ids}"></iframe>
    					 
					</div>
				</div>
				</div>
				</div>
    </body>

<script>
	function sendNext(){
		var xtoName = "";
		var oldSelect  = frames['frame101'].document.getElementById('oldSelect');
		for(var i = 0 ; i < oldSelect.options.length; i++){
				var xtoInfo = oldSelect.options[i].value.split('|');
				xtoName += xtoInfo[0]+"|"+xtoInfo[1] + ",";
		}
		if(xtoName){
			xtoName = xtoName.substring(0, xtoName.length - 1);
			window.returnValue = xtoName;
			window.close();
		}else{
			window.returnValue = "2";
			window.close();
		}
	}
	
</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
</html>
