function Paging(maxindex,pagesize,selectIndex){
			this.formObj=null;//用于提交的表单对象
			this.skipUrl="";//用于提交的url

			this.paramsObj=null;//用于拼接sql字符串查询条件信息传于数据库
			this.valuesObj=null;//用于保存页面查询条件信息传于页面
			
			this.goObj=null;//用户自定跳转框对象
			this.pageRemindObj=null;//用于显示数字分页框
			
			this.deleteIdsObj=null;//用于保存批删除信息传于数据库			
			this.chcObj=null;//复选框对象
			this.chcObjs=null;//所有复选框对象
			this.deleteURL="";//批删除URL
			
			//初始化全局分页参数
			this.MaxIndex=maxindex;//信息总条数-从后台获得
			this.pageSize=pagesize;//一页显示的信息条数-从后台获得
			this.selectIndex=selectIndex;//当前页面的页数-从后台获得
			//总页数
			this.pageCount=parseInt(this.MaxIndex % this.pageSize != 0 ? this.MaxIndex / this.pageSize + 1: this.MaxIndex / this.pageSize,10);
			this.startCount=(this.selectIndex-1)*this.pageSize+1;//当前页面第一条数据条数
			this.endCount=this.selectIndex*this.pageSize<this.MaxIndex?this.selectIndex*this.pageSize:this.MaxIndex;//当前页面最后一条数据条数			
			this.startpage=0;//当前提供给用户点击分页目录开始数值
			this.endpage=0;//当前提供给用户点击分页目录结束数值
			this.maxCountPerPage=8;//一页显示的分页条数
			this.currentPagePosInMax=4;//当前页面在分页条数中的位置
			this.hasnext="...";//用于提示 是否还有下页
	
			//计算提供给用户点击分页开始结束数值
			if(this.pageCount<=this.maxCountPerPage){
				this.startpage=1;
				this.endpage=this.pageCount;
			}else if(this.selectIndex<=this.currentPagePosInMax){
				this.startpage=1;
				this.endpage=this.startpage+5;
			}else if(this.selectIndex>this.currentPagePosInMax){
				this.startpage=this.selectIndex-this.currentPagePosInMax+1;
				if(this.startpage+5<=this.pageCount){
					this.endpage=this.startpage+5;
				}else{
					this.endpage=this.pageCount;
					this.startpage=this.endpage-5;
					this.hasnext="";
				}
			}
			//把查询条件拼成字符串放入隐藏域在后台统一获取
			 this.sortStr="";
			 this.setQueryParm=function(){
				 if(this.paramsObj!=null||this.valuesObj!=null){
					var inputs=document.getElementsByTagName('input');
					var allParm="";
					var allValues="";
					for(var i=0;i<inputs.length;i++){
						var o=inputs[i];
						if(o.alt!=null&&o.alt!=""&&o.value.replace(/\s/g,'')!=""){
							if(this.paramsObj!=null)allParm+=o.name+"--"+o.value.replace(/\s/g,'')+"--"+o.alt+"||";
							if(this.valuesObj!=null)allValues+=o.id+"--"+o.value.replace(/\s/g,'')+"||";
						}
					}
					//添加查询条件给隐藏标签
					if(this.paramsObj!=null)
						this.paramsObj.value=allParm.substring(0,allParm.length-2);
					//添加查询数值给隐藏标签
					if(this.valuesObj!=null)
						this.valuesObj.value=allValues.substring(0,allValues.length-2);
					//根据参数判断是否保存排序条件
					if(this.isAlwaysSort){
						this.sortObj.value=this.sortStr;
					}
				 }
			}		
			//上一页跳转
			this.lastPage=function(){
				var isDis = $("#last_sp_page").hasClass('wf-disabled')
				if(isDis) {
					return;
				}
				this.setQueryParm();
				first=parseInt(this.selectIndex,10);				
				url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageIndex='+((first-2)*this.pageSize)+'&selectIndex='+(first-1)+'&pageSize='+this.pageSize;
				this.formObj.action=url;
				this.formObj.submit();
			}			
			//下一页跳转
			this.nextPage=function(){
				var isDis = $("#next_sp_page").hasClass('wf-disabled')
				if(isDis) {
					return;
				}
				this.setQueryParm();
				first=parseInt(this.selectIndex,10);
				url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageIndex='+(first*this.pageSize)+'&selectIndex='+(first-1+2)+'&pageSize='+this.pageSize;
				this.formObj.action=url;
				this.formObj.submit();
			}	
			//首页跳转
			this.startPage=function(){
				var isDis = $("#first_sp_page").hasClass('wf-disabled')
				if(isDis) {
					return;
				}
				this.setQueryParm();
				url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageIndex=0';
				this.formObj.action=url;
				this.formObj.submit();
			}
			//末页跳转
			this.endPage=function(){
				var isDis = $("#next_sp_page").hasClass('wf-disabled')
				if(isDis) {
					return;
				}
				this.setQueryParm();
				url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageIndex='+((this.pageCount-1)*this.pageSize)+'&selectIndex='+this.pageCount+'&pageSize='+this.pageSize;
				this.formObj.action=url;
				this.formObj.submit();
			}
			//用户填数字跳转
			this.goPage=function(objid){
				var goObj=document.getElementById(objid);
				this.setQueryParm();
				var go=goObj.value.replace(/\s/g,'');
				if(go==null||go==''){
					alert("页数不能为空!");
					goObj.value='';
					goObj.focus();
					return false;
				}
				if(go.match(/\d+/)!=go){
					alert("页数为数字!请重新输入!");
					goObj.select();
					return false;
				}
				if(go<1){
					alert("页码不能小于1!请重新输入!");
					goObj.select();
					return false;
				}
				if(parseInt(go,10)>this.pageCount){
					alert("最大页数为"+this.pageCount+",请重新输入!");
					goObj.select();
					return false;
				}
				var goCount=parseInt(go,10);
				url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageIndex='+((goCount-1)*this.pageSize)+'&selectIndex='+goCount+'&pageSize='+this.pageSize;
				this.formObj.action=url;
				this.formObj.submit();
				return true;
			}
			
			//用户选择自定义pagesize
			this.changePagesize=function(pageSize){
				this.setQueryParm();
				url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageSize='+pageSize+'&pageIndex=0';
				this.formObj.action=url;
				this.formObj.submit();
				return true;
			}
			
			//用户选择数字跳转
			this.selectPage=function(selectIndex){
				this.setQueryParm();
				url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageIndex='+((selectIndex-1)*this.pageSize)+'&selectIndex='+selectIndex+'&pageSize='+this.pageSize;
				this.formObj.action=url;
				this.formObj.submit();
			}
			
			//下拉列表框跳转
			this.changPage=function(selectIndex){ 
				this.setQueryParm();	
				url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageIndex='+((selectIndex-1)*this.pageSize)+'&selectIndex='+selectIndex+'&pageSize='+this.pageSize;
				this.formObj.action=url;
				this.formObj.submit();
			}			
			//批删除
			this.deleteAllCar=function(){
				if(this.deleteURL==''){
					alert("出错了,批删除URL为空！");
					return false;
				}
				//拼接url传参字符串
				var delIdsStr="";	
				var count=0;
				for(var i=0;i<this.chcObjs.length;i++){		
					if(this.chcObjs[i].checked==true){
						delIdsStr+=this.chcObjs[i].value+',';
						count++;
					}
				}
				if(count==0){
					alert('至少选中一条信息!');
					return false;
				}
				if(confirm('确认删除'+count+'条信息吗?')){
					alert(delIdsStr);
					this.deleteIdsObj.value=delIdsStr.substr(0,delIdsStr.length-1);
					url=this.deleteURL;
					this.formObj.action=url;
					this.formObj.submit();
				}
				return true;			
			}
			//批选取
			this.getAllChc=function(){
				var chcObjs=this.chcObjs;
				if(this.chcObj!=null&&this.chcObjs!=null){
					this.chcObj.onclick=function(){
						var ischecked=this.checked;				
						for(var i=0;i<chcObjs.length;i++){		
							chcObjs[i].checked=ischecked;
						}
					};
				}
				
			}
			
			//通用的样式选择器
			this.changeStyle=function(obj,styleType,text){
				switch(styleType){
					case "color":obj.style.color=text;break;
					case "backgroundColor":obj.style.backgroundColor=text;break;
					case "cssText":obj.style.cssText=text;break;
					case "className":obj.className=text;break;
				}
			}
			//初始化分页菜单状态
			this.setState=function(overStyle,outStyle,styleType){
				var ids=['last_sp_page','first_sp_page','next_sp_page','end_sp_page'];
				//根据id和颜色设置鼠标经过离开对象时的颜色变化
				for(var i=0;i<ids.length;i++){
					var obj=document.getElementById(ids[i]);
					if(obj!=null){
						obj.onmouseover=function(){
							if(styleType==null||styleType=="")
								this.style.color=overStyle;
							else
								paging.changeStyle(this,styleType,overStyle);
						}
						obj.onmouseout=function(){
							if(styleType==null||styleType=="")
								this.style.color=outStyle;
							else
								paging.changeStyle(this,styleType,outStyle);
						}
					}
				}
				//判断是否第一页
				if(this.selectIndex==1){
					for(var i=0;i<2;i++){
						var obj=document.getElementById(ids[i]);
						if(obj!=null){
							obj.onclick=null;
							obj.style.cursor='';
							obj.onmouseover=null;
							obj.onmouseout=null;
							$(obj).addClass('wf-disabled');
						}
					}
				}
				//判断是否最后页
				if(this.selectIndex==this.pageCount||this.pageCount==1){
					for(var i=2;i<4;i++){
						var obj=document.getElementById(ids[i]);
						if(obj!=null){
							obj.onclick=null;
							obj.style.cursor='';
							obj.onmouseover=null;
							obj.onmouseout=null;
							$(obj).addClass('wf-disabled');
						}
					}
				}
			}
			
			//条件查询
			this.query=function(){
				this.setQueryParm();
				url=this.skipUrl;
				this.formObj.action=url;
				this.formObj.submit();
				return false;
			}
			//查询全部
			this.queryAll=function(){
				var inputs=document.getElementsByTagName('input');
				for(var i=0;i<inputs.length;i++){
					var o=inputs[i];
					if(o.alt!=null&&o.alt!=""){
						o.value='';
					}
				}
				
				this.setQueryParm();
				
				url=this.skipUrl;
				this.formObj.action=url;
				this.formObj.submit();
				return false;
			}			
			//选行变色
			this.selectTrChangeTrColor=function(overStyle,outStyle,styleType){
				if(this.tableObj!=null){
					var trCount=this.tableObj.rows.length;
					for(var i=1;i<trCount;i++){//i=0代表从第二行开始，第一行默认为标题头
						var tr_obj=this.tableObj.rows[i];
						tr_obj.onmouseover=function(){
							if(styleType==null||styleType=="")
								this.style.backgroundColor=overStyle;
							else
								paging.changeStyle(this,styleType,overStyle);
						};
						tr_obj.onmouseout=function(){
							if(styleType==null||styleType=="")
								this.style.backgroundColor=outStyle;
							else
								paging.changeStyle(this,styleType,outStyle);
						};
					    
					}
				}else{alert("请设置表格对象，例:paging.tableObj=document.getElementById('o_tbl');");}
			}
			//选标题变色
			this.selectTitleChangeAllTdColor=function(overStyle,outStyle,styleType){
				var tblObj=this.tableObj;
				if(this.tableObj!=null){
					var td_objs=this.tableObj.rows[0].childNodes;
					for(var j=0;j<td_objs.length;j++){
						td_objs[j].column=j;
						td_objs[j].onmouseover=function(){
							var trCount=tblObj.rows.length;
							for(var i=1;i<trCount;i++){
								var td_objs=tblObj.rows[i].childNodes;
								if(styleType==null||styleType=="")
									td_objs[this.column].style.backgroundColor=overStyle;
								else
									paging.changeStyle(td_objs[this.column],styleType,overStyle);
								
							}
						};
						td_objs[j].onmouseout=function(){
							var trCount=tblObj.rows.length;
							for(var i=1;i<trCount;i++){
								var td_objs=tblObj.rows[i].childNodes;
								if(styleType==null||styleType=="")
									td_objs[this.column].style.backgroundColor=outStyle;
								else
									paging.changeStyle(td_objs[this.column],styleType,outStyle);
							}
						};
					}
				}else{alert("请设置表格对象，例:paging.tableObj=document.getElementById('o_tbl');");}
			}
			//选列变色
			this.selectTdChangeAllTdColor=function(overStyle,outStyle,styleType,selfoverStyle,selfstyleType){
				var tblObj=this.tableObj;
				if(this.tableObj!=null){
					var trCount=this.tableObj.rows.length;
					for(var i=1;i<trCount;i++){//i=1代表从第二行开始，第一行默认为标题头
						var td_objs=this.tableObj.rows[i].childNodes;
						for(var j=0;j<td_objs.length;j++){
							td_objs[j].column=j;
							td_objs[j].onmouseover=function(){
								var trCount=tblObj.rows.length;
								for(var i=1;i<trCount;i++){
									var td_objs=tblObj.rows[i].childNodes;
									if(styleType==null||styleType=="")
										td_objs[this.column].style.backgroundColor=overStyle;
									else
										paging.changeStyle(td_objs[this.column],styleType,overStyle);
								}
								if(styleType==null||styleType=="")
									this.style.backgroundColor=selfoverStyle;
								else
									paging.changeStyle(this,selfstyleType,selfoverStyle);
								
							};
							td_objs[j].onmouseout=function(){
								var trCount=tblObj.rows.length;
								for(var i=1;i<trCount;i++){
									var td_objs=tblObj.rows[i].childNodes;
									if(styleType==null||styleType=="")
										td_objs[this.column].style.backgroundColor=outStyle;
									else
										paging.changeStyle(td_objs[this.column],styleType,outStyle);
								}
								if(styleType==null||styleType=="")
									this.style.backgroundColor=outStyle;
								else
									paging.changeStyle(this,selfstyleType,outStyle);
							};
						}
					    
					}
				}else{alert("请设置表格对象，例:paging.tableObj=document.getElementById('o_tbl');");}
			}			


			//如下为排序参数
			this.isAlwaysSort=true;//是否一直采用所选字段排序
			this.sortType="js";//排序方式 js代表脚本排序 sql代表数据库查询排序
			this.sortItems=[];//排序列Td的所有元素集合
			this.sortIdentitys=[];//非常重要，用于存储排序tr的所有元素集合
			this.sortUpOrDown='up';//用于保存逆序还是顺序排列
			this.sortObj=null;//
			//设置排序对象
			this.setSortObj=function(sortObjAttributes){
				if(this.tableObj!=null){
					var tdObjs=this.tableObj.rows[0].childNodes;//获得标题td列数据						
					for(var i=0;i<tdObjs.length;i++){
						if(this.sortType=='js'){
							if(tdObjs[i].abbr!=null&&tdObjs[i].abbr!='')
								tdObjs[i].innerHTML="<a title=\"点击排序\" href=\"javascript:paging.sortByJS("+i+",'"+tdObjs[i].abbr+"')\" "+sortObjAttributes+">"+tdObjs[i].innerHTML+"</a>";
							
						}else if(this.sortType=='sql'){
							if(tdObjs[i].id!=null&&tdObjs[i].id!='')
								tdObjs[i].innerHTML="<a title=\"点击排序\" href=\"javascript:paging.sortBySql('"+tdObjs[i].id+"')\" "+sortObjAttributes+">"+tdObjs[i].innerHTML+"</a>";
							
						}
					}
				}else{alert("老兄，你想排序，表格对象总不能为空吧，不然让俺怎么知道给哪张表的哪一列进行排序！！！\n请在window.onload事件中设置表格对象！");return false;}
			}
			//改变排序状态-升序或降序
			this.changeSortUpOrDown=function(){
				if(this.sortUpOrDown=='up'){
					this.sortUpOrDown='down';
				}else if(this.sortUpOrDown=='down'){
					this.sortUpOrDown='up';
				}
			}
			//脚本排序
			this.sortByJS=function(columnIndex,typename){
				this.sortItems=[];
				this.sortIdentitys=[];//非常重要，用于存储排序序列
				//检验排序类型是否为空
				if(!(typename=='string'||typename=='number'||typename=='date')){
					alert("必须在需排序的TD中加入abbr属性，且属性值为sting、number、date");
					return false;
				}
				//获得排序数据
				var trCount=this.tableObj.rows.length;
				for(var i=1;i<trCount;i++){
					this.sortItems.push(this.tableObj.rows[i].childNodes[columnIndex].innerHTML.replace(/\s/g,''));
					var tdItems=[];
					for(var j=0;j<this.tableObj.rows[i].childNodes.length;j++){
						tdItems.push(this.tableObj.rows[i].childNodes[j].innerHTML);
					}
					this.sortIdentitys.push(tdItems);
				}

				//根据排序类型进行排序
				switch(typename){
					case "string":this.sortChinese(this.sortItems,this.sortIdentitys);break;
					case "date":this.sortDate(this.sortItems,this.sortIdentitys);break;
					case "number":this.sortNumber(this.sortItems,this.sortIdentitys);break;
				}
				//重新调整tr位置-真正排序
				var trCount=this.tableObj.rows.length;
				for(var i=1;i<trCount;i++){
					for(var j=0;j<this.tableObj.rows[i].childNodes.length;j++){
						this.tableObj.rows[i].childNodes[j].innerHTML=this.sortIdentitys[i-1][j];
					}
				}
				//改变升降序状态
				this.changeSortUpOrDown();
			}
			//数字排序
			this.sortNumber=function(){
				var temp=null;
				for(var i=0;i<this.sortItems.length-1;i++){
					for(var j=0;j<this.sortItems.length-i-1;j++){
						if(parseFloat(this.sortItems[j])>=parseFloat(this.sortItems[j+1])){
							temp=this.sortItems[j];
							this.sortItems[j]=this.sortItems[j+1];
							this.sortItems[j+1]=temp;

							temp=this.sortIdentitys[j];
							this.sortIdentitys[j]=this.sortIdentitys[j+1];
							this.sortIdentitys[j+1]=temp;
						}
					}
				}
			}
			//日期排序
			this.sortDate=function(){
				var temp=null;
				for(var i=0;i<this.sortItems.length-1;i++){
					for(var j=0;j<this.sortItems.length-i-1;j++){
						var condition=this.sortUpOrDown=='up'?parseInt(this.sortItems[j].match(/\d+/g)[0],10)>parseInt(this.sortItems[j+1].match(/\d+/g)[0],10):parseInt(this.sortItems[j].match(/\d+/g)[0],10)<parseInt(this.sortItems[j+1].match(/\d+/g)[0],10);
						if(condition){
							temp=sortItems[j];
							this.sortItems[j]=this.sortItems[j+1];
							this.sortItems[j+1]=temp;

							temp=this.sortIdentitys[j];
							this.sortIdentitys[j]=this.sortIdentitys[j+1];
							this.sortIdentitys[j+1]=temp;
						}
						if(parseInt(this.sortItems[j].match(/\d+/g)[0],10)==parseInt(this.sortItems[j+1].match(/\d+/g)[0],10)){
							for(k=1;k<3;k++){
								var condition=this.sortUpOrDown=='up'?parseInt(this.sortItems[j].match(/\d+/g)[k],10)>parseInt(this.sortItems[j+1].match(/\d+/g)[k],10):parseInt(this.sortItems[j].match(/\d+/g)[k],10)<parseInt(this.sortItems[j+1].match(/\d+/g)[k],10);
								if(parseInt(this.sortItems[j].match(/\d+/g)[k],10)==parseInt(this.sortItems[j+1].match(/\d+/g)[k],10)){
									continue;
								}else if(condition){									
									temp=this.sortItems[j];
									this.sortItems[j]=this.sortItems[j+1];
									this.sortItems[j+1]=temp;

									temp=this.sortIdentitys[j];
									this.sortIdentitys[j]=this.sortIdentitys[j+1];
									this.sortIdentitys[j+1]=temp;
									break;
								}else{
									break;
								}
							}
						}
					}
				}
			}
			//中文unicode排序-先按第一个字排序，如果第一个相同按第二个排序，依次类推
			this.sortChinese=function(){
				var temp=null;
				for(var i=0;i<this.sortItems.length-1;i++){
					for(var j=0;j<this.sortItems.length-i-1;j++){
						var condition=this.sortUpOrDown=='up'?this.sortItems[j].charCodeAt(0)>this.sortItems[j+1].charCodeAt(0):this.sortItems[j].charCodeAt(0)<this.sortItems[j+1].charCodeAt(0);
						if(condition){
							temp=this.sortItems[j];
							this.sortItems[j]=this.sortItems[j+1];
							this.sortItems[j+1]=temp;
							
							temp=this.sortIdentitys[j];
							this.sortIdentitys[j]=this.sortIdentitys[j+1];
							this.sortIdentitys[j+1]=temp;
						}
						if(this.sortItems[j].charCodeAt(0)==this.sortItems[j+1].charCodeAt(0)){
							var minlength=this.sortItems[j].length>=this.sortItems[j+1].length?this.sortItems[j+1].length:this.sortItems[j].length;
							for(k=1;k<minlength;k++){
								var condition=this.sortUpOrDown=='up'?this.sortItems[j].charCodeAt(k)>this.sortItems[j+1].charCodeAt(k):this.sortItems[j].charCodeAt(k)<this.sortItems[j+1].charCodeAt(k);
								if(this.sortItems[j].charCodeAt(k)==this.sortItems[j+1].charCodeAt(k)){
									continue;
								}else if(condition){									
									temp=this.sortItems[j];
									this.sortItems[j]=this.sortItems[j+1];
									this.sortItems[j+1]=temp;

									temp=this.sortIdentitys[j];
									this.sortIdentitys[j]=this.sortIdentitys[j+1];
									this.sortIdentitys[j+1]=temp;
									break;
								}else{
									break;
								}
							}
						}
					}
				}
				
			}
			//数据库查询排序
			this.sortBySql=function(columnname){
				if(this.sortObj!=null){					
					this.setQueryParm();
					this.sortUpOrDown=this.sortStr.split("--")[1]=="up"?"down":"up";
					//添加排序列名到隐藏标签
					this.sortObj.value=columnname+"--"+this.sortUpOrDown+"--"+this.isAlwaysSort;
					url=this.skipUrl+(skipUrl.indexOf("?")==-1?'?':'&')+'pageIndex='+((this.selectIndex-1)*this.pageSize)+'&selectIndex='+this.selectIndex;
					this.formObj.action=url;
					this.formObj.submit();
				}else{
					alert("请设置sortObj对象!该对象为一个隐藏input标签!");
				}
				
			}
		}


/*function startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj){
	//建立分页对象-必须
	window.paging=new Paging(MaxIndex,pageSize,selectIndex);
	
	//设置页面全局参数-必须
	paging.sortStr=sortStr;
	paging.formObj=submitForm;//设置提交的表单对象
	paging.skipUrl=skipUrl;//设置提交的url
	
	//paging.paramsObj=document.getElementById('queryParams');//设置用于存储所有拼接sql语句字符串查询条件信息
	//paging.valuesObj=document.getElementById('queryValues');//设置用于存储页面查询条件信息
	//paging.deleteIdsObj=document.getElementById('allDeleteids');//设置用于存储批删除所有id
	//paging.sortObj=document.getElementById('sortStr');//设置用于存储排序信息
	
	//设置分页提醒信息-可选可删可修改-可改变字符
	var allInfo='共'+paging.MaxIndex+'条信息，共'+paging.pageCount+'页，当前第'+paging.selectIndex+'页，显示'+paging.startCount+'至'+paging.endCount+'条信息';
	
	//设置页数选择链接-可选可删可修改-onclick事件不能改变
	var selectStr="";
	for(var j=paging.startpage;j<=paging.endpage;j++){
		var selStyle='margin-right:2px;cursor:pointer;';
		if(j==selectIndex)selStyle='margin-right:2px;cursor:pointer;font-weight: bold;';
		selectStr+="<span style='"+selStyle+"' onmouseover=\"paging.changeStyle(this,'color','red')\" onmouseout=\"paging.changeStyle(this,'color','')\" onclick='paging.selectPage("+j+")'>"+j+"</span>,";
	}
	if(selectStr.length>0&&selectStr!=''){
		selectStr=selectStr.substr(0,selectStr.length-1);
	}

	//设置下拉列表框链接-可选可删可修改-onclick事件不能改变
	var select="<select id='page_sel' onchange='paging.changPage(this.value)'>";
	for(var i=1;i<=paging.pageCount;i++){
		select+="<option value='"+i+"'>"+i+"</option>";
	}
	select+="</select>"; 

	//设置首页上一页链接-可选可删可修改-onclick事件、id名不能改变
	var startlast="<span>[</span><span class='ml5' id='first_sp_page' onclick='paging.startPage()' class='fy_menu' style='cursor: pointer;'>首页</span><span class='ml5'>/</span><span class='ml5' id='last_sp_page' onclick='paging.lastPage()' class='fy_menu' style='cursor: pointer;'>上页</span><span class='ml5'>]</span>";

	//设置下一页末页链接-可选可删可修改-onclick事件、id名不能改变
	var endnext="<span class='ml5'>[</span><span class='ml5' id='next_sp_page' onclick='paging.nextPage()' class='fy_menu' style='cursor: pointer;'>下页</span><span class='ml5'>/</span><span class='ml5' id='end_sp_page' onclick='paging.endPage()' class='fy_menu' style='cursor: pointer;'>末页</span><span class='ml5'>]</span>";

	//设置GO链接-可选可删可修改-onclick事件、id名不能改变
	var go="<span class='ml5'>跳转到第</span><input class='ml5' id='go_t' type='text' style='width: 30px;height: 18px;border: 1px solid #999999;padding-top: 2px;'/><span class='ml5'>页</span><input type='button' value=' ' class='pagebtn ml5' onclick=\"return paging.goPage('go_t')\"/>";
	
	//统一设置上页下页首页末页鼠标移动时变化状态-必须	
	//参数1为选中时的样式，参数2为离开时的样式,参数3为样式类型(可有可无，默认改变字体颜色)  目前参数3只支持color、backgroundColor、cssText、className
	//paging.setState('red','');
	//paging.setState('color:red;','color:green;','cssText');

	//判断当前信息条数小于页面最大信息条数时是否显示分页框-可选可删
	//paging.pageRemindObj=document.getElementById('selRemind');//设置用于分页框显示
	
	if(innerObj!=null&&paging.MaxIndex<=paging.pageSize)
	innerObj.style.display='none';
	
	
	var pageStr='<table   style="width:100%;border:0px;">'
					+'<tr>'
						+'<td style="text-align: left;">'
						+allInfo
						+'</td>'
						+'<td style="text-align: right;">'	
						+startlast+'&nbsp;'+selectStr+'&nbsp;'+endnext+'&nbsp;'+select+'&nbsp;'+go
						+'</td>'
					+'</tr>'
				+'</table>';
	if(innerObj){
		innerObj.innerHTML=pageStr;
		paging.setState('color:red;cursor:pointer;font-weight: bold;','','cssText');//统一设置上页下页首页末页鼠标移动时变化状态-必须	
		if(document.getElementById('page_sel')){//选中当前页数
			document.getElementById('page_sel').value=selectIndex;
		}
		
	}
	
}*/

function startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj){
	//建立分页对象-必须
	window.paging=new Paging(MaxIndex,pageSize,selectIndex);
	
	//设置页面全局参数-必须
	paging.sortStr=sortStr;
	paging.formObj=submitForm;//设置提交的表单对象
	paging.skipUrl=skipUrl;//设置提交的url
	//paging.paramsObj=document.getElementById('queryParams');//设置用于存储所有拼接sql语句字符串查询条件信息
	//paging.valuesObj=document.getElementById('queryValues');//设置用于存储页面查询条件信息
	//paging.deleteIdsObj=document.getElementById('allDeleteids');//设置用于存储批删除所有id
	//paging.sortObj=document.getElementById('sortStr');//设置用于存储排序信息
	//设置分页提醒信息-可选可删可修改-可改变字符
	if(paging.MaxIndex==0){
		return ;
	}
	
	var allInfo='共<label style="color:#3063a3;">'+paging.MaxIndex+'</label>条，共<label style="color:#3063a3;">'+paging.pageCount+'</label>页，';
	//设置页数选择链接-可选可删可修改-onclick事件不能改变
	var selectStr="";
	for(var j=paging.startpage;j<=paging.endpage;j++){
		var selStyle='margin-right:2px;cursor:pointer;';
		if(j==selectIndex)selStyle='wf-active';
		selectStr+="<li class='"+selStyle+ "' onclick='paging.selectPage("+j+")'><a href='javascript: paging.selectPage("+j+")'>"+j+"</a></li>";
	}
//	if(selectStr.length>0&&selectStr!=''){
//		selectStr=selectStr.substr(0,selectStr.length-1);
//	}

	//设置下拉列表框链接-可选可删可修改-onclick事件不能改变
	var select="<select id='page_sel' onchange='paging.changPage(this.value)'>";
	for(var i=1;i<=paging.pageCount;i++){
		select+="<option value='"+i+"'>"+i+"</option>";
	}
	select+="</select>"; 

	//设置首页上一页链接-可选可删可修改-onclick事件、id名不能改变
	var startlast="<li id='first_sp_page' onclick='paging.startPage()'><a href='javascript: paging.startPage()'>首页</a></li><li id='last_sp_page' onclick='paging.lastPage()'><a href='javascript: paging.lastPage()'>上一页</a></li>";

	//设置下一页末页链接-可选可删可修改-onclick事件、id名不能改变
	var endnext="<li id='next_sp_page' onclick='paging.nextPage()'><a href='javascript: paging.nextPage()'>下一页</a></li><li id='end_sp_page' onclick='paging.endPage()'><a href='javascript: paging.endPage()'>末页</a></li>";

	//设置GO链接-可选可删可修改-onclick事件、id名不能改变
	var go="<span> 跳转到第 </span><input class='wf-form-text' id='go_t' type='text' /><span> 页 </span><input type='button' value='GO' class='wf-page-go-btn' onclick=\"return paging.goPage('go_t')\"/>";
	
	//一页显示的信息条数
	var pages="<span>每页显示 </span><select style='border: 1px solid #e5e5e5;border-radius:3px;' id='page_selp' onchange='paging.changePagesize(this.value)'><option value='10'>10</option><option value='15'>15</option><option value='20'>20</option><option value='30'>30</option><option value='40'>40</option><option value='50'>50</option></select><span> 条</span>";
	//统一设置上页下页首页末页鼠标移动时变化状态-必须	
	//参数1为选中时的样式，参数2为离开时的样式,参数3为样式类型(可有可无，默认改变字体颜色)  目前参数3只支持color、backgroundColor、cssText、className
	//paging.setState('red','');
	//paging.setState('color:red;','color:green;','cssText');

	//判断当前信息条数小于页面最大信息条数时是否显示分页框-可选可删
	paging.pageRemindObj=document.getElementById('selRemind');//设置用于分页框显示
	if(paging.pageRemindObj!=null&&paging.MaxIndex<=paging.pageSize)
	paging.pageRemindObj.style.display='none';
	var pageStr = '<div class="wf-pagination">'
		+'<span class="wf-page-info">'
		+ allInfo
		+ pages
		+'</span>'
		+'<ul class="wf-page-list">'
		+startlast
		+selectStr
		+endnext
		+'<li class="wf-page-go">'
		
		
		+go
		+'</li>'
		+'</ul>'
		+'</div>';
	if(innerObj){
		innerObj.innerHTML=pageStr;
		paging.setState('color:red;cursor:pointer;','','cssText');//统一设置上页下页首页末页鼠标移动时变化状态-必须	
		if(document.getElementById('page_sel')){//选中当前页数
			document.getElementById('page_sel').value=selectIndex;
		}
		document.getElementById('page_selp').value=pageSize;
	}
}
