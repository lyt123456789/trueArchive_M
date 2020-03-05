;define(function(require, exports, module){
	var $=require("jquery");
		require("lib/contextmenu");
	//模拟class
	var Class={
		create:function(){
			return function(){
				this.initialize.apply(this,arguments);
			}
		}
	}
	/**
	 * Module
	 **/
	//元素精灵
	var Sprite=Class.create();
	Sprite.prototype={
		initialize:function(options){
			var options=$.extend({
					x:0,
					y:0,
					state:-1
				},options);
			this.x=options.x;			//矩阵中的x位置
			this.y=options.y;			//矩阵中的Y位置
			this.state=options.state;	//所属状态	-1:缺省 0:过道 1:座位
			this.cell=[];				//宿主对象
			
			this.groupId='';
		},
		setXY:function(xy){
			this.x=(xy.x!='')?xy.x:this.x;
			this.y=(xy.y!='')?xy.y:this.y;
		},
		getXY:function(xy){
			return {x:this.x,y:this.y};
		},
		setState:function(state){
			this.state=state;
		},
		getState:function(state){
			return this.state;
		},
		addCell:function(o){
			this.cell.push(o);
		},
		getCell:function(){
			return this.cell;
		},
		delCell:function(o){
			for(var i=0,l=this.cell.length;i<l;i++){
				if(this.cell[i]===o) return this.cell.splice(i,1);
			}
		},
		setGroupId:function(gid){
			this.groupId=gid;
		},
		delGroupId:function(){
			this.groupId='';
		},
		getGroupId:function(){
			return this.groupId;
		}
	};
	
	var Groups=Class.create();
	Groups.prototype={
		initialize:function(id,name,color,seat){
			/**
			seat:[{'r-c':{id:userid,name:username}}]
			**/
			this.id=(id)?id:'';							//分组ID
			this.color=(color)?color:'';				//分组颜色
			this.name=(name)?name:'';	//分组名称
			this.seats={};								//座位信息
			if(!!seat) this.init(seat);
		},
		init:function(seat){
			var _this=this;
			if(typeof seat=="string"){
				eval("var seat=("+seat+")");	
			}
			var i=0,l;
			for(i,l=seat.length;i<l;i++){
				$.each(seat[i],function(j,k){
					_this.seats[j]=k;
				});
			}
			return _this;
		},
		setSeatVal:function(x,y,id,name){
			this.seats[y+'-'+x]['id']=id;
			this.seats[y+'-'+x]['name']=name;
			this.seats[y+'-'+x]['x']=x;
			this.seats[y+'-'+x]['y']=y;
		},
		addSeat:function(x,y){
			this.seats[y+'-'+x]={id:'',name:'',r:y,c:x};
			return this;
		},
		delSeat:function(x,y){
			if(this.seats[y+'-'+x]) delete this.seats[y+'-'+x];
			return this;
		},
		getSeat:function(x,y){
			if(this.seats[y+'-'+x])
				return this.seats[y+'-'+x];
			else
				return null;
		},
		toSeatJSON:function(bool){
			var json=[];
			$.each(this.seats,function(j,k){
				json.push('{"'+j+'":{"id":"'+k.id+'","name":"'+k.name+'"}}');
			});
			if(bool){
				return '{"gid":"'+this.id+'","gname":"'+this.name+'","gcolor":"'+this.color+'","gseats":['+json.join(',')+']}';
			}else{
				return '['+json.join(',')+']';
			}
		},
		//生成分组的座位数组，方便排序
		toSeatArr:function(){
			var arr=[];
			$.each(this.seats,function(j,k){
				arr.push({'id':k.id,'name':k.name,'r':k.r,'c':k.c});
			});
			return arr;
		}
	}
	/**
	 * View
	 **/
	//网格对象
	var TABLE=Class.create();
	TABLE.prototype={
		initialize:function(map){
			this.map=map;
			this.numRows=0;				//行数
			this.numCols=0;				//列数
			this.cell=[];				//td宿主对象
			this.groups={};				//分组信息
			this.init(map);				//初始化
		},
		init:function(map){
			this.numRows=map.length;
			this.numCols=map[0].length;
			
			for(var i=0;i<this.numRows;i++){
				this.cell[i]=[];
				for(var j=0;j<this.numCols;j++){
					this.cell[i][j] = new Sprite({x:i,y:j,state:map[i][j]});
				}
			}
		},
		draw:function(eachFn){
			var oFrag=document.createDocumentFragment();
			var map=document.createElement("table");
			for(var i=0;i<this.numRows;i++){
				var tr=document.createElement('tr');
				for(var j=0;j<this.numCols;j++){
					var td=document.createElement('td');
						td.setAttribute('r',i);
						td.setAttribute('c',j);
						if(eachFn) eachFn.call(td,i,j);
					tr.appendChild(td);
				}
				map.appendChild(tr);
			}
			return oFrag.appendChild(map);
		},
		addGroup:function(gid,gname,gcolor){
			this.groups[gid]=new Groups(gid,gname,gcolor);
		},
		getGroup:function(){
			
		},
		swapGroup:function(){
			//交换分组
			
		},
		swapUser:function(x,y,tx,ty,tab){
			if(x==tx&&y==ty) return;
			//交换人员
			var gid=this.cell[y][x].getGroupId();	//待调换分组的id
			var tgid=this.cell[ty][tx].getGroupId();	//目标分组的id
			console.log(gid+','+tgid);
			if(gid==tgid){
				this.sortSeat(this.groups[gid],x,y,tx,ty,tab);
			}else{
				alert('暂不支持组间调换！');
			}
			gid=tgid=null;	
		},
		sortSeat:function(obj,x,y,tx,ty,tab){
			var __seats=obj.seats;
			if(__seats[ty+'-'+tx].id==''){
				__seats[ty+'-'+tx].id=__seats[y+'-'+x].id;
				__seats[ty+'-'+tx].name=__seats[y+'-'+x].name;
				__seats[y+'-'+x].id='';
				__seats[y+'-'+x].name='';
				tab.find('tr').eq(y).find('td').eq(x).text('');
				tab.find('tr').eq(ty).find('td').eq(tx).text(__seats[ty+'-'+tx].name);
			}else{
				var arr=obj.toSeatArr();
				arr.sort(function(o1,o2){
					if(o1.r==o2.r){
						return o1.c-o2.c;
					}else if(o1.r>o2.r){
						return 1;
					}else{
						return -1;
					}
				});
				console.log('sort',arr);
				
				var cId='',cName='',tId=__seats[y+'-'+x].id,tName=__seats[y+'-'+x].name,i=0,l=arr.length,bnum=l;
				
				__seats[y+'-'+x].id='';
				__seats[y+'-'+x].name='';
				tab.find('tr').eq(y).find('td').eq(x).text('');
				
				for(i;i<l;i++){
					var iR=arr[i].r,iC=arr[i].c;
					if(arr[i].r==ty&&arr[i].c==tx){
						cId=__seats[iR+'-'+iC].id;
						cName=__seats[iR+'-'+iC].name;
						
						__seats[iR+'-'+iC].id=tId;
						__seats[iR+'-'+iC].name=tName;
						tab.find('tr').eq(iR).find('td').eq(iC).text(tName);
						
						bnum=i;
					}
					if(i>bnum){
						if(cId==undefined||cName==undefined) break;
						tId=cId;
						tName=cName;
						
						cId=__seats[arr[i].r+'-'+arr[i].c].id;
						cName=__seats[arr[i].r+'-'+arr[i].c].name;
						
						__seats[iR+'-'+iC].id=tId;
						__seats[iR+'-'+iC].name=tName;
						tab.find('tr').eq(iR).find('td').eq(iC).text(tName);
						if(iR==y&&iC==x) break;
					}
				}
			}
		},
		transGroups:function(group){
			var i=0,l=group.length;
			for(i;i<l;i++){
				this.groups[group[i].gid]=new Groups(group[i].gid, group[i].gname, group[i].gcolor);
			}
			return this;
		}
	};
	//矩阵数组对象
	var MATRIX=Class.create();
	MATRIX.prototype={
		initialize:function(numCols,numRows){
			this.numRows=numRows?numRows:0;				//行数
			this.numCols=numCols?numCols:0;				//列数
			this.matrix=[];
			this.init(this.numRows,this.numCols);
		},
		init:function(x,y){
			for(var i=0;i<y;i++){
				this.matrix[i]=[];
				for(var j=0;j<x;j++){
					this.matrix[i][j]=-1;
				}
			}
		},
		setMatrixVal:function(x,y,value){
			this.matrix[y][x]=value!=undefined?value:0;
		},
		getMatrixVal:function(x,y){
			return this.matrix[y][x];
		},
		getMatrix:function(){
			return this.matrix;
		},
		setMatrix:function(numCols,numRows){
			this.numRows=numRows;				//行数
			this.numCols=numCols;				//列数
			this.init(this.numRows,this.numCols);
		},
		resetVal:function(x,y,value){
			this.matrix[y][x]=value;
		},
		getString:function(){
			return this.numRows+','+this.numCols+'$'+this.matrix.toString();
		},
		transMatrix:function(matrixString){
			if(!matrixString) return;
			var ms=matrixString.split('$');
			var rowsNum=ms[0].split(',')[0],colsNum=ms[0].split(',')[1],vals=ms[1].split(',');
			this.numRows=rowsNum?rowsNum:0;				//行数
			this.numCols=colsNum?colsNum:0;				//列数
			var ns=0;
			for(var i=0;i<this.numCols;i++){
				this.matrix[i]=[];
				for(var j=0;j<this.numRows;j++){
					this.matrix[i][j]=vals[ns];
					ns++;
				}
			}
			return this.matrix;
		}
	}
	/**
	 * Controller
	 **/
	//会议控制类
	$.extend($.fn,{
		showmeeting:function(options){
			var settings=$.extend({
					seatCls:'map-seat',
					corridorCls:'map-corridor',
					map:[],				//地图信息
					group:[]			//分组信息
				},options);
			return this.each(function(){
				var $this=$(this);
				var table=new TABLE(settings.map);
				var $map=$(table.draw()).appendTo($this.empty()).css({width:$this.width(),height:$this.height()}).addClass('mice-map').find('td').each(function(){
						var x=$(this).attr('c'),y=$(this).attr('r');
						(settings.map[y][x]>-1)?(settings.map[y][x]>0?this.setAttribute('class',settings.seatCls):this.setAttribute('class',settings.corridorCls)):'';
						
						if(settings.group[y+'-'+x]) $(this).attr('style','background-color:'+settings.group[y+'-'+x].groupColor+';');
						
						this.oncontextmenu=function(e){
							return false;
						}
						x=y=null;
					});
			});
		},
		meeting:function(options){
			var settings=$.extend({
					map:[],
					group:[],
					groupIdEl:'',
					groupColorEl:'',
					groupUserIdEl:'',
					seatCls:'map-seat',
					corridorCls:'map-corridor',
					brushMode:-1,	//画笔状态：-1:无操作 0:移除座位 1:绘制座位
					drawState:0,	//绘制状态：0:关,1:开
					setGroupButton:'.mice-set-group',
					completeButton:'.mice-get-group',
					completeFn:function(){}
				},options);
			return this.each(function(){
				var $this=$(this),__this=this;
				var table=new TABLE(settings.map);
				table.transGroups(settings.group);
				console.log(table.groups);
				
				var $map=$(table.draw()).appendTo($this.empty()).css({width:$this.width(),height:$this.height()}).addClass('mice-map').find('td').each(function(){
						var x=$(this).attr('c'),y=$(this).attr('r');
						(settings.map[y][x]>-1)?(settings.map[y][x]>0?this.setAttribute('class',settings.seatCls):this.setAttribute('class',settings.corridorCls)):'';
						
						$(this).bind('mousedown mouseenter',function(e){
							var e=window.event||e;
							if(e.button==2) return;
							//鼠标移入设置分组
							var x=$(this).attr('c'),y=$(this).attr('r');
							if(settings.drawState===1&&settings.map[y][x]==1){
								if(settings.brushMode===0){
									//删除分组
									var gid=table.cell[y][x].getGroupId();
									if(gid){
										table.groups[gid].delSeat(x,y);
										table.cell[y][x].delGroupId();
										$(this).attr('style','');
									}
									gid=null;
									
								}else if(settings.brushMode===1){
									var gid=$(settings.groupIdEl).val();
									if(gid){
										//如果座位已经分发，清空已分发的信息
										var ogid=table.cell[y][x].getGroupId();
										if(ogid!=''){
											table.groups[ogid].delSeat(x,y);
											table.cell[y][x].delGroupId();
										}
										ogid=null;
										table.cell[y][x].setGroupId(gid);
										table.groups[gid].addSeat(x,y);
										$(this).attr('style','background-color:'+table.groups[gid].color+';');
									}
									gid=null;
								}
							}
							x=y=null;
						}).bind('mousedown',function(e){
							var e=window.event||e;
							if(e.button==2) return;
							
							settings.drawState=1;
							var x=$(this).attr('c'),y=$(this).attr('r');							
							
							if(settings.drawState===1&&settings.map[y][x]==1){
								if(settings.brushMode===0){
									//清除分组
									console.log('0:' ,y+'-'+x);
									var gid=table.cell[y][x].getGroupId();
									if(gid){
										table.groups[gid].delSeat(x,y);
										table.cell[y][x].delGroupId();
										$(this).attr('style','');
									}
									gid=null;
								
								}else if(settings.brushMode===1){
									//设置分组
									var gid=$(settings.groupIdEl).val();
									if(gid){
										
										var ogid=table.cell[y][x].getGroupId();
										console.log(ogid);
										if(ogid!=''){
											table.groups[ogid].delSeat(x,y);
											table.cell[y][x].delGroupId();
										}
										ogid=null;
										table.cell[y][x].setGroupId(gid);
										table.groups[gid].addSeat(x,y);
										$(this).attr('style','background-color:'+table.groups[gid].color+';');
									}
									gid=null;
									
								}else if(settings.brushMode===2){
									//交换人员
									console.log('start: ',y+'-'+x+"=====================");
									var ogid=table.cell[y][x].getGroupId();
									if(ogid!=''){
										if(table.groups[ogid].seats[y+'-'+x].id==''){
											$this.data('x','').data('y','');
										}else{
											$this.data('x',x).data('y',y);
										};
										
									}
								}
							}
							x=y=null;
						}).bind('mouseup',function(e){
							var e=window.event||e;
							settings.drawState=0;
							var x=$(this).attr('c'),y=$(this).attr('r');
							if(settings.brushMode===2){
								if(e.button==2) return;
								if(settings.map[y][x]==1){
									var startX=$this.data('x'),startY=$this.data('y');
									if(!!startX||!!startY){
										table.swapUser(startX, startY, x, y,$this);
									}
									console.log('start: ','=========================================');
									console.log('start: ',startY+'-'+startX);
									console.log('target: ',y+'-'+x);
								}else{
									alert('目标不是座位！');
								}
							}
						});
						
						this.oncontextmenu=function(){
							$(this).contextmenu({prefix:'mice-contextmenu-group-',context:this,events:[{tag:'返回',cls:'splitline'},{tag:'设置分组',cls:''},{tag:'撤销分组',cls:''},{tag:'设置人员',cls:''}],eventsFn:function(i){
								var x=$(this).attr('c'),y=$(this).attr('r');
								switch(i){
									case 0:
									break;
									case 1:
									//设置分组
										if(settings.map[y][x]!=1) break;
										//设置分组
										var gid=$(settings.groupIdEl).val();
										if(gid){
											
											var ogid=table.cell[y][x].getGroupId();
											console.log(ogid);
											if(ogid!=''){
												table.groups[ogid].delSeat(x,y);
												table.cell[y][x].delGroupId();
											}
											ogid=null;
											table.cell[y][x].setGroupId(gid);
											table.groups[gid].addSeat(x,y);
											$(this).attr('style','background-color:'+table.groups[gid].color+';');
										}
										gid=null;
										
									break;
									case 2:
									//撤销分组
										if(settings.map[y][x]!=1) break;
										var gid=table.cell[y][x].getGroupId();
										if(gid){
											table.groups[gid].delSeat(x,y);
											table.cell[y][x].delGroupId();
											$(this).attr('style','');
										}
										gid=null;
										
									break;
									case 3:
									//设置人员
										$(this).text(y+x);
										var gid=table.cell[y][x].getGroupId();
										table.groups[gid].seats[y+'-'+x].id=y+x;
										table.groups[gid].seats[y+'-'+x].name=y+x;
									break;
									default:
									break;
								}
								x=y=null;
							}});
							return false;
						}
						x=y=null;
					});
				/**setting mode**/
				$(settings.setGroupButton).bind('click',function(){
					settings.brushMode=parseInt(this.value);
				});
				$(settings.completeButton).bind('click',function(){
					settings.completeFn.call($this,table);
				});
				$(window).bind('mouseup',function(){
					settings.drawState=0;
				});
			});
		},
		//设置座位和通道
		setMap:function(options){
			var settings=$.extend({
					map:'',
					brushMode:-1,	//画笔状态：-1:无操作 0:移除座位 1:绘制座位
					drawState:0,	//绘制状态：0:关,1:开
					seatCls:'map-seat',
					corridorCls:'map-corridor',
					setValButton:'.mice-set-matrix',
					completeButton:'.mice-get-matrix',
					completeFn:function(){}
				},options);
			return this.each(function(){
				var $this=$(this);
				var matrix=new MATRIX();
				var __map=matrix.transMatrix(settings.map);
				var table=new TABLE(__map);
				var $map=$(table.draw()).appendTo($this.empty()).css({width:$this.width(),height:$this.height()}).addClass('mice-map').find('td').each(function(){
					var x=$(this).attr('c'),y=$(this).attr('r');
						(__map[y][x]>-1)?(__map[y][x]>0?this.setAttribute('class',settings.seatCls):this.setAttribute('class',settings.corridorCls)):'';
						
						$(this).bind('mouseenter',function(e){
							var e=window.event||e;
							if(e.button==2) return;
							
							var x=$(this).attr('c'),y=$(this).attr('r');
							if(settings.drawState===1){
								if(settings.brushMode===0){
									matrix.setMatrixVal(x,y,0);
									$(this).removeClass().addClass(settings.corridorCls);
								}else if(settings.brushMode===1){
									matrix.setMatrixVal(x,y,1);
									$(this).removeClass().addClass(settings.seatCls);
								}
							}
							x=y=null;
						}).bind('mousedown',function(e){
							var e=window.event||e;
							if(e.button==2) return;
							
							settings.drawState=1;
							var x=$(this).attr('c'),y=$(this).attr('r');
							if(settings.drawState===1){
								if(settings.brushMode===0){
									matrix.setMatrixVal(x,y,0);
									$(this).removeClass().addClass(settings.corridorCls);
								}else if(settings.brushMode===1){
									matrix.setMatrixVal(x,y,1);
									$(this).removeClass().addClass(settings.seatCls);
								}
							}
							x=y=null;
						}).bind('mouseup',function(){
							settings.drawState=0;
						});
						this.oncontextmenu=function(){
							
							$(this).contextmenu({context:this,events:[{tag:'返回',cls:'splitline'},{tag:'设为坐席',cls:''},{tag:'设为通道',cls:''}],eventsFn:function(i){
								var x=$(this).attr('c'),y=$(this).attr('r');
								switch(i){
									case 0:
									break;
									case 1:
										matrix.setMatrixVal(x,y,1);
										$(this).removeClass().addClass(settings.seatCls);
									break;
									case 2:
										matrix.setMatrixVal(x,y,0);
										$(this).removeClass().addClass(settings.corridorCls);
									break;
									default:
									break;
								}
								x=y=null;
							}});
							return false;
						}
					});
				__map=null;
				x=y=null;
				/**setting mode**/
				$(settings.setValButton).bind('click',function(){
					settings.brushMode=parseInt(this.value);
				});
				$(settings.completeButton).bind('click',function(){
					settings.completeFn.call($this,matrix,table);
				});
				$(window).bind('mouseup',function(){
					settings.drawState=0;
				});
			});
		},
		setGroup:function(options){
			var settings=$.extend({
					groupIdEl:'.groupidel',
					groupColorEl:'.groupcolorel',
					setGroupButton:'.mice-setgroup',
					completeButton:'.mice-getgroup',
					completeFn:function(){},
					render:'groupListElement',
					prefix:'mice-showgroup-'
				},options);
			return this.each(function(){
				var $this=$(this);
				var groupArr={};
				$(settings.setGroupButton).bind('click',function(){
					var gid=gname=gcolor='',$box;
					$(settings.groupIdEl,$this).each(function(){
						gid=this.options[this.selectedIndex].value;
						gname=this.options[this.selectedIndex].text;
					});
					gcolor=$(settings.groupColorEl,$this).val();
					if(gid=='') return;
					if($('#'+settings.prefix+settings.render).attr('id')==settings.prefix+settings.render){
						$box=$('#'+settings.prefix+settings.render);
					}else{
						$box=$('<table class="groupColorList" id="'+settings.prefix+settings.render+'"></table>').append('<thead><tr><th width="100" height="30">分组ID</th><th width="100">分组名称</th><th width="100">分组颜色</th></tr></thead>').appendTo($('#'+settings.render));
					}
					
					if($(('#'+gid),$box).attr('id')==gid){
						groupArr[gid].color=gcolor;
						$('#'+gid,$box).find('td').eq(2).css('background-color',gcolor);
					}else{
						groupArr[gid]=new Groups(gid,gname,gcolor);
						$('<tr id="'+gid+'"><td>'+gid+'</td><td>'+gname+'</td><td style="background-color:'+gcolor+';">&nbsp;</td></tr>').appendTo($box);
					}
				});
				$(settings.completeButton).bind('click',function(){
					settings.completeFn.call(null,groupArr);
				});
			});
		}
		
	});
	//获取鼠标位置
	function mousePosition(ev){
		if(ev.pageX || ev.pageY){
			return {left:ev.pageX, top:ev.pageY};
		}
		return {
			left:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
			top:ev.clientY + document.body.scrollTop - document.body.clientTop
		};
	}
	//加入modlue
	exports.Sprite=Sprite;
	exports.TABLE=TABLE;
	exports.MATRIX=MATRIX;
	exports.$=$;
});