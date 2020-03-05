<%@ page language="java" pageEncoding="UTF-8"%><!--告诉服务器，页面用的是UTF-8编码-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>部门人员组织树</title>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<!--  告诉浏览器，页面用的是UTF-8编码 -->
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<%@ include file="/common/header.jsp"%>
		<link href="${ctx}/widgets/tree/css/tree_common.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/widgets/tree/css/tree_common_people.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
		
			
		<script type="text/javascript">
	        $(document).ready(function(){
	    	 $("#black").treeview({
			   url: "departmentTree_getContent.do?timestamp="+ new Date().getTime()
		     })
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
	<body  >
		<form id="thisForm" method="post" name="thisForm" action="${ctx }/group_getInnerUserList.do" >
			<input type="hidden" name="type" value="${type }"/>
			<input type="hidden" name="employeeinfo" id="employeeinfo" value=""/>
			<input type="hidden" name="id" value="${innerUser.id }"/>
		</form>
		<table style="width: 100%;border-collapse: collapse;">
			<tr>
				<td style="">
					<div style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 500px;border: 1px dashed #C2C2C2 " >
						<a href="${pageContext.request.contextPath}/departmentTree_showDepartmentTree.do" style="text-decoration: none;">刷新</a>
						<ul id="black" class="filetree"></ul>
					</div>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
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
								for(var i=0;i<jsobj.length;i++){
									oldSelect.options.add(new Option(jsobj[i][1]+'{'+jsobj[i][4],jsobj[i][0]+'}|'+jsobj[i][1]+'|'+jsobj[i][4]+'}')); 
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
								for(var i=0;i<jsobj.length;i++){
									oldSelect.options.add(new Option(jsobj[i][1]+'{'+jsobj[i][4],jsobj[i][0]+'}|'+jsobj[i][1]+'|'+jsobj[i][4])); 
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
				alert('数据保存中,请耐心等待。。。');
				document.getElementById('thisForm').action='${ctx }/group_addMapEmployeeInfo.do';
				document.getElementById('thisForm').submit();
			};
		</script>
	</body>
	<%@ include file="/common/function.jsp"%>
</html>