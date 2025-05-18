package com.ivanfranchin.bitcoinapi.crypto;

import org.apache.kafka.common.serialization.Deserializer;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

public class RSADeserializer implements Deserializer<String> {
    private static final String RSA_PRIVATE_KEY = "TUlJRXZ3SUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NCS2t3Z2dTbEFnRUFBb0lCQVFDT1QrVW93bmg3U2ljMXVjTjV2YnI3YmF3R3p1ZEhlZ2lDQjVwdmtiM09NMjhINTN2aThPU0VUVWtDeTZ5VUh1RTA2Ky9UMkk3aU9nNDM4YXp1cUxJYkl5ZTdUdk0wNDUxZjNiVFpyUnpFSGlkN0xrMFRWMUlSQ28vTXJJT2RoaXc2eHpJUStlYlhMUjFTY25TZUQ1UWpJc3I1R1JVSEJ2Z2tCK2FobXcyYmh3bmxTWFhEOGF2RjNMelNoazN4bFZGTWUxV2Y0Z0NhM1Z5dFdVS1F4NzNWUVhJZTF6ZXpmMWdIeHkvQityS2hDcDUrd1FlTjBDcjRONjBaNWNlZHV5U0doWUNENVM5YURnMGhReEJCZ1JWTzNvVnAwWGR5Um1PMk5HQURjSEwwcXR2UnJWUkRNQjRmb3pMSHpEOG1VRmd5RldtaDFJR2ZOZEdOZjNwVWFKdi9BZ01CQUFFQ2dnRUFRL1h4aUtwK0lOcURxUXVDUnlRTkpENzJsUFcveXdtREFKMmk2YjdldHBjblljM3hBcXBwWnFrRndka0VIR1EyeGpBYUFyLzdWQnZIYUgzNFNLeTFDWEtiYWI3Rk5DK3ltdkpraUVNOFpvZkQ4UFptaG1MLzVQVjNRSlh0OEk3L2xqNUVNWWsxN1BJMWU0Yi9HL0J5bk9qanAxdU1DQ1FTNGhvSDBJc2NUM1hTQkp2NVFzN2RYYnp2YVM1Rnh0ZGlJQ0V5cnhxSm1pOUVrcmpMRGFkOVpGQXl0eG94czlFOStVQUhwbFhXMmcwcURhYXdnc21RRnhTa3FaRi9OMHB3VVNtY3Z0QVlMRnVDS0NpT3dzSldyYkFzRW95NE0ySEJJeDNoWVE4RVgwL050R2U1R2ZjVVpBY2lqVjRXM1BIUFY0OUJIVTFad3dSL0M2VmNjY0IzYVFLQmdRQzFyd2U5VVJic0tPOE1oelhEbUE3N0ZKcUpyUWRDTkpLMU1ZZGtEblowaFc0WnBad0tXVjJsbzRYeFZBRXdEaTZGUjJPUGQzZko5U3IzeExPT0V5T3IyQlcyVDRmZHBiSjZFN0RaUlVlMkI1V0JtQVRwcjBxMk91ODBqaUlSZ2c5OXZyL1RaNk5tdkZnMmVtQnBFczV1Vmx3NWJGNWVDR0xPdU5vSXNzdHBJd0tCZ1FESWhoTUQ5eVRLS080QnBnV1pMODZXbk9MMUo4N2FWZVJ6Ylp2UnozMFFVVHBIVjhLMVNsWkxSQ0lXbFZLN2tBRkNEUU5ERmszQktMUDE4VWVtTDVRNkx2OWdoUFhjeWRUL2NlWEJOQVJiSVpIaHNqUGJjWU9CWnZXNWQybk52R3RIWmVwYnN5WU82T1ZTMHJxTUxqZUVUZWczVC93RC93UGdSUy81ZUhxbGRRS0JnUUNOLzgwV3hJZ2I4Q3BXS2w5MURpVzNTL1UwNGMyb2l4cVhENno0SU8yb1NKa3k1bU05RzNlNy9FQzF5NmhaL051VFE2YW0vUUxkRUFlVE8yUHpDVFo2R3h0dE1GL0tkclUyQUtuYVJ2R3piZDRHT2VDdEhGcFNiMkdDUEF5WWpvUzVEUnMwaWU0R1gvSzZNWStrMEJRVDNOK2I2ZjBmbFlKVWU5bTlyckNZN1FLQmdRQ3RSVjF0RWNQU2hDZVZlUlpoVmpRWFpSMmdaWE9TcXFHck5FMXVzUElLaFlYc1crTTlzQ3g5QVRUTGV1QndmbkQzMkRLM1o3QnNkblY2MGJEdDR6TWNTN3dVZFEvV1p2a01laWprRjhTajBwQXlERUJQSVBsWGVkWmZHdFpzRnFKRW9jWXUwdERTRldFRGZsLy8vWGV2ZFhweFFVWXRWZERObkR0bVFEc0tlUUtCZ1FDYW5EOE5xOGd4MVdMVUViMFVsUS9xbW5tMTlnRVc5VU5hb1owZmdkQTA3cTlmaVE1NXVCUE1RaXljYUlETWZBdFN4U2lCejRjUm9KRVg4UjE4YnV3Q1NiVWxiR0tPSTROSnlSQlk0NjZkTmt3MXl0MUJnem43WXJlYVNkQUVSTzJLZ202OXpmRThPVTdJK1IrVmp0MjdQdEFtN0RqV1JaUlVpN3l0YnNsYmtnPT0=";
    private PrivateKey privateKey;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(RSA_PRIVATE_KEY);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure RSA private key", e);
        }
    }

    @Override
    public String deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(data);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error during RSA deserialization", e);
        }
    }

    @Override
    public void close() {}
}
