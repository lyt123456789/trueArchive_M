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
		 label {
            background: none no-repeat;
		    width: auto;
		    height: 15px;
		    border: 0;
        }
        
        .search input {
            width: auto;
            text-indent: 5px;
        }
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="list"  id="list" action="" method="post" style="display:inline-block;width: 100%;">
				<div class="search">
		    	    <button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-plus"></i>添加
				    </button>
			    	<!-- <button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-file-zip-o"></i>查看
				    </button> -->
    				<button type="button" onclick="javascript:del();" target="_self" class="btn btn-danger" style="float:left">
    					<i class="fa fa-trash-o fa-lg"></i>删除
    				</button>
    				<!-- <button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-files-o"></i>复制
				    </button>
				    <button type="button" id="addTemplate" class="btn btn-add" style="float:left">
				    		<i class="fa fa-window-restore"></i>批量追加功能权限
				    </button> -->
				    <!-- <select style="float:left" class="wf-form-select" id="docnumbig" name="parentid">
                		<option value="">全部</option>
     					<option value="DF866F12">系统管理类</option>
     					<option value="B632D4D3">功能管理类</option>
                    </select>
                    <input type="checkbox" autocomplete="off" class="btn btn-write" style="float:left"  tabindex="0">
	    				<label for="collkeyword_showRelevance" class="x-form-cb-label" style="float:left" id="ext-gen1098">显示子机构角色</label>
	    			 -->	
    				<%-- <div class="fr">
        				<input type="text" id="estitle" name="esTitle" value="${esTitle }" placeholder="">
        				<button type="submit" class="btn_seargjc" style="width: 85px;margin-right: 20px;">
        					<img src="${ctx}/img/sear_ico.png"style="vertical-align: middle;margin-right: 5px;">搜索
        				</button>
    				</div> --%>
	            </div>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
			<thead>
				<tr>
				    <th width="2%"></th>
	    	  		<th width="3%"></th>
	    	  		<th align="center" width="5%">Id</th>
	    	  		<th align="center" width="6%">启用</th>
	    	  		<th align="center" width="7%">测试连接</th>
	    	  		<th align="center" width="8%">传输协议</th>
	    	  		<th align="center" width="8%">服务器别名</th>
	    	  		<th align="center" width="10%">服务器Ip</th>
	    	  		<th align="center" width="8%">用户</th>
	    	  		<th align="center" width="8%">虚拟路径</th>
	    	  		<th align="center" width="8%">文件访问角色</th>
	    	  		<th align="center" width="12%">角色描述</th>
	    	  		<th align="center" width="6%">端口号</th>
	    	  		<th align="center" width="8%">服务的端口号</th>
	    	  		<!-- <th align="center" width="12%">服务的Url</th>
	    	  		<th align="center" width="8%">绝对路径</th> -->
                </tr>
	    	</thead>
	    	<c:forEach items="${list}" var="data" varStatus="status">
	    		<tr>
	    			<td align="center" >
						<input type="checkbox"  name="nameSpaceId"  value="${data.id}" class="check_view_state" id="${data.id}" style="display: none;">
						<label for="${data.id}"></label>
					</td>
					<td align="center"  title ="${data.esCreator}" >${data.esCreator }</td>
					<td align="center"  title ="${data.esTitle}" >${data.esTitle}</td>
					<td align="center"  title ="${data.esUrl}" >${data.esUrl}</td>
					<td align="center"  title ="${data.esDescription}" >${data.esDescription}</td>
					<td align="center"  title ="${data.esIdentifier}" >${data.esIdentifier}</td>
					<td align="center"  title ="${data.esDate}" >${data.esDate}</td>
					<td align="center"  title ="${data.esVersion}" >${data.esVersion}</td>
	    		</tr>
	    	</c:forEach>
	    	
	    	<tr>
	    	    <td align="center"  title ="" ></td>
    			<td align="center" >
					<input type="checkbox"  name="checkbox"  value="11" class="check_view_state" id="11" style="display: none;">
					<label for="11"></label>
				</td>
				<td align="center"  title ="" >8</td>
				<td align="center"  title ="" >是</td>
				<td align="center"  title ="" ></td>
				<td align="center"  title ="" >FTP</td>
				<td align="center"  title ="" >fileServer</td>
				<td align="center"  title ="" >32.142.11.212</td>
				<td align="center"  title ="" >root</td>
				<td align="center"  title ="" >data</td>
				<td align="center"  title ="" >fileAccess</td>
				<td align="center"  title ="" >fileAccess可以访问电子文件的角色</td>
				<td align="center"  title ="" >21</td>
				<td align="center"  title ="" >8080</td>
				<!-- <td align="center"  title ="" >http://32.142.11.212:8080/</td>
				<td align="center"  title ="" >D:/file</td> -->
    		</tr>
		</table>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>


</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/demos_toJsglJsp.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('lendLingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	
	
	
	function del(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		var n = 0;
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='checkbox' && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  n++;
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		} else if(n != 1&& n!=0){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		if(confirm('确定删除所选数据吗')){
			$.ajax({
				url:"${ctx}/met_checkNameSpaceDelete.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",
				data:{"id":ids},
				success:function(data){
					var sfv = data.flag;
					if(sfv=="delete"){
						// 验证
						$.ajax({
					        async:true,//是否异步
					        type:"POST",//请求类型post\get
					        cache:false,//是否使用缓存
					        dataType:"json",
					        data:{"ids":ids},
					        url:"${ctx}/met_deleteNameSpace.do",
					        success:function(data){
					        	 var sfv = data.flag;
									if(sfv=="yes"){
										window.location.reload();
									}else{
										alert("删除失败");
									}
					        }
					    })
					}else{
						alert("不可删除");
					}
				},
				error:function(){
					alert("系统错误请重试");
				}	
			});
		}
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
	
	$(function(){
        //$('table tr:not(:first)').remove();
        var len = $('table tr').length;
        for(var i = 1;i<len;i++){
            $('table tr:eq('+i+') td:first').text(i);
        }
            
    });
	</script>
</html>