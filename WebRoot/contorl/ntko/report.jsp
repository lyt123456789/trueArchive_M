<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html>
	<head>
		<title>主页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/common/css/ued.base.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/common/css/foodSafety.commcon.css">
	</head>
	<base target="_self"/>
	<body>
		<div class="w-here">
			<div class="w-here-box"><span> 检测报告  → 签发</span></div>
		</div>
		 	<form style="display: none;" id="form1" name="form1" action="" method="post"></form> 
			<div style="clear: both;">
	            <select name="model" id="model" onchange="openfile(this.value)">
	                <option  value="">请选择模板</option>
	                <c:forEach var="att" items="${attachmentList}">
	                	<option value="${att.aid }">${att.aname }</option>
	                </c:forEach>
	            </select>
	            <span id="span_qianfa_btn">
		            <input class="btn" type="button" value="盖  章" onclick="ok();"/>
	            	&nbsp;
	            	<input class="btn" type="button" value="完成签发" onclick="qianfa();"/>
	            	&nbsp;
	            	<input class="btn" type="button" value="打  印" onclick="print();"/>
	            	&nbsp;
	            	<input class="btn" type="button" value="关  闭" onclick="window.close()"/>
	            	&nbsp;
            	</span>
            	<a  href="http://32.142.11.53:9090/foodSafety/control/ceb/ceb.rar" target="_blank">ceb安装文档</a>
            	&nbsp;
            	<a  href="http://32.142.11.53:9090/foodSafety/control/ntko/ntko.doc" target="_blank">ntko控件帮助文档</a>
            </div>
            
        <div id="taoda">
            <!-- 加载ntko控件 -->
            <object id="TANGER_OCX" classid="clsid:6AA93B0B-D450-4a80-876E-3909055B0640" codebase="http://32.142.11.53:9090/foodSafety/control/ntko/ofctnewclsid.cab#version=5,0,2,7" width="100%" height="100%">
			    <param name="BorderStyle" value="1">
			    <param name="BorderColor" value="14402205">
			    <param name="TitlebarColor" value="15658734">
			    <param name="TitlebarTextColor" value="0">
			    <param name="IsShowToolMenu" value="-1">
			    <param name="IsUseUTF8URL" value="-1">
			    <param name="IsUseUTF8Data" value="-1">
			    <param name="IsShowNetErrorMsg" value="-1">
			    <param name="MaxUploadSize" value="10000000">
			    <param name="CustomMenuCaption" value="我的菜单">
			    <param name="MenubarColor" value="14402205">
				<param name="MakerCaption" value="江苏中威科技软件系统有限公司">
				<param name="MakerKey" value="866CF5B5DB3510905937E18F071E26313A3F4DF4">
				<param name="ProductCaption" value="江苏中威科技软件系统有限公司"> 
				<param name="ProductKey" value="03450F2A369FA8790329FC67D839D116B4B2F065">
			    <param NAME="MenuButtonColor" value="16180947">
			    <param name="MenuBarStyle" value="3">
			    <param name="MenuButtonStyle" value="7">
			    <span STYLE="color:red">
			    	<a href="${ctx}/control/ntko/ntko.doc">无法加载ntko控件帮助文档</a>
			    	&nbsp;&nbsp;
			    	不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。
			    </span>
			</object>
		</div>
		
	</body> 
	<script type="text/javascript" src="${ctx}/widgets/common/js/god_Core.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/common/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/control/ntko/tangerocx.js"></script>
	<script type="text/javascript" src="${ctx}/control/ceb/CebCaAbout.js"></script>
	<script type="text/javascript">
		function openfile(id){
	        var docURL = '${ctx}/attachment_download.do?id=' + id;
	        var aa = TANGER_OCX_Init(docURL);
	        dt();
	    }
		/*
			套打(只多不少)
			对象:foodInspection
			属性:
				sample_id				样品流水号
				sample_name 			样品名称
				authorize_org_address	委托单位地址
				inspection_org			检验检测机构
				authorize_org			委托单位
				dep_ins_id				部门检测编号
				product_kinds			食品类别名称
				sample_package			样品包装
				grade					规格
				sample_num				样品数量
				//production_date			生产日期
				production_date_num			生产日期或批号
				sample_status			包装状况
				product_org				生产单位
				product_org_address		生产单位地址
				sampling_org			被抽样单位
				sampling_address		被抽样单位地点
				sampling_area			抽样地区
				sampling_character		抽样性质
				sampling_person			抽样人
				sampling_send			送样人
				sample_recieve_time		收样时间
				ins_start_date			检验开始日期
				ins_end_date			检验结束日期
				ins_method				检验依据和方法
				ins_project				检验项目
		*/
        function dt(){
			var depId='${departmentId}';
			/**************************水产品 ******************************/
        	if(depId=='{7F000001-FFFF-FFFF-E2AE-096E00000025}'){
				//报告编号3
        		for(var i=0;i<3;i++){
					CopyTextToBookMark("${foodInspection.dep_ins_id}", "报告编号"+i);
				};
				//产品名称2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.sample_name}", "产品名称"+i);
				};
				//委托单位2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.authorize_org}", "委托单位"+i);
				};
				//检验类别
				CopyTextToBookMark("${foodInspection.product_kinds}", "检验类别");
				//样品数量
				CopyTextToBookMark("${foodInspection.sample_num}", "样品数量");
				//样品流水号
				CopyTextToBookMark("${foodInspection.sample_id}", "样品编号");
				//样品状态
				CopyTextToBookMark("${foodInspection.sample_status}", "样品状态");
				//收样日期
				CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "收样日期");
				//抽样地点
				CopyTextToBookMark("${foodInspection.sampling_org}", "抽样地点");
				//抽样者
				CopyTextToBookMark("${foodInspection.sampling_person}", "抽样者");
				//检验依据
				CopyTextToBookMark("${foodInspection.ins_method}", "检验依据");
				//实验环境条件
				
				//所用主要仪器
				
				//检验项目
				CopyTextToBookMark("${foodInspection.ins_project}", "检验项目");
				//检验结论
				
				//备注
				
				//批准
				CopyTextToBookMark("${foodInspection.doperson}", "批准");
				//批准时间
				CopyTextToBookMark("${foodInspection.dotime}".substr(0,10), "批准时间");
				//审核
				CopyTextToBookMark("${foodInspection.re_approve_person}", "审核");
				//审核时间
				CopyTextToBookMark("${today}", "审核时间");
				//制表
				CopyTextToBookMark("${username}", "制表");
				//制表时间
				CopyTextToBookMark("${today}", "制表时间");
	
				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark("${foodInspection.sample_id}", "样品编号"+count);
					CopyTextToBookMark("${index.foodIndex.name}", "检测项目"+count);
					CopyTextToBookMark("${index.test_value}", "检验结果"+count);
					CopyTextToBookMark("${index.foodIndex.judgeStandard}", "标准值"+count);
					CopyTextToBookMark("${index.foodIndex.method}", "检测方法"+count);
					CopyTextToBookMark("${index.result}", "单项判定"+count);
					CopyTextToBookMark("${index.foodIndex.value}", "检出限"+count);
					CopyTextToBookMark("${index.foodIndex.des}", "备注"+count);
					count++;
				</c:forEach>
			/**************************疾病预防控制中心 ******************************/	
        	}else  if(depId=='{BFA811EA-0000-0000-4549-941D0000006C}'){
				//检字5
				for(var i=0;i<5;i++){
					CopyTextToBookMark("${foodInspection.dep_ins_id}", "检字"+i);
				};
		    	//样品名称3
				for(var i=0;i<3;i++){
					CopyTextToBookMark("${foodInspection.sample_name}", "样品名称"+i);
				};
				//受检单位3
				for(var i=0;i<3;i++){
					CopyTextToBookMark("${foodInspection.sampling_org}", "受检单位"+i);
				};
				//年月日3
				for(var i=0;i<3;i++){
					CopyTextToBookMark("${today_nyr}", "年月日"+i);
				};
				//商标、生产单位、受检单位地址、采集地点、检测类别、采样日期、包装情况、样品数量、样品性状
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.brand}", "商标"+i);
					CopyTextToBookMark("${foodInspection.product_org}", "生产单位"+i);
					CopyTextToBookMark("${foodInspection.sampling_address}", "受检单位地址"+i);
					CopyTextToBookMark("", "采集地点"+i);
					CopyTextToBookMark("${foodInspection.product_kinds}", "检测类别"+i);
					CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "采样日期"+i);
					CopyTextToBookMark("${foodInspection.sample_package}", "包装情况"+i);
					CopyTextToBookMark("${foodInspection.sample_num}", "样品数量"+i);
					CopyTextToBookMark("${foodInspection.sample_status}", "样品性状"+i);
				};
				//评价依据
				CopyTextToBookMark("${foodInspection.ins_method}", "评价依据");
				//结论
				
				//编制2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.tester}", "编制"+i);
				};
				//审核0
				CopyTextToBookMark("${foodInspection.re_approve_person}", "审核0");
				//授权签字人2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${username}", "授权签字人"+i);
				};
				//样品流水号
				CopyTextToBookMark("${foodInspection.sample_id}", "样品编号");
				//检测依据
				CopyTextToBookMark("${foodInspection.ins_method}", "检测依据");

				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark("${index.foodIndex.name}", "检测项目"+count);//
					CopyTextToBookMark("${index.test_value}", "结果"+count);
					CopyTextToBookMark("${index.foodIndex.judgeStandard}", "标准值"+count);
					count++;
				</c:forEach>

				//审核理化
				CopyTextToBookMark("${foodInspection.re_approve_person}", "审核理化");
				//审核微检
				

				//检测环境条件

				//仪器编号、仪器名称、仪器型号

			/**************************药品检验所 ******************************/	
		    }else if(depId=='{7F000001-FFFF-FFFF-EA16-E32100000008}'){
				//报告书编号2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.dep_ins_id}", "报告书编号"+i);
				};
				//检品名称2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.sample_name}", "检品名称"+i);
				};
				//供样单位2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.sampling_org}", "供样单位"+i);
				};
				//检验目的2

				//授权签字人
				CopyTextToBookMark("${foodInspection.doperson}", "授权签字人");
				//签发日期
				CopyTextToBookMark("${today_nyr}", "签发日期");
				//检品编号
				CopyTextToBookMark("${foodInspection.sample_id}", "检品编号");
				//批号
				CopyTextToBookMark("${foodInspection.production_date_num}", "批号");
				//产地
				CopyTextToBookMark("${foodInspection.product_org_address}", "产地");
				//检验项目
				CopyTextToBookMark("${foodInspection.ins_project}", "检验项目");
				//委托单位
				CopyTextToBookMark("${foodInspection.authorize_org}", "委托单位");
				//检验依据
				CopyTextToBookMark("${foodInspection.ins_method}", "检验依据");
				//规格
				CopyTextToBookMark("${foodInspection.grade}", "规格");
				//包装
				CopyTextToBookMark("${foodInspection.sample_package}", "包装");
				//有效期

				//检品数量
				CopyTextToBookMark("${foodInspection.sample_num}", "检品数量");
				//收验日期
				CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "收验日期");
				//报告日期
				CopyTextToBookMark("${today_nyr}", "报告日期");
				
				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark("${index.foodIndex.name}", "检验项目"+count);//
					CopyTextToBookMark("${index.test_value}", "检验结果"+count);
					CopyTextToBookMark("${index.foodIndex.judgeStandard}", "标准规定"+count);
					count++;
				</c:forEach>

				//结论
				
			/**************************畜产品 ******************************/	
		    }else if(depId=='{7F000001-FFFF-FFFF-EA05-9FCF00000001}'){
				//编号2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.dep_ins_id}", "编号"+i);
				};
				//样品名称
				CopyTextToBookMark("${foodInspection.sample_name}", "样品名称");
				//生产单位
				CopyTextToBookMark("${foodInspection.product_org}", "生产单位");
				//委托单位
				CopyTextToBookMark("${foodInspection.authorize_org}", "委托单位");
				//检品名称
				CopyTextToBookMark("${foodInspection.sample_name}", "检品名称");
				//动物品种

				//检验类别
				CopyTextToBookMark("${foodInspection.product_kinds}", "检验类别");
				//检验项目
				CopyTextToBookMark("${foodInspection.ins_project}", "检验项目");
				//检验依据
				CopyTextToBookMark("${foodInspection.ins_method}", "检验依据");
				//判定依据

				//样品流水号
				CopyTextToBookMark("${foodInspection.dep_ins_id}", "样品编号");//部门检测编号
				//包装
				CopyTextToBookMark("${foodInspection.sample_package}", "包装");
				//样品状态
				CopyTextToBookMark("${foodInspection.sample_status}", "样品状态");
				//送样人
				CopyTextToBookMark("${foodInspection.sampling_send}", "送样人");
				//检品数量
				CopyTextToBookMark("${foodInspection.sample_num}", "检品数量");
				//收样日期
				CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "收样日期");
				//报告日期
				CopyTextToBookMark("${today_nyr}", "报告日期");
				//检品数量
				CopyTextToBookMark("${foodInspection.sample_num}", "检品数量");
				//检品数量
				CopyTextToBookMark("${foodInspection.sample_num}", "检品数量");
				//温度

				//湿度

				//仪器名称、型号、仪器编号

				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark("${foodInspection.sample_id}", "样品原号"+count);
					CopyTextToBookMark("${index.foodIndex.name}", "检验项目"+count);//
					CopyTextToBookMark("${index.test_value}", "检验结果"+count);
					CopyTextToBookMark("${index.foodIndex.judgeStandard}", "技术指标"+count);
					CopyTextToBookMark("${index.result}", "单项判定"+count);
					count++;
				</c:forEach>
				//备注

				//结论

				//编制
				CopyTextToBookMark("${foodInspection.tester}", "编制");
				//审核
				CopyTextToBookMark("${foodInspection.re_approve_person}", "审核");
				//批准
				CopyTextToBookMark("${foodInspection.doperson}", "批准");

			/**************************粮检 ******************************/
		    }else if(depId=='{7F000001-FFFF-FFFF-E2B1-A97C0000002E}'){
				//检验报告编号3
				for(var i=0;i<3;i++){
					CopyTextToBookMark("${foodInspection.dep_ins_id}", "检验报告编号"+i);
				};
				//产品名称2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.sample_name}", "产品名称"+i);
				};
				//委托单位2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.authorize_org}", "委托单位"+i);
				};
				//检验类别2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.product_kinds}", "检验类别"+i);
				};
				//单位地址
				CopyTextToBookMark("${foodInspection.authorize_org_address}", "单位地址");
				//生产单位
				CopyTextToBookMark("${foodInspection.product_org}", "生产单位");
				//抽样地点
				CopyTextToBookMark("${foodInspection.sampling_org}", "抽样地点");
				//样品数量
				CopyTextToBookMark("${foodInspection.sample_num}", "样品数量");
				//样品状态
				CopyTextToBookMark("${foodInspection.sample_status}", "样品状态");
				//检验依据
				CopyTextToBookMark("${foodInspection.ins_method}", "检验依据");
				//规格
				
				//商标
				CopyTextToBookMark("${foodInspection.brand}", "商标");
				//电话
				
				//邮编
				
				//产品等级
				CopyTextToBookMark("${foodInspection.grade}", "产品等级");
				//抽样日期
				CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "抽样日期");
				//抽样基数
				
				//批号
				CopyTextToBookMark("${foodInspection.production_date_num}", "批号");
				//检验结论
				
				//签发日期
				CopyTextToBookMark("${today_nyr}", "签发日期");
				//检验环境
				
				//备注
				
				//批准人
				CopyTextToBookMark("${foodInspection.doperson}", "批准人");
				//审核人
				CopyTextToBookMark("${foodInspection.re_approve_person}", "审核人");
				//编制人
				CopyTextToBookMark("${foodInspection.tester}", "编制人");
				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark((count+1)+"", "序号"+count);
					CopyTextToBookMark("${index.foodIndex.name}", "检验项目"+count);
					//CopyTextToBookMark("${index.foodIndex.judgeStandard}", "计量单位"+count);
					CopyTextToBookMark("${index.foodIndex.judgeStandard}", "技术要求"+count);//index.foodIndex.method
					CopyTextToBookMark("${index.test_value}", "检验结果"+count);
					CopyTextToBookMark("${index.result}", "单项评价"+count);
					count++;
				</c:forEach>
				//仪器序号、名称、规格型号、仪器编号、量程、精度
				
				
				
				/***********米面油专用************/
				//样品名称
				CopyTextToBookMark("${foodInspection.sample_name}", "样品名称");
				//生产单位
				CopyTextToBookMark("${foodInspection.product_org}", "生产单位");
				//样品来源
				
				//样品流水号
				CopyTextToBookMark("${foodInspection.dep_ins_id}", "样品编号");
				//标示等级
				CopyTextToBookMark("${foodInspection.grade}", "标示等级");
				//送样日期
				CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "送样日期");
				//检验依据
				CopyTextToBookMark("${foodInspection.ins_method}", "检验依据");
				//结论
				
				//备注
				
				//签发批准人
				CopyTextToBookMark("${username}", "签发批准人");
				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark((count+1)+"", "序号"+count);
					CopyTextToBookMark("${index.foodIndex.name}", "检验项目"+count);
					CopyTextToBookMark("${index.test_value}", "检验结果"+count);
					count++;
				</c:forEach>

				
			/**************************质监所 ******************************/	
		    }else if(depId=='{7F000001-FFFF-FFFF-E299-2A620000000F}'){ 
				//编号3
				for(var i=0;i<3;i++){
					CopyTextToBookMark("${foodInspection.dep_ins_id}", "编号"+i);
				};
				//产品名称2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.sample_name}", "产品名称"+i);
				};
				//受检单位
				CopyTextToBookMark("${foodInspection.sampling_org}", "受检单位");
				//生产单位
				CopyTextToBookMark("${foodInspection.product_org}", "生产单位");
				//委托单位
				CopyTextToBookMark("${foodInspection.authorize_org}", "委托单位");
				//检验类别2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.product_kinds}", "检验类别"+i);
				};
				//生产日期
				CopyTextToBookMark("${foodInspection.production_date_num}", "生产日期");
				//规格型号
				CopyTextToBookMark("${foodInspection.grade}", "规格型号");
				//商标
				CopyTextToBookMark("${foodInspection.brand}", "商标");
				//受检单位名称
				CopyTextToBookMark("${foodInspection.sampling_org}", "受检单位名称");
				//受检单位地址
				CopyTextToBookMark("${foodInspection.sampling_address}", "受检单位地址");
				//受检单位固定电话
				CopyTextToBookMark("----", "受检单位固定电话");
				//受检单位固定手机
				CopyTextToBookMark("----", "受检单位固定手机");
				//受检单位邮箱
				CopyTextToBookMark("----", "受检单位邮箱");
				//生产单位名称
				CopyTextToBookMark("${foodInspection.product_org}", "生产单位名称");
				//生产单位地址
				CopyTextToBookMark("${foodInspection.product_org_address}", "生产单位地址");
				//生产单位固定电话
				CopyTextToBookMark("----", "生产单位固定电话");
				//生产单位固定手机
				CopyTextToBookMark("----", "生产单位固定手机");
				//生产单位邮箱
				CopyTextToBookMark("----", "生产单位邮箱");
				//样品数量
				CopyTextToBookMark("${foodInspection.sample_num}", "样品数量");
				//抽样日期
				CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "抽样日期");
				//样品等级
				CopyTextToBookMark("${foodInspection.grade}", "样品等级");
				//样品到达日期
				
				//检测开始时间
				CopyTextToBookMark("${foodInspection.ins_start_date}".substr(0,10), "检测开始时间");
				//检测结束时间
				CopyTextToBookMark("${foodInspection.ins_end_date}".substr(0,10), "检测结束时间");
				//任务来源
				
				//抽样基数
				
				//抽样人员
				CopyTextToBookMark("${foodInspection.sampling_person}", "抽样人员");
				//准样量及封存地点
				
				//样品状态
				CopyTextToBookMark("${foodInspection.sample_status}", "样品状态");
				//抽样单编号
				
				//样品流水号
				CopyTextToBookMark("${foodInspection.sample_id}", "样品编号");
				//抽样地点
				
				//封样状态
				
				//检查封样人员
				
				//检测地点
				CopyTextToBookMark("${foodInspection.authorize_org_address}", "检测地点");
				//检验依据
				CopyTextToBookMark("${foodInspection.ins_method}", "检验依据");
				//检验结论

				//备注1

				//批准
				CopyTextToBookMark("${foodInspection.doperson}", "批准");
				//审核
				CopyTextToBookMark("${foodInspection.re_approve_person}", "审核");
				//主检
				CopyTextToBookMark("${foodInspection.tester}", "主检");
				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark((count+1)+"", "序号"+count);
					CopyTextToBookMark("${index.foodIndex.name}", "检验项目"+count);
					CopyTextToBookMark("${index.foodIndex.judgeStandard}", "单位"+count);
					CopyTextToBookMark("${index.foodIndex.method}", "技术要求"+count);
					CopyTextToBookMark("${index.test_value}", "检验结果"+count);
					CopyTextToBookMark("${index.result}", "单项评价"+count);
					count++;
				</c:forEach>

			/**************************检验检疫局综合技术中心实验室 ******************************/
		    }else if(depId=='{7F000001-FFFF-FFFF-E2A1-D42C00000019}'){
				//检测编号
				CopyTextToBookMark("${foodInspection.dep_ins_id}", "检测编号");
				//日期
				CopyTextToBookMark("${today_nyr}", "日期");
				//申请人
				CopyTextToBookMark("${foodInspection.authorize_org}", "申请人");
				//样品名称00
				CopyTextToBookMark("${foodInspection.sample_name}", "样品名称00");
				//样品来源
				
				//收样日期
				CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "收样日期");
				//检测开始时间
				CopyTextToBookMark("${foodInspection.ins_start_date}".substr(0,10), "检测开始时间");
				//检测结束时间
				CopyTextToBookMark("${foodInspection.ins_end_date}".substr(0,10), "检测结束时间");
				//检测结果

				//样品流水号
				CopyTextToBookMark("${foodInspection.sample_id}", "样品编号");
				//样品名称
				CopyTextToBookMark("${foodInspection.sample_name}", "样品名称");
				//样品标识
				
				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark("${index.foodIndex.name}", "检测项目"+count);
					CopyTextToBookMark("${index.foodIndex.method}", "检测方法"+count);
					CopyTextToBookMark("${index.test_value}", "测试值"+count);
					count++;
				</c:forEach>
				//授权签字人
				CopyTextToBookMark("${username}", "授权签字人");
				//签证日期
				CopyTextToBookMark("${today_nyr}", "签证日期");
				
			/**************************市农产品质量检验测试中心 ******************************/
		    }else if(depId=='{7F000001-FFFF-FFFF-E2A8-D66000000020}'){
				//检测编号
				CopyTextToBookMark("${foodInspection.dep_ins_id}", "检测编号");
				//产品名称2
				for(var i=0;i<2;i++){
					CopyTextToBookMark("${foodInspection.sample_name}", "产品名称"+i);
				};
				//委托单位
				CopyTextToBookMark("${foodInspection.authorize_org}", "委托单位");
				//规格型号
				
				//商标
				CopyTextToBookMark("${foodInspection.brand}", "商标");
				//受检单位
				CopyTextToBookMark("${foodInspection.sampling_org}", "受检单位");
				//生产单位
				CopyTextToBookMark("${foodInspection.product_org}", "生产单位");
				//抽样地点
				CopyTextToBookMark("${foodInspection.sampling_address}", "抽样地点");
				//样品数量
				CopyTextToBookMark("${foodInspection.sample_num}", "样品数量");
				//抽样基数
				
				//样品状态
				CopyTextToBookMark("${foodInspection.sample_status}", "样品状态");
				//检验依据
				CopyTextToBookMark("${foodInspection.ins_method}", "检验依据");
				//样品等级
				CopyTextToBookMark("${foodInspection.grade}", "样品等级");
				//送样日期
				CopyTextToBookMark("${foodInspection.sample_recieve_time}".substr(0,10), "送样日期");
				//送样者
				CopyTextToBookMark("${foodInspection.sampling_send}", "送样者");
				//生产日期
				CopyTextToBookMark("${foodInspection.production_date_num}", "生产日期");
				//检测项目
				CopyTextToBookMark("${foodInspection.ins_project}", "检测项目");
				//检验开始时间
				CopyTextToBookMark("${foodInspection.ins_start_date}".substr(0,10), "检验开始时间");
				//检验结束时间
				CopyTextToBookMark("${foodInspection.ins_end_date}".substr(0,10), "检验结束时间");
				//检验结论

				//主要检验仪器

				//实验环境条件

				//备注

				//批准
				CopyTextToBookMark("${foodInspection.doperson}", "批准");
				//批准日期
				CopyTextToBookMark("${foodInspection.dotime}".substr(0,10), "批准日期");
				//审核
				CopyTextToBookMark("${foodInspection.re_approve_person}", "审核");
				//审核日期
				CopyTextToBookMark("${today}", "审核日期");
				//制表
				CopyTextToBookMark("${username}", "制表");
				//制表时间
				CopyTextToBookMark("${today}", "制表时间");	
				//样品名称
				CopyTextToBookMark("${foodInspection.sample_name}", "样品名称");
				//样品流水号
				CopyTextToBookMark("${foodInspection.sample_id}", "样品编号");
				//采样地点
				CopyTextToBookMark("${foodInspection.sampling_address}", "采样地点");
				var count=0;//指标值计数器
				<c:forEach var="index" items="${indexList}">
					CopyTextToBookMark("${index.foodIndex.name}", "检测项目"+count);
					CopyTextToBookMark("${index.foodIndex.value}", "最低检出限"+count);
					CopyTextToBookMark("${index.foodIndex.judgeStandard}", "标准值"+count);
					CopyTextToBookMark("${index.test_value}", "检测结果"+count);
					count++;
				</c:forEach>				
		    };


		    
        };

		var isFirstGaizhang=true;
		var docid=null;
		var docName=null;
		var modelId=null;
		var realpath=null;
		var success=false;//盖章是否成功
        function ok(){ 
            if(isFirstGaizhang){
            	isFirstGaizhang=false;
            	if(modelId==''){
					alert('请先选择模板');
					return false;
	            }
	            modelId=document.getElementById('model').value;
	            
	            //保存当前签发doc流至服务端并在服务端生成doc文件同任务流程相绑定
	            var url="${ctx}/ceb_saveDoc.do?foodInspectionId=${foodInspection.id}";
	            //暂时以模板名称 作为绑定任务流程的doc文件名称
	            var docname=g.g('model').options[g.g('model').selectedIndex].innerHTML;
	            
	            var re=saveFileToUrl(url,docname); 
				//异步获取上传成功后的doc信息
				$.ajax({
				        async:true,//是否异步
				        type:"POST",//请求类型post\get
				        cache:false,//是否使用缓存
				        dataType:"text",//返回值类型
				        data:{"foodInspectionId":"${foodInspection.id}"},
				        url:"${ctx}/foodInspection_getDocId.do",
				        success:function(text){
				        	if(text!=''){
					        	var params=text.split(/,/);
					        	docid=params[0];
					    		docName=params[1];
				        		createCeb(docid,docName,modelId);
				        	}else if(text=='-1'){
								alert('数据库错误');
				        	};
				        }
				    });
            }else{
            	createCeb(docid,docName,modelId);
            };
			
        };
        function createCeb(docid,docName,modelId){
			//下载doc文件至本地，用于转换成ceb文件
			var docUrl="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${ctx}/attachment_download.do?id="+docid+'&fileName='+docName;
			//转换ceb并获得ceb存放在本地的绝对路径
			if(realpath==null||realpath==''){
				realpath=wordToCEB(docUrl);
			}
			
			if(!realpath){
				return;
			}
			//从公章服务器获得数据开始盖章(就是重写盖章前的ceb)
			var LDAPProxySvr = "<%=SystemParamConfigUtil.getParamValueByParam("LDAPProxySvr")%>";
			var AffixRegisterURL = "<%=SystemParamConfigUtil.getParamValueByParam("StampServer_AffixRegisterURL")%>";
			var PrintURL = "<%=SystemParamConfigUtil.getParamValueByParam("StampServer_Printerror")%>"; 
			success=visualstampclient(realpath,LDAPProxySvr,AffixRegisterURL,PrintURL);
        };
        function qianfa(){
            if(!realpath){
				alert('请先点击盖章按钮,成功盖章过后方可签发报告!');
				return;
            }
			if(success){//盖章成功后
				var cebName=realpath.match(/\d+\.ceb/)+'';
				var uploadUrl="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${ctx}/ceb_saveCeb.do?foodInspectionId=${foodInspection.id}&cebName="+cebName;
				//alert(realpath+'---------'+uploadUrl);
				var uploadSuccess=uploadLocalFile(uploadUrl,realpath);//把盖章后的ceb文件上传
				if(uploadSuccess){
					//更改状态，保存数据
		        	g.g('form1').action='${ctx }/foodInspection_reportDo.do?state=2&foodInspectionId=${foodInspection.id}&modelId='+modelId;
					g.g('form1').submit();
				};
			}else{
				alert('上一次盖章未成功，成功盖章过后方可签发报告!');
			};
        }
        function print(){
        	var modelId=document.getElementById('model').value;
            if(modelId==''){
				alert('请先选择模板');
				return false;
            }
        	try
        	{
            	//document.all("TANGER_OCX")
        		 TANGER_OCX_OBJ.printout(true);
        	}
        	catch(err){
				
            };
        };
	</script>
</html>
