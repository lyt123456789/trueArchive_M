<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>列表</title>
    <link rel="stylesheet" href="css/list.css">
    <script src="js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="js/icapturevideo.js"></script>
    <script src="js/SpryTabbedPanels.js" type="text/javascript"></script>
	<script src="js/SpryValidationRadio.js" type="text/javascript"></script>
	<script src="js/SpryCollapsiblePanel.js" type="text/javascript"></script>
    
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>
<script type="text/javascript">	
	//初始化，打开高拍仪
	function init(){
		try{
			Content = document.getElementById("content");//获取文本框对象
			//WMSelect = document.getElementById("WaterMarkSelect");//根据js的脚本内容，必须先获取object对象	
			Capture = document.getElementById("Capture");//根据js的脚本内容，必须先获取object对象
			SetColorMode(0);
			SetFileType(0)		
			ResSelect = document.getElementById("Resolution_Select");//获取分辨率select标签的object对象
		}catch(err){
			alert("请安装Active X控件CaptureVideo.cab");
		}
	}
	function onbeforeunload()   
	{   
		Capture.CloseDeviceEx();
		//Capture.Dispose();
		//window.event.returnValue="确定要退出本页吗？"; 
	}
</script>
<body onLoad="init()" onBeforeUnload="onbeforeunload()">
<div class="search">
	<form name="list"  id="list" action="${ctx }/model_metaDataList.do?" method="post" style="display:inline-block;">
		    <input type="hidden" id="formbh" name="formbh" value="${formbh}">
		    <input type="hidden" name="CammeraType" id="CammeraType"  value="0">
		    <div class="wf-top-tool">	
		        
                    &nbsp;&nbsp;&nbsp;&nbsp;
					<a class="wf-btn-primary" href="javascript:void(0);" onclick="OpenDevice('0')" target="_self">
						<i class="wf-icon-pencil" style="display:inline-block;"></i> 开始扫描
					</a>
					&nbsp;&nbsp;&nbsp;
					<select id="Resolution_Select" name="Resolution" style="width:100px;display: none;" onchange="SetResIndex(Resolution_Select.value)"></select>
					
					<a class="wf-btn"  id="addnum"  href="javascript:void(0)" onclick="CaptureToFile()">
						<i class="wf-icon-plus-circle" style="display:inline-block;"></i> 拍摄
					</a>
					&nbsp;&nbsp;&nbsp;
					<a class="wf-btn-danger" href="javascript:void(0);" onclick="CloseDeviceEx;closeWin();"   target="_self">
						<i class="wf-icon-trash" style="display:inline-block;"></i> 结束扫描
					</a>
				</div>
	</form>
</div>
 <div class="content" align="center">
   <div style="width: 99%;height: 99%;border: 1px solid black;float:left" align="center">
	  	<div style="width:99%;height:99%" align="center">
			<object id="Capture" style ="width: 99%;height: 500px;border: 5 gray solid;" align="middle" classid="clsid:9A73DB73-2CA3-478D-9A3F-7E9D6A8D327C" codebase="CaptureVideo.cab#version=1,7,0,0">
	    	</object>
    	</div>
	</div>
 </div>

</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">

	var callbackdata = function () {
			debugger
			var name = '';
			var val = '';
			 $('input[name="checkbox"]:checked').each(function(){
		    	val=$(this).val();
		    	name = $(this).parent().next().next().text();
		    });
		    var data = {
		    		name: name,
		        	val:val
		    };
		    return data;
		}
	
<%-- 	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/model_metaDataList.do";//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('list');								//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	}; --%>
</script>
<script type="text/javascript">
function addFj(fileName,strBase64){
	var formid = document.getElementById("formbh").value;
	$.ajax({
        async:true,//是否异步
        type:"POST",//请求类型post\get
        cache:false,//是否使用缓存
        dataType:"text",//返回值类型
        data:{"formid":formid,"fileName":fileName,"strBase64":strBase64},
        url:"${ctx}/using_saveJydFj.do",
        success:function(text){
        	 if(text=="success"){
        		 alert("扫描成功");
        	 }
        }
    });
}
function closeWin(){
	 parent.location.reload();
	 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
     parent.layer.close(index); //再执行关闭   
}
</script>
</html>