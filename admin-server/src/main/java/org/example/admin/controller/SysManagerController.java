package org.example.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.admin.model.param.*;
import org.example.admin.model.result.SysMenuResult;
import org.example.admin.model.result.SysRoleInfoResult;
import org.example.admin.model.result.SysRoleResult;
import org.example.admin.service.SysMenuService;
import org.example.admin.service.SysRoleService;
import org.example.framework.common.base.BasePageResult;
import org.example.framework.common.base.Result;
import org.example.framework.idempotent.core.NoDuplicateSubmit;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/manager")
@RequiredArgsConstructor
public class SysManagerController {
    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;

    @PostMapping("/menu-add-or-update")
    @PreAuthorize("@se.hasAllAuthorities('sys:menu:add','sys:menu:update','sys:role:info')")
    @NoDuplicateSubmit
    public Result<Void> addOrUpdateMenu(@RequestBody @Valid MenuAddOrUpdateParam param) {
        sysMenuService.addOrUpdateMenu(param);
        return Result.ok();
    }

    @GetMapping("/menu/list")
    @PreAuthorize("@se.hasAuthorities('sys:menu:list')")
    public Result<List<SysMenuResult>> menuList(@RequestParam(value = "type", required = false) Integer type) {
        return Result.ok(sysMenuService.geMenuList(type));
    }

    @PostMapping("/menu/info")
    @PreAuthorize("@se.hasAuthorities('sys:menu:info')")
    public Result<SysMenuResult> menuInfo(@RequestBody @Valid MenuInfoQueryParam param) {
        return Result.ok(BeanUtil.toBean(sysMenuService.getById(param.id()), SysMenuResult.class));
    }

    @PostMapping("/menu/del")
    @PreAuthorize("@se.hasAuthorities('sys:menu:del')")
    public Result<Void> menuDel(@RequestBody @Valid MenuDelParam param) {
        sysMenuService.delMenu(param);
        return Result.ok();
    }

    @PostMapping("/role-add-or-update")
    @PreAuthorize("@se.hasAllAuthorities('sys:role:add','sys:role:update','sys:role:info')")
    @NoDuplicateSubmit
    public Result<Void> addOrUpdateRole(@RequestBody @Valid RoleAddOrUpdateParam param) {
        sysRoleService.addOrUpdateRole(param);
        return Result.ok();
    }

    @PostMapping("/role/page")
    @PreAuthorize("@se.hasAuthorities('sys:role:list')")
    public Result<BasePageResult<SysRoleResult>> rolePage(@RequestBody RoleListQueryParam param) {
        return Result.ok(sysRoleService.rolePage(param));
    }

    @PostMapping("/role/list")
    @PreAuthorize("@se.hasAuthorities('sys:role:list')")
    public Result<List<SysRoleResult>> roleList(@RequestBody RoleListQueryParam param) {
        return Result.ok(sysRoleService.roleList(param));
    }

    @PostMapping("/role/info")
    @PreAuthorize("@se.hasAuthorities('sys:role:info')")
    public Result<SysRoleInfoResult> roleInfo(@RequestBody @Valid RoleInfoQueryParam param) {
        return Result.ok(sysRoleService.roleInfo(param));
    }

    @PostMapping("/role/del")
    @PreAuthorize("@se.hasAuthorities('sys:role:del')")
    public Result<Void> roleDel(@RequestBody @Valid RoleDelParam param) {
        sysRoleService.delRole(param);
        return Result.ok();
    }
}
