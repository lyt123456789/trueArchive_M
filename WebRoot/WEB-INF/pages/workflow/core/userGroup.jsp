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
  <body onload="showF('2')">
  <div class="pageContent">
	<div class="tabs" currentIndex="1" eventType="click">
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio selected" onclick=""><a ><span onclick="showF('2')">流程人员</span></a></li>  
					<li class="lio" onclick=""><a ><span onclick="showF('1')">常用联系人</span></a></li>  
					<!-- <li class="lio" onclick=""><a ><span onclick="showF('3')">全部人员</span></a></li>-->
					<li style="float:right !important;"><a href="#" onclick="sendNext()"><span>发送</span></a></li>  
				</ul>
			</div>
		</div>
    	<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
    				<div id="div2" >
						<iframe id="frame100" name="frame100" class="frame10" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_toDepartmentJsp1.do?routType=${routType}&nodeId=${nodeId}&exchange=${exchange}&isTreeAll=${isTreeAll}"></iframe>
						<input type="hidden" id="toName10" name="toName10">
						<input type="hidden" id="xtoName10" name="xtoName10">
						<input type="hidden" id="xccName10" name="xccName10">
					</div>
    				<div id="div1" style="display: none;">
    					<iframe id="frame101" name="frame101" class="frame101"frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/userGroup_getUserGroupList.do?type=${routType}"></iframe>
					</div>
					<div id="div3" style="display: none;">
						<iframe id="frame10" name="frame10" class="frame10" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_toDepartmentJsp.do?routType=${routType}"></iframe>
						<input type="hidden" id="toName1" name="toName1">
						<input type="hidden" id="xtoName1" name="xtoName1">
						<input type="hidden" id="xccName1" name="xccName1">
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
					oldSelect = frames['frame101'].document.getElementById('oldSelect');
				}else if(div2.style.display != 'none'){
					oldSelect = frames['frame100'].document.getElementById('oldSelect');
				}else if(div3.style.display != 'none'){
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
				var xtoInfo = oldSelect.options[0].value.split('|');
				xtoName = xtoInfo[0];
			}else if(neRouteType == 1 || neRouteType == 3 || neRouteType == 4 || neRouteType == 5){
				var oldSelect ;
				if(div1.style.display != 'none'){
					oldSelect = frames['frame101'].document.getElementById('oldSelect');
				}else if(div2.style.display != 'none'){
					oldSelect = frames['frame100'].document.getElementById('oldSelect');
				}else if(div3.style.display != 'none'){
					oldSelect = frames['frame10'].document.getElementById('oldSelect');
				}
				//var oldSelect = frames['frame10'].document.getElementById('oldSelect');
				if(oldSelect.options.length < 1){
					alert("请选择发送人！");
					return false;
				}
				for(var i = 0 ; i < oldSelect.options.length; i++){
						var xtoInfo = oldSelect.options[i].value.split('|');
						xtoName += xtoInfo[0] + ",";
				}
				xtoName = xtoName.substring(0, xtoName.length - 1);
			}else if(neRouteType == 2){
				var zsSelect;
				var csSelect;
				if(div1.style.display != 'none'){
					zsSelect = frames['frame101'].document.getElementById('zsSelect');
					csSelect = frames['frame101'].document.getElementById('csSelect');
				}else if(div2.style.display != 'none'){
					zsSelect = frames['frame100'].document.getElementById('zsSelect');
					csSelect = frames['frame100'].document.getElementById('csSelect');
				}else if(div3.style.display != 'none'){
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
					var xtoInfo = zsSelect.options[0].value.split('|');
					xtoName += xtoInfo[0] + ",";
				for(var j = 0; j < csSelect.options.length; j++){
					//xccName = csSelect.options[j].value + ",";
						var xccInfo = csSelect.options[j].value.split('|');
						xccName += xccInfo[0] + ",";
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
</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
</html>
