;define(function(require, exports, module){
	var $=require('jquery');
	var Class={
		create:function(){
			return function(){
				this.initialize.apply(this,arguments);
			}
		}
	}
	var agenda=Class.create();
		agenda.prototype={
			initialize:function(year,data){
				this.calendar={};
				if(year){
					if(Object.prototype.toString.call(year)=='[object Array]'){
						for(var i=0,l=year.length;i<l;i++){
							this.calendar[year]={};
						}
					}else{
						this.calendar[year]={};
					}
				}else{
					this.calendar[new Date().getFullYear()]={};
				}
				if(data) this.setCalendar(data);
			},
			setCalendar:function(data){
				var $this=this;
				$.each(data,function(i,v){
					$.extend($this.calendar[i],v);
				});
			},
			toJSON:function(year){
				var $this=this, years=[];
				if(year){
					years.push(this._toString(year));
				}else{
					$.each(this.calendar,function(i,v){
						years.push($this._toString(i));
					});
				}
				//console.log('{'+years.toString()+'}');
				return '{'+years.join(',')+'}';
			},
			_toString:function(year){
				var string=[],month=[];
				if(!this.calendar[year]) return ''; 
				//console.log('Year:',year);
				$.each(this.calendar[year],function(i,v){
					string=[];
					//console.log('Month:',i);
					$.each(v,function(j,k){
						//console.log('day:',j+'='+k);
						string.push('"'+j+'":"'+k+'"');	
					});
					month.push('"'+i+'":{'+string.join(',')+'}');
				});
				return '"'+year+'":{'+month.join(',')+'}';
			},
			//返回相应状态的日期
			toTypeJSON:function(type,year){
				var $this=this, years=[];
				if(year){
					years.push(this._toTypeDay(year,type));
				}else{
					$.each(this.calendar, function(i,v){
						years.push($this._toTypeDay(i,type));
					});
				}
				return '{'+years.join(',')+'}';
			},
			//返回相应状态的日期
			_toTypeDay:function(year,type){
				var string=[],month=[];
				if(!this.calendar[year]) return ''; 
				//console.log('Year:',year);
				$.each(this.calendar[year],function(i,v){
					string=[];
					//console.log('Month:',i);
					$.each(v,function(j,k){
						//console.log('day:',j+'='+k);
						//console.log(k+'=='+type+': '+(k==type));
						if(k==type) string.push(j);	
					});
					month.push('"'+i+'":['+string.join(',')+']');
				});
				return '"'+year+'":{'+month.join(',')+'}';
			}
		}
	var calendar=Class.create();
	calendar.prototype={
		initialize:function(container, options){
			this.container=$(container);//容器(table结构)
			this.days=[];//日期对象列表
			this.setoptions(options);
			
			this.year=this.options.year||new Date().getFullYear();
			this.month=this.options.month||new Date().getMonth()+1;
			this.selectday=this.options.selectday?new Date(this.options.selectday):null;
			this.onselectday=this.options.onselectday;
			this.ontoday=this.options.ontoday;
			this.onfinish=this.options.onfinish;
			this.dateEvent=this.options.dateEvent;
			this.agenda=(this.options.agenda)?this.options.agenda:new agenda([this.year]);
			this.autoRest=this.options.autoRest;
			this.data=this.options.data;
			//console.log(this.agenda);
			this.createDef();
			this.draw();
		},
		setoptions:function(options){
			this.options=$.extend({
				autoRest:true,
				agenda:null,
				year:0,//年
				month:0,//月
				selectday:null,//选择的日期
				onselectday:function(){},//选择日期触发
				ontoday:function(){},//当天日期触发
				onfinish:function(){},//构建完日历结束后触发的事件
				dateEvent:function(){},
				data:null
			},options);
		},
		//当前月
		nowmonth:function(){
			this.predraw(new Date());
		},
		//上一个月
		premonth:function(){
			this.predraw(new Date(this.year, this.month-2, 1));
		},
		//下个月
		nextmonth:function(){
			this.predraw(new Date(this.year, this.month, 1));
		},
		//上一年
		preyear:function(){
			this.predraw(new Date(this.year-1, this.month-1, 1));
		},
		//下一年
		nextyear:function(){
			this.predraw(new Date(this.year+1, this.month-1, 1));
		},
		//重新绘制日历
		predraw:function(date){
			this.year=date.getFullYear();this.month=date.getMonth()+1;
			this.draw();
		},
		//生成年份的自动设置
		createDef:function(year){
			var year=(year!=''&&year!=undefined)?year:this.year;
			if(!this.agenda.calendar[year]) this.agenda.calendar[year]={};
			for(var month=1;month<=12;month++){
				if(!this.agenda.calendar[year][month]) this.agenda.calendar[year][month]={};
				var arr=[];//日期列表
				//第一日的位置
				for(var i=1, fd=new Date(year, month-1, 1).getDay();i<=fd;i++){
					arr.push(0);
				}
				//最后一日
				for(var i=1, ed=new Date(year, month, 0).getDate();i<=ed;i++){
					arr.push(i);
				}
				while(arr.length){
					for(var i=0;i<7;i++){
						if(arr.length){
							var d=arr.shift();
							if(d){
								if(new Date(year, month-1, d).getDay()==0||new Date(year, month-1, d).getDay()==6){
									this.agenda.calendar[year][month][d]=0;
								}else{
									this.agenda.calendar[year][month][d]=-1;
								};
							}
						}
					}
				}
			}
		},
		//绘制日历
		draw:function(){
			//存储精灵
			if(this.agenda.calendar[this.year]){
				this.agenda.calendar[this.year][this.month]={};
			}else{
				this.agenda.calendar[this.year]={};
				this.agenda.calendar[this.year][this.month]={};
			}
							
			var $this=this;
			this.container.html('')
			var temp='<table class="date"><tr><td class="precalendar"></td><td><span class="yearcalendar"></span>年<span class="monthcalendar"></span>月</td><td class="nextcalendar"></td></tr></table><table class="datemap"><thead><tr class="week"><td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td></tr></thead><tbody year="'+this.year+'"  month="'+this.month+'" class="days"></tbody></table>';
			this.container.append(temp);
			var arr=[];//日期列表
			//第一日的位置
			for(var i=1, fd=new Date(this.year, this.month-1, 1).getDay();i<=fd;i++){
				arr.push(0);
			}
			//最后一日
			for(var i=1, ed=new Date(this.year, this.month, 0).getDate();i<=ed;i++){
				arr.push(i);
			}
			//清空列表
			this.days=[];
			//缓存文档对象
			var frag=document.createDocumentFragment();
			while(arr.length){
				var row=document.createElement('tr');
				for(var i=0;i<7;i++){
					var cell=document.createElement('td');
						cell.innerHTML='&nbsp;';
					if(arr.length){
						var d=arr.shift();
						if(d){
							cell.setAttribute('data-day',d);
							cell.innerHTML=d;
							this.days[d]=cell;
							var on=new Date(this.year, this.month-1, d);
							//判断是否是今日
							this.istoday(on, new Date())&&this.ontoday(cell);
							//判断是否选择日期
							this.selectday&&this.istoday(on, this.selectday)&&this.onselectday(cell);
							//回调
							if(this.autoRest){
								if(new Date(this.year, this.month-1, d).getDay()==0||new Date(this.year, this.month-1, d).getDay()==6){
									this.agenda.calendar[this.year][this.month][d]=0;
									cell.setAttribute('class','rest');
								}else{
									this.agenda.calendar[this.year][this.month][d]=-1;
									cell.setAttribute('class','work');
								};
							}
							$this.dateEvent.call(cell);
						}
					}
					row.appendChild(cell);
				}
				frag.appendChild(row);
			}
			//附加节点
			$('.days',this.container).append(frag);
			$('.yearcalendar',this.container).html(this.year);
			$('.monthcalendar',this.container).html(this.month);
			$('.precalendar',this.container).click(function(){
				$this.premonth();
			});
			$('.nextcalendar',this.container).click(function(){
				$this.nextmonth();
			});
			
			if(this.data) this.agenda.setCalendar(this.data);
			this.onfinish.call(this);
		},
		//判断是否是今日
		istoday:function(d1, d2){
			return (d1.getFullYear()==d2.getFullYear()&&d1.getMonth()==d2.getMonth()&&d1.getDate()==d2.getDate());
		}
	}
	
	module.exports=calendar;
});