package org.example.framework.database.core;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class DbEncryptHelper implements InitializingBean {

    @Value("${db-aes-key}")
    private String aesKey;

    private static AES Aes;

    public static String encrypt(String value) {
        try {
            return Aes.encryptHex(value);
        } catch (Exception e) {
            return value;
        }
    }

    public static String decrypt(String value) {
        try {
            return Aes.decryptStr(value, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return value;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Aes = SecureUtil.aes(aesKey.getBytes(StandardCharsets.UTF_8));
    }
}
