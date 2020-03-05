package cn.com.trueway.archiveModel.service.impl;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

import cn.com.trueway.archiveModel.dao.EssModelDao;
import cn.com.trueway.archiveModel.service.EssEepService;
import cn.com.trueway.workflow.core.pojo.Department;

@Service("essEepService")
public class EssEepServiceImpl implements EssEepService {

	@Autowired
	private EssModelDao essModelDao;
	
	

	public EssModelDao getEssModelDao() {
		return essModelDao;
	}

	public void setEssModelDao(EssModelDao essModelDao) {
		this.essModelDao = essModelDao;
	}

	@Override
	public String logOutEEP(HttpServletRequest request) {
		String id = request.getParameter("id");//数据id
		String struc = request.getParameter("struc");//结构id
		String codeId = request.getParameter("codeId");//节点id
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
		//Department dept = (Department) request.getSession().getAttribute(Constant.SESSION_DEPARTMENT);
		String quanzong="全宗名";
		quanzong = essModelDao.getTopTreeOfBranch(codeId);//全宗名
		List<Map<String, Object>> neededFields = essModelDao.getNeededFields(struc);
		String FondID="全宗号";
		String Year="年度";
		String RecordID="档号";
		String Classification="分类号";
		String FilesID="案卷号";
		String Title="案卷题名";
		String Creator="责任者";
		String PreservationPeriod="保管期限";
		String SecurityLevel="密级";
		String PageCount="总页数";
		String Keyword="主题词";
		String DocumentID="文件编号";
		String DocumentType="文件类别";
		String FilePath="原文路径";
		String FileSize="文件大小";
		String DeptPath="所属部门";
				
		if(neededFields!=null&&neededFields.size()>0) {
			for(int j=0;j<neededFields.size();j++) {
				String zdid = neededFields.get(j).get("ID").toString();
				String zdname = neededFields.get(j).get("NAME").toString();
				String sql = "select C"+zdid+" as \""+zdname+"\" from ESP_"+struc+" where ID="+id;
				String oneMes = essModelDao.getOneMesByTag(sql);
				if(StringUtils.isNotBlank(oneMes)) {
					if("FondID".equals(zdname)) {
						FondID=oneMes;
					}else if("Year".equals(zdname)) {
						Year=oneMes;
					}else if("RecordID".equals(zdname)) {
						RecordID=oneMes;
					}else if("Classification".equals(zdname)) {
						Classification=oneMes;
					}else if("FilesID".equals(zdname)) {
						FilesID=oneMes;
					}else if("Title".equals(zdname)) {
						Title=oneMes;
					}else if("Creator".equals(zdname)) {
						Creator=oneMes;
					}else if("PreservationPeriod".equals(zdname)) {
						PreservationPeriod=oneMes;
					}else if("SecurityLevel".equals(zdname)) {
						SecurityLevel=oneMes;
					}else if("PageCount".equals(zdname)) {
						PageCount=oneMes;
					}else if("Keyword".equals(zdname)) {
						Keyword=oneMes;
					}else if("DocumentID".equals(zdname)) {
						DocumentID=oneMes;
					}else if("DocumentType".equals(zdname)) {
						DocumentType=oneMes;
					}else if("FilePath".equals(zdname)) {
						FilePath=oneMes;
					}else if("FileSize".equals(zdname)) {
						FileSize=oneMes;
					}else if("DeptPath".equals(zdname)) {
						DeptPath=oneMes;
					}
				}
			}
		}
		//签名
		String bqmdx = "<xs:element name=\"被签名对象\"><xs:annotation><xs:documentation>被电子签名的部分</xs:documentation>" + 
				"</xs:annotation><xs:complexType><xs:sequence><xs:element ref=\"封装包类型\"/><xs:element ref=\"封装包类型描述\"/>" + 
				"<xs:element ref=\"封装包创建时间\"/><xs:element ref=\"封装包创建单位\"/><xs:choice><xs:element ref=\"封装内容\"/>" + 
				"<xs:element ref=\"修改封装内容\"/></xs:choice></xs:sequence><xs:attribute name=\"eep版本\" type=\"xs:gYear\" use=\"required\" fixed=\"2009\">" + 
				"<xs:annotation><xs:documentation>本属性的值和元素“版本”的值相同，且被电子签名，用于验证版本的真实性</xs:documentation>" + 
				"</xs:annotation></xs:attribute></xs:complexType></xs:element>";
		String sign = this.getSign(bqmdx.trim(),id,struc);
		
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\r\n" + 
				"xmlns=\"http://www.saac.gov.cn/standards/ERM/encapsulation\"\r\n" + 
				"targetNamespace=\"http://www.saac.gov.cn/standards/ERM/encapsulation\"\r\n" + 
				"elementFormDefault=\"qualified\">");
		xml.append(" <xs:element name=\"电子文件封装包\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>eep 根元素</xs:documentation>\r\n" + " </xs:annotation>\r\n" + 
				" <xs:complexType>\r\n" + " <xs:sequence>\r\n" + " <xs:element ref=\"封装包格式描述\"/>\r\n" + 
				" <xs:element ref=\"版本\"/>\r\n" + " <xs:element ref=\"被签名对象\"/>\r\n" + 
				" <xs:sequence minOccurs=\"0\">\r\n" + " <xs:element ref=\"电子签名块\"/>\r\n" + 
				" <xs:element ref=\"锁定签名\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:sequence>\r\n" + 
				" </xs:complexType>\r\n" + " </xs:element>");	
		xml.append(" <xs:element name=\"被签名对象\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>被电子签名的部分</xs:documentation>\r\n" + " </xs:annotation>\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"eep\"/>\r\n"+ "<xs:element ref=\"封装包类型描述\"/>\r\n" + 
				" <xs:element ref=\"封装包创建时间\"/>\r\n" + " <xs:element ref=\"封装包创建单位\"/>\r\n" + 
				" <xs:choice>\r\n" + " <xs:element ref=\"封装内容\"/>\r\n" + " <xs:element ref=\"修改封装内容\"/>\r\n" + 
				" </xs:choice>\r\n" + " </xs:sequence> \r\n" + 
				" <xs:attribute name=\"eep版本\" type=\"xs:gYear\" use=\"required\" fixed=\"2009\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>本属性的值和元素“版本”的值相同，且被电子签名，用于验证版本的真实性</xs:documentation>\r\n" + 
				" </xs:annotation>\r\n" + " </xs:attribute>\r\n" + " </xs:complexType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"封装内容\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"文件实体块\"/>\r\n" + " <xs:element ref=\"业务实体块\"/>\r\n" +
				" <xs:element ref=\"机构人员实体块\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + 
				" </xs:element>\r\n" + " <xs:element name=\"文件实体块\">\r\n" + " <xs:complexType>\r\n" + 
				" <xs:sequence>\r\n" + " <xs:element ref=\"文件实体\"/>\r\n" + 
				" <xs:element ref=\"文件实体关系\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"文件实体\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"聚合层次\"/>\r\n"+ " <xs:element ref=\"来源\"/>\r\n" + " <xs:element ref=\"电子文件号\"/>\r\n" + 
				" <xs:element ref=\"档号\"/>\r\n"+" <xs:element ref=\"内容描述\"/>\r\n" + " <xs:element ref=\"形式特征\"/>\r\n" + 
				" <xs:element ref=\"存储位置\"/>\r\n" +" <xs:element ref=\"权限管理\"/>\r\n" +
				" <xs:element ref=\"信息系统描述\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\r\n" + 
				" <xs:element ref=\"附注\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\r\n" +
				" <xs:element ref=\"文件数据\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"来源\">\r\n" + " <xs:complexType> \r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"档案馆名称\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"档案馆代码\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"全宗名称\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"立档单位名称\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"档号\">\r\n" + " <xs:complexType mixed=\"true\">\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"全宗号\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"目录号\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"年度\"/>\r\n" + " <xs:element ref=\"保管期限\"/>\r\n" + 
				" <xs:element ref=\"机构或问题\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"室编案卷号\" minOccurs=\"0\"/>\r\n"+
				" <xs:element ref=\"馆编案卷号\" minOccurs=\"0\"/>\r\n" + " <xs:choice>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"室编件号\"/>\r\n" + " <xs:element ref=\"馆编件号\" minOccurs=\"0\"/>\r\n" + 
				" </xs:sequence>\r\n" + " <xs:sequence>\r\n" + " <xs:element ref=\"馆编件号\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:choice>\r\n" +" <xs:element ref=\"页号\" minOccurs=\"0\"/>\r\n"+ " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"内容描述\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"题名\"/>\r\n" + "<xs:element ref=\"并列题名\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"副题名\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"说明题名文字\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"主题词\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\r\n" + " <xs:element ref=\"关键词\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"人名\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"摘要\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"分类号\" minOccurs=\"0\"/>\r\n"+ " <xs:element ref=\"文件编号\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"责任者\"/>\r\n" + " <xs:element ref=\"日期\"/>\r\n" +
				" <xs:element ref=\"文种\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"紧急程度\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"主送\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"抄送\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"密级\"/>\r\n" + " <xs:element ref=\"保密期限\" minOccurs=\"0\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"主题词\">\r\n" + " <xs:complexType>\r\n" + " <xs:simpleContent>\r\n" + 
				" <xs:extension base=\"xs:string\">\r\n" + " <xs:attribute name=\"主题词表名称\" type=\"xs:string\"/>\r\n" + 
				" </xs:extension>\r\n" + " </xs:simpleContent>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"形式特征\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"文件组合类型\"/>\r\n" + " <xs:element ref=\"页数\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"语种\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"稿本\" minOccurs=\"0\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>"+
				" <xs:element name=\"存储位置\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"当前位置\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"脱机载体编号\" maxOccurs=\"unbounded\"/>\r\n" + 
				" <xs:element ref=\"脱机载体存址\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\r\n" + " <xs:element ref=\"缩微号\" minOccurs=\"0\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"权限管理\">\r\n" + " <xs:complexType>\r\n" +" <xs:sequence> \r\n" + 
				" <xs:element ref=\"知识产权说明\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"授权\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\r\n" + 
				" <xs:element ref=\"控制标识\" minOccurs=\"0\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + 
				" </xs:element>\r\n" + " <xs:element name=\"授权\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"授权对象\"/>\r\n" + " <xs:element ref=\"授权行为\"/>\r\n" + " </xs:sequence>\r\n" + 
				" </xs:complexType>\r\n" + " </xs:element>\r\n" + " <xs:element name=\"文件数据\">\r\n" + " <xs:complexType>\r\n" +" <xs:sequence>\r\n" + 
				" <xs:element ref=\"文档\" maxOccurs=\"unbounded\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"文档\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"文档标识符\"/>\r\n" + " <xs:element ref=\"文档序号\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"文档主从声明\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"题名\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"文档数据\" maxOccurs=\"unbounded\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"文档数据\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>一个文档数据对应文档的一个稿本或收文处理单(文件拟稿标签)</xs:documentation>\r\n" + 
				" </xs:annotation>\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"编码\" maxOccurs=\"unbounded\"/>\r\n" + " </xs:sequence>\r\n" + 
				" <xs:attribute name=\"文档数据ID\" type=\"xs:ID\" use=\"required\" default=\"修改 0-文档 1-文档数据 1\"/>\r\n" + " </xs:complexType>\r\n" + " </xs:element> \r\n" + 
				" <xs:element name=\"编码\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>为一个计算机文件</xs:documentation>\r\n" + " </xs:annotation>\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"电子属性\"/>\r\n" + " <xs:element ref=\"数字化属性\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"编码描述\"/>\r\n" + " <xs:element ref=\"反编码关键字\"/>\r\n" + 
				" <xs:element ref=\"编码数据\"/>\r\n" + " </xs:sequence>\r\n" + 
				" <xs:attribute name=\"编码ID\" type=\"xs:ID\" use=\"required\" default=\"修改 0-文档  1-文档数据 1-编码 1\"/>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"电子属性\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"格式信息\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"计算机文件名\"/>\r\n" + 
				" <xs:element ref=\"计算机文件大小\"/>\r\n" + " <xs:element ref=\"文档创建程序\" minOccurs=\"0\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"数字化属性\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"数字化对象形态\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"扫描分辨率\"/>\r\n" + 
				" <xs:element ref=\"扫描色彩模式\"/>\r\n" + " <xs:element ref=\"图像压缩方案\" minOccurs=\"0\"/>\r\n" + 
				" <xs:documentation>在递交签名时，属性的文本顺序依次为“编码数据ID”和“引用编码数据ID”</xs:documentation>\r\n" + "</xs:annotation>\r\n" + 
				" <xs:attribute name=\"编码数据ID\" type=\"xs:ID\" use=\"required\" default=\"修改 0-文档  1-文档数据 1-编码 1\"/>\r\n" + 
				" <xs:attribute name=\"引用编码数据ID\" type=\"xs:IDREF\" default=\"修改 0-文档  1-文档数据 1-编码 1\"/>\r\n" + 
				" </xs:extension>\r\n" + " </xs:simpleContent>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"文件实体关系\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"文件标识符\"/>\r\n" + " <xs:element ref=\"被关联文件标识符\"/>\r\n" + 
				" <xs:element ref=\"关系类型\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"关系\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"关系描述\" minOccurs=\"0\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"业务实体块\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"业务实体\" maxOccurs=\"unbounded\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"业务实体\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"业务标识符\"/>\r\n" + " <xs:element ref=\"机构人员标识符\"/>\r\n" + " <xs:element ref=\"文件标识符\"/>\r\n" + 
				" <xs:element ref=\"业务状态\"/>\r\n" + " <xs:element ref=\"业务行为\"/>\r\n" + " <xs:element ref=\"行为时间\"/>\r\n" + 
				" <xs:element ref=\"行为依据\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"行为描述\" minOccurs=\"0\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"机构人员实体块\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence> \r\n" + 
				" <xs:element ref=\"机构人员实体\" maxOccurs=\"unbounded\"/>\r\n" + " <xs:element ref=\"机构人员实体关系\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"机构人员实体\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"机构人员标识符\"/>\r\n" + " <xs:element ref=\"机构人员类型\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"机构人员名称\"/>\r\n" + " <xs:element ref=\"组织机构代码\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"个人职位\" minOccurs=\"0\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"机构人员实体关系\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"机构人员标识符\"/>\r\n" + " <xs:element ref=\"被关联机构人员标识符\"/>\r\n" + 
				" <xs:element ref=\"关系类型\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"关系\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"关系描述\" minOccurs=\"0\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"电子签名块\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"电子签名\" maxOccurs=\"unbounded\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"电子签名\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"签名标识符\"/>\r\n" + " <xs:element ref=\"签名规则\"/>\r\n" + 
				" <xs:element ref=\"签名时间\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"签名人\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"签名结果\"/>\r\n" + " <xs:element ref=\"证书块\" maxOccurs=\"unbounded\"/>\r\n" + 
				" <xs:element ref=\"签名算法标识\"/> \r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"证书块\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"证书\" maxOccurs=\"unbounded\"/>\r\n" + " <xs:element ref=\"证书引证\" minOccurs=\"0\"/>\r\n" + 
				" </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"锁定签名\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>对某一“电子签名”进行的签名</xs:documentation>\r\n" + " </xs:annotation>\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"被锁定签名标识符\"/>\r\n" + " <xs:element ref=\"签名规则\"/>\r\n" + 
				" <xs:element ref=\"签名时间\" minOccurs=\"0\"/>\r\n" + " <xs:element ref=\"签名人\" minOccurs=\"0\"/>\r\n" + 
				" <xs:element ref=\"签名结果\"/>\r\n" + " <xs:element ref=\"证书块\" maxOccurs=\"unbounded\"/>\r\n" + 
				" <xs:element ref=\"签名算法标识\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"修改封装内容\">\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"修改标识符\"/>\r\n" + " <xs:element ref=\"原封装包\"/>\r\n" + 
				" <xs:element ref=\"修订内容\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"原封装包\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>包含修改前封装包中的被签名对象和电子签名块</xs:documentation>\r\n" + " </xs:annotation>\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"被签名对象\"/>\r\n" + " <xs:element ref=\"电子签名块\" minOccurs=\"0\"/> \r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"修订内容\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>包含电子文件元数据及被修改的数据</xs:documentation>\r\n" + " </xs:annotation>\r\n" + " <xs:complexType>\r\n" + " <xs:sequence>\r\n" + 
				" <xs:element ref=\"文件实体块\"/>\r\n" + " <xs:element ref=\"业务实体块\"/>\r\n" + 
				" <xs:element ref=\"机构人员实体块\"/>\r\n" + " </xs:sequence>\r\n" + " </xs:complexType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"封装包格式描述\" type=\"xs:string\" default=\"本EEP根据中华人民共和国档案行业标准DA/T48-2009《基于XML的电子文件封装规范》生成\"/>\r\n" + 
				" <xs:element name=\"版本\" type=\"xs:gYear\" fixed=\"2009\">\r\n" + " <xs:annotation>\r\n" + 
				" <xs:documentation>本元素的值和元素“被签名对象”的属性“epp版本”的值相同</xs:documentation>\r\n" + " </xs:annotation>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"封装包类型\" default=\"原始型\">\r\n" + " <xs:simpleType>\r\n" + 
				" <xs:restriction base=\"xs:string\">\r\n" + " <xs:enumeration value=\"原始型\"/>\r\n" + " <xs:enumeration value=\"修改型\"/>\r\n" +
				" </xs:restriction>\r\n" +" </xs:simpleType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"封装包类型描述\" default=\"本封装包包含电子文件数据及其元数据，原始封装，未经修改\">\r\n" + 
				" <xs:simpleType>\r\n" + " <xs:restriction base=\"xs:string\">\r\n" + 
				" <xs:enumeration value=\"本封装包包含电子文件数据及其元数据，原始封装，未经修改\"/>\r\n" +
				" <xs:enumeration value=\"本封装包包含电子文件数据及其元数据，系修改封装，在保留原封装包的基础上，添加了修改层\"/>\r\n"+
				" </xs:restriction>\r\n" + " </xs:simpleType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"封装包创建时间\" type=\"xs:dateTime\" default=\""+format.format(date)+"\"/>\r\n" + 
				" <xs:element name=\"封装包创建单位\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"聚合层次\" type=\"xs:string\" fixed=\"文件\"/> \r\n" + 
				" <xs:element name=\"档案馆名称\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"档案馆代码\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"全宗名称\" type=\"xs:string\" default=\""+quanzong+"\"/>\r\n" + 
				" <xs:element name=\"立档单位名称\" type=\"xs:string\" default=\""+DeptPath+"\"/>\r\n" + 
				" <xs:element name=\"电子文件号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"全宗号\" type=\"xs:string\" default=\""+FondID+"\"/>\r\n" + 
				" <xs:element name=\"目录号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"年度\" type=\"xs:gYear\" default=\""+Year+"\"/>\r\n" + 
				" <xs:element name=\"保管期限\" type=\"xs:string\" default=\""+PreservationPeriod+"\"/>\r\n" + 
				" <xs:element name=\"机构或问题\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"类别号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"室编案卷号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"馆编案卷号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"室编件号\" type=\"xs:positiveInteger\"/>\r\n" + 
				" <xs:element name=\"馆编件号\" type=\"xs:positiveInteger\"/>\r\n" + 
				" <xs:element name=\"页号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"题名\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"并列题名\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"副题名\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"说明题名文字\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"关键词\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"人名\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"摘要\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"分类号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"文件编号\" type=\"xs:string\" default=\""+DocumentID+"\"/>\r\n" + 
				" <xs:element name=\"责任者\" type=\"xs:string\" default=\""+Creator+"\"/>\r\n" + 
				" <xs:element name=\"日期\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"文种\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"紧急程度\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"主送\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"抄送\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"密级\" type=\"xs:string\" default=\""+SecurityLevel+"\"/>\r\n" + 
				" <xs:element name=\"保密期限\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"文件组合类型\" default=\"单件\">\r\n" + 
				" <xs:simpleType>\r\n" + " <xs:restriction base=\"xs:string\">\r\n" + 
				" <xs:enumeration value=\"单件\"/>\r\n" + "<xs:enumeration value=\"组合文件\"/>\r\n"+
				" </xs:restriction>\r\n" + " </xs:simpleType>\r\n" + " </xs:element>");
		xml.append(" <xs:element name=\"页数\" type=\"xs:positiveInteger\"/>\r\n" + 
				" <xs:element name=\"语种\" type=\"xs:string\" default=\"汉语\"/>\r\n" + 
				" <xs:element name=\"稿本\" type=\"xs:string\"/> \r\n" + 
				" <xs:element name=\"当前位置\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"脱机载体编号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"脱机载体存址\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"缩微号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"知识产权说明\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"授权对象\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"授权行为\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"控制标识\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"信息系统描述\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"附注\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"文档标识符\" type=\"xs:ID\"/>\r\n" + 
				" <xs:element name=\"文档序号\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"文档主从声明\">\r\n" + " <xs:simpleType>\r\n" + " <xs:restriction base=\"xs:string\">\r\n" + 
				" <xs:enumeration value=\"主文档\"/>\r\n" + 
				" <xs:enumeration value=\"附属文档\"/>\r\n" + " </xs:restriction>\r\n" + " </xs:simpleType>\r\n" + " </xs:element>"+
				" <xs:element name=\"格式信息\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"计算机文件名\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"计算机文件大小\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"文档创建程序\" type=\"xs:string\"/>\r\n"+
				" <xs:element name=\"数字化对象形态\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"扫描分辨率\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"扫描色彩模式\">\r\n" + " <xs:simpleType>\r\n" + " <xs:restriction base=\"xs:string\">\r\n" + 
				" <xs:enumeration value=\"黑白二值\"/>\r\n" + " <xs:enumeration value=\"灰度\"/>\r\n" + " <xs:enumeration value=\"彩色\"/>\r\n" + 
				" </xs:restriction>\r\n" + " </xs:simpleType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"图像压缩方案\" type=\"xs:string\"/>\r\n"+
				" <xs:element name=\"编码描述\" type=\"xs:string\" default=\"本封装包中“编码数据”元素存储的是计算机文件二" + 
				"进制流的Base64编码，有关Base64编码规则参见IETF RFC 2045多用途邮件扩展（MIME）第一部分：互联网信息体格式。" + 
				"当提取和显现封装在编码数据元素中的计算机文件时，应对Base64编码进行反编码，并依据封装包中“反编码关键字”" + 
				"元素中记录的值还原计算机文件的扩展名\"/>\r\n" + 
				" <xs:element name=\"反编码关键字\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"文件标识符\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"被关联文件标识符\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"关系类型\" type=\"xs:string\"/> \r\n" + 
				" <xs:element name=\"关系\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"关系描述\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"业务标识符\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"机构人员标识符\" type=\"xs:string\"/>\r\n");
		xml.append(" <xs:element name=\"业务状态\">\r\n" + " <xs:simpleType>\r\n" + " <xs:restriction base=\"xs:string\">\r\n" + 
				" <xs:enumeration value=\"历史行为\"/>\r\n" + " <xs:enumeration value=\"计划任务\"/>\r\n" + 
				" </xs:restriction>\r\n" + " </xs:simpleType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"业务行为\" type=\"xs:string\"/>\r\n" + " <xs:element name=\"行为时间\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"行为依据\" type=\"xs:string\"/>\r\n" + " <xs:element name=\"行为描述\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"机构人员类型\">\r\n" + " <xs:simpleType>\r\n" + " <xs:restriction base=\"xs:string\">\r\n" + 
				" <xs:enumeration value=\"单位\"/>\r\n" + " <xs:enumeration value=\"内设机构\"/>\r\n" + 
				" <xs:enumeration value=\"个人\"/>\r\n" + " </xs:restriction>\r\n" + " </xs:simpleType>\r\n" + " </xs:element>\r\n" + 
				" <xs:element name=\"机构人员名称\" type=\"xs:string\"/>\r\n" + " <xs:element name=\"组织机构代码\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"个人职位\" type=\"xs:string\"/>\r\n" + " <xs:element name=\"被关联机构人员标识符\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"签名标识符\" type=\"xs:ID\"/>\r\n" + " <xs:element name=\"签名规则\" type=\"xs:string\" default=\"RSA\"/>\r\n" + 
				" <xs:element name=\"签名时间\" type=\"xs:dateTime\" default=\""+format.format(date)+"\"/>\r\n" + " <xs:element name=\"签名人\" type=\"xs:string\"/>\r\n" + 
				" <xs:element name=\"签名结果\" type=\"xs:base64Binary\" default=\""+sign+"\"/>\r\n" + " <xs:element name=\"证书\" type=\"xs:base64Binary\"/>\r\n" + 
				" <xs:element name=\"证书引证\" type=\"xs:anyURI\"/>\r\n" + " <xs:element name=\"签名算法标识\" type=\"xs:string\" default=\"RSA\"/>\r\n" + 
				" <xs:element name=\"被锁定签名标识符\" type=\"xs:IDREF\"/>\r\n" + " <xs:element name=\"修改标识符\" type=\"xs:ID\"/>");
		xml.append("</xs:schema>");
		
		return xml.toString();
	}

	@Override
	public String getSign(String data, String id, String struc) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*public String getSign(String data, String id, String struc) {
		// 生成密钥对
        KeyPair keyPair;
        String sign = null;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		try {
			keyPair = RsaUtil.getKeyPair();
			String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
			String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
			
			RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			//获取N值
			int bitLength = rsaPublicKey.getModulus().bitLength();
			
			// RSA签名
            sign = RsaUtil.sign(data, RsaUtil.getPrivateKey(privateKey));
            RsaMes rsa = new RsaMes(uuid, id, struc, privateKey, publicKey, bitLength+"");
            essModelDao.saveRsaMes(rsa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sign;
	}*/
	
}
