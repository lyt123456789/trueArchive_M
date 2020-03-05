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
	
	<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
</head>
	<body>
	<form onsubmit="return navTabSearch(this);" action="demo_page1.html" method="post">
		<div class="panelBar"> 
		<ul class="toolBar">
		</ul>
	</div>
	</form>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
	<input type="hidden"  value="${allowType }" name="allowType" id="allowType"/>
	<input type="hidden"  value="${glid }" name="glid" id="glid"/>
	
	<input type="hidden"  value="" name="ids" id="ids"/>
	<input type="hidden"  value="" name="roleid" id="roleid"/>
	<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<tbody>
			<tr>
				<td width="15%" align="right" class="bgs ls">角色类型:</td>
				<td width="85%" class="tdrs" align="left">  
					<select name="role_type" id="role_type" onchange="getGroups()" style="width:150px">
						<option value="1,内置用户">内置用户</option>
						<option value="2,全局用户组">全局用户组</option>
						<option value="3,平台用户组">平台用户组</option>
						<option value="4,流程用户组">流程用户组</option>
						<option value="5,动态角色">动态角色</option>
						<option value="6,人员">人员</option>
						<option value="7,本部门">本部门</option>
						<option value="8,本部门递归">本部门递归</option>
					</select>
				</td>   
			</tr>
			<tr id="tr1">
				<td width="15%" align="right" class="bgs ls"><span id="span_typename">用户组：</span></td>
				<td class="tdrs" align="left">  
					<select name="role" id="role" style="width:150px">
					</select>
				</td>   
			</tr>
			<tr id="tr2" style="display: none;">
				<td width="" align="center" colspan="3">
					<iframe id="iframe_deptree" name="iframe_deptree" style="width: 80%;border: 0px;height: 400px;margin: auto;" src="${ctx }/departmentTree_showDepartmentTree_allow.do"></iframe>
				</td>
			</tr>
		</tbody> 
		</table>
	</div>
	<div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5"> 
				<li>
					<div class="button"><div class="buttonContent"><button name="CmdCancel" class="close" type="button" onclick="javascript:add();">保存</button></div></div>
					<div class="button"><div class="buttonContent"><button name="CmdCancel" class="close" type="button" onclick="window.history.go(-1);">返回</button></div></div>
				</li>
			</ul>
		  </div>
	</form>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	<script type="text/javascript">
		window.onload=function(){
			var v=g.g('role_type').value;
			var index=v.split(/,/)[0];
			syncGetGroups(index);
		};
		function toSet(id){
			g.g('glid').value=id;
			g.g('thisForm').action='${ctx }/allow_getAllowList.do';
			g.g('thisForm').submit();
		};
		//批删除提示
		function deleteSelected(checkboxesName){
			var chcs=g.gbn(checkboxesName);
			var ids='';
			for(var i=0;i<chcs.length;i++){
				if(chcs[i].checked==true){
					ids+=chcs[i].value+',';
				}
			}
			if(ids==''){
				alert('请至少选择一条记录删除!');
				return;
			}
			ids=ids.substr(0,ids.length-1);
			if(!confirm("确认删除所选记录吗?")){
				return;
			}
			g.g('ids').value=ids;
			g.g('thisForm').action='${ctx}/form_deleteForm.do';
			g.g('thisForm').submit();
		};
		function toaddjsp(){
			//g.g('glid').value=id;
			g.g('thisForm').action='${ctx}/allow_toAddAllowJsp.do';
			g.g('thisForm').submit();
		};

		function getGroups(){
			var v=g.g('role_type').value;
			var index=v.split(/,/)[0];
			if(index<=5){
				syncGetGroups(index);
				g.g('tr2').style.display='none';
				g.g('tr1').style.display='';
			}else{
				g.g('tr2').style.display='';
				g.g('tr1').style.display='none';
			};
		};
		
		function syncGetGroups(index){
			$.ajax({
   			    url: 'group_getGroups.do',
   			    type: 'POST',
   			    cache:false,
   			    async:false,
   			    data:{'type':index},
   			    error: function(){
   			        alert('AJAX调用错误');
   			    },
   			    success: function(msg){
   			        if(msg=='-1'){
   						alert('数据错误，请联系管理员');
   			        }else{ 
   			        	var oldSelect=document.getElementById('role');
   			        	oldSelect.options.length=0;
   			        	var jsobj=msg.split(/;/);
						if(jsobj){
							for(var i=0;i<jsobj.length;i++){
								if(jsobj[i].match(/,/)){
									var objs=jsobj[i].split(/,/);
									oldSelect.options.add(new Option(objs[0],objs[1])); 
								};
							};
						};
   			        }
   			    }
   			});
		};

		function add(){
			var v=g.g('role_type').value;
			var index=v.split(/,/)[0];
			var roleid=null;
			if(index<=5){
				roleid=g.g('role').value;
			}else{
				var iframe=window.frames['iframe_deptree'];
				if(iframe){
					var type=iframe.departmentOrEmployee;//当前选中的是部门还是人员(0为部门 1为人员)
					var id=iframe.itemId;//当前选中的部门id或是人员id
					if(index==6){//人员
						if(id==null){
							alert('请选择人员');
							return;
						}else{
							if(type==0){
								alert('请选择人员');
								return;
							};
						};
					}else{//部门
						if(id==null){
							alert('请选择部门');
							return;
						}else{
							if(type==1){
								alert('请选择部门');
								return;
							};
						};
					}
					roleid=id;
				};
			};
			g.g('roleid').value=roleid;
			g.g('thisForm').action='${ctx }/allow_addAllow.do';
			g.g('thisForm').submit();
		};
			
	</script>
	</body>
		<%@ include file="/common/function.jsp"%>
</html>
