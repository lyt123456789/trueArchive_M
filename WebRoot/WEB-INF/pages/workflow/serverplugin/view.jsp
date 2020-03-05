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
	<form id="form1" action="${ctx }/form_addForm.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${serverPlugin.id }"/>
	
			<table class="infotan" width="100%">
				<tr>
				 <td width="20%" class="bgs ls">服务器插件中文名称：</td>
				 <td width="80%">${serverPlugin.cname}</td>
				</tr> 
				<tr>
				 <td width="20%" class="bgs ls">xml文件查看：</td>
				 <td width="80%">
				 	${serverPlugin.xml_name}&nbsp;<a href="${ctx }/serverPlugin_download.do?id=${serverPlugin.id}&type=xml" style="color: red;font: 12px;">下载</a>
				 </td>
				</tr> 
				<tr>
					 <td width="20%" class="bgs ls">jar包查看：</td>
					 <td width="80%">
						 ${serverPlugin.jar_name}&nbsp;<a href="${ctx }/serverPlugin_download.do?id=${serverPlugin.id}&type=jar" style="color: red;font: 12px;">下载</a>
					 </td>
				</tr>
				<tr>
					 <td width="20%" class="bgs ls">入库时间：</td>
					 <td width="80%">
						 <fmt:formatDate value="${serverPlugin.intime}" pattern="yyyy-MM-dd"/>
					 </td>
				</tr>
				<tr>
					 <td width="20%" class="bgs ls">更新时间：</td>
					 <td width="80%">
						 <fmt:formatDate value="${serverPlugin.updatetime}" pattern="yyyy-MM-dd"/>
					 </td>
				</tr>
			</table>

		<div class="formBar pa" style="bottom:0px;width:100%;">  
		<ul class="mr5"> 
			<li><a onclick="goHistroy();" name="CmdView" class="buttonActive"><span>返回</span></a></li>
		</ul>
		</div>
	</form>
	<iframe id="test_ifr" name="test_ifr" style="display: none;"></iframe>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	
	<script type="text/javascript">
		function sub(){
			g.g('form1').target='_self';
			g.g('form1').action='${ctx }/serverPlugin_update.do';
			g.g('form1').submit();
		};
		function goHistroy(){
			parent.$('.page iframe:visible').attr('src',parent.$('.page iframe:visible').attr('src'));
		}
	</script>
	</body>
	<%@ include file="/common/function.jsp"%>
</html>
