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
		<script src="${ctx}/widgets/tree/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
		
</head>
<script type="text/javascript">
		   
	        function initTree(){
		    	 $("#black").treeview({
				 	url: "departmentTree_getContentCylxr.do?timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI('${mc}'))
			     });
	        }
	        function sousuo(){
	        	var name=$('#mc').val();
	        	document.getElementById("filetreeDiv").innerHTML="<ul id=\"black\" class=\"filetree\"></ul>"; 
	        	$("#black").treeview({
				 	url: "departmentTree_getContentCylxr.do?timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI(name))
			    });
	        }
	        document.onkeydown = function(e){
	        	e = e ? e : window.event;
	        	var keyCode = e.which ? e.which : e.keyCode;
	        	if(keyCode == 13)
	        	{
	            	var name=$('#mc').val();
	            	if(name!=''){
	            		document.getElementById("filetreeDiv").innerHTML="<ul id=\"black\" class=\"filetree\"></ul>"; 
	    	        	$("#black").treeview({
	    				 	url: "departmentTree_getContentCylxr.do?timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI(name))
	    			    });
	            	}
	        	}
	        };
            var lastSelectedObj=null;
            var lx=null;
            var departmentOrEmployee=null;//当前选中的是部门还是人员(0为部门 1为人员)
            var itemId="";//当前选中的部门id或是人员id
            function check(o,type){ 
             	// 使选中节点的背景变为checked样式中的颜色
                if(lastSelectedObj)lastSelectedObj.className='';
                //对新的选中元素的处理
                o.className = "checked";
                lastSelectedObj=o;
                lx = type;
                departmentOrEmployee=type;
                itemId=o.id;
                //alert(departmentOrEmployee);alert(itemId);
            }

            
	    </script>
	    <script type="text/javascript">
			function add(type){	
				$.ajax({
					url: '${ctx}/departmentTree_syncGetAllEmployees1.do',
	                type: 'POST',
	                cache: false,
	                async: false,
	                data:"id="+itemId+"&type="+departmentOrEmployee+"&userList=${userList}&iscylxr=1",
	   			    error: function(){
	   			        alert('AJAX调用错误');
	   			    },
	   			    success: function(msg){
	   			        if(msg=='-1'){
	   						alert("数据库错误，请联系管理员!!!");
	   			        }else{
		   			        if(msg!=''){
		   			        	var oldSelect;
		   			        	if(type*1 == 1){
		   			        		oldSelect=document.getElementById('oldSelect');
		   			        	}else if(type*1 == 2){
		   			        		oldSelect=document.getElementById('zsSelect');
			   			        }else {
			   			        	oldSelect=document.getElementById('csSelect');
				   			    }
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
	   			    }
	   			});
			}
			
			function del(type){
				var oldSelect;
	        	if(type*1 == 1){
	        		oldSelect=document.getElementById('oldSelect');
	        	}else if(type*1 == 2){
	        		oldSelect=document.getElementById('zsSelect');
		        }else {
		        	oldSelect=document.getElementById('csSelect');
   			    }
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

		</script>
	<body   onload="initTree()">
	<div class="panelBar"> 
		<ul class="toolBar">
			<li><a href="${ctx}/userGroup_toAddUserGroupJsp.do?type=${type }" class="add"><span>新建</span></a></li>
			<li><a class="edit" href="javascript:xg_row();" ><span>修改</span></a></li>
			<li><a class="delete" href="javascript:deleteSelected('chcs');" ><span>删除</span></a></li>
			<!--
			<li><a class="auxiliary" href="javascript:ck_row();" ><span>详细</span></a></li>
			<li><a class="set" href="javascript:sd_row();" ><span>设置</span></a></li>  
			-->
		</ul>
	</div>
	<table style="width: 100%;border-collapse: collapse;">
			<tr><td>
			<table class="searchContent">
			<!-- <tr >
				<td style="padding-left: 2px;">
					<span style="font-size: 15px">姓名：</span>
				</td> 
				<td>
					<input type="text" id="mc" value="${mc}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="sousuo()">检索</button></div></div>
			</td>
			</tr> -->
		</table>
				</td></tr>
			<tr>
				<td style="width:330px;">
					<div id="filetreeDiv" style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 400px;border: 1px dashed #C2C2C2 " >
						<ul id="black" class="filetree"></ul>
					</div>
				</td>
				<td  style="width: 80px;text-align: center;padding: 5px;">
					<c:if test="${type != '2'}">
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add(1)"/>
						<br/>
						<br/>
							<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right2.png"  onclick="add(1);"/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del(1)"/>
						<br/>
						<br/>
					</c:if>
					<c:if test="${type == '2'}">
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add(2)"/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del(2)"/>
						<br/>
						<br/>
						<br/>
						<br/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add(3)"/>
						<br/>
						<br/><img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right2.png"  onclick="add(3);"/>
					<br/><br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del(3)"/>
					</c:if>
				</td> 
				<td style="width: 330px;">
					<c:if test="${type != '2'}">
						<select id="oldSelect" size="20" style="width:100%;height: 390px;border: 1px dashed #C2C2C2" multiple="multiple">
						</select>
					</c:if>
					<c:if test="${type == '2'}">
						主送
						<select id="zsSelect" size="20" style="width:100%;height: 190px;border: 1px dashed #C2C2C2" multiple="multiple">
						</select>
						抄送
						<select id="csSelect" size="20" style="width:100%;height: 190px;border: 1px dashed #C2C2C2" multiple="multiple">
						</select>
					</c:if>
				</td>
			</tr>
		</table>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
	<script type="text/javascript">
	
	//复选框批选取
	function chcChecked(parentCheckboxId,checkboxesName){
		var p=g.g(parentCheckboxId);
		var arr=g.gbn(checkboxesName);
		if(p&&arr){
			for(var i=0;i<arr.length;i++){
				arr[i].checked=p.checked;
			};
		}
	};

	function xg_row(){
		 if(itemId!=""&&lx==0){
		 	location.href = "${ctx}/userGroup_toUpdateUserGroupJsp.do?id="+itemId+"&type=${type }";
		 }else{
			alert('请选择要修改的组！');
	     }
	}

	function ck_row(){
		 if(itemId!=""&&lx==0){
		 	location.href = "${ctx}/userGroup_viewUserGroup.do?id="+itemId+"&type=${type }";
		 }else{
			alert('请选择要查看的组！');
	     }
	}

	function sd_row(){
		 if(itemId!=""&&lx==0){
		 	location.href = "${ctx}/userGroup_toMapEmployeeJsp.do?id="+itemId+"&type=${type }";
		 }else{
			alert('请选择要设定的组！');
	     }
	}
	

	//批删除提示
	function deleteSelected(checkboxesName){
		if(!(itemId!=""&&lx==0)){
			alert('请至少选择一条记录删除!');
			return;
		}
		if(!confirm("确认删除所选记录吗?")){
			return;
		}
		window.location.href="${ctx}/userGroup_deleteUserGroup.do?ids="+itemId;
	};
	</script>
	</body>
	<%@ include file="/common/function.jsp"%>
</html>
