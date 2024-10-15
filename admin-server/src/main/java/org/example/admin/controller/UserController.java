package org.example.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.dto.req.UserPhoneLoginDTO;
import org.example.admin.model.dto.req.UserPhoneRegisterDTO;
import org.example.admin.model.dto.resp.UserLoginRespDTO;
import org.example.admin.service.SysUserService;
import org.example.framework.common.base.Result;
import org.example.framework.security.core.annotation.Anonymous;
import org.example.framework.security.core.user.SecurityUserContext;
import org.example.framework.security.core.user.UserAuthorized;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class UserController {

    private final SysUserService sysUserService;

    @PostMapping("/phone/register")
    @Anonymous
    public Result<Void> register(@RequestBody @Valid UserPhoneRegisterDTO param) {
        sysUserService.phoneRegister(param);
        return Result.ok();
    }

    @PostMapping("/phone/login")
    @Anonymous
    public Result<UserLoginRespDTO> register(@RequestBody @Valid UserPhoneLoginDTO param) {
        return Result.ok(sysUserService.phoneLogin(param));
    }

    @GetMapping("/info")
    public Result<UserAuthorized> info() {
        return Result.ok(SecurityUserContext.ensureGetUser());
    }
}
