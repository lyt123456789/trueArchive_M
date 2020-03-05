package cn.com.trueway.document.component.taglib.attachment.junit;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;


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
public class TestAttachmentDao {
	
	private static AttachmentDao attachmentDao;
	private long t = 0;
	
	@Before
	public void init(){
		t = System.currentTimeMillis();
		System.out.println("测试开始 ..."+ t);
		//加载 spring 配置文件
		System.out.println("开始加载配置文件...");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:spring/common/dataAccessContext.xml","classpath:spring/component/componentContext_dao.xml"});
		attachmentDao = (AttachmentDao)ctx.getBean("attachmentDao");
		System.out.println("加载配置文件成功");
	}
	
	@After
	public void destroy(){
		System.out.println("测试结束 ..."+System.currentTimeMillis());
		t = System.currentTimeMillis() - t ;
		System.out.println("耗时：" + t + "ms");
	}

	@Test
	public void find(){
		SendAttachments atts= attachmentDao.findSendAtts("d-E2A4-45E8-9B6B-A0C321CC7F6D");
		System.out.println(atts);
	}
	
	@Test
	public void add(){
		SendAttachments atts = new SendAttachments();
		atts.setDocguid("111");
		atts.setFiletype("doc");
		atts.setFilename("111");
		atts.setFileindex(100L);
		atts.setFiletime(new Date());
		atts.setLocalation("222");
		atts.setFilesize(1000l);
		attachmentDao.addSendAtts(atts);
	}
	
	public void del(){
		attachmentDao.deleteSendAtts("111");
	}
	
}
