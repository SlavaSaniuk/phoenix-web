package com.phoenix.services.utility;

public class ConversionUtility {

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        String output = sb.toString();
        System.out.println(output);
        return output;
    }
}
