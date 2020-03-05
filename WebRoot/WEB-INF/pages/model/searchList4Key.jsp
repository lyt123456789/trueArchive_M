<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>列表</title>
    <link rel="stylesheet" href="css/list.css?t=11">
    <script src="js/jquery-1.8.2.min.js"></script>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <style type="text/css">
    #high-search {
	    background: url("../img/high_search.png") no-repeat;
	    width: 1000px;
	    height: 250px;
	    position: absolute;
	    right: 10%;
	    z-index: 2000;
	    top: 50%;
	    display: none;
	    text-align: center;
	    background-color: white; 
	}
	lable{
		color: red;
	    font-weight: bold;
	}
    </style>
</head>
<body style="overflow: auto;">
<div class="search">
	<form name="list"  id="list" action="${ctx }/model_toModelTask4Key.do?type=1" method="post" style="display:inline-block;">
        	<input type="hidden" id="fatherId" name="fatherId" value="${fatherId}"/><!-- 左侧目录树的id -->
        	<input type="hidden" id="type" name="type" value="${type}"/><!-- 判断是从从左目录树点进来查询还是此页面的查询 -->
        	<input type="hidden" id="jydId" name="jydId" value="${jydId}"/><!-- 判断是否从借阅单进来的 -->
        	<input type="hidden" id="zzcdFlag" name="zzcdFlag" value="${zzcdFlag}"/>
		    <span>查询范围：</span>
		    <select id="structId" name="structId" onchange="searchData()">
		    	<c:forEach var="m" items='${jjtypelist}'>
		    		<c:set value="${ fn:split(m, ',') }" var="names" />
					<option value="${names[0]}" <c:if test="${structId ==names[0]}">selected="selected"</c:if>>${names[1]}</option>
				</c:forEach> 
			</select>
		    <input type="text"  id="keyWord" name="keyWord" value="${keyWord}">
		    <button type="button" class="btn_seargjc" onclick="searchData();"><img src="img/sear_ico.png">关键词检索</button>
		    <button type="button" class="btn_searzh" onclick="showSearch();"><img src="img/sear_ico.png">综合查询</button>
		    <c:if test="${!empty jydId}">
		    	<button type="button" class="btn_addpackage" onclick="addToJYK();"><img src="img/addpackage.png">加入借阅库</button>
		      	<button type="button" class="btn_formdetail" onclick="showJYD();"><img src="img/form_detail.png">借阅单明细</button>
		    </c:if>	 
		    <c:if test="${!empty djdId}">
		    	<button type="button" class="btn_addpackage" onclick="addToDJK();"><img src="img/addpackage.png">加入调卷库</button>
		    	<button type="button" class="btn_formdetail" onclick="showDJD();"><img src="img/form_detail.png">调卷单明细</button>
		    </c:if>	  
	</form>
	<div class="dark"></div>
	<div class="detail">
		<form method="post" id="zhform" name="zhform" action="${ctx }/model_toModelTask4Key.do?type=1" class="tw-form">
			<input type="hidden" id="fatherId" name="fatherId" value="${fatherId}"/>
       			<input type="hidden" id="zzcdFlag" name="zzcdFlag" value="${zzcdFlag}"/>
       			<input type="hidden" id="type" name="type" value="${type}"/>
       			<input type="hidden" id="row" name="row" value="${row}"/>
       			<input type="hidden" id="rowcolumn" name="rowcolumn" value="${rowcolumn}"/>
       			<input type="hidden" id="jydId" name="jydId" value="${jydId}"/><!-- 判断是否从借阅单进来的 -->
		 		<div class="top">综合检索</div>
				    <div class="mid">
				        <div class="info">
				        	<table cellspacing="0" cellpadding="0" border="0" id="tab">
				                <tr>
				                    <th width="75">左括号</th>
				                    <th width="120">字段</th>
				                    <th width="95">比较符</th>
				                    <th width="200">值</th>
				                    <th width="75">右括号</th>
				                    <th width="100">关系符</th>
				                    <th width="90">增加</th>
				                    <th width="95">删除</th>
				                </tr>
					               <c:if test="${count ne null}">
					               	<c:forEach var="c" items='${countlist}'>
					               		<c:set var="lkh" value="lkh${c}"></c:set>
					               		<c:set var="column" value="column${c}"></c:set>
					               		<c:set var="com" value="com${c}"></c:set>
					               		<c:set var="keyWordd" value="keyWord${c}"></c:set>
					               		<c:set var="rkh" value="rkh${c}"></c:set>
					               		<c:set var="rela" value="rela${c}"></c:set>
					               		<tr>
						                    <td>
						                    	<select name="lkh${c}">
								                	<option value=""></option>
									 				<option value="(" <c:if test="${columnMap[lkh] eq '('}">selected="selected"</c:if>>(</option>
								                </select>
								            </td>
						                    <td>	
						                    	<select name="column${c}">
								                	<option value=""></option>
									 				<c:forEach items="${tags}" var="tag" varStatus="state">
									 					<option value="${tag.id}" <c:if test="${columnMap[column] eq tag.id}">selected="selected"</c:if>>${tag.esIdentifier}</option>
											    	</c:forEach>	
								                </select>
											</td>
											 <td>	
						                    	<select  name="com${c}">
									 				<option value="1" <c:if test="${columnMap[com] eq '1'}">selected="selected"</c:if>>包含</option>
									 				<option value="2" <c:if test="${columnMap[com] eq '2'}">selected="selected"</c:if>>等于</option>
									 				<option value="3" <c:if test="${columnMap[com] eq '3'}">selected="selected"</c:if>>小于</option>
									 				<option value="4" <c:if test="${columnMap[com] eq '4'}">selected="selected"</c:if>>小于等于</option>
									 				<option value="5" <c:if test="${columnMap[com] eq '5'}">selected="selected"</c:if>>大于等于</option>
									 				<option value="6" <c:if test="${columnMap[com] eq '6'}">selected="selected"</c:if>>大于</option>
									 				<option value="7" <c:if test="${columnMap[com] eq '7'}">selected="selected"</c:if>>不等于</option>
									 				<option value="8" <c:if test="${columnMap[com] eq '8'}">selected="selected"</c:if>>不包含</option>
								                </select>
											</td>
											<td>
												<input type="text"  id="keyWord${c}" name="keyWord${c}" value="${columnMap[keyWordd]}">
											</td>
											 <td>	
						                    	<select name="rkh${c}">
								                	<option value=""></option>
									 				<option value=")" <c:if test="${columnMap[rkh] eq ')'}">selected="selected"</c:if>>)</option>
								                </select>
											</td>
											 <td>	
						                    	<select name="rela${c}">
						                    		<option value=""></option>
									 				<option value="AND" <c:if test="${columnMap[rela] eq 'AND'}">selected="selected"</c:if>>并且</option>
									 				<option value="OR" <c:if test="${columnMap[rela] eq 'OR'}">selected="selected"</c:if>>或者</option>
								                </select>
											</td>
											<td><button type="button" class="btn_add" onclick="addtr()"></button></td>
				                    		<td><button type="button" class="btn_del" onclick="deltr(this)"></button></td>
						                </tr>
					 				</c:forEach>
					               </c:if>
               				<c:if test="${count eq null}">          			            
				                <tr>
				                    <td><select name="lkh1">
						                	<option value=""></option>
							 				<option value="(">(</option>
						                </select></td>
				                    <td><select name="column1">
						                	<option value=""></option>
							 				<c:forEach items="${tags}" var="tag" varStatus="state">
							 					<option value="${tag.id}">${tag.esIdentifier}</option>
									    	</c:forEach>	
						                </select></td>
				                    <td><select  name="com1">
							 				<option value="1">包含</option>
							 				<option value="2">等于</option>
							 				<option value="3">小于</option>
							 				<option value="4">小于等于</option>
							 				<option value="5">大于等于</option>
							 				<option value="6">大于</option>
							 				<option value="7">不等于</option>
							 				<option value="8">不包含</option>
						                </select></td>
				                    <td><input type="text"  id="keyWord1" name="keyWord1" value=""></td>
				                    <td><select name="rkh1">
						                	<option value=""></option>
							 				<option value=")">)</option>
						                </select></td>
				                    <td><select name="rela1">
				                    		<option value=""></option>
							 				<option value="AND">并且</option>
							 				<option value="OR">或者</option>
						                </select></td>
				                    <td><button type="button" class="btn_add" onclick="addtr()"></button></td>
				                    <td><button type="button" class="btn_del" onclick="deltr(this)"></button></td>
				                </tr>
				                 <tr>
				                    <td><select name="lkh2">
						                	<option value=""></option>
							 				<option value="(">(</option>
						                </select></td>
				                    <td><select name="column2">
						                	<option value=""></option>
							 				<c:forEach items="${tags}" var="tag" varStatus="state">
							 					<option value="${tag.id}">${tag.esIdentifier}</option>
									    	</c:forEach>	
						                </select></td>
				                    <td><select  name="com2">
							 				<option value="1">包含</option>
							 				<option value="2">等于</option>
							 				<option value="3">小于</option>
							 				<option value="4">小于等于</option>
							 				<option value="5">大于等于</option>
							 				<option value="6">大于</option>
							 				<option value="7">不等于</option>
							 				<option value="8">不包含</option>
						                </select></td>
				                    <td><input type="text"  id="keyWord1" name="keyWord2" value=""></td>
				                    <td><select name="rkh2">
						                	<option value=""></option>
							 				<option value=")">)</option>
						                </select></td>
				                    <td><select name="rela2">
				                    		<option value=""></option>
							 				<option value="AND">并且</option>
							 				<option value="OR">或者</option>
						                </select></td>
				                    <td><button type="button" class="btn_add" onclick="addtr()"></button></td>
				                    <td><button type="button" class="btn_del" onclick="deltr(this)"></button></td>
				                </tr>
				                 <tr>
				                    <td><select name="lkh3">
						                	<option value=""></option>
							 				<option value="(">(</option>
						                </select></td>
				                    <td><select name="column3">
						                	<option value=""></option>
							 				<c:forEach items="${tags}" var="tag" varStatus="state">
							 					<option value="${tag.id}">${tag.esIdentifier}</option>
									    	</c:forEach>	
						                </select></td>
				                    <td><select  name="com3">
							 				<option value="1">包含</option>
							 				<option value="2">等于</option>
							 				<option value="3">小于</option>
							 				<option value="4">小于等于</option>
							 				<option value="5">大于等于</option>
							 				<option value="6">大于</option>
							 				<option value="7">不等于</option>
							 				<option value="8">不包含</option>
						                </select></td>
				                    <td><input type="text"  id="keyWord3" name="keyWord3" value=""></td>
				                    <td><select name="rkh3">
						                	<option value=""></option>
							 				<option value=")">)</option>
						                </select></td>
				                    <td><select name="rela3">
				                    		<option value=""></option>
							 				<option value="AND">并且</option>
							 				<option value="OR">或者</option>
						                </select></td>
				                    <td><button type="button" class="btn_add" onclick="addtr()"></button></td>
				                    <td><button type="button" class="btn_del" onclick="deltr(this)"></button></td>
				                </tr>
					</c:if>
					</table>
			        </div>
			    </div>
			    <div style="text-align: center;">
			        <button type="button" class="btn_ok" onclick="searchDataOfZH();">检索</button>
			        <button type="button" class="btn_qx">取消</button>
			    </div>
    	</form>
   	</div>
</div>
<div class="clear"></div>
<div class="table">
<div style="overflow: auto;">
    <table cellspacing="0" cellpadding="0">
    	<c:if test="${!empty tags}">
	    	<tr class="fr_tr">
	    		<c:if test="${!empty jydId || !empty djdId}">
	        		<td style="width:25px;"><div class="checkbox"><input type="checkbox" name="checkboxAll" onclick="chooseAll(this);"/></div></td>
	        	</c:if>
	        	<td>操作</td>
	        	<c:forEach items="${tags}" var="tag" varStatus="state">
		    		<td>${tag.esIdentifier}</td>
		    	</c:forEach>	
	        </tr>
    	</c:if>
        
        <c:forEach items="${list}" var="item" varStatus="state">
        	<c:set var="id" value="${item['id']}"></c:set>
        	<tr>
        		<c:if test="${!empty jydId || !empty djdId}">
	        		<td><div class="checkbox"><input type="checkbox" name="checkbox" value="${id}"/></div></td>
	        	</c:if>
	  			 <td>
	  			 	<%-- <c:if test="${'3' eq structType}">
	  			 		<button type="button" class="btn_yw"></button>
	  			 	</c:if> --%>
	                <button type="button" class="btn_xq" onclick="showDetail('${id}')"></button>
	            </td>
	  			<c:forEach items="${tags}" var="tag" varStatus="state">
	  				<c:set var="key" value="C${tag.id}"></c:set>
		    		<td align="center">${item[key]}</td>
		    	</c:forEach>
			</tr>
    	</c:forEach>
    </table>
</div>
</div>
<div class="wf-list-ft" id="pagingWrap">
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		if("${searchType}"=="zh"){
			var row = $("#tab").find("tr").length;
			skipUrl="<%=request.getContextPath()%>"+"/model_toModelTask4Key.do?structId=${structId}&row="+row+"&searchType=zh";//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('zhform');	
		}else{
			skipUrl="<%=request.getContextPath()%>"+"/model_toModelTask4Key.do?structId=${structId}";//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('list');	
		}								//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		setTimeout(function(){
			var list2 = document.getElementsByClassName('wf-input-datepick');
			/* for(var i=0;i<2;i++){
				list2[i].style.width = '125px';
			}
			for(var i=2;i<4;i++){
				list2[i].style.width = '123px';
			} */
		}, 500);
	};
</script>
<script type="text/javascript">
$(".btn_qx").click(function () {
    $(".dark").hide();
    $(".detail").hide();
})
	function searchData(){
		var fatherId = document.getElementById('fatherId').value;
		if(fatherId==""||fatherId==null){
			alert("请选择左侧目录");
			return;
		}
		document.getElementById('list').submit();
	}
	function searchDataOfZH(){		
		var row = $("#tab").find("tr").length;
		document.getElementById('row').value=row;
		var action = document.getElementById('zhform').action;
		document.getElementById('zhform').action=action+"&structId="+document.getElementById('structId').value+"&searchType=zh";
		document.getElementById('zhform').submit();
	}
	var hopen=false;
	function showSearch(){
		var fatherId = document.getElementById('fatherId').value;
		if(fatherId==""||fatherId==null){
			alert("请选择左侧目录");
			return;
		}
	/* 	if(!hopen){ */
			//document.getElementById("high-search").style.display="block";
			$(".dark").show();
			$(".detail").show();
			hopen=true;
		/* }
		else{
			//document.getElementById("high-search").style.display="none";
			$(".dark").hide();
			$(".detail").hide();
			hopen=false;
		} */
	}
	 function addtr() {
	        var tr="     <tr>\n" +
	            "                    <td><select><option value=''></option><option value='('>(</option></select></td>\n" +
	            "                    <td><select><option value=''></option>";
	            var tagstr = "${tagstr}";
	            var tags = tagstr.split(";");
	            for(var i=0;i<tags.length;i++){
	            	tr+="<option value='"+tags[i].split(",")[0]+"'>"+tags[i].split(",")[1]+"</option>";
	            }
	    	tr+="                    </select></td>\n" +
	            "                    <td><select><option value='1'>包含</option><option value='2'>等于</option><option value='3'>小于</option><option value='4'>小于等于</option>"+
 				"                                <option value='5'>大于等于</option><option value='6'>大于</option><option value='7'>不等于</option><option value='8'>不包含</option>"+
 				"                        </select></td>\n" +
	            "                    <td><input type='text'></td>\n" +
	            "                    <td><select><option value=''></option><option value=')'>)</option></select></td>\n" +
	            "                    <td><select><option value=''></option><option value='AND'>并且</option><option value='OR'>或者</option></select></td>\n" +
	            "                    <td><button type=\"button\" class=\"btn_add\" onclick=\"addtr()\"></button></td>\n" +
	            "                    <td><button type=\"button\" class=\"btn_del\" onclick=\"deltr(this)\"></button></td>\n" +
	            "                </tr>";
	        $(".info table").append(tr);
	        initRows(document.getElementById('tab'));

	    }
	 
	    function deltr(tr) {
	        var tab = document.getElementById('tab');
	        var trobj = tr.parentNode.parentNode;
	        if(tab.rows.length>2){
	            //tr.parentNode，指的是，table对象;移除子节点;
	            trobj.parentNode.removeChild(trobj);
	        }
	        initRows(tab);
	    }
	    function initRows(tab){
	        var tabRows = tab.rows;
	        for(var i = 0;i<tabRows.length;i++){
                tab.rows[i].cells[0].firstChild.name="lkh"+i;
                tab.rows[i].cells[1].firstChild.name="column"+i;
                tab.rows[i].cells[2].firstChild.name="com"+i;
                tab.rows[i].cells[3].firstChild.name="keyWord"+i;
                tab.rows[i].cells[4].firstChild.name="rkh"+i;
                tab.rows[i].cells[5].firstChild.name="rela"+i;
	       }
	   }

	function showDetail(id){
		var url = '${ctx}/model_showDetail.do?id='+id+'&structId=${structId}&fatherId=${fatherId}';
		var index = parent.layer.open({
			title:"浏览数据",
		    type: 2,
		    content: url,
		    area: ['300px', '195px']
		});
		parent.layer.full(index);
	}
	
	function chooseAll(obj){
		var choose = document.getElementsByName("checkbox");
		if(obj.checked){
			for(var i=0;i<choose.length;i++){
				choose[i].checked=true;
			}
		}else{
			for(var i=0;i<choose.length;i++){
				choose[i].checked=false;
			}
		}
	}
	function addToJYK(){
		var jydId = parent.document.getElementById('jydId').value;
		if(jydId==""){
			alert("请选择借阅单！");
			return;
		}
		var ids = "";
		var choose = document.getElementsByName("checkbox");
		for(var i=0;i<choose.length;i++){
			if(choose[i].checked){
				ids+=choose[i].value+",";
			}
		}
		if(ids!=""){
			ids=ids.substring(0, ids.length-1);
		}
		if(ids==""){
			alert("请选择数据！");
			return;
		}
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{
	        	"jydId":jydId,
	        	"ids":ids,
	        	"fatherId":"${fatherId}",
	        	"structId":"${structId}",
	        	"type":"1"
	        },
	        url:"${ctx}/model_addToJYK.do",
	        success:function(text){
	        	if(text=="success"){
	        		 alert("加入成功！");
	        	}else{
	        		 alert("加入失败！");
	        	}	
	        }
	    });
	}
	
	function addToDJK(){
		var djdId = parent.document.getElementById('djdId').value;
		if(djdId==""){
			alert("请选择调卷单！");
			return;
		}
		var ids = "";
		var choose = document.getElementsByName("checkbox");
		for(var i=0;i<choose.length;i++){
			if(choose[i].checked){
				ids+=choose[i].value+",";
			}
		}
		if(ids!=""){
			ids=ids.substring(0, ids.length-1);
		}
		if(ids==""){
			alert("请选择数据！");
			return;
		}
		
		//先检验是否能够调阅
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"djdId":djdId,"ids":ids,"structId":"${structId}","type":"1"},
	        url:"${ctx}/model_checkDJK.do",
	        success:function(text){
	        	if(text=="false"){
	        		alert("校验失败，请联系管理员！");	
	        	}else if(text==""){
	        		$.ajax({
	        	        async:true,//是否异步
	        	        type:"POST",//请求类型post\get
	        	        cache:false,//是否使用缓存
	        	        dataType:"text",//返回值类型
	        	        data:{
	        	        	"djdId":djdId,
	        	        	"ids":ids,
	        	        	"fatherId":"${fatherId}",
	        	        	"structId":"${structId}",
	        	        	"type":"1"
	        	        },
	        	        url:"${ctx}/model_addToDJK.do",
	        	        success:function(text){
	        	        	if(text=="success"){
	        	        		 alert("加入成功！");
	        	        	}else{
	        	        		 alert("加入失败！");
	        	        	}	
	        	        }
	        	    });
	        	}else if(text!=""){
	        		alert(text+",已在调卷中，无法调卷！");	
	        	}	
	        }
	    });
	}
	
	function showJYD(){
		var url = '${ctx}/using_showUsingForm.do?vc_table=1&type=&id=${jydId}&zzcdFlag=${zzcdFlag}';
		var index = parent.layer.open({
			title:"浏览数据",
		    type: 2,
		    content: url,
		    area: ['100%', '100%']
		});
	}
	function showDJD(){
		parent.layer.open({
            type: 2,
            title: "调卷单明细",
            shadeClose: true,
            shade: 0.4,
            area: ['100%', '100%'],
            content: "${ctx}/using_showTransferForm.do?id=${djdId}&statusSe=${statusSe}"
        });
	}
</script>
</html>