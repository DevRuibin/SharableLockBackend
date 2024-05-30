package org.example.mqttserver;

import java.math.BigInteger;
import java.util.Base64;

public class Base64Converter {

    /**
     * Converts a Base64 string to a hexadecimal string.
     *
     * @param base64String the Base64 encoded string
     * @return the hexadecimal representation of the Base64 string
     */
    public static String base64ToHex(String base64String) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        StringBuilder hexString = new StringBuilder();

        for (byte b : decodedBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    /**
     * Converts a Base64 string to a decimal string.
     *
     * @param base64String the Base64 encoded string
     * @return the decimal representation of the Base64 string
     */
    public static String base64ToDecimal(String base64String) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        BigInteger bigInt = new BigInteger(1, decodedBytes);

        return bigInt.toString(10);
    }
}

