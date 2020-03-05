<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
</head>

<body>
	<div class="wf-layout">
		<div class="wf-list-top">
			<form id="smallForm" name="smallForm" action="${ctx}/docNumberManager_docNumberSmallClass.do"  method="post" >
			<div class="wf-search-bar">
				<div class="wf-top-tool">
		            <a class="wf-btn" id="addnum" >
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-danger" id="delete" title="确定要删除吗？" warn="请选择一条数据" >
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
		        </div>
		        <label class="wf-form-label" for="">名称：</label>
		        <input type="text" class="wf-form-text" name="name"  value="${name}" placeholder="输入关键字"/>
				<label class="wf-form-label" for="">所属大类：</label>
                <select class="wf-form-select" id="docnumbig" name="parentid">
                		<option value="" >--请选择--</option>
     					<c:forEach var="bigtype" items="${allbigtype}">
     						<option value="${bigtype.typeid}">${bigtype.name}</option>
     					</c:forEach>
                </select>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
			</div>
			</form>
		</div>
		<form id="disform" method="POST" name="disform" action="${ctx}/docNumberManager_docNumberModelManage.do" >
		<input type="hidden" id="typeid_h" name="typeid_h" value="" />
		<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
						<th width="5%" style="font-weight:bold;text-align:center;"><input id='chc_all' type='checkbox' onclick='checkAllchc()'/></th>
						<th width="30%" style="font-weight:bold;text-align:center;">名称</th>
						<th width="30%" style="font-weight:bold;text-align:center;">所属大类</th>
						<th width="30%" style="font-weight:bold;text-align:center;">备注</th>
						<th ></th>
					</tr>
		    	</thead>
		    	<tbody>
					<c:forEach items="${docNumSmallList}" var="docNum" varStatus="status">
						<tr > 
							<td style="text-align:center;"><input type="checkbox" name="typeid" value="${docNum.typeid}" opStatus="${docNum.type eq loginEmployee.employeeGuid}"/></td>
							<td style="text-align:left;" beanid="${docNum.typeid}"><a href="javascript:;" onclick="getDetil('${docNum.typeid}');">${docNum.name}</a></td>
							<td style="text-align:left;">${docNum.parentName}</td>
							<td style="text-align:center;">
								<c:if test="${docNum.type != loginEmployee.employeeGuid}">
									不可修改
								</c:if>
							</td>
							<td >
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
		</form>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
		<script>
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/docNumberManager_docNumberSmallClass.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('smallForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		</script>
		<script type="text/javascript" src="${ctx}/widgets/common/js/god_Core.js" defer="defer"></script>
        <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
	    <script type="text/javascript">
	        function getDetil(id){
	    		layer.open({
	    			title:'编辑文号小类',
	    			type:2,
	    			area:['700px','200px'],
	    			content:"${ctx}/docNumberManager_toSingledocNumClass.do?typeid="+id+"&isparent=1",
	    			btn:["确定","取消"],
	    			yes:function(index, layero){
	    				var body = layer.getChildFrame('body', index);
	    				var name = $(body).find("#name").val();
	    				var type = $(body).find("#type").val();
	    				var isparent = $(body).find("#isparent").val();
	    				var parentid = $(body).find("#parentid").val();
		            	var begin = $(body).find("#begin").val();
		            	var middle = $(body).find("#middle").val();
		            	var end = $(body).find("#end").val();
		            	var doctype = $(body).find("#doctype").val();
		            	var typeid = $(body).find("#typeid").val();
	    				
		            	name = begin + middle + end;
		            	
		   				 if($.trim(name)==""){
		   						alert("名称不能为空！");
		   						return false;
		   				 }
		   				 if($.trim(name).match(/[,;]/)!=null){
		   					 	alert("名称不能包含特殊字符(,和;)");
		   						return false;
		   				 }
		   				 if($.trim(parentid)==""){
		   						alert("所属文号类别不能为空！");
		   						return false;
		   				 }
		   				 if($.trim(doctype)==""){
		   						alert("所属公文类别不能为空！");
		   						return false;
		   				 }
		   				 $.ajax({
		   						url:"${ctx}/docNumberManager_docNumTypeManage.do",
		   						type:"post",
		   						async:false,
		   						cache: false,
		   						data:{'name':name,'type':type,'isparent':isparent,'parentid':parentid,'typeid':typeid,'doctype':doctype,'op':'edit'},
		   						success:function(msg){
		   							if(msg=="addOk")alert("新增成功");
		   							if(msg=="modifyOk")alert("修改成功");
		   							if(msg=="fail")alert("已有同名文号");
		   						},
		   						error:function(){
		   							alert("系统错误请重试");
		   						}	
		   				});
		   				window.location.href="${ctx}/docNumberManager_docNumberSmallClass.do";
	    			}
	    		});
	        }
	        
	        $("#addnum").bind("click",function(){
	    		layer.open({
	    			title:'新增文号小类',
	    			type:2,
	    			area:['700px','200px'],
	    			content:"${ctx}/docNumberManager_toSingledocNumClass.do?isparent=1",
	    			btn:["确定","取消"],
	    			yes:function(index, layero){
	    				var body = layer.getChildFrame('body', index);
	    				var name = $(body).find("#name").val();
	    				var type = $(body).find("#type").val();
	    				var isparent = $(body).find("#isparent").val();
	    				var parentid = $(body).find("#parentid").val();
		            	var begin = $(body).find("#begin").val();
		            	var middle = $(body).find("#middle").val();
		            	var end = $(body).find("#end").val();
		            	var doctype = $(body).find("#doctype").val();
		            	var typeid = $(body).find("#typeid").val();
	    				
		            	name = begin + middle + end;
		            	
		   				 if($.trim(name)==""){
		   						alert("名称不能为空！");
		   						return false;
		   				 }
		   				 if($.trim(name).match(/[,;]/)!=null){
		   					 	alert("名称不能包含特殊字符(,和;)");
		   						return false;
		   				 }
		   				 if($.trim(parentid)==""){
		   						alert("所属文号类别不能为空！");
		   						return false;
		   				 }
		   				 if($.trim(doctype)==""){
		   						alert("所属公文类别不能为空！");
		   						return false;
		   				 }
		   				 $.ajax({
		   						url:"${ctx}/docNumberManager_docNumTypeManage.do",
		   						type:"post",
		   						async:false,
		   						cache: false,
		   						data:{'name':name,'type':type,'isparent':isparent,'parentid':parentid,'typeid':typeid,'doctype':doctype,'op':'add'},
		   						success:function(msg){
		   							if(msg=="addOk")alert("新增成功");
		   							if(msg=="modifyOk")alert("修改成功");
		   							if(msg=="fail")alert("已有同名文号");
		   						},
		   						error:function(){
		   							alert("系统错误请重试");
		   						}	
		   				});
		   				window.location.href="${ctx}/docNumberManager_docNumberSmallClass.do";
	    			}
	    		});
	        });
	      //回车监听
	        document.onkeydown = keyDown;
	       	function keyDown(e){ 
	       		if((e ? e.which : event.keyCode)==13 ){
	       				$("#search").click();
	     	  	}
	       	}
	       	
	       	function checkAllchc(){ 
	    		var chc_all=document.getElementById('chc_all');
	    		var chcs=document.getElementsByName('typeid');
	    		for(var i=0;i<chcs.length;i++){
	    			if(chc_all.checked==true){
	    				chcs[i].checked=true;
	    			}else{
	    				chcs[i].checked=false;
	    			}
	    		}
	    	}
			$("#chc_all").bind("click",function (){
				var check = document.getElementsByName("typeid");
				for(var i=0,j=check.length; i<j; i++){
					if(check[i].checked==true){
						check[i].checked=false;
					}else{
						check[i].checked=true;
					}
				}
			});
			 $("#delete").bind("click",function (){
				var opFlag = true;
				var aa = false;
				$("input[name='typeid']").each(function(i,e){
					if(e.checked&&$(e).attr("opstatus")=="false"){
						opFlag = false;
					}else if(e.checked){
						aa = true;
					}
				});
				if(!opFlag){
					alert('请勿选择不可修改的内容');
					return false;
				}
				if(!aa){
				  	alert('请至少选择一个数据用于删除');
					return false;
				}
				if(!confirm('确认删除所选记录吗?')){
					return false; 
				}
				$("#disform").attr("action","${ctx}/docNumberManager_smallDocNumdelete.do");
				$('#disform').submit();
			});
		</script>
</html>
