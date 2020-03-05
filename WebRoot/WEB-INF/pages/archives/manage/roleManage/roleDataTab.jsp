<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="renderer" content="webkit">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	    <link rel="stylesheet" type="text/css" href="${ctx}/layui2.5.1/layui/css/layui.css">
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    	<link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
    	<style type="text/css">
    		.buton {
    			margin-top:10px;
    			margin-left:20px;
    		}
    		.cardDiv {
    			background:#f2f2f2;
    			width:98%;
    			height:430px;
    			border-radius:8px;
    			position:relative;
    			top:60px;
    			left:10px;
    		}
			.cardHeader {
				height:45px;
				font-size:20px;
				border-bottom:1px solid #c2c2c2;
				color:#023131;
				font-weight:900;
				font-family:"Times New Roman",Georgia,Serif;
			}
			.cardHeader span {
				line-height:45px;
				text-align:center;
				margin-left:-83px;
			}
			.cardBody {
				font-size:16px;
				height:310px;
				overflow:auto;
				color:#023131;
				font-weight:500;
				font-family:"Times New Roman",Georgia,Serif;
			}
			.cardBody::-webkit-scrollbar {/*滚动条整体样式*/
		        width: 5px;     /*高宽分别对应横竖滚动条的尺寸*/
		        height: 1px;
			}
			.cardBody::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
		        border-radius: 13px;
		        -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
		        background: #d2d2d2;
			}
			.cardBody::-webkit-scrollbar-track {/*滚动条里面轨道*/
		        -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
		        border-radius: 13px;
		        background: #f2f2f2;
			}
			.cardBody span {
				line-height:35px;
			}
			.change,.delete{
			 	font-size:14px!important;
   			    width: 50px;
			    height: 27px;
			    color: #fff;
			    border-radius: 5px;
    		}
    		.change {
    			position:fixed;
			    right:30px;
			    top:490px;
    		}
    		.delete {
    			position:fixed;
			    right:90px;
			    top:490px;
    		}
    		.change {
			    background:#FFB800;
    		}
    		.delete {
    			background:#FF5722;
    		}
    	</style>
	</head>
	<body>
		<input type="hidden" value="${structureId }" id="structureId"/>
		<input type="hidden" value="${list[0]['idchild'] }" id="idchild"/>
		<input type="hidden" value="${list[0]['title'] }" id="title"/>
		<div class="layui-tab layui-tab-brief" lay-filter="statistics">
		  <ul class="layui-tab-title">
			<c:forEach items="${list }" var="data" varStatus="ds">
		    	<li lay-id="${data['idchild']}" <c:if test="${ds['index'] eq 0}">class="layui-this"</c:if>>${data['title']}</li>
		    </c:forEach>
		  </ul>
		</div>
		<div class="buton">
			<button type="button" id="addnum" data_tab="${list[0]['idchild'] }" onclick="setTabDataRange(this)" class="btn btn-add" style="float:left">
    			<i class="fa fa-plus"></i>添加
    		</button>
		</div>
		<div class="cardDiv">
			<div class="cardHeader">
				<span id="titleShowIn"></span>
			</div>
			<div class="cardBody">
			</div>
		</div>
	</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript" src="${ctx}/layui2.5.1/layui/layui.js"></script>
	<script type="text/javascript">
	layui.use('element', function(){
		var element = layui.element;
		element.on('tab(statistics)', function(){
		   var index = layer.load(2,{shade:[0.1,'#fff']});
		   var tabFlag = this.getAttribute('lay-id');
		   $("#addnum").attr("data_tab",tabFlag);
		   $("#idchild").val(tabFlag);
		   $("#title").val($(this).text());
		   getTabPageData(tabFlag);
		   layer.close(index);
		});
	});
	
	$(function() {
		var idchild = $("#idchild").val();	
		getTabPageData(idchild);
	});
	
	//获取对应分类对应的权限条件
	function getTabPageData(idchild) {
		var structureId = $("#structureId").val();
		var idchild = idchild;
		var obj = {
			"roleId":"${roleId}",//角色ID
        	"treeNode":structureId,//节点ID
        	"dataIdChild":idchild
		};
		$.ajax({
			url:"${ctx}/rolemanage_getRoleDataRange.do",
			type:"post",
			async:false,
			cache: false,
			dataType:"json",//返回值类型
			data:obj,
			success:function(data){
				var flag = data.flag;
				if("success"==flag) {
					$(".cardDiv").show();
					var title = $("#title").val();
					$("#titleShowIn").text("临时用户"+title+"数据查询设定条件");
					var range = data.data;
					var show = range.conditionShow;
					show = eval('(' + show + ')');
					var domStr = "<button class='delete' onclick='deleteCard(this)' data_id='"+ range.id +"' type='button'>删除</button>";
					domStr += "<button class='change' onclick='editCard(this)' data_id='"+ range.id +"' type='button'>编辑</button>"
					for(var i = 0; i < show.length; i = i + 2) {
						domStr += "<span>&emsp;权限范围："+show[i]+"</span><br/>";
						domStr += "<span>&emsp;权限关系："+ show[i+1] +"</span><br/>";
					}
					$(".cardBody").empty();
					$(".cardBody").append(domStr);
				} else {
					$(".cardDiv").hide();
				}
			},
			error:function(){
				alert("系统错误请重试");
			}	
		}); 
	}
	
	//删除
	function deleteCard(dom) {
		var id = $(dom).attr("data_id");
		if(confirm('确定删除所选权限吗')){
			//异步获取上传成功后的doc信息
			$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"json",//返回值类型
		        data:{"id":id},
		        url:"${ctx}/rolemanage_delRoleDataRange.do",
		        success:function(data){
		        	if("success" == data.flag) {
		        		window.location.reload();
		        	} else {
		        		alert("删除失败");
		        	}
		        }
		    })
		}
	}
	
	//修改权限
	function editCard(dom) {
		var id = $(dom).attr("data_id");
		var dataIdChild = $("#idchild").val();
		var dataFabric = $("#title").val();
		var title = encodeURI(encodeURI($("#title").val()));
		var condition = "?roleId=${roleId}&structureId=${structureId}&aeFlag=edit&idchild="+dataIdChild+"&title="+title+"&id="+id;
		layer.open({
		    type: 2,
		    title: "设置数据查询条件",
		    shade: 0.4,
		    area: ['970px', '380px'],
		    content: "${ctx}" + "/rolemanage_toSetRoleDataRange.do"+condition,
		    cancel: function(){
		        //右上角关闭回调
		   	 	window.location.reload();
		   }
		});
		
	}
	
	//添加对应分类的权限条件
	function setTabDataRange(dom) {
		var idchild = $(dom).attr("data_tab");
		var obj = {
			"roleId":"${roleId}",
			"structureId":"${structureId}",
			"idchild":idchild,
			"treeId":"${treeId}"
		};
		$.ajax({
			url:"${ctx}/rolemanage_checkRoleAllotData.do",
			type:"post",
			async:false,
			cache: false,
			dataType:"json",//返回值类型
			data:obj,
			success:function(data){
				var flag = data.have;
				if("have" == flag) {
					alert("抱歉，您已设定该角色的查询权限，请点击下方编辑按钮进行编辑或删除后再添加！");
				} else {
					var title = encodeURI(encodeURI($("#title").val()));
					var condition = "?roleId=${roleId}&structureId=${structureId}&aeFlag=add&idchild="+idchild+"&title="+title;
					layer.open({
        			    type: 2,
        			    title: "设置数据查询条件",
        			    shade: 0.4,
        			    area: ['970px', '380px'],
        			    content: "${ctx}" + "/rolemanage_toSetRoleDataRange.do"+condition,
        			    cancel: function(){
        			        //右上角关闭回调
        			   	 	window.location.reload();
        			   }
        			});
				}
			},
			error:function(){
				alert("系统错误请重试");
			}	
		}); 
	}
	</script>
</html>