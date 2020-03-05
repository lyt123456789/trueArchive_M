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
   
<style type="text/css">

</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="lendLingList"  id="lendLingList" action="${ctx }/rolemanage_toRoleDataAuthorizeJsp.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
				<div class="search">
					<!-- <button type="button" onclick="javascript:checkMenu();" target="_self" class="btn btn-write" style="float:left">
			    		<i class="fa fa-pencil-square-o fa-lg"></i>目录树授权
			    	</button>
   					<button type="button" onclick="javascript:dataLicense();" class="btn btn-detail fts" style="float: left;">
   						<i class="fa fa-file-text-o"></i>数据授权
   					</button> -->
    				<div class="fr">
        				<input type="text" id="roleName" name="roleName" value="${esTitle }" placeholder="">
        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
        				</button>
    				</div>
	            </div>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
			<thead>
				<tr>
	    	  		<!-- <th width="2%"></th> -->
	    	  		<th width="14%">操作</th>
	    	  		<th align="center" width="14%">序号</th>
	    	  		<th align="center" width="14%">角色名称</th>
	    	  		<th align="center" width="24%">状态</th>
	    	  		<th align="center" width="14%">排序号</th>
                   </tr>
	    	</thead>
	    	<c:forEach items="${list}" var="item" varStatus="status">
	    		<tr>
	    			<%-- <td align="center" >
						<input type="checkbox"  name="roleId"  value="${item.roleId}" class="check_view_state" id="${item.roleId}" style="display: none;">
						<label for="${item.roleId}"></label>
					</td> --%>
					<td align="center" >
						<button type="button"  onclick="javascript:checkMenu('${item.roleId}');" class="btn btn-detail fts">
    						<i class="fa fa-file-text-o"></i>目录树授权
    					</button>
    					<button type="button"  onclick="javascript:dataLicense('${item.roleId}');" class="btn btn-detail fts">
    						<i class="fa fa-file-text-o"></i>数据授权
    					</button>
    					<button type="button"  onclick="javascript:btnLicense('${item.roleId}');" class="btn btn-detail fts">
    						<i class="fa fa-file-text-o"></i>按钮授权
    					</button>
					</td>
					<td align="center"  title ="${status.count}" >${status.count}</td>
					<td align="center"  title ="${item.roleName}" >${item.roleName}</td>
					<td align="center"  title ="${item.roleStatus}" >
						<c:if test="${item.roleStatus eq 1}"><font style="color: green;">启用</font></c:if>	
		        		<c:if test="${item.roleStatus eq 0}"><font style="color: red;">禁用</font></c:if>
		        		<c:if test="${empty item.roleStatus}">暂无数据</c:if>
					</td>
					<td align="center"  title ="${item.roleSort}" >${item.roleSort}</td>
	    		</tr>
	    	</c:forEach>
		</table>
	</div>
	<!-- <div class="wf-list-ft" id="pagingWrap">
		
	</div> -->
</div>

<%-- <dl class="checkBox" id="btn_manage">
        <dt><input type="checkbox" id="checkAll">
            <label>全选</label>
            <a href="javascript:;">反选</a>
        </dt>
        <dd>
            <!-- /*选项由选择框(输入框)和标示构成，所以使用<input>标签和<label>标签,label 元素不会呈现任何特殊效果。如果在 label 元素内点击文本，就会触发此控件*/ -->
            <c:forEach items="${btn_list}" var="item" varStatus="status">
            <p><input type="checkbox" name="item" value="${item.id}" onclick="demo(this);"><label>${item.btn_name}</label></p>
            </c:forEach>
        </dd>
</dl> --%>

</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	<%-- window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/met_toNameSpacePage.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lendLingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	}; --%>
	
	function checkMenu(roleId) {
	
		layer.open({
            type: 2,
            title: "目录树授权",
         	resize :true,
         	moveOut:true,
         	scrollbar:true,
            shade: 0.4,
         	zIndex: layer.zIndex, //重点1
  	        success: function(layero){
  	          layer.setTop(layero); //重点2
  	        },
            area: ['30%', '88%'],
            content: "${ctx}/rolemanage_toCasualTreePage.do?roleId="+roleId,
            btn:['授权','关闭'],
            yes: function(index){
            	 //当点击‘确定’按钮的时候，获取弹出层返回的值
                var treeNodes = window["layui-layer-iframe" + index].callbackdata();
                $.ajax({
    				url:"${ctx}/rolemanage_saveRoleNode.do",
    				type:"post",
    				async:false,
    				cache: false,
    				data:{"roleId":roleId,"nodeIds":treeNodes},
    				success:function(msg){
    					if(msg=="success"){
    						alert("授权成功");
    						window.location.reload();
    					}else{
    						alert("修改失败");
    					}
    				},
    				error:function(){
    					alert("系统错误请重试");
    				}	
    			});
            },
            cancel: function(){
                //右上角关闭回调
           	 window.location.reload();
           }
        });
	}
	
	function dataLicense(roleId) {
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"json",//返回值类型
	        data:{"roleId":roleId},
	        url:"${ctx}/rolemanage_checkRoleNodes.do",
	        success:function(obj){
	        	 var flag = obj.flag;
	        	 if("success" == flag) {
	        		 layer.open({
	        			    type: 2,
	        			    title: "设置数据权限",
	        			    shade: 0.4,
	        			    area: ['100%','100%'],
	        			    content: "${ctx}" + "/rolemanage_toRoleDataPage.do?roleId="+roleId,
	        			    cancel: function(){
	        			        //右上角关闭回调
	        			   	 window.location.reload();
	        			   }
	        			});
	        	 } else {
	        		 alert("请先进行目录树授权");
	        	 }
	        }
	    })
	}
	//按钮授权
	function btnLicense(roleId) {
		var return_btn="";
		$.ajax({
			url:"${ctx}/rolemanage_searchRoleBtn.do",
			type:"post",
			async:false,
			cache: false,
			data:{"roleId":roleId},
			success:function(msg){
				return_btn=msg;
			},
			error:function(){
				alert("系统错误请重试");
			}	
		});
		var content="<dl class='checkBox' id='btn_manage'>"
        +"<dd>"+return_btn+"</dd></dl>";
		layer.open({
            type: 1,
            title: "按钮授权",
         	resize :true,
         	moveOut:true,
         	scrollbar:true,
            shade: 0.4,
         	zIndex: layer.zIndex, //重点1
  	        success: function(layero){
  	          layer.setTop(layero); //重点2
  	        },
            area: ['30%', '50%'],
            content: content,
            /* content: $("#btn_manage").html(), */
            btn:['授权','关闭'],
            yes: function(index){
            	 //当点击‘确定’按钮的时候，获取弹出层返回的值
            	 var objs = document.getElementsByTagName('input');
				 var btn_ids = '';
				 var n = 0;
				 debugger;
				 for(var i=0; i<objs.length; i++) {
				   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='item' && objs[i].checked==true ){
					   btn_ids += objs[i].value+",";
					  n ++;
				   }
				 }
				 btn_ids = btn_ids.substring(0, btn_ids.length-1);
                 $.ajax({
    				url:"${ctx}/rolemanage_saveRoleBtn.do",
    				type:"post",
    				async:false,
    				cache: false,
    				data:{"roleId":roleId,"btn_ids":btn_ids},
    				success:function(msg){
    					if(msg=="success"){
    						alert("授权成功");
    						window.location.reload();
    					}else{
    						alert("修改失败");
    					}
    				},
    				error:function(){
    					alert("系统错误请重试");
    				}	
    			});
            },
            cancel: function(){
                //右上角关闭回调
           	 window.location.reload();
           }
        });
	}
	
	
	function update(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='nameSpaceId' && objs[i].checked==true ){
			  ids += objs[i].value;
			  n ++;
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}else if(n != 1&& n!=0){
			alert("请选择一条数据");
			return;
		}
		 openPending("修改命名空间","/met_toNameSpaceEditPage.do?id="+ids);
	}
	</script>
	<script>
	(function ($) {
	 $.extend({
	  Request: function (m) {
	   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
	   return sValue ? sValue[1] : sValue;
	  },
	  UrlUpdateParams: function (url, name, value) {
	   var r = url;
	   if (r != null && r != 'undefined' && r != "") {
	    value = encodeURIComponent(value);
	    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
	    var tmp = name + "=" + value;
	    if (url.match(reg) != null) {
	     r = url.replace(eval(reg), tmp);
	    }
	    else {
	     if (url.match("[\?]")) {
	      r = url + "&" + tmp;
	     } else {
	      r = url + "?" + tmp;
	     }
	    }
	   }
	   return r;
	  }
	 
	 });
	})(jQuery);
	
	/* function demo(obj){
		if($(obj).prop('checked')){
			$(obj).attr("checked",false);
		}else{
			$(obj).attr("checked",true);
		}
		
	//	$(obj).attr("disabled","disabled");
	} */
	/* var content="<dl class='checkBox' id='btn_manage'><dt><input type='checkbox' id='checkAll'><label>全选</label>"
        +"<a href='javascript:;'>反选</a></dt>"
        +"<dd>"+return_btn+"</dd></dl>"; */
	</script>
	
</html>