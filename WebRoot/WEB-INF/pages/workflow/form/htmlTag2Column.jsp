<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>目录</title>
		<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/headerindex.jsp"%>
		<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
		<link rel="stylesheet" href="${ctx}/assets/themes/default/css/style.css" type="text/css" />
		<style type="text/css">
			* {
				text-align: center;
				margin: 0px;
				margin: auto;
			}
			.tbl{
				width: 99%;
				margin: auto;
				border-collapse: collapse;
				border: 0px;
			}
			.tbl td{
				border: 0px;
			}
			.tbl_in{
				font-size:12px;
				width: 100%;
				margin: auto;
				border-collapse: collapse;
				border: solid #ccccc;
				border-width: 1px 0 0 1px;
			}
			.tbl_in td{
				border: solid #ccccc;
				border-width: 0 1px 1px 0;
			}
			.input_none{
				width: 99%;
				border: 0px;
				text-align: left;
				background-color: transparent;
			}
			table.list thead tr th, table.list thead tr td {background: #f0f7fc;font-size: 12px;padding: 8px 5px;vertical-align: middle;line-height: 1.5;text-align:center;font-weight:normal;}
</style>
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${ctx}/js/transtotrue.js?type=2016"></script>
</head> 
<base target="_self"/>   
<body>
	<div class="tw-layout" style="display:none" id="hideDiv">
		<form id="commentform" method="post" action="" class="wf-form wf-form-horizontal-lg">
			<input type="hidden" id="columnId" name="id">
			<input type="hidden" id="id_u" name="id_u">
			<table class="tw-table tw-table-form">
                <colgroup>
                    <col  width="30%" />
                    <col />
                </colgroup>
                <tr>
                    <th>关联必填的字段：</th>
                    <td><input type="text" class="tw-form-text" id="associatedColumns" name="associatedColumns"/></td>
                </tr>
                <tr>
                    <th>正则表达式：</th>
                    <td><input type="text" class="tw-form-text" id="regularExpression" name="regularExpression"/></td>
                </tr>
                <tr>
                    <th>正则表达式含义：</th>
                    <td><input type="text" class="tw-form-text" id="regularMeanings" name="regularMeanings"/></td>
                </tr>
                <tr>
                    <th>值生成公式：</th>
                    <td><input type="text" class="tw-form-text" id="generationMode" name="generationMode"/></td>
                </tr>
                <tr>
                    <th>关联字段名称：</th>
                    <td><input type="text" class="tw-form-text" id="correlation" name="correlation"/></td>
                </tr>
                <tr>
                    <th>关联修改字段名称：</th>
                    <td><input type="text" class="tw-form-text" id="connectfield" name="connectfield"/></td>
                </tr>
            </table>
		</form>
	</div>
	 <form id="form1" action="${ctx}/form_saveStringToHTML.do" method="post" >
	 		<input type="hidden" name="formid" id="formid" value="${form.id }"/>
	 		<input type="hidden" name="tempfilename" id="tempfilename" value="${tempfilename }"/>
	 		<table class="tbl">
	 			<tr>
	 				<td valign="top" style="text-align: left;">
	 					数据库表:
	 					&nbsp;
	 					<select id="sel_tagTable" onchange="choose(this)" >
	 						<option value="">请选择...</option>
	 						<c:forEach var="d" items="${tables}">
	 							<option value="${d.vc_tablename};<c:forEach var="f" items="${d.fields}">${f.vc_fieldname}:${f.vc_name },</c:forEach>">${d.vc_tablename}</option>
	 						</c:forEach>
	 					</select>
	 					&nbsp;
	 					<!-- 
	 					<input type="button" value="根据表单元素对应字段"/> -->
	 				</td>
	 				
	 				<td style="text-align: left;">
	 					<input style="margin-bottom: 3px; width:100px" class="tdbtn" type="button" value="保存设置" onclick="save()"/>
	 					<input style="margin-bottom: 3px; width:100px" class="tdbtn" type="button" value="查看HTML源码" onclick="viewHTML()"/>
	 					<input style="margin-bottom: 3px; width:100px" class="tdbtn" type="button" value="查看JSP生成码" onclick="viewJsp()"/>
	 					<input style="margin-right:10px;float:right; margin-bottom: 3px; width:50px" 
	 							class="tdbtn" type="button" value="返回" onclick="javescript:window.location.href='${ctx}/form_getFormList.do?workflowId=${form.workflowId}'"/>
	 				</td>
	 				
	 			</tr>
	 			<tr>
	 				<td colspan="2">
	 					<div style="width: 100%;border: 1px solid #cccccc;height: 200px;overflow: auto;">
		 					<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		 						<thead>
		 						<tr>
		 							<Th style="width: 60px" rowspan="2" >元素名称</Th>
		 							<Th style="width: 60px" rowspan="2" >元素类型</Th>
		 							<Th style="width: 60px" rowspan="2" >列表名称</Th>
		 							<Th style="width: 100px" rowspan="2" >公文交换要素</Th>
		 							<Th style="width: 260px;align:center" colspan="2">取值</Th>
		 							<Th style="width: 260px;align:center" colspan="2">赋值</Th>
		 							<Th style="width: 60px;display: none" rowspan="2"  >接口插件</Th>
		 							<Th style="width: 60px" rowspan="2" >数据字典</Th>
		 							<Th style="width: 60px" rowspan="2" >意见区域</Th>
		 							<Th style="width: 60px" rowspan="2" >是否使用请阅模式</Th>
		 							<Th style="width: 200px" rowspan="2" >事件属性（URL,长,宽）</Th>
		 							<Th style="width: 80px" rowspan="2" >默认值</Th>
									<Th style="width: 80px" rowspan="2" >文本最大长度</Th>
		 							<Th style="width: 80px" rowspan="2" >字段设置</Th>
		 							<!-- <Th style="width: 80px" rowspan="2" >值格式化</Th>
		 							<Th style="width: 80px" rowspan="2" >值验证</Th>
		 							<Th style="width: 80px" rowspan="2" >其他</Th> -->
		 						</tr>
		 						<tr>
		 							<Th style="width: 200px">表名</Th>
		 							<Th style="width: 60px">字段名</Th>
		 							<Th style="width: 200px">表名</Th>
		 							<Th style="width: 60px">字段名</Th>
		 						</tr>
		 					    </thead>
		 					    <tbody>
		 						<c:forEach var="m" items="${columnMapList}" varStatus="in"> 
		 							<tr  <c:if test="${m.formtagtype=='comment'||m.formtagtype=='attachment'||m.formtagtype=='wh'}">style="display: none"</c:if>>
			 							<Td  onclick="jspTagTohtmlTag(this,'${m.formtagname}')"><input id="tagnames_${in.index }" name="formtagnames" type="text" readonly="readonly" class="input_none" value="${m.formtagname }"/></Td>
			 							<Td >
			 								<input name="formtagtypes" type="text" readonly="readonly" class="input_none" value="${m.formtagtype }"/>
			 								<input name="selectDics" type="hidden"  value="${m.selectDic }"/>
			 								<input name="commentDeses" type="hidden"  value="${m.columnCname }"/>
			 							</Td>
			 							<Td >
	 										<input name="listIds" type="text" readonly="readonly" class="input_none" style="text-align: center;" value="${m.listId }"/>
	 										<select name="tagtypes" class="input_none" style="text-align: left;display: none;">
				 									<option value="100" <c:if test="${m.tagtype=='100'}">selected="selected"</c:if>>100</option>
				 									<option value="201" <c:if test="${m.tagtype=='201'}">selected="selected"</c:if>>201</option>
				 									<option value="202" <c:if test="${m.tagtype=='202'}">selected="selected"</c:if>>202</option>
				 									<option value="203" <c:if test="${m.tagtype=='203'}">selected="selected"</c:if>>203</option>
				 									<option value="204" <c:if test="${m.tagtype=='204'}">selected="selected"</c:if>>204</option>
				 									<option value="205" <c:if test="${m.tagtype=='205'}">selected="selected"</c:if>>205</option>
				 									<option value="206" <c:if test="${m.tagtype=='206'}">selected="selected"</c:if>>206</option>
				 									<option value="207" <c:if test="${m.tagtype=='207'}">selected="selected"</c:if>>207</option>
				 									<option value="208" <c:if test="${m.tagtype=='208'}">selected="selected"</c:if>>208</option>
				 									<option value="209" <c:if test="${m.tagtype=='209'}">selected="selected"</c:if>>209</option>
				 									<option value="210" <c:if test="${m.tagtype=='210'}">selected="selected"</c:if>>210</option>
				 									<option value="211" <c:if test="${m.tagtype=='211'}">selected="selected"</c:if>>211</option>
				 									<option value="212" <c:if test="${m.tagtype=='212'}">selected="selected"</c:if>>212</option>
				 									<option value="401" <c:if test="${m.tagtype=='401'}">selected="selected"</c:if>>401</option>
				 								</select>	
			 							</Td>
			 							<Td>
	 											<select name="docColumns" class="input_none" style="text-align: left;">
	 												<option value="" ></option>
				 									<option value="lwbt" <c:if test="${m.docColumn=='lwbt'}">selected="selected"</c:if> >lwbt[来文标题]</option>
				 									<option value="yfdw" <c:if test="${m.docColumn=='yfdw'}">selected="selected"</c:if> >yfdw[印发单位]</option>
				 									<option value="lwh" <c:if test="${m.docColumn=='lwh'}">selected="selected"</c:if> >lwh[来文号]</option>
				 									<option value="fwh" <c:if test="${m.docColumn=='fwh'}">selected="selected"</c:if> >fwh[发文号]</option>
				 									<option value="fwsj" <c:if test="${m.docColumn=='fwsj'}">selected="selected"</c:if> >fwsj[发文时间]</option>
				 									<option value="swsj" <c:if test="${m.docColumn=='swsj'}">selected="selected"</c:if> >swsj[收文时间]</option>
				 									<option value="doctype" <c:if test="${m.docColumn=='doctype'}">selected="selected"</c:if> >doctype[公文类型]</option>
				 									<option value="fs" <c:if test="${m.docColumn=='fs'}">selected="selected"</c:if> >fs[份数]</option>
				 									<option value="gwlx" <c:if test="${m.docColumn=='gwlx'}">selected="selected"</c:if> >gwlx[公文类型]</option>
				 									<option value="zsdw" <c:if test="${m.docColumn=='zsdw'}">selected="selected"</c:if> >zsdw[主送单位]</option>
				 									<option value="csdw" <c:if test="${m.docColumn=='csdw'}">selected="selected"</c:if> >csdw[抄送单位]</option>
				 									<option value="ztc" <c:if test="${m.docColumn=='ztc'}">selected="selected"</c:if> >ztc[主题词]</option>
				 									<option value="jjcd" <c:if test="${m.docColumn=='jjcd'}">selected="selected"</c:if> >jjcd[紧急程度]</option>
				 									<option value="wh" <c:if test="${m.docColumn=='wh'}">selected="selected"</c:if> >wh[文号]</option>
				 									<option value="lwrq" <c:if test="${m.docColumn=='lwrq'}">selected="selected"</c:if> >lwrq[来文日期]</option>
				 									<option value="wjbh" <c:if test="${m.docColumn=='wjbh'}">selected="selected"</c:if> >wjbh[文件编号]</option>
				 									<option value="lwdw" <c:if test="${m.docColumn=='lwdw'}">selected="selected"</c:if> >lwdw[来文单位]</option>
				 									<option value="wjnr" <c:if test="${m.docColumn=='wjnr'}">selected="selected"</c:if> >wjnr[文件内容]</option>
				 								</select>
			 							</Td>
			 							<Td > 
			 								<select id="" name="tagTables" class="input_none" onchange="chooseOne(this,'${in.index}','tagFields')"  style="text-align: left;">
			 									<c:forEach var="d" items="${tables}">
			 										<option <c:if test="${m.tablename==d.vc_tablename}">selected="selected"</c:if> value="${d.vc_tablename};<c:forEach var="f" items="${d.fields}">${f.vc_fieldname}:${f.vc_name },</c:forEach>">${d.vc_tablename}</option>
						 						</c:forEach>
			 								</select>
			 							</Td>
			 							<Td >
			 								<select id="tagFields_${in.index}" name="tagFields" class="input_none">
			 									<option value="null">无</option>  
			 									<c:forEach var="d" items="${tables}" varStatus="i">
			 										<c:if test="${not empty m.tablename}">
				 										<c:if test="${m.tablename==d.vc_tablename}">
				 											<c:forEach var="f" items="${d.fields}">
				 												<option value="${f.vc_fieldname}:${f.vc_name}" <c:if test="${m.columnname==f.vc_fieldname}">selected="selected"</c:if>>${f.vc_fieldname}</option>
				 											</c:forEach>"
				 										</c:if> 
			 										</c:if> 
			 										<c:if test="${empty m.tablename}">
				 										<c:if test="${i.index==0}">
				 											<c:forEach var="f" items="${d.fields}">
				 												<option value="${f.vc_fieldname}:${f.vc_name}">${f.vc_fieldname}</option>
				 											</c:forEach>"
				 										</c:if>
			 										</c:if>
						 						</c:forEach>
			 								</select>
			 							</Td>
			 							<td>
			 								<select id="" name="assignTagTables" class="input_none"  onchange="chooseOne(this,'${in.index}','assignTagFields')"  style="text-align: left;">
			 									<c:forEach var="d" items="${tables}">
			 										<option style="width: 200px;" <c:if test="${m.assignTableName==d.vc_tablename}">selected="selected"</c:if> value="${d.vc_tablename};<c:forEach var="f" items="${d.fields}">${f.vc_fieldname}:${f.vc_name },</c:forEach>">${d.vc_tablename}</option>
						 						</c:forEach>
			 								</select>
			 							</td>
			 							<td>
			 								<select id="assignTagFields_${in.index}" name="assignTagFields" class="input_none">
			 									<option value="null">无</option>  
			 									<c:forEach var="d" items="${tables}" varStatus="i">
			 										<c:if test="${not empty m.assignTableName}">
				 										<c:if test="${m.assignTableName==d.vc_tablename}">
				 											<c:forEach var="f" items="${d.fields}">
				 												<option value="${f.vc_fieldname}:${f.vc_name}" <c:if test="${m.assignColumnName==f.vc_fieldname}">selected="selected"</c:if>>${f.vc_fieldname}</option>
				 											</c:forEach>"
				 										</c:if> 
			 										</c:if> 
			 										<c:if test="${empty m.assignTableName}">
				 										<c:if test="${i.index==0}">
				 											<c:forEach var="f" items="${d.fields}">
				 												<option value="${f.vc_fieldname}:${f.vc_name}">${f.vc_fieldname}</option>
				 											</c:forEach>"
				 										</c:if>
			 										</c:if>
						 						</c:forEach>
			 								</select>
			 							</td>
			 							<td style="display: none">
			 								<select id="serverPlugins_${in.index }" name="serverPlugins" class="input_none">
			 									<option value=""></option>  
			 									<c:forEach var="p" items="${serverPlugins}" varStatus="i">
			 										<option value="${p.id}" <c:if test="${m.serverPlugin_id==p.id}">selected="selected"</c:if>>${p.cname}</option> 
						 						</c:forEach>
			 								</select>
			 							</td>
			 							<Td>
			 								<select id="dic_${in.index }" name="dics" class="input_none">
			 									<option value=""></option>  
			 									<c:forEach var="p" items="${dicList}" varStatus="i">
			 										<option value="${p.vc_name}" <c:if test="${m.selectDic==p.vc_name}">selected="selected"</c:if>>${p.vc_name}</option> 
						 						</c:forEach>
			 								</select>
			 								<input name="dicdatas" type="hidden"  class="input_none" value="${m.dicdata}"/>
			 							</Td>
			 							<Td >
			 								<select id="trueArea_${in.index}" name="trueArea" class="input_none">
			 									<option value=""></option>
			 									<option value="1"  <c:if test="${m.trueArea== '1'}">selected="selected"</c:if>>是</option>
			 									<option value="2"  <c:if test="${m.trueArea== '2'}">selected="selected"</c:if>>IMG</option>     
			 								</select>
			 							</Td>
			 							<Td >
			 								<select id="isPOrW_${in.index}" name="isPleaseOrWatch" class="input_none">
			 									<option value=""></option>
			 									<option value="1"  <c:if test="${m.isPleaseOrWatch== '1'}">selected="selected"</c:if>>是</option>
			 									<option value="2"  <c:if test="${m.isPleaseOrWatch== '2'}">selected="selected"</c:if>>否</option>     
			 								</select>
			 							</Td>
			 							<Td><input name="otherdatas" type="text"  class="input_none" value="${m.otherData}"/></Td>
			 							<Td>
			 								<select id="constantValues_${in.index}" name="constantValues" class="input_none">
			 									<option value=""  <c:if test="${m.constantValue== ''}">selected="selected"</c:if>></option>
			 									<option value="people"  <c:if test="${m.constantValue== 'people'}">selected="selected"</c:if>>登录人</option>
			 									<option value="peopleAndId"  <c:if test="${m.constantValue== 'peopleAndId'}">selected="selected"</c:if>>登录人:id</option>
			 									<option value="peopleId"  <c:if test="${m.constantValue== 'peopleId'}">selected="selected"</c:if>>人员id</option>
			 									<option value="depName" <c:if test="${m.constantValue== 'depName'}">selected="selected"</c:if>>登录部门</option>
			 									<option value="unitName" <c:if test="${m.constantValue== 'unitName'}">selected="selected"</c:if>>登录单位</option>
			 									<option value="depId" <c:if test="${m.constantValue== 'depId'}">selected="selected"</c:if>>部门Id</option>
												<option value="fwdepName" <c:if test="${m.constantValue== 'fwdepName'}">selected="selected"</c:if>>发文登录部门</option>
			 									<option value="timeZh"  <c:if test="${m.constantValue== 'timeZh'}">selected="selected"</c:if>>时间:中文</option>
			 									<option value="timeZhs"  <c:if test="${m.constantValue== 'timeZhs'}">selected="selected"</c:if>>时间:中文秒</option>
			 									<option value="timeEn"  <c:if test="${m.constantValue== 'timeEn'}">selected="selected"</c:if>>时间:英文</option>  
			 									<option value="timeEns"  <c:if test="${m.constantValue== 'timeEns'}">selected="selected"</c:if>>时间:英文秒</option>
			 									<option value="timeEnSe"  <c:if test="${m.constantValue== 'timeEnSe'}">selected="selected"</c:if>>时间:英文分</option>
			 									<option value="docNum"  <c:if test="${m.constantValue== 'docNum'}">selected="selected"</c:if>>文号</option>
			 									<option value="timeEnss"  <c:if test="${m.constantValue== 'timeEnss'}">selected="selected"</c:if>>时间:英文2</option>
			 								</select>
			 							</Td>
			 							<Td>
			 								<select id="textMaxLen_${in.index}" name="textMaxLen" class="input_none">
			 									<option value=""  <c:if test="${m.textMaxLen== ''}">selected="selected"</c:if>>不限制</option>
			 									<option value="50"  <c:if test="${m.textMaxLen== '50'}">selected="selected"</c:if>>50</option>
			 									<option value="100"  <c:if test="${m.textMaxLen== '100'}">selected="selected"</c:if>>100</option>
			 									<option value="300"  <c:if test="${m.textMaxLen== '300'}">selected="selected"</c:if>>300</option>
			 								</select>
			 							</Td>
			 							<Td>
			 								<input style="margin-bottom: 3px; width:100px"  class="tdbtn" type="button" value="规则设置" onclick="setUpColumn('${m.id}','${m.associatedColumns}','${m.regularExpression}','${m.regularMeanings}','${m.generationMode}','${m.correlation}','${m.connectfield}');"/>
			 							</Td>
			 							<input name="valueformat" type="hidden"  class="input_none" value="${m.valueformat}"/>
			 							<input name="verifyformat" type="hidden"  class="input_none" value="${m.verifyformat}"/>
			 							<input name="bindfields" type="hidden"  class="input_none" value="${m.bindfields}"/>
			 							<input name="associatedColumns" type="hidden"  class="input_none" value="${m.associatedColumns}"/>
			 							<input name="regularExpression" type="hidden"  class="input_none" value="${m.regularExpression}"/>
			 							<input name="regularMeanings" type="hidden"  class="input_none" value="${m.regularMeanings}"/>
			 							<input name="generationMode" type="hidden"  class="input_none" value="${m.generationMode}"/>
			 							<input name="correlation" type="hidden"  class="input_none" value="${m.correlation}"/>
			 							<input name="connectfield" type="hidden"  class="input_none" value="${m.connectfield}"/>
			 						</tr>
		 						</c:forEach>
		 						</tbody>
		 					</table>
	 					</div>
	 				</td>
	 			</tr>
	 			<tr>
	 				<td colspan="2">
	 					<iframe name="iframe_html" id="iframe_form" src="${ctx}/form/html/${tempfilename }" style="width: 100%;margin: auto;height: 450px;"></iframe>
	 				</td>
	 			</tr>
	 		</table>
	 		
	 		<!-- 以下四个隐藏域，用于读取文件流需要的路径和类型信息 -->
			  <input type="hidden" name="isview" id="isview" value="1"/>
			  <input type="hidden" name="zwkjForm.form_htmlfilename" id="filename" value="${filename }"/>
			  <input type="hidden" name="formType" id="formType" value="html"/>
			  <input type="hidden" name="jspfilename" id="jspfilename" value="${jspfilename }"/>
	  </form>
	  <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	  <script src="/trueWorkFlow/dwz/style/js/jquery-1.7.1.min.js"></script>
	  <script type="text/javascript">
	  	 function sub(){
	  		document.getElementById('form1').submit(); 
	  	 };
	  	
	  	
		 //子页面调用，用于映射选中相应标签名称
		 var god_current_selected_tr=null;//当前选中的标签行
	  	 function htmlTagToJspTag(tagname){
		  	if(!tagname)return;
		  	var selectedBgColor='pink';//当前选中行的背景色
			var tagnames=g.gbn('formtagnames');
			//取消上一次选中的行的背景色
			if(god_current_selected_tr){ 
				for(var j=0;j<god_current_selected_tr.cells.length;j++){ 
					god_current_selected_tr.cells[j].style.backgroundColor='';
				};
			};
			if(tagnames){
				for(var i=0;i<tagnames.length;i++){
					if(tagnames[i].value==tagname){
						var tr=tagnames[i].parentNode.parentNode;
						
						
						//记录当前选中的行
						god_current_selected_tr=tr;
						//变色提示选中行
						if(tr){
							for(var j=0;j<tr.cells.length;j++){
								tr.cells[j].style.backgroundColor=selectedBgColor;
							};
						}
						tagnames[i].focus();
						break;
					};
				};
			};
		 };	 
		//本页面调用，用于映射子页面选中相应标签
		 function jspTagTohtmlTag(src,tagname){
			 src=src.parentNode;
			var selectedBgColor='pink';//当前选中行的背景色
			//取消上一次选中的行的背景色
			if(god_current_selected_tr){ 
				for(var j=0;j<god_current_selected_tr.cells.length;j++){ 
					god_current_selected_tr.cells[j].style.backgroundColor='';
				};
			};
			//记录当前选中的行
			god_current_selected_tr=src;
			//变色提示选中行
			if(src){
				for(var j=0;j<src.cells.length;j++){
					src.cells[j].style.backgroundColor=selectedBgColor;
				};
			}
			var iframe=window.frames['iframe_html'];//根据name获得子页面对象
			if(iframe&&iframe.jsp2html)iframe.jsp2html(tagname);
		 };

		 //查看html源码
		 function viewHTML(){
			 g.g('formType').value='html';
			 window.showModalDialog("${ctx}/form/html/src/readHTML.jsp?d="+Math.random(),window,'dialogWidth: '+'900'+'px;dialogHeight: '+'550'+'px; status: no; scrollbars: no; Resizable: no; help: no;center:yes;');
		 };
		//查看html源码
		 function viewJsp(){
			 g.g('formType').value='jsp';
			 window.showModalDialog("${ctx}/form/html/src/readHTML.jsp?d="+Math.random(),window,'dialogWidth: '+'900'+'px;dialogHeight: '+'550'+'px; status: no; scrollbars: no; Resizable: no; help: no;center:yes;');
		 };

		 
		 //保存设置
		 function save(){
			 // 获取位置信息
		    var json = getLocationJson();			//传统的页面结构
			var formPages = getFormPageJson();		//表单的意见格式
			$.ajax({
				url : '${ctx}/form_updateFormLocationJson.do',
				type : 'POST',
				cache : false,
				async : false,
				data : {
					'formid':$("#formid").val()
				    ,'locations':JSON2.stringify(json)
				    ,'formPages':JSON2.stringify(formPages)
				},
				error : function() {
					alert('AJAX调用错误');
				},
				success : function(ret) {
					if(ret == 'yes'){
						$.ajax({
							url : '${ctx}/form_addFormTagMapColumn.do',
							type : 'POST',
							cache : false,
							async : false,
							data : $('#form1').serialize(),
							error : function() {
								alert('AJAX调用错误');
							},
							success : function(ret) {
								alert("设置成功");
								parent.layer.closeAll();
							}
						});
					}
				}
			}); 
		 };


		 function getLocationJson(){			       
			 var body = $(document.getElementById("iframe_form").contentWindow.document.body);
			 //获取屏幕的宽度
			 var screen = document.getElementById("iframe_form").getBoundingClientRect().right-document.getElementById("iframe_form").getBoundingClientRect().left;
			 var factor =  Math.round((1420-screen)/2);
			 var json = coverFormToLocationTrueform(body,factor);
			 return json;
		 }
		 
		 
		 
		 //解析页面html内容、解析为trueJson的文件格式
		 function getFormPageJson(){			       
			 var body = $(document.getElementById("iframe_form").contentWindow.document.body);
		       //获取屏幕的宽度
			var screen = document.getElementById("iframe_form").getBoundingClientRect().right-document.getElementById("iframe_form").getBoundingClientRect().left;
			// var factor =  Math.round((1420-screen)/2); //其实这个东西在这里就没有多大的用途了。
			var factor = 1024/1420; //缩放的比例,这个也非常有可能没有用
			var trueFile = coverFormToTrueform(body,factor);
		    return trueFile;
	 	}
		 
		 //获取页面表单中输入框
		 function coverFormToLocationTrueform(scope,factor){
				var forms={};
				$('input[type=text]',scope).each(function(){
					//console.log('text:: ',this.name);
					$(this).addClass('br');
					var nature = $(this).attr('nature');
					if(typeof nature == "undefined"){
						nature = "";
					}
					forms[this.name]={e:this,t:'text',n:nature};
				});
				$('input[type=radio]',scope).each(function(){
					//console.log('radio:: ',this.name);
					$(this).addClass('br');
					if(!forms[this.name]){
						forms[this.name]={e:this,t:'radio',n:''};
					}
				});
				$('input[type=checkbox]',scope).each(function(){
					//console.log('checkbox:: ',this.name);
					$(this).addClass('br');
					if(!forms[this.name]){
						forms[this.name]={e:this,t:'checkbox',n:''};
					}
				});
				$('select',scope).each(function(){
					//console.log('select:: ',this.name);
					$(this).addClass('br');
					forms[this.name]={e:this,t:'select',n:''};
				});
				$('textarea',scope).each(function(){
					//console.log('textarea:: ',this.name);
					$(this).addClass('br');
					var nature = $(this).attr('nature');
					if(typeof nature == "undefined"){
						nature = "";
					}
					forms[this.name]={e:this,t:'textarea',n:nature};
				});
				
				// 判断表单里面有没有这个元素
				var offsetY = 53 ;
				var formpos=[];
				for(var i in forms){
					var ot=$(forms[i].e).closest("td")
						,of=ot.offset()
						,ow=ot.width()
						,oh=ot.height();
						
					if(of==null){
						of=ot.offset();
					}
					if(of!=null){
						 l = of.left,t = of.top;
						if(forms[i].t == 'textarea'){
							 oh = oh -10;
							if(l < 330){
								 ow = ow-10;
							}
							if(oh >=80){
								t = t-5;
								ow = ow-10;
								l = l +4;
							}
						} else{
							t = t-2;
							oh = oh-2;
						}
					    l = Math.round(l);
						t = Math.round(t);
						ow = Math.round(ow);
						oh = Math.round(oh);
						
					 	if( t%1362 > 681){
							t = t -5;
						}
						if( t % 681 >1000){
							t = t -2;
						}
						if(t/1362 > 1)
						{
								t = t+5;
						}
						if(forms[i].t == 'textarea' || oh > 400){
							oh = oh -5;
						}
						var nature = forms[i].n;
						if(typeof forms[i].e.value != "undefined" && forms[i].e.value != '' && forms[i].e.value.indexOf("\$") == -1  && typeof forms[i].e.userdefine != "undefined" &&forms[i].e.userdefine == "hidden"){
							formpos.push({"name":i,"type":'hidden',"startX":0,"startY":0,"width":0,"height":0,"value":forms[i].e.value,"nature":nature});
						}else{
							formpos.push({"name":i,"type":forms[i].t,"startX":l-178+factor,"startY":t+offsetY,"width":ow-5,"height":oh-1,"value":'',"nature":nature});
						}
					}else{
						var nature = forms[i].n;
						if(forms[i].e.value != '' && forms[i].e.value.indexOf("\$") == -1  && typeof forms[i].e.userdefine != "undefined" &&forms[i].e.userdefine == "hidden"){
							formpos.push({"name":i,"type":'hidden',"startX":0,"startY":0,"width":0,"height":0,"value":forms[i].e.value,"nature":nature});							
						}else if(forms[i].e.value != '' && forms[i].e.value.indexOf("\$") == 0 && typeof forms[i].e.userdefine != "undefined" &&forms[i].e.userdefine == "hidden"){
							formpos.push({"name":i,"type":'hidden',"startX":0,"startY":0,"width":0,"height":0,"value":"","nature":nature});	
						}
					}
				}
				return formpos;
			}
		 
		 
	 function getElementLeft(element){
			var actualLeft = element.offsetLeft;
			var current = element.offsetParent;
			while (current !== null){
				actualLeft += current.offsetLeft;
				current = current.offsetParent;
				}
			return actualLeft;
		}
	 
	 function getElementTop(element){
			var actualTop = element.offsetTop;
			var current = element.offsetParent;
		while (current !== null){
			actualTop += current.offsetTop;
			current = current.offsetParent;
		}
			return actualTop;
		}
		function choose(src){
			var v=src.value;
			if(v!=''){
				var tables=g.gbn('tagTables');
				var fields=g.gbn('tagFields');
				for(var i=0;i<tables.length;i++){
					tables[i].value=v;
					var filedsArr=(v.split(/;/)[1]).split(/,/);
					fields[i].options.length=0;//删除option所有元素
					
					if(filedsArr){ 
						fields[i].options.add(new Option('无','null'));
						for(var j=0;j<filedsArr.length;j++){
							fields[i].options.add(new Option(filedsArr[j].split(/:/)[0],filedsArr[j])); //${f.vc_fieldname}:${f.vc_name },
						};
						
					};
				};
				selectTags('tagFields');
				
				
				var tagTables=g.gbn('assignTagTables');
				var tagFields=g.gbn('assignTagFields');
				for(var i=0;i<tagTables.length;i++){
					//alert(ragTables[i]);
					tagTables[i].value=v;
					var filedsArr=(v.split(/;/)[1]).split(/,/);
					tagFields[i].options.length=0;//删除option所有元素
					if(filedsArr){ 
						tagFields[i].options.add(new Option('无','null'));
						for(var j=0;j<filedsArr.length;j++){
							tagFields[i].options.add(new Option(filedsArr[j].split(/:/)[0],filedsArr[j])); //${f.vc_fieldname}:${f.vc_name },
						};
						
					};
				};
				selectTags('assignTagFields');
				
			};
		 };
		 function selectTags(tagFields){
			 var tagnames=g.gbn('formtagnames');
			 var fields=g.gbn(tagFields);
			 for(var i=0;i<fields.length;i++){ 
				var select=fields[i];
				var isin=false;
				for(var j=0;j<select.options.length;j++){
					if(select.options[j].value.indexOf(tagnames[i].value.toUpperCase())!=-1){
						select.options[j].selected=true; 
						isin=true;break;
					}
				};
				if(isin){ 
				}else{
					fields[i].value="null";
				};
			 };
		 };

		 function chooseOne(src,index,field){ 
			var fieldSel=g.g(field+'_'+index); 
			var tagname=g.g('tagnames_'+index);
			var v=src.value;
			var filedsArr=(v.split(/;/)[1]).split(/,/);
			
			if(filedsArr){
				fieldSel.options.length=0;//删除option所有元素
				fieldSel.options.add(new Option('无','null'));
				for(var j=0;j<filedsArr.length;j++){
					fieldSel.options.add(new Option(filedsArr[j].split(/:/)[0],filedsArr[j]));
				};
				for(var j=0;j<fieldSel.options.length;j++){//alert(select.options[j].value.indexOf(tagnames[i].value.toUpperCase()));
					if(fieldSel.options[j].value.indexOf(tagname.value.toUpperCase())!=-1){
						fieldSel.options[j].selected=true; 
						break;
					}
				};
			}
		 };

		 window.onload=function(){
			 if('${addorupdate}'=='0'){
			 	selectTags();
			 }
		 };
		 
		function setUpColumn(id,associatedColumns,regularExpression,regularMeanings,generationMode,correlation,connectfield){
			$("#columnId").val(id);
			$("#associatedColumns").val(associatedColumns);
			$("#regularExpression").val(regularExpression);
			$("#regularMeanings").val(regularMeanings);
			$("#generationMode").val(generationMode);
			$("#correlation").val(correlation);
			$("#connectfield").val(connectfield);
			layer.open({
				title:'设置',
				type:1,
				area:['390px','340px'],
				content:$("#hideDiv"),
				btn:["确定","取消"],
				yes:function(){
					//$("#commentform").attr("action","${ctx}/form_setColumnRule.do");
					//$("#commentform").submit();
					$.ajax({
						url : '${ctx}/form_setColumnRule.do',
						type : 'POST',
						cache : false,
						async : false,
						data : $('#commentform').serialize(),
						error : function() {
							alert('AJAX调用错误');
						},
						success : function(ret) {
							alert("规则设置成功");
							window.location.reload();
						}
					});
				}
			});
		}
	  </script>
	</body>
</html>