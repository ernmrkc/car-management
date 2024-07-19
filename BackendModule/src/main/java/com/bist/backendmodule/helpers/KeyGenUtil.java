package com.bist.backendmodule.helpers;

import com.bist.backendmodule.exceptions.AlgorithmNotFoundException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for generating and providing a secret key for JWT signing.
 */
public class KeyGenUtil {
    private static final SecretKey SECRET_KEY;
    private static final String ALGORITHM = "HmacSHA256";

    // Key generating bench
    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(256);
            SECRET_KEY = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException exception) {
            throw new AlgorithmNotFoundException("Failed to generate key using " + ALGORITHM, exception, KeyGenUtil.class);
        }
    }

    /**
     * Returns the generated secret key.
     *
     * @return The secret key
     */
    public static SecretKey getKey() {
        return SECRET_KEY;
    }
}
