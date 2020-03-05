<%@ page language="java" pageEncoding="UTF-8"%><!--告诉服务器，页面用的是UTF-8编码-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门人员组织树</title>
<!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<!--  告诉浏览器，页面用的是UTF-8编码 -->
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/headerindex.jsp"%>
		<link href="${ctx}/widgets/tree/css/tree_common.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/widgets/tree/css/tree_common_people.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
<script type="text/javascript">
	  function initTree(){
	    	 $("#black").treeview({
			 	url: "selectTree_getExchangeDepartmentTree.do?timestamp="+ new Date().getTime()
		     });
     }
	 var lastSelectedObj=null;  
	 var depId = "";
	 function check(o,type){ 
		if(lastSelectedObj)lastSelectedObj.className='';
        //对新的选中元素的处理
        o.className = "checked";
        lastSelectedObj=o;
        depId = o.id;
     }
	</script>
</head>
<body onload="initTree()">
<div class="pageContent">
	<div class="tabs" currentIndex="1" eventType="click">
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li style="float:right !important;"><a href="#" onclick="sendNext()"><span>发送</span></a></li>  
				</ul>
			</div>
		</div>
	</div>
</div>
<form id="thisForm" method="post" name="thisForm" action="${ctx }/table_freeSet.do" >
 <table class="list" width="100%"  height="98%;">
	<tr>
	    <td width="60%">
		    <table class="list" width="100%;"  height="98%;">
			    	<tr >
			    		<td style="width: 50%; height: 450px;">
								<div id="filetreeDiv" style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 450px;border: 1px dashed #C2C2C2 " >
						<ul id="black" class="filetree"></ul>
					</div>				    
						</td>
					    <td  style="width: 80px;text-align: center;padding: 5px;">
								<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add(1)"/>
								<br/>
								<br/>
								<br/>
								<br/>
								<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del(1)"/>
								<br/>
								<br/>
						</td> 
					    <td style="width: 40%;" >
					   	 	<select id="oldSelect" size="20" style="width:100%;height: 450px;border: 1px dashed #C2C2C2" multiple="multiple">
							</select>
					    </td>
			    	</tr>
		    </table>
		  </td>
		 </tr>
		</table>
	</form>
    </body> 
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">
	function add(type){
		var oldSelect=document.getElementById('oldSelect');
		var depName = "";
		//ajax异步获取一下
   		$.ajax({   
   			url : '${ctx}/selectTree_getLhfwNameById.do',
   			type : 'POST',   
   			cache : false,
   			async : false,
   			error : function() {  
   				alert('AJAX调用错误(selectTree_getLhfwNameById.do)');
   			},
   			data : {
   				'type':type, 'depId':depId 
   			},    
   			success : function(msg) { 
   				depName = msg;
   			}
   		});
		if(depName!=null && depName!=''){
			var arrays = depName.split(",");
			//跳过已经选择的
			var id_=arrays[0];
			var name_=arrays[1];
			var isin=false;
			for(var j=0;j<oldSelect.options.length;j++){
				var val = oldSelect.options[j].value;
				if(val==id_){
					isin=true;break;
				};
			}
			if(!isin){
				oldSelect.options.add(new Option(name_,id_)); 
			}
		}
	}
	
	function del(type){
		var oldSelect;
		if(type=='1'){
       		oldSelect=document.getElementById('oldSelect');
		}else if(type=='2'){
			oldSelect=document.getElementById('newSelect');
		}
		var size=0;
		for(var k=0;k<oldSelect.options.length;k++){
			if(oldSelect.options[k].selected) 
	    	{ 
				size=size+1;
		    }
		}
		if(size==0){
			alert("请选择移除的机构！");
			return false;	
		}
		for(var k=0;k<oldSelect.options.length;k++){
		    if(oldSelect.options[k].selected) 
		    { 
				var value=oldSelect.options[k].value;
				oldSelect.options.remove(k--);
			} 
		}
	}
	
	function sendNext(){
		var xtoName = "";
		var oldSelect=document.getElementById('oldSelect');
		if(oldSelect.options.length < 1){
			alert("请选择机构！");
			return false;
		}
		for(var i = 0 ; i < oldSelect.options.length; i++){
			var xtoInfo = oldSelect.options[i].value.split('|');
			xtoName += xtoInfo[0] + ",";
		}
		xtoName = xtoName.substring(0, xtoName.length - 1);
		window.returnValue = xtoName;
		window.close();
	}
</script>
</html>
