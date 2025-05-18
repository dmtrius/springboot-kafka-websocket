package com.ivanfranchin.bitcoinapi.crypto;

import org.apache.kafka.common.serialization.Serializer;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class AESSerializer implements Serializer<String> {
    private static final String AES_KEY = "cR1rfxNKUQLtjsgb2z3jEK94U30Nzp7cSx2d7948LiA="; // Replace with your key
    private SecretKeySpec secretKey;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(AES_KEY);
            secretKey = new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure AES key", e);
        }
    }

    @Override
    public byte[] serialize(String topic, String data) {
        if (data == null) {
            return new byte[0];
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encode(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error during AES serialization", e);
        }
    }

    @Override
    public void close() {}
}
