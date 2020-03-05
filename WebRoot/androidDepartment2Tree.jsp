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
	        var zb=null;
            function check(o,type){
            	if('${userids}'.indexOf(o.id)==-1){
        			return;
        		}
            	var id=o.id.replace('{','').replace('}','');
             	// 使选中节点的背景变为checked样式中的颜色
             	if(type==1){
             		if(document.getElementById(id).checked){
                        if(zb==o.id){
             				var r=confirm("取消主办吗？");
             				if(r){
             					zb=null;
                                document.getElementById(id).checked=false;
             				}
                        }else{
                        	var r=confirm("取消协办吗？");
             				if(r){
                                document.getElementById(id).checked=false;
             				}
                        }
             		}else{
             			if(zb==null){
             				var r=confirm("选择为主办吗？");
             				if(r){
             					zb=o.id;
             					document.getElementById(id).checked=true;
             				}
             			}else{
             				var r=confirm("选择为协办吗？");
             				if(r){
                                document.getElementById(id).checked=true;
             				}
             			}
             		}
             	}else{
             		if('${isExchange}'==1){ 
             			if(zb==null&&bmid.indexOf(o.id)==-1){
                 			var r=confirm("选择为主办吗？");
                 			if(r){
                     			zb=o.id;
                     			o.className = "checked";
             				}
                 		}else{
                 			if(zb==o.id){
                 				var r=confirm("取消主办吗？");
                 				if(r){
                 					zb=null;
                                    if(o.className=="checked"){
                	        			o.className='';
                	  				}
                 					if(o.className=="checked hover"){
                	        			o.className='';
                	  				}
                 				}
                 			}else{
                 				if(bmid.indexOf(o.id)==-1){
                 					var r=confirm("选择为协办吗？");
                     				if(r){
                     					o.className = "checked";
                        				bmid+=o.id+",";
                     				}
                    				
                				}else{
                					var r=confirm("取消协办吗？");
                     				if(r){
                     					if(o.className=="checked hover"){
                    	        			o.className='';
                    	  				}
                     					if(o.className=="checked"){
                    	        			o.className='';
                    	  				}
                    	        		bmid =bmid.replace((o.id+","),"");
                     				}
                				}
                 			}
                 		}
             		}
             	}
            }
            function djcheckbox(id){
            	var ids=id.replace('{','').replace('}','');
            	if(!document.getElementById(ids).checked){
                    if(zb==id){
         				var r=confirm("取消主办吗？");
         				if(!r){
                            document.getElementById(ids).checked=true;
         				}else{
         					zb=null;
         				}
                    }else{
                    	var r=confirm("取消协办吗？");
         				if(!r){
                            document.getElementById(ids).checked=true;
         				}
                    }
         		}else{
         			if(zb==null){
         				var r=confirm("选择为主办吗？");
         				if(!r){
         					document.getElementById(ids).checked=false;
         				}else{
         					zb=id;
         				}
         			}else{
         				var r=confirm("选择为协办吗？");
         				if(!r){
                            document.getElementById(ids).checked=false;
         				}
         			}
         		}
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
					<input type="text" id="mc" style="height: 25px;font-size: 20px" value="${mc}"/>
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
						<ul id="black" class="filetree" ></ul>
					</div>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			function cdv_getvalues(){
				var itemIds="";
             	if(zb==null){
             		itemIds = 'null;';
             	}else{
             		itemIds=zb+';';
             	}
             	var items="";
             	var boxs = document.getElementsByName('box');
				 for(var i=0;i<boxs.length;i++){
			         if(boxs[i].checked){
			        	 var ids = boxs[i].value+"";
			        	 if(ids.indexOf(zb)<0){
			        		 items +=ids+",";
			        	 }
			       }
			    }
				if(bmid!=""){
					items +=bmid;
				}
             	if(items==""){
             		items="null";
             	}else{
             		items = items.substring(0,items.length-1);
             	}
				return itemIds+items;
			}
		</script>
	</body>
</html>