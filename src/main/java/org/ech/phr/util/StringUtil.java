package org.ech.phr.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.ech.phr.model.exception.BusinessException;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;


public class StringUtil {

	public static String ENCODING_UTF_8 = "UTF-8";
	private static final HashFunction HASH_FUNCTION = Hashing.sha256();

	public static byte[] getBytes(String text) throws BusinessException {
		byte[] result = null;
		if (text != null) {
			result = text.getBytes(UTF_8);
		}
		return result;
	}

	public static String getHash(String text)  throws BusinessException {
		return HASH_FUNCTION.hashString(text, UTF_8).toString();
	}
}