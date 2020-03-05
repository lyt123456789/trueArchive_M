			//获取JS传参
	function getJsSrc(name,paras){
		var scripts=document.getElementsByTagName('script'),i,n,slen=scripts.length,plen=paras.length,returns=[];
		for(i=0;i<slen;i++){
			var src=scripts[i].src;
			if(src.indexOf(name)>-1){
				for(n=0;n<plen;n++){
					returns.push(src.getjsparas(paras[n]))
				}
				return returns;
			}
		}
	}
	//获取js的参数
	String.prototype.getjsparas=function (name){     
		var paraString = this.substring(this.indexOf("?")+1,this.length).split("&");     
		var paraObj = {}     
		for (i=0; j=paraString[i]; i++){     
			paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);     
		}     
		var returnValue = paraObj[name.toLowerCase()];     
		if(typeof(returnValue)=="undefined"){     
			return "";     
		}else{     
			return returnValue;     
		}     
	}
			var navparams=getJsSrc('displayTag_page',['topages']); 			
			function fucCheck(INDEX){
                var i, j, strTemp;
                strTemp = "0123456789";
                for (i = 0; i < INDEX.length; i++) {
                    j = strTemp.indexOf(INDEX.charAt(i));
                    if (j == -1) {
                        //说明有字符不合法        
                        return false;
                    }
                }
                //说明合法        
                return true;
            }
 			
            function firstPage_Go(){
                var page = document.getElementById("tz").value;
                var total = navparams; // 当前数据的总页数，从Action中传值过来   
               
                if (page=='') {
                    alert("页数不能为空！");
                    return false;
                }
                if (!isNumber(page)) {
                    alert("输入页数非法，请重新输入！");
                    return false;
                }
                if(parseFloat(page) > parseFloat(total)){   
                    alert("超出了最大页数，请重新输入！");   
                }
                else {
                    if (parseFloat(page) < 1) {
                        alert("页数不能小于1！");
                    }
                    else {
                        window.location = document.getElementById("hd").value.replace("p=", "p=" + document.getElementById("tz").value);
                    }
            }
            }
            function OtherPage_Go(){
            	
                var page = document.getElementById("tz").value;
                var total = navparams;// 当前数据的总页数，从Action中传值过来   
                if (page=='') {
                    alert("页数不能为空！");
                    return false;
                }
                if (!isNumber(page)) {
                    alert("输入页数非法，请重新输入！");
                    return false;
                }
                if(parseFloat(page) > parseFloat(total)){   
                	
                   alert("超出了最大页数，请重新输入！");   
                }
                else {
                    if (parseFloat(page) < 1) {
                        alert("页数不能小于1！");
                    }
                    else {
                        window.location = document.getElementById("hd").value.replace("p=1", "p=" + document.getElementById("tz").value);
                    }
            }
            }
         
        	
            function openNewPage(URL,oaurl) {
        		var w = parseInt(window.screen.width * 100 / 100, 10);
        		var h = parseInt(window.screen.availHeight * 100 / 100, 10);
        		var l = parseInt((window.screen.width - w) / 2, 10);
        		var t = parseInt((window.screen.availHeight - h ) / 2, 10);
        		var st = "top="
        				+ t
        				+ ",left="
        				+ l
        				+ ",height="
        				+ h
        				+ ", width="
        				+ w
        				+ ", toolbar= yes, menubar=yes, scrollbars=yes, resizable=yes, location=yes, status=yes";
        		if(arguments.length == 2){
        			URL = oaurl + "/employeeright_showUrlWithOAFrame.do?service=" + encodeURIComponent(URL);
        		}
        		window.open( URL,"", st);
        	}
        	function isNumber(oNum){
        	    if (!oNum) 
        	        return false;
        	    var strP = /^-?\d+(\.\d+)?$/;
        	    if (!strP.test(oNum)) 
        	        return false;
        	    try {
        	        if (parseFloat(oNum) != oNum) 
        	            return false;
        	    } 
        	    catch (ex) {
        	        return false;
        	    }
        	    return true;
        	}