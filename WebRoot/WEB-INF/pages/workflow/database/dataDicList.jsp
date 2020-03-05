<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
	<style type="text/css">
	.layout {
	}
	.layout .ly-left {
	  margin-top:0px;
	  float: left;
	  display: block;
	  width: 12%;
	}
	.layout .ly-right {
	  float: left;
	  display: block;
	  width: 88%;
	}
	</style>
	<script type="text/javascript">
	function formsub(allModId){
		if("" == allModId){
			document.getElementById('modId').value="#@@";
		}else{
			document.getElementById('modId').value=allModId;
		}
		document.getElementById('form_nr').submit();
	};
	</script>
</head>
<body>
<div class="layout">
	<div class="ly-left">
			<div class="wf-layout">
				<div  class="wf-list-wrap" >
					<form id="thisForm" method="POST" name="thisForm" action="${ctx}/dataCenter_getDataDicList.do" >
						<table class="wf-fixtable" layoutH="140" >
						<thead>
					    	<tr>
						    	<th align="center" width="80%">数据库名称</th>
							</tr>
				    	</thead>
				    	<tbody>
			    		<c:forEach var="e" items="${list}" varStatus="status"> 
			    		  <tr>
					        <td align="center" id="${e.id}" class="item" onclick="changeBusMod('${e.id}')"  title="${e.modName}" >${e.modName}</td>
					      </tr>
					    </c:forEach>
					    </tbody>
		    		</table>
					</form>
				</div>
			</div>
	</div>
	<div class="ly-right">
		<div class="wf-layout">
			<div  class="wf-list-wrap" >
				<form target="frame_nr" id="form_nr" method="post" action="${ctx}/dataCenter_getDataDicTable.do?t="+Math.random()*1000">
					<input type="hidden" name="modId" id="modId" />
				</form>
				<iframe name="frame_nr" id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:640px;width:98%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" scrolling="no" src=""></iframe>
			</div>
		</div>
	</div>
</div>
</body>
	<script type="text/javascript">
		function sel(){
			var selAll = document.getElementById('selAll');
			var selid = document.getElementsByName('selid');
			for(var i = 0 ; i < selid.length; i++){
				if(selAll.checked){
					selid[i].checked = true;
				}else{
					selid[i].checked = false;
				}
			}
		}
		
		var allModId;
		
		formsub("");
		
		function changeBusMod(modId){
			allModId=modId;
			formsub(allModId);
			$("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+"refresh"+'&t='+Math.random()*1000);
		}
		
	</script>
</html>