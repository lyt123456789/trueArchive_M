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
  <body onload="">
  <div class="panelBar"> 
		<ul class="toolBar">
			<li><a class="auxiliary" href="javascript:back();" ><span>返回</span></a></li>
		</ul>
	</div>
  <div class="pageContent">
	<div id="div2" >
<!--						<table class="infotan mt10" width="100%">-->
<!--							<c:if test="${routType==2}">   -->
<!--								<tr>-->
<!--									<td width="20%" style="font-weight:bold;text-align:left;">名称</td>-->
<!--									<td width="20%" style="font-weight:bold;text-align:left;">部门-岗位</td>-->
<!--									<td width="20%" style="font-weight:bold;text-align:left;">主送</td>-->
<!--									<td width="20%" style="font-weight:bold;text-align:left;">抄送</td>-->
<!--								</tr>-->
<!--								<c:if test="${send==1 || (send !='' && send !=0)}">-->
<!--									<c:forEach items="${userList}" var="user">-->
<!--										<tr>-->
<!--											<td width="20%" >${user.employee_name}</td>-->
<!--											<td width="30%" >${user.employee_shortdn}</td>-->
<!--											<td width="10%" ><input type="checkbox" id="xto" name="xtoName" value="${user.employee_id}"/></td>-->
<!--											<td width="10%" ><input type="checkbox" id="xcc" name="xccName" value="${user.employee_id}"/></td>-->
<!--										</tr>-->
<!--									</c:forEach>-->
<!--								</c:if>-->
<!--							</c:if>-->
<!--							<c:if test="${routType==0 or routType ==1}">-->
<!--								<tr>-->
<!--									<td width="20%" style="font-weight:bold;text-align:left;">名称</td>-->
<!--									<td width="20%" style="font-weight:bold;text-align:left;">部门-岗位</td>-->
<!--									<td width="20%" style="font-weight:bold;text-align:left;">发送人</td>-->
<!--								</tr>-->
<!--								<c:if test="${send==1 || (send !='' && send !=0)}">-->
<!--									<c:forEach items="${userList}" var="user">-->
<!--										<tr style="height:10px;">-->
<!--											<td width="20%" >${user.employee_name}</td>-->
<!--											<td width="30%" >${user.employee_shortdn}</td>-->
<!--											<td width="10%" ><input type="checkbox" id="toName" name="toName" value="${user.employee_id}"/></td>-->
<!--										</tr>-->
<!--									</c:forEach>-->
<!--								</c:if>-->
<!--							</c:if>-->
<!--						</table>-->
<table style="width: 100%;border-collapse: collapse;">
			<tr> 
				<td style="width:330px;">
					<div style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 400px;border: 1px dashed #C2C2C2 " >
						<ul id="black" class="filetree"></ul>
						<select id="users" size="20" style="width:100%;height: 365px;border: 1px dashed #C2C2C2" multiple="multiple">
							<c:forEach var="m" items="${userGroup.employeeList}">
								<option value="${m.employeeGuid }">${m.employeeName} {${m.department.departmentShortdn}}</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<td  style="width: 80px;text-align: center;padding: 5px;">
					<c:if test="${routType != '2'}">
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="addu(1)"/>
						<br/>
						<br/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="delu(1)"/>
						<br/>
						<br/>
					</c:if>
					<c:if test="${routType == '2'}">
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="addu(2)"/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="delu(2)"/>
						<br/>
						<br/>
						<br/>
						<br/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="addu(3)"/>
						<br/>
						<br/>
						<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="delu(3)"/>
					</c:if>
				</td> 
				<td style="width: 320px;">
					<c:if test="${routType != '2'}">
						<select id="oldSelect" size="20" style="width:100%;height: 370px;border: 1px dashed #C2C2C2" multiple="multiple">
						</select>
					</c:if>
					<c:if test="${routType == '2'}">
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
					</div>	
				</div>
    </body>

<script>

function addu(type){	
       	var oldSelect;
       	if(type*1 == 1){
       		oldSelect=document.getElementById('oldSelect');
       	}else if(type*1 == 2){
       		oldSelect=document.getElementById('zsSelect');
        }else {
	        oldSelect=document.getElementById('csSelect');
 		}
        var	userSelect=document.getElementById('users');
       	for(var i=0;i<userSelect.length;i++){
			if(userSelect[i].selected){
				//循环遍历人员下拉框
				var isin=false;
				for(var j=0;j<oldSelect.options.length;j++){
					if(oldSelect.options[j].value==userSelect[i].value){
						isin=true;break;
					};
				}
				if(!isin){
					oldSelect.options.add(new Option(userSelect[i].text,userSelect[i].value));
				};
				//oldSelect.options.add(new Option(userSelect[i].text,userSelect[i].value)); 
			}
       	}
		
		
}

function delu(type){
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

	function sendNext(){
		var neRouteType = '${routType}'*1;
		var xtoName = "";
		var xccName = "";
		var div1 = document.getElementById("div1");
			if(neRouteType == 0){
				var oldSelect ;;
				if(div1.style.display != 'none'){
					oldSelect = document.getElementById('oldSelect');
				}else{
					oldSelect = frames['frame10'].document.getElementById('oldSelect');
				}
				//var oldSelect = frames['frame10'].document.getElementById('oldSelect');
				if(oldSelect.options.length > 1){
					alert("此节点类型发送人只能有一个，请重新选择！");
					return false;
				}else if(oldSelect.options.length < 1){
					alert("请选择发送人！");
					return false;
				}
				//xtoName = oldSelect.options[0].value;
				if(div1.style.display != 'none'){
					xtoName = oldSelect.options[0].value;
				}else{
					var xtoInfo = oldSelect.options[0].value.split('|');
					xtoName = xtoInfo[0];
				}
				
			}else if(neRouteType == 1){
				var oldSelect ;;
				if(div1.style.display != 'none'){
					oldSelect = document.getElementById('oldSelect');
				}else{
					oldSelect = frames['frame10'].document.getElementById('oldSelect');
				}
				//var oldSelect = frames['frame10'].document.getElementById('oldSelect');
				if(oldSelect.options.length < 1){
					alert("请选择发送人！");
					return false;
				}
				for(var i = 0 ; i < oldSelect.options.length; i++){
					//xtoName += oldSelect.options[i].value + ",";
					if(div1.style.display != 'none'){
						xtoName += oldSelect.options[i].value + ",";
					}else{
						var xtoInfo = oldSelect.options[0].value.split('|');
						xtoName += xtoInfo[0] + ",";
					}
				}
				xtoName = xtoName.substring(0, xtoName.length - 1);
			}else{
				var zsSelect;
				var csSelect;
				if(div1.style.display != 'none'){
					zsSelect = document.getElementById('zsSelect');
					csSelect = document.getElementById('csSelect');
				}else{
					zsSelect = frames['frame10'].document.getElementById('zsSelect');
					csSelect = frames['frame10'].document.getElementById('csSelect');
				}
				if(zsSelect.options.length < 1){
					alert('请选择主送人！');
					return false;
				}
				if(zsSelect.options.length > 1){
					alert('只能选择一个主送人！');
					return false;
				} 

				//xtoName = zsSelect.options[0].value;
				if(div1.style.display != 'none'){
					xtoName = zsSelect.options[0].value;
				}else{
					var xtoInfo = zsSelect.options[0].value.split('|');
					xtoName += xtoInfo[0] + ",";
				}
				for(var j = 0; j < csSelect.options.length; j++){
					//xccName = csSelect.options[j].value + ",";
					if(div1.style.display != 'none'){
						xccName = csSelect.options[j].value + ",";
					}else{
						var xccInfo = csSelect.options[0].value.split('|');
						xccName += xccInfo[0] + ",";
					}
				}
				if(xccName.length > 0){
					xccName = xccName.substring( 0, xccName.length - 1);
				}
				if(xccName == xtoName){
					alert('主送人和抄送人不能为同一人');
				}
			}
		

		if(neRouteType*1 == 2){
			window.returnValue = xtoName+";"+xccName;
		}else{
			window.returnValue = xtoName;
		}
		window.close();
	}
   $('.lio').click(function(){
	   $('.lio').removeClass("selected");
	   $(this).addClass("selected");
	   });
	function showF(type){
		if(type == '1'){
			document.getElementById('div2').style.display = "none";
			document.getElementById('div3').style.display = "none";
			document.getElementById('div1').style.display = '';
		}else if(type == '2'){
			document.getElementById('div1').style.display = "none";
			document.getElementById('div3').style.display = "none";
			document.getElementById('div2').style.display = '';
		}else if(type == '3'){
			document.getElementById('div1').style.display = "none";
			document.getElementById('div2').style.display = "none";
			document.getElementById('div3').style.display = '';
		}
	}
	function back(){
		location.href = "${ctx}/userGroup_getUserGroupList.do";
	};
</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
</html>
