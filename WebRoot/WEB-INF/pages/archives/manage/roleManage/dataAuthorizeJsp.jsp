<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
	<style>
		#main{
			width: 100%!important;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar" style="padding: 9px 7px 4px 4px;">
			<form name="roleList"  id="roleList" action="${ctx }/role_toDataAuthorizeJsp.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
			    <div class="search" style="height: auto;">
			    	<button type="button" id="addnum" class="btn btn-add" style="float:left">
			    		<i class="fa fa-plus"></i>目录树授权
			    	</button>
    				<%-- <div class="fr">
        				<input type="text" id="contect" name="contect" value="${contect}" placeholder="输入关键字查询">
        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
        				</button>
    				</div> --%>
	            </div>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div>
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/role_toDataAuthorizeJsp.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
					<tr>
						<th width="5%">序号</th>
		    	  		<th width="1%"></th>
		    	  		<th align="center">操作</th>
		    	  		<th align="center">角色名称</th>
		    	  		<th align="center">角色描述</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${roleList}" var="role" varStatus="status">
		    		<tr>
		    			<td align="center" >
							${(selectIndex-1)*pageSize+status.count }
						</td>
		    			<td align="center" >
							<input type="checkbox" name="checkbox" value=""/>
						</td>
						<td align="center" >
						</td>
						<td align="center" >
						</td>
						<td align="center" >
						</td>
		    		</tr>
		    	</c:forEach>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>


</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/using_showLendingList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('roleList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
		
	$("#addnum").bind("click",function(){
		var objs = document.getElementsByName('checkbox');
		var ids = '';
		/* for(var i=0; i<objs.length; i++) {
		   if(objs[i].checked==true ){
			  ids += objs[i].value+",";
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1); */
		ids='11';
		layer.open({
            type: 2,
            title: "目录树授权",
            shade: 0.4,
            area: ['400px', '600px'],
            content: "${ctx}/role_showDirectories.do?roleIds="+ids,
            btn:['授权','关闭'],
            yes: function(index){
            	 //当点击‘确定’按钮的时候，获取弹出层返回的值
                var treeNodes = window["layui-layer-iframe" + index].callbackdata();
                //打印返回的值，看是否有我们想返回的值。
                if(treeNodes!=""){
                	 $.ajax({
     			        async:true,//是否异步
     			        type:"POST",//请求类型post\get
     			        cache:false,//是否使用缓存
     			        dataType:"text",//返回值类型
     			        data:{"treeNodes":treeNodes,"roleIds":ids},
     			        url:"${ctx}/role_authorize.do",
     			        success:function(result){
     			        	if(result=="success"){
     			        		alert("授权成功！")
     			        		 //最后关闭弹出层
         		                layer.close(index);
     			        	}else{
     			        		alert("授权失败！")
     			        	}
     			        }
     			    }); 
                }else{
                	alert("请选择目录");
                }
            },
            cancel: function(){
                //右上角关闭回调
           	 window.location.reload();
           }
        });
	});
	
	
</script>
</html>