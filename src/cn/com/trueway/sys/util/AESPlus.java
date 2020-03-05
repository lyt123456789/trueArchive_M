package cn.com.trueway.sys.util;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/*
 * AES对称加密和解密
 */
	public class AESPlus
	{
	  public static String encrypt(String content)
	    throws Exception
	  {
	    SecretKeySpec secretKeySpec = getKey(
	      SystemParamConfigUtil.getParamValueByParam("aeskye"));
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec(
	      SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
	    cipher.init(1, secretKeySpec, iv);
	    byte[] encrypted = cipher.doFinal(content.getBytes());
	    return new BASE64Encoder().encode(encrypted);
	  }

	  public static String encrypt(String content, String aeskye, String aesiv)
	    throws Exception
	  {
	    SecretKeySpec secretKeySpec = getKey(aeskye);
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec(aesiv.getBytes());
	    cipher.init(1, secretKeySpec, iv);
	    byte[] encrypted = cipher.doFinal(content.getBytes());
	    return new BASE64Encoder().encode(encrypted);
	  }

	  public static byte[] encrypt(byte[] content)
	    throws Exception
	  {
	    SecretKeySpec secretKeySpec = getKey(
	      SystemParamConfigUtil.getParamValueByParam("aeskye"));
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec(
	      SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
	    cipher.init(1, secretKeySpec, iv);
	    return cipher.doFinal(content);
	  }

	  private static SecretKeySpec getKey(String strKey) throws Exception
	  {
	    byte[] arrBTmp = strKey.getBytes();
	    byte[] arrB = new byte[16];
	    for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); i++) {
	      arrB[i] = arrBTmp[i];
	    }
	    SecretKeySpec secretKeySpec = new SecretKeySpec(arrB, "AES");
	    return secretKeySpec;
	  }

	  public static String decrypt(String content)
	    throws Exception
	  {
	    SecretKeySpec secretKeySpec = getKey(
	      SystemParamConfigUtil.getParamValueByParam("aeskye"));
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec(
	      SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
	    cipher.init(2, secretKeySpec, iv);
	    byte[] encrypted = new BASE64Decoder().decodeBuffer(content);
	    byte[] original = cipher.doFinal(encrypted);
	    return new String(original);
	  }

	  public static byte[] decrypt(byte[] content)
	    throws Exception
	  {
	    SecretKeySpec secretKeySpec = getKey(
	      SystemParamConfigUtil.getParamValueByParam("aeskye"));
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec(
	      SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
	    cipher.init(2, secretKeySpec, iv);
	    byte[] original = cipher.doFinal(content);
	    return original;
	  }

	  public static byte[] decryptByByte(String content)
	    throws Exception
	  {
	    SecretKeySpec secretKeySpec = getKey(
	      SystemParamConfigUtil.getParamValueByParam("aeskye"));
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    IvParameterSpec iv = new IvParameterSpec(
	      SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
	    cipher.init(2, secretKeySpec, iv);
	    byte[] encrypted = new BASE64Decoder().decodeBuffer(content);
	    byte[] original = cipher.doFinal(encrypted);
	    return original;
	  }

}