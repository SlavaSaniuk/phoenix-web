package com.phoenix.services.utility;

/**
 * Utility class define static methods to convert different
 * values from one to other type.
 */
public class ConversionUtility {

    /**
     * Convert {@link Byte[]} array to hexadecimal format {@link String}.
     * @param bytes - Byte array to convert.
     * @return - Hexadecimal string.
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
