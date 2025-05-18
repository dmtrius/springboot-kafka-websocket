package com.ivanfranchin.bitcoinapi.crypto;

import org.apache.kafka.common.serialization.Deserializer;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class AESDeserializer implements Deserializer<String> {
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
    public String deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(data);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error during AES deserialization", e);
        }
    }

    @Override
    public void close() {}
}
