//表格隔行变色
function setDifStyleForTable(tableid){
	for(var i=0;i<arguments.length;i++){
		var tblObj=document.getElementById(arguments[i]);
		if(typeof(tblObj)!='undefined'){
			tblObj.rows[0].className="tbl_header";
			for(var j=1;j<tblObj.rows.length;j++){
				tblObj.rows[j].className=(j%2==0)?"t1":"t2"; 
			}
		}
	}
} 
//表格选中变色
function setFocusStyleForTable(){
    for(var i=0;i<arguments.length;i++){
		var tblObj=document.getElementById(arguments[i]);
		if(typeof(tblObj)!='undefined'){
			for(var j=1;j<tblObj.rows.length;j++){
			    var oldClassName='';
				tblObj.rows[j].onmouseover=function(){
				    oldClassName=this.className;
				    this.className="t3";
				};
				tblObj.rows[j].onmouseout=function(){
				    this.className=oldClassName;
				};
			}
		}
	}
}