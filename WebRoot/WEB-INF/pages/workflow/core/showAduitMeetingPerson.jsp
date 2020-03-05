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
			   url: "departmentTree_getContent.do?isBigDep=${isBigDep}&notEmployee=${notEmployee}&timestamp="+ new Date().getTime()
		     })
	        });
	        
            
            var lastSelectedObj=null;
            
            var departmentOrEmployee=null;//当前选中的是部门还是人员(0为部门 1为人员)
            var empId=null;//当前选中的部门id或是人员id
            var empName = null;
            function check(o,type){ 
             	// 使选中节点的背景变为checked样式中的颜色
                if(lastSelectedObj)lastSelectedObj.className='';
                //对新的选中元素的处理
                o.className = "checked";
                lastSelectedObj=o;

                departmentOrEmployee=1;
                empId=o.id;
                //alert(departmentOrEmployee);alert(itemId);
               	$.ajax({
   					url: '${ctx}/departmentTree_getNameById.do',
   	                type: 'POST',
   	                cache: false,
   	                async: false,
   	                data:"id="+empId+"&type="+departmentOrEmployee,
   	   			    error: function(){
   	   			        alert('AJAX调用错误');
   	   			    },
   	   			    success: function(msg){
   	   	   			    empName = msg;
   	   			    }
               	});
            }

            function selDep(){
                /*
                if(!itemId || departmentOrEmployee == '1'){
					alert('请选择部门');
					return;
                }*/
				window.returnValue = empId+","+empName;
				window.close();
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
					<div style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 400px;border: 1px dashed #C2C2C2 " >
						<!-- 
						<a href="${pageContext.request.contextPath}/departmentTree_showDepartmentTree.do" style="text-decoration: none;">刷新</a>
						 -->
						<a href="javascript:;" style="text-decoration: none;">刷新</a>
						<ul id="black" class="filetree"></ul>
					</div>
					<div class="formBar pa" style="bottom:0px;width:100%;">  
					<ul class="mr5"> 
					<li><a class="buttonActive" href="javascript:selDep();"><span>确定</span></a></li>
					</ul>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>