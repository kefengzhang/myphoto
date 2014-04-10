package  com.puduhe.util.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.DecoderException;

public class RSAEncrypt {
    /**
     * convert a hex value to a decimal value
     *
     * @param hexValue : a hex value to convert
     *
     * @return a decimal value
     */
    private static BigInteger hex2Integer(String hexValue) {
        return new BigInteger(hexValue, 16);
    }

    /**
     * decode hex char array
     *
     * @param hexChars : hex char array
     *
     * @return a byte array if successful, otherwise throws a DES exception
     */
    private static byte[] decodeHex(char[] hexChars) {
        try {
            return Hex.decodeHex(hexChars);
        } catch (DecoderException e) {
            return new byte[0];
        }
    }

    /**
     * 对字符串进行RSA加密
     *
     * @param plaintext
     * @param modulus
     * @param publicExponent
     * @param charset
     *
     * @return
     *
     * @throws UnsupportedEncodingException
     */
    public static String encode(String plaintext, String modulus,
                                String publicExponent, String charset)
            throws UnsupportedEncodingException {
        // convert plain text to big integer
        byte[] plaintextBytes = plaintext.getBytes(charset);
        char[] hexChars = Hex.encodeHex(plaintextBytes);
        String hexString = new String(hexChars);
        BigInteger m = hex2Integer(hexString);

        // convert some parameters to big integer
        BigInteger n = hex2Integer(modulus);
        BigInteger e = hex2Integer(publicExponent);

        // get cipher text in decimal
        BigInteger c = m.modPow(e, n);
        String lowerCaseCiphertext = c.toString(16);
        String upperCaseCiphertext = lowerCaseCiphertext.toUpperCase();

        // release...
        plaintextBytes = null;
        hexChars = null;
        hexString = null;
        m = null;
        n = null;
        e = null;
        c = null;
        lowerCaseCiphertext = null;

        return upperCaseCiphertext;
    }

    /**
     * 对字符串进行RSA解密
     *
     * @param ciphertext
     * @param modulus
     * @param privateExponent
     * @param charset
     *
     * @return
     *
     * @throws UnsupportedEncodingException
     */
    public static String decode(String ciphertext, String modulus,
                                String privateExponent, String charset)
            throws UnsupportedEncodingException {
        // get parameters
        BigInteger c = hex2Integer(ciphertext);
        BigInteger n = hex2Integer(modulus);
        BigInteger e = hex2Integer(privateExponent);

        // decrypt ciphertext
        BigInteger m = c.modPow(e, n);

        // get plain string
        String hexPlainString = m.toString(16);
        char[] hexPlainDataArray = hexPlainString.toCharArray();
        byte[] decPlainDataArray = decodeHex(hexPlainDataArray);
        String plainString = new String(decPlainDataArray, charset);

        // release...
        c = null;
        n = null;
        e = null;
        m = null;
        hexPlainString = null;
		hexPlainDataArray = null;
		decPlainDataArray = null;

		return plainString;
	}
}