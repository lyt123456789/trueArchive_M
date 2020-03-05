package cn.com.trueway.base.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.com.trueway.sys.util.SystemParamConfigUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加密
 * 
 * @author trueway
 * 
 */
public class AESPlus {

	/**
	 * AES加密
	 * 
	 * @param content
	 *            需要加密内容
	 * @return
	 */
	public static String encrypt(String content) throws Exception {

		SecretKeySpec secretKeySpec = getKey(SystemParamConfigUtil.getParamValueByParam("aeskye"));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return new BASE64Encoder().encode(encrypted);
	}
	
	
	/**
	 * AES加密
	 * 
	 * @param content
	 *            需要加密内容
	 * @return
	 */
	public static byte[] encrypt(byte[] content) throws Exception {

		SecretKeySpec secretKeySpec = getKey(SystemParamConfigUtil.getParamValueByParam("aeskye"));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
		return cipher.doFinal(content);
	}

	private static SecretKeySpec getKey(String strKey) throws Exception {

		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		SecretKeySpec secretKeySpec = new SecretKeySpec(arrB, "AES");
		return secretKeySpec;
	}

	/**
	 * AES解密
	 * 
	 * @param content
	 *            需要解密内容
	 * @return
	 */
	public static String decrypt(String content) throws Exception {
		SecretKeySpec secretKeySpec = getKey(SystemParamConfigUtil.getParamValueByParam("aeskye"));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
		byte[] encrypted = new BASE64Decoder().decodeBuffer(content);
		byte[] original = cipher.doFinal(encrypted);
		return new String(original);
	}
	
	/**
	 * AES解密
	 * 
	 * @param content
	 *            需要解密内容
	 * @return
	 */
	public static byte[] decrypt(byte[] content) throws Exception {
		SecretKeySpec secretKeySpec = getKey(SystemParamConfigUtil.getParamValueByParam("aeskye"));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(SystemParamConfigUtil.getParamValueByParam("aesiv").getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
		byte[] original = cipher.doFinal(content);
		return original;
	}
	/**
	 * AES解密
	 * 
	 * @param content
	 *            需要解密内容
	 * @return
	 */
	public static byte[] decryptByByte(String content) throws Exception {
		SecretKeySpec secretKeySpec = getKey(SystemParamConfigUtil
				.getParamValueByParam("aeskye"));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(SystemParamConfigUtil
				.getParamValueByParam("aesiv").getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
		byte[] encrypted = new BASE64Decoder().decodeBuffer(content);
		byte[] original = cipher.doFinal(encrypted);
		return original;
	}
}
