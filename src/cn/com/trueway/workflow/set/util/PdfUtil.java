package cn.com.trueway.workflow.set.util;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.itextpdf.text.DocumentException;
/**
 * ��Ϣ���͹�����
 * @author jiang.li
 * @date 2013-12-17 13:17
 */
public class PdfUtil {
    
    /**
     * ���Ͷ���
     * @param params
     * @throws JSONException 
     */
    public void sendSMS(Map<?, ?> params) throws JSONException{
    	JSONArray imgs = new JSONArray(params.get("imgs").toString());
        String output = params.get("output").toString();
        String pdfs = params.get("pdfs").toString();
    	System.out.println("============start============"+output);
		System.out.println(System.currentTimeMillis());
		pdftools _pdftools=new pdftools(); 
		try {
			_pdftools.initDrawPDF(pdfs,output);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//首先要初始化
		try {
			_pdftools.DrawImage(imgs);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		//生成pdf
		try {
			_pdftools.Output();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("===========end============="+output);
		System.out.println(System.currentTimeMillis());
		/**/
    }
}