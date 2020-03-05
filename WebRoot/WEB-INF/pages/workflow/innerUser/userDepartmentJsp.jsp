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
				 	url: "${ctx}/departmentTree_getContent.do?timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI('${mc}'))
			     });
	        }
	        function sousuo(){
	        	var name=$('#mc').val();
	        	document.getElementById("filetreeDiv").innerHTML="<ul id=\"black\" class=\"filetree\"></ul>"; 
	        	$("#black").treeview({
				 	url: "${ctx}/departmentTree_getContent.do?timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI(name))
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
	    				 	url: "departmentTree_getContent.do?timestamp="+ new Date().getTime()+"&mc="+encodeURI(encodeURI(name))
	    			    });
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
	    </script>
	</head>
	<body  onload="initTree()">
<!-- 	<div class="panelBar"> 
		<ul class="toolBar"> 
			<li><a href="javascript:choose();" class="add"><span>确定选择</span></a></li>
		</ul>
	</div> -->
		<table style="width: 100%;border-collapse: collapse;">
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
					<div  class="buttonActive"><div class="buttonContent"><button type="button"  onclick="sousuo()">检索</button></div></div>
			</td>
			</tr>
		</table>
				</td></tr>
			<tr>
				<td style="width:330px;">
					<div id="filetreeDiv" style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 400px;border: 1px dashed #C2C2C2 " >
						<ul id="black" class="filetree"></ul>
					</div>
				</td>
				<td  style="width: 80px;text-align: center;padding: 5px;">
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add(1)"/>
						<br/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/right2.png" title="添加指定部门下全部人员"  onclick="addAll(3)"/>
						<br/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del(1)"/>
						<br/>
						<br/>
				</td> 
				<td style="">
						已选择人员或者机构:<span id="span_employees"></span>
						<select id="oldSelect" size="20" style="width:100%;height: 390px;border: 1px dashed #C2C2C2" multiple="multiple" name="oldSelect">
							<c:forEach var="m" items="${mapList}">
								<option value="${m.employeeGuid }|${m.employeeName }|${m.departmentDn }">${m.employeeName} { ${m.departmentDn} }</option>
							</c:forEach>
							<c:forEach var="k" items="${depList}">
								<option value="${k.departmentGuid }|${k.departmentName }|${k.departmentShortdn }">${k.departmentName} { ${k.departmentShortdn} }</option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			function add(type){	
				//选择机构
				if(departmentOrEmployee=='0'){	//选择机构时即为机构
					//ajax获取相应的机构信息
					$.ajax({
						url: '${ctx}/selectTree_getDepmentInfo.do',
               			type: 'POST',
               			cache: false,
               			 async: false,
               		 	data:"id="+itemId,
   			   			 error: function(){
   			        		alert('AJAX调用错误');
   			    		},
   			    	 success: function(msg){
   			    		if(msg!=''){
   			        	var oldSelect;
   			        	if(type*1 == 1){
   			        		oldSelect=document.getElementById('oldSelect');
   			        	}else if(type*1 == 2){
   			        		oldSelect=document.getElementById('zsSelect');
	   			        }else {
	   			        	oldSelect=document.getElementById('csSelect');
		   			    }
   			        	if(type*1 == 2){
   			        		if(oldSelect.options.length>0){
   			        			return;
   			        		}
   			        	}
   			        	var jsobj=eval('('+msg+')');
						if(jsobj){
							if(jsobj.length==0){
								alert("选择的机构不对");
								return;
							}
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
									oldSelect.options.add(new Option(jsobj[i][1]+' {'+jsobj[i][2]+'}',jsobj[i][0]+'|'+jsobj[i][1]+'|'+jsobj[i][2])); 
								}
							};
						};
		   			}
   			    }
				});
			}else{//选择人员
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
			}
			
			
			function addAll(){
				if(!confirm('确定添加所有人员'))return;
				alert('请耐心等待片刻。。。');
				$.ajax({
					url: '${ctx}/departmentTree_syncGetAllEmployees.do',
	                type: 'POST',
	                cache: false,
	                async: false,
	                data:"id="+itemId+"&type=0",
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
							//showCount();
	   			        }
	   			    }
	   			});
			};

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
			
			//遍历list
			function choose(){
				var length = $("select[name=oldSelect] option").length;
				var returnValue = "";
				$("select[name=oldSelect] option").each(function(){
					var value = $(this).val();
					if(value!=''){
						returnValue += value.split("|")[0]+",";
					}
				});
				return returnValue;
				/* window.returnValue=returnValue;
				window.close(); */
			}
		</script>
	</body>
</html>