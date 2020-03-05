<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	  padding: 0px;
	  *zoom: 1;
	}
	.layout:before, .layout:after {
	  content: "";
	  display: table;
	}
	.layout:after {
	  clear: both;
	}
	.layout .ly-left {
	  margin-top:0px;
	  float: left;
	  display: block;
	  width: 50%;
	}
	.layout .ly-right {
	  float: left;
	  display: block;
	  width: 50%;
	}
	</style>
</head>
<body>
<div id="pageContent" class="layout">
	<div class="ly-left">
		<div class="wf-layout">
			<div class="wf-list-top">
			<form id="thisForm1" method="POST" name="thisForm1" action="${ctx}/depConfig_getDep_F.do" >
				<div class="wf-search-bar">
					<input type="hidden" id="superior_guid" name="superior_guid" value="${superior_guid}" />
		            <a class="wf-btn" href="javascript:toadd_depConfig_list();">
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-primary" href="javascript:toupdate_depConfig_list();">
		                <i class="wf-icon-pencil"></i> 修改
		            </a>
		            <a class="wf-btn-danger" href="javascript:todelete_depConfig_list();" target="_self" title="确定要删除吗？" warn="请选择一个对象">
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
				</div>
				</form>
			</div>
			<div  class="wf-list-wrap" style="padding:0px 0px 0 10px">
					<table class="wf-fixtable" layoutH="140">
						<thead>
							<tr>
								<th width="5%" align="center"><input type="checkbox" name="allDataDicChk" id="allDataDicChk" onclick="allChk_DataDicList();"></th>
								<th width="5%">序号</th>
								<th width="15%">父部门名称</th>
								<th width="15%" >排序号</th>
							</tr>
						</thead>
				    	<tbody>
							<c:forEach var="e" items="${list}" varStatus="status"> 
								<tr onclick="changeDataDIc('${e.id}')">
									<td align="center">
						    			<input type="checkbox" name="chcs_DataDic" value="${e.id}" >
						    		</td>
							    	<td align="center" itemid="${e.id}">
							    		${status.count}
							    	</td>
							    	<td align="center" workflownnameid="1" title="${e.name}">
										${e.name}
							    	</td>
							    	<td align="center"  workflownnameid="1" title="${e.orderNum}">
							    		${e.orderNum}
							    	</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
			</div>
		</div>
	</div>
	<div class="ly-right">
		<div class="wf-layout">
			<div class="wf-list-top">
				<div class="wf-search-bar">
		            <a class="wf-btn" href="javascript:toadd_depChild_list();">
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-primary" href="javascript:toupdate_depChild_list();">
		                <i class="wf-icon-pencil"></i> 修改
		            </a>
		            <a class="wf-btn-danger" href="javascript:todelete_depChild_list();" target="_self" title="确定要删除吗？" warn="请选择一个对象">
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
				</div>
			</div>
			<div id="pageContent2" class="wf-list-wrap" style="padding:0px 0px 0 10px">
				<table class="wf-fixtable" layoutH="140">
					<thead>
						<tr>
							<th width="5%" align="center"><input type="checkbox" name="allMatchChk" id="allMatchChk" onclick="allChk_matchList();"></th>
							<th width="5%" align="center">序号</th>
							<th width="15%"  align="center">子部门名称</th>
							<th width="15%" align="center">排序号</th>
						</tr>
					</thead>
			    	<tbody>
					
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
var allDicID="";

var allMatchID="";

function allChk_DataDicList(){
	var chk = $('#allDataDicChk').prop('checked');
	if(chk){
		for( var i = 0; i < dataDicIds.length; i++ ){ 
			dataDicIds[i].checked=true;
		    /* sjzdId += sjzdIds[i].value+','; */
		} 
		/* sjzdCount=sjzdIds.length;
		sjzdId = sjzdId.substr(0,sjzdId.length-1); */
	}else{
		for( var i = 0; i < dataDicIds.length; i++ ){ 
			dataDicIds[i].checked=false;
		}
		dataDicCount=0;
		dataDicId = ""; 
	}
}
function toadd_depConfig_list(){
	var url = "${ctx}/depConfig_toAddDep_F.do?superior_guid=${superior_guid}&a=" + Math.random();
	//用layer的模式打开
	layer.open({
		title:'新加部门',
		type:2,
		area:['550px', '450px'],
		btn:["保存","取消"],
		content: url,
		yes:function(index,layero){
			var iframeWin = window[layero.find('iframe')[0]['name']];
			iframeWin.checkForm();
    		$("#pageContent").load('${ctx}/depConfig_getDep_F.do?t='+Math.random()*1000);
		}
	}); 
}

function toupdate_depConfig_list(){
	getDataDicId();
	if(dataDicCount != 1){
		alert("请选择一条需要修改的数据");
		dataDicCount=0;
		dataDicId="";
		return;
	}
	var url = "${ctx}/depConfig_toUpdateDep_F.do?id="+dataDicId+"&a=" + Math.random();
	//用layer的模式打开
	layer.open({
		title:'修改部门',
		type:2,
		area:['550px', '450px'],
		btn:["保存","取消"],
		content: url,
		yes:function(index,layero){
			var iframeWin = window[layero.find('iframe')[0]['name']];
			iframeWin.checkForm();
    		$("#pageContent").load('${ctx}/depConfig_getDep_F.do?t='+Math.random()*1000);
		}
	}); 
	dataDicCount=0;
	dataDicId="";
	allDicID="";
}

function todelete_depConfig_list(){
	getDataDicId();
	if(dataDicCount == 0){
		alert("请选择一条需要删除的数据");
		dataDicCount=0;
		dataDicId="";
		return;
	}
    if(confirm("确定要删除选中数据？")){
        $.ajax({   
            url : '${ctx}/depConfig_delDep_F.do',
            type : 'POST',   
            cache : false,
            async : false,
            error : function() {  
                alert('AJAX调用错误(depConfig_delDep_F.do)');
            },
            data : {
                'ids':dataDicId
            },    
            success : function(result) {
                var res = eval("("+result+")");
                if(res.success){
                	alert("删除成功");
                	$("#pageContent").load('${ctx}/depConfig_getDep_F.do?t='+Math.random()*1000);
                	dataDicCount=0;
                	dataDicId="";
                }else{
                    alert("删除失败");
                }
            }
        });
    }else{
    	dataDicCount=0;
    	dataDicId="";
    }
}

function changeDataDIc(dicId){
	allDicID=dicId;
	$("#pageContent2").load('${ctx}/depConfig_getDep_C.do?id='+allDicID+'&t='+Math.random()*1000);
}

function toadd_depChild_list(){
	if(null==allDicID || allDicID==''){
		alert('请选择父部门！');
		return;
	}
	var url = "${ctx}/depConfig_toAddDep_C.do?id="+allDicID+"&a=" + Math.random();
	//用layer的模式打开
	layer.open({
		title:'新加部门',
		type:2,
		area:['550px', '450px'],
		btn:["保存","取消"],
		content: url,
		yes:function(index,layero){
			var iframeWin = window[layero.find('iframe')[0]['name']];
			iframeWin.checkForm();
			$("#pageContent2").load('${ctx}/depConfig_getDep_C.do?id='+allDicID+'&t='+Math.random()*1000);
		}
	}); 
	
}

function toupdate_depChild_list(){
	getMatchId();
	if(matchCount != 1){
		alert("请选择一条需要修改的数据");
		matchCount=0;
		matchId="";
		return;
	}
	var url = "${ctx}/depConfig_toUpdateDep_C.do?id="+matchId+"&a=" + Math.random();
	//用layer的模式打开
	layer.open({
		title:'修改部门',
		type:2,
		area:['550px', '450px'],
		btn:["保存","取消"],
		content: url,
		yes:function(index,layero){
			var iframeWin = window[layero.find('iframe')[0]['name']];
			iframeWin.checkForm();
			$("#pageContent2").load('${ctx}/depConfig_getDep_C.do?id='+allDicID+'&t='+Math.random()*1000);
		}
	}); 
	
	matchCount=0;
	matchId="";
	allMatchID="";
}

function todelete_depChild_list(){
	getMatchId();
	if(matchCount == 0){
		alert("请选择一条需要删除的数据");
		matchCount=0;
		matchId="";
		return;
	}
    if(confirm("确定要删除选中数据？")){
    	$.ajax({   
            url : '${ctx}/depConfig_delDep_C.do',
            type : 'POST',   
            cache : false,
            async : false,
            error : function() {  
                alert('AJAX调用错误(depConfig_delDep_C.do)');
            },
            data : {
                'ids':matchId
            },    
            success : function(result) {
                var res = eval("("+result+")");
                if(res.success){
                	alert("删除成功");
                	$("#pageContent2").load('${ctx}/depConfig_getDep_C.do?id='+allDicID+'&t='+Math.random()*1000);
                	matchCount=0;
                	matchId="";
                }else{
                    alert("删除失败");
                }
            }
        });
    }else{
    	matchCount=0;
    	matchId="";
    }
}



var dataDicIds = document.getElementsByName("chcs_DataDic"); 
var dataDicCount=0;
var dataDicId='';

function getDataDicId(){
	for( var i = 0; i < dataDicIds.length; i++ ){ 
	    if ( dataDicIds[i].checked ){
	    	dataDicId += dataDicIds[i].value+',';
	    	dataDicCount += 1;
		}
	} 
	if(""==dataDicId&&(""!=allDicID)){
		dataDicId = allDicID + ",";
		if("" != dataDicId){
			dataDicCount += 1;
		}
	}
	dataDicId = dataDicId.substr(0,dataDicId.length-1);
}

var matchIds = document.getElementsByName("chcs_match"); 
var matchCount=0;
var matchId='';
function getMatchId(){
	for( var i = 0; i < matchIds.length; i++ ){ 
	    if ( matchIds[i].checked ){
	    	matchId += matchIds[i].value+',';
	    	matchCount += 1;
		}
	} 
	if(""==matchId && ""!=allMatchID ){
		matchId = allMatchID + ",";
		if("" != matchId){
			matchCount += 1;
		}
	}
	matchId = matchId.substr(0,matchId.length-1);
}

function allChk_DataDicList(){
	var chk = $('#allDataDicChk').prop('checked');
	if(chk){
		for( var i = 0; i < dataDicIds.length; i++ ){ 
			dataDicIds[i].checked=true;
		    /* sjzdId += sjzdIds[i].value+','; */
		} 
		/* sjzdCount=sjzdIds.length;
		sjzdId = sjzdId.substr(0,sjzdId.length-1); */
	}else{
		for( var i = 0; i < dataDicIds.length; i++ ){ 
			dataDicIds[i].checked=false;
		}
		dataDicCount=0;
		dataDicId = ""; 
	}
}

function allChk_matchList(){
	var chk = $('#allMatchChk').prop('checked');
	if(chk){
		for( var i = 0; i < matchIds.length; i++ ){ 
			matchIds[i].checked=true;
		    /* sjzdId += sjzdIds[i].value+','; */
		} 
		/* sjzdCount=sjzdIds.length;
		sjzdId = sjzdId.substr(0,sjzdId.length-1); */
	}else{
		for( var i = 0; i < matchIds.length; i++ ){ 
			matchIds[i].checked=false;
		}
		matchCount=0;
		matchId = ""; 
	}
}


function chooseMatch(matchId){
	allMatchID=matchId;
}


</script>

<%@ include file="/common/function.jsp"%>
<script>
$('#pageContent').height($(window).height()-70);
</script>
	
</html>