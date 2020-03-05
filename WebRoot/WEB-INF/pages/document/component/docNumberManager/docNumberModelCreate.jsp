<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>

<html>
	<head>
		<title>主页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
        <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	</head>
	<body style="overflow:auto;">
		<form id="createform" action="${ctx}/docNumberManager_addModel.do" method="post">
			<input type="hidden" name="amodel" id="amodel"/>
			<input type="hidden" name="modelstr" id="modelstr"/>
		</form>
		<div class="pageContent">
			<div id="w_list_print">
				<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
					<script type="text/javascript">
						var i=0;
						var countPerTr=5;
						var counts=0;
						var temp=0;
						var temp2=0;
					</script>
					<c:forEach items="${whtypes}" var="whtype">
						<tr>
							<td style="width: 180px;text-align: center;">
								${whtype.big.name}
							</td>
							<td style="padding: 0;">
								<script type="text/javascript">
									i=0;
									counts=parseInt('${whtype.smallcount}',10);
									if(counts<=countPerTr){
										temp=0;
									}else{
										temp=Math.floor(counts/countPerTr);
									}
									temp2=temp*countPerTr;
								</script>
								<table class="tbl-in">
									<c:forEach var="smalltype" items="${whtype.small}">
										<script type="text/javascript">
											if(i==0||i%countPerTr==0){
												document.write("<tr>");
											}
											i++;
											if(i%countPerTr==0){
												if(i>temp2){
													document.write('<td style="border-right: 0px;border-bottom: 0px;" id="${smalltype.doctype}"  ondblclick="selectItem(this)" title="双击选择文号" lang="${smalltype.typeid}">${smalltype.name}</td>');
												}else{
													document.write('<td style="border-right: 0px;" id="${smalltype.doctype}"   ondblclick="selectItem(this)" title="双击选择文号" lang="${smalltype.typeid}">${smalltype.name}</td>');	
												}						
											}else{
												if(i>temp2){
													document.write('<td style="border-bottom: 0px;" id="${smalltype.doctype}"   ondblclick="selectItem(this)" title="双击选择文号" lang="${smalltype.typeid}">${smalltype.name}</td>');
												}else{
													document.write('<td  id="${smalltype.doctype}"  ondblclick="selectItem(this)" title="双击选择文号" lang="${smalltype.typeid}">${smalltype.name}</td>');
												}
											}
											if(i%countPerTr==0){
												document.write("</tr>");
											}
											
										</script>
									</c:forEach>
								</table>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div>
			<table width="100%" height="90px">
				<tr height="90px">
					<td style="font-size:20px;line-height:90px;text-align:center; color:green" id="previewArea">
					</td>
				</tr>
			</table>
		</div>
		<!-- <div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5">  
				<li><a id="preview" class="buttonActive"><span>预  览</span></a></li>
				<li><a onclick="clearItem();" class="buttonActive" href="javascript:;"><span>清  空</span></a></li>
				<li><a onclick="createModel()" class="buttonActive" href="javascript:;"><span>生  成</span></a></li>
			</ul>
		</div> -->
		<div style="display:none;" id="td_wh"></div>
		
		 <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
		<script type="text/javascript">
			
			//记录总共选中元素的个数
			var selectedCount=0;
			//记录被选中的元素
			var selectedItems=[];
			function selectItem(o){
				//取消鼠标默认选中td事件 
				//选中td背景色变化,并提示步骤号
				if(typeof(o.isItem)=='undefined'||o.isItem==false){
					o.isItem=true;
					o.oldInner=o.innerHTML;
					selectedCount++;
					o.className='selected_wh';
					o.innerHTML=o.innerHTML+'<span style="color:red">'+selectedCount+'</span>';
					selectedItems[selectedCount-1]=o;				
				}
				addItemToTD();
				$("#previewArea").text($("#td_wh").text());
			};
			function clearItem(){
				for(var i=0;i<selectedItems.length;i++){
					selectedItems[i].isItem=false;
					selectedItems[i].className='';
					selectedItems[i].innerHTML=selectedItems[i].oldInner;
				}
				selectedCount=0;
				selectedItems=[];
				$("#previewArea").text("");
			}

			function addItemToTD(){
				var td=document.getElementById('td_wh');
				var innerStr='';
				for(var i=0;i<selectedItems.length;i++){
					innerStr+=selectedItems[i].oldInner;
				}
				td.innerHTML=innerStr;
			};

			function createModel(){
				var str='';
				if(selectedItems==null||selectedItems.length==0){
					alert("请至少选择一个文号(双击文号元素进行选择)");
					return false;
				}
				var modelstr='';
				var doctypestr='';
				for(var i=0;i<selectedItems.length;i++){
					modelstr+=i==selectedItems.length-1?selectedItems[i].oldInner:selectedItems[i].oldInner+',';
					str+=selectedItems[i].lang+',';
					str+=(i+1)+';';
					doctypestr+=i==selectedItems.length-1?selectedItems[i].id:selectedItems[i].id+',';
				}
				if(modelstr!=''&&doctypestr!=''){
					modelstr+=';'+doctypestr;
				}
				if(doctypestr.match(/fwxh/)==null){
					alert("必须要选择一个发文序号和公文字段对应");
					return false;
				}
				if(doctypestr.match(/gjdz/)==null){
					alert("必须要选择一个发文标题和公文字段对应");
					return false;
				}
				if(doctypestr.match(/gjdz/)!=null&&doctypestr.match(/gjdz/g).length>1){
					alert("最多只能选择一个发文标题,请重新选择");
					return false;
				}
				if(doctypestr.match(/fwnh/)!=null&&doctypestr.match(/fwnh/g).length>1){
					alert("最多只能选择一个发文年号,请重新选择");
					return false;
				}
				if(doctypestr.match(/fwxh/)!=null&&doctypestr.match(/fwxh/g).length>1){
					alert("最多只能选择一个发文序号,请重新选择");
					return false;
				}
				$.ajax({
					url : '${ctx}/docNumberManager_addModel.do',
					type : 'POST',
					cache : false,
					async : false,
					data : {'modelstr' : modelstr,"amodel":str},
					error : function() {
						alert('AJAX调用错误(docNumberManager_addModel.do)');
					},
					success : function(msg) {
						alert(msg);
					}
				});
			};
		</script>
		<script type="text/javascript">
		$("#preview").bind("click",function (){
			addItemToTD();
			art.dialog({content:$("#td_wh").text(),lock:true});
		});
		</script>
	</body>
</html>
