package org.example.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.admin.model.entity.SysUser;
import org.example.admin.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class AdminApplicationTest {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysUserService sysUserService;

    @Test
    void adminInit() {
        SysUser sysUser = new SysUser();
        sysUser.setSuperAdmin(1);
        sysUser.setMobile("18781075421");
        sysUser.setUsername("admin");
        sysUser.setPassword(passwordEncoder.encode("dhj461212"));
        sysUser.setStatus(1);

        sysUserService.save(sysUser);
    }
}
