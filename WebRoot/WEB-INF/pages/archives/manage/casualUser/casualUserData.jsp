 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
 <%@ include file="/common/headerbase.jsp"%>
<html>
    <head>
        <title>添加角色</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
    	<link rel="stylesheet" href="${ctx}/lib/bootstrap-material/css/bootstrap-material-design.css">
		<link rel="stylesheet" href="${ctx}/lib/layui/css/layui.css">
    	<link rel="stylesheet" href="${ctx}/lib/layui_ext/dtree/dtree.css">
    	<link rel="stylesheet" href="${ctx}/lib/layui_ext/dtree/font/dtreefont.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/layui2.5.1/layui/css/layui.css">
    	<style type="text/css">
    		.left {
    			width:23%;
    			height:620px;
    			overflow:scroll;
    			border-right:1px solid #e2e2e2;
    		}
    		.right {
    			width:77%;
    			height:620px;
    			position:absolute;
    			top:0px;
    			left:23%;
    			overflow:auto;
    		}
    		.leftov::-webkit-scrollbar {/*滚动条整体样式*/
		        width: 5px;     /*高宽分别对应横竖滚动条的尺寸*/
		        height: 1px;
			}
			.leftov::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
		        border-radius: 13px;
		        -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
		        background: #00BFFF;
			}
			.leftov::-webkit-scrollbar-track {/*滚动条里面轨道*/
		        -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
		        border-radius: 13px;
		        background: #E0FFFF;
			}
			iframe {
	    		width:100%;
	    		height:620px;
	    	}
    	</style>
    </head>
    <body>
    	<div class="left leftov">
    		<div id="toolbarDiv">
	    		<ul id="demoTree" class="dtree" data-id="-1"></ul>
			</div>
    	</div>
    	<div class="right">
    		<iframe id="tabIframe" name="statisticsIframe" src="" frameborder="0"></iframe>
    	</div>
    </body>
    <script src="${ctx}/lib/jquery-3.2.1.js"></script>
	<script src="${ctx}/lib/popper.js"></script>
	<script type="text/javascript" src="${ctx}/layui2.5.1/layui/layui.js"></script>
	<script src="${ctx}/lib/bootstrap-material/js/bootstrap-material-design.js"></script>
    <script type="text/javascript">
	    layui.extend({
			dtree: '${ctx}/lib/layui_ext/dist/dtree'
		}).use(['element','layer', 'dtree'], function(){
			var element = layui.element, layer = layui.layer, dtree = layui.dtree, $ = layui.$;
			var dataJson=${dataJson};
			var commonTree = dtree.render({
			    elem: "#demoTree",
			    //url: "${ctx}/model_getModelTreeData.do",
			    data:dataJson,
			    defaultRequest:{nodeId:"id",parentId:"parentId"},
			    dataFormat: "list",  //配置data的风格为list
			    dot:false,
			    type:"all",//全量加载
			    initLevel: "2",
			    checkbar: true,  
			    checkbarType: "all",
			    checkbarData: "choose" 
			});

			// 点击节点触发回调
			dtree.on("node('demoTree')" ,function(obj){
				var $div = obj.dom;
			    commonTree.clickSpread($div);  //调用内置函数展开节点
			    var treeId = obj.param.id;
				var basicData = obj.param.basicData;
				if(basicData != "0" && basicData != "-1") {
					$("#tabIframe").attr("src","${ctx}/role_toCasualUserDataTabPage.do?structureId="+basicData+"&casualId=${casualId}&treeId="+treeId);
				}
			}); 
		});
    
		$("#no").bind("click",function(){
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
			
		$("#yes").bind("click",function(){
			var eyIdStr = $("#checkedPeople").val();
			var roleId = $("#roleId").val();
			var obj = {
				"roleId":roleId,
				"eyIdStr":eyIdStr
			};
			if(eyIdStr == "") {
				if(confirm('确定清空角色对应的人员配置吗')){
					updateAj(obj);
				}
			} else {
				updateAj(obj);
			}
		});
		
		function updateAj(obj) {
			$.ajax({
				url:"${ctx}/role_setRoleEmployeeAbout.do",
				type:"post",
				async:false,
				cache: false,
				data:obj,
				success:function(msg){
					if(msg=="success"){
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					}else{
						alert("新增失败");
					}
				},
				error:function(){
					alert("系统错误请重试");
				}	
			}); 
		}
	</script>
</html>
