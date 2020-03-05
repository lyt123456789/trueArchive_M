<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
  
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<!--  告诉浏览器，页面用的是UTF-8编码 -->
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	 <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
	<link href="${ctx}/widgets/tree/css/tree_common.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/widgets/tree/css/tree_common_people.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
	<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
	<script type="text/javascript">
			function showTree(){
				document.getElementById('black').innerHTML='';
				$("#black").treeview({
					   url: "departmentTree_getContent.do?isBigDep=1&notEmployee=0&timestamp="+ new Date().getTime()
				});
			};
	        $(document).ready(function(){
	        	showTree();
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
  <body onload="showF('1')">
	<div class="tabs" currentIndex="1" eventType="click">
    	<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio selected" onclick=""><a ><span onclick="showF('1')">传阅人员</span></a></li>  
					<li class="lio" onclick=""><a ><span onclick="showF('2')">传阅处室</span></a></li>  
					<li style="float:right !important;"><a href="#" onclick="saveName()"><span>确定</span></a></li>  
				</ul>
			</div>
		</div>
    	<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
			<div id="div1" style="display: none;">
				<table style="width: 100%;border-collapse: collapse;">
					<tr> 
						<td style="width:330px;">
							<div style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 400px;border: 1px dashed #C2C2C2 " >
								<ul id="black" class="filetree"></ul>
							</div>
						</td>
						<td  style="width: 80px;text-align: center;padding: 5px;">
							<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="add()"/>
							<br/>
							<br/>
							<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="del()"/>
							<br/>
							<br/>
						</td> 
						<td style="width: 320px;">
							<select id="oldSelect" size="20" style="width:100%;height: 390px;border: 1px dashed #C2C2C2" multiple="multiple">
								<c:forEach var="m" items="${personNameList}">
									<option value="${m.employeeId}|${m.employeeName}|${m.employeeShortDn}">${m.employeeName} {${m.employeeShortDn}}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</div>	
			<div id="div2" style="display: none;">
				<iframe id="frame10" name="frame10" class="frame10" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_cyOfficeNameList.do?instanceId=${instanceId}"></iframe>
			</div>
		</div>
	 </div>
  </body>

<script>

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
						//循环遍历人员下拉框
						for(var i=0;i<jsobj.length;i++){
							var isin=false;
							for(var j=0;j<oldSelect.options.length;j++){
								if(oldSelect.options[j].value==jsobj[i][0]+'|'+jsobj[i][1]+'|'+jsobj[i][4]){
									isin=true;break;
								};
							}
							if(!isin){
								oldSelect.options.add(new Option(jsobj[i][1]+' {'+jsobj[i][4]+'}',jsobj[i][0]+'|'+jsobj[i][1]+'|'+jsobj[i][4]+''));
							};
						};
					};
			        }
			    }
			});
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
	}

	function saveName(){
		var personNameSelect = document.getElementById('oldSelect');
		var officeNameSelect = frames['frame10'].document.getElementById('oldSelect');
		var personName = '';
		var officeName = '';
		for(var i = 0 ; i < personNameSelect.options.length; i++){
			personName += personNameSelect.options[i].value + "#";
		}
		if(personName != ''){
			personName = personName.substring( 0, personName.length - 1);
		}
		for(var j = 0 ; j < officeNameSelect.options.length; j++){
			officeName += officeNameSelect.options[j].value + "#";
		}
		if(officeName != ''){
			officeName = officeName.substring( 0, officeName.length - 1);
		}
		window.returnValue = personName+";"+officeName;
		window.close();
	}

    $('.lio').click(function(){
	   $('.lio').removeClass("selected");
	   $(this).addClass("selected");
    });
	   
	function showF(type){
		if(type == '1'){
			document.getElementById('div2').style.display = "none";
			document.getElementById('div1').style.display = '';
		}else if(type == '2'){
			document.getElementById('div1').style.display = "none";
			document.getElementById('div2').style.display = '';
		}
	}
</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
</html>
