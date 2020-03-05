<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${systitle}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/dtree/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/font/dtreefont.css">
    <style type="text/css">
    	#toolbarDiv{
		    overflow: auto;
		    flex: 26;
    	}
    	#treediv{
		    height: 100%;
		    display: flex;
		    flex-direction: column;
		    flex: 1;
    	}
    	#editDiv{
		    height: 100%;
		    flex: 5;
    	}
    	.iframe{
    	height: 654px;
    	}
    	#structureDiv{
    		overflow: hidden;
    		flex: 13;
    	}
    </style>
</head> 
<style type="text/css">


</style>

</head>
<body style="width: 100%;height: 100%;">
<div style="height: 100%;width: 100%;display: flex;">
	<div  id="treediv" >
	  <%-- <input type="button" value="${business}">${business}</input> --%>
	    <div style="flex:1;"><select onchange="chooseBusinessType(this.options[this.options.selectedIndex].value)">
	    <!-- <option value=""></option> -->
	    <c:forEach items="${businessList}" var="data" varStatus="status">
	    <option value="${data.id}" <c:if test="${business eq data.id}"> selected="selected"</c:if> >${data.esTitle}</option>
	    </c:forEach>
	    </select></div>
	  
		<!-- <div><select><option>档案</option></select></div> -->
		<div  id="toolbarDiv">
		    <ul id="demoTree" class="dtree" data-id="-1"></ul>
		</div>
		<div  id="structureDiv" style="overflow: auto;">
		    结构树
		    <ul id="demoTree2" class="dtree" data-id="-1"></ul>
		</div>
	</div>
	<div id="editDiv">
		<iframe id="frame" width="100%" frameborder="0" src="" class="iframe"></iframe>
	</div>
</div>	
	
<script src="${ctx}/lib/jquery-3.2.1.js"></script>
<script src="${ctx}/lib/popper.js"></script>
<script src="${ctx}/lib/layui/layui.js"></script>
<script src="${ctx}/lib/bootstrap-material/js/bootstrap-material-design.js"></script>
<script>
var ctx = "${ctx}";

   function chooseBusinessType(businessId){
	  // alert(businessId);
		window.location.href="${ctx}/dataTemp_toDataTempJsp.do?business="+businessId;
  }
//   chooseBusinessType(); 
    

	layui.extend({
		dtree: '${ctx}/dtree/layui_ext/dtree/dtree'
	}).use(['element','layer', 'dtree'], function(){
		var element = layui.element, layer = layui.layer, dtree = layui.dtree, $ = layui.$;
		var dataJson=${treeJson};
		var commonTree = dtree.render({
		    elem: "#demoTree",
		    url: "${ctx}/dataTemp_getSonTreeJson.do",
		    data:dataJson,
		    defaultRequest:{nodeId:"nodeId",parentId:"parentId"},
		    dataFormat: "list",  //配置data的风格为list
		    ficon: ["1","-1"],
		    line:true,
		    type:"load",//分级加载
		    cache:false,
		    initLevel: "2",
		    toolbar:true,
		    toolbarExt:[
		                {toolbarId: "copyNode",icon:"dtree-icon-wefill",title:"复制",handler: function(node){
		                	//复制模板
		    				var nodeId = node.nodeId;
		    				var parentId = node.parentId;
		    				var leaf = node.leaf;
		    				var structureId = node.basicData;
		    				if(!leaf){
		    					layer.msg("该节点下已有子节点，无法引用模板");
		    				}else if(leaf&&(structureId!="0"&&structureId!="-1")){
		    					layer.msg("该节点已经关联了模板");
		    				}else{
			    				layer.open({
		    				           type: 2,
		    				           title: "复制模板",
		    				           shadeClose: true,
		    				           shade: 0.4,
		    				           area: ['50%', '90%'],
		    				           content: "${ctx}/dataTemp_toCopyStructurePage.do?nodeId="+nodeId+"&parentId="+parentId,
		    				           btn:['确定','取消'],
		    				           yes:function(index,layero){
		    				               var copyNodeId = $(layero).find("iframe")[0].contentWindow.document.getElementById("copyNodeId").value;
		    				               if(copyNodeId==""){
		    				            	   layer.msg("请选择复制的节点");
		    				               }else{
		    				            	   layer.load();
			                   					$.ajax({
			   					                    url:'${ctx}/dataTemp_addCopyStructure.do',
			   					                    async:true,//是否异步
			   					        	        type:"POST",//请求类型post\get
			   					        	        cache:false,//是否使用缓存
			   					        	        dataType:"text",//返回值类型
			   					                    data:{"copyNodeId":copyNodeId,"nodeId":nodeId},
			   					                    success:function(data){
			   					                    	layer.closeAll('loading');
			   					                        if(data == 'success'){
			   					                        	layer.msg("复制成功")
			   					                        	refreshTreeNode(parentId);
			   					                        }else{
			   					                            layer.msg("复制失败")
			   					                        }
			   					                    },
			   					                    error:function () {
			   					                    	layer.closeAll('loading');
			   					                        layer.msg("复制失败")
			   					                    }
			   									});
		    				               }
		    				           }
		    					});
		    				}
		                
		                }},
		    			{toolbarId: "chooseTemp",icon:"dtree-icon-wefill",title:"选择模板",handler: function(node){
		    				//选择模板
		    				var nodeId = node.nodeId;
		    				var parentId = node.parentId;
		    				var leaf = node.leaf;
		    				var structureId = node.basicData;
		    				if(!leaf){
		    					layer.msg("该节点下已有子节点，无法关联模板");
		    				}else if(leaf&&(structureId!="0"&&structureId!="-1")){
		    					layer.msg("该节点已经关联了模板");
		    				}else{
		    					layer.open({
		    				           type: 2,
		    				           title: "选择模板",
		    				           shadeClose: true,
		    				           shade: 0.4,
		    				           area: ['90%', '90%'],
		    				           content: "${ctx}/dataTemp_toChooseStructurePage.do?nodeId="+nodeId+"&parentId="+parentId
		    					});
		    				}
		    			}},
		    			{toolbarId: "copyTemp",icon:"dtree-icon-wefill",title:"引用模板",handler: function(node){
		    				//引用模板
		    				var nodeId = node.nodeId;
		    				var parentId = node.parentId;
		    				var leaf = node.leaf;
		    				var structureId = node.basicData;
		    				if(!leaf){
		    					layer.msg("该节点下已有子节点，无法引用模板");
		    				}else if(leaf&&(structureId!="0"&&structureId!="-1")){
		    					layer.msg("该节点已经关联了模板");
		    				}else{
			    				layer.open({
		    				           type: 2,
		    				           title: "引用模板",
		    				           shadeClose: true,
		    				           shade: 0.4,
		    				           area: ['90%', '90%'],
		    				           content: "${ctx}/dataTemp_toQuoteStructurePage.do?nodeId="+nodeId+"&parentId="+parentId
		    					});
		    				}
		    			}},
		    			{toolbarId: "deleteTemp",icon:"dtree-icon-wefill",title:"取消关联",handler: function(node){
		    				var structureId = node.basicData;	
		    				var parentId = node.parentId;
	    					if(structureId=="0"||structureId=="-1"){
		    					layer.msg("该节点未关联模板");
		    				}else{
		    					layer.confirm('取消节点关联的下级结构时，下级结构中的数据也将被删除，是否继续？', { btn: ['确定','取消'] //按钮
            			    	}, function(){
                					layer.closeAll('dialog');
                					layer.load();
                					$.ajax({
					                    url:'${ctx}/dataTemp_deleteStructureFromNode.do',
					                    async:true,//是否异步
					        	        type:"POST",//请求类型post\get
					        	        cache:false,//是否使用缓存
					        	        dataType:"text",//返回值类型
					                    data:{"nodeId":node.nodeId},
					                    success:function(data){
					                    	layer.closeAll('loading');
					                        if(data == 'success'){
					                        	layer.msg("取消关联成功")
					                        	//commonTree.changeTreeNodeAdd("refresh");
					                        	refreshTreeNode(parentId);
					                        }else{
					                            layer.msg("取消关联失败")
					                        }
					                    },
					                    error:function () {
					                    	layer.closeAll('loading');
					                        layer.msg("取消关联失败")
					                    }
									});
                				});			
		    				}
		    			}}
		    		],
		    toolbarFun: {
		        addTreeNode: function(treeNode, $div) { 
					//新增节点
		    		/* var structureId = treeNode.basicData;
					console.log($div);
		    		if(structureId!="0"&&structureId!="-1"){
		    			layer.msg("该节点下已关联结构，无法新增子节点");
		    			return;
		    		} */
					$.ajax({
	                    url:'${ctx}/dataTemp_addTreeNode.do',
	                    async:true,//是否异步
	        	        type:"POST",//请求类型post\get
	        	        cache:false,//是否使用缓存
	        	        dataType:"text",//返回值类型
	                    data:{"parentId":treeNode.parentId,"title":treeNode.context},
	                    success:function(data){
	                        if(data == 'success'){
	                        	document.getElementById("frame").src=encodeURI('${ctx}/dataTemp_toTempEditMainJsp.do?treeId='+treeNode.parentId);
	                        	commonTree.changeTreeNodeAdd("refresh");
	                        }else{
	                            layer.msg("添加失败")
	                        }
	                    },
	                    error:function () {
	                        layer.msg("添加失败")
	                    }
					});
		         },
		         editTreeNode: function(treeNode, $div) {
		        	 $.ajax({
		                    url:'${ctx}/dataTemp_updateTreeNode.do',
		                    async:true,//是否异步
		        	        type:"POST",//请求类型post\get
		        	        cache:false,//是否使用缓存
		        	        dataType:"text",//返回值类型
		                    data:{"id":treeNode.nodeId,"title":treeNode.context},
		                    success:function(data){
		                        if(data == 'success'){
		                        	 layer.msg("修改成功")
		                        	 commonTree.changeTreeNodeEdit(true);
		                        	 document.getElementById("frame").src=encodeURI('${ctx}/dataTemp_toTempEditMainJsp.do?treeId='+treeNode.parentId);
		                        }else{
		                            layer.msg("修改失败")
		                        }
		                    },
		                    error:function () {
		                        layer.msg("修改失败")
		                    }
						});
		         },
		         delTreeNode: function(treeNode, $div){
		        	//下校验节点下结构是否有数据
 					$.ajax({
		                    url:'${ctx}/dataTemp_checkIsHaveDataOfNode.do',
		                    async:true,//是否异步
		        	        type:"POST",//请求类型post\get
		        	        cache:false,//是否使用缓存
		        	        dataType:"text",//返回值类型
		                    data:{"nodeId":treeNode.nodeId},
		                    success:function(data){
		                    	if(data="false"){
		                    		layer.confirm('已选择的节点下有数据，删除后不可恢复！您确定需要删除该节点吗？', { btn: ['确定','取消'] //按钮
	            			    	}, function(){
	            			    		layer.closeAll('dialog');
	            			    		$.ajax({
	            		                    url:'${ctx}/dataTemp_deleteTreeNode.do',
	            		                    async:true,//是否异步
	            		        	        type:"POST",//请求类型post\get
	            		        	        cache:false,//是否使用缓存
	            		        	        dataType:"text",//返回值类型
	            		                    data:{"ids":treeNode.nodeId},
	            		                    success:function(data){
	            		                        if(data == 'success'){
	            		                        	//refreshTreeNode(treeNode.parentId);
	            		                        	commonTree.changeTreeNodeDel(true);
	            		                        	document.getElementById("frame").src=encodeURI('${ctx}/dataTemp_toTempEditMainJsp.do?treeId='+treeNode.parentId);
	            		                        }else{
	            		                            layer.msg("删除失败")
	            		                        }
	            		                    },
	            		                    error:function () {
	            		                        layer.msg("删除失败")
	            		                    }
	            						});
	                				});	
		                    	}else if(data="true"){
		                    		$.ajax({
		    		                    url:'${ctx}/dataTemp_deleteTreeNode.do',
		    		                    async:true,//是否异步
		    		        	        type:"POST",//请求类型post\get
		    		        	        cache:false,//是否使用缓存
		    		        	        dataType:"text",//返回值类型
		    		                    data:{"ids":treeNode.nodeId},
		    		                    success:function(data){
		    		                        if(data == 'success'){
		    		                        	//refreshTreeNode(treeNode.parentId);
		    		                        	commonTree.changeTreeNodeDel(true);
		    		                        	document.getElementById("frame").src=encodeURI('${ctx}/dataTemp_toTempEditMainJsp.do?treeId='+treeNode.parentId);
		    		                        }else{
		    		                            layer.msg("删除失败")
		    		                        }
		    		                    },
		    		                    error:function () {
		    		                        layer.msg("删除失败")
		    		                    }
		    						});
		                    	}
		                    	                  
		                    },
		                    error:function () {
		                        layer.msg("校验失败")
		                    }
					});
		         },
		      }
		});	
		
		
		//加载业务树下的结构树
		var commonTree2;
		// 点击节点触发回调
		dtree.on("node('demoTree')" ,function(obj){
		    var $div = obj.dom;
		    commonTree.clickSpread($div);  //调用内置函数展开节点
		    var treeId = obj.param.nodeId;
			var parentId = obj.param.parentId;
			var structureId = obj.param.basicData;
			var leaf = obj.param.leaf;
			//查询结构
			 $.ajax({
                 url:'${ctx}/dataTemp_getStructureJsonOfTree.do',
                 async:true,//是否异步
     	         type:"POST",//请求类型post\get
     	         cache:false,//是否使用缓存
     	         dataType:"text",//返回值类型
                 data:{"treeId":treeId,"structureId":structureId},
                 success:function(data){
                	 var structureJson=[];
                	 if(data=="[]"){//如果没有子结构，就直接定位到数据视图 
                		 document.getElementById("frame").src=encodeURI('${ctx}/dataTemp_toTempEditMainJsp.do?treeId='+treeId);
                	 }else{//若有子结构，则直接定位到结构视图
                		 document.getElementById("frame").src=encodeURI('${ctx}/dataTemp_toTempEditMainJsp.do?treeId='+treeId+'&structureId='+structureId);
                		 structureJson = JSON.parse(data);
                	 }
                	 commonTree2 = dtree.render({
   					    elem: "#demoTree2",
   					    data:JSON.parse(data),
   					    dataFormat: "list",  //配置data的风格为list
   					    ficon: ["1","-1"],
   					    line:true,
   					    initLevel: "4",
   					    type:"all"
   					});	
                	
                 },
                 error:function () {
                     layer.msg("结构查询出错")
                 }
				});
				 
		});  
	
		
		// 点击节点触发回调
		dtree.on("node('demoTree2')" ,function(obj){
		    var $div = obj.dom;
			var structureId = obj.param.nodeId;
			var treeId = obj.param.basicData;
			document.getElementById("frame").src=encodeURI('${ctx}/dataTemp_toTempEditMainJsp.do?treeId='+treeId+'&structureId='+structureId);
		});  
		
	});
	
	function refreshTreeNode(treeId){
		$("div[dtree-click='itemNodeClick'][dtree-id='demoTree'][data-id='"+treeId+"']").click();
		$("div[dtree-click='itemNodeClick'][dtree-id='demoTree'][data-id='"+treeId+"']").click()
	}
	
	</script>
</body>
</html>
