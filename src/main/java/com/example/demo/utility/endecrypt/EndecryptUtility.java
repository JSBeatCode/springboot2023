package com.example.demo.utility.endecrypt;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EndecryptUtility {
    
    private static String defaultKey = "defaultKey";
    
    public static String encrypt(byte[] key, String rawData) throws Exception {
        //암호화 하려면 encrypt(getKey(), 암호화할 String 데이터)
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        
        byte[] iv = new byte[12];
        (new SecureRandom()).nextBytes(iv);
        
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec ivSpec = new GCMParameterSpec(16 * Byte.SIZE, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        
        byte[] cipherText = cipher.doFinal(rawData.getBytes("UTF8"));
        byte[] encrypted = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(cipherText, 0, encrypted, iv.length, cipherText.length);
        
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public static byte[] decrypt(byte[] key, String encryptedData) throws Exception {
        // 풀러면 StringUtils.newStringUtf8(decrypt(getKey(), String 암호화된 데이터)) 로 받아야 함
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        
        byte[] iv = Arrays.copyOfRange(decoded, 0, 12);
        
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec ivSpec = new GCMParameterSpec(16 * Byte.SIZE, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        
        return cipher.doFinal(decoded, 12, decoded.length - 12);
    }
    
    public static byte[] getKey() {
        byte[] key = null;
        MessageDigest sha = null;
        
        try {
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(Base64.getDecoder().decode(defaultKey));
            key = Arrays.copyOf(key, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }
}