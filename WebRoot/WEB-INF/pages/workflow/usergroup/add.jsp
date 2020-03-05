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
	
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<!--  告诉浏览器，页面用的是UTF-8编码 -->
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<link href="${ctx}/widgets/tree/css/tree_common.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/widgets/tree/css/tree_common_people.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
		<script type="text/javascript">
			function showTree(){
				document.getElementById('black').innerHTML='';
				$("#black").treeview({
					   url: "departmentTree_getContent.do?timestamp="+ new Date().getTime()
				});
			};
	        $(document).ready(function(){
	        	showTree();
	        });
	         
            
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

            
	    </script>
</head>

<body > 
	<div class="panelBar"> 
		<ul class="toolBar">
			<li><a class="edit" href="javascript:sub();" ><span>保存</span></a></li>
			<li><a class="auxiliary" href="javascript:back();" ><span>返回</span></a></li>
		</ul>
	</div>
	<form id="form1" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
			<input type="hidden" name="type" value="${type }"/>
			<input type="hidden" name="employeeinfo" id="employeeinfo" value=""/>
			<input type="hidden" name="id" value="${innerUser.id }"/>
	
<table class="infotan" width="100%">
<tr>
 <td width="20%" class="bgs ls">自定义组名：</td>
 <td width="80%"><input type="text" name="userGroup.name" id="teamName" size="40"/></td>
</tr> 
<tr>
 <td class="bgs ls">排序号：</td>
 <td><input type="text" name="userGroup.zindex" id="sortId" size="40"/>（注：按数值大小倒序）</td>
</tr>
<tr>
 <td class="bgs ls">组优先级：</td>
 <td><input type="text" name="userGroup.grade" id="grade" size="40"/>（注：非必填按需设置）</td>
</tr>
</table>

<table style="width: 100%;border-collapse: collapse;">
			<tr>
				<td style="width:40%;">
					<div style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 300px;border: 1px dashed #C2C2C2 " >
						<!-- <a href="javascript:showTree();" style="text-decoration: none;">刷新</a> -->
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
						当前人数:<span id="span_employees" >0</span>
						<select id="oldSelect" size="20" style="width:100%;height: 290px;border: 1px dashed #C2C2C2" multiple="multiple">
							<c:forEach var="m" items="${mapList}">
								<option value="${m.employee_id }|${m.employee_name }|${m.employee_shortdn }">${m.employee_name} { ${m.employee_shortdn} }</option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
 

<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
	<li><a  name="CmdView" class="buttonActive" href="javascript:sub();"><span>保存</span></a></li>
	<li><a onclick="" name="CmdView" class="buttonActive" href="javascript:window.history.go(-1);"><span>返回</span></a></li>
</ul>
</div>
	</form>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	
	<script type="text/javascript">
			window.onload=function(){
				showCount();
			};
			function showCount(){
				document.getElementById('span_employees').innerHTML=document.getElementById('oldSelect').options.length;
			};
			function addAll(){
				if(!confirm('确定添加所有人员'))return;
				alert('请耐心等待片刻。。。');
				var department_rootId='${department_rootId}';
				$.ajax({
					url: '${ctx}/departmentTree_syncGetAllEmployees.do',
	                type: 'POST',
	                cache: false,
	                async: false,
	                data:"id="+department_rootId+"&type=0",
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
				var teamName = document.getElementById("teamName").value;
				var sortId = document.getElementById("sortId").value;
				var grade = document.getElementById("grade").value;
				if(teamName == ""){
					alert("请填写组名！");
				}else if(sortId == ""){
					alert("请填写排序号 ！");
				}else{
				    var re = /^[1-9]\d*/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/   
				    if (!re.test(sortId)||(grade != "" &&!re.test(grade))){
					    alert("请输入数字！");
					    return ;
					}else{
			   			var obj=document.getElementById('oldSelect');
						var str='';
						if(obj){
							for(var i=0;i<obj.options.length;i++){
								str+=obj.options[i].value+'#';
							};
							if(str!='')str=str.substr(0,str.length-1);
							document.getElementById('employeeinfo').value=str;
							
						};
						alert('数据保存中,请耐心等待。。。');
						g.g('form1').action='${ctx}/userGroup_addUserGroup.do';
						g.g('form1').submit();
					}
				}
			};
			function back(){
				location.href = "${ctx}/userGroup_getUserGroupList.do";
			};
		</script>
	
	</body>
	
</html>
