package com.unicorn.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public final class RSAUtils {

    public static RSAPublicKey getPublicKey(final String base64PublicKey) throws Exception {

        byte[] bytes = Base64.decodeBase64(base64PublicKey);

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);

    }

    public static RSAPrivateKey getPrivateKey(final String base64PrivateKey) throws Exception {

        byte[] bytes = Base64.decodeBase64(base64PrivateKey);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public static String encryptByPublicKey(final String plaintext, final String base64PublicKey) throws Exception {

        RSAPublicKey publicKey = getPublicKey(base64PublicKey);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] plaintextBytes = plaintext.getBytes("UTF-8");
        final int allowByteLength = publicKey.getModulus().bitLength() / 8 - 11;

        if (plaintextBytes.length <= allowByteLength) {
            byte[] bytes = cipher.doFinal(plaintextBytes);
            return Base64.encodeBase64String(bytes);
        }

        int mod = plaintextBytes.length % allowByteLength;
        int count = mod == 0 ? plaintextBytes.length / allowByteLength : plaintextBytes.length / allowByteLength + 1;

        List<byte[]> bytesList = new ArrayList<byte[]>();
        int totalLength = 0;
        int i = 0;
        while (i < count) {
            int start = i * allowByteLength;
            int end = Math.min(plaintextBytes.length, (i + 1) * allowByteLength);
            byte[] bytes = cipher.doFinal(Arrays.copyOfRange(plaintextBytes, start, end));
            totalLength += bytes.length;
            bytesList.add(bytes);
            i++;
        }

        byte[] totalBytes = new byte[totalLength];
        i = 0;
        for (byte[] bytes : bytesList) {
            for (byte b : bytes) {
                totalBytes[i++] = b;
            }
        }

        return Base64.encodeBase64String(totalBytes);

    }

    public static String decryptByPrivateKey(final String ciphertext, final String base64PrivateKey) throws Exception {

        RSAPrivateKey privateKey = getPrivateKey(base64PrivateKey);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] ciphertextBytes = Base64.decodeBase64(ciphertext);

        int byteLength = privateKey.getModulus().bitLength() / 8;
        int mod = ciphertextBytes.length % byteLength;
        int count = mod == 0 ? ciphertextBytes.length / byteLength : ciphertextBytes.length / byteLength + 1;

        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < count) {
            int start = i * byteLength;
            int end = Math.min(ciphertextBytes.length, (i + 1) * byteLength);
            byte[] bytes = cipher.doFinal(Arrays.copyOfRange(ciphertextBytes, start, end));
            sb.append(new String(bytes, "UTF-8"));
            i++;
        }

        return sb.toString().replace("\u0000", "");

    }

    public static String encryptByPrivateKey(final String plaintext, final String base64PrivateKey) throws Exception {

        RSAPrivateKey privateKey = getPrivateKey(base64PrivateKey);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);


        byte[] plaintextBytes = plaintext.getBytes("UTF-8");
        final int allowByteLength = privateKey.getModulus().bitLength() / 8 - 11;

        if (plaintextBytes.length <= allowByteLength) {
            byte[] bytes = cipher.doFinal(plaintextBytes);
            return Base64.encodeBase64String(bytes);
        }

        int mod = plaintextBytes.length % allowByteLength;
        int count = mod == 0 ? plaintextBytes.length / allowByteLength : plaintextBytes.length / allowByteLength + 1;

        List<byte[]> bytesList = new ArrayList<byte[]>();
        int totalLength = 0;
        int i = 0;
        while (i < count) {
            int start = i * allowByteLength;
            int end = Math.min(plaintextBytes.length, (i + 1) * allowByteLength);
            byte[] bytes = cipher.doFinal(Arrays.copyOfRange(plaintextBytes, start, end));
            totalLength += bytes.length;
            bytesList.add(bytes);
            i++;
        }

        byte[] totalBytes = new byte[totalLength];
        i = 0;
        for (byte[] bytes : bytesList) {
            for (byte b : bytes) {
                totalBytes[i++] = b;
            }
        }

        return Base64.encodeBase64String(totalBytes);
    }

    public static String decryptByPublicKey(final String ciphertext, final String base64PublicKey) throws Exception {

        RSAPublicKey publicKey = getPublicKey(base64PublicKey);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] ciphertextBytes = Base64.decodeBase64(ciphertext);

        int byteLength = publicKey.getModulus().bitLength() / 8;
        int mod = ciphertextBytes.length % byteLength;
        int count = mod == 0 ? ciphertextBytes.length / byteLength : ciphertextBytes.length / byteLength + 1;

        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < count) {
            int start = i * byteLength;
            int end = Math.min(ciphertextBytes.length, (i + 1) * byteLength);
            byte[] bytes = cipher.doFinal(Arrays.copyOfRange(ciphertextBytes, start, end));
            sb.append(new String(bytes, "UTF-8"));
            i++;
        }

        return sb.toString().replace("\u0000", "");

    }

    public static String sign(final String text, final String base64PrivateKey) throws Exception {

        RSAPrivateKey privateKey = getPrivateKey(base64PrivateKey);

        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(text.getBytes("UTF-8"));

        return Base64.encodeBase64String(signature.sign());
    }

    public static boolean verify(final String text, final String base64PublicKey, final String sign) throws Exception {

        RSAPublicKey publicKey = getPublicKey(base64PublicKey);

        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey);
        signature.update(text.getBytes("UTF-8"));

        return signature.verify(Base64.decodeBase64(sign));
    }

    public static RSAPublicKey generatePublicKey(final String base64Modulus, final String base64Exponent) throws Exception {

        BigInteger modulus = new BigInteger(Base64.decodeBase64(base64Modulus.getBytes()));
        BigInteger exponent = new BigInteger(Base64.decodeBase64(base64Exponent.getBytes()));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);

        return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

    }

    public static RSAPrivateKey generatePrivateKey(final String base64Modulus, final String base64Exponent) throws Exception {

        BigInteger modulus = new BigInteger(Base64.decodeBase64(base64Modulus.getBytes()));
        BigInteger exponent = new BigInteger(Base64.decodeBase64(base64Exponent.getBytes()));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(modulus, exponent);

        return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
    }

    public static Map<String, String> generateRSAKeyPair(final int keySize) throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize, new SecureRandom());

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, String> keyMap = new HashMap<String, String>();
        keyMap.put("RSAPublicKey", Base64.encodeBase64String(publicKey.getEncoded()));
        keyMap.put("RSAPrivateKey", Base64.encodeBase64String(privateKey.getEncoded()));

        return keyMap;

    }

    public static String getRSAPublicKeyModulusBase64(RSAPublicKey publicKey) {

        byte[] bytes = publicKey.getModulus().toByteArray();

        // 如果数组首元素是为0，则将其删除，保证模的位数是128
        if (bytes[0] == 0 && bytes.length == 129) {
            byte[] tempBytes = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, tempBytes, 0, bytes.length - 1);
            return Base64.encodeBase64String(tempBytes);
        } else {
            return Base64.encodeBase64String(bytes);
        }
    }

    public static String getRSAPublicKeyExponentBase64(RSAPublicKey publicKey) {

        BigInteger exponent = publicKey.getPublicExponent();
        return Base64.encodeBase64String(exponent.toByteArray());
    }

    public static String getRSAPrivateKeyModulusBase64(RSAPrivateKey privateKey) {

        byte[] bytes = privateKey.getModulus().toByteArray();

        // 如果数组首元素是为0，则将其删除，保证模的位数是128
        if (bytes[0] == 0 && bytes.length == 129) {
            byte[] tempBytes = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, tempBytes, 0, bytes.length - 1);
            return Base64.encodeBase64String(tempBytes);
        } else {
            return Base64.encodeBase64String(bytes);
        }
    }

    public static String getRSAPrivateKeyExponentBase64(RSAPrivateKey privateKey) {

        BigInteger exponent = privateKey.getPrivateExponent();
        return Base64.encodeBase64String(exponent.toByteArray());
    }


    public static void main(String[] args) {

    }
}
