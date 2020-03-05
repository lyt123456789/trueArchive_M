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
	    	 $("#black2").treeview({
				 	url: "selectTree_getDocxgDepartmentTree.do?timestamp="+ new Date().getTime()
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
 <div class="panelBar"> 
 		<ul class="toolBar">
			<li><a onclick="" href="javascript:initDocXgDept();" class="add"><span>初始化公文交换平台机构</span></a></li>
			<li><a onclick="" href="javascript:sub();" class="add"><span>新增</span></a></li>
		</ul>
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_freeSet.do" >
	  <table class="list" width="100%"  height="98%;">
	   <tr>
	    <td width="60%">
		    <table class="list" width="100%;"  height="98%;">
			    	<tr >
			    		<td style="width: 50%; height: 300px;">
								<div id="filetreeDiv" style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 300px;border: 1px dashed #C2C2C2 " >
<!--						<font color="red">国土资源局</font>-->
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
					   	 	<select id="oldSelect" size="20" style="width:100%;height: 300px;border: 1px dashed #C2C2C2" multiple="multiple">
							</select>
					    </td>
			    	</tr>
			  	  <tr >
			    		<td style="width: 50%; height: 300px">
							<div id="filetreeDiv" style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 300px;border: 1px dashed #C2C2C2 " >
									<font color="red">公文交换平台系统</font>
									<ul id="black2" class="filetree"></ul>
							</div>
			    		</td>
					    <td  style="width: 80px;text-align: center;padding: 5px;">
								<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add(2)"/>
								<br/>
								<br/>
								<br/>
								<br/>
								<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del(2)"/>
								<br/>
								<br/>
						</td> 
					    <td style="width: 50%;" >
					    	<select id="newSelect" size="20" style="width:100%;height: 300px;border: 1px dashed #C2C2C2" multiple="multiple">
							</select>
					    </td>
			    	</tr>
		    </table>
		  </td>
		  <td style="width: 30%; vertical-align: top;">
			  <table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
				<thead>
			  		<tr>
						<th width="10%" align="center">序号</th>
						<th width="35%" align="center">崇川机构</th>
						<th align="center" width="45%" >公文交换平台机构</th>
						<th align="center" width="10%" >操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="item" varStatus="status">
						<tr>
							<td width="10%" align="center">${status.count}</td>
							<td width="10%" align="center">${item.gtj_depName }</td>
							<td width="10%" align="center">${item.docxg_depName}</td>
							<td width="10%" align="center">
							<a href="#" onclick="deleteShip('${item.id}');">删除</a>
							
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
		  </td>
		 </tr>
		</table>
	</form>
    </body> 
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">
	function initDocXgDept(){
		//ajax异步获取一下
   		$.ajax({   
   			url : '${ctx}/selectTree_initDocXgDept.do',
   			type : 'POST',   
   			cache : false,
   			async : false,
   			error : function() {  
   				alert('AJAX调用错误(selectTree_initDocXgDept.do)');
   			},
   			data : {},    
   			success : function(msg) { 
   				if(msg=='success'){
   					alert("初始化成功！");
   					window.location.href="${ctx}/selectTree_setDepExchangeRelation.do";
   				}
   			}
   		});
	}

	function add(type){
		var oldSelect;
		var depName = "";
		if(type=='1'){
       		oldSelect=document.getElementById('oldSelect');
		}else if(type=='2'){
			oldSelect=document.getElementById('newSelect');
		}
	
		//ajax异步获取一下
   		$.ajax({   
   			url : '${ctx}/selectTree_getDepNameById.do',
   			type : 'POST',   
   			cache : false,
   			async : false,
   			error : function() {  
   				alert('AJAX调用错误(table_addOfficeName.do)');
   			},
   			data : {
   				'type':type, 'depId':depId 
   			},    
   			success : function(msg) { 
   				depName = msg;
   			}
   		});
		//depName = document.getElementById(depId).innerText;
		
		var length = oldSelect.options.length;
		if(length>0){
			alert("只能选择一个机构");
			return;
		}
		oldSelect.options.add(new Option(depName,depId)); 
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
	
	function sub(){
		var oldSelect = document.getElementById('oldSelect');
		var newSelect = document.getElementById('newSelect');
 		if(oldSelect.options.length>0 && newSelect.options.length>0){
 			var gtjDep = oldSelect.options[0].value;
 			var docxgDep = newSelect.options[0].value;
 			var docxgDepName = newSelect.options[0].text;
 			$.ajax({   
 				url : '${ctx}/selectTree_addDepartmentRelationShip.do',
 				type : 'POST',   
 				cache : false,
 				async : false,
 				error : function() {  
 					alert('AJAX调用错误(selectTree_addDepartmentRelationShip.do)');
 				},
 				data : {
 					'gtj_depId':gtjDep, 'docxg_depId':docxgDep, 'docxgDepName':docxgDepName
 				},    
 				success : function(msg) { 
 					if(msg=='success'){
 						alert("设置成功！");
 						window.location.href="${ctx}/selectTree_setDepExchangeRelation.do";
 					}
 				}
 			});
 		}
	}
	
	
	function deleteShip(id){
		if(confirm("确定删除匹配关系吗？")){
			$.ajax({   
 				url : '${ctx}/fieldMatching_deleteDepShipById.do',
 				type : 'POST',   
 				cache : false,
 				async : false,
 				error : function() {  
 					alert('AJAX调用错误(fieldMatching_deleteDepShipById.do)');
 				},
 				data : {
 					'id':id
 				},    
 				success : function(msg) { 
 					if(msg=='success'){
 						alert("删除成功！");
 						window.location.href="${ctx}/selectTree_setDepExchangeRelation.do";
 					}
 				}
 			});
		}
	}
</script>
</html>
