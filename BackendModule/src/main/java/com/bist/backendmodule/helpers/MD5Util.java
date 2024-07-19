package com.bist.backendmodule.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing strings using the MD5 algorithm.
 */
public class MD5Util {

    /**
     * Hashes the given input string using the MD5 algorithm.
     *
     * @param input The input string to be hashed
     * @return The hashed string in hexadecimal format
     * @throws RuntimeException if the MD5 algorithm is not available
     */
    public static String hash(String input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md5.digest(input.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : messageDigest) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
