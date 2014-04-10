package  com.puduhe.util.security;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

public class DESUtility {
    // bit pattern for byte, from the 8th bit to the 1st bit
    private static final int[] ByteBitPattern =
            {
                    0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01
            };

    /**
     * convert a byte to 8 byte array, and everyone is save a bit
     *
     * @param value : a byte value
     *
     * @return a byte array with length 8
     */
    public static byte[] byte2Bits(byte value) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            int result = value & ByteBitPattern[i];
            bytes[i] = (byte) ((result != 0x00) ? 0x01 : 0x00);
        }
        return bytes;
    }

    /**
     * convert byte array to bit array
     *
     * @param byteArray : byte array
     *
     * @return bit array
     */
    public static byte[] bytes2Bits(byte[] byteArray) {
        byte[] stringBits = new byte[byteArray.length * 8];

        for (int i = 0; i < byteArray.length; i++) {
            // get string bits
            byte[] byteBits = DESUtility.byte2Bits(byteArray[i]);
            for (int j = 0; j < 8; j++)
                stringBits[i * 8 + j] = byteBits[j];

            // release...
            byteBits = null;
        }

        return stringBits;
    }

    /**
     * copy byte from source array to destination array
     *
     * @param destination : destination array
     * @param toStartAt   : start index (based on 0) to copy to
     * @param source      : source array
     * @param fromStartAt : start index (based on 0) to from to
     * @param copyCount   : copy count
     */
    public static void byteCopy(byte[] destination, int toStartAt,
                                byte[] source, int fromStartAt, int copyCount) {
        for (int i = 0; i < copyCount; i++)
            destination[toStartAt + i] = source[fromStartAt + i];
    }

    /**
     * initialize byte array
     *
     * @param array        : an array to empty
     * @param defaultValue : default value
     */
    public static void initializeByteArray(byte[] array, byte defaultValue) {
        for (int i = 0; i < array.length; i++)
            array[i] = defaultValue;
    }

    /**
     * convert hex text to bit array of plain text
     *
     * @param hexText : hex text
     *
     * @return a byte array of the plain text if successful, otherwise null
     */
    public static byte[] hexText2PlainTextBits(String hexText) throws DecoderException {
        char[] hexChars = hexText.toCharArray();
        byte[] plaintextBits = Hex.decodeHex(hexChars);

        // release...
        hexChars = null;
        return plaintextBits;
    }

    /**
     * convert a byte value (0 <= value <= 15) to 4 bits
     *
     * @param byteValue : byte value
     *
     * @return bit array with length 4
     */
    public static byte[] byteTo4Bits(byte byteValue) {
        if (0 <= byteValue && byteValue <= 15) {
            switch (byteValue) {
                case 0:
                    return new byte[]{0, 0, 0, 0};
                case 1:
                    return new byte[]{0, 0, 0, 1};
                case 2:
                    return new byte[]{0, 0, 1, 0};
                case 3:
                    return new byte[]{0, 0, 1, 1};
                case 4:
                    return new byte[]{0, 1, 0, 0};
                case 5:
                    return new byte[]{0, 1, 0, 1};
                case 6:
                    return new byte[]{0, 1, 1, 0};
                case 7:
                    return new byte[]{0, 1, 1, 1};
                case 8:
                    return new byte[]{1, 0, 0, 0};
                case 9:
                    return new byte[]{1, 0, 0, 1};
                case 10:
                    return new byte[]{1, 0, 1, 0};
                case 11:
                    return new byte[]{1, 0, 1, 1};
                case 12:
                    return new byte[]{1, 1, 0, 0};
                case 13:
                    return new byte[]{1, 1, 0, 1};
                case 14:
                    return new byte[]{1, 1, 1, 0};
                case 15:
                    return new byte[]{1, 1, 1, 1};
            }
        }
        return null;
    }

    /**
     * bits to hex string
     *
     * @param bits : bit array
     *
     * @return a hex string
     */
    public static String bits2HexString(byte[] bits) {
        if (bits.length % 8 == 0) {
            // combine a binary string
            StringBuilder binaryString = new StringBuilder();
            for (int i = 0; i < bits.length; i++)
                binaryString.append(bits[i]);

            // get hex string by big integer
            BigInteger bigInteger = new BigInteger(binaryString.toString(), 2);
            String hexString = bigInteger.toString(16);

            // fix hex string for prefix 0
            for (int i = 0; i < bits.length; i++) {
                if (bits[i] != 0) {
                    break;
                } else if (i % 4 == 3) {
                    String temporary = "0" + hexString;
                    hexString = null;
                    hexString = temporary;
                }
            }

            // release...
            binaryString = null;
            bigInteger = null;

            return hexString;
        }
        return null;
    }

    /**
     * trim mark from tail of hexString
     *
     * @param hexString : hex string
     *
     * @return a new string without mark
     */
    public static String trimMark(String hexString) {
        while (hexString.endsWith("00")) {
            // get sub string
            int length = hexString.length();
            String subString = hexString.substring(0, length - 2);

            // release...
            hexString = null;

            // for next step
            hexString = subString;
        }
        return hexString;
    }

    /**
     * release buffer
     *
     * @param array : an array with tow dimensions
     */
	public static void releaseBuffer(byte[][] array)
	{
		// release byte array in rows
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}
}