package com.df.core.common.utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;

public class ByteUtils {

    private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
	    (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
	    (byte) 'e', (byte) 'f' };
    private static Charset asciiCharset = Charset.forName("ASCII");

    /**
     * Convert from an array of floats to an array of bytes
     * 
     * @param floatArray
     */
    public static byte[] FloatArraytoByteArray(float floatArray[]) {
	byte byteArray[] = new byte[floatArray.length * 4];
	ByteBuffer byteBuf = ByteBuffer.wrap(byteArray);
	FloatBuffer floatBuf = byteBuf.asFloatBuffer();
	floatBuf.put(floatArray);
	return byteArray;
    }

    /**
     * Convert from an array of bytes to an array of floats
     * 
     * @param byteArray
     */
    public static float[] ByteArraytoFloatArray(byte byteArray[]) {
	float floatArray[] = new float[byteArray.length / 4];
	ByteBuffer byteBuf = ByteBuffer.wrap(byteArray);
	FloatBuffer floatBuf = byteBuf.asFloatBuffer();
	floatBuf.get(floatArray);
	return floatArray;
    }

    /**
     * Convert from a byte array to one int
     * 
     * @param byteArray
     */
    public static final int ByteArrayToInt(byte[] byteArray) {
	return ByteArrayToInt(byteArray, 0);
    }

    /**
     * Convert from a byte array at an offset to one int
     * 
     * @param byteArray
     * @param offset
     *            the offset in the byteArray that is the first byte of the int
     * 
     *            TODO: Test that this will work for leading-zero bytes
     */
    public static final int ByteArrayToInt(byte[] byteArray, int offset) {
	return byteArray[0 + offset] << 24 | (byteArray[1 + offset] & 0xff) << 16 | (byteArray[2 + offset] & 0xff) << 8
		| (byteArray[3 + offset] & 0xff);
    }

    /**
     * Convert from one int to a byte array
     * 
     * @param i
     *            the integer
     */
    public static final byte[] IntToByteArray(int i) {
	return new byte[] { (byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i };
    }

    public static String bytesToHexString(byte[] bytes) {
	byte[] hex = new byte[2 * bytes.length];
	int index = 0;
	for (byte b : bytes) {
	    int v = b & 0xFF;
	    hex[index++] = HEX_CHAR_TABLE[v >>> 4];
	    hex[index++] = HEX_CHAR_TABLE[v & 0xF];
	}
	return new String(hex, asciiCharset);
    }

    public static byte[] hexStringToBytes(String str) {
	if (str.length() % 2 != 0) {
	    throw new IllegalArgumentException("hexBinary needs to be even-length:" + str);
	}

	byte[] out = new byte[str.length() / 2];
	for (int i = 0; i < str.length(); i += 2) {
	    int h = hexToBin(str.charAt(i));
	    int l = hexToBin(str.charAt(i + 1));
	    if (h == -1 || l == -1) {
		throw new IllegalArgumentException("contains illegal character for hexBinary: " + str);
	    }
	    out[i / 2] = (byte) (h * 16 + l);
	}
	return out;
    }

    public static int hexToBin(char ch) {
	if ('0' <= ch && ch <= '9') {
	    return ch - '0';
	}
	if ('A' <= ch && ch <= 'F') {
	    return ch - 'A' + 10;
	}
	if ('a' <= ch && ch <= 'f') {
	    return ch - 'a' + 10;
	}
	return -1;
    }
}
