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

<body > 

	<form id="form1" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
			<input type="hidden" name="id" value="${procedure.id }"/>
	
			<table class="infotan" width="100%">
				<tr>
				 <td width="20%" class="bgs ls">存储过程英文名称：</td>
				 <td width="80%"><input value="${procedure.pname }" type="text" name="procedurename" id="" size="40"/></td>
				</tr> 
				<tr>
				 <td width="20%" class="bgs ls">存储过程中文名称：</td>
				 <td width="80%"><input value="${procedure.pcname}" type="text" name="pcname" id="" size="40"/></td>
				</tr>
				<tr>
				 <td class="bgs ls">存储过程输入输出参数:</td>
				 <td>
				 	<table class="infotan mt10" width="100%" style="margin-bottom: 10px;">
						<Tr>
							<Td style="">参数名称</Td>
							<Td style="width: 30%">参数类型</Td>
							<Td style="width: 30%">输入输出类型</Td>
							<Td style="100px"><input type="button"  value="新增行" onclick="addTr(this)"/></Td>
						</Tr>
						<!-- 模板行 -->
						<Tr  style="display: none">
							<Td>
								<select name="paramname" style="width: 100%;border: 0px;">
									<option value="uuid">uuid</option>
									<option value="workflow_id">workflow_id</option>
									<option value="workflow_instance_id">workflow_instance_id</option>
									<option value="form_id">form_id</option>
									<option value="node_id">node_id</option>
								</select>
							</Td>
							<Td>
								<select name="paramtype" style="width: 100%;border: 0px;">
									<option value="VARCHAR">VARCHAR</option>
									<option value="INTEGER">INTEGER</option>
									<option value="DATE">DATE</option>
								</select>
							</Td>
							<Td>
								<select name="inoutparam"  style="width: 100%;border: 0px;">
									<option value="in">输入</option>
									<option value="out">输出</option>
								</select>
							</Td>
							<Td><input type="button"  value="删除行" onclick="delTr(this,2)"/></Td>
						</Tr>
						<c:forEach var="p" items="${params}">
							<Tr >  
								<Td> 
									<select name="paramname" style="width: 100%;border: 0px;">
										<option value="uuid"  <c:if test="${p.ppname=='uuid'}">selected="selected"</c:if>>uuid</option>
										<option value="workflow_id" <c:if test="${p.ppname=='workflow_id'}">selected="selected"</c:if>>workflow_id</option>
										<option value="workflow_instance_id" <c:if test="${p.ppname=='workflow_instance_id'}">selected="selected"</c:if>>workflow_instance_id</option>
										<option value="form_id" <c:if test="${p.ppname=='form_id'}">selected="selected"</c:if>>form_id</option>
										<option value="node_id" <c:if test="${p.ppname=='node_id'}">selected="selected"</c:if>>node_id</option>
									</select>
								</Td>
								<Td>
									<select name="paramtype" style="width: 100%;border: 0px;">
										<option value="VARCHAR" <c:if test="${p.pptype=='VARCHAR'}">selected="selected"</c:if>>VARCHAR</option>
										<option value="INTEGER" <c:if test="${p.pptype=='INTEGER'}">selected="selected"</c:if>>INTEGER</option>
										<option value="DATE" <c:if test="${p.pptype=='DATE'}">selected="selected"</c:if>>DATE</option>
									</select>
								</Td>
								<Td>
									<select name="inoutparam"  style="width: 100%;border: 0px;">
										<option value="in" <c:if test="${p.ppinout=='in'}">selected="selected"</c:if>>输入</option>
										<option value="out" <c:if test="${p.ppinout=='out'}">selected="selected"</c:if>>输出</option>
									</select>
								</Td>
								<Td><input type="button"  value="删除行" onclick="delTr(this,2)"/></Td>
							</Tr>
						</c:forEach>
						
					</table>
				 </td>
				</tr>
				<tr> 
				 <td width="20%" class="bgs ls">存储过程内容：</td>
				 <td width="80%">
				 	<textarea style="width: 80%;height: 300px;" name="paramcontent">${procedure.pcontent }</textarea>
				 	<span style="color: red">*录入完整代码</span>
				 </td>
				</tr> 
			</table>

		<div class="formBar pa" style="bottom:0px;width:100%;">  
		<ul class="mr5"> 
			<li><a  name="CmdView" class="buttonActive" href="javascript:testPro();"><span>测试</span></a></li>
			<li><a  name="CmdView" class="buttonActive" href="javascript:sub();"><span>保存</span></a></li>
			<li><a onclick="" name="CmdView" class="buttonActive" href="javascript:window.history.go(-1);"><span>返回</span></a></li>
		</ul>
		</div>
	</form>
	<iframe id="test_ifr" name="test_ifr" style="display: none;"></iframe>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	
	<script type="text/javascript">
		function addTr(src){
			//获取table
			var tbl=getParentNode(src,'TABLE');
			var templateTr=tbl.rows[1];
		   	var newTr=tbl.insertRow(tbl.rows.length);
			for(var i=0;i<templateTr.cells.length;i++){
				var newTd=newTr.insertCell(i);
				newTd.innerHTML=templateTr.cells[i].innerHTML;
			    newTd.style.cssText=templateTr.cells[i].style.cssText;
			}
			return newTr;	
		};
		function delTr(src,notdeleteindex){
			//获取tr
			var tr=getParentNode(src,'TR');
			//获取table
			var tbl=getParentNode(src,'TABLE');
			if(tr&&tbl&&notdeleteindex){
				if(tbl.rows.length>notdeleteindex){
					tr.parentNode.removeChild(tr);
				};
			};
		};
		function getParentNode(childnode,ptagname){
			var p=childnode;
			while(p.tagName.toUpperCase()!=ptagname){
				p=p.parentNode;
			};
			return p;
		};
		function sub(){
			g.g('form1').action='${ctx }/procedure_update.do';
			g.g('form1').submit();
		};
		//测试存储过程
		function testPro(){
			g.g('form1').target='test_ifr';
			g.g('form1').action='${ctx }/procedure_test.do';
			g.g('form1').submit();
			g.g('form1').target='_self';
		};
	</script>
	</body>
	<%@ include file="/common/function.jsp"%>
</html>
