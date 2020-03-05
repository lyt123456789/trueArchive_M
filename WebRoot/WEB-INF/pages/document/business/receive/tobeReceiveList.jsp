<!DOCTYPE html >
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="cn.com.trueway.document.business.docxg.client.vo.DocExchangeVo"%>

<%@page import="java.text.SimpleDateFormat"%><html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="renderer" content="webkit">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	    <%@ include file="/common/header.jsp"%>
	    <%@ include file="/common/headerbase.jsp"%>
    </head>
    <body>
    	<div class="wf-layout">
			<div class="wf-list-top">
				<form id="thisForm" action="${ctx}/rec_tobeRecList.do"  method="post" >
				<div class="wf-search-bar">
		 	       		<label class="wf-form-label" style="font-size:14; font-family:黑体; font-weight:bold;" for="">部门:</label>
		 	        	${depName}
				</div>
				</form>
			</div>
			<div class="wf-list-wrap">
					<form id="disform" method="POST" name="disform" action="${ctx}/rec_tobeRecList.do" >
					<table class="wf-fixtable" layoutH="140">
						<thead>
							<tr>
								<th width="15%"  style="font-weight:bold;text-align:center;">文号</th>
								<th width="10%" style="font-weight:bold;text-align:center;">发文单位</th>
								<th width="20%" style="font-weight:bold;text-align:center;">标题</th>
								<th width="15%" style="font-weight:bold;text-align:center;">发文时间</th>
								<th width="15%" style="font-weight:bold;text-align:center;">公文类型</th>
								<th width="10%" style="font-weight:bold;text-align:center;">收取</th>
							</tr>  
						</thead>
				    	<tbody>
							<%  
								LinkedHashMap<String, DocExchangeVo> doc_lists = ((LinkedHashMap<String, DocExchangeVo>)session.getAttribute("doc_list"));
								Iterator<DocExchangeVo> its = doc_lists.values().iterator();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								while(its.hasNext()){
									DocExchangeVo vo = (DocExchangeVo)its.next();
							%>
								<tr >
									<td style="text-align:center;"><%=vo.getDocExchangeBox().getJgdz()%></td>
									<td style="text-align:center;"><%=vo.getDocExchangeBox().getFwjg()%></td>
									<td style="text-align:left;"><%=vo.getDocExchangeBox().getTitle()%></td>
									<td style="text-align:center;"><%=sdf.format(vo.getDocExchangeBox().getSubmittm())%></td>
									<td style="text-align:center;"><%=vo.getDocExchangeBox().getDoctype()%></td>
									<td style="text-align:center;"> <a href="#" onclick="receive('<%=vo.getQueue().getId()%>');">收取</a></td>
								</tr>
							<% }
							%>
						</tbody>
					</table>
					</form>
			</div>
		</div>
   </body>
     <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
     <script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
  	<script type="text/javascript">
       	seajs.use('lib/form',function(){
			$('#departmentId').cssSelect({trigger:true,triggerFn:function(){
				window.location.href="${ctx}/rec_tobeRecList.do?type=webid&departmentId="+$(this).val();
			}});
           });

           seajs.use('lib/progress',function(p){
           	var progressbar=new p.progressbar({sender:'#sender',actions:[
				<%  
					LinkedHashMap<String, DocExchangeVo> doc_list = ((LinkedHashMap<String, DocExchangeVo>)session.getAttribute("doc_list"));
					Iterator<DocExchangeVo> it = doc_list.values().iterator();
					while(it.hasNext()){
						//if(it.next()==null) break;
						DocExchangeVo vo = (DocExchangeVo)it.next();
				%>
					<%if(it.hasNext()){%>
					"${ctx}/rec_receiveDoc.do?id=<%=vo.getQueue().getId()%>",
					<%} else {%>
					"${ctx}/rec_receiveDoc.do?id=<%=vo.getQueue().getId()%>"
				<% }
					}
				%>
           	],successFn:function(){setTimeout(function(){window.location.reload();},2000)}});
           });
           if('<%=doc_list.size()%>'==='0'){
               document.getElementById('sender').disabled=true;
           }
    </script>
    <script type="text/javascript">
		function receive(id){
			if(confirm("确定收取该待收信息吗？")){
				$.ajax({
					url:'${ctx}/rec_receiveDocToWfProcess.do',
					type:'POST',
					cache:false,
					async:true,
					data : {
						'receiveType':'one','id':id
					}, 
					error : function() {
						alert('AJAX调用错误(rec_receiveDocToWfProcess.do)');
					},
					success : function(msg) {
						alert("收取成功!");
						window.location.href="${ctx}/rec_tobeRecList.do";
					}
				});
			}
		}
    </script>
</html>
