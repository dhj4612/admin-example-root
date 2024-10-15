package org.example.framework.utils;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.framework.common.exception.BizException;

import java.util.Base64;

@Slf4j
public class AesUtil {

    private static String Key;

    public static void init(String aesKey) {
        Key = aesKey;
        log.info("AesUtil init complete......");
    }

    public static String encrypt(String dataStr) {
        return encrypt(Key, dataStr);
    }

    public static String decrypt(String encryptDataStr) {
        return decrypt(Key, encryptDataStr);
    }

    /**
     * 生成 AES 算法的密钥
     */
    public static String generateAesKey() {
        return Base64.getEncoder().encodeToString(SecureUtil.generateKey("AES").getEncoded());
    }

    /**
     * Aes 加密字符串数据
     */
    public static String encrypt(String key, String dataStr) {
        if (StringUtils.isBlank(key)) {
           throw new BizException();
        }
        byte[] encryptBytes = SecureUtil.aes(key.getBytes()).encrypt(dataStr.getBytes());
        return Base64.getEncoder().encodeToString(encryptBytes);
    }

    /**
     * Aes 解密字符串数据
     */
    public static String decrypt(String key, String encryptDataStr) {
        if (StringUtils.isBlank(key)) {
            throw new BizException();
        }
        byte[] decryptBytes = SecureUtil.aes(key.getBytes()).decrypt(Base64.getDecoder().decode(encryptDataStr));
        return new String(decryptBytes);
    }
}
