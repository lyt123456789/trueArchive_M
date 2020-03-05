<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
   	<%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" media="screen"/>	
	<link href="${ctx}/widgets/tree/css/tree_common.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/widgets/tree/css/tree_common_people.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function showTree(){
		document.getElementById('black').innerHTML='';
		$("#black").treeview({
			url: "departmentTree_getContent.do?isBigDep=0&timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI('${mc}'))
		});
	};
	
	$(document).ready(function(){
		showTree();
	});
	         
	function sousuo(){
		var name=$('#mc').val();
		document.getElementById("filetreeDiv").innerHTML="<ul id=\"black\" class=\"filetree\"></ul>"; 
		$("#black").treeview({
			url: "departmentTree_getContent.do?isBigDep=0&timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI(name))
		});
	}
	        
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

	document.onkeydown = function(e){
		e = e ? e : window.event;
		var keyCode = e.which ? e.which : e.keyCode;
		if(keyCode == 13){
			var name=$('#mc').val();
			if(name!=''){
				document.getElementById("filetreeDiv").innerHTML="<ul id=\"black\" class=\"filetree\"></ul>"; 
				$("#black").treeview({
					url: "departmentTree_getContent.do?isBigDep=0&timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI(name))
				});
			}
		}
	};
</script>
<body style="overflow-y:auto;font-family: 'Microsoft Yahei', Helvetica, Tahoma, sans-serif;">     
	<div class="tw-layout">
		<form id="form1" method="POST" name="thisForm" action="">
			<input id="employeeinfo" name="employeeinfo" type="hidden">
			<table class="tw-table tw-table-form">
				<colgroup>
					<col  width="12%" />
					<col />
				</colgroup>
				<tr>
					<th>部门：</th>
					<td>
						<input type="text" class="tw-form-text" name="depName" id="depName" size="40" readonly="readonly" />
						<a class="tw-btn-orange" href="javascript:selDep();" style="color: white;"><i class="tw-icon-check-square-o"></i> 选择</a>
						<input type="hidden" name="depId" id="depId" value="">
					</td>
				</tr> 
				<tr>
					<th>领导人：</th>
					<td>
						<input type="text" class="tw-form-text" name="leaderName" id="leaderName" size="40"  readonly="readonly" />
						<a class="tw-btn-orange" href="javascript:selEmployee();" style="color: white;"><i class="tw-icon-check-square-o"></i> 选择</a>
						<input type="hidden" name="leaderId" id="leaderId" value="">
					</td>
				</tr>
				<tr>
					<th>人员类型(职位)：</th>
					<td>
						<select name="empType" id="empType" class="tw-form-select">
							<option value="">---请选择---</option>
							<c:forEach items="${typeList}" var="type">
								<option value="${type.value }">${type.key}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="bgs ls">排序号：</th>
					<td><input type="text" class="tw-form-text" name="sortId" id="sortId" size="40"/></td>
				</tr>
				<tr>
					<th class="bgs ls">下属成员：</th>
					<td>
						<table style="width: 100%;border-collapse: collapse;">
							<tr>
								<td>
									姓名:
									<input type="text" class="tw-form-text" id="mc" value="${mc }"/>
									<button type="button" class="tw-btn-primary" onclick="sousuo();" style="color: white;"><i class="tw-icon-search"></i> 搜索</button>
								</td>
							</tr>
							<tr>
								<td style="width:40%;">
									<div id="filetreeDiv" style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 400px;border: 1px dashed #C2C2C2 " >
										<ul id="black" class="filetree"></ul>
									</div>
								</td>
								<td  style="width: 80px;text-align: center;padding: 5px;">
									<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add()"/>
									<br/>
									<br/>
									<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/right2.png" onclick="addAll()"/>
									<br/>
									<br/>
									<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del()"/>
									<br/>
									<br/>
									<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left2.png" onclick="delAll()"/>
								</td> 
								<td style="">
									当前人数:<span id="span_employees" style="color: red">0</span>
									<select id="oldSelect" size="20" style="width:100%;height: 390px;border: 1px dashed #C2C2C2" multiple="multiple">
										<c:forEach var="m" items="${mapList}">
											<option value="${m.employee_id }|${m.employee_name }|${m.employee_shortdn }">${m.employee_name} { ${m.employee_shortdn}}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
                    <td></td>
                    <td>
						<a class="tw-btn-primary tw-btn-lg" style="color: white;" href="javascript:sub();">
							<i class="tw-icon-send"></i> 保存
						</a>
						<a class="tw-btn tw-btn-lg" href="javascript:window.history.go(-1);">
							<i class="tw-icon-minus-circle"></i> 返回
						</a>
					</td>
                </tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">
function selDep(){
	var url ="${ctx}/item_showTree.do?isBigDep=0&notEmployee=1"
	//用layer的模式打开
	layer.open({
		title:'选择部门',
		type:2,
		area:['350px', '450px'],
		btn:["确定","取消"],
		content: url,
		yes:function(index,layero){
		 	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
		 	var ret = iframeWin.selDep();
            var item = ret.split(",");
   		 	var depName = item[1];
   			var depId = item[0];
   			if(depId!=null && depId!='' && depId!='null'){
   				document.getElementById("depName").value=depName;
   			 	document.getElementById("depId").value=depId;
   		 	}else{
   				document.getElementById("depName").value="";
   			 	document.getElementById("depId").value="";
   			}
		 	layer.close(index);
		}
	}); 
}

//选择人员
function selEmployee(){
	var url ="${ctx}/employeeLeader_getLeaderTree.do";
		//用layer的模式打开
		layer.open({
			title:'选择人员',
			type:2,
			area:['350px', '450px'],
			btn:["确定","取消"],
			content: url,
			yes:function(index,layero){
			 	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
			 	var ret = iframeWin.selDep();
	            var item = ret.split(",");
	   		 	var depName = item[1];
	   			var depId = item[0];
	   			if(depId!=null && depId!='' && depId!='null'){
	   				document.getElementById("leaderName").value=depName;
	   			 	document.getElementById("leaderId").value=depId;
	   		 	}else{
	   				document.getElementById("leaderName").value="";
	   			 	document.getElementById("leaderId").value="";
	   			}
			 	layer.close(index);
			}
		}); 
	
	/* var ret=window.showModalDialog("${ctx}/employeeLeader_getLeaderTree.do",null,"dialogWidth:700px;dialogHeight:450px;help:no;status:no");
	if(ret == "undefined" || typeof(ret) == "undefined"){
		 
	 }else{
 		 if(ret==null || ret==''){
 			document.getElementById("leaderName").value="";
			document.getElementById("leaderId").value=""; 
		 }else{
			 var item = ret.split("|");
			 document.getElementById("leaderName").value=item[1];
			document.getElementById("leaderId").value=item[0]; 
		 }
	 } */
}

</script>
<script type="text/javascript">
			window.onload=function(){
				showCount();
			};
			function showCount(){
				document.getElementById('span_employees').innerHTML=document.getElementById('oldSelect').options.length;
			};
			function addAll(){
				$.ajax({
					url: '${ctx}/departmentTree_syncGetAllEmployees.do',
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
								//循环遍历人员下拉框
								for(var i=0;i<jsobj.length;i++){
									var isin=false;
									for(var j=0;j<oldSelect.options.length;j++){
										if(oldSelect.options[j].value==jsobj[i][0]+'|'+jsobj[i][1]+'|'+jsobj[i][4]){
											isin=true;break;
										};
									}
									if(!isin){
										oldSelect.options.add(new Option(jsobj[i][1]+' {'+jsobj[i][4]+'}',jsobj[i][0]+'|'+jsobj[i][1]+'|'+jsobj[i][4]+''));
									};
								};
							};
							showCount();
	   			        }
	   			    }
	   			});
			};

		
			function add(){	
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
								//循环遍历人员下拉框
								for(var i=0;i<jsobj.length;i++){
									var isin=false;
									for(var j=0;j<oldSelect.options.length;j++){
										if(oldSelect.options[j].value==jsobj[i][0]+'|'+jsobj[i][1]+'|'+jsobj[i][4]){
											isin=true;break;
										};
									}
									if(!isin){
										oldSelect.options.add(new Option(jsobj[i][1]+' {'+jsobj[i][4]+'}',jsobj[i][0]+'|'+jsobj[i][1]+'|'+jsobj[i][4]+''));
									};
								};
							};
							showCount();
	   			        }
	   			    }
	   			});
			};

			function delAll(){
				var obj=document.getElementById('oldSelect');
				obj.options.length=0;
				showCount();
			};
		
			function del(){
				var obj=document.getElementById('oldSelect');
				 var size=0;
				for(var k=0;k<obj.options.length;k++){
					if(obj.options[k].selected) 
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
					    for(var k=0;k<obj.options.length;k++){
					    	if(obj.options[k].selected) 
					    	{ 
								var value=obj.options[k].value;
								obj.options.remove(k--);
								
					    	} 
						}
						
					//}
		
				}
				showCount();
	}

			function sub(){
				var depId = document.getElementById("depId").value;		//机构Id
				var depName = document.getElementById("depName").value;		//机构名称
				
				var leaderId = document.getElementById("leaderId").value;		//领导人id
				var leaderName = document.getElementById("leaderName").value;	//领导人姓名
				var empType = document.getElementById("empType").value;  		//人员类型
				var sortId = document.getElementById("sortId").value;			//排序号
				
				if(depId == ""){
					alert("请选择部门！");
					return;
				}
				if(leaderId == ""){
					alert("请选择领导人 ！");
					return;
				}
				
				var obj=document.getElementById('oldSelect');
				var str='';
				if(obj){
					for(var i=0;i<obj.options.length;i++){
						str+=obj.options[i].value+'#';
					};
					if(str!='')str=str.substr(0,str.length-1);
					//alert(str);
					document.getElementById('employeeinfo').value=str;
				};
				
				//ajax异步提交
				$.ajax({
					url: '${ctx}/employeeLeader_saveEmployeeLeader.do',
	                type: 'POST',
	                cache: false,
	                async: false,
	                data:{
	                	'depId':depId, 
	                	'depName':depName,  
	                	'leaderId':leaderId,  
	                	'leaderName':leaderName,  
	                	'sortId':sortId,  
	                	'employeeinfo':str,
	                	'empType':empType
	                },
	   			    error: function(){
	   			        alert('AJAX调用错误');
	   			    },
	   			    success: function(result){
	   			    	var res = eval("("+result+")");
	   					if(res.success){
	   						alert("新增成功");
	   						window.location.href = "${ctx}/employeeLeader_getDepartmentLeaderList.do";
	   					}else{
	   						alert("新增失败");
	   					}
	   			    }
	   			});
			};
</script>
</html>

