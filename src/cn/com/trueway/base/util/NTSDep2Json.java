package cn.com.trueway.base.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.trueway.document.business.util.GetAxisInterface;



/**
 * 描述：得到 市级单位 JSON字符串;<br>
 * 
 * 作者：王雪峰<br>
 * 创建时间：2011-8-10 下午05:51:28<br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class NTSDep2Json {
	@SuppressWarnings("unchecked")
	public static String getJson(File xmlFile) throws DocumentException {
		//生成SAXReader对象用来读取XML文件
		SAXReader readerXML = new SAXReader();
		//读取XML文件返回Document对象
		Document doc = readerXML.read(xmlFile);
		//得到该文件的根节点
		Element root = doc.getRootElement();
		//得到DEPARTMENTSORT标签的集合
		List<Element> depList= root.elements("DEPARTMENTSORT");
		//得到DEPARTMENTSORT标签的个数
		int depListSize = depList.size();
		//生成StringBuffer对象用来存放Json字符串
		StringBuffer json = new StringBuffer();
		//开始解析文件，并将解析的内容按固定的格式存到json中
		json.append("[");
		for(int i=0;i<depListSize;i++){
			Element dep = depList.get(i);
			json.append("{");
			json.append("id:").append("'").append(dep.attributeValue("ID")).append("',");
			json.append("name:").append("'").append(dep.elementText("NAME")).append("',");
			json.append("ind:").append("'").append(dep.elementText("IND")).append("',");
			json.append("children:").append("[");
			List<Element> depChildList=dep.element("DEPARTMENTCHILDREN").elements("DEPARTMENTCHILD");
			int depChildListSize = depChildList.size();
			for(int j=0;j<depChildListSize;j++){
				Element depChild = depChildList.get(j);
				json.append("{");
				json.append("id:").append("'").append(depChild.elementText("ID")).append("',");
				json.append("name:").append("'").append(depChild.elementText("NAME")).append("',");
				json.append("gzname:").append("'").append(depChild.elementText("GZNAME")).append("',");
				json.append("ind:").append("'").append(depChild.elementText("IND")).append("'");
				//判断是否是集合的最后一个元素，从而做不同和操作
				if(j==depChildListSize-1){
					json.append("}");
				}else{
					json.append("},");
				}
			}
			json.append("]");
			//判断是否是集合的最后一个元素，从而做不同和操作
			if(i==depListSize-1){
				json.append("}");
			}else{
				json.append("},");
			}
		}
		json.append("]");
		//解析结束 返回
		return json.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static String getJson(String xmlStr) throws DocumentException {
		//将字符串转为XML  返回Document对象
		Document doc = DocumentHelper.parseText(xmlStr);  
		//得到该文件的根节点
		Element root = doc.getRootElement();
		List<Element> depList= root.elements("DEPARTMENTSORT");
		int depListSize = depList.size();
		StringBuffer json = new StringBuffer();
		json.append("[");
		for(int i=0;i<depListSize;i++){
			Element dep = depList.get(i);
			json.append("{");
			json.append("id:").append("'").append(dep.attributeValue("ID")).append("',");
			json.append("name:").append("'").append(dep.elementText("NAME")).append("',");
			json.append("ind:").append("'").append(dep.elementText("IND")).append("',");
			json.append("children:").append("[");
			List<Element> depChildList=dep.element("DEPARTMENTCHILDREN").elements("DEPARTMENTCHILD");
			int depChildListSize = depChildList.size();
			for(int j=0;j<depChildListSize;j++){
				Element depChild = depChildList.get(j);
				json.append("{");
				json.append("id:").append("'").append(depChild.elementText("ID")).append("',");
				json.append("name:").append("'").append(depChild.elementText("NAME")).append("',");
				json.append("gzname:").append("'").append(depChild.elementText("GZNAME")).append("',");
				json.append("ind:").append("'").append(depChild.elementText("IND")).append("'");
				//判断是否是集合的最后一个元素，从而做不同和操作
				if(j==depChildListSize-1){
					json.append("}");
				}else{
					json.append("},");
				}
			}
			json.append("]");
			//判断是否是集合的最后一个元素，从而做不同和操作
			if(i==depListSize-1){
				json.append("}");
			}else{
				json.append("},");
			}
		}
		json.append("]");
		//解析结束 返回
		return json.toString(); 
	}
	
	//城管 获得部门json
	public static String getJson4Cg() {
		try {
			String xmlStr = GetAxisInterface.getDept("");
			//将字符串转为XML  返回Document对象
//			String str = "<?xml version=\"1.0\" encoding=\"gb2312\"?><EpointDataBody><DATA><UserArea><ReturnInfo><Status>Success</Status><Description></Description></ReturnInfo><OrgList><OUInfo><OUGuid>ec11cfd6-e79a-4380-b5f6-e3fc405ea4f2</OUGuid><OUName>中共南通市通州区委</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>1200</OrderNumber></OUInfo><OUInfo><OUGuid>c2ee9229-bdf6-47b4-9834-7b144ca564c9</OUGuid><OUName>南通市通州区人大常委会</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>1100</OrderNumber></OUInfo><OUInfo><OUGuid>cf0e34ed-3ea9-4261-98e6-83735df6d2ff</OUGuid><OUName>南通市通州区人民政府</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>1050</OrderNumber></OUInfo><OUInfo><OUGuid>cd629f3f-c0ad-4840-90b9-e483f5937157</OUGuid><OUName>政协南通市通州区委员会</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>1000</OrderNumber></OUInfo><OUInfo><OUGuid>3db1d42c-f6bc-4326-8916-0dae26a4906e</OUGuid><OUName>区委办公室</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>900</OrderNumber></OUInfo><OUInfo><OUGuid>7d81fd23-e2ce-4293-aa3f-4254e7fbd9f4</OUGuid><OUName>区人大工作机关</OUName><IsSub>1</IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>800</OrderNumber></OUInfo><OUInfo><OUGuid>4e8d19e9-3cd1-4f99-9db1-f172d74eade3</OUGuid><OUName>区政府办公室</OUName><IsSub>1</IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>700</OrderNumber></OUInfo><OUInfo><OUGuid>3c40e974-dc65-47a5-87a2-0dbd34c99b5d</OUGuid><OUName>区政协工作机关</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>600</OrderNumber></OUInfo><OUInfo><OUGuid>2f92daf8-d434-4285-8079-71b0bc889cc1</OUGuid><OUName>区纪委、人武部、区两院</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>188</OrderNumber></OUInfo><OUInfo><OUGuid>c0723624-bc19-40d7-a1eb-77b800c2a67a</OUGuid><OUName>区委工作部门</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>186</OrderNumber></OUInfo><OUInfo><OUGuid>00e2ba74-e944-4778-a8bf-957e6857c198</OUGuid><OUName>区政府工作部门</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>182</OrderNumber></OUInfo><OUInfo><OUGuid>00118e41-46c7-486a-bb5b-1e8a808ec833</OUGuid><OUName>部分区委区政府事业机构、供销总社</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>176</OrderNumber></OUInfo><OUInfo><OUGuid>eb65d2a5-8c29-4e86-8338-236b7a6c5ae3</OUGuid><OUName>部分挂靠、合署、部门管理机构</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>174</OrderNumber></OUInfo><OUInfo><OUGuid>dce8e3f7-0bf3-43bb-87b4-4efbdeec6297</OUGuid><OUName>人民团体、民主党派、记者站、部队</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>172</OrderNumber></OUInfo><OUInfo><OUGuid>eaa745b5-315e-4108-82ec-0d7426c14828</OUGuid><OUName>关工委、老促会、老科协、老体协、老年协会、慈善基金会</OUName><IsSub>1</IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>168</OrderNumber></OUInfo><OUInfo><OUGuid>922468b8-df2b-4ac1-9fc4-0c95679d8c52</OUGuid><OUName>双重管理机构(非金融保险)、直管单位</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>58</OrderNumber></OUInfo><OUInfo><OUGuid>38642baa-c8c7-4041-ab61-42e5a44a6abf</OUGuid><OUName>双重管理机构（部分金融保险）</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>56</OrderNumber></OUInfo><OUInfo><OUGuid>e771b3da-12e2-49a2-bc2c-f6125d76037a</OUGuid><OUName>区、园、镇</OUName><IsSub></IsSub><HasSubWeb>1</HasSubWeb><OrderNumber>55</OrderNumber></OUInfo><OUInfo><OUGuid>24fb78c6-71e5-4166-830b-4c5e83b31e85</OUGuid><OUName>区全国文明城市建设工作领导小组</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>50</OrderNumber></OUInfo><OUInfo><OUGuid>fa137b76-5df5-40e8-b666-d53764653ed2</OUGuid><OUName>区城市建设指挥部</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>40</OrderNumber></OUInfo><OUInfo><OUGuid>b2a5ff65-c662-4a91-8d77-695f6e621f39</OUGuid><OUName>横港沙工程建设指挥部</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>30</OrderNumber></OUInfo><OUInfo><OUGuid>f2f7d892-90cf-4a42-881d-e5549484e64f</OUGuid><OUName>系统管理部</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>0</OrderNumber></OUInfo><OUInfo><OUGuid>595063cb-5865-44dc-9256-54f42fad9aec</OUGuid><OUName>一河两岸工程建设指挥部</OUName><IsSub>1</IsSub><HasSubWeb></HasSubWeb><OrderNumber>0</OrderNumber></OUInfo></OrgList></UserArea></DATA></EpointDataBody>";
			Document doc = DocumentHelper.parseText(xmlStr);  
			//得到该文件的根节点
			Element root = doc.getRootElement();
			List<Element> depList= root.element("DATA").element("UserArea").element("OrgList").elements("OUInfo");
			int depListSize = depList.size();
			StringBuffer json = new StringBuffer();
			json.append("[");
			for(int i=0;i<depListSize;i++){
				System.out.println(i);
				Element dep = depList.get(i);
				json.append("{");
				json.append("id:").append("'").append(dep.elementText("OUGuid")).append("',");
				json.append("name:").append("'").append(dep.elementText("OUName")).append("',");
				json.append("ind:").append("'").append("',");
				json.append("children:").append("[");
				if("1".equals(dep.elementText("HasSubWeb"))){
					String childXml = GetAxisInterface.getDept(dep.elementText("OUGuid"));
					Document childDep = DocumentHelper.parseText(childXml);  
					Element childRoot = childDep.getRootElement();
					List<Element> depChildList= childRoot.elements("OrgList");
					int depChildListSize = depChildList.size();
					for(int j=0;j<depChildListSize;j++){
						Element cdep = depList.get(j);
						json.append("{");
						json.append("id:").append("'").append(cdep.elementText("OUGuid")).append("',");
						json.append("name:").append("'").append(cdep.elementText("OUName")).append("',");
						json.append("gzname:").append("'").append("").append("',");
						json.append("ind:").append("'").append("").append("'");
						if(j==depChildListSize-1){
							json.append("}");
						}else{
							json.append("},");
						}
					}
				}
				
				json.append("]");
				//判断是否是集合的最后一个元素，从而做不同和操作
				if(i==depListSize-1){
					json.append("}");
				}else{
					json.append("},");
				}
			}
			json.append("]");
			//解析结束 返回
			return json.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public static void main(String[] args) {
//		try {
//			File xmlFile = new File("C:\\南通市发文单位.xml");
//			System.out.println(NTSDep2Json.getJson(xmlFile));
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
		try {
//			String s = "<?xml version='1.0' encoding='UTF-8'?><DEPARTMENTS><DEPARTMENTSORT ID='ldjg'><NAME>市四套班子及军分</NAME><IND>1</IND><DEPARTMENTCHILDREN><DEPARTMENTCHILD><ID>{BFA811EA-0000-0000-455E-F607000012D8}</ID><NAME>市委</NAME><GZNAME>市委</GZNAME><IND>1</IND></DEPARTMENTCHILD><DEPARTMENTCHILD><ID>{BFA811EA-0000-0000-4557-1B100000076E}</ID><NAME>市人大</NAME><GZNAME>市人大</GZNAME><IND>2</IND></DEPARTMENTCHILD><DEPARTMENTCHILD><ID>{BFA811EA-0000-0000-455A-C2C600000CEC}</ID><NAME>市政府</NAME><GZNAME>市政府</GZNAME><IND>3</IND></DEPARTMENTCHILD></DEPARTMENTCHILDREN></DEPARTMENTSORT></DEPARTMENTS>";
//			System.out.println(NTSDep2Json.getJson(s));
//			getJson4Cg();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
