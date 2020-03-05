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
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar" style="height: 22px;">
			<div class="wf-top-tool">
	            <a class="wf-btn" href="javascript:check_button();">
	                <i class="wf-icon-plus-circle"></i> 确认选择
	            </a>
	          </div> 
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/item_getItemList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
		    		<th align="center" width="10%" ><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
					<th align="center" width="10%">序号</th>
					<th align="center">群组名称</th>
		    	</tr>
		    	</thead>
		    	<tbody id="table_list" style="line-height: 30px; text-align: center; border: 1px;" >
				</tbody>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
	</div>
</div>
</body>
<script type="text/javascript">
window.onload=function(){ 
	//ajax后台异步获取数据
	var groupInfo = '${groupInfo}';
	if(groupInfo==null || groupInfo==''){				//数据不存在
		$.ajax({   
			url : '${ctx}/table_getGroupChatList.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_getGroupChatList.do)');
			},
			success : function(result) {  
				if(result!=null && result!=''){
					var groups = result.split(';');
					for(var i=0; i<groups.length; i++){
						var datas = groups[i].split(',');
						var id = datas[0];
						var groupName = datas[1];
						conterAdd(id, groupName);
					}
				}
			}
		});
	
	}
};


function sel(){
	var selAll = document.getElementById('selAll');
	var selid = document.getElementsByName('groupId');
	for(var i = 0 ; i < selid.length; i++){
		if(selAll.checked){
			selid[i].checked = true;
		}else{
			selid[i].checked = false;
		}
	}
}



function conterAdd(id, groupName){
	var length = document.getElementById('table_list').rows.length;
	var tr=document.createElement("tr");
	tr.id=(length+1)+"_list";
	var td = document.createElement("td");
	var itemNameText = document.createElement("input");
	itemNameText.setAttribute("type", "checkbox");
	itemNameText.setAttribute("name", "groupId");
	itemNameText.setAttribute("value", id+","+groupName);
	itemNameText.style.width="120px";
	td.appendChild(itemNameText);
	tr.appendChild(td);;
	
	var td = document.createElement("td");
	td.appendChild(document.createTextNode(length+1));
	tr.appendChild(td);
	
	var td = document.createElement("td");
	td.appendChild(document.createTextNode(groupName));
	tr.appendChild(td);
	
	document.getElementById('table_list').appendChild(tr);
}


function check_button(){
	var selid = document.getElementsByName('groupId');
	var ids = "";
	for(var i = 0 ; i < selid.length; i++){
		if(selid[i].checked){
			ids += selid[i].value + ",";
		}
	}
	if(ids == ""){
		alert("请选择群组");
		return;
	}
	//window.returnValue =ids;
	//window.close();
	if(top.window && top.window.process && top.window.process.type){
        var remote = top.window.nodeRequire('remote');
        var browserwindow = remote.require('browser-window');
        var win = browserwindow.fromId(parseInt($.Request('focusedId')));
		if(win){
            win.webContents.send('message-departmentTree',ids);
        }
    }else{
    	opener.window.returnValue = ids;
	    window.close();
	}
}
</script>
<script type="text/javascript">
 $(document).ready(function(){
  $(".zlgx-con tr").mousemove(function(){
   $(this).css("background","#e8eff9");
  });
  
  $(".zlgx-con tr").mouseout(function(){
   $(this).css("background","none");
  }); 
 }); 
$("#chkall").click(
function(){
    if(this.checked){
        $("input[name='checkbox[]']").each(function(){this.checked=true;});
    }else{
        $("input[name='checkbox[]']").each(function(){this.checked=false;});
    }
});
</script>
<script>
(function ($) {
 $.extend({
  Request: function (m) {
   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
   return sValue ? sValue[1] : sValue;
  },
  UrlUpdateParams: function (url, name, value) {
   var r = url;
   if (r != null && r != 'undefined' && r != "") {
    value = encodeURIComponent(value);
    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
    var tmp = name + "=" + value;
    if (url.match(reg) != null) {
     r = url.replace(eval(reg), tmp);
    }
    else {
     if (url.match("[\?]")) {
      r = url + "&" + tmp;
     } else {
      r = url + "?" + tmp;
     }
    }
   }
   return r;
  }
 
 });
})(jQuery);
</script>
</html>