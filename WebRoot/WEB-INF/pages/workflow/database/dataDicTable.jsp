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
	  padding: 5px;
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
	  float: left;
	  display: block;
	  width: 43%;
	}
	.layout .ly-right {
	  float: left;
	  display: block;
	  width: 57%;
	}
	</style>
</head>
<body>
<div id="pageContent" class="layout">
	<div class="ly-left">
		<div class="wf-layout">
			<div class="wf-list-top">
			<form id="thisForm1" method="POST" name="thisForm1" action="${ctx}/dataCenter_getDataDicTable.do" >
				<div class="wf-search-bar">
					<input type="hidden" id="modId" name="modId" value="${modId}" />
					<div class="wf-top-tool">
			            <a class="wf-btn" href="javascript:toadd_dataDic_list();">
			                <i class="wf-icon-plus-circle"></i> 新建
			            </a>
			            <a class="wf-btn-primary" href="javascript:toupdate_dataDic_list();">
			                <i class="wf-icon-pencil"></i> 修改
			            </a>
			            <a class="wf-btn-danger" href="javascript:todelete_dataDic_list();" target="_self" title="确定要删除吗？" warn="请选择一个对象">
			                <i class="wf-icon-minus-circle"></i> 删除
			            </a>
			        </div>
			            <label class="wf-form-label" for="">编码：</label>
			            <input type="text"  class="wf-form-text" style="width:60px;" name="dicCode" id="dicCode" value="${dicCode}" placeholder="输入关键字"/>
			            <button class="wf-btn-primary" onclick="search();">
			                <i class="wf-icon-search"></i> 搜索
			            </button>
				</div>
				</form>
			</div>
			<div  class="wf-list-wrap">
					<table class="wf-fixtable" layoutH="140">
						<thead>
							<tr>
								<th width="5%" align="center"><input type="checkbox" name="allDataDicChk" id="allDataDicChk" onclick="allChk_DataDicList();"></th>
								<th width="5%">序号</th>
								<th width="15%">调用表编码</th>
								<th width="15%" >调用表名称</th>
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
							    	<td  workflownnameid="1" title="${e.dicCode}">
										${e.dicCode}
							    	</td>
							    	<td   workflownnameid="1" title="${e.dicName}">
							    		${e.dicName}
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
					<div class="wf-top-tool">
			            <a class="wf-btn" href="javascript:toadd_match_list();">
			                <i class="wf-icon-plus-circle"></i> 新建
			            </a>
			            <a class="wf-btn-primary" href="javascript:toupdate_match_list();">
			                <i class="wf-icon-pencil"></i> 修改
			            </a>
			            <a class="wf-btn-danger" href="javascript:todelete_match_list();" target="_self" title="确定要删除吗？" warn="请选择一个对象">
			                <i class="wf-icon-minus-circle"></i> 删除
			            </a>
			            <a class="wf-btn-green" href="javascript:toset_match_list();">
			                <i class="wf-icon-check-circle"></i> 匹配
			            </a>
			            <a class="wf-btn-blue" href="javascript:toset_permit_list();" >
			                <i class="wf-icon-crosshairs"></i> 设置
			            </a>
			        </div>
			            <label class="wf-form-label" for="">编码：</label>
			            <input type="text" class="wf-form-text" style="width:60px;" name="matchCode" id="matchCode" value="${matchCode}" />
			            <button class="wf-btn-primary" onclick="searchMatch();">
			                <i class="wf-icon-search"></i> 搜索
			            </button>
				</div>
			</div>
			<div id="pageContent2" class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
						<tr>
							<th width="5%" align="center"><input type="checkbox" name="allMatchChk" id="allMatchChk" onclick="allChk_matchList();"></th>
							<th width="8%" align="center">序号</th>
							<th width="30%"  align="center">匹配表编码</th>
							<th width="25%" align="center">匹配表名称</th>
							<th>复制</th>
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

var allModId=document.getElementById("modId").value;

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
function toadd_dataDic_list(){
	if(null==allModId || allModId==''){
		alert('请选择业务模块！');
		return;
	}
	var retVal = window.showModalDialog("${ctx}/dataCenter_toAddDataDic.do?modId=" + allModId + "&a=" + Math.random(),null,"dialogWidth:900px;dialogHeight:380px;help:no;status:no;scroll:no");
	if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
    }else{
	}
	$("#pageContent").load('${ctx}/dataCenter_getDataDicTable.do?modId='+allModId+'&t='+Math.random()*1000);
}

function toadd_match_list(){
	if(null==allDicID || allDicID==''){
		alert('请选择数据字典！');
		return;
	}
	var retVal = window.showModalDialog("${ctx}/dataCenter_toAddMatch.do?dicId=" + allDicID + "&a=" + Math.random(),null,"dialogWidth:600px;dialogHeight:200px;help:no;status:no;scroll:no");
	if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
    }else{
	}
	$("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+allDicID+'&t='+Math.random()*1000);
}

var dataDicIds = document.getElementsByName("chcs_DataDic"); 
var dataDicCount=0;
var dataDicId='';
var modId=null;

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


function toupdate_dataDic_list(){
	getDataDicId();
	if(dataDicCount != 1){
		alert("请选择一条需要修改的数据");
		dataDicCount=0;
		dataDicId="";
		return;
	}
	var retVal = window.showModalDialog("${ctx}/dataCenter_toUpdateDataDic.do?id="+ dataDicId + "&modId=" + allModId + "&a=" + Math.random(),null,"dialogWidth:900px;dialogHeight:380px;help:no;status:no;scroll:no");
	if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
    }else{
	}
	$("#pageContent").load('${ctx}/dataCenter_getDataDicTable.do?modId='+allModId+'&t='+Math.random()*1000);
	dataDicCount=0;
	dataDicId="";
	allDicID="";
}

function toupdate_match_list(){
	getMatchId();
	if(matchCount != 1){
		alert("请选择一条需要修改的数据");
		matchCount=0;
		matchId="";
		return;
	}
	var retVal = window.showModalDialog("${ctx}/dataCenter_toUpdateMatch.do?id="+ matchId + "&dicId=" + allDicID + "&a=" + Math.random(),null,"dialogWidth:900px;dialogHeight:380px;help:no;status:no;scroll:no");
	if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
    }else{
	}
	$("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+allDicID+'&t='+Math.random()*1000);
	matchCount=0;
	matchId="";
	allMatchID="";
}

function toset_match_list(){
	getMatchId();
	if(matchCount != 1){
		alert("请选择一条需要设置匹配关系的数据");
		matchCount=0;
		matchId="";
		return;
	}
	//var w=window.screen.availWidth?window.screen.availWidth:'800';
	//var h=window.screen.availHeight?window.screen.availHeight:'600';
	var w='800';
	var h='400';
	//必须在url后面追加随机参数Math.random(),防止出现缓存，网页对话框可接受返回值
    //如需在子页面操作父页面，则必须把父页面window作为参数传给子页面
	var retVal=window.showModalDialog("${ctx}/dataCenter_toSetMatch.do?matchId="+matchId+"&dicId="+allDicID + "&modId=" + allModId +"&d="+Math.random(),window,'dialogWidth: '+w+'px;dialogHeight: '+h+'px; status: no; scrollbars: no; Resizable: no; help: no;center:yes;');
	if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
    }else{
	}
	$("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+allDicID+'&t='+Math.random()*1000);
	matchCount=0;
	matchId="";
	allMatchID="";
}

function toset_permit_list(){
	getMatchId();
	if(matchCount != 1){
		alert("请选择一条需要设置的数据");
		matchCount=0;
		matchId="";
		return;
	}
	//var w=window.screen.availWidth?window.screen.availWidth:'800';
	//var h=window.screen.availHeight?window.screen.availHeight:'600';
	var w='900';
	var h='400';
	//必须在url后面追加随机参数Math.random(),防止出现缓存，网页对话框可接受返回值
    //如需在子页面操作父页面，则必须把父页面window作为参数传给子页面
	var retVal=window.showModalDialog("${ctx}/dataCenter_toSetPermit.do?matchId="+matchId+"&dicId="+allDicID + "&modId=" + allModId +"&d="+Math.random(),window,'dialogWidth: '+w+'px;dialogHeight: '+h+'px; status: no; scrollbars: no; Resizable: no; help: no;center:yes;');
	if (retVal == 'noChange' || retVal == null || typeof retVal == "undefined" || retVal === undefined || retVal == "") {
    }else{
	}
	$("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+allDicID+'&t='+Math.random()*1000);
	matchCount=0;
	matchId="";
	allMatchID="";
}

function todelete_dataDic_list(){
	getDataDicId();
	if(dataDicCount == 0){
		alert("请选择一条需要删除的数据");
		dataDicCount=0;
		dataDicId="";
		return;
	}
    if(confirm("确定要删除选中数据？")){
        $.ajax({   
            url : '${ctx}/dataCenter_delDataDic.do',
            type : 'POST',   
            cache : false,
            async : false,
            error : function() {  
                alert('链接异常，请检查网络');
            },
            data : {
                'ids':dataDicId
            },    
            success : function(result) {
                var res = eval("("+result+")");
                if(res.success){
                	alert("删除成功");
                	$("#pageContent").load('${ctx}/dataCenter_getDataDicTable.do?modId='+allModId+'&t='+Math.random()*1000);
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

function todelete_match_list(){
	getMatchId();
	if(matchCount == 0){
		alert("请选择一条需要删除的数据");
		matchCount=0;
		matchId="";
		return;
	}
    if(confirm("确定要删除选中数据？")){
        $.ajax({   
            url : '${ctx}/dataCenter_delMatch.do',
            type : 'POST',   
            cache : false,
            async : false,
            error : function() {  
                alert('链接异常，请检查网络');
            },
            data : {
                'ids':matchId
            },    
            success : function(result) {
                var res = eval("("+result+")");
                if(res.success){
                	alert("删除成功");
                	$("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+allDicID+'&t='+Math.random()*1000);
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

function copyUrl(matchId){
	var url = "${ctx}/dataCenter_toSendListFromData.do?matchId="+matchId+"&dicId="+allDicID + "&modId=" + allModId +"&d="+Math.random();
	$.ajax({   
        url : '${ctx}/dataCenter_toCopyUrl.do',
        type : 'POST',   
        cache : false,
        async : false,
        error : function() {  
            alert('复制失败');
        },
        data : {
            'url':url
        },    
        success : function(result) {
            var urlNow = result;
            alert('复制成功');
            window.clipboardData.setData("text",urlNow);
            $("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+allDicID+'&t='+Math.random()*1000);
        }
    });
}

function changeDataDIc(dicId){
	allDicID=dicId;
	$("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+allDicID+'&t='+Math.random()*1000);
}

function chooseMatch(matchId){
	allMatchID=matchId;
}

function search(){
	var dicCode = document.getElementById("dicCode").value;
	$("#pageContent").load('${ctx}/dataCenter_getDataDicTable.do?modId='+allModId+'&t='+Math.random()*1000+'&dicCode='+dicCode);
	dataDicCount=0;
	dataDicId="";
	allDicID="";
}

function searchMatch(){
	var matchCode = document.getElementById("matchCode").value;
	$("#pageContent2").load('${ctx}/dataCenter_getDataDicMatch.do?dicId='+allDicID+'&t='+Math.random()*1000+'&matchCode='+matchCode);
	matchCount=0;
	matchId="";
	allMatchID="";
}
</script>
</html>