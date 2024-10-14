package org.example.framework.utils;

import cn.hutool.crypto.SecureUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.framework.common.exception.BizException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@RequiredArgsConstructor
public class AesUtil {

    private final String aesKey;

    /**
     * 生成 AES 算法的密钥
     */
    public String generateAesKey() {
        return Base64.getEncoder().encodeToString(SecureUtil.generateKey("AES").getEncoded());
    }

    public String encrypt(String dataStr) {
        return encrypt(aesKey, dataStr);
    }

    public String decrypt(String encryptDataStr) {
        return decrypt(aesKey, encryptDataStr);
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
