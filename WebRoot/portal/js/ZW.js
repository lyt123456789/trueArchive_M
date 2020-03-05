(	
	function(){
		window._ZW_ZW=window.ZW;//存储变量,用于返回控制权
		window.ZW={};ZW.fn={};ZW.cl={};
		//注册命名空间
		ZW.fn.namespace=function(s){
			var names=s.split(/\./);
			var f=window.ZW;
			for(var i=0;i<names.length;i++){
				if(!f[names[i]]){
					f[names[i]]={};
				};
				f=f[names[i]];
			};
		};
		//返回控制权限给其它使用ZW的类
		ZW.fn.noConflict=function(){
			window.ZW=window._ZW_ZW;
		};
	}
)();
//扩展类库示例如下
//1、添加命名空间   根目录为ZW
//ZW.fn.namespace('fn.math');
//2、添加属于该命名空间的方法
//ZW.fn.math.radom=function(){alert("I'am a new method named radom");};
//3、使用新加的方法
//ZW.fn.math.radom();

ZW.cl.Validation=function(){
	this.config={
		//注意des描述名称不能重复,例('notnull','date')
		'rgExp':[
			//非空判断
			{'type':'notnull','des':'非空判断','fmt':/[^\s]+/,'mes':'不能为空!'},
			//日期			
			{'type':'date','des':'日期','fmt':/^[1-9]\d{3}-\d[1-9]-\d[1-9]$/,'mes':'格式不正确!\n正确格式为: (2011-01-15)\n*注意1、月份和天数不足10时补0的情况\n*注意2、月份和天数不可能出现00的情况'},
			//时间				
			{'type':'time','des':'时间','fmt':/^\d{2}:\d{2}:\d{2}$/,'mes':'格式不正确!\n正确格式为: (12:24:30)\n*注意时分秒不足10时补0的情况'},
			//日期时间				
			{'type':'datetime','des':'日期时间','fmt':/^[1-9]\d{3}-\d[1-9]-\d[1-9]\s{1}\d{2}:\d{2}:\d{2}$/,'mes':'格式不正确!\n正确格式为: (2011-01-15 12:24:30)\n*注意日期和时间之间只有一个空格'},
			//电话号码			
			{'type':'phone','des':'电话号码','fmt':/^\d{3,4}-\d{7,8}$/,'mes':'格式不正确!\n*注意区号为3到4位数字,号码为7到8位数字'},	
			//手机号码		
			{'type':'mobile','des':'手机号码','fmt':/^[1-9]\d{10}$/,'mes':'格式不正确!\n*注意手机号码应为11位'},
			//电话号码和手机号码		
			{'type':'telephone','des':'电话号码和手机号码','fmt':/(^\d{3,4}-\d{7,8}$)|(^[1-9]\d{10}$)/,'mes':'格式不正确!\n*注意1、区号3到4位,号码7到8位\n*注意2、手机号码应为11位'},	
			//电子邮件		
			{'type':'email','des':'电子邮件','fmt':/^[\w\d]+@[\w\d]+(\.[\w\d]+)+$/,'mes':'格式不正确!\n*注意1：必须包含@和.两个符号\n*注意2：@必须在.前面,且@和.两边都应该有字符包含'},
			//身份证号码		
			{'type':'sid','des':'身份证号码','fmt':/(^\d{15}$)|(^\d{17}[0-9xX]$)/,'mes':'格式不正确!\n*注意：18位的身份证号码的最后一位数可以为x'},
			//整数
			{'type':'number','des':'整数','fmt':/(^0$)|(^-?[1-9]\d*$)/,'mes':'只能为整数!包括正整数、负整数和零!'},
			//小数
			{'type':'dnumber','des':'小数','fmt':/^\d+(\.\d+)?$/,'mes':'只能为整数、小数点！'},
			//正整数
			{'type':'pnumber','des':'正整数','fmt':/(^[1-9]\d*$)/,'mes':'只能为正整数!不包括零!'},
			//负整数
			{'type':'nnumber','des':'负整数','fmt':/(^-[1-9]\d*$)/,'mes':'只能为负整数!不包括零!'},
			//字母
			{'type':'word','des':'字母','fmt':/^\w+$/i,'mes':'只能为字母!'},
			//中文，支持繁体字
			{'type':'character','des':'中文(支持繁体字)','fmt':/^[\u4e00-\u9fa5]+$/,'mes':'只能为中文!'},
			//正浮点数
			{'type':'zfloat','des':'正浮点数','fmt':/(^0$)|(^-?(([0-9]+\.[0-9]+)|([0-9]*))$)/,'mes':'必须为正数!'}
		],
		'remindType':'alert'		//提醒方式,默认采用alert()弹出框的形式提醒
	};
	this.setRgExp=function(o){
		if(typeof(o)!='undefined'&&typeof(o)=='object'){
			var rgExps=this.config.rgExp;
			for(var i=0;i<o.length;i++){
				var isin=false;
				for(var j=0;j<rgExps.length;j++){
					if(o[i].type==rgExps[j].type){
						this.config.rgExp[j]=o[i];
						isin=true;break;
					}
				}
				if(!isin){this.config.rgExp[rgExps.length]=o[i];}
			}
		}else{
			alert('setConfig()方法传参出错！\n参数必须为对象\n例:{"null":/./,"date":/./}');
		}
	};
	this.excute=function(data){
		if(typeof(data)!='undefined'&&typeof(data)=='object'){ 
			for(var i=0;i<data.length;i++){//遍历数据源集合
				var o=this.g(data[i].id);
				if(o!=null){
					var types=data[i]['types'].split(',');
					//判断是否强制验证,当验证类型中不包含非空验证时,如果是空数据的话不对其进行验证
					var isHave_notnull;
					if(data[i]['types'].match('notnull'))isHave_notnull=true;
					else isHave_notnull=false;
					
					for(var j=0;j<types.length;j++){//遍历每条数据的types属性
						var exp=this.getRgExpByType(types[j]);
						if(exp!=null){
							if(isHave_notnull||(!isHave_notnull&&o.value!='')){
								if(o.value.match(exp.fmt)==null){
									var remindstr=data[i]['mes']+exp['mes'];
									this.remind(o,remindstr);
									return false;
								}
							}
						}else{
							alert('验证类型'+types[i]+'不存在,请编辑');
							return false;
						}
					}
				}else{
					alert('id为'+data[i].id+'的对象不存在,请编辑');
					break;
				}
			}
		}else{
			alert('excute()方法传参出错！\n参数必须为对象\n例:{"id":"notnull,date,email"}');
		}
		return true;
	};
	this.remind=function(o,s){ //本方法可扩展
		if(this.config.remindType=='alert'){
			this.alert(o,s);
		}
	};
	this.alert=function(o,s){
		alert(s);
		o.select();
	};
	this.g=function(id){
		return document.getElementById(id);
	};
	this.getRgExpByType=function(type){
		var returnvalue=null;
		var rgExps=this.config.rgExp;
		for(var i=0;i<rgExps.length;i++){
			if(rgExps[i].type==type){
				returnvalue=rgExps[i];
				break;
			}
		}
		return returnvalue;
	};
	this.view=function(){
		var returnstr='当前Validation验证类所有验证类型如下:\n';
		var rgExps=this.config.rgExp;
		for(var i=0;i<rgExps.length;i++){
			returnstr+=rgExps[i].type+':'+rgExps[i].des+'\n';
		}
		alert(returnstr);
	};
};
//使用ntko编辑保存doc
/*
 * docid:模板id(表FC_FILE中)
 * docname:模板名称
 * url:站点前缀(无需改动)
 * nodeid:步骤节点id(表NTKO_NODE中)
 * attachment_type:附件类型(不为空''代表可以再次编辑)
 * isview:附件docid来源(有该参数为从attachment表来，没有则为file表来)
 * */
ZW.fn.saveNtko=function(docid,docname,url,nodeid,attachment_type,isview){
	window.god_docname=docname;
	window.god_divId='div_upload';
	window.god_hiddenDocId='upload_docid';
	if(attachment_type&&attachment_type!=''){
		window.god_attachment_type=attachment_type;
	}
	if(isview&&isview!=''){
		window.god_isview=isview;
	}
	url=url+"/ntko_createBookMarkData.do?docid="+docid+"&d="+Math.random();
	if(nodeid&&nodeid!=''){url+=url+'&nodeid='+nodeid;};
	if(attachment_type&&attachment_type!=''){url+=url+'&attachment_type='+attachment_type;};
	var god_width=950;//(window.screen.width*9)/10;
	var god_height=700;//(window.screen.height*9)/10;
	var returnvalue=window.showModalDialog(url+'',window,'dialogWidth: '+god_width+'px;dialogHeight: '+god_height+'px; status: no; scrollbars: no; Resizable: no; help: no;');
	if(returnvalue&&returnvalue=='close'){
		alert('文档编辑成功！');
	};
	window.god_docname=null;
	window.god_attachment_type=null;
	window.god_isview=null;
};
//用于一个页面中编辑多个文档--何登--2012-03-05
ZW.fn.saveNtkoPassDivId=function(docid,docname,url,nodeid,attachment_type,isview,divId,hiddenId){
	window.god_docname=docname;
	window.god_divId=divId;
	window.god_hiddenDocId=hiddenId;
	if(attachment_type&&attachment_type!=''){
		window.god_attachment_type=attachment_type;
	}
	if(isview&&isview!=''){
		window.god_isview=isview;
	}
	url=url+"/ntko_createBookMarkData.do?docid="+docid+"&d="+Math.random();
	if(nodeid&&nodeid!=''){url+=url+'&nodeid='+nodeid;};
	if(attachment_type&&attachment_type!=''){url+=url+'&attachment_type='+attachment_type;};
	var god_width=950;//(window.screen.width*9)/10;
	var god_height=700;//(window.screen.height*9)/10;
	var returnvalue=window.showModalDialog(url+'',window,'dialogWidth: '+god_width+'px;dialogHeight: '+god_height+'px; status: no; scrollbars: no; Resizable: no; help: no;');
	if(returnvalue&&returnvalue=='close'){
		alert('文档编辑成功！');
	};
	window.god_docname=null;
	window.god_attachment_type=null;
	window.god_isview=null;
};

function showDigInputValue(obj,ctx){
	var value='';
	if(obj.value!=''){
		value=obj.value;
	}
	var retVal = window.showModalDialog(ctx+'/showView.jsp', value, 'dialogWidth: ' + 500 + 'px;dialogHeight: ' + 300 + 'px; status: no; scrollbars: no; Resizable: no; help: no;');
	if(retVal!=''){
		obj.value=retVal;
	}else{
		obj.value='';
	}
}