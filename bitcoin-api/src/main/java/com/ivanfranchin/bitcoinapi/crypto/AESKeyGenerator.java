package com.ivanfranchin.bitcoinapi.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESKeyGenerator {
    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Generated AES Key: " + generateKey());
        String key = "Y1IxcmZ4TktVUUx0anNnYjJ6M2pFSzk0VTMwTnpwN2NTeDJkNzk0OExpQT0=";
    }
}
