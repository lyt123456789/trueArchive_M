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
  <div class="panelBar"> 
		<ul class="toolBar"> 
			<li><a href="javascript:choose();" class="add"><span>确定选择</span></a></li>
		</ul>
	</div>
  <div class="pageContent">
	<div class="tabs" currentIndex="1" eventType="click">
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio" onclick=""><a ><span >节点人员</span></a></li>  
				</ul>
			</div>
		</div>
    	<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
			<div id="div">
				<table style="width: 100%;border-collapse: collapse;">
					<tr> 
						<td style="width:330px;">
							<div style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 400px;border: 1px dashed #C2C2C2 " >
								<ul id="black" class="filetree"></ul>
							<select id="users" size="20" style="width:100%;height: 385px;border: 1px dashed #C2C2C2" multiple="multiple">
							<c:forEach items="${userList}" var="user">
								<option value="${user.employee_id}">${user.employee_name} {${user.employee_shortdn}}</option>
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
						<select id="oldSelect" size="20" style="width:100%;height: 390px;border: 1px dashed #C2C2C2" multiple="multiple">
						</select>
					</c:if>
					<c:if test="${routType == '2'}">
						主送
						<select id="zsSelect" size="20" style="width:100%;height: 190px;border: 1px dashed #C2C2C2" multiple="multiple">
							<c:forEach items="${zsuserList}" var="user">
								<option value="${user.employee_id}">${user.employee_name} {${user.employee_shortdn}}</option>
							</c:forEach>
						</select>
						抄送
						<select id="csSelect" size="20" style="width:100%;height: 190px;border: 1px dashed #C2C2C2" multiple="multiple">
							<c:forEach items="${csuserList}" var="user">
								<option value="${user.employee_id}">${user.employee_name} {${user.employee_shortdn}}</option>
							</c:forEach>
						</select>
					</c:if>
				</td>
			</tr>
		</table>
					</div>	
				</div>
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
		return false;	
	}else{
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
   $('.lio').click(function(){
	   $('.lio').removeClass("selected");
	   $(this).addClass("selected");
   });
   
   function choose(){
	 	//  var object = document.getElementById("frame10").contentWindow;
		var oldSelect = document.getElementById('zsSelect');
		var length = oldSelect.options.length ;
		var flag = true;
		/* if(length==0){
			alert("请选择主送人员");
			flag = false;
		} */
		
		if(length>1){
			alert("只能选择一个主动人员");
			flag = false;
		}
		
		if(flag){	//获取页面中填写的值
			//循环遍历,  主送,操作的人员信息
			var zsry = "";			//主送人员
			for(var i=0;i<oldSelect.options.length;i++){
				zsry += oldSelect.options[i].value +',';
			}
			
			if(zsry!=''){
				zsry=zsry.substr(0,zsry.length-1);
			}
			
			var newSelect = document.getElementById('csSelect');
			var csry = "";			//抄送人员
			for(var i=0;i<newSelect.options.length;i++){
				csry += newSelect.options[i].value +',';
			}
			
			if(csry!=''){
				csry=csry.substr(0,csry.length-1);
				
				zsry = zsry+";"+csry ;
			}
			window.returnValue=zsry;
			window.close();
			
			
			
		}
	}
</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
</html>
