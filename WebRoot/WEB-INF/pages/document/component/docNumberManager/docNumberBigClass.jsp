 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle")%></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
</head>
<body>
	<div class="wf-layout">
		<div class="wf-list-top">
			<div class="wf-search-bar">
				<form id="thisForm" action="${ctx}/docNumberManager_docNumberBigClass.do"  method="post" >
				<div class="wf-top-tool">
		            <a class="wf-btn" id="addnum" >
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-danger" title="确定要删除吗？" warn="请选择一条数据" onclick="checkboxnum();" >
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
		        </div>
		        <label class="wf-form-label" for="">名称：</label>
		        <input type="text" class="wf-form-text"  name="name"  value="${name}" placeholder="输入关键字"/>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
	            </form>
			</div>
		</div>
		<form id="disform" method="POST" name="disform" action="${ctx}/docNumberManager_bigDocNumdelete.do" >
		<input type="hidden" id="typeid_h" name="typeid_h" value="" />
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
				    	<tr>
							<th width="30" style="font-weight:bold;text-align:center;"><input id='chc_all' type='checkbox' onclick='checkAllchc()'/></th>
							<th width="200" style="font-weight:bold;text-align:center;">名称</th>
							<th width="300" style="font-weight:bold;text-align:center;">备注</th>
							<th ></th>
						</tr>
			    	</thead>
			    	<tbody>
						<c:forEach items="${docNumList}" var="docNum" varStatus="status">
							<tr > 
								<td style="text-align:center;"><input type="checkbox" id="typeid" name="typeid" value="${docNum.typeid}"/></td>
								<td style="text-align:center;" beanid="${docNum.typeid}"><a href="javascript:;" onclick="getDetil('${docNum.typeid}');">${docNum.name}</a></td>
								<td style="text-align:center;">${docNum.type}</td>
								<td ></td>
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
		skipUrl="<%=request.getContextPath()%>"+"/docNumberManager_docNumberBigClass.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
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
      <script language="javascript"> 
          function getDetil(id){
      		layer.open({
    			title:'修改文号大类',
    			type:2,
    			area:['300px','170px'],
    			content:"${ctx}/docNumberManager_toSingledocNumClass.do?typeid="+id+"&isparent=0",
    			btn:["确定","取消"],
    			yes:function(index, layero){
    				var body = layer.getChildFrame('body', index)
    				var name = $(body).find("#name").val();
    				var type = $(body).find("#type").val();
    				var isparent = $(body).find("#isparent").val();
    				var parentid = $(body).find("#parentid").val();
    				var typeid = $(body).find("#typeid").val();
    				if(name == ""){
    					alert("名称不能为空！");
    					return false;
    				}
    				$.ajax({
    					url:"${ctx}/docNumberManager_docNumTypeManage.do",
    					type:"post",
    					async:false,
    					cache: false,
    					data:{'name':name,'type':type,'isparent':isparent,'parentid':parentid,'typeid':typeid,'op':'edit'},
    					success:function(msg){
    						if(msg=="addOk")alert("新增成功");
    						if(msg=="modifyOk")alert("修改成功");
    						if(msg=="fail")alert("已有同名文号");
    					},
    					error:function(){
    						alert("系统错误请重试");
    					}	
    				});
    				window.location.href = "${ctx}/docNumberManager_docNumberBigClass.do";
    			}
    		});
        	  
        	  
        	  
/*           	art.dialog({
 					lock: true,
 				    content: '<'+'iframe  id="editFrame"  src="${ctx}/docNumberManager_toSingledocNumClass.do?typeid='+id+'&isparent=0" height="80" width="200" frameborder="no" border="0" style="border:0px;"></'+'iframe>',
 				    id: 'EF893M',
 				    ok:function(){
 				    	var name = window.frames["editFrame"].document.getElementById("name").value;
 						var type = window.frames["editFrame"].document.getElementById("type").value;
 						var isparent = window.frames["editFrame"].document.getElementById("isparent").value;
 						var parentid = window.frames["editFrame"].document.getElementById("parentid").value;
 						var typeid = window.frames["editFrame"].document.getElementById("typeid").value;
 						var doctype = "";
 						if(name == ""){
 							alert("名称不能为空！");
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
 			   	    	window.location.href = "${ctx}/docNumberManager_docNumberBigClass.do";
 				    }
 				}); */
          }
	function  checkboxnum(){
		var aa=false;
		var objs = document.getElementsByTagName('input');
		for(var i=0; i<objs.length; i++) {
 		  if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='typeid' && objs[i].checked==true ){
 	  		aa=true;
		   }
		}
		var id=$(".wf-actived td:eq(1)").attr("beanid");
		if(id!=null && id.length>0){
			aa=true;
			document.getElementById("typeid_h").value=id;
		}
		if(!aa){
		    //seajs.use('common/dialog/artDialog',function(){
			//	art.dialog('请至少选择一个数据用于删除');
		    //});
		    alert('请至少选择一个数据用于删除');
		    return false;
		}
	
		if(!confirm( '删除大类将同时删除相关子类数据,确定删除所选信息吗? ')){
			return false;
		}
		
		
		document.getElementById('disform').submit();
		return aa; 
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
	
	
	
	$("#addnum").bind("click",function(){
		layer.open({
			title:'新增文号大类',
			type:2,
			area:['300px','170px'],
			content:"${ctx}/docNumberManager_toSingledocNumClass.do",
			btn:["确定","取消"],
			yes:function(index, layero){
				var body = layer.getChildFrame('body', index)
				var name = $(body).find("#name").val();
				var type = $(body).find("#type").val();
				var isparent = $(body).find("#isparent").val();
				var parentid = $(body).find("#parentid").val();
				if(name == ""){
					alert("名称不能为空！");
					return false;
				}
				$.ajax({
					url:"${ctx}/docNumberManager_docNumTypeManage.do",
					type:"post",
					async:false,
					cache: false,
					data:{'name':name,'type':type,'isparent':isparent,'parentid':parentid,'op':'add'},
					success:function(msg){
						if(msg=="addOk")alert("新增成功");
						if(msg=="modifyOk")alert("修改成功");
						if(msg=="fail")alert("已有同名文号");
					},
					error:function(){
						alert("系统错误请重试");
					}	
				});
				window.location.href = "${ctx}/docNumberManager_docNumberBigClass.do";
			}
		});
		
/* 		art.dialog({
			lock: true,
		    content: '<'+'iframe id="newFrame" name="newFrame" src="${ctx}/docNumberManager_toSingledocNumClass.do" height="80" width="200" frameborder="no" border="0" style="border:0px;"></'+'iframe>',
		    id: 'EF893L',
		 	ok:function(){
				var name = window.frames["newFrame"].document.getElementById("name").value;
				var type = window.frames["newFrame"].document.getElementById("type").value;
				var isparent = window.frames["newFrame"].document.getElementById("isparent").value;
				var parentid = window.frames["newFrame"].document.getElementById("parentid").value;
				var typeid = window.frames["newFrame"].document.getElementById("typeid").value;
				var doctype = "";
				if(name == ""){
					alert("名称不能为空！");
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
	   	    	window.location.href = "${ctx}/docNumberManager_docNumberBigClass.do";
	   		},
	   		okVal:'提交'
		}); */
   	});
</script> 
</html>
