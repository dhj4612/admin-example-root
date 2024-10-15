package org.example.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.framework.security.core.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AdminApplicationTest {

    @Resource
    private JwtUtil jwtTokenUtil;

    @Test
    void testToken() {

    }

}
