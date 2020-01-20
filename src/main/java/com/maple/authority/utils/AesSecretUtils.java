package com.maple.authority.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.UUID;


public class AesSecretUtils {
    /**
     * 秘钥的大小
     */
    private static final int KEYSIZE = 128;

    /**
     * @param data - 待加密内容
     * @param key  - 加密秘钥
     */
    public static byte[] encrypt(String data, String key) {
        if (StringUtils.isNotBlank(data)) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                // 选择一种固定算法，为了避免不同java实现的不同算法，生成不同的密钥，而导致解密失败
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(key.getBytes());
                keyGenerator.init(KEYSIZE, random);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
                // 创建密码器
                Cipher cipher = Cipher.getInstance("AES");
                byte[] byteContent = data.getBytes(StandardCharsets.UTF_8);
                // 初始化
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                return cipher.doFinal(byteContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * AES加密
     *
     * @param data - 待加密内容
     * @param key  - 加密秘钥
     * @return 加密后的字符串
     */
    public static String encryptToStr(String data, String key) {

        return StringUtils.isNotBlank(data) ? parseByte2HexStr(encrypt(data, key)) : null;
    }


    /**
     * AES解密
     *
     * @param data - 待解密字节数组
     * @param key  - 秘钥
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] data, String key) {
        if (ArrayUtils.isNotEmpty(data)) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                //选择一种固定算法，为了避免不同java实现的不同算法，生成不同的密钥，而导致解密失败
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(key.getBytes());
                keyGenerator.init(KEYSIZE, random);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
                // 创建密码器
                Cipher cipher = Cipher.getInstance("AES");
                // 初始化
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                // 加密
                return cipher.doFinal(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param enCryptdata - 待解密字节数组
     * @param key         - 秘钥
     * @return 解密后的字符串
     */
    public static String decryptToStr(String enCryptdata, String key) {
        return StringUtils.isNotBlank(enCryptdata) ? new String(decrypt(parseHexStr2Byte(enCryptdata), key)) : null;
    }

    /**
     * 二进制转换成16进制
     *
     * @param buf - 二进制数组
     * @return 16进制字符串
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr - 16进制字符串
     * @return 转换后的字节数组
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        String key = "awlsmxcs";
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        String aa = encryptToStr(uuid, key);
        System.out.println(aa);
        String bb = decryptToStr(aa, key);
        System.out.println(bb);
    }
}