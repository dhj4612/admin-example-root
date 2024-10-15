package org.example.framework.database.core;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public final class DbEncryptHelper {

    private static AES Aes;

    public static void init(String key) {
        Aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
        log.info("DbEncryptHelper init complete...");
    }

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
}
