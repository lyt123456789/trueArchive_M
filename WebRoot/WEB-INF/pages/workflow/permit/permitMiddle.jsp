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
</head>
<body>
	<form id="permitform" method="POST" name="thisForm" action="${ctx }/permit_addTagPermit.do" >
		<input type="hidden" name="vc_formid" id="vc_formid" value="${vc_formid}">
		<input type="hidden" name="vc_tagname" id="vc_tagname" value="${vc_tagname}">
		<input type="hidden" name="type" id="type" value="${type}">
    	<div id="w_list_print" align="center">
		     <div>
		    <table width="100%" class="infotan">
		    	<Tr>
		    		<Td width="20%" align="right" class="bgs ls">角色类型:</Td>
		    		<Td width="80%">
			    		<select onchange="getRole();" name="vc_roletype" id="vc_roletype" style="width: 200px">
							<option value="1">内置用户组</option>
							<option value="2">全局用户组</option>
							<option value="3">平台用户组</option>
							<option value="4" selected="selected">流程用户组</option>
							<option value="5">动态角色</option>
							<option value="6">部门</option>
							<option value="7">用户</option>
							<option value="8">部门级联</option>
						</select>
					</Td>
		    	</Tr>
		    	<Tr  id="rolediv" >	
		    		<Td width='10%' align='right' class='bgs ls'>角色名称:</td> 
		    		<Td id="rolenameTd">
		    			<select name='vc_rolename' id='vc_rolename' style='width: 200px'>
		    			</select>
		    		</td>	
		    	</Tr>
		    	<Tr >	
		    		<Td colspan="2">
		    			<iframe style="display: none;overflow:auto;" name="roletree" id="roletree" src="${ctx}/permit_showDepartmentTree.do" width="350px" height="300px" scrolling="auto"></iframe>
		    		</td>	
		    	</Tr>
		    </table>
			
		</div>
    	</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5"> 
				<li><a class="buttonActive" href="javascript:window.location.href='${ctx }/permition_permitMiddleList.do?lcid=${lcid}&formid=${formid }&nodeid=${nodeid }&type=${type }';"><span>返回</span></a></li>
			</ul> 
		</div>
		</form>
    </body>
   
	<script type="text/javascript">
		$("table.list", document).cssTable();

		function getRole(){
			var type = document.getElementById('vc_roletype').value;
			if(type * 1 <= 5){
				document.getElementById('rolediv').style.display="";
				document.getElementById('roletree').style.display="none";
				$.post("${ctx}/permition_getRole.do?lcid=${lcid}&type="+type, null, function(value) {
					//document.getElementById('vc_rolename').innerHTML = value;
					var s1 = "<select name='vc_rolename' id='vc_rolename' style='width: 200px'>";
					var s2 = "</select>";
					document.getElementById('rolenameTd').innerHTML = s1+value+s2;
 
					//默认选中绑定的角色组
					var select=document.getElementById('vc_rolename');
					for(var i=0;i<select.options.length;i++){
						if(select.options[i].value.indexOf('${node.wfn_staff}')!=-1){
							select.options[i].selected=true;break;
						};
					};
				});
			}else{
				document.getElementById('rolediv').style.display="none";
				document.getElementById('roletree').style.display="";
			}
		}

		function checkForm(){
			var type = document.getElementById('vc_roletype').value;
			if(type*1 == 6){
				var dore = document.getElementById('dore').value;
				if(dore*1 != 0){
					alert('请选择部门');
					return;
				}
			}
			if(type*1 == 7){
				var dore = document.getElementById('dore').value;
				if(dore*1 != 1){
					alert('请选择人员');
					return;
				}
			}
			document.getElementById('permitform').submit();
		}
		window.itemid=null;
		window.itemName=null;
		function check(){
			var index=document.getElementById('vc_roletype').value;
			var iframe=window.frames['roletree'];
			if(iframe){
				var type=iframe.departmentOrEmployee;//当前选中的是部门还是人员(0为部门 1为人员)
				var id=iframe.itemId;//当前选中的部门id或是人员id
				var name=iframe.itemName;
				if(type==null||id==null)return false;
				if(index==7){//人员
					if(id==null){
						alert('请选择人员');
						return false;
					}else{
						if(type==0){
							alert('请选择人员');
							return false;
						};
					};
				}else{//部门
					if(id==null){
						alert('请选择部门');
						return false;
					}else{
						if(type==1){
							alert('请选择部门');
							return false;
						};
					};
				}
				window.itemid=id;
				window.itemName=name;
			};
			return true;
		};
		window.onload=function(){
			getRole();
		};
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
