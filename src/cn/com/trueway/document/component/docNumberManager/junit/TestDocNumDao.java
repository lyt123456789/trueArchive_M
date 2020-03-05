package cn.com.trueway.document.component.docNumberManager.junit;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.document.component.docNumberManager.dao.DocNumberManagerDao;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumber;


/**
 * 描述：JdbcTemplateExample 测试<br>
 * 作者：吴新星<br>
 * 创建时间：2011-8-7 下午06:22:30<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class TestDocNumDao {
	
	private static DocNumberManagerDao attachmentDao;
	private long t = 0;
	
	@Before
	public void init(){
		t = System.currentTimeMillis();
		System.out.println("测试开始 ..."+ t);
		//加载 spring 配置文件
		System.out.println("开始加载配置文件...");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/common/dataAccessContext.xml","classpath:spring/component/componentContext_dao.xml"});
		attachmentDao = (DocNumberManagerDao)ctx.getBean("docNumberManagerDao");
		System.out.println("加载配置文件成功");
	}
	
	@After
	public void destroy(){
		System.out.println("测试结束 ..."+System.currentTimeMillis());
		t = System.currentTimeMillis() - t ;
		System.out.println("耗时：" + t + "ms");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void find(){
		long t1 = System.currentTimeMillis();
		List<DocNumber> list = (List<DocNumber>)attachmentDao.bwDocNumUsed("2011", "请字", null,null,null,1, Integer.MAX_VALUE).getDataList();
		System.out.println("耗时000：" + (System.currentTimeMillis() - t1) + "ms");
		for(DocNumber bw : list){
			System.out.println(bw.getNumber()+","+bw.getIsused());
		}
	}
	@Test
	public void get(){
		String docNum = "[2011]来字01001号";
		DocNumber dn = new DocNumber();
		//取字号
		String regChina = "[\u4e00-\u9fa5]+";
        Pattern p = Pattern.compile(regChina);
        Matcher m = p.matcher(docNum);
        if (m.find()) {
        	dn.setType(m.group());
        }
        //取年号
        p = Pattern.compile("\\[\\d{4}\\]");
        m = p.matcher(docNum);
        if (m.find()) {
        	 p = Pattern.compile("\\d{4}");
             m = p.matcher(m.group());
             if(m.find()){
            	 dn.setYear(m.group());
             }
        }
        //取序号
        p = Pattern.compile("\\d+");
        m = p.matcher(docNum);
        while (m.find()) {
        	String str = m.group();
        	if(str.length()==4){
        		dn.setNumber(str);
        	}
        	if(str.length()==5){
        		dn.setLwdwlx(str.substring(0,2));
        		dn.setNumber(str.substring(2,5));
        	}
        }
        System.out.println(dn.toString());
	}
	@Test
	public void tttt(){
		String s = "";
		System.out.println(s.intern());
	}
}
