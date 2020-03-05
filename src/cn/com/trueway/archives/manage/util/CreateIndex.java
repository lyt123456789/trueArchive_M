package cn.com.trueway.archives.manage.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.archiveModel.entity.EssTag;
import cn.com.trueway.archiveModel.listener.MsgWebSocket;
import cn.com.trueway.archiveModel.service.EssModelService;
import cn.com.trueway.archives.manage.util.FullTextSearchUtil;
import cn.com.trueway.archives.using.pojo.ArchiveMsg;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.archives.using.util.FtpUtil;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class CreateIndex {
	private EssModelService essModelService;
	private ArchiveUsingService archiveUsingService; 
	
	public void CreateIndex(Map<?, ?> params){
		try{
			//ComThread.InitSTA();
			String indexBasePath=SystemParamConfigUtil.getParamValueByParam("indexBasePath");//索引文件的基路径
			List<Map<String,String>> documentList = new ArrayList<Map<String,String>>();
			String treeId = params.get("treeId").toString();
			String id = params.get("id").toString();
			String indexName = params.get("indexName").toString();
			String structId = params.get("structId").toString();
			Employee emp = (Employee) params.get("emp");
			essModelService = (EssModelService)params.get("essModelService");
			archiveUsingService = (ArchiveUsingService)params.get("archiveUsingService");
			
			
			/*************************获取卷内级id（建立索引 是将卷内级档案 里面的文件查出来与卷内级关联作为一个索引）*/		
			String JNstructId = "";//卷内级id
			String WJstructId = "";//电子文件级id
			List<Map<String, Object>> strucList = essModelService.getChildStructure(Integer.valueOf(structId));
			if(strucList!=null){
				for(Map<String, Object> map:strucList) {
					String idChild = map.get("IDCHILD").toString();
					String title = map.get("TITLE").toString();
					if("卷内目录".equals(title)){
						JNstructId=idChild;
					}
					if("电子文件".equals(title)){
						WJstructId=idChild;
					}
				}
			}
			/****************************获取卷内级档案的数据*/
			//获取字段名(配置的展示字段)
			Map<String,String> map = new HashMap<String,String>();
			map.put("idStructure", JNstructId);
			List<EssTag> tags = essModelService.queryTagsForShow(map);
			//获取检索列
			List<EssTag> tags1 = essModelService.queryTagsForSearch(map);
			//获取排序列
			String orderSql = essModelService.queryTagsForOrder(map);
			
			//查询数据
			Map<String,String> map1 = new HashMap<String,String>();
			map1.put("struc", JNstructId);
			map1.put("orderSql", orderSql);
			List<Map<String, Object>> list = essModelService.getTaskDetails(map1,tags,tags1, null,null);
			
			/*******************************查询元数据*/
			Map<String,String> metamap = new HashMap<String,String>();
			metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('Title') and id_namespace=3");//题名
			List<String[]> metaDatalist1 = essModelService.getMetaDataList(metamap);
			metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('ManName') and id_namespace=3");//男方姓名
			List<String[]> metaDatalist2 = essModelService.getMetaDataList(metamap);
			metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('WomanName') and id_namespace=3");//女方姓名
			List<String[]> metaDatalist3 = essModelService.getMetaDataList(metamap);
			metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('RecordID') and id_namespace=3");//档号
			List<String[]> metaDatalist4 = essModelService.getMetaDataList(metamap);
			metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('PreservationPeriod') and id_namespace=3");//保管期限
			List<String[]> metaDatalist5 = essModelService.getMetaDataList(metamap);
			metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('Creator') and id_namespace=3");//责任者
			List<String[]> metaDatalist6 = essModelService.getMetaDataList(metamap);
			metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('Year') and id_namespace=3");//年度
			List<String[]> metaDatalist7 = essModelService.getMetaDataList(metamap);
			String metadata_title="";
			if(metaDatalist1!=null&&metaDatalist1.size()>0){
				metadata_title=metaDatalist1.get(0)[0];
			}
			String metadata_man="";
			if(metaDatalist2!=null&&metaDatalist2.size()>0){
				metadata_man=metaDatalist2.get(0)[0];
			}
			String metadata_woman="";
			if(metaDatalist3!=null&&metaDatalist3.size()>0){
				metadata_woman=metaDatalist3.get(0)[0];
			}
			String metadata_dh="";
			if(metaDatalist4!=null&&metaDatalist4.size()>0){
				metadata_dh=metaDatalist4.get(0)[0];
			}
			String metadata_bgqx="";
			if(metaDatalist5!=null&&metaDatalist5.size()>0){
				metadata_bgqx=metaDatalist5.get(0)[0];
			}
			String metadata_zrz="";
			if(metaDatalist6!=null&&metaDatalist6.size()>0){
				metadata_zrz=metaDatalist6.get(0)[0];
			}
			String metadata_nd="";
			if(metaDatalist7!=null&&metaDatalist7.size()>0){
				metadata_nd=metaDatalist7.get(0)[0];
			}
			int errorCount=0;
			boolean Flag=true;
			for(int i=0;i<list.size();i++){
				String JNdataId=list.get(i).get("id").toString();//卷内级数据的id，用来查询文件级数据的
				String title="";
				String man="";
				String woman="";
				String dh="";
				String bgqx="";
				String zrz="";
				String nd="";
				for(int j=0;j<tags.size();j++){
					if(metadata_title.equals(tags.get(j).getIdMetadata()+"")){
						title=list.get(i).get("C"+tags.get(j).getId())!=null?list.get(i).get("C"+tags.get(j).getId()).toString():"";
					}else if(metadata_man.equals(tags.get(j).getIdMetadata()+"")){
						man=list.get(i).get("C"+tags.get(j).getId())!=null?list.get(i).get("C"+tags.get(j).getId()).toString():"";
					}else if(metadata_woman.equals(tags.get(j).getIdMetadata()+"")){
						woman=list.get(i).get("C"+tags.get(j).getId())!=null?list.get(i).get("C"+tags.get(j).getId()).toString():"";
					}else if(metadata_dh.equals(tags.get(j).getIdMetadata()+"")){
						dh=list.get(i).get("C"+tags.get(j).getId())!=null?list.get(i).get("C"+tags.get(j).getId()).toString():"";
					}else if(metadata_bgqx.equals(tags.get(j).getIdMetadata()+"")){
						bgqx=list.get(i).get("C"+tags.get(j).getId())!=null?list.get(i).get("C"+tags.get(j).getId()).toString():"";
					}else if(metadata_zrz.equals(tags.get(j).getIdMetadata()+"")){
						zrz=list.get(i).get("C"+tags.get(j).getId())!=null?list.get(i).get("C"+tags.get(j).getId()).toString():"";
					}else if(metadata_nd.equals(tags.get(j).getIdMetadata()+"")){
						nd=list.get(i).get("C"+tags.get(j).getId())!=null?list.get(i).get("C"+tags.get(j).getId()).toString():"";
					}
				}
				if("".equals(title)){
					title=man+"--"+woman;
				}
				//查询原文，并组织原文内容
				errorCount=0;//每个卷内级目录中的电子文件获取text错误计数
				String content = getYWContent(JNdataId,JNstructId,WJstructId,errorCount);
				Map<String,String> indexMap = new HashMap<String,String>();
				indexMap.put("dataId", JNdataId);
				indexMap.put("fatherStructId", structId);
				indexMap.put("JNstructId", JNstructId);
				indexMap.put("WJstructId", WJstructId);
				indexMap.put("title", title);
				indexMap.put("dh", dh);
				indexMap.put("bgqx", bgqx);
				indexMap.put("zrz", zrz);
				indexMap.put("nd", nd);
				indexMap.put("content", content);
				if(content!=null&&!"".equals(content)&&errorCount==0){
					documentList.add(indexMap);
				}
				if(errorCount>0){
					Flag=false;//一旦出现错误就不创建索引 也不记录数据库
				}
			}
			
			//发送短消息
			ArchiveMsg msg = new ArchiveMsg();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			msg.setGlid("");
			msg.setMsgType("3");//1：借阅申请  2：调卷申请  3：  系统消息
			msg.setSender("系统");
			msg.setSendTime(sd.format(new Date()));
			msg.setStatus("0");
			msg.setRecevier(emp.getEmployeeName());
			try {
				if(Flag){
					String indexPath = indexBasePath+"/"+treeId+"_1/index";
					//创建索引
					FullTextSearchUtil.createIndexByMapList(documentList,indexPath);
					//索引创建完成后，数据库记录	
					String sql = " update  t_archive_fulltextindex set index_path='"+indexPath+"' where id='"+id+"'";
					archiveUsingService.updateStore(sql);
					msg.setContent_message(indexName+"索引创建成功！");
					archiveUsingService.updateArchiveMsg(msg);
					MsgWebSocket.onMessage("{\"To\":\""+emp.getEmployeeLoginname()+"\",\"msgType\":\"1\"}");
				}else{
					msg.setContent_message(indexName+"索引创建失败！");
					archiveUsingService.updateArchiveMsg(msg);
					MsgWebSocket.onMessage("{\"To\":\""+emp.getEmployeeLoginname()+"\",\"msgType\":\"1\"}");
				}
			} catch (Exception e) {
				msg.setContent_message(indexName+"索引创建失败！");
				archiveUsingService.updateArchiveMsg(msg);
				MsgWebSocket.onMessage("{\"To\":\""+emp.getEmployeeLoginname()+"\",\"msgType\":\"1\"}");
				e.printStackTrace();
			}
			
			//ComThread.Release();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	////查询原文，并组织原文内容(参数：卷内级文件数据的id ;卷内级 表的structid;文件级 表的structid)
	public String getYWContent(String JNdataId,String JNstructId,String WJstructId,int errorCount){
		/****************************获取文件级档案的数据*/
		//获取字段名(配置的展示字段)
		Map<String,String> map = new HashMap<String,String>();
		map.put("idStructure", WJstructId);
		List<EssTag> tags = essModelService.queryTagsForShow(map);
		
		Map<String,String> metamap = new HashMap<String,String>();
		metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('DocumentType') and id_namespace=2");//文件类型
		List<String[]> metaDatalist1 = essModelService.getMetaDataList(metamap);
		metamap.put("sql", "select * from ESS_METADATA where esidentifier in ('FilePath') and id_namespace=2");//原文路径
		List<String[]> metaDatalist2 = essModelService.getMetaDataList(metamap);
		String metadata_dt="";
		if(metaDatalist1!=null&&metaDatalist1.size()>0){
			metadata_dt=metaDatalist1.get(0)[0];
		}
		String metadata_fp="";
		if(metaDatalist2!=null&&metaDatalist2.size()>0){
			metadata_fp=metaDatalist2.get(0)[0];
		}
		String column="id,";
		for(int i=0;i<tags.size();i++){
			if(metadata_dt.equals(tags.get(i).getIdMetadata()+"")){
				column+="C"+tags.get(i).getId()+",";//文件类型
			}else if(metadata_fp.equals(tags.get(i).getIdMetadata()+"")){
				column+="C"+tags.get(i).getId()+",";//文件路径
			}
		}
		column=column.substring(0, column.length()-1);
		String sql =" select "+column+" from ESP_"+WJstructId+" where id_parent_package="+JNdataId;
		List<String[]> infolist = essModelService.queryInfo(sql,column.split(",").length);
		String content= getFtpFileContent(infolist,JNdataId,JNstructId,column.split(",").length,errorCount);
		return content;
	}
	
	//组织原文内容(参数：文件级数据集合  ; 卷内级structid;集合的列数)
	public String getFtpFileContent(List<String[]> infolist,String JNdataId,String JNstructId,int columnsize,int errorCount){
		String ftpTempPath=SystemParamConfigUtil.getParamValueByParam("ftpTempPath");//从ftp下载下来的文件临时地址
		File tempfile = new File(ftpTempPath);
		if(!tempfile.exists()){
			tempfile.mkdirs();
		}
		String content="";
		for(int i=0;infolist!=null&&infolist.size()>0&&i<infolist.size();i++){
			String id= infolist.get(i)[0];
			try {	
				String ywlj = infolist.get(i)[2];//文件级 数据的原文路径  如：FTP://ftpServerNew/data/D017/D017-2001-001-0001_2273.pdf
				String ftpsql = " select filepath from ESS_RULE_SCANPOLICY where id_structure="+JNstructId;
				int returnColumn=1;//需要查询的列数
				List<String[]> ftpinfo = essModelService.queryInfo(ftpsql,returnColumn);
				if(ftpinfo!=null&&ftpinfo.size()>0){
					String ftpparam = ftpinfo.get(0)[0];//例如  FTP://ftpServer/data
					String protocol = ftpparam.split(":")[0];
					String serveralias = ftpparam.split("//")[1].split("/")[0];
					String path = ftpparam.split("//")[1].split("/")[1];
					String serverSql = " select server,fileserverport,username,psw,path from  ESSFILESERVER where protocol='"+protocol+"' and serveralias='"+serveralias+"' and path='"+path+"'";
					List<String[]> ftpServerinfo = essModelService.queryInfo(serverSql,5);
					if(ftpServerinfo!=null&&ftpServerinfo.size()>0){
						String server = ftpServerinfo.get(0)[0];
						String fileserverport = ftpServerinfo.get(0)[1];
						String username = ftpServerinfo.get(0)[2];
						String psw = ftpServerinfo.get(0)[3];			
						String fileName = ywlj.split("/")[ywlj.split("/").length-1];
						String str = ywlj.split(serveralias)[1];//如    /data/D017/D017-2001-001-0001_2273.pdf
						String wjpath = str.split(fileName)[0];
						File file = FtpUtil.getFile(server, Integer.valueOf(fileserverport), username, psw, wjpath, fileName,ftpTempPath);
						if(file!=null){
							content += FullTextSearchUtil.getTextContent(file);
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				errorCount++;
				System.out.println("error***********************************ESP_"+JNstructId+"表中id为"+JNdataId+"的卷内目录中的id为"+id+"的电子文件获取内容失败");
				e.printStackTrace();
			}		
		}
		return content;
	}

}
