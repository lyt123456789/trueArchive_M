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
	<script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
		<link href="${ctx}/widgets/tree/css/tree_common.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/widgets/tree/css/tree_common_people.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
	
</head>
<script type="text/javascript">

function initTree(){
	 $("#black").treeview({
	 	url: "departmentTree_getTsByProcessId.do?timestamp="+ new Date().getTime()+"&processId=${processId}&isZBPush=${isZBPush}"
     });
}
function sousuo(){
	var name=$('#mc').val();
	document.getElementById("filetreeDiv").innerHTML="<ul id=\"black\" class=\"filetree\"></ul>"; 
	$("#black").treeview({
	 	url: "departmentTree_getTsByProcessId.do?timestamp="+ new Date().getTime()+"&processId=${processId}&isZBPush=${isZBPush}&mc="+encodeURI(encodeURI(name))
    });
}
document.onkeydown = function(e){
	e = e ? e : window.event;
	var keyCode = e.which ? e.which : e.keyCode;
	if(keyCode == 13)
	{
		if('${isZBPush}'!='1'){
			var name=$('#mc').val();
	    	if(name!=''){
	    		document.getElementById("filetreeDiv").innerHTML="<ul id=\"black\" class=\"filetree\"></ul>"; 
	    		$("#black").treeview({
	    		 	url: "departmentTree_getTsByProcessId.do?timestamp="+ new Date().getTime()+"&processId=${processId}&isZBPush=${isZBPush}&mc="+encodeURI(encodeURI(name))
	    	    });
	    	}
		}
	}
};
var lastSelectedObj=null;

var departmentOrEmployee=null;//当前选中的是部门还是人员(0为部门 1为人员)
var itemId=null;//当前选中的部门id或是人员id
function check(o,type){ 
 	// 使选中节点的背景变为checked样式中的颜色
    if(lastSelectedObj)lastSelectedObj.className='';
    //对新的选中元素的处理
    o.className = "checked";
    lastSelectedObj=o;

    departmentOrEmployee=type;
    itemId=o.id;
    //alert(departmentOrEmployee);alert(itemId);
}

//function setValueTextarea(value){
//	$('#push').val(value);
//}
</script>
  <body  onload="initTree()">
  <div class="pageContent">
	<div class="tabs" currentIndex="1" eventType="click"> 
	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio selected" ><a ><span>附件上传</span></a></li>  
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="padding:1px 0px 1px 0px !important;background-color: white;">
			<table width="790" class="infotan" style="margin-left: 5px;margin-right: 5px;">
			<tr height="80px">
    	    	<Td  width="6%">
    	    		<div style="float: right;">附件:</div>
    	    		<div style="float: left;">
    	    		<trueway:att onlineEditAble="true" id="${id}pushfj" docguid="${id}pushfj" showId="pushfjshow" ismain="true" downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="true" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
				</div>
    	    		</Td>
    			<Td height="200px" width="94%">
    				<span id="pushfjshow"></span>
    			</Td>
    	    </tr>
				<tr height="80px">
				<Td height="200px" width="6%">
    	    		<div style="float: right;">消息:</div>
    	    		</Td>
					<td height="200px" width="94%">
							<textarea cols="100" rows="5" style="width: 100%" id="push"></textarea>
					</td>
				</tr>
			</table>
		</div>
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li style="float:right !important;"><a href="#" onclick="sendNext()"><span style="font-size: 14px">发送</span></a></li>  
				</ul>
			</div>
		</div>
		
		</div>
    	<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
<table style="width: 100%;border-collapse: collapse;">
				<c:if test="${isZBPush!=1}">
				<tr><td>
			<table class="searchContent">
			<tr >
				<td style="padding-left: 12px;">
					<span style='font:12px/18px Arial,sans-serif,"Lucida Grande",Helvetica,arial,tahoma,\5b8b\4f53;'>姓名：</span>
				</td> 
				<td>
					<input type="text" id="mc" style="height: 20px" value="${mc}"/>
				</td>
				<td style="padding-left: 10px">
					<div class="buttonActive"><div class="buttonContent"><button type="button" style="padding-top: 3px"  onclick="sousuo()">检索</button></div></div>
			</td>
			</tr>
		</table>
				</td></tr></c:if>
			<tr>
				<td style="width:260px;">
					<div id="filetreeDiv" style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 370px;border: 1px dashed #C2C2C2 " >
						<ul id="black" class="filetree"></ul>
					</div>
				</td>
				<td  style="width: 80px;text-align: center;padding: 5px;">
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add()"/>
						<br/>
						<br/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del()"/>
						<br/>
						<br/>
				</td> 
				<td style="width: 260px;">
						<select id="oldSelect" size="20" style="width:100%;height: 370px;border: 1px dashed #C2C2C2" multiple="multiple">
						</select>
				</td>
			</tr>
		</table>
			</div>	
		</div>
    </body>

<script>
function add(){	
	if(itemId){
		$.ajax({
			url: '${ctx}/departmentTree_syncGetAllEmployees2.do',
	        type: 'POST',
	        cache: false,
	        async: false,
	        data:"id="+itemId+"&type="+departmentOrEmployee,
			error: function(){
			      alert('AJAX调用错误');
			},
			success: function(msg){
			    if(msg=='-1'){
					alert("数据库错误，请联系管理员!!!");
			    }else{
				    var oldSelect=document.getElementById('oldSelect');
				    var jsobj=eval('('+msg+')');
					if(jsobj){
						for(var i=0;i<jsobj.length;i++){
							//循环遍历人员下拉框
							var isin=false;
							for(var j=0;j<oldSelect.options.length;j++){
								var val = oldSelect.options[j].value;
								if(val.split(",")[0].split("|")[1]==jsobj[i][1]){
									isin=true;break;
								};
							}
							if(!isin){
								oldSelect.options.add(new Option(jsobj[i][1]+' {'+jsobj[i][4]+'}',jsobj[i][0]+'|'+jsobj[i][1]+'|'+jsobj[i][4])); 
							}
						};
					};
		   		}
			}
		});
	}
}

function del(type){
	var oldSelect=document.getElementById('oldSelect');
	var size=0;
	for(var k=0;k<oldSelect.options.length;k++){
		if(oldSelect.options[k].selected) 
    	{ 
			size=size+1;
	    	}
	}
	if(size==0){
		//alert("请选择人员！");
		return false;	
	}else{
		//if(confirm("是否确定删除?")){
			
		   // var roleId=document.getElementById('role.roleId').value;
		   
		    //index,要删除选项的序号，这里取当前选中选项的序号
		    for(var k=0;k<oldSelect.options.length;k++){
		    	if(oldSelect.options[k].selected) 
		    	{ 
					var value=oldSelect.options[k].value;
					oldSelect.options.remove(k--);
					
		    	} 
			}
			
		//}

	}
}

	function sendNext(){
		var xtoName = "";
		var oldSelect  = document.getElementById('oldSelect');
		if(oldSelect.options.length < 1){
			alert("请选择发送人！");
			return false;
		}
		for(var i = 0 ; i < oldSelect.options.length; i++){
			var value=oldSelect.options[i].value.split('|');
			xtoName += value[0] + ",";
		}
		xtoName = xtoName.substring(0, xtoName.length - 1);
		var pushMessage = document.getElementById('push').value;
		var pushResult = '';
		$.ajax({   
			url : '${ctx}/table_pushDb.do?processId=${processId}',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_pushDb.do)');
			},
			data : {
				'xtoName':xtoName,'allInstanceId':'${allInstanceId}','pushMessage':pushMessage,'fijd':'${id}','isZBPush':'${isZBPush}'
			},    
			success : function(result) {  
				if(result == 'success'){
					pushResult = "success";
				}else if(result == 'fail'){
					pushResult = "fail";
				}
			}
		});
		window.close();
		window.returnValue = pushResult;
	}
	
    $('.lio').click(function(){
	   $(this).addClass("selected");
    });
</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
<script type="text/javascript"> 
	//以下必须有
	function loadCss(){ 
   		seajs.use('lib/form',function(){  
			$('input[mice-btn]').cssBtn();
			$('input[mice-input]').cssInput();
			$('select[mice-select]').cssSelect();
	    });
	}
	//以上必须有
</script>
</html>
