package  com.puduhe.util.security;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;


public class BASE64Encrypt {
    /**
     * BASE64加密
     *
     * @param s
     * @param charset
     *
     * @return
     */
    public static String encode(String s, String charset) {
        try {
            return encode(s.getBytes(charset), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * BASE64加密
     *
     * @param b
     *
     * @return
     */
    public static String encode(byte[] b, String charset) {
        if (b == null) {
            return null;
        }
        try {
            return new String(Base64.encodeBase64(b), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * BASE64解密
     *
     * @param s
     * @param charset
     *
     * @return
     */
    public static String decode(String s, String charset) {
        try {
            return decode(s.getBytes(charset), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * BASE64解密
     *
     * @param b
     * @param charset
     *
     * @return
     */
    public static String decode(byte[] b, String charset) {
        try {
            return new String(Base64.decodeBase64(b), charset);
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}
}
