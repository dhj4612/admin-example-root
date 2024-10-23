package org.example.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.param.*;
import org.example.admin.model.result.*;
import org.example.admin.service.SysUserService;
import org.example.framework.common.base.BasePageResult;
import org.example.framework.common.base.Result;
import org.example.framework.idempotent.core.NoDuplicateSubmit;
import org.example.framework.security.core.annotation.Anonymous;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class UserController {

    private final SysUserService sysUserService;

    @PostMapping("/add-or-update")
    @PreAuthorize("@se.hasAllAuthorities('sys:user:add','sys:user:update','sys:user:info')")
    @NoDuplicateSubmit
    public Result<Void> saveOrUpdate(@RequestBody @Valid UserSaveOrUpdateParam param) {
        sysUserService.userSaveOrUpdate(param);
        return Result.ok();
    }

    @PostMapping("/page")
    @PreAuthorize("@se.hasAuthorities('sys:user:list')")
    public Result<BasePageResult<SysUserResult>> page(@RequestBody UserListQueryParam param) {
        return Result.ok(sysUserService.userPage(param));
    }

    @PostMapping("/update-info")
    @PreAuthorize("@se.hasAuthorities('sys:user:info')")
    public Result<SysUserInfoResult> updateInfo(@RequestBody @Valid SysUserInfoQueryParam param) {
        return Result.ok(sysUserService.getUpdateUserInfo(param));
    }

    @PostMapping("/del")
    @PreAuthorize("@se.hasAuthorities('sys:user:del')")
    public Result<Void> del(@RequestBody @Valid UserDelParam param) {
        sysUserService.delUser(param);
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
