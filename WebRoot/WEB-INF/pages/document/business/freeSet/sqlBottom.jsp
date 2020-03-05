<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
</head>
<script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
<style>
body{font-size:12px;}
span{border:1px solid #88999d;cursor:pointer;width:75px;text-align:center;border-bottom:0px}
.selected{background-color:#88999d;font-weight:700}
</style>
<body>

		<form>
		
			<span id="daiban">待办SQL</span>
			<span id="yiban">已办SQL</span>
			<span id="yibanjie">已办结SQL</span>
			<span style="border-width:0px;cursor:default"></span>
			<span id="yl" onclick="preview();">SQL结果预览</span>
			<span id="setsql" onclick="setSql();">生成SQL</span>
		<div style="border:1px solid #88999d;">
					
		<textarea id="daibansql" style="width:100%;border:0px;height:100px;font-size:12px;" >${daiBanSql }</textarea>
		<textarea id="yibansql" style="width:100%;border:0px;height:100px;font-size:12px; " >${yiBanSql }</textarea>
		<textarea id="yibanjiesql" style="width:100%;border:0px;height:100px;font-size:12px; ">${yiBanJieSql }</textarea>
		</div>
		</form>

</body>	
<script type="text/javascript">
	//预览
	function preview(){
		window.parent.sub();
		var iframe_middle =	 window.parent.frames['iframe_middle'];
		var itemId = iframe_middle.document.getElementById('itemId').value;
		var type = $(".selected").first().attr("id");
		$.ajax({   
			url : '${ctx }/freeSet_checkSql.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {
				alert('Sql语句错误，请确认后提交！');
			},
			data : {
				'itemId':itemId
			},    
			success : function() {  
				window.parent.location.href="${ctx }/freeSet_preview.do?itemId="+itemId+"&preview=preview&type="+type;
			}
		});
	}
	
	//待办、已办、已办结Sql截取
	$("#daiban").click(function(){
		$("#daiban").addClass("selected");
		$("#yiban").removeClass("selected");
		$("#yibanjie").removeClass("selected");
		$("#daibansql").css({"display":"block"});
		$("#yibansql").css({"display":"none"});
		$("#yibanjiesql").css({"display":"none"});
	});
	
	$("#yiban").click(function(){
		$("#daiban").removeClass("selected");
		$("#yiban").addClass("selected");
		$("#yibanjie").removeClass("selected");
		$("#daibansql").css({"display":"none"});
		$("#yibansql").css({"display":"block"});
		$("#yibanjiesql").css({"display":"none"});
	});
	
	$("#yibanjie").click(function(){
		$("#daiban").removeClass("selected");
		$("#yiban").removeClass("selected");
		$("#yibanjie").addClass("selected");
		$("#daibansql").css({"display":"none"});
		$("#yibansql").css({"display":"none"});
		$("#yibanjiesql").css({"display":"block"});
	});
	
	function setSql(){
		var iframe_right =	 window.parent.frames['iframe_right'];
		var sql = iframe_right.document.getElementById('sql').value;
		//待办 sql
		var type = "where p.isexchanging = 0"+
			   " and p.user_uid = '{p.userid.value}'"+
			   " and i.id = p.wf_item_uid"+
			   " and (p.fromnodeid != p.tonodeid or p.step_index = '1')"+
			   " and p.is_over = 'NOT_OVER'"+
			   " and p.is_show = 1"+
			   " and (p.is_back is null or p.is_back != '2')"+
			   " and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null)"+
			   " and (p.action_status is null or p.action_status != 2)";
		$("#daibansql").val(sql.replace("#type#",type));
		//已办sql
		 type =  "where p.isexchanging = 0"+
		   " and p.user_uid = '{p.userid.value}'"+
		   " and i.id = p.wf_item_uid"+
		   " and (p.fromnodeid != p.tonodeid or p.step_index = '1')"+
		   " and p.is_over = 'OVER'"+
		   " and p.is_show = 1"+
		   " and (p.is_back is null or p.is_back != '2')"+
		   " and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null)"+
		   " and (p.action_status is null or p.action_status != 2)"+
		   " and p.finsh_time ="+
		       " (select max(p2.finsh_time)"+
		          " from t_wf_process p2"+
		         " where p.isexchanging = 0"+
		           " and i.id = p.wf_item_uid"+
		           " and (p.fromnodeid != p.tonodeid or p.step_index = '1')"+
		           " and p.is_over = 'OVER'"+
		           " and p.is_show = 1"+
		           " and (p.is_back is null or p.is_back != '2')"+
		           " and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null)"+
		           " and (p.action_status is null or p.action_status != 2)"+
		           " and p.wf_instance_uid = p2.wf_instance_uid)";
		 $("#yibansql").val(sql.replace("#type#",type));  
		 //已办结sql
		type =  "where p.isexchanging = 0"+
		   " and p.user_uid = '{p.userid.value}'"+
		   " and i.id = p.wf_item_uid"+
		   " and (p.fromnodeid != p.tonodeid or p.step_index = '1')"+
		   " and p.is_over = 'OVER'"+
		   " and p.is_show = 1"+
		   " and (p.is_back is null or p.is_back != '2')"+
		   " and (p.IS_DUPLICATE != 'true' or p.IS_DUPLICATE is null)"+
		   " and (p.action_status is null or p.action_status != 2)"+
		   " and p.is_end = 1";
		 $("#yibanjiesql").val(sql.replace("#type#",type));  
	}
	
	//页面刷新默认选中待办
	$(function(){
		 $("#daiban").click();
	});
</script>
</html>
