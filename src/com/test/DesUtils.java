package com.test;

import java.security.Key;

import javax.crypto.Cipher;

public class DesUtils {
	
	public static void main(String[] args) throws Exception {
//		System.out.println("key : \r\n" + AfMD5.getMD5(""));
		String key = AfMD5.getMD5("");//"d41d8cd98f00b204e9800998ecf8427e";
		String value = "authors.txt";
		System.out.println("pack:\r\n"+new DesUtils(key).encrypt(value));
		System.out.println("decr:\r\n"+new DesUtils(key).decrypt(new DesUtils(key).encrypt(value)));
	}
	
	private static String strDefaultKey = "national";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// ÿ��byte�������ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ�����
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// �Ѹ���ת��Ϊ����
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// С��0F������Ҫ��ǰ�油0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		// �����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public DesUtils() throws Exception {
		this(strDefaultKey);
	}

	public DesUtils(String strKey) {
		// Security.addProvider(null);
		Key key;
		try {
			key = getKey(strKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes("UTF-8")));
	}

	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)),"UTF-8");
	}

	/**
	 * �� strIn ���� ʧ�ܷ��� ����
	 * @param strIn
	 * @return ����ֵ
	 */
	public String decryptSafe(String strIn){
		try {
			return new String(decrypt(hexStr2ByteArr(strIn)));
		} catch (Throwable e) {
			// TODO: handle exception
			return "";
		}
	}
	private Key getKey(byte[] arrBTmp) throws Exception {
		// ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
		byte[] arrB = new byte[8];
		// ��ԭʼ�ֽ�����ת��Ϊ8λ
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// ������Կ
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}
	
}