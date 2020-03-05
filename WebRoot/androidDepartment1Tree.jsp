<%@ page language="java" pageEncoding="UTF-8"%><!--告诉服务器，页面用的是UTF-8编码-->
<%@ include file="/common/header.jsp"%>
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
		<link href="${ctx}/widgets/tree/css/tree_common.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/widgets/tree/css/tree_common_people.css" rel="stylesheet" type="text/css" />
		<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.js" type="text/javascript"></script>
		<script src="${ctx}/widgets/tree/js/jquery.treeview.async.js" type="text/javascript"></script>
		
			
		<script type="text/javascript">
		function sousuo(){
        	var name=$('#mc').val();
        	document.getElementById("filetreeDiv").innerHTML="<ul id=\"black\" class=\"filetree\"></ul>"; 
        	$("#black").treeview({
			 	url: "departmentTree_getContent3.do?userId=${userId}&nodeId=${nodeId}&timestamp="+ new Date().getTime()+"&nodeId=${nodeId}&mc="+encodeURI(encodeURI(name))
		    });
        }
	        function initTree(){
		    	 $("#black").treeview({
				 	url: "departmentTree_getContent3.do?userId=${userId}&nodeId=${nodeId}&timestamp="+ new Date().getTime()+"&nodeId=${nodeId}&mc="+encodeURI(encodeURI('${mc}'))
			     });
	        }
	        
	        var bmid="";
	        var lastSelectedObj=null;
	        function check(o,type){ 
	        	if('${userids}'.indexOf(o.id)==-1){
        			return;
        		}
        		if(type==1){
        			var id=o.id.replace('{','').replace('}','');
            		document.getElementById(id).checked=!document.getElementById(id).checked;
        		}else{
        			if('${isExchange}'==1){ 
        				if(bmid.indexOf(o.id)==-1){
            				o.className = "checked";
                            lastSelectedObj=o;
            				bmid+=o.id+",";
        				}else{
        	        		if(o.className=="checked hover"){
        	        			o.className='';
        	  				}
        	        		bmid =bmid.replace((o.id+","),"");
        					lastSelectedObj=null;
        				}
        			}
        		}
        	}
	        function djcheckbox(id){
            }
            
	    </script>
		
	</head>
	<body  onload="initTree()">
		<table style="width: 100%;border-collapse: collapse;">
		<tr><td>
			<table class="searchContent">
			<tr >
				<td style="padding-left: 2px;">
					<span style="font-size: 20px">姓名：</span>
				</td> 
				<td>
					<input type="text" id="mc" style="height: 25px;font-size: 20px"  value="${mc}"/>
				</td>
				<td style="padding-left: 20px">
					<div  class="buttonActive"><div class="buttonContent"><button type="button"  onclick="sousuo()">检索</button></div></div>
			</td>
			</tr>
		</table>
				</td></tr>
			<tr>
				<td>
					<div id="filetreeDiv" style="padding-left: 20px;padding-top: 10px;overflow: auto;" >
						<ul id="black" class="filetree"></ul>
					</div>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
				function cdv_getvalues(){
					var boxs = document.getElementsByName('box');
					var itemId="";
					 for(var i=0;i<boxs.length;i++){
				         if(boxs[i].checked){
				        	 itemId +=boxs[i].value+",";
				       }
				    }    
					 if(itemId==""&&bmid==""){
		             		return null;
		             }
					 if(bmid!=""){
						 itemId +=bmid;
					 }
					 if(itemId!=""){
						 itemId = itemId.substring(0,itemId.length-1);
		             }
					return itemId;
				}
		</script>
	</body>
</html>