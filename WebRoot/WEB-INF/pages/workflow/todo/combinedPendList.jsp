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
  <body onload="showF('2')">
  <div class="pageContent">
	<div class="tabs" currentIndex="1" eventType="click">
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio selected" onclick=""><a ><span onclick="showF('2')">国土局资源管理系统</span></a></li>  
					<li class="lio" onclick=""><a ><span onclick="showF('1')">公文交换平台</span></a></li>  
				</ul>
			</div>
		</div>
    	<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
    				<div id="div2" >
						<iframe id="frame100" name="frame100" class="frame10" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_getDoFileReceiveList.do?status=0"></iframe>
					</div>
    				<div id="div1" style="display: none;">
    					<iframe id="frame101" name="frame101" class="frame101"frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/rec_tobeRecList.do"></iframe>
					</div>
		</div>
	</div>
</div>
</body>
<script>
   $('.lio').click(function(){
	   $('.lio').removeClass("selected");
	   $(this).addClass("selected");
	   });
	function showF(type){
		if(type == '1'){
			document.getElementById('div2').style.display = "none";
			document.getElementById('div1').style.display = '';
		}else if(type == '2'){
			document.getElementById('div1').style.display = "none";
			document.getElementById('div2').style.display = '';
		}else if(type == '3'){
			document.getElementById('div1').style.display = "none";
			document.getElementById('div2').style.display = "none";
		}
	}
</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
</html>
