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
    		.left {
    			width:49%;
    			height:580px;
    			overflow-y:auto;
    			border-right:1px solid #e2e2e2;
    		}
    		.right {
    			width:49%;
    			height:580px;
    			position:absolute;
    			top:0px;
    			left:50%;
    			overflow-y:auto;
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
			.divCommonStyle {
				font-size:14px;
				margin-top:20px;
				margin-left:20px;
			}
    	</style>
    </head>
    <body>
    	<input type="hidden" id="roleId" value="${roleId }"/>
    	<div class="left leftov">
    		<c:forEach items="${menuList}" var="data" varStatus="status">
	    		<div class="divCommonStyle">
	    			<input type="checkbox" onchange="checkMenu(this)" name="role" data_name="${data.menuName}"  value="${data.id}" <c:if test="${data.isCheck=='yes'}"> checked="checked"</c:if> >
	    			${data.menuName}
	    		</div>
	    	</c:forEach>
    	</div>
    	<div class="right leftov">
    		<div class="divCommonStyle">
    			选中 <span id="checkNum" style="color:#EE7942">${checkNum}</span> / <span>${fn:length(menuList)}</span>
    		</div>
    		<div id="checkedMenu">
    			<c:forEach items="${menuList}" var="data" varStatus="status">
	    			<c:if test="${data.isCheck=='yes'}"><div id="${data.id }" class="divCommonStyle">${data.menuName}</div></c:if>
	    		</c:forEach>
    		</div>
    	</div>
   		<button id="no" type="button">取消</button>
   		<button id="yes" type="button">确定</button>
    </body>
    <script type="text/javascript">
    	function checkMenu(dom) {
    		var menuId = $(dom).val();
    		var menuName = $(dom).attr("data_name");
    		var checkNum = parseInt($("#checkNum").text());
    		if($(dom).prop("checked")){
    			var str = "<div id='"+menuId+"' class='divCommonStyle'>"+menuName+"</div>";
    			$("#checkedMenu").append(str);
    			checkNum += 1;
    		} else {
    			$("#"+menuId).remove();
    			checkNum -=1;
    		}
    		$("#checkNum").text(checkNum);
    	}
    
		$("#no").bind("click",function(){
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
			
		$("#yes").bind("click",function(){
			var menuIdStr = "";
			$("#checkedMenu div").each(function() {
				var id = $(this).attr("id");
				menuIdStr += id + ",";
			})
			var roleId = $("#roleId").val();
			var obj = {
				"roleId":roleId,
				"menuIdStr":menuIdStr
			};
			console.log(roleId);
			if(menuIdStr == "") {
				if(confirm('确定清空角色对应的菜单配置吗')){
					updateAj(obj);
				}
			} else {
				updateAj(obj);
			}
		});
		
		function updateAj(obj) {
			$.ajax({
				url:"${ctx}/role_setRoleMenuList.do",
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
