package org.example.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.param.UserPhoneLoginParam;
import org.example.admin.model.param.UserSaveOrUpdateParam;
import org.example.admin.model.result.UserInfoResult;
import org.example.admin.model.result.UserLoginResult;
import org.example.admin.service.SysUserService;
import org.example.framework.common.base.Result;
import org.example.framework.security.core.annotation.Anonymous;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class UserController {

    private final SysUserService sysUserService;

    @PostMapping("/save-or-update")
    @Anonymous
    public Result<Void> save(@RequestBody @Valid UserSaveOrUpdateParam param) {
        sysUserService.userSaveOrUpdate(param);
        return Result.ok();
    }

    @PostMapping("/phone/login")
    @Anonymous
    public Result<UserLoginResult> register(@RequestBody @Valid UserPhoneLoginParam param) {
        return Result.ok(sysUserService.phoneLogin(param));
    }

    @GetMapping("/info")
    public Result<UserInfoResult> info() {
        return Result.ok(sysUserService.getUserInfo());
    }
}
