package cn.com.trueway.archives.templates.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.dom4j.DocumentException;

import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.archives.templates.pojo.EssZDXZCommon;
import cn.com.trueway.archives.templates.service.DataReceptionWebService;
import cn.com.trueway.archives.templates.service.DataTempService;
import cn.com.trueway.base.util.JsonUtil;

public class DataReceptionWebServiceImpl implements DataReceptionWebService {

	
	private DataTempService dataTempService;

	public void setDataTempService(DataTempService dataTempService) {
		this.dataTempService = dataTempService;
	}

	private static final String FAIL = "-1";
	private static final String SUCCESS = "1";
 
	@Override
	public String saveDataReception(String data) {
		String treeId = "100037";
		String structureId = "20001";
		String parentId = "";
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("tableFlag","ofForm");
		List<EssTag> etList1 = dataTempService.getEssTagList(map,null,null);
		List<EssTag> etList = new ArrayList<EssTag>();
		List<EssZDXZCommon> gridList = this.dataTempService.getZDXZDataList(map);
		for(int i = 0; i < gridList.size()&&gridList!=null;i++){
			int tagId = gridList.get(i).getIdtag();
			for(int j = 0; j < etList1.size()&&etList1!=null;j++){
				int id = etList1.get(j).getId();
				if(id == tagId){
					etList.add(etList1.get(j));
					break;
				}
			}
		}
		JSONObject jo = null;
		try {
			jo = JsonUtil.xml2Json(data);
			System.out.println(jo);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Object dataReception = getJSONValue(jo,"dataReception");
		JSONArray jarr = JSONArray.fromObject(dataReception);
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < jarr.size();i++){
			Map<String,Object> map2 = new HashMap<String, Object>();
			Object obj = jarr.get(i);
			JSONObject jo1 = JSONObject.fromObject(obj);
			for(int j = 0; j < etList.size();j++){
				EssTag essTag = etList.get(j);
				//esIdentifier
				String esIdentifier = essTag.getEsIdentifier();
				String esIsNotNull = essTag.getEsIsNotNull();
				Object obj1 = getJSONValue(jo1,"C"+essTag.getId());
				if("1".equals(esIsNotNull)){
					if(obj1 == null || "".equals(obj1)){
						return returnErrorMessage("C"+essTag.getId());
					}
				}
				map2.put("C"+etList.get(j).getId(), obj1);
			}
			maps.add(map2);
		}
		for(int i = 0; i < maps.size();i++){
			Map<String, Object> map1 = maps.get(i);
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("id", treeId);
			List<EssTree> data1 = dataTempService.getTreeByMap(map2,null,null);
			EssTree et = data1.get(0);
			//获取结构层次信息(递归获取父级结构)
			map2.clear();
			map2.put("structureId", structureId);
			map2.put("type", "-dg");//反递归查询     查询下级   dg   -dg反递归 查询上级
			List<EssStructure> stList = dataTempService.getStructureList(map2,null,null);
			String espath="";
			String id_parent_structure = "";
			String id_parent_package = "";
			if(stList!=null&&stList.size()==1){//本身就是最顶层结构
				id_parent_structure = "1";
				id_parent_package = treeId;
				espath = et.getEsPath();
			}else{
				id_parent_structure = stList.get(stList.size()-2).getId()+"";
				if("".equals(parentId)){
					id_parent_package = "-1";
					String ss="";
					for(int j=0;j<stList.size()-1;j++){//减1是为了排除自己
						ss+="/@"+stList.get(j).getId();
					}	
					espath = et.getEsPath()+ss;
				}else{
					id_parent_package = parentId;
					String sql = "select espath,1 from ESP_"+id_parent_structure+" where id="+parentId;
					List<Object[]> list = dataTempService.excuteNativeSql(sql);
					espath = list.get(0)[0]+"/"+parentId+"@"+id_parent_structure;
				}	
			}
			map2.clear();
			map2.put("structureId", structureId);
			List<EssTag> etList2 = dataTempService.getEssTagList(map2,null,null);
			String sql=" insert into ESP_"+structureId;
			String column1="id,espath,id_parent_structure,id_parent_package,tree_nodeid";
			String value1="SEQ_ESP_"+structureId+".nextval,'"+espath+"','"+id_parent_structure+"','"+id_parent_package+"','"+treeId+"'";
			String column2="";
			String value2="";
			for(int j=0;j<etList2.size();j++){
				if("归档状态".equals(etList2.get(j).getEsIdentifier())){
					column2+=",C"+etList2.get(j).getId();
					value2+=",'未归档'";
				}
			}
			for(String key:map1.keySet()) {
				column2 += ","+key;
				value2 += ",'"+map1.get(key)+"'";
			}
			sql=sql+" ("+column1+column2+") values "+" ( "+value1+value2+" ) ";
			dataTempService.updateNativeSql(sql);
		}
		return returnSuccessMessage();
	}
	
	public Object getJSONValue(JSONObject jo,String key){
		try{
			Object obj = jo.get(key);
			return obj;
		}catch(Exception e){
			return "";
		}
	}
	
	//判断非空数据
	public boolean isNotBLANK(Object obj){
		if(obj == null || "".equals(obj)){
			return true;
		}else{
			return false;
		}
	}
	
	public String returnErrorMessage(String attribute){
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><attribute>"+attribute+"</attribute><status>"+attribute+" cannot be empty</status>";
	}
	public String returnSuccessMessage(){
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><status>success</status>";
	}

	public boolean uploadImageWithByte(byte[] imageByte, int length,String filename) {
		FileOutputStream fos = null;
		try {
			// 将上传的文件保存在D盘的文件中，注意设置为true续写文件
			fos = new FileOutputStream("c:\\"+filename,true);
			fos.write(imageByte,0, length);
			fos.close();
		} catch (Exception e) {
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
 
				}
			}
		}
		return true;
	}
	
	@Override
	public String upload(DataHandler handler, String fileName, String json_str,String file64) throws Exception {
		String chgName = "tomcat8.rar";
		String realPath = "c:\\11";
		
		
		/*InputStream inputStream = null;
		inputStream = handler.getInputStream();*/
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String attachmentName = realPath + "\\" + chgName;
		
		
		
		//处理附件
		 // 解码，然后将字节转换为文件
       byte[] bytes = Base64.getDecoder().decode(file64); // 将字符串转换为byte数组
       FileOutputStream  out = null;
       try {
       	//转化为输入流
       	ByteArrayInputStream in = new ByteArrayInputStream(bytes);

       	//写出文件
       	byte[] buffer = new byte[1024];

       	out = new FileOutputStream(new File(attachmentName));

       	//写文件
       	int len = 0;
       	while ((len = in.read(buffer)) != -1) {
       		out.write(buffer, 0, len); // 文件写操作
       	}
       } catch (Exception e) {
       	// TODO Auto-generated catch block
       	e.printStackTrace();
       }finally{
       	try {
       		if(out != null){
       			out.close();
       		}
       	} catch (IOException e) {
       		// TODO Auto-generated catch block
       		e.printStackTrace();
       	}
	}
		
		
		
		
		
	/*	int flag = 0;
		try {
			InputStream input = handler.getInputStream();
			FileOutputStream fos = new FileOutputStream(new File(attachmentName));
			byte[] buffer=new byte[4096];
			while((flag=input.read(buffer))!=-1){
				fos.write(buffer,0,flag);
			}
			input.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "failure";
		}*/
		/*InputStream is = handler.getInputStream();
		OutputStream os = new FileOutputStream(new File(attachmentName));
		
		IOUtils.copy(is, os);*/
		/*File ff = new File(attachmentName);
		FileOutputStream fileOut = null;
		fileOut = new FileOutputStream(ff);
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(b)) != -1)
			fileOut.write(b, 0, len);
 
		if (fileOut != null)
			fileOut.close();
		if (inputStream != null)
			inputStream.close();*/
 
		//处理数据
		System.out.println(file64);
		return SUCCESS;
	}

	//获取xml模板
	@Override
	public String achiveXMLMB() {
		String structureId = "20001";
		Map<String,String> map = new HashMap<String, String>();
		map.put("structureId", structureId);
		map.put("tableFlag","ofForm");
		List<EssTag> etList1 = dataTempService.getEssTagList(map,null,null);
		List<EssTag> etList = new ArrayList<EssTag>();
		List<EssZDXZCommon> gridList = this.dataTempService.getZDXZDataList(map);
		for(int i = 0; i < gridList.size()&&gridList!=null;i++){
			int tagId = gridList.get(i).getIdtag();
			for(int j = 0; j < etList1.size()&&etList1!=null;j++){
				int id = etList1.get(j).getId();
				if(id == tagId){
					etList.add(etList1.get(j));
					break;
				}
			}
		}
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><dataReceptionList><dataReception>";
		for(int i = 0; i < etList.size();i++){
			EssTag essTag = etList.get(i);
			//esIdentifier
			xml += "<C"+essTag.getId()+">"+essTag.getEsIdentifier();
			//esIsNotNull
			if("1".equals(essTag.getEsIsNotNull())){
				xml += "(必填)";
			}
			xml += "</C"+essTag.getId()+">";
		}
		xml += "</dataReception></dataReceptionList>";
		return xml;
	}

}
