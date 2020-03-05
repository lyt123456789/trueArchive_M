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
    		.mid::-webkit-scrollbar {/*滚动条整体样式*/
		        width: 5px;     /*高宽分别对应横竖滚动条的尺寸*/
		        height: 1px;
			}
			.mid::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
		        border-radius: 13px;
		        -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
		        background: #00BFFF;
			}
			.mid::-webkit-scrollbar-track {/*滚动条里面轨道*/
		        -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
		        border-radius: 13px;
		        background: #E0FFFF;
			}
	    	.detail {
	    		display:block!important;
	    	}
    	</style>
    </head>
    <body>
    	<div class="detail">
			<form method="post" id="zhform" name="zhform" class="tw-form">
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
				                <c:if test="${'add' eq aeFlag}">          			            
					                <tr>
					                    <td>
					                    	<select name="lkh1">
							                	<option value=""></option>
								 				<option value="(">(</option>
							                </select>
							            </td>
					                    <td>
					                    	<select name="column1">
							                	<option value=""></option>
								 				<c:forEach items="${tags}" var="tag" varStatus="state">
								 					<option value="${tag.id}">${tag.esIdentifier}</option>
										    	</c:forEach>	
							                </select>
							            </td>
					                    <td>
					                    	<select  name="com1">
								 				<option value="1">包含</option>
								 				<option value="2">等于</option>
								 				<option value="3">小于</option>
								 				<option value="4">小于等于</option>
								 				<option value="5">大于等于</option>
								 				<option value="6">大于</option>
								 				<option value="7">不等于</option>
								 				<option value="8">不包含</option>
							                </select>
						                </td>
					                    <td><input type="text"  id="keyWord1" name="keyWord1" value=""></td>
					                    <td>
					                    	<select name="rkh1">
							                	<option value=""></option>
								 				<option value=")">)</option>
							                </select>
							            </td>
					                    <td>
					                    	<select name="rela1">
					                    	 	<option value=""></option>
								 				<option value="AND">并且</option>
								 				<option value="OR">或者</option>
							                </select>
							            </td>
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
					            <c:if test="${'edit' eq aeFlag}">
					               	<c:forEach var="data" items='${jsonArray}'>
					               		<tr>
						                    <td>
						                    	<select name="lkh">
								                	<option value=""></option>
									 				<option value="(" <c:if test="${data.leftBrackets eq '('}">selected="selected"</c:if>>(</option>
								                </select>
								            </td>
						                    <td>	
						                    	<select name="column">
								                	<option value=""></option>
									 				<c:forEach items="${tags}" var="tag" varStatus="state">
									 					<option value="${tag.id}" <c:if test="${data.field eq tag.id}">selected="selected"</c:if>>${tag.esIdentifier}</option>
											    	</c:forEach>	
								                </select>
											</td>
											 <td>	
						                    	<select  name="com">
									 				<option value="1" <c:if test="${data.comparator eq '1'}">selected="selected"</c:if>>包含</option>
									 				<option value="2" <c:if test="${data.comparator eq '2'}">selected="selected"</c:if>>等于</option>
									 				<option value="3" <c:if test="${data.comparator eq '3'}">selected="selected"</c:if>>小于</option>
									 				<option value="4" <c:if test="${data.comparator eq '4'}">selected="selected"</c:if>>小于等于</option>
									 				<option value="5" <c:if test="${data.comparator eq '5'}">selected="selected"</c:if>>大于等于</option>
									 				<option value="6" <c:if test="${data.comparator eq '6'}">selected="selected"</c:if>>大于</option>
									 				<option value="7" <c:if test="${data.comparator eq '7'}">selected="selected"</c:if>>不等于</option>
									 				<option value="8" <c:if test="${data.comparator eq '8'}">selected="selected"</c:if>>不包含</option>
								                </select>
											</td>
											<td>
												<input type="text"  id="keyWord" name="keyWord" value="${data.inputValue}">
											</td>
											 <td>	
						                    	<select name="rkh">
								                	<option value=""></option>
									 				<option value=")" <c:if test="${data.rightBrackets eq ')'}">selected="selected"</c:if>>)</option>
								                </select>
											</td>
											 <td>	
						                    	<select name="rela">
						                    		<option value=""></option>
									 				<option value="AND" <c:if test="${data.relatives eq 'AND'}">selected="selected"</c:if>>并且</option>
									 				<option value="OR" <c:if test="${data.relatives eq 'OR'}">selected="selected"</c:if>>或者</option>
								                </select>
											</td>
											<td><button type="button" class="btn_add" onclick="addtr()"></button></td>
				                    		<td><button type="button" class="btn_del" onclick="deltr(this)"></button></td>
						                </tr>
					 				</c:forEach>
					 				<input type="hidden" id="cardId" value="${id }"/>
					           </c:if>
						</table>
			        </div>
			    </div>
			    <div style="text-align:center;">
			        <button type="button" class="btn_ok" onclick="getAllCondition();">保存</button>
			        <button type="button" class="btn_qx" onclick="back()">取消</button>
			    </div>
	    	</form>
	   	</div>
	</body>
    <script src="${ctx}/lib/jquery-3.2.1.js"></script>
	<script src="${ctx}/lib/popper.js"></script>
	<script type="text/javascript" src="${ctx}/layui2.5.1/layui/layui.js"></script>
	<script src="${ctx}/lib/bootstrap-material/js/bootstrap-material-design.js"></script>
    <script type="text/javascript">
	    function addtr() {
	        var tr="     <tr>\n" +
	            "                    <td><select><option value=''></option><option value='('>(</option></select></td>\n" +
	            "                    <td><select><option value=''></option>";
	            var tagstr = "${tagstr}";
	            var tags = tagstr.split(";");
	            for(var i=0;i<tags.length;i++){
	            	if(tags[i].split(",")[0]!=""&&tags[i].split(",")[0]!=null){
	            		tr+="<option value='"+tags[i].split(",")[0]+"'>"+tags[i].split(",")[1]+"</option>";
	            	}
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
	    
	    //获取设定条件
	    function getAllCondition(){
	    	var trList = $("#tab").find("tr");
	    	var conditionJson = [];
	    	var conditionShow = [];
	    	var conditionSql = "";
	    	for (var i=1;i<trList.length;i++) {
	    		var and = "and";
    	        var tdArr = trList.eq(i).find("td");
    	        var leftBrackets = tdArr.eq(0).find('select option:selected').val();//左括号
    	        var field = tdArr.eq(1).find('select option:selected').val();//字段
    	        var fieldText = tdArr.eq(1).find('select option:selected').text();
    	        var comparator = tdArr.eq(2).find('select option:selected').val();//比较符
    	        var comparatorText = tdArr.eq(2).find('select option:selected').text();
    	        var inputValue = tdArr.eq(3).find('input').val();//输入值
    	        var rightBrackets = tdArr.eq(4).find('select option:selected').val();//右括号
    	        var relatives = tdArr.eq(5).find('select option:selected').val();//关系符
    	        var realtivesText = tdArr.eq(5).find('select option:selected').text();
    	        if(isEmpty(field)) {
    	        	continue;
    	        }
    	        
    	        var json = {};
    	        json["leftBrackets"]=leftBrackets;
    	        json["field"]=field;
    	        json["comparator"]=comparator;
    	        json["inputValue"]=inputValue;
    	        json["rightBrackets"]=rightBrackets;
    	        json["relatives"]=relatives;
    	        conditionJson.push(json);
    	        
    	        var show = leftBrackets+"["+fieldText+"]"+" "+ comparatorText +" \""+inputValue+"\""+rightBrackets;
    	        conditionShow.push(show);
    	        if(isEmpty(realtivesText)) {
    	        	realtivesText = "";
    	        }
    	        conditionShow.push(realtivesText);
    	        
    	        var sqlCon = "";
    	        switch (comparator) {
    	        case "1":
    	        	sqlCon = " like '%"+inputValue+"%'";
    	            break;
    	        case "2":
    	        	sqlCon = " = '"+inputValue+"'";
    	            break;
    	        case "3":
    	        	sqlCon = " < '"+inputValue+"'";
    	            break;
    	        case "4":
    	        	sqlCon = " <= '"+inputValue+"'";
    	            break;
    	        case "5":
    	        	sqlCon = " >= '"+inputValue+"'";
    	            break;
    	        case "6":
    	        	sqlCon = " > '"+inputValue+"'";
    	            break;
    	        case "7":
    	        	sqlCon = " <> '"+inputValue+"'";
    	            break;
    	        case "8":
    	        	sqlCon = " not like '%"+inputValue+"%'";
    	            break;
    	        default: 
    	        	sqlCon = " is not null";
    	    	} 
    	        conditionSql += leftBrackets + "C" + field+sqlCon+rightBrackets+" " + relatives + " ";
    	    }
	    	var conditionJsons = {};
	    	conditionJsons["cds"]=conditionJson;
	    	var id="";
	    	if($("#cardId").length > 0) {
	    		id = $("#cardId").val();
	    	}
	        var obj = {
	        	"roleId":"${roleId}",//临时用户ID
	        	"treeNode":"${structureId}",//节点ID
	        	"dataIdChild":"${idchild}",//文档内容分类ID
	        	"dataFabric":"${dataFabric}",//文档内容分类名称
	        	"condition":JSON.stringify(conditionJsons),
	        	"conditionShow":JSON.stringify(conditionShow),
	        	"sqlCondition":conditionSql,
	        	"id":id
	        };
	        $.ajax({
				url:"${ctx}/rolemanage_saveRoleDataRange.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",//返回值类型
				traditional:true,
				data:obj,
				success:function(data){
					var flag = data.flag;
					if("success"==flag) {
						window.parent.location.reload(); // 父页面刷新
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.layer.close(index);
					} else if("cannot" == flag) {
						alert("抱歉，经系统验证，您所设定的条件无法进行查询活动！请重新设定查询条件。");
					} else {
						alert("保存失败");
					}
				},
				error:function(){
					alert("系统错误请重试");
				}	
			});
	    }
	    
	   	function back() {
	   		var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
			window.location.reload();
	   	}
	</script>
</html>
