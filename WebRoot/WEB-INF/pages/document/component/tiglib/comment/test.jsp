<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
<script type="text/javascript" src="${ctx}/widgets/component/writeByHand/js/websign.js"></script>
<script type="text/javascript" src="${ctx}/widgets/component/taglib/comment/js/comment.js"></script>
<title></title>
</head>
<body>
	<trueway:comment typeinAble="true" processLinkAble="true" isSatisfied="true" haveReadAble="true" handWriteAble="true" id="8DF10148-4521-4606-A9F1-FCE8A7D3372Aypyj" instanceId="8DF10148-4521-4606-A9F1-FCE8A7D3372A" currentStepId="322E83E4-FEA8-4F15-9F89-2271B95B0738"/>
	
	<!--
		<input type="button" onclick="send()" value="发送到下一步"/>
		<input type="button" onclick="toPrintModel()" value="套打" />
	-->
	<table border="1">
		<tr>
			<td>内容：<td>
			<td id="test">测试<td>
		</tr>
	</table>
	<script type="text/javascript">
	
		function isok(){
			//var isApprovedis=isOrNotApproved('8DF10148-4521-4606-A9F1-FCE8A7D3372Aypyj','8DF10148-4521-4606-A9F1-FCE8A7D3372A','322E83E4-FEA8-4F15-9F89-2271B95B0738');
			//if(!isApprovedis){
				//alert("没有审核意见,不能进入下一步");
				//return false;
			//}
			var isWrite = isOrNotWrite("8DF10148-4521-4606-A9F1-FCE8A7D3372Aypyj","322E83E4-FEA8-4F15-9F89-2271B95B0738");
   			if(!isWrite){
				alert("没有填写意见,不能进入下一步");
				return false;
			}
			
			return true;
		}

		function send(){
			if(isok()){
				alert("进入下一步成功");
			}
		}
		//触发套打，BWGUID和业务相关，取得相对应的对象的值如办文单对象，INSTANCEID：通过其属性得到意见标签所需的对象
		
		function toPrintModel(){ 
			var instansId = "26FCB33C-0589-4200-9DAA-34026BFFCCEA";
			var formId = "2B0C3979-783A-47C5-A7AF-EA04E7C11CC8";
			var docguid = "";
			var sheight = screen.height-70;
	        var swidth = screen.width-10;
	        var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:no;scroll:yes;resizable:yes;center:yes";
	        var tmp=window.showModalDialog('${ctx}/tem_toPrintModel.do?saveAble=false&time='+new Date().getTime()+'&instanceId='+instansId+'&formId='+formId+"&docguid="+docguid,window,winoption);
		}
			
	</script>
</body>

</html>
