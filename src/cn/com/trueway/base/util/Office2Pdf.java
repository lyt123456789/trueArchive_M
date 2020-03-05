package cn.com.trueway.base.util;
import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;
/**
 *
 * @author caiyj
 *
 */
public class Office2Pdf {
	public void createPDF(String officePath, String pdfPath) throws Exception {
		ReleaseManager rm = null;
		IDispatch app = null;
		try {
			rm = new ReleaseManager();
			app = new IDispatch(rm, "PDFMakerAPI.PDFMakerApp");
			app.method("CreatePDF", new Object[] { officePath, pdfPath });
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				app = null;
				rm.release();
				rm = null;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Office2Pdf one = new Office2Pdf();
		// one.createPDF("d:\\codigg.ppt","E:\\codigg-ppt.pdf");
		one.createPDF("d:\\表单设置.docx", "E:\\codigg-doc.pdf");
		one.createPDF("d:\\人力资源管理规定考试成绩-2014.2.xls", "d:\\codigg-xls.pdf");
	}

}
