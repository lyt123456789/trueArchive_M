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
	
    </style>
</head>
<body style="overflow: auto;">
	<div class="zhcx">
		<form method="post" id="zhform" name="zhform" class="tw-form">
       			<input type="hidden" id="row" name="row" value="${row}"/>
       			<input type="hidden" id="rowcolumn" name="rowcolumn" value="${rowcolumn}"/>
       			<input type="hidden" id="structureId" name="structureId" value="${structureId}"/>
       			<input type="hidden" id="zhcxCondition" name="zhcxCondition" value="${zhcxCondition}"/>
       			<input type="hidden" id="zhcxConditionSql" name="zhcxConditionSql" value="${zhcxConditionSql}"/>
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
					               <c:if test="${row ne null}">
					               	<c:forEach var="c"  begin="1" end="${row}">
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
               				<c:if test="${row eq null}">          			            
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
			  <!--   <div style="text-align: center;">
			        <button type="button" class="btn_ok" onclick="searchDataOfZH();">检索</button>
			        <button type="button" class="btn_qx">取消</button>
			    </div> -->
    	</form>
   	</div>

</body>
<script type="text/javascript">

	function checkZhcxCondition(){		
		var row = $("#tab").find("tr").length;
		document.getElementById('row').value=row;
		$.ajax({
			  async:false,//是否异步
	          type: "POST",//方法类型
	          dataType: "text",//预期服务器返回的数据类型
	          url: "${ctx}/dataManage_checkZhcxCondition.do" ,//url
	          data:  $("#zhform").serialize(),
	          success: function (result) {
	        	  var obj = eval('(' + result + ')');
	        	  if(obj.flag=="success"){
	        		  document.getElementById("zhcxCondition").value=obj.msg;
	        		  document.getElementById("zhcxConditionSql").value=obj.sql;
	        	  }else{
	        		  top.layer.msg(obj.msg);
	        	  }
	          },
	          error : function() {
	        	  top.layer.msg("异常");
	          }
	      });
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
</script>
</html>