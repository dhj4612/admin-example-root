package org.example.admin;

import cn.hutool.core.map.MapBuilder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.framework.security.core.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AdminApplicationTest {

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void testToken() {
        String accessToken = jwtTokenUtil.createAccessToken(MapBuilder.<String, String>create()
                .put("id", "11111")
                .put("type", "c").build());

        log.info("access-token:{}", accessToken);
    }

}
