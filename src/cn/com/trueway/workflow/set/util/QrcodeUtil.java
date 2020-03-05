package cn.com.trueway.workflow.set.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.swetake.util.Qrcode;

/**
 * 文件名称： cn.com.trueway.workflow.set.util.QrcodeUtil.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2018-11-14<br/>
 * 功能说明： 二维码工具类 <br/>  
 * 修改记录：<br/> 
 * 修改作者        日期       修改内容<br/> 
 */
public class QrcodeUtil {
	private QrcodeUtil() {
		
	}

	public static void encoderQRCoder(String content, HttpServletResponse response) {  
        try {  
            Qrcode handler = new Qrcode();  
            handler.setQrcodeErrorCorrect('M');  
            handler.setQrcodeEncodeMode('B');  
            handler.setQrcodeVersion(7);  
              
            System.out.println(content);  
            byte[] contentBytes = content.getBytes("UTF-8");  
              
            BufferedImage bufImg = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);  
              
            Graphics2D gs = bufImg.createGraphics();  
              
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, 140, 140);  
              
            //设定图像颜色：BLACK  
            gs.setColor(Color.BLACK);  
              
            //设置偏移量  不设置肯能导致解析出错  
            int pixoff = 2;  
            //输出内容：二维码  
            if(contentBytes.length > 0 && contentBytes.length < 124) {  
                boolean[][] codeOut = handler.calQrcode(contentBytes);  
                for(int i = 0; i < codeOut.length; i++) {  
                    for(int j = 0; j < codeOut.length; j++) {  
                        if(codeOut[j][i]) {  
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff,3, 3);  
                        }  
                    }  
                }  
            } else {  
                System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");  
            }  
              
            gs.dispose();  
            bufImg.flush();  
              
              
              
            //生成二维码QRCode图片  
            ImageIO.write(bufImg, "png", response.getOutputStream());  
              
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
	
	/*public static void main(String[] args)
    {
		String content = "{/"appNme/": /"政务协同/","
                +"/"cmd/":/"login/","
                +"/"uuid/":/"F3C0925412A545E0A017B57D71C4E0Eg/"}";
	

        System.out.println("input your filename ends with EnterKey");
        String filepath="D://test.png";
//        QrcodeUtil.drawQRCODE(content, filepath);
        System.out.println("draw QR_code successfullly!");
        System.out.println(content);

    }*/
    public static void drawQRCODE(String content,HttpServletResponse response){
        try {
            Qrcode qrcode=new Qrcode();

            qrcode.setQrcodeErrorCorrect('M');
            qrcode.setQrcodeEncodeMode('B');
            qrcode.setQrcodeVersion(8);
            int width= 100;
            int height=100;
            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g2=image.createGraphics();
            g2.setBackground(Color.WHITE);
            g2.clearRect(0,0,100,100);
            g2.setColor(Color.BLACK);

            byte[] contentbytes=content.getBytes("utf-8");
            boolean[][] codeout= qrcode.calQrcode(contentbytes);
            for (int i = 0; i <codeout.length; i++) {
                for (int j = 0; j < codeout.length; j++) {

                    if (codeout[j][i]) g2.fillRect(j*2+2,i*2+2,2,2);
                }
            }
            g2.dispose();
            image.flush();
            //File imgFile = new File(filepath);
            ImageIO.write(image, "png", response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param imgPath
	 *            图片路径
	 * @param imgType
	 *            图片类型
	 * @param size
	 *            二维码尺寸
	 */
	public static boolean encoderQRCode(String content, String imgPath, String imgType, int size) {

		try {
			BufferedImage bufImg = qRCodeCommon(content, imgType, size);
			if(bufImg==null){
				return false;
			}
			File imgFile = new File(imgPath);
			if(!"".equals(imgPath)&&!imgFile.exists()){
				imgFile.createNewFile();
			}
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, imgType, imgFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
    
    /**
	 * 生成二维码(QRCode)图片的公共方法
	 * 
	 * @param content
	 *            存储内容
	 * @param imgType
	 *            图片类型
	 * @param size
	 *            二维码尺寸
	 * @return
	 */
	private static BufferedImage qRCodeCommon(String content, String imgType, int size) {

		BufferedImage bufImg = null;
		try {
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcodeHandler.setQrcodeVersion(size);
			// 获得内容的字节数组，设置编码格式
			byte[] contentBytes = content.getBytes("utf-8");
			// 图片尺寸
			int imgSize = 67 + (12 * (size - 1));
			bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			// 设置背景颜色
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);

			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量，不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容> 二维码
			if ((contentBytes.length > 0) && (contentBytes.length < 800)) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect((j * 3) + pixoff, (i * 3) + pixoff, 3, 3);
						}
					}
				}
			} else {
				//throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
				return null;
			}
			gs.dispose();
			bufImg.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImg;
	}
	
//	public static void main(String[] args) {
//		boolean b = QrcodeUtil.encoderQRCode("http://192.168.5.133:8086/trueWorkFlow/table_getOfficeInfoByQrcode.do?instanceId=DBACDA9D-A97F-4C2E-A354-64905D567B31", "D:/workflow/workflow_njxxzx/1.jpg", "jpg", 16);
//		System.out.println(b);
//	}
}
