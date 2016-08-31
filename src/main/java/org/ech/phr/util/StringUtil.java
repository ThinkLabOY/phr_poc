package org.ech.phr.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.ech.phr.model.exception.BusinessException;

public class StringUtil {
	
	final static Logger log = Logger.getLogger(StringUtil.class);

	public static String ENCODING_UTF_8 = "UTF-8";

	public static byte[] getBytes(String text) throws BusinessException {
		byte[] result = null;
		if (text != null) {
			try {
				result = text.getBytes(ENCODING_UTF_8);
			} 
			catch (UnsupportedEncodingException e) {
				BusinessException.throwBusinessException(BusinessException.EX_UTL_001);
			}
		}
		return result;
	}

	public static String getHash(String text)  throws BusinessException {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} 
		catch (NoSuchAlgorithmException e) {
			BusinessException.throwBusinessException(BusinessException.EX_UTL_002);
		}
		byte[] textBytes = getBytes(text);
		messageDigest.update(textBytes);
		String encryptedHashString = new String(messageDigest.digest());
		return encryptedHashString;
	}
}