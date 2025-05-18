package com.ivanfranchin.bitcoinapi.crypto;

import org.apache.kafka.common.serialization.Serializer;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

public class RSASerializer implements Serializer<String> {
    private static final String RSA_PUBLIC_KEY = "TUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUFqay9sS01KNGUwb25OYm5EZWIyNisyMnNCczduUjNvSWdnZWFiNUc5empOdkIrZDc0dkRraEUxSkFzdXNsQjdoTk92djA5aU80am9PTi9HczdxaXlHeU1udTA3ek5PT2RYOTIwMmEwY3hCNG5leTVORTFkU0VRcVB6S3lEbllZc09zY3lFUG5tMXkwZFVuSjBuZytVSXlMSytSa1ZCd2I0SkFmbW9ac05tNGNKNVVsMXcvR3J4ZHk4MG9aTjhaVlJUSHRWbitJQW10MWNyVmxDa01lOTFVRnlIdGMzczM5WUI4Y3Z3ZnF5b1FxZWZzRUhqZEFxK0RldEdlWEhuYnNraG9XQWcrVXZXZzROSVVNUVFZRVZUdDZGYWRGM2NrWmp0alJnQTNCeTlLcmIwYTFVUXpBZUg2TXl4OHcvSmxCWU1oVnBvZFNCbnpYUmpYOTZWR2liL3dJREFRQUI=";
    private PublicKey publicKey;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(RSA_PUBLIC_KEY);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure RSA public key", e);
        }
    }

    @Override
    public byte[] serialize(String topic, String data) {
        if (data == null) {
            return new byte[0];
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encode(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error during RSA serialization", e);
        }
    }

    @Override
    public void close() {}
}
