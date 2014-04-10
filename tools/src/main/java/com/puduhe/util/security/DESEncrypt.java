package  com.puduhe.util.security;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.DecoderException;

public class DESEncrypt {
    // left shift rotation for KeyPermutedChoice
    private static final byte[] LeftShiftRotation = {1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1};

    // a permuted choice array for convert key from 64 bits to 56 bits
    private static final byte[] KeyPermutedChoice = {56, 48, 40, 32, 24, 16,
            8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3};

    // a permuted choice array for get 48bits sub key
    private static final byte[] SubKeyPermutedChoice = {13, 16, 10, 23, 0, 4,
            2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1,
            40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33,
            52, 45, 41, 49, 35, 28, 31};

    // initial permutation
    private static final byte[] InitialPermutation = {57, 49, 41, 33, 25, 17,
            9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7, 56, 48, 40, 32, 24, 16, 8, 0, 58,
            50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54,
            46, 38, 30, 22, 14, 6};

    // expansion table E
    private static final byte[] ExpansionE = {31, 0, 1, 2, 3, 4, 3, 4, 5, 6,
            7, 8, 7, 8, 9, 10, 11, 12, 11, 12, 13, 14, 15, 16, 15, 16, 17, 18,
            19, 20, 19, 20, 21, 22, 23, 24, 23, 24, 25, 26, 27, 28, 27, 28, 29,
            30, 31, 0};

    // substitution box [8][4][16]
    private static final byte[][][] SubstitutionBox = {
            // substitution box 0
            {{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},

            // substitution box 1
            {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},

            // substitution box 2
            {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},

            // substitution box 3
            {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},

            // substitution box 4
            {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},

            // substitution box 5
            {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},

            // substitution box 6
            {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},

            // substitution box 7
            {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}};

    // 32-bit permutation function P used on the output of the s-boxes
    private static final byte[] PermutationP = {15, 6, 19, 20, 28, 11, 27, 16,
            0, 14, 22, 25, 4, 17, 30, 9, 1, 7, 23, 13, 31, 26, 2, 8, 18, 12,
            29, 5, 21, 10, 3, 24};

    // final permutation
    private static final byte[] FinalPermutation = {39, 7, 47, 15, 55, 23, 63,
            31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34,
            2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25, 32, 0, 40,
            8, 48, 16, 56, 24};

    /**
     * permuted choice key with KeyPermutedChoice matrix
     *
     * @param key64Bits : key bit array with length 64
     *
     * @return a bit array with length 56 for key
     */
    private static byte[] permutedChoiceKey(byte[] key64Bits) {
        byte[] bits = new byte[56];
        for (int i = 0; i < 56; i++) {
            int choiceBitIndex = KeyPermutedChoice[i];
            bits[i] = key64Bits[choiceBitIndex];
        }
        return bits;
    }

    /**
     * split 56 bits key
     *
     * @param key56Bits : the 56 bits key
     *
     * @return a 2 dimensions array, rows is 2 and columns is 28
     */
    private static byte[][] splitKey(byte[] key56Bits) {
        byte[][] keyPartBits = new byte[2][28];

        for (int i = 0; i < 28; i++)
            keyPartBits[0][i] = key56Bits[i];

        for (int i = 28; i < 56; i++)
            keyPartBits[1][i - 28] = key56Bits[i];

        return keyPartBits;
    }

    /**
     * get 16 sub keys in bit
     *
     * @param keyLeft28  : left 28 bits of key
     * @param keyRight28 : right 28 bits of key
     *
     * @return a 2 dimensions sub key array, rows is 16 and columns is 48
     */
    private static byte[][] getSubKey(byte[] keyLeft28, byte[] keyRight28) {
        byte[][] subKeyBits = new byte[16][48];

        for (int i = 0; i < 16; i++) {
            int shiftCount = LeftShiftRotation[i];
            int copyCount = 28 - shiftCount;

            // left key rotation
            byte[] keyLeft28Shift = new byte[28];
            DESUtility.byteCopy(keyLeft28Shift, 0, keyLeft28, shiftCount,
                    copyCount);
            DESUtility.byteCopy(keyLeft28Shift, copyCount, keyLeft28, 0,
                    shiftCount);

            // right key rotation
            byte[] keyRight28Shift = new byte[28];
            DESUtility.byteCopy(keyRight28Shift, 0, keyRight28, shiftCount,
                    copyCount);
            DESUtility.byteCopy(keyRight28Shift, copyCount, keyRight28, 0,
                    shiftCount);

            // combine left key and right key
            byte[] key56Shift = new byte[56];
            DESUtility.byteCopy(key56Shift, 0, keyLeft28Shift, 0, 28);
            DESUtility.byteCopy(key56Shift, 28, keyRight28Shift, 0, 28);

            // make sub key
            for (int j = 0; j < 48; j++) {
                int choiceBitIndex = SubKeyPermutedChoice[j];
                subKeyBits[i][j] = key56Shift[choiceBitIndex];
            }

            // evaluate new values for next step
            DESUtility.byteCopy(keyLeft28, 0, keyLeft28Shift, 0, 28);
            DESUtility.byteCopy(keyRight28, 0, keyRight28Shift, 0, 28);

            // release...
            keyLeft28Shift = null;
            keyRight28Shift = null;
            key56Shift = null;
        }

        return subKeyBits;
    }

    /**
     * initial permutation for plain text
     *
     * @param plainText64Bits : 64 bits of plain text
     *
     * @return an array with length 64 that transformed by initial permutation
     */
    private static byte[] initialPermutation(byte[] plainText64Bits) {
        byte[] permutation = new byte[64];

        for (int i = 0; i < 64; i++) {
            int choiceBitIndex = InitialPermutation[i];
            permutation[i] = plainText64Bits[choiceBitIndex];
        }

        return permutation;
    }

    /**
     * split initial permutation (64 bits)
     *
     * @param initialPermutation64Bits : the 56 bits of plain text
     *
     * @return a 2 dimensions array, rows is 2 and columns is 32
     */
    private static byte[][] splitInitialPermutation(
            byte[] initialPermutation64Bits) {
        byte[][] initialPermutationParts = new byte[2][32];

        for (int i = 0; i < 32; i++)
            initialPermutationParts[0][i] = initialPermutation64Bits[i];

        for (int i = 32; i < 64; i++)
            initialPermutationParts[1][i - 32] = initialPermutation64Bits[i];

        return initialPermutationParts;
    }

    /**
     * expansion 32 bits to 48 bits for right of plain text
     *
     * @param plainTextRight32Bits : right of plain text (32 bits)
     *
     * @return expansion array with length 48
     */
    private static byte[] ExpansionPlainTextRight32Bits(
            byte[] plainTextRight32Bits) {
        byte[] expansion = new byte[48];
        for (int i = 0; i < 48; i++) {
            int choiceBitIndex = ExpansionE[i];
            expansion[i] = plainTextRight32Bits[choiceBitIndex];
        }
        return expansion;
    }

    /**
     * xor operation
     *
     * @param array1 : byte array 1
     * @param array2 : byte array 2
     *
     * @return xor value
     */
    private static byte[] xor(byte[] array1, byte[] array2) {
        if (array1.length == array2.length) {
            byte[] xorBits = new byte[array1.length];
            for (int i = 0; i < array1.length; i++) {
                xorBits[i] = (byte) (array1[i] ^ array2[i]);
            }
            return xorBits;
        }
        return null;
    }

    /**
     * get compress bits with length 32
     *
     * @param xor48Bits : xor result (48 bits)
     *
     * @return a compress bit array with length 32
     */
    private static byte[] getCompressBits(byte[] xor48Bits) {
        byte[] compressBits = new byte[32];

        for (int i = 0; i < 8; i++) {
            byte[] position = new byte[6];
            DESUtility.byteCopy(position, 0, xor48Bits, i * 6, 6);

            int rowIndex = position[0] * 2 + position[5];
            int columnIndex = position[1] * 8 + position[2] * 4 + position[3]
                    * 2 + position[4] * 1;
            byte boxValue = SubstitutionBox[i][rowIndex][columnIndex];

            byte[] fourBits = DESUtility.byteTo4Bits(boxValue);
            DESUtility.byteCopy(compressBits, 4 * i, fourBits, 0, 4);

            // release...
            position = null;
            fourBits = null;
        }

        return compressBits;
    }

    /**
     * permutation with PermutationP table
     *
     * @param compress32Bits : compress bits (length 32)
     *
     * @return permutation bits with length 32
     */
    private static byte[] permutationP(byte[] compress32Bits) {
        byte[] permutation = new byte[32];

        for (int i = 0; i < 32; i++) {
            int choiceBitIndex = PermutationP[i];
            permutation[i] = compress32Bits[choiceBitIndex];
        }

        return permutation;
    }

    /**
     * main permutation function
     *
     * @param subKeyIndex          : sub key index based on 0
     * @param subKeyArray          : sub key array, 16 rows, 48 columns
     * @param plainTextLeft32Bits  : left 32 bits of plain text
     * @param plainTextRight32Bits : right 32 bits of plain text
     */
    private static void mainPermutation(int subKeyIndex, byte[][] subKeyArray,
                                        byte[] plainTextLeft32Bits, byte[] plainTextRight32Bits) {
        byte[] plainTextRightExpansion48Bits = ExpansionPlainTextRight32Bits(plainTextRight32Bits);
        byte[] xor48Bits = xor(subKeyArray[subKeyIndex],
                plainTextRightExpansion48Bits);
        byte[] compress32Bits = getCompressBits(xor48Bits);
        byte[] permutation32Bits = permutationP(compress32Bits);
        byte[] xor32Bits = xor(permutation32Bits, plainTextLeft32Bits);

        DESUtility
                .byteCopy(plainTextLeft32Bits, 0, plainTextRight32Bits, 0, 32);
        DESUtility.byteCopy(plainTextRight32Bits, 0, xor32Bits, 0, 32);

        // release...
        plainTextRightExpansion48Bits = null;
        xor48Bits = null;
        compress32Bits = null;
        permutation32Bits = null;
        xor32Bits = null;
    }

    /**
     * final permutation
     *
     * @param final64Bits : final bits (length 64)
     *
     * @return ciphertext bits with length 64
     */
    private static byte[] finalPermutation(byte[] final64Bits) {
        byte[] ciphertextBits = new byte[64];

        for (int i = 0; i < 64; i++) {
            int choiceBitIndex = FinalPermutation[i];
            ciphertextBits[i] = final64Bits[choiceBitIndex];
        }

        return ciphertextBits;
    }

    /**
     * is key valid ?
     *
     * @param hexKey : hex key
     *
     * @return true if valid, otherwise false
     */
    public static boolean isValidKey(String hexKey) {
        try {
            // get key length
            byte[] plainTextBits = DESUtility.hexText2PlainTextBits(hexKey);
            int length = plainTextBits.length;

            // release...
            plainTextBits = null;
            return length == 8;
        } catch (DecoderException e) {
            return false;
        }
    }

    /**
     * get byte array of key from a string in hex
     *
     * @param hexKey : a key string in hex
     *
     * @return byte array of key if successful, otherwise throws a DES exception
     */
    private static byte[] getKeyBytes(String hexKey) {
        try {
            return DESUtility.hexText2PlainTextBits(hexKey);
        } catch (DecoderException e) {
            return new byte[0];
        }
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
     * encrypt a block (64 bits)
     *
     * @param keyLeft28Bits  : 28 bit array of key left
     * @param keyRight28Bits : 28 bit array of key right
     * @param plainText      : plain text (the length of text is less or equal to 8)
     * @param charset
     *
     * @return a hex string with length 16
     *
     * @throws UnsupportedEncodingException
     */
    private static String encode64Bits(byte[] keyLeft28Bits,
                                       byte[] keyRight28Bits, String plainText, String charset)
            throws UnsupportedEncodingException {
        // get 16 sub keys (16 rows, 48 columns)
        byte[][] subKeyArray = getSubKey(keyLeft28Bits, keyRight28Bits);

        // get plain text 64 bits
        byte[] plainText64 = new byte[64];
        byte[] plainTextBits = DESUtility.bytes2Bits(plainText
                .getBytes(charset));
        DESUtility.initializeByteArray(plainText64, (byte) 0x00);
        DESUtility.byteCopy(plainText64, 0, plainTextBits, 0,
                plainTextBits.length);

        // initial permutation 64 bits
        byte[] initialPermutation = initialPermutation(plainText64);

        // split plain text 64 bits (2 rows, 32 columns)
        byte[][] plainTextParts = splitInitialPermutation(initialPermutation);

        // main permutation
        for (int i = 0; i < 16; i++)
            mainPermutation(i, subKeyArray, plainTextParts[0],
                    plainTextParts[1]);

        // get final 64 bits
        byte[] final64 = new byte[64];
        DESUtility.byteCopy(final64, 0, plainTextParts[0], 0, 32);
        DESUtility.byteCopy(final64, 32, plainTextParts[1], 0, 32);

        // final permutation
        byte[] ciphertext64 = finalPermutation(final64);
        String hexString = DESUtility.bits2HexString(ciphertext64);
        String upperCaseHexString = hexString.toUpperCase();

        // release...
        DESUtility.releaseBuffer(subKeyArray);
        plainText64 = null;
        plainTextBits = null;
        initialPermutation = null;
        DESUtility.releaseBuffer(plainTextParts);
        final64 = null;
        ciphertext64 = null;
        hexString = null;

        return upperCaseHexString;
    }

    /**
     * 对字符串进行DES加密
     *
     * @param hexKey
     * @param plaintext
     * @param charset
     *
     * @return
     *
     * @throws UnsupportedEncodingException
     */
    public static String encode(String hexKey, String plaintext, String charset)
            throws UnsupportedEncodingException {
        String hexString = null;

        // get key bytes
        byte[] keyBytes = getKeyBytes(hexKey);

        // encrypt plaintext
        if (keyBytes.length == 8 && plaintext != null) {
            StringBuilder hexStringBuilder = new StringBuilder();

            // get key parts (2 rows, 28 columns)
            byte[] key64Bits = DESUtility.bytes2Bits(keyBytes);
            byte[] key56 = permutedChoiceKey(key64Bits);
            byte[][] keyParts = splitKey(key56);

            // encrypt...
            while (plaintext != null) {
                // get plaintext length
                int length = plaintext.length();

                // encrypt plaintext block
                int blockLength = (length > 8) ? 8 : length;
                String encryptBlock = plaintext.substring(0, blockLength);
                String hexEncryptBlock = encode64Bits(keyParts[0], keyParts[1],
                        encryptBlock, charset);
                hexStringBuilder.append(hexEncryptBlock);

                // release...
                encryptBlock = null;
                hexEncryptBlock = null;

                // for encrypt next plaintext block
                String temporary = (length > 8) ? plaintext.substring(8) : null;
                plaintext = null;
                plaintext = temporary;
            }
            hexString = hexStringBuilder.toString();

            // release...
            hexStringBuilder = null;
            key64Bits = null;
            key56 = null;
            DESUtility.releaseBuffer(keyParts);
        }

        // release...
        keyBytes = null;
        return hexString;
    }

    /**
     * decrypt a ciphertext block (64 bits)
     *
     * @param keyLeft28Bits  : 28 bit array of key left
     * @param keyRight28Bits : 28 bit array of key right
     * @param ciphertext     : ciphertext (16 hex characters, 64 bits)
     * @param charset
     *
     * @return plain text
     *
     * @throws UnsupportedEncodingException
     */
    private static String decode64Bits(byte[] keyLeft28Bits,
                                       byte[] keyRight28Bits, String ciphertext, String charset)
            throws UnsupportedEncodingException {
        // get 16 sub keys (16 rows, 48 columns)
        byte[][] subKeyArray = getSubKey(keyLeft28Bits, keyRight28Bits);

        // get ciphertext 64 bits
        char[] hexChars = ciphertext.toCharArray();
        byte[] ciphertext64 = DESUtility.bytes2Bits(decodeHex(hexChars));

        // initial permutation 64 bits
        byte[] initialPermutation = initialPermutation(ciphertext64);

        // split ciphertext 64 bits (2 rows, 32 columns)
        byte[][] ciphertextParts = splitInitialPermutation(initialPermutation);

        // main permutation
        for (int i = 0; i < 16; i++)
            mainPermutation(15 - i, subKeyArray, ciphertextParts[1],
                    ciphertextParts[0]);

        // get final 64 bits
        byte[] final64 = new byte[64];
        DESUtility.byteCopy(final64, 0, ciphertextParts[0], 0, 32);
        DESUtility.byteCopy(final64, 32, ciphertextParts[1], 0, 32);

        // final permutation
        byte[] plaintext64 = finalPermutation(final64);
        String hexString = DESUtility.bits2HexString(plaintext64);
        hexString = DESUtility.trimMark(hexString);

        // plain text bits
        byte[] plaintextBits = decodeHex(hexString.toCharArray());
        String plainText = new String(plaintextBits, charset);

        // release...
        DESUtility.releaseBuffer(subKeyArray);
        hexChars = null;
        ciphertext64 = null;
        initialPermutation = null;
        DESUtility.releaseBuffer(ciphertextParts);
        final64 = null;
        plaintext64 = null;
        plaintextBits = null;
        hexString = null;

        return plainText;
    }

    /**
     * 对字符串进行DES解密
     *
     * @param hexKey
     * @param ciphertext
     * @param charset
     *
     * @return
     *
     * @throws UnsupportedEncodingException
     */
    public static String decode(String hexKey, String ciphertext, String charset)
            throws UnsupportedEncodingException {
        String plaintext = null;

        // get key bytes
        byte[] keyBytes = getKeyBytes(hexKey);

        // decrypt ciphertext
        if (keyBytes.length == 8 && ciphertext != null
                && ciphertext.length() % 16 == 0) {
            StringBuilder plaintextBuilder = new StringBuilder();

            // get key parts (2 rows, 28 columns)
            byte[] key64Bits = DESUtility.bytes2Bits(keyBytes);
            byte[] key56 = permutedChoiceKey(key64Bits);
            byte[][] keyParts = splitKey(key56);

            // encrypt...
            while (ciphertext != null) {
                // get ciphertext length
                int length = ciphertext.length();

                // encrypt plaintext block
                int blockLength = (length > 16) ? 16 : length;
                String decryptBlock = ciphertext.substring(0, blockLength);
                String decryptText = decode64Bits(keyParts[0], keyParts[1],
                        decryptBlock, charset);
                plaintextBuilder.append(decryptText);

                // release...
                decryptBlock = null;
                decryptText = null;

                // for decrypt next ciphertext block
				String temporary = (length > 16) ? ciphertext.substring(16)
						: null;
				ciphertext = null;
				ciphertext = temporary;
			}
			plaintext = plaintextBuilder.toString();

			// release...
			plaintextBuilder = null;
			key64Bits = null;
			key56 = null;
			DESUtility.releaseBuffer(keyParts);
		}

		// release...
		keyBytes = null;
		return plaintext;
	}
}