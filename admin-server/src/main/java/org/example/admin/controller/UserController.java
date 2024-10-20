package org.example.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.param.UserPhoneLoginParam;
import org.example.admin.model.param.UserSaveOrUpdateParam;
import org.example.admin.model.result.SysMenuResult;
import org.example.admin.model.result.UserInfoResult;
import org.example.admin.model.result.UserLoginResult;
import org.example.admin.service.SysUserService;
import org.example.framework.common.base.Result;
import org.example.framework.security.core.annotation.Anonymous;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class UserController {

    private final SysUserService sysUserService;

    @PostMapping("/save-or-update")
    public Result<Void> save(@RequestBody @Valid UserSaveOrUpdateParam param) {
        sysUserService.userSaveOrUpdate(param);
        return Result.ok();
    }

    @PostMapping("/phone/login")
    @Anonymous
    public Result<UserLoginResult> register(@RequestBody @Valid UserPhoneLoginParam param) {
        return Result.ok(sysUserService.phoneLogin(param));
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        sysUserService.logout();
        return Result.ok();
    }

    @GetMapping("/info")
    public Result<UserInfoResult> info() {
        return Result.ok(sysUserService.getUserInfo());
    }

    @GetMapping("/authorities")
    public Result<Set<String>> authorities() {
        return Result.ok(sysUserService.getUserAuthoritiesAndRoles());
    }

    @GetMapping("/menu/nav")
    public Result<List<SysMenuResult>> menuNav() {
        return Result.ok(sysUserService.getUserMenuNav());
    }
}
