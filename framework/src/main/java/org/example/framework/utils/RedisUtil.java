package org.example.framework.utils;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashSet;
import java.util.Set;

public class RedisUtil {
    public static StringRedisTemplate StringRedisTemplate;

    public static void init(StringRedisTemplate template) {
        StringRedisTemplate = template;
    }

    public static Set<String> findKeysByPattern(String pattern) {
        Set<String> keys = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        Cursor<String> cursor = StringRedisTemplate.scan(options);

        while (cursor.hasNext()) {
            keys.add(cursor.next());
        }

        cursor.close();
        return keys;
    }
}
