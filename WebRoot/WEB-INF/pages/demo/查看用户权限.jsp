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
	    <style type="text/css">
	    .layui-tab {
		    margin: 0px 0;
		    text-align: left!important;
		}
	    </style>
	</head>
	<body>
		<div class="layui-tab layui-tab-brief" lay-filter="statistics">
			  <%-- <ul class="layui-tab-title">
			  		<c:if test="${'project' eq esType}">
			  			<li lay-id="project" class="layui-this">项目级</li>
			  		</c:if>
			  		<c:if test="${'project' eq esType || 'file' eq esType}">
			  			<li lay-id="file" <c:if test="${'file' eq esType}">class="layui-this"</c:if>>案卷级</li>
			  		</c:if>
			  		<c:if test="${'project' eq esType || 'file' eq esType || 'innerFile' eq esType }">
			  			<li lay-id="innerFile" <c:if test="${'innerFile' eq esType}">class="layui-this"</c:if>>卷内级</li>
			  		</c:if >
				  	<li lay-id="document" >电子文件级</li>
			  </ul> --%>
			  <ul class="layui-tab-title">
			  			<li lay-id="project" class="layui-this">用户权限</li>
			  			<li lay-id="file" class="layui-this">库权限</li>
			  			<!-- <li lay-id="innerFile" class="layui-this">设置报表</li>
				  	    <li lay-id="document" >设置权限</li>
				  	    <li lay-id="document" >临时用户</li> -->
			  </ul>
		</div>
		<div style="height:590px;overflow:auto;">
			<%-- <iframe id="tabIframe" name="statisticsIframe" width="100%" height="99%" src="${ctx}/str_toSetModelTagsEditPage.do?modelId=${modelId}&esType=${esType}&onlyLook=${onlyLook}" frameborder="0"></iframe> --%>
		    <div class="wf-layout">
	          <div class="wf-list-top">
					<div class="wf-search-bar">
						<form name="lendLingList"  id="lendLingList" action="${ctx }/str_toSetModelTagsEditPage.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
						    <input type="hidden" id="esType" name="esType" value="${esType}"/>
						    <input type="hidden" id="modelId" name="modelId" value="${modelId}"/>
						    <c:if test="${'1' ne onlyLook}">
						    	<div class="wf-top-tool">
						    	<a class="wf-btn" onclick="javascript:add();" target="_self">
									<i class="wf-icon-search"></i> 查看角色
								</a>
						    	<select  class="wf-form-select" id="docnumbig" name="parentid">
			                		<option value="">启用</option>
			     					<option value="DF866F12">未启用</option>
			     					<option value="B632D4D3">全部</option>
                                </select>
						    	<!-- <input type="radio" autocomplete="off" id="usingForm" name="usingForm" class=" x-form-radio x-form-field x-hidden" tabindex="0" value="ess_using_form" checked="">
						    	<label for="usingForm" class="x-form-cb-label" id="ext-gen1996">利用单&nbsp;&nbsp;</label>
						    	<input type="radio" autocomplete="off" id="usingForm" name="usingForm" class=" x-form-radio x-form-field x-hidden" tabindex="0" value="ess_using_form" checked="">
						    	<label for="usingForm" class="x-form-cb-label" id="ext-gen1996">利用库&nbsp;&nbsp;</label>
								<a class="wf-btn" onclick="javascript:add();" target="_self">
									<i class="wf-icon-plus-circle" style="display:inline-block;"></i> 添加
								</a>
								<a class="wf-btn" onclick="javascript:add();" target="_self">
									<i class="wf-icon-pencil"></i> 编辑
								</a>
								<a  class="wf-btn-danger del" onclick="javascript:del();" target="_self">
									<i class="wf-icon-trash" style="display:inline-block;"></i> 删除
								</a>
								<a  class="wf-btn-danger del" onclick="javascript:del();" target="_self">
									<i class="wf-icon-pencil" style="display:inline-block;"></i> 扫描设置
								</a> -->
								<%-- <a  class="wf-btn-primary  del" onclick="javascript:saveAdd();" target="_self">
									<i class="wf-icon-pencil" style="display:inline-block;"></i> 保存新增
								</a>
								<a  class="wf-btn-primary  del" onclick="javascript:saveUpdate();" target="_self">
									<i class="wf-icon-pencil" style="display:inline-block;"></i> 保存修改
								</a>
								<a  class="wf-btn-primary  del" onclick="javascript:matchMetaData('${modelId}','${esType}');" target="_self">
									<i class="wf-icon-pencil" style="display:inline-block;"></i> 引用元数据
								</a>
								<a  class="wf-btn-primary  del" onclick="javascript:void(0);" target="_self">
									<i class="wf-icon-pencil" style="display:inline-block;"></i> 导出
								</a>
								<a  class="wf-btn-primary  del" onclick="javascript:void(0);" target="_self">
									<i class="wf-icon-pencil" style="display:inline-block;"></i> 导入
								</a> --%>
							</div>
						    </c:if>
						    
			 	        	<%-- <label class="wf-form-label" for="">字段名：</label>
			                <input type="text" class="wf-form-text w-auto-10" id="vc_name" name="vc_name" style="width: 90px" value="${vc_name}" placeholder="输入关键字">
				            <button class="wf-btn-primary" type="button" onclick="checkForm('2');">
				                <i class="wf-icon-search"></i> 搜索
				            </button> --%>
							 </form>
					</div>
	           </div>
				<div class="wf-list-wrap" style="height:490px">
					<div class="loading"></div><!--2017-11-10-->
					<form id="thisForm" method="POST" name="thisForm" action="" >
						<table class="wf-fixtable" id="dataTable" layoutH="140"  style="width: 100%;">
							<thead>
				    	  		<th width="3%"></th>
				    	  		<th align="center" width="8%">姓名</th>
			                    <th align="center" width="10%">用户名</th>
			                    <th align="center" width="10%">机构</th>
								<th align="center" width="10%">手机</th>
								<th align="center" width="8%">E-mail</th>
			                   	<th align="center" width="8%">办公电话</th>
			                   	<th align="center" width="8%">家庭电话</th>                  
			                   	<!-- <th align="center" width="8%">是否显示</th>
			                   	<th align="center" width="8%">是否为系统字段</th>  -->              
			                    <c:forEach items="${list}" var="item" varStatus="status">
			                   		<input type="hidden" id="old_filedName_${item.id }" value="${item.tagName}"/>
			                    	<tr class="istrue"  id = "${item.id }">
			                    		<td align="center" itemid="${item.id}">
			                    			<input type="checkbox" name="selid" class="${item.id}" id="${item.id}"  value="${item.id}"  >
			                    		</td>
			                    		<td align="center"  class="td" onclick="addCheck('${item.id}',1,'inp')"><span>${item.viewOrder}</span><input class="inp  ${item.id}"  value="${item.viewOrder}"></input></td>
			                    		<td align="center"  class="td" onclick="addCheck('${item.id}',2,'inp')"><span>${item.tagName}</span><input class="inp  ${item.id}"  value="${item.tagName}" dataType="tagName"></input></td>
			                    		<td align="center"  class="td" onclick="chooseMateData_td('${item.id}')"><span>${item.metaDataFullName}</span><input class="inp ${item.id}"   value="${item.metaDataFullName}" ><input type='hidden' class="${item.id}" value="${item.id_MetaData}"/></input></td>
			                    		<td align="center"  class="td" onclick="addCheck('${item.id}',4,'inp')"><span>${item.tagType}</span>
			                    			<select class="inp  ${item.id}" >
			                    				<option value="文本类型" <c:if test="${'文本类型' eq item.tagType }">selected="selected"</c:if>>文本类型</option>
			                    				<option value="大文本型" <c:if test="${'大文本型' eq item.tagType }">selected="selected"</c:if>>大文本型</option>
			                    				<option value="数值类型" <c:if test="${'数值类型' eq item.tagType }">selected="selected"</c:if>>数值类型</option>
			                    				<option value="浮点类型" <c:if test="${'浮点类型' eq item.tagType }">selected="selected"</c:if>>浮点类型</option>
			                    			</select>
			                    		</td>
			                    		<td align="center"  class="td" onclick="addCheck('${item.id}',5,'inp')"><span>${item.tagDesc}</span><input class="inp  ${item.id}"  value="${item.tagDesc}"></input></td>
			                    		<td align="center"  class="td" onclick="addCheck('${item.id}',6,'inp')"><span>${item.esLength}</span><input class="inp  ${item.id}"   value="${item.esLength}" dataType="esLength"></input></td>
			                    		<td align="center"  class="td" onclick="addCheck('${item.id}',7,'inp')"><span>${item.esDoLength}</span><input class="inp  ${item.id}" value="${item.esDoLength}" dataType="esDoLength"></input></td>
			                    		
			                    		<td align="center"  class="td" onclick="addCheck('${item.id}',8,'box')"><input class="box ${item.id}" onchange='checkchange(this)' type="checkbox"  name="istrue"  value="${item.esIsEdit}" <c:if test="${item.esIsEdit eq '1'}">checked="checked"</c:if>  ></td>
			                    		<td align="center"  class="td" onclick="addCheck('${item.id}',9,'box')"><input class="box ${item.id}" onchange='checkchange(this)' type="checkbox"  name="istrue"  value="${item.esIsNotNull}" <c:if test="${item.esIsNotNull eq '1'}">checked="checked"</c:if>  ></td>	
			                    	</tr>
			                    </c:forEach>
			                    
			                    <tr>
					    			<td align="center" >
										<input type="checkbox"  name="checkbox"  value="11" class="check_view_state" id="11" >
										<label for="11"></label>
									</td>
									<td align="center"  title ="" >administratoradmin</td>
									<td align="center"  title ="" >admin</td>
									<td align="center"  title ="" >organ1</td>
									<td align="center"  title ="" >0</td>
									<td align="center"  title ="" >services@flyingsoft.cn</td>
									<td align="center"  title ="" ></td>
									<td align="center"  title ="" ></td>
									<!-- <td align="center"  title ="" ></td>
									<td align="center"  title ="" ></td> -->
					    		 </tr>
			                 </thead>
						</table>
					</form>
				  </div>
				<div class="wf-list-ft" id="pagingWrap"></div>
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
			var esType = "";
			if(tabFlag == "project") {
				esType="project";
			} else if(tabFlag == "file") {
				esType="file";
			} else if(tabFlag == "innerFile") {
				esType="innerFile";
			} else if(tabFlag == "document") {
				esType="document";
			}
			$("#tabIframe").attr("src","${ctx}/str_toSetModelTagsEditPage.do?modelId=${modelId}&onlyLook=${onlyLook}&esType="+esType);
			layer.close(index);
		});
	});
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
	</script>
</html>