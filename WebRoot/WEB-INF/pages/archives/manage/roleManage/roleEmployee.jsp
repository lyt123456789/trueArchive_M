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
    	<style type="text/css">
    		#yes,#no {
   			    width: 93px;
			    height: 40px;
			    color: #fff;
			    border-radius: 5px;
    		}
    		#no {
    			position:fixed;
			    bottom:20px;
			    right:120px;
			    background: #ccc;
    		}
    		#yes {
    			position:fixed;
			    bottom:20px;
			    right:20px;
    			background: #1c9dd4;
    		}
    	</style>
    </head>
    <body>
    	<input type="hidden" id="roleId" value="${roleId }"/>
    	<input type="hidden" id="checkedPeople" value="${checked }"/>
    	<div class="left leftov">
    		<div id="toolbarDiv">
	    		<ul id="demoTree" class="dtree" data-id="1"></ul>
			</div>
    	</div>
   		<button id="no" type="button">取消</button>
   		<button id="yes" type="button">确定</button>
    </body>
    <script src="${ctx}/lib/jquery-3.2.1.js"></script>
	<script src="${ctx}/lib/popper.js"></script>
	<script src="${ctx}/lib/layui/layui.js"></script>
	<script src="${ctx}/lib/bootstrap-material/js/bootstrap-material-design.js"></script>
    <script type="text/javascript">
	    layui.extend({
			dtree: '${ctx}/lib/layui_ext/dist/dtree'
		}).use(['element','layer', 'dtree'], function(){
			var element = layui.element, layer = layui.layer, dtree = layui.dtree, $ = layui.$;
			var dataJson=${dataJson};
			dtree.render({
			    elem: "#demoTree",
			    //url: "${ctx}/model_getModelTreeData.do",
			    data:dataJson,
			    defaultRequest:{nodeId:"id",parentId:"parentId"},
			    dataFormat: "list",  //配置data的风格为list
			    dot:false,
			    type:"all",//全量加载
			    initLevel: "4",
			    checkbar: true,  
			    checkbarType: "all",
			    checkbarData: "choose" 
			});

			dtree.on("chooseDone('demoTree')" ,function(obj){
				var params = dtree.getCheckbarNodesParam("demoTree");
				console.log(params);
				var emIdStr = "";
				if(params != null && params != undefined && params.length != 0) {
					for(var i=0;i<params.length;i++) {
						var flag = params[i].basicData;
						if(!isEmpty(flag)) {
							emIdStr += params[i].id + ",";
						}
					}
					$("#checkedPeople").val(emIdStr);
				} else {
					$("#checkedPeople").val("");
				}
			});
		});
    
    	/* function checkEy(dom) {
    		var eyId = $(dom).val();
    		var eyName = $(dom).attr("data_name");
    		var checkNum = parseInt($("#checkNum").text());
    		if($(dom).prop("checked")){
    			var str = "<div id='"+eyId+"' class='divCommonStyle'>"+eyName+"</div>";
    			$("#checkedEy").append(str);
    			checkNum += 1;
    		} else {
    			$("#"+eyId).remove();
    			checkNum -=1;
    		}
    		$("#checkNum").text(checkNum);
    	} */
    
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
